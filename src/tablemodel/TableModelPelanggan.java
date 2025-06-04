package tablemodel;

import model.ModelPelanggan;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelPelanggan extends AbstractTableModel {
    
    private List<ModelPelanggan> list;
    private final String[] columnNames = {"ID", "Nama", "No.KTP", "No.HP", "Email", "Alamat", "Jenis Kelamin", "Status"};
    
    public TableModelPelanggan(List<ModelPelanggan> list) {
        this.list = list;
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
        ModelPelanggan pelanggan = list.get(rowIndex);
        switch (columnIndex) {
            case 0: return pelanggan.getId();
            case 1: return pelanggan.getNama();
            case 2: return pelanggan.getNoKtp();
            case 3: return pelanggan.getNoHp();
            case 4: return pelanggan.getEmail();
            case 5: return pelanggan.getAlamat();
            case 6: return pelanggan.getJenisKelamin();
            case 7: return pelanggan.getStatus();
            default: return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // Tabel tidak bisa diedit langsung
    }
    
    public void setData(List<ModelPelanggan> list) {
        this.list = list;
        fireTableDataChanged();
    }
    
    public ModelPelanggan getPelangganAt(int rowIndex) {
        return list.get(rowIndex);
    }
}