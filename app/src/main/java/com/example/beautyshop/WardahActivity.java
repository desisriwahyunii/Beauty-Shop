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

public class WardahActivity extends AppCompatActivity {
    List<Produk> lstProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardah);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("Beauty") != null) {
                Toast.makeText(getApplicationContext(),  "Beauty :" + bundle.getString("Beauty"), Toast.LENGTH_SHORT).show();
            }

            lstProduk = new ArrayList<>();
            lstProduk.add(new Produk("Exclusive Liquid Foundation 03", "Rp.65.600", "Kode Produk : W01", R.drawable.wardah1));
            lstProduk.add(new Produk("Lightening Two Way Cake 02", "Rp.44.625", "Kode Produk : W02", R.drawable.wardah2));
            lstProduk.add(new Produk("Lip Cream Exclusive Matte 03", "Rp.50.000", "Kode Produk : W03", R.drawable.wardah3));
            lstProduk.add(new Produk("Lip Cream Exclusive Matte 08", "Rp.50.000", "Kode Produk : W04", R.drawable.wardah4));
            lstProduk.add(new Produk("Colorvit Velvet Matte Lip 05", "Rp.73.000", "Kode Produk : W05", R.drawable.wardah5));
            lstProduk.add(new Produk("Colorfit Ultralight Matte Lipstick 03", "Rp.35.000", "Kode Produk : W06", R.drawable.wardah6));
            lstProduk.add(new Produk("EyeXpert Staylast Liquid Eyeliner", "Rp.31.000", "Kode Produk : W07", R.drawable.wardah7));
            lstProduk.add(new Produk("Eyexpert Aqua Lash Mascara", "Rp.36.000", "Kode Produk : W08", R.drawable.wardah8));
            lstProduk.add(new Produk("Instaperfect MATTECENTRIC Lip Crayon 01", "Rp.89.500", "Kode Produk : W09", R.drawable.wardah9));
            lstProduk.add(new Produk("Wardah Blush On C", "Rp.38.250", "Kode Produk : W10", R.drawable.wardah10));
            lstProduk.add(new Produk("Wardah Eyexpert Eyebrow Kit", "Rp.53.550", "Kode Produk : W11", R.drawable.wardah11));
            lstProduk.add(new Produk("Wardah Eyebrow Pencil Brown", "Rp.31.450", "Kode Produk : W12", R.drawable.wardah12));
            lstProduk.add(new Produk("Wardah EyeXpert Perfectcurl Mascara", "Rp.56.100", "Kode Produk : W13", R.drawable.wardah13));
            lstProduk.add(new Produk("Wardah Exclusive Eyeshadow Palette 01", "Rp.84.575", "Kode Produk : W14", R.drawable.wardah14));
            lstProduk.add(new Produk("Instaperfect MINERALIGHT MATTE BB Cushion 13", "Rp.177.000", "Kode Produk : W15", R.drawable.wardah15));
            lstProduk.add(new Produk("Wardah Blush On A", "Rp.38.250", "Kode Produk : W16", R.drawable.wardah16));
            lstProduk.add(new Produk("Wardah Lip Palette Pinky Peach", "Rp.57.200", "Kode Produk : W17", R.drawable.wardah17));
            lstProduk.add(new Produk("Instaperfect MATTESETTER Lip Matte Paint 02", "Rp.89.000", "Kode Produk : W18", R.drawable.wardah18));
            lstProduk.add(new Produk("Wardah Everyday Cheek & Liptint 02", "Rp.37.400", "Kode Produk : W19", R.drawable.wardah19));
            lstProduk.add(new Produk("Wardah Blush On B", "Rp.38.250", "Kode Produk : W20", R.drawable.wardah20));
            lstProduk.add(new Produk("Wardah Intense Matte Lipstick 07", "Rp.38.800", "Kode Produk : W21", R.drawable.wardah21));

            RecyclerView myrv = (RecyclerView) findViewById(R.id.recyclerview_id);
            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, lstProduk);
            myrv.setLayoutManager(new GridLayoutManager(this, 3));
            myrv.setAdapter(myAdapter);
        }
    }
}