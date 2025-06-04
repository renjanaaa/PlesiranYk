package service;

import model.ModelPenyewaan;
import java.util.List;

public interface ServicePenyewaan {

    void tambahData(ModelPenyewaan model);
    void hapusData(ModelPenyewaan model);
    void updateData(ModelPenyewaan model);

    List<ModelPenyewaan> tampilData(int posisiAwal, int dataPerHalaman);
    List<ModelPenyewaan> pencarianData(String keyword, int posisiAwal, int dataPerHalaman);

    List<ModelPenyewaan> tampilDataByStatus(String status, int posisiAwal, int dataPerHalaman);

    String generateNoTransaksi();
    void simpanNoTransaksi();

    int getTotalData();
    int getTotalDataPencarian(String keyword);
    int getTotalDataByStatus(String status);

    ModelPenyewaan getDataById(int id);
    ModelPenyewaan getDataByKode(String kode);
    boolean updateStatus(int id, String status);
    List<ModelPenyewaan> getActivePenyewaan();
    boolean validateData(ModelPenyewaan model);
}