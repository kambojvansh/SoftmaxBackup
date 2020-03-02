package com.softmax.vansh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.softmax.vansh.myapplication.Login_page.LOGGEDIN_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;

public class MainActivity extends AppCompatActivity {
    private boolean loggedIn=false;
    private FirebaseAuth mAuth;
    public static int TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Phone_num.class);
                startActivity(intent);
                finish();
            }
        },TIME);
    }


}
