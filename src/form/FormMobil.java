
package form;

import dialog.DialogMobil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import koneksi.koneksi;
import model.ModelMobil;
import tablemodel.TableModelMobil;


public class FormMobil extends javax.swing.JPanel {
    
    private ModelMobil model;
    private TableModelMobil tableModel;

    public FormMobil() {
        initComponents();
        tableModel = new TableModelMobil(new ArrayList<>());
        jTable1.setModel(tableModel);
        loadData();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelUtama = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPencarianData = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnHapus = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        jPanel2.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        jLabel1.setFont(new java.awt.Font("Poppins ExtraBold", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PLESIRAN YK > MASTER > MOBIL");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtPencarianData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPencarianDataActionPerformed(evt);
            }
        });

        btnTambah.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnTambah.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setText("+ Tambah");
        btnTambah.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnHapus.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Red"));
        btnHapus.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setText("Hapus");
        btnHapus.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Yellow"));
        btnUpdate.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("Update");
        btnUpdate.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelUtamaLayout = new javax.swing.GroupLayout(PanelUtama);
        PanelUtama.setLayout(PanelUtamaLayout);
        PanelUtamaLayout.setHorizontalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelUtamaLayout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1055, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelUtamaLayout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelUtamaLayout.createSequentialGroup()
                        .addComponent(txtPencarianData, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        PanelUtamaLayout.setVerticalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUtamaLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPencarianData)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtPencarianDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPencarianDataActionPerformed
        String keyword = txtPencarianData.getText();
        searchData(keyword);
    }//GEN-LAST:event_txtPencarianDataActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        DialogMobil dialog = new DialogMobil(parentFrame);
        dialog.setVisible(true);
    
        if (dialog.isSuccess()) {
            loadData();
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateData();
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelUtama;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtPencarianData;
    // End of variables declaration//GEN-END:variables


    private void loadData() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT * FROM mobil ORDER BY id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<ModelMobil> list = new ArrayList<>();
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
            tableModel.setData(list);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    // MODIFIKASI: Ganti method tambahData() untuk menggunakan DialogMobil
    private void tambahData() {
        // Mendapatkan parent frame dari FormMainMenu
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        
        // Membuat dan menampilkan DialogMobil
        DialogMobil dialog = new DialogMobil(parentFrame);
        dialog.setVisible(true);
        
        // Jika data berhasil disimpan, refresh tabel
        if (dialog.isSuccess()) {
            loadData();
            JOptionPane.showMessageDialog(this, "Data mobil berhasil ditambahkan!");
        }
    }

    // MODIFIKASI: Ganti method updateData() untuk menggunakan DialogMobil
    private void updateData() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diupdate.");
            return;
        }

        // Ambil data mobil yang dipilih
        ModelMobil mobilTerpilih = tableModel.getMobilAt(row);
        
        // Mendapatkan parent frame dari FormMainMenu
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        
        // Membuat dan menampilkan DialogMobil untuk edit
        DialogMobil dialog = new DialogMobil(parentFrame, mobilTerpilih);
        dialog.setVisible(true);
        
        // Jika data berhasil diupdate, refresh tabel
        if (dialog.isSuccess()) {
            loadData();
            JOptionPane.showMessageDialog(this, "Data mobil berhasil diupdate!");
        }
    }

    private void hapusData() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.");
            return;
        }

        ModelMobil m = tableModel.getMobilAt(row);
        int konfirmasi = JOptionPane.showConfirmDialog(this, 
                "Yakin ingin menghapus mobil: " + m.getMerk() + " " + m.getModel() + " (" + m.getNoPolisi() + ")?", 
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try (Connection conn = koneksi.getConnection()) {
            String sql = "DELETE FROM mobil WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, m.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data mobil berhasil dihapus!");
            loadData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage());
        }
    }

    private void searchData(String keyword) {
        if (keyword.trim().isEmpty()) {
            loadData();
            return;
        }
        
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT * FROM mobil WHERE no_polisi LIKE ? OR merk LIKE ? OR model LIKE ? OR status LIKE ? ORDER BY id";
            PreparedStatement ps = conn.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, searchPattern);
            }
            ResultSet rs = ps.executeQuery();

            List<ModelMobil> list = new ArrayList<>();
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

            tableModel.setData(list);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }
}