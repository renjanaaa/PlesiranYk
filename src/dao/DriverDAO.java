
package dao;

import model.ModelDriver;
import service.ServiceDriver;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import koneksi.koneksi;

public class DriverDAO {

    public List<ModelDriver> getAll() {
        List<ModelDriver> list = new ArrayList<>();
        String sql = "SELECT * FROM driver";

        try (Connection conn = koneksi.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ModelDriver d = new ModelDriver(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("no_ktp"),
                    rs.getString("no_hp"),
                    rs.getString("status")
                );
                list.add(d);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(ModelDriver d) {
        String sql = "INSERT INTO driver (nama, no_ktp, no_hp, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNama());
            ps.setString(2, d.getNoKtp());
            ps.setString(3, d.getNoHp());
            ps.setString(4, d.getStatus());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(ModelDriver d) {
        String sql = "UPDATE driver SET nama=?, no_ktp=?, no_hp=?, status=? WHERE id=?";

        try (Connection conn = koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, d.getNama());
            ps.setString(2, d.getNoKtp());
            ps.setString(3, d.getNoHp());
            ps.setString(4, d.getStatus());
            ps.setInt(5, d.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM driver WHERE id=?";

        try (Connection conn = koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

