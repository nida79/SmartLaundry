package com.ekr.smartlaundry.ui.admin;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.data.HargaModel;
import com.ekr.smartlaundry.utils.MoneyHelper;
import com.ekr.smartlaundry.utils.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

import static com.ekr.smartlaundry.utils.GlobalPath.DB_HARGA;
import static com.ekr.smartlaundry.utils.GlobalPath.DB_HARGA_PAKET;
import static com.ekr.smartlaundry.utils.GlobalPath.DB_ITEM;
import static com.ekr.smartlaundry.utils.GlobalPath.DB_ITEM2;
import static com.ekr.smartlaundry.utils.GlobalPath.DB_USER;


public class DashboardFragment extends Fragment {
    private Session session;
    private Button button_1, button_2;
    private Query query, query2;
    private android.app.AlertDialog alertDialog;
    private Dialog dialoEdit;
    private DatabaseReference rootRef;
    DatabaseReference rootRef2;
    private TextView textView_1, textView_2, textView_3,
            textView_4, textView_5, textView_6, textView_7, textView_8, textView_9;
    private TextView textViewcs_1, textViewcs_2, textViewcs_3,
            textViewcs_4, textViewcs_5, textViewcs_6, textViewcs_7, textViewcs_8, textViewcs_9;
    private int c_1, c_2, c_3, c_4, c_5, c_6, c_7, c_8, c_9;
    private int cs_1, cs_2, cs_3, cs_4, cs_5, cs_6, cs_7, cs_8, cs_9;
    private HargaModel model;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(requireContext());
        alertDialog = new SpotsDialog.Builder().setContext(requireActivity()).build();
        alertDialog.setMessage("Mohon Tunggu");
        alertDialog.setIcon(R.mipmap.ic_smartlaundry);
        rootRef = FirebaseDatabase.getInstance().getReference(DB_ITEM2);
        rootRef.keepSynced(true);
        query = rootRef.orderByChild(DB_HARGA_PAKET);
        model = new HargaModel();
        rootRef2 = FirebaseDatabase.getInstance().getReference(DB_ITEM);
        rootRef2.keepSynced(true);
        query2 = rootRef2.orderByChild(DB_HARGA);
        getData();
        getData2();
    }

    private void getData() {
        final android.app.AlertDialog alertDialog =
                new SpotsDialog.Builder().setContext(requireActivity()).build();
        alertDialog.setMessage("Mohon Tunggu");
        alertDialog.setIcon(R.mipmap.ic_smartlaundry);
        alertDialog.show();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alertDialog.dismiss();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    c_1 = ds.child("harga_1").getValue(Integer.class);
                    c_2 = ds.child("harga_2").getValue(Integer.class);
                    c_3 = ds.child("harga_3").getValue(Integer.class);
                    c_4 = ds.child("harga_4").getValue(Integer.class);
                    c_5 = ds.child("harga_5").getValue(Integer.class);
                    c_6 = ds.child("harga_6").getValue(Integer.class);
                    c_7 = ds.child("harga_7").getValue(Integer.class);
                    c_8 = ds.child("harga_8").getValue(Integer.class);
                    c_9 = ds.child("harga_9").getValue(Integer.class);
                }
                MoneyHelper.setRupiah(textView_1, c_1);
                MoneyHelper.setRupiah(textView_2, c_2);
                MoneyHelper.setRupiah(textView_3, c_3);
                MoneyHelper.setRupiah(textView_4, c_4);
                MoneyHelper.setRupiah(textView_5, c_5);
                MoneyHelper.setRupiah(textView_6, c_6);
                MoneyHelper.setRupiah(textView_7, c_7);
                MoneyHelper.setRupiah(textView_8, c_8);
                MoneyHelper.setRupiah(textView_9, c_9);
                button_1.setOnClickListener(view1 -> {
                    dialoEdit = new Dialog(requireContext());
                    dialoEdit.setContentView(R.layout.dialog_edit_harga);
                    dialoEdit.setCanceledOnTouchOutside(false);
                    Objects.requireNonNull(dialoEdit.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT
                            , WindowManager.LayoutParams.WRAP_CONTENT);
                    dialoEdit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialoEdit.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                    dialoEdit.show();
                    EditText editText_1, editText_2, editText_3, editText_4, editText_5, editText_6, editText_7,
                            editText_8, editText_9;
                    Button btn_cancel, btn_Save;
                    btn_cancel = dialoEdit.findViewById(R.id.btn_batal);
                    btn_Save = dialoEdit.findViewById(R.id.btn_simpan);
                    editText_1 = dialoEdit.findViewById(R.id.edt_harga_baju);
                    editText_2 = dialoEdit.findViewById(R.id.edt_harga_celana);
                    editText_3 = dialoEdit.findViewById(R.id.edt_harga_levis);
                    editText_4 = dialoEdit.findViewById(R.id.edt_harga_sprei);
                    editText_5 = dialoEdit.findViewById(R.id.edt_harga_selimut);
                    editText_6 = dialoEdit.findViewById(R.id.edt_harga_bed);
                    editText_7 = dialoEdit.findViewById(R.id.edt_harga_jaket);
                    editText_8 = dialoEdit.findViewById(R.id.edt_harga_handuk);
                    editText_9 = dialoEdit.findViewById(R.id.edt_harga_mukena);
                    editText_1.setText(String.valueOf(c_1));
                    editText_2.setText(String.valueOf(c_2));
                    editText_3.setText(String.valueOf(c_3));
                    editText_4.setText(String.valueOf(c_4));
                    editText_5.setText(String.valueOf(c_5));
                    editText_6.setText(String.valueOf(c_6));
                    editText_7.setText(String.valueOf(c_7));
                    editText_8.setText(String.valueOf(c_8));
                    editText_9.setText(String.valueOf(c_9));
                    btn_cancel.setOnClickListener(view2 -> {
                        dialoEdit.dismiss();
                    });
                    btn_Save.setOnClickListener(view -> {
                        int baju = Integer.parseInt(editText_1.getText().toString());
                        int celana = Integer.parseInt(editText_2.getText().toString());
                        int levis = Integer.parseInt(editText_3.getText().toString());
                        int sprei = Integer.parseInt(editText_4.getText().toString());
                        int selimut = Integer.parseInt(editText_5.getText().toString());
                        int bed = Integer.parseInt(editText_6.getText().toString());
                        int jaket = Integer.parseInt(editText_7.getText().toString());
                        int handuk = Integer.parseInt(editText_8.getText().toString());
                        int mukena = Integer.parseInt(editText_9.getText().toString());
                        if (editText_1.getText().toString().isEmpty() ||
                                editText_2.getText().toString().isEmpty() ||
                                editText_3.getText().toString().isEmpty() ||
                                editText_4.getText().toString().isEmpty() ||
                                editText_5.getText().toString().isEmpty() ||
                                editText_6.getText().toString().isEmpty() ||
                                editText_7.getText().toString().isEmpty() ||
                                editText_8.getText().toString().isEmpty() ||
                                editText_9.getText().toString().isEmpty()
                        ) {
                            Toast.makeText(requireContext(), "Kolom Pengisian Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                        } else {
                            saveToFirebase(baju, celana, levis, sprei, selimut, bed, jaket, handuk, mukena);
                        }
                    });

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                alertDialog.dismiss();
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Home", "cek" + error.getDetails());
            }
        });
    }

    private void saveToFirebase(int baju, int celana, int levis, int sprei,
                                int selimut, int bed, int jaket, int handuk, int mukena) {
        alertDialog.show();
        model.setHarga_1(baju);
        model.setHarga_2(celana);
        model.setHarga_3(levis);
        model.setHarga_4(sprei);
        model.setHarga_5(selimut);
        model.setHarga_6(bed);
        model.setHarga_7(jaket);
        model.setHarga_8(handuk);
        model.setHarga_9(mukena);
        rootRef.getRef().child(DB_HARGA_PAKET).setValue(model).addOnSuccessListener(requireActivity(), aVoid -> {
            alertDialog.dismiss();
            dialoEdit.dismiss();
        }).addOnFailureListener(e -> {
            alertDialog.dismiss();
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });


    }
    private void saveToFirebase2(int baju, int celana, int levis, int sprei,
                                int selimut, int bed, int jaket, int handuk, int mukena) {
        alertDialog.show();
        model.setHarga_1(baju);
        model.setHarga_2(celana);
        model.setHarga_3(levis);
        model.setHarga_4(sprei);
        model.setHarga_5(selimut);
        model.setHarga_6(bed);
        model.setHarga_7(jaket);
        model.setHarga_8(handuk);
        model.setHarga_9(mukena);
        rootRef2.getRef().child(DB_HARGA).setValue(model).addOnSuccessListener(requireActivity(), aVoid -> {
            alertDialog.dismiss();
            dialoEdit.dismiss();
        }).addOnFailureListener(e -> {
            alertDialog.dismiss();
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        });


    }

    private void getData2() {
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    cs_1 = ds.child("harga_1").getValue(Integer.class);
                    cs_2 = ds.child("harga_2").getValue(Integer.class);
                    cs_3 = ds.child("harga_3").getValue(Integer.class);
                    cs_4 = ds.child("harga_4").getValue(Integer.class);
                    cs_5 = ds.child("harga_5").getValue(Integer.class);
                    cs_6 = ds.child("harga_6").getValue(Integer.class);
                    cs_7 = ds.child("harga_7").getValue(Integer.class);
                    cs_8 = ds.child("harga_8").getValue(Integer.class);
                    cs_9 = ds.child("harga_9").getValue(Integer.class);
                }
                MoneyHelper.setRupiah(textViewcs_1, cs_1);
                MoneyHelper.setRupiah(textViewcs_2, cs_2);
                MoneyHelper.setRupiah(textViewcs_3, cs_3);
                MoneyHelper.setRupiah(textViewcs_4, cs_4);
                MoneyHelper.setRupiah(textViewcs_5, cs_5);
                MoneyHelper.setRupiah(textViewcs_6, cs_6);
                MoneyHelper.setRupiah(textViewcs_7, cs_7);
                MoneyHelper.setRupiah(textViewcs_8, cs_8);
                MoneyHelper.setRupiah(textViewcs_9, cs_9);
                button_2.setOnClickListener(view1 -> {
                    dialoEdit = new Dialog(requireContext());
                    dialoEdit.setContentView(R.layout.dialog_edit_harga);
                    dialoEdit.setCanceledOnTouchOutside(false);
                    Objects.requireNonNull(dialoEdit.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT
                            , WindowManager.LayoutParams.WRAP_CONTENT);
                    dialoEdit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialoEdit.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                    dialoEdit.show();
                    EditText editText_1, editText_2, editText_3, editText_4, editText_5, editText_6, editText_7,
                            editText_8, editText_9;
                    Button btn_cancel, btn_Save;
                    btn_cancel = dialoEdit.findViewById(R.id.btn_batal);
                    btn_Save = dialoEdit.findViewById(R.id.btn_simpan);
                    editText_1 = dialoEdit.findViewById(R.id.edt_harga_baju);
                    editText_2 = dialoEdit.findViewById(R.id.edt_harga_celana);
                    editText_3 = dialoEdit.findViewById(R.id.edt_harga_levis);
                    editText_4 = dialoEdit.findViewById(R.id.edt_harga_sprei);
                    editText_5 = dialoEdit.findViewById(R.id.edt_harga_selimut);
                    editText_6 = dialoEdit.findViewById(R.id.edt_harga_bed);
                    editText_7 = dialoEdit.findViewById(R.id.edt_harga_jaket);
                    editText_8 = dialoEdit.findViewById(R.id.edt_harga_handuk);
                    editText_9 = dialoEdit.findViewById(R.id.edt_harga_mukena);
                    editText_1.setText(String.valueOf(cs_1));
                    editText_2.setText(String.valueOf(cs_2));
                    editText_3.setText(String.valueOf(cs_3));
                    editText_4.setText(String.valueOf(cs_4));
                    editText_5.setText(String.valueOf(cs_5));
                    editText_6.setText(String.valueOf(cs_6));
                    editText_7.setText(String.valueOf(cs_7));
                    editText_8.setText(String.valueOf(cs_8));
                    editText_9.setText(String.valueOf(cs_9));
                    btn_cancel.setOnClickListener(view2 -> {
                        dialoEdit.dismiss();
                    });
                    btn_Save.setOnClickListener(view -> {
                        int baju = Integer.parseInt(editText_1.getText().toString());
                        int celana = Integer.parseInt(editText_2.getText().toString());
                        int levis = Integer.parseInt(editText_3.getText().toString());
                        int sprei = Integer.parseInt(editText_4.getText().toString());
                        int selimut = Integer.parseInt(editText_5.getText().toString());
                        int bed = Integer.parseInt(editText_6.getText().toString());
                        int jaket = Integer.parseInt(editText_7.getText().toString());
                        int handuk = Integer.parseInt(editText_8.getText().toString());
                        int mukena = Integer.parseInt(editText_9.getText().toString());
                        if (editText_1.getText().toString().isEmpty() ||
                                editText_2.getText().toString().isEmpty() ||
                                editText_3.getText().toString().isEmpty() ||
                                editText_4.getText().toString().isEmpty() ||
                                editText_5.getText().toString().isEmpty() ||
                                editText_6.getText().toString().isEmpty() ||
                                editText_7.getText().toString().isEmpty() ||
                                editText_8.getText().toString().isEmpty() ||
                                editText_9.getText().toString().isEmpty()
                        ) {
                            Toast.makeText(requireContext(), "Kolom Pengisian Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                        } else {
                            saveToFirebase2(baju, celana, levis, sprei, selimut, bed, jaket, handuk, mukena);
                        }
                    });
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Home", "cek" + error.getDetails());
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        inittialize(view);


        return view;
    }

    private void inittialize(View view) {
        textView_1 = view.findViewById(R.id.tv_c1);
        textView_2 = view.findViewById(R.id.tv_c2);
        textView_3 = view.findViewById(R.id.tv_c3);
        textView_4 = view.findViewById(R.id.tv_c4);
        textView_5 = view.findViewById(R.id.tv_c5);
        textView_6 = view.findViewById(R.id.tv_c6);
        textView_7 = view.findViewById(R.id.tv_c7);
        textView_8 = view.findViewById(R.id.tv_c8);
        textView_9 = view.findViewById(R.id.tv_c9);
        textViewcs_1 = view.findViewById(R.id.tv_cs1);
        textViewcs_2 = view.findViewById(R.id.tv_cs2);
        textViewcs_3 = view.findViewById(R.id.tv_cs3);
        textViewcs_4 = view.findViewById(R.id.tv_cs4);
        textViewcs_5 = view.findViewById(R.id.tv_cs5);
        textViewcs_6 = view.findViewById(R.id.tv_cs6);
        textViewcs_7 = view.findViewById(R.id.tv_cs7);
        textViewcs_8 = view.findViewById(R.id.tv_cs8);
        textViewcs_9 = view.findViewById(R.id.tv_cs9);
        button_1 = view.findViewById(R.id.btn_edit_harga_1);
        button_2 = view.findViewById(R.id.btn_edit_harga_2);
    }


}