package com.example.beautyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class DetailBeliActivity extends AppCompatActivity {
    Button btnChoose, btnUpload;
    ImageView imageViewTf;
    private DatabaseReference databaseData;
    public Uri mImageUri;
    private NotificationManager mNotifyManager;
    private StorageReference mStorageTransfer;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.beautyshop.ACTION_UPDATE_NOTIFICATION";
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_beli);

        createNotificationChannel();

        mStorageTransfer = FirebaseStorage.getInstance().getReference("transfer");

        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
        imageViewTf = findViewById(R.id.imageViewTf);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFile();
                sendNotification();
            }
        });

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

//        iAlamat = findViewById(R.id.i_alamat);
//        iTelepon = findViewById(R.id.i_telepon);
//        //iKategori = findViewById(R.id.i_kategori);
//        iKode = findViewById(R.id.i_kode);
//        iJumlah = findViewById(R.id.i_jumlah);
//        iBayar = findViewById(R.id.i_pembayaran);
//        iTotal = findViewById(R.id.i_total);
//
//        Intent intent = getIntent();
//        String Nama = intent.getStringExtra("Nama");
//        String Alamat = intent.getStringExtra("Alamat");
//        String Telepon = intent.getStringExtra("Telepon");
//        String Kategori = intent.getStringExtra("Kategori");
//        String Kode = intent.getStringExtra("Kode");
//        String Jumlah = intent.getStringExtra("Jumlah");
//        String Bayar = intent.getStringExtra("Bayar");
//        String Total = intent.getStringExtra("Total");
//
//
//        iNama.setText(Nama);
//        iAlamat.setText(Alamat);
//        iTelepon.setText(Telepon);
////        iKategori.setText(Kategori);
//        iKode.setText(Kode);
//        iJumlah.setText(Jumlah);
//        iBayar.setText(Bayar);
//        iTotal.setText(Total);
//
    }
    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            getString(R.string.notification_channel_name),
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    (getString(R.string.notification_channel_description));

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public void sendNotification() {

        // Sets up the pending intent to update the notification.
        // Corresponds to a press of the Update Me! button.
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        // Build the notification with all of the parameters using helper
        // method.
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        // Add the action button using the pending intent.
//        notifyBuilder.addAction(R.drawable.ic_menu_manage,
//                getString(R.string.update), updatePendingIntent);

        // Deliver the notification.
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        // Enable the update and cancel buttons but disables the "Notify
        // Me!" button.
        setNotificationButtonState(false, true, true);
    }

    private NotificationCompat.Builder getNotificationBuilder() {

        // Set up the pending intent that is delivered when the notification
        // is clicked.
        Intent notificationIntent = new Intent(this, DetailBeliActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity
                (this, NOTIFICATION_ID, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        // Build the notification with all of the parameters.
        NotificationCompat.Builder notifyBuilder = new NotificationCompat
                .Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.code)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean
            isUpdateEnabled, Boolean isCancelEnabled) {
        btnUpload.setEnabled(isNotifyEnabled);

    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {
        }

        /**
         * Receives the incoming broadcasts and responds accordingly.
         *
         * @param context Context of the app when the broadcast is received.
         * @param intent The broadcast intent containing the action.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            // Update the notification.

        }
    }

        private String getFile(Uri uri){
            ContentResolver cr = getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return  mime.getExtensionFromMimeType(cr.getType(uri));
        }


        private void UploadFile() {
            if (mImageUri != null){
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                StorageReference fileReference = mStorageTransfer.child(System.currentTimeMillis()
                        +"." +getFile(mImageUri));

                fileReference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(com.google.firebase.storage.UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
//                                Handler handler = new Handler();
//                                handler.postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        // mProgressBar.setProgress(0);
//                                    }
//                                } ,   50000);
                                Toast.makeText(DetailBeliActivity.this, "Upload berhasil", Toast.LENGTH_LONG).show();
//                                Upload upload = new Upload(Nama.getText().toString().trim(),
//                                        taskSnapshot.getUploadSessionUri().toString());
//                                String uploadId = databaseData.push().getKey();
//                                databaseData.child(uploadId).setValue(upload);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(DetailBeliActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull com.google.firebase.storage.UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                //mProgressBar.setProgress((int) progress);
                            }
                        });
                Intent selesai = new Intent(DetailBeliActivity.this, LoginActivity.class);
                startActivity(selesai);
                finish();
            }else {
                Toast.makeText(this, "Anda memilih metode bayar di tempat", Toast.LENGTH_LONG).show();
                Intent selesai = new Intent(DetailBeliActivity.this, LoginActivity.class);
                startActivity(selesai);
                finish();
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
                Picasso.get().load(mImageUri).into(imageViewTf);
            }
        }

    }
