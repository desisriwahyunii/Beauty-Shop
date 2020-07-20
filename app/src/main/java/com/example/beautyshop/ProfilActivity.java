package com.example.beautyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {
    TextView nUsername, nEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        nUsername = findViewById(R.id.tv_person);
        nEmail = findViewById(R.id.tv_email);

        showAllUserData();
    }

    private void showAllUserData(){
        Intent intent = getIntent();
        String username = intent.getStringExtra("nama");
        String email = intent.getStringExtra("email");

        nUsername.setText(username);
        nEmail.setText(email);


    }
}
