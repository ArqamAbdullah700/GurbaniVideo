package com.gurbanivideo;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class PrivacyPolicy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        WebView webView = findViewById(R.id.webView);

        // Enable JavaScript (if your HTML requires it)
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load HTML content
        String url = "https://www.gurbanistatus.in/GurbaniAppApi/Gurbani_Video_Privacy.html"; // Replace with your URL
        webView.loadUrl(url);
    }
}