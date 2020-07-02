package com.example.beautyshop.ui.home;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class DetailBeliActivity extends AppCompatActivity {
    TextView iNama, iAlamat, iKode, iJumlah, iBayar;
    Button Selesai;

    private StorageTask UploadTask;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView buktiTf;
    private Button btnChoose, btnUpload;
    private EditText namaTf;
    private ProgressBar progressBar;
    public Uri imguri;
    private StorageReference buktiPembayaran;
    private DatabaseReference dataPembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_beli);

        buktiPembayaran = FirebaseStorage.getInstance().getReference("Images");
        dataPembayaran = FirebaseDatabase.getInstance().getReference("databayar");

        iNama = findViewById(R.id.i_nama);
        iAlamat = findViewById(R.id.i_alamat);
        iKode = findViewById(R.id.i_kode);
        iJumlah = findViewById(R.id.i_jumlah);
        iBayar = findViewById(R.id.i_bayar);
        buktiTf = (ImageView) findViewById(R.id.buktiTf);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        namaTf = (EditText) findViewById(R.id.namatf);
        Selesai = (Button) findViewById(R.id.selesai);
        //progressBar = findViewById(R.id.progress_bar);

        Selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notification();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UploadTask != null && UploadTask.isInProgress()) {
                    Toast.makeText(DetailBeliActivity.this, "Upload in progress", Toast.LENGTH_LONG).show();
                } else {
                    FileUploader();
//                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        Intent intent = getIntent();
        String dataNama = intent.getStringExtra("Nama");
        String dataAlamat = intent.getStringExtra("Alamat");
        String dataKode = intent.getStringExtra("Kode");
        String dataJumlah = intent.getStringExtra("Jumlah");
        String dataBayar = intent.getStringExtra("Bayar");

        iNama.setText(dataNama);;
        iAlamat.setText(dataAlamat);
        iKode.setText(dataKode);
        iJumlah.setText(dataJumlah);
        iBayar.setText(dataBayar);
    }

    private String getFileExt (Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void FileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void FileUploader()
    {
        if (imguri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

        StorageReference Ref = buktiPembayaran.child(System.currentTimeMillis()+"."+ getFileExt(imguri));

        UploadTask = Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailBeliActivity.this, "File Uploaded", Toast.LENGTH_LONG).show();
                        Upload upload =  new Upload(namaTf.getText().toString().trim(),
                                taskSnapshot.getUploadSessionUri().toString());
                        String uploadId = dataPembayaran.push().getKey();
                        dataPembayaran.child(uploadId).setValue(upload);
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(DetailBeliActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })

                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        progressDialog.setMessage(((int) progress) + "%Uploaded...");
                    }
                });
        } else {
            //display a error toast
            Toast.makeText(DetailBeliActivity.this, "No file Selected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST || resultCode == RESULT_OK || data != null || data.getData()!=null)
        {
            imguri = data.getData();
            buktiTf.setImageURI(imguri);

            //Picasso.get().load(imguri).into(buktiTf);
        }
    }

    public void notification(){
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_menu_send)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_send))
                .setContentTitle("Notification from Beauty Shop")
                .setContentText("Pesanan telah terkirim, harap verifikasi lewat email anda");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

}
