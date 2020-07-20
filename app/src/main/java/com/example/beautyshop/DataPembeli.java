package com.example.beautyshop;

public class DataPembeli {
    String Id;
    String Nama;
    String Alamat;
    String Telepon;
    String Kode;
    String Jumlah;
    String Pembayaran;
    String Total;
    String Image;

    public DataPembeli(String id, String nama, String alamat, String telp, String kode, String jml, String bayar, String total) {
        this.Id = id;
        this.Nama = nama;
        this.Alamat = alamat;
        this.Telepon = telp;
        this.Kode = kode;
        this.Jumlah = jml;
        this.Pembayaran = bayar;
        this.Total = total;
    }

    public String getId() {
        return Id;
    }

    public String getNama() {
        return Nama;
    }

    public String getAlamat() {
        return Alamat;
    }

    public String getTelepon() {
        return Telepon;
    }

    public String getKode() {
        return Kode;
    }

    public String getJumlah() {
        return Jumlah;
    }

    public String getBayar() {
        return Pembayaran;
    }
    public String getTotal() {
        return Total;
    }
}
