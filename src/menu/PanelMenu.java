package menu;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;

public class PanelMenu extends JPanel {

    private final ModelMenu menu;
    private final List<MenuEvent> events;
    private final int index;
    private final int subIndex;
    private boolean menuFull;
    private boolean subMenu;
    private boolean selected;

    public PanelMenu(ModelMenu menu, List<MenuEvent> events, int index, boolean menuFull) {
        this(menu, events, index, -1, menuFull);
    }

    public PanelMenu(ModelMenu menu, List<MenuEvent> events, int index, int subIndex, boolean menuFull) {
        this.menu = menu;
        this.events = events;
        this.index = index;
        this.subIndex = subIndex;
        this.menuFull = menuFull;
        init();
    }

    private void init() {
        setLayout(new MigLayout("fill, insets 0", "[fill]", "[fill]"));
        setOpaque(false);

        if (menu.getType() == ModelMenu.MenuType.MENU) {
            JButton button = new JButton();
            button.setIcon(menu.toIcon());
            button.setText(menu.getName());
            button.setHorizontalAlignment(JButton.LEADING);
            button.setBorder(new EmptyBorder(12, 15, 12, 15));
            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            
            // Set font to be larger and bold
            Font currentFont = button.getFont();
            Font newFont = new Font(currentFont.getName(), Font.BOLD, 14);
            button.setFont(newFont);

            button.putClientProperty(FlatClientProperties.STYLE, ""
                    + "foreground:$Menu.foreground;"
                    + "background:$Menu.background;"
                    + "selectedBackground:$Menu.button.selectedBackground;"
                    + "hoverBackground:$Menu.button.hoverBackground");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (events != null) {
                        for (MenuEvent event : events) {
                            event.menuSelected(index, subIndex, new MenuAction());
                        }
                    }
                }
            });

            add(button, "grow");
        }
    }

    public void setMenuFull(boolean menuFull) {
        this.menuFull = menuFull;
        repaint();
    }

    public void setSubMenu(boolean subMenu) {
        this.subMenu = subMenu;
        if (subMenu) {
            setBorder(new EmptyBorder(0, 20, 0, 0));
            // Make submenu text slightly smaller but still bold
            if (getComponentCount() > 0 && getComponent(0) instanceof JButton) {
                JButton button = (JButton) getComponent(0);
                Font currentFont = button.getFont();
                Font newFont = new Font(currentFont.getName(), Font.BOLD, 12);
                button.setFont(newFont);
            }
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (selected) {
            g2.setColor(new Color(255, 255, 255, 50));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        }

        g2.dispose();
    }
}