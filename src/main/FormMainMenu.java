
package main;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import form.FormDashboard;
import form.FormDriver;
import form.FormLogin;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import menu.MenuAction;

public class FormMainMenu extends JFrame {
    
    private final FormLogin formLogin;
    private static FormMainMenu app;
    private final Main mainForm;
    public FormMainMenu() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        formLogin = new FormLogin();
        mainForm = new Main();
        setContentPane(formLogin);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMainContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout panelMainContentLayout = new javax.swing.GroupLayout(panelMainContent);
        panelMainContent.setLayout(panelMainContentLayout);
        panelMainContentLayout.setHorizontalGroup(
            panelMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panelMainContentLayout.setVerticalGroup(
            panelMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMainContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMainContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static void main(String args[]) {
        FlatLightLaf.setup();
        java.awt.EventQueue.invokeLater(() -> {
            app = new FormMainMenu();
            app.setVisible(true);
        });
    }
    
    public void showForm(Component component){
        component.applyComponentOrientation(getComponentOrientation());
        app.mainForm.showForm(component);
    }
    
     public static void login(){
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainForm);
        app.mainForm.applyComponentOrientation(app.getComponentOrientation());
        setSelectedMenu(0,0);
        SwingUtilities.updateComponentTreeUI(app.mainForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }
     
    public static void logout() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.formLogin);
        app.formLogin.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.formLogin);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }
     
     public static void setSelectedMenu(int index, int subMenu) {
        app.mainForm.setSelectedMenu(index,subMenu);
    }

    public void showSignUp() {
        FlatAnimatedLafChange.showSnapshot();
        setContentPane(new form.FormSignUp());
        SwingUtilities.updateComponentTreeUI(this);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    public void loginSuccess() {
        FlatAnimatedLafChange.showSnapshot();
        app.setContentPane(app.mainForm); // ini sekarang JPanel valid
        app.mainForm.applyComponentOrientation(app.getComponentOrientation());
        SwingUtilities.updateComponentTreeUI(app.mainForm);
        FlatAnimatedLafChange.hideSnapshotWithAnimation();

        app.mainForm.showForm(new FormDashboard());
    }

    

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelMainContent;
    // End of variables declaration//GEN-END:variables

}
