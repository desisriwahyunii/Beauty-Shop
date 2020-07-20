package com.example.beautyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ShopActivity extends AppCompatActivity {
    ImageView Wardah, Emina, MadamGie, Pixy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Wardah = findViewById(R.id.btnWardah);
        Emina = findViewById(R.id.btnEmina);
        MadamGie = findViewById(R.id.btnMadamgie);
        Pixy = findViewById(R.id.btnPixy);

        Wardah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getApplicationContext(), WardahActivity.class);
                a.putExtra("Beauty", " Wardah");
                startActivity(a);
            }
        });

        Emina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(getApplicationContext(), EminaActivity.class);
                b.putExtra("Beauty", " Emina");
                startActivity(b);
            }
        });

        MadamGie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(getApplicationContext(), MadameGieActivity.class);
                c.putExtra("Beauty", " Madamegie");
                startActivity(c);
            }
        });

        Pixy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(getApplicationContext(), PixyActivity.class);
                d.putExtra("Beauty", " Pixy");
                startActivity(d);
            }
        });
    }

}
