package com.softmax.vansh.myapplication;

import android.annotation.SuppressLint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebResourceRequest;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;




public class Ebook_Pdf extends AppCompatActivity {
    WebView webview;
    ProgressBar progressbar;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook__pdf);
        webview = (WebView)findViewById(R.id.webview_ebook);
        progressbar = (ProgressBar) findViewById(R.id.progressbar2);

        displayWebView();

    }
    private void displayWebView() {
        final String TempHolder = getIntent().getStringExtra("ListViewClickedValue");

        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //view.loadUrl(myPdfUrl);
                //view.loadUrl("https://drive.google.com/file/d/"+TempHolder);
                progressbar.setVisibility(View.GONE);
                return true;
            }
        });
       // webview.loadUrl("https://drive.google.com/file/d/"+TempHolder);
        webview.loadUrl(TempHolder);

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    }
