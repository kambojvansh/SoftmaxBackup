package com.softmax.vansh.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Etest extends AppCompatActivity {
    WebView webview;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etest);

        webview = (WebView)findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        final String value = settings.getString(KEY_PASSWORD, "");
        SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        final String school = settings1.getString(SCHOOL_SHARED_PREF, "");
        String school_name=replace(school);
        SharedPreferences settings5 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        final String clas = settings5.getString(CLASS_SHARED_PREF, "");
        webview.getSettings().setJavaScriptEnabled(true);
        String filename ="https://softmax.info/questionBank/studentPanel/test_info.php?username="+school_name+"&class="+clas+"&rollnum="+value;
        webview.loadUrl(filename);

        webview.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    public String replace(String str) {
        String[] words = str.split(" ");
        StringBuilder sentence = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; ++i) {
            sentence.append("%20");
            sentence.append(words[i]);
        }

        return sentence.toString();
    }
}
