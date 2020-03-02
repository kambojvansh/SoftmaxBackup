package com.softmax.vansh.myapplication;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class SyllabusTimetable extends AppCompatActivity {

    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_card_image);
        final String TempHolder = getIntent().getStringExtra("ListViewClickedValue");
        //String subject = replace(TempHolder);


        //WebView wv = (WebView) findViewById(R.id.webview1);
        WebView urlWebView = (WebView) findViewById(R.id.webview1);
        progressbar = (ProgressBar) findViewById(R.id.progressbarimage);
        //wv.setWebViewClient(new Callback());
       /* wv.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);*/
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        final String value = settings.getString(SCHOOL_SHARED_PREF, "");
        SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        final String clas = settings1.getString(CLASS_SHARED_PREF, "");

        String school = replace(TempHolder);

       // WebView urlWebView = (WebView)findViewById(R.id.containWebView);
        Toast.makeText(SyllabusTimetable.this, TempHolder, Toast.LENGTH_SHORT).show();
        urlWebView.setWebViewClient(new AppWebViewClients());
        urlWebView.getSettings().setJavaScriptEnabled(true);
        urlWebView.getSettings().setUseWideViewPort(true);
        WebSettings webSettings = urlWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        urlWebView.loadUrl("https://docs.google.com/gview?embedded=true&url=http://softmax.info/"
                + TempHolder);
//        progressbar.setVisibility(View.GONE);




       // File myFile = new File("http://softmax.info/uploadpic/" + school + "/" + clas + "/" + TempHolder);
        //open(SyllabusTimetable.this, myFile);
       //wv.loadUrl(TempHolder);
       // wv.loadUrl("http://softmax.info/uploadpic/"+school+"/"+clas+"/"+TempHolder);
       //progressbar.setVisibility(View.GONE);
       /* wv.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressbar.setVisibility(View.GONE);
            }
        });*/
        /*String file = new String(TempHolder);
        String doc="<iframe src='http://docs.google.com/viewer?    url="+file+"'"+
                " width='100%' height='100%' style='border: none;'></iframe>";

        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setAllowFileAccess(true);
        //wv.loadUrl(doc);
        wv.loadData( doc , "text/html",  "UTF-8");*/


        //openDocument(file);


        //File myFile = new File("http://softmax.info/uploadpic/" + school + "/" + clas + "/" + TempHolder);
       /* try {
            Callback.FileOpen.openFile(SyllabusTimetable.this, myFile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //open(SyllabusTimetable.this, myFile);

        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        if (ext.toString().contains(".doc") || ext.toString().contains(".docx")) {
            // Word document
            intent.setDataAndType(Uri.fromFile(new File(file.toString())), "application/msword");
        } else if (ext.toString().contains(".pdf")) {
            // PDF file
            intent.setDataAndType(Uri.fromFile(new File(file.toString())), "application/pdf");
        String type = mime.getMimeTypeFromExtension(ext);
        //intent.setDataAndType(Uri.fromFile(new File(file.toString())), type);
        try
        {
            startActivity(intent);
            progressbar.setVisibility(View.GONE);
        }
        catch(Exception e){}
    }*/

   /* private  static  class Callback extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return (false);

        }


        public static class FileOpen {

            public static void openFile(Context context, File url) throws IOException {
                // Create URI
                File file = url;
                Uri uri = Uri.fromFile(file);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                // Check what kind of file you are trying to open, by comparing the url with extensions.
                // When the if condition is matched, plugin sets the correct intent (mime) type,
                // so Android knew what application to use to open the file
                if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                    // Word document
                    intent.setDataAndType(uri, "application/msword");
                } else if (url.toString().contains(".pdf")) {
                    // PDF file
                    intent.setDataAndType(uri, "application/pdf");
                } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                    // Powerpoint file
                    intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                    // Excel file
                    intent.setDataAndType(uri, "application/vnd.ms-excel");
                } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
                    // WAV audio file
                    intent.setDataAndType(uri, "application/x-wav");
                } else if (url.toString().contains(".rtf")) {
                    // RTF file
                    intent.setDataAndType(uri, "application/rtf");
                } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                    // WAV audio file
                    intent.setDataAndType(uri, "audio/x-wav");
                } else if (url.toString().contains(".gif")) {
                    // GIF file
                    intent.setDataAndType(uri, "image/gif");
                } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                    // JPG file
                    intent.setDataAndType(uri, "image/jpeg");
                } else if (url.toString().contains(".txt")) {
                    // Text file
                    intent.setDataAndType(uri, "text/plain");
                } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                    // Video files
                    intent.setDataAndType(uri, "video/*");
                } else {
                    //if you want you can also define the intent type for any other file

                    //additionally use else clause below, to manage other unknown extensions
                    //in this case, Android will show all applications installed on the device
                    //so you can choose which application to use
                    intent.setDataAndType(uri, "*//**");
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }*/
    }
    public class AppWebViewClients extends WebViewClient {



        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            //progressbar.setVisibility(View.GONE);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            //progressbar.setVisibility(View.GONE);

        }
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

   public void open(Context context, File url){
       File file = url;
       Uri uri = Uri.fromFile(file);


       Intent intent = new Intent(Intent.ACTION_VIEW);
       // Check what kind of file you are trying to open, by comparing the url with extensions.
       // When the if condition is matched, plugin sets the correct intent (mime) type,
       // so Android knew what application to use to open the file
       if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
           // Word document
           intent.setDataAndType(uri, "application/msword");
       } else if (url.toString().contains(".pdf")) {
           // PDF file
           intent.setDataAndType(uri, "application/pdf");
       } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
           // Powerpoint file
           intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
       } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
           // Excel file
           intent.setDataAndType(uri, "application/vnd.ms-excel");
       } else if (url.toString().contains(".zip") || url.toString().contains(".rar")) {
           // WAV audio file
           intent.setDataAndType(uri, "application/x-wav");
       } else if (url.toString().contains(".rtf")) {
           // RTF file
           intent.setDataAndType(uri, "application/rtf");
       } else if (url.toString().contains(".wav") || url.toString().contains(".mp3")) {
           // WAV audio file
           intent.setDataAndType(uri, "audio/x-wav");
       } else if (url.toString().contains(".gif")) {
           // GIF file
           intent.setDataAndType(uri, "image/gif");
       } else if (url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
           // JPG file
           intent.setDataAndType(uri, "image/jpeg");
       } else if (url.toString().contains(".txt")) {
           // Text file
           intent.setDataAndType(uri, "text/plain");
       } else if (url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
           // Video files
           intent.setDataAndType(uri, "video/*");
       } else {
           //if you want you can also define the intent type for any other file

           //additionally use else clause below, to manage other unknown extensions
           //in this case, Android will show all applications installed on the device
           //so you can choose which application to use
           intent.setDataAndType(uri, "*/*");
       }

       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
       context.startActivity(intent);

   }

    public void openDocument(String name) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
        File file = new File(name);
        String extension = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        String mimetype = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        if (extension.equalsIgnoreCase("") || mimetype == null) {
            // if there is no extension or there is no definite mimetype, still try to open the file
            intent.setDataAndType(Uri.fromFile(file), "text/*");
        } else {
            intent.setDataAndType(Uri.fromFile(file), mimetype);
        }
        // custom message for the intent
        startActivity(Intent.createChooser(intent, "Choose an Application:"));
    }
}
