package dao;

import model.ModelPengembalianDetail;
import java.sql.*;
import java.util.*;
import koneksi.koneksi;
import service.ServicePengembalianDetail;

public class PengembalianDetailDAO implements ServicePengembalianDetail {
    private Connection conn;

    public PengembalianDetailDAO() throws SQLException {
        conn = koneksi.getConnection();
    }

    @Override
    public void tambahData(ModelPengembalianDetail model) {
        String sql = "INSERT INTO pengembalian_detail VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, model.getNoDetail());
            st.setString(2, model.getNoPengembalian());
            st.setString(3, model.getIdMobil());
            st.setInt(4, model.getDenda());
            st.setString(5, model.getKeterangan());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapusData(ModelPengembalianDetail model) {
        String sql = "DELETE FROM pengembalian_detail WHERE no_detail = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, model.getNoDetail());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ModelPengembalianDetail> tampilData(int posisiAwal, int dataPerHalaman) {
        List<ModelPengembalianDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM pengembalian_detail LIMIT ?, ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, posisiAwal);
            st.setInt(2, dataPerHalaman);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ModelPengembalianDetail m = new ModelPengembalianDetail();
                m.setNoDetail(rs.getString("no_detail"));
                m.setNoPengembalian(rs.getString("no_pengembalian"));
                m.setIdMobil(rs.getString("id_mobil"));
                m.setDenda(rs.getInt("denda"));
                m.setKeterangan(rs.getString("keterangan"));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ModelPengembalianDetail> pencarianData(String noPengembalian, int posisiAwal, int dataPerHalaman) {
        List<ModelPengembalianDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM pengembalian_detail WHERE no_pengembalian LIKE ? LIMIT ?, ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "%" + noPengembalian + "%");
            st.setInt(2, posisiAwal);
            st.setInt(3, dataPerHalaman);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ModelPengembalianDetail m = new ModelPengembalianDetail();
                m.setNoDetail(rs.getString("no_detail"));
                m.setNoPengembalian(rs.getString("no_pengembalian"));
                m.setIdMobil(rs.getString("id_mobil"));
                m.setDenda(rs.getInt("denda"));
                m.setKeterangan(rs.getString("keterangan"));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ModelPengembalianDetail> tampilDataByPengembalian(String noPengembalian) {
        List<ModelPengembalianDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM pengembalian_detail WHERE no_pengembalian = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, noPengembalian);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ModelPengembalianDetail m = new ModelPengembalianDetail();
                m.setNoDetail(rs.getString("no_detail"));
                m.setNoPengembalian(rs.getString("no_pengembalian"));
                m.setIdMobil(rs.getString("id_mobil"));
                m.setDenda(rs.getInt("denda"));
                m.setKeterangan(rs.getString("keterangan"));
                list.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String generateNoDetail() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void simpanNoPengembalian() {
        // Placeholder jika perlu implementasi simpan nomor pengembalian
    }

    @Override
    public int getTotalData() {
        String sql = "SELECT COUNT(*) FROM pengembalian_detail";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getTotalDataByPengembalian(String noPengembalian) {
        String sql = "SELECT COUNT(*) FROM pengembalian_detail WHERE no_pengembalian = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, noPengembalian);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}