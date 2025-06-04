package form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import form.FormLogin;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import koneksi.koneksi;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import main.FormMainMenu;
import java.security.MessageDigest;


public class FormSignUp extends JPanel {
    
    public FormSignUp() {
        initComponents();
        setLayoutForm();

        btnSign.addActionListener(this::handleSignUp);
        btnBackLogin.addActionListener(e -> {
        FormMainMenu.login(); 
        });
    }

    private void handleSignUp(ActionEvent e) {
        String username = txtUsname.getText().trim();
        String password = new String(txtPass.getPassword());
        String confirm = new String(txtConfPass.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field harus diisi");
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Password tidak cocok");
            return;
        }

        try (Connection conn = koneksi.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username sudah terpakai");
                return;
            }

            PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            insertStmt.setString(1, username);
            insertStmt.setString(2, password);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Akun berhasil dibuat. Silakan login.");
            
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(new FormLogin());
            frame.revalidate();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat signup");
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
        }
            return sb.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLogin = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        lblPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        btnSign = new javax.swing.JButton();
        lblConfPass = new javax.swing.JLabel();
        txtConfPass = new javax.swing.JPasswordField();
        btnBackLogin = new javax.swing.JButton();
        lblUsname = new javax.swing.JLabel();
        txtUsname = new javax.swing.JTextField();

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/rental.png"))); // NOI18N

        lblTitle.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("SignUp to PlesiranYk");

        lblNama.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblNama.setText("Nama Lengkap");

        lblPass.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblPass.setText("Password");

        btnSign.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        btnSign.setText("Sign Up");
        btnSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignActionPerformed(evt);
            }
        });

        lblConfPass.setText("Confirm Password");

        txtConfPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtConfPassActionPerformed(evt);
            }
        });

        btnBackLogin.setText("Back to Login");
        btnBackLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackLoginActionPerformed(evt);
            }
        });

        lblUsname.setText("Username");

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLoginLayout.createSequentialGroup()
                        .addComponent(lblConfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNama)
                    .addComponent(txtPass, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtConfPass)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoginLayout.createSequentialGroup()
                        .addGap(0, 73, Short.MAX_VALUE)
                        .addComponent(btnSign)
                        .addGap(18, 18, 18)
                        .addComponent(btnBackLogin)
                        .addGap(0, 73, Short.MAX_VALUE))
                    .addComponent(lblPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUsname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsname))
                .addContainerGap())
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblUsname)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtUsname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblConfPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtConfPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSign)
                    .addComponent(btnBackLogin))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(174, Short.MAX_VALUE)
                .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(175, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignActionPerformed
    String username = txtUsname.getText().trim();
    String password = new String(txtPass.getPassword());
    String confirm = new String(txtConfPass.getPassword());

    if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
        return;
    }

    if (!password.equals(confirm)) {
        JOptionPane.showMessageDialog(this, "Password tidak cocok!");
        return;
    }

    try (Connection conn = koneksi.getConnection()) {
        PreparedStatement check = conn.prepareStatement("SELECT * FROM users WHERE username=?");
        check.setString(1, username);
        ResultSet rs = check.executeQuery();

        if (rs.next()) {
            JOptionPane.showMessageDialog(this, "Username sudah digunakan!");
            return;
        }   

    PreparedStatement insert = conn.prepareStatement("INSERT INTO users(username, password) VALUES (?, ?)");
    insert.setString(1, username);
    insert.setString(2, password);
    insert.executeUpdate();

    JOptionPane.showMessageDialog(this, "Pendaftaran berhasil!");

    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FormSignUp.this);
    frame.setContentPane(new FormLogin());
    frame.revalidate();
    frame.repaint();

    } catch (Exception e) {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan ke database.\n" + e.getMessage());
    }
    }//GEN-LAST:event_btnSignActionPerformed

    private void txtConfPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtConfPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtConfPassActionPerformed

    private void btnBackLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackLoginActionPerformed
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FormSignUp.this);
        frame.setContentPane(new FormLogin());
        frame.revalidate();
        frame.repaint();
    }//GEN-LAST:event_btnBackLoginActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackLogin;
    private javax.swing.JButton btnSign;
    private javax.swing.JLabel lblConfPass;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblNama;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUsname;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPasswordField txtConfPass;
    private javax.swing.JTextField txtNama;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUsname;
    // End of variables declaration//GEN-END:variables
    private void setLayoutForm(){
        setLayout((LayoutManager) new FormSignUpLayout());
        pnlLogin.setLayout(new SignUpLayout());
        
        // Enhanced title styling
        lblTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +4;"
                + "foreground:#2c3e50");
        
        // Enhanced panel styling with shadow and modern look
        pnlLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:#ffffff;"
                + "arc:25;"
                + "border:40,50,50,40;"
                + "borderColor:#e8ecf0;"
                + "borderWidth:1");
        
        // Enhanced input field styling
        String inputStyle = ""
                + "arc:10;"
                + "borderWidth:1;"
                + "borderColor:#bdc3c7;"
                + "focusedBorderColor:#3498db;"
                + "background:#ffffff;"
                + "margin:8,12,8,12;"
                + "minimumHeight:40";
        
        txtNama.putClientProperty(FlatClientProperties.STYLE, inputStyle);
        txtUsname.putClientProperty(FlatClientProperties.STYLE, inputStyle);
        
        txtPass.putClientProperty(FlatClientProperties.STYLE, inputStyle + ";"
                + "showRevealButton:true;"
                + "showCapsLock:true");
        
        txtConfPass.putClientProperty(FlatClientProperties.STYLE, inputStyle + ";"
                + "showRevealButton:true;"
                + "showCapsLock:true");
        
        // Enhanced sign up button styling
        btnSign.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "arc:10;"
                + "background:#3498db;"
                + "hoverBackground:#2980b9;"
                + "pressedBackground:#21618c;"
                + "foreground:#ffffff;"
                + "minimumHeight:40;"
                + "font:bold");
        
        // Enhanced back to login button styling
        btnBackLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:2;"
                + "borderColor:#3498db;"
                + "focusWidth:0;"
                + "arc:10;"
                + "background:#ffffff;"
                + "hoverBackground:#ecf0f1;"
                + "pressedBackground:#d5dbdb;"
                + "foreground:#3498db;"
                + "minimumHeight:40;"
                + "font:bold");
        
        // Enhanced label styling
        String labelStyle = ""
                + "font:bold;"
                + "foreground:#34495e";
        
        lblNama.putClientProperty(FlatClientProperties.STYLE, labelStyle);
        lblUsname.putClientProperty(FlatClientProperties.STYLE, labelStyle);
        lblPass.putClientProperty(FlatClientProperties.STYLE, labelStyle);
        lblConfPass.putClientProperty(FlatClientProperties.STYLE, labelStyle);
    }
    
    private class FormSignUpLayout implements LayoutManager{

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0,0); 
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5,5); 
            }
        }
        
        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                int width = parent.getWidth();
                int height = parent.getHeight();
                int loginWidth = UIScale.scale(450);
                int loginHeight = pnlLogin.getPreferredSize().height;
                int x = (width - loginWidth) / 2;
                int y = (height - loginHeight) / 2;
                pnlLogin.setBounds(x, y, loginWidth, loginHeight);
            }
        }
    }
    
    private class SignUpLayout implements LayoutManager{
        
        private final int titleGap = 15;
        private final int textGap = 12;
        private final int labelGap = 8;
        private final int buttonGap = 30;

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int height = insets.top + insets.bottom;
                
                height += lblLogo.getPreferredSize().height;
                height += UIScale.scale(titleGap);
                height += lblTitle.getPreferredSize().height;
                height += UIScale.scale(titleGap);
                
                // 4 input fields with labels
                height += (lblNama.getPreferredSize().height + UIScale.scale(labelGap) + 40 + UIScale.scale(textGap)) * 4;
                height += UIScale.scale(buttonGap);
                height += 40; // Button height
                
                return new Dimension(0, height);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0,0); 
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            Insets insets = parent.getInsets();
            int x = insets.left;
            int y = insets.top;
            int width = parent.getWidth() - (insets.left + insets.right);

            lblLogo.setBounds(x, y, width, lblLogo.getPreferredSize().height);
            y += lblLogo.getPreferredSize().height + UIScale.scale(titleGap);
    
            lblTitle.setBounds(x, y, width, lblTitle.getPreferredSize().height);
            y += lblTitle.getPreferredSize().height + UIScale.scale(titleGap);

            // Nama Lengkap
            lblNama.setBounds(x, y, width, lblNama.getPreferredSize().height);
            y += lblNama.getPreferredSize().height + UIScale.scale(labelGap);
            txtNama.setBounds(x, y, width, 40);
            y += 40 + UIScale.scale(textGap);

            // Username
            lblUsname.setBounds(x, y, width, lblUsname.getPreferredSize().height);
            y += lblUsname.getPreferredSize().height + UIScale.scale(labelGap);
            txtUsname.setBounds(x, y, width, 40);
            y += 40 + UIScale.scale(textGap);

            // Password
            lblPass.setBounds(x, y, width, lblPass.getPreferredSize().height);
            y += lblPass.getPreferredSize().height + UIScale.scale(labelGap);
            txtPass.setBounds(x, y, width, 40);
            y += 40 + UIScale.scale(textGap);

            // Confirm Password
            lblConfPass.setBounds(x, y, width, lblConfPass.getPreferredSize().height);
            y += lblConfPass.getPreferredSize().height + UIScale.scale(labelGap);
            txtConfPass.setBounds(x, y, width, 40);
            y += 40 + UIScale.scale(buttonGap);

            // Buttons
            int buttonWidth = (width - UIScale.scale(15)) / 2;
            int buttonHeight = 40;

            btnSign.setBounds(x, y, buttonWidth, buttonHeight);
            btnBackLogin.setBounds(x + buttonWidth + UIScale.scale(15), y, buttonWidth, buttonHeight);
        }
    }
}
