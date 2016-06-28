package com.mygcmpractice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {

    private BroadcastReceiver mNewMessageArrived;
    private static final String TAG = "ChatActivity";
    private Button btn_SendMsg;
    private TextView chatTextHistory;
    private String sender;
    private String reciever;
    private EditText txt_msg;
    private Context chatContext;
    private Activity activity;
    String user_name;
    String user_email;
    String msg_toSend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        Log.i(TAG, "send activiyty created");
        btn_SendMsg = (Button)findViewById(R.id.btn_sendMsg);
        chatTextHistory = (TextView)findViewById(R.id.textChatHistory);
        //make this chat window multi line
        chatTextHistory.setSingleLine(false);
        //make the chat history scrollable
        chatTextHistory.setMovementMethod(new ScrollingMovementMethod());
        txt_msg = (EditText)findViewById(R.id.txt_msg);
        chatContext = getApplicationContext();
        activity = this;
        //this activty is created when a name in chatList is clicked
        //so the recevier data comes from the chatList activity which created this activty
        reciever = getIntent().getStringExtra("RECEIVER").toString();
        sender = "Mykey";//get the sender from the shared prefreences

        btn_SendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GCMPreferences.checkPlayServices(activity)) {
                    // Start IntentService to register this application with GCM.
                    Log.i("GCM-Miki Sending msg","Trying to send message");
                    txt_msg = (EditText)findViewById(R.id.txt_msg);
                    msg_toSend = (String)txt_msg.getText().toString();
                    ApplicationServer appServer = new ApplicationServer();
                    appServer.sendMessageToDevice(chatContext, msg_toSend, sender, reciever);
                    Log.i("TAG"," Miki reciever has been set --- "+reciever);
                    //sendMessageToOtherDevice(msg_toSend,user_name);
                    String newMessage = "\n\n ->" + msg_toSend;
                    String prevChatHistory = getChatTextHistory();
                    //chatTextHistory.setText("\n\n -> : "+msg_toSend);
                    setChatTextHistory(prevChatHistory + newMessage);
                    //clear the edit text window
                    txt_msg.setText("");
                    //scroll to the last text just added
                    scrollToLast();
                }
            }
        });

        mNewMessageArrived = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // mRegistrationProgressBar.setVisibility(ProgressBar.GONE);
                //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String newMessage = "\n\n <-" + intent.getStringExtra("MESSAGE").toString();

                String prevChatHistory = getChatTextHistory();
                Log.i(TAG," Miki Recevied new message -> "+newMessage);
                Log.i(TAG," Miki History message to update -> "+prevChatHistory + newMessage);
                setChatTextHistory(prevChatHistory + newMessage);
                scrollToLast();
                //updateChatHistory();
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mNewMessageArrived,
                new IntentFilter(GCMPreferences.NEW_MESSAGE_ARRIVED));
        //here it should check if there are messages from the server.
        //and update the chatHistory accordingly
        GCMPreferences.updateChatHistory(getChatTextHistory());
        //get reciever information from the chatList activty
        reciever = getIntent().getStringExtra("RECEIVER").toString();
        //String user_email = intent.getStringExtra("USER_EMAIL").toString();

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewMessageArrived);
        super.onPause();
    }

    public String getChatTextHistory() {
        return chatTextHistory.getText().toString();
    }

    public void setChatTextHistory(String str) {
        this.chatTextHistory.setText(str);

    }
    private void scrollToLast(){
        int scrollAmount = 0;
        if(chatTextHistory.getLayout() != null)
            scrollAmount = chatTextHistory.getLayout().getLineTop(chatTextHistory.getLineCount()) - chatTextHistory.getHeight();
        // if there is no need to scroll, scrollAmount will be <=0
        if (scrollAmount > 0)
            chatTextHistory.scrollTo(0, scrollAmount);
        else
            chatTextHistory.scrollTo(0, 0);
    }

}

