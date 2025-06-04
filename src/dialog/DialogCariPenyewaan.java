package dialog;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import koneksi.koneksi;

public class DialogCariPenyewaan extends JDialog {
    
    private JTable tablePenyewaan;
    private JTextField txtCari;
    private JButton btnPilih;
    private JButton btnBatal;
    private DefaultTableModel tableModel;
    
    private boolean isSelected = false;
    private int selectedPenyewaanId = 0;
    private String selectedKodePenyewaan = "";
    private String selectedPelanggan = "";
    
    private NumberFormat currencyFormat;
    private SimpleDateFormat dateFormat;
    
    public DialogCariPenyewaan(JFrame parent) {
        super(parent, "Cari Penyewaan", true);
        initializeFormatters();
        initComponents();
        setupStyling();
        setupEventHandlers();
        loadData();
        
        setSize(900, 600);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initializeFormatters() {
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Search Panel
        JPanel searchPanel = createSearchPanel();
        add(searchPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel lblTitle = new JLabel("PILIH PENYEWAAN YANG AKAN DIKEMBALIKAN");
        lblTitle.setFont(new Font("Poppins", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        
        headerPanel.add(lblTitle);
        return headerPanel;
    }
    
    private JPanel createSearchPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Search input panel
        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel lblCari = new JLabel("Cari:");
        lblCari.setFont(new Font("Poppins", Font.PLAIN, 12));
        
        txtCari = new JTextField(30);
        txtCari.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, 
            "Masukkan kode penyewaan atau nama pelanggan...");
        
        searchInputPanel.add(lblCari);
        searchInputPanel.add(txtCari);
        
        // Table
        createTable();
        JScrollPane scrollPane = new JScrollPane(tablePenyewaan);
        scrollPane.setPreferredSize(new Dimension(0, 400));
        
        mainPanel.add(searchInputPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private void createTable() {
        String[] columnNames = {
            "No", "Kode Penyewaan", "Pelanggan", "Tanggal Sewa", 
            "Tanggal Jatuh Tempo", "Total Biaya", "Sisa Bayar", "Status"
        };
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Integer.class;
                return String.class;
            }
        };
        
        tablePenyewaan = new JTable(tableModel);
        tablePenyewaan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePenyewaan.setRowHeight(35);
        tablePenyewaan.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 12));
        tablePenyewaan.setFont(new Font("Poppins", Font.PLAIN, 11));
        
        // Set column widths
        setColumnWidths();
        
        // Highlight overdue rentals
        tablePenyewaan.setDefaultRenderer(Object.class, new OverdueTableCellRenderer());
    }
    
    private void setColumnWidths() {
        if (tablePenyewaan.getColumnModel().getColumnCount() > 0) {
            tablePenyewaan.getColumnModel().getColumn(0).setPreferredWidth(40);   // No
            tablePenyewaan.getColumnModel().getColumn(1).setPreferredWidth(120);  // Kode
            tablePenyewaan.getColumnModel().getColumn(2).setPreferredWidth(150);  // Pelanggan
            tablePenyewaan.getColumnModel().getColumn(3).setPreferredWidth(100);  // Tgl Sewa
            tablePenyewaan.getColumnModel().getColumn(4).setPreferredWidth(120);  // Jatuh Tempo
            tablePenyewaan.getColumnModel().getColumn(5).setPreferredWidth(120);  // Total
            tablePenyewaan.getColumnModel().getColumn(6).setPreferredWidth(120);  // Sisa
            tablePenyewaan.getColumnModel().getColumn(7).setPreferredWidth(80);   // Status
        }
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        
        btnPilih = new JButton("Pilih");
        btnBatal = new JButton("Batal");
        
        Dimension buttonSize = new Dimension(100, 35);
        btnPilih.setPreferredSize(buttonSize);
        btnBatal.setPreferredSize(buttonSize);
        
        buttonPanel.add(btnPilih);
        buttonPanel.add(btnBatal);
        
        return buttonPanel;
    }
    
