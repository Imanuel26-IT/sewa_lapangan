package service;
import koneksi.Koneksi;
import model.Transaksi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TransaksiService {

    // Ambil semua booking yang belum punya transaksi / menunggu pembayaran
    public List<String> getBookingMenunggu() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT b.id_booking, m.nama, l.nama_lapangan, j.tanggal, j.jam_mulai, j.jam_selesai, l.harga_per_jam " +
                     "FROM booking b JOIN member m ON b.id_member=m.id_member " +
                     "JOIN jadwal j ON b.id_jadwal=j.id_jadwal " +
                     "JOIN lapangan l ON j.id_lapangan=l.id_lapangan " +
                     "WHERE b.status_booking='Menunggu Pembayaran' ORDER BY b.id_booking";
        try (Connection conn = Koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                int durasi = rs.getTime("jam_selesai").getHours() - rs.getTime("jam_mulai").getHours();
                if (durasi <= 0) durasi = 1;
                int total = Transaksi.hitungTotal(rs.getInt("harga_per_jam"), durasi);
                list.add(rs.getInt("id_booking") + " | " + rs.getString("nama") + " | " + rs.getString("nama_lapangan") +
                         " | " + rs.getDate("tanggal") + " " + rs.getTime("jam_mulai") + "-" + rs.getTime("jam_selesai") +
                         " | Harga/Jam: " + rs.getInt("harga_per_jam") + " | Durasi: " + durasi + " jam | TOTAL: " + total);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Simpan transaksi baru + update booking jadi Terkonfirmasi
    public boolean simpanTransaksi(int idBooking, int totalHarga, String metode, String status) {
        String insert = "INSERT INTO transaksi (id_booking, total_harga, metode_pembayaran, status_pembayaran) VALUES (?,?,?,?)";
        String updateBooking = "UPDATE booking SET status_booking=? WHERE id_booking=?";
        String updateJadwal = "UPDATE jadwal j JOIN booking b ON j.id_jadwal=b.id_jadwal SET j.status='Dipesan' WHERE b.id_booking=?";
        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(insert)) {
                ps.setInt(1, idBooking); ps.setInt(2, totalHarga); ps.setString(3, metode); ps.setString(4, status);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement(updateBooking)) {
                ps.setString(1, status.equals("Lunas") ? "Terkonfirmasi" : "Menunggu Pembayaran");
                ps.setInt(2, idBooking); ps.executeUpdate();
            }
            if (status.equals("Lunas")) {
                try (PreparedStatement ps = conn.prepareStatement(updateJadwal)) {
                    ps.setInt(1, idBooking); ps.executeUpdate();
                }
            }
            conn.commit(); return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // Batalkan transaksi
    public boolean batalkanTransaksi(int idTransaksi, int idBooking) {
        try (Connection conn = Koneksi.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement("UPDATE transaksi SET status_pembayaran='Dibatalkan' WHERE id_transaksi=?")) {
                ps.setInt(1, idTransaksi); ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("UPDATE booking SET status_booking='Dibatalkan' WHERE id_booking=?")) {
                ps.setInt(1, idBooking); ps.executeUpdate();
            }
            try (PreparedStatement ps = conn.prepareStatement("UPDATE jadwal j JOIN booking b ON j.id_jadwal=b.id_jadwal SET j.status='Tersedia' WHERE b.id_booking=?")) {
                ps.setInt(1, idBooking); ps.executeUpdate();
            }
            conn.commit(); return true;
        } catch (Exception e) { e.printStackTrace(); return false; }
    }

    // Load laporan ke tabel
    public void loadLaporan(DefaultTableModel model, String filterStatus) {
        model.setRowCount(0);
        String sql = "SELECT t.id_transaksi, t.id_booking, m.nama, l.nama_lapangan, j.tanggal, CONCAT(j.jam_mulai,' - ',j.jam_selesai) as jam, " +
                     "l.harga_per_jam, t.total_harga, t.metode_pembayaran, t.status_pembayaran, t.tanggal_transaksi " +
                     "FROM transaksi t JOIN booking b ON t.id_booking=b.id_booking " +
                     "JOIN member m ON b.id_member=m.id_member JOIN jadwal j ON b.id_jadwal=j.id_jadwal " +
                     "JOIN lapangan l ON j.id_lapangan=l.id_lapangan ";
        if (!filterStatus.equals("Semua")) sql += " WHERE t.status_pembayaran='" + filterStatus + "' ";
        sql += " ORDER BY t.tanggal_transaksi DESC";
        try (Connection conn = Koneksi.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                // Hitung durasi dari jam string "08:00:00 - 09:00:00" sederhana: total / harga
                int harga = rs.getInt("harga_per_jam");
                int total = rs.getInt("total_harga");
                int durasi = (harga > 0 && total > 0) ? total / harga : 0;
                String validasi = "";
                if (rs.getString("status_pembayaran").equals("Dibatalkan")) validasi = "DIBATALKAN";
                else if (total <= 0) validasi = "⚠ DATA ERROR (Total 0)";
                else if (harga > 0 && total % harga != 0) validasi = "⚠ DATA MENCURIGAKAN";
                else validasi = "✓ Valid";
                model.addRow(new Object[]{rs.getInt("id_transaksi"), rs.getInt("id_booking"), rs.getString("nama"), rs.getString("nama_lapangan"), rs.getDate("tanggal"), rs.getString("jam"), harga, durasi, total, rs.getString("metode_pembayaran"), rs.getString("status_pembayaran"), validasi, rs.getTimestamp("tanggal_transaksi")});
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public int getTotalPendapatanLunas() {
        String sql = "SELECT SUM(total_harga) as total FROM transaksi WHERE status_pembayaran='Lunas'";
        try (Connection conn = Koneksi.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("total");
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
    public int getTotalPendapatanValid() {
        // Pendapatan yang valid: Lunas DAN total habis dibagi harga (menghindari data error 99999)
        String sql = "SELECT SUM(t.total_harga) as total FROM transaksi t JOIN booking b ON t.id_booking=b.id_booking JOIN jadwal j ON b.id_jadwal=j.id_jadwal JOIN lapangan l ON j.id_lapangan=l.id_lapangan WHERE t.status_pembayaran='Lunas' AND t.total_harga > 0 AND t.total_harga % l.harga_per_jam = 0";
        try (Connection conn = Koneksi.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("total");
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
}
