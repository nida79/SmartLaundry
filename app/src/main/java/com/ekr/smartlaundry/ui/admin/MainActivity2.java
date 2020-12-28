package com.ekr.smartlaundry.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.ui.main.HistoryFragment;
import com.ekr.smartlaundry.ui.main.HomeFragment;
import com.ekr.smartlaundry.ui.main.MainActivity;
import com.ekr.smartlaundry.ui.main.ProfileFragment;
import com.ekr.smartlaundry.utils.Session;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity2 extends AppCompatActivity {
    private ChipNavigationBar bottomBar;
    private FragmentManager fragmentManager;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bottomBar = findViewById(R.id.main_bottom_bar2);
        if (savedInstanceState == null) {
            bottomBar.setItemSelected(R.id.histori2, true);
            fragmentManager = getSupportFragmentManager();
            HistoryFragment history = new HistoryFragment();
            fragmentManager.beginTransaction().replace(R.id.main_container2, history).commit();
        }
        bottomBar.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i) {
                case R.id.histori2:
                    fragment = new HistoryFragment();
                    break;
                case R.id.dashboard2:
                    fragment = new DashboardFragment();
                    break;
                case R.id.profile2:
                    fragment = new ProfileFragment();
                    break;
            }
            if (fragment != null) {
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container2, fragment).commit();
            }else {
                Log.e("Cek Fragment", "onCreate: " );
            }
        });
    }
}