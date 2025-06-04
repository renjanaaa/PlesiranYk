package tablemodel;

import model.ModelMobil;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

public class TableModelMobil extends AbstractTableModel {
    
    private List<ModelMobil> list;
    private final String[] columnNames = {"ID", "No. Polisi", "Merk", "Model", "Kapasitas", "Harga Sewa/Hari", "Status"};
    private NumberFormat currencyFormat;
    
    public TableModelMobil(List<ModelMobil> list) {
        this.list = list;
        // Inisialisasi format currency Indonesia
        this.currencyFormat = NumberFormat.getNumberInstance(new Locale("id", "ID"));
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
        ModelMobil mobil = list.get(rowIndex);
        switch (columnIndex) {
            case 0: return mobil.getId();
            case 1: return mobil.getNoPolisi();
            case 2: return mobil.getMerk();
            case 3: return mobil.getModel();
            case 4: return mobil.getKapasitasPenumpang();
            case 5: return currencyFormat.format(mobil.getHargaSewaPerHari()); // Format harga dengan pemisah ribuan
            case 6: return mobil.getStatus();
            default: return null;
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // Tabel tidak bisa diedit langsung
    }
    
    public void setData(List<ModelMobil> list) {
        this.list = list;
        fireTableDataChanged();
    }
    
    public ModelMobil getMobilAt(int rowIndex) {
        return list.get(rowIndex);
    }
}