package com.example.beautyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerUsername, registerEmail, registerPassword;
    Button btnRegister;
    private TextView btnLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference dataUser;
    CheckBox checkBox;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dataUser = FirebaseDatabase.getInstance().getReference("datauser");
        mAuth = FirebaseAuth.getInstance();

        registerUsername = findViewById(R.id.registerNama);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.login);
        checkBox = findViewById(R.id.register_checkbox);
        progressBar = findViewById(R.id.register_progresBar);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    registerPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    registerPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });

//        if (mAuth.getCurrentUser() != null) {
//            //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//            //finish();
//            Intent home = new Intent(RegisterActivity.this, HomeActivity.class);
//            startActivity(home);
//        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = registerUsername.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();

                if (username.isEmpty()) {
                    registerUsername.setError("Username tidak boleh kosong");
                    registerUsername.requestFocus();
                    return;
                }
                else if (email.isEmpty()) {
                    registerEmail.setError("Email tidak boleh kosong");
                    registerEmail.requestFocus();
                    return;
                }
                else if (password.isEmpty()) {
                    registerPassword.setError("Password tidak boleh kosong");
                    registerPassword.requestFocus();
                    return;
                }
                else if (password.length() < 6) {
                    registerPassword.setError("Password kurang dari 6 karakter");
                    registerPassword.requestFocus();
                    return;
                }
                    String id = dataUser.push().getKey();

                    DataUser data = new DataUser(id, username, email, password);

                    dataUser.child(id).setValue(data);

                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(RegisterActivity.this, "Akun berhasil ditambahkan.", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(RegisterActivity.this, "Format Email Salah", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });


//                    Toast.makeText(this, "Data berhasil", Toast.LENGTH_LONG).show();
//                } else {
//                    //Toast.makeText(this, "Kamu belum mengisi form nama", Toast.LENGTH_LONG).show();

//               } else if (username.equals("")) {
//                    Toast.makeText(RegisterActivity.this, "Silahkan isi username Anda", Toast.LENGTH_SHORT).show();
//                } else if (email.equals("")) {
//                    Toast.makeText(RegisterActivity.this, "Silahkan isi email Anda", Toast.LENGTH_SHORT).show();
//                } else if (password.equals("")) {
//                    Toast.makeText(RegisterActivity.this, "Silahkan isi password Anda", Toast.LENGTH_SHORT).show();
//                    String id = dataUser.push().getKey();
//                    DataUser data = new DataUser(id, username, email, password);
//                    dataUser.child(id).setValue(data);
//                } else {
////                    progressBar.setVisibility(View.VISIBLE);
//                    mAuth.createUserWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        Toast.makeText(RegisterActivity.this, "Akun berhasil ditambahkan.", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                        progressBar.setVisibility(View.GONE);
//                                    }
//                                }
//                            });
                }

//                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(i);

        });
    }

    public void onBackPressed() {
        Intent goLogin = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(goLogin);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
}
