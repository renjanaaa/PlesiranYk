
package form;

import com.formdev.flatlaf.FlatClientProperties;
import dialog.DialogCariDriver;
import dialog.DialogCariMobil;
import dialog.DialogCariPelanggan;
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
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import koneksi.koneksi;
import model.ModelDriver;
import tablemodel.TableModelDriver;
import dialog.DialogDriver;
import dialog.DialogDriver;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import model.ModelMobil;
import model.ModelPelanggan;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import tablemodel.TableModelMobil;
import util.PenyewaanDetail;
import util.PenyewaanHeader;


public class FormPenyewaan extends JPanel {
    
    // Model objects untuk menyimpan data yang dipilih
    private ModelPelanggan selectedPelanggan;
    private ModelMobil selectedMobil;
    private ModelDriver selectedDriver;
    
    // Formatters
    private NumberFormat currencyFormat;
    private SimpleDateFormat dateFormat;
    
    // Layout management
    private CardLayout cardLayout;
    private boolean isListView = true;

    public FormPenyewaan() {
        System.out.println("=== Constructor FormPenyewaan dimulai ===");
        initializeFormatters();
        initComponents(); // Auto-generated method
        setupCustomLayout();
        setupStyling();
        setupEventHandlers();
        setupInitialState();
        System.out.println("Akan memanggil loadData() dari constructor...");
        loadData();
        System.out.println("Constructor selesai");
    }
    
    private void initializeFormatters() {
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    private void setupCustomLayout() {
        cardLayout = new CardLayout();
        setLayout(cardLayout);
        
        removeAll();
        setupListPanel();
        setupInputPanel();
        
        add(PanelMain, "LIST");
        add(addPanel, "INPUT");
        cardLayout.show(this, "LIST");
    }
    
    private void setupListPanel() {
        PanelMain.setLayout(new BorderLayout(10, 10));
        
        // Header panel
        jPanel4.setPreferredSize(new Dimension(0, 60));
        PanelMain.add(jPanel4, BorderLayout.NORTH);
        
        // Search and button panel
        JPanel searchPanel = createSearchPanel();
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(jScrollPane2, BorderLayout.CENTER);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(tablePanel, BorderLayout.CENTER);
        
        PanelMain.add(centerPanel, BorderLayout.CENTER);
    }
    
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(btnTambah1);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(txtPencarianData1);
        
        searchPanel.add(leftPanel, BorderLayout.WEST);
        searchPanel.add(rightPanel, BorderLayout.EAST);
        
        return searchPanel;
    }
    
    private void setupInputPanel() {
        addPanel.setLayout(new BorderLayout(10, 10));
        
        // Header
        jPanel3.setPreferredSize(new Dimension(0, 60));
        addPanel.add(jPanel3, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form panels (2 columns)
        JPanel formPanel = createFormPanel();
        contentPanel.add(formPanel, BorderLayout.NORTH);
        
        // Table and bottom panel
        JPanel tableDetailPanel = createTableDetailPanel();
        contentPanel.add(tableDetailPanel, BorderLayout.CENTER);
        
        addPanel.add(contentPanel, BorderLayout.CENTER);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout(20, 10));
        
        // Left column: Customer and Driver info
        JPanel leftColumn = new JPanel(new BorderLayout(0, 10));
        leftColumn.add(jPanel7, BorderLayout.NORTH);  // Customer info
        leftColumn.add(jPanel5, BorderLayout.CENTER); // Driver info
        
        // Right column: Car and Transaction info
        JPanel rightColumn = new JPanel(new BorderLayout(0, 10));
        rightColumn.add(jPanel2, BorderLayout.NORTH);  // Car info
        rightColumn.add(jPanel1, BorderLayout.CENTER); // Transaction info
        
        formPanel.add(leftColumn, BorderLayout.WEST);
        formPanel.add(rightColumn, BorderLayout.CENTER);
        
        return formPanel;
    }
    
    private JPanel createTableDetailPanel() {
        JPanel tableDetailPanel = new JPanel(new BorderLayout(0, 10));
        tableDetailPanel.add(jScrollPane1, BorderLayout.CENTER);

        // Bottom panel with notes, payment, and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(jPanel6, BorderLayout.WEST); // Notes

        JPanel paymentAndButtonPanel = new JPanel(new BorderLayout(10, 0));
        setupPembayaranPanel();
        paymentAndButtonPanel.add(jPanel8, BorderLayout.CENTER);
        paymentAndButtonPanel.add(createButtonPanel(), BorderLayout.EAST);  // Pastikan ini ada

        bottomPanel.add(paymentAndButtonPanel, BorderLayout.CENTER);
        tableDetailPanel.add(bottomPanel, BorderLayout.SOUTH);

        return tableDetailPanel;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        Dimension buttonSize = new Dimension(120, 35); // Perbesar sedikit

        // Configure buttons
        configureButton(btnTambahSewa, buttonSize);
        configureButton(btnCetakNota, buttonSize);  // Tambahkan ini
        configureButton(btnSimpan, buttonSize);
        configureButton(btnKembalikedepan, buttonSize);

        // Add buttons with spacing
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnTambahSewa);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(btnCetakNota);  // Tambahkan ini
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(btnSimpan);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(btnKembalikedepan);
        buttonPanel.add(Box.createVerticalGlue());

