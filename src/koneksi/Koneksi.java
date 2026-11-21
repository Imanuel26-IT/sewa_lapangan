package koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Koneksi {
    // GANTI NAMA DATABASE SESUAI PUNYA KALIAN
    // Untuk testing pakai database dummy
    private static final String URL = "jdbc:mysql://localhost:3306/db_booking_lapangan_dummy?useSSL=false&serverTimezone=Asia/Jakarta&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASS = ""; // XAMPP default kosong, kalau pakai password isi di sini

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Koneksi Database!\n" + e.getMessage() + "\n\nPastikan XAMPP MySQL sudah jalan dan database sudah di-import.", "Error Koneksi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
