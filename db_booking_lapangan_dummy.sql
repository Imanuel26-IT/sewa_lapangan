-- ======================================================
-- DATABASE DUMMY UNTUK UJI COBA
-- Project: Sistem Booking Lapangan
-- Database: db_booking_lapangan_dummy
-- Khusus untuk: Kelompok 5 (Transaksi & Testing)
-- ======================================================

DROP DATABASE IF EXISTS db_booking_lapangan_dummy;
CREATE DATABASE db_booking_lapangan_dummy;
USE db_booking_lapangan_dummy;

-- 1. ADMIN
CREATE TABLE admin (
    id_admin INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nama_admin VARCHAR(100) NOT NULL
);

-- 2. MEMBER
CREATE TABLE member (
    id_member INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    no_hp VARCHAR(15),
    alamat TEXT
);

-- 3. LAPANGAN
CREATE TABLE lapangan (
    id_lapangan INT AUTO_INCREMENT PRIMARY KEY,
    nama_lapangan VARCHAR(100) NOT NULL,
    jenis VARCHAR(50),
    harga_per_jam INT NOT NULL,
    status VARCHAR(20) DEFAULT 'Tersedia'
);

-- 4. JADWAL
CREATE TABLE jadwal (
    id_jadwal INT AUTO_INCREMENT PRIMARY KEY,
    id_lapangan INT NOT NULL,
    tanggal DATE NOT NULL,
    jam_mulai TIME NOT NULL,
    jam_selesai TIME NOT NULL,
    status VARCHAR(20) DEFAULT 'Tersedia',
    FOREIGN KEY (id_lapangan) REFERENCES lapangan(id_lapangan) ON DELETE CASCADE
);

-- 5. BOOKING
CREATE TABLE booking (
    id_booking INT AUTO_INCREMENT PRIMARY KEY,
    id_member INT NOT NULL,
    id_jadwal INT NOT NULL,
    tanggal_booking DATE NOT NULL,
    status_booking VARCHAR(30) DEFAULT 'Menunggu Pembayaran',
    FOREIGN KEY (id_member) REFERENCES member(id_member) ON DELETE CASCADE,
    FOREIGN KEY (id_jadwal) REFERENCES jadwal(id_jadwal) ON DELETE CASCADE
);

-- 6. TRANSAKSI
CREATE TABLE transaksi (
    id_transaksi INT AUTO_INCREMENT PRIMARY KEY,
    id_booking INT NOT NULL,
    tanggal_transaksi DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_harga INT NOT NULL,
    metode_pembayaran VARCHAR(50),
    status_pembayaran VARCHAR(30) DEFAULT 'Belum Lunas',
    FOREIGN KEY (id_booking) REFERENCES booking(id_booking) ON DELETE CASCADE
);

-- ======================================================
-- DATA DUMMY
-- ======================================================

-- ADMIN
INSERT INTO admin (username, password, nama_admin) VALUES
('admin', 'admin123', 'Admin Utama'),
('budi_admin', 'budi123', 'Budi Santoso');

-- MEMBER (10 orang, ada nama aneh untuk tes)
INSERT INTO member (nama, username, password, no_hp, alamat) VALUES
('Valen Linggi', 'valen', '12345', '081234567890', 'Jl. Merdeka No. 1'),
('Andi Pratama', 'andi', '12345', '081234567891', 'Jl. Sudirman No. 12'),
('Siti Nurhaliza', 'siti', '12345', '081234567892', 'Jl. Pahlawan No. 5'),
('Bambang Wijaya', 'bambang', '12345', '081234567893', 'Jl. Kenanga No. 8'),
('Rina Marlina', 'rina', '12345', '081234567894', 'Jl. Melati No. 20'),
('Dedi Kurniawan', 'dedi', '12345', '081234567895', 'Jl. Anggrek No. 3'),
('Putri Ayu', 'putri', '12345', '081234567896', 'Jl. Mawar No. 15'),
('Joko Susilo', 'joko', '12345', '081234567897', 'Jl. Flamboyan No. 7'),
('Ayu Lestari', 'ayu', '12345', '081234567898', 'Jl. Dahlia No. 9'),
('Fajar Nugroho', 'fajar', '12345', '081234567899', 'Jl. Kamboja No. 11');

-- LAPANGAN (5 lapangan, harga beda-beda)
INSERT INTO lapangan (nama_lapangan, jenis, harga_per_jam, status) VALUES
('Lapangan A - Futsal', 'Futsal', 100000, 'Tersedia'),
('Lapangan B - Futsal VIP', 'Futsal', 150000, 'Tersedia'),
('Lapangan C - Badminton', 'Badminton', 50000, 'Tersedia'),
('Lapangan D - Basket', 'Basket', 80000, 'Maintenance'),
('Lapangan E - Futsal Mini', 'Futsal', 75000, 'Tersedia');

