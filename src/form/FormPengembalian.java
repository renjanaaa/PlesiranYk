package form;

import com.formdev.flatlaf.FlatClientProperties;
import dialog.DialogCariPenyewaan;
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
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import koneksi.koneksi;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FormPengembalian extends JPanel {
    private JLabel lblInfoPenyewaan;
    private JButton btnGantiPenyewaan;
    private int selectedPenyewaanId;
    private String selectedKodePenyewaan;

    private NumberFormat currencyFormat;
    private SimpleDateFormat dateFormat;

    private CardLayout cardLayout;
    private boolean isListView = true;

    private static final double DENDA_PER_JAM = 50000;
    
    private static final String[] KONDISI_OPTIONS = {
        "Baik", "Rusak Ringan", "Rusak Sedang", "Rusak Berat"
    };
    
    public FormPengembalian() {
        initializeFormatters();
        initComponents(); // Auto-generated method
        setupCustomLayout();
        setupStyling();
        setupTableStyling(); // Pastikan ini dipanggil
        setupEventHandlers();
        setupInitialState();
        setupAlternativeTableAction(); // Dan ini juga
        loadData();

        System.out.println("FormPengembalian initialization completed");
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
        tablePanel.add(pagination2, BorderLayout.SOUTH);
        
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

        // Header dengan info penyewaan
        JPanel headerPanel = createHeaderWithInfo();
        addPanel.add(headerPanel, BorderLayout.NORTH);

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
        
        // Left column: Data Penyewaan
        JPanel leftColumn = new JPanel(new BorderLayout(0, 10));
        leftColumn.add(jPanel7, BorderLayout.CENTER);  // Data Penyewaan
        
        // Right column: Proses Pengembalian
        JPanel rightColumn = new JPanel(new BorderLayout(0, 10));
        rightColumn.add(jPanel9, BorderLayout.CENTER); // Proses Pengembalian
        
        formPanel.add(leftColumn, BorderLayout.WEST);
        formPanel.add(rightColumn, BorderLayout.CENTER);
        
        return formPanel;
    }
    
    private JPanel createTableDetailPanel() {
        JPanel tableDetailPanel = new JPanel(new BorderLayout(0, 10));
        tableDetailPanel.add(jScrollPane1, BorderLayout.CENTER);
        
        // Bottom panel with payment and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        
        JPanel paymentAndButtonPanel = new JPanel(new BorderLayout(10, 0));
        paymentAndButtonPanel.add(jPanel8, BorderLayout.CENTER);
        paymentAndButtonPanel.add(createButtonPanel(), BorderLayout.EAST);
        
        bottomPanel.add(paymentAndButtonPanel, BorderLayout.CENTER);
        tableDetailPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        return tableDetailPanel;
    }

    private JPanel createHeaderWithInfo() {
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Header asli
        headerPanel.add(jPanel3, BorderLayout.NORTH);

        // Info penyewaan yang sedang diproses
        lblInfoPenyewaan = new JLabel();
        lblInfoPenyewaan.setFont(new Font("Poppins", Font.BOLD, 12));
        lblInfoPenyewaan.setForeground(new Color(52, 152, 219));
        lblInfoPenyewaan.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        lblInfoPenyewaan.setOpaque(true);
        lblInfoPenyewaan.setBackground(new Color(240, 248, 255));

        headerPanel.add(lblInfoPenyewaan, BorderLayout.SOUTH);

        return headerPanel;
    }

    // Update info penyewaan ketika data dimuat
    private void updateInfoPenyewaan() {
        if (lblInfoPenyewaan != null) {
            String info = String.format("Memproses Pengembalian: %s - %s", 
                selectedKodePenyewaan, 
                txtPelanggan.getText());
            lblInfoPenyewaan.setText(info);
        }
    }
    // Panggil updateInfoPenyewaan() di akhir fillFieldsFromResultSet()

    
    private void configureButton(JButton button, Dimension size) {
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    private void setupStyling() {
        setBackground(new Color(240, 242, 247));
        setupTextFieldStyling();
        setupButtonStyling();
        setupPanelStyling();
        setupTableStyling();
    }
    
    private void setupTextFieldStyling() {
        // Perbaiki style string - hapus minimumHeight yang tidak dikenali
        String textFieldStyle = "arc:8;borderWidth:1;borderColor:#bdc3c7;focusedBorderColor:#3498db;background:#ffffff;margin:5,10,5,10";

        // Apply to all text fields
        JTextField[] textFields = {
            txtKodePenyewaan, txtPelanggan, txtTanggalSewa, txtJatuhTempo, txtStatus,
            txtKembaliRill, txtKeterlambatan, txtDenda,
            txtSisa, txtTotalDenda, txtPelunasan, txtPembayaran, txtKembalian,
            txtPencarianData1
        };

        for (JTextField field : textFields) {
            if (field != null) {
                field.putClientProperty(FlatClientProperties.STYLE, textFieldStyle);
                // Set preferred height secara manual
                field.setPreferredSize(new Dimension(field.getPreferredSize().width, 35));
            }
        }

        // Set read-only fields
        setReadOnlyFields();
    }
    
    private void setReadOnlyFields() {
        JTextField[] readOnlyFields = {
            txtKodePenyewaan, txtPelanggan, txtTanggalSewa, txtJatuhTempo, txtStatus,
            txtKeterlambatan, txtDenda, txtSisa, txtTotalDenda, txtPelunasan, txtKembalian
        };
        
        for (JTextField field : readOnlyFields) {
            if (field != null) {
                field.setEditable(false);
            }
        }
    }
    
    private void setupButtonStyling() {
        // Perbaiki style string - hapus minimumHeight
        String primaryStyle = "borderWidth:0;focusWidth:0;arc:8;background:#3498db;hoverBackground:#2980b9;pressedBackground:#21618c;foreground:#ffffff;font:bold";

        // Primary buttons
        btnTambah1.putClientProperty(FlatClientProperties.STYLE, primaryStyle);
        btnSimpan.putClientProperty(FlatClientProperties.STYLE, primaryStyle);
        btnKembalikedepan.putClientProperty(FlatClientProperties.STYLE, primaryStyle);

        // Set preferred height secara manual
        Dimension buttonSize = new Dimension(100, 35);
        btnTambah1.setPreferredSize(buttonSize);
        btnSimpan.setPreferredSize(buttonSize);
        btnKembalikedepan.setPreferredSize(buttonSize);
    }
    
    private void setupPanelStyling() {
        String panelStyle = "background:#3498db;arc:10;border:10,15,10,15";
        
        JPanel[] panels = {jPanel7, jPanel8, jPanel9};
        for (JPanel panel : panels) {
            panel.putClientProperty(FlatClientProperties.STYLE, panelStyle);
        }
    }
    
    private void setupTableStyling() {

        String[] columnNames = {"No", "Kode Penyewaan", "Pelanggan", "Tanggal Sewa", "Tanggal Jatuh Tempo", "Status", "Aksi"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        jTable2.setModel(model);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        configureTable(jTable2);

        String[] detailColumns = {"No", "Mobil", "No.Polisi", "Kondisi", "Denda Kerusakan", "Keterangan"};
        DefaultTableModel detailModel = new DefaultTableModel(detailColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {

                return column == 3 || column == 4 || column == 5;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) return String.class; // Kondisi
                if (columnIndex == 4) return String.class; // Denda
                return String.class;
            }
        };
        jTable1.setModel(detailModel);

        JComboBox<String> kondisiCombo = new JComboBox<>(KONDISI_OPTIONS);
        jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(kondisiCombo));

        // Setup editor untuk denda kerusakan dengan format currency
        JTextField dendaField = new JTextField();
        dendaField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String text = dendaField.getText().trim();
                if (!text.isEmpty()) {
                    try {
                        double value = parseDoubleFromCurrency(text);
                        dendaField.setText(formatCurrency(value));
                        // Hitung ulang total setelah perubahan
                        hitungTotalPembayaran();
                    } catch (Exception ex) {
                        dendaField.setText("0");
                    }
                }
            }
        });
        jTable1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(dendaField));

        configureTable(jTable1);
        setTableColumnWidths();

        // Tambahkan listener untuk perubahan kondisi
        detailModel.addTableModelListener(e -> {
            if (e.getColumn() == 3) { // Kolom kondisi berubah
                int row = e.getFirstRow();
                String kondisi = (String) detailModel.getValueAt(row, 3);
                
                // Auto-set denda berdasarkan kondisi
                double dendaOtomatis = getDendaByKondisi(kondisi);
                detailModel.setValueAt(formatCurrency(dendaOtomatis), row, 4);
                
                // Hitung ulang total
                hitungTotalPembayaran();
            } else if (e.getColumn() == 4) { // Kolom denda berubah
                hitungTotalPembayaran();
            }
        });

        System.out.println("Enhanced table styling setup completed");
    }
    
    private double getDendaByKondisi(String kondisi) {
        switch (kondisi) {
            case "Baik": return 0;
            case "Rusak Ringan": return 100000; // Rp 100.000
            case "Rusak Sedang": return 300000; // Rp 300.000
            case "Rusak Berat": return 500000;  // Rp 500.000
            default: return 0;
        }
    }

    
    private void addInstructionLabel() {
    JLabel lblInstruksi = new JLabel("Pilih penyewaan yang akan diproses, lalu klik tombol 'Proses' atau double-click pada baris");
    lblInstruksi.setFont(new Font("Poppins", Font.ITALIC, 11));
    lblInstruksi.setForeground(new Color(100, 100, 100));
    }

    private void setTableColumnWidths() {
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);   // No
            jTable2.getColumnModel().getColumn(1).setPreferredWidth(120);  // Kode
            jTable2.getColumnModel().getColumn(2).setPreferredWidth(150);  // Pelanggan
            jTable2.getColumnModel().getColumn(3).setPreferredWidth(100);  // Tgl Sewa
            jTable2.getColumnModel().getColumn(4).setPreferredWidth(120);  // Jatuh Tempo
            jTable2.getColumnModel().getColumn(5).setPreferredWidth(80);   // Status
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(80);   // Aksi
        }
    }

    private void setupTableActions() {

        for (MouseListener ml : jTable2.getMouseListeners()) {
            jTable2.removeMouseListener(ml);
        }

        // Tambahkan mouse listener sederhana
        jTable2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = jTable2.getSelectedRow();
                int col = jTable2.getSelectedColumn();

                System.out.println("Table clicked - Row: " + row + ", Col: " + col);

                // Jika klik di kolom "Aksi" (kolom 6) atau double click di mana saja
                if ((col == 6) || (e.getClickCount() == 2)) {
                    if (row >= 0 && row < jTable2.getRowCount()) {
                        // Cek apakah bukan baris kosong
                        Object kodeObj = jTable2.getValueAt(row, 1);
                        if (kodeObj != null && !kodeObj.toString().contains("Tidak ada")) {
                            System.out.println("Processing row: " + row);
                            prosesPenyewaanFromTable(row);
                        }
                    }
                }
            }
        });

        System.out.println("Table actions setup completed");
    }
    
    private void setupAlternativeTableAction() {
        System.out.println("=== SETUP ALTERNATIVE TABLE ACTION ===");

        // Perbaiki text button
        btnTambah1.setText("Proses");
        btnTambah1.setPreferredSize(new Dimension(120, 35));

        // Hapus semua event handler lama dari button
        for (ActionListener al : btnTambah1.getActionListeners()) {
            btnTambah1.removeActionListener(al);
        }

        // Hapus semua mouse listener lama dari table
        for (MouseListener ml : jTable2.getMouseListeners()) {
            jTable2.removeMouseListener(ml);
        }

        // Tambahkan event handler baru untuk button
        btnTambah1.addActionListener(e -> {
            System.out.println("Button Proses clicked");
            prosesLangsungDariTabel();
        });

        // Tambahkan mouse listener untuk table
        jTable2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Table clicked - Row: " + jTable2.getSelectedRow() + 
                                 ", Click count: " + e.getClickCount());

                if (e.getClickCount() == 2) {
                    int row = jTable2.getSelectedRow();
                    if (row >= 0) {
                        System.out.println("Double click detected on row: " + row);
                        prosesRowTerpilih(row);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Mouse pressed on table");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse released on table");
            }
        });

        // Tambahkan selection listener untuk debugging
        jTable2.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = jTable2.getSelectedRow();
                System.out.println("Row selected: " + selectedRow);

                if (selectedRow >= 0) {
                    Object kode = jTable2.getValueAt(selectedRow, 1);
                    System.out.println("Selected kode: " + kode);
                }
            }
        });

        System.out.println("Alternative table action setup completed");
    }

    private void prosesLangsungDariTabel() {
        System.out.println("=== PROSES LANGSUNG DARI TABEL ===");

        int selectedRow = jTable2.getSelectedRow();
        System.out.println("Selected row: " + selectedRow);
        System.out.println("Total rows: " + jTable2.getRowCount());

        if (selectedRow == -1) {
            // Jika tidak ada yang diselect, coba ambil row pertama
            if (jTable2.getRowCount() > 0) {
                jTable2.setRowSelectionInterval(0, 0);
                selectedRow = 0;
                System.out.println("Auto-selected first row: " + selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Tidak ada data penyewaan untuk diproses!", 
                    "Tidak Ada Data", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        prosesRowTerpilih(selectedRow);
    }

    private void prosesRowTerpilih(int row) {
        System.out.println("=== PROSES ROW TERPILIH ===");
        System.out.println("Processing row: " + row);

        try {
            // Validasi row
            if (row < 0 || row >= jTable2.getRowCount()) {
                System.err.println("Invalid row: " + row);
                JOptionPane.showMessageDialog(this, "Baris tidak valid!");
                return;
            }

            // Ambil data dari tabel
            Object kodeObj = jTable2.getValueAt(row, 1);
            Object pelangganObj = jTable2.getValueAt(row, 2);

            System.out.println("Kode object: " + kodeObj);
            System.out.println("Pelanggan object: " + pelangganObj);

            if (kodeObj == null || kodeObj.toString().trim().isEmpty() || 
                kodeObj.toString().contains("Tidak ada")) {
                JOptionPane.showMessageDialog(this, "Pilih penyewaan yang valid!");
                return;
            }

            String kodePenyewaan = kodeObj.toString().trim();
            String pelanggan = pelangganObj != null ? pelangganObj.toString() : "Tidak diketahui";

            System.out.println("Kode: " + kodePenyewaan);
            System.out.println("Pelanggan: " + pelanggan);

            // Konfirmasi
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Proses pengembalian untuk:\n\n" +
                "Kode Penyewaan: " + kodePenyewaan + "\n" +
                "Pelanggan: " + pelanggan + "\n\n" +
                "Lanjutkan proses pengembalian?", 
                "Konfirmasi Proses Pengembalian", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                prosesPenyewaanLangsung(kodePenyewaan);
            }

        } catch (Exception e) {
            System.err.println("Error processing row: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error memproses data: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prosesPenyewaanLangsung(String kodePenyewaan) {
        try (Connection conn = koneksi.getConnection()) {
            System.out.println("=== PROSES PENYEWAAN LANGSUNG ===");
            System.out.println("Kode: " + kodePenyewaan);

            // Query untuk mendapatkan data lengkap
            String sql = """
                SELECT p.*, pel.nama as nama_pelanggan_join 
                FROM penyewaan p 
                LEFT JOIN pelanggan pel ON p.pelanggan_id = pel.id 
                WHERE p.kode_penyewaan = ? AND p.status = 'aktif'
                """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodePenyewaan);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                // PENTING: Set selectedPenyewaanId dan selectedKodePenyewaan
                selectedPenyewaanId = rs.getInt("id");
                selectedKodePenyewaan = kodePenyewaan;

                System.out.println("Data ditemukan - ID: " + selectedPenyewaanId);
                System.out.println("Kode: " + selectedKodePenyewaan);

                showInputView();
                fillFieldsFromResultSet(rs);
                loadMobilDetail();

                SwingUtilities.invokeLater(() -> {
                    txtKembaliRill.requestFocus();
                    txtKembaliRill.selectAll();
                });

                JOptionPane.showMessageDialog(this, 
                    "Data penyewaan berhasil dimuat!\n\n" +
                    "Kode: " + selectedKodePenyewaan + "\n" +
                    "ID: " + selectedPenyewaanId + "\n\n" +
                    "Silakan isi:\n" +
                    "1. Tanggal kembali aktual\n" +
                    "2. Kondisi mobil (jika ada kerusakan)\n" +
                    "3. Jumlah pembayaran", 
                    "Berhasil", 
                    JOptionPane.INFORMATION_MESSAGE);

            } else {
                System.err.println("Data tidak ditemukan untuk kode: " + kodePenyewaan);
                JOptionPane.showMessageDialog(this, 
                    "Data penyewaan tidak ditemukan atau sudah tidak aktif!", 
                    "Data Tidak Ditemukan", 
                    JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error database: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void fillFieldsFromResultSet(ResultSet rs) throws SQLException {
        System.out.println("=== FILLING FIELDS FROM RESULTSET ===");

        // Isi field satu per satu
        txtKodePenyewaan.setText(rs.getString("kode_penyewaan"));

        // Handle nama_pelanggan yang NULL
        String namaPelanggan = rs.getString("nama_pelanggan");
        if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
            // Jika nama_pelanggan NULL, cari dari tabel pelanggan
            int pelangganId = rs.getInt("pelanggan_id");
            namaPelanggan = getNamaPelangganById(pelangganId);
        }
        txtPelanggan.setText(namaPelanggan);

        txtTanggalSewa.setText(rs.getString("tanggal_sewa"));
        txtJatuhTempo.setText(rs.getString("tanggal_kembali_rencana"));
        txtStatus.setText(rs.getString("status"));

        // Isi field pembayaran
        double sisaBayar = rs.getDouble("sisa_bayar");
        txtSisa.setText(currencyFormat.format(sisaBayar));

        // Set tanggal kembali aktual ke hari ini
        String tanggalHariIni = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        txtKembaliRill.setText(tanggalHariIni);

        // Hitung keterlambatan
        hitungKeterlambatan();

        System.out.println("Fields berhasil diisi!");
    }
    

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

        Dimension buttonSize = new Dimension(120, 35);

        // Button Ganti Penyewaan (baru)
        btnGantiPenyewaan = new JButton("Ganti Penyewaan");
        configureButton(btnGantiPenyewaan, buttonSize);
        btnGantiPenyewaan.putClientProperty(FlatClientProperties.STYLE, 
            "borderWidth:1;borderColor:#e67e22;focusWidth:0;arc:8;background:#f39c12;hoverBackground:#e67e22;pressedBackground:#d68910;foreground:#ffffff;font:bold");

        // Configure existing buttons
        configureButton(btnSimpan, buttonSize);
        configureButton(btnKembalikedepan, buttonSize);

        // Add buttons with spacing
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(btnGantiPenyewaan);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(btnSimpan);
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(btnKembalikedepan);
        buttonPanel.add(Box.createVerticalGlue());

        return buttonPanel;
    }
    // Setup event handler untuk button ganti penyewaan


    private void gantiPenyewaan() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin mengganti penyewaan?\n" +
            "Data yang sudah diisi akan hilang.", 
            "Konfirmasi Ganti Penyewaan", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            showListView();
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int selectedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = "Proses";
            button.setText(label);
            button.setBackground(new Color(52, 152, 219));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Poppins", Font.BOLD, 11));
            isPushed = true;
            selectedRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Proses penyewaan yang dipilih
                prosesPenyewaanFromTable(selectedRow);
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    private void prosesPenyewaanFromTable(int row) {
        System.out.println("=== PROSES PENYEWAAN DIRECT ===");
        System.out.println("Row selected: " + row);

        try {
            // Ambil kode penyewaan dari tabel
            String kodePenyewaan = jTable2.getValueAt(row, 1).toString();
            System.out.println("Kode dari tabel: " + kodePenyewaan);

            // Query langsung untuk mendapatkan semua data
            try (Connection conn = koneksi.getConnection()) {
                String sql = "SELECT * FROM penyewaan WHERE kode_penyewaan = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, kodePenyewaan);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    // Set ID yang ditemukan
                    selectedPenyewaanId = rs.getInt("id");
                    selectedKodePenyewaan = kodePenyewaan;

                    System.out.println("Data ditemukan - ID: " + selectedPenyewaanId);

                    // Pindah ke input view
                    showInputView();

                    // Isi field langsung dari ResultSet
                    fillFieldsFromResultSet(rs);

                    // Load detail mobil
                    loadMobilDetail();

                    // Set focus
                    SwingUtilities.invokeLater(() -> txtKembaliRill.requestFocus());

                } else {
                    System.err.println("Data tidak ditemukan untuk kode: " + kodePenyewaan);
                    JOptionPane.showMessageDialog(this, "Data tidak ditemukan!");
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // Method helper untuk mendapatkan nama pelanggan
    private String getNamaPelangganById(int pelangganId) {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT nama FROM pelanggan WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pelangganId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nama");
            }
        } catch (SQLException e) {
            System.err.println("Error getting nama pelanggan: " + e.getMessage());
        }
        return "Nama tidak ditemukan";
    }

    private int getPenyewaanIdByKode(String kodePenyewaan) {
        try (Connection conn = koneksi.getConnection()) {
            // Debug: print kode yang dicari
            System.out.println("Mencari penyewaan dengan kode: '" + kodePenyewaan + "'");

            // Gunakan status 'aktif' (huruf kecil) sesuai enum di database
            String sql = "SELECT id, kode_penyewaan, status FROM penyewaan WHERE kode_penyewaan = ? AND status = 'aktif'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodePenyewaan.trim());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                System.out.println("Penyewaan ditemukan dengan ID: " + id + ", Status: " + rs.getString("status"));
                return id;
            } else {
                System.out.println("Penyewaan tidak ditemukan!");

                // Debug: cek semua data penyewaan dengan kode tersebut (tanpa filter status)
                String debugSql = "SELECT id, kode_penyewaan, status FROM penyewaan WHERE kode_penyewaan = ?";
                PreparedStatement debugPs = conn.prepareStatement(debugSql);
                debugPs.setString(1, kodePenyewaan.trim());
                ResultSet debugRs = debugPs.executeQuery();

                if (debugRs.next()) {
                    System.out.println("Penyewaan ditemukan tapi dengan status: '" + debugRs.getString("status") + "'");
                } else {
                    System.out.println("Kode penyewaan '" + kodePenyewaan + "' tidak ada di database");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting penyewaan ID: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    private void configureTable(JTable table) {
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 12));
    }
    
    private void setupEventHandlers() {
        // Navigation events - hapus yang lama dulu
        for (ActionListener al : btnTambah1.getActionListeners()) {
            btnTambah1.removeActionListener(al);
        }
        for (ActionListener al : btnKembalikedepan.getActionListeners()) {
            btnKembalikedepan.removeActionListener(al);
        }

        // Setup yang baru
        setupAlternativeTableAction(); // Untuk btnTambah1
        btnKembalikedepan.addActionListener(e -> showListView());

        // Action events
        btnSimpan.addActionListener(e -> simpanPengembalian());

        // Search events
        txtPencarianData1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchData(txtPencarianData1.getText().trim());
            }
        });

        // Calculation events
        setupCalculationEvents();

        System.out.println("Event handlers setup completed");
    }
    
    private void setupCalculationEvents() {
    // Event untuk menghitung keterlambatan saat tanggal kembali aktual diubah
    txtKembaliRill.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            hitungKeterlambatan();
        }
    });
    
    // Event untuk menghitung kembalian saat pembayaran diubah
    txtPembayaran.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            System.out.println("Payment input changed: " + txtPembayaran.getText());
            hitungKembalian();
        }
    });
    
    // Tambahkan focus listener untuk format pembayaran
    txtPembayaran.addFocusListener(new FocusAdapter() {
        @Override
        public void focusLost(FocusEvent e) {
            try {
                String text = txtPembayaran.getText().trim();
                if (!text.isEmpty()) {
                    double value = parseDoubleFromCurrency(text);
                    txtPembayaran.setText(String.valueOf(value));
                }
            } catch (Exception ex) {
                // Ignore formatting errors
            }
        }
    });
}

