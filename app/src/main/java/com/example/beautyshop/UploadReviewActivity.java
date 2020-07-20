package com.example.beautyshop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class UploadReviewActivity extends AppCompatActivity {

    private StorageTask mUploadTask;

    private static final int PICK_IMAGE_REQUEST = 234;
    public static final String STORAGE_PATH_UPLOADS = "uploads/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";
    private ImageView mImageView;
    private Button mButtonChooseImage, mButtonUpload, mView;
    private EditText mEditTextFileName;
    private ProgressBar mProgressBar;
    public Uri mImageUri;
    private StorageReference mStorageReview;
    private DatabaseReference mDatabaseReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_review);

        mButtonChooseImage = findViewById(R.id.choose);
        mButtonUpload = findViewById(R.id.upload);
        mEditTextFileName = findViewById(R.id.isiReview);
        mImageView = findViewById(R.id.fotoReview);
        mView = findViewById(R.id.lihatReview);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorageReview = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseReview = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadReviewActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               OpenImageActivity();
            }
        });
    }

    private String getFile(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return  mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadFile() {
    if (mImageUri != null){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StorageReference fileReference = mStorageReview.child(System.currentTimeMillis()
        +"." +getFile(mImageUri));

        mUploadTask = fileReference.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<com.google.firebase.storage.UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(0);
                            }
                        } ,   500);
                        Toast.makeText(UploadReviewActivity.this, "Upload berhasil", Toast.LENGTH_LONG).show();
                        Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                taskSnapshot.getUploadSessionUri().toString());
                        String uploadId = mDatabaseReview.push().getKey();
                        mDatabaseReview.child(uploadId).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(UploadReviewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<com.google.firebase.storage.UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                mProgressBar.setProgress((int) progress);
                    }
                });
    }else {
        Toast.makeText(this, "Anda belum memilih foto", Toast.LENGTH_SHORT).show();
    }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST || resultCode == RESULT_OK || data != null || data.getData()!=null)
        {
            mImageUri = data.getData();
            //mImageView.setImageURI(mImageUri);
           Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private void OpenImageActivity(){
        Intent intent = new Intent(this, ReviewActivity.class);
        startActivity(intent);
    }
}
