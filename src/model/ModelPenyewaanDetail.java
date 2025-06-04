package model;

public class ModelPenyewaanDetail {
    private int id;
    private ModelMobil mobil;
    private ModelPenyewaan penyewaan;
    private double hargaPerHari;
    private int jumlahHari;

    public ModelPenyewaanDetail() {}
    
    public ModelPenyewaanDetail(ModelMobil mobil, double hargaPerHari, int jumlahHari) {
        this.mobil = mobil;
        this.hargaPerHari = hargaPerHari;
        this.jumlahHari = jumlahHari;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public ModelMobil getMobil() { return mobil; }
    public void setMobil(ModelMobil mobil) { this.mobil = mobil; }

    public ModelPenyewaan getPenyewaan() { return penyewaan; }
    public void setPenyewaan(ModelPenyewaan penyewaan) { this.penyewaan = penyewaan; }

    public double getHargaPerHari() { return hargaPerHari; }
    public void setHargaPerHari(double hargaPerHari) { this.hargaPerHari = hargaPerHari; }

    public int getJumlahHari() { return jumlahHari; }
    public void setJumlahHari(int jumlahHari) { this.jumlahHari = jumlahHari; }

    // SUBTOTAL dihitung otomatis
    public double getSubtotal() {
        return hargaPerHari * jumlahHari;
    }
    
    // Helper methods untuk tampilan
    public String getInfoMobil() {
        return mobil != null ? mobil.getMerk() + " " + mobil.getModel() + " (" + mobil.getNoPolisi() + ")" : "";
    }
    
    public int getIdMobil() {
        return mobil != null ? mobil.getId() : 0;
    }
    
    public void setIdMobil(int idMobil) {
        if (this.mobil == null) {
            this.mobil = new ModelMobil();
        }
        this.mobil.setId(idMobil);
    }
}