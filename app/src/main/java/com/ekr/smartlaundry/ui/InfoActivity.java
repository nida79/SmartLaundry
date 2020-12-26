package com.ekr.smartlaundry.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.ekr.smartlaundry.R;

public class InfoActivity extends AppCompatActivity {
    private WebView webView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        webView = findViewById(R.id.webView_Info);
        imageView = findViewById(R.id.ic_back_info);
        imageView.setOnClickListener(view -> finish());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/help.html");

    }
}