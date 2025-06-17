
package tablemodel;

import model.ModelDriver;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelDriver extends AbstractTableModel {
    private List<ModelDriver> data;
    private final String[] columnNames = {"ID", "Nama", "No KTP", "No HP", "Alamat", "Status"};

    public TableModelDriver(List<ModelDriver> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelDriver driver = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return driver.getId();
            case 1: return driver.getNama();
            case 2: return driver.getNoKtp();
            case 3: return driver.getNoHp();
            case 4: return driver.getAlamat();
            case 5: return driver.getStatus(); // Pastikan ini benar
            default: return null;
        }
    }

    public void setData(List<ModelDriver> data) {
        this.data = data;
        fireTableDataChanged();
    }

    public ModelDriver getDriverAt(int rowIndex) {
        return data.get(rowIndex);
    }
}