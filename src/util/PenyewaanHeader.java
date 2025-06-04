package util;

import java.math.BigDecimal;
import java.util.Date;

public class PenyewaanHeader {
    private String kodePenyewaan;
    private String namaPelanggan;
    private String noKtp;
    private String noHp;
    private String alamat;
    private Date tanggalSewa;
    private Date tanggalKembali;
    private Integer totalHari;
    private BigDecimal totalBiaya;
    private BigDecimal uangMuka;
    private BigDecimal sisaBayar;
    private String status;
    private String keterangan;
    private boolean denganDriver;
    private String namaDriver;
    private String noHpDriver;

    // Default constructor
    public PenyewaanHeader() {}

    // Constructor dengan parameter
    public PenyewaanHeader(String kodePenyewaan, String namaPelanggan, String noKtp, 
                          String noHp, String alamat, Date tanggalSewa, Date tanggalKembali,
                          Integer totalHari, BigDecimal totalBiaya, BigDecimal uangMuka, 
                          BigDecimal sisaBayar, String status) {
        this.kodePenyewaan = kodePenyewaan;
        this.namaPelanggan = namaPelanggan;
        this.noKtp = noKtp;
        this.noHp = noHp;
        this.alamat = alamat;
        this.tanggalSewa = tanggalSewa;
        this.tanggalKembali = tanggalKembali;
        this.totalHari = totalHari;
        this.totalBiaya = totalBiaya;
        this.uangMuka = uangMuka;
        this.sisaBayar = sisaBayar;
        this.status = status;
    }

    // Getters and Setters
    public String getKodePenyewaan() {
        return kodePenyewaan;
    }

    public void setKodePenyewaan(String kodePenyewaan) {
        this.kodePenyewaan = kodePenyewaan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Date getTanggalSewa() {
        return tanggalSewa;
    }

    public void setTanggalSewa(Date tanggalSewa) {
        this.tanggalSewa = tanggalSewa;
    }

    public Date getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public Integer getTotalHari() {
        return totalHari;
    }

    public void setTotalHari(Integer totalHari) {
        this.totalHari = totalHari;
    }

    public BigDecimal getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(BigDecimal totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public BigDecimal getUangMuka() {
        return uangMuka;
    }

    public void setUangMuka(BigDecimal uangMuka) {
        this.uangMuka = uangMuka;
    }

    public BigDecimal getSisaBayar() {
        return sisaBayar;
    }

    public void setSisaBayar(BigDecimal sisaBayar) {
        this.sisaBayar = sisaBayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public boolean isDenganDriver() {
        return denganDriver;
    }

    public void setDenganDriver(boolean denganDriver) {
        this.denganDriver = denganDriver;
    }

    public String getNamaDriver() {
        return namaDriver;
    }

    public void setNamaDriver(String namaDriver) {
        this.namaDriver = namaDriver;
    }

    public String getNoHpDriver() {
        return noHpDriver;
    }

    public void setNoHpDriver(String noHpDriver) {
        this.noHpDriver = noHpDriver;
    }

    @Override
    public String toString() {
        return "PenyewaanHeader{" +
                "kodePenyewaan='" + kodePenyewaan + '\'' +
                ", namaPelanggan='" + namaPelanggan + '\'' +
                ", totalBiaya=" + totalBiaya +
                ", status='" + status + '\'' +
                '}';
    }
}