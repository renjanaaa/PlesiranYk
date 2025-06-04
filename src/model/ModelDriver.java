package model;

public class ModelDriver {
    private int id;
    private String nama;
    private String noKtp;
    private String noSim;
    private String noHp;
    private String alamat;
    private String status;

    public ModelDriver() {}

    public ModelDriver(int id, String nama, String noKtp, String noHp, String alamat, String status) {
        this.id = id;
        this.nama = nama;
        this.noKtp = noKtp;
        this.noSim = noSim;
        this.noHp = noHp;
        this.alamat = alamat;
        this.status = status;
    }

    public ModelDriver(int aInt, String string, String string0, String string1, String string2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNoKtp() { return noKtp; }
    public void setNoKtp(String noKtp) { this.noKtp = noKtp; }
    
    public String getNoSim() { return noSim; }
    public void setNoSim(String noSim) { this.noSim = noSim; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}