package tablemodel;

import model.ModelPenyewaan;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TableModelPenyewaan extends AbstractTableModel {
    
    private List<ModelPenyewaan> list;
    private final String[] columnNames = {
        "Kode", "Tanggal Sewa", "Tanggal Kembali", "Pelanggan", 
        "Driver", "Total Biaya", "Status"
    };
    private NumberFormat currencyFormat;
    private SimpleDateFormat dateFormat;
    
    public TableModelPenyewaan(List<ModelPenyewaan> list) {
        this.list = list;
        this.currencyFormat = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    @Override
    public int getRowCount() {
        return list.size();
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
        ModelPenyewaan penyewaan = list.get(rowIndex);
        switch (columnIndex) {
            case 0: return penyewaan.getKodePenyewaan();
            case 1: return penyewaan.getTanggalSewa() != null ? 
                          dateFormat.format(penyewaan.getTanggalSewa()) : "";
            case 2: return penyewaan.getTanggalKembali() != null ? 
                          dateFormat.format(penyewaan.getTanggalKembali()) : "";
            case 3: return penyewaan.getNamaPelanggan();
            case 4: return penyewaan.getNamaDriver();
            case 5: return "Rp " + currencyFormat.format(penyewaan.getTotalBiaya());
            case 6: return penyewaan.getStatus();
            default: return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
    
    public void setData(List<ModelPenyewaan> list) {
        this.list = list;
        fireTableDataChanged();
    }
    
    public ModelPenyewaan getPenyewaanAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < list.size()) {
            return list.get(rowIndex);
        }
        return null;
    }
    
    public void addPenyewaan(ModelPenyewaan penyewaan) {
        list.add(penyewaan);
        fireTableRowsInserted(list.size() - 1, list.size() - 1);
    }
    
    public void updatePenyewaan(int rowIndex, ModelPenyewaan penyewaan) {
        if (rowIndex >= 0 && rowIndex < list.size()) {
            list.set(rowIndex, penyewaan);
            fireTableRowsUpdated(rowIndex, rowIndex);
        }
    }
    
    public void removePenyewaan(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < list.size()) {
            list.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
    
    public void clear() {
        int size = list.size();
        if (size > 0) {
            list.clear();
            fireTableRowsDeleted(0, size - 1);
        }
    }
}