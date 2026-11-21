# ⚽ Booking Lapangan - Kelompok 5 (AST PBO C)

Aplikasi desktop Java Swing untuk pengelolaan transaksi dan booking lapangan, dilengkapi dengan koneksi basis data MySQL via JDBC.

---

## 📋 Fitur Utama
1. **Menu Utama (Dashboard):** Navigasi ke Form Transaksi, Form Laporan, dan Pengujian Rumus Perhitungan.
2. **Form Transaksi & Pembayaran:** Mengambil data booking yang berstatus *Menunggu Pembayaran*, menghitung total secara otomatis (`Harga/Jam x Durasi`), memilih metode pembayaran (Cash, Transfer, QRIS), dan menyimpan transaksi.
3. **Form Laporan & Pembatalan:** Menampilkan rekapitulasi data transaksi, ringkasan pendapatan, validasi data, serta fitur pembatalan transaksi yang otomatis mengembalikan status jadwal lapangan menjadi *Tersedia*.
4. **Database MySQL:** Sudah disediakan skrip SQL dummy (`db_booking_lapangan_dummy.sql`).

---

## 🚀 Cara Menjalankan di Apache NetBeans

### 1. Persiapan Database (XAMPP)
1. Buka **XAMPP Control Panel**, lalu klik **Start** pada modul **Apache** dan **MySQL**.
2. Buka browser dan akses [http://localhost/phpmyadmin](http://localhost/phpmyadmin).
3. Buat database baru bernama `db_booking_lapangan_dummy` (atau langsung klik tab **Import**).
4. Pilih file **`db_booking_lapangan_dummy.sql`** yang ada di folder project ini, lalu klik **Import / Go**.

> **Catatan Konfigurasi Koneksi:**
> Jika MySQL Anda menggunakan password, buka file `src/koneksi/Koneksi.java` dan sesuaikan variabel `PASS = "password_anda"`.

### 2. Membuka Project di NetBeans
1. Buka aplikasi **Apache NetBeans**.
2. Klik menu **File** -> **Open Project...** (atau tekan `Ctrl + Shift + O`).
3. Cari dan pilih folder **`BookingLapangan_Kelompok5`** (ikon cangkir kopi Java akan otomatis terdeteksi).
4. Klik **Open Project**.
5. Driver **MySQL Connector/J** sudah terpasang otomatis di dalam folder `lib/` dan langsung terhubung dengan NetBeans project classpath.

### 3. Menjalankan Aplikasi
- Tekan **F6** (atau klik tombol hijau **Run Project** di toolbar NetBeans).
- Atau klik kanan file `src/Main.java` -> pilih **Run File** (`Shift + F6`).

---

## 📁 Struktur Project
```
BookingLapangan_Kelompok5/
├── build.xml                       # Ant build script NetBeans
├── manifest.mf                     # Java manifest
├── nbproject/                      # Konfigurasi NetBeans Ant project
│   ├── build-impl.xml
│   ├── genfiles.properties
│   ├── project.properties
│   └── project.xml
├── lib/
│   └── mysql-connector-j-26.10.0.jar # MySQL JDBC Driver
├── src/
│   ├── Main.java                   # Main Entry Point
│   ├── gui/                        # Tampilan UI (MainMenu, FormTransaksi, FormLaporan)
│   ├── koneksi/                    # Koneksi JDBC MySQL (Koneksi.java)
│   ├── model/                      # Class Data Transaksi & Logika Hitung
│   └── service/                    # Business Logic Transaksi (TransaksiService.java)
├── db_booking_lapangan_dummy.sql   # SQL Schema & Dummy Data
└── README.md                       # Dokumentasi Project
```
