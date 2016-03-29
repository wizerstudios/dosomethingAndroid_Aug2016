package com.dosomething.android;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.auth.AccountHandle;
import com.androidquery.callback.AjaxStatus;
import com.dosomething.android.Beanclasses.Filterbean;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.RangeSeekBar;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.SlidingMenu;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.dosomething.android.Database.DBAdapter;
import com.dosomething.android.Fragments.DoSomethingChatBox;
import com.dosomething.android.Fragments.DoSomethingChatList;
import com.dosomething.android.Fragments.DoSomethingNearMe;
import com.dosomething.android.Fragments.FragmentProfile;
import com.dosomething.android.Fragments.FragmentSettings;
import com.dosomething.android.Fragments.FragmentStatus;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

public class DoSomethingStatus extends AppCompatActivity {
    private static final int RESULT_LOAD_IMG = 1;
    private static final String TAG_PROFILEUSERID = "profile_user_id";
    private static final String TAG_TIME = "datetime";
    LinearLayout status_layout_pin;
    LinearLayout status_layout_menu;
    LinearLayout status_layout_chat;
    LinearLayout status_layout_profile;
    LinearLayout status_layout_settings;
    private static final String TAG_DATEOFBIRTH = "dob";
    private static final String TAG_PROFILEIMAGE1 = "profileImage1";
    private static final String TAG_PROFILEIMAGE2 = "profileImage2";
    private static final String TAG_PROFILEIMAGE3 = "profileImage3";
    private static final String TAG_LONGTITUDE = "longitude";
    private static final String TAG_NOTIFICATIONMESSAGE = "notification_message";
    private static final String TAG_NOTIFICATIONSOUND = "notification_sound";
    private static final String TAG_NOTIFICATIONVIBRATION = "notification_vibration";
    private static final String TAG_NOTIFICATIONMATCH = "do_match";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_PROFILEID = "profileId";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_ABOUT = "about";
    private static final String TAG_HOBBIES = "hobbies";
    ImageView status_ImageView_pin;
    ImageView status_ImageView_chat;
    ImageView status_ImageView_profile;
    ImageView status_ImageView_setting;
    TextView activity_dosomething_textview_toolbar_search;
    TextView activity_dosomething_textview_toolbar_save;
    StringBuilder dosomething_activity_hobbies_id_null = null;
    FrameLayout detail_fragment;
    boolean slide_search = false;
    boolean click_action12 = false;
    boolean click_action13 = false;
    boolean click_action14 = false;
    boolean click_action15 = false;
    SlidingMenu menu;
    Context context;
    int check = 0;
    private static String url = "http://wiztestinghost.com/dosomething/nearestusers?";
    private static String urlupdate = "http://wiztestinghost.com/dosomething/updateprofile?";
    private static final String TAG_SESSIONID = "sessionid";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";
    private static final String TAG_FILTER_STATUS = "filter_status";
    private static final String TAG_FILTER_GENDER = "filter_gender";
    private static final String TAG_FILTER_AGERANGE = "filter_agerange";
    private static final String TAG_FILTER_DISTANCE = "filter_distance";
    String filter_status, filter_available, filter_gender, filter_agerange, filter_distance;
    String latitude;
    String longitude;
    Jsonfunctions jsonfunctions;
    JSONObject json_object, json_content, details;
    private ProgressDialog pDialog;
    SharedPrefrences sharedPreferences;
    private String pin_state, chat_state;
    private ArrayList<Integer> bundle_list;
    private FragmentProfile loginfrg;
    private TransparentProgressDialog pd;

    Handler handler = new Handler();
    Timer timer_chat = new Timer();
    private AnimationDrawable splashAnimation;
    private ImageView kbv;
    RelativeLayout relativelayoutupdate_profile_progress;
    String firstname, lastname, sessionid, type, email, password, profileId, dob, profileImage1, profileImage2, profileImage3, gender, about, hobbies, device, sessionId, json_string, json_string_updateposition, notification_message, notification_sound, notification_vibration, deviceid, response;
    private AQuery aQuery;
    private AccountHandle handle = null;
    RelativeLayout relativelayout_alertdialog_save;
    TextView status_textview_save, alert_textview_save, alert_textview_save_cancel;
    RadioButton filterby_status_radiobutton_online;
    RadioButton filterby_status_radiobutton_offline;
    RadioButton filterby_avlaible_radiobutton_yes;
    RadioButton filterby_avlaible_radiobutton_no;
    RadioButton filterby_gender_radiobutton_male;
    RadioButton filterby_gender_radiobutton_female;
    RadioButton filterby_status_radiobutton_both;
    RadioButton filterby_avlaible_radiobutton_both;
    RadioButton filterby_gender_radiobutton_both;
    private Inflater inflater;
    private Date oneWayTripDate;
    private String tripDate;
    ImageView activity_dosomething_imageview_filter_icon;
    ImageView activity_dosomething_imageview_filter_right;
    private TextView slidingmenu_clear_all_filter_textvalue;
    private static String urlImageStatus = "http://wiztestinghost.com/dosomething/gethobbies?";
    private DBAdapter dbAdapter;
    private Bundle hobbiesBundle;
    private DoSomethingNearMe somethingNearMe = null;
    private RelativeLayout realative_alertdailog_dosomething_password_match;
    TextView password_no_match;
    private TextView alert_textview_password_nomatch_cancel;
    String dosomething_save_click_action;
    private String match_chat_state;
    private FragmentStatus fragmentStatus;
    TextView dosomething_status_unread_meassge_count;
    private String datetime;
    LinearLayout dosomething_status_main_layout;
    private Dialog dialog;
    private Dialog dialog_savechanges;
    private String profile_update_response = "";

    // chat from notification
    boolean mChat_repalce = false;
    String conversationid, sendrequest, Fragment_trasaction = "";


    /*Range Bar section*/
    RangeSeekBar rangebar_age, rangebar_distance;
    LinearLayout linearlayout_age, linearlayout_distance;
    private String profile_user_id;

    private RelativeLayout layout_walkthrough_profile;
    private ImageView image_walkthrough_account_profile;
    private String name="NearBy Screen";
    private Tracker mTracker;
    private Timer timer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_something_status);
        try
        {
            MyApplication application = (MyApplication) getApplication();
            mTracker = application.getDefaultTracker();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
//        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        Window window = getWindow();
        context = this;
// clear FLAG_TRANSLUCENT_STATUS flag:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        pd = new TransparentProgressDialog(context, getResources().getDrawable(R.drawable.loading));
        relativelayoutupdate_profile_progress=(RelativeLayout)findViewById(R.id.relativelayoutupdate_profile_progress);
        kbv = (ImageView) findViewById(R.id.update_profile_progress);
        kbv.setBackgroundResource(R.drawable.progress_drawable);

        splashAnimation = (AnimationDrawable) kbv.getBackground();
        sharedPreferences = new SharedPrefrences();
        aQuery = new AQuery(context);
        dbAdapter = new DBAdapter(context);
        jsonfunctions = new Jsonfunctions(context);
        sessionid = sharedPreferences.getSessionid(context);
        latitude = sharedPreferences.getLatitude(context);
        longitude = sharedPreferences.getLongitude(context);
        bundle_list = new ArrayList<>();
        if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
            if (bundle_list.isEmpty()) {

                new AsynProfileClass().execute();

            }


        }


        if (!sharedPreferences.getSessionid(context).equals("")) {
            timer_chat.schedule(new AutoChatHistory(), 0, 5000);
        }


        try {
            ActionBar mActionBar = getSupportActionBar();
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));
            mActionBar.setDisplayShowHomeEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.toolbar, null);
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            activity_dosomething_imageview_filter_icon = (ImageView) mCustomView.findViewById(R.id.activity_dosomething_imageview_filter_icon);
            activity_dosomething_imageview_filter_right = (ImageView) mCustomView.findViewById(R.id.activity_dosomething_imageview_filter_right);
            activity_dosomething_textview_toolbar_search = (TextView) mCustomView.findViewById(R.id.activity_dosomething_textview_toolbar_search);
            activity_dosomething_textview_toolbar_save = (TextView) mCustomView.findViewById(R.id.activity_dosomething_textview_toolbar_save);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            activity_dosomething_textview_toolbar_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (!dosomething_save_click_action.equals("clickfalse")) {
                            String date = sharedPreferences.getDateOfbirth(context);
//                    String date ="29/07/13";
                            SimpleDateFormat input = new SimpleDateFormat("dd / MM / yyyy");
                            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");

                            try {
                                Log.d("tripDate", date);
                                oneWayTripDate = input.parse(date);                 // parse input
                                tripDate = output.format(oneWayTripDate);// format output
                                Log.d("tripDate", tripDate);
                                dob = tripDate;
                                sharedPreferences.setDateofBirth(context, tripDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                                updateprofile();
                                sharedPreferences.setBooleam(context, "false");
                                sharedPreferences.setHobbies(context, "");
                            } else {
                                NetworkCheck.alertdialog(context);

                            }


                        } else {
                            password_no_match.setText("Password No match");
                            alert_textview_password_nomatch_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    realative_alertdailog_dosomething_password_match.setVisibility(View.GONE);
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        menu = new SlidingMenu(DoSomethingStatus.this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.SLIDING_WINDOW);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.setSlidingEnabled(false);
        menu.attachToActivity(DoSomethingStatus.this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.activity_sliding_menu_filter);
        slidingmenu_clear_all_filter_textvalue = (TextView) findViewById(R.id.slidingmenu_clear_all_filter_textvalue);
        filterby_status_radiobutton_online = (RadioButton) findViewById(R.id.filterby_status_radiobutton_online);
        filterby_status_radiobutton_offline = (RadioButton) findViewById(R.id.filterby_status_radiobutton_offline);
        filterby_avlaible_radiobutton_yes = (RadioButton) findViewById(R.id.filterby_avlaible_radiobutton_yes);
        filterby_avlaible_radiobutton_no = (RadioButton) findViewById(R.id.filterby_avlaible_radiobutton_no);
        filterby_gender_radiobutton_male = (RadioButton) findViewById(R.id.filterby_gender_radiobutton_male);
        filterby_gender_radiobutton_female = (RadioButton) findViewById(R.id.filterby_gender_radiobutton_female);
        filterby_avlaible_radiobutton_both = (RadioButton) findViewById(R.id.filterby_avlaible_radiobutton_both);
        filterby_status_radiobutton_both = (RadioButton) findViewById(R.id.filterby_status_radiobutton_both);
        filterby_gender_radiobutton_both = (RadioButton) findViewById(R.id.filterby_gender_radiobutton_both);


        // Add to layout
        linearlayout_age = (LinearLayout) findViewById(R.id.linearlayout_age);
        linearlayout_distance = (LinearLayout) findViewById(R.id.linearlayout_distance);

        rangebar_age = new RangeSeekBar<Integer>(this);
        rangebar_distance = new RangeSeekBar<Integer>(this);
        // Set the range
        rangebar_age.setRangeValues(18, 80);
        rangebar_age.setSelectedMinValue(18);
        rangebar_age.setSelectedMaxValue(80);
        // Set the range
        rangebar_distance.setRangeValues(0, 100);
        rangebar_distance.setSelectedMinValue(0);
        rangebar_distance.setSelectedMaxValue(100);

        // Add to layout
        linearlayout_age.addView(rangebar_age);
        linearlayout_distance.addView(rangebar_distance);

//        rangebar_age = (RangeBar) findViewById(R.id.rangebar_age);
//        rangebar_distance = (RangeBar) findViewById(R.id.rangebar_distance);


        slidingmenu_clear_all_filter_textvalue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("slidingmenu_clear_all", "clicked");
//                rangebar_age.setTickStart(18);
//                rangebar_age.setTickEnd(80);
//                rangebar_distance.setTickStart(0);
//                rangebar_distance.setTickEnd(50);
//                Log.d("rangebar_age", "age" + rangebar_age.getTickStart());
//                Log.d("rangebar_age", "age" + rangebar_age.getTickEnd());
//                Log.d("rangebar_distance", "age" + rangebar_distance.getTickStart());
//                Log.d("rangebar_distance","age"+rangebar_distance.getTickEnd());

                rangebar_age.setSelectedMinValue(18);
                rangebar_age.setSelectedMaxValue(80);

                rangebar_distance.setSelectedMinValue(0);
                rangebar_distance.setSelectedMaxValue(100);

                filterby_status_radiobutton_both.setChecked(true);
                filterby_avlaible_radiobutton_both.setChecked(true);
                filterby_gender_radiobutton_both.setChecked(true);
                filterby_gender_radiobutton_male.setChecked(false);
                filterby_gender_radiobutton_female.setChecked(false);
                filterby_avlaible_radiobutton_no.setChecked(false);
                filterby_avlaible_radiobutton_yes.setChecked(false);
                filterby_status_radiobutton_online.setChecked(false);
                filterby_status_radiobutton_offline.setChecked(false);

                sharedPreferences.setFilterGender(context, "");
                sharedPreferences.setFilterAvailable(context, "");
                sharedPreferences.setFilterStatus(context, "");
                sharedPreferences.setFilterDistance(context, "");
                sharedPreferences.setFilterAge(context, "");

            }
        });


        activity_dosomething_imageview_filter_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (click_action12) {
                    if (slide_search) {
                        status_ImageView_setting.setClickable(true);
                        activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                        activity_dosomething_imageview_filter_icon.setVisibility(View.VISIBLE);
                        activity_dosomething_imageview_filter_right.setVisibility(View.GONE);
                        menu.toggle();
                        slide_search = false;
                    } else {
                        status_ImageView_setting.setClickable(false);
                        activity_dosomething_textview_toolbar_search.setVisibility(View.VISIBLE);
                        activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
                        activity_dosomething_imageview_filter_right.setVisibility(View.VISIBLE);
                        menu.toggle();
                        slide_search = true;
                    }
                }
            }
        });
        activity_dosomething_imageview_filter_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status_ImageView_setting.setClickable(true);

                activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                activity_dosomething_imageview_filter_icon.setVisibility(View.VISIBLE);
                activity_dosomething_imageview_filter_right.setVisibility(View.GONE);
                menu.toggle();
                slide_search = false;

            }


        });
        activity_dosomething_textview_toolbar_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTracker.send(new HitBuilders.EventBuilder()
                            .setCategory("Applying filter")
                            .setAction("User list")
                            .build());
                    ((MyApplication) getApplication()).getListFilterBeans().clear();
                    latitude = sharedPreferences.getLatitude(context);
                    longitude = sharedPreferences.getLongitude(context);
                    sessionid = sharedPreferences.getSessionid(context);
                    Log.d("EEEEEEE", "RRRR" + latitude + "--" + longitude + "--" + sessionid);
                    if (filterby_status_radiobutton_online.isChecked()) {
                        filter_status = "1";
                        sharedPreferences.setFilterStatus(context, filter_status);
                    } else if (filterby_status_radiobutton_offline.isChecked()) {
                        filter_status = "0";
                        sharedPreferences.setFilterStatus(context, filter_status);

                    } else {
                        filter_status = "";
                        sharedPreferences.setFilterStatus(context, filter_status);

                    }
                    if (filterby_avlaible_radiobutton_yes.isChecked()) {
                        filter_available = "Yes";
                        sharedPreferences.setFilterAvailable(context, filter_available);

                    } else if (filterby_avlaible_radiobutton_no.isChecked()) {
                        filter_available = "No";
                        sharedPreferences.setFilterAvailable(context, filter_available);

                    } else {
                        filter_available = "";
                        sharedPreferences.setFilterAvailable(context, filter_available);

                    }
                    if (filterby_gender_radiobutton_male.isChecked()) {
                        filter_gender = "male";
                        sharedPreferences.setFilterGender(context, filter_gender);

                    } else if (filterby_gender_radiobutton_female.isChecked()) {
                        filter_gender = "female";
                        sharedPreferences.setFilterGender(context, filter_gender);

                    } else {
                        filter_gender = "";
                        sharedPreferences.setFilterGender(context, filter_gender);
                    }


                    rangebar_age.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                        @Override
                        public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                            filter_agerange = minValue + "-" + maxValue;
                            sharedPreferences.setFilterAge(context, filter_agerange);
                            Log.d("filter_age", "" + minValue);
                            Log.d("filter_age", "" + maxValue);
                        }
                    });

                    rangebar_distance.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
                        @Override
                        public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                            filter_distance = minValue + "-" + maxValue;
                            sharedPreferences.setFilterDistance(context, filter_distance);
                            Log.d("filter_distance", "" + minValue);
                            Log.d("filter_distance", "" + maxValue);
                        }
                    });


                    settext("YES");
                    menu.toggle();
                    activity_dosomething_imageview_filter_icon.setVisibility(View.VISIBLE);
                    activity_dosomething_textview_toolbar_save.setVisibility(View.GONE);
                    status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin_active));
                    status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                    status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                    status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                    DoSomethingNearMe somethingNearMe = new DoSomethingNearMe();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.detail_fragment, somethingNearMe);
                    fragmentTransaction.commit();
                    click_action15 = false;
                    click_action14 = false;
                    click_action12 = true;
                    click_action13 = false;
                    status_ImageView_setting.setClickable(true);
                    activity_dosomething_imageview_filter_right.setVisibility(View.GONE);
                    activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                    menu.toggle();
                    slide_search = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        ((MyApplication) getApplication()).setDoSomethingStatus(this);
        pin_state = "YES";
        match_chat_state = "match_chat";
        chat_state = "YES";


        ///////////////////calling Tyface method/////////////////////////////////////

        hobbiesBundle = getIntent().getExtras();
        if (bundle_list.isEmpty()) {
            if (hobbiesBundle != null) {
                if (hobbiesBundle.containsKey("array_bundle_hobbies_image")) {
                    hobbiesBundle.getIntegerArrayList("array_bundle_hobbies_image");

                    bundle_list.clear();
                    bundle_list.addAll(hobbiesBundle.getIntegerArrayList("array_bundle_hobbies_image"));


                    Log.d("DOSOMETHING_hobbies_id", "hobbiesBundle" + hobbiesBundle.getIntegerArrayList("array_bundle_hobbies_image"));
                }

            }
        }


        ///////////////Click Function////////////////////////////////
        relativelayout_alertdialog_save = (RelativeLayout) findViewById(R.id.relativelayout_alertdialog_save);
        realative_alertdailog_dosomething_password_match = (RelativeLayout) findViewById(R.id.realative_alertdailog_dosomething_password_match);
        status_layout_pin = (LinearLayout) findViewById(R.id.status_layout_pin);
        status_layout_chat = (LinearLayout) findViewById(R.id.status_layout_chat);
        status_ImageView_pin = (ImageView) findViewById(R.id.status_ImageView_pin);
        status_ImageView_chat = (ImageView) findViewById(R.id.status_ImageView_chat);
        status_ImageView_profile = (ImageView) findViewById(R.id.status_ImageView_profile);
        status_ImageView_setting = (ImageView) findViewById(R.id.status_ImageView_setting);
        status_layout_menu = (LinearLayout) findViewById(R.id.status_layout_menu);
        status_layout_settings = (LinearLayout) findViewById(R.id.status_layout_settings);
        status_layout_profile = (LinearLayout) findViewById(R.id.status_layout_profile);
        detail_fragment = (FrameLayout) findViewById(R.id.detail_fragment);
        dialog = new Dialog(DoSomethingStatus.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dosomething_password_match);
        password_no_match = (TextView) dialog.findViewById(R.id.password_no_match);
        alert_textview_password_nomatch_cancel = (TextView) dialog.findViewById(R.id.alert_textview_password_nomatch_cancel);
        dialog_savechanges = new Dialog(DoSomethingStatus.this);
        dialog_savechanges.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_savechanges.setContentView(R.layout.dosomething_alert_save_changes);
        status_textview_save = (TextView) dialog_savechanges.findViewById(R.id.status_textview_save);
        alert_textview_save = (TextView) dialog_savechanges.findViewById(R.id.alert_textview_save);
        alert_textview_save_cancel = (TextView) dialog_savechanges.findViewById(R.id.alert_textview_save_cancel);
        dosomething_status_unread_meassge_count = (TextView) findViewById(R.id.dosomething_status_unread_meassge_count);
        dosomething_status_main_layout = (LinearLayout) findViewById(R.id.dosomething_status_main_layout);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dosomething_status_main_layout.getWindowToken(), 0);


        alert_textview_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                    String date = sharedPreferences.getDateOfbirth(context);
