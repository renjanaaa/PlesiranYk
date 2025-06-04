
package service;

import model.ModelPengembalian;
import java.util.List;

public interface ServicePengembalian {
    void tambahData(ModelPengembalian model);
    void hapusData(ModelPengembalian model);

    List<ModelPengembalian> tampilData(int posisiAwal, int dataPerHalaman);
    List<ModelPengembalian> pencarianData(String id, int posisiAwal, int dataPerHalaman);

    String generateNoTransaksi();
    void simpanNoTransaksi();

    int getTotalData();
}
