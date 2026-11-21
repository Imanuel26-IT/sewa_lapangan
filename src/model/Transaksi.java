package model;

public class Transaksi {
    private int idTransaksi;
    private int idBooking;
    private String namaMember;
    private String namaLapangan;
    private String tanggalJadwal;
    private String jam;
    private int hargaPerJam;
    private int durasiJam;
    private int totalHarga;
    private String metodePembayaran;
    private String statusPembayaran;
    private String tanggalTransaksi;

    // Getter Setter
    public int getIdTransaksi() { return idTransaksi; }
    public void setIdTransaksi(int idTransaksi) { this.idTransaksi = idTransaksi; }
    public int getIdBooking() { return idBooking; }
    public void setIdBooking(int idBooking) { this.idBooking = idBooking; }
    public String getNamaMember() { return namaMember; }
    public void setNamaMember(String namaMember) { this.namaMember = namaMember; }
    public String getNamaLapangan() { return namaLapangan; }
    public void setNamaLapangan(String namaLapangan) { this.namaLapangan = namaLapangan; }
    public String getTanggalJadwal() { return tanggalJadwal; }
    public void setTanggalJadwal(String tanggalJadwal) { this.tanggalJadwal = tanggalJadwal; }
    public String getJam() { return jam; }
    public void setJam(String jam) { this.jam = jam; }
    public int getHargaPerJam() { return hargaPerJam; }
    public void setHargaPerJam(int hargaPerJam) { this.hargaPerJam = hargaPerJam; }
    public int getDurasiJam() { return durasiJam; }
    public void setDurasiJam(int durasiJam) { this.durasiJam = durasiJam; }
    public int getTotalHarga() { return totalHarga; }
    public void setTotalHarga(int totalHarga) { this.totalHarga = totalHarga; }
    public String getMetodePembayaran() { return metodePembayaran; }
    public void setMetodePembayaran(String metodePembayaran) { this.metodePembayaran = metodePembayaran; }
    public String getStatusPembayaran() { return statusPembayaran; }
    public void setStatusPembayaran(String statusPembayaran) { this.statusPembayaran = statusPembayaran; }
    public String getTanggalTransaksi() { return tanggalTransaksi; }
    public void setTanggalTransaksi(String tanggalTransaksi) { this.tanggalTransaksi = tanggalTransaksi; }

    // LOGIKA INTI KELOMPOK 5: Perhitungan Total Harga
    public static int hitungTotal(int hargaPerJam, int durasiJam) {
        if (hargaPerJam <= 0 || durasiJam <= 0) return 0;
        return hargaPerJam * durasiJam;
    }

    // Validasi: Cek apakah total di DB sesuai hitungan
    public boolean isValid() {
        int seharusnya = hitungTotal(hargaPerJam, durasiJam);
        return totalHarga == seharusnya && totalHarga > 0;
    }
}
