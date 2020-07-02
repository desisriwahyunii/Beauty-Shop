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

public class PixyActivity extends AppCompatActivity {
    List<Produk> lstProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pixy);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("Beauty") != null) {
                Toast.makeText(getApplicationContext(), "Beauty :" + bundle.getString("Beauty"), Toast.LENGTH_SHORT).show();
            }

            lstProduk = new ArrayList<>();
            lstProduk.add(new Produk("PIXY Make It Glow Dewy Cushion", "Rp.100.000", "Kode Produk : P01", R.drawable.pixy1));
            lstProduk.add(new Produk("Pixy Lip Cream Matte 18", "Rp.38.500", "Kode Produk : P02", R.drawable.pixy2));
            lstProduk.add(new Produk("Pixy Aqua Beauty Protecting Mist", "Rp.25.500", "Kode Produk : P03", R.drawable.pixy3));
            lstProduk.add(new Produk("Pixy Uvw Twc Perfect Las 01t", "Rp.38.000", "Kode Produk : P04", R.drawable.pixy4));
            lstProduk.add(new Produk("PIXY Intense to Last Pen Eyeliner", "Rp.60.300", "Kode Produk : P05", R.drawable.pixy5));
            lstProduk.add(new Produk("PIXY Twin Blush 01", "Rp.49.500", "Kode Produk : P06", R.drawable.pixy6));
            lstProduk.add(new Produk("Pixy Waterproof Mascara Blue", "Rp.48.800", "Kode Produk : P07", R.drawable.pixy7));
            lstProduk.add(new Produk("Pixy Eyeshadow - Romantic Poem", "Rp.31.500", "Kode Produk : P08", R.drawable.pixy8));
            lstProduk.add(new Produk("PIXY Make It Glow Crayonttention 09", "Rp.31.500", "Kode Produk : P09", R.drawable.pixy9));
            lstProduk.add(new Produk("Pixy Uv Whitening Bb Cream 01", "Rp.35.800", "Kode Produk : P10", R.drawable.pixy10));
            lstProduk.add(new Produk("Pixy Make It Glow - Silky Powdery Cake 301", "Rp.64.300", "Kode Produk : P11", R.drawable.pixy11));
            lstProduk.add(new Produk("Pixy Lipstick Matte In Love 505", "Rp.36.600", "Kode Produk : P12", R.drawable.pixy12));
            lstProduk.add(new Produk("Pixy Line & Shadow Black", "Rp.38.800", "Kode Produk : P13", R.drawable.pixy13));
            lstProduk.add(new Produk("PIXY Tint Me! 03", "Rp.41.400", "Kode Produk : P14", R.drawable.pixy14));
            lstProduk.add(new Produk("Pixy Eyebrow Crayon - Black", "Rp.34.000", "Kode Produk : P15", R.drawable.pixy15));
            lstProduk.add(new Produk("Pixy Uvw Twc Perfect Fit 05", "Rp.24.700", "Kode Produk : P16", R.drawable.pixy16));
            lstProduk.add(new Produk("PIXY Tint Me! 02", "Rp.41.400", "Kode Produk : P17", R.drawable.pixy17));
            lstProduk.add(new Produk("Pixy Make It Glow Dewy Cushion 201", "Rp.114.800", "Kode Produk : P18", R.drawable.pixy18));
            lstProduk.add(new Produk("Pixy Beauty Skin Primer Beige 101", "Rp.47.700", "Kode Produk : P19", R.drawable.pixy19));
            lstProduk.add(new Produk("Pixy Uvw Concealing Base 01 Natural Beige", "Rp.34.800", "Kode Produk : P20", R.drawable.pixy20));
            lstProduk.add(new Produk("Pixy Uvw Twc Perfect Fit 05 Refill Pocket Size", "Rp.16.600", "Kode Produk : P21", R.drawable.pixy21));

            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstProduk);
            myrv.setLayoutManager(new GridLayoutManager(this, 3));
            myrv.setAdapter(myAdapter);
        }
    }
}