//                    String date ="29/07/13";
                    SimpleDateFormat input = new SimpleDateFormat("dd / MM / yyyy");
                    SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Log.d("tripDate", date);
                        oneWayTripDate = input.parse(date);                 // parse input
                        tripDate = output.format(oneWayTripDate);// format output
                        Log.d("tripDate", tripDate);
                        dob = tripDate;
                        sharedPreferences.setDateofBirth(context, tripDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    updateprofile();
                    sharedPreferences.setBooleam(context, "false");
                    dialog_savechanges.dismiss();
                } else {
                    NetworkCheck.alertdialog(context);

                }


            }
        });
        alert_textview_save_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fadeOut_AlertSave();
//                relativelayout_alertdialog_save.setVisibility(View.GONE);
                dialog_savechanges.dismiss();
                sharedPreferences.setBooleam(context, "false");
            }
        });


        if (getIntent().hasExtra("GCM_chat")) {
            mChat_repalce = getIntent().getExtras().getBoolean("GCM_chat");
        }
        if (getIntent().hasExtra("conversationid")) {
            conversationid = getIntent().getExtras().getString("conversationid");
            sharedPreferences.setConversationId(context, conversationid);
        }

        if (getIntent().hasExtra("senderId")) {
            String senderId = getIntent().getExtras().getString("senderId");
            sharedPreferences.setFriendUserId(context, senderId);
            profile_user_id = sharedPreferences.getFriendUserId(context);
        }


        if (mChat_repalce) {
            if (!sharedPreferences.getPushType(context).equals("sendrequest")) {
                ((MyApplication) getApplication()).getListChatBean().clear();
                activity_dosomething_textview_toolbar_save.setVisibility(View.GONE);
                activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                DoSomethingChatBox doSomethingChatBox = new DoSomethingChatBox();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatBox);
                fragmentTransaction.commit();
                activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
                status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat_active));
                status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin));
                status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                click_action15 = false;
                click_action14 = false;
                click_action13 = true;
                click_action12 = false;
                check++;
                ((MyApplication) getApplication()).getDoSomethingStatus().setstate("YES");
            } else {
                new GetUserDetails().execute();
            }

        } else {
            if (!sharedPreferences.getHobbies(context).equalsIgnoreCase("Yes")) {
                FragmentStatus fragmentStatus = new FragmentStatus();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.detail_fragment, fragmentStatus);
                fragmentTransaction.commit();
            } else {
                status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon_active));
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                FragmentProfile fragmentProfile = new FragmentProfile();
                fragmentTransaction.replace(R.id.detail_fragment, fragmentProfile);
                fragmentTransaction.commit();
                activity_dosomething_textview_toolbar_save.setVisibility(View.VISIBLE);
                activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
                if (bundle_list.isEmpty()) {
                    Bundle bundle = new Bundle();
                    if (hobbiesBundle != null) {
                        bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        fragmentProfile.setArguments(bundle);
                    } else {
                        bundle_list.clear();
                        bundle_list.add(R.drawable.pluis_icon);
                        Log.d("FFFFFFFFFFFFFFFFFFFFFFF", "H" + bundle_list);

                        bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        fragmentProfile.setArguments(bundle);
                    }
                } else {
                    Bundle value = new Bundle();
                    value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                    Log.d("ARRAYYYYBUNDLE", "............" + value);
                    fragmentProfile.setArguments(value);
                }

                status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin));
                status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                click_action14 = true;
                click_action15 = false;
                click_action12 = false;
                click_action13 = false;
                sharedPreferences.setHobbies(context, "Yes");
                sharedPreferences.setHobbiesId(context, dosomething_activity_hobbies_id_null);
            }
        }

        status_ImageView_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.setPushType(context, "");
                ((MyApplication) getApplication()).getListFilterBeans().clear();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(status_ImageView_profile.getWindowToken(), 0);
                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                    if (!click_action14 || click_action15 || click_action12 || click_action13) {
                        status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon_active));
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
                        FragmentProfile fragmentProfile = new FragmentProfile();

                        activity_dosomething_textview_toolbar_save.setVisibility(View.VISIBLE);
                        activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                        activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
                        if (bundle_list.isEmpty()) {
                            Bundle bundle = new Bundle();
                            bundle_list.clear();
                            bundle_list.add(R.drawable.pluis_icon);
                            Log.d("FFFFFFFFFFFFFFFFFFFFFFF", "H" + bundle_list);

                            bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                            fragmentProfile.setArguments(bundle);
                        } else {
                            Bundle value = new Bundle();

//                value.putStringArrayList("array_bundle_hobbies_name", hobbies_array);

                            value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);


                            Log.d("ARRAYYYYBUNDLE", "............" + value);
//                i.putStringArrayListExtra("array_bundle_hobbies_name", hobbies_array);
                            fragmentProfile.setArguments(value);
                        }
                        fragmentTransaction.replace(R.id.detail_fragment, fragmentProfile);
                        fragmentTransaction.commit();
                        status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin));
                        status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                        status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                        click_action14 = true;
                        click_action15 = false;
                        click_action12 = false;
                        click_action13 = false;

                    }

                } else {
                    NetworkCheck.alertdialog(context);
                }

            }
        });
        status_ImageView_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.setPushType(context, "");
                ((MyApplication) getApplication()).getListFilterBeans().clear();
                ((MyApplication) getApplication()).setanInt(0);
                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(status_ImageView_setting.getWindowToken(), 0);
                    if (sharedPreferences.getBoolean(context).equals("false")) {
                        if (!click_action15 || click_action12 || click_action13 || click_action14) {

                            status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting_icon_active));
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
                            FragmentSettings fragmentSettings = new FragmentSettings();
                            fragmentTransaction.replace(R.id.detail_fragment, fragmentSettings);
                            fragmentTransaction.commit();
                            activity_dosomething_textview_toolbar_save.setVisibility(View.VISIBLE);
                            activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                            activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
                            status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin));
                            status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                            status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                            status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting_icon_active));
                            click_action15 = true;
                            click_action12 = false;
                            click_action14 = false;
                            click_action13 = false;

                        }
                    } else {


                        dialog_savechanges.show();
                    }

                } else {
                    NetworkCheck.alertdialog(context);
                }
            }
        });
        status_layout_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                    sharedPreferences.setPushType(context, "");
                    ((MyApplication) getApplication()).getListFilterBeans().clear();
                    ((MyApplication) getApplication()).setanInt(0);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(status_layout_menu.getWindowToken(), 0);
                    if (sharedPreferences.getBoolean(context).equals("false")) {
                        if (click_action12 || click_action13 || click_action15 || click_action14) {

                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);

                            if (fragmentStatus == null) {
                                fragmentStatus = new FragmentStatus();
                            }

                            fragmentTransaction.replace(R.id.detail_fragment, fragmentStatus);
                            fragmentTransaction.commit();
                            activity_dosomething_textview_toolbar_save.setVisibility(View.GONE);
                            activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                            activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
                            status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin));
                            status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                            status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                            status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                            click_action15 = false;
                            click_action14 = false;
                            click_action12 = false;
                            click_action13 = false;
                        }
                    } else {
//                        fadeIn_alertdialogSave();
                        dialog_savechanges.show();
                    }

                } else {
                    NetworkCheck.alertdialog(context);
                }
            }
        });
        status_layout_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(status_layout_pin.getWindowToken(), 0);
                settext("YES");
                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context))

                {
                    final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        final Dialog dialog = new Dialog(context);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.dosomething_alert_save_changes);
                        TextView status_textview_save = (TextView) dialog.findViewById(R.id.status_textview_save);
                        TextView alert_textview_save = (TextView) dialog.findViewById(R.id.alert_textview_save);
                        TextView alert_textview_save_cancel = (TextView) dialog.findViewById(R.id.alert_textview_save_cancel);
                        status_textview_save.setText("Your GPS seems to be disabled, do you want to enable it?");
                        alert_textview_save.setText("Yes");
                        alert_textview_save_cancel.setText("No");
                        alert_textview_save.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        alert_textview_save_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    } else {
                        if (sharedPreferences.getBoolean(context).equals("false")) {
                            settext("YES");
                            sharedPreferences.setDosomething_filterImage_Visibility(context, "Yes");
                            sharedPreferences.setFilterGender(context, "");
                            sharedPreferences.setFilterAvailable(context, "");
                            sharedPreferences.setFilterStatus(context, "");
                            sharedPreferences.setFilterDistance(context, "");
                            sharedPreferences.setFilterAge(context, "");
                            status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin_active));
                            status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                            status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                            status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


                            somethingNearMe = new DoSomethingNearMe();
