package menu;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import menu.MenuAction;
import net.miginfocom.swing.MigLayout;

public class Menu extends JScrollPane {

    private final List<MenuEvent> events = new ArrayList<>();
    private boolean menuFull = true;
    private final int menuMaxWidth = 280;
    private final int menuMinWidth = 70;
    private JPanel panelMenu = null;
    private JPanel brandPanel = null;

    public Menu() {
        init();
        
        setPreferredSize(new Dimension(UIScale.scale(menuMaxWidth), 0));
    }

    private void init() {
        panelMenu = new JPanel(new MigLayout("wrap, fillx, insets 3", "[fill]", "[]0[]"));
        panelMenu.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.background");
        
        setViewportView(panelMenu);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setBorder(null);
        
        createBrandHeader();
        createMenu();
    }

    private void createBrandHeader() {
        // Panel untuk branding dengan background biru
        brandPanel = new JPanel(new MigLayout("wrap, fillx, insets 15 20 15 20", "[fill]", "[]5[]"));
        brandPanel.setBackground(new Color(52, 152, 219)); // Warna biru modern
        
        // Label untuk nama perusahaan
        JLabel lblBrand = new JLabel("PLESIRAN YK");
        lblBrand.setFont(new Font("Poppins", Font.BOLD, 20));
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setHorizontalAlignment(SwingConstants.LEFT);
        
        // Label untuk tagline
        JLabel lblTagline = new JLabel("Partner Perjalanan Anda");
        lblTagline.setFont(new Font("Poppins", Font.PLAIN, 12));
        lblTagline.setForeground(new Color(255, 255, 255, 200)); // Putih dengan transparansi
        lblTagline.setHorizontalAlignment(SwingConstants.LEFT);
        
        brandPanel.add(lblBrand, "growx");
        brandPanel.add(lblTagline, "growx");
        
        // Tambahkan brand panel ke menu utama
        panelMenu.add(brandPanel, "growx, h 80!");
        
        // Tambahkan separator kecil
        JPanel separator = new JPanel();
        separator.setBackground(new Color(0, 0, 0, 0)); // Transparent
        separator.setPreferredSize(new Dimension(0, 10));
        panelMenu.add(separator, "h 10!");
    }

    private void createMenu() {
        // Dashboard (index 0)
        addMenu(new ModelMenu("dashboard", "Dashboard", ModelMenu.MenuType.MENU), 0);
        
        // Data Master (index 1)
        addMenu(new ModelMenu("database", "Data Master", ModelMenu.MenuType.MENU), 1);
        addSubMenu(new ModelMenu("car", "Data Mobil", ModelMenu.MenuType.MENU), 1, 1);
        addSubMenu(new ModelMenu("user", "Data Pelanggan", ModelMenu.MenuType.MENU), 1, 2);
        addSubMenu(new ModelMenu("driver", "Data Driver", ModelMenu.MenuType.MENU), 1, 3);
        
        // Transaksi (index 2)
        addMenu(new ModelMenu("transaction", "Transaksi", ModelMenu.MenuType.MENU), 2);
        addSubMenu(new ModelMenu("rental", "Penyewaan", ModelMenu.MenuType.MENU), 2, 1);
        addSubMenu(new ModelMenu("return", "Pengembalian", ModelMenu.MenuType.MENU), 2, 2);
        
        // Laporan (index 3)
        addMenu(new ModelMenu("report", "Laporan", ModelMenu.MenuType.MENU), 3);
        addSubMenu(new ModelMenu("rentalreport", "Laporan Penyewaan", ModelMenu.MenuType.MENU), 3, 1);
        
        // Logout (index 11)
        addMenu(new ModelMenu("logout", "Logout", ModelMenu.MenuType.MENU), 11);
    }

    private void addMenu(ModelMenu menu, int index) {
        PanelMenu panelMenuItem = new PanelMenu(menu, events, index, menuFull);
        panelMenu.add(panelMenuItem, "h 45!");
    }

    private void addSubMenu(ModelMenu menu, int parentIndex, int subIndex) {
        PanelMenu panelMenuItem = new PanelMenu(menu, events, parentIndex, subIndex, menuFull);
        panelMenuItem.setSubMenu(true);
        panelMenu.add(panelMenuItem, "h 40!");
    }

    public void addMenuEvent(MenuEvent event) {
        events.add(event);
    }

    public void setMenuFull(boolean menuFull) {
        this.menuFull = true;
    }

    private void updateMenuWidth() {
        revalidate();
        repaint();
    }

    public boolean isMenuFull() {
        return menuFull;
    }

    public int getMenuMaxWidth() {
        return menuMaxWidth;
    }

    public int getMenuMinWidth() {
        return menuMinWidth;
    }

    public void hideMenuItem() {
        for (Component component : panelMenu.getComponents()) {
            component.setVisible(false);
        }
    }

    public void setSelectedMenu(int index, int subIndex, MenuAction action) {
        runEvent(index, subIndex, action);
    }

    public void setSelectedMenu(int index, int subIndex) {
        runEvent(index, subIndex, null);
    }

    private void runEvent(int index, int subIndex, MenuAction action) {
        MenuAction act = action;
        if (act == null) {
            act = new MenuAction();
        }
        for (MenuEvent event : events) {
            event.menuSelected(index, subIndex, act);
        }
    }
}