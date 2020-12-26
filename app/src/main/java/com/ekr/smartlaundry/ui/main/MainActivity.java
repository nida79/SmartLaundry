package com.ekr.smartlaundry.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.ui.admin.MainActivity2;
import com.ekr.smartlaundry.utils.Session;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar bottomBar;
    private FragmentManager fragmentManager;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(this);
//        if (session.getSpRule().equals("admin")){
//            startActivity(new Intent(this, MainActivity2.class));
//            finishAffinity();
//            finish();
//        }
        bottomBar = findViewById(R.id.main_bottom_bar);
        if (savedInstanceState == null) {
            bottomBar.setItemSelected(R.id.home, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.main_container, homeFragment).commit();
        }
        bottomBar.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i) {
                case R.id.home:
                    fragment = new HomeFragment();
                    break;
                case R.id.histori:
                    fragment = new HistoryFragment();
                    break;
                case R.id.profile:
                    fragment = new ProfileFragment();
                    break;
            }
            if (fragment != null) {
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
            }else {
                Log.e("Cek Fragment", "onCreate: " );
            }
        });
    }
}