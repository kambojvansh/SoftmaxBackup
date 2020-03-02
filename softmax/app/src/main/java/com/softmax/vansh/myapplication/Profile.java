package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import static com.softmax.vansh.myapplication.ChatFragment.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.EMAIL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.LOGGEDIN_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.CLASS_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.MEDIUM_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Profile extends AppCompatActivity {

    String[] name;
    String[] clas;
    String[] school;
    String[] fname;
    String[] rnum;
    String[] names;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    BufferedInputStream is;
    String line = null;
    String result = null;
    TextView Name;
    TextView School;
    TextView Fname;
    TextView Rnum;
    TextView Clas;
    TextView medium;
    Spinner spinner;
    String text ="";
    ImageView imageView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Name= findViewById(R.id.name);
        School= findViewById(R.id.school);
        Fname= findViewById(R.id.fname);
        Clas= findViewById(R.id.clas);
        Rnum= findViewById(R.id.rollnum);
        medium= findViewById(R.id.medium);
        spinner=findViewById(R.id.spinner);
        imageView =findViewById(R.id.student_image);
        progressBar = findViewById(R.id.progressbar_image);
        new Load().execute();
        new  Multi_student().execute();
        new Image().execute();


    }


    private class Load extends AsyncTask<Void, Void, Void> {
        ProgressDialog dg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg = new ProgressDialog(Profile.this);
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
            Name.setText(name[0]);
            School.setText(school[0]);
            Clas.setText(clas[0]);
            Fname.setText(fname[0]);
            Rnum.setText(rnum[0]);
            SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String value = settings.getString(MEDIUM_SHARED_PREF, "");
            medium.setText(value);


        }

        @Override
        protected Void doInBackground(Void... voids) {

            {


                /*SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String value = settings.getString(EMAIL_SHARED_PREF, "");


                String urladdress = "http://softmax.info/getprofile.php?email="+value;*/

                SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String value = settings.getString(KEY_PASSWORD, "");
                SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String school_n = settings1.getString(EMAIL_SHARED_PREF, "");
                //String s_name=replace(school_n);


                String urladdress = "https://softmax.info/getprofile2.php?id="+value+"&mobile="+school_n;
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
                    school=new String[ja.length()];
                    fname=new String[ja.length()];
                    clas = new String[ja.length()];
                    rnum = new String[ja.length()];

                    for (int i = 0; i <= ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        school[i] = jo.getString("school_name");
                        name[i] = jo.getString("student_name");
                        fname[i] = jo.getString("father_name");

                        clas[i]=jo.getString("class");
                        //title.add(jo.getString("name"));
                        //url.add(jo.getString("url"));
                        rnum[i] = jo.getString("student_id");
                    /*if(data[0].equals("failure")){
                        notice_text.setText("No Notice Fond");
                        //return;

                    }*/
                        if(name[0].equals("failure")){
                            TextView massege = findViewById(R.id.massege);
                            //massege.setText("No Book Found");
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


    // for spinner code to fatch multi student data

    private class Multi_student extends AsyncTask<Void, Void, Void>  {
        ProgressDialog dg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg = new ProgressDialog(Profile.this);
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
            int i=1;
            int v=1;
            String[] roll= new String[name.length+1];
            roll[0]="Select";
            for(int a=0;a<name.length;a++){
                roll[i]=name[a];
                i++;

            }
            /*final String[] roll1= new String[name.length+1];
            roll[0]="Select";
            for(int a=0;a<rnum.length;a++){
                roll1[v]=name[a];
                v++;

            }*/


            /*Spinner firstSpinner = (Spinner) findViewById(R.id.spinner);
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Profile.this, android.R.layout.simple_spinner_item,name);
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            // Apply the adapter to the spinner
            firstSpinner.setAdapter(adapter);*/

            // Application of the Array to the Spinner
            //String colors[] = {"Red","Blue","White","Yellow","Black", "Green","Purple","Orange","Grey"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Profile.this,   android.R.layout.simple_spinner_item, roll);

            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            spinner.setSelection(7);
            spinner.setAdapter(spinnerArrayAdapter);


            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                    String roll_num;



                    if (i == 0) {

                        return;
                    }
                    //MyModel selected = items.get(i);
                        switch (i) {
                            case 0:
                                //Toast.makeText(adapterView.getContext(), "Spinner item 1!", Toast.LENGTH_SHORT).show();

                                break;
                            case 1:
                                //Toast.makeText(adapterView.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                                roll_num=rnum[i-1];
                                SharedPreferences sharedPreferences1 = Profile.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor1 = sharedPreferences1.edit();

                                editor1.putBoolean(LOGGEDIN_SHARED_PREF, true);
                                //editor.putString(EMAIL_SHARED_PREF, email);
                                editor1.putString(KEY_PASSWORD,roll_num);

                                editor1.apply();
                                Intent intent1 = new Intent(Profile.this, Selection_page.class);
                                //intent1.putExtra("SpinnerName",rnum[1]);
                                startActivity(intent1);
                                finish();
                                break;
                            case 2:
                                //Toast.makeText(adapterView.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                                roll_num=rnum[i-1];
                                SharedPreferences sharedPreferences2 = Profile.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor2 = sharedPreferences2.edit();

                                editor2.putBoolean(LOGGEDIN_SHARED_PREF, true);
                                //editor.putString(EMAIL_SHARED_PREF, email);
                                editor2.putString(KEY_PASSWORD,roll_num);

                                editor2.apply();
                                Intent intent2 = new Intent(Profile.this, Selection_page.class);
                                //intent1.putExtra("SpinnerName",rnum[1]);
                                startActivity(intent2);
                                finish();
                                break;
                            case 3:
                                //Toast.makeText(adapterView.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                                roll_num=rnum[i-1];
                                SharedPreferences sharedPreferences3 = Profile.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor3 = sharedPreferences3.edit();

                                editor3.putBoolean(LOGGEDIN_SHARED_PREF, true);
                                //editor.putString(EMAIL_SHARED_PREF, email);
                                editor3.putString(KEY_PASSWORD,roll_num);

                                editor3.apply();
                                Intent intent3 = new Intent(Profile.this, Selection_page.class);
                                //intent1.putExtra("SpinnerName",rnum[1]);
                                startActivity(intent3);
                                finish();
                                break;
                            case 4:
                                //Toast.makeText(adapterView.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                                roll_num=rnum[i-1];
                                SharedPreferences sharedPreferences4 = Profile.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor4 = sharedPreferences4.edit();

                                editor4.putBoolean(LOGGEDIN_SHARED_PREF, true);
                                //editor.putString(EMAIL_SHARED_PREF, email);
                                editor4.putString(KEY_PASSWORD,roll_num);

                                editor4.apply();
                                Intent intent4 = new Intent(Profile.this, Selection_page.class);
                                //intent1.putExtra("SpinnerName",rnum[1]);
                                startActivity(intent4);
                                finish();
                                break;
                             default:
                                 String text = adapterView.getItemAtPosition(i).toString();
                                 Toast.makeText(adapterView.getContext(), "You Need to Login with "+text+" Rollnumber", Toast.LENGTH_LONG).show();

                        }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });




        }



        @Override
        protected Void doInBackground(Void... voids) {

            {



                SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String value = settings.getString(KEY_PASSWORD, "");
                SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String school_n = settings1.getString(EMAIL_SHARED_PREF, "");
                //String s_name=replace(school_n);


                String urladdress = "https://softmax.info/getprofile_spinner.php?mobile="+school_n;
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
                    school=new String[ja.length()];
                    fname=new String[ja.length()];
                    clas = new String[ja.length()];
                    rnum = new String[ja.length()];

                    for (int i = 0; i <= ja.length(); i++) {
                        jo = ja.getJSONObject(i);
                        school[i] = jo.getString("school_name");
                        name[i] = jo.getString("student_name");
                        fname[i] = jo.getString("father_name");

                        clas[i]=jo.getString("class");
                        //title.add(jo.getString("name"));
                        //url.add(jo.getString("url"));
                        rnum[i] = jo.getString("student_id");
                    /*if(data[0].equals("failure")){
                        notice_text.setText("No Notice Fond");
                        //return;

                    }*/
                        if(name[0].equals("failure")){
                            TextView massege = findViewById(R.id.massege);
                            //massege.setText("No Book Found");
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

    //for student profile photo

    private class Image extends AsyncTask<String,Void,Bitmap> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*dg=new ProgressDialog(Profile.this);
            dg.setMessage("Loading...");
            dg.setCancelable(false);
            dg.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // Toast.makeText(Notice.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    dg.dismiss();
                }
            }, 9000);*/
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if(result!=null){
                imageView.setImageBitmap(result);
                progressBar.setVisibility(View.GONE);
            }

            progressBar.setVisibility(View.GONE);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String rollnum = settings.getString(KEY_PASSWORD, "");
                SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings1.getString(SCHOOL_SHARED_PREF, "");
                String school=replace(value);
                URL url = new URL("https://softmax.info/uploaded/"+school+"/Photos/"+rollnum+".jpg");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                //Log.d(TAG,e.getMessage());
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
