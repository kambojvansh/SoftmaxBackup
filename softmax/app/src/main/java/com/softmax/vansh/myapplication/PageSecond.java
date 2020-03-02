package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.softmax.vansh.myapplication.ChatFragment.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.EMAIL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.LOGGEDIN_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;
import static com.softmax.vansh.myapplication.Theme.THEME_SHARED_PREF;

public class PageSecond extends AppCompatActivity {
    private CardView home_work;
    private CardView attendance;
    private CardView HomeWork;
    private CardView style;
    private CardView marks1;
    private CardView syllbus;
    private CardView notice;
    private CardView reportcard;
    private CardView video;
    private CardView ebook;
    private CardView s_video;
    private CardView dairypic;
    private CardView time;
    private CardView gallary;
    private CardView etest;
    private ImageView logo;
    ImageView imageView2;
    ProgressBar progressBar;
    private TextView birthday;

    public static final String SCHOOL_SHARED_PREF = "school";
    public static final String CLASS_SHARED_PREF = "class";
    public static final String MEDIUM_SHARED_PREF = "medium";
    public static final String SHARED_PREF_NAME2 = "vansh";


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
    String[] dob;
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
    String formattedDate;

    public PageSecond() {
        doubleBackToExitPressedOnce = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_second);
        //Getting out sharedpreferences
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        //Getting editor
        SharedPreferences.Editor editor = preferences.edit();
        //Putting blank value to email
        editor.putString(THEME_SHARED_PREF, "second");

        //Saving the sharedpreferences
        editor.commit();
        isNetworkConnectionAvailable();

        home_work = findViewById(R.id.Homework);
        // result = findViewById(R.id.result_img);
        attendance = findViewById(R.id.attendance1);
        //HomeWork = findViewById(R.id.HomeWork);
        style = findViewById(R.id.theme);
        marks1 = findViewById(R.id.student_marks);
        notice = findViewById(R.id.notice);
        reportcard = findViewById(R.id.reportcard);
        video = findViewById(R.id.videos);
        ebook = findViewById(R.id.e_book);
        s_video=findViewById(R.id.student_videos);
        time=findViewById(R.id.time_table2);
        dairypic=findViewById(R.id.dairy_pic);
        //logo=findViewById(R.id.school_logo);
        imageView2 =findViewById(R.id.school_logo);
        progressBar = findViewById(R.id.progressbar_image);
        birthday = findViewById(R.id.birthday);
        birthday.setVisibility(View.GONE);
        gallary = findViewById(R.id.gallary1);
        etest = findViewById(R.id.etest);



        //due_fees = findViewById(R.id.due_fees_img);

        // result = findViewById(R.id.result_img);

        //notice_board = findViewById(R.id.notice_board_img);
        name_text = findViewById(R.id.username);
        school_text = findViewById(R.id.userschool);
        class_text = findViewById(R.id.userclass);
        notice_text = findViewById(R.id.textmarquee);
        //imageView = findViewById(R.id.image1);
        syllbus = findViewById(R.id.student_syllabs);

        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        formattedDate = df.format(c);

        home_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this, Home_work.class);
                startActivity(intent);

            }
        });
        etest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this, Etest.class);
                startActivity(intent);

            }
        });
