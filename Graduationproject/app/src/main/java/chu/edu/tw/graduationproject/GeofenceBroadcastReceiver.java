package chu.edu.tw.graduationproject;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;
import java.util.Locale;

public class GeofenceBroadcastReceiver extends BroadcastReceiver{

    private static final String TAG = "GeofenceBroadcastReceiv";
//    SQLiteDatabase myDB;
    DBHelper db;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        Toast.makeText(context, "Geofence triggered...", Toast.LENGTH_SHORT).show();
        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if(geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for(Geofence geofence: geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }

        int transitionType = geofencingEvent.getGeofenceTransition();
        db = new DBHelper(context);

        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                if(Locale.getDefault().getLanguage() == "zh"){
                    String message = "使用者進入柵欄區域";
                    Cursor cursor = db.getdata();
                    if(cursor.moveToFirst()) {
                        String number = cursor.getString(6);
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, message, null, null);
                    }
                    notificationHelper.sendHighPriorityNotification("使用者進入柵欄區域", "", MapsActivity.class);
                    break;
                }
                else{
                    String message = "The user is entering the fenced area" ;
                    Cursor cursor = db.getdata();
                    if(cursor.moveToFirst()) {
                        String number = cursor.getString(6);
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, message, null, null);
                    }
                    notificationHelper.sendHighPriorityNotification("The user is entering the fenced area", "", MapsActivity.class);
                    break;
                }



            /*case Geofence.GEOFENCE_TRANSITION_DWELL:
                if(Locale.getDefault().getLanguage() == "zh"){
                    notificationHelper.sendHighPriorityNotification("使用者正在柵欄區域中", "", MapsActivity.class);
                }
                else{
                    notificationHelper.sendHighPriorityNotification("The user is in the fence area now", "", MapsActivity.class);
                }
                break;*/


            case Geofence.GEOFENCE_TRANSITION_EXIT:
                if(Locale.getDefault().getLanguage() == "zh"){
                    String message1 = "使用者離開柵欄區域";
                    Cursor cursor1 = db.getdata();
                    if(cursor1.moveToFirst()) {
                        String number = cursor1.getString(6);
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, message1, null, null);
                }
                    notificationHelper.sendHighPriorityNotification("使用者離開柵欄區域", "", MapsActivity.class);
                    break;
                }
                else{
                    String message1 = "The user has left the fenced area";
                    Cursor cursor1 = db.getdata();
                    if(cursor1.moveToFirst()) {
                        String number = cursor1.getString(6);
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, message1, null, null);
                    }
                    notificationHelper.sendHighPriorityNotification("The user has left the fenced area", "", MapsActivity.class);
                    break;
                }

        }
    }
}
