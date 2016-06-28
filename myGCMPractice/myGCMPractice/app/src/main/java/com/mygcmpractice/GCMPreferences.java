package com.mygcmpractice;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by Biwota on 2/6/2016.
 */
public class GCMPreferences {
    public static final String SENT_TOKEN_TO_SERVER     = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE    = "registrationComplete";
    public static final String NEW_MESSAGE_ARRIVED      = "newMessageArrived";
    public static final String REGISTRATION_URL         = "http://10.10.54.121/gcm/register.php";
    public static final String SEND_MSG_URL             = "http://10.10.54.121/gcm/send_message_to_other_device.php";
    public static final String UNREGISTER_URL           = "http://10.10.54.121/gcm/unregister.php";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    static public boolean checkPlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("PREFERENCE", "GCM-Miki This device is not supported.");
                //finish();
                activity.finish();
            }
            return false;
        }
        return true;
    }


    public static void updateChatHistory(String chatTextHistory) {
    }
}
