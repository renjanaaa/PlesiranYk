package dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import koneksi.koneksi;
import model.ModelPelanggan;

public class DialogPelanggan extends JDialog {

private JTextField txtNama, txtKTP, txtHP, txtEmail, txtAlamat;
private JComboBox<String> cmbJenisKelamin, cmbStatus;
private JButton btnSimpan, btnBatal;
private ModelPelanggan pelanggan;
private boolean isEdit = false;
private boolean isSuccess = false;

public DialogPelanggan(JFrame parent) {
    super(parent, "Tambah Pelanggan", true);
    initComponents();
    setLocationRelativeTo(parent);
}

public DialogPelanggan(JFrame parent, ModelPelanggan pelanggan) {
    super(parent, "Edit Pelanggan", true);
    this.pelanggan = pelanggan;
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
    
    JLabel headerLabel = new JLabel(isEdit ? "DATA MASTER > PELANGGAN > EDIT" : "DATA MASTER > PELANGGAN > TAMBAH");
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setFont(new Font("Poppins", Font.BOLD, 20));
    headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // Padding kiri disejajarkan
    headerPanel.add(headerLabel, BorderLayout.WEST); // Posisi di kiri
    
    // Main Content Panel - Padding dikurangi
    JPanel contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(new Color(240, 240, 240));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // Padding dikurangi
    
    // Form Panel dengan GridBagLayout
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(240, 240, 240));
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15, 25, 15, 25); // Spacing dikurangi dari 40,50 ke 15,25
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.NONE;
    
    // Initialize components dengan ukuran yang lebih kecil
    txtNama = createTextField();
    txtKTP = createTextField();
    txtHP = createTextField();
    txtEmail = createTextField();
    txtAlamat = createTextField();
    cmbJenisKelamin = createJenisKelaminComboBox();
    cmbStatus = createStatusComboBox();
    
    // Baris 1: Nama dan No.KTP
    gbc.gridx = 0; gbc.gridy = 0;
    formPanel.add(createFieldPanel("Nama Pelanggan", txtNama), gbc);
    
    gbc.gridx = 1; gbc.gridy = 0;
    formPanel.add(createFieldPanel("No.KTP", txtKTP), gbc);
    
    // Baris 2: No.HP dan Email
    gbc.gridx = 0; gbc.gridy = 1;
    formPanel.add(createFieldPanel("No.HP", txtHP), gbc);
    
    gbc.gridx = 1; gbc.gridy = 1;
    formPanel.add(createFieldPanel("Email", txtEmail), gbc);
    
    // Baris 3: Alamat dan Jenis Kelamin
    gbc.gridx = 0; gbc.gridy = 2;
    formPanel.add(createFieldPanel("Alamat", txtAlamat), gbc);
    
    gbc.gridx = 1; gbc.gridy = 2;
    formPanel.add(createComboPanel("Jenis Kelamin", cmbJenisKelamin), gbc);
    
    // Baris 4: Status (center span)
    gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.CENTER;
    formPanel.add(createComboPanel("Status", cmbStatus), gbc);
    
    // Button Panel - Spacing dikurangi
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30)); // Spacing dikurangi
    buttonPanel.setBackground(new Color(240, 240, 240));
    
    btnSimpan = new JButton(isEdit ? "Update" : "Simpan");
    btnSimpan.setPreferredSize(new Dimension(150, 50)); // Dikurangi dari 180x60 ke 150x50
    btnSimpan.setBackground(new Color(52, 152, 219));
    btnSimpan.setForeground(Color.WHITE);
    btnSimpan.setFont(new Font("Poppins", Font.BOLD, 16)); // Font dikurangi dari 18 ke 16
    btnSimpan.setBorder(BorderFactory.createEmptyBorder());
    btnSimpan.setFocusPainted(false);
    btnSimpan.addActionListener(this::simpanData);
    
    btnBatal = new JButton("Batal");
    btnBatal.setPreferredSize(new Dimension(150, 50)); // Dikurangi dari 180x60 ke 150x50
    btnBatal.setBackground(new Color(231, 76, 60));
    btnBatal.setForeground(Color.WHITE);
    btnBatal.setFont(new Font("Poppins", Font.BOLD, 16)); // Font dikurangi dari 18 ke 16
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
    textField.setPreferredSize(new Dimension(400, 45)); // Dikurangi dari 500x60 ke 400x45
    textField.setMinimumSize(new Dimension(400, 45));
    textField.setMaximumSize(new Dimension(400, 45));
    textField.setFont(new Font("Poppins", Font.PLAIN, 14)); // Font dikurangi dari 16 ke 14
    textField.setBackground(Color.WHITE);
    textField.setForeground(Color.BLACK);
    textField.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
        BorderFactory.createEmptyBorder(10, 15, 10, 15) // Padding dikurangi
    ));
    textField.setOpaque(true);
    return textField;
}

private JComboBox<String> createJenisKelaminComboBox() {
    JComboBox<String> combo = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
    combo.setPreferredSize(new Dimension(400, 45)); // Dikurangi dari 500x60 ke 400x45
    combo.setMinimumSize(new Dimension(400, 45));
    combo.setMaximumSize(new Dimension(400, 45));
    combo.setFont(new Font("Poppins", Font.PLAIN, 14)); // Font dikurangi dari 16 ke 14
    combo.setBackground(Color.WHITE);
    combo.setForeground(Color.BLACK);
    combo.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
    combo.setOpaque(true);
    return combo;
}

