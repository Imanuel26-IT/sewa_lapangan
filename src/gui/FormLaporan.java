package gui;
import service.TransaksiService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class FormLaporan extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbFilter;
    private JLabel lblTotal, lblValid;
    private TransaksiService service = new TransaksiService();

    public FormLaporan() {
        setTitle("Laporan Transaksi");
        setSize(1050, 520);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Filter Status:"));
        cbFilter = new JComboBox<>(new String[]{"Semua","Lunas","Belum Lunas","Dibatalkan"});
        top.add(cbFilter);
        JButton btnTampil = new JButton("Tampilkan");
        JButton btnBatal = new JButton("❌ Batalkan Transaksi Terpilih");
        btnBatal.setBackground(new Color(231,76,60)); btnBatal.setForeground(Color.WHITE);
        top.add(btnTampil); top.add(btnBatal);
        add(top, BorderLayout.NORTH);

        String[] cols = {"ID Transaksi","ID Booking","Member","Lapangan","Tanggal","Jam","Harga/Jam","Durasi","Total","Metode","Status","Validasi","Tgl Transaksi"};
        model = new DefaultTableModel(cols, 0) { public boolean isCellEditable(int r, int c){ return false; } };
        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridLayout(2,1));
        lblTotal = new JLabel("Total Pendapatan (Lunas, termasuk data error): Rp 0");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblValid = new JLabel("Total Pendapatan VALID (Lunas, tanpa data error): Rp 0");
        lblValid.setFont(new Font("Arial", Font.BOLD, 14)); lblValid.setForeground(new Color(39,174,96));
        bottom.add(lblTotal); bottom.add(lblValid);
        bottom.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(bottom, BorderLayout.SOUTH);

        btnTampil.addActionListener(e -> loadData());
        cbFilter.addActionListener(e -> loadData());
        btnBatal.addActionListener(e -> batalkan());
        loadData();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void loadData() {
        service.loadLaporan(model, (String) cbFilter.getSelectedItem());
        NumberFormat nf = NumberFormat.getInstance(new Locale("id","ID"));
        lblTotal.setText("Total Pendapatan (Lunas, termasuk data error): Rp " + nf.format(service.getTotalPendapatanLunas()));
        lblValid.setText("Total Pendapatan VALID (Lunas, tanpa data error): Rp " + nf.format(service.getTotalPendapatanValid()));
    }
    private void batalkan() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Pilih dulu transaksi yang mau dibatalkan!"); return; }
        int idTransaksi = (int) model.getValueAt(table.convertRowIndexToModel(row), 0);
        int idBooking = (int) model.getValueAt(table.convertRowIndexToModel(row), 1);
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin batalkan Transaksi ID " + idTransaksi + "?\nJadwal akan kembali Tersedia.", "Konfirmasi Batal", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (service.batalkanTransaksi(idTransaksi, idBooking)) {
                JOptionPane.showMessageDialog(this, "Transaksi Dibatalkan! Jadwal Kembali Tersedia.");
                loadData();
            } else JOptionPane.showMessageDialog(this, "Gagal Membatalkan!");
        }
    }
}
