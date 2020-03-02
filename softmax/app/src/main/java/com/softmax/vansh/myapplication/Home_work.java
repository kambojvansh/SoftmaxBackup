package com.softmax.vansh.myapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Home_work extends AppCompatActivity {

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



    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private TextView dispalyHomeWork;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        //datepicker

        mDisplayDate = (TextView) findViewById(R.id.tvDate);
        dispalyHomeWork=findViewById(R.id.work);
        show=findViewById(R.id.show_work);
        time_table=findViewById(R.id.time_table);
        sullabus=findViewById(R.id.syllabs);
        listView = findViewById(R.id.marks);
        listView2 = findViewById(R.id.marks_image1);



        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Home_work.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
               // Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "-" + day + "-" + year);
                Log.d(TAG, "onDateSet: yyy/mm/dd: " + year + "-" + month + "-" + day);

                String date = year + "-" + month + "-" + day;
                selected_date=date;
                mDisplayDate.setText(date);
            }
        };
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    //onLoginFailed();
                    return;
                }
                new Load().execute();


            }
        });
        time_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //new Load().execute();
                //Toast.makeText(Home_work.this, "Time Table", Toast.LENGTH_SHORT).show();
                String TempListViewClickedValue = "Time_table.jpg";

                Intent intent = new Intent(Home_work.this, TimeTableView.class);

                // Sending value to another activity using intent.
                intent.putExtra("ListViewClickedValue", TempListViewClickedValue);

                startActivity(intent);


            }
        });
        sullabus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Home_work.this, "Syllabus", Toast.LENGTH_SHORT).show();
                String TempListViewClickedValue = "syllabus.jpg";

                Intent intent = new Intent(Home_work.this, TimeTableView.class);
                //Intent intent = new Intent(Home_work.this, PlayVideo.class);

                // Sending value to another activity using intent.
                intent.putExtra("ListViewClickedValue", TempListViewClickedValue);

                startActivity(intent);

                //new Load().execute();


            }
        });
        isNetworkConnectionAvailable();


    }

    //collect data from tables

    private class Load extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
        super.onPreExecute();
        dg=new ProgressDialog(Home_work.this);
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
            dispalyHomeWork.setText(data[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String clas = settings1.getString(CLASS_SHARED_PREF, "");
            final String date=selected_date;
            SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String value = settings.getString(SCHOOL_SHARED_PREF, "");

            String school=replace(value);


            //String urladdress = "http://softmax.info/gethomework.php?class="+clas+"&date="+date+"&school="+school;
            String urladdress = "https://softmax.info/gethomework.php?date="+date+"&clg="+school+"&class="+clas;
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
                // email=new String[ja.length()];
                //imagepath=new String[ja.length()];
                data=new String[ja.length()];

                //phone=new String[ja.length()];

                for(int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    // name[i]=jo.getString("name");
                    // email[i]=jo.getString("email");
                    //imagepath[i]=jo.getString("photo");
                    data[i]=jo.getString("homework");
                    //phone[i]=jo.getString("mobile");
                    //mobile=jo.getString("mobile");
                    if(data[0].equals("failure")){
                        dispalyHomeWork.setText("Home work not Given on thid Date");
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
    public boolean validate() {
        boolean valid = true;
        mDisplayDate = (TextView) findViewById(R.id.tvDate);


        String date = mDisplayDate.getText().toString();


        if (date.isEmpty() /*|| email.length()!=10*/) {
            mDisplayDate.setError("Select Homework Date");
            valid = false;
        } else {
            mDisplayDate.setError(null);
        }

        /*if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editTextPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }*/

        return valid;
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

    private class Load2 extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(Home_work.this);
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
            ListView listView2;
            //blank[0]="Last Home Work";
            //listView2= findViewById(R.id.work);
            //CustomListView customListView2=new CustomListView(Home_work.this,data,blank);
            //listView2.setAdapter(customListView2);
            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(Videos.this,android.R.layout.simple_list_item_1,title);
            listView.setAdapter(adapter);*/
            dispalyHomeWork.setText("Last Home work :- "+data[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String clas = settings1.getString(CLASS_SHARED_PREF, "");
            final String date=selected_date;
            SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String value = settings.getString(SCHOOL_SHARED_PREF, "");

            String school=replace(value);


            //String urladdress = "http://softmax.info/gethomework.php?class="+clas+"&date="+date+"&school="+school;
            String urladdress = "https://softmax.info/get_homework.php?school="+school+"&class="+clas;;
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
                // email=new String[ja.length()];
                //imagepath=new String[ja.length()];
                data=new String[ja.length()];

                //phone=new String[ja.length()];

                for(int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    // name[i]=jo.getString("name");
                    // email[i]=jo.getString("email");
                    //imagepath[i]=jo.getString("photo");
                    data[i]=jo.getString("homework");
                    //phone[i]=jo.getString("mobile");
                    //mobile=jo.getString("mobile");
                    if(data[0].equals("failure")){
                        dispalyHomeWork.setText("Home work not Given on thid Date");
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
            new Load2().execute();
            new Marks().execute();
            new Marks_pic().execute();
        } else {
            //no connection
            checkNetworkConnection();

        }
    }

    private class Marks extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(Home_work.this);
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
            CustomListView2 customListView2=new CustomListView2(Home_work.this,subject,marks);
            listView.setAdapter(customListView2);

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
    private class Marks_pic extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(Home_work.this);
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
            CustomListView2 customListView3=new CustomListView2(Home_work.this,subject,marks);
            listView2.setAdapter(customListView3);



            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub
                    String TempListViewClickedValue = imagepath[position].toString();
                    if (TempListViewClickedValue.toString().contains(".doc") || TempListViewClickedValue.toString().contains(".docx")) {
                        // Word document
                        Intent intent = new Intent(Home_work.this, SyllabusTimetable.class);
                        intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
                        startActivity(intent);

                    } else if (TempListViewClickedValue.toString().contains(".pdf")) {
                        // PDF file
                        Intent intent = new Intent(Home_work.this, SyllabusTimetable.class);
                        intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
                        startActivity(intent);

                    }else if (TempListViewClickedValue.toString().contains(".jpg") || TempListViewClickedValue.toString().contains(".jpeg") || TempListViewClickedValue.toString().contains(".png")) {
                        // JPG file
                        Intent intent = new Intent(Home_work.this, ShowJpg.class);
                        intent.putExtra("ListViewClickedValue", TempListViewClickedValue);
                        startActivity(intent);

                    }

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
}