// Perbaiki method hitungKembalian
private void hitungKembalian() {
    try {
        System.out.println("=== HITUNG KEMBALIAN ===");
        
        String pelunasanText = txtPelunasan.getText();
        String pembayaranText = txtPembayaran.getText();
        
        System.out.println("Total pelunasan text: '" + pelunasanText + "'");
        System.out.println("Pembayaran text: '" + pembayaranText + "'");
        
        double totalPelunasan = parseDoubleFromCurrency(pelunasanText);
        double pembayaran = parseDoubleFromCurrency(pembayaranText);
        
        System.out.println("Total pelunasan parsed: " + totalPelunasan);
        System.out.println("Pembayaran parsed: " + pembayaran);
        
        double kembalian = pembayaran - totalPelunasan;
        System.out.println("Kembalian calculated: " + kembalian);
        
        txtKembalian.setText(formatCurrency(Math.max(0, kembalian)));
        
        System.out.println("=== SELESAI HITUNG KEMBALIAN ===");
        
    } catch (Exception e) {
        System.err.println("Error calculating kembalian: " + e.getMessage());
        e.printStackTrace();
        txtKembalian.setText(formatCurrency(0));
    }
}
    
    private void formatPembayaranInput() {
        try {
            String text = txtPembayaran.getText().replaceAll("[^0-9]", "");
            if (!text.isEmpty()) {
                double value = Double.parseDouble(text);
                // Format tanpa desimal untuk input
                String formatted = String.format("%,.0f", value).replace(",", ".");

                // Set text tanpa trigger event lagi
                txtPembayaran.removeKeyListener(txtPembayaran.getKeyListeners()[0]);
                txtPembayaran.setText(formatted);
                txtPembayaran.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        formatPembayaranInput();
                        hitungKembalian();
                    }
                });
            }
        } catch (Exception e) {
            // Ignore formatting errors
        }
    }

    private void setupInitialState() {
        txtPencarianData1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, 
            "Cari berdasarkan kode penyewaan atau nama pelanggan...");

        // Set placeholder untuk tanggal kembali aktual
        txtKembaliRill.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, 
            "yyyy-mm-dd");

        // Set placeholder untuk pembayaran
        txtPembayaran.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, 
            "Masukkan jumlah pembayaran...");
    }
    
    
    // Navigation methods
    public void showListView() {
        cardLayout.show(this, "LIST");
        isListView = true;

        // Reset selected data ketika kembali ke list
        selectedPenyewaanId = 0;
        selectedKodePenyewaan = "";

        // Clear input fields
        clearInputFields();

        // Reload data untuk refresh
        loadData();

        System.out.println("Switched to list view - state reset");
    }

    // Perbaiki method showInputView
    public void showInputView() {
        cardLayout.show(this, "INPUT");
        isListView = false;

        System.out.println("Switched to input view - ID: " + selectedPenyewaanId);
    }
    // Perbaiki method clearInputFields

    
    // Dialog methods
    private void cariPenyewaan() {
        try {
            DialogCariPenyewaan dialog = new DialogCariPenyewaan((JFrame) SwingUtilities.getWindowAncestor(this));
            dialog.setVisible(true);

            if (dialog.isSelected()) {
                // Ambil data yang dipilih
                selectedPenyewaanId = dialog.getSelectedPenyewaanId();
                selectedKodePenyewaan = dialog.getSelectedKodePenyewaan();

                System.out.println("Data dari dialog - ID: " + selectedPenyewaanId + ", Kode: " + selectedKodePenyewaan);

                if (selectedPenyewaanId <= 0) {
                    JOptionPane.showMessageDialog(this, "ID penyewaan tidak valid!");
                    return;
                }

                // Pindah ke input view
                showInputView();

                // Load data penyewaan langsung dari database
                loadPenyewaanDataDirect(selectedPenyewaanId);
            }
        } catch (Exception e) {
            System.err.println("Error in cariPenyewaan: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void loadPenyewaanDataDirect(int penyewaanId) {
        System.out.println("Loading penyewaan data directly with ID: " + penyewaanId);

        try (Connection conn = koneksi.getConnection()) {
            // Query langsung dengan ID
            String sql = """
                SELECT p.*, pel.nama as nama_pelanggan_join 
                FROM penyewaan p 
                LEFT JOIN pelanggan pel ON p.pelanggan_id = pel.id 
                WHERE p.id = ?
                """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, penyewaanId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Data found for ID: " + penyewaanId);

                // Isi field dari ResultSet
                txtKodePenyewaan.setText(rs.getString("kode_penyewaan"));

                // Gunakan nama dari JOIN jika ada, jika tidak gunakan nama_pelanggan
                String namaPelanggan = rs.getString("nama_pelanggan_join");
                if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
                    namaPelanggan = rs.getString("nama_pelanggan");
                }
                if (namaPelanggan == null || namaPelanggan.trim().isEmpty()) {
                    namaPelanggan = "Nama tidak tersedia";
                }
                txtPelanggan.setText(namaPelanggan);

                txtTanggalSewa.setText(rs.getString("tanggal_sewa"));
                txtJatuhTempo.setText(rs.getString("tanggal_kembali_rencana"));
                txtStatus.setText(rs.getString("status"));

                // Isi field pembayaran
                double sisaBayar = rs.getDouble("sisa_bayar");
                txtSisa.setText(currencyFormat.format(sisaBayar));

                // Set tanggal kembali aktual ke hari ini
                String tanggalHariIni = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                txtKembaliRill.setText(tanggalHariIni);

                // Hitung keterlambatan
                hitungKeterlambatan();

                // Load detail mobil
                loadMobilDetail();

                // Hitung total pembayaran
                hitungTotalPembayaran();

                System.out.println("Data penyewaan berhasil dimuat!");

            } else {
                System.err.println("Data tidak ditemukan untuk ID: " + penyewaanId);
                JOptionPane.showMessageDialog(this, "Data penyewaan tidak ditemukan!");
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }
    
    public void testTableClick() {
        System.out.println("=== TEST TABLE CLICK ===");
        System.out.println("Table enabled: " + jTable2.isEnabled());
        System.out.println("Table focusable: " + jTable2.isFocusable());
        System.out.println("Table visible: " + jTable2.isVisible());
        System.out.println("Table row count: " + jTable2.getRowCount());
        System.out.println("Selection mode: " + jTable2.getSelectionModel().getSelectionMode());

        // Force select first row
        if (jTable2.getRowCount() > 0) {
            jTable2.setRowSelectionInterval(0, 0);
            System.out.println("Forced selection of row 0");
            System.out.println("Selected row after force: " + jTable2.getSelectedRow());
        }
    }
    
    private void loadPenyewaanData() {
        System.out.println("Loading penyewaan data with ID: " + selectedPenyewaanId);

        if (selectedPenyewaanId <= 0) {
            System.err.println("ID penyewaan tidak valid: " + selectedPenyewaanId);
            JOptionPane.showMessageDialog(this, "ID penyewaan tidak valid: " + selectedPenyewaanId);
            return;
        }

        try (Connection conn = koneksi.getConnection()) {
            // Gunakan nama_pelanggan langsung dari tabel penyewaan
            String sql = """
                SELECT p.* 
                FROM penyewaan p 
                WHERE p.id = ?
                """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, selectedPenyewaanId);

            System.out.println("Executing query with ID: " + selectedPenyewaanId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Debug: print data yang diambil
                System.out.println("Data found for ID: " + selectedPenyewaanId);

                // Isi semua field di bagian DATA PENYEWAAN
                txtKodePenyewaan.setText(rs.getString("kode_penyewaan"));
                txtPelanggan.setText(rs.getString("nama_pelanggan")); // Langsung dari tabel penyewaan
                txtTanggalSewa.setText(rs.getString("tanggal_sewa"));
                txtJatuhTempo.setText(rs.getString("tanggal_kembali_rencana"));
                txtStatus.setText(rs.getString("status"));

                // Isi field pembayaran
                double sisaBayar = rs.getDouble("sisa_bayar");
                txtSisa.setText(currencyFormat.format(sisaBayar));

                // Set tanggal kembali aktual ke hari ini sebagai default
                String tanggalHariIni = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                txtKembaliRill.setText(tanggalHariIni);

                // Hitung keterlambatan otomatis
                hitungKeterlambatan();

                // Load detail mobil yang disewa
                loadMobilDetail();

                // Hitung total pembayaran
                hitungTotalPembayaran();

                System.out.println("Data penyewaan berhasil dimuat!");

            } else {
                System.err.println("Tidak ada data penyewaan dengan ID: " + selectedPenyewaanId);
                JOptionPane.showMessageDialog(this, 
                    "Data penyewaan dengan ID " + selectedPenyewaanId + " tidak ditemukan!");
            }

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data penyewaan: " + e.getMessage());
        }
    }
    
    private void loadMobilDetail() {
        System.out.println("Loading mobil detail for penyewaan ID: " + selectedPenyewaanId);

        if (selectedPenyewaanId <= 0) {
            System.err.println("ID penyewaan tidak valid untuk load detail mobil");
            return;
        }

        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT * FROM penyewaan_detail WHERE penyewaan_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, selectedPenyewaanId);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            int no = 1;
            while (rs.next()) {
                Object[] row = {
                    no++,
                    rs.getString("mobil"),
                    rs.getString("no_polisi"),
                    "Baik", // Default kondisi
                    formatCurrency(0), // Default denda kerusakan
                    "" // Keterangan kosong
                };
                model.addRow(row);

                System.out.println("Loaded mobil: " + rs.getString("mobil") + 
                                 ", No Polisi: " + rs.getString("no_polisi"));
            }

            System.out.println("Total mobil loaded: " + (no - 1));

        } catch (SQLException e) {
            System.err.println("Error loading mobil detail: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat detail mobil: " + e.getMessage());
        }
    }
    
    private void setDefaultReturnDate() {
        // Set tanggal kembali aktual ke hari ini
        String tanggalHariIni = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        txtKembaliRill.setText(tanggalHariIni);
    }

    private void hitungKeterlambatan() {
        try {
            String tanggalJatuhTempo = txtJatuhTempo.getText().trim();
            String tanggalKembaliAktual = txtKembaliRill.getText().trim();

            if (!tanggalJatuhTempo.isEmpty() && !tanggalKembaliAktual.isEmpty()) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateJatuhTempo = inputFormat.parse(tanggalJatuhTempo);
                Date dateKembaliAktual = inputFormat.parse(tanggalKembaliAktual);

                long diffInMillies = dateKembaliAktual.getTime() - dateJatuhTempo.getTime();
                long diffInHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                if (diffInHours > 0) {

                    String keterlambatanText;
                    if (diffInHours >= 24) {
                        long days = diffInHours / 24;
                        long remainingHours = diffInHours % 24;
                        keterlambatanText = days + " hari " + remainingHours + " jam (TERLAMBAT)";
                    } else {
                        keterlambatanText = diffInHours + " jam (TERLAMBAT)";
                    }

                    txtKeterlambatan.setText(keterlambatanText);

                    double dendaKeterlambatan = diffInHours * DENDA_PER_JAM;
                    txtDenda.setText(currencyFormat.format(dendaKeterlambatan));

                } else if (diffInHours < 0) {

                    long earlyHours = Math.abs(diffInHours);
                    String keterlambatanText;

                    if (earlyHours >= 24) {
                        long days = earlyHours / 24;
                        long remainingHours = earlyHours % 24;
                        keterlambatanText = days + " hari " + remainingHours + " jam (LEBIH AWAL)";
                    } else {
                        keterlambatanText = earlyHours + " jam (LEBIH AWAL)";
                    }

                    txtKeterlambatan.setText(keterlambatanText);
                    txtDenda.setText(currencyFormat.format(0));

                    System.out.println("Pengembalian dini: " + earlyHours + " jam lebih awal");

                } else {

                    txtKeterlambatan.setText("Tepat waktu");
                    txtDenda.setText(currencyFormat.format(0));
                }

                hitungTotalPembayaran();
            }
        } catch (Exception e) {
            txtKeterlambatan.setText("0 jam");
            txtDenda.setText(currencyFormat.format(0));
            System.err.println("Error calculating time difference: " + e.getMessage());
        }
    }

    private void hitungTotalPembayaran() {
        try {
            System.out.println("=== HITUNG TOTAL PEMBAYARAN ===");

            // Ambil sisa pembayaran sewa
            String sisaText = txtSisa.getText();
            System.out.println("Sisa pembayaran text: '" + sisaText + "'");
            double sisaPembayaran = parseFromCurrency(sisaText);
            System.out.println("Sisa pembayaran parsed: " + sisaPembayaran);

            // Ambil denda keterlambatan
            String dendaText = txtDenda.getText();
            System.out.println("Denda keterlambatan text: '" + dendaText + "'");
            double dendaKeterlambatan = parseFromCurrency(dendaText);
            System.out.println("Denda keterlambatan parsed: " + dendaKeterlambatan);

            // Ambil total denda kerusakan dari tabel
            double dendaKerusakan = hitungTotalDendaKerusakan();
            System.out.println("Denda kerusakan: " + dendaKerusakan);

            // Hitung total denda
            double totalDenda = dendaKeterlambatan + dendaKerusakan;
            System.out.println("Total denda: " + totalDenda);
            txtTotalDenda.setText(currencyFormat.format(totalDenda));

            // Hitung total yang harus dibayar
            double totalPelunasan = sisaPembayaran + totalDenda;
            System.out.println("Total pelunasan: " + totalPelunasan);
            txtPelunasan.setText(currencyFormat.format(totalPelunasan));

            // Hitung kembalian
            hitungKembalian();

            System.out.println("=== SELESAI HITUNG TOTAL PEMBAYARAN ===");

        } catch (Exception e) {
            System.err.println("Error calculating total payment: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private double hitungTotalDendaKerusakan() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        double total = 0;
        
        for (int i = 0; i < model.getRowCount(); i++) {
            try {
                String dendaStr = model.getValueAt(i, 4).toString();
                double denda = parseFromCurrency(dendaStr);
                total += denda;
            } catch (Exception e) {
                // Ignore parsing errors
            }
        }
        
        return total;
    }
  
    private double parseFromCurrency(String currencyStr) {
        if (currencyStr == null || currencyStr.trim().isEmpty()) {
            return 0;
        }

        System.out.println("Parsing currency: '" + currencyStr + "'");

        try {
            // Hapus semua karakter non-digit dan koma/titik
            String cleanStr = currencyStr.replaceAll("[^0-9,.]", "");
            System.out.println("After cleaning: '" + cleanStr + "'");

            // Handle format Indonesia (gunakan koma sebagai pemisah desimal)
            if (cleanStr.contains(",")) {
                // Format: 10.150.000,00 atau 10150000,00
                cleanStr = cleanStr.replace(".", ""); // Hapus titik pemisah ribuan
                cleanStr = cleanStr.replace(",", "."); // Ganti koma dengan titik untuk parsing
            }

            System.out.println("Final string for parsing: '" + cleanStr + "'");

            double result = Double.parseDouble(cleanStr);
            System.out.println("Parsed result: " + result);

            return result;

        } catch (NumberFormatException e) {
            System.err.println("Error parsing currency '" + currencyStr + "': " + e.getMessage());
            return 0;
        }
    }
    
    // Method untuk debug status form
    public void debugFormStatus() {
        System.out.println("=== DEBUG FORM STATUS ===");
        System.out.println("Selected Penyewaan ID: " + selectedPenyewaanId);
        System.out.println("Selected Kode Penyewaan: " + selectedKodePenyewaan);
        System.out.println("Kode Penyewaan field: " + txtKodePenyewaan.getText());
        System.out.println("Pelanggan field: " + txtPelanggan.getText());
        System.out.println("Tanggal Sewa field: " + txtTanggalSewa.getText());
        System.out.println("Tanggal Jatuh Tempo field: " + txtJatuhTempo.getText());
        System.out.println("Status field: " + txtStatus.getText());
        System.out.println("Tanggal Kembali Aktual field: " + txtKembaliRill.getText());
        System.out.println("Pembayaran field: " + txtPembayaran.getText());
    }
    
    private void simpanPengembalian() {
        debugFormStatus();
        if (!validateInput()) return;

        try (Connection conn = koneksi.getConnection()) {
            conn.setAutoCommit(false);

            try {
               
                debugMobilTableStructure();

                int pengembalianId = insertPengembalian(conn);
                insertPengembalianDetail(conn, pengembalianId);
                updateStatusMobil(conn);
                updateStatusPenyewaan(conn);
                
                Integer driverId = getDriverIdFromPenyewaan(conn, selectedPenyewaanId);
            
                resetDriverStatusYangTidakDigunakan(conn);

                if (driverId != null) {
                    System.out.println("Driver ID " + driverId + " dari penyewaan " + selectedPenyewaanId + " telah dibebaskan");
                }

                conn.commit();
                JOptionPane.showMessageDialog(this, 
                    "Data pengembalian berhasil disimpan!\n" +
                    "ID Pengembalian: " + pengembalianId + "\n" +
                    "Status mobil telah diupdate menjadi 'tersedia'.");

                // Refresh dashboard jika ada
                refreshDashboardIfExists();

                showListView();

            } catch (Exception e) {
                conn.rollback();
                System.err.println("Error dalam simpanPengembalian: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Gagal menyimpan data: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.err.println("Error koneksi database: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Gagal koneksi ke database: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshDashboardIfExists() {
        try {
            java.awt.Container parent = getParent();
            while (parent != null) {
                if (parent instanceof javax.swing.JFrame) {
                    javax.swing.JFrame frame = (javax.swing.JFrame) parent;
                    // Cari komponen FormDashboard
                    for (Component comp : frame.getContentPane().getComponents()) {
                        if (comp instanceof form.FormDashboard) {
                            ((form.FormDashboard) comp).refreshDashboard();
                            break;
                        }
                    }
                    break;
                }
                parent = parent.getParent();
            }
        } catch (Exception e) {
            // Ignore error jika dashboard tidak ditemukan
        }
    }
    
    private int insertPengembalian(Connection conn) throws SQLException {
        System.out.println("=== INSERTING PENGEMBALIAN (ENHANCED FOR EARLY RETURNS) ===");

        String checkColumnSql = "SHOW COLUMNS FROM pengembalian LIKE 'jam_keterlambatan'";
        PreparedStatement checkPs = conn.prepareStatement(checkColumnSql);
        ResultSet checkRs = checkPs.executeQuery();

        boolean hasJamKeterlambatanColumn = checkRs.next();
        checkRs.close();
        checkPs.close();

        String sql;
        if (hasJamKeterlambatanColumn) {

            sql = """
                INSERT INTO pengembalian (kode_pengembalian, penyewaan_id, tanggal_kembali_aktual, 
                jam_keterlambatan, total_denda, total_biaya_tambahan, total_bayar, status, keterangan, user_id) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        } else {

            sql = """
                INSERT INTO pengembalian (kode_pengembalian, penyewaan_id, tanggal_kembali_aktual, 
                total_denda, total_biaya_tambahan, total_bayar, status, keterangan, user_id) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        }

        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        String kodePengembalian = generateKodePengembalian();
        int jamSelisih = hitungJamSelisih();

        double dendaKeterlambatan = parseDoubleFromCurrency(txtDenda.getText());
        double dendaKerusakan = hitungTotalDendaKerusakan();
        double totalDenda = dendaKeterlambatan + dendaKerusakan;
        double biayaTambahan = parseDoubleFromCurrency(txtSisa.getText());
        double totalBayar = parseDoubleFromCurrency(txtPelunasan.getText());

        String keterangan;
        if (jamSelisih > 0) {
            keterangan = "Pengembalian terlambat " + jamSelisih + " jam";
        } else if (jamSelisih < 0) {
            keterangan = "Pengembalian lebih awal " + Math.abs(jamSelisih) + " jam";
        } else {
            keterangan = "Pengembalian tepat waktu";
        }

        System.out.println("Enhanced values for early/late returns:");
        System.out.println("  jam_selisih: " + jamSelisih + " (negatif = dini, positif = telat)");
        System.out.println("  keterangan: " + keterangan);

        ps.setString(1, kodePengembalian);
        ps.setInt(2, selectedPenyewaanId);
        ps.setString(3, txtKembaliRill.getText().trim());

        if (hasJamKeterlambatanColumn) {
            ps.setInt(4, jamSelisih);
            ps.setDouble(5, totalDenda);
            ps.setDouble(6, biayaTambahan);
            ps.setDouble(7, totalBayar);
            ps.setString(8, "selesai");
            ps.setString(9, keterangan);
            ps.setInt(10, 1);
        } else {
            ps.setDouble(4, totalDenda);
            ps.setDouble(5, biayaTambahan);
            ps.setDouble(6, totalBayar);
            ps.setString(7, "selesai");
            ps.setString(8, keterangan);
            ps.setInt(9, 1);
        }

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int id = rs.getInt(1);
            System.out.println("Pengembalian berhasil disimpan dengan ID: " + id);
            return id;
        }
        throw new SQLException("Failed to get generated ID");
    }

    private int hitungJamSelisih() {
        try {
            String tanggalJatuhTempo = txtJatuhTempo.getText().trim();
            String tanggalKembaliAktual = txtKembaliRill.getText().trim();

            if (!tanggalJatuhTempo.isEmpty() && !tanggalKembaliAktual.isEmpty()) {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateJatuhTempo = inputFormat.parse(tanggalJatuhTempo);
                Date dateKembaliAktual = inputFormat.parse(tanggalKembaliAktual);

                long diffInMillies = dateKembaliAktual.getTime() - dateJatuhTempo.getTime();
                long diffInHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                return (int) diffInHours; // Bisa negatif untuk pengembalian dini
            }
        } catch (Exception e) {
            System.err.println("Error calculating hour difference: " + e.getMessage());
        }
        return 0;
    }
 
    public void debugMobilTableStructure() {
        try (Connection conn = koneksi.getConnection()) {
            System.out.println("=== STRUKTUR TABEL MOBIL ===");

            String sql = "DESCRIBE mobil";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(String.format("%-25s | %-15s | %-5s | %-5s | %-10s | %s",
                    rs.getString("Field"),
                    rs.getString("Type"),
                    rs.getString("Null"),
                    rs.getString("Key"),
                    rs.getString("Default"),
                    rs.getString("Extra")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error checking mobil table structure: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void debugDriverStatusAfterReturn() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = """
                SELECT d.id, d.nama, d.status,
                       CASE 
                           WHEN p.driver_id IS NOT NULL THEN 'Sedang digunakan'
                           ELSE 'Tidak digunakan'
                       END as usage_status
                FROM driver d
                LEFT JOIN penyewaan p ON d.id = p.driver_id AND p.status = 'aktif'
                ORDER BY d.nama
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("=== Status Driver Setelah Pengembalian ===");
            while (rs.next()) {
                System.out.println(String.format("ID: %d, Nama: %s, Status: %s, Usage: %s",
                    rs.getInt("id"),
                    rs.getString("nama"),
                    rs.getString("status"),
                    rs.getString("usage_status")
                ));
            }
            System.out.println("==========================================");

        } catch (SQLException e) {
            System.err.println("Error debug driver status: " + e.getMessage());
        }
    }
    
    private int insertPengembalianAlternative(Connection conn) throws SQLException {
        // Cek struktur tabel terlebih dahulu
        String checkSql = "DESCRIBE pengembalian";
        try (PreparedStatement checkPs = conn.prepareStatement(checkSql);
             ResultSet rs = checkPs.executeQuery()) {

            System.out.println("=== STRUKTUR TABEL PENGEMBALIAN ===");
            while (rs.next()) {
                System.out.println("Column: " + rs.getString("Field") + 
                                 " | Type: " + rs.getString("Type"));
            }
        }

        // Query dengan nama kolom yang mungkin berbeda
        String sql = """
            INSERT INTO pengembalian (kode_pengembalian, penyewaan_id, tanggal_kembali_aktual, 
            lama_keterlambatan, denda_keterlambatan, denda_kerusakan, biaya_tambahan, total_bayar, 
            status, keterangan, user_id) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            // Generate kode pengembalian
            String kodePengembalian = generateKodePengembalian();

            // Debug values before insert
            System.out.println("=== INSERT PENGEMBALIAN VALUES ===");
            System.out.println("Kode: " + kodePengembalian);
            System.out.println("Penyewaan ID: " + selectedPenyewaanId);
            System.out.println("Tanggal Kembali: " + txtKembaliRill.getText().trim());

            // Parse keterlambatan sebagai integer (hari)
            int hariKeterlambatan = 0;
            try {
                String keterlambatanText = txtKeterlambatan.getText().replaceAll("[^0-9]", "");
                if (!keterlambatanText.isEmpty()) {
                    hariKeterlambatan = Integer.parseInt(keterlambatanText);
                }
            } catch (Exception e) {
                System.err.println("Error parsing keterlambatan: " + e.getMessage());
            }

            System.out.println("Hari Keterlambatan: " + hariKeterlambatan);
            System.out.println("Denda text: " + txtDenda.getText());
            System.out.println("Denda parsed: " + parseDoubleFromCurrency(txtDenda.getText()));
            System.out.println("Denda Kerusakan: " + hitungTotalDendaKerusakan());
            System.out.println("Sisa Bayar text: " + txtSisa.getText());
            System.out.println("Sisa Bayar parsed: " + parseDoubleFromCurrency(txtSisa.getText()));
            System.out.println("Total Pelunasan text: " + txtPelunasan.getText());
            System.out.println("Total Pelunasan parsed: " + parseDoubleFromCurrency(txtPelunasan.getText()));

            ps.setString(1, kodePengembalian);
            ps.setInt(2, selectedPenyewaanId);
            ps.setString(3, txtKembaliRill.getText().trim());
            ps.setInt(4, hariKeterlambatan); // Gunakan integer untuk hari
            ps.setDouble(5, parseDoubleFromCurrency(txtDenda.getText()));
            ps.setDouble(6, hitungTotalDendaKerusakan());
            ps.setDouble(7, parseDoubleFromCurrency(txtSisa.getText()));
            ps.setDouble(8, parseDoubleFromCurrency(txtPelunasan.getText()));
            ps.setString(9, "Selesai");
            ps.setString(10, "Pengembalian berhasil diproses");
            ps.setInt(11, 1); // User ID

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Failed to get generated ID");
        }
    
    private void insertPengembalianDetail(Connection conn, int pengembalianId) throws SQLException {
        System.out.println("=== INSERTING ENHANCED PENGEMBALIAN DETAIL ===");
        
        String sql = """
            INSERT INTO pengembalian_detail (pengembalian_id, mobil_id, kondisi_mobil, 
            denda_kerusakan, keterangan_kondisi, created_at) 
            VALUES (?, ?, ?, ?, ?, NOW())
            """;

        PreparedStatement ps = conn.prepareStatement(sql);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            System.out.println("Processing enhanced row " + i + ":");

            String mobilName = model.getValueAt(i, 1).toString();
            String noPolisi = model.getValueAt(i, 2).toString();
            String kondisi = model.getValueAt(i, 3).toString();
            String dendaKerusakanStr = model.getValueAt(i, 4).toString();
            String keterangan = model.getValueAt(i, 5).toString();

            int mobilId = getMobilIdByNameOrPlate(mobilName, noPolisi);
            double dendaKerusakan = parseDoubleFromCurrency(dendaKerusakanStr);

            ps.setInt(1, pengembalianId);
            ps.setInt(2, mobilId);
            ps.setString(3, kondisi);
            ps.setDouble(4, dendaKerusakan);
            ps.setString(5, keterangan);

            System.out.println("  Enhanced detail - kondisi: " + kondisi + 
                             ", denda: " + dendaKerusakan);

            ps.executeUpdate();
        }

        ps.close();
        System.out.println("Enhanced detail pengembalian berhasil disimpan!");
    }
    
    private int getMobilIdByNameOrPlate(String mobilName, String noPolisi) throws SQLException {
        try (Connection conn = koneksi.getConnection()) {
            // Coba cari berdasarkan no polisi terlebih dahulu (lebih akurat)
            String sql = "SELECT id FROM mobil WHERE no_polisi = ? LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, noPolisi);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                System.out.println("  Found mobil_id " + id + " by no_polisi: " + noPolisi);
                return id;
            }

            // Jika tidak ditemukan berdasarkan no polisi, coba berdasarkan nama
            sql = "SELECT id FROM mobil WHERE nama LIKE ? OR merk LIKE ? LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + mobilName + "%");
            ps.setString(2, "%" + mobilName + "%");

            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                System.out.println("  Found mobil_id " + id + " by name: " + mobilName);
                return id;
            }

            // Jika masih tidak ditemukan, buat entry baru atau gunakan default
            System.err.println("  Mobil tidak ditemukan: " + mobilName + " / " + noPolisi);
            return 1; // Default ID, atau bisa throw exception
        }
    }

    private String getMobilByName(String mobilName) {
        try (Connection conn = koneksi.getConnection()) {
            String sql = """
                SELECT mobil FROM penyewaan_detail 
                WHERE penyewaan_id = ? AND mobil = ?
                """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, selectedPenyewaanId);
            ps.setString(2, mobilName);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("mobil");
            }
            return "";
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            return "";
        }
    }
    
    private void updateStatusPenyewaan(Connection conn) throws SQLException {
        String sql = "UPDATE penyewaan SET status = 'Selesai' WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, selectedPenyewaanId);
        ps.executeUpdate();
        ps.close();
        
        debugDriverStatusAfterReturn();
    }
    
    private void updateStatusMobil(Connection conn) throws SQLException {

        String selectSql = "SELECT no_polisi FROM penyewaan_detail WHERE penyewaan_id = ?";
        PreparedStatement selectPs = conn.prepareStatement(selectSql);
        selectPs.setInt(1, selectedPenyewaanId);
        ResultSet rs = selectPs.executeQuery();

        // Update status mobil satu per satu berdasarkan no_polisi
        String updateSql = "UPDATE mobil SET status = 'tersedia' WHERE no_polisi = ?";
        PreparedStatement updatePs = conn.prepareStatement(updateSql);

        while (rs.next()) {
            String noPolisi = rs.getString("no_polisi");
            updatePs.setString(1, noPolisi);
            updatePs.executeUpdate();
            System.out.println("Updated status mobil: " + noPolisi + " -> tersedia");
        }

        rs.close();
        selectPs.close();
        updatePs.close();
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
                AND status IN ('Aktif', 'aktif')
            )
        """;

        PreparedStatement ps = conn.prepareStatement(sql);
        int rowsAffected = ps.executeUpdate();
        System.out.println("Reset status driver yang tidak digunakan: " + rowsAffected + " drivers");
    }

    private Integer getDriverIdFromPenyewaan(Connection conn, int penyewaanId) throws SQLException {
        String sql = "SELECT driver_id FROM penyewaan WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, penyewaanId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getObject("driver_id", Integer.class);
        }
        return null;
    }

    private String generateKodePengembalian() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String kode = "PG-" + dateStr + "-" + String.format("%03d", (int)(Math.random() * 999) + 1);
        return kode;
    }
    
    private boolean validateInput() {
        System.out.println("=== VALIDATING INPUT ===");
        System.out.println("Selected Penyewaan ID: " + selectedPenyewaanId);

        // Cek apakah penyewaan sudah dipilih
        if (selectedPenyewaanId <= 0) {
            showError("Pilih penyewaan yang akan dikembalikan!");
            return false;
        }

        // Cek apakah tanggal kembali aktual sudah diisi
        if (txtKembaliRill.getText().trim().isEmpty()) {
            showError("Tanggal kembali aktual harus diisi!");
            txtKembaliRill.requestFocus();
            return false;
        }

        // Validasi format tanggal
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            sdf.parse(txtKembaliRill.getText().trim());
        } catch (Exception e) {
            showError("Format tanggal kembali aktual tidak valid! Gunakan format: yyyy-mm-dd");
            txtKembaliRill.requestFocus();
            return false;
        }

        // Cek apakah pembayaran sudah diisi
        String pembayaranText = txtPembayaran.getText().trim();
        if (pembayaranText.isEmpty()) {
            showError("Jumlah pembayaran harus diisi!");
            txtPembayaran.requestFocus();
            return false;
        }

        // Validasi pembayaran dengan debug
        try {
            String pelunasanText = txtPelunasan.getText();

            System.out.println("Validating payment:");
            System.out.println("Pelunasan text: '" + pelunasanText + "'");
            System.out.println("Pembayaran text: '" + pembayaranText + "'");

            // Parse pembayaran dengan metode yang lebih robust
            double totalPelunasan = parseDoubleFromCurrency(pelunasanText);
            double pembayaran = parseDoubleFromCurrency(pembayaranText);

            System.out.println("Pelunasan parsed: " + totalPelunasan);
            System.out.println("Pembayaran parsed: " + pembayaran);

            if (pembayaran < totalPelunasan) {
                showError("Pembayaran tidak mencukupi!\n\n" +
                         "Total yang harus dibayar: " + formatCurrency(totalPelunasan) + "\n" +
                         "Pembayaran yang dimasukkan: " + formatCurrency(pembayaran) + "\n" +
                         "Kekurangan: " + formatCurrency(totalPelunasan - pembayaran));
                txtPembayaran.requestFocus();
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error validating payment: " + e.getMessage());
            e.printStackTrace();
            showError("Format pembayaran tidak valid!");
            txtPembayaran.requestFocus();
            return false;
        }

        System.out.println("Validation passed!");
        return true;
    }
    
    private double parseDoubleFromCurrency(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }

        System.out.println("Parsing input: '" + text + "'");

        try {
            // Cek apakah input dalam format scientific notation
            if (text.contains("E") || text.contains("e")) {
                System.out.println("Detected scientific notation: " + text);
                // Parse langsung sebagai double untuk scientific notation
                double value = Double.parseDouble(text);
                System.out.println("Scientific notation parsed to: " + value);
                return value;
            }

            // Hapus semua karakter non-digit kecuali koma dan titik
            String cleanText = text.replaceAll("[^0-9,.]", "");
            System.out.println("Cleaned text: '" + cleanText + "'");

            if (cleanText.isEmpty()) {
                return 0;
            }

            // Jika menggunakan format Indonesia (koma sebagai desimal)
            if (cleanText.contains(",")) {
                cleanText = cleanText.replace(".", ""); // Hapus pemisah ribuan
                cleanText = cleanText.replace(",", "."); // Ganti koma dengan titik untuk parsing
            }

            System.out.println("Final for parsing: '" + cleanText + "'");

            double result = Double.parseDouble(cleanText);
            System.out.println("Parsed result: " + result);
            return result;

        } catch (Exception e) {
            System.err.println("Error parsing: " + e.getMessage());

            // Coba cara alternatif - ambil hanya digit
            try {
                String digitsOnly = text.replaceAll("[^0-9]", "");
                System.out.println("Fallback - digits only: '" + digitsOnly + "'");

                if (digitsOnly.isEmpty()) {
                    return 0;
                }

                double result = Double.parseDouble(digitsOnly);
                System.out.println("Fallback result: " + result);
                return result;
            } catch (Exception ex) {
                System.err.println("Fallback parsing failed: " + ex.getMessage());
                return 0;
            }
        }
    }

    // Tambahkan method untuk mencegah scientific notation pada input
    private void setupPaymentInputFilter() {
        // Document filter untuk mencegah scientific notation
        ((AbstractDocument) txtPembayaran.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                // Hanya izinkan digit dan titik
                if (string.matches("[0-9.]*")) {
                    super.insertString(fb, offset, string, attr);
                    // Format ulang setelah input
                    SwingUtilities.invokeLater(() -> formatPaymentInput());
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[0-9.]*")) {
                    super.replace(fb, offset, length, text, attrs);
                    SwingUtilities.invokeLater(() -> formatPaymentInput());
                }
            }
        });
    }

    private void formatPaymentInput() {
        try {
            String text = txtPembayaran.getText().replaceAll("[^0-9]", "");
            if (!text.isEmpty() && text.length() <= 15) { // Batasi panjang input
                long value = Long.parseLong(text);
                // Format dengan pemisah ribuan
                String formatted = String.format("%,d", value).replace(",", ".");
                DocumentListener paymentDocumentListener = null;

                // Update text tanpa trigger event
                txtPembayaran.getDocument().removeDocumentListener(paymentDocumentListener);
                txtPembayaran.setText(formatted);
                txtPembayaran.getDocument().addDocumentListener(paymentDocumentListener);
            }
        } catch (Exception e) {
            // Ignore formatting errors
        }
    }
    
    private String formatCurrency(double value) {
        return currencyFormat.format(value);
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
        pagination2 = new model.Pagination();
        addPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblCust1 = new javax.swing.JLabel();
        lblKTP1 = new javax.swing.JLabel();
        lblNoHPCust1 = new javax.swing.JLabel();
        txtKodePenyewaan = new javax.swing.JTextField();
        txtPelanggan = new javax.swing.JTextField();
        txtTanggalSewa = new javax.swing.JTextField();
        txtJatuhTempo = new javax.swing.JTextField();
        lblAlamat1 = new javax.swing.JLabel();
        lblCust2 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        lblPembayaran = new javax.swing.JLabel();
        lblPembayaran1 = new javax.swing.JLabel();
        txtSisa = new javax.swing.JTextField();
        lblPembayaran2 = new javax.swing.JLabel();
        txtTotalDenda = new javax.swing.JTextField();
        lblPembayaran4 = new javax.swing.JLabel();
        lblPembayaran5 = new javax.swing.JLabel();
        txtPembayaran = new javax.swing.JTextField();
        txtPelunasan = new javax.swing.JTextField();
        lblPembayaran6 = new javax.swing.JLabel();
        txtKembalian = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnKembalikedepan = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblCust3 = new javax.swing.JLabel();
        lblKTP2 = new javax.swing.JLabel();
        lblNoHPCust2 = new javax.swing.JLabel();
        txtKembaliRill = new javax.swing.JTextField();
        txtKeterlambatan = new javax.swing.JTextField();
        txtDenda = new javax.swing.JTextField();

        jPanel4.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        jLabel3.setFont(new java.awt.Font("Poppins ExtraBold", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PLESIRAN YK > TRANSAKSI > PENGEMBALIAN");

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
        btnTambah1.setText("+ Proses");
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

        pagination2.setBackground(new java.awt.Color(255, 255, 255));
        pagination2.setForeground(new java.awt.Color(255, 255, 255));
        pagination2.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N

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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1055, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PanelMainLayout.createSequentialGroup()
                                .addComponent(pagination2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(246, 246, 246)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        PanelMainLayout.setVerticalGroup(
            PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelMainLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(PanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPencarianData1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pagination2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jPanel3.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        jLabel2.setFont(new java.awt.Font("Poppins ExtraBold", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PLESIRAN YK > TRANSAKSI > PENGEMBALIAN > PROSES");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jPanel7.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("DATA PENYEWAAN");

        lblCust1.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblCust1.setForeground(new java.awt.Color(255, 255, 255));
        lblCust1.setText("Kode Penyewaan");

        lblKTP1.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblKTP1.setForeground(new java.awt.Color(255, 255, 255));
        lblKTP1.setText("Pelanggan");

        lblNoHPCust1.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblNoHPCust1.setForeground(new java.awt.Color(255, 255, 255));
        lblNoHPCust1.setText("Tanggal Sewa");

        txtKodePenyewaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodePenyewaanActionPerformed(evt);
            }
        });

        txtPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPelangganActionPerformed(evt);
            }
        });

        txtTanggalSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalSewaActionPerformed(evt);
            }
        });

        txtJatuhTempo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJatuhTempoActionPerformed(evt);
            }
        });

        lblAlamat1.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblAlamat1.setForeground(new java.awt.Color(255, 255, 255));
        lblAlamat1.setText("Tanggal Jatuh Tempo");

        lblCust2.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblCust2.setForeground(new java.awt.Color(255, 255, 255));
        lblCust2.setText("Status");

        txtStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusActionPerformed(evt);
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
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTanggalSewa, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPelanggan, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKodePenyewaan, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJatuhTempo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblCust2)
                        .addGap(18, 18, 18)
                        .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCust1)
                    .addComponent(txtKodePenyewaan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCust2)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKTP1)
                    .addComponent(txtPelanggan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoHPCust1)
                    .addComponent(txtTanggalSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAlamat1)
                    .addComponent(txtJatuhTempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel8.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        lblPembayaran.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran.setText("PEMBAYARAN");

        lblPembayaran1.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran1.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran1.setText("Sisa Pembayaran");

        lblPembayaran2.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran2.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran2.setText("Total Denda");

        lblPembayaran4.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran4.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran4.setText("Total Pelunasan");

        lblPembayaran5.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran5.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran5.setText("Pembayaran");

        lblPembayaran6.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPembayaran6.setForeground(new java.awt.Color(255, 255, 255));
        lblPembayaran6.setText("Kembalian");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPembayaran)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPembayaran1)
                            .addComponent(lblPembayaran2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTotalDenda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSisa, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPembayaran4)
                            .addComponent(lblPembayaran5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPembayaran, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addComponent(txtPelunasan, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblPembayaran6)
                                .addGap(18, 18, 18)
                                .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPembayaran)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPembayaran1)
                            .addComponent(txtSisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPembayaran2)
                            .addComponent(txtTotalDenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPembayaran6)
                                .addComponent(txtKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPembayaran4)
                                .addComponent(txtPelunasan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPembayaran5)
                            .addComponent(txtPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

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

        jPanel9.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        jPanel9.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PROSES PENGEMBALIAN");

        lblCust3.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblCust3.setForeground(new java.awt.Color(255, 255, 255));
        lblCust3.setText("Tanggal Kembali Aktual");

        lblKTP2.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblKTP2.setForeground(new java.awt.Color(255, 255, 255));
        lblKTP2.setText("Keterlambatan");

        lblNoHPCust2.setFont(new java.awt.Font("Poppins SemiBold", 0, 12)); // NOI18N
        lblNoHPCust2.setForeground(new java.awt.Color(255, 255, 255));
        lblNoHPCust2.setText("Denda Keterlambatan");

        txtKembaliRill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKembaliRillActionPerformed(evt);
            }
        });

        txtKeterlambatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKeterlambatanActionPerformed(evt);
            }
        });

        txtDenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCust3)
                            .addComponent(lblKTP2)
                            .addComponent(lblNoHPCust2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtKeterlambatan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                            .addComponent(txtKembaliRill, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDenda)))
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCust3)
                    .addComponent(txtKembaliRill, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKTP2)
                    .addComponent(txtKeterlambatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNoHPCust2)
                    .addComponent(txtDenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout addPanelLayout = new javax.swing.GroupLayout(addPanel);
        addPanel.setLayout(addPanelLayout);
        addPanelLayout.setHorizontalGroup(
            addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(addPanelLayout.createSequentialGroup()
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(addPanelLayout.createSequentialGroup()
                        .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(addPanelLayout.createSequentialGroup()
                                .addGap(109, 109, 109)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(addPanelLayout.createSequentialGroup()
                                .addGap(416, 416, 416)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnKembalikedepan, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(addPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
        addPanelLayout.setVerticalGroup(
            addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addPanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(addPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnKembalikedepan))
                .addGap(31, 31, 31))
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

    private void btnKembalikedepanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembalikedepanActionPerformed
        showListView();
    }//GEN-LAST:event_btnKembalikedepanActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        simpanPengembalian();
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtJatuhTempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJatuhTempoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJatuhTempoActionPerformed

    private void txtTanggalSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalSewaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalSewaActionPerformed

    private void txtPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPelangganActionPerformed

    private void txtKodePenyewaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodePenyewaanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodePenyewaanActionPerformed

    private void txtStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusActionPerformed

    private void txtKembaliRillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKembaliRillActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKembaliRillActionPerformed

    private void txtKeterlambatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKeterlambatanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKeterlambatanActionPerformed

    private void txtDendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDendaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelMain;
    private javax.swing.JPanel addPanel;
    private javax.swing.JButton btnKembalikedepan;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblAlamat1;
    private javax.swing.JLabel lblCust1;
    private javax.swing.JLabel lblCust2;
    private javax.swing.JLabel lblCust3;
    private javax.swing.JLabel lblKTP1;
    private javax.swing.JLabel lblKTP2;
    private javax.swing.JLabel lblNoHPCust1;
    private javax.swing.JLabel lblNoHPCust2;
    private javax.swing.JLabel lblPembayaran;
    private javax.swing.JLabel lblPembayaran1;
    private javax.swing.JLabel lblPembayaran2;
    private javax.swing.JLabel lblPembayaran4;
    private javax.swing.JLabel lblPembayaran5;
    private javax.swing.JLabel lblPembayaran6;
    private model.Pagination pagination2;
    private javax.swing.JTextField txtDenda;
    private javax.swing.JTextField txtJatuhTempo;
    private javax.swing.JTextField txtKembaliRill;
    private javax.swing.JTextField txtKembalian;
    private javax.swing.JTextField txtKeterlambatan;
    private javax.swing.JTextField txtKodePenyewaan;
    private javax.swing.JTextField txtPelanggan;
    private javax.swing.JTextField txtPelunasan;
    private javax.swing.JTextField txtPembayaran;
    private javax.swing.JTextField txtPencarianData1;
    private javax.swing.JTextField txtSisa;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTanggalSewa;
    private javax.swing.JTextField txtTotalDenda;
    // End of variables declaration//GEN-END:variables
   private void loadData() {
        try (Connection conn = koneksi.getConnection()) {
            System.out.println("=== LOADING DATA PENYEWAAN ===");

            // Query dengan LEFT JOIN untuk mendapatkan nama pelanggan
            String sql = """
                SELECT p.*, pel.nama as nama_pelanggan_join 
                FROM penyewaan p 
                LEFT JOIN pelanggan pel ON p.pelanggan_id = pel.id 
                WHERE p.status = 'aktif' 
                ORDER BY p.tanggal_kembali_rencana ASC
                """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);

            int no = 1;
            while (rs.next()) {
                // Gunakan nama dari JOIN, jika NULL gunakan nama_pelanggan dari tabel penyewaan
                String namaPelanggan = rs.getString("nama_pelanggan_join");
                if (namaPelanggan == null) {
                    namaPelanggan = rs.getString("nama_pelanggan");
                }
                if (namaPelanggan == null) {
                    namaPelanggan = "Nama tidak tersedia";
                }

                Object[] row = {
                    no++,
                    rs.getString("kode_penyewaan"),
                    namaPelanggan,
                    rs.getDate("tanggal_sewa"),
                    rs.getDate("tanggal_kembali_rencana"),
                    rs.getString("status"),
                    "Proses"
                };
                model.addRow(row);

                System.out.println("Loaded: " + rs.getString("kode_penyewaan") + " - " + namaPelanggan);
            }

            System.out.println("Total rows loaded: " + model.getRowCount());

            if (model.getRowCount() == 0) {
                Object[] emptyRow = {"", "Tidak ada penyewaan aktif", "", "", "", "", ""};
                model.addRow(emptyRow);
            }

        } catch (SQLException e) {
            System.err.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }
    
    private void searchData(String keyword) {
        if (keyword.trim().isEmpty()) {
            loadData();
            return;
        }

        try (Connection conn = koneksi.getConnection()) {
            // Gunakan status 'aktif' dan nama_pelanggan langsung dari tabel penyewaan
            String sql = """
                SELECT p.id, p.kode_penyewaan, p.nama_pelanggan, 
                       p.tanggal_sewa, p.tanggal_kembali_rencana, p.status
                FROM penyewaan p 
                WHERE p.status = 'aktif' AND (p.kode_penyewaan LIKE ? OR p.nama_pelanggan LIKE ?)
                ORDER BY p.tanggal_kembali_rencana ASC
                """;

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
                    rs.getString("nama_pelanggan"),
                    rs.getDate("tanggal_sewa"),
                    rs.getDate("tanggal_kembali_rencana"),
                    rs.getString("status"),
                    "Proses"
                };
                model.addRow(row);
            }

            if (model.getRowCount() == 0) {
                Object[] emptyRow = {"", "Data tidak ditemukan", "", "", "", "", ""};
                model.addRow(emptyRow);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void clearInputFields() {
        JTextField[] fields = {
            txtKodePenyewaan, txtPelanggan, txtTanggalSewa, txtJatuhTempo, txtStatus,
            txtKembaliRill, txtKeterlambatan, txtDenda,
            txtSisa, txtTotalDenda, txtPelunasan, txtPembayaran, txtKembalian
        };
        
        for (JTextField field : fields) {
            if (field != null) {
                field.setText("");
            }
        }
        
        // Clear table
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        // Reset selections
        selectedPenyewaanId = 0;
        selectedKodePenyewaan = "";
    }

}