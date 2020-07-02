package com.example.beautyshop.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beautyshop.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class BeliActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUST = 234;
    EditText etNama;
    EditText etAlamat;
    EditText etKode;
    EditText etJumlah;
    RadioGroup Bayar;
    RadioButton MBayar;
    Button Beli;

    private DatabaseReference databaseData;
//    private ImageView buktiTf;
//    private  Button btnChoose, btnUpload;
//    private Uri imguri;
//    private StorageReference dataPembayaran;
//    private StorageTask UploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli);

        databaseData = FirebaseDatabase.getInstance().getReference("datapembeli");
//        dataPembayaran = FirebaseStorage.getInstance().getReference("images");

        etNama = (EditText) findViewById(R.id.et_nama);
        etAlamat = (EditText) findViewById(R.id.et_alamat);
        etKode = (EditText) findViewById(R.id.et_kode);
        etJumlah = (EditText) findViewById(R.id.et_jumlah);
        Bayar = (RadioGroup) findViewById(R.id.radiogroupbayar);
        Beli = (Button) findViewById(R.id.btn_beli);
//        buktiTf = (ImageView) findViewById(R.id.buktiTf);
//        btnChoose = (Button) findViewById(R.id.btnChoose);
//        btnUpload = (Button) findViewById(R.id.btnUpload);

        Beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = Bayar.getCheckedRadioButtonId();
                MBayar = (RadioButton) findViewById(radioId);
                getDataPembeli();
                //Toast.makeText(getApplicationContext(),"Data Tersimpan", Toast.LENGTH_SHORT).show();
            }
        });

//        btnChoose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Filechooser();
//            }
//        });
//
//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (UploadTask != null && UploadTask.isInProgress()){
//                    Toast.makeText(BeliActivity.this, "Upload in progress", Toast.LENGTH_LONG).show();
//                }
//                FileUploader();
//            }
//        });
    }

//    private String getExtension (Uri uri)
//    {
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
//        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
//
//    }
//
//    private void Filechooser(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, 1);
//    }
//
//    private void FileUploader()
//    {
//        StorageReference Ref = dataPembayaran.child(System.currentTimeMillis()+"."+ getExtension(imguri));
//
//            UploadTask = Ref.putFile(imguri)
//                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            Toast.makeText(BeliActivity.this, "Image Upload Success", Toast.LENGTH_LONG).show();
//                        }
//                    })
//
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                        }
//                    });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData()!=null){
//            imguri = data.getData();
//            buktiTf.setImageURI(imguri);
//        }
//    }

    public void getDataPembeli(){
        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String kode = etKode.getText().toString();
        String jumlah = etJumlah.getText().toString();
        String bayar = MBayar.getText().toString();

        if (!TextUtils.isEmpty(nama)){

            String id = databaseData.push().getKey();

            Data data = new Data(id, nama, alamat, kode, jumlah, bayar);

            databaseData.child(id).setValue(data);

            Toast.makeText(this, "Data berhasil", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Kamu belum mengisi form pembelian", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(BeliActivity.this, DetailBeliActivity.class);

        intent.putExtra("Nama" , nama);
        intent.putExtra("Alamat", alamat);
        intent.putExtra("Kode", kode);
        intent.putExtra("Jumlah", jumlah);
        intent.putExtra("Bayar", bayar);

        startActivity(intent);
    }
}
