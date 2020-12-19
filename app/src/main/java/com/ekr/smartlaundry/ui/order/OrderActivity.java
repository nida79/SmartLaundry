package com.ekr.smartlaundry.ui.order;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.data.CuciModel;
import com.ekr.smartlaundry.utils.ResiHelper;
import com.ekr.smartlaundry.utils.Session;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rishabhharit.roundedimageview.RoundedImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import dmax.dialog.SpotsDialog;

import static com.ekr.smartlaundry.utils.GlobalPath.DB_LAUNDRY;
import static com.ekr.smartlaundry.utils.GlobalPath.STORAGE;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = "CEK";
    private Session session;
    private RadioGroup radioGroup;
    private int qty_1 = 0;
    private int qty_2 = 0;
    private int qty_3 = 0;
    private int qty_4 = 0;
    private int qty_5 = 0;
    private int qty_6 = 0;
    private int qty_7 = 0;
    private int qty_8 = 0;
    private final int harga_1 = 1500;
    private final int harga_2 = 2500;
    private final int harga_3 = 3000;
    private final int harga_4 = 5000;
    private final int harga_5 = 7000;
    private final int harga_6 = 3500;
    private final int harga_7 = 2000;
    private final int harga_8 = 3500;
    private int total_harga = 0;
    private CuciModel productItem;
    private TextView tv_total_qty1, tv_total_qty2, tv_total_qty3, tv_total_qty4,
            tv_total_qty5, tv_total_qty6, tv_total_qty7, tv_total_qty8,textViewTipe;
    private ImageView plus_1, plus_2, plus_3, plus_4, plus_5, plus_6, plus_7, plus_8;
    private ImageView minus_1, minus_2, minus_3, minus_4, minus_5, minus_6, minus_7, minus_8;
    private EditText tie_alamat, tie_nohp, tie_keterangan;
    private String tipe_pesanan, tipe_pembayaran, alamat_order, tanggal_order, nohp_order, keyID, keterangan_order, resi;
    private TextView textView_totalHarga, textView_norek;
    private Button button_hitung, button_upload, button_Submit, button_kembali;
    private RoundedImageView img_buktiTransfer;
    private Uri imageuri;
    private android.app.AlertDialog dialog;
    private StorageReference mStorageReference;
    private DatabaseReference dbOrder;
    private String imageName;
    private RadioButton rb_cash, rb_trf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        dialog = new SpotsDialog.Builder().setContext(OrderActivity.this).build();
        dialog.setMessage("Mohon Tunggu");
        dialog.setIcon(R.mipmap.ic_smartlaundry);
        initListener();
        settingCheckbox();
        takePicture();
        calculate();
        hitungTotal();
        tombolSubmit();
        Calendar calendar = Calendar.getInstance();
        tanggal_order = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
    }

    private void tombolSubmit() {
        button_Submit.setOnClickListener(view -> {
            if (tipe_pesanan.equals("Setrika")) {
                resi = "SA-" + ResiHelper.getRandomString(5);
            } else if (tipe_pesanan.equals("Cuci Kering")) {
                resi = "CK-" + ResiHelper.getRandomString(5);
            }else {
                resi = "CS-"+ResiHelper.getRandomString(5);
            }
            if (rb_trf.isChecked()) {
                tipe_pembayaran = "Transfer";
            }
            if (rb_cash.isChecked()) {
                tipe_pembayaran = "Tunai";
            }
            alamat_order = tie_alamat.getText().toString();
            nohp_order = tie_nohp.getText().toString();
            keterangan_order = tie_keterangan.getText().toString();
            if (total_harga == 0) {
                Toast.makeText(getApplicationContext(), "Harga Belum Dihitung",
                        Toast.LENGTH_SHORT).show();
            } else if (tie_alamat.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Alamat Tidak Boleh Kosong",
                        Toast.LENGTH_SHORT).show();
            } else if (tie_nohp.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Alamat Tidak Boleh Kosong",
                        Toast.LENGTH_SHORT).show();
            } else {
                if (tipe_pembayaran.equals("Transfer")) {
                    if (imageuri == null) {
                        Toast.makeText(getApplicationContext(), "Gambar Belum Di Upload", Toast.LENGTH_SHORT).show();
                    } else {
                        new android.app.AlertDialog.Builder(OrderActivity.this)
                                .setTitle("Konfirmasi Pemesanan!")
                                .setIcon(R.mipmap.ic_launcher)
                                .setMessage("Apakah Anda Yakin Ingin Pesanan Sudah Sesuai ?")
                                .setCancelable(false)
                                .setPositiveButton("Ya", (dialog, which) -> bayarTransfer())
                                .setNegativeButton("Tidak", null)
                                .show();
                    }

                } else {
                    new android.app.AlertDialog.Builder(OrderActivity.this)
                            .setTitle("Konfirmasi Pemesanan!")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("Apakah Anda Yakin Ingin Pesanan Sudah Sesuai ?")
                            .setCancelable(false)
                            .setPositiveButton("Ya", (dialog, which) -> bayarCash())
                            .setNegativeButton("Tidak", null)
                            .show();
                }
            }
        });
    }

    private void bayarCash() {
        dialog.show();
        keyID = dbOrder.push().getKey();
        productItem.setAlamat(alamat_order);
        productItem.setFoto("");
        productItem.setKeterangan(keterangan_order);
        productItem.setKeyID(keyID);
        productItem.setNama(session.getSPNama());
        productItem.setResi(resi);
        productItem.setNoHp(nohp_order);
        productItem.setTanggal(tanggal_order);
        productItem.setUuid(session.getSPUid());
        productItem.setStatus("Waiting");
        productItem.setTipe_pembayaran(tipe_pembayaran);
        productItem.setTipe_pesanan(tipe_pesanan);
        productItem.setTotal_pembayaran(String.valueOf(total_harga));
        dbOrder.push().setValue(productItem).addOnSuccessListener(this, aVoid -> {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Pemesanan Berhasil ", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void bayarTransfer() {

        dialog.show();
        StorageReference folderFoto = mStorageReference.child(STORAGE + imageName);
        UploadTask uploadTask = folderFoto.putFile(imageuri);
        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                throw Objects.requireNonNull(task.getException());
            }
            return folderFoto.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri resultUri = task.getResult();
                String uploadImage = resultUri.toString();
                keyID = dbOrder.push().getKey();
                productItem.setAlamat(alamat_order);
                productItem.setFoto(uploadImage);
                productItem.setKeterangan(keterangan_order);
                productItem.setKeyID(keyID);
                productItem.setNama(session.getSPNama());
                productItem.setResi(resi);
                productItem.setNoHp(nohp_order);
                productItem.setTanggal(tanggal_order);
                productItem.setUuid(session.getSPUid());
                productItem.setStatus("Waiting");
                productItem.setTipe_pembayaran(tipe_pembayaran);
                productItem.setTipe_pesanan(tipe_pesanan);
                productItem.setTotal_pembayaran(String.valueOf(total_harga));
                dbOrder.push().setValue(productItem).addOnSuccessListener(this, aVoid -> {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Pemesanan Berhasil ", Toast.LENGTH_SHORT).show();
                    finish();
                }).addOnFailureListener(e -> {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });


            } else {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void calculate() {
        plus_1.setOnClickListener(view -> {
            qty_1++;
            tv_total_qty1.setText(String.valueOf(qty_1));
            int hasil = Integer.parseInt(tv_total_qty1.getText().toString());
            if (hasil < 1) {
                minus_1.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_1.setVisibility(View.VISIBLE);
            }
        });
        plus_2.setOnClickListener(view -> {
            qty_2++;
            tv_total_qty2.setText(String.valueOf(qty_2));
            int hasil = Integer.parseInt(tv_total_qty2.getText().toString());
            if (hasil < 1) {
                minus_2.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_2.setVisibility(View.VISIBLE);
            }
        });
        plus_3.setOnClickListener(view -> {
            qty_3++;
            tv_total_qty3.setText(String.valueOf(qty_3));
            int hasil = Integer.parseInt(tv_total_qty3.getText().toString());
            if (hasil < 1) {
                minus_3.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_3.setVisibility(View.VISIBLE);
            }
        });
        plus_4.setOnClickListener(view -> {
            qty_4++;
            tv_total_qty4.setText(String.valueOf(qty_4));
            int hasil = Integer.parseInt(tv_total_qty4.getText().toString());
            if (hasil < 1) {
                minus_4.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_4.setVisibility(View.VISIBLE);
            }
        });
        plus_5.setOnClickListener(view -> {
            qty_5++;
            tv_total_qty5.setText(String.valueOf(qty_5));
            int hasil = Integer.parseInt(tv_total_qty5.getText().toString());
            if (hasil < 1) {
                minus_5.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_5.setVisibility(View.VISIBLE);
            }
        });
        plus_6.setOnClickListener(view -> {
            qty_6++;
            tv_total_qty6.setText(String.valueOf(qty_6));
            int hasil = Integer.parseInt(tv_total_qty6.getText().toString());
            if (hasil < 1) {
                minus_6.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_6.setVisibility(View.VISIBLE);
            }
        });
        plus_7.setOnClickListener(view -> {
            qty_7++;
            tv_total_qty7.setText(String.valueOf(qty_7));
            int hasil = Integer.parseInt(tv_total_qty7.getText().toString());
            if (hasil < 1) {
                minus_7.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_7.setVisibility(View.VISIBLE);
            }
        });
        plus_8.setOnClickListener(view -> {
            qty_8++;
            tv_total_qty8.setText(String.valueOf(qty_8));
            int hasil = Integer.parseInt(tv_total_qty8.getText().toString());
            if (hasil < 1) {
                minus_8.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_8.setVisibility(View.VISIBLE);
            }
        });

        minus_1.setOnClickListener(view -> {
            qty_1--;
            tv_total_qty1.setText(String.valueOf(qty_1));
            int hasil = Integer.parseInt(tv_total_qty1.getText().toString());
            if (hasil < 1) {
                minus_1.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_1.setVisibility(View.VISIBLE);
            }
        });
        minus_2.setOnClickListener(view -> {
            qty_2--;
            tv_total_qty2.setText(String.valueOf(qty_2));
            int hasil = Integer.parseInt(tv_total_qty2.getText().toString());
            if (hasil < 1) {
                minus_2.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_2.setVisibility(View.VISIBLE);
            }
        });
        minus_3.setOnClickListener(view -> {
            qty_3--;
            tv_total_qty3.setText(String.valueOf(qty_3));
            int hasil = Integer.parseInt(tv_total_qty3.getText().toString());
            if (hasil < 1) {
                minus_3.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_3.setVisibility(View.VISIBLE);
            }
        });
        minus_4.setOnClickListener(view -> {
            qty_4--;
            tv_total_qty4.setText(String.valueOf(qty_4));
            int hasil = Integer.parseInt(tv_total_qty4.getText().toString());
            if (hasil < 1) {
                minus_4.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_4.setVisibility(View.VISIBLE);
            }
        });
        minus_5.setOnClickListener(view -> {
            qty_5--;
            tv_total_qty5.setText(String.valueOf(qty_5));
            int hasil = Integer.parseInt(tv_total_qty5.getText().toString());
            if (hasil < 1) {
                minus_5.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_5.setVisibility(View.VISIBLE);
            }
        });
        minus_6.setOnClickListener(view -> {
            qty_6--;
            tv_total_qty6.setText(String.valueOf(qty_6));
            int hasil = Integer.parseInt(tv_total_qty6.getText().toString());
            if (hasil < 1) {
                minus_6.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_6.setVisibility(View.VISIBLE);
            }
        });
        minus_7.setOnClickListener(view -> {
            qty_7--;
            tv_total_qty7.setText(String.valueOf(qty_7));
            int hasil = Integer.parseInt(tv_total_qty7.getText().toString());
            if (hasil < 1) {
                minus_7.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_7.setVisibility(View.VISIBLE);
            }
        });
        minus_8.setOnClickListener(view -> {
            qty_8--;
            tv_total_qty8.setText(String.valueOf(qty_8));
            int hasil = Integer.parseInt(tv_total_qty8.getText().toString());
            if (hasil < 1) {
                minus_8.setVisibility(View.INVISIBLE);
            }
            if (hasil > 0) {
                minus_8.setVisibility(View.VISIBLE);
            }
        });


    }

    private void hitungTotal() {
        button_hitung.setOnClickListener(view -> {
            int restult1 = harga_1 * qty_1;
            int restult2 = harga_2 * qty_2;
            int restult3 = harga_3 * qty_3;
            int restult4 = harga_4 * qty_4;
            int restult5 = harga_5 * qty_5;
            int restult6 = harga_6 * qty_6;
            int restult7 = harga_7 * qty_7;
            int restult8 = harga_8 * qty_8;
            total_harga = restult1 + restult2 + restult3 + restult4 + restult5 + restult6 + restult7 + restult8;
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            formatRupiah.setMaximumFractionDigits(0);
            textView_totalHarga.setText(formatRupiah.format((double) total_harga));
        });
    }

    private void takePicture() {
        button_upload.setOnClickListener(view -> {
            Dexter.withActivity(this)
                    .withPermissions(
                            Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {

                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            // check if all permissions are granted
                            if (report.areAllPermissionsGranted()) {
                                CropImage.activity()
                                        .setGuidelines(CropImageView.Guidelines.ON)
                                        .start(OrderActivity.this);
                            }

                            // check for permanent denial of any permission
                            if (report.isAnyPermissionPermanentlyDenied()) {
                                // show alert dialog navigating to Settings
                                showSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).
                    withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_LONG).show())
                    .onSameThread()
                    .check();

        });
    }

    private void initListener() {
        session = new Session(this);
        imageName = System.currentTimeMillis() + ".jpeg";
        mStorageReference = FirebaseStorage.getInstance().getReference();
        dbOrder = FirebaseDatabase.getInstance().getReference(DB_LAUNDRY);
        productItem = new CuciModel();
        tipe_pesanan = getIntent().getStringExtra("tipe_pesanan");
        textViewTipe = findViewById(R.id.tipe_oder);
        textViewTipe.setText(tipe_pesanan);
        textView_totalHarga = findViewById(R.id.tv_order_total);
        textView_norek = findViewById(R.id.text_no_rekOrder);
        button_hitung = findViewById(R.id.order_btn_hitung);
        rb_cash = findViewById(R.id.check_cash);
        rb_trf = findViewById(R.id.check_transfer);
        button_upload = findViewById(R.id.btn_upload_order);
        button_Submit = findViewById(R.id.btn_submit_order);
        img_buktiTransfer = findViewById(R.id.img_buktiTransfer_order);
        tie_alamat = findViewById(R.id.edt_alamat_order);
        tie_nohp = findViewById(R.id.edt_noHp_order);
        button_kembali = findViewById(R.id.btn_back_order);
        tie_keterangan = findViewById(R.id.edt_keterangan_order);
        identitasHitung();
        radioGroup = findViewById(R.id.radioGroup2);
        tie_alamat.setText(session.getSpAlamat());
        tie_nohp.setText(session.getSpNohp());
        button_kembali.setOnClickListener(view -> finish());

    }

    private void identitasHitung() {
        plus_1 = findViewById(R.id.plus_qty_1);
        plus_2 = findViewById(R.id.plus_qty_2);
        plus_3 = findViewById(R.id.plus_qty_3);
        plus_4 = findViewById(R.id.plus_qty_4);
        plus_5 = findViewById(R.id.plus_qty_5);
        plus_6 = findViewById(R.id.plus_qty_6);
        plus_7 = findViewById(R.id.plus_qty_7);
        plus_8 = findViewById(R.id.plus_qty_8);
        minus_1 = findViewById(R.id.minus_qty_1);
        minus_2 = findViewById(R.id.minus_qty_2);
        minus_3 = findViewById(R.id.minus_qty_3);
        minus_4 = findViewById(R.id.minus_qty_4);
        minus_5 = findViewById(R.id.minus_qty_5);
        minus_6 = findViewById(R.id.minus_qty_6);
        minus_7 = findViewById(R.id.minus_qty_7);
        minus_8 = findViewById(R.id.minus_qty_8);
        tv_total_qty1 = findViewById(R.id.tv_total_qty1);
        tv_total_qty2 = findViewById(R.id.tv_total_qty_2);
        tv_total_qty3 = findViewById(R.id.tv_total_qty_3);
        tv_total_qty4 = findViewById(R.id.tv_total_qty_4);
        tv_total_qty5 = findViewById(R.id.tv_total_qty_5);
        tv_total_qty6 = findViewById(R.id.tv_total_qty_6);
        tv_total_qty7 = findViewById(R.id.tv_total_qty_7);
        tv_total_qty8 = findViewById(R.id.tv_total_qty_8);
    }

    @SuppressLint("NonConstantResourceId")
    private void settingCheckbox() {

        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.check_cash:
                    tipe_pembayaran = "Tunai";
                    button_upload.setVisibility(View.GONE);
                    textView_norek.setVisibility(View.GONE);
                    img_buktiTransfer.setVisibility(View.GONE);
                    break;
                case R.id.check_transfer:
                    tipe_pembayaran = "Transfer";
                    button_upload.setVisibility(View.VISIBLE);
                    textView_norek.setVisibility(View.VISIBLE);
                    if (imageuri != null) {
                        img_buktiTransfer.setVisibility(View.VISIBLE);
                    }
                    break;
            }

        });
    }

    private void showSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
        builder.setTitle("Perizinan Diperlukan");
        builder.setMessage("Aktifkan Perizinan untuk Mengupload Gambar");
        builder.setPositiveButton("BUKA PENGATURAN", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (result != null) {
                    imageuri = result.getUri();
                }
                img_buktiTransfer.setImageURI(imageuri);
                img_buktiTransfer.setVisibility(View.VISIBLE);
            }
        }
    }
}