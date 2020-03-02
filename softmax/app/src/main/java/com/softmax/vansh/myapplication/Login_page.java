package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.softmax.vansh.myapplication.Phone_num;
import com.softmax.vansh.myapplication.R;
import com.softmax.vansh.myapplication.Selection_page;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.softmax.vansh.myapplication.Phone_num.PHONE_SHARED_PREF;
import static com.softmax.vansh.myapplication.Phone_num.SHARED_PREF_NAME1;
import static com.softmax.vansh.myapplication.Phone_num.PHONE_SHARED_PREF;
import static com.softmax.vansh.myapplication.Phone_num.SHARED_PREF_NAME1;

public class Login_page extends AppCompatActivity {
    public static final String LOGIN_URL="https://softmax.info/Login.php";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";
    public static final String LOGIN_SUCCESS="success";
    public static final String SHARED_PREF_NAME="tech";
    public static final String EMAIL_SHARED_PREF="email";
    public static final String LOGGEDIN_SHARED_PREF="loggedin";
    public String[] data;
    ProgressDialog progressDialog;
    //private EditText editTextEmail;
    private EditText editTextPassword;
    private Button BtnLogin;
    private TextView phone;
    private TextView change;
    private boolean loggedIn=false;

    String password_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //editTextEmail=(EditText)findViewById(R.id.input_id);
        editTextPassword=(EditText)findViewById(R.id.input_pass);
        BtnLogin=(Button)findViewById(R.id.login_button);
        phone = findViewById(R.id.user_id);
        change = findViewById(R.id.change);
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME1, MODE_PRIVATE);
        final String phone1 = settings.getString(PHONE_SHARED_PREF, "");
        phone.setText(phone1);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_page.this, Phone_num.class);
                startActivity(intent);
                finish();
            }
        });
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });



        isNetworkConnectionAvailable();


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
            Toast.makeText(this,"active...",Toast.LENGTH_SHORT);
        } else {
            //no connection
            checkNetworkConnection();

        }
    }

    private void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME1, MODE_PRIVATE);
        final String phone1 = settings.getString(PHONE_SHARED_PREF, "");
        final String email = phone1.trim();
        final String password = editTextPassword.getText().toString().trim();
        //Toast.makeText(Login_page.this, "working...", Toast.LENGTH_LONG).show();
//        ProgressDialog progressDialog;

        progressDialog = new ProgressDialog(Login_page.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equalsIgnoreCase(LOGIN_SUCCESS)){

                            SharedPreferences sharedPreferences = Login_page.this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean(LOGGEDIN_SHARED_PREF, true);
                            editor.putString(EMAIL_SHARED_PREF, email);
                            editor.putString(KEY_PASSWORD,password);

                            editor.commit();

                            Intent intent = new Intent(Login_page.this, PageSecond.class);
                            startActivity(intent);
                            finish();
//                            Toast.makeText(Login_page.this, "success username or password", Toast.LENGTH_LONG).show();
//                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(Login_page.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> prams = new HashMap<>();
                prams.put(KEY_EMAIL, email);
                prams.put(KEY_PASSWORD, password);

                return prams;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //onLoginSuccess();
                        // onLoginFailed();
                        //Toast.makeText(Login_page.this,"Try Agin",Toast.LENGTH_LONG).show();
                       /// progressDialog.dismiss();
                    }
                }, 3000);
    }
   /* @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        loggedIn = sharedPreferences.getBoolean(LOGGEDIN_SHARED_PREF, false);
        if(loggedIn){
            Intent intent = new Intent(Login_page.this, Selection_page.class);
            startActivity(intent);
            finish();
        }
    }*/

    public boolean validate() {
        boolean valid = true;

        //String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (password.isEmpty() /*|| email.length()!=10*/) {
            editTextPassword.setError("Enter valid Password");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }

}