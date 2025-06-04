package dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import koneksi.koneksi;
import model.ModelMobil;

public class DialogMobil extends JDialog {
    
    private JTextField txtNoPolisi, txtMerk, txtModel, txtKapasitas, txtHargaSewa;
    private JComboBox<String> cmbStatus;
    private JButton btnSimpan, btnBatal;
    private ModelMobil mobil;
    private boolean isEdit = false;
    private boolean isSuccess = false;
    
    public DialogMobil(JFrame parent) {
        super(parent, "Tambah Mobil", true);
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    public DialogMobil(JFrame parent, ModelMobil mobil) {
        super(parent, "Edit Mobil", true);
        this.mobil = mobil;
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
        
        JLabel headerLabel = new JLabel(isEdit ? "DATA MASTER > MOBIL > EDIT" : "DATA MASTER > MOBIL > TAMBAH");
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
        txtNoPolisi = createTextField();
        txtMerk = createTextField();
        txtModel = createTextField();
        txtKapasitas = createNumericTextField();
        txtHargaSewa = createNumericTextField();
        cmbStatus = createComboBox();
        
        // Baris 1: No. Polisi dan Merk
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createFieldPanel("No. Polisi", txtNoPolisi), gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(createFieldPanel("Merk", txtMerk), gbc);
        
        // Baris 2: Model dan Kapasitas Penumpang
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createFieldPanel("Model", txtModel), gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(createFieldPanel("Kapasitas Penumpang", txtKapasitas), gbc);
        
        // Baris 3: Harga Sewa/Hari dan Status
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createFieldPanel("Harga Sewa/Hari", txtHargaSewa), gbc);
        
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
    
    private JTextField createNumericTextField() {
        JTextField textField = createTextField();
        
        // Tambahkan key listener untuk hanya menerima angka
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (textField == txtHargaSewa) {
                    // Untuk harga sewa, izinkan angka dan titik desimal
                    if (!Character.isDigit(c) && c != '.' && c != '\b') {
                        evt.consume();
                    }
                    // Hanya satu titik desimal
                    if (c == '.' && textField.getText().contains(".")) {
                        evt.consume();
                    }
                } else {
                    // Untuk kapasitas, hanya angka
                    if (!Character.isDigit(c) && c != '\b') {
                        evt.consume();
                    }
                }
            }
        });
        return textField;
    }
    
    private JComboBox<String> createComboBox() {
        JComboBox<String> combo = new JComboBox<>(new String[]{"Tersedia", "Disewa", "Maintenance"});
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
        if (mobil != null) {
            txtNoPolisi.setText(mobil.getNoPolisi());
            txtMerk.setText(mobil.getMerk());
            txtModel.setText(mobil.getModel());
            txtKapasitas.setText(String.valueOf(mobil.getKapasitasPenumpang()));
            txtHargaSewa.setText(String.valueOf(mobil.getHargaSewaPerHari()));
            cmbStatus.setSelectedItem(mobil.getStatus());
        }
    }
    
    
    
    private void simpanData(ActionEvent e) {
        if (!validateInput()) return;
        
        try (Connection conn = koneksi.getConnection()) {
            String sql;
            PreparedStatement ps;
            
            if (isEdit) {
                sql = "UPDATE mobil SET no_polisi=?, merk=?, model=?, kapasitas_penumpang=?, harga_sewa_per_hari=?, status=? WHERE id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, txtNoPolisi.getText().trim());
                ps.setString(2, txtMerk.getText().trim());
                ps.setString(3, txtModel.getText().trim());
                ps.setInt(4, Integer.parseInt(txtKapasitas.getText().trim()));
                ps.setDouble(5, Double.parseDouble(txtHargaSewa.getText().trim()));
                ps.setString(6, cmbStatus.getSelectedItem().toString());
                ps.setInt(7, mobil.getId());
            } else {
                sql = "INSERT INTO mobil(no_polisi, merk, model, kapasitas_penumpang, harga_sewa_per_hari, status) VALUES (?, ?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, txtNoPolisi.getText().trim());
                ps.setString(2, txtMerk.getText().trim());
                ps.setString(3, txtModel.getText().trim());
                ps.setInt(4, Integer.parseInt(txtKapasitas.getText().trim()));
                ps.setDouble(5, Double.parseDouble(txtHargaSewa.getText().trim()));
                ps.setString(6, cmbStatus.getSelectedItem().toString());
            }
            
            if (ps.executeUpdate() > 0) {
                isSuccess = true;
                JOptionPane.showMessageDialog(this, 
                    isEdit ? "Data mobil berhasil diupdate!" : "Data mobil berhasil ditambahkan!");
                dispose();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error Database: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: Format angka tidak valid!");
        }
    }
    
    private boolean validateInput() {
        if (txtNoPolisi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No. Polisi tidak boleh kosong!");
            txtNoPolisi.requestFocus();
            return false;
        }
        if (txtMerk.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Merk tidak boleh kosong!");
            txtMerk.requestFocus();
            return false;
        }
        if (txtModel.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Model tidak boleh kosong!");
            txtModel.requestFocus();
            return false;
        }
        if (txtKapasitas.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kapasitas Penumpang tidak boleh kosong!");
            txtKapasitas.requestFocus();
            return false;
        }
        if (txtHargaSewa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harga Sewa/Hari tidak boleh kosong!");
            txtHargaSewa.requestFocus();
            return false;
        }
        
        // Validasi format angka
        try {
            int kapasitas = Integer.parseInt(txtKapasitas.getText().trim());
            if (kapasitas <= 0) {
                JOptionPane.showMessageDialog(this, "Kapasitas Penumpang harus lebih dari 0!");
                txtKapasitas.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Kapasitas Penumpang harus berupa angka!");
            txtKapasitas.requestFocus();
            return false;
        }
        
        try {
            double harga = Double.parseDouble(txtHargaSewa.getText().trim());
            if (harga <= 0) {
                JOptionPane.showMessageDialog(this, "Harga Sewa/Hari harus lebih dari 0!");
                txtHargaSewa.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga Sewa/Hari harus berupa angka!");
            txtHargaSewa.requestFocus();
            return false;
        }
        
        return true;
    }
    
    public boolean isSuccess() {
        return isSuccess;
    }
}