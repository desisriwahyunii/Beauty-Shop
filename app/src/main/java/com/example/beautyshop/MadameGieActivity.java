package com.example.beautyshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MadameGieActivity extends AppCompatActivity {
    List<Produk> lstProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madame_gie);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("Beauty") != null) {
                Toast.makeText(getApplicationContext(), "Beauty :" + bundle.getString("Beauty"), Toast.LENGTH_SHORT).show();
            }

            lstProduk = new ArrayList<>();
            lstProduk.add(new Produk("Make Up Kit To Go", "Rp.32.000", "Kode Produk : M01", R.drawable.madamgie1));
            lstProduk.add(new Produk("Sweet Cheek Blushes 02", "Rp.18.000", "Kode Produk : M02", R.drawable.madamgie2));
            lstProduk.add(new Produk("Lip Liquide Brilliant Glaze 301", "Rp.16.500", "Kode Produk : M03", R.drawable.madamgie3));
            lstProduk.add(new Produk("Liquide Lip Cream Mate 421", "Rp.17.000", "Kode Produk : M04", R.drawable.madamgie4));
            lstProduk.add(new Produk("Madam Gie Two Way Cake", "Rp.23.500", "Kode Produk : M05", R.drawable.madamgie5));
            lstProduk.add(new Produk("Maskara dan Eyeliner", "Rp.28.500", "Kode Produk : M06", R.drawable.madamgie6));
            lstProduk.add(new Produk("Eyeshadow Sensous Drama Queen", "Rp.31.500", "Kode Produk : M07", R.drawable.madamgie7));
            lstProduk.add(new Produk("Madame Gie Lip Tin 03", "Rp.17.000", "Kode Produk : M08", R.drawable.madamgie8));
            lstProduk.add(new Produk("Madame Gie Madame Make It Sharp", "Rp.13.500", "Kode Produk : M09", R.drawable.madamgie9));
            lstProduk.add(new Produk("Madame Gie Beauty Blink Fame 02", "Rp.15.200", "Kode Produk : M10", R.drawable.madamgie10));
            lstProduk.add(new Produk("Madame Gie Magnifique Lip Crayon Matte 605", "Rp.23.200", "Kode Produk : M11", R.drawable.madamgie11));
            lstProduk.add(new Produk("Madame Gie Femme Cheek Xoxo 04", "Rp.17.850", "Kode Produk : M12", R.drawable.madamgie12));
            lstProduk.add(new Produk("Madame Gie Simple Look Cheek 01 Lipmatte 401", "Rp.38.320", "Kode Produk : M13", R.drawable.madamgie13));
            lstProduk.add(new Produk("Madame Gie Blinded By Drama 01", "Rp.31.500", "Kode Produk : M14", R.drawable.madamgie14));
            lstProduk.add(new Produk("Madame Gie Magnifique Lip Tint 1 Set", "Rp.120.000", "Kode Produk : M15", R.drawable.madamgie15));
            lstProduk.add(new Produk("Madame Gie Madame Perfect Brow", "Rp.18.000", "Kode Produk : M16", R.drawable.madamgie16));
            lstProduk.add(new Produk("Madame Gie Wing It Lady", "Rp.21.600", "Kode Produk : M17", R.drawable.madamgie17));
            lstProduk.add(new Produk("Madame Gie Eyeshadow Moondust Temptation MD03", "Rp.22.160", "Kode Produk : M18", R.drawable.madamgie18));
            lstProduk.add(new Produk("Madame Gie Femme Banana Loose Powder 03", "Rp.23.120", "Kode Produk : M19", R.drawable.madamgie19));
            lstProduk.add(new Produk("Madame Gie Total Cover BB Cushion BBC01", "Rp.63.750", "Kode Produk : M20", R.drawable.madamgie20));
            lstProduk.add(new Produk("Madame Gie Total Cover BB Cushion Refill BBC01", "Rp.15.920", "Kode Produk : M21", R.drawable.madamgie21));

            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstProduk);
            myrv.setLayoutManager(new GridLayoutManager(this, 3));
            myrv.setAdapter(myAdapter);
        }
    }
}