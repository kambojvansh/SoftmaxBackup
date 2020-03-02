package com.softmax.vansh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import static com.softmax.vansh.myapplication.Login_page.EMAIL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.LOGGEDIN_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;

public class Theme extends AppCompatActivity {
    ImageView theme1;
    ImageView theme2;
    public static final String THEME_SHARED_PREF = "first";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        theme1 = findViewById(R.id.theme1);
        theme2 = findViewById(R.id.theme2);
        theme1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                //Getting out sharedpreferences
//                SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
//                //Getting editor
//                SharedPreferences.Editor editor = preferences.edit();
//                //Putting blank value to email
//                editor.putString(THEME_SHARED_PREF, "first");
//
//                //Saving the sharedpreferences
//                editor.commit();
                Intent intent = new Intent(Theme.this, Selection_page.class);
                startActivity(intent);
                finish();

            }
        });
        theme2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                //Getting out sharedpreferences
//                SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
//                //Getting editor
//                SharedPreferences.Editor editor = preferences.edit();
//                //Putting blank value to email
//                editor.putString(THEME_SHARED_PREF, "second");
//
//                //Saving the sharedpreferences
//                editor.commit();
                Intent intent = new Intent(Theme.this, PageSecond.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
