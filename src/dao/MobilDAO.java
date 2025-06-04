package dao;

import model.ModelMobil;
import java.sql.*;
import java.util.*;
import koneksi.koneksi;

public class MobilDAO {
    public List<ModelMobil> getAllMobil() {
        List<ModelMobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil";
        try (Connection conn = koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ModelMobil m = new ModelMobil();
                m.setId(rs.getInt("id"));
                m.setNoPolisi(rs.getString("no_polisi"));
                m.setMerk(rs.getString("merk"));
                m.setModel(rs.getString("model"));
                m.setKapasitasPenumpang(rs.getInt("kapasitas_penumpang"));
                m.setHargaSewaPerHari(rs.getDouble("harga_sewa_per_hari"));
                m.setStatus(rs.getString("status"));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertMobil(ModelMobil m) {
        String sql = "INSERT INTO mobil (no_polisi, merk, model, kapasitas_penumpang, harga_sewa_per_hari, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, m.getNoPolisi());
            st.setString(2, m.getMerk());
            st.setString(3, m.getModel());
            st.setInt(4, m.getKapasitasPenumpang());
            st.setDouble(5, m.getHargaSewaPerHari());
            st.setString(6, m.getStatus());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateMobil(ModelMobil m) {
        String sql = "UPDATE mobil SET no_polisi=?, merk=?, model=?, kapasitas_penumpang=?, harga_sewa_per_hari=?, status=? WHERE id=?";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, m.getNoPolisi());
            st.setString(2, m.getMerk());
            st.setString(3, m.getModel());
            st.setInt(4, m.getKapasitasPenumpang());
            st.setDouble(5, m.getHargaSewaPerHari());
            st.setString(6, m.getStatus());
            st.setInt(7, m.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean deleteMobil(int id) {
        String sql = "DELETE FROM mobil WHERE id=?";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<ModelMobil> searchMobil(String keyword) {
        List<ModelMobil> list = new ArrayList<>();
        String sql = "SELECT * FROM mobil WHERE no_polisi LIKE ? OR merk LIKE ? OR model LIKE ? OR status LIKE ?";
        try (Connection conn = koneksi.getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) {
                st.setString(i, searchPattern);
            }
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                ModelMobil m = new ModelMobil();
                m.setId(rs.getInt("id"));
                m.setNoPolisi(rs.getString("no_polisi"));
                m.setMerk(rs.getString("merk"));
                m.setModel(rs.getString("model"));
                m.setKapasitasPenumpang(rs.getInt("kapasitas_penumpang"));
                m.setHargaSewaPerHari(rs.getDouble("harga_sewa_per_hari"));
                m.setStatus(rs.getString("status"));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}