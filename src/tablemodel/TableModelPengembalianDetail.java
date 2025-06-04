package tablemodel;

import model.ModelPengembalianDetail;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelPengembalianDetail extends AbstractTableModel {
    private final List<ModelPengembalianDetail> list;

    private final String[] columnNames = {"No Detail", "No Pengembalian", "ID Mobil", "Denda", "Keterangan"};

    public TableModelPengembalianDetail(List<ModelPengembalianDetail> list) {
        this.list = list;
    }

    public int getRowCount() {
        return list.size();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        ModelPengembalianDetail model = list.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> model.getNoDetail();
            case 1 -> model.getNoPengembalian();
            case 2 -> model.getIdMobil();
            case 3 -> model.getDenda();
            case 4 -> model.getKeterangan();
            default -> null;
        };
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}