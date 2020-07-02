package com.example.beautyshop.ui.home;

public class Upload {
    private String mNama;
    private String mImguri;

    public Upload(){}

    public Upload (String name, String imageuri){
        if (name.trim().equals("")){
            name = "no name";
        }

        mNama = name;
        mImguri = imageuri;
    }

    public String getmNama() {
        return mNama;
    }

    public void setmNama(String mNama) {
        this.mNama = mNama;
    }

    public String getmImguri() {
        return mImguri;
    }

    public void setmImguri(String mImguri) {
        this.mImguri = mImguri;
    }
}
