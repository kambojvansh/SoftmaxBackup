package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.softmax.vansh.myapplication.R ;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.softmax.vansh.myapplication.ChatFragment.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class AnimeActivity extends AppCompatActivity {
    ImageView img;
    ProgressBar progressBar;
    String image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime);

        // hide the default actionbar
        getSupportActionBar().hide();

        // Recieve data

        String name  = getIntent().getExtras().getString("anime_name");
        String description = getIntent().getExtras().getString("anime_description");
       //String studio = getIntent().getExtras().getString("anime_studio") ;
       // String category = getIntent().getExtras().getString("anime_category");
        //int nb_episode = getIntent().getExtras().getInt("anime_nb_episode") ;
        //String rating = getIntent().getExtras().getString("anime_rating") ;
         image_url = getIntent().getExtras().getString("anime_img") ;

        // ini views

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

       // TextView tv_name = findViewById(R.id.aa_anime_name);
       // TextView tv_studio = findViewById(R.id.aa_studio);
       // TextView tv_categorie = findViewById(R.id.aa_categorie) ;
        TextView tv_description = findViewById(R.id.aa_description);
       // TextView tv_rating  = findViewById(R.id.aa_rating) ;
        img = findViewById(R.id.aa_thumbnail);
        progressBar = findViewById(R.id.progressbar_image);

        // setting values to each view

       // tv_name.setText(name);
        //tv_categorie.setText(category);
        tv_description.setText(description);
        //tv_rating.setText(rating);
        //tv_studio.setText(studio);

        collapsingToolbarLayout.setTitle(name);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // set image using Glide
        //Glide.with(this).load(image_url).apply(requestOptions).into(img);
        new Image().execute();





    }

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
                img.setImageBitmap(result);
                progressBar.setVisibility(View.GONE);
            }

            progressBar.setVisibility(View.GONE);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            try {

                URL url = new URL(image_url);
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

