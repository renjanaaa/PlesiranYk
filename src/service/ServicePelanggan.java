package service;

import model.ModelPelanggan;
import java.util.List;

public interface ServicePelanggan {

    void tambahData(ModelPelanggan model);
    void hapusData(ModelPelanggan model);

    List<ModelPelanggan> tampilData(int posisiAwal, int dataPerHalaman);
    List<ModelPelanggan> pencarianData(String nama, int posisiAwal, int dataPerHalaman);

    String generateNoPelanggan();
    void simpanNoTransaksi();

    int getTotalData();
}