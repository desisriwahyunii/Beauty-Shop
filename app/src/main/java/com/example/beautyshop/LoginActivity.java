package com.example.beautyshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private FirebaseAuth mAuth;
    Toast back;
    TextView Register;
    Button btnLogin;
    long timepress;
    CheckBox checkBox;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;

    //private GoogleSignInClient mGoogleSignInClient;
    //private final static int RC_SIGN_IN = 123;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        Register = findViewById(R.id.Register);
        btnLogin = findViewById(R.id.btnLogin);
        checkBox = findViewById(R.id.login_checkbox);
        progressBar = findViewById(R.id.login_progresBar);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String email = sharedPreferences.getString(KEY_EMAIL, null);

        if (email !=null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDataUser();
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if (email.isEmpty()) {
                    loginEmail.setError("Email tidak boleh kosong");
                    loginEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    loginPassword.setError("Password tidak boleh kosong");
                    loginPassword.requestFocus();
                    return;
                }

                if (email.equals("")) {
                    Toast.makeText(LoginActivity.this, "Silahkan input Email.", Toast.LENGTH_SHORT).show();
                } else if (password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Silahkan input Password.", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this, "Login Suskes", Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(KEY_EMAIL,loginEmail.getText().toString());
                                        editor.putString(KEY_PASSWORD,loginPassword.getText().toString());
                                        editor.apply();
                                        Intent home = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(home);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Pastikan Email dan Password benar", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }
            }
        });
    }

//            public void getDataUser(){
//                String email = loginEmail.getText().toString();
//                Intent namaEmail = new Intent(LoginActivity.this, HomeActivity.class);
//                namaEmail.putExtra("Email", email);
//                startActivity(namaEmail);
//            }
//        });

//        createRequest();
//        findViewById(R.id.Google).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });
    //}

//    private void createRequest() {
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//    }

//    private void signIn() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == RC_SIGN_IN){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//            }catch (ApiException e){
//                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//
//                        }
//                    }
//                });
//    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null){
//            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            startActivity(intent);
//        }
        }

    @Override
    public void onBackPressed() {
        if (timepress + 2000>System.currentTimeMillis()){
            back.cancel();
            Intent i= new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
        }
        else {
            back = Toast.makeText(getApplicationContext(), "Back press again to exit", Toast.LENGTH_LONG);
            back.show();
        }

        timepress = System.currentTimeMillis();
    }

    private Boolean validateEmail() {
        String val = loginEmail.getText().toString();

        if (val.isEmpty()) {
            loginEmail.setError("Email tidak boleh kosong");
            return false;
        } else {
            loginEmail.setError(null);
            return true;
        }
    }
        private Boolean validatePassword(){
            String val = loginPassword.getText().toString();

            if (val.isEmpty()){
                loginPassword.setError("Password tidak boleh kosong");
                return false;
            }else {
                loginPassword.setError(null);
                return true;
            }
    }

    public void isUser(){
        final String userEnteredEmail = loginEmail.getText().toString().trim();
        final String userEnteredPassword = loginPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("datauser");
        Query checkUser = reference.orderByChild("email").equalTo(userEnteredEmail);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loginEmail.setError(null);
                    String passwordFromDB = snapshot.child(userEnteredEmail).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)){
                        String usernameFromDB = snapshot.child(userEnteredEmail).child("username").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredEmail).child("email").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);

                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        startActivity(intent);
                    }else {
                        loginPassword.setError("Wrong Password");
                    }
                }else {
                    loginEmail.setError("Email tidak terdaftar");
                    loginEmail.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
