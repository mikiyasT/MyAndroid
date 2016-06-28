package com.mygcmpractice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {


    private static final String TAG = "RegisterActivity";
    private Button btn_register;
    private Button btn_unRegister;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    private Activity thisActivity;
    private EditText txt_username;
    private EditText txt_email;
    private String user_name;
    private String user_email;
    private Context registerContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Log.i(TAG, "Register activiyty created");
        thisActivity = this;
        registerContext = getApplicationContext();
        mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        txt_username    = (EditText)findViewById(R.id.txt_userId);
        txt_email       = (EditText)findViewById(R.id.txt_email);
        btn_register    = (Button)findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GCMPreferences.checkPlayServices(thisActivity)) {
                    // Start IntentService to register this application with GCM.
                    user_name = (String) txt_username.getText().toString();
                    user_email = (String) txt_email.getText().toString();
                    Log.i("GCM-Miki Registration", "Trying to register device");
                    regisgterDeviceWithGCM();
                }

            }
        });

        btn_unRegister = (Button)findViewById(R.id.btn_unRegister);
        btn_unRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GCMPreferences.checkPlayServices(thisActivity)) {
                    // Start IntentService to register this application with GCM.
                    Log.i("GCM-Miki 1 Registration","Trying to register device Main application");
                    unregisgterDeviceWithGCM();

                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {

        super.onPause();
    }
    private void regisgterDeviceWithGCM() {
        //inside the RegistrationIntentService class separete the registration with the GCM from the registration on the App server.
        //Registration on the app server should be done here in this class, after getting result from the registrationToken.
        mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
        if (GCMPreferences.checkPlayServices(thisActivity)) {
            // Start IntentService to register this application with GCM.
            Log.i("GCM-Miki 2 Registration", "Trying to register in DB");
            Intent intent = new Intent(getApplicationContext(), RegistrationIntentService.class);
            Log.i("GCM-Miki 2 Registration", "after inte");
            intent.putExtra("USER_NAME", user_name);
            intent.putExtra("USER_EMAIL",user_email);
            Log.i("GCM-Miki 2 Registration", "after adding data to int");
            startService(intent);
            Log.i("GCM-Miki 2 Registration", "after starting intent");
        }
    }

    private void unregisgterDeviceWithGCM() {
        if (GCMPreferences.checkPlayServices(thisActivity)) {
            // Start IntentService to register this application with GCM.
            Log.i("GCM-Miki UnRegistration", "Trying to unregister device");
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(registerContext);
            sharedPreferences.edit().putBoolean(GCMPreferences.SENT_TOKEN_TO_SERVER, false).apply();
            //delete the old registration id from the server database,
            new ApplicationServer().unRegisterFromServer("token");
            // if necessary refresh and obtain new token and register that.
            mInformationTextView.setText("");

        }
    }

}

