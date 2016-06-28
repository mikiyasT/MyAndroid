package com.mygcmpractice;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Biwota on 2/16/2016.
 */
public class ApplicationServer extends Application {

    private JSONArray chatHistory = null;
    private String json;
    private static final String JSON_ARRAY ="result";
    private static final String KEY_SENDER = "sender";
    private static final String KEY_RECEIVER = "receiver";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ID = "id";

    private static String[] senders;
    private static String[] receivers;
    private static String[] messages;
    private static String[] ids;






    ApplicationServer(String json){
        this.json = json;
    }
    ApplicationServer()
    {

    }

    protected void sendRegistrationToServer(final Context context, final String token, final String name, final String email)
    {
        // Add custom implementation, as needed.
        StringRequest postRequest = new StringRequest(Request.Method.POST, GCMPreferences.REGISTRATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //PD.dismiss();
                        // item_et.setText("");
                        Toast.makeText(context,
                                "Data Inserted Successfully",
                                Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //PD.dismiss();
                Toast.makeText(context,
                        "failed to insert " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("DB insertion error", "-> " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token",token);
                params.put("name", name);
                params.put("email", email);

                return params;
            }
        };

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(postRequest);
        Log.i("GCM-Miki SENDING TO DB", "REGISTRATION COMPLETE");
        Log.i("NAME"," " + name);
        Log.i("EMAIL"," " + email);
        Log.i("TOKEN", " " + token);
        //-----------------------------------------
    }

    protected void sendMessageToDevice(final Context context,final String msg, final String sender,final String receiver)
    {
        // Add custom implementation, as needed.
        StringRequest postRequest = new StringRequest(Request.Method.POST, GCMPreferences.SEND_MSG_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //PD.dismiss();
                        // item_et.setText("");
                        Toast.makeText(context,
                                "Message sent Successfully",
                                Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //PD.dismiss();
                Toast.makeText(context,
                        "failed to send " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Msg sending error", "-> " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sender",sender);
                params.put("receiver",receiver);
                params.put("message",msg);
                return params;
            }
        };

        // Adding request to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(postRequest);
        Log.i("GCM-Miki SENDING TO DB", "REGISTRATION COMPLETE");
        Log.i("MESSAGE"," " + msg);
        Log.i("SENDER"," " + sender);
        Log.i("RECIEVER"," " + receiver);
        //Log.i("TOKEN", " " + token);
        //-----------------------------------------
    }
    protected void unRegisterFromServer(String token) {
        // Add custom implementation, as needed.
    }
    protected void getNewMessageFromServer(){
        //send http request to get new messages from the server to this device user.
        //
    }

   // this is to parse chat history message.
    // then will be displayed for the user (message history with that receiver)
    protected void parseJsonMessage(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            chatHistory = jsonObject.getJSONArray(JSON_ARRAY);
            senders = new String[chatHistory.length()];
            receivers = new String[chatHistory.length()];
            messages = new String[chatHistory.length()];
            ids = new String[chatHistory.length()];

            for(int i = 0; i < chatHistory.length(); i++){
                JSONObject  jo = chatHistory.getJSONObject(i);
                senders[i] = jo.getString(KEY_SENDER);
                receivers[i] = jo.getString(KEY_RECEIVER);
                messages[i] = jo.getString(KEY_MESSAGE);
                ids[i] = jo.getString(KEY_ID);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
