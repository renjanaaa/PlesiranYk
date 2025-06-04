package model;

public class ModelPelanggan {
    private int id;
    private String nama;
    private String noKtp;
    private String noHp;
    private String email;
    private String alamat;
    private String jenisKelamin;
    private String status;
    
    public ModelPelanggan() {
    }
    
    public ModelPelanggan(int id, String nama, String noKtp, String noHp, String email, String alamat, String jenisKelamin, String status) {
        this.id = id;
        this.nama = nama;
        this.noKtp = noKtp;
        this.noHp = noHp;
        this.email = email;
        this.alamat = alamat;
        this.jenisKelamin = jenisKelamin;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}