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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class DairyPic extends AppCompatActivity {

    String notice;
    String class_work;
    String[] imagepath;
    String[] data;
    String[] subject;
    String[] marks;
    String[] blank;
    TextView notice_text;
    ArrayAdapter<String> arrayAdapter;
    BufferedInputStream is;
    String line = null;
    String result = null;
    String selected_date;
    private Button show;
    private Button time_table;
    private Button sullabus;
    ListView listView;
    ListView listView2;

    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> url = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy_pic);
        listView2 = findViewById(R.id.marks_image1);

        new Marks_pic().execute();
    }

    private class Marks_pic extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(DairyPic.this);
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
            CustomListView2 customListView3=new CustomListView2(DairyPic.this,subject,marks);
            listView2.setAdapter(customListView3);



            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    // Getting listview click value into String variable.
                    String TempListViewClickedValue = imagepath[position].toString();
                    if (TempListViewClickedValue.toString().contains(".doc") || TempListViewClickedValue.toString().contains(".docx")) {
                        // Word document
                        Intent intent = new Intent(DairyPic.this, SyllabusTimetable.class);
                        intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
                        startActivity(intent);

                    } else if (TempListViewClickedValue.toString().contains(".pdf")) {
                        // PDF file
                        Intent intent = new Intent(DairyPic.this, SyllabusTimetable.class);
                        intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
                        startActivity(intent);

                    }else if (TempListViewClickedValue.toString().contains(".jpg") || TempListViewClickedValue.toString().contains(".jpeg") || TempListViewClickedValue.toString().contains(".png")) {
                        // JPG file
                        Intent intent = new Intent(DairyPic.this, ShowJpg.class);
                        intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
                        startActivity(intent);

                    }

                    //Intent intent = new Intent(DairyPic.this, SyllabusTimetable.class);

                    // Sending value to another activity using intent.




                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {


            SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String value = settings.getString(SCHOOL_SHARED_PREF, "");
            final String clas = settings.getString(CLASS_SHARED_PREF, "");

            String school=replace(value);


            //String urladdress = "http://softmax.info/gethomework.php?class="+clas+"&date="+date+"&school="+school;
            String urladdress = "https://softmax.info/get_markspic.php?school="+school+"&class="+clas;
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
                imagepath=new String[ja.length()];
                marks=new String[ja.length()];

                //phone=new String[ja.length()];

                for(int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    // name[i]=jo.getString("name");
                    subject[i]=jo.getString("name");
                    imagepath[i]=jo.getString("image");
                    marks[i]=jo.getString("date");
                    //phone[i]=jo.getString("mobile");
                    //mobile=jo.getString("mobile");
                    if(marks[0].equals("failure")){
                        //dispalyHomeWork.setText("Home work not Given on thid Date");
                        //return null;

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
