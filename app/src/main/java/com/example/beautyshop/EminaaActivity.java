package com.example.beautyshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.beautyshop.ui.home.Produk;
import com.example.beautyshop.ui.home.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class EminaaActivity extends AppCompatActivity {
    List<Produk> lstProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eminaa);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("Beauty") != null) {
                Toast.makeText(getApplicationContext(),  "Beauty :" + bundle.getString("Beauty"), Toast.LENGTH_SHORT).show();
            }

            lstProduk = new ArrayList<>();
            lstProduk.add(new Produk("Emina Cream Matte Lipcream 02", "Rp.38.000", "Kode Produk : E01", R.drawable.emina1));
            lstProduk.add(new Produk("Lightening Two Way Cake", "Rp.30.000", "Kode Produk : E02", R.drawable.emina2));
            lstProduk.add(new Produk("Emina Magic Potion Lip Tint", "Rp.36.000", "Kode Produk : E03", R.drawable.emina3));
            lstProduk.add(new Produk("Emina Mineral Cushion", "Rp.98.000", "Kode Produk : E04", R.drawable.emina4));
            lstProduk.add(new Produk("Emina Cheklit Blush on 01", "Rp.36.000", "Kode Produk : E05", R.drawable.emina5));
            lstProduk.add(new Produk("Emina Bright Stuff Loose Powder", "Rp.17.000", "Kode Produk : E06", R.drawable.emina6));
            lstProduk.add(new Produk("Emina Scrub Mask 2in1 50mL", "Rp.34.000", "Kode Produk : E07", R.drawable.emina7));
            lstProduk.add(new Produk("Emina Bright Stuff Moisturizing Cream", "Rp.20.000", "Kode Produk : E08", R.drawable.emina8));
            lstProduk.add(new Produk("Emina Bare With Me Mineral Cushion 01", "Rp.100.000", "Kode Produk : E09", R.drawable.emina9));
            lstProduk.add(new Produk("Emina Glossy Stain 05", "Rp.47.500", "Kode Produk : E10", R.drawable.emina10));
            lstProduk.add(new Produk("Emina City Chic CC Cake 01", "Rp.44.625", "Kode Produk : E11", R.drawable.emina11));
            lstProduk.add(new Produk("Emina Pop Rouge Pressed Eye Shadow 01", "Rp.33.200", "Kode Produk : E12", R.drawable.emina12));
            lstProduk.add(new Produk("Emina Pop Rouge Pressed Eye Shadow 02", "Rp.33.200", "Kode Produk : E13", R.drawable.emina13));
            lstProduk.add(new Produk("Emina Glossy Stain 01", "Rp.47.500", "Kode Produk : E14", R.drawable.emina14));
            lstProduk.add(new Produk("Emina Top Secret Eyebrow", "Rp.44.625", "Kode Produk : E15", R.drawable.emina15));
            lstProduk.add(new Produk("Emina Eye Do Pencil Eyeliner Black", "Rp.30.000", "Kode Produk : E16", R.drawable.emina16));
            lstProduk.add(new Produk("Emina Lip Cushion 04", "Rp.45.200", "Kode Produk : E17", R.drawable.emina17));
            lstProduk.add(new Produk("Emina Lip Cushion 05", "Rp.45.200", "Kode Produk : E18", R.drawable.emina18));
            lstProduk.add(new Produk("Emina Creamatte Metallic Edition 01", "Rp.40.000", "Kode Produk : E19", R.drawable.emina19));
            lstProduk.add(new Produk("Emina Creamatte Metallic Edition 02", "Rp.40.000", "Kode Produk : E20", R.drawable.emina20));
            lstProduk.add(new Produk("Emina Sugar Rush Lipstick 03", "Rp.30.400", "Kode Produk : E21", R.drawable.emina21));

            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstProduk);
            myrv.setLayoutManager(new GridLayoutManager(this, 3));
            myrv.setAdapter(myAdapter);
        }
    }
}
