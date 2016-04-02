package com.dosomething.android;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.util.Log;

import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.google.android.gcm.GCMBaseIntentService;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GCMIntentService extends GCMBaseIntentService {
    private final int NOTIFCATION_ID = 0;
    private String conversationid = "";
    private String push_type = "";
    SharedPrefrences sharedPrefrences = new SharedPrefrences();
    private String senderId = "";

    public GCMIntentService() {
        super();
    }

    public GCMIntentService(String... senderIds) {
        super(senderIds);
    }

    @Override
    protected void onError(Context arg0, String arg1) {
        Log.v("GCMIntentService", "Error :" + arg1);
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        //check whether the user and password can still be used
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.home_button);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        try {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(R.drawable.home_button);


//			http://indiawebcoders.com/mobileapps/dosomething/uploads/profile/noimage.png

            mBuilder.setLargeIcon(largeIcon);
            mBuilder.setColor(getResources().getColor(R.color.notification_icon_background));
            mBuilder.setContentTitle(getString(R.string.app_name));
            mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;

            String setting_sound;
            if (intent.hasExtra("setting_sound")) {

                setting_sound = intent.getExtras().getString("setting_sound");
                sharedPrefrences.setSetting_Sound(context, setting_sound);

            } else {
                setting_sound = "1";
            }


            if(sharedPrefrences.getSetting_Sound(context).equals("1"))
            {
                mBuilder.setSound(uri);
            }
            mBuilder.setAutoCancel(true);

            Log.d("RESPONCE", intent.getExtras().toString());
            String message = "";
            try {
                message = intent.getExtras().getString("message");
                if (intent.hasExtra("push_type")) {

                    String type = intent.getExtras().getString("push_type");

                    assert type != null;
                    if (type.equals("chat")) {
                        sharedPrefrences.setPushType(context,"chat");
                        assert message != null;
                        String[] parts = message.split(" ");
                        String part = parts[4];
                        sharedPrefrences.setFriendFirstname(context, part);
                    } else if (type.equals("sendrequest")) {
                        sharedPrefrences.setPushType(context,"sendrequest");
                        assert message != null;
                        String[] parts = message.split(" ");
                        String part = parts[6];
                        sharedPrefrences.setFriendFirstname(context, part);

                    }

                }



                if (intent.hasExtra("conversationid")) {

                    conversationid = intent.getExtras().getString("conversationid");
                    sharedPrefrences.setConversationId(context, conversationid);

                } else {
                    conversationid = "";
                }

                if (intent.hasExtra("senderId")) {
                    senderId = intent.getExtras().getString("senderId");

                    sharedPrefrences.setFriendUserId(context, senderId);
                } else {
                    senderId = "";
                }


                assert message != null;
                if (message.length() == 0)
                    message = "";
            } catch (Exception e) {
                e.printStackTrace();
            }


            System.out.println("====" + intent.getExtras().getString("message"));
            BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
            bigStyle.bigText(message);
            mBuilder.setContentText(message);
            mBuilder.setStyle(bigStyle);

			/*int itemId = mRecyclerView.getChildPosition(view);
            Intent intentDetail = new Intent(context, ArticleDetailsActivity.class);
	        intentDetail.putExtra("id", itemId);    					
	        PendingIntent notificationIntent = PendingIntent.getActivity(this, 0,
	        		intentDetail,
					PendingIntent.FLAG_UPDATE_CURRENT);*/
            Intent newsIntent = null;
            if (conversationid != null && conversationid.length() > 0) {
                newsIntent = new Intent(context, DoSomethingStatus.class);
                newsIntent.putExtra("GCM_chat", true);
                newsIntent.putExtra("conversationid", conversationid);
                newsIntent.putExtra("senderId", senderId);
                newsIntent.putExtra("push_type", push_type);


            } else {
                newsIntent = new Intent(context, SplashActivity.class);
            }
            PendingIntent notificationIntent = PendingIntent.getActivity(this, 0,
                    newsIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(notificationIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTIFCATION_ID, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onRegistered(final Context context, final String regId) {


        sharedPrefrences.setDeviceToken(context, regId);

        Thread thread = new Thread(new Runnable() {
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                String result = "";
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    String URL = "http://indiawebcoders.com/mobileapps/dosomething/push/android.php?" + "device_id" + "=" + regId;
                    String devName = Build.SERIAL;
                    if (devName == null || devName.length() == 0)
                        devName = "" + System.currentTimeMillis();
                    Log.i("TAG", "android.os.Build.SERIAL: " + devName);
                    Log.i("TAG", "android.os.Build.SERIAL: " + regId);

                    JSONObject data = new JSONObject();
                    data.put("device_id", regId);

                    Log.d("api_registerForPushAndroid", "" + URL + "data" + data);
                    HttpPost postRequest = new HttpPost(URL);

                    StringEntity se = new StringEntity(data.toString());
                    se.setContentType("application/json;charset=UTF-8");
                    se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));

                    postRequest.setEntity(se);
                    HttpResponse response;

                    response = httpClient.execute(postRequest);
                    Log.d("getStatusCode", "" + response.getStatusLine().getStatusCode());
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
                    Log.d("result", result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }

    @Override
    protected void onUnregistered(Context arg0, String arg1) {
        Log.v("onUNRegistered", "");
    }

}

