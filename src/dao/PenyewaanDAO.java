package dao;

import model.ModelPenyewaan;
import model.ModelPelanggan;
import model.ModelDriver;
import model.ModelPenyewaanDetail;
import model.ModelMobil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenyewaanDAO {

    private Connection conn;
    private MobilDAO mobilDAO;

    public PenyewaanDAO(Connection conn) {
        this.conn = conn;
        this.mobilDAO = new MobilDAO();
    }
    
    public List<ModelPenyewaan> getPaginated(int start, int limit) throws SQLException {
    List<ModelPenyewaan> result = new ArrayList<>();
    String sql = """
        SELECT p.*, 
               pel.nama as nama_pelanggan, pel.noKtp, pel.noHp as hp_pelanggan, pel.alamat,
               d.nama as nama_driver, d.noHp as hp_driver, d.noSim
        FROM penyewaan p
        LEFT JOIN pelanggan pel ON p.idPelanggan = pel.id
        LEFT JOIN driver d ON p.idDriver = d.id
        ORDER BY p.tanggalSewa DESC
        LIMIT ? OFFSET ?
        """;
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, limit);
        ps.setInt(2, start);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ModelPenyewaan m = mapResultSetToPenyewaan(rs);
                result.add(m);
            }
        }
    }
    return result;
}

    public int getTotalCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM penyewaan";
        try (Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
                }
            }
        return 0;
    }

    public int getSearchCount(String keyword) throws SQLException {
        String sql = """
            SELECT COUNT(*) FROM penyewaan p
            LEFT JOIN pelanggan pel ON p.idPelanggan = pel.id
            LEFT JOIN driver d ON p.idDriver = d.id
            WHERE p.kodePenyewaan LIKE ? 
            OR pel.nama LIKE ? 
            OR d.nama LIKE ?
            OR p.status LIKE ?
            """;
    
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
        
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                    }
                }
        }
        return 0;
    }

    public boolean insert(ModelPenyewaan m) throws SQLException {
        String sql = "INSERT INTO penyewaan (kodePenyewaan, idPelanggan, idDriver, tanggalSewa, tanggalKembali, totalHari, totalBiaya, uangMuka, sisaBayar, status, keterangan) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getKodePenyewaan());
            ps.setInt(2, m.getPelanggan().getId());
            
            if (m.getDriver() != null) {
                ps.setInt(3, m.getDriver().getId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            
            ps.setDate(4, new java.sql.Date(m.getTanggalSewa().getTime()));
            ps.setDate(5, new java.sql.Date(m.getTanggalKembali().getTime()));
            ps.setInt(6, m.getTotalHari());
            ps.setDouble(7, m.getTotalBiaya());
            ps.setDouble(8, m.getUangMuka());
            ps.setDouble(9, m.getSisaBayar());
            ps.setString(10, m.getStatus());
            ps.setString(11, m.getKeterangan());
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                // Get generated ID
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        m.setId(rs.getInt(1));
                    }
                }
                
                // Insert detail penyewaan
                insertDetails(m);
                return true;
            }
        }
        return false;
    }

    public boolean update(ModelPenyewaan m) throws SQLException {
        String sql = "UPDATE penyewaan SET kodePenyewaan=?, idPelanggan=?, idDriver=?, tanggalSewa=?, tanggalKembali=?, totalHari=?, totalBiaya=?, uangMuka=?, sisaBayar=?, status=?, keterangan=? WHERE id=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getKodePenyewaan());
            ps.setInt(2, m.getPelanggan().getId());
            
            if (m.getDriver() != null) {
                ps.setInt(3, m.getDriver().getId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }
            
            ps.setDate(4, new java.sql.Date(m.getTanggalSewa().getTime()));
            ps.setDate(5, new java.sql.Date(m.getTanggalKembali().getTime()));
            ps.setInt(6, m.getTotalHari());
            ps.setDouble(7, m.getTotalBiaya());
            ps.setDouble(8, m.getUangMuka());
            ps.setDouble(9, m.getSisaBayar());
            ps.setString(10, m.getStatus());
            ps.setString(11, m.getKeterangan());
            ps.setInt(12, m.getId());
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                // Update detail penyewaan
                deleteDetails(m.getId());
                insertDetails(m);
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) throws SQLException {
        // Delete details first (foreign key constraint)
        deleteDetails(id);
        
        String sql = "DELETE FROM penyewaan WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ModelPenyewaan> getAll() throws SQLException {
        List<ModelPenyewaan> list = new ArrayList<>();
        String sql = """
            SELECT p.*, 
                   pel.nama as nama_pelanggan, pel.noKtp, pel.noHp as hp_pelanggan, pel.alamat,
                   d.nama as nama_driver, d.noHp as hp_driver, d.noSim
            FROM penyewaan p
            LEFT JOIN pelanggan pel ON p.idPelanggan = pel.id
            LEFT JOIN driver d ON p.idDriver = d.id
            ORDER BY p.tanggalSewa DESC
            """;
        
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ModelPenyewaan m = mapResultSetToPenyewaan(rs);
                // Load details dengan data mobil lengkap
                m.setDetailPenyewaan(getDetailsByPenyewaanId(m.getId()));
                list.add(m);
            }
        }
        return list;
    }

    public ModelPenyewaan getById(int id) throws SQLException {
        String sql = """
            SELECT p.*, 
                   pel.nama as nama_pelanggan, pel.noKtp, pel.noHp as hp_pelanggan, pel.alamat,
                   d.nama as nama_driver, d.noHp as hp_driver, d.noSim
            FROM penyewaan p
            LEFT JOIN pelanggan pel ON p.idPelanggan = pel.id
            LEFT JOIN driver d ON p.idDriver = d.id
            WHERE p.id = ?
            """;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ModelPenyewaan m = mapResultSetToPenyewaan(rs);
                    // Load details dengan data mobil lengkap
                    m.setDetailPenyewaan(getDetailsByPenyewaanId(id));
                    return m;
                }
            }
        }
        return null;
    }

    public ModelPenyewaan getByKode(String kodePenyewaan) throws SQLException {
        String sql = """
            SELECT p.*, 
                   pel.nama as nama_pelanggan, pel.noKtp, pel.noHp as hp_pelanggan, pel.alamat,
                   d.nama as nama_driver, d.noHp as hp_driver, d.noSim
            FROM penyewaan p
            LEFT JOIN pelanggan pel ON p.idPelanggan = pel.id
            LEFT JOIN driver d ON p.idDriver = d.id
            WHERE p.kodePenyewaan = ?
            """;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodePenyewaan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ModelPenyewaan m = mapResultSetToPenyewaan(rs);
                    m.setDetailPenyewaan(getDetailsByPenyewaanId(m.getId()));
                    return m;
                }
            }
        }
        return null;
    }

    public List<ModelPenyewaan> search(String keyword) throws SQLException {
        List<ModelPenyewaan> list = new ArrayList<>();
        String sql = """
            SELECT p.*, 
                   pel.nama as nama_pelanggan, pel.noKtp, pel.noHp as hp_pelanggan, pel.alamat,
                   d.nama as nama_driver, d.noHp as hp_driver, d.noSim
            FROM penyewaan p
            LEFT JOIN pelanggan pel ON p.idPelanggan = pel.id
            LEFT JOIN driver d ON p.idDriver = d.id
            WHERE p.kodePenyewaan LIKE ? 
               OR pel.nama LIKE ? 
               OR d.nama LIKE ?
               OR p.status LIKE ?
            ORDER BY p.tanggalSewa DESC
            """;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModelPenyewaan m = mapResultSetToPenyewaan(rs);
                    m.setDetailPenyewaan(getDetailsByPenyewaanId(m.getId()));
                    list.add(m);
                }
            }
        }
        return list;
    }

    public List<ModelPenyewaan> getByStatus(String status) throws SQLException {
        List<ModelPenyewaan> list = new ArrayList<>();
        String sql = """
            SELECT p.*, 
                   pel.nama as nama_pelanggan, pel.noKtp, pel.noHp as hp_pelanggan, pel.alamat,
                   d.nama as nama_driver, d.noHp as hp_driver, d.noSim
            FROM penyewaan p
            LEFT JOIN pelanggan pel ON p.idPelanggan = pel.id
            LEFT JOIN driver d ON p.idDriver = d.id
            WHERE p.status = ?
            ORDER BY p.tanggalSewa DESC
            """;
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModelPenyewaan m = mapResultSetToPenyewaan(rs);
                    m.setDetailPenyewaan(getDetailsByPenyewaanId(m.getId()));
                    list.add(m);
                }
            }
        }
        return list;
    }

    // Helper method untuk mapping ResultSet ke ModelPenyewaan
    private ModelPenyewaan mapResultSetToPenyewaan(ResultSet rs) throws SQLException {
        ModelPenyewaan m = new ModelPenyewaan();
        m.setId(rs.getInt("id"));
        m.setKodePenyewaan(rs.getString("kodePenyewaan"));
        m.setTanggalSewa(rs.getDate("tanggalSewa"));
        m.setTanggalKembali(rs.getDate("tanggalKembali"));
        m.setTotalHari(rs.getInt("totalHari"));
        m.setTotalBiaya(rs.getDouble("totalBiaya"));
        m.setUangMuka(rs.getDouble("uangMuka"));
        m.setSisaBayar(rs.getDouble("sisaBayar"));
        m.setStatus(rs.getString("status"));
        m.setKeterangan(rs.getString("keterangan"));
        
        // Set pelanggan
        ModelPelanggan pelanggan = new ModelPelanggan();
        pelanggan.setId(rs.getInt("idPelanggan"));
        pelanggan.setNama(rs.getString("nama_pelanggan"));
        pelanggan.setNoKtp(rs.getString("noKtp"));
        pelanggan.setNoHp(rs.getString("hp_pelanggan"));
        pelanggan.setAlamat(rs.getString("alamat"));
        m.setPelanggan(pelanggan);
        
        // Set driver (jika ada)
        if (rs.getObject("idDriver") != null) {
            ModelDriver driver = new ModelDriver();
            driver.setId(rs.getInt("idDriver"));
            driver.setNama(rs.getString("nama_driver"));
            driver.setNoHp(rs.getString("hp_driver"));
            driver.setNoSim(rs.getString("noSim"));
            m.setDriver(driver);
        }
        
        return m;
    }

    // Methods untuk handle detail penyewaan dengan integrasi MobilDAO
    private void insertDetails(ModelPenyewaan penyewaan) throws SQLException {
        if (penyewaan.getDetailPenyewaan() != null && !penyewaan.getDetailPenyewaan().isEmpty()) {
            String sql = "INSERT INTO penyewaan_detail (idPenyewaan, idMobil, hargaPerHari, durasi, subtotal) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                for (ModelPenyewaanDetail detail : penyewaan.getDetailPenyewaan()) {
                    ps.setInt(1, penyewaan.getId());
                    ps.setInt(2, detail.getMobil().getId());
                    ps.setDouble(3, detail.getHargaPerHari());
                    ps.setInt(4, detail.getJumlahHari());
                    ps.setDouble(5, detail.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void deleteDetails(int idPenyewaan) throws SQLException {
        String sql = "DELETE FROM penyewaan_detail WHERE idPenyewaan = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPenyewaan);
            ps.executeUpdate();
        }
    }

    // INTEGRASI DENGAN MOBILDAO
    private List<ModelPenyewaanDetail> getDetailsByPenyewaanId(int idPenyewaan) throws SQLException {
        List<ModelPenyewaanDetail> details = new ArrayList<>();
        String sql = "SELECT * FROM penyewaan_detail WHERE idPenyewaan = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPenyewaan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModelPenyewaanDetail detail = new ModelPenyewaanDetail();
                    detail.setId(rs.getInt("id"));
                    detail.setHargaPerHari(rs.getDouble("hargaPerHari"));
                    detail.setJumlahHari(rs.getInt("durasi"));
                    
                    // INTEGRASI: Ambil data mobil lengkap dari MobilDAO
                    int idMobil = rs.getInt("idMobil");
                    List<ModelMobil> allMobil = mobilDAO.getAllMobil();
                    ModelMobil mobil = allMobil.stream()
                        .filter(m -> m.getId() == idMobil)
                        .findFirst()
                        .orElse(null);
                    
                    if (mobil != null) {
                        detail.setMobil(mobil);
                    } else {
                        // Fallback jika mobil tidak ditemukan
                        ModelMobil mobilFallback = new ModelMobil();
                        mobilFallback.setId(idMobil);
                        mobilFallback.setMerk("Data tidak ditemukan");
                        mobilFallback.setModel("");
                        mobilFallback.setNoPolisi("N/A");
                        detail.setMobil(mobilFallback);
                    }
                    
                    details.add(detail);
                }
            }
        }
        return details;
    }

    // Method untuk generate kode penyewaan otomatis
    public String generateKodePenyewaan() throws SQLException {
        String sql = "SELECT COUNT(*) + 1 as next_number FROM penyewaan WHERE DATE(tanggalSewa) = CURDATE()";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) {
                int nextNumber = rs.getInt("next_number");
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");
                String dateStr = sdf.format(new java.util.Date());
                return "PYW" + dateStr + String.format("%03d", nextNumber);
            }
        }
        return "PYW" + System.currentTimeMillis(); // fallback
    }

    // Method untuk update status penyewaan
    public boolean updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE penyewaan SET status = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Method untuk mendapatkan penyewaan yang sedang aktif
    public List<ModelPenyewaan> getActivePenyewaan() throws SQLException {
        return getByStatus("Disewa");
    }
}