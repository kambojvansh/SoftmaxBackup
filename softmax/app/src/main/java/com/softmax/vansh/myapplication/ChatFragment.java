package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import static android.content.Context.MODE_PRIVATE;
import static com.softmax.vansh.myapplication.Login_page.EMAIL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.LOGGEDIN_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {
    public static final String SCHOOL_SHARED_PREF = "school";
    public static final String CLASS_SHARED_PREF = "class";
    public static final String MEDIUM_SHARED_PREF = "medium";
    public static final String SHARED_PREF_NAME2 = "vansh";


    private CardView home_work;
    private CardView attendance;
    private CardView gallary;

//    private CardView marks1;
    ImageView imageView_logo;
    ProgressBar progressBar;

    String urladdress = "https://softmax.info/fetch_data.php";
    String name;
    String school_name;
    String class_name;
    String[] data;
    String[] subject;
    String[] marks;
    String s_medium;
    String[] medium;
    String[] phone;
    String[] school;
    String[] student_class;
    String[] imagepath;
    TextView name_text;
    TextView school_text;
    TextView class_text;
    TextView nav_header;
    TextView nav_subtitle;
    ArrayAdapter<String> arrayAdapter;
    BufferedInputStream is;
    String line = null;
    String result1 = null;
    String result = null;
    TextView  notice_text;
    private ImageView imageView;


    @Override
    public  View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        //return inflater.inflate(R.layout.fragment_chat, container, false);
        //here we set old code
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);



        //due_fees = findViewById(R.id.due_fees_img);
        home_work = rootView.findViewById(R.id.Homework);
        // result = findViewById(R.id.result_img);
        attendance = rootView.findViewById(R.id.attendance1);
        //notice_board = findViewById(R.id.notice_board_img);
        name_text = rootView.findViewById(R.id.username);
        school_text = rootView.findViewById(R.id.userschool);
        class_text = rootView.findViewById(R.id.userclass);
        notice_text = rootView.findViewById(R.id.textmarquee);
        imageView = (ImageView) rootView.findViewById(R.id.image1);
        //HomeWork = rootView.findViewById(R.id.HomeWork);

//        marks1 = rootView.findViewById(R.id.student_marks);

        imageView_logo =rootView.findViewById(R.id.school_logo);
        progressBar = rootView.findViewById(R.id.progressbar_image);
        gallary = rootView.findViewById(R.id.gallary1);

        //SharedPreferences settings = getActivity().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        //final String value = settings.getString(EMAIL_SHARED_PREF, "");
        //Toast.makeText(getActivity(),value,Toast.LENGTH_SHORT);
        isNetworkConnectionAvailable();



        home_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Home_work.class);
                startActivity(intent);

            }
        });
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Gallery.class);
                startActivity(intent);

            }
        });


        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Attendance.class);
                startActivity(intent);
            }
        });

//        marks1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), Marks.class);
//                startActivity(intent);
//
//            }
//        });
        return rootView;


    }

    @Override
    public void onClick(View view) {

    }







    //data fatch


    private class Load extends AsyncTask<Void,Void,Void> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            name_text.setText("Welcome"+" "+name);
            school_text.setText(school_name);
            class_text.setText(class_name);
            //s_medium=medium[0];
            //nav_header.setText(school_name);
            //nav_subtitle.setText(mobile);

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
            editor.putString(SCHOOL_SHARED_PREF, school_name);
            editor.putString(CLASS_SHARED_PREF, class_name);
            //editor.putString(MEDIUM_SHARED_PREF, medium[0]);
            editor.putString(MEDIUM_SHARED_PREF, s_medium);

            editor.commit();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            SharedPreferences settings = getActivity().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            final String value = settings.getString(KEY_PASSWORD, "");
            SharedPreferences settings1 = getActivity().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            final String school_n = settings1.getString(EMAIL_SHARED_PREF, "");
            //String s_name=replace(school_n);


            String urladdress = "https://softmax.info/getprofile2.php?id="+value+"&mobile="+school_n;
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
                result1=sb.toString();

            }
            catch (Exception ex)
            {
                ex.printStackTrace();

            }

