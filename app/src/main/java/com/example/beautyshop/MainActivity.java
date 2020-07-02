package com.example.beautyshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Produk> lstProduk;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        VIewPagerAdapter vIewPagerAdapter = new VIewPagerAdapter(this);
        viewPager.setAdapter(vIewPagerAdapter);

        lstProduk = new ArrayList<>();
        lstProduk.add(new Produk("Wardah","Kategori","Deskripsi",R.drawable.wardah_menu));
        lstProduk.add(new Produk("Madam Gie","Kategori","Deskripsi",R.drawable.madamgie_menu));
        lstProduk.add(new Produk("Pixy","Kategori","Deskripsi",R.drawable.pixy_menu));
        lstProduk.add(new Produk("Emina","Kategori","Deskripsi",R.drawable.emina_menu));

        RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this,lstProduk);
        myrv.setLayoutManager(new GridLayoutManager(this,3));
        myrv.setAdapter(myAdapter);
    }

}
