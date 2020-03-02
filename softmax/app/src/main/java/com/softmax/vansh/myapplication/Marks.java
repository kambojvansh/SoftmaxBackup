package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Marks extends AppCompatActivity {
    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    String[] subject;
    String[] marks;
    BufferedInputStream is;
    String line = null;
    String result = null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);
        listView= findViewById(R.id.student_marks);
        isNetworkConnectionAvailable();
    }

    private class Marks1 extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(Marks.this);
            dg.setMessage("Loading...");
            dg.setCancelable(false);
            dg.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //Toast.makeText(Home_work.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    dg.dismiss();
                }
            }, 9000);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dg.dismiss();
            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(Videos.this,android.R.layout.simple_list_item_1,title);
            listView.setAdapter(adapter);*/
            //dispalyHomeWork.setText(data[0]);
            if(subject.length<1)
            {
                subject[0]=" ";
                marks[0]=" ";
            }
            CustomListView2 customListView=new CustomListView2(Marks.this,subject,marks);
            listView.setAdapter(customListView);

        }

        @Override
        protected Void doInBackground(Void... voids) {


            SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String value = settings.getString(SCHOOL_SHARED_PREF, "");
            SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            final String rollnum = settings1.getString(KEY_PASSWORD, "");

            String school=replace(value);


            //String urladdress = "http://softmax.info/gethomework.php?class="+clas+"&date="+date+"&school="+school;
            String urladdress = "https://softmax.info/get_marks.php?school="+school+"&rollnum="+rollnum;
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
                subject=new String[ja.length()];
                //imagepath=new String[ja.length()];
                marks=new String[ja.length()];

                //phone=new String[ja.length()];

                for(int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    // name[i]=jo.getString("name");
                    subject[i]=jo.getString("exam_name");
                    //imagepath[i]=jo.getString("photo");
                    marks[i]=jo.getString("marks");
                    //phone[i]=jo.getString("mobile");
                    //mobile=jo.getString("mobile");
                    if(marks[0].equals("failure")){
                        //dispalyHomeWork.setText("Home work not Given on thid Date");
                        //return null;
                        marks[0]=" ";
                        subject[0]=" ";

                    }
                }
            }
            catch (Exception ex)
            {

                ex.printStackTrace();
            }


            return null;
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
            //connection is avlilable
            //Toast.makeText(Selection_page.this,"active...",Toast.LENGTH_SHORT);
            new Marks1().execute();
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