-- JADWAL (15 jadwal, tanggal 20-22 Nov 2026)
INSERT INTO jadwal (id_lapangan, tanggal, jam_mulai, jam_selesai, status) VALUES
(1, '2026-11-20', '08:00:00', '09:00:00', 'Dipesan'),
(1, '2026-11-20', '09:00:00', '11:00:00', 'Dipesan'),
(1, '2026-11-20', '13:00:00', '14:00:00', 'Tersedia'),
(1, '2026-11-21', '15:00:00', '17:00:00', 'Dipesan'),
(1, '2026-11-21', '19:00:00', '20:00:00', 'Tersedia'),
(2, '2026-11-20', '10:00:00', '12:00:00', 'Dipesan'),
(2, '2026-11-21', '08:00:00', '09:00:00', 'Dipesan'),
(2, '2026-11-22', '16:00:00', '18:00:00', 'Tersedia'),
(3, '2026-11-20', '07:00:00', '08:00:00', 'Dipesan'),
(3, '2026-11-21', '09:00:00', '11:00:00', 'Dipesan'),
(3, '2026-11-22', '13:00:00', '14:00:00', 'Tersedia'),
(4, '2026-11-20', '15:00:00', '16:00:00', 'Dipesan'),
(5, '2026-11-21', '10:00:00', '11:00:00', 'Dipesan'),
(5, '2026-11-22', '08:00:00', '10:00:00', 'Dipesan'),
(5, '2026-11-22', '14:00:00', '15:00:00', 'Tersedia');

-- BOOKING (12 booking, berbagai status)
INSERT INTO booking (id_member, id_jadwal, tanggal_booking, status_booking) VALUES
(1, 1, '2026-11-19', 'Terkonfirmasi'),
(2, 2, '2026-11-19', 'Terkonfirmasi'),
(3, 4, '2026-11-20', 'Menunggu Pembayaran'),
(4, 6, '2026-11-19', 'Terkonfirmasi'),
(5, 7, '2026-11-20', 'Terkonfirmasi'),
(1, 9, '2026-11-19', 'Dibatalkan'),
(6, 10, '2026-11-20', 'Terkonfirmasi'),
(7, 12, '2026-11-19', 'Dibatalkan'),
(8, 13, '2026-11-21', 'Terkonfirmasi'),
(9, 14, '2026-11-21', 'Menunggu Pembayaran'),
(10, 3, '2026-11-20', 'Menunggu Pembayaran'),
(2, 5, '2026-11-20', 'Menunggu Pembayaran');

-- TRANSAKSI (Data untuk tes perhitungan kamu)
-- Perhitungan yang BENAR untuk referensi:
-- id 1: Lap A 100rb x 1 jam = 100rb (BENAR)
-- id 2: Lap A 100rb x 2 jam = 200rb (BENAR)
-- id 3: Lap A 100rb x 2 jam = 200rb (BENAR, tapi belum lunas)
-- id 4: Lap B 150rb x 2 jam = 300rb (BENAR)
-- id 5: Lap B 150rb x 1 jam = 150rb (BENAR)
-- id 6: Lap C 50rb x 1 jam = 50rb (TAPI DIBATALKAN - harus 0 / tidak masuk laporan)
-- id 7: Lap C 50rb x 2 jam = 100rb (BENAR)
-- id 8: Lap D 80rb x 1 jam = 80rb (TAPI DIBATALKAN)
-- id 9: Lap E 75rb x 1 jam = 75rb (BENAR)
-- id 10: Lap E 75rb x 2 jam = 150rb (BENAR, tapi belum lunas)
-- id 11: KASUS ERROR - Harusnya 100rb x 1 jam = 100rb, tapi diisi 99999 (SALAH, untuk tes validasi)
-- id 12: KASUS ERROR - Harusnya 100rb x 1 jam = 100rb, tapi diisi 0 (SALAH)

INSERT INTO transaksi (id_booking, tanggal_transaksi, total_harga, metode_pembayaran, status_pembayaran) VALUES
(1, '2026-11-19 08:15:00', 100000, 'Cash', 'Lunas'),
(2, '2026-11-19 09:30:00', 200000, 'Transfer', 'Lunas'),
(3, '2026-11-20 10:00:00', 200000, 'QRIS', 'Belum Lunas'),
(4, '2026-11-19 11:45:00', 300000, 'Cash', 'Lunas'),
(5, '2026-11-20 08:20:00', 150000, 'Transfer', 'Lunas'),
(6, '2026-11-19 12:00:00', 50000, 'Cash', 'Dibatalkan'),
(7, '2026-11-20 09:10:00', 100000, 'QRIS', 'Lunas'),
(8, '2026-11-19 14:00:00', 80000, 'Cash', 'Dibatalkan'),
(9, '2026-11-21 10:05:00', 75000, 'Transfer', 'Lunas'),
(10, '2026-11-21 08:30:00', 150000, 'Cash', 'Belum Lunas'),
(11, '2026-11-20 13:00:00', 99999, 'Cash', 'Lunas'),
(12, '2026-11-20 19:05:00', 0, 'Transfer', 'Belum Lunas');

-- ======================================================
-- QUERY UNTUK TESTING (Jangan dihapus, untuk laporan)
-- ======================================================
-- 1. Total Pendapatan Lunas:
-- SELECT SUM(total_harga) FROM transaksi WHERE status_pembayaran='Lunas' AND total_harga > 0;
-- Hasil yang benar: 100000+200000+300000+150000+100000+75000+99999 = 1.024.999 (termasuk data error)
-- Kalau filter data error, harusnya: 925.000

-- 2. Laporan Gabungan (untuk GUI Laporan):
-- SELECT t.id_transaksi, m.nama, l.nama_lapangan, j.tanggal, j.jam_mulai, j.jam_selesai, t.total_harga, t.status_pembayaran
-- FROM transaksi t
-- JOIN booking b ON t.id_booking = b.id_booking
-- JOIN member m ON b.id_member = m.id_member
-- JOIN jadwal j ON b.id_jadwal = j.id_jadwal
-- JOIN lapangan l ON j.id_lapangan = l.id_lapangan
-- ORDER BY t.tanggal_transaksi DESC;