    // Perbaiki method setupStyling() di DialogCariPenyewaan.java
    private void setupStyling() {
        // Text field styling - hapus minimumHeight
        String textFieldStyle = "arc:8;borderWidth:1;borderColor:#bdc3c7;focusedBorderColor:#3498db;background:#ffffff;margin:5,10,5,10";
        txtCari.putClientProperty(FlatClientProperties.STYLE, textFieldStyle);
        txtCari.setPreferredSize(new Dimension(txtCari.getPreferredSize().width, 35)); // Set height manual

        // Button styling - hapus minimumHeight
        String primaryStyle = "borderWidth:0;focusWidth:0;arc:8;background:#3498db;hoverBackground:#2980b9;pressedBackground:#21618c;foreground:#ffffff;font:bold";
        String secondaryStyle = "borderWidth:1;borderColor:#95a5a6;focusWidth:0;arc:8;background:#ecf0f1;hoverBackground:#d5dbdb;pressedBackground:#bdc3c7;foreground:#2c3e50;font:bold";

        btnPilih.putClientProperty(FlatClientProperties.STYLE, primaryStyle);
        btnBatal.putClientProperty(FlatClientProperties.STYLE, secondaryStyle);

        // Set preferred size manual
        Dimension buttonSize = new Dimension(100, 35);
        btnPilih.setPreferredSize(buttonSize);
        btnBatal.setPreferredSize(buttonSize);

        // Initially disable pilih button
        btnPilih.setEnabled(false);
    }
    
    private void setupEventHandlers() {
        // Search functionality
        txtCari.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchData(txtCari.getText().trim());
            }
        });
        
        // Table selection
        tablePenyewaan.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnPilih.setEnabled(tablePenyewaan.getSelectedRow() != -1);
            }
        });
        
        // Double click to select
        tablePenyewaan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tablePenyewaan.getSelectedRow() != -1) {
                    pilihPenyewaan();
                }
            }
        });
        
        // Button actions
        btnPilih.addActionListener(e -> pilihPenyewaan());
        btnBatal.addActionListener(e -> {
            isSelected = false;
            dispose();
        });
        
        // Enter key to select
        txtCari.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && tablePenyewaan.getRowCount() > 0) {
                    tablePenyewaan.setRowSelectionInterval(0, 0);
                    pilihPenyewaan();
                }
            }
        });
    }
    
    private void loadData() {
        System.out.println("=== DEBUG DIALOG CARI PENYEWAAN ===");

        try (Connection conn = koneksi.getConnection()) {
            if (conn == null) {
                System.err.println("Koneksi database NULL!");
                JOptionPane.showMessageDialog(this, "Koneksi database gagal!");
                return;
            }

            System.out.println("Koneksi database OK");

            // Test query sederhana dulu
            String testSql = "SELECT COUNT(*) as total FROM penyewaan";
            PreparedStatement testPs = conn.prepareStatement(testSql);
            ResultSet testRs = testPs.executeQuery();

            if (testRs.next()) {
                int total = testRs.getInt("total");
                System.out.println("Total penyewaan di database: " + total);
            }

            // Test query dengan status
            String statusSql = "SELECT id, kode_penyewaan, status FROM penyewaan";
            PreparedStatement statusPs = conn.prepareStatement(statusSql);
            ResultSet statusRs = statusPs.executeQuery();

            System.out.println("=== SEMUA DATA PENYEWAAN ===");
            while (statusRs.next()) {
                System.out.println("ID: " + statusRs.getInt("id") + 
                                 ", Kode: " + statusRs.getString("kode_penyewaan") + 
                                 ", Status: '" + statusRs.getString("status") + "'");
            }

            // Query utama - tanpa filter status dulu
            String sql = """
                SELECT p.id, p.kode_penyewaan, p.nama_pelanggan, 
                       p.tanggal_sewa, p.tanggal_kembali_rencana, p.total_biaya, 
                       p.sisa_bayar, p.status
                FROM penyewaan p 
                ORDER BY p.tanggal_kembali_rencana ASC
                """;

            System.out.println("Executing main query...");
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            tableModel.setRowCount(0);
            int no = 1;
            int count = 0;

            while (rs.next()) {
                count++;
                String status = rs.getString("status");
                System.out.println("Processing row " + count + " - Status: '" + status + "'");

                Object[] row = {
                    no++,
                    rs.getString("kode_penyewaan"),
                    rs.getString("nama_pelanggan"),
                    dateFormat.format(rs.getDate("tanggal_sewa")),
                    dateFormat.format(rs.getDate("tanggal_kembali_rencana")),
                    currencyFormat.format(rs.getDouble("total_biaya")),
                    currencyFormat.format(rs.getDouble("sisa_bayar")),
                    status
                };
                tableModel.addRow(row);
            }

            System.out.println("Total rows loaded: " + count);

            if (tableModel.getRowCount() == 0) {
                System.out.println("Tidak ada data, menambahkan row kosong");
                Object[] emptyRow = {"", "Tidak ada penyewaan", "", "", "", "", "", ""};
                tableModel.addRow(emptyRow);
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Gagal memuat data penyewaan: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchData(String keyword) {
        if (keyword.isEmpty()) {
            loadData();
            return;
        }
        
        try (Connection conn = koneksi.getConnection()) {
            String sql = """
                SELECT p.id, p.kode_penyewaan, pel.nama as nama_pelanggan, 
                       p.tanggal_sewa, p.tanggal_kembali_rencana, p.total_biaya, 
                       p.sisa_bayar, p.status
                FROM penyewaan p 
                LEFT JOIN pelanggan pel ON p.pelanggan_id = pel.id 
                WHERE p.status = 'Aktif' 
                AND (p.kode_penyewaan LIKE ? OR pel.nama LIKE ?)
                ORDER BY p.tanggal_kembali_rencana ASC
                """;
            
            PreparedStatement ps = conn.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            
            ResultSet rs = ps.executeQuery();
            
            tableModel.setRowCount(0);
            int no = 1;
            
            while (rs.next()) {
                Object[] row = {
                    no++,
                    rs.getString("kode_penyewaan"),
                    rs.getString("nama_pelanggan"),
                    dateFormat.format(rs.getDate("tanggal_sewa")),
                    dateFormat.format(rs.getDate("tanggal_kembali_rencana")),
                    currencyFormat.format(rs.getDouble("total_biaya")),
                    currencyFormat.format(rs.getDouble("sisa_bayar")),
                    rs.getString("status")
                };
                tableModel.addRow(row);
            }
            
            if (tableModel.getRowCount() == 0) {
                Object[] emptyRow = {"", "Data tidak ditemukan", "", "", "", "", "", ""};
                tableModel.addRow(emptyRow);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Gagal mencari data: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void pilihPenyewaan() {
        int selectedRow = tablePenyewaan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Pilih penyewaan terlebih dahulu!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Check if it's an empty row
        Object kodePenyewaan = tableModel.getValueAt(selectedRow, 1);
        if (kodePenyewaan == null || kodePenyewaan.toString().contains("Tidak ada") || 
            kodePenyewaan.toString().contains("tidak ditemukan")) {
            JOptionPane.showMessageDialog(this, 
                "Tidak ada data penyewaan yang dapat dipilih!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Get the selected penyewaan ID directly from database
            String kode = tableModel.getValueAt(selectedRow, 1).toString().trim();
            System.out.println("Selected kode: " + kode);

            try (Connection conn = koneksi.getConnection()) {
                String sql = "SELECT id FROM penyewaan WHERE kode_penyewaan = ? AND status = 'aktif'";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, kode);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    selectedPenyewaanId = rs.getInt("id");
                    selectedKodePenyewaan = kode;
                    selectedPelanggan = tableModel.getValueAt(selectedRow, 2).toString();

                    System.out.println("Selected ID: " + selectedPenyewaanId);
                    System.out.println("Selected Kode: " + selectedKodePenyewaan);
                    System.out.println("Selected Pelanggan: " + selectedPelanggan);

                    isSelected = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Data penyewaan tidak ditemukan di database!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            System.err.println("Error selecting penyewaan: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Gagal memilih penyewaan: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private int getPenyewaanIdByKode(String kodePenyewaan) throws SQLException {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT id FROM penyewaan WHERE kode_penyewaan = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodePenyewaan);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            throw new SQLException("Penyewaan tidak ditemukan");
        }
    }
    
    // Getter methods
    public boolean isSelected() {
        return isSelected;
    }
    
    public int getSelectedPenyewaanId() {
        return selectedPenyewaanId;
    }
    
    public String getSelectedKodePenyewaan() {
        return selectedKodePenyewaan;
    }
    
    public String getSelectedPelanggan() {
        return selectedPelanggan;
    }
}