package form;

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
import javax.swing.SwingUtilities;
import koneksi.koneksi;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import javax.swing.JTable;

public class FormLaporanMobil extends javax.swing.JPanel {
    
    private DefaultTableModel tableModel;
    private DecimalFormat currencyFormat;
    private SimpleDateFormat dateFormat;

    public FormLaporanMobil() {
        initComponents();
        setupTable();
        setupFormatters();
        checkDatabaseStructure();
        loadData();
    }
    
    private void setupTable() {
        // Setup kolom tabel untuk laporan mobil
        String[] columns = {
            "ID", "No. Polisi", "Merk", "Model", 
            "Kapasitas", "Harga Sewa/Hari", "Status"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jTable1.setModel(tableModel);

        jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);   // ID
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(120);  // No Polisi
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(100);  // Merk
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);  // Model
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(80);   // Kapasitas
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(150);  // Harga
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);  // Status
    }
    
    private void setupFormatters() {
        currencyFormat = new DecimalFormat("Rp #,##0");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    private void checkDatabaseStructure() {
        try (Connection conn = koneksi.getConnection()) {
 
            System.out.println("=== STRUKTUR TABEL MOBIL ===");
            String sql = "DESCRIBE mobil";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Column: " + rs.getString("Field") + 
                                 " | Type: " + rs.getString("Type"));
            }

            System.out.println("\n=== SAMPLE DATA MOBIL ===");
            String sqlSample = "SELECT * FROM mobil LIMIT 1";
            PreparedStatement psSample = conn.prepareStatement(sqlSample);
            ResultSet rsSample = psSample.executeQuery();

            if (rsSample.next()) {
                for (int i = 1; i <= rsSample.getMetaData().getColumnCount(); i++) {
                    System.out.println(rsSample.getMetaData().getColumnName(i) + ": " + 
                                     rsSample.getObject(i));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error checking database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelUtama = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtHargaMin = new javax.swing.JTextField();
        txtHargaMax = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnTampilkan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        txtPencarianData = new javax.swing.JTextField();

        jPanel2.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        jLabel1.setFont(new java.awt.Font("Poppins ExtraBold", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PLESIRAN YK > LAPORAN > MOBIL");

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

        jPanel1.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        jLabel2.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Harga Minimum");

        txtHargaMin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaMinActionPerformed(evt);
            }
        });

        txtHargaMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaMaxActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Harga Maksimum");

        btnTampilkan.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnTampilkan.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnTampilkan.setText("Tampilkan");
        btnTampilkan.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Red"));
        btnBatal.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setText("Batal");
        btnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnCetak.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnCetak.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnCetak.setText("Cetak");
        btnCetak.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        txtPencarianData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPencarianDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(txtHargaMin, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtHargaMax, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPencarianData)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHargaMin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtHargaMax, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPencarianData, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelUtamaLayout = new javax.swing.GroupLayout(PanelUtama);
        PanelUtama.setLayout(PanelUtamaLayout);
        PanelUtamaLayout.setHorizontalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelUtamaLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1055, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );
        PanelUtamaLayout.setVerticalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUtamaLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
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

    private void txtHargaMinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaMinActionPerformed
        filterByPriceRange();
    }//GEN-LAST:event_txtHargaMinActionPerformed

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        filterByPriceRange();
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void txtHargaMaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaMaxActionPerformed
        filterByPriceRange();
    }//GEN-LAST:event_txtHargaMaxActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        txtHargaMin.setText("");
        txtHargaMax.setText("");
        txtPencarianData.setText("");
        loadData();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
       cetakLaporan();
    }//GEN-LAST:event_btnCetakActionPerformed

    private void txtPencarianDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPencarianDataActionPerformed
        String keyword = txtPencarianData.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
        } else {
            searchData(keyword);
        }
    }//GEN-LAST:event_txtPencarianDataActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelUtama;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnTampilkan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtHargaMax;
    private javax.swing.JTextField txtHargaMin;
    private javax.swing.JTextField txtPencarianData;
    // End of variables declaration//GEN-END:variables
    private void loadData() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT * FROM mobil ORDER BY no_polisi";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Clear existing data
            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("no_polisi"),
                    rs.getString("merk"),
                    rs.getString("model"),
                    rs.getInt("kapasitas_penumpang") + " orang",
                    currencyFormat.format(rs.getDouble("harga_sewa_per_hari")),
                    rs.getString("status")
                };
                tableModel.addRow(row);
            }

            updateTableInfo();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void filterByPriceRange() {
        String hargaMin = txtHargaMin.getText().trim();
        String hargaMax = txtHargaMax.getText().trim();

        if (hargaMin.isEmpty() && hargaMax.isEmpty()) {
            loadData();
            return;
        }

        try (Connection conn = koneksi.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM mobil WHERE 1=1");
            List<Object> params = new ArrayList<>();

            if (!hargaMin.isEmpty()) {
                sql.append(" AND harga_sewa_per_hari >= ?");
                params.add(Double.parseDouble(hargaMin));
            }

            if (!hargaMax.isEmpty()) {
                sql.append(" AND harga_sewa_per_hari <= ?");
                params.add(Double.parseDouble(hargaMax));
            }

            sql.append(" ORDER BY no_polisi");

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            // Clear existing data
            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("no_polisi"),
                    rs.getString("merk"),
                    rs.getString("model"),
                    rs.getInt("kapasitas_penumpang") + " orang",
                    currencyFormat.format(rs.getDouble("harga_sewa_per_hari")),
                    rs.getString("status")
                };
                tableModel.addRow(row);
            }

            updateTableInfo();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format harga tidak valid! Gunakan angka saja.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memfilter data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void searchData(String keyword) {
        try (Connection conn = koneksi.getConnection()) {
            String sql = """
                SELECT * FROM mobil 
                WHERE no_polisi LIKE ? 
                   OR merk LIKE ?
                   OR model LIKE ?
                   OR status LIKE ?
                ORDER BY no_polisi
                """;

            PreparedStatement ps = conn.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);
            ps.setString(4, searchPattern);

            ResultSet rs = ps.executeQuery();

            // Clear existing data
            tableModel.setRowCount(0);

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("no_polisi"),
                    rs.getString("merk"),
                    rs.getString("model"),
                    rs.getInt("kapasitas_penumpang") + " orang",
                    currencyFormat.format(rs.getDouble("harga_sewa_per_hari")),
                    rs.getString("status")
                };
                tableModel.addRow(row);
            }

            updateTableInfo();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cetakLaporan() {
        try {
            MessageFormat header = new MessageFormat("Laporan Data Mobil - PLESIRAN YK");
            MessageFormat footer = new MessageFormat("Halaman {0}");

            boolean complete = jTable1.print(JTable.PrintMode.FIT_WIDTH, header, footer);

            if (complete) {
                JOptionPane.showMessageDialog(this, "Laporan berhasil dicetak!");
            } else {
                JOptionPane.showMessageDialog(this, "Pencetakan dibatalkan.");
            }

        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateTableInfo() {
        int totalRows = tableModel.getRowCount();
        System.out.println("Total data mobil: " + totalRows);
        
        // Hitung statistik
        int tersedia = 0, disewakan = 0, maintenance = 0;
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String status = tableModel.getValueAt(i, 6).toString().toLowerCase();
            switch (status) {
                case "tersedia":
                    tersedia++;
                    break;
                case "disewakan":
                    disewakan++;
                    break;
                case "maintenance":
                    maintenance++;
                    break;
            }
        }
        
        System.out.println("Statistik Mobil:");
        System.out.println("- Tersedia: " + tersedia);
        System.out.println("- Disewakan: " + disewakan);
        System.out.println("- Maintenance: " + maintenance);
    }
}