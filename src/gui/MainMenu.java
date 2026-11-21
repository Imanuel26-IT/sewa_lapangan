package gui;
import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Sistem Booking Lapangan - Kelompok 5 | Transaksi & Testing");
        setSize(520, 340);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel judul = new JLabel("<html><center><h1>BOOKING LAPANGAN</h1><p>Kelompok 5 - Transaksi & Testing</p></center></html>", SwingConstants.CENTER);
        judul.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
        add(judul, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(3,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,40,20,40));
        JButton btnTransaksi = new JButton("💳  FORM TRANSAKSI / PEMBAYARAN");
        JButton btnLaporan = new JButton("📊  LAPORAN TRANSAKSI");
        JButton btnTesting = new JButton("🧪  TESTING - Cek Perhitungan");

        btnTransaksi.setFont(new Font("Arial", Font.BOLD, 14));
        btnLaporan.setFont(new Font("Arial", Font.BOLD, 14));
        btnTesting.setFont(new Font("Arial", Font.BOLD, 14));

        btnTransaksi.addActionListener(e -> new FormTransaksi().setVisible(true));
        btnLaporan.addActionListener(e -> new FormLaporan().setVisible(true));
        btnTesting.addActionListener(e -> {
            // Testing sederhana
            int hasil1 = model.Transaksi.hitungTotal(100000, 2);
            int hasil2 = model.Transaksi.hitungTotal(50000, 0);
            String msg = "TEST 1: 100.000 x 2 jam = " + hasil1 + " (Harusnya 200000) -> " + (hasil1==200000?"PASS ✅":"FAIL ❌") + "\n" +
                         "TEST 2: 50.000 x 0 jam = " + hasil2 + " (Harusnya 0) -> " + (hasil2==0?"PASS ✅":"FAIL ❌") + "\n" +
                         "TEST 3: Validasi data error 99999 -> " + (99999 % 100000 != 0 ? "TERDETEKSI SEBAGAI ERROR ✅" : "GAGAL");
            JOptionPane.showMessageDialog(this, msg, "Hasil Testing", JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(btnTransaksi); panel.add(btnLaporan); panel.add(btnTesting);
        add(panel, BorderLayout.CENTER);

        JLabel footer = new JLabel("Database: db_booking_lapangan_dummy | Pastikan XAMPP MySQL Jalan", SwingConstants.CENTER);
        footer.setBorder(BorderFactory.createEmptyBorder(0,0,15,0));
        add(footer, BorderLayout.SOUTH);
    }
}
