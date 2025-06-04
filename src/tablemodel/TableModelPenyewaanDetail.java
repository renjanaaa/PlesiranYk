package tablemodel;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.ModelPenyewaanDetail;

public class TableModelPenyewaanDetail extends AbstractTableModel {

    private List<ModelPenyewaanDetail> data;

    private final String[] kolom = {
        "ID Detail", "Mobil", "Harga/Hari", "Durasi (hari)", "Subtotal"
    };

    public TableModelPenyewaanDetail(List<ModelPenyewaanDetail> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public int getColumnCount() {
        return kolom.length;
    }

    @Override
    public String getColumnName(int col) {
        return kolom[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        ModelPenyewaanDetail d = data.get(row);
        switch (col) {
            case 0: return d.getId();
            case 1: return d.getMobil().getMerk();
            case 2: return d.getHargaPerHari();
            case 3: return d.getJumlahHari();
            case 4: return d.getSubtotal();
            default: return null;
        }
    }
}
