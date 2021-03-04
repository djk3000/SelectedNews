package com.example.selectednews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class NewsDetialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detial);
        WebView webView = (WebView) findViewById(R.id.webView);

        String url = getIntent().getExtras().getString("url");
        webView.loadUrl(url);
    }
}