private JComboBox<String> createStatusComboBox() {
    JComboBox<String> combo = new JComboBox<>(new String[]{"Aktif", "Tidak Aktif", "Blacklist"});
    combo.setPreferredSize(new Dimension(400, 45)); // Dikurangi dari 500x60 ke 400x45
    combo.setMinimumSize(new Dimension(400, 45));
    combo.setMaximumSize(new Dimension(400, 45));
    combo.setFont(new Font("Poppins", Font.PLAIN, 14)); // Font dikurangi dari 16 ke 14
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
    panel.setPreferredSize(new Dimension(450, 80)); // Dikurangi dari 550x120 ke 450x80
    panel.setMinimumSize(new Dimension(450, 80));
    panel.setMaximumSize(new Dimension(450, 80));
    panel.setOpaque(true);
    
    JLabel label = new JLabel(labelText);
    label.setFont(new Font("Poppins", Font.BOLD, 14)); // Font dikurangi dari 16 ke 14
    label.setForeground(Color.BLACK);
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0)); // Spacing dikurangi
    
    textField.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    panel.add(label);
    panel.add(textField);
    
    return panel;
}

private JPanel createComboPanel(String labelText, JComboBox<String> combo) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(240, 240, 240));
    panel.setPreferredSize(new Dimension(450, 80)); // Dikurangi dari 550x120 ke 450x80
    panel.setMinimumSize(new Dimension(450, 80));
    panel.setMaximumSize(new Dimension(450, 80));
    panel.setOpaque(true);
    
    JLabel label = new JLabel(labelText);
    label.setFont(new Font("Poppins", Font.BOLD, 14)); // Font dikurangi dari 16 ke 14
    label.setForeground(Color.BLACK);
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    label.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0)); // Spacing dikurangi
    
    combo.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    panel.add(label);
    panel.add(combo);
    
    return panel;
}

private void fillForm() {
    if (pelanggan != null) {
        txtNama.setText(pelanggan.getNama());
        txtKTP.setText(pelanggan.getNoKtp());
        txtHP.setText(pelanggan.getNoHp());
        txtEmail.setText(pelanggan.getEmail());
        txtAlamat.setText(pelanggan.getAlamat());
        cmbJenisKelamin.setSelectedItem(pelanggan.getJenisKelamin());
        cmbStatus.setSelectedItem(pelanggan.getStatus());
    }
}

private void simpanData(ActionEvent e) {
    if (!validateInput()) return;
    
    try (Connection conn = koneksi.getConnection()) {
        String sql;
        PreparedStatement ps;
        
        if (isEdit) {
            sql = "UPDATE pelanggan SET nama=?, no_ktp=?, no_hp=?, email=?, alamat=?, jenis_kelamin=?, status=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, txtNama.getText().trim());
            ps.setString(2, txtKTP.getText().trim());
            ps.setString(3, txtHP.getText().trim());
            ps.setString(4, txtEmail.getText().trim());
            ps.setString(5, txtAlamat.getText().trim());
            ps.setString(6, cmbJenisKelamin.getSelectedItem().toString());
            ps.setString(7, cmbStatus.getSelectedItem().toString());
            ps.setInt(8, pelanggan.getId());
        } else {
            sql = "INSERT INTO pelanggan(nama, no_ktp, no_hp, email, alamat, jenis_kelamin, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, txtNama.getText().trim());
            ps.setString(2, txtKTP.getText().trim());
            ps.setString(3, txtHP.getText().trim());
            ps.setString(4, txtEmail.getText().trim());
            ps.setString(5, txtAlamat.getText().trim());
            ps.setString(6, cmbJenisKelamin.getSelectedItem().toString());
            ps.setString(7, cmbStatus.getSelectedItem().toString());
        }
        
        if (ps.executeUpdate() > 0) {
            isSuccess = true;
            JOptionPane.showMessageDialog(this, 
                isEdit ? "Data pelanggan berhasil diupdate!" : "Data pelanggan berhasil ditambahkan!");
            dispose();
        }
        
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error Database: " + ex.getMessage());
    }
}

private boolean validateInput() {
    if (txtNama.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Nama Pelanggan tidak boleh kosong!");
        txtNama.requestFocus();
        return false;
    }
    if (txtKTP.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "No.KTP tidak boleh kosong!");
        txtKTP.requestFocus();
        return false;
    }
    if (txtHP.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "No.HP tidak boleh kosong!");
        txtHP.requestFocus();
        return false;
    }
    if (txtEmail.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Email tidak boleh kosong!");
        txtEmail.requestFocus();
        return false;
    }
    if (txtAlamat.getText().trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Alamat tidak boleh kosong!");
        txtAlamat.requestFocus();
        return false;
    }
    
    // Validasi format email sederhana
    String email = txtEmail.getText().trim();
    if (!email.contains("@") || !email.contains(".")) {
        JOptionPane.showMessageDialog(this, "Format email tidak valid!");
        txtEmail.requestFocus();
        return false;
    }
    
    return true;
}

public boolean isSuccess() {
    return isSuccess;
}
}