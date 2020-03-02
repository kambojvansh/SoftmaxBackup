package com.softmax.vansh.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Selection_page.SHARED_PREF_NAME2;
import static com.softmax.vansh.myapplication.Theme.THEME_SHARED_PREF;


public class Phone_num extends AppCompatActivity {
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    public static final String SHARED_PREF_NAME1="softmax";
    public static final String PHONE_SHARED_PREF="email";
    private Button verify;
    private EditText phone;
    String number;
    String theme_number;
    private boolean loggedIn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_num);

        verify = findViewById(R.id.verify_button);
        phone = (EditText) findViewById(R.id.input_num);
        number =phone.getText().toString().trim();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    SharedPreferences sharedPreferences = Phone_num.this.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                    editor.putString(PHONE_SHARED_PREF, phone.getText().toString());
                    //editor.putString(KEY_PASSWORD,password);

                    editor.commit();
                    //here otp class called
                    Intent intent = new Intent(Phone_num.this,Otp.class);
//                    Intent intent = new Intent(Phone_num.this,Login_page.class);
                    startActivity(intent);
                    finish();
                }
                //Toast.makeText(Phone_num.this,phone.getText().toString(),Toast.LENGTH_LONG).show();

            }
        });
    }
    public boolean validate() {
        boolean valid = true;

        String mobile = phone.getText().toString();

        if (mobile.isEmpty()) {
            phone.setError("Enter Phone number");
            valid = false;
        } else if(mobile.length()!=10){
            phone.setError("Enter valid Phone number");
            valid = false;

        }

        return valid;
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME2, MODE_PRIVATE);
        final String value = settings.getString(THEME_SHARED_PREF, "");
        if(loggedIn){
            if(value.equals("first")){
                Intent intent = new Intent(Phone_num.this, Selection_page.class);
                startActivity(intent);
                finish();
            } else if(value.equals("second")){
                Intent intent = new Intent(Phone_num.this, PageSecond.class);
                startActivity(intent);
                finish();

            }


        }
    }
}
