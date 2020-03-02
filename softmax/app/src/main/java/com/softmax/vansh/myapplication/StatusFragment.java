package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment implements View.OnClickListener {
    private CardView reportcard;
    private CardView notice;
    private CardView dairypic;
    private CardView time;
    private CardView syllbus;
    private CardView profile;
    private  ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_status, container, false);
        reportcard = rootView.findViewById(R.id.reportcard);
        notice = rootView.findViewById(R.id.notice);
        dairypic= rootView.findViewById(R.id.dairy_pic);
        time=rootView.findViewById(R.id.time_table2);
        profile=rootView.findViewById(R.id.Profile);
        syllbus = rootView.findViewById(R.id.student_syllabs);

        imageView = (ImageView) rootView.findViewById(R.id.image2);
        isNetworkConnectionAvailable();
        //new Load().execute();
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Profile.class);
                startActivity(intent);

            }
        });

        reportcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Report_card.class);
                startActivity(intent);

            }
        });


        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Notice.class);
                startActivity(intent);

            }
        });
        dairypic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),DairyPic.class);
                startActivity(intent);

            }
        });
        syllbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Home_work.this, "Syllabus", Toast.LENGTH_SHORT).show();
                String TempListViewClickedValue = "syllabus.jpg";

                Intent intent = new Intent(getActivity(), TimeTableView.class);
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

                Intent intent = new Intent(getActivity(), TimeTableView.class);
                //Intent intent = new Intent(Home_work.this, PlayVideo.class);

                // Sending value to another activity using intent.
                intent.putExtra("ListViewClickedValue", TempListViewClickedValue);

                startActivity(intent);

                //new Load().execute();

            }
        });
//        reportcard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),Profile.class);
//                startActivity(intent);
//
//            }
//        });

        return rootView;

    }

    @Override
    public void onClick(View view) {

    }

    private class Load extends AsyncTask<String,Void,Bitmap> {
        ProgressDialog dg;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*dg=new ProgressDialog(getActivity());
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
            //dg.dismiss();
            imageView.setImageBitmap(result);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                SharedPreferences settings1 = getActivity().getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings1.getString(SCHOOL_SHARED_PREF, "");
                 String school=replace(value);
                URL url = new URL("https://softmax.info/images/"+school+"/image2.jpg");
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
            //new ChatFragment.Image().execute();
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
}
