package tablemodel;

import model.ModelPengembalian;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

public class TableModelPengembalian extends AbstractTableModel {
    private List<ModelPengembalian> data;
    private String[] kolom = {
        "ID", "ID Pelanggan", "ID Driver", 
        "Tanggal Sewa", "Tanggal Kembali", 
        "Total Biaya", "Status"
    };

    public TableModelPengembalian(List<ModelPengembalian> data) {
        this.data = data;
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return kolom.length;
    }

    public String getColumnName(int col) {
        return kolom[col];
    }

    public Object getValueAt(int row, int col) {
        ModelPengembalian p = data.get(row);
        switch (col) {
             case 0: return p.getId();
            case 1: return p.getIdPenyewaan();
            case 2: return p.getTanggalKembaliRiil();
            case 3: return p.getDenda();
            case 4: return p.getTotalBayar();
            default: return null;
        }
    }
}
