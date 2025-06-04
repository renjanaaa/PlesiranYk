package dialog;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class OverdueTableCellRenderer extends DefaultTableCellRenderer {
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component component = super.getTableCellRendererComponent(
            table, value, isSelected, hasFocus, row, column);
        
        // Check if this row represents an overdue rental
        try {
            Object jatuhTempoObj = table.getValueAt(row, 4); // Tanggal Jatuh Tempo column
            if (jatuhTempoObj != null && !jatuhTempoObj.toString().isEmpty()) {
                Date jatuhTempo = dateFormat.parse(jatuhTempoObj.toString());
                Date today = new Date();
                
                if (jatuhTempo.before(today)) {
                    // Overdue - highlight in red
                    if (!isSelected) {
                        component.setBackground(new Color(255, 235, 235)); // Light red
                        component.setForeground(new Color(139, 0, 0)); // Dark red
                    }
                } else {
                    // Not overdue - normal colors
                    if (!isSelected) {
                        component.setBackground(Color.WHITE);
                        component.setForeground(Color.BLACK);
                    }
                }
            }
        } catch (Exception e) {
            // If parsing fails, use normal colors
            if (!isSelected) {
                component.setBackground(Color.WHITE);
                component.setForeground(Color.BLACK);
            }
        }
        
        // Keep selection colors
        if (isSelected) {
            component.setBackground(new Color(52, 152, 219));
            component.setForeground(Color.WHITE);
        }
        
        return component;
    }
}