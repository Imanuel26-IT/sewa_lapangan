CARA MENJALANKAN DI NETBEANS (5 MENIT)
====================================
1. Buka XAMPP -> Start Apache & MySQL
2. Buka phpMyAdmin -> Import file db_booking_lapangan_dummy.sql (yang sudah dikasih sebelumnya)
3. Buka NetBeans -> File -> New Project -> Java with Ant -> Java Application
   - Project Name: BookingLapangan_Kelompok5
   - Uncheck "Create Main Class" (karena sudah ada Main.java)
4. Copy folder src/ dari ZIP ini ke dalam project (replace folder src yang kosong)
   - Atau: Copy paste isi file satu per satu sesuai package-nya
5. Tambahkan Library MySQL Connector:
   - Klik kanan Project -> Properties -> Libraries -> Add JAR/Folder
   - Pilih file mysql-connector-j-8.0.xx.jar
   - Kalau belum punya, download di: https://dev.mysql.com/downloads/connector/j/
   - Atau di NetBeans: Tools -> Libraries -> cari MySQL JDBC Driver
6. Klik kanan Main.java -> Run File
   - Atau tekan Shift+F6
7. Menu Utama akan muncul. Coba:
   - Form Transaksi: pilih booking yang menunggu pembayaran, simpan
   - Laporan: lihat validasi data error 99999 dan total pendapatan

STRUKTUR FILE:
src/Main.java -> titik awal program
src/koneksi/Koneksi.java -> koneksi JDBC ke MySQL
src/model/Transaksi.java -> model + rumus hitungTotal()
src/service/TransaksiService.java -> logika simpan, batal, laporan
src/gui/MainMenu.java -> menu utama
src/gui/FormTransaksi.java -> form pembayaran
src/gui/FormLaporan.java -> laporan + pembatalan

CATATAN PENTING:
- Kalau password MySQL kamu bukan kosong, ubah di Koneksi.java: PASS = "passwordkamu"
- Kalau nama database asli beda, ubah URL di Koneksi.java dari db_booking_lapangan_dummy jadi nama database asli.
