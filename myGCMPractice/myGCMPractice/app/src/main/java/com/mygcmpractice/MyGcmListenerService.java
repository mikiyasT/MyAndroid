package com.mygcmpractice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Biwota on 2/8/2016.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    private String sender ;
    private String body ;

    @Override
    public void onCreate(){

    }
    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        Log.i(TAG, "From: " + from);
        Log.i(TAG, "Message: " + message);

        if (message.startsWith("Device")) {
            // message received from app server for the first time after registration.
            sender = "SABiSA";
            body = message;
        } else if(message.startsWith("#")){
            // normal downstream message from another device to this device.
            //Sender id enclosed in #mykey#, the rest is the message
            String fromExpresion = "";
            int lastIndexofSep = message.lastIndexOf("#");
            sender = message.substring(0,lastIndexofSep);
            body = message.substring(lastIndexofSep);
            //store the message history into the database
            //from , to(this app user id from sharedPref),message, status(unread)
            // Notify UI that registration has completed, so the progress indicator can be hidden.
            Intent newMessageArrived = new Intent(GCMPreferences.NEW_MESSAGE_ARRIVED);
            newMessageArrived.putExtra("MESSAGE",message);
            Log.i(TAG, "Miki Listener sending message: " + message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(newMessageArrived);

        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI. // add the newly received message to the chat history
         */


        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(message);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle(sender)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
