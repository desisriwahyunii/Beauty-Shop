package com.example.beautyshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BeliActivity extends AppCompatActivity {
    //TextView selected;
    private static final int PICK_IMAGE_REQUEST = 1;
    Spinner kodeProduk, kategoriProduk;
    private ImageView mImageView;
    Button btnBeli, btnTotal, btnUpload;
    FirebaseDatabase database;
    EditText Nama, Alamat, Telp, jmlProduk, totalHarga;
    TextView hrgSatuan, hrgTotal;
    RadioGroup Pembayaran;
    RadioButton mBayar;

    private DatabaseReference databaseData;
    public Uri mImageUri;
    private StorageReference mStorageTransfer;

    int total;
    int hargaProduk[] = {0, 65600, 44625, 50000, 50000, 73000, 35000, 31000, 36000, 89500, 38250, 53550, 31450, 56100, 84575, 177000, 38250, 57200, 89000, 37400, 38250, 38800
    , 38000, 30000, 36000, 98000, 36000, 17000, 34000, 20000, 100000, 47500, 44625, 33200, 33200, 47500, 44625, 30000, 45200, 45200, 40000, 40000, 30400
    , 32000, 18000, 16500, 17000, 23500, 28500, 31500, 17000, 13500, 15200, 23200, 17850, 38320, 31500, 120000, 18000, 21600, 22160, 23120, 63750, 15920
    , 100000, 38500, 25500, 38000, 60300, 49500, 48800, 31500, 31500, 35800, 64300, 36600, 38800, 41400, 34000, 24700, 41400, 114800, 47700, 34800, 16600};

    String spinnerProduk[] = {"Pilih Kode Produk","W01","W02","W03","W04","W05","W06","W07","W08","W09","W010","W011","W012","W013","W014","W015","W016","W017","W018","W019","W020","W021",
            "E01","E02","E03","E04","E05","E06","E07","E08","E09","E10","E11","E12","E13","E14","E15","E16","E17","E18","E19","E20","E21",
            "M01","M02","M03","M04","M05","M06","M07","M08","M09","M10","M11","M12","M13","M14","M15","M16","M17","M18","M19","M20","M21",
            "P01","P02","P03","P04","P05","P06","P07","P08","P09","P10","P11","P12","P13","P14","P15","P16","P17","P18","P19","P20","P21"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli);

        databaseData = FirebaseDatabase.getInstance().getReference("datapembeli");

        Nama = (EditText) findViewById(R.id.Nama);
        Alamat = (EditText) findViewById(R.id.Alamat);
        Telp = (EditText) findViewById(R.id.Telp);
        jmlProduk = (EditText) findViewById(R.id.Jumlah);
        Pembayaran = (RadioGroup) findViewById(R.id.radiogroupbayar);
        hrgSatuan = (TextView) findViewById(R.id.hrgSatuan);
        hrgTotal = (TextView) findViewById(R.id.hrgTotal);
//        hrg = findViewById(R.id.hrg);
//        btnMinus = findViewById(R.id.min);
//        btnPlus = findViewById(R.id.plus);
        //kategoriProduk = findViewById(R.id.Produk);
        kodeProduk = (Spinner) findViewById(R.id.kodeProduk);
        btnBeli = (Button) findViewById(R.id.btnBeli);
        btnTotal = (Button) findViewById(R.id.btnTotal);
        btnUpload = findViewById(R.id.btnUpload);
        mImageView = findViewById(R.id.imageViewTf);

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTotalHarga();
            }
        });


        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDataPembeli();
                int radioId = Pembayaran.getCheckedRadioButtonId();
                mBayar = (RadioButton) findViewById(radioId);

                String nama = Nama.getText().toString();
                String alamat = Alamat.getText().toString();
                String telp = Telp.getText().toString();
                //String kategori = String.valueOf(kategoriProduk.getSelectedItemId());
                String kode = String.valueOf(kodeProduk.getSelectedItemId());
                String bayar = mBayar.getText().toString();
                String jml = jmlProduk.getText().toString();
                String total = hrgTotal.getText().toString();

                if (nama.isEmpty()) {
                    Nama.setError("Nama tidak boleh kosong");
                    Nama.requestFocus();
                    return;
                }

                if (alamat.isEmpty()) {
                    Alamat.setError("Alamat tidak boleh kosong");
                    Alamat.requestFocus();
                    return;
                }

                if (telp.isEmpty()) {
                    Telp.setError("Telepon tidak boleh kosong");
                    Telp.requestFocus();
                    return;
                }

                if (jml.isEmpty()) {
                    jmlProduk.setError("Jumlah tidak boleh kosong");
                    jmlProduk.requestFocus();
                    return;
                }

                if (!TextUtils.isEmpty(nama)){
                    String id = databaseData.push().getKey();
                    DataPembeli data = new DataPembeli(id, nama, alamat, telp, kode, jml, bayar, total);
                    databaseData.child(id).setValue(data);

                   // Toast.makeText(BeliActivity.this, "Data berhasil", Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(this, "Kamu belum mengisi form nama", Toast.LENGTH_LONG).show();
                }

                Intent intent = new Intent(BeliActivity.this, DetailBeliActivity.class);
                intent.putExtra("Nama", nama);
                intent.putExtra("Alamat", alamat);
                intent.putExtra("Telepon", telp);
                //intent.putExtra("Kategori", kategori);
                intent.putExtra("Kode", kode);
                intent.putExtra("Jumlah", jml);
                intent.putExtra("Bayar", bayar);
                intent.putExtra("Total Harga", total);
                startActivity(intent);
            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(BeliActivity.this, android.R.layout.simple_list_item_1,spinnerProduk);
        kodeProduk.setAdapter(arrayAdapter);

        kodeProduk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = kodeProduk.getItemAtPosition(position).toString();

                if (select == spinnerProduk[0]){
                    hrgSatuan.setText(String.valueOf(hargaProduk[0]));
                }else if (select == spinnerProduk[1]){
                    hrgSatuan.setText(String.valueOf(hargaProduk[1]));
                }else if (select == spinnerProduk[2]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[2]));
                }else if (select == spinnerProduk[3]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[3]));
                }else if (select == spinnerProduk[4]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[4]));
                }else if (select == spinnerProduk[5]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[5]));
                }else if (select == spinnerProduk[6]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[6]));
                }else if (select == spinnerProduk[7]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[7]));
                }else if (select == spinnerProduk[8]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[8]));
                }else if (select == spinnerProduk[9]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[9]));
                }else if (select == spinnerProduk[10]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[10]));
                }else if (select == spinnerProduk[11]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[11]));
                }else if (select == spinnerProduk[12]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[12]));
                }else if (select == spinnerProduk[13]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[13]));
                }else if (select == spinnerProduk[14]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[14]));
                }else if (select == spinnerProduk[15]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[15]));
                }else if (select == spinnerProduk[16]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[16]));
                }else if (select == spinnerProduk[17]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[17]));
                }else if (select == spinnerProduk[18]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[18]));
                }else if (select == spinnerProduk[19]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[19]));
                }else if (select == spinnerProduk[20]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[20]));
                }else if (select == spinnerProduk[21]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[21]));
                }else if (select == spinnerProduk[22]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[22]));
                }else if (select == spinnerProduk[23]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[23]));
                }else if (select == spinnerProduk[24]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[24]));
                }else if (select == spinnerProduk[25]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[25]));
                }else if (select == spinnerProduk[26]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[26]));
                }else if (select == spinnerProduk[27]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[27]));
                }else if (select == spinnerProduk[28]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[28]));
                }else if (select == spinnerProduk[29]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[29]));
                }else if (select == spinnerProduk[30]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[30]));
                }else if (select == spinnerProduk[31]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[31]));
                }else if (select == spinnerProduk[32]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[32]));
                }else if (select == spinnerProduk[33]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[33]));
                }else if (select == spinnerProduk[34]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[34]));
                }else if (select == spinnerProduk[35]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[35]));
                }else if (select == spinnerProduk[36]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[36]));
                }else if (select == spinnerProduk[37]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[37]));
                }else if (select == spinnerProduk[38]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[38]));
                }else if (select == spinnerProduk[39]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[39]));
                }else if (select == spinnerProduk[40]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[40]));
                }else if (select == spinnerProduk[41]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[41]));
                }else if (select == spinnerProduk[42]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[42]));
                }else if (select == spinnerProduk[43]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[43]));
                }else if (select == spinnerProduk[44]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[44]));
                }else if (select == spinnerProduk[45]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[45]));
                }else if (select == spinnerProduk[46]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[46]));
                }else if (select == spinnerProduk[47]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[47]));
                }else if (select == spinnerProduk[48]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[48]));
                }else if (select == spinnerProduk[49]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[49]));
                }else if (select == spinnerProduk[50]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[50]));
                }else if (select == spinnerProduk[51]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[51]));
                }else if (select == spinnerProduk[52]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[53]));
                }else if (select == spinnerProduk[54]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[54]));
                }else if (select == spinnerProduk[55]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[55]));
                }else if (select == spinnerProduk[56]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[56]));
                }else if (select == spinnerProduk[57]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[57]));
                }else if (select == spinnerProduk[58]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[58]));
                }else if (select == spinnerProduk[59]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[59]));
                }else if (select == spinnerProduk[60]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[60]));
                }else if (select == spinnerProduk[61]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[61]));
                }else if (select == spinnerProduk[62]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[62]));
                }else if (select == spinnerProduk[63]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[63]));
                }else if (select == spinnerProduk[64]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[64]));
                }else if (select == spinnerProduk[65]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[65]));
                }else if (select == spinnerProduk[66]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[66]));
                }else if (select == spinnerProduk[67]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[67]));
                }else if (select == spinnerProduk[68]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[68]));
                }else if (select == spinnerProduk[69]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[69]));
                }else if (select == spinnerProduk[70]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[70]));
                }else if (select == spinnerProduk[71]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[71]));
                }else if (select == spinnerProduk[72]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[72]));
                }else if (select == spinnerProduk[73]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[73]));
                }else if (select == spinnerProduk[74]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[74]));
                }else if (select == spinnerProduk[75]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[75]));
                }else if (select == spinnerProduk[76]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[76]));
                }else if (select == spinnerProduk[77]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[77]));
                }else if (select == spinnerProduk[78]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[78]));
                }else if (select == spinnerProduk[79]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[79]));
                }else if (select == spinnerProduk[80]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[80]));
                }else if (select == spinnerProduk[81]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[81]));
                }else if (select == spinnerProduk[82]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[82]));
                }else if (select == spinnerProduk[83]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[83]));
                }else if (select == spinnerProduk[84]) {
                    hrgSatuan.setText(String.valueOf(hargaProduk[84]));
                }else {
                    hrgSatuan.setText("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //reference = database.getReference().child("Spinner");

//        if (savedInstanceState != null) {
//            String nilaiSaved = savedInstanceState.getString("nilai");
//            tv_angka.setText(nilaiSaved);
//        }

//        btnPlus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vJumlah += 1;
//                tv_angka.setText(vJumlah.toString());
//                int total = Integer.parseInt(tv_angka.getText().toString());
//                int harga = Integer.parseInt(hrg.getText().toString());
//                int hargaTotal = total*harga;
//                totalHarga.setText(String.valueOf(hargaTotal));
//            }
//        });
//
//        btnMinus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (vJumlah > 1)
//                vJumlah -= 1;
//                tv_angka.setText(vJumlah.toString());
//                int total = Integer.parseInt(tv_angka.getText().toString());
//                int harga = Integer.parseInt(hrg.getText().toString());
//                int hargaTotal = total*harga;
//                totalHarga.setText(String.valueOf(hargaTotal));
//            }
//        });
    }
    public void getTotalHarga() {
        int Harga = Integer.parseInt(hrgSatuan.getText().toString());
        int jumlahProduk = Integer.parseInt(jmlProduk.getText().toString());

        total = Harga*jumlahProduk;
        hrgTotal.setText(String.valueOf(total));
    }

    //public void getDataPembeli(){
//        String nama = Nama.getText().toString();
//        String alamat = Alamat.getText().toString();
//        String telp = Telp.getText().toString();
//        //String kategori = String.valueOf(kategoriProduk.getSelectedItemId());
//        String kode = String.valueOf(kodeProduk.getSelectedItemId());
//        String bayar = mBayar.getText().toString();
//        String jml = jmlProduk.getText().toString();
//        String total = totalHarga.getText().toString();
//
//        if (nama.isEmpty()) {
//            Nama.setError("Nama tidak boleh kosong");
//            Nama.requestFocus();
//            return;
//        }
//
//        if (alamat.isEmpty()) {
//            Alamat.setError("Alamat tidak boleh kosong");
//            Alamat.requestFocus();
//            return;
//        }
//
//        if (telp.isEmpty()) {
//            Telp.setError("Telepon tidak boleh kosong");
//            Telp.requestFocus();
//            return;
//        }
//
//        if (jml.isEmpty()) {
//            jmlProduk.setError("Jumlah tidak boleh kosong");
//            jmlProduk.requestFocus();
//            return;
//        }
//
//        if (!TextUtils.isEmpty(nama)){
//            String id = databaseData.push().getKey();
//            DataPembeli data = new DataPembeli(id, nama, alamat, telp, kode, jml, bayar, total);
//            databaseData.child(id).setValue(data);
//
//            Toast.makeText(this, "Data berhasil", Toast.LENGTH_LONG).show();
//        } else {
//            //Toast.makeText(this, "Kamu belum mengisi form nama", Toast.LENGTH_LONG).show();
//        }
//
//        Intent intent = new Intent(BeliActivity.this, DetailBeliActivity.class);
//        intent.putExtra("Nama", nama);
//        intent.putExtra("Alamat", alamat);
//        intent.putExtra("Telepon", telp);
//        //intent.putExtra("Kategori", kategori);
//        intent.putExtra("Kode", kode);
//        intent.putExtra("Jumlah", jml);
//        intent.putExtra("Bayar", bayar);
//        intent.putExtra("Total Harga", total);
//        startActivity(intent);
    //}
}