//JSON
            try{
                JSONArray ja=new JSONArray(result1);
                JSONObject jo=null;
                //name=new String[ja.length()];
                //email=new String[ja.length()];
                //imagepath=new String[ja.length()];
                data=new String[ja.length()];
                school = new String[ja.length()];
                medium = new String[ja.length()];
                student_class = new String[ja.length()];
                //phone=new String[ja.length()];

                for(int i=0;i<=ja.length();i++){
                    jo=ja.getJSONObject(i);
                    // name[i]=jo.getString("name");
                    // email[i]=jo.getString("email");
                    //imagepath[i]=jo.getString("photo");
                    data[i]=jo.getString("student_name");
                    name=jo.getString("student_name");
                    school[i]=jo.getString("school_name");
                    school_name=jo.getString("school_name");
                    student_class[i]=jo.getString("class");
                    class_name=jo.getString("class");
                    medium[i]=jo.getString("medium");
                    s_medium=jo.getString("medium");
                    //phone[i]=jo.getString("mobile");
                    //mobile=jo.getString("mobile");
                }
                if(data[0].equals("failure")){
                    //.setText("Notice not Found");
                    //return;
                    //Toast.makeText(getActivity(),"data not found",Toast.LENGTH_SHORT);


                }
            }
            catch (Exception ex)
            {

                ex.printStackTrace();
            }

            return null;
        }

    }
    private class Image extends AsyncTask<String,Void,Bitmap> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(getActivity());
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
        protected void onPostExecute(Bitmap result) {
            dg.dismiss();
            imageView.setImageBitmap(result);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                SharedPreferences settings1 = getActivity().getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings1.getString(SCHOOL_SHARED_PREF, "");
                String school=replace(value);
                URL url = new URL("https://softmax.info/images/"+school+"/image1.jpg");
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
    public void checkNetworkConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null) && (wifi.isConnected() | datac.isConnected())) {
            new Load().execute();
            new Image().execute();
            new marqueeText().execute();
            new Image_logo().execute();
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
    private class marqueeText extends AsyncTask<Void,Void,Void> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(getActivity());
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
            SharedPreferences settings = getActivity().getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
            final String value = settings.getString(SCHOOL_SHARED_PREF, "");
            //notice_text.setText(data[0]+" Marks :-"+subject[0]+" "+marks[0]);
            //notice_text.setText(noticebord);
            StringBuffer sb = new StringBuffer(" ");
            if(subject.length<=1)
            {
                subject[0]="Marks not Upload";
                marks[0]=" ";
            }
            for(int i=0;i<subject.length;i++)
            {
                sb.append(subject[i]);
                sb.append(" ");
                sb.append(marks[i]);
                sb.append(", ");


            }

            notice_text.setText(data[0]+": Marks :-"+sb);
        }


        @Override
        protected Void doInBackground(Void... voids) {
            {



                SharedPreferences settings = getActivity().getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings.getString(SCHOOL_SHARED_PREF, "");
                SharedPreferences settings1 = getActivity().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String rollnum = settings1.getString(KEY_PASSWORD, "");



                String school=replace(value);
                String urladdress = "https://softmax.info/getnotice.php?school="+school;
                String urladdress2 = "https://softmax.info/get_marks.php?school="+school+"&rollnum="+rollnum;
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
                    data=new String[ja.length()];

                    for(int i=0;i<=ja.length();i++){
                        jo=ja.getJSONObject(i);
                        data[i]=jo.getString("notice");
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
                //return null;
                // marks for marquee text





                try{

                    URL url=new URL(urladdress2);
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
                        if(subject.length<=0){
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
    }

    // code for school logo

    private class Image_logo extends AsyncTask<String,Void,Bitmap> {
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
                imageView_logo.setImageBitmap(result);
                progressBar.setVisibility(View.GONE);
            }

            progressBar.setVisibility(View.GONE);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                SharedPreferences settings = getActivity().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                final String rollnum = settings.getString(KEY_PASSWORD, "");
                SharedPreferences settings1 = getActivity().getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings1.getString(SCHOOL_SHARED_PREF, "");
                String school=replace(value);
                URL url = new URL("https://softmax.info/images/"+school+"/logo.png");
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


}
