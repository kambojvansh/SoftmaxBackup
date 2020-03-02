package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.softmax.vansh.myapplication.Login_page.EMAIL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Videos extends AppCompatActivity {
    String[] name;
    String[] data;
    String[] email;
    String[] url1;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    BufferedInputStream is;
    String line = null;
    String result = null;
    TextView massage;

    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);


        listView = (ListView) findViewById(R.id.listview);
        massage = findViewById(R.id.video_massage);

        new Load().execute();



    }

    private class Load extends AsyncTask<Void, Void, Void> {
        ProgressDialog dg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg = new ProgressDialog(Videos.this);
            dg.setMessage("Loading...");
            dg.setCancelable(false);
            dg.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    //Toast.makeText(Videos.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    dg.dismiss();
                }
            }, 9000);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dg.dismiss();

            CustomListView customListView=new CustomListView(Videos.this,name,email);
            listView.setAdapter(customListView);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    // Getting listview click value into String variable.
                    String TempListViewClickedValue = url1[position].toString();

                    Intent intent = new Intent(Videos.this, PlayVideo.class);

                    // Sending value to another activity using intent.
                    intent.putExtra("ListViewClickedValue", TempListViewClickedValue);

                    startActivity(intent);

                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {

            {


                SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings.getString(CLASS_SHARED_PREF, "");


                String urladdress = "https://softmax.info/getvideo.php?class_name="+value;
//Connection
                try {

                    URL url = new URL(urladdress);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    is = new BufferedInputStream(con.getInputStream());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //content
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();

                } catch (Exception ex) {
                    ex.printStackTrace();

                }

//JSON
                try {
                    JSONArray ja = new JSONArray(result);
                    JSONObject jo = null;
                    name=new String[ja.length()];
                    email=new String[ja.length()];
                    //imagepath=new String[ja.length()];
                    data = new String[ja.length()];
                    url1 = new String[ja.length()];

                    for (int i = 0; i <= ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        //data[i] = jo.getString("url");
                        name[i] = jo.getString("title");
                        email[i] = jo.getString("language");

                        //notice=jo.getString("notice");
                        //title.add(jo.getString("name"));
                        //url.add(jo.getString("url"));
                        url1[i] = jo.getString("url");
                    if(data[0].equals("failure")){
                        massage.setText("No Video Fond");
                        //return;

                    }
                    }
                } catch (Exception ex) {

                    ex.printStackTrace();
                }
                return null;


            }

        }
    }



}