        return buttonPanel;
    }
    
    private void configureButton(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void setupPembayaranPanel() {
        jPanel8.removeAll();
        jPanel8.setLayout(new BorderLayout(10, 10));
        jPanel8.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        headerPanel.setOpaque(false);
        lblPembayaran.setFont(new Font("Poppins", Font.BOLD, 14));
        headerPanel.add(lblPembayaran);
        jPanel8.add(headerPanel, BorderLayout.NORTH);
        
        // Payment fields
        JPanel fieldsPanel = createPaymentFieldsPanel();
        jPanel8.add(fieldsPanel, BorderLayout.CENTER);
        
        jPanel8.revalidate();
        jPanel8.repaint();
    }
    
    private JPanel createPaymentFieldsPanel() {
        JPanel fieldsPanel = new JPanel(new BorderLayout(20, 10));
        fieldsPanel.setOpaque(false);
        
        // Left side: Total and Down payment
        JPanel leftPaymentPanel = new JPanel();
        leftPaymentPanel.setLayout(new BoxLayout(leftPaymentPanel, BoxLayout.Y_AXIS));
        leftPaymentPanel.setOpaque(false);
        
        leftPaymentPanel.add(createFieldPanel(lblPembayaran1, jTextField1, "Total Biaya"));
        leftPaymentPanel.add(Box.createVerticalStrut(8));
        leftPaymentPanel.add(createFieldPanel(lblPembayaran2, jTextField2, "Uang Muka"));
        
        // Right side: Remaining balance
        JPanel rightPaymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 15));
        rightPaymentPanel.setOpaque(false);
        rightPaymentPanel.add(createFieldPanel(lblPembayaran3, jTextField3, "Sisa Bayar"));
        
        jTextField3.setEditable(false); // Read-only
        
        fieldsPanel.add(leftPaymentPanel, BorderLayout.WEST);
        fieldsPanel.add(rightPaymentPanel, BorderLayout.EAST);
        
        return fieldsPanel;
    }
    
    private JPanel createFieldPanel(JLabel label, JTextField field, String labelText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        panel.setOpaque(false);
        
        label.setText(labelText);
        label.setPreferredSize(new Dimension(80, 25));
        field.setPreferredSize(new Dimension(150, 30));
        
        panel.add(label);
        panel.add(field);
        
        return panel;
    }
    
    private void setupStyling() {
        setBackground(new Color(240, 242, 247));
        setupTextFieldStyling();
        setupButtonStyling();
        setupPanelStyling();
        setupTableStyling();
        setupComboBoxStyling();
    }
    
    private void setupTextFieldStyling() {
        String textFieldStyle = "arc:8;borderWidth:1;borderColor:#bdc3c7;focusedBorderColor:#3498db;background:#ffffff;margin:5,10,5,10;minimumHeight:35";
        
        // Apply to all text fields
        JTextField[] textFields = {
            txtPenyewaan, txtTanggalSewa, txtTanggalKembali, txtTotalHari,
            txtCust, txtKTP, txtHPCust, txtAlamat,
            txtNamaDriver, txtNoHPDriv,
            txtMobil, txtNoPol, txtMerkMobil, txtHargaSewa,
            txtPencarianData1, txtKet,
            jTextField1, jTextField2, jTextField3
        };
        
        for (JTextField field : textFields) {
            field.putClientProperty(FlatClientProperties.STYLE, textFieldStyle);
        }
        
        // Set read-only fields
        setReadOnlyFields();
    }
    
    private void setReadOnlyFields() {
        JTextField[] readOnlyFields = {
            txtTotalHari, txtKTP, txtHPCust, txtAlamat,
            txtNoPol, txtMerkMobil, txtHargaSewa, txtNoHPDriv, jTextField3
        };
        
        for (JTextField field : readOnlyFields) {
            field.setEditable(false);
        }
    }
    
    private void setupButtonStyling() {
        String primaryStyle = "borderWidth:0;focusWidth:0;arc:8;background:#3498db;hoverBackground:#2980b9;pressedBackground:#21618c;foreground:#ffffff;minimumHeight:35;font:bold";
        String secondaryStyle = "borderWidth:1;borderColor:#3498db;focusWidth:0;arc:8;background:#ffffff;hoverBackground:#ecf0f1;pressedBackground:#d5dbdb;foreground:#3498db;minimumHeight:35;font:bold";

        // Primary buttons (background biru, teks putih)
        btnTambah1.putClientProperty(FlatClientProperties.STYLE, primaryStyle);
        btnSimpan.putClientProperty(FlatClientProperties.STYLE, primaryStyle);
        btnTambahSewa.putClientProperty(FlatClientProperties.STYLE, primaryStyle);
        btnCetakNota.setEnabled(true);
        btnCetakNota.setVisible(true);
        btnCetakNota.setFocusable(true);
        btnCetakNota.putClientProperty(FlatClientProperties.STYLE, primaryStyle);

        // Tambahkan tooltip untuk memastikan tombol responsif
        btnCetakNota.setToolTipText("Klik untuk mencetak nota penyewaan");

        // Debug: tambahkan mouse listener untuk memastikan tombol menerima event
        btnCetakNota.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("Mouse clicked pada btnCetakNota");
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                System.out.println("Mouse entered btnCetakNota");
            }
        });
        btnCariPel.putClientProperty(FlatClientProperties.STYLE, secondaryStyle);
        btnCariDriv.putClientProperty(FlatClientProperties.STYLE, secondaryStyle);
        btnCariMob.putClientProperty(FlatClientProperties.STYLE, secondaryStyle);
        btnKembalikedepan.putClientProperty(FlatClientProperties.STYLE, secondaryStyle);
    }
    
    private void setupPanelStyling() {
        String panelStyle = "background:#3498db;arc:10;border:10,15,10,15";
        
        JPanel[] panels = {jPanel1, jPanel2, jPanel5, jPanel6, jPanel7, jPanel8};
        for (JPanel panel : panels) {
            panel.putClientProperty(FlatClientProperties.STYLE, panelStyle);
        }
        
        lblPembayaran3.setText("Sisa Bayar");
    }
    
    private void setupTableStyling() {
        // Main table model
        String[] columnNames = {"No", "Kode Sewa", "Pelanggan", "Mobil", "Tanggal Sewa", "Tanggal Kembali", "Total", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable2.setModel(model);
        
        // Detail table model
        String[] detailColumns = {"No", "Mobil", "No.Polisi", "Merk/Type", "Harga/Hari", "Jumlah Hari", "Subtotal"};
        DefaultTableModel detailModel = new DefaultTableModel(detailColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable1.setModel(detailModel);
        
        // Table styling
        configureTable(jTable2);
        configureTable(jTable1);
        setColumnWidths();
    }
    
    private void configureTable(JTable table) {
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 12));
    }
    
    private void setColumnWidths() {
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(120);
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(150);
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(150);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(100);
            jTable2.getColumnModel().getColumn(7).setPreferredWidth(80);
        }
    }
    
    private void setupComboBoxStyling() {
        cmbxStatus.removeAllItems();
        cmbxStatus.addItem("Aktif");
        cmbxStatus.addItem("Selesai");
        cmbxStatus.addItem("Dibatalkan");
        
        String comboStyle = "arc:8;borderWidth:1;borderColor:#bdc3c7;focusedBorderColor:#3498db;background:#ffffff";
        cmbxStatus.putClientProperty(FlatClientProperties.STYLE, comboStyle);
    }
    
    private void setupEventHandlers() {
        // Navigation events
        btnTambah1.addActionListener(e -> showInputView());
        btnKembalikedepan.addActionListener(e -> showListView());
        
        // Action events
        btnSimpan.addActionListener(e -> simpanData());
        btnTambahSewa.addActionListener(e -> tambahMobilKeTable());
        
        // Search events
        txtPencarianData1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchData(txtPencarianData1.getText().trim());
            }
        });
        
        // Dialog events
        btnCariPel.addActionListener(e -> cariPelanggan());
        btnCariMob.addActionListener(e -> cariMobil());
        btnCariDriv.addActionListener(e -> cariDriver());
        
        btnCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakNotaActionPerformed(evt);
            }
        });
        setupCalculationEvents();

    }
    
    private void setupCalculationEvents() {
        KeyAdapter dateCalculator = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                hitungTotalHari();
            }
        };

        KeyAdapter paymentCalculator = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                hitungSisaPembayaran();
            }
        };

        txtTanggalSewa.addKeyListener(dateCalculator);
        txtTanggalKembali.addKeyListener(dateCalculator);

        jTextField2.addKeyListener(paymentCalculator);
    }

    
    private void setupInitialState() {
        toggleDriverFields();
        txtPencarianData1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, 
            "Cari berdasarkan kode, pelanggan, atau mobil...");
    }
    
    // Navigation methods
    public void showListView() {
        System.out.println("=== showListView() dipanggil ===");
        cardLayout.show(this, "LIST");
        isListView = true;
        System.out.println("Akan memanggil loadData()...");
        loadData();
        System.out.println("loadData() selesai dipanggil");
    }
    
    public void showInputView() {
        cardLayout.show(this, "INPUT");
        isListView = false;
        clearInputFields();
        generateKodePenyewaan();
    }
    
    // Dialog methods
    private void cariPelanggan() {
        DialogCariPelanggan dialog = new DialogCariPelanggan((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        
        if (dialog.isSelected()) {
            selectedPelanggan = dialog.getSelectedPelanggan();
            fillPelangganFields();
        }
    }
    
    private void fillPelangganFields() {
        if (selectedPelanggan != null) {
            txtCust.setText(selectedPelanggan.getNama());
            txtKTP.setText(selectedPelanggan.getNoKtp());
            txtHPCust.setText(selectedPelanggan.getNoHp());
            txtAlamat.setText(selectedPelanggan.getAlamat());
        }
    }
    
    private void cariMobil() {
        DialogCariMobil dialog = new DialogCariMobil((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        
        if (dialog.isSelected()) {
            selectedMobil = dialog.getSelectedMobil();
            fillMobilFields();
        }
    }
    
    private void fillMobilFields() {
        if (selectedMobil != null) {
            txtMobil.setText(selectedMobil.getModel());
            txtNoPol.setText(selectedMobil.getNoPolisi());
            txtMerkMobil.setText(selectedMobil.getMerk() + " " + selectedMobil.getModel());
            txtHargaSewa.setText(String.valueOf(selectedMobil.getHargaSewaPerHari()));
        }
    }
    
    private void cariDriver() {
        DialogCariDriver dialog = new DialogCariDriver((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);

        if (dialog.isSelected()) {
            selectedDriver = dialog.getSelectedDriver();
            fillDriverFields();

            System.out.println("Driver dipilih: " + selectedDriver.getNama() + " (ID: " + selectedDriver.getId() + ")");
        }
    }
    
    private void fillDriverFields() {
        if (selectedDriver != null) {
            txtNamaDriver.setText(selectedDriver.getNama());
            txtNoHPDriv.setText(selectedDriver.getNoHp());
        }
    }
    
    // Calculation methods
    private void hitungTotalHari() {
        try {
            String tanggalSewa = txtTanggalSewa.getText().trim();
            String tanggalKembali = txtTanggalKembali.getText().trim();
            
            if (!tanggalSewa.isEmpty() && !tanggalKembali.isEmpty()) {
                Date dateSewa = dateFormat.parse(tanggalSewa);
                Date dateKembali = dateFormat.parse(tanggalKembali);
                
                long diffInMillies = dateKembali.getTime() - dateSewa.getTime();
                long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
                
                txtTotalHari.setText(String.valueOf(Math.max(1, diffInDays)));
            }
        } catch (Exception e) {
            // Ignore parsing errors
        }
    }
    
    private void updateTotalBiaya() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        double total = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                // Ambil nilai subtotal dari kolom ke-6 (index 6)
                String subtotalStr = model.getValueAt(i, 6).toString();

                // Hapus format currency (Rp, titik, koma) dan parse sebagai double
                String cleanStr = subtotalStr.replaceAll("[^0-9,]", "").replace(",", ".");

                // Jika masih ada koma sebagai pemisah ribuan (format Indonesia)
                if (cleanStr.contains(".") && cleanStr.lastIndexOf('.') != cleanStr.indexOf('.')) {
                    // Ada multiple dots, hapus yang bukan decimal separator
                    int lastDotIndex = cleanStr.lastIndexOf('.');
                    cleanStr = cleanStr.substring(0, lastDotIndex).replace(".", "") + 
                              cleanStr.substring(lastDotIndex);
                }

                double subtotal = Double.parseDouble(cleanStr);
                total += subtotal;

            } catch (NumberFormatException e) {
                System.err.println("Error parsing subtotal at row " + i + ": " + e.getMessage());
            }
        }

        // Set total biaya tanpa format currency (hanya angka)
        jTextField1.setText(String.valueOf((long)total));

        // Hitung sisa bayar
        hitungSisaPembayaran();
    }

    private void hitungSisaPembayaran() {
        try {
            // Parse total biaya
            String totalBiayaStr = jTextField1.getText().trim();
            double totalBiaya = totalBiayaStr.isEmpty() ? 0 : Double.parseDouble(totalBiayaStr);

            // Parse uang muka
            String uangMukaStr = jTextField2.getText().trim();
            double uangMuka = uangMukaStr.isEmpty() ? 0 : Double.parseDouble(uangMukaStr);

            // Hitung sisa bayar
            double sisaBayar = totalBiaya - uangMuka;

            // Set sisa bayar (format sebagai currency untuk display)
            jTextField3.setText(currencyFormat.format(sisaBayar));

        } catch (NumberFormatException e) {
            jTextField3.setText(currencyFormat.format(0));
            System.err.println("Error calculating remaining payment: " + e.getMessage());
        }
    }
    // Tambahkan method helper untuk parsing angka dari format currency


    // Tambahkan method untuk memformat angka ke format currency
    private String formatToCurrency(double amount) {
        return currencyFormat.format(amount);
    }

    // Perbaiki method validateMobilInput untuk lebih robust
    private boolean validateMobilInput() {
        if (txtMobil.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih mobil terlebih dahulu!");
            return false;
        }

        if (txtTotalHari.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tentukan tanggal sewa dan kembali terlebih dahulu!");
            return false;
        }

        try {
            int totalHari = Integer.parseInt(txtTotalHari.getText().trim());
            if (totalHari <= 0) {
                JOptionPane.showMessageDialog(this, "Total hari harus lebih dari 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format total hari tidak valid!");
            return false;
        }

        if (txtHargaSewa.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harga sewa tidak boleh kosong!");
            return false;
        }

        try {
            // Coba parse harga sewa
            String hargaStr = txtHargaSewa.getText().trim().replace(".", "").replace(",", "");
            Double.parseDouble(hargaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format harga sewa tidak valid!");
            return false;
        }

        return true;
    }
    
    private double parseDouble(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0.0;
        }

        try {
            String original = text.trim();
            System.out.println("Parsing: '" + original + "'"); // Debug log

            // Hapus semua karakter selain angka, titik, dan koma
            String cleaned = original.replaceAll("[^0-9.,]", "");
            System.out.println("After removing non-numeric: '" + cleaned + "'");

            if (cleaned.isEmpty()) {
                return 0.0;
            }

            // Handle different number formats
            if (cleaned.contains(",") && cleaned.contains(".")) {
                // Determine which is decimal separator based on position
                int lastCommaPos = cleaned.lastIndexOf(',');
                int lastDotPos = cleaned.lastIndexOf('.');

                if (lastCommaPos > lastDotPos) {
                    // Format: 1.234.567,89 (European/Indonesian)
                    cleaned = cleaned.substring(0, lastCommaPos).replace(".", "").replace(",", "") + 
                             "." + cleaned.substring(lastCommaPos + 1);
                } else {
                    // Format: 1,234,567.89 (US)
                    cleaned = cleaned.replace(",", "");
                }
            } else if (cleaned.contains(",")) {
                // Only comma present
                int commaPos = cleaned.lastIndexOf(',');
                String afterComma = cleaned.substring(commaPos + 1);

                if (afterComma.length() <= 2 && !afterComma.contains(".")) {
                    // Likely decimal separator: 1234,56
                    cleaned = cleaned.replace(",", ".");
                } else {
                    // Likely thousands separator: 1,234 or 1,234,567
                    cleaned = cleaned.replace(",", "");
                }
            } else if (cleaned.contains(".")) {
                // Only dots present
                long dotCount = cleaned.chars().filter(ch -> ch == '.').count();

                if (dotCount == 1) {
                    // Single dot - could be decimal or thousands
                    int dotPos = cleaned.indexOf('.');
                    String afterDot = cleaned.substring(dotPos + 1);

                    if (afterDot.length() > 3) {
                        // Likely thousands separator: 1.2345
                        cleaned = cleaned.replace(".", "");
                    } 
                    // else: keep as decimal separator
                } else {
                    // Multiple dots - all are thousands separators except possibly the last
                    int lastDotPos = cleaned.lastIndexOf('.');
                    String afterLastDot = cleaned.substring(lastDotPos + 1);

                    if (afterLastDot.length() <= 2) {
                        // Last dot is decimal separator: 1.234.567.89
                        String integerPart = cleaned.substring(0, lastDotPos).replace(".", "");
                        cleaned = integerPart + "." + afterLastDot;
                    } else {
                        // All dots are thousands separators: 1.234.567
                        cleaned = cleaned.replace(".", "");
                    }
                }
            }

            System.out.println("Final cleaned string: '" + cleaned + "'");

            // Final validation - should not contain multiple dots
            if (cleaned.chars().filter(ch -> ch == '.').count() > 1) {
                System.err.println("Still contains multiple dots, removing all dots");
                cleaned = cleaned.replace(".", "");
            }

            double result = Double.parseDouble(cleaned);
            System.out.println("Parsed result: " + result);
            return result;

        } catch (NumberFormatException e) {
            System.err.println("Error parsing '" + text + "': " + e.getMessage());

            // Fallback: extract only digits
            String digitsOnly = text.replaceAll("[^0-9]", "");
            if (!digitsOnly.isEmpty()) {
                try {
                    return Double.parseDouble(digitsOnly);
                } catch (NumberFormatException e2) {
                    System.err.println("Fallback also failed for: " + digitsOnly);
                }
            }
            return 0.0;
        }
    }

    private void tambahMobilKeTable() {
        if (!validateMobilInput()) return;

        try {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

            int jumlahHari = Integer.parseInt(txtTotalHari.getText().trim());

            // Parse harga sewa dengan benar - JANGAN kalikan dengan 1000 lagi!
            String hargaStr = txtHargaSewa.getText().trim();
            double hargaPerHari = Double.parseDouble(hargaStr);

            // Hitung subtotal
            double subtotal = jumlahHari * hargaPerHari;

            Object[] row = {
                model.getRowCount() + 1,
                txtMobil.getText(),
                txtNoPol.getText(),
                txtMerkMobil.getText(),
                currencyFormat.format(hargaPerHari), // Format currency untuk display
                jumlahHari,
                currencyFormat.format(subtotal)      // Format currency untuk display
            };
            model.addRow(row);

            // Clear mobil fields
            clearMobilFields();

            // Update total biaya
            updateTotalBiaya();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Format angka tidak valid: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private double parseCurrencyToDouble(String currencyStr) {
        if (currencyStr == null || currencyStr.trim().isEmpty()) {
            return 0;
        }

        // Hapus "Rp" dan spasi
        String cleaned = currencyStr.replace("Rp", "").trim();

        // Hapus titik sebagai pemisah ribuan, tapi pertahankan koma sebagai decimal
        // Format Indonesia: Rp1.000.000,00 atau Rp1.000.000
        if (cleaned.contains(",")) {
            // Ada decimal separator
            String[] parts = cleaned.split(",");
            String integerPart = parts[0].replace(".", ""); // Hapus titik pemisah ribuan
            String decimalPart = parts.length > 1 ? parts[1] : "00";
            cleaned = integerPart + "." + decimalPart;
        } else {
            // Tidak ada decimal, hapus semua titik
            cleaned = cleaned.replace(".", "");
        }

        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing currency: " + currencyStr);
            return 0;
        }
    }
    
    private void clearMobilFields() {
        txtMobil.setText("");
        txtNoPol.setText("");
        txtMerkMobil.setText("");
        txtHargaSewa.setText("");
        selectedMobil = null;
    }
    
    private void updateStatusDriver(Connection conn, int driverId, String status) throws SQLException {
        String sql = "UPDATE driver SET status = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, driverId);
        int rowsAffected = ps.executeUpdate();
        System.out.println("Driver ID " + driverId + " status updated to: " + status + " (Rows affected: " + rowsAffected + ")");
    }

    private void resetDriverStatusYangTidakDigunakan(Connection conn) throws SQLException {
        String sql = """
            UPDATE driver SET status = 'tidak aktif' 
            WHERE id NOT IN (
                SELECT DISTINCT driver_id 
                FROM penyewaan 
                WHERE driver_id IS NOT NULL 
                AND status IN ('Aktif', 'Berlangsung')
            )
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        int rowsAffected = ps.executeUpdate();
        System.out.println("Reset status driver yang tidak digunakan: " + rowsAffected + " drivers");
    }

    private void setDriverAktifYangDigunakan(Connection conn) throws SQLException {
        String sql = """
            UPDATE driver SET status = 'aktif' 
            WHERE id IN (
                SELECT DISTINCT driver_id 
                FROM penyewaan 
                WHERE driver_id IS NOT NULL 
                AND status IN ('Aktif', 'Berlangsung')
            )
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        int rowsAffected = ps.executeUpdate();
        System.out.println("Set driver aktif yang sedang digunakan: " + rowsAffected + " drivers");
    }

    private void simpanData() {
        if (!validateInput()) return;

        try (Connection conn = koneksi.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Insert main rental record
                int penyewaanId = insertPenyewaan(conn);

                // Insert rental details
                insertPenyewaanDetail(conn, penyewaanId);

                // Update status mobil menjadi "disewakan"
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                List<Integer> mobilIds = getMobilIdsFromDetail(model);

                for (Integer mobilId : mobilIds) {
                    updateStatusMobil(conn, mobilId, "disewakan");
                }
                
                resetDriverStatusYangTidakDigunakan(conn);

                if (selectedDriver != null) {
                    updateStatusDriver(conn, selectedDriver.getId(), "aktif");
                }

                setDriverAktifYangDigunakan(conn);

                conn.commit();
                JOptionPane.showMessageDialog(this, "Data penyewaan berhasil disimpan!\nStatus mobil telah diupdate menjadi 'disewakan'.");
                showListView();

                // Refresh dashboard jika ada
                refreshDashboardIfExists();

            } catch (Exception e) {
                conn.rollback();
                throw e;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method untuk refresh dashboard
    private void refreshDashboardIfExists() {
        try {
            // Tunggu sebentar untuk memastikan database sudah terupdate
            Thread.sleep(500);

            // Cari FormDashboard di semua window
            boolean dashboardFound = false;

            for (Window window : Window.getWindows()) {
                if (window instanceof JFrame) {
                    JFrame frame = (JFrame) window;
                    if (refreshDashboardInFrame(frame)) {
                        dashboardFound = true;
                        System.out.println("Dashboard refreshed successfully!");
                    }
                }
            }

            if (!dashboardFound) {
                System.out.println("WARNING: Dashboard not found for refresh!");
            }

        } catch (Exception e) {
            System.err.println("Error refreshing dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean refreshDashboardInFrame(JFrame frame) {
        // Cari komponen FormDashboard
        return findAndRefreshDashboard(frame.getContentPane());
    }

    private boolean findAndRefreshDashboard(Container container) {
        // Cek semua komponen dalam container
        for (Component comp : container.getComponents()) {
            // Jika komponen adalah FormDashboard
            if (comp instanceof form.FormDashboard) {
                form.FormDashboard dashboard = (form.FormDashboard) comp;
                // Panggil forceRefresh untuk memastikan refresh dilakukan
                dashboard.forceRefresh();
                return true;
            }

            // Jika komponen adalah container, cek di dalamnya
            if (comp instanceof Container) {
                if (findAndRefreshDashboard((Container) comp)) {
                    return true;
                }
            }
        }

        return false;
    }
    
    private void refreshDashboardInPanel(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component comp : components) {
            if (comp instanceof form.FormDashboard) {
                ((form.FormDashboard) comp).refreshDashboard();
                System.out.println("Dashboard found in panel and refreshed!");
                return;
            }

            if (comp instanceof JPanel) {
                refreshDashboardInPanel((JPanel) comp);
            }
        }
    }

    private void debugMobilStatusAfterSave() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT no_polisi, status FROM mobil ORDER BY no_polisi";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("=== Status Mobil Setelah Save ===");
            while (rs.next()) {
                String noPolisi = rs.getString("no_polisi");
                String status = rs.getString("status");
                System.out.println(noPolisi + " -> " + status);
            }
            System.out.println("=================================");

        } catch (SQLException e) {
            System.err.println("Error debug mobil status: " + e.getMessage());
        }
    }
    
    private int insertPenyewaan(Connection conn) throws SQLException {
        String sql = "INSERT INTO penyewaan (kode_penyewaan, nama_pelanggan, pelanggan_id, driver_id, tanggal_sewa, tanggal_kembali_rencana, total_hari, total_biaya, uang_muka, sisa_bayar, status, keterangan, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        ps.setString(1, txtPenyewaan.getText().trim());
        ps.setString(2, selectedPelanggan.getNama());
        ps.setInt(3, selectedPelanggan.getId());

        if (selectedDriver != null) {
            ps.setInt(4, selectedDriver.getId());
        } else {
            ps.setNull(4, java.sql.Types.INTEGER); 
        }

        ps.setString(5, txtTanggalSewa.getText().trim());
        ps.setString(6, txtTanggalKembali.getText().trim());
        ps.setInt(7, Integer.parseInt(txtTotalHari.getText().trim()));
        ps.setDouble(8, parseDouble(jTextField1.getText()));
        ps.setDouble(9, parseDouble(jTextField2.getText()));
        ps.setDouble(10, parseDouble(jTextField3.getText()));
        ps.setString(11, cmbxStatus.getSelectedItem().toString());
        ps.setString(12, txtKet.getText().trim());
        ps.setInt(13, 1); // User ID

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        throw new SQLException("Failed to get generated ID");
    }
    
    private void insertPenyewaanDetail(Connection conn, int penyewaanId) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String sql = "INSERT INTO penyewaan_detail (penyewaan_id, mobil, no_polisi, merk_type, harga_per_hari, jumlah_hari, subtotal) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement ps = conn.prepareStatement(sql);
        
        for (int i = 0; i < model.getRowCount(); i++) {
            ps.setInt(1, penyewaanId);
            ps.setString(2, model.getValueAt(i, 1).toString());
            ps.setString(3, model.getValueAt(i, 2).toString());
            ps.setString(4, model.getValueAt(i, 3).toString());
            ps.setDouble(5, parseDouble(model.getValueAt(i, 4).toString()));
            ps.setInt(6, Integer.parseInt(model.getValueAt(i, 5).toString()));
            ps.setDouble(7, parseDouble(model.getValueAt(i, 6).toString()));
            
            ps.executeUpdate();
        }
    }
    
    private boolean validateInput() {
        if (txtPenyewaan.getText().trim().isEmpty()) {
            showError("Kode penyewaan harus diisi!");
            return false;
        }
        if (selectedPelanggan == null) {
            showError("Pelanggan harus dipilih!");
            return false;
        }
        if (txtTanggalSewa.getText().trim().isEmpty()) {
            showError("Tanggal sewa harus diisi!");
            return false;
        }
        if (txtTanggalKembali.getText().trim().isEmpty()) {
            showError("Tanggal kembali harus diisi!");
            return false;
        }
        if (jTable1.getRowCount() == 0) {
            showError("Minimal satu mobil harus dipilih!");
            return false;
        }

        if (!txtNamaDriver.getText().trim().isEmpty() && selectedDriver == null) {
            showError("Jika nama driver diisi, silakan pilih driver dari database!");
            return false;
        }

        return true;
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validasi Error", JOptionPane.WARNING_MESSAGE);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelMain = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtPencarianData1 = new javax.swing.JTextField();
        btnTambah1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        pagination1 = new model.Pagination();
        addPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnSimpan = new javax.swing.JButton();
        btnKembalikedepan = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblPenyewaan = new javax.swing.JLabel();
        lblTanggalSewa = new javax.swing.JLabel();
        lblTanggalKembali = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblTotalHari = new javax.swing.JLabel();
        cmbxStatus = new javax.swing.JComboBox<>();
        txtPenyewaan = new javax.swing.JTextField();
        txtTanggalSewa = new javax.swing.JTextField();
        txtTanggalKembali = new javax.swing.JTextField();
        txtTotalHari = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblCust = new javax.swing.JLabel();
        lblKTP = new javax.swing.JLabel();
        lblNoHPCust = new javax.swing.JLabel();
        txtMobil = new javax.swing.JTextField();
        txtNoPol = new javax.swing.JTextField();
        txtMerkMobil = new javax.swing.JTextField();
        txtHargaSewa = new javax.swing.JTextField();
        lblHargaSewa = new javax.swing.JLabel();
        btnCariMob = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblnfoDriv = new javax.swing.JLabel();
        lblDriver = new javax.swing.JLabel();
        lblNoHPDriv = new javax.swing.JLabel();
        txtNamaDriver = new javax.swing.JTextField();
        txtNoHPDriv = new javax.swing.JTextField();
        btnCariDriv = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        lblKet = new javax.swing.JLabel();
        txtKet = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblCust1 = new javax.swing.JLabel();
        lblKTP1 = new javax.swing.JLabel();
        lblNoHPCust1 = new javax.swing.JLabel();
        txtCust = new javax.swing.JTextField();
        txtKTP = new javax.swing.JTextField();
        txtHPCust = new javax.swing.JTextField();
        txtAlamat = new javax.swing.JTextField();
        lblAlamat1 = new javax.swing.JLabel();
        btnCariPel = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        lblPembayaran = new javax.swing.JLabel();
        lblPembayaran1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        lblPembayaran2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        lblPembayaran3 = new javax.swing.JLabel();
        btnTambahSewa = new javax.swing.JButton();
        btnCetakNota = new javax.swing.JButton();

        jPanel4.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        jLabel3.setFont(new java.awt.Font("Poppins ExtraBold", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PLESIRAN YK > TRANSAKSI > PENYEWAAN");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel3)
                .addContainerGap(584, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtPencarianData1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPencarianData1ActionPerformed(evt);
            }
        });

        btnTambah1.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnTambah1.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnTambah1.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah1.setText("+ Tambah");
        btnTambah1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambah1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout PanelMainLayout = new javax.swing.GroupLayout(PanelMain);
        PanelMain.setLayout(PanelMainLayout);
        PanelMainLayout.setHorizontalGroup(
            PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelMainLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelMainLayout.createSequentialGroup()
                                .addComponent(btnTambah1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(682, 682, 682)
                                .addComponent(txtPencarianData1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1055, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelMainLayout.setVerticalGroup(
            PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPencarianData1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pagination1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        jPanel3.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        jLabel2.setFont(new java.awt.Font("Poppins ExtraBold", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PLESIRAN YK > TRANSAKSI > PENYEWAAN > TAMBAH");

        btnSimpan.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnSimpan.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnKembalikedepan.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnKembalikedepan.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnKembalikedepan.setText("Kembali");
        btnKembalikedepan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembalikedepanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel2)
                .addGap(225, 225, 225)
                .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnKembalikedepan, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan)
                    .addComponent(btnKembalikedepan))
                .addContainerGap())
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

        jLabel1.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INFORMASI TRANSAKSI");

        lblPenyewaan.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblPenyewaan.setForeground(new java.awt.Color(255, 255, 255));
        lblPenyewaan.setText("Kode Penyewaan");

        lblTanggalSewa.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblTanggalSewa.setForeground(new java.awt.Color(255, 255, 255));
        lblTanggalSewa.setText("Tanggal Sewa");

        lblTanggalKembali.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblTanggalKembali.setForeground(new java.awt.Color(255, 255, 255));
        lblTanggalKembali.setText("Tanggal Kembali");

        lblStatus.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 255, 255));
        lblStatus.setText("Status");

        lblTotalHari.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblTotalHari.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalHari.setText("Total Hari");

        cmbxStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbxStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbxStatusActionPerformed(evt);
            }
        });

        txtPenyewaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPenyewaanActionPerformed(evt);
            }
        });

        txtTanggalSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalSewaActionPerformed(evt);
            }
        });

        txtTanggalKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalKembaliActionPerformed(evt);
            }
        });

        txtTotalHari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalHariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPenyewaan)
                            .addComponent(lblTanggalSewa))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPenyewaan)
                            .addComponent(txtTanggalSewa)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTanggalKembali)
                        .addGap(18, 18, 18)
                        .addComponent(txtTanggalKembali)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalHari)
                    .addComponent(lblStatus))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbxStatus, 0, 143, Short.MAX_VALUE)
                    .addComponent(txtTotalHari))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPenyewaan)
                    .addComponent(lblStatus)
                    .addComponent(cmbxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPenyewaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTanggalSewa)
                    .addComponent(lblTotalHari)
                    .addComponent(txtTanggalSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalHari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTanggalKembali)
                    .addComponent(txtTanggalKembali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("INFORMASI MOBIL");

        lblCust.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblCust.setForeground(new java.awt.Color(255, 255, 255));
        lblCust.setText("Mobil");

        lblKTP.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblKTP.setForeground(new java.awt.Color(255, 255, 255));
        lblKTP.setText("No.Polisi");

        lblNoHPCust.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblNoHPCust.setForeground(new java.awt.Color(255, 255, 255));
        lblNoHPCust.setText("Merk/Type");

        txtMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMobilActionPerformed(evt);
            }
        });

        txtNoPol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoPolActionPerformed(evt);
            }
        });

        txtMerkMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMerkMobilActionPerformed(evt);
            }
        });

        txtHargaSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaSewaActionPerformed(evt);
            }
        });

        lblHargaSewa.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblHargaSewa.setForeground(new java.awt.Color(255, 255, 255));
        lblHargaSewa.setText("Harga Sewa");

        btnCariMob.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        btnCariMob.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnCariMob.setText("CARI");
        btnCariMob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariMobActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCust)
                            .addComponent(lblKTP)
                            .addComponent(lblNoHPCust)
                            .addComponent(lblHargaSewa))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNoPol)
                            .addComponent(txtMobil, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtHargaSewa)
                            .addComponent(txtMerkMobil, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariMob)
                        .addGap(16, 16, 16))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCust)
                    .addComponent(txtMobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariMob))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKTP)
                    .addComponent(txtNoPol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoHPCust)
                    .addComponent(txtMerkMobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHargaSewa)
                    .addComponent(txtHargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jPanel5.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        lblnfoDriv.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblnfoDriv.setForeground(new java.awt.Color(255, 255, 255));
        lblnfoDriv.setText("INFORMASI DRIVER");

        lblDriver.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblDriver.setForeground(new java.awt.Color(255, 255, 255));
        lblDriver.setText("Driver");

        lblNoHPDriv.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblNoHPDriv.setForeground(new java.awt.Color(255, 255, 255));
        lblNoHPDriv.setText("No.HP");

        txtNamaDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaDriverActionPerformed(evt);
            }
        });

        txtNoHPDriv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoHPDrivActionPerformed(evt);
            }
        });

        btnCariDriv.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        btnCariDriv.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnCariDriv.setText("CARI");
        btnCariDriv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariDrivActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDriver)
                            .addComponent(lblNoHPDriv))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNoHPDriv, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                            .addComponent(txtNamaDriver))
                        .addGap(68, 68, 68)
                        .addComponent(btnCariDriv))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(lblnfoDriv)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblnfoDriv)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDriver)
                    .addComponent(txtNamaDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariDriv))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoHPDriv)
                    .addComponent(txtNoHPDriv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        lblKet.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblKet.setForeground(new java.awt.Color(255, 255, 255));
        lblKet.setText("KETERANGAN");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(lblKet)
                        .addGap(0, 331, Short.MAX_VALUE))
                    .addComponent(txtKet))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtKet)
                .addContainerGap())
        );

        jPanel7.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("INFORMASI PELANGGAN");

        lblCust1.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblCust1.setForeground(new java.awt.Color(255, 255, 255));
        lblCust1.setText("Pelanggan");

        lblKTP1.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblKTP1.setForeground(new java.awt.Color(255, 255, 255));
        lblKTP1.setText("No.Ktp");

        lblNoHPCust1.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblNoHPCust1.setForeground(new java.awt.Color(255, 255, 255));
        lblNoHPCust1.setText("No.Hp");

        txtCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCustActionPerformed(evt);
            }
        });

        txtKTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKTPActionPerformed(evt);
            }
        });

        txtHPCust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHPCustActionPerformed(evt);
            }
        });

        txtAlamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlamatActionPerformed(evt);
            }
        });

        lblAlamat1.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblAlamat1.setForeground(new java.awt.Color(255, 255, 255));
        lblAlamat1.setText("Alamat");

        btnCariPel.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        btnCariPel.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnCariPel.setText("CARI");
        btnCariPel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCust1)
                            .addComponent(lblKTP1)
                            .addComponent(lblNoHPCust1)
                            .addComponent(lblAlamat1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKTP, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
                            .addComponent(txtCust, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtAlamat)
                            .addComponent(txtHPCust, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariPel)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(124, 124, 124))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCust1)
                    .addComponent(txtCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariPel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKTP1)
                    .addComponent(txtKTP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoHPCust1)
                    .addComponent(txtHPCust, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAlamat1)
                    .addComponent(txtAlamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        lblPembayaran.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran.setText("PEMBAYARAN");

        lblPembayaran1.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran1.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran1.setText("Total Biaya");

        lblPembayaran2.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran2.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran2.setText("Uang Muka");

        lblPembayaran3.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran3.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran3.setText("Uang Muka");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(lblPembayaran)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(lblPembayaran1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(lblPembayaran2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(lblPembayaran3)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(137, 137, 137))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPembayaran)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPembayaran1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPembayaran3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPembayaran2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTambahSewa.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnTambahSewa.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnTambahSewa.setText("Tambah");
        btnTambahSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahSewaActionPerformed(evt);
            }
        });

        btnCetakNota.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        btnCetakNota.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnCetakNota.setText("Cetak Nota");
        btnCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakNotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addPanelLayout = new javax.swing.GroupLayout(addPanel);
        addPanel.setLayout(addPanelLayout);
        addPanelLayout.setHorizontalGroup(
            addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(addPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(addPanelLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCetakNota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnTambahSewa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(addPanelLayout.createSequentialGroup()
                        .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        addPanelLayout.setVerticalGroup(
            addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(addPanelLayout.createSequentialGroup()
                        .addComponent(btnTambahSewa)
                        .addGap(18, 18, 18)
                        .addComponent(btnCetakNota)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(addPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(addPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void txtPencarianData1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPencarianData1ActionPerformed
        String keyword = txtPencarianData1.getText().trim();
        searchData(keyword);
    }//GEN-LAST:event_txtPencarianData1ActionPerformed

    private void btnTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambah1ActionPerformed
         showInputView();
    }//GEN-LAST:event_btnTambah1ActionPerformed

    private void txtTotalHariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalHariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalHariActionPerformed

    private void txtNoHPDrivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoHPDrivActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoHPDrivActionPerformed

    private void btnCariDrivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariDrivActionPerformed
        cariDriver();
    }//GEN-LAST:event_btnCariDrivActionPerformed

    private void btnCariPelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPelActionPerformed
        cariPelanggan();
    }//GEN-LAST:event_btnCariPelActionPerformed

    private void btnCariMobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariMobActionPerformed
        cariMobil();
    }//GEN-LAST:event_btnCariMobActionPerformed

    private void btnTambahSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahSewaActionPerformed
        tambahMobilKeTable();
    }//GEN-LAST:event_btnTambahSewaActionPerformed

    private void btnKembalikedepanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembalikedepanActionPerformed
        showListView();
    }//GEN-LAST:event_btnKembalikedepanActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpanData();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtTanggalSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalSewaActionPerformed
        simpanData();
    }//GEN-LAST:event_txtTanggalSewaActionPerformed

    private void txtTanggalKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalKembaliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalKembaliActionPerformed

    private void cmbxStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbxStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbxStatusActionPerformed

    private void txtPenyewaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPenyewaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPenyewaanActionPerformed

    private void txtNamaDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaDriverActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaDriverActionPerformed

    private void txtAlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAlamatActionPerformed

    private void txtHPCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHPCustActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHPCustActionPerformed

    private void txtKTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKTPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKTPActionPerformed

    private void txtHargaSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaSewaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaSewaActionPerformed

    private void txtMerkMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMerkMobilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMerkMobilActionPerformed

    private void txtNoPolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoPolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoPolActionPerformed

    private void txtMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMobilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMobilActionPerformed

    private void txtCustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCustActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCustActionPerformed

    private void btnCetakNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakNotaActionPerformed
    System.out.println("=== TOMBOL CETAK NOTA DIKLIK ===");
    
    try {
        // Validasi data terlebih dahulu
        if (txtPenyewaan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kode penyewaan tidak boleh kosong!");
            return;
        }
        
        if (txtCust.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama pelanggan tidak boleh kosong!");
            return;
        }
        
        // Buat objek header dan details dari data form
        System.out.println("Membuat header untuk cetak...");
        PenyewaanHeader header = buatHeaderUntukCetak();
        System.out.println("Header berhasil dibuat: " + header.getKodePenyewaan());
        
        System.out.println("Membuat detail untuk cetak...");
        List<PenyewaanDetail> details = buatDetailUntukCetak();
        System.out.println("Detail berhasil dibuat, jumlah: " + details.size());
        
        // Panggil method cetakNotaJasper
        System.out.println("Memanggil cetakNotaJasper...");
        cetakNotaJasper(header, details);
        
    } catch (Exception e) {
        System.err.println("Error saat mencetak nota: " + e.getMessage());
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal mencetak nota: " + e.getMessage());
    }
    
    System.out.println("=== SELESAI TOMBOL CETAK NOTA ===");
    }//GEN-LAST:event_btnCetakNotaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelMain;
    private javax.swing.JPanel addPanel;
    private javax.swing.JButton btnCariDriv;
    private javax.swing.JButton btnCariMob;
    private javax.swing.JButton btnCariPel;
    private javax.swing.JButton btnCetakNota;
    private javax.swing.JButton btnKembalikedepan;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah1;
    private javax.swing.JButton btnTambahSewa;
    private javax.swing.JComboBox<String> cmbxStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblAlamat1;
    private javax.swing.JLabel lblCust;
    private javax.swing.JLabel lblCust1;
    private javax.swing.JLabel lblDriver;
    private javax.swing.JLabel lblHargaSewa;
    private javax.swing.JLabel lblKTP;
    private javax.swing.JLabel lblKTP1;
    private javax.swing.JLabel lblKet;
    private javax.swing.JLabel lblNoHPCust;
    private javax.swing.JLabel lblNoHPCust1;
    private javax.swing.JLabel lblNoHPDriv;
    private javax.swing.JLabel lblPembayaran;
    private javax.swing.JLabel lblPembayaran1;
    private javax.swing.JLabel lblPembayaran2;
    private javax.swing.JLabel lblPembayaran3;
    private javax.swing.JLabel lblPenyewaan;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTanggalKembali;
    private javax.swing.JLabel lblTanggalSewa;
    private javax.swing.JLabel lblTotalHari;
    private javax.swing.JLabel lblnfoDriv;
    private model.Pagination pagination1;
    private javax.swing.JTextField txtAlamat;
    private javax.swing.JTextField txtCust;
    private javax.swing.JTextField txtHPCust;
    private javax.swing.JTextField txtHargaSewa;
    private javax.swing.JTextField txtKTP;
    private javax.swing.JTextField txtKet;
    private javax.swing.JTextField txtMerkMobil;
    private javax.swing.JTextField txtMobil;
    private javax.swing.JTextField txtNamaDriver;
    private javax.swing.JTextField txtNoHPDriv;
    private javax.swing.JTextField txtNoPol;
    private javax.swing.JTextField txtPencarianData1;
    private javax.swing.JTextField txtPenyewaan;
    private javax.swing.JTextField txtTanggalKembali;
    private javax.swing.JTextField txtTanggalSewa;
    private javax.swing.JTextField txtTotalHari;
    // End of variables declaration//GEN-END:variables
    public void cetakNotaJasper(PenyewaanHeader header, List<PenyewaanDetail> details) {
        try {
            System.out.println("Memulai proses cetak nota dengan JasperReports (80mm)...");

            // Load template nota
            String reportPath = "src/report/NotaPenyewaan.jrxml";
            File reportFile = new File(reportPath);

            if (!reportFile.exists()) {
                System.err.println("File template tidak ditemukan: " + reportFile.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "File template tidak ditemukan!");
                return;
            }

            System.out.println("Kompilasi template...");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);

            // Siapkan parameter untuk nota
            Map<String, Object> parameters = new HashMap<>();

            // Parameter header
            parameters.put("kodePenyewaan", header.getKodePenyewaan());
            parameters.put("tanggalSewa", formatDate(header.getTanggalSewa()));
            parameters.put("tanggalKembali", formatDate(header.getTanggalKembali()));
            parameters.put("totalHari", header.getTotalHari());

            // Parameter pelanggan
            parameters.put("namaPelanggan", header.getNamaPelanggan());
            parameters.put("noKTP", header.getNoKtp());
            parameters.put("noHPPelanggan", header.getNoHp());
            parameters.put("alamatPelanggan", header.getAlamat());

            // Parameter driver
            parameters.put("namaDriver", header.isDenganDriver() ? header.getNamaDriver() : "Tanpa Driver");
            parameters.put("noHPDriver", header.isDenganDriver() ? header.getNoHpDriver() : "-");

            // Parameter detail mobil (ambil dari detail pertama)
            if (!details.isEmpty()) {
                PenyewaanDetail firstDetail = details.get(0);
                parameters.put("mobilType", firstDetail.getMobil());
                parameters.put("noPolisi", firstDetail.getNoPolisi());
                parameters.put("merkType", firstDetail.getMerkType());
                parameters.put("hargaPerHari", firstDetail.getHargaPerHari().doubleValue());
                parameters.put("subtotal", firstDetail.getSubtotal().doubleValue());
            } else {
                parameters.put("mobilType", "");
                parameters.put("noPolisi", "");
                parameters.put("merkType", "");
                parameters.put("hargaPerHari", 0.0);
                parameters.put("subtotal", 0.0);
            }

            // Parameter pembayaran
            parameters.put("totalBiaya", header.getTotalBiaya().doubleValue());
            parameters.put("uangMuka", header.getUangMuka().doubleValue());
            parameters.put("sisaBayar", header.getSisaBayar().doubleValue());

            // Parameter keterangan
            parameters.put("keterangan", header.getKeterangan() != null ? header.getKeterangan() : "");

            System.out.println("Generate report...");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Tampilkan dialog pilihan
            String[] options = {"Preview", "Cetak ke Thermal", "Cetak Biasa", "Batal"};
            int choice = JOptionPane.showOptionDialog(
                this,
                "Pilih metode cetak:",
                "Cetak Nota",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == 0) {
                // Preview
                System.out.println("Menampilkan preview...");
                JasperViewer.viewReport(jasperPrint, false);
            } else if (choice == 1) {
                // Cetak ke thermal printer
                System.out.println("Mencetak ke thermal printer...");
                cetakKeThermalPrinter(jasperPrint);
            } else if (choice == 2) {
                // Cetak biasa
                System.out.println("Mencetak biasa...");
                JasperPrintManager.printReport(jasperPrint, true);
                JOptionPane.showMessageDialog(this, "Nota berhasil dicetak!");
            }

        } catch (Exception e) {
            System.err.println("Error mencetak nota JasperReports: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mencetak nota: " + e.getMessage());
        }
    }
    
    private void cetakKeThermalPrinter(JasperPrint jasperPrint) {
        try {
            // Cari thermal printer
            PrintService thermalPrinter = findThermalPrinter();

            if (thermalPrinter != null) {
                System.out.println("Mencetak ke thermal printer: " + thermalPrinter.getName());

                // Set print attributes untuk thermal printer
                PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();
                printAttributes.add(MediaSizeName.ISO_A7); // Ukuran mendekati 80mm

                // Print tanpa dialog
                JasperPrintManager.printReport(jasperPrint, false);
                JOptionPane.showMessageDialog(this, "Nota berhasil dicetak ke thermal printer!");
            } else {
                System.out.println("Thermal printer tidak ditemukan, menggunakan dialog printer");
                JasperPrintManager.printReport(jasperPrint, true);
            }
        } catch (Exception e) {
            System.err.println("Error printing to thermal: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error cetak thermal: " + e.getMessage());
        }
    }

    private PrintService findThermalPrinter() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        for (PrintService printer : printServices) {
            String printerName = printer.getName().toLowerCase();

            if (printerName.contains("thermal") || 
                printerName.contains("receipt") || 
                printerName.contains("pos") ||
                printerName.contains("80mm") ||
                printerName.contains("58mm") ||
                printerName.contains("epson") ||
                printerName.contains("tm-") ||
                printerName.contains("xprinter")) {
                return printer;
            }
        }

        return null;
    }
   
    private PenyewaanHeader buatHeaderUntukCetak() throws ParseException {
        PenyewaanHeader header = new PenyewaanHeader();

        header.setKodePenyewaan(txtPenyewaan.getText());
        header.setNamaPelanggan(txtCust.getText());
        header.setNoKtp(txtKTP.getText());
        header.setNoHp(txtHPCust.getText());
        header.setAlamat(txtAlamat.getText());
        header.setTanggalSewa(dateFormat.parse(txtTanggalSewa.getText()));
        header.setTanggalKembali(dateFormat.parse(txtTanggalKembali.getText()));
        header.setTotalHari(Integer.parseInt(txtTotalHari.getText()));
        header.setTotalBiaya(new BigDecimal(parseDouble(jTextField1.getText())));
        header.setUangMuka(new BigDecimal(parseDouble(jTextField2.getText())));
        header.setSisaBayar(new BigDecimal(parseFromCurrency(jTextField3.getText())));
        header.setStatus(cmbxStatus.getSelectedItem().toString());
        header.setKeterangan(txtKet.getText());

        // Ganti kondisi checkbox dengan pengecekan field driver
        if (!txtNamaDriver.getText().trim().isEmpty() && selectedDriver != null) {
            header.setDenganDriver(true);
            header.setNamaDriver(txtNamaDriver.getText());
            header.setNoHpDriver(txtNoHPDriv.getText());
        } else {
            header.setDenganDriver(false);
        }

        return header;
    }

    // Method untuk buat detail dari tabel
    private List<PenyewaanDetail> buatDetailUntukCetak() {
        List<PenyewaanDetail> details = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            String mobil = model.getValueAt(i, 1).toString();
            String noPolisi = model.getValueAt(i, 2).toString();
            String merkType = model.getValueAt(i, 3).toString();

            // Parse harga dari format currency
            String hargaStr = model.getValueAt(i, 4).toString();
            BigDecimal hargaPerHari = new BigDecimal(parseFromCurrency(hargaStr));

            int jumlahHari = Integer.parseInt(model.getValueAt(i, 5).toString());

            String subtotalStr = model.getValueAt(i, 6).toString();
            BigDecimal subtotal = new BigDecimal(parseFromCurrency(subtotalStr));

            PenyewaanDetail detail = new PenyewaanDetail(
                mobil, noPolisi, merkType, hargaPerHari, jumlahHari, subtotal);

            details.add(detail);
        }

        return details;
    }

    private static String formatDate(Date date) {
       if (date == null) return "";
       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
       return sdf.format(date);
   }

    private double parseFromCurrency(String currencyStr) {
        if (currencyStr == null || currencyStr.trim().isEmpty()) {
            return 0.0;
        }

        try {

            String cleaned = currencyStr.replace("Rp", "").trim();

            if (cleaned.contains(",")) {

                String[] parts = cleaned.split(",");
                String integerPart = parts[0].replace(".", ""); // Hapus titik pemisah ribuan
                String decimalPart = parts.length > 1 ? parts[1] : "00";
                cleaned = integerPart + "." + decimalPart;
            } else {

                cleaned = cleaned.replace(".", "");
            }

            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing currency: " + currencyStr);
            return 0.0;
        }
    }

    private void printNota(String notaContent) {
        try {
            // Buat JTextArea untuk print
            javax.swing.JTextArea textArea = new javax.swing.JTextArea(notaContent);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

            // Print
            boolean complete = textArea.print();

            if (complete) {
                JOptionPane.showMessageDialog(this, "Nota berhasil dicetak!");
            } else {
                JOptionPane.showMessageDialog(this, "Pencetakan dibatalkan.");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak nota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveNotaToFile(String notaContent) {
        try {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Simpan Nota");
            fileChooser.setSelectedFile(new java.io.File("Nota_" + txtPenyewaan.getText() + ".txt"));

            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();

                try (java.io.FileWriter writer = new java.io.FileWriter(fileToSave)) {
                    writer.write(notaContent);
                    JOptionPane.showMessageDialog(this, "Nota berhasil disimpan ke: " + fileToSave.getAbsolutePath());
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan nota: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadData() {
        System.out.println("jTable2 object: " + jTable2);
        System.out.println("jTable2 model: " + jTable2.getModel());
        System.out.println("=== DEBUG loadData() dimulai ===");

        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT * FROM penyewaan ORDER BY id DESC";
            System.out.println("SQL Query: " + sql);

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            System.out.println("Jumlah kolom di model: " + model.getColumnCount());
            System.out.println("Nama kolom: ");
            for (int i = 0; i < model.getColumnCount(); i++) {
                System.out.println("  Kolom " + i + ": " + model.getColumnName(i));
            }

            model.setRowCount(0);

            int no = 1;
            int rowCount = 0;
            while (rs.next()) {
                String kodePenyewaan = rs.getString("kode_penyewaan");
                String namaPelanggan = rs.getString("nama_pelanggan");

                System.out.println("Row " + rowCount + ":");
                System.out.println("  Kode: " + kodePenyewaan);
                System.out.println("  Nama Pelanggan: " + namaPelanggan);
                System.out.println("  Nama Pelanggan null? " + (namaPelanggan == null));
                System.out.println("  Nama Pelanggan empty? " + (namaPelanggan != null && namaPelanggan.trim().isEmpty()));

                double totalBiaya = rs.getDouble("total_biaya");
                if (totalBiaya > 0 && totalBiaya < 10000) {
                    totalBiaya *= 1000;
                }

                Object[] row = {
                    no++,
                    kodePenyewaan,
                    namaPelanggan, // Langsung ambil dari kolom nama_pelanggan
                    "Mobil",
                    rs.getDate("tanggal_sewa"),
                    rs.getDate("tanggal_kembali_rencana"),
                    currencyFormat.format(totalBiaya),
                    rs.getString("status")
                };

                System.out.println("  Row data yang akan ditambahkan:");
                for (int i = 0; i < row.length; i++) {
                    System.out.println("    Index " + i + ": " + row[i]);
                }

                model.addRow(row);
                rowCount++;
            }

            System.out.println("Total rows ditambahkan: " + rowCount);
            System.out.println("Rows di model setelah load: " + model.getRowCount());

        } catch (SQLException e) {
            System.out.println("ERROR di loadData(): " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=== DEBUG loadData() selesai ===");
    }
    
    private void searchData(String keyword) {
        if (keyword.trim().isEmpty()) {
            loadData();
            return;
        }

        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT * FROM penyewaan WHERE kode_penyewaan LIKE ? OR nama_pelanggan LIKE ? ORDER BY id DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            int no = 1;
            while (rs.next()) {
                Object[] row = {
                    no++,
                    rs.getString("kode_penyewaan"),
                    rs.getString("nama_pelanggan"), // Langsung ambil dari kolom nama_pelanggan
                    "Mobil",
                    rs.getDate("tanggal_sewa"),
                    rs.getDate("tanggal_kembali_rencana"),
                    currencyFormat.format(rs.getDouble("total_biaya")),
                    rs.getString("status")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Utility methods
    private void toggleDriverFields() {
   
        boolean enabled = true;
        txtNamaDriver.setEnabled(enabled);
        txtNoHPDriv.setEnabled(enabled);
        btnCariDriv.setEnabled(enabled);

        if (!enabled) {
            txtNamaDriver.setText("");
            txtNoHPDriv.setText("");
            selectedDriver = null;
        }
    }

    private void clearInputFields() {
        
        JTextField[] fields = {
            txtPenyewaan, txtTanggalSewa, txtTanggalKembali, txtTotalHari,
            txtCust, txtKTP, txtHPCust, txtAlamat,
            txtNamaDriver, txtNoHPDriv,
            txtMobil, txtNoPol, txtMerkMobil, txtHargaSewa,
            txtKet, jTextField2 // Jangan clear jTextField1 dan jTextField3 karena auto-calculated
        };

        for (JTextField field : fields) {
            field.setText("");
        }

        // Clear calculated fields
        jTextField1.setText("0");
        jTextField3.setText(currencyFormat.format(0));

        // Clear table
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // Reset selections
        selectedPelanggan = null;
        selectedMobil = null;
        selectedDriver = null;

        toggleDriverFields();
    }
    
    private void generateKodePenyewaan() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String kode = "SW-" + dateStr + "-" + String.format("%03d", (int)(Math.random() * 999) + 1);
        txtPenyewaan.setText(kode);
    }

    private void updateStatusMobil(Connection conn, int mobilId, String status) throws SQLException {
        String sql = "UPDATE mobil SET status = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, mobilId);
        ps.executeUpdate();
    }
    
    private void updateStatusMobilBatch(Connection conn, String status) throws SQLException {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        String sql = "UPDATE mobil SET status = ? WHERE no_polisi = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        System.out.println("=== Updating Status Mobil ===");

        for (int i = 0; i < model.getRowCount(); i++) {
            // PERBAIKAN: Pastikan kolom no_polisi benar
            String noPolisi = model.getValueAt(i, 2).toString();

            ps.setString(1, status.trim());
            ps.setString(2, noPolisi.trim());

            int rowsAffected = ps.executeUpdate();
            System.out.println("Updated: " + noPolisi + " -> " + status + " (Rows affected: " + rowsAffected + ")");
        }

        ps.close();
        System.out.println("=== Update Status Complete ===");

        verifyStatusUpdate(conn, status);
    }

    // Method untuk verifikasi update status
    private void verifyStatusUpdate(Connection conn, String expectedStatus) throws SQLException {
        String sql = "SELECT no_polisi, status FROM mobil WHERE status = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, expectedStatus);
        ResultSet rs = ps.executeQuery();

        System.out.println("=== Verifikasi Status " + expectedStatus + " ===");
        int count = 0;
        while (rs.next()) {
            System.out.println(rs.getString("no_polisi") + ": " + rs.getString("status"));
            count++;
        }
        System.out.println("Total mobil dengan status " + expectedStatus + ": " + count);
        System.out.println("=====================================");
    }
    
    private void debugMobilStatusAfterUpdate(Connection conn) throws SQLException {
        String sql = "SELECT no_polisi, status FROM mobil";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        System.out.println("=== Status Mobil Setelah Update ===");
        while (rs.next()) {
            System.out.println(rs.getString("no_polisi") + ": " + rs.getString("status"));
        }
        System.out.println("==================================");
    }

    // Method untuk mendapatkan mobil ID dari detail penyewaan
    private List<Integer> getMobilIdsFromDetail(DefaultTableModel model) {
        List<Integer> mobilIds = new ArrayList<>();

        try (Connection conn = koneksi.getConnection()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                String noPolisi = model.getValueAt(i, 2).toString(); // No.Polisi ada di kolom 2

                String sql = "SELECT id FROM mobil WHERE no_polisi = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, noPolisi);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    mobilIds.add(rs.getInt("id"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting mobil IDs: " + e.getMessage());
        }

        return mobilIds;
    }
}