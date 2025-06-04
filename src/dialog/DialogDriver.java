package dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import koneksi.koneksi;
import model.ModelDriver;

public class DialogDriver extends JDialog {
    
    private JTextField txtNama, txtKTP, txtSim, txtHP, txtAlamat;
    private JComboBox<String> cmbStatus;
    private JButton btnSimpan, btnBatal;
    private ModelDriver driver;
    private boolean isEdit = false;
    private boolean isSuccess = false;
    
    public DialogDriver(JFrame parent) {
        super(parent, "Tambah Driver", true);
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    public DialogDriver(JFrame parent, ModelDriver driver) {
        super(parent, "Edit Driver", true);
        this.driver = driver;
        this.isEdit = true;
        initComponents();
        fillForm();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Mendapatkan ukuran layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.9);
        int height = (int) (screenSize.height * 0.9);
        
        setSize(width, height);
        setResizable(true);
        
        // Header Panel (Blue) - Disejajarkan dengan benar
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219));
        headerPanel.setPreferredSize(new Dimension(0, 70));
        
        JLabel headerLabel = new JLabel(isEdit ? "DATA MASTER > DRIVER > EDIT" : "DATA MASTER > DRIVER > TAMBAH");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
        headerPanel.add(headerLabel, BorderLayout.WEST);
        
        // Main Content Panel - Padding dikurangi
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(240, 240, 240));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Form Panel dengan GridBagLayout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 25, 15, 25); // Spacing dikurangi
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        
        // Initialize components dengan ukuran yang lebih kecil
        txtNama = createTextField();
        txtKTP = createTextField();
        txtSim = createTextField();
        txtHP = createTextField();
        txtAlamat = createTextField();
        cmbStatus = createComboBox();
        
        // Baris 1: Nama Driver dan No.KTP
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createFieldPanel("Nama Driver", txtNama), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(createFieldPanel("No.KTP", txtKTP), gbc);
        
        // Baris 2: No.SIM dan No.HP
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createFieldPanel("No.SIM", txtSim), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(createFieldPanel("No.HP", txtHP), gbc);
        
        // Baris 3: Alamat dan Status
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createFieldPanel("Alamat", txtAlamat), gbc);
        
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(createComboPanel("Status", cmbStatus), gbc);
        
        // Button Panel - Spacing dikurangi
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        btnSimpan = new JButton(isEdit ? "Update" : "Simpan");
        btnSimpan.setPreferredSize(new Dimension(150, 50));
        btnSimpan.setBackground(new Color(52, 152, 219));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFont(new Font("Poppins", Font.BOLD, 16));
        btnSimpan.setBorder(BorderFactory.createEmptyBorder());
        btnSimpan.setFocusPainted(false);
        btnSimpan.addActionListener(this::simpanData);
        
        btnBatal = new JButton("Batal");
        btnBatal.setPreferredSize(new Dimension(150, 50));
        btnBatal.setBackground(new Color(231, 76, 60));
        btnBatal.setForeground(Color.WHITE);
        btnBatal.setFont(new Font("Poppins", Font.BOLD, 16));
        btnBatal.setBorder(BorderFactory.createEmptyBorder());
        btnBatal.setFocusPainted(false);
        btnBatal.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnBatal);
        
        // Add panels to content
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add panels to dialog
        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(400, 45));
        textField.setMinimumSize(new Dimension(400, 45));
        textField.setMaximumSize(new Dimension(400, 45));
        textField.setFont(new Font("Poppins", Font.PLAIN, 14));
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        textField.setOpaque(true);
        return textField;
    }
    
    private JComboBox<String> createComboBox() {
        JComboBox<String> combo = new JComboBox<>(new String[]{"Aktif", "Tidak Aktif"});
        combo.setPreferredSize(new Dimension(400, 45));
        combo.setMinimumSize(new Dimension(400, 45));
        combo.setMaximumSize(new Dimension(400, 45));
        combo.setFont(new Font("Poppins", Font.PLAIN, 14));
        combo.setBackground(Color.WHITE);
        combo.setForeground(Color.BLACK);
        combo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        combo.setOpaque(true);
        return combo;
    }
    
    private JPanel createFieldPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setPreferredSize(new Dimension(450, 80));
        panel.setMinimumSize(new Dimension(450, 80));
        panel.setMaximumSize(new Dimension(450, 80));
        panel.setOpaque(true);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Poppins", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        
        textField.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(label);
        panel.add(textField);
        
        return panel;
    }
    
    private JPanel createComboPanel(String labelText, JComboBox<String> combo) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(240, 240, 240));
        panel.setPreferredSize(new Dimension(450, 80));
        panel.setMinimumSize(new Dimension(450, 80));
        panel.setMaximumSize(new Dimension(450, 80));
        panel.setOpaque(true);
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Poppins", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(label);
        panel.add(combo);
        
        return panel;
    }
    
    private void fillForm() {
        if (driver != null) {
            txtNama.setText(driver.getNama());
            txtKTP.setText(driver.getNoKtp());
            txtSim.setText(driver.getNoSim());
            txtHP.setText(driver.getNoHp());
            txtAlamat.setText(driver.getAlamat());
            cmbStatus.setSelectedItem(driver.getStatus());
        }
    }
    
    private void simpanData(ActionEvent e) {
        if (!validateInput()) return;
        
        try (Connection conn = koneksi.getConnection()) {
            String sql;
            PreparedStatement ps;
            
            if (isEdit) {
                sql = "UPDATE driver SET nama=?, no_ktp=?, no_sim=?, no_hp=?, alamat=?, status=? WHERE id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, txtNama.getText().trim());
                ps.setString(2, txtKTP.getText().trim());
                ps.setString(3, txtSim.getText().trim());
                ps.setString(4, txtHP.getText().trim());
                ps.setString(5, txtAlamat.getText().trim());
                ps.setString(6, cmbStatus.getSelectedItem().toString());
                ps.setInt(7, driver.getId());
            } else {
                sql = "INSERT INTO driver(nama, no_ktp, no_sim, no_hp, alamat, status) VALUES (?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, txtNama.getText().trim());
                ps.setString(2, txtKTP.getText().trim());
                ps.setString(3, txtSim.getText().trim());
                ps.setString(4, txtHP.getText().trim());
                ps.setString(5, txtAlamat.getText().trim());
                ps.setString(6, cmbStatus.getSelectedItem().toString());
            }
            
            if (ps.executeUpdate() > 0) {
                isSuccess = true;
                JOptionPane.showMessageDialog(this, 
                    isEdit ? "Data driver berhasil diupdate!" : "Data driver berhasil ditambahkan!");
                dispose();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error Database: " + ex.getMessage());
        }
    }
    
    private boolean validateInput() {
        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Driver tidak boleh kosong!");
            txtNama.requestFocus();
            return false;
        }
        if (txtKTP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No KTP tidak boleh kosong!");
            txtKTP.requestFocus();
            return false;
        }
        if (txtSim.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No SIM tidak boleh kosong!");
            txtSim.requestFocus();
            return false;
        }
        if (txtHP.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No HP tidak boleh kosong!");
            txtHP.requestFocus();
            return false;
        }
        if (txtAlamat.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong!");
            txtAlamat.requestFocus();
            return false;
        }
        return true;
    }
    
    public boolean isSuccess() {
        return isSuccess;
    }
}