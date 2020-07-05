package com.example.beautyshop.ui.home;

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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beautyshop.MainActivity;
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

    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.example.beautyshop.ACTION_UPDATE_NOTIFICATION";
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private static final int NOTIFICATION_ID = 0;

    TextView iNama, iAlamat, iKabupaten, iKecamatan, iKode, iJumlah, iBayar;
    private Button Selesai;
    private NotificationManager mNotifyManager;
    //private NotificationReceiver mReceiver = new NotificationReceiver();

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

        createNotificationChannel();

        //registerReceiver(mReceiver,
                //new IntentFilter(ACTION_UPDATE_NOTIFICATION));

        buktiPembayaran = FirebaseStorage.getInstance().getReference("Images");
        dataPembayaran = FirebaseDatabase.getInstance().getReference("databayar");

        iNama = findViewById(R.id.i_nama);
        iAlamat = findViewById(R.id.i_alamat);
        iKabupaten= findViewById(R.id.i_kabupaten);
        iKecamatan = findViewById(R.id.i_kecamatan);
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
                sendNotification();
                Intent intent = new Intent(DetailBeliActivity.this, MainActivity.class);
                startActivity(intent);
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
        String dataKabupaten = intent.getStringExtra("Kabupaten");
        String dataKecamatan = intent.getStringExtra("Kecamatan");
        String dataKode = intent.getStringExtra("Kode");
        String dataJumlah = intent.getStringExtra("Jumlah");
        String dataBayar = intent.getStringExtra("Bayar");

        iNama.setText(dataNama);;
        iAlamat.setText(dataAlamat);
        iKabupaten.setText(dataKabupaten);
        iKecamatan.setText(dataKecamatan);
        iKode.setText(dataKode);
        iJumlah.setText(dataJumlah);
        iBayar.setText(dataBayar);
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
                .setSmallIcon(R.drawable.exo_icon_rewind)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        return notifyBuilder;
    }

    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean
            isUpdateEnabled, Boolean isCancelEnabled) {
        Selesai.setEnabled(isNotifyEnabled);

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

//    public void notification(){
//        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                .setDefaults(NotificationCompat.DEFAULT_ALL)
//                .setSmallIcon(R.drawable.ic_menu_send)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_send))
//                .setContentTitle("Notification from Beauty Shop")
//                .setContentText("Pesanan telah terkirim, harap verifikasi lewat email anda");
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(1, notificationBuilder.build());
//    }

}