//                        fragmentTransaction.setCustomAnimations(R.anim.slide_out, R.anim.slide_out);

                            activity_dosomething_textview_toolbar_save.setVisibility(View.GONE);
                            activity_dosomething_imageview_filter_icon.setVisibility(View.VISIBLE);
                            fragmentTransaction.replace(R.id.detail_fragment, somethingNearMe);
                            fragmentTransaction.commit();
                            click_action15 = false;
                            click_action14 = false;
                            click_action12 = true;
                            click_action13 = false;
                        } else {
//                        fadeIn_alertdialogSave();
                            dialog_savechanges.show();
                        }
                    }


                } else {
                    NetworkCheck.alertdialog(context);
                }
            }
        });
        status_layout_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.setPushType(context, "");
                ((MyApplication) getApplication()).getListFilterBeans().clear();
                ((MyApplication) getApplication()).setanInt(0);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(status_layout_pin.getWindowToken(), 0);
                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {

                    if (sharedPreferences.getBoolean(context).equals("false")) {

                        ((MyApplication) getApplication()).getListChatBean().clear();
                        activity_dosomething_textview_toolbar_save.setVisibility(View.GONE);

                        activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);

                        DoSomethingChatList doSomethingChatList = new DoSomethingChatList();
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatList);
                        fragmentTransaction.commit();
                        activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
                        status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat_active));
                        status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin));
                        status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                        status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                        click_action15 = false;
                        click_action14 = false;
                        click_action13 = true;
                        click_action12 = false;
                        check++;
                    } else {
                        dialog_savechanges.show();
                    }
                } else {
                    NetworkCheck.alertdialog(context);
                }
            }
        });

        text_font_typeface();
    }

    ////////////// in status page//////////////////
    public void settext(String state) {
        Log.d("FFFF", "GGG" + state);
        pin_state = state;
        if (pin_state.equals("YES")) {
            activity_dosomething_imageview_filter_icon.setClickable(true);
//            Toast.makeText(DoSomethingStatus.this, "YES", Toast.LENGTH_SHORT).show();
        } else if (pin_state.equals("No")) {
            activity_dosomething_imageview_filter_icon.setClickable(false);
//            Toast.makeText(DoSomethingStatus.this, "NO", Toast.LENGTH_SHORT).show();
        } else {
            activity_dosomething_imageview_filter_icon.setClickable(true);
        }
    }


    public void setstate(String state) {
        chat_state = state;
    }

    public void setmatchState(String state) {
        match_chat_state = state;
    }

    private void text_font_typeface() {
        Typeface patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");
        Typeface patron_regular = Typeface.createFromAsset(getAssets(), "fonts/Patron-Regular.ttf");
        Typeface patron_medium = Typeface.createFromAsset(getAssets(), "fonts/Patron-Medium.ttf");
        Typeface myfonts_thin = Typeface.createFromAsset(getAssets(), "fonts/Patron-Thin.ttf");
        activity_dosomething_textview_toolbar_search.setTypeface(patron_regular);
        activity_dosomething_textview_toolbar_save.setTypeface(patron_regular);
        status_textview_save.setTypeface(patron_bold);
        alert_textview_save.setTypeface(patron_bold);
        alert_textview_save_cancel.setTypeface(patron_bold);
        password_no_match.setTypeface(patron_bold);
        alert_textview_password_nomatch_cancel.setTypeface(patron_bold);

    }

    @Override
    public void onBackPressed() {

        if (click_action13 && chat_state.equals("No")) {

            ((MyApplication) getApplication()).setanInt(0);
            DoSomethingChatList doSomethingChatList = new DoSomethingChatList();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_out);
            fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatList);
            fragmentTransaction.commit();
            status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat_active));
            status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin));

        } else if (click_action12 && pin_state.equals("No")) {
            settext("YES");
            ((MyApplication) getApplication()).setanInt(0);
            sharedPreferences.setDosomething_filterImage_Visibility(context, "Yes");
            sharedPreferences.setFilterGender(context, "");
            sharedPreferences.setFilterAvailable(context, "");
            sharedPreferences.setFilterStatus(context, "");
            sharedPreferences.setFilterDistance(context, "");
            sharedPreferences.setFilterAge(context, "");

            status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin_active));
            status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
            status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
            status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                somethingNearMe = new DoSomethingNearMe();
//                        fragmentTransaction.setCustomAnimations(R.anim.slide_out, R.anim.slide_out);

            activity_dosomething_textview_toolbar_save.setVisibility(View.GONE);
            activity_dosomething_imageview_filter_icon.setVisibility(View.VISIBLE);
            fragmentTransaction.replace(R.id.detail_fragment, somethingNearMe);
            fragmentTransaction.commit();
            click_action15 = false;
            click_action14 = false;
            click_action12 = true;
            click_action13 = false;
        } else if (click_action12 && match_chat_state.equals("match_chat")) {
            settext("YES");
            ((MyApplication) getApplication()).setanInt(0);
            sharedPreferences.setDosomething_filterImage_Visibility(context, "Yes");
            sharedPreferences.setFilterGender(context, "");
            sharedPreferences.setFilterAvailable(context, "");
            sharedPreferences.setFilterStatus(context, "");
            sharedPreferences.setFilterDistance(context, "");
            sharedPreferences.setFilterAge(context, "");

            status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin_active));
            status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
            status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
            status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                somethingNearMe = new DoSomethingNearMe();
//                        fragmentTransaction.setCustomAnimations(R.anim.slide_out, R.anim.slide_out);

            activity_dosomething_textview_toolbar_save.setVisibility(View.GONE);
            activity_dosomething_imageview_filter_icon.setVisibility(View.VISIBLE);
            fragmentTransaction.replace(R.id.detail_fragment, somethingNearMe);
            fragmentTransaction.commit();
            click_action15 = false;
            click_action14 = false;
            click_action12 = true;
            click_action13 = false;
            match_chat_state = "";
        } else {
            super.onBackPressed();
            Intent i = new Intent(Intent.ACTION_MAIN);

            i.addCategory(Intent.CATEGORY_HOME);

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(i);
        }
        showHideBottomLayout(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void fadeIn_alertdialogSave() {


        Animation fadeIn = new AlphaAnimation(0, 1);

        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this

        fadeIn.setStartOffset(0);

        fadeIn.setDuration(1000);


        AnimationSet animation = new AnimationSet(false); //change to false


        animation.addAnimation(fadeIn);

        relativelayout_alertdialog_save.setVisibility(View.VISIBLE);


        relativelayout_alertdialog_save.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog_save.clearAnimation();

            }


        });


    }

    public void fadeOut_AlertSave() {


        Animation fadeOut = new AlphaAnimation(1, 0);

        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this

        fadeOut.setStartOffset(0);

        fadeOut.setDuration(1000);


        AnimationSet animation = new AnimationSet(false); //change to false

        animation.addAnimation(fadeOut);

        relativelayout_alertdialog_save.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog_save.clearAnimation();

            }


        });


    }

    //    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
