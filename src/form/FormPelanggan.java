package form;

import dialog.DialogPelanggan;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import model.ModelPelanggan;
import tablemodel.TableModelPelanggan;

public class FormPelanggan extends javax.swing.JPanel {
    
    private ModelPelanggan model;
    private TableModelPelanggan tableModel;

    public FormPelanggan() {
        initComponents();
        tableModel = new TableModelPelanggan(new ArrayList<>());
        jTable1.setModel(tableModel);
        loadData();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelUtama1 = new javax.swing.JPanel();
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
        jLabel1.setText("PLESIRAN YK > MASTER > PELANGGAN");

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

        javax.swing.GroupLayout PanelUtama1Layout = new javax.swing.GroupLayout(PanelUtama1);
        PanelUtama1.setLayout(PanelUtama1Layout);
        PanelUtama1Layout.setHorizontalGroup(
            PanelUtama1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelUtama1Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(PanelUtama1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1055, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelUtama1Layout.createSequentialGroup()
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelUtama1Layout.createSequentialGroup()
                        .addComponent(txtPencarianData, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        PanelUtama1Layout.setVerticalGroup(
            PanelUtama1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUtama1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(96, 96, 96)
                .addGroup(PanelUtama1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPencarianData)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelUtama1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelUtama1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelUtama1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtPencarianDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPencarianDataActionPerformed
        String keyword = txtPencarianData.getText();
        searchData(keyword);
    }//GEN-LAST:event_txtPencarianDataActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        DialogPelanggan dialog = new DialogPelanggan(parentFrame);
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
    private javax.swing.JPanel PanelUtama1;
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
            String sql = "SELECT * FROM pelanggan ORDER BY id"; // Diubah dari mobil ke pelanggan
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<ModelPelanggan> list = new ArrayList<>();
            while (rs.next()) {
                ModelPelanggan p = new ModelPelanggan();
                p.setId(rs.getInt("id"));
                p.setNama(rs.getString("nama"));
                p.setNoKtp(rs.getString("no_ktp"));
                p.setNoHp(rs.getString("no_hp"));
                p.setEmail(rs.getString("email"));
                p.setAlamat(rs.getString("alamat"));
                p.setJenisKelamin(rs.getString("jenis_kelamin"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
            tableModel.setData(list);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void updateData() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diupdate.");
            return;
        }

        ModelPelanggan pelangganTerpilih = tableModel.getPelangganAt(row);
        
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        DialogPelanggan dialog = new DialogPelanggan(parentFrame, pelangganTerpilih);
        dialog.setVisible(true);
        
        if (dialog.isSuccess()) {
            loadData();
            JOptionPane.showMessageDialog(this, "Data pelanggan berhasil diupdate!");
        }
    }

    private void hapusData() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus.");
            return;
        }

        ModelPelanggan p = tableModel.getPelangganAt(row);
        int konfirmasi = JOptionPane.showConfirmDialog(this, 
                "Yakin ingin menghapus pelanggan: " + p.getNama() + " (" + p.getNoKtp() + ")?", 
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try (Connection conn = koneksi.getConnection()) {
            String sql = "DELETE FROM pelanggan WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data pelanggan berhasil dihapus!");
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
            String sql = "SELECT * FROM pelanggan WHERE nama LIKE ? OR no_ktp LIKE ? OR no_hp LIKE ? OR email LIKE ? OR status LIKE ? ORDER BY id";
            PreparedStatement ps = conn.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            for (int i = 1; i <= 5; i++) {
                ps.setString(i, searchPattern);
            }
            ResultSet rs = ps.executeQuery();

            List<ModelPelanggan> list = new ArrayList<>();
            while (rs.next()) {
                ModelPelanggan p = new ModelPelanggan();
                p.setId(rs.getInt("id"));
                p.setNama(rs.getString("nama"));
                p.setNoKtp(rs.getString("no_ktp"));
                p.setNoHp(rs.getString("no_hp"));
                p.setEmail(rs.getString("email"));
                p.setAlamat(rs.getString("alamat"));
                p.setJenisKelamin(rs.getString("jenis_kelamin"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }

            tableModel.setData(list);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }
}