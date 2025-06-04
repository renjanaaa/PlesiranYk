
package dao;

import model.ModelPengembalian;
import service.ServicePengembalian;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import koneksi.koneksi;


public class PengembalianDAO {

    private Connection conn;

    public PengembalianDAO(Connection conn) {
        this.conn = conn;
    }

    public void insert(ModelPengembalian m) throws SQLException {
        String sql = "INSERT INTO pengembalian (idPenyewaan, tanggalKembaliRiil, denda, totalBayar) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getIdPenyewaan());
            ps.setDate(2, new java.sql.Date(m.getTanggalKembaliRiil().getTime()));
            ps.setDouble(3, m.getDenda());
            ps.setDouble(4, m.getTotalBayar());
            ps.executeUpdate();
        }
    }

    public void update(ModelPengembalian m) throws SQLException {
        String sql = "UPDATE pengembalian SET idPenyewaan=?, tanggalKembaliRiil=?, denda=?, totalBayar=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, m.getIdPenyewaan());
            ps.setDate(2, new java.sql.Date(m.getTanggalKembaliRiil().getTime()));
            ps.setDouble(3, m.getDenda());
            ps.setDouble(4, m.getTotalBayar());
            ps.setInt(5, m.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM pengembalian WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<ModelPengembalian> getAll() throws SQLException {
        List<ModelPengembalian> list = new ArrayList<>();
        String sql = "SELECT * FROM pengembalian";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                ModelPengembalian m = new ModelPengembalian();
                m.setId(rs.getInt("id"));
                m.setIdPenyewaan(rs.getInt("idPenyewaan"));
                m.setTanggalKembaliRiil(rs.getDate("tanggalKembaliRiil"));
                m.setDenda(rs.getDouble("denda"));
                m.setTotalBayar(rs.getDouble("totalBayar"));
                list.add(m);
            }
        }
        return list;
    }

    public ModelPengembalian getById(int id) throws SQLException {
        String sql = "SELECT * FROM pengembalian WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ModelPengembalian m = new ModelPengembalian();
                    m.setId(rs.getInt("id"));
                    m.setIdPenyewaan(rs.getInt("idPenyewaan"));
                    m.setTanggalKembaliRiil(rs.getDate("tanggalKembaliRiil"));
                    m.setDenda(rs.getDouble("denda"));
                    m.setTotalBayar(rs.getDouble("totalBayar"));
                    return m;
                }
            }
        }
        return null;
    }
}
