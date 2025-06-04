package service;

import model.ModelPengembalianDetail;
import java.util.List;

public interface ServicePengembalianDetail {
    void tambahData(ModelPengembalianDetail model);
    void hapusData(ModelPengembalianDetail model);

    List<ModelPengembalianDetail> tampilData(int posisiAwal, int dataPerHalaman);
    List<ModelPengembalianDetail> pencarianData(String noPengembalian, int posisiAwal, int dataPerHalaman);
    List<ModelPengembalianDetail> tampilDataByPengembalian(String noPengembalian);

    String generateNoDetail();
    void simpanNoPengembalian();

    int getTotalData();
    int getTotalDataByPengembalian(String noPengembalian);
}
