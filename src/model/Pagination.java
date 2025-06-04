package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class Pagination extends JPanel {
    
    private JButton btnFirst, btnPrevious, btnNext, btnLast;
    private JLabel lblPageInfo;
    private JComboBox<Integer> cmbPageSize;
    private int currentPage = 1;
    private int totalPages = 1;
    private int totalRecords = 0;
    private int pageSize = 10;
    
    private ActionListener pageChangeListener;
    
    public Pagination() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0)); // Reduced vertical spacing
        setBackground(new Color(248, 249, 250));
        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Reduced top/bottom padding
        
        // First Page Button
        btnFirst = createButton("<<", "Halaman Pertama");
        
        // Previous Page Button
        btnPrevious = createButton("<", "Halaman Sebelumnya");
        
        // Page Info Label
        lblPageInfo = new JLabel();
        lblPageInfo.setFont(new Font("Poppins", Font.PLAIN, 12));
        lblPageInfo.setForeground(new Color(73, 80, 87));
        lblPageInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        // Next Page Button
        btnNext = createButton(">", "Halaman Selanjutnya");
        
        // Last Page Button
        btnLast = createButton(">>", "Halaman Terakhir");
        
        // Create a panel for page size selection with BoxLayout
        JPanel pageSizePanel = new JPanel();
        pageSizePanel.setLayout(new BoxLayout(pageSizePanel, BoxLayout.X_AXIS));
        pageSizePanel.setBackground(new Color(248, 249, 250));
        pageSizePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        
        // Page Size Label
        JLabel lblPageSize = new JLabel("Tampilkan:");
        lblPageSize.setFont(new Font("Poppins", Font.PLAIN, 12));
        lblPageSize.setForeground(new Color(73, 80, 87));
        
        // Page Size ComboBox
        cmbPageSize = new JComboBox<>(new Integer[]{5, 10, 25, 50, 100});
        cmbPageSize.setSelectedItem(pageSize);
        cmbPageSize.setFont(new Font("Poppins", Font.PLAIN, 12));
        cmbPageSize.setPreferredSize(new Dimension(60, 25));
        cmbPageSize.setMaximumSize(new Dimension(60, 25));
        cmbPageSize.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        cmbPageSize.setBackground(Color.WHITE);
        cmbPageSize.addActionListener(e -> {
            pageSize = (Integer) cmbPageSize.getSelectedItem();
            currentPage = 1; // Reset ke halaman pertama
            updatePagination();
            if (pageChangeListener != null) {
                pageChangeListener.actionPerformed(e);
            }
        });
        
        // Per Page Label
        JLabel lblPerPage = new JLabel("per halaman");
        lblPerPage.setFont(new Font("Poppins", Font.PLAIN, 12));
        lblPerPage.setForeground(new Color(73, 80, 87));
        
        // Add components to page size panel with spacing
        pageSizePanel.add(lblPageSize);
        pageSizePanel.add(Box.createRigidArea(new Dimension(5, 0)));
        pageSizePanel.add(cmbPageSize);
        pageSizePanel.add(Box.createRigidArea(new Dimension(5, 0)));
        pageSizePanel.add(lblPerPage);
        
        // Add action listeners to buttons
        btnFirst.addActionListener(e -> goToPage(1));
        btnPrevious.addActionListener(e -> goToPage(currentPage - 1));
        btnNext.addActionListener(e -> goToPage(currentPage + 1));
        btnLast.addActionListener(e -> goToPage(totalPages));
        
        // Create a panel for navigation buttons to ensure vertical alignment
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
        navPanel.setBackground(new Color(248, 249, 250));
        navPanel.add(btnFirst);
        navPanel.add(btnPrevious);
        navPanel.add(lblPageInfo);
        navPanel.add(btnNext);
        navPanel.add(btnLast);
        
        // Add components to main panel
        add(navPanel);
        add(pageSizePanel);
        
        updatePagination();
    }
    
    private JButton createButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(30, 25));
        button.setFont(new Font("Poppins", Font.BOLD, 12));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        button.setFocusPainted(false);
        button.setToolTipText(tooltip);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(41, 128, 185));
                }
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(new Color(52, 152, 219));
                }
            }
        });
        
        return button;
    }
    
    private void goToPage(int page) {
        if (page >= 1 && page <= totalPages && page != currentPage) {
            currentPage = page;
            updatePagination();
            if (pageChangeListener != null) {
                pageChangeListener.actionPerformed(null);
            }
        }
    }
    
    private void updatePagination() {
        // Update button states
        btnFirst.setEnabled(currentPage > 1);
        btnPrevious.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < totalPages);
        btnLast.setEnabled(currentPage < totalPages);
        
        // Update disabled button appearance
        updateButtonAppearance(btnFirst);
        updateButtonAppearance(btnPrevious);
        updateButtonAppearance(btnNext);
        updateButtonAppearance(btnLast);
        
        // Update page info
        int startRecord = totalRecords == 0 ? 0 : (currentPage - 1) * pageSize + 1;
        int endRecord = Math.min(currentPage * pageSize, totalRecords);
        
        lblPageInfo.setText(String.format("Halaman %d dari %d (%d-%d dari %d data)", 
            currentPage, totalPages, startRecord, endRecord, totalRecords));
    }
    
    private void updateButtonAppearance(JButton button) {
        if (button.isEnabled()) {
            button.setBackground(new Color(52, 152, 219));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            button.setBackground(new Color(173, 216, 230));
            button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
    
    // Public methods untuk mengatur pagination
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
        this.totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages == 0) totalPages = 1;
        if (currentPage > totalPages) currentPage = totalPages;
        updatePagination();
    }
    
    public void setPageChangeListener(ActionListener listener) {
        this.pageChangeListener = listener;
    }
    
    public int getCurrentPage() {
        return currentPage;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }
    
    public void reset() {
        currentPage = 1;
        updatePagination();
    }
}