//        HomeWork.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(PageSecond.this, HomeWorkShow.class);
//                startActivity(intent);
//
//            }
//        });


        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this, Attendance.class);
                startActivity(intent);
            }
        });
        style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this, Theme.class);
                startActivity(intent);

            }
        });
        marks1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this, Marks.class);
                startActivity(intent);

            }
        });

        syllbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Home_work.this, "Syllabus", Toast.LENGTH_SHORT).show();
                String TempListViewClickedValue = "syllabus.jpg";

                Intent intent = new Intent(PageSecond.this, TimeTableView.class);
                //Intent intent = new Intent(Home_work.this, PlayVideo.class);

                // Sending value to another activity using intent.
                intent.putExtra("ListViewClickedValue", TempListViewClickedValue);

                startActivity(intent);

                //new Load().execute();

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Home_work.this, "Syllabus", Toast.LENGTH_SHORT).show();
                String TempListViewClickedValue = "Time_table.jpg";

                Intent intent = new Intent(PageSecond.this, TimeTableView.class);
                //Intent intent = new Intent(Home_work.this, PlayVideo.class);

                // Sending value to another activity using intent.
                intent.putExtra("ListViewClickedValue", TempListViewClickedValue);

                startActivity(intent);

                //new Load().execute();

            }
        });
        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this,Notice.class);
                startActivity(intent);

            }
        });
        reportcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this,Report_card.class);
                startActivity(intent);

            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this,PlayVideo.class);
                startActivity(intent);

            }
        });


        ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this,Ebook_sub.class);
                startActivity(intent);

            }
        });
        s_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this,Profile.class);
                startActivity(intent);

            }
        });
        dairypic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this,DairyPic.class);
                startActivity(intent);

            }
        });
        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageSecond.this,Gallery.class);
                startActivity(intent);

            }
        });
    }

    //manu bar


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.selection_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logout();
            return true;
        }else if(id==R.id.action_exit){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Are you sure you want to Exit?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                            //System.exit(0);
                            moveTaskToBack(true);

                        }
                    });

            alertDialogBuilder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            arg0.dismiss();

                        }
                    });

            //Showing the alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }else if(id==R.id.action_profile){
            Intent intent = new Intent(PageSecond.this, Profile.class);
            startActivity(intent);


        }else if(id==R.id.action_profil){
            Intent intent = new Intent(PageSecond.this, Profile.class);
            startActivity(intent);


        }else if(id==R.id.policy){
            Intent intent = new Intent(PageSecond.this, Policy.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }


    boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish();
            moveTaskToBack(true);

        }
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (doubleBackToExitPressedOnce) {
            finish();
            moveTaskToBack(true);

        }*/

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);


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

            new marqueeText().execute();
            new Image().execute();
            //connection is avlilable
            //Toast.makeText(Selection_page.this,"active...",Toast.LENGTH_SHORT);
        } else {
            //no connection
            checkNetworkConnection();

        }
    }

    //other section for school info

    public String replace(String str) {
        String[] words = str.split(" ");
        StringBuilder sentence = new StringBuilder(words[0]);

        for (int i = 1; i < words.length; ++i) {
            sentence.append("%20");
            sentence.append(words[i]);
        }

        return sentence.toString();
    }


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
            String date=dob[0];
            //String bday=date.substring(0,5);
            //String mdate=formattedDate.substring(0,5);
            //birthday.setText(mdate+" "+bday);
//            if(bday.equals( mdate)){
//                birthday.setVisibility(View.VISIBLE);
//                Animation anim = new AlphaAnimation(0.0f, 1.0f);
//                anim.setDuration(200); //You can manage the blinking time with this parameter
//                anim.setStartOffset(20);
//                anim.setRepeatMode(Animation.REVERSE);
//                anim.setRepeatCount(Animation.INFINITE);
//                birthday.startAnimation(anim);
//                birthday.setText("Happy Birthday "+name);
//
////            }
//            else {
//                birthday.setVisibility(View.GONE);
//
//            }
            //s_medium=medium[0];
            //nav_header.setText(school_name);
            //nav_subtitle.setText(mobile);

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);

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

            SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            final String value = settings.getString(KEY_PASSWORD, "");
            SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
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
                dob = new String[ja.length()];
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
                    dob[i]=jo.getString("dob");
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




    private class marqueeText extends AsyncTask<Void,Void,Void> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(PageSecond.this);
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



                SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings.getString(SCHOOL_SHARED_PREF, "");
                SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
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
    //code for set school logo

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
                imageView2.setImageBitmap(result);
                progressBar.setVisibility(View.GONE);
            }

            progressBar.setVisibility(View.GONE);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
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


    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(PageSecond.this, Phone_num.class);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}