////        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_settings) {
////            return true;
////        }
//
//        return super.onOptionsItemSelected(item);
//    }
    public void onResume() {
        super.onResume();

        Log.i("tracker", "Setting screen name: " + name);
        mTracker.setScreenName("Image~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        if (!sharedPreferences.getSessionid(context).equals("")) {
            timer_chat = new Timer();
            timer_chat.schedule(new AutoChatHistory(), 0, 5000);
        }


    }


    public void hideFilterIconVisible(Boolean hide) {
        if (hide) {
            activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
        } else {
            activity_dosomething_imageview_filter_icon.setVisibility(View.VISIBLE);
        }
    }

    public void passwordMatch(boolean b) {
        if (b) {

            dosomething_save_click_action = "";
        } else {
            dosomething_save_click_action = "clickfalse";
        }

    }

    private class AsynDataClass extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(DoSomethingStatus.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {

                HashMap<String, Object> paramsfb = new HashMap<>();
                paramsfb.put(TAG_SESSIONID, sessionid);
                paramsfb.put(TAG_LATITUDE, latitude);
                paramsfb.put(TAG_LONGITUDE, longitude);
                paramsfb.put(TAG_FILTER_STATUS, filter_status);
                paramsfb.put(TAG_FILTER_GENDER, filter_gender);
                paramsfb.put(TAG_FILTER_AGERANGE, filter_agerange);
                paramsfb.put(TAG_FILTER_DISTANCE, filter_distance);
                json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_nearestusers), paramsfb);
                Log.v("jason url=======>", String.valueOf(paramsfb));
                try {
                    json_object = new JSONObject(json_string);
                    json_content = json_object.getJSONObject("nearestusers");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                try {
                    if (json_object.has("nearestusers")) {
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
                            Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_LONG);
                            pDialog.dismiss();

//
                            JSONArray userlist = json_content.getJSONArray("UserList");
                            for (int i = 0; i < userlist.length(); i++) {


                                JSONObject userlistobject = userlist.getJSONObject(i);
                                int user_id = userlistobject.getInt("user_id");
                                String first_name = userlistobject.getString("first_name");
                                String last_name = userlistobject.getString("last_name");
                                String about = userlistobject.getString("about");
                                String gender = userlistobject.getString("gender");
                                String date_of_birth = userlistobject.getString("date_of_birth");
                                int online_status = userlistobject.getInt("online_status");
                                String image2 = userlistobject.getString("image2");
                                String image1 = userlistobject.getString("image1");
                                String image3 = userlistobject.getString("image3");
                                String available_now = userlistobject.getString("available_now");
                                String dosomething = userlistobject.getString("send_request");
                                String distance = userlistobject.getString("distance");


                                ArrayList<Filterbean> filterbeans = new ArrayList<>();
                                Log.d("BEAN", "GHGHGH" + filterbeans.get(0).getuser_id());
                                Log.d("BEAN", "GHGHGH" + filterbeans.get(0).getfirst_name());
                                Log.d("BEAN", "GHGHGH" + filterbeans.get(0).getlast_name());
                                Log.d("BEAN", "GHGHGH" + filterbeans.get(0).getonline_status());
                                Log.d("BEAN", "GHGHGH" + filterbeans.get(0).getDoSomething());

                            }
//                            Log.v("SessionId =======>", SessionId);
//                            finish();
//if(json_content.getString("Message").equalsIgnoreCase("Registred Successfully"))
//{
// }else
//{
//    JSONArray sportsArray = json_content.getJSONArray("userDetails");
//    JSONObject firstSport = sportsArray.getJSONObject(0);
//    String SessionId = firstSport.getString("SessionId");
//    sharedPreferences.setSessionid(context, SessionId);
//    Log.v("SessionId =======>", SessionId);
//    Intent i = new Intent(DoSomeThingCreateAccount.this, DoSomethingStatus.class);
//    startActivity(i);
//    finish();
//}
//
                        } else if (json_content.getString("status").equalsIgnoreCase("failed")) {
                            Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_LONG);
                            pDialog.dismiss();
                        } else if (json_content.getString("status").equalsIgnoreCase("error")) {
                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG);
                            pDialog.dismiss();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        try {
            String fragmentSimpleName = fragment.getClass().getSimpleName();

            if (fragmentSimpleName.equals("fragmentprofile")) {
                Log.d(":::::::::", "LoginFragment");
                loginfrg = (FragmentProfile) fragment;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK && null != data) {
            if (loginfrg != null) {
                loginfrg.onActivityResult(requestCode, resultCode, data);
            }

        }

    }


    public void updateprofile() {
        if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {


            relativelayoutupdate_profile_progress.setVisibility(View.VISIBLE);
            kbv.setVisibility(View.VISIBLE);
            timer = new Timer();
            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation.start();

            HashMap<String, Object> params = new HashMap<>();
            if (!sharedPreferences.getDateOfbirth(context).equals("") || !sharedPreferences.getDateOfbirth(context).equalsIgnoreCase("00/00/0000") || !sharedPreferences.getDateOfbirth(context).equalsIgnoreCase("0000-00-00")) {
                params.put(TAG_DATEOFBIRTH, sharedPreferences.getDateOfbirth(context));
            }
            if (!sharedPreferences.getLatitude(context).equals("")) {
                params.put(TAG_LATITUDE, sharedPreferences.getLatitude(context));

            }
            if (!sharedPreferences.getLongitude(context).equals("")) {
                params.put(TAG_LONGTITUDE, sharedPreferences.getLongitude(context));

            }
            params.put(TAG_ABOUT, sharedPreferences.getAbout(context));
            params.put(TAG_HOBBIES, sharedPreferences.getHobbiesId(context));
            params.put(TAG_NOTIFICATIONMESSAGE, sharedPreferences.getNotifyMessage(context));
            params.put(TAG_NOTIFICATIONSOUND, sharedPreferences.getNotifySound(context));
            params.put(TAG_NOTIFICATIONVIBRATION, sharedPreferences.getNotifyVibration(context));
            params.put(TAG_NOTIFICATIONMATCH, sharedPreferences.getNotifyMatch(context));
            params.put(TAG_SESSIONID, sharedPreferences.getSessionid(context));
            if (!sharedPreferences.getUpdatepassword(context).equalsIgnoreCase("")) {
                params.put(TAG_PASSWORD, sharedPreferences.getUpdatepassword(context));
            }

            File file = null;
            if (!sharedPreferences.getUpdateProfilePicture(context).equalsIgnoreCase("")) {
                try {
                    String image_url = sharedPreferences.getUpdateProfilePicture(context);
                    image_url = image_url.replace(" ", "");
                    if (image_url == null || image_url.equals("")) {
//                        Log.d("BITMAP===>", "NULL");
//                        Log.d("null", sharedPreferences.getUpdateProfilePicture(context));
                    } else {
                        file = new File(Environment.getExternalStorageDirectory(),
                                getResources().getString(R.string.app_name) + "fragment_profie_image" + ".jpg");
//                        Log.d("PATH====>", sharedPreferences.getUpdateProfilePicture(context));
//                        params.put(TAG_PROFILEIMAGE1, file);
//                        uploadProfilePicture(file);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            File file2 = null;
            if (!sharedPreferences.getUpdateProfilePicture1(context).equalsIgnoreCase("")) {
                try {
                    String image_url = sharedPreferences.getUpdateProfilePicture1(context);
                    image_url = image_url.replace(" ", "");
                    if (image_url == null || image_url.equals("")) {
//                        Log.d("BITMAP===>", "NULL");
//                        Log.d("null", sharedPreferences.getUpdateProfilePicture1(context));
                    } else {
                        file2 = new File(Environment.getExternalStorageDirectory(),
                                getResources().getString(R.string.app_name) + "fragment_profie_image1" + ".jpg");
//                        Log.d("PATH====>", sharedPreferences.getUpdateProfilePicture1(context));
//                        params.put(TAG_PROFILEIMAGE2, file2);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            File file3 = null;
            if (!sharedPreferences.getUpdateProfilePicture2(context).equalsIgnoreCase("")) {
                try {
                    String image_url = sharedPreferences.getUpdateProfilePicture2(context);
                    image_url = image_url.replace(" ", "");
                    if (image_url == null || image_url.equals("")) {
//                        Log.d("BITMAP===>", "NULL");
//                        Log.d("null", sharedPreferences.getUpdateProfilePicture2(context));
                    } else {
                        file3 = new File(Environment.getExternalStorageDirectory(),
                                getResources().getString(R.string.app_name) + "fragment_profie_image2" + ".jpg");
//                        Log.d("PATH====>", sharedPreferences.getUpdateProfilePicture2(context));
//                        params.put(TAG_PROFILEIMAGE3, file3);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            uploadProfilePicture(file, file2, file3, params);


//                params.put(TAG_PROFILEIMAGE1, profileImage1);
//                params.put(TAG_PROFILEIMAGE2, profileImage2);
//                params.put(TAG_PROFILEIMAGE3, profileImage3);


//                BitmapFactory.Options bmOptions1 = new BitmapFactory.Options();
//                Bitmap bitmap1 = BitmapFactory.decodeFile(sharedPrefrences.getProfilePicture1(context), bmOptions1);
//                json_string = jsonfunctions.postImageWebservice(url, params, bitmap1, TAG_PROFILEIMAGE2);
//                BitmapFactory.Options bmOptions2 = new BitmapFactory.Options();
//                Bitmap bitmap2 = BitmapFactory.decodeFile(sharedPrefrences.getProfilePicture2(context), bmOptions2);
//                json_string = jsonfunctions.postImageWebservice(url, params, bitmap2, TAG_PROFILEIMAGE3);
//            aQuery.auth(handle).ajax(getString(R.string.dosomething_apilink_string_updateprofile), params, String.class, this, "photoCb");
//                json_string = jsonfunctions.postToURL(url, params);

//
            Log.v("jason url=======>", String.valueOf(params));
        }
    }


    private void uploadProfilePicture(final File file1,
                                      final File file2,
                                      final File file3,
                                      final HashMap<String, Object> params) {

        url = getString(R.string.dosomething_apilink_string_updateprofile);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... p) {
                try {
                    postImageWebservice(
                            url,
                            file1,
                            file2,
                            file3,
                            params);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    json_object = new JSONObject(profile_update_response);
                    if (json_object.has("updateprofile")) {
                        json_content = json_object.getJSONObject("updateprofile");
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
//                        pd.dismiss();


                            if (bundle_list != null) {
                                bundle_list.clear();
                            }
                            JSONArray sportsArray = json_content.getJSONArray("userDetails");
                            JSONObject firstSport = sportsArray.getJSONObject(0);
                            String user_id = firstSport.getString("user_id");
                            String image1 = firstSport.getString("image1");
                            String image2 = firstSport.getString("image2");
                            String image3 = firstSport.getString("image3");
                            String notification_message = firstSport.getString("notification_message");
                            String notification_sound = firstSport.getString("notification_sound");
                            String notification_vibration = firstSport.getString("notification_vibration");
                            String notification_match = firstSport.getString("isMatch");
                            sharedPreferences.setUserId(context, user_id);
                            sharedPreferences.setProfilePicture(context, image1);
                            sharedPreferences.setProfilePicture1(context, image2);
                            sharedPreferences.setProfilePicture2(context, image3);

                            Log.d("image1", sharedPreferences.setProfilePicture(context, image1));
                            Log.d("image2", sharedPreferences.setProfilePicture1(context, image2));
                            Log.d("image3", sharedPreferences.setProfilePicture2(context, image3));
                            sharedPreferences.setNotifyMessage(context, notification_message);
                            sharedPreferences.setNotifySound(context, notification_sound);
                            sharedPreferences.setNotifyVibration(context, notification_vibration);
                            sharedPreferences.setNotifyMatch(context, notification_match);
                            if (bundle_list.isEmpty()) {
                                JSONArray hobbiesArray = firstSport.getJSONArray("hobbieslist");
                                for (int j = 0; j < hobbiesArray.length(); j++) {
                                    JSONObject details = hobbiesArray.getJSONObject(j);
                                    int id = details.getInt("hobbies_id");
                                    int category_id = details.getInt("category_id");
                                    String name = details.getString("name");
                                    String Image = details.getString("image");
                                    String ActiveImage = details.getString("image_active");
                                    bundle_list.add(id);
                                    Log.d("bundle_list", String.valueOf(bundle_list));
                                }

                            }


                            sharedPreferences.setHobbiesId(context, dosomething_activity_hobbies_id_null);


                            if (click_action14) {
                                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                FragmentProfile fragmentProfile = new FragmentProfile();
                                activity_dosomething_textview_toolbar_save.setVisibility(View.VISIBLE);
                                activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                                activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
                                if (bundle_list.isEmpty()) {
                                    Bundle bundle = new Bundle();
                                    bundle_list.clear();
                                    bundle_list.add(R.drawable.pluis_icon);
                                    Log.d("FFFFFFFFFFFFFFFFFFFFFFF", "H" + bundle_list);

                                    bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                                    fragmentProfile.setArguments(bundle);
                                } else {
                                    Bundle value = new Bundle();


                                    value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);


                                    Log.d("ARRAYYYYBUNDLE", "............" + value);
                                    fragmentProfile.setArguments(value);
                                }
                                fragmentTransaction.replace(R.id.detail_fragment, fragmentProfile);
                                fragmentTransaction.commit();
                                status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon_active));
                                status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin));
                                status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                                status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                                click_action14 = true;
                                click_action15 = false;
                                click_action12 = false;
                                click_action13 = false;
                            }


                            relativelayoutupdate_profile_progress.setVisibility(View.GONE);
                            kbv.setVisibility(View.GONE);
                            if (timer != null) {

                                timer.cancel();


                                timer = null;

                            }
                            splashAnimation.stop();


                        } else if (json_content.getString("status").equalsIgnoreCase("failed")) {

                        }
                    } else if (json_content.getString("status").equalsIgnoreCase("error")) {


                        relativelayoutupdate_profile_progress.setVisibility(View.GONE);
                        kbv.setVisibility(View.GONE);
                        if (timer != null) {

                            timer.cancel();


                            timer = null;

                        }
                        splashAnimation.stop();
                    }
                } catch (JSONException e) {
                    System.out.println("TEST...INTERNET_DISCONNECT...EXPECTION");
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    /*public void uploadFileUsingHttpPost(String url, File myFile) {
        Log.d("POST Image==========", "" + myFile);
        try {
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpProtocolParams.setHttpElementCharset(params, HTTP.UTF_8);

            // Setting timeout
            HttpConnectionParams.setConnectionTimeout(params, 100000);
            HttpConnectionParams.setSoTimeout(params, 100000);
            DefaultHttpClient httpClient = new DefaultHttpClient(params);
            HttpPost postRequest = new HttpPost(url);

            System.out.println("URL:" + url);

            MultipartEntity reqEntity = new MultipartEntity(
                    HttpMultipartMode.BROWSER_COMPATIBLE);
            if (myFile != null && myFile.isFile()) {
                // as a custom file name
                try {
                    InputStream is = null;
                    ByteArrayBody bab;
                    ByteArrayOutputStream bos;
                    if (myFile.isFile()) {
                        is = new BufferedInputStream(
                                new FileInputStream(myFile));
                        bos = new ByteArrayOutputStream();
                        while (is.available() > 0) {
                            bos.write(is.read());
                        }
                        byte[] data = bos.toByteArray();
                        bab = new ByteArrayBody(data,
                                ContentType.create("image/jpg"),
                                String.valueOf(System.currentTimeMillis() + ".jpg"));
                        reqEntity.addPart(TAG_PROFILEIMAGE1, bab);
                        if (is != null) {
                            is.close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();
            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            String resStr = s.toString();
            Log.i("Response", resStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void postImageWebservice(
            String url,
            File file1,
            File file2,
            File file3, HashMap<String, Object> post_params) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);

            Log.d("POST VALUES", "====" + post_params.toString());
            Log.d("POST URL", "====" + url);

            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

            try {
                try {
                    InputStream is = null;
                    ByteArrayBody bab;
                    ByteArrayOutputStream bos;
                    if (file1 != null) {
                        if (file1.isFile()) {
                            is = new BufferedInputStream(new FileInputStream(file1));
                            bos = new ByteArrayOutputStream();
                            while (is.available() > 0) {
                                bos.write(is.read());
                            }
                            byte[] data = bos.toByteArray();
                            entity.addPart(TAG_PROFILEIMAGE1, new ByteArrayBody(data, "val1.jpg"));

                            if (is != null) {
                                is.close();
                            }
                        }
                    }

                    if (file2 != null) {
                        if (file2.isFile()) {
                            is = new BufferedInputStream(new FileInputStream(file2));
                            bos = new ByteArrayOutputStream();
                            while (is.available() > 0) {
                                bos.write(is.read());
                            }
                            byte[] data = bos.toByteArray();
                            entity.addPart(TAG_PROFILEIMAGE2, new ByteArrayBody(data, "val2.jpg"));

                            if (is != null) {
                                is.close();
                            }
                        }
                    }
                    if (file3 != null) {
                        if (file3.isFile()) {
                            is = new BufferedInputStream(new FileInputStream(file3));
                            bos = new ByteArrayOutputStream();
                            while (is.available() > 0) {
                                bos.write(is.read());
                            }
                            byte[] data = bos.toByteArray();
                            entity.addPart(TAG_PROFILEIMAGE3, new ByteArrayBody(data, "val3.jpg"));

                            if (is != null) {
                                is.close();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Iterator<String> myVeryOwnIterator = post_params.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = myVeryOwnIterator.next();
                String value = (String) post_params.get(key);
                entity.addPart(key, new StringBody(value));
            }
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost,
                    localContext);

            InputStreamReader is = new InputStreamReader(response.getEntity().getContent(), "UTF-8");
            BufferedReader reader = new BufferedReader(is);
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String sResponse = sb.toString();
            Log.d("Responce", "====" + sResponse);
            profile_update_response = sResponse;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }

    public void refreshProfileData() {

    }


    public void photoCb(String url, String json, AjaxStatus status) {
        Log.d("photoCb....", "" + status.getMessage() + ".." + status.getCode());
        if (status.getCode() == 200) {
            Log.d("Response::", "" + json.toString());

            try {
                json_object = new JSONObject(json.toString());
                if (json_object.has("updateprofile")) {
                    json_content = json_object.getJSONObject("updateprofile");
                    if (json_content.getString("status").equalsIgnoreCase("success")) {
//                        pd.dismiss();

                        relativelayoutupdate_profile_progress.setVisibility(View.GONE);
                        kbv.setVisibility(View.GONE);
                        if (timer != null) {

                            timer.cancel();


                            timer = null;

                        }
                        splashAnimation.stop();
                        if (json_content.getString("Message").equalsIgnoreCase("Profile Updated Successfully")) {

                            if (bundle_list != null) {
                                bundle_list.clear();
                            }
                            JSONArray sportsArray = json_content.getJSONArray("userDetails");
                            JSONObject firstSport = sportsArray.getJSONObject(0);
                            String user_id = firstSport.getString("user_id");
                            String image1 = firstSport.getString("image1");
                            String image2 = firstSport.getString("image2");
                            String image3 = firstSport.getString("image3");
                            String notification_message = firstSport.getString("notification_message");
                            String notification_sound = firstSport.getString("notification_sound");
                            String notification_vibration = firstSport.getString("notification_vibration");
                            String notification_match = firstSport.getString("isMatch");
                            sharedPreferences.setUserId(context, user_id);
                            sharedPreferences.setProfilePicture(context, image1);
                            sharedPreferences.setProfilePicture1(context, image2);
                            sharedPreferences.setProfilePicture2(context, image3);
                            sharedPreferences.setNotifyMessage(context, notification_message);
                            sharedPreferences.setNotifySound(context, notification_sound);
                            sharedPreferences.setNotifyVibration(context, notification_vibration);
                            sharedPreferences.setNotifyMatch(context, notification_match);
                            if (bundle_list.isEmpty()) {
                                JSONArray hobbiesArray = firstSport.getJSONArray("hobbieslist");
                                for (int j = 0; j < hobbiesArray.length(); j++) {
                                    JSONObject details = hobbiesArray.getJSONObject(j);
                                    int id = details.getInt("hobbies_id");
                                    int category_id = details.getInt("category_id");
                                    String name = details.getString("name");
                                    String Image = details.getString("image");
                                    String ActiveImage = details.getString("image_active");
                                    bundle_list.add(id);
                                    Log.d("bundle_list", String.valueOf(bundle_list));
                                }

//                                    hobbiesBeans.add(new HobbiesBean(id, category_id, name, Image, ActiveImage));
//                                    hobbies_id.add(id);
//                                    hobbies_name.add(name);
//                                    hobbies_image.add(Image);
                            }


                            sharedPreferences.setHobbiesId(context, dosomething_activity_hobbies_id_null);
                            status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon_active));
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            FragmentProfile fragmentProfile = new FragmentProfile();
                            activity_dosomething_textview_toolbar_save.setVisibility(View.VISIBLE);
                            activity_dosomething_textview_toolbar_search.setVisibility(View.GONE);
                            activity_dosomething_imageview_filter_icon.setVisibility(View.GONE);
                            if (bundle_list.isEmpty()) {
                                Bundle bundle = new Bundle();
                                bundle_list.clear();
                                bundle_list.add(R.drawable.pluis_icon);
                                Log.d("FFFFFFFFFFFFFFFFFFFFFFF", "H" + bundle_list);

                                bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                                fragmentProfile.setArguments(bundle);
                            } else {
                                Bundle value = new Bundle();


                                value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);


                                Log.d("ARRAYYYYBUNDLE", "............" + value);
                                fragmentProfile.setArguments(value);
                            }
                            fragmentTransaction.replace(R.id.detail_fragment, fragmentProfile);
                            fragmentTransaction.commit();
                            status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin));
                            status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                            status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                            click_action14 = true;
                            click_action15 = false;
                            click_action12 = false;
                            click_action13 = false;

                        }
                    } else if (json_content.getString("status").equalsIgnoreCase("failed")) {


                        relativelayoutupdate_profile_progress.setVisibility(View.GONE);
                        kbv.setVisibility(View.GONE);
                        if (timer != null) {

                            timer.cancel();


                            timer = null;

                        }
                        splashAnimation.stop();

//                            pDialog.dismiss();
//                            pDialog = new ProgressDialog(DoSomeThingCreateAccount.this);
//                            pDialog.setMessage("Email id already exist");
//                            pDialog.show();
//                        AlertDialog dialogBuilder =new AlertDialog.Builder(getApplicationContext()).create();
//                        dialogBuilder.setTitle("Info");
//                        dialogBuilder.setMessage("Failed to login");
//                        dialogBuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        dialogBuilder.show();
                    }
                } else if (json_content.getString("status").equalsIgnoreCase("error")) {
//                        pDialog.dismiss();
//                    pd.dismiss();

                    relativelayoutupdate_profile_progress.setVisibility(View.GONE);
                    kbv.setVisibility(View.GONE);
                    if (timer != null) {

                        timer.cancel();


                        timer = null;

                    }
                    splashAnimation.stop();
                }
            } catch (JSONException e) {
                System.out.println("TEST...INTERNET_DISCONNECT...EXPECTION");
                e.printStackTrace();
            }
        }

    }

    class Goodsin_Background extends AsyncTask<String, String, String> {
        JSONObject opj;
        JSONArray content, hobbieslist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(DoSomethingHobbies.this);
//            pDialog.setMessage("Loading......");
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        protected String doInBackground(String... params) {
            response = jsonfunctions.getJSONfromURL(urlImageStatus);
            JSONObject json = new JSONObject();
            try {
                String tv1 = sharedPreferences.getSessionid(context);
//                url = "http://wiztestinghost.com/dosomething/dosomethinglist?";
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("sessionid", tv1);
                response = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_gethobbies), map);
                opj = new JSONObject(response);
                Log.v("response_goods", response);
                JSONObject dosumthng = opj.getJSONObject("gethobbies");
                if (dosumthng.getString("status").equalsIgnoreCase("success")) {
                    content = dosumthng.getJSONArray("list");
                    for (int i = 0; i < content.length(); i++) {
                        JSONObject details = content.getJSONObject(i);
                        int Id = details.getInt("categories_id");
                        Log.v("categories_id", String.valueOf(Id));
                        Log.d("List", "........" + Id);
                        if (details.getInt("categories_id") == 1) {
                            if (details.getString("name").equalsIgnoreCase("Arts")) {
                                hobbieslist = details.getJSONArray("hobbieslist");
                                for (int j = 0; j < hobbieslist.length(); j++) {
                                    JSONObject values = hobbieslist.getJSONObject(j);
                                    int hobbies_id = values.getInt("hobbies_id");
                                    int category_id = values.getInt("category_id");
                                    String name = values.getString("name");
                                    Log.d("NAMEEEEEEE", ",,,," + name);
                                    String image = values.getString("image");
                                    String image_active = values.getString("image_active");
                                    Log.d("List1", "........");


//                                   hobbies_image_arts.add(image);
                                }
                            }
                        } else if (details.getInt("categories_id") == 2) {
                            if (details.getString("name").equalsIgnoreCase("Food")) {
                                hobbieslist = details.getJSONArray("hobbieslist");
                                for (int j = 0; j < hobbieslist.length(); j++) {
                                    JSONObject values = hobbieslist.getJSONObject(j);
                                    int hobbies_id = values.getInt("hobbies_id");
                                    int category_id = values.getInt("category_id");
                                    String name = values.getString("name");
                                    String image = values.getString("image");
                                    String image_active = values.getString("image_active");
                                    Log.d("List2", "........");


//                                    hobbies_image_food.add(image);
                                }
                            }
                        } else if (details.getInt("categories_id") == 3) {
                            if (details.getString("name").equalsIgnoreCase("Pets")) {
                                hobbieslist = details.getJSONArray("hobbieslist");
                                for (int j = 0; j < hobbieslist.length(); j++) {
                                    JSONObject values = hobbieslist.getJSONObject(j);
                                    int hobbies_id = values.getInt("hobbies_id");
                                    int category_id = values.getInt("category_id");
                                    String name = values.getString("name");
                                    String image = values.getString("image");
                                    String image_active = values.getString("image_active");
                                    Log.d("List3", "........");


//                                    hobbies_image_pets.add(image);
                                }
                            }
                        } else if (details.getInt("categories_id") == 4) {
                            if (details.getString("name").equalsIgnoreCase("Recreation")) {
                                hobbieslist = details.getJSONArray("hobbieslist");
                                for (int j = 0; j < hobbieslist.length(); j++) {
                                    JSONObject values = hobbieslist.getJSONObject(j);
                                    int hobbies_id = values.getInt("hobbies_id");
                                    int category_id = values.getInt("category_id");
                                    String name = values.getString("name");
                                    String image = values.getString("image");
                                    String image_active = values.getString("image_active");
                                    Log.d("List4", "........");


//                                    hobbies_image_recreation.add(image);
                                }
                            }
                        }


//                        String ActiveImage = details.getString("ActiveImage");
//                        String InactiveImage = details.getString("InactiveImage");


//                        hobbies_list.add(new ImageBan(Id, name, ActiveImage, InactiveImage));
//                        hobbies_list.add(Id, name);
//                        Log.d("img_list.size()", String.valueOf(hobbies_list.size()));
//                        Log.d("List", String.valueOf(hobbies_list));
                    }

                } else {


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }
    }


    private class AsynProfileClass extends AsyncTask<Void, Void, Void> {

        private String first_name, last_name, gender, about;
        private String email;
        private String status = "";
        private String device;
        private int age;
        private int user_id;
        private String profile_id;
        private String image2;
        private String image1;
        private String image3;
        private String device_token;
        private String date_of_birth;
        private String image1_thumb;
        private String image2_thumb;
        private String image3_thumb;
        @Override

        protected void onPreExecute() {

            super.onPreExecute();



        }


        @Override

        protected Void doInBackground(Void... params) {
            if (context != null) {
                if (bundle_list != null) {
                    bundle_list.clear();
                }
//                bundle_list=new ArrayList<>();
                if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {


                    HashMap<String, Object> paramsfb = new HashMap<>();
                    paramsfb.put(TAG_SESSIONID, sessionid);

                    paramsfb.put(TAG_PROFILEUSERID, sharedPreferences.getUserId(context));

                    String urlgetUser = "http://wiztestinghost.com/dosomething/getuserdetails?";
                    json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_getuserdetails), paramsfb);

                    Log.v("jason url=======>", sessionid);

                    try {

                        json_object = new JSONObject(json_string);


                        if (json_object.has("getuserdetails")) {
                            json_content = json_object.getJSONObject("getuserdetails");

                            if (json_content.getString("status").equalsIgnoreCase("success")) {


                                JSONObject userlistobject = json_content.getJSONObject("userDetails");

                                user_id = userlistobject.getInt("user_id");

                                profile_id = userlistobject.getString("profile_id");
                                first_name = userlistobject.getString("first_name");

                                last_name = userlistobject.getString("last_name");


                                gender = userlistobject.getString("gender");

                                about = userlistobject.getString("about");
                                email = userlistobject.getString("email");
                                status = userlistobject.getString("status");

                                device = userlistobject.getString("device");

                                age = Integer.parseInt(userlistobject.getString("age"));

                                image2 = userlistobject.getString("image2");

                                image1 = userlistobject.getString("image1");

                                image3 = userlistobject.getString("image3");

                                device_token = userlistobject.getString("device_token");

                                date_of_birth = userlistobject.getString("date_of_birth");
                                if (userlistobject.has("image1_thumb")) {
                                    image1_thumb = userlistobject.getString("image1_thumb");
                                }
                                if (userlistobject.has("image2_thumb")) {
                                    image2_thumb = userlistobject.getString("image2_thumb");
                                }
                                if (userlistobject.has("image3_thumb")) {
                                    image3_thumb = userlistobject.getString("image3_thumb");
                                }

                                if (bundle_list.isEmpty()) {
                                    JSONArray hobbiesArray = userlistobject.getJSONArray("hobbieslist");
                                    for (int j = 0; j < hobbiesArray.length(); j++) {
                                        Log.e("hobbiesArray", hobbiesArray + "");
                                        JSONObject details = hobbiesArray.getJSONObject(j);
                                        int id = details.getInt("hobbies_id");
                                        int category_id = details.getInt("category_id");
                                        String name = details.getString("name");
                                        String Image = details.getString("image");
                                        String ActiveImage = details.getString("image_active");
                                        bundle_list.add(id);

//

                                    }

                                }


//                                profileBeans.add(new ProfileBean(user_id, profile_id, first_name, last_name, gender, about, email, status, device, age, image2, image1, image3, device_token, date_of_birth));
//                                sharedPrefrences.setFirstname(getActivity(), first_name);
//                                sharedPrefrences.setLastname(getActivity(), last_name);
//                                sharedPrefrences.setGender(getActivity(), gender);
//                                sharedPrefrences.setAbout(getActivity(), about);
//                                sharedPrefrences.setLogin(getActivity(), email);
//                                sharedPrefrences.setProfilePicture(getActivity(), image1);
//                                sharedPrefrences.setProfilePicture1(getActivity(), image2);
//                                sharedPrefrences.setProfilePicture2(getActivity(), image3);

                                Log.d("BEAN", "GHGHGH" + user_id);
                                Log.d("BEAN", "GHGHGH" + profile_id);

                                Log.d("BEAN", "GHGHGH" + first_name);

                                Log.d("BEAN", "GHGHGH" + last_name);

                                Log.d("BEAN", "GHGHGH" + gender);

                                Log.d("BEAN", "GHGHGH" + about);

                                Log.d("BEAN", "GHGHGH" + email);

                                Log.d("BEAN", "GHGHGH" + status);


                                Log.d("BEAN", "GHGHGH" + image1);
                                Log.d("BEAN", "GHGHGH" + date_of_birth);


                            } else if (json_content.getString("status").equalsIgnoreCase("failed")) {

                                status = "InvalidSession";
                            } else if (json_content.getString("status").equalsIgnoreCase("error")) {

                                status = "InvalidSession";
                            }

                        }
                        if (json_object.has("error")) {
                            if (json_object.getString("error").equalsIgnoreCase("InvalidSession")) {
                                pd.dismiss();

                                status = "InvalidSession";

                            }
                        }

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }

                }

            }
            return null;

        }


        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            if (!status.equalsIgnoreCase("InvalidSession")) {

                Log.d("bundle_list", String.valueOf(bundle_list));
            } else if (status.equalsIgnoreCase(""))

            {
                Log.d("bundle_list", String.valueOf(bundle_list));
            } else {
                sharedPreferences.setLogin(context, "");
                sharedPreferences.setDateofBirth(context, "");
                sharedPreferences.setFirstname(context, "");
                sharedPreferences.setDeviceToken(context, "");
                sharedPreferences.setLastname(context, "");
                sharedPreferences.setGender(context, "");
                sharedPreferences.setPassword(context, "");
                sharedPreferences.setUserId(context, "");
                sharedPreferences.setSessionid(context, "");
                sharedPreferences.setProfileId(context, "");
                sharedPreferences.setNotifyMessage(context, "");
                sharedPreferences.setNotifyVibration(context, "");
                sharedPreferences.setNotifySound(context, "");
                sharedPreferences.setProfilePicture(context, "");
                sharedPreferences.setProfilePicture1(context, "");
                sharedPreferences.setProfilePicture2(context, "");
                sharedPreferences.setFBProfilePicture(context, "");
                sharedPreferences.setProfileImageBitmap1(context, "");
                sharedPreferences.setProfileImageBitmap2(context, "");
                sharedPreferences.setProfileImageBitmap3(context, "");
                sharedPreferences.setAbout(context, "");
                sharedPreferences.setHobbies(context, "");
                sharedPreferences.setHobbiesId(context, null);
                sharedPreferences.setBooleam(context, "false");
                sharedPreferences.setAvailable(context, "");
                sharedPreferences.setProfileImageBitmap1(context, "");
                sharedPreferences.setProfileImageBitmap2(context, "");
                sharedPreferences.setProfileImageBitmap3(context, "");
                dbAdapter.open();
                dbAdapter.delete();
                dbAdapter.close();
            }
        }


    }

    class AutoSlider extends TimerTask {

        @Override

        public void run() {

            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        if (splashAnimation.isRunning()) {
                            splashAnimation.stop();
                        } else {
                            splashAnimation.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO Auto-generated catch block
                    }


                }

            });

        }

    }


    class AutoChatHistory extends TimerTask {

        @Override

        public void run() {

            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        // PerformBackgroundTask this class is the class that extends AsynchTask
                        if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {

                            Date d = new Date();
                            CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                            datetime = String.valueOf(s);
                            Log.d("date and time", datetime);
                            new ChatHistory().execute();
                        }


                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }

            });

        }

    }


    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {

                                Date d = new Date();
                                CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                                datetime = String.valueOf(s);
                                Log.d("date and time", datetime);
                                new ChatHistory().execute();
                            }


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 7000); //execute in every 50000 ms
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (timer_chat != null) {

            timer_chat.cancel();


            timer_chat = null;

        }
    }


    private class ChatHistory extends AsyncTask<Void, Void, Boolean> {
        String status;
        private int UserId;
        private String unreadmessage;
        ArrayList<String> unreadmessage_list = new ArrayList<>();
        int count = 0;
        Exception error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                HashMap<String, Object> paramsfb = new HashMap<>();
                paramsfb.put(TAG_SESSIONID, sharedPreferences.getSessionid(context));
                paramsfb.put(TAG_TIME, datetime);

                json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_getchathistory), paramsfb);
                json_object = new JSONObject(json_string);
                if (json_object.has("getchathistory")) {
                    json_content = json_object.getJSONObject("getchathistory");


                    if (json_content.getString("status").equalsIgnoreCase("success")) {
                        unreadmessage_list.clear();

                        if (json_content.has("converation")) {
                            JSONArray hobbiesArray = json_content.getJSONArray("converation");
                            for (int i = 0; i < hobbiesArray.length(); i++) {
                                JSONObject details = hobbiesArray.getJSONObject(i);
                                if(details.has("unreadmessage"))
                                {
                                    unreadmessage = details.getString("unreadmessage");

                                }else
                                {
                                    unreadmessage="0";
                                }
                                status = "success";

                                if (!unreadmessage.equals("0")) {
                                    count++;
                                }


                            }


                        } else {


                            status = "No Conversaion Details Found";


                        }


                    } else if (json_content.getString("status").equalsIgnoreCase("failed")) {

                        status = "No Conversaion Details Found";
                    }
                } else if (json_object.has("error")) {
                    if (json_object.getString("error").equalsIgnoreCase("InvalidSession")) {

                        status = "InvalidSession";

                        if (timer_chat != null) {

                            timer_chat.cancel();


                            timer_chat = null;

                        }
                    }
                }
                return true;

            } catch (JSONException e) {
                error = e;
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            {


                if (result) {
                    try {
                        if (status != null) {
                            switch (status) {
                                case "success":
                                    dosomething_status_unread_meassge_count.setText(String.valueOf(count));
                                    Log.d("unreadmessage", "count" + count);

                                    if (count == 0) {
                                        dosomething_status_unread_meassge_count.setVisibility(View.GONE);
                                    } else {
                                        dosomething_status_unread_meassge_count.setVisibility(View.VISIBLE);
                                    }

                                    break;
                                case "No Conversaion Details Found":
                                    dosomething_status_unread_meassge_count.setVisibility(View.GONE);
                                    break;
                                case "InvalidSession":
                                    if (timer != null) {

                                        timer.cancel();


                                        timer = null;

                                    }
                                    break;
                            }
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("ConnectTimeoutException","Exception"+e);
                    }

                } else {
                    if (error != null) {
                        if (timer != null) {

                            timer.cancel();


                            timer = null;

                        }
                    }
                }

            }


        }
    }

    public void showHideBottomLayout(boolean isVisible) {
        if (!isVisible) {
            ((LinearLayout) findViewById(R.id.activity_dosomething_status_bottom_layout)).setVisibility(View.GONE);
        } else {
            ((LinearLayout) findViewById(R.id.activity_dosomething_status_bottom_layout)).setVisibility(View.VISIBLE);
        }
    }




    public void clickNearme(boolean slide)
    {
        if (slide) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(status_layout_pin.getWindowToken(), 0);
            settext("YES");
            if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context))

            {
                final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.dosomething_alert_save_changes);
                    TextView status_textview_save = (TextView) dialog.findViewById(R.id.status_textview_save);
                    TextView alert_textview_save = (TextView) dialog.findViewById(R.id.alert_textview_save);
                    TextView alert_textview_save_cancel = (TextView) dialog.findViewById(R.id.alert_textview_save_cancel);
                    status_textview_save.setText("Your GPS seems to be disabled, do you want to enable it?");
                    alert_textview_save.setText("Yes");
                    alert_textview_save_cancel.setText("No");
                    alert_textview_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    alert_textview_save_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                } else {
                    if (sharedPreferences.getBoolean(context).equals("false")) {
                        settext("YES");
                        sharedPreferences.setDosomething_filterImage_Visibility(context, "Yes");
                        sharedPreferences.setFilterGender(context, "");
                        sharedPreferences.setFilterAvailable(context, "");
                        sharedPreferences.setFilterStatus(context, "");
                        sharedPreferences.setFilterDistance(context, "");
                        sharedPreferences.setFilterAge(context, "");
                        status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin_active));
                        status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                        status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                        status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


                        somethingNearMe = new DoSomethingNearMe();
//                        fragmentTransaction.setCustomAnimations(R.anim.slide_out, R.anim.slide_out);

                        activity_dosomething_textview_toolbar_save.setVisibility(View.GONE);
                        activity_dosomething_imageview_filter_icon.setVisibility(View.VISIBLE);
                        fragmentTransaction.replace(R.id.detail_fragment, somethingNearMe);
                        fragmentTransaction.commit();
                        click_action15 = false;
                        click_action14 = false;
                        click_action12 = true;
                        click_action13 = false;
                    } else {
//                        fadeIn_alertdialogSave();
                        dialog_savechanges.show();
                    }
                }


            } else {
                NetworkCheck.alertdialog(context);
            }
            slide=false;
        }
    }

    private class GetUserDetails extends AsyncTask<Void, Void, Void> {

        private String first_name, last_name, gender, about;
        private String email;
        private String status;
        private String device;
        private String age;
        private int user_id;
        private String profile_id;
        private String image2;
        private String image1;
        private String image3;
        private String send_request;
        private String device_token;
        private String date_of_birth;

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


        }


        @Override

        protected Void doInBackground(Void... params) {


            try {
                HashMap<String, Object> paramsfb = new HashMap<>();
                paramsfb.put(TAG_SESSIONID, sessionid);

                paramsfb.put(TAG_PROFILEUSERID, profile_user_id);

                json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_getuserdetails), paramsfb);


                json_object = new JSONObject(json_string);


                if (json_object.has("getuserdetails")) {
                    json_content = json_object.getJSONObject("getuserdetails");

                    if (json_content.getString("status").equalsIgnoreCase("success")) {

//                            pDialog.dismiss();


                        JSONObject userlistobject = json_content.getJSONObject("userDetails");

                        user_id = userlistobject.getInt("user_id");

                        profile_id = userlistobject.getString("profile_id");
                        first_name = userlistobject.getString("first_name");

                        last_name = userlistobject.getString("last_name");

                        gender = userlistobject.getString("gender");

                        about = userlistobject.getString("about");

                        email = userlistobject.getString("email");

                        status = userlistobject.getString("status");

                        device = userlistobject.getString("device");

                        age = userlistobject.getString("age");

                        image2 = userlistobject.getString("image2");

                        image1 = userlistobject.getString("image1");
                        image3 = userlistobject.getString("image3");
                        send_request = userlistobject.getString("send_request");

                        device_token = userlistobject.getString("device_token");

                        date_of_birth = userlistobject.getString("date_of_birth");


                    } else if (json_content.getString("status").equalsIgnoreCase("failed")) {

                        status = "";

//                            pDialog.dismiss();

                    } else if (json_content.getString("status").equalsIgnoreCase("error")) {

                        status = "";

//                            pDialog.dismiss();

                    }

                } else if (json_object.has("error")) {
                    if (json_object.getString("error").equalsIgnoreCase("InvalidSession")) {
                        status = "";
                    }

                }

            } catch (JSONException e) {
                Log.e("Timeout Exception: ", e.toString());
                e.printStackTrace();

            }


            return null;

        }


        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);

            try {


                if (!status.equalsIgnoreCase("")) {


                    sharedPreferences.setFriendProfilePicture(context, image1);
                    sharedPreferences.setFriendFirstname(context, first_name);

                    sharedPreferences.setDosomething_filterImage_Visibility(context, "Yes");
                    sharedPreferences.setFilterGender(context, "");
                    sharedPreferences.setFilterAvailable(context, "");
                    sharedPreferences.setFilterStatus(context, "");
                    sharedPreferences.setFilterDistance(context, "");
                    sharedPreferences.setFilterAge(context, "");
                    status_ImageView_pin.setImageDrawable(getResources().getDrawable(R.drawable.pin_active));
                    status_ImageView_chat.setImageDrawable(getResources().getDrawable(R.drawable.chat));
                    status_ImageView_profile.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                    status_ImageView_setting.setImageDrawable(getResources().getDrawable(R.drawable.setting));
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    somethingNearMe = new DoSomethingNearMe();
                    activity_dosomething_textview_toolbar_save.setVisibility(View.GONE);
                    activity_dosomething_imageview_filter_icon.setVisibility(View.VISIBLE);
                    fragmentTransaction.replace(R.id.detail_fragment, somethingNearMe);
                    fragmentTransaction.commit();
                    click_action15 = false;
                    click_action14 = false;
                    click_action12 = true;
                    click_action13 = false;

                } else {
                    sharedPreferences.setLogin(context, "");
                    sharedPreferences.setEmail(context, "");
                    sharedPreferences.setDateofBirth(context, "");
                    sharedPreferences.setFirstname(context, "");
                    sharedPreferences.setDeviceToken(context, "");
                    sharedPreferences.setLastname(context, "");
                    sharedPreferences.setGender(context, "");
                    sharedPreferences.setPassword(context, "");
                    sharedPreferences.setUserId(context, "");
                    sharedPreferences.setSessionid(context, "");
                    sharedPreferences.setProfileId(context, "");
                    sharedPreferences.setNotifyMessage(context, "");
                    sharedPreferences.setNotifyVibration(context, "");
                    sharedPreferences.setNotifySound(context, "");
                    sharedPreferences.setProfilePicture(context, "");
                    sharedPreferences.setProfilePicture1(context, "");
                    sharedPreferences.setProfilePicture2(context, "");
                    sharedPreferences.setFBProfilePicture(context, "");
                    sharedPreferences.setAbout(context, "");
                    Intent i = new Intent(context, DoSomeThingLogin.class);
                    startActivity(i);
                    finish();
                    pd.dismiss();
                }


            } catch (Exception e) {
                Log.e("Timeout Exception: ", e.toString());
                e.printStackTrace();
            }


        }
    }






}
