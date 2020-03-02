package com.softmax.vansh.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.File;

public class ReportCardImage extends AppCompatActivity {
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_card_image);
        final String TempHolder = getIntent().getStringExtra("ListViewClickedValue");
        //String subject = replace(TempHolder);


        WebView wv = (WebView) findViewById(R.id.webview1);
        progressbar = (ProgressBar) findViewById(R.id.progressbarimage);
        //wv.setWebViewClient(new Callback());
        wv.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);
        //wv.loadUrl("http://softmax.info/"+TempHolder);
        //progressbar.setVisibility(View.GONE);
        /*wv.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });*/
        File file = new File("https://softmax.info/"+TempHolder);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        String type = mime.getMimeTypeFromExtension(ext);
        intent.setDataAndType(Uri.fromFile(new File(file.toString())), type);
        try
        {
            startActivity(intent);
            progressbar.setVisibility(View.GONE);
        }
        catch(Exception e){}
    }




}
