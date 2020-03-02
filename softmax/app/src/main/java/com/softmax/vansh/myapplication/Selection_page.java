package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
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

import static com.softmax.vansh.myapplication.Login_page.EMAIL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.LOGGEDIN_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Theme.THEME_SHARED_PREF;


public class Selection_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SCHOOL_SHARED_PREF = "school";
    public static final String CLASS_SHARED_PREF = "class";
    public static final String MEDIUM_SHARED_PREF = "medium";
    public static final String SHARED_PREF_NAME2 = "vansh";

    private ImageButton report_card;
    private ImageButton e_book;
    private ImageButton notice;
    private ImageButton due_fees;
    private ImageButton home_work;
    private ImageButton result;
    private ImageButton attendance;
    private ImageButton notice_board;
    private ImageButton videos;
    private TextView user_name;
    private String password_id;

    //for data fatch
    String urladdress = "https://softmax.info/fetch_data.php";
    String name;
    String school_name;
    String class_name;
    String[] data;
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


    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    PageAdapter pageAdapter;
    TabItem tabChats;
    TabItem tabStatus;
    TabItem tabCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_page);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Getting out sharedpreferences
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        //Getting editor
        SharedPreferences.Editor editor = preferences.edit();
        //Putting blank value to email
        editor.putString(THEME_SHARED_PREF, "first");

        //Saving the sharedpreferences
        editor.commit();
        setSupportActionBar(toolbar);
        isNetworkConnectionAvailable();

        tabLayout = findViewById(R.id.tablayout);
        tabChats = findViewById(R.id.tabChats);
        tabStatus = findViewById(R.id.tabStatus);
        tabCalls = findViewById(R.id.tabCalls);
        viewPager = findViewById(R.id.viewPager);
        nav_header = findViewById(R.id.nav_header_title);
        nav_subtitle = findViewById(R.id.nav_header_subtitle);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(Selection_page.this,
                            R.color.colorAccent));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(Selection_page.this,
                            R.color.colorAccent));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(Selection_page.this,
                                R.color.colorAccent));
                    }
                } else if (tab.getPosition() == 2) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(Selection_page.this,
                            android.R.color.darker_gray));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(Selection_page.this,
                            android.R.color.darker_gray));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(Selection_page.this,
                                android.R.color.darker_gray));
                    }
                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(Selection_page.this,
                            R.color.colorPrimary));
                    tabLayout.setBackgroundColor(ContextCompat.getColor(Selection_page.this,
                            R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(Selection_page.this,
                                R.color.colorPrimaryDark));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }




    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (doubleBackToExitPressedOnce) {
            finish();
            moveTaskToBack(true);

        }

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
            //connection is avlilable
            //Toast.makeText(Selection_page.this,"active...",Toast.LENGTH_SHORT);
        } else {
            //no connection
            checkNetworkConnection();

        }
    }

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
            Intent intent = new Intent(Selection_page.this, Profile.class);
            startActivity(intent);


        }else if(id==R.id.action_profil){
            Intent intent = new Intent(Selection_page.this, Profile.class);
            startActivity(intent);


        }else if(id==R.id.policy){
            Intent intent = new Intent(Selection_page.this, Policy.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_report_card) {

            Intent intent = new Intent(Selection_page.this, Report_card.class);
            startActivity(intent);
        } else if (id == R.id.nav_E_book) {
            Intent intent = new Intent(Selection_page.this, Ebook_sub.class);
            startActivity(intent);

        } else if (id == R.id.nav_video) {
            Intent intent = new Intent(Selection_page.this, Video_url.class);
            startActivity(intent);

        }else if (id == R.id.nav_attendance) {
            Intent intent = new Intent(Selection_page.this, Attendance.class);
            startActivity(intent);

        } else if (id == R.id.nav_notice) {
            Intent intent = new Intent(Selection_page.this, Notice.class);
            startActivity(intent);

        }  else if (id == R.id.nav_home_work) {
            Intent intent = new Intent(Selection_page.this, Home_work.class);
            startActivity(intent);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(Selection_page.this, Profile.class);
            startActivity(intent);

        }else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(Selection_page.this, About_us.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            logout();

        } else if (id == R.id.nav_exit) {
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

        }else if (id == R.id.nav_policy) {
            Intent intent = new Intent(Selection_page.this, Policy.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                        Intent intent = new Intent(Selection_page.this, Phone_num.class);
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



       /* //here we set old code
        nav_header = findViewById(R.id.nav_header_title);
        nav_subtitle = findViewById(R.id.nav_header_subtitle);
        name_text = findViewById(R.id.username);
        school_text = findViewById(R.id.userschool);
        class_text = findViewById(R.id.userclass);


        e_book = findViewById(R.id.e_book_img);
        notice = findViewById(R.id.notice_img);
        //due_fees = findViewById(R.id.due_fees_img);
        home_work = findViewById(R.id.home_work_img);
        // result = findViewById(R.id.result_img);
        attendance = findViewById(R.id.attendance_img);
        //notice_board = findViewById(R.id.notice_board_img);
        videos = findViewById(R.id.videos_img);
        report_card = findViewById(R.id.report_card_img);
        new Load().execute();

        report_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Selection_page.this, Report_card.class);
                startActivity(intent);
            }
        });

        e_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Selection_page.this, Ebook_sub.class);
                startActivity(intent);
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Selection_page.this, Notice.class);
                startActivity(intent);
            }
        });



        home_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Selection_page.this, Home_work.class);
                startActivity(intent);
            }
        });



        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Selection_page.this, Attendance.class);
                startActivity(intent);
            }
        });

        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Selection_page.this, Video_url.class);
                startActivity(intent);
            }
        });




        LinearLayout layout = (LinearLayout) findViewById(R.id.report_card);
        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub




            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (doubleBackToExitPressedOnce) {
            finish();
            moveTaskToBack(true);

        }

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
            //connection is avlilable
            Toast.makeText(Selection_page.this,"active...",Toast.LENGTH_SHORT);
        } else {
            //no connection
            checkNetworkConnection();

        }
    }

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
            Intent intent = new Intent(Selection_page.this, Profile.class);
            startActivity(intent);


        }else if(id==R.id.action_profil){
            Intent intent = new Intent(Selection_page.this, Profile.class);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_report_card) {

            Intent intent = new Intent(Selection_page.this, Report_card.class);
            startActivity(intent);
        } else if (id == R.id.nav_E_book) {
            Intent intent = new Intent(Selection_page.this, Ebook_sub.class);
            startActivity(intent);

        } else if (id == R.id.nav_video) {
            Intent intent = new Intent(Selection_page.this, Video_url.class);
            startActivity(intent);

        }else if (id == R.id.nav_attendance) {
        Intent intent = new Intent(Selection_page.this, Attendance.class);
        startActivity(intent);

        } else if (id == R.id.nav_notice) {
        Intent intent = new Intent(Selection_page.this, Notice.class);
        startActivity(intent);

        }  else if (id == R.id.nav_home_work) {
        Intent intent = new Intent(Selection_page.this, Home_work.class);
        startActivity(intent);

        } else if (id == R.id.nav_profile) {
        Intent intent = new Intent(Selection_page.this, Profile.class);
        startActivity(intent);

    }else if (id == R.id.nav_about_us) {
            Intent intent = new Intent(Selection_page.this, About_us.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            logout();

        } else if (id == R.id.nav_exit) {
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

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                        Intent intent = new Intent(Selection_page.this, Phone_num.class);
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

    //data fatch


    private class Load extends AsyncTask<Void,Void,Void> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dg=new ProgressDialog(Selection_page.this);
            dg.setMessage("Loading...");
            dg.setCancelable(false);
            dg.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                   // Toast.makeText(Selection_page.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    dg.dismiss();
                }
            }, 9000);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dg.dismiss();


            name_text.setText("Welcome"+" "+name);
            school_text.setText(school_name);
            class_text.setText(class_name);
            //s_medium=medium[0];
            //nav_header.setText(school_name);
            //nav_subtitle.setText(mobile);

            SharedPreferences sharedPreferences = Selection_page.this.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
            editor.putString(SCHOOL_SHARED_PREF, school_name);
            editor.putString(CLASS_SHARED_PREF, class_name);
            editor.putString(MEDIUM_SHARED_PREF, medium[0]);

            editor.commit();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            final String value = settings.getString(EMAIL_SHARED_PREF, "");


            String urladdress = "http://softmax.info/getprofile.php?email="+value;
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
                if(ja.equals("failure")){
                    //.setText("Notice not Found");
                    //return;
                    Toast.makeText(Selection_page.this,"data not found",Toast.LENGTH_SHORT);


                }
            }
            catch (Exception ex)
            {

                ex.printStackTrace();
            }

            return null;
        }

    }

}*/
