package main;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import form.FormDashboard;
import form.FormDriver;
import form.FormLaporanMobil;
import form.FormLaporanPenyewaan;
import form.FormMobil;
import form.FormPelanggan;
import form.FormPengembalian;
import form.FormPenyewaan;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import menu.Menu;
import menu.MenuAction;
import menu.MenuEvent;
import menu.PanelMenu;

public class Main extends JPanel {

    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton; // Kita tetap deklarasi tapi tidak akan ditampilkan

    public Main() {
        init();
        showForm(new FormDashboard());
    }

    private void init() {
        // Gunakan BorderLayout untuk layout utama
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0)); // Hapus semua border
        
        menu = new Menu();
        panelBody = new JPanel(new BorderLayout());
        
        // Tambahkan menu dan panel body langsung ke layout
        add(menu, BorderLayout.WEST);
        add(panelBody, BorderLayout.CENTER);
        
        // Inisialisasi menu event
        initMenuEvent();
    }

    private void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            switch (index) {
                case 0: // Dashboard
                    showForm(new FormDashboard());
                    break;
                case 1: // Data Master
                    handleDataMasterMenu(subIndex, action);
                    break;
                case 2: // Transaksi
                    handleTransaksiMenu(subIndex, action);
                    break;
                case 3:
                    handleLaporanMenu(subIndex, action);
                    break;
                case 11: // Logout
                    handleLogout();
                    break;
                default:
                    if (action != null) action.cancel();
                    break;
            }
        });
    }

    private void handleDataMasterMenu(int subIndex, MenuAction action) {
        switch (subIndex) {
            case 1: // Data Mobil
                showForm(new FormMobil());
                break;
            case 2: // Data Pelanggan
                showForm(new FormPelanggan());
                break;
            case 3: // Data Driver
                showForm(new FormDriver());
                break;
            default:
                if (action != null) action.cancel();
                break;
        }
    }

    private void handleTransaksiMenu(int subIndex, MenuAction action) {
        switch (subIndex) {
            case 1: // Penyewaan
                showForm(new FormPenyewaan());
                break;
            case 2: // Pengembalian
                showForm(new FormPengembalian());
                break;
            default:
                if (action != null) action.cancel();
                break;
        }
    }
    
    private void handleLaporanMenu(int subIndex, MenuAction action) {
        switch (subIndex) {
            case 1: // Laporan Penyewaan
                showForm(new FormLaporanPenyewaan());
                break;
            case 2: // Laporan Mobil - INI YANG HILANG!
                showForm(new FormLaporanMobil());
                break;
            default:
                if (action != null) action.cancel();
                break;
        }
    }

    private void handleLogout() {
        int option = javax.swing.JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin keluar?", 
            "Konfirmasi Logout", 
            javax.swing.JOptionPane.YES_NO_OPTION
        );
        if (option == javax.swing.JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component) {
        panelBody.removeAll();
        panelBody.add(component, BorderLayout.CENTER);
        panelBody.revalidate();
        panelBody.repaint();
        component.applyComponentOrientation(getComponentOrientation());
    }

    public void setSelectedMenu(int index, int subIndex, MenuAction action) {
        menu.setSelectedMenu(index, subIndex, action);
    }

    public void setSelectedMenu(int index, int subMenu) {
        menu.setSelectedMenu(index, subMenu, null);
    }
}