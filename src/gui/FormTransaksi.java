package gui;
import service.TransaksiService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormTransaksi extends JFrame {
    private JComboBox<String> cbBooking;
    private JTextField txtTotal;
    private JComboBox<String> cbMetode, cbStatus;
    private TransaksiService service = new TransaksiService();
    private List<String> dataBooking;

    public FormTransaksi() {
        setTitle("Form Transaksi - Pembayaran");
        setSize(620, 420);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8); gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblJudul = new JLabel("FORM PEMBAYARAN / TRANSAKSI", SwingConstants.CENTER);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2; add(lblJudul, gbc); gbc.gridwidth=1;

        gbc.gridx=0; gbc.gridy=1; add(new JLabel("Pilih Booking (Menunggu Pembayaran):"), gbc);
        cbBooking = new JComboBox<>(); gbc.gridx=1; gbc.gridy=1; add(cbBooking, gbc);

        gbc.gridx=0; gbc.gridy=2; add(new JLabel("Total Harga (Rp):"), gbc);
        txtTotal = new JTextField(); txtTotal.setEditable(false); txtTotal.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx=1; gbc.gridy=2; add(txtTotal, gbc);

        gbc.gridx=0; gbc.gridy=3; add(new JLabel("Metode Pembayaran:"), gbc);
        cbMetode = new JComboBox<>(new String[]{"Cash","Transfer","QRIS"});
        gbc.gridx=1; gbc.gridy=3; add(cbMetode, gbc);

        gbc.gridx=0; gbc.gridy=4; add(new JLabel("Status Pembayaran:"), gbc);
        cbStatus = new JComboBox<>(new String[]{"Lunas","Belum Lunas"});
        gbc.gridx=1; gbc.gridy=4; add(cbStatus, gbc);

        JButton btnSimpan = new JButton("💾 SIMPAN TRANSAKSI");
        btnSimpan.setBackground(new Color(46,204,113)); btnSimpan.setForeground(Color.WHITE); btnSimpan.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx=0; gbc.gridy=5; gbc.gridwidth=2; add(btnSimpan, gbc);

        JButton btnRefresh = new JButton("🔄 Refresh Data Booking");
        gbc.gridx=0; gbc.gridy=6; gbc.gridwidth=2; add(btnRefresh, gbc);

        JLabel lblInfo = new JLabel("<html><i>Total dihitung otomatis: Harga/Jam x Durasi.<br>Cek di Laporan untuk validasi data error.</i></html>");
        gbc.gridx=0; gbc.gridy=7; gbc.gridwidth=2; add(lblInfo, gbc);

        loadBooking();
        cbBooking.addActionListener(e -> updateTotal());
        btnRefresh.addActionListener(e -> loadBooking());
        btnSimpan.addActionListener(e -> simpan());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void loadBooking() {
        dataBooking = service.getBookingMenunggu();
        cbBooking.removeAllItems();
        if (dataBooking.isEmpty()) cbBooking.addItem("Tidak ada booking menunggu pembayaran");
        else for (String s : dataBooking) cbBooking.addItem(s);
        updateTotal();
    }
    private void updateTotal() {
        if (dataBooking==null || dataBooking.isEmpty()) { txtTotal.setText("0"); return; }
        try {
            String selected = (String) cbBooking.getSelectedItem();
            // Ambil TOTAL: di akhir string
            String totalPart = selected.substring(selected.lastIndexOf("TOTAL:")+6).trim();
            txtTotal.setText(totalPart);
        } catch (Exception e) { txtTotal.setText("0"); }
    }
    private void simpan() {
        if (dataBooking==null || dataBooking.isEmpty()) { JOptionPane.showMessageDialog(this, "Tidak ada booking!"); return; }
        try {
            String selected = (String) cbBooking.getSelectedItem();
            int idBooking = Integer.parseInt(selected.split(" \\| ")[0].trim());
            int total = Integer.parseInt(txtTotal.getText().trim());
            String metode = (String) cbMetode.getSelectedItem();
            String status = (String) cbStatus.getSelectedItem();
            boolean ok = service.simpanTransaksi(idBooking, total, metode, status);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Transaksi Berhasil Disimpan!\nBooking ID: " + idBooking + "\nTotal: Rp " + total + "\nStatus: " + status);
                loadBooking();
            } else JOptionPane.showMessageDialog(this, "Gagal Simpan! Cek Koneksi.");
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }
}
