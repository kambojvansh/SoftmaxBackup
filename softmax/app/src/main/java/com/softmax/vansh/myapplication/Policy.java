package com.softmax.vansh.myapplication;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


public class Policy extends AppCompatActivity {
    WebView webview;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        webview = (WebView)findViewById(R.id.webview2);
        progressbar = (ProgressBar) findViewById(R.id.progressbar2);
        webview.getSettings().setJavaScriptEnabled(true);
        //String filename ="http://softmax.info/privacy_policy.html";
       // webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + filename);
        webview.loadUrl("https://softmax.info/privacy_policy.html");

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });
    }
}
