package service;

import model.ModelPenyewaanDetail;
import java.util.List;

public interface ServicePenyewaanDetail {

    void tambahData(ModelPenyewaanDetail model);
    void hapusData(ModelPenyewaanDetail model);

    List<ModelPenyewaanDetail> tampilData(int posisiAwal, int dataPerHalaman);
    List<ModelPenyewaanDetail> pencarianData(String noTransaksi, int posisiAwal, int dataPerHalaman);
    List<ModelPenyewaanDetail> tampilDataByPenyewaan(String noPenyewaan);

    String generateNoDetail();
    void simpanNoTransaksi();

    int getTotalData();
    int getTotalDataByPenyewaan(String noPenyewaan);
}