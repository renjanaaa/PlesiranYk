package form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import koneksi.koneksi;
import javax.swing.Timer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class FormDashboard extends JPanel {
    
    private Timer refreshTimer;
    
    // Komponen untuk header
    private JPanel pnlHeader;
    private JLabel lblTitle;
    private JLabel lblTagline;
    private JLabel lblDateTime;
    
    // Komponen untuk chart
    private JPanel pnlChart;
    private ChartPanel chartPanel;
    
    public FormDashboard() {
        initComponents();
        setupHeaderAndLayout();
        setupPanelStyles();
        setupChart();
        loadAllData();
        setupAutoRefresh();
    }
    
    private void setupHeaderAndLayout() {

        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        createHeaderPanel();
        JPanel statsPanel = createStatsPanel();
        createChartPanel();

        add(pnlHeader, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
        add(pnlChart, BorderLayout.SOUTH);
    }
    
    private void createHeaderPanel() {
        pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setOpaque(false);
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // Margin bawah lebih kecil lagi

        // Panel kiri untuk title dan tagline
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        // Title
        lblTitle = new JLabel("PLESIRAN YK");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Tagline
        lblTagline = new JLabel("Partner Perjalanan Anda");
        lblTagline.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        lblTagline.setForeground(new Color(127, 140, 141));
        lblTagline.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftPanel.add(lblTitle);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(lblTagline);

        // Panel kanan untuk tanggal dan waktu
        lblDateTime = new JLabel();
        lblDateTime.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDateTime.setForeground(new Color(52, 73, 94));
        lblDateTime.setHorizontalAlignment(SwingConstants.RIGHT);
        updateDateTime();

        // Timer untuk update waktu setiap detik
        Timer timeTimer = new Timer(1000, e -> updateDateTime());
        timeTimer.start();

        pnlHeader.add(leftPanel, BorderLayout.WEST);
        pnlHeader.add(lblDateTime, BorderLayout.EAST);
    }
    
    private void updateDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy - HH:mm:ss");
        lblDateTime.setText(sdf.format(new Date()));
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setOpaque(false);
        // HILANGKAN margin bawah sepenuhnya
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Buat kotak dengan ukuran lebih lebar
        pnlTotDriv = createStatBox("TOTAL DRIVER", "0", new Color(46, 204, 113), "👨‍✈️");
        pnlTotDriv.setPreferredSize(new Dimension(280, 120)); // Lebar dari 200 jadi 280

        pnlTotMobil = createStatBox("TOTAL MOBIL", "0", new Color(52, 152, 219), "🚗");
        pnlTotMobil.setPreferredSize(new Dimension(280, 120));

        pnlMobKeluar = createStatBox("MOBIL KELUAR", "0", new Color(231, 76, 60), "📤");
        pnlMobKeluar.setPreferredSize(new Dimension(280, 120));

        pnlMobMasuk = createStatBox("MOBIL MASUK", "0", new Color(241, 196, 15), "📥");
        pnlMobMasuk.setPreferredSize(new Dimension(280, 120));

        panel.add(pnlTotDriv);
        panel.add(pnlTotMobil);
        panel.add(pnlMobKeluar);
        panel.add(pnlMobMasuk);

        return panel;
    }
    
    private JPanel createStatBox(String title, String value, Color bgColor, String icon) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(8, 8)); // Spacing horizontal lebih besar
        panel.setBackground(bgColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20) // Padding horizontal lebih besar
        ));

        // Header dengan title dan icon
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13)); // Font sedikit lebih besar

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18)); // Icon sedikit lebih besar
        lblIcon.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblIcon, BorderLayout.EAST);

        // Value label
        JLabel lblValue = new JLabel(value);
        lblValue.setForeground(Color.WHITE);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Font angka sedikit lebih besar
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);

        // Simpan referensi label
        if (title.equals("TOTAL DRIVER")) {
            lblTotDriv = lblValue;
        } else if (title.equals("TOTAL MOBIL")) {
            lblTotMob = lblValue;
        } else if (title.equals("MOBIL KELUAR")) {
            lblMobKel = lblValue;
        } else if (title.equals("MOBIL MASUK")) {
            lblMobMas = lblValue;
        }

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(lblValue, BorderLayout.CENTER);

        // Hover effect (sama seperti sebelumnya)
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 3),
                    BorderFactory.createEmptyBorder(14, 19, 14, 19)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 2),
                    BorderFactory.createEmptyBorder(15, 20, 15, 20)
                ));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (title.equals("TOTAL DRIVER")) {
                    showDetailDriver();
                } else if (title.equals("TOTAL MOBIL")) {
                    showDetailMobil();
                } else if (title.equals("MOBIL KELUAR")) {
                    showDetailMobilKeluar();
                } else if (title.equals("MOBIL MASUK")) {
                    showDetailMobilMasuk();
                }
            }
        });

        return panel;
    }

    private void createChartPanel() {
        pnlChart = new JPanel(new BorderLayout());
        pnlChart.setOpaque(false);
        pnlChart.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            "Statistik Penyewaan",
            0, 0,
            new Font("Segoe UI", Font.BOLD, 16),
            new Color(44, 62, 80)
        ));
        // Tinggi chart diperbesar maksimal untuk mengisi ruang kosong
        pnlChart.setPreferredSize(new Dimension(0, 500)); // Dari 400 jadi 500
    }
    
    private void setupChart() {
        // Buat dataset untuk bar chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(0, "Jumlah", "Penyewaan Aktif");
        dataset.addValue(0, "Jumlah", "Penyewaan Selesai");
        dataset.addValue(0, "Jumlah", "Penyewaan Dibatalkan");

        // Buat bar chart
        JFreeChart chart = ChartFactory.createBarChart(
            "Status Penyewaan",
            "Status",
            "Jumlah",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );

        // Customize chart
        chart.setBackgroundPaint(new Color(245, 247, 250));

        // Customize title font
        chart.getTitle().setFont(new Font("Segoe UI", Font.BOLD, 18));
        chart.getTitle().setPaint(new Color(44, 62, 80));

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setRangeGridlinePaint(new Color(200, 200, 200));
        plot.setDomainGridlinesVisible(false);

        // Customize axis fonts
        plot.getDomainAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
        plot.getRangeAxis().setLabelFont(new Font("Segoe UI", Font.BOLD, 14));
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));

        // Customize bar colors
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(52, 152, 219));
        renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());

        // Atur lebar bar
        renderer.setMaximumBarWidth(0.15);

        // Buat chart panel dengan ukuran maksimal
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(0, 450)); // Dari 350 jadi 450
        chartPanel.setBackground(new Color(245, 247, 250));
        chartPanel.setMouseWheelEnabled(true);

        pnlChart.add(chartPanel, BorderLayout.CENTER);
    }
    
    // Method untuk setup style panel (sudah ada, tapi perlu disesuaikan)
    private void setupPanelStyles() {
        // Style sudah diatur di createStatBox()
        addClickEvents();
    }
    
    // Method untuk tambah event click
    private void addClickEvents() {
        // Event sudah ditambahkan di createStatBox()
    }
    
    // Method untuk load semua data
    private void loadAllData() {
        loadTotalDriver();
        loadTotalMobil();
        loadMobilKeluar();
        loadMobilMasuk();
        loadChartData();
    }
    
    // Load Total Driver
    private void loadTotalDriver() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT COUNT(*) as total FROM driver";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                lblTotDriv.setText(String.valueOf(rs.getInt("total")));
            }
        } catch (SQLException e) {
            lblTotDriv.setText("0");
            System.err.println("Error loading total driver: " + e.getMessage());
        }
    }
    
    // Load Total Mobil
    private void loadTotalMobil() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT COUNT(*) as total FROM mobil";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                lblTotMob.setText(String.valueOf(rs.getInt("total")));
            }
        } catch (SQLException e) {
            lblTotMob.setText("0");
            System.err.println("Error loading total mobil: " + e.getMessage());
        }
    }
    
    // Load Mobil Keluar (dari penyewaan aktif)
    private void loadMobilKeluar() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT COUNT(DISTINCT pd.no_polisi) as total " +
                         "FROM penyewaan_detail pd " +
                         "JOIN penyewaan p ON pd.penyewaan_id = p.id " +
                         "WHERE UPPER(TRIM(p.status)) = 'AKTIF'";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                lblMobKel.setText(String.valueOf(rs.getInt("total")));
            }
        } catch (SQLException e) {
            lblMobKel.setText("0");
            System.err.println("Error loading mobil keluar: " + e.getMessage());
        }
    }
    
    // Load Mobil Masuk (dari pengembalian hari ini)
    private void loadMobilMasuk() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT COUNT(*) as total " +
                         "FROM pengembalian " +
                         "WHERE DATE(tanggal_kembali) = CURDATE()";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                lblMobMas.setText(String.valueOf(rs.getInt("total")));
            } else {
                // Fallback: hitung mobil tersedia
                loadMobilTersedia();
            }
        } catch (SQLException e) {
            // Fallback: hitung mobil tersedia
            loadMobilTersedia();
            System.err.println("Error loading mobil masuk: " + e.getMessage());
        }
    }
    
    // Fallback method untuk mobil tersedia
    private void loadMobilTersedia() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT COUNT(*) as total FROM mobil WHERE UPPER(TRIM(status)) = 'TERSEDIA'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                lblMobMas.setText(String.valueOf(rs.getInt("total")));
            }
        } catch (SQLException e) {
            lblMobMas.setText("0");
            System.err.println("Error loading mobil tersedia: " + e.getMessage());
        }
    }
    
    private void loadChartData() {
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT status, COUNT(*) as jumlah FROM penyewaan GROUP BY status";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // Inisialisasi dengan 0
            int aktif = 0, selesai = 0, dibatalkan = 0;

            while (rs.next()) {
                String status = rs.getString("status");
                int jumlah = rs.getInt("jumlah");

                if (status != null) {
                    status = status.trim();
                    if (status.equalsIgnoreCase("Aktif")) {
                        aktif = jumlah;
                    } else if (status.equalsIgnoreCase("Selesai")) {
                        selesai = jumlah;
                    } else if (status.equalsIgnoreCase("Dibatalkan")) {
                        dibatalkan = jumlah;
                    }
                }
            }

            // Tambahkan data ke dataset dengan urutan yang konsisten
            dataset.addValue(aktif, "Status", "Aktif");
            dataset.addValue(selesai, "Status", "Selesai");
            dataset.addValue(dibatalkan, "Status", "Dibatalkan");

            // Update chart
            JFreeChart chart = chartPanel.getChart();
            CategoryPlot plot = (CategoryPlot) chart.getPlot();
            plot.setDataset(dataset);

            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, new Color(52, 152, 219));  // Aktif - Biru

        } catch (SQLException e) {
            System.err.println("Error loading chart data: " + e.getMessage());
        }
    }
    
    private void showDetailDriver() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Detail Semua Driver", true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"Nama", "No. HP", "Alamat", "Status"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0);
        
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT nama, no_hp, alamat, status FROM driver ORDER BY status, nama";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nama"),
                    rs.getString("no_hp"),
                    rs.getString("alamat"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JButton btnClose = new JButton("Tutup");
        btnClose.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnClose, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    // Show Detail Mobil
    private void showDetailMobil() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Detail Semua Mobil", true);
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"No. Polisi", "Merk/Type", "Model", "Tahun", "Status"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0);
        
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT no_polisi, merk_type, model, tahun, status FROM mobil ORDER BY status, no_polisi";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("no_polisi"),
                    rs.getString("merk_type"),
                    rs.getString("model"),
                    rs.getString("tahun"),
                    rs.getString("status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JButton btnClose = new JButton("Tutup");
        btnClose.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnClose, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    // Show Detail Mobil Keluar
    private void showDetailMobilKeluar() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Detail Mobil Keluar (Penyewaan Aktif)", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"No. Polisi", "Merk/Type", "Kode Sewa", "Pelanggan", "Tgl Sewa", "Tgl Kembali"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0);
        
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT m.no_polisi, m.merk_type, p.kode_penyewaan, p.nama_pelanggan, " +
                         "p.tanggal_sewa, p.tanggal_kembali_rencana " +
                         "FROM mobil m " +
                         "JOIN penyewaan_detail pd ON m.no_polisi = pd.no_polisi " +
                         "JOIN penyewaan p ON pd.penyewaan_id = p.id " +
                         "WHERE UPPER(TRIM(p.status)) = 'AKTIF' " +
                         "ORDER BY p.tanggal_kembali_rencana";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("no_polisi"),
                    rs.getString("merk_type"),
                    rs.getString("kode_penyewaan"),
                    rs.getString("nama_pelanggan"),
                    rs.getDate("tanggal_sewa"),
                    rs.getDate("tanggal_kembali_rencana")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JButton btnClose = new JButton("Tutup");
        btnClose.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnClose, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    // Show Detail Mobil Masuk
    private void showDetailMobilMasuk() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Detail Mobil Masuk (Pengembalian Hari Ini)", true);
        dialog.setSize(800, 500);
        dialog.setLocationRelativeTo(this);
        
        String[] columns = {"No. Polisi", "Merk/Type", "Kode Sewa", "Pelanggan", "Tgl Kembali", "Kondisi"};
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0);
        
        try (Connection conn = koneksi.getConnection()) {
            String sql = "SELECT m.no_polisi, m.merk_type, p.kode_penyewaan, p.nama_pelanggan, " +
                         "pg.tanggal_kembali, pg.kondisi_mobil " +
                         "FROM pengembalian pg " +
                         "JOIN penyewaan p ON pg.penyewaan_id = p.id " +
                         "JOIN penyewaan_detail pd ON p.id = pd.penyewaan_id " +
                         "JOIN mobil m ON pd.no_polisi = m.no_polisi " +
                         "WHERE DATE(pg.tanggal_kembali) = CURDATE() " +
                         "ORDER BY pg.tanggal_kembali DESC";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("no_polisi"),
                    rs.getString("merk_type"),
                    rs.getString("kode_penyewaan"),
                    rs.getString("nama_pelanggan"),
                    rs.getTimestamp("tanggal_kembali"),
                    rs.getString("kondisi_mobil")
                });
            }
            
            if (model.getRowCount() == 0) {
                model.addRow(new Object[]{"Tidak ada", "pengembalian", "hari ini", "", "", ""});
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        JButton btnClose = new JButton("Tutup");
        btnClose.addActionListener(e -> dialog.dispose());
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnClose, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    // Auto refresh
    private void setupAutoRefresh() {
        refreshTimer = new Timer(30000, e -> {
            loadAllData();
            System.out.println("Dashboard auto refreshed!");
        });
        refreshTimer.start();
    }
    
    // Public method untuk refresh manual
    public void refreshDashboard() {
        SwingUtilities.invokeLater(() -> {
            loadAllData();
            repaint();
            revalidate();
        });
    }
    
    public void stopAutoRefresh() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
    }
    
    @Override
    public void removeNotify() {
        stopAutoRefresh();
        super.removeNotify();
    }
    
    public void forceRefresh() {
        System.out.println("Force refresh dipanggil...");

        if (refreshTimer != null) {
            refreshTimer.stop();
        }

        Timer forceTimer = new Timer(1000, e -> {
            ((Timer)e.getSource()).stop();

            SwingUtilities.invokeLater(() -> {
                System.out.println("Executing force refresh...");
                loadAllData();
                repaint();
                revalidate();
                System.out.println("Force refresh selesai!");
            });
        });
        forceTimer.setRepeats(false);
        forceTimer.start();

        setupAutoRefresh();
    }

    public void instantRefresh() {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Instant refresh...");
            loadAllData();
            repaint();
            revalidate();
            System.out.println("Instant refresh selesai!");
        });
    }

    public void refreshWithLoading() {
        lblTotMob.setText("...");
        lblTotDriv.setText("...");
        lblMobMas.setText("...");
        lblMobKel.setText("...");

        Timer loadingTimer = new Timer(500, e -> {
            ((Timer)e.getSource()).stop();
            loadAllData();
            System.out.println("Refresh dengan loading selesai!");
        });
        loadingTimer.setRepeats(false);
        loadingTimer.start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTotMobil = new javax.swing.JPanel();
        lblTotMob = new javax.swing.JLabel();
        pnlTotDriv = new javax.swing.JPanel();
        lblTotDriv = new javax.swing.JLabel();
        pnlMobMasuk = new javax.swing.JPanel();
        lblMobMas = new javax.swing.JLabel();
        pnlMobKeluar = new javax.swing.JPanel();
        lblMobKel = new javax.swing.JLabel();

        pnlTotMobil.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        lblTotMob.setText("Total Mobil");

        javax.swing.GroupLayout pnlTotMobilLayout = new javax.swing.GroupLayout(pnlTotMobil);
        pnlTotMobil.setLayout(pnlTotMobilLayout);
        pnlTotMobilLayout.setHorizontalGroup(
            pnlTotMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotMobilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotMob)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        pnlTotMobilLayout.setVerticalGroup(
            pnlTotMobilLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotMobilLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotMob)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        pnlTotDriv.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));

        lblTotDriv.setText("Total Driver");

        javax.swing.GroupLayout pnlTotDrivLayout = new javax.swing.GroupLayout(pnlTotDriv);
        pnlTotDriv.setLayout(pnlTotDrivLayout);
        pnlTotDrivLayout.setHorizontalGroup(
            pnlTotDrivLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotDrivLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotDriv)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        pnlTotDrivLayout.setVerticalGroup(
            pnlTotDrivLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTotDrivLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotDriv)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMobMasuk.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Yellow"));

        lblMobMas.setText("Mobil Masuk");

        javax.swing.GroupLayout pnlMobMasukLayout = new javax.swing.GroupLayout(pnlMobMasuk);
        pnlMobMasuk.setLayout(pnlMobMasukLayout);
        pnlMobMasukLayout.setHorizontalGroup(
            pnlMobMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMobMasukLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMobMas)
                .addContainerGap(146, Short.MAX_VALUE))
        );
        pnlMobMasukLayout.setVerticalGroup(
            pnlMobMasukLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMobMasukLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMobMas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMobKeluar.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Red"));

        lblMobKel.setText("Mobil Keluar");

        javax.swing.GroupLayout pnlMobKeluarLayout = new javax.swing.GroupLayout(pnlMobKeluar);
        pnlMobKeluar.setLayout(pnlMobKeluarLayout);
        pnlMobKeluarLayout.setHorizontalGroup(
            pnlMobKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMobKeluarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMobKel)
                .addContainerGap(140, Short.MAX_VALUE))
        );
        pnlMobKeluarLayout.setVerticalGroup(
            pnlMobKeluarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMobKeluarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMobKel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlTotMobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlTotDriv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlMobMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnlMobKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlMobKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMobMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTotDriv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTotMobil, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(342, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    private JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new java.awt.Font("Segoe UI", 1, 12));
        label.setForeground(java.awt.Color.WHITE);
        return label;
    }

    // Setup panel lainnya
    private void setupOtherPanels() {
        // Setup panel Total Driver
        pnlTotDriv.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        lblTotDriv.setText("0");
        lblTotDriv.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblTotDriv.setForeground(java.awt.Color.WHITE);
        lblTotDriv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Setup panel Mobil Masuk
        pnlMobMasuk.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Yellow"));
        lblMobMas.setText("0");
        lblMobMas.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblMobMas.setForeground(java.awt.Color.WHITE);
        lblMobMas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        // Setup panel Mobil Keluar
        pnlMobKeluar.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Red"));
        lblMobKel.setText("0");
        lblMobKel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblMobKel.setForeground(java.awt.Color.WHITE);
        lblMobKel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblMobKel;
    private javax.swing.JLabel lblMobMas;
    private javax.swing.JLabel lblTotDriv;
    private javax.swing.JLabel lblTotMob;
    private javax.swing.JPanel pnlMobKeluar;
    private javax.swing.JPanel pnlMobMasuk;
    private javax.swing.JPanel pnlTotDriv;
    private javax.swing.JPanel pnlTotMobil;
    // End of variables declaration//GEN-END:variables
    private javax.swing.JLabel jLabel1;
    
}


