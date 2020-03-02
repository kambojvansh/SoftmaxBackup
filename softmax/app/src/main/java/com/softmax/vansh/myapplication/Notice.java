package com.softmax.vansh.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.softmax.vansh.myapplication.Login_page.EMAIL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Notice extends AppCompatActivity {

    String notice;
    String[] email;
    String[] imagepath;
    String[] data;
    String noticebord;
    TextView notice_text;
    ArrayAdapter<String> arrayAdapter;
    BufferedInputStream is;
    String line = null;
    String result = null;
    TextView Student_notice;
    String[] notice_student;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);




        /*StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();*/

        //spinner.setVisibility(View.GONE);
        notice_text=findViewById(R.id.text_notice);
        Student_notice=findViewById(R.id.notice_parants);
        //noticebord="Notice not found";
        //notice_text.setText(noticebord);
        isNetworkConnectionAvailable();



    }

    private class Load extends AsyncTask<Void,Void,Void> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(Notice.this);
            dg.setMessage("Loading...");
            dg.setCancelable(false);
            dg.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                   // Toast.makeText(Notice.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    dg.dismiss();
                }
            }, 9000);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dg.dismiss();
            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(Video.this,android.R.layout.simple_list_item_1,title);
            listView.setAdapter(adapter);*/
            SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String value = settings.getString(SCHOOL_SHARED_PREF, "");
            notice_text.setText(data[0]);
            //notice_text.setText(noticebord);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            {



                SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings.getString(SCHOOL_SHARED_PREF, "");

                String school=replace(value);
                String urladdress = "https://softmax.info/getnotice.php?school="+school;
//Connection
                try{

                    URL url=new URL(urladdress);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    con.setRequestMethod("GET");
                    is=new BufferedInputStream(con.getInputStream());

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                //content
                try{
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb=new StringBuilder();
                    while ((line=br.readLine())!=null){
                        sb.append(line+"\n");
                    }
                    is.close();
                    result=sb.toString();

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();

                }

//JSON
                try{
                    JSONArray ja=new JSONArray(result);
                    JSONObject jo=null;
                    //name=new String[ja.length()];
                    email=new String[ja.length()];
                    //imagepath=new String[ja.length()];
                    data=new String[ja.length()];

                    for(int i=0;i<=ja.length();i++){
                        jo=ja.getJSONObject(i);
                        data[i]=jo.getString("notice");
                        //email[i]=jo.getString("school_name");
                        //notice=jo.getString("notice");

                        if(data[0].equals("failure")){
                           notice_text.setText("Notice not Found");
                            //return;

                        }
                    }
                }
                catch (Exception ex)
                {
                    //notice_text.setText("No Notice Fond");
                    ex.printStackTrace();
                    //notice_text.setText("Notice not Found");
                }
                return null;



            }



        }
    }

    //for Student lavel notice
    private class Notice_student extends AsyncTask<Void,Void,Void> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(Notice.this);
            dg.setMessage("Loading...");
            dg.setCancelable(false);
            dg.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // Toast.makeText(Notice.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    dg.dismiss();
                }
            }, 9000);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dg.dismiss();
            Student_notice.setText(notice_student[0]);
            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(Video.this,android.R.layout.simple_list_item_1,title);
            listView.setAdapter(adapter);*/
            /*if(notice_student[0].equals("failure"))
            {
                Student_notice.setText("");

            }
            else{
                Student_notice.setText(notice_student[0]);

            }*/
            //notice_text.setText(data[0]);
            //notice_text.setText(noticebord);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            {



                SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings.getString(SCHOOL_SHARED_PREF, "");

                String school=replace(value);



                SharedPreferences settings2 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String rollnum = settings2.getString(KEY_PASSWORD, "");
                SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String mobile = settings1.getString(EMAIL_SHARED_PREF, "");
                String urladdress = "https://softmax.info/get_student_notice.php?school="+school+"&rollnum="+rollnum+"&mobile="+mobile;
//Connection
                try{

                    URL url=new URL(urladdress);
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    con.setRequestMethod("GET");
                    is=new BufferedInputStream(con.getInputStream());

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
                //content
                try{
                    BufferedReader br=new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb=new StringBuilder();
                    while ((line=br.readLine())!=null){
                        sb.append(line+"\n");
                    }
                    is.close();
                    result=sb.toString();

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();

                }

//JSON
                try{
                    JSONArray ja=new JSONArray(result);
                    JSONObject jo=null;
                    //name=new String[ja.length()];
                    //email=new String[ja.length()];
                    //imagepath=new String[ja.length()];
                    //data=new String[ja.length()];
                    notice_student=new String[ja.length()];

                    for(int i=0;i<=ja.length();i++){
                        jo=ja.getJSONObject(i);
                        notice_student[i]=jo.getString("my_text");
                        //email[i]=jo.getString("school_name");
                        //notice=jo.getString("notice");

                        if(data[0].equals("failure")){
                            //notice_text.setText("Notice not Found");
                            //return;

                        }
                    }
                }
                catch (Exception ex)
                {
                    //notice_text.setText("No Notice Fond");
                    ex.printStackTrace();
                    //notice_text.setText("Notice not Found");
                }
                return null;



            }



        }
    }
    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void isNetworkConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null) && (wifi.isConnected() | datac.isConnected())) {
            new Load().execute();
            new Notice_student().execute();
            //connection is avlilable
            //Toast.makeText(Selection_page.this,"active...",Toast.LENGTH_SHORT);
        } else {
            //no connection
            checkNetworkConnection();

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


}
