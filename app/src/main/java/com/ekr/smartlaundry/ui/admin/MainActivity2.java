package com.ekr.smartlaundry.ui.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ekr.smartlaundry.R;
import com.ekr.smartlaundry.ui.main.MainActivity;
import com.ekr.smartlaundry.utils.Session;

public class MainActivity2 extends AppCompatActivity {
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        session = new Session(this);
        if (session.getSpRule().equals("user")){
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
            finish();
        }
    }
}