package com.example.beautyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProdukActivity extends AppCompatActivity {

    private TextView tvTitle, tvHarga, tvDeskrisi;
    private ImageView imgProduk;
    private Button btnBeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_produk);
        tvTitle = (TextView) findViewById(R.id.produk_title);
        tvHarga = (TextView) findViewById(R.id.produk_harga);
        tvDeskrisi = (TextView) findViewById(R.id.deskripsi);
        imgProduk = (ImageView) findViewById(R.id.produkthumbnail);
        btnBeli = (Button) findViewById(R.id.beli);


        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProdukActivity.this, BeliActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Harga = intent.getExtras().getString("Harga");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail");

        tvTitle.setText(Title);
        tvHarga.setText(Harga);
        tvDeskrisi.setText(Description);
        imgProduk.setImageResource(image);

    }
}