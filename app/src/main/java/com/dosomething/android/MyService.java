package com.dosomething.android;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


public class MyService extends Service implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    private ServiceHandler serviceHandler;
    private static final long INTERVAL = 1000 * 60;
    private static final long FASTEST_INTERVAL = 1000 * 60;
    LocationRequest LocationRequest;
    GoogleApiClient googleApiClient;
    Location currentLocation;
    String lat, lng, currTime;
    private static final int notif_id = 1;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Boolean isConnectionExist = false;
    LocationManager locationManager = null;
    WIFIInternetConnectionDetector cd;
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGTITUDE = "longitude";
    private static final String TAG_SESSIONID = "sessionid";
    private static final String TAG_DEVICETOKEN = "device_token";
    private static final String TAG_PUSHTYPE = "push_type";
    private static final String TAG_STATUS = "status";
    String sessionid, status, json_string_updateposition;
    String latitude;
    String longitude;
    Jsonfunctions jsonfunctions;
    JSONObject json_object_updateposition, json_content_updateposition;
    SharedPrefrences sharedPrefrences;
    Context context = this;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            message();

        }
    }


    public void message() {
        Log.d("service", "////////////" + "SERVICE");
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        } else {
            location_update();

            if (isNetworkAvailable()) {
                sessionid = sharedPrefrences.getSessionid(context);
                latitude = sharedPrefrences.setLatitude(context, lat);
                longitude = sharedPrefrences.setLongtitude(context, lng);
                if (!sharedPrefrences.getSessionid(context).equals("") && !sharedPrefrences.getLatitude(context).equals("") && !sharedPrefrences.getLongitude(context).equals("")) {
                    new UpdatePosition().execute();

                }


            } else {
                Log.d("NOInternet", "nnnnn" + "--------NOT WORKING");
            }

            Log.d("MMmmmm", "nnnnn" + "--------WORKING");

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                message();
            }
        }, 1000 * 60);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isForeground(context, getPackageName());
            }
        }, 1000 * 30);

        cd = new WIFIInternetConnectionDetector();
        isConnectionExist = WIFIInternetConnectionDetector.isConnected(context);
        if (!isConnectionExist) {

            Log.d("-1-", "-*-" + lat);
            Log.d("-2-", "-*-" + lng);
            Log.d("-3-", "-*-" + currTime);
        } else {
            Log.d("--", "-*-" + "WIFI----");
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onCreate() {
        context = this;
        HandlerThread thread = new HandlerThread("ServiceStartArguments", 1);
        thread.start();
        Looper serviceLooper = thread.getLooper();
        sharedPrefrences = new SharedPrefrences();

        serviceHandler = new ServiceHandler(serviceLooper);
        if (!isGooglePlayServicesAvailable()) {
            //finish();
        }
        createLocationRequest();
        jsonfunctions = new Jsonfunctions(this);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        googleApiClient.connect();


        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        if (sharedPrefrences.getSetting_Sound(context).equals("1")) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        } else if (sharedPrefrences.getSetting_Sound(context).equals("0")) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        }

       /* if(sharedPrefrences.getNotifySound(context).equals("Yes"))
        {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

        }else
        {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }*/

    }


    private void createLocationRequest() {

        LocationRequest = new LocationRequest();
        LocationRequest.setInterval(INTERVAL);
        LocationRequest.setFastestInterval(FASTEST_INTERVAL);
        LocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SERVICE-ONCOMMAND", "onStartCommand");
        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        location_update();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    ////////////////////////////to check google play service//////////////////////

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        return ConnectionResult.SUCCESS == status;
    }

    ///////////////////fused Api///////////////////////////////

    private void startLocationUpdates() {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, LocationRequest, this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (mLastLocation != null) {

            sharedPrefrences.setLatitude(context, String.valueOf(mLastLocation.getLatitude()));

            sharedPrefrences.setLongtitude(context, String.valueOf(mLastLocation.getLongitude()));


        }

    }

    //////////////////////////////LOCATION UPDATE////////////////

    private void location_update() {
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (currentLocation != null) {
            sessionid = sharedPrefrences.getSessionid(context);

            lat = String.valueOf(currentLocation.getLatitude()).trim();
            lng = String.valueOf(currentLocation.getLongitude()).trim();
            if (!lat.equals("")) {
                sharedPrefrences.setLatitude(context, lat);
            }
            if (!lng.equals("")) {
                sharedPrefrences.setLongtitude(context, lng);
            }


            currTime = dateFormat.format(currentLocation.getTime());

            Log.d("latitude............", sessionid + sharedPrefrences.getLatitude(context) + sharedPrefrences.getLongitude(context));
            updateNotification();

        } else {

            Log.d("TRACK", "location.... is null");
        }

    }


    ////////////////////////////// NOTIFICATION//////////////////

    private void updateNotification() {
        Log.d("LLLLLLLLLLLLL", "LLLLLL" + "TRRRRRRRRR");
        String text = "LAT: " + lat + "\nLONG: " + lng + "\nTIME: " + currTime;

//        Notification notification = getMyActivityNotification(text);
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(notif_id, notification);
    }

    private Notification getMyActivityNotification(String text) {
        // The PendingIntent to launch our activity if the user selects
        // this notification
        CharSequence title = getText(R.string.notification_text);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SplashActivity.class), 0);
        return new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.menu_icon)
                .setContentIntent(contentIntent).getNotification();
    }

    /////////////////////////service to run in background ///////////////

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    ////////////////////////to check wifi////////////////////

    private static class WIFIInternetConnectionDetector {
        public static int TYPE_WIFI = 1;
        public static int TYPE_MOBILE = 2;
        public static int TYPE_NOT_CONNECTED = 0;


        /***
         * Check the Internet connectivity
         ***/
        public static boolean isConnected(Context context) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo[] info = cm.getAllNetworkInfo();
                if (info != null)
                    for (NetworkInfo anInfo : info)
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }

            assert cm != null;
            NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetwork != null && wifiNetwork.isConnected()) {
                return true;
            }

            NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobileNetwork != null && mobileNetwork.isConnected()) {
                return true;
            }

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();

        }

        public String getWifiName(Context context) {
            String ssid = "none";
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState()) == NetworkInfo.DetailedState.CONNECTED) {
                ssid = wifiInfo.getSSID();
            }
            return ssid;
        }

        public static int getConnectivityStatus(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
            return TYPE_NOT_CONNECTED;
        }

        public static String getConnectivityStatusString(Context context) {
            int conn = WIFIInternetConnectionDetector.getConnectivityStatus(context);
            String status = null;
            if (conn == WIFIInternetConnectionDetector.TYPE_WIFI) {
                status = "Wifi enabled";
            } else if (conn == WIFIInternetConnectionDetector.TYPE_MOBILE) {
                status = "Mobile data enabled";
            } else if (conn == WIFIInternetConnectionDetector.TYPE_NOT_CONNECTED) {
                status = "Not connected to Internet";
            }
            return status;
        }
    }


    //    @Override
//    public boolean stopService(Intent name) {
//        serviceHandler.removeCallbacksAndMessages(null);
//        return super.stopService(name);
//    }
    private class UpdatePosition extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HashMap<String, Object> paramsupdateposition = new HashMap<>();
            paramsupdateposition.put(TAG_SESSIONID, sessionid);
            paramsupdateposition.put(TAG_LATITUDE, sharedPrefrences.getLatitude(context));
            paramsupdateposition.put(TAG_LONGTITUDE, sharedPrefrences.getLongitude(context));
            paramsupdateposition.put(TAG_DEVICETOKEN, sharedPrefrences.getDeviceToken(context));
            json_string_updateposition = jsonfunctions.postToURL(getResources().getString(R.string.dosomething_apilink_string_updatelocation), paramsupdateposition);
            Log.v("jason update=======>", String.valueOf(paramsupdateposition));

            try {
                json_object_updateposition = new JSONObject(json_string_updateposition);
                json_content_updateposition = json_object_updateposition.getJSONObject("updatelocation");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            try {
                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                    try {
                        if (json_object_updateposition.has("updatelocation")) {
                            if (json_content_updateposition.getString("status").equalsIgnoreCase("success")) {

                            } else if (json_content_updateposition.getString("status").equalsIgnoreCase("InvalidSession")) {


                            }
                        } else if (json_object_updateposition.getString("error").equalsIgnoreCase("InvalidSession")) {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private class StatusUpdate extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HashMap<String, Object> paramsupdateposition = new HashMap<>();
            paramsupdateposition.put(TAG_SESSIONID, sessionid);
            paramsupdateposition.put(TAG_STATUS, status);

            json_string_updateposition = jsonfunctions.postToURL(getResources().getString(R.string.dosomething_apilink_string_onlinestatus), paramsupdateposition);
            Log.v("jason update=======>", String.valueOf(paramsupdateposition));

            try {
                json_object_updateposition = new JSONObject(json_string_updateposition);
                json_content_updateposition = json_object_updateposition.getJSONObject("onlinestatus");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog

            try {
                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                    try {
                        if (json_object_updateposition.has("onlinestatus")) {

                            if (json_content_updateposition.getString("status").equalsIgnoreCase("success")) {

                            } else if (json_content_updateposition.getString("status").equalsIgnoreCase("InvalidSession")) {


                            }
                        } else if (json_object_updateposition.getString("error").equalsIgnoreCase("InvalidSession")) {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public boolean isForeground(Context ctx, String myPackage) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);

        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        if (componentInfo.getPackageName().equals(myPackage)) {


            if (!sharedPrefrences.getSessionid(context).equals("")) {
                status = "1";
                new StatusUpdate().execute();
            }

            Log.d("trueeeeeeeeeeeeee", "launch");

            return true;
        }

       /* if (!sharedPrefrences.getSessionid(context).equals("")) {
            status = "0";
            new StatusUpdate().execute();
        }*/

        Log.d("falsee", "launch");
        return false;
    }


}













