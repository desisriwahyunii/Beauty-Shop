package com.example.beautyshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    Button btnLogout;
    ImageView btnShop, btnProfil, btnReview, btnTutorial;
    FirebaseAuth mAuth;
    TextView nEmail;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        nEmail = findViewById(R.id.tv_email);
//        Intent i = getIntent();
//        String namaEmail = i.getStringExtra("Email");
//        nEmail.setText(namaEmail);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (signInAccount != null){
            nEmail.setText(signInAccount.getEmail());
        }

        btnLogout = findViewById(R.id.btnLogout);
        btnShop = findViewById(R.id.btnShop);
        btnProfil = findViewById(R.id.btnProfil);
        btnReview = findViewById(R.id.btnReview);
        btnTutorial = findViewById(R.id.btnTutorial);
        TextView tvEmail = findViewById(R.id.tv_email);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String email = sharedPreferences.getString(KEY_EMAIL, null);
        String password = sharedPreferences.getString(KEY_EMAIL, null);

        if (email != null || password != null){
            tvEmail.setText(""+ email);
        }

        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(HomeActivity.this, TutorialActivity.class);
                startActivity(t);
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(HomeActivity.this, ReviewActivity.class);
                startActivity(r);
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pp = new Intent(HomeActivity.this, ProfilActivity.class);
                startActivity(pp);
            }
        });

        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shop = new Intent(HomeActivity.this, ShopActivity.class);
                startActivity(shop);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Toast.makeText(HomeActivity.this, "Logout Sukses", Toast.LENGTH_LONG).show();
                mAuth.signOut();
                Intent logout = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(logout);
                finish();

            }
        });
    }
}
