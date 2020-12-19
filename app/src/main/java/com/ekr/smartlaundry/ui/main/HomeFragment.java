package com.ekr.smartlaundry.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.ui.InfoActivity;
import com.ekr.smartlaundry.ui.order.OrderActivity;
import com.ekr.smartlaundry.ui.order.PaketActivity;
import com.ekr.smartlaundry.utils.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import dmax.dialog.SpotsDialog;

import static com.ekr.smartlaundry.utils.GlobalPath.DB_USER;


public class HomeFragment extends Fragment {
    private CardView cardView_paket, cardView_cuci, cardView_setrika, cardView_info;
    private Session session;
    private String nama, alamat, email, noHp;
    private Query query;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(requireContext());
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.keepSynced(true);
        DatabaseReference userRef = rootRef.child(DB_USER);
        query = userRef.orderByChild("uuid").equalTo(session.getSPUid());
        getData();

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
                }
                session.saveSPString(Session.SP_EMAIL, email);
                session.saveSPString(Session.SP_NAMA, nama);
                session.saveSPString(Session.SP_NOHP, noHp);
                session.saveSPString(Session.SP_ALAMAT, alamat);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cardView_paket = view.findViewById(R.id.cv_paket);
        cardView_cuci = view.findViewById(R.id.cv_cuci);
        cardView_setrika = view.findViewById(R.id.cv_setrika);
        cardView_info = view.findViewById(R.id.cv_info);
        setupIntent();
        return view;
    }

    private void setupIntent() {
        cardView_paket.setOnClickListener(v -> {
            Intent paket = new Intent(requireActivity(),PaketActivity.class);
            paket.putExtra("tipe_pesanan","Cuci Setrika");
            startActivity(paket);
        });
        cardView_cuci.setOnClickListener(v -> {
            Intent cuci = new Intent(requireActivity(),OrderActivity.class);
            cuci.putExtra("tipe_pesanan","Cuci");
            startActivity(cuci);

        });
        cardView_setrika.setOnClickListener(v -> {
            Intent setrika = new Intent(requireActivity(),OrderActivity.class);
            setrika.putExtra("tipe_pesanan","Setrika");
            startActivity(setrika);
        });
        cardView_info.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), InfoActivity.class));
        });
    }
}