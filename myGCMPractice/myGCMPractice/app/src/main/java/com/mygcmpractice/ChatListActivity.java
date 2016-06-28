package com.mygcmpractice;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {


    private static final String TAG = "ChatListActivity";
    private ListView ChatlistView;
    private Cursor cursor;
    private Context chatListContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatlist_activity);
        Log.i(TAG,"send activiyty created");
        chatListContext = getApplicationContext();
        ChatlistView = (ListView)findViewById(R.id.listViewChat);
        final List<String> chatHeads = new ArrayList<String>();

        chatHeads.add(0,"Mykey");
        chatHeads.add(1,"Liab");
        chatHeads.add(2,"Eyob");
        chatHeads.add(3, "Hiwi");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.chatlist_item_activity,chatHeads);
        ChatlistView.setAdapter(arrayAdapter);
        Log.i(TAG, "Starting Chat list");
        ChatlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChatListActivity.this, ChatActivity.class);
                intent.putExtra("RECEIVER",chatHeads.get(position));
                //intent.putExtra("RECEIVER","Mykey");
                //passing the reciver info to the chatActivity
                startActivity(intent);
                Log.i(TAG, "Starting intent Chat message");
            }
        });


        /*
        btn_SendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
       */


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

