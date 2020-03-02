package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.softmax.vansh.myapplication.Login_page.EMAIL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Attendance extends AppCompatActivity {
    String[] name;
    String[] email;
    int count=0;
    String[] imagepath;
    String[] data;
    ListView listView;
    TextView attendence_count;
    ArrayAdapter<String> arrayAdapter;
    BufferedInputStream is;
    String line = null;
    String result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        listView = (ListView) findViewById(R.id.listview2);
        attendence_count=findViewById(R.id.attendance_counter);

       /* StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        collectData();*/
        new Load().execute();



    }

    //collect data from server

    private class Load extends AsyncTask<Void,Void,Void> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(Attendance.this);
            dg.setMessage("Loading...");
            dg.setCancelable(false);
            dg.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //Toast.makeText(Attendance.this,"No Internet Connection",Toast.LENGTH_LONG).show();
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
            attendence_count.setText("Total Absent="+count);
            CustomListView customListView=new CustomListView(Attendance.this,name,email);
            listView.setAdapter(customListView);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            final String value = settings.getString(KEY_PASSWORD, "");
            SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String value1 = settings1.getString(SCHOOL_SHARED_PREF, "");

            String school=replace(value1);


            String urladdress = "https://softmax.info/getattendence.php?rollnum="+value+"&school="+school;
//Connection
            try{

                URL url=new URL(urladdress);
                HttpURLConnection con=(HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                is=new BufferedInputStream(con.getInputStream());
            /*if(new InputStreamReader(is)==null){
                attendence_count.setText("Total Absent=0");

            }*/

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
                name=new String[ja.length()];
                email=new String[ja.length()];
                //imagepath=new String[ja.length()];
                data=new String[ja.length()];

                for(int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    name[i]=jo.getString("date");
                    // email[i]=jo.getString("student_name");
                    //imagepath[i]=jo.getString("photo");
                    data[i]=jo.getString("date");
                    count++;
                    email[i]="ABSENT";
                    if(data[0].equals("failure")){
                        attendence_count.setText("Total Absent=0");
                        //return;

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
