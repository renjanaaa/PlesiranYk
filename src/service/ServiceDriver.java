package service;

import model.ModelDriver;
import java.util.List;

public interface ServiceDriver {

    void tambahData(ModelDriver model);
    void hapusData(ModelDriver model);

    List<ModelDriver> tampilData(int posisiAwal, int dataPerHalaman);
    List<ModelDriver> pencarianData(String nama, int posisiAwal, int dataPerHalaman);

    String generateNoDriver();

    int getTotalData();
}