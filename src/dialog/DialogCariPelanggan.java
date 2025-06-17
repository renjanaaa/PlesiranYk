package dialog;

import java.awt.BorderLayout;
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
import model.ModelPelanggan;
import com.formdev.flatlaf.FlatClientProperties;

public class DialogCariPelanggan extends JDialog {
    
    private JTextField txtCari;
    private JTable tablePelanggan;
    private JButton btnPilih, btnBatal;
    private ModelPelanggan selectedPelanggan;
    private boolean isSelected = false;
    
    public DialogCariPelanggan(JFrame parent) {
        super(parent, "Cari Pelanggan", true);
        initComponents();
        setupStyling();
        setupEventHandlers();
        loadData("");
        
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new java.awt.Color(52, 152, 219));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitle = new JLabel("CARI PELANGGAN");
        lblTitle.setFont(new Font("Poppins", Font.BOLD, 18));
        lblTitle.setForeground(java.awt.Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        JLabel lblCari = new JLabel("Cari:");
        lblCari.setFont(new Font("Poppins", Font.PLAIN, 12));
        
        txtCari = new JTextField(30);
        txtCari.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, 
            "Masukkan nama, KTP, atau nomor HP pelanggan...");
        
        searchPanel.add(lblCari);
        searchPanel.add(txtCari);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Table Panel
        String[] columnNames = {"ID", "Nama", "No. KTP", "No. HP", "Alamat"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablePelanggan = new JTable(model);
        tablePelanggan.setRowHeight(30);
        tablePelanggan.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 12));
        tablePelanggan.setFont(new Font("Poppins", Font.PLAIN, 11));
        
        // Hide ID column
        tablePelanggan.getColumnModel().getColumn(0).setMinWidth(0);
        tablePelanggan.getColumnModel().getColumn(0).setMaxWidth(0);
        tablePelanggan.getColumnModel().getColumn(0).setWidth(0);
        
        // Set column widths
        tablePelanggan.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablePelanggan.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablePelanggan.getColumnModel().getColumn(3).setPreferredWidth(120);
        tablePelanggan.getColumnModel().getColumn(4).setPreferredWidth(250);
        
        JScrollPane scrollPane = new JScrollPane(tablePelanggan);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        btnPilih = new JButton("Pilih");
        btnBatal = new JButton("Batal");
        
        btnPilih.setPreferredSize(new Dimension(80, 35));
        btnBatal.setPreferredSize(new Dimension(80, 35));
        
        buttonPanel.add(btnPilih);
        buttonPanel.add(btnBatal);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupStyling() {
        // Text field styling
        String textFieldStyle = "arc:8;borderWidth:1;borderColor:#bdc3c7;focusedBorderColor:#3498db;background:#ffffff;minimumHeight:35";
        txtCari.putClientProperty(FlatClientProperties.STYLE, textFieldStyle);
        
        // Button styling
        String primaryButtonStyle = "borderWidth:0;focusWidth:0;arc:8;background:#3498db;hoverBackground:#2980b9;foreground:#ffffff;font:bold";
        String secondaryButtonStyle = "borderWidth:1;borderColor:#95a5a6;focusWidth:0;arc:8;background:#ffffff;hoverBackground:#ecf0f1;foreground:#2c3e50;font:bold";
        
        btnPilih.putClientProperty(FlatClientProperties.STYLE, primaryButtonStyle);
        btnBatal.putClientProperty(FlatClientProperties.STYLE, secondaryButtonStyle);
    }
    
    private void setupEventHandlers() {
        // Search on typing
        txtCari.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtCari.getText().trim();
                loadData(keyword);
            }
        });
        
        // Double click to select
        tablePelanggan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    pilihPelanggan();
                }
            }
        });
        
        // Button events
        btnPilih.addActionListener(e -> pilihPelanggan());
        btnBatal.addActionListener(e -> {
            isSelected = false;
            dispose();
        });
    }

    private void loadData(String keyword) {
        try (Connection conn = koneksi.getConnection()) {
            String sql;
            PreparedStatement ps;

            if (keyword.isEmpty()) {
                // QUERY DIPERBAIKI: Cari pelanggan yang TIDAK sedang menyewa
                sql = "SELECT p.* FROM pelanggan p " +
                      "WHERE p.id NOT IN ( " +
                      "    SELECT DISTINCT penyewaan.pelanggan_id " +
                      "    FROM penyewaan " +
                      "    WHERE penyewaan.status = 'Aktif' " +
                      ") " +
                      "ORDER BY p.nama";
                ps = conn.prepareStatement(sql);
            } else {
                // QUERY DIPERBAIKI: Cari pelanggan berdasarkan keyword yang TIDAK sedang menyewa
                sql = "SELECT p.* FROM pelanggan p " +
                      "WHERE (p.nama LIKE ? OR p.no_ktp LIKE ? OR p.no_hp LIKE ?) " +
                      "AND p.id NOT IN ( " +
                      "    SELECT DISTINCT penyewaan.pelanggan_id " +
                      "    FROM penyewaan " +
                      "    WHERE penyewaan.status = 'Aktif' " +
                      ") " +
                      "ORDER BY p.nama";
                ps = conn.prepareStatement(sql);
                String searchPattern = "%" + keyword + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
            }

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tablePelanggan.getModel();
            model.setRowCount(0);

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("no_ktp"),
                    rs.getString("no_hp"),
                    rs.getString("alamat")
                };
                model.addRow(row);
            }

            // Tampilkan pesan jika tidak ada data
            if (!hasData) {
                if (keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "Tidak ada pelanggan yang tersedia saat ini!\n" +
                        "Semua pelanggan sedang dalam masa penyewaan.", 
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Tidak ada pelanggan tersedia dengan kata kunci: " + keyword + "\n" +
                        "Coba kata kunci lain atau tunggu pelanggan selesai menyewa.", 
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void pilihPelanggan() {
        int selectedRow = tablePelanggan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pelanggan terlebih dahulu!");
            return;
        }
        
        // Create ModelPelanggan object
        selectedPelanggan = new ModelPelanggan();
        selectedPelanggan.setId((Integer) tablePelanggan.getValueAt(selectedRow, 0));
        selectedPelanggan.setNama((String) tablePelanggan.getValueAt(selectedRow, 1));
        selectedPelanggan.setNoKtp((String) tablePelanggan.getValueAt(selectedRow, 2));
        selectedPelanggan.setNoHp((String) tablePelanggan.getValueAt(selectedRow, 3));
        selectedPelanggan.setAlamat((String) tablePelanggan.getValueAt(selectedRow, 4));
        
        isSelected = true;
        dispose();
    }
    
    public ModelPelanggan getSelectedPelanggan() {
        return selectedPelanggan;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
}