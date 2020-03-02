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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;
import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallFragment extends Fragment implements View.OnClickListener {
    private CardView video;
    private  CardView ebook;
    private CardView s_video;
    private ImageView imageView;
    private CardView style;
    private CardView marks1;
    private CardView etest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_call, container, false);
        video = rootView.findViewById(R.id.videos);
        ebook = rootView.findViewById(R.id.e_book);

        marks1 = rootView.findViewById(R.id.student_marks);
//        s_video=rootView.findViewById(R.id.student_videos);
        etest = rootView.findViewById(R.id.etest2);
        imageView = (ImageView) rootView.findViewById(R.id.image3);
        style = rootView.findViewById(R.id.theme);
        isNetworkConnectionAvailable();
        //new Load().execute();

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PlayVideo.class);
                startActivity(intent);

            }
        });

        etest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Etest.class);
                startActivity(intent);

            }
        });

        style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Theme.class);
                startActivity(intent);

            }
        });


        ebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Ebook_sub.class);
                startActivity(intent);

            }
        });
        marks1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Marks.class);
                startActivity(intent);

            }
        });
//        s_video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(),PlayVideo.class);
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

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                SharedPreferences settings1 = getActivity().getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
                final String value = settings1.getString(SCHOOL_SHARED_PREF, "");
                String school=replace(value);
                URL url = new URL("https://softmax.info/images/"+school+"/image3.jpg");
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
