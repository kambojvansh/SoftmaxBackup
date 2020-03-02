package com.softmax.vansh.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.softmax.vansh.myapplication.Login_page.EMAIL_SHARED_PREF;
import static com.softmax.vansh.myapplication.Login_page.KEY_PASSWORD;
import static com.softmax.vansh.myapplication.Login_page.SHARED_PREF_NAME;
import static com.softmax.vansh.myapplication.Phone_num.PHONE_SHARED_PREF;
import static com.softmax.vansh.myapplication.Phone_num.SHARED_PREF_NAME1;

public class Otp extends AppCompatActivity {
    //private FirebaseAuth firebaseAuth;
    TextView editTextPhone;
    TextView resend;
    EditText editTextCode;
    FirebaseAuth mAuth;
    String codesend;
    Button sub_otp;
    ProgressDialog dg;
    //SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    //final String phone = settings.getString(EMAIL_SHARED_PREF, "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        sub_otp= findViewById(R.id.otp_button);
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME1, MODE_PRIVATE);
        final String phone1 = settings.getString(PHONE_SHARED_PREF, "");
        mAuth = FirebaseAuth.getInstance();
        editTextCode = findViewById(R.id.otp);
        editTextPhone = findViewById(R.id.phone);
        resend = findViewById(R.id.resend);
        editTextPhone.setText(phone1);
        isNetworkConnectionAvailable();
        //sendVerificationCode();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationCode();

            }
        });

        sub_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code=editTextCode.getText().toString().trim();
                if(code.isEmpty()){
                    editTextCode.setError("Plese Enter code here");
                    //return;

                }
                else {

                    dg=new ProgressDialog(Otp.this);
                    dg.setMessage("Verifying...");
                    dg.setCancelable(false);
                    dg.show();
                    verifiSigninCode();
                }

            }
        });



    }
    @Override
    public void onBackPressed(){
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

    private void verifiSigninCode() {
        String code = editTextCode.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codesend, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            dg.dismiss();
                            final String phone1 = getIntent().getStringExtra("number");

                            Intent intent = new Intent(Otp.this,Login_page.class);
                            intent.putExtra("number1", phone1);
                            startActivity(intent);
                            finish();

                            //Toast.makeText(Otp.this, "SUCESSFULL...!!!", Toast.LENGTH_SHORT).show();
                            //registerUser();
                            //send_data();



                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                dg.dismiss();

                                // The verification code entered was invalid

                                Toast.makeText(Otp.this, "Enter a Valid Code", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                });
    }

    private void sendVerificationCode() {
        dg=new ProgressDialog(Otp.this);
        dg.setMessage("Sending Otp.....");
        dg.setCancelable(false);
        dg.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //Toast.makeText(Attendance.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                dg.dismiss();
            }
        }, 3000);
        SharedPreferences settings = getSharedPreferences(SHARED_PREF_NAME1, MODE_PRIVATE);
        final String phone1 = settings.getString(PHONE_SHARED_PREF, "");

        String phone = "+91"+phone1;//editTextPhone.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                Otp.this,
                mCallbacks);// Activity (for callback binding)

    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            dg.dismiss();
            Toast.makeText(Otp.this,"Code send",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            dg.dismiss();
            Toast.makeText(Otp.this,"Code not send",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codesend = s;
        }
    };



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
            //Toast.makeText(this,"active...",Toast.LENGTH_SHORT);
            sendVerificationCode();
        } else {
            //no connection
            checkNetworkConnection();

        }
    }




}
