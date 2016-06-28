package com.mygcmpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainMenuActivity extends AppCompatActivity {


    private static final String TAG = "MainMenuActivity";
    private Button btn_account;
    private Button btn_unRegister;
    private Button btn_chat;
    private Button btn_back;
    private Button btn_send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        Log.i(TAG, "menu activiyty created");
        btn_account = (Button)findViewById(R.id.btn_account);
        btn_chat = (Button)findViewById(R.id.btn_chat);


        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this,ChatListActivity.class);
                startActivity(intent);
                Log.i(TAG,"Starting intent Chat message");
            }
        });

        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this,RegisterActivity.class);
                startActivity(intent);
                Log.i(TAG,"Starting intent Register message");
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

}

