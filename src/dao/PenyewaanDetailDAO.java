package dao;

import java.sql.*;
import java.util.*;
import model.ModelMobil;
import model.ModelPenyewaan;
import model.ModelPenyewaanDetail;

public class PenyewaanDetailDAO {
    private final Connection conn;
    private MobilDAO mobilDAO;

    public PenyewaanDetailDAO(Connection conn) {
        this.conn = conn;
        this.mobilDAO = new MobilDAO();
    }

    public boolean insert(ModelPenyewaanDetail pd) throws SQLException {
        String sql = "INSERT INTO penyewaan_detail (idPenyewaan, idMobil, hargaPerHari, durasi, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pd.getPenyewaan().getId());
            ps.setInt(2, pd.getMobil().getId());
            ps.setDouble(3, pd.getHargaPerHari());
            ps.setInt(4, pd.getJumlahHari());
            ps.setDouble(5, pd.getSubtotal());
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        pd.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean update(ModelPenyewaanDetail pd) throws SQLException {
        String sql = "UPDATE penyewaan_detail SET idPenyewaan=?, idMobil=?, hargaPerHari=?, durasi=?, subtotal=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pd.getPenyewaan().getId());
            ps.setInt(2, pd.getMobil().getId());
            ps.setDouble(3, pd.getHargaPerHari());
            ps.setInt(4, pd.getJumlahHari());
            ps.setDouble(5, pd.getSubtotal());
            ps.setInt(6, pd.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM penyewaan_detail WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteByPenyewaanId(int idPenyewaan) throws SQLException {
        String sql = "DELETE FROM penyewaan_detail WHERE idPenyewaan=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPenyewaan);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ModelPenyewaanDetail> getAll() throws SQLException {
        List<ModelPenyewaanDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM penyewaan_detail";
        try (Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ModelPenyewaanDetail pd = mapResultSetToDetail(rs);
                list.add(pd);
            }
        }
        return list;
    }

    public ModelPenyewaanDetail getById(int id) throws SQLException {
        String sql = "SELECT * FROM penyewaan_detail WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDetail(rs);
                }
            }
        }
        return null;
    }

    public List<ModelPenyewaanDetail> getByPenyewaanId(int idPenyewaan) throws SQLException {
        List<ModelPenyewaanDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM penyewaan_detail WHERE idPenyewaan=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPenyewaan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModelPenyewaanDetail pd = mapResultSetToDetail(rs);
                    list.add(pd);
                }
            }
        }
        return list;
    }

    public List<ModelPenyewaanDetail> getByMobilId(int idMobil) throws SQLException {
        List<ModelPenyewaanDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM penyewaan_detail WHERE idMobil=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMobil);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModelPenyewaanDetail pd = mapResultSetToDetail(rs);
                    list.add(pd);
                }
            }
        }
        return list;
    }

    // Helper method untuk mapping ResultSet ke ModelPenyewaanDetail
    private ModelPenyewaanDetail mapResultSetToDetail(ResultSet rs) throws SQLException {
        ModelPenyewaanDetail pd = new ModelPenyewaanDetail();
        pd.setId(rs.getInt("id"));
        pd.setHargaPerHari(rs.getDouble("hargaPerHari"));
        pd.setJumlahHari(rs.getInt("durasi"));

        // Set mobil dengan data lengkap dari MobilDAO
        int idMobil = rs.getInt("idMobil");
        List<ModelMobil> allMobil = mobilDAO.getAllMobil();
        ModelMobil mobil = allMobil.stream()
            .filter(m -> m.getId() == idMobil)
            .findFirst()
            .orElse(null);
        
        if (mobil != null) {
            pd.setMobil(mobil);
        } else {
            // Fallback
            ModelMobil mobilFallback = new ModelMobil();
            mobilFallback.setId(idMobil);
            mobilFallback.setMerk("Data tidak ditemukan");
            mobilFallback.setModel("");
            mobilFallback.setNoPolisi("N/A");
            pd.setMobil(mobilFallback);
        }

        // Set penyewaan (hanya ID untuk menghindari circular reference)
        ModelPenyewaan penyewaan = new ModelPenyewaan();
        penyewaan.setId(rs.getInt("idPenyewaan"));
        pd.setPenyewaan(penyewaan);

        return pd;
    }
}