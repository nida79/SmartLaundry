package com.ekr.smartlaundry.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.ui.auth.LoginActivity;
import com.ekr.smartlaundry.ui.detail.DetailActivity;
import com.ekr.smartlaundry.utils.Session;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

import static com.ekr.smartlaundry.utils.GlobalPath.DB_USER;


public class ProfileFragment extends Fragment {
    private Session session;
    private String nama, alamat, email, noHp,rule;
    private TextView tv_nama,tv_email,tv_alamat,tv_hape;
    private Query query;
    private  Dialog dialoEdit;
    private android.app.AlertDialog alertDialog;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private Button btn_logout,btn_edit;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(requireContext());
        alertDialog = new SpotsDialog.Builder().setContext(requireActivity()).build();
        alertDialog.setMessage("Mohon Tunggu");
        alertDialog.setIcon(R.mipmap.ic_smartlaundry);
        auth = FirebaseAuth.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.keepSynced(true);
        DatabaseReference userRef = rootRef.child(DB_USER);
        query = userRef.orderByChild("uuid").equalTo(session.getSPUid());
        getData();
        authListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
            if (user1 == null) {
                startActivity(new Intent(requireActivity(), LoginActivity.class));
                requireActivity().finishAffinity();
                requireActivity().finish();
            }
        };


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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    nama = dataSnapshot.child("nama").getValue(String.class);
                    email = dataSnapshot.child("email").getValue(String.class);
                    noHp = dataSnapshot.child("nohp").getValue(String.class);
                    alamat = dataSnapshot.child("alamat").getValue(String.class);
                    rule = dataSnapshot.child("rule").getValue(String.class);
                }
                tv_alamat.setText(alamat);
                tv_nama.setText(nama);
                tv_hape.setText(noHp);
                tv_email.setText(email);
                session.saveSPString(Session.SP_EMAIL, email);
                session.saveSPString(Session.SP_NAMA, nama);
                session.saveSPString(Session.SP_NOHP, noHp);
                session.saveSPString(Session.SP_ALAMAT, alamat);
                session.saveSPString(Session.SP_RULE, rule);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                alertDialog.dismiss();
                Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Home", "cek"+error.getDetails());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }
    @Override
    public void onStop() {
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
        super.onStop();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btn_edit = view.findViewById(R.id.btn_edit_profile);
        btn_logout = view.findViewById(R.id.btn_logout);
        tv_nama = view.findViewById(R.id.profile_nama);
        tv_email = view.findViewById(R.id.profile_email);
        tv_alamat = view.findViewById(R.id.alamat_profile);
        tv_hape = view.findViewById(R.id.profile_hp);
        btn_logout.setOnClickListener(v -> new AlertDialog.Builder(requireActivity())
                .setTitle("SignOut Akun!")
                .setIcon(R.mipmap.ic_smartlaundry)
                .setMessage("Apakah Anda Yakin Ingin Keluar Akun ?")
                .setCancelable(false)
                .setPositiveButton("Ya", (dialog, which) -> ProfileFragment.this.auth.signOut())
                .setNegativeButton("Tidak", null)
                .show());

        btn_edit.setOnClickListener(view1 -> {
            dialoEdit = new Dialog(requireContext());
            dialoEdit.setContentView(R.layout.alert_dialog_edit);
            dialoEdit.setCanceledOnTouchOutside(false);
            Objects.requireNonNull(dialoEdit.getWindow()).setLayout(WindowManager.LayoutParams.MATCH_PARENT
                    , WindowManager.LayoutParams.WRAP_CONTENT);
            dialoEdit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialoEdit.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            dialoEdit.show();
            EditText editTextNama,editAlamat,editTelop;
            editTextNama = dialoEdit.findViewById(R.id.edt_nama);
            editAlamat = dialoEdit.findViewById(R.id.edt_alamat);
            editTelop = dialoEdit.findViewById(R.id.edt_telp);
            editTextNama.setText(tv_nama.getText().toString());
            editAlamat.setText(tv_alamat.getText().toString());
            editTelop.setText(tv_hape.getText().toString());
            Button btn_cancel,btn_save;
            btn_cancel = dialoEdit.findViewById(R.id.cancel_edit);
            btn_save = dialoEdit.findViewById(R.id.submit_edit);
            btn_cancel.setOnClickListener(view2 -> dialoEdit.dismiss());
            btn_save.setOnClickListener(view2 -> {
                String name,address,phone;
                name = editTextNama.getText().toString();
                address = editAlamat.getText().toString();
                phone = editTelop.getText().toString();
                if (name.isEmpty() || address.isEmpty() || phone.isEmpty()){
                    Toast.makeText(requireContext(),"Kolom Pengisian Tidak Boleh Kosong",Toast.LENGTH_SHORT).show();
                }else {
                    saveToFirebase(name,address,phone);
                }
            });
        });

        return view;
    }
    private void saveToFirebase(String name, String address, String phone) {
        alertDialog.show();
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot updatedata : snapshot.getChildren()) {
                    updatedata.getRef().child("nama").setValue(name);
                    updatedata.getRef().child("alamat").setValue(address);
                    updatedata.getRef().child("nohp").setValue(phone);
                }
                session.saveSPString(Session.SP_NAMA,name);
                session.saveSPString(Session.SP_ALAMAT,address);
                session.saveSPString(Session.SP_NOHP,phone);
                tv_alamat.setText(address);
                tv_nama.setText(name);
                tv_hape.setText(phone);
                alertDialog.dismiss();
                dialoEdit.dismiss();
                Toast.makeText(requireContext(),"Update Berhasil",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                alertDialog.dismiss();
                dialoEdit.dismiss();
                Toast.makeText(requireContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}