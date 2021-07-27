package com.balsa.teletraderentryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GitHubActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_git_hub);

        webView = findViewById(R.id.webView);
        webView.loadUrl("https://github.com/Balsa028");
        webView.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }

    }
}