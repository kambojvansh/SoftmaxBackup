package com.softmax.vansh.myapplication;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Video_url extends AppCompatActivity {
    WebView webview;
    //WebView webView;
    ProgressBar progressbar;
    String video_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_url);
        //webView = (WebView)findViewById(R.id.webview2);


        webview = (WebView)findViewById(R.id.webview2);
        webview.onPause();    // This will pause videos and needs to be called for EVERY WebView you create
        webview.pauseTimers();
        progressbar = (ProgressBar) findViewById(R.id.progressbar2);
        video_url = getIntent().getExtras().getString("anime_video") ;
        String name  = getIntent().getExtras().getString("anime_name");
        String description = getIntent().getExtras().getString("anime_description");

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);
        TextView tv_description = findViewById(R.id.aa_description);

        tv_description.setText(description);
        //tv_rating.setText(rating);
        //tv_studio.setText(studio);

        collapsingToolbarLayout.setTitle(name);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        webview.getSettings().setJavaScriptEnabled(true);
        //String filename ="http://softmax.info/mehak/"+value+".pdf";
        webview.loadUrl(video_url);

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
