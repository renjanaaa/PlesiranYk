package dialog;

import java.awt.BorderLayout;
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
import model.ModelDriver;
import com.formdev.flatlaf.FlatClientProperties;

public class DialogCariDriver extends JDialog {
    
    private JTextField txtCari;
    private JTable tableDriver;
    private JButton btnPilih, btnBatal;
    private ModelDriver selectedDriver;
    private boolean isSelected = false;
    
    public DialogCariDriver(JFrame parent) {
        super(parent, "Cari Driver", true);
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
        
        JLabel lblTitle = new JLabel("CARI DRIVER");
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
            "Masukkan nama atau nomor HP driver...");
        
        searchPanel.add(lblCari);
        searchPanel.add(txtCari);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Table Panel
        String[] columnNames = {"ID", "Nama Driver", "No. HP", "Alamat", "No. SIM", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableDriver = new JTable(model);
        tableDriver.setRowHeight(30);
        tableDriver.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 12));
        tableDriver.setFont(new Font("Poppins", Font.PLAIN, 11));
        
        // Hide ID column
        tableDriver.getColumnModel().getColumn(0).setMinWidth(0);
        tableDriver.getColumnModel().getColumn(0).setMaxWidth(0);
        tableDriver.getColumnModel().getColumn(0).setWidth(0);
        
        // Set column widths
        tableDriver.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableDriver.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableDriver.getColumnModel().getColumn(3).setPreferredWidth(250);
        tableDriver.getColumnModel().getColumn(4).setPreferredWidth(120);
        tableDriver.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(tableDriver);
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
        String textFieldStyle = "arc:8;borderWidth:1;borderColor:#bdc3c7;focusedBorderColor:#3498db;background:#ffffff;minimumHeight:35";
        txtCari.putClientProperty(FlatClientProperties.STYLE, textFieldStyle);
        
        String primaryButtonStyle = "borderWidth:0;focusWidth:0;arc:8;background:#3498db;hoverBackground:#2980b9;foreground:#ffffff;font:bold";
        String secondaryButtonStyle = "borderWidth:1;borderColor:#95a5a6;focusWidth:0;arc:8;background:#ffffff;hoverBackground:#ecf0f1;foreground:#2c3e50;font:bold";
        
        btnPilih.putClientProperty(FlatClientProperties.STYLE, primaryButtonStyle);
        btnBatal.putClientProperty(FlatClientProperties.STYLE, secondaryButtonStyle);
    }
    
    private void setupEventHandlers() {
        txtCari.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String keyword = txtCari.getText().trim();
                loadData(keyword);
            }
        });
        
        tableDriver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    pilihDriver();
                }
            }
        });
        
        btnPilih.addActionListener(e -> pilihDriver());
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
                sql = "SELECT * FROM driver WHERE status = 'Aktif' ORDER BY nama";
                ps = conn.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM driver WHERE (nama LIKE ? OR no_hp LIKE ?) AND status = 'Aktif' ORDER BY nama";
                ps = conn.prepareStatement(sql);
                String searchPattern = "%" + keyword + "%";
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
            }
            
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tableDriver.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("no_hp"),
                    rs.getString("alamat"),
                    rs.getString("no_sim"),
                    rs.getString("status")
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void pilihDriver() {
        int selectedRow = tableDriver.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih driver terlebih dahulu!");
            return;
        }
        
        selectedDriver = new ModelDriver();
        selectedDriver.setId((Integer) tableDriver.getValueAt(selectedRow, 0));
        selectedDriver.setNama((String) tableDriver.getValueAt(selectedRow, 1));
        selectedDriver.setNoHp((String) tableDriver.getValueAt(selectedRow, 2));
        selectedDriver.setAlamat((String) tableDriver.getValueAt(selectedRow, 3));
        selectedDriver.setNoSim((String) tableDriver.getValueAt(selectedRow, 4));
        selectedDriver.setStatus((String) tableDriver.getValueAt(selectedRow, 5));
        
        isSelected = true;
        dispose();
    }
    
    public ModelDriver getSelectedDriver() {
        return selectedDriver;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
}