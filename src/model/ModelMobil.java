package model;

public class ModelMobil {
    private int id;
    private String noPolisi;
    private String merk;
    private String model;
    private int kapasitasPenumpang;
    private double hargaSewaPerHari;
    private String status;
    
    public ModelMobil() {
    }
    
    public ModelMobil(int id, String noPolisi, String merk, String model, int kapasitasPenumpang, double hargaSewaPerHari, String status) {
        this.id = id;
        this.noPolisi = noPolisi;
        this.merk = merk;
        this.model = model;
        this.kapasitasPenumpang = kapasitasPenumpang;
        this.hargaSewaPerHari = hargaSewaPerHari;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoPolisi() {
        return noPolisi;
    }

    public void setNoPolisi(String noPolisi) {
        this.noPolisi = noPolisi;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getKapasitasPenumpang() {
        return kapasitasPenumpang;
    }

    public void setKapasitasPenumpang(int kapasitasPenumpang) {
        this.kapasitasPenumpang = kapasitasPenumpang;
    }

    public double getHargaSewaPerHari() {
        return hargaSewaPerHari;
    }

    public void setHargaSewaPerHari(double hargaSewaPerHari) {
        this.hargaSewaPerHari = hargaSewaPerHari;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}