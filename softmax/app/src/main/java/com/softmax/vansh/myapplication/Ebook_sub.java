package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.MEDIUM_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Ebook_sub extends AppCompatActivity {
    String[] subject;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    BufferedInputStream is;
    String line = null;
    String result = null;
    String[] data;
    int count=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_sub);


        listView = (ListView) findViewById(R.id.ebook_sub);



        new Load().execute();
    }
    private class Load extends AsyncTask<Void, Void, Void> {
        ProgressDialog dg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg = new ProgressDialog(Ebook_sub.this);
            dg.setMessage("Loading...");
            dg.setCancelable(false);
            dg.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    dg.dismiss();
                }
            }, 9000);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dg.dismiss();
            TextView subjects = findViewById(R.id.subjects);
             subjects.setText(count+" Books Found");
         /* Typeface fontHindi = Typeface.createFromAsset(getAssets(),
                  "fonts/arial.ttf");
           massege.setTypeface(fontHindi);*/



            /*CustomEbook customEbook=new CustomEbook(E_Book.this,name,email);
            listView.setAdapter(customEbook);*/

            CustomListView customListView=new CustomListView(Ebook_sub.this,subject,data);
            listView.setAdapter(customListView);

            //mTypeface = Typeface.createFromAsset(getAssets(),"fonts/arial.ttf");
            //Hindi_text title = new Hindi_text(getActivity());
            //title.setText(issue.getName());
            //massege.setText("अध्याय 1: मछली उछली");









            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    // Getting listview click value into String variable.
                    String TempListViewClickedValue = subject[position].toString();

                    Intent intent = new Intent(Ebook_sub.this, E_Book.class);

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
                final String medium = settings.getString(MEDIUM_SHARED_PREF, "");
                final String clas = settings.getString(CLASS_SHARED_PREF, "");



                String urladdress = "https://softmax.info/get_ebook_sub.php?medium="+medium+"&class="+clas;
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
                    SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                    final String medium1 = settings1.getString(MEDIUM_SHARED_PREF, "");
                    JSONArray ja = new JSONArray(result);
                    JSONObject jo = null;
                    subject=new String[ja.length()];
                    data=new String[ja.length()];

                    for (int i = 0; i <= ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        //data[i] = jo.getString("medium");
                        subject[i] = jo.getString("subject");
                        data[i]=medium1.trim();
                        count++;


                        if(subject[0].equals("failure")){

                           // massege.setText("No Book Found");
                            //return;

                        }
                        /*else {
                            massege.setText("Some Books Found");

                        }*/
                    }
                } catch (Exception ex) {

                    ex.printStackTrace();
                }
                return null;


            }

        }
    }

}
