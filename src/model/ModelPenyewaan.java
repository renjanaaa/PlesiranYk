package model;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class ModelPenyewaan {
    private int id;
    private String kodePenyewaan;
    private ModelPelanggan pelanggan;
    private ModelDriver driver; // nullable
    private Date tanggalSewa;
    private Date tanggalKembali;
    private int totalHari;
    private double totalBiaya;
    private double uangMuka;
    private double sisaBayar;
    private String status; // "Booking", "Disewa", "Selesai", "Dibatalkan"
    private String keterangan;
    private List<ModelPenyewaanDetail> detailPenyewaan;

    public ModelPenyewaan() {
        this.detailPenyewaan = new ArrayList<>();
    }

    public ModelPenyewaan(ModelPelanggan pelanggan, Date tanggalSewa, Date tanggalKembali) {
        this();
        this.pelanggan = pelanggan;
        this.tanggalSewa = tanggalSewa;
        this.tanggalKembali = tanggalKembali;
        this.totalHari = calculateTotalHari();
        this.status = "Booking";
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getKodePenyewaan() { return kodePenyewaan; }
    public void setKodePenyewaan(String kodePenyewaan) { this.kodePenyewaan = kodePenyewaan; }

    public ModelPelanggan getPelanggan() { return pelanggan; }
    public void setPelanggan(ModelPelanggan pelanggan) { this.pelanggan = pelanggan; }

    public ModelDriver getDriver() { return driver; }
    public void setDriver(ModelDriver driver) { this.driver = driver; }

    public Date getTanggalSewa() { return tanggalSewa; }
    public void setTanggalSewa(Date tanggalSewa) { 
        this.tanggalSewa = tanggalSewa; 
        this.totalHari = calculateTotalHari();
    }

    public Date getTanggalKembali() { return tanggalKembali; }
    public void setTanggalKembali(Date tanggalKembali) { 
        this.tanggalKembali = tanggalKembali; 
        this.totalHari = calculateTotalHari();
    }

    public int getTotalHari() { return totalHari; }
    public void setTotalHari(int totalHari) { this.totalHari = totalHari; }

    public double getTotalBiaya() { return totalBiaya; }
    public void setTotalBiaya(double totalBiaya) { this.totalBiaya = totalBiaya; }

    public double getUangMuka() { return uangMuka; }
    public void setUangMuka(double uangMuka) { 
        this.uangMuka = uangMuka; 
        this.sisaBayar = this.totalBiaya - uangMuka;
    }

    public double getSisaBayar() { return sisaBayar; }
    public void setSisaBayar(double sisaBayar) { this.sisaBayar = sisaBayar; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getKeterangan() { return keterangan; }
    public void setKeterangan(String keterangan) { this.keterangan = keterangan; }

    public List<ModelPenyewaanDetail> getDetailPenyewaan() { return detailPenyewaan; }
    public void setDetailPenyewaan(List<ModelPenyewaanDetail> detailPenyewaan) { 
        this.detailPenyewaan = detailPenyewaan; 
        calculateTotalBiaya();
    }

    // Helper methods untuk kompatibilitas dengan kode lama
    public int getIdPelanggan() {
        return pelanggan != null ? pelanggan.getId() : 0;
    }

    public void setIdPelanggan(int idPelanggan) {
        if (this.pelanggan == null) {
            this.pelanggan = new ModelPelanggan();
        }
        this.pelanggan.setId(idPelanggan);
    }

    public Integer getIdDriver() {
        return driver != null ? driver.getId() : null;
    }

    public void setIdDriver(Integer idDriver) {
        if (idDriver != null) {
            if (this.driver == null) {
                this.driver = new ModelDriver();
            }
            this.driver.setId(idDriver);
        } else {
            this.driver = null;
        }
    }

    // Business logic methods
    private int calculateTotalHari() {
        if (tanggalSewa != null && tanggalKembali != null) {
            long diffInMillies = tanggalKembali.getTime() - tanggalSewa.getTime();
            return (int) (diffInMillies / (1000 * 60 * 60 * 24)) + 1; // +1 karena termasuk hari pertama
        }
        return 0;
    }

    public void calculateTotalBiaya() {
        double total = 0;
        if (detailPenyewaan != null) {
            for (ModelPenyewaanDetail detail : detailPenyewaan) {
                total += detail.getSubtotal();
            }
        }
        this.totalBiaya = total;
        this.sisaBayar = this.totalBiaya - this.uangMuka;
    }

    public void addDetailPenyewaan(ModelPenyewaanDetail detail) {
        if (this.detailPenyewaan == null) {
            this.detailPenyewaan = new ArrayList<>();
        }
        detail.setPenyewaan(this);
        detail.setJumlahHari(this.totalHari);
        this.detailPenyewaan.add(detail);
        calculateTotalBiaya();
    }

    public void removeDetailPenyewaan(ModelPenyewaanDetail detail) {
        if (this.detailPenyewaan != null) {
            this.detailPenyewaan.remove(detail);
            calculateTotalBiaya();
        }
    }

    // Display methods
    public String getNamaPelanggan() {
        return pelanggan != null ? pelanggan.getNama() : "";
    }

    public String getNamaDriver() {
        return driver != null ? driver.getNama() : "Tanpa Driver";
    }

    public String getInfoPenyewaan() {
        return kodePenyewaan + " - " + getNamaPelanggan() + " (" + status + ")";
    }

    public boolean isDenganDriver() {
        return driver != null;
    }

    public int getJumlahMobil() {
        return detailPenyewaan != null ? detailPenyewaan.size() : 0;
    }

    @Override
    public String toString() {
        return getInfoPenyewaan();
    }
}