
package form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import koneksi.koneksi;
import main.FormMainMenu;

public class FormLogin extends javax.swing.JPanel {

    public FormLogin() {
        initComponents();
        setLayoutForm();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLogin = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lblPass = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        btnSignUp = new javax.swing.JButton();

        pnlLogin.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));

        lblLogo.setForeground(new java.awt.Color(255, 255, 255));
        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/rental.png"))); // NOI18N

        lblTitle.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("Login to PlesiranYk");

        lblUser.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setText("Username");

        lblPass.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        lblPass.setForeground(new java.awt.Color(255, 255, 255));
        lblPass.setText("Password");

        btnLogin.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        btnLogin.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnSignUp.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        btnSignUp.setForeground(javax.swing.UIManager.getDefaults().getColor("Actions.Blue"));
        btnSignUp.setText("SignUp");
        btnSignUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignUpActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLoginLayout = new javax.swing.GroupLayout(pnlLogin);
        pnlLogin.setLayout(pnlLoginLayout);
        pnlLoginLayout.setHorizontalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUser)
                    .addComponent(txtPass, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlLoginLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnLogin)
                        .addGap(18, 18, 18)
                        .addComponent(btnSignUp)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlLoginLayout.setVerticalGroup(
            pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoginLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(lblUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPass)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(pnlLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogin)
                    .addComponent(btnSignUp))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(198, Short.MAX_VALUE)
                .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(198, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(99, Short.MAX_VALUE)
                .addComponent(pnlLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Username dan Password harus diisi!");
            return;
        }

        try (java.sql.Connection conn = koneksi.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            java.sql.ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Login berhasil!");
            FormMainMenu main = (FormMainMenu) javax.swing.SwingUtilities.getWindowAncestor(this);
            main.loginSuccess(); // ganti sesuai logika halaman selanjutnya
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Username atau password salah");
            }
        } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat login: " + e.getMessage());
    }   
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignUpActionPerformed
        FormMainMenu main = (FormMainMenu) SwingUtilities.getWindowAncestor(this);
        main.showSignUp();
    }//GEN-LAST:event_btnSignUpActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnSignUp;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUser;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
private void setLayoutForm(){
        setLayout(new FormLoginLayout());
        pnlLogin.setLayout(new LoginLayout());
        
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
        txtUser.putClientProperty(FlatClientProperties.STYLE, ""
                + "arc:10;"
                + "borderWidth:1;"
                + "borderColor:#bdc3c7;"
                + "focusedBorderColor:#3498db;"
                + "background:#ffffff;"
                + "margin:8,12,8,12;"
                + "minimumHeight:40");
        
        txtPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true;"
                + "arc:10;"
                + "borderWidth:1;"
                + "borderColor:#bdc3c7;"
                + "focusedBorderColor:#3498db;"
                + "background:#ffffff;"
                + "margin:8,12,8,12;"
                + "minimumHeight:40");
        
        // Enhanced login button styling
        btnLogin.putClientProperty(FlatClientProperties.STYLE, ""
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "arc:10;"
                + "background:#3498db;"
                + "hoverBackground:#2980b9;"
                + "pressedBackground:#21618c;"
                + "foreground:#ffffff;"
                + "minimumHeight:40;"
                + "font:bold");
        
        // Enhanced signup button styling
        btnSignUp.putClientProperty(FlatClientProperties.STYLE, ""
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
        
        // Add subtle shadow effect to the main panel
        setBorder(BorderFactory.createEmptyBorder());
        
        // Enhanced label styling
        lblUser.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold;"
                + "foreground:#34495e");
        
        lblPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold;"
                + "foreground:#34495e");
    }
    
    private class FormLoginLayout implements LayoutManager{

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
                int loginWidth = UIScale.scale(450); // Slightly wider for better proportions
                int loginHeight = pnlLogin.getPreferredSize().height;
                int x = (width - loginWidth) / 2;
                int y = (height - loginHeight) / 2;
                pnlLogin.setBounds(x, y, loginWidth, loginHeight);
            }
        }
        
    }
    
    private class LoginLayout implements LayoutManager{
        
        private final int titleGap = 15; // Increased spacing
        private final int textGap = 15;  // Increased spacing
        private final int labelGap = 8;  // Slightly reduced
        private final int buttonGap = 35; // Increased spacing

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
                height += lblUser.getPreferredSize().height;
                height += UIScale.scale(labelGap);
                height += 40; // Fixed height for input fields
                height += UIScale.scale(textGap);

                height += lblPass.getPreferredSize().height;
                height += UIScale.scale(labelGap);
                height += 40; // Fixed height for input fields
                height += UIScale.scale(buttonGap);
                height += 40; // Fixed height for buttons
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

            lblUser.setBounds(x, y, width, lblUser.getPreferredSize().height);
            y += lblUser.getPreferredSize().height + UIScale.scale(labelGap);
            txtUser.setBounds(x, y, width, 40); // Fixed height
            y += 40 + UIScale.scale(textGap);

            lblPass.setBounds(x, y, width, lblPass.getPreferredSize().height);
            y += lblPass.getPreferredSize().height + UIScale.scale(labelGap);
            txtPass.setBounds(x, y, width, 40); // Fixed height
            y += 40 + UIScale.scale(buttonGap);

            int buttonWidth = (width - UIScale.scale(15)) / 2; // Slightly more spacing
            int buttonHeight = 40; // Fixed height

            btnLogin.setBounds(x, y, buttonWidth, buttonHeight);
            btnSignUp.setBounds(x + buttonWidth + UIScale.scale(15), y, buttonWidth, buttonHeight);
        }
    }
}