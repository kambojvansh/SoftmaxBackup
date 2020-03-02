package com.softmax.vansh.myapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.softmax.vansh.myapplication.R;
import com.softmax.vansh.myapplication.RecyclerViewAdapter;
import com.softmax.vansh.myapplication.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.softmax.vansh.myapplication.Selection_page.SCHOOL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Gallery extends AppCompatActivity {
    //private final String JSON_URL = "https://gist.githubusercontent.com/aws1994/f583d54e5af8e56173492d3f60dd5ebf/raw/c7796ba51d5a0d37fc756cf0fd14e54434c547bc/anime.json" ;
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<Anime> lstAnime ;
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        lstAnime = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);

        jsonrequest();



    }

    private void jsonrequest() {
        SharedPreferences settings1 = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        final String value1 = settings1.getString(SCHOOL_SHARED_PREF, "");

        String school=replace(value1);
        String JSON_URL = "https://softmax.info/get_gallary.php?school="+school;


        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject  = null ;

                for (int i = 0 ; i < response.length(); i++ ) {


                    try {
                        jsonObject = response.getJSONObject(i) ;
                        Anime anime = new Anime() ;
                        anime.setName(jsonObject.getString("title"));
                        anime.setDescription(jsonObject.getString("description"));
                        //anime.setRating(jsonObject.getString("Rating"));
                        //anime.setCategorie(jsonObject.getString("categorie"));
                        //anime.setNb_episode(jsonObject.getInt("episode"));
                        //anime.setStudio(jsonObject.getString("studio"));
                        anime.setImage_url(jsonObject.getString("path"));
                        lstAnime.add(anime);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                setuprecyclerview(lstAnime);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        requestQueue = Volley.newRequestQueue(Gallery.this);
        requestQueue.add(request) ;


    }

    private void setuprecyclerview(List<Anime> lstAnime) {


        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,lstAnime) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

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
