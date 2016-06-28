package com.mygcmpractice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

   // private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    private TextView mInformationTextView;
    private Button btn_SendMsg;
    private Button btn_back;
    private Button btn_back2;
    private Context main_context;
    private EditText txt_username;
    private EditText txt_email;
    private EditText txt_msg;
    private String user_name;
    private String user_email;
    private String msg_toSend;
    private Activity thisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_context = getApplicationContext();
        thisActivity = this;
        //new PrefsFragment().AddPreference();
        /*mRegistrationProgressBar = (ProgressBar) findViewById(R.id.registrationProgressBar);
        txt_username    = (EditText)findViewById(R.id.txt_userId);
        txt_email       = (EditText)findViewById(R.id.txt_email);*/
        btn_back        = (Button)findViewById(R.id.btn_back);
        btn_back2        = (Button)findViewById(R.id.btn_back2);

        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,MainMenuActivity.class);
               startActivity(intent);
                Log.i(TAG,"Starting intent Menu activityy");
            }
        });


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainMenuActivity.class);
                startActivity(intent);
                Log.i(TAG,"Starting intent Menu activityy");
            }
        });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                   // mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    boolean sentToken = sharedPreferences.getBoolean(GCMPreferences.SENT_TOKEN_TO_SERVER,false);
                    if (sentToken) {
                        Log.i("GCM-Miki Registration","Token has been sent to server");
                        mInformationTextView.setText(getString(R.string.gcm_send_message));
                    } else {
                        Log.i("GCM-Miki Registration","Problem sending token to server");
                        mInformationTextView.setText(getString(R.string.token_error_message));
                    }
                }
            };
        mInformationTextView = (TextView) findViewById(R.id.informationTextView);

    }

    private void sendMessageToOtherDevice(String _msg, String user_name) {

    }
    private void openMenu(){
        Intent intent = new Intent(MainActivity.this,MainMenuActivity.class);
        startActivity(intent);
        Log.i(TAG,"Starting intent Menu activityy");
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
        new IntentFilter(GCMPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    /*
    public static class PrefsFragment extends PreferenceFragment{
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
        }
        public void AddPreference(){
            addPreferencesFromResource(R.xml.mygcmpreference);
        }
    }
    */
}

