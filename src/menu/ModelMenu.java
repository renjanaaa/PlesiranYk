package menu;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ModelMenu {

    public enum MenuType {
        MENU, EMPTY
    }

    private String icon;
    private String name;
    private MenuType type;

    public ModelMenu(String icon, String name, MenuType type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }

    public ModelMenu() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public Icon toIcon() {
        if (icon == null || icon.isEmpty()) {
            return null;
        }
        try {
            // Load PNG icon from icons folder
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("/icons/" + icon + ".png"));
            // Resize icon to 20x20 pixels
            Image img = imageIcon.getImage();
            Image scaledImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImg);
        } catch (Exception e) {
            System.out.println("Icon not found: /icons/" + icon + ".png");
            // Return null if icon not found
            return null;
        }
    }
}