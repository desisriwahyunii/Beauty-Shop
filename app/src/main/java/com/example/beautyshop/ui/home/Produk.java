package com.example.beautyshop.ui.home;

public class Produk {
    private String Title;
    private String Harga;
    private String Description;
    private int Thumbnail;

    public Produk(String title, String harga, String description, int thumbnail) {
        Title = title;
        Harga = harga;
        Description = description;
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public String getHarga() {
        return Harga;
    }

    public String getDescription() {
        return Description;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setHarga(String harga) {
        Harga = harga;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }
}

