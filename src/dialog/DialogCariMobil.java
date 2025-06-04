package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;
import model.ModelMobil;

public class DialogCariMobil extends JDialog {
    
    private JTextField txtCari;
    private JTable tableMobil;
    private JButton btnPilih, btnBatal;
    private ModelMobil selectedMobil;
    private boolean isSelected = false;
    private NumberFormat currencyFormat;
    
    public DialogCariMobil(JFrame parent) {
        super(parent, "Cari Mobil", true);
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        initComponents();
        setupEventHandlers();
        loadData("");
        
        setSize(900, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Main Content Panel - Simple white background
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Search Panel - Simple layout like customer dialog
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(Color.WHITE);
        
        JLabel lblCari = new JLabel("Cari:");
        lblCari.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblCari.setForeground(Color.BLACK);
        
        txtCari = new JTextField();
        txtCari.setPreferredSize(new Dimension(400, 35));
        txtCari.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCari.setBackground(Color.WHITE);
        txtCari.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Add placeholder-like behavior
        txtCari.setToolTipText("Masukkan nama mobil, no polisi, atau merk...");
        
        searchPanel.add(lblCari);
        searchPanel.add(txtCari);
        
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Table Panel - Simple styling like customer dialog
        String[] columnNames = {"ID", "Nama Mobil", "No. Polisi", "Merk", "Model", "Kapasitas", "Harga Sewa/Hari", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableMobil = new JTable(model);
        tableMobil.setRowHeight(30);
        tableMobil.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableMobil.getTableHeader().setBackground(new Color(240, 240, 240));
        tableMobil.getTableHeader().setForeground(Color.BLACK);
        tableMobil.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableMobil.setBackground(Color.WHITE);
        tableMobil.setSelectionBackground(new Color(0, 120, 215)); // Windows blue selection
        tableMobil.setSelectionForeground(Color.WHITE);
        tableMobil.setGridColor(new Color(230, 230, 230));
        tableMobil.setShowGrid(true);
        
        // Hide ID column
        tableMobil.getColumnModel().getColumn(0).setMinWidth(0);
        tableMobil.getColumnModel().getColumn(0).setMaxWidth(0);
        tableMobil.getColumnModel().getColumn(0).setWidth(0);
        
        // Set column widths
        tableMobil.getColumnModel().getColumn(1).setPreferredWidth(150); // Nama Mobil
        tableMobil.getColumnModel().getColumn(2).setPreferredWidth(100); // No. Polisi
        tableMobil.getColumnModel().getColumn(3).setPreferredWidth(80);  // Merk
        tableMobil.getColumnModel().getColumn(4).setPreferredWidth(80);  // Model
        tableMobil.getColumnModel().getColumn(5).setPreferredWidth(70);  // Kapasitas
        tableMobil.getColumnModel().getColumn(6).setPreferredWidth(120); // Harga Sewa
        tableMobil.getColumnModel().getColumn(7).setPreferredWidth(80);  // Status
        
        JScrollPane scrollPane = new JScrollPane(tableMobil);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel - Same style as customer dialog
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        buttonPanel.setBackground(Color.WHITE);
        
        btnPilih = new JButton("Pilih");
        btnPilih.setPreferredSize(new Dimension(80, 35));
        btnPilih.setBackground(new Color(0, 120, 215)); // Windows blue
        btnPilih.setForeground(Color.WHITE);
        btnPilih.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnPilih.setBorder(BorderFactory.createEmptyBorder());
        btnPilih.setFocusPainted(false);
        
        btnBatal = new JButton("Batal");
        btnBatal.setPreferredSize(new Dimension(80, 35));
        btnBatal.setBackground(new Color(240, 240, 240));
        btnBatal.setForeground(Color.BLACK);
        btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBatal.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        btnBatal.setFocusPainted(false);
        
        buttonPanel.add(btnPilih);
        buttonPanel.add(btnBatal);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void setupEventHandlers() {
        // Search event dengan debouncing
        txtCari.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtCari.getText().trim();
                loadData(keyword);
            }
        });

        // Double click event
        tableMobil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    pilihMobil();
                }
            }
        });

        // Button events
        btnPilih.addActionListener(e -> pilihMobil());

        btnBatal.addActionListener(e -> {
            isSelected = false;
            selectedMobil = null;
            dispose();
        });

        // Enter key pada search field
        txtCari.addActionListener(e -> {
            if (tableMobil.getRowCount() > 0) {
                tableMobil.setRowSelectionInterval(0, 0);
                tableMobil.requestFocus();
            }
        });

        // Enter key pada tabel
        tableMobil.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pilihMobil();
                }
            }
        });
    }
    
    private void loadData(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tableMobil.getModel();
        model.setRowCount(0);

        try (Connection conn = koneksi.getConnection()) {
            String sql;
            PreparedStatement ps;

            if (keyword.isEmpty()) {
                sql = "SELECT * FROM mobil WHERE status = 'Tersedia' ORDER BY merk, model";
                ps = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM mobil WHERE (merk LIKE ? OR model LIKE ? OR no_polisi LIKE ?) AND status = 'Tersedia' ORDER BY merk, model";
                ps = conn.prepareStatement(sql);
                String searchPattern = "%" + keyword + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Ambil harga langsung dari database tanpa modifikasi
                double hargaSewa = rs.getDouble("harga_sewa_per_hari");

                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("merk") + " " + rs.getString("model"),
                    rs.getString("no_polisi"),
                    rs.getString("merk"),
                    rs.getString("model"),
                    rs.getInt("kapasitas_penumpang") + " orang",
                    currencyFormat.format(hargaSewa), // Format currency untuk display saja
                    rs.getString("status")
                };
                model.addRow(row);
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Gagal memuat data: " + e.getMessage(), 
                "Error Database", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void pilihMobil() {
        int selectedRow = tableMobil.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Silakan pilih mobil terlebih dahulu!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Ambil ID mobil dari tabel
            int mobilId = (Integer) tableMobil.getValueAt(selectedRow, 0);

            // Query ulang ke database untuk mendapatkan data yang akurat
            try (Connection conn = koneksi.getConnection()) {
                String sql = "SELECT * FROM mobil WHERE id = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, mobilId);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    selectedMobil = new ModelMobil();
                    selectedMobil.setId(rs.getInt("id"));
                    selectedMobil.setNoPolisi(rs.getString("no_polisi"));
                    selectedMobil.setMerk(rs.getString("merk"));
                    selectedMobil.setModel(rs.getString("model"));
                    selectedMobil.setKapasitasPenumpang(rs.getInt("kapasitas_penumpang"));
                    selectedMobil.setHargaSewaPerHari(rs.getDouble("harga_sewa_per_hari"));
                    selectedMobil.setStatus(rs.getString("status"));

                    isSelected = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Data mobil tidak ditemukan!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }

                rs.close();
                ps.close();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error saat memproses data mobil: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private boolean hasColumn(ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

// Method untuk parsing currency yang aman
    private double parseCurrencyToDouble(String currencyStr) {
        if (currencyStr == null || currencyStr.trim().isEmpty()) {
            return 0.0;
        }

        try {
            // Hapus semua karakter non-digit kecuali titik dan koma
            String cleaned = currencyStr.replaceAll("[^0-9.,]", "");

            // Handle format Indonesia (1.000.000,00) vs format US (1,000,000.00)
            if (cleaned.contains(",") && cleaned.contains(".")) {
                // Format Indonesia: 1.000.000,00
                if (cleaned.lastIndexOf(",") > cleaned.lastIndexOf(".")) {
                    cleaned = cleaned.replace(".", "").replace(",", ".");
                } else {
                    // Format US: 1,000,000.00
                    cleaned = cleaned.replace(",", "");
                }
            } else if (cleaned.contains(",")) {
                // Hanya ada koma, bisa jadi decimal separator atau thousands separator
                int commaIndex = cleaned.lastIndexOf(",");
                if (cleaned.length() - commaIndex == 3) {
                    // Kemungkinan decimal separator (,00)
                    cleaned = cleaned.replace(",", ".");
                } else {
                    // Kemungkinan thousands separator
                    cleaned = cleaned.replace(",", "");
                }
            }

            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing currency: " + currencyStr);
            return 0.0;
        }
    }
    
    public ModelMobil getSelectedMobil() {
        return selectedMobil;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    private boolean validateSelectedMobil() {
        if (selectedMobil == null) {
            JOptionPane.showMessageDialog(this, 
                "Data mobil tidak valid!", 
                "Error Validasi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (selectedMobil.getId() <= 0) {
            JOptionPane.showMessageDialog(this, 
                "ID mobil tidak valid!", 
                "Error Validasi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (selectedMobil.getNoPolisi() == null || selectedMobil.getNoPolisi().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Nomor polisi tidak valid!", 
                "Error Validasi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (selectedMobil.getHargaSewaPerHari() <= 0) {
            JOptionPane.showMessageDialog(this, 
                "Harga sewa tidak valid!", 
                "Error Validasi", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}