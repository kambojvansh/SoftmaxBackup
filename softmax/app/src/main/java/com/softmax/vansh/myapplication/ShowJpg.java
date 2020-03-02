package com.softmax.vansh.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class ShowJpg extends AppCompatActivity {
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_jpg);
        final String TempHolder = getIntent().getStringExtra("ListViewClickedValue");
        //String subject = replace(TempHolder);
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        final String value = settings.getString(SCHOOL_SHARED_PREF, "");
        SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        final String clas = settings1.getString(CLASS_SHARED_PREF, "");


        WebView wv = (WebView) findViewById(R.id.webview1);
        progressbar = (ProgressBar) findViewById(R.id.progressbarimage);
        //wv.setWebViewClient(new Callback());
        wv.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);
        String school = replace(TempHolder);
        wv.loadUrl("https://softmax.info/" + school);
        //progressbar.setVisibility(View.GONE);
        wv.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });

//        File file = new File("http://softmax.info/uploadpic/" + school + "/" + clas + "/" + TempHolder);
////        File file = new File("http://softmax.info/" + TempHolder);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
//        String type = mime.getMimeTypeFromExtension(ext);
//        intent.setDataAndType(Uri.fromFile(new File(file.toString())), type);
//        try
//        {
//            startActivity(intent);
//            progressbar.setVisibility(View.GONE);
//        }
//        catch(Exception e){}
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
