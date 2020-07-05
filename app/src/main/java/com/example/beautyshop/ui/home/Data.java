package com.example.beautyshop.ui.home;

public class Data {

    String dataId;
    String dataNama;
    String dataAlamat;
    String dataKabupaten;
    String dataKecamatan;
    String dataKode;
    String dataJumlah;
    String dataBayar;

    public Data(){

    }

    public Data(String dataId, String dataNama, String dataAlamat, String dataKabupaten, String dataKecamatan, String dataKode, String dataJumlah, String dataBayar) {
        this.dataId = dataId;
        this.dataNama = dataNama;
        this.dataAlamat = dataAlamat;
        this.dataKabupaten = dataKabupaten;
        this.dataKecamatan = dataKecamatan;
        this.dataKode = dataKode;
        this.dataJumlah = dataJumlah;
        this.dataBayar = dataBayar;
    }

    public String getDataId() {
        return dataId;
    }

    public String getDataNama() {
        return dataNama;
    }

    public String getDataAlamat() {
        return dataAlamat;
    }

    public String getDataKabupaten() {
        return dataKabupaten;

    }public String getDataKecamatan() {
        return dataKecamatan;
    }

    public String getDataKode() {
        return dataKode;
    }

    public String getDataJumlah() {
        return dataJumlah;
    }

    public String getDataBayar() {
        return dataBayar;
    }
}
