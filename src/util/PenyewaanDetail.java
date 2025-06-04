package util;

import java.math.BigDecimal;

public class PenyewaanDetail {
    private String mobil;
    private String noPolisi;
    private String merkType;
    private BigDecimal hargaPerHari;
    private Integer jumlahHari;
    private BigDecimal subtotal;

    // Default constructor
    public PenyewaanDetail() {}

    // Constructor dengan parameter
    public PenyewaanDetail(String mobil, String noPolisi, String merkType, 
                          BigDecimal hargaPerHari, Integer jumlahHari, BigDecimal subtotal) {
        this.mobil = mobil;
        this.noPolisi = noPolisi;
        this.merkType = merkType;
        this.hargaPerHari = hargaPerHari;
        this.jumlahHari = jumlahHari;
        this.subtotal = subtotal;
    }

    // Getters and Setters
    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getNoPolisi() {
        return noPolisi;
    }

    public void setNoPolisi(String noPolisi) {
        this.noPolisi = noPolisi;
    }

    public String getMerkType() {
        return merkType;
    }

    public void setMerkType(String merkType) {
        this.merkType = merkType;
    }

    public BigDecimal getHargaPerHari() {
        return hargaPerHari;
    }

    public void setHargaPerHari(BigDecimal hargaPerHari) {
        this.hargaPerHari = hargaPerHari;
    }

    public Integer getJumlahHari() {
        return jumlahHari;
    }

    public void setJumlahHari(Integer jumlahHari) {
        this.jumlahHari = jumlahHari;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "PenyewaanDetail{" +
                "mobil='" + mobil + '\'' +
                ", noPolisi='" + noPolisi + '\'' +
                ", merkType='" + merkType + '\'' +
                ", hargaPerHari=" + hargaPerHari +
                ", jumlahHari=" + jumlahHari +
                ", subtotal=" + subtotal +
                '}';
    }
}