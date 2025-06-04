package model;

import java.util.Date;

public class ModelPengembalian {
    private int id;
    private int idPenyewaan;
    private Date tanggalKembaliRiil;
    private double denda;
    private double totalBayar;

    public ModelPengembalian(int id, int idPenyewaan, Date tanggalKembaliRiil, double denda, double totalBayar) {
        this.id = id;
        this.idPenyewaan = idPenyewaan;
        this.tanggalKembaliRiil = tanggalKembaliRiil;
        this.denda = denda;
        this.totalBayar = totalBayar;
    }

    public ModelPengembalian() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPenyewaan() { return idPenyewaan; }
    public void setIdPenyewaan(int idPenyewaan) { this.idPenyewaan = idPenyewaan; }

    public Date getTanggalKembaliRiil() { return tanggalKembaliRiil; }
    public void setTanggalKembaliRiil(Date tanggalKembaliRiil) { this.tanggalKembaliRiil = tanggalKembaliRiil; }

    public double getDenda() { return denda; }
    public void setDenda(double denda) { this.denda = denda; }

    public double getTotalBayar() { return totalBayar; }
    public void setTotalBayar(double totalBayar) { this.totalBayar = totalBayar; }

}

