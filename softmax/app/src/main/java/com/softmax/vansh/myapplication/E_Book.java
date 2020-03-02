package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.MEDIUM_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class E_Book extends AppCompatActivity {

    String[] name;
    String[] data;
    String[] email;
    String[] url1;
    int count=0;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    BufferedInputStream is;
    String line = null;
    String result = null;
    private ArrayAdapter mAdapter;
    private Typeface mTypeface;
    TextView massege;


    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e__book);
        massege = findViewById(R.id.massege);

        listView = (ListView) findViewById(R.id.ebook);


        new Load().execute();

    }

    private class Load extends AsyncTask<Void, Void, Void> {
        ProgressDialog dg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg = new ProgressDialog(E_Book.this);
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
            TextView massege = findViewById(R.id.massege);
            massege.setText("Total "+count+" Chapters Found");
         /* Typeface fontHindi = Typeface.createFromAsset(getAssets(),
                  "fonts/arial.ttf");
           massege.setTypeface(fontHindi);*/



            /*CustomEbook customEbook=new CustomEbook(E_Book.this,name,email);
            listView.setAdapter(customEbook);*/

            CustomListView customListView=new CustomListView(E_Book.this,name,data);
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
                    String TempListViewClickedValue = url1[position].toString();

                    Intent intent = new Intent(E_Book.this, Ebook_Pdf.class);

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
                final String medium = settings.getString(MEDIUM_SHARED_PREF, "");

                final String TempHolder = getIntent().getStringExtra("ListViewClickedValue");
                String subject = replace(TempHolder);


                String urladdress = "https://softmax.info/get_ebook.php?class_name="+value+"&medium="+medium+"&subject="+subject;
//Connection

                //String urladdress = "http://softmax.info/get_ebook.php?class_name=5&medium=English&subject=EVS";

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
                        data[i] = jo.getString("medium");
                        name[i] = jo.getString("chapter");
                        //email[i] = jo.getString("chapter");

                        //notice=jo.getString("notice");
                        //title.add(jo.getString("name"));
                        //url.add(jo.getString("url"));
                        url1[i] = jo.getString("url");
                        count++;
                    /*if(data[0].equals("failure")){
                        notice_text.setText("No Notice Fond");
                        //return;

                    }*/
                        if(name[0].equals("failure")){

                            massege.setText("No Book Found");
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
