package com.softmax.vansh.myapplication;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class PlayVideo extends AppCompatActivity {
    WebView webview;
    //WebView webView;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        //webView = (WebView)findViewById(R.id.webview2);


        webview = (WebView)findViewById(R.id.webview2);
        webview.onPause();    // This will pause videos and needs to be called for EVERY WebView you create
        webview.pauseTimers();
        progressbar = (ProgressBar) findViewById(R.id.progressbar2);
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        final String value = settings.getString(KEY_PASSWORD, "");
        SharedPreferences setting = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        final String school = setting.getString(SCHOOL_SHARED_PREF, "");
        final String clas = setting.getString(CLASS_SHARED_PREF, "");
        webview.getSettings().setJavaScriptEnabled(true);
        //String filename ="http://softmax.info/mehak/"+value+".pdf";
        webview.loadUrl("https://softmax.info/Schools/"+school+"/"+clas+".php");

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });


    }
    @Override
    protected void onPause(){
        super.onPause();
        if(webview != null){
            webview.onPause();
            webview.pauseTimers();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(webview != null){
            webview.onResume();
            webview.resumeTimers();
        }
    }
}