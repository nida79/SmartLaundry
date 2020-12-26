package com.ekr.smartlaundry.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.adapter.HistoryAdapter;
import com.ekr.smartlaundry.data.CuciModel;
import com.ekr.smartlaundry.ui.detail.DetailActivity;
import com.ekr.smartlaundry.utils.Session;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.ekr.smartlaundry.utils.GlobalPath.DATA;
import static com.ekr.smartlaundry.utils.GlobalPath.DB_LAUNDRY;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private Session session;
    private ArrayList<CuciModel> models;
    private HistoryAdapter hisotryAdapter;
    private Query query;
    private SpinKitView spinKitView;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(requireContext());
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference(DB_LAUNDRY);
        rootRef.keepSynced(true);
        query = rootRef.orderByChild("uuid").equalTo(session.getSPUid());

    }

    @Override
    public void onStart() {
        super.onStart();
        getDataUser();
    }

    private void getDataUser() {
        spinKitView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        if (query != null) {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        models = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            models.add(ds.getValue(CuciModel.class));
                        }
                        hisotryAdapter = new HistoryAdapter(models, requireActivity());
                        recyclerView.setAdapter(hisotryAdapter);
                        hisotryAdapter.notifyDataSetChanged();
                        spinKitView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        hisotryAdapter.setOnItemClickListener(position -> {
                            Intent intent = new Intent(requireContext(), DetailActivity.class);
                            intent.putExtra(DATA, models.get(position));
                            startActivity(intent);
                        });
                    } else {
                        spinKitView.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    spinKitView.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.rcv_history);
        spinKitView = view.findViewById(R.id.spin_kit_history);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setStackFromEnd(true);
        manager.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(manager);
        return view;
    }
}