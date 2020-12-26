package com.ekr.smartlaundry.ui.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.bumptech.glide.Glide;
import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.data.CuciModel;
import com.ekr.smartlaundry.utils.GlobalPath;
import com.ekr.smartlaundry.utils.Session;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.rishabhharit.roundedimageview.RoundedImageView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static com.ekr.smartlaundry.utils.GlobalPath.step1;
import static com.ekr.smartlaundry.utils.GlobalPath.step2;
import static com.ekr.smartlaundry.utils.GlobalPath.step3;

public class DetailActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private Query query;
    private android.app.AlertDialog alertDialog;
    private Button update, back;
    private NumberFormat formatRupiah;
    private LinearLayout linearLayout;
    private HorizontalStepView horizontalStepView;
    private Session session;
    private TextView textView_tanggal, textView_nama, textView_alamat, textView_noHP, textView_totalHarga, textView_resi,
            textView_tipePesan, textView_pembayaran, textView_catatan, tv_bukti;
    private RoundedImageView imageView_bukti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        session = new Session(this);
        initializeID();

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initializeID() {
        Intent intent = getIntent();
        CuciModel terimadata = intent.getParcelableExtra(GlobalPath.DATA);
        update = findViewById(R.id.detail_btn_update);
        back = findViewById(R.id.detail_btn_back);
        linearLayout = findViewById(R.id.detail_ll);
        textView_alamat = findViewById(R.id.detail_Alamat);
        textView_tanggal = findViewById(R.id.detail_tanggal);
        textView_nama = findViewById(R.id.detail_nama);
        horizontalStepView = findViewById(R.id.detail_status);
        textView_noHP = findViewById(R.id.detail_HP);
        textView_totalHarga = findViewById(R.id.detail_harga);
        textView_resi = findViewById(R.id.detail_resi);
        textView_tipePesan = findViewById(R.id.detail_tipePesanan);
        textView_pembayaran = findViewById(R.id.detail_bayar);
        textView_catatan = findViewById(R.id.detail_catatan);
        tv_bukti = findViewById(R.id.tv_bukti_trf);
        imageView_bukti = findViewById(R.id.detail_bukti);

        if (terimadata != null) {
            textView_alamat.setText(terimadata.getAlamat());
            textView_tanggal.setText(terimadata.getTanggal());
            textView_nama.setText(terimadata.getNama());
            textView_noHP.setText(terimadata.getNoHp());
            textView_totalHarga.setText(terimadata.getNoHp());
            textView_resi.setText(terimadata.getResi());
            textView_pembayaran.setText(terimadata.getTipe_pembayaran());
            textView_tipePesan.setText(terimadata.getTipe_pesanan());
            textView_catatan.setText(terimadata.getKeterangan());
            String foto = terimadata.getFoto();
            String proses = terimadata.getStatus();
            String rule = session.getSpRule();
            if (rule.equals("admin")) {
                if (!terimadata.getStatus().equals("Selesai")){
                    linearLayout.setWeightSum(2);
                    update.setVisibility(View.VISIBLE);
                }else {
                    linearLayout.setWeightSum(1);
                    update.setVisibility(View.GONE);
                }

            } else {
                linearLayout.setWeightSum(1);
                update.setVisibility(View.GONE);
            }
            if (terimadata.getTipe_pembayaran().equals("Transfer")) {
                if (foto != null || !foto.equals("")) {
                    tv_bukti.setVisibility(View.VISIBLE);
                    imageView_bukti.setVisibility(View.VISIBLE);
                    Glide.with(this).load(foto).into(imageView_bukti);
                } else {
                    tv_bukti.setVisibility(View.GONE);
                    imageView_bukti.setVisibility(View.GONE);
                }
            }

            switch (proses) {
                case "Menunggu":
                    step1 = 0;
                    step2 = -1;
                    step3 = -1;
                    break;
                case "Proses":
                    step1 = 1;
                    step2 = 0;
                    step3 = -1;
                    break;
                case "Selesai":
                    step1 = 1;
                    step2 = 1;
                    step3 = 1;
                    break;
            }
            List<StepBean> stepBeans = new ArrayList<>();
            stepBeans.add(new StepBean("Menunggu", step1));
            stepBeans.add(new StepBean("Proses", step2));
            stepBeans.add(new StepBean("Selesai", step3));

            horizontalStepView.setStepViewTexts(stepBeans)
                    .setTextSize(13)
                    .setStepsViewIndicatorCompletedLineColor(getApplicationContext().getColor(R.color.background))
                    .setStepsViewIndicatorUnCompletedLineColor(getApplicationContext().getColor(R.color.orange))
                    .setStepViewComplectedTextColor(getApplicationContext().getColor(R.color.black))
                    .setStepViewUnComplectedTextColor(getApplicationContext().getColor(R.color.black))
                    .setStepsViewIndicatorCompleteIcon(getApplicationContext().getDrawable(R.drawable.sukses))
                    .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.default_icon))
                    .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.attention));

        }
        back.setOnClickListener(view -> finish());
    }
}