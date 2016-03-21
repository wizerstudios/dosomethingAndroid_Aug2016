package com.dosomething.android;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.auth.AccountHandle;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.dosomething.android.Beanclasses.Arts_hobbies;
import com.dosomething.android.Beanclasses.Food_hobbies;
import com.dosomething.android.Beanclasses.Pets_hobbies;
import com.dosomething.android.Beanclasses.Recreation_hobbies;
import com.dosomething.android.CommonClasses.ExpandableHeightGridView;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;

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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


public class DoSomethingprofile extends AppCompatActivity implements Profile_image_one_fragment.Profile_image_viewpager_dots_one,
        Profile_image_two_fragment.Profile_image_viewpager_dots_two, Profile_image_three_fragment.Profile_image_viewpager_dots_three {
    LinearLayout dotsLayout;
    Toolbar toolbar;
    ImageView
            profile_page_imageview_camera,
            profile_page_imageview_profile_image,
            profile_page_imageview_hobbies,
            grid_layout_profile_imageview_hobbies,
            profile_Cam;
    AccountHandle handle = null;
    RelativeLayout dosomething_account_confirmation_alert;
    LinearLayout dosomething_account_confirmation_linearlayout_alertdialog;
    TextView dosomething_account_confirmation_create, dosomething_account_confirmation_no, dosomething_account_confirmation_yes;
    EditText
            profile_page_edittext_firstname,
            profile_page_edittext_lastname,
            profile_page_edittext_about_you,
            profile_page_edittext_email,
            profile_page_edittext_password;

    TextView
            profile_page_textview_title_profile,
            profile_page_textview_firstname,
            profile_page_textview_lastname,
            profile_page_textview_dateofbirth,
            profile_page_textview_about_you,
            profile_page_textview_hobbies,
            profile_page_textview_title_account,
            profile_page_textview_email,
            profile_page_textview_password,
            profile_page_textview_title_notification,
            profile_page_textview_notification_message, profile_page_textview_notification_sound, profile_page_textview_notification_vibration,settings_page_textview_notification_match,
            profile_page_textview_datepicker,
            profile_page_textview_male,
            profile_page_textview_female,
            custom_toolbar_textview_save,
            grid_layout_profile_textview_name;

    Button
            profile_page_button_policy,
            profile_page_button_terms;

    FrameLayout
            profile_page_linearlayout_hobbies;
    StringBuilder hobbies_append_id;

    private static final int CAMERA_REQUEST = 1888;
    Bitmap bitmap;
    File file;
    Uri imageUri;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Date choosenDateFromUI;
    boolean genderBolean = false;
    Context context = this;
    ArrayList<Integer> name, empty;
    ArrayList<Integer> image;
    ArrayList<String> text;
    ExpandableHeightGridView profile_page_gridview_hobbies;
    Bundle bundle, value;

    String male = "one";
    String female = "one";
    LinearLayout profile_page_layout_notification_vibration;
    LinearLayout profile_page_layout_notification_sound;
    LinearLayout profile_page_layout_notification_message;
    CheckBox profile_page_checkbox_notification_vibration;
    CheckBox profile_page_checkbox_notification_sound;
    CheckBox profile_page_checkbox_notification_message;
    CheckBox profilepage_checkbox_notifiacation_match;
    private PagerAdapter mPagerAdapter;
    private List<ImageView> dots;
    Context mContext;
    private final static int NUM_PAGES = 3;
    ViewPager pager;
    LinearLayout viewpagerdots;
    private static String urlupdate = "http://wiztestinghost.com/dosomething/updateprofile?";
    private static String url = "http://wiztestinghost.com/dosomething/register?";
    private static final String TAG_TYPE = "type";
    private static final String TAG_FIRSTNAME = "first_name";
    private static final String TAG_LASTNAME = "last_name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_PROFILEID = "profileId";
    private static final String TAG_DATEOFBIRTH = "dob";
    private static final String TAG_PROFILEIMAGE1 = "profileImage1";
    private static final String TAG_PROFILEIMAGE2 = "profileImage2";
    private static final String TAG_PROFILEIMAGE3 = "profileImage3";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_ABOUT = "about";
    private static final String TAG_HOBBIES = "hobbies";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGTITUDE = "longitude";
    private static final String TAG_DEVICE = "device";
    private static final String TAG_DEVICEID = "deviceid";
    private static final String TAG_NOTIFICATIONMESSAGE = "notification_message";
    private static final String TAG_NOTIFICATIONSOUND = "notification_sound";
    private static final String TAG_NOTIFICATIONVIBRATION = "notification_vibration";
    private static final String TAG_NOTIFICATIONMATCH = "isMatch";
    private static final String TAG_SESSIONID = "sessionid";
    private static final String TAG_FBIMAGE = "fbimage";
    private TransparentProgressDialog pd;
    String firstname, lastname, sessionid, type, email, password, profileId, dob, profileImage1, profileImage2, profileImage3, gender, about, hobbies, device, sessionId, json_string, json_string_updateposition, notification_match, notification_message, notification_sound, notification_vibration, deviceid, response;
    String latitude;
    String longitude;
    String notification;
    JSONObject json_object, json_content, details;
    JSONObject json_object_updateposition, json_content_updateposition, details_updateposition;
    ArrayList<Integer> hobbies_list;
    ArrayList<Integer> hobbies_list_arts;
    ArrayList<String> hobbies_list_arts_name;
    ArrayList<String> hobbies_list_arts_image;
    ArrayList<Integer> hobbies_list_food;
    ArrayList<String> hobbies_list_food_name;
    ArrayList<String> hobbies_list_food_image;
    ArrayList<Integer> hobbies_list_pets;
    ArrayList<String> hobbies_list_pets_name;
    ArrayList<String> hobbies_list_pets_image;
    ArrayList<Integer> hobbies_list_recreation;
    ArrayList<String> hobbies_list_recreation_name;
    ArrayList<String> hobbies_list_recreation_image;
    ArrayList<String> hobbies_name;
    ArrayList<String> hobbies_image;
    ArrayList<Integer> hobbies_id;

    ArrayList<Arts_hobbies> hobbies_array_arts;
    //    ArrayList<Integer> hobbies_image_arts;
    ArrayList<Food_hobbies> hobbies_array_food;
    //    ArrayList<String> hobbies_image_food;
    ArrayList<Pets_hobbies> hobbies_array_pets;
    //    ArrayList<String> hobbies_image_pets;
    ArrayList<Recreation_hobbies> hobbies_array_recreation;
//    ArrayList<String> hobbies_image_recreation;

    int selectedposition;
    SharedPrefrences sharedPrefrences;
    Jsonfunctions jsonfunctions;
    private ProgressDialog pDialog;

    AQuery aQuery;
    private Date oneWayTripDate;
    String tripDate;
    private String sound;
    private String message;
    private String vibration;
    private String match;
    private String text2;
    LinearLayout profile_page_password_layout;
    LinearLayout dosomething_profile_mainlayout;
    Handler handler = new Handler();
    Timer timer = new Timer();
    private AnimationDrawable splashAnimation;
    private ImageView kbv;
    RelativeLayout image_relative;
    private Dialog dialog;
    private Dialog progress_bar;
    private TextView status_textview_availablenow;
    private TextView status_textview_accept_check;
    private Dialog dialog_createAccount;
    private String showpassword;
    private String profile_update_response;
RelativeLayout layout_walkthrough_profile;
RelativeLayout layout_walkthrough_profilesave;
    ImageView image_walkthrough_account_profilesave;
    ImageView image_walkthrough_account_profile;
    ImageView walkthrough_profile_imageView;
    ImageView walkthrough_profilesave_ImageView;
    TextView walkthrough_account_create_TextView;
    TextView walkthrough_profilesave_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_somethingprofile);
//        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        Window window = getWindow();

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

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.dosomething_navigation_icon);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        pd = new TransparentProgressDialog(context, getResources().getDrawable(R.drawable.loading));

        /*image_relative=(RelativeLayout)findViewById(R.id.image_relative);
        kbv = (ImageView) findViewById(R.id.image);
        kbv.setBackgroundResource(R.drawable.progress_drawable);

        splashAnimation = (AnimationDrawable) kbv.getBackground();*/
        name = new ArrayList<Integer>();

        image = new ArrayList<Integer>();
        hobbies_list = new ArrayList<>();
        sharedPrefrences = new SharedPrefrences();
        jsonfunctions = new Jsonfunctions(DoSomethingprofile.this);
        sessionid = sharedPrefrences.getSessionid(DoSomethingprofile.this);

        hobbies_array_arts = new ArrayList<>();

        Log.d("LLLL", "LLLL" + Arts_hobbies.class);
//        hobbies_image_arts = new ArrayList<>();
        hobbies_array_food = new ArrayList<>();
//        hobbies_image_food = new ArrayList<>();
        hobbies_array_pets = new ArrayList<>();
//        hobbies_image_pets = new ArrayList<>();
        hobbies_array_recreation = new ArrayList<>();
        hobbies_list_arts = new ArrayList<>();
        hobbies_list_food = new ArrayList<>();
        hobbies_list_pets = new ArrayList<>();
        hobbies_list_recreation = new ArrayList<>();

        hobbies_list_arts_name = new ArrayList<>();
        hobbies_list_food_name = new ArrayList<>();
        hobbies_list_pets_name = new ArrayList<>();
        hobbies_list_recreation_name = new ArrayList<>();

        hobbies_list_arts_image = new ArrayList<>();
        hobbies_list_food_image = new ArrayList<>();
        hobbies_list_pets_image = new ArrayList<>();
        hobbies_list_recreation_image = new ArrayList<>();
        hobbies_name = new ArrayList<>();
        hobbies_image = new ArrayList<>();
        hobbies_id = new ArrayList<>();
        hobbies_list.clear();
        name.clear();

        bundle = new Bundle();
        bundle = getIntent().getExtras();
        bundle.getIntegerArrayList("array_bundle_hobbies_image");

        hobbies_list.addAll(bundle.getIntegerArrayList("array_bundle_hobbies_image"));

        name.addAll(bundle.getIntegerArrayList("array_bundle_hobbies_image"));

        Log.d("ARTS", "AAAAHHHH" + hobbies_list);
        aQuery = new AQuery(DoSomethingprofile.this);
        assign_declaration();


        this.initialisePaging();
        addDots();
        selectDot(0);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(dosomething_profile_mainlayout.getWindowToken(), 0);






        // bundle value check condition
        if (hobbies_list == null) {
            if (bundle == null) {
                profile_page_imageview_hobbies.setVisibility(View.VISIBLE);
                profile_page_gridview_hobbies.setVisibility(View.GONE);
                Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHif1");
                //intent to hobbies page
                profile_page_imageview_hobbies.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPrefrences.setFirstname(context, profile_page_edittext_firstname.getText().toString());
                        sharedPrefrences.setLastname(context, profile_page_edittext_lastname.getText().toString());
                        sharedPrefrences.setDateofBirth(context, profile_page_textview_datepicker.getText().toString());
                        sharedPrefrences.setAbout(context, profile_page_edittext_about_you.getText().toString());
                        sharedPrefrences.setEmail(context, profile_page_edittext_email.getText().toString());
                        sharedPrefrences.setPassword(context, profile_page_edittext_password.getText().toString());
                        if (male.equals("two")) {

                            text2 = profile_page_textview_male.getText().toString();
                            sharedPrefrences.setGender(context, text2);
                            Log.d("cliclkm", "pressed");

                        } else if (female.equals("two")) {
                            text2 = profile_page_textview_female.getText().toString();
                            sharedPrefrences.setGender(context, text2);
                            Log.d("cliclkf", "pressed");
                        }
                        Intent i = new Intent(DoSomethingprofile.this, DoSomethingHobbies.class);
                        bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
                        Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                        startActivity(i);
                    }
                });
            } else {

                profile_page_imageview_hobbies.setVisibility(View.GONE);
                bundle = getIntent().getExtras();
                Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHelse1");



                if (name.get(0) == R.drawable.pluis_icon) {
                    profile_page_imageview_hobbies.setVisibility(View.VISIBLE);
                    profile_page_gridview_hobbies.setVisibility(View.GONE);
                    Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHif");
                    profile_page_imageview_hobbies.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            sharedPrefrences.setFirstname(context, profile_page_edittext_firstname.getText().toString());
                            sharedPrefrences.setLastname(context, profile_page_edittext_lastname.getText().toString());
                            sharedPrefrences.setDateofBirth(context, profile_page_textview_datepicker.getText().toString());
                            sharedPrefrences.setAbout(context, profile_page_edittext_about_you.getText().toString());
                            sharedPrefrences.setEmail(context, profile_page_edittext_email.getText().toString());
                            sharedPrefrences.setPassword(context, profile_page_edittext_password.getText().toString());
                            if (male.equals("two")) {

                                text2 = profile_page_textview_male.getText().toString();
                                sharedPrefrences.setGender(context, text2);
                                Log.d("cliclkm", "pressed");

                            } else if (female.equals("two")) {
                                text2 = profile_page_textview_female.getText().toString();
                                sharedPrefrences.setGender(context, text2);
                                Log.d("cliclkf", "pressed");
                            }
                            Intent i = new Intent(DoSomethingprofile.this, DoSomethingHobbies.class);
                            bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
                            Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                            i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                            startActivity(i);
                        }
                    });
                } else {
                    profile_page_imageview_hobbies.setVisibility(View.GONE);
                    profile_page_gridview_hobbies.setVisibility(View.VISIBLE);
                    Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHelse");
                    new Goodsin_Background().execute();

                }
                Log.d("BUNDLE", ">>>>>>>>>>>>>>>>" + name + "--" + image);

            }
        } else {
            if (bundle == null) {
                Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHelseif1");
                profile_page_imageview_hobbies.setVisibility(View.VISIBLE);
                profile_page_gridview_hobbies.setVisibility(View.GONE);

                profile_page_imageview_hobbies.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sharedPrefrences.setFirstname(context, profile_page_edittext_firstname.getText().toString());
                        sharedPrefrences.setLastname(context, profile_page_edittext_lastname.getText().toString());
                        sharedPrefrences.setDateofBirth(context, profile_page_textview_datepicker.getText().toString());
                        sharedPrefrences.setAbout(context, profile_page_edittext_about_you.getText().toString());
                        sharedPrefrences.setEmail(context, profile_page_edittext_email.getText().toString());
                        sharedPrefrences.setPassword(context, profile_page_edittext_password.getText().toString());
                        if (male.equals("two")) {
                            text2 = profile_page_textview_male.getText().toString();
                            sharedPrefrences.setGender(context, text2);
                            Log.d("cliclkm", "pressed");

                        } else if (female.equals("two")) {
                            text2 = profile_page_textview_female.getText().toString();
                            sharedPrefrences.setGender(context, text2);
                            Log.d("cliclkf", "pressed");
                        }
                        Intent i = new Intent(DoSomethingprofile.this, DoSomethingHobbies.class);
//                        bundle.putStringArrayList("array_bundle_hobbies_name", name);
                        bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
//                            i.putStringArrayListExtra("array_bundle_hobbies_name", name);
                        Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                        startActivity(i);

                    }
                });
            } else {
                Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHelseelse1");

                profile_page_imageview_hobbies.setVisibility(View.GONE);


                name.addAll(bundle.getIntegerArrayList("array_bundle_hobbies_image"));
                if (name.get(0) == R.drawable.pluis_icon) {
                    Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHelseif");
                    profile_page_imageview_hobbies.setVisibility(View.VISIBLE);
                    profile_page_gridview_hobbies.setVisibility(View.GONE);
                    profile_page_imageview_hobbies.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            name.clear();
                            Intent i = new Intent(DoSomethingprofile.this, DoSomethingHobbies.class);
                            sharedPrefrences.setFirstname(context, profile_page_edittext_firstname.getText().toString());
                            sharedPrefrences.setLastname(context, profile_page_edittext_lastname.getText().toString());
                            sharedPrefrences.setDateofBirth(context, profile_page_textview_datepicker.getText().toString());
                            sharedPrefrences.setAbout(context, profile_page_edittext_about_you.getText().toString());
                            sharedPrefrences.setEmail(context, profile_page_edittext_email.getText().toString());
                            sharedPrefrences.setPassword(context, profile_page_edittext_password.getText().toString());
                            if (male.equals("two")) {
                                text2 = profile_page_textview_male.getText().toString();
                                sharedPrefrences.setGender(context, text2);
                                Log.d("cliclkm", "pressed");

                            } else if (female.equals("two")) {
                                text2 = profile_page_textview_female.getText().toString();
                                sharedPrefrences.setGender(context, text2);
                                Log.d("cliclkf", "pressed");
                            }
                            bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
                            Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                            i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                            startActivity(i);
                        }
                    });
                } else {
                    Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHelseelse");
                    //custom grid view
                    profile_page_imageview_hobbies.setVisibility(View.GONE);
                    profile_page_gridview_hobbies.setVisibility(View.VISIBLE);

                    new Goodsin_Background().execute();

                }
                Log.d("BUNDLE", ">>>>>>>>>>>>>>>>" + name + "--" + image);


            }

        }

        profile_page_gridview_hobbies.setExpanded(true);
        if (sharedPrefrences.getLoginType(context).equals("Facebook")) {
            profile_page_edittext_firstname.setTextColor(getResources().getColor(R.color.text_grey));
            profile_page_edittext_firstname.setKeyListener(null);
            profile_page_edittext_lastname.setTextColor(getResources().getColor(R.color.text_grey));
            profile_page_edittext_lastname.setKeyListener(null);
            if (sharedPrefrences.getGender(context).equalsIgnoreCase("male")) {

                profile_page_textview_male.setTextColor(getResources().getColor(R.color.text_red));
                profile_page_textview_male.setClickable(false);
                profile_page_textview_male.setEnabled(false);
                profile_page_textview_female.setTextColor(getResources().getColor(R.color.text_grey));
                profile_page_textview_female.setClickable(false);
                profile_page_textview_female.setEnabled(false);
            } else if (sharedPrefrences.getGender(context).equalsIgnoreCase("female")) {
                profile_page_textview_male.setClickable(false);
                profile_page_textview_male.setEnabled(false);
                profile_page_textview_female.setTextColor(getResources().getColor(R.color.text_red));
                profile_page_textview_male.setTextColor(getResources().getColor(R.color.text_grey));
                profile_page_textview_female.setClickable(false);
                profile_page_textview_female.setEnabled(false);

            }
            if (!sharedPrefrences.getDateOfbirth(context).equals("")&& !profile_page_textview_datepicker.getText().toString().equals("DD / MM / YYYY")) {
                profile_page_textview_datepicker.setTextColor(getResources().getColor(R.color.text_grey));
                profile_page_textview_datepicker.setClickable(false);
                profile_page_textview_datepicker.setEnabled(false);

            }
            profile_page_edittext_email.setTextColor(getResources().getColor(R.color.text_grey));
            profile_page_edittext_email.setKeyListener(null);
            profile_page_password_layout.setVisibility(View.GONE);


        }
        profile_page_gridview_hobbies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("POSITION", "-------------->>>" + position + "--" + name.size());
                if (position == hobbies_list.size()) {
                    sharedPrefrences.setFirstname(context, profile_page_edittext_firstname.getText().toString());
                    sharedPrefrences.setLastname(context, profile_page_edittext_lastname.getText().toString());
                    sharedPrefrences.setDateofBirth(context, profile_page_textview_datepicker.getText().toString());
                    sharedPrefrences.setAbout(context, profile_page_edittext_about_you.getText().toString());
                    sharedPrefrences.setEmail(context, profile_page_edittext_email.getText().toString());
                    sharedPrefrences.setPassword(context, profile_page_edittext_password.getText().toString());
                    if (male.equals("two")) {
                        text2 = profile_page_textview_male.getText().toString();
                        sharedPrefrences.setGender(context, text2);
                        Log.d("cliclkm", "pressed");

                    } else if (female.equals("two")) {
                        text2 = profile_page_textview_female.getText().toString();
                        sharedPrefrences.setGender(context, text2);
                        Log.d("cliclkf", "pressed");
                    }
                    Intent i = new Intent(DoSomethingprofile.this, DoSomethingHobbies.class);
                    bundle = new Bundle();
                    bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
                    Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                    i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                    startActivity(i);
                    finish();
                }
            }
        });


        // male--female button
        gender_selection();

        //Typeface
        text_font_typeface();

        pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                sharedPrefrences.setFirstname(context, profile_page_edittext_firstname.getText().toString());
                sharedPrefrences.setLastname(context, profile_page_edittext_lastname.getText().toString());
                if (male.equals("two")) {

                    String text2 = profile_page_textview_male.getText().toString();
                    sharedPrefrences.setGender(context, text2);
                    Log.d("cliclkm", "pressed");

                } else if (female.equals("two")) {
                    String text2 = profile_page_textview_female.getText().toString();
                    sharedPrefrences.setGender(context, text2);
                    Log.d("cliclkf", "pressed");
                }
                sharedPrefrences.setDateofBirth(context, profile_page_textview_datepicker.getText().toString());
                sharedPrefrences.setAbout(context, profile_page_edittext_about_you.getText().toString());
                sharedPrefrences.setEmail(context, profile_page_edittext_email.getText().toString());
                sharedPrefrences.setPassword(context, profile_page_edittext_password.getText().toString());
                return false;
            }
        });
        profile_page_button_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoSomethingprofile.this, DoSomethingPrivacyPolicy.class);
                startActivity(intent);
            }
        });
        profile_page_button_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoSomethingprofile.this, DoSomethingTermsofUse.class);
                startActivity(intent);
            }
        });
        profile_page_checkbox_notification_vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profile_page_checkbox_notification_vibration.isChecked()) {
                    vibration = "Yes";
                } else {
                    vibration = "No";
                }
            }
        });
        profile_page_checkbox_notification_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profile_page_checkbox_notification_sound.isChecked()) {
                    sound = "Yes";
                } else {
                    sound = "No";
                }
            }
        });
        profile_page_checkbox_notification_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profile_page_checkbox_notification_message.isChecked()) {
                    message = "Yes";
                } else {
                    message = "No";
                }
            }
        });


        profilepage_checkbox_notifiacation_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profilepage_checkbox_notifiacation_match.isChecked()) {
                    match = "Yes";
                } else {
                    match = "No";
                }
            }
        });

        //upnavigation
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefrences.setLogin(context, "");
                sharedPrefrences.setEmail(context, "");
                sharedPrefrences.setDateofBirth(context, "");
                sharedPrefrences.setFirstname(context, "");
                sharedPrefrences.setDeviceToken(context, "");
                sharedPrefrences.setLastname(context, "");
                sharedPrefrences.setGender(context, "");
                sharedPrefrences.setPassword(context, "");
                sharedPrefrences.setUserId(context, "");
                sharedPrefrences.setSessionid(context, "");
                sharedPrefrences.setProfileId(context, "");
                sharedPrefrences.setNotifyMessage(context, "");
                sharedPrefrences.setNotifyVibration(context, "");
                sharedPrefrences.setNotifySound(context, "");
                sharedPrefrences.setProfilePicture(context, "");
                sharedPrefrences.setProfilePicture1(context, "");
                sharedPrefrences.setProfilePicture2(context, "");
                sharedPrefrences.setFBProfilePicture(context, "");
                sharedPrefrences.setProfileImageBitmap1(context, "");
                sharedPrefrences.setProfileImageBitmap2(context, "");
                sharedPrefrences.setProfileImageBitmap3(context, "");
                sharedPrefrences.setAbout(context, "");
                Intent upIntent = new Intent(DoSomethingprofile.this, DoSomeThingCreateAccount.class);
                startActivity(upIntent);
                finish();
            }
        });

        profile_page_edittext_firstname.setText(sharedPrefrences.getFirstName(context));
        profile_page_edittext_lastname.setText(sharedPrefrences.getLastname(context));
        if (sharedPrefrences.getGender(context).equalsIgnoreCase("male")) {
            profile_page_textview_male.setTextColor(getResources().getColor(R.color.text_red));
        } else if (sharedPrefrences.getGender(context).equalsIgnoreCase("female")) {
            profile_page_textview_female.setTextColor(getResources().getColor(R.color.text_red));
        }
        profile_page_edittext_email.setText(sharedPrefrences.getEMAIl(context));

        dosomething_account_confirmation_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_createAccount.dismiss();
            }
        });
        dosomething_account_confirmation_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_createAccount.dismiss();


                if (sharedPrefrences.getProfileID(context).equals("")) {
                    type = "1";
                    Log.d("oooo", "2" + type);
                } else if (!sharedPrefrences.getProfileID(context).equals("")) {
                    type = "2";
                    Log.d("oooo", "1" + type);
                }
                if (male.equals("two")) {

                    text2 = profile_page_textview_male.getText().toString();
                    sharedPrefrences.setGender(context, text2);
                    Log.d("cliclkm", "pressed");

                } else if (female.equals("two")) {
                    text2 = profile_page_textview_female.getText().toString();
                    sharedPrefrences.setGender(context, text2);
                    Log.d("cliclkf", "pressed");
                }



                profileId = sharedPrefrences.getProfileID(context);
                Log.d("oooo", profileId);
                String text = profile_page_edittext_firstname.getText().toString();
                String text1 = profile_page_edittext_lastname.getText().toString();
                firstname = text;
                lastname = text1;
                Log.d("oooo", "1" + firstname);
                Log.d("oooo", "1" + lastname);
                gender = sharedPrefrences.getGender(context);
                Log.d("oooo", "1" + gender);
                sharedPrefrences.setFirstname(context, text);
                sharedPrefrences.setLastname(context, text1);
                Log.d("oooo", "1" + profile_page_textview_datepicker.getText().toString());


                about = profile_page_edittext_about_you.getText().toString();
                sharedPrefrences.setAbout(context, about);
                hobbies = String.valueOf(hobbies_append_id);
                String date = profile_page_textview_datepicker.getText().toString();
                sharedPrefrences.setDateofBirth(context, date);
                SimpleDateFormat input = new SimpleDateFormat("dd / MM / yyyy");
                SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Log.d("tripDate", date);
                    oneWayTripDate = input.parse(date);                 // parse input
                    tripDate = output.format(oneWayTripDate);// format output
                    Log.d("tripDate", tripDate);
                    dob = tripDate;
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (!sharedPrefrences.getFBProfilePicture(context).equals("")) {
                    profileImage1 = sharedPrefrences.getFBProfilePicture(context);
                    Log.d("oooo", "1" + profileImage1);
                }

                email = profile_page_edittext_email.getText().toString();
                sharedPrefrences.setEmail(context, email);
                Log.d("oooo", "1" + email);

                sharedPrefrences.setEmail(context, email);
                password = profile_page_edittext_password.getText().toString();
                sharedPrefrences.setPassword(context, password);
                Log.d("oooo", "1" + password);
                device = "Android";
                deviceid = sharedPrefrences.getDeviceToken(context);
                latitude = sharedPrefrences.getLatitude(context);
                longitude = sharedPrefrences.getLongitude(context);
                if (vibration == null) {
                    notification_vibration = "Yes";
                } else {
                    notification_vibration = vibration;
                }
                if (sound == null) {
                    notification_sound = "Yes";
                } else {
                    notification_sound = sound;
                }
                if (message == null) {
                    notification_message = "Yes";
                } else {
                    notification_message = message;
                }

                if (match == null) {
                    notification_match = "Yes";
                } else {
                    notification_match = match;
                }

                updateprofile();


            }
        });
        custom_toolbar_textview_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (profile_page_edittext_firstname.getText().length() == 0) {


                    status_textview_availablenow.setText("Please Enter your First name");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();




                } else if (profile_page_edittext_lastname.getText().length() == 0) {


                    status_textview_availablenow.setText("Please Enter your Last name");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();





                } else if (profile_page_edittext_about_you.getText().length() == 0) {


                    status_textview_availablenow.setText("Say Something About you!!!");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();




                } else if (profile_page_textview_datepicker.getText().equals("DD / MM / YYYY")) {


                    status_textview_availablenow.setText("Please Enter your Birthday");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                } else if (sharedPrefrences.getGender(context).equals("")) {



                    status_textview_availablenow.setText("Please Choose your Gender");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();




                } else if (!sharedPrefrences.getLoginType(context).equals("Facebook")) {
                    if (profile_page_edittext_password.getText().length() == 0) {



                        status_textview_availablenow.setText("Please Enter password to register");
                        status_textview_accept_check.setText("Dismiss");
                        status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();




                    } else {

                        dialog_createAccount.show();

                    }
                } else {
                    dialog_createAccount.show();


                }

            }
        });


        profile_page_textview_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //custom datepicker
                datepickerdialog();
                sharedPrefrences.setDateofBirth(context, profile_page_textview_datepicker.getText().toString());
            }
        });

        profile_page_edittext_firstname.setText(sharedPrefrences.getFirstName(context));
        profile_page_edittext_lastname.setText(sharedPrefrences.getLastname(context));
        if (sharedPrefrences.getGender(context).equals("Male")) {
            profile_page_textview_male.setTextColor(getResources().getColor(R.color.red));
        } else if (sharedPrefrences.getGender(context).equals("Female")) {
            profile_page_textview_female.setTextColor(getResources().getColor(R.color.red));
        }
        if (sharedPrefrences.getDateOfbirth(context).equals("")) {
            profile_page_textview_datepicker.setText("DD / MM / YYYY");

        } else {
            profile_page_textview_datepicker.setText(sharedPrefrences.getDateOfbirth(context));

        }
        profile_page_edittext_about_you.setText(sharedPrefrences.getAbout(context));
        String text2 = sharedPrefrences.getEMAIl(context);
        profile_page_edittext_email.setText(text2);
        String text = sharedPrefrences.getpassword(context);
        profile_page_edittext_password.setText(text);

    }

    public void updateprofile() {
        if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {

            progress_bar.show();
            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation.start();
//            pd.show();
//            pd.setCancelable(true);

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(TAG_TYPE, type);
            params.put(TAG_PROFILEID, profileId);
            params.put(TAG_FIRSTNAME, firstname);
            params.put(TAG_LASTNAME, lastname);
            params.put(TAG_GENDER, gender);
            params.put(TAG_DATEOFBIRTH, dob);
            params.put(TAG_EMAIL, email);
            params.put(TAG_PASSWORD, password);
            params.put(TAG_HOBBIES, hobbies);
            params.put(TAG_LATITUDE, latitude);
            params.put(TAG_LONGTITUDE, longitude);
            params.put(TAG_ABOUT, about);
            params.put(TAG_DEVICE, device);
            params.put(TAG_DEVICEID, deviceid);
            params.put(TAG_NOTIFICATIONMESSAGE, notification_message);
            params.put(TAG_NOTIFICATIONSOUND, notification_sound);
            params.put(TAG_NOTIFICATIONVIBRATION, notification_vibration);
            params.put(TAG_NOTIFICATIONMATCH, notification_match);

            File file = null;

            if (!sharedPrefrences.getFBProfilePicture(context).equalsIgnoreCase("")) {
                params.put(TAG_PROFILEIMAGE1, profileImage1);
            } else {
                if (!sharedPrefrences.getProfilePicture(context).equalsIgnoreCase("")) {
                    try {
                        String image_url = sharedPrefrences.getProfilePicture(context);
                        image_url = image_url.replace(" ", "");
                        if (image_url != null && !image_url.equals("")) {
                            file = new File(Environment.getExternalStorageDirectory(),
                                    getResources().getString(R.string.app_name) + "profile_image" + ".jpg");

                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }


            File file2 = null;
            if (!sharedPrefrences.getProfilePicture1(context).equalsIgnoreCase("")) {
                try {
                    String image_url = sharedPrefrences.getProfilePicture1(context);
                    image_url = image_url.replace(" ", "");
                    if (image_url != null && !image_url.equals("")) {
                        file2 = new File(Environment.getExternalStorageDirectory(),
                                getResources().getString(R.string.app_name) + "profile_image1" + ".jpg");

                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            File file3 = null;
            if (!sharedPrefrences.getProfilePicture2(context).equalsIgnoreCase("")) {
                try {
                    String image_url = sharedPrefrences.getProfilePicture2(context);
                    image_url = image_url.replace(" ", "");
                    if (image_url != null && !image_url.equals("")) {
                        file3 =new File(Environment.getExternalStorageDirectory(),
                                getResources().getString(R.string.app_name) + "profile_image2" + ".jpg");
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            uploadProfilePicture(file, file2, file3, params);


            Log.v("jason url=======>", String.valueOf(params));
        }
    }



    private void uploadProfilePicture(final File file1,
                                      final File file2,
                                      final File file3,
                                      final HashMap<String, Object> params) {

        url = getString(R.string.dosomething_apilink_string_register);
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
                refreshProfileData();
            }
        }.execute();
    }



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
            Iterator myVeryOwnIterator = post_params.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
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
            profile_update_response =  sResponse;
        } catch (Exception e) {
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }

    public void refreshProfileData() {
        try {
            json_object = new JSONObject(profile_update_response);
            json_content = json_object.getJSONObject("register");
            if (json_object.has("register")) {
                if (json_content.getString("status").equalsIgnoreCase("success")) {
                    if (json_content.getString("Message").equalsIgnoreCase("Registred Successfully")) {
                        JSONArray sportsArray = json_content.getJSONArray("userDetails");
                        JSONObject firstSport = sportsArray.getJSONObject(0);
                        String SessionId;
                        if(firstSport.has("SessionId"))
                        {
                            SessionId = firstSport.getString("SessionId");

                        }else
                        {
                            SessionId="";
                        }
                        String user_id;
                        if(firstSport.has("user_id"))
                        {
                            user_id = firstSport.getString("user_id");

                        }else
                        {
                            user_id="";
                        }
                        String image1;
                        if(firstSport.has("image1"))
                        {
                             image1 = firstSport.getString("image1");

                        }else
                        {
                            image1="";
                        }
                        String image2;
                        if(firstSport.has("image2"))
                        {
                            image2 = firstSport.getString("image2");
                        }else
                        {
                            image2="";
                        }

                        String image3;
                      if(firstSport.has("image3"))
                      {
                          image3 = firstSport.getString("image3");

                      }else
                      {
                          image3="";
                      }
                        String notification_message;
                        if(firstSport.has("notification_message"))
                        {
                            notification_message = firstSport.getString("notification_message");

                        }else
                        {
                            notification_message="";
                        }
                        String notification_sound;
                        if(firstSport.has("notification_sound"))
                        {
                            notification_sound = firstSport.getString("notification_sound");

                        }else
                        {
                            notification_sound="";
                        }
                        String notification_vibration;
                        if(firstSport.has("notification_vibration"))
                        {
                            notification_vibration = firstSport.getString("notification_vibration");

                        }else
                        {
                            notification_vibration="";
                        }
                        String notification_match;
                        if(firstSport.has("isMatch"))
                        {
                            notification_match = firstSport.getString("isMatch");

                        }else
                        {
                            notification_match="";
                        }
                        String registervia;
                        if(firstSport.has("registervia"))
                        {
                            registervia = firstSport.getString("registervia");

                        }else
                        {
                            registervia="";
                        }

                        if(firstSport.has("showpassword"))
                        {
                            showpassword = firstSport.getString("showpassword");

                        }else
                        {
                            showpassword="";
                        }

                        sharedPrefrences.setShowPassword(context, showpassword);
                        sharedPrefrences.setRegistervia(context, registervia);
                        //                    String date ="29/07/13";
                        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat output = new SimpleDateFormat("dd / MM / yyyy");
                        try {
                            Log.d("tripDate", firstSport.getString("date_of_birth"));
                            Date oneWayTripDate = input.parse(firstSport.getString("date_of_birth"));                 // parse input
                            String tripDate = output.format(oneWayTripDate);// format output
                            Log.d("tripDate", tripDate);
                            sharedPrefrences.setDateofBirth(context, tripDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        sharedPrefrences.setSessionid(context, SessionId);
                        sharedPrefrences.setUserId(context, user_id);
                        sharedPrefrences.setProfilePicture(context, image1);
                        sharedPrefrences.setProfilePicture1(context, image2);
                        sharedPrefrences.setProfilePicture2(context, image3);
                        sharedPrefrences.setNotifyMessage(context, notification_message);
                        sharedPrefrences.setNotifySound(context, notification_sound);
                        sharedPrefrences.setNotifyVibration(context, notification_vibration);
                        sharedPrefrences.setNotifyMatch(context, notification_match);
                        sharedPrefrences.setLogin(context, email);
                        sharedPrefrences.setProfileImageBitmap1(context, "");
                        sharedPrefrences.setProfileImageBitmap2(context, "");
                        sharedPrefrences.setProfileImageBitmap3(context, "");
                        sharedPrefrences.setFBProfilePicture(context, "");
                        progress_bar.dismiss();
                        splashAnimation.stop();
                        Intent i = new Intent(DoSomethingprofile.this, DoSomethingStatus.class);
                        startActivity(i);
                        finish();
                    }
                } else if (json_content.getString("status").equalsIgnoreCase("failed")) {


                    progress_bar.dismiss();
                    splashAnimation.stop();
                }
            } else if (json_content.getString("status").equalsIgnoreCase("error")) {




                progress_bar.dismiss();

                splashAnimation.stop();


            }
        } catch (JSONException e) {
            System.out.println("TEST...INTERNET_DISCONNECT...EXPECTION");
            e.printStackTrace();
        }
    }






    public void photoCb(String url, String json, AjaxStatus status) {
        Log.d("photoCb....", "" + status.getMessage() + ".." + status.getCode());
        if (status.getCode() == 200) {
            Log.d("Response::", "" + json);

            try {
                json_object = new JSONObject(json);
                json_content = json_object.getJSONObject("register");
                if (json_object.has("register")) {
                    if (json_content.getString("status").equalsIgnoreCase("success")) {
                        if (json_content.getString("Message").equalsIgnoreCase("Registred Successfully")) {
                            JSONArray sportsArray = json_content.getJSONArray("userDetails");
                            JSONObject firstSport = sportsArray.getJSONObject(0);
                            String SessionId = firstSport.getString("SessionId");
                            String user_id = firstSport.getString("user_id");
                            String image1 = firstSport.getString("image1");
                            String image2 = firstSport.getString("image2");
                            String image3 = firstSport.getString("image3");
                            String notification_message = firstSport.getString("notification_message");
                            String notification_sound = firstSport.getString("notification_sound");
                            String notification_vibration = firstSport.getString("notification_vibration");
                            String notification_match = firstSport.getString("isMatch");
                            showpassword = firstSport.getString("showpassword");
                            sharedPrefrences.setShowPassword(context, showpassword);
                            //                    String date ="29/07/13";
                            SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
                            SimpleDateFormat output = new SimpleDateFormat("dd / MM / yyyy");
                            try {
                                Log.d("tripDate", firstSport.getString("date_of_birth"));
                                Date oneWayTripDate = input.parse(firstSport.getString("date_of_birth"));                 // parse input
                                String tripDate = output.format(oneWayTripDate);// format output
                                Log.d("tripDate", tripDate);
                                sharedPrefrences.setDateofBirth(context, tripDate);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            sharedPrefrences.setSessionid(context, SessionId);
                            sharedPrefrences.setUserId(context, user_id);
                            sharedPrefrences.setProfilePicture(context, image1);
                            sharedPrefrences.setProfilePicture1(context, image2);
                            sharedPrefrences.setProfilePicture2(context, image3);
                            sharedPrefrences.setNotifyMessage(context, notification_message);
                            sharedPrefrences.setNotifySound(context, notification_sound);
                            sharedPrefrences.setNotifyVibration(context, notification_vibration);
                            sharedPrefrences.setNotifyMatch(context, notification_match);
                            sharedPrefrences.setLogin(context, email);
                            sharedPrefrences.setProfileImageBitmap1(context, "");
                            sharedPrefrences.setProfileImageBitmap2(context, "");
                            sharedPrefrences.setProfileImageBitmap3(context, "");
                            sharedPrefrences.setFBProfilePicture(context, "");
//                            pd.dismiss();


                          /*  kbv.setVisibility(View.GONE);
                            image_relative.setVisibility(View.GONE);*/
                            progress_bar.dismiss();

                            splashAnimation.stop();
                            Intent i = new Intent(DoSomethingprofile.this, DoSomethingStatus.class);
                            startActivity(i);
                            finish();
                        }
                    } else if (json_content.getString("status").equalsIgnoreCase("failed")) {


                    /*    kbv.setVisibility(View.GONE);
                        image_relative.setVisibility(View.GONE);
*/
                        progress_bar.dismiss();
                        splashAnimation.stop();
//                            pDialog.dismiss();
//                        pd.dismiss();
//                            pDialog = new ProgressDialog(DoSomeThingCreateAccount.this);
//                            pDialog.setMessage("Email id already exist");
//                            pDialog.show();
//                        pd.show();
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



                 /*   kbv.setVisibility(View.GONE);
                    image_relative.setVisibility(View.GONE);*/
                    progress_bar.dismiss();

                    splashAnimation.stop();
//                        pDialog.dismiss();
//                    pd.dismiss();

                }
            } catch (JSONException e) {
                System.out.println("TEST...INTERNET_DISCONNECT...EXPECTION");
                e.printStackTrace();
            }
        }

    }


    ////////////To assign the font typeface//////////////////

    private void text_font_typeface() {
        Typeface patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");
        Typeface patron_regular = Typeface.createFromAsset(getAssets(), "fonts/Patron-Regular.ttf");
        Typeface patron_medium = Typeface.createFromAsset(getAssets(), "fonts/Patron-Medium.ttf");
        Typeface myfonts_thin = Typeface.createFromAsset(getAssets(), "fonts/Patron-Thin.ttf");


        profile_page_textview_title_profile.setTypeface(patron_medium);
        profile_page_textview_firstname.setTypeface(patron_medium);
        profile_page_textview_lastname.setTypeface(patron_medium);
        profile_page_textview_dateofbirth.setTypeface(patron_medium);
        profile_page_textview_about_you.setTypeface(patron_medium);
        profile_page_textview_hobbies.setTypeface(patron_medium);
        profile_page_textview_title_account.setTypeface(patron_medium);
        profile_page_textview_email.setTypeface(patron_medium);
        profile_page_textview_password.setTypeface(patron_medium);
        profile_page_textview_title_notification.setTypeface(patron_medium);
        profile_page_textview_notification_message.setTypeface(patron_medium);
        profile_page_textview_notification_sound.setTypeface(patron_medium);
        settings_page_textview_notification_match.setTypeface(patron_medium);
        profile_page_textview_notification_vibration.setTypeface(patron_medium);
        custom_toolbar_textview_save.setTypeface(patron_medium);
        profile_page_textview_datepicker.setTypeface(patron_medium);
        profile_page_textview_male.setTypeface(patron_regular);
        profile_page_textview_female.setTypeface(patron_regular);

        profile_page_edittext_firstname.setTypeface(patron_regular);
        profile_page_edittext_lastname.setTypeface(patron_regular);
        profile_page_edittext_about_you.setTypeface(patron_regular);
        profile_page_edittext_email.setTypeface(patron_regular);
        profile_page_edittext_password.setTypeface(patron_regular);

        profile_page_button_policy.setTypeface(patron_regular);
        profile_page_button_terms.setTypeface(patron_regular);
        dosomething_account_confirmation_create.setTypeface(patron_regular);
        dosomething_account_confirmation_no.setTypeface(patron_regular);
        dosomething_account_confirmation_yes.setTypeface(patron_regular);
        walkthrough_account_create_TextView.setTypeface(patron_regular);
        walkthrough_profilesave_TextView.setTypeface(patron_regular);
    }
    //////////profile picture////////////////

    @Override
    public void profile_Image_one() {
        viewpagerdots.setVisibility(View.VISIBLE);
    }

    @Override
    public void profile_Image_two() {
        viewpagerdots.setVisibility(View.VISIBLE);

    }

    @Override
    public void profile_Image_three() {
        viewpagerdots.setVisibility(View.VISIBLE);

    }


    public class PagerAdapter extends FragmentPagerAdapter {

        private List<android.support.v4.app.Fragment> fragments;


        public PagerAdapter(FragmentManager supportFragmentManager, List<android.support.v4.app.Fragment> fragments) {
            super(supportFragmentManager);
            this.fragments = fragments;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }

    private void initialisePaging() {

        List<android.support.v4.app.Fragment> fragments = new Vector<android.support.v4.app.Fragment>();
        fragments.add(android.support.v4.app.Fragment.instantiate(this, Profile_image_one_fragment.class.getName()));
        fragments.add(android.support.v4.app.Fragment.instantiate(this, Profile_image_two_fragment.class.getName()));
        fragments.add(android.support.v4.app.Fragment.instantiate(this, Profile_image_three_fragment.class.getName()));
        this.mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        pager = (ViewPager) super.findViewById(R.id.pager);
        pager.setAdapter(this.mPagerAdapter);
        pager.setOffscreenPageLimit(3);

    }

    public void addDots() {
        dots = new ArrayList<>();
        Log.d("num_pages", "______" + NUM_PAGES);
        for (int i = 0; i < NUM_PAGES; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageDrawable(getResources().getDrawable(R.drawable.dot1));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(2, 5, 2, 5);
            viewpagerdots.addView(dot, params);
            dots.add(dot);
        }
        pager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefrences.setFirstname(context, profile_page_edittext_firstname.getText().toString());
                sharedPrefrences.setLastname(context, profile_page_edittext_lastname.getText().toString());
                sharedPrefrences.setAbout(context, profile_page_edittext_about_you.getText().toString());
                sharedPrefrences.setPassword(context, profile_page_edittext_password.getText().toString());
            }
        });
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                selectDot(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void selectDot(int idx) {
        Resources res = getResources();
        for (int i = 0; i < NUM_PAGES; i++) {
            System.out.println("dots_pos..." + idx);
            int drawableId = (i == idx) ? (R.drawable.dot1) : (R.drawable.dot1_active);
            Drawable drawable = res.getDrawable(drawableId);
            drawable.setBounds(0, 0, 0, 0);
            dots.get(i).setImageDrawable(drawable);
        }
    }

    ///////To select gender ////////////

    private void gender_selection() {
        if (!genderBolean) {
            profile_page_textview_male.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    male = "two";
                    sharedPrefrences.setGender(context, profile_page_textview_male.getText().toString());
                    profile_page_textview_male.setTextColor(getResources().getColor(R.color.red));
                    profile_page_textview_female.setTextColor(getResources().getColor(R.color.text_grey));
                }
            });
            profile_page_textview_female.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    female = "two";
                    sharedPrefrences.setGender(context, profile_page_textview_female.getText().toString());

                    profile_page_textview_female.setTextColor(getResources().getColor(R.color.red));
                    profile_page_textview_male.setTextColor(getResources().getColor(R.color.text_grey));
                }
            });
            genderBolean = true;
        } else {
            profile_page_textview_male.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPrefrences.setGender(context, profile_page_textview_female.getText().toString());

                    profile_page_textview_male.setTextColor(getResources().getColor(R.color.text_grey));
                    profile_page_textview_female.setTextColor(getResources().getColor(R.color.red));
                }
            });
            profile_page_textview_female.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sharedPrefrences.setGender(context, profile_page_textview_male.getText().toString());

                    profile_page_textview_female.setTextColor(getResources().getColor(R.color.text_grey));
                    profile_page_textview_male.setTextColor(getResources().getColor(R.color.red));
                }
            });
            genderBolean = false;
        }


    }

    ///////////////variable declaration////////////////


    private void assign_declaration() {
//        profile_page_imageview_camera = (ImageView) findViewById(R.id.profile_page_imageview_camera);
        image_walkthrough_account_profilesave = (ImageView) findViewById(R.id.image_walkthrough_account_profilesave);
        image_walkthrough_account_profile = (ImageView) findViewById(R.id.image_walkthrough_account_profile);
        walkthrough_profile_imageView = (ImageView) findViewById(R.id.walkthrough_profile_imageView);
        walkthrough_profilesave_ImageView = (ImageView) findViewById(R.id.walkthrough_profilesave_ImageView);
        walkthrough_account_create_TextView=(TextView)findViewById(R.id.walkthrough_account_create_TextView);
        walkthrough_profilesave_TextView=(TextView)findViewById(R.id.walkthrough_profilesave_TextView);
        layout_walkthrough_profile=(RelativeLayout) findViewById(R.id.layout_walkthrough_profile);
        layout_walkthrough_profilesave=(RelativeLayout) findViewById(R.id.layout_walkthrough_profilesave);
        dosomething_profile_mainlayout = (LinearLayout) findViewById(R.id.dosomething_profile_mainlayout);
        profile_page_layout_notification_vibration = (LinearLayout) findViewById(R.id.profile_page_layout_notification_vibration);
        profile_page_layout_notification_sound = (LinearLayout) findViewById(R.id.profile_page_layout_notification_sound);
        profile_page_layout_notification_message = (LinearLayout) findViewById(R.id.profile_page_layout_notification_message);
        profile_page_checkbox_notification_vibration = (CheckBox) findViewById(R.id.profile_page_checkbox_notification_vibration);
        profile_page_checkbox_notification_sound = (CheckBox) findViewById(R.id.profile_page_checkbox_notification_sound);
        profile_page_checkbox_notification_message = (CheckBox) findViewById(R.id.profile_page_checkbox_notification_message);
        profilepage_checkbox_notifiacation_match = (CheckBox) findViewById(R.id.profilepage_checkbox_notifiacation_match);
        profile_page_imageview_hobbies = (ImageView) findViewById(R.id.profile_page_imageview_hobbies);
        profile_page_edittext_firstname = (EditText) findViewById(R.id.profile_page_edittext_firstname);
        profile_page_edittext_lastname = (EditText) findViewById(R.id.profile_page_edittext_lastname);
        profile_page_edittext_about_you = (EditText) findViewById(R.id.profile_page_edittext_about_you);
        profile_page_edittext_email = (EditText) findViewById(R.id.profile_page_edittext_email);
        profile_page_edittext_password = (EditText) findViewById(R.id.profile_page_edittext_password);
        profile_page_textview_title_profile = (TextView) findViewById(R.id.profile_page_textview_title_profile);
        profile_page_textview_firstname = (TextView) findViewById(R.id.profile_page_textview_firstname);
        profile_page_textview_lastname = (TextView) findViewById(R.id.profile_page_textview_lastname);
        profile_page_textview_dateofbirth = (TextView) findViewById(R.id.profile_page_textview_dateofbirth);
        profile_page_textview_about_you = (TextView) findViewById(R.id.profile_page_textview_about_you);
        profile_page_textview_hobbies = (TextView) findViewById(R.id.profile_page_textview_hobbies);
        profile_page_textview_title_account = (TextView) findViewById(R.id.profile_page_textview_title_account);
        profile_page_textview_email = (TextView) findViewById(R.id.profile_page_textview_email);
        profile_page_textview_password = (TextView) findViewById(R.id.profile_page_textview_password);
        profile_page_textview_title_notification = (TextView) findViewById(R.id.profile_page_textview_title_notification);
        profile_page_textview_notification_message = (TextView) findViewById(R.id.profile_page_textview_notification_message);
        profile_page_textview_notification_sound = (TextView) findViewById(R.id.profile_page_textview_notification_sound);
        profile_page_textview_notification_vibration = (TextView) findViewById(R.id.profile_page_textview_notification_vibration);
        settings_page_textview_notification_match = (TextView) findViewById(R.id.settings_page_textview_notification_match);
        profile_page_textview_datepicker = (TextView) findViewById(R.id.profile_page_textview_datepicker);
        profile_page_textview_male = (TextView) findViewById(R.id.profile_page_textview_male);
        profile_page_textview_female = (TextView) findViewById(R.id.profile_page_textview_female);
        custom_toolbar_textview_save = (TextView) findViewById(R.id.custom_toolbar_textview_save);

        profile_page_button_policy = (Button) findViewById(R.id.profile_page_button_policy);
        profile_page_button_terms = (Button) findViewById(R.id.profile_page_button_terms);

        profile_page_linearlayout_hobbies = (FrameLayout) findViewById(R.id.profile_page_linearlayout_hobbies);

        profile_page_gridview_hobbies = (ExpandableHeightGridView) findViewById(R.id.profile_page_gridview_hobbies);
        profile_page_password_layout = (LinearLayout) findViewById(R.id.profile_page_password_layout);


        /*dosomething_account_confirmation_alert = (RelativeLayout) findViewById(R.id.dosomething_account_confirmation_alert);
        dosomething_account_confirmation_create = (TextView) findViewById(R.id.dosomething_account_confirmation_create);
        dosomething_account_confirmation_no = (TextView) findViewById(R.id.dosomething_account_confirmation_no);
        dosomething_account_confirmation_yes = (TextView) findViewById(R.id.dosomething_account_confirmation_yes);*/
        progress_bar = new Dialog(DoSomethingprofile.this);
        progress_bar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress_bar.setContentView(R.layout.progress_bar);
        ImageView progress_bar_imageview = (ImageView) progress_bar.findViewById(R.id.progress_bar_imageview);
        progress_bar_imageview.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) progress_bar_imageview.getBackground();
        progress_bar.setCancelable(false);
        dialog = new Dialog(DoSomethingprofile.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        status_textview_availablenow = (TextView) dialog.findViewById(R.id.status_textview_availablenow);
        status_textview_accept_check = (TextView) dialog.findViewById(R.id.status_textview_accept_check);

        dialog_createAccount = new Dialog(DoSomethingprofile.this);
        dialog_createAccount.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_createAccount.setContentView(R.layout.dosomething_create_account_alert);
        dosomething_account_confirmation_create = (TextView) dialog_createAccount.findViewById(R.id.dosomething_account_confirmation_create);
        dosomething_account_confirmation_no = (TextView) dialog_createAccount.findViewById(R.id.dosomething_account_confirmation_no);
        dosomething_account_confirmation_yes = (TextView) dialog_createAccount.findViewById(R.id.dosomething_account_confirmation_yes);




//        profile_page_edittext_firstname.setText(sharedPreferences.getFirstName(context));
//        profile_page_edittext_lastname.setText(sharedPreferences.getLastname(context));
//        if (sharedPreferences.getGender(context).equals("Male")) {
//            profile_page_textview_male.setTextColor(getResources().getColor(R.color.red));
//        } else  if (sharedPreferences.getGender(context).equals("Female")){
//            profile_page_textview_female.setTextColor(getResources().getColor(R.color.red));
//        }
//        profile_page_textview_datepicker.setText(sharedPreferences.getDateOfbirth(context));
        viewpagerdots = (LinearLayout) findViewById(R.id.viewpager_dots);
//        profile_Cam=(ImageView)findViewById(R.id.profile_page_imageview_camera);

        if (sharedPrefrences.getFirstName(context).length() > 0) {
            profile_page_edittext_firstname.setText(sharedPrefrences.getFirstName(context));
        }

    }

/////////////// To select image and converting to bitmap/////////////


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
//                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//                int width = displayMetrics.widthPixels;
//                int height = displayMetrics.heightPixels;
                // Set the Image in ImageView after decoding the String
                Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
                Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
                Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 10);
                profile_page_imageview_profile_image.setImageBitmap(conv_bm);

            } else {
                final Bitmap photo = (Bitmap) data.getExtras().get("data");
                Bitmap resized = Bitmap.createScaledBitmap(photo, 200, 200, true);
                Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 10);
                profile_page_imageview_profile_image.setImageBitmap(conv_bm);
//                saveToInternalSorage(photo);
            }


        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
//                    .show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_profile__page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            sharedPrefrences.setLogin(context, "");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


////////////// To crop image and convert to bitmap///////////////

    public static Bitmap getRoundedRectanguleBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(100, 100, 100, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException | OutOfMemoryError ignored) {
        }
        return result;
    }

    ///////////////// custom alert datepicker /////////////////


    private void datepickerdialog() {

        @SuppressWarnings("RedundantCast") LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        @SuppressLint("InflateParams") View customView = inflater.inflate(R.layout.datepicker, null);
        dialogBuilder.setView(customView);
        final Calendar now = Calendar.getInstance();
        final DatePicker datePicker = (DatePicker) customView.findViewById(R.id.datepicker_dateofbirth);
        final SimpleDateFormat dateViewFormatter = new SimpleDateFormat("dd / MM / yyyy", Locale.US);
        final SimpleDateFormat formatter = new SimpleDateFormat("dd / MM / yyyy", Locale.US);
        Calendar minDate = Calendar.getInstance();
        Calendar maxdate = Calendar.getInstance();
        maxdate.add(Calendar.YEAR, 100);
        try {
            minDate.setTime(formatter.parse("01 / 01 / 1900"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datePicker.setMinDate(minDate.getTimeInMillis());
        datePicker.setMaxDate(maxdate.getTimeInMillis());


//        dialogBuilder.setTitle(Html.fromHtml("<font color='#09998c'>Select your DOB</font>"));
        final Calendar choosenDate = Calendar.getInstance();
        int year = choosenDate.get(Calendar.YEAR);
        int month = choosenDate.get(Calendar.MONTH);
        int day = choosenDate.get(Calendar.DAY_OF_MONTH);
        try {
            choosenDateFromUI = formatter.parse(profile_page_textview_datepicker.getText().toString());
            choosenDate.setTime(choosenDateFromUI);
            year = choosenDate.get(Calendar.YEAR);
            month = choosenDate.get(Calendar.MONTH);
            day = choosenDate.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar dateToDisplay = Calendar.getInstance();
        dateToDisplay.set(year, month, day);
        dialogBuilder.setPositiveButton(
                "Choose",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("*****", "-------" + choosenDateFromUI + "--" + choosenDate);
                        Calendar choosen = Calendar.getInstance();
                        choosen.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

                        profile_page_textview_datepicker.setText(dateViewFormatter.format(choosen.getTime()));

                        dialog.dismiss();
                    }
                }
        );
        final AlertDialog dialog = dialogBuilder.create();

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @SuppressWarnings("RedundantCast")
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar choosenDate = Calendar.getInstance();
                        choosenDate.set(year, monthOfYear, dayOfMonth);
                        Calendar mindate = new GregorianCalendar();
                        mindate.add(Calendar.YEAR, 0);
                        if (mindate.before(choosenDate)) {
                            ((Button) dialog.getButton(AlertDialog.BUTTON_POSITIVE)).setEnabled(false);
                        } else {
                            ((Button) dialog.getButton(AlertDialog.BUTTON_POSITIVE)).setEnabled(true);
                            ((Button) dialog.getButton(AlertDialog.BUTTON_POSITIVE)).setTextColor(getResources().getColor(R.color.red));
                        }
                    }
                }
        );
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.red));
            }
        });
        dialog.show();
    }

    private class CustomGrid extends BaseAdapter {

        Context context;
        ArrayList<String> hobbies_name;
        ArrayList<String> hobbies_image;

        public CustomGrid(DoSomethingprofile doSomethingprofile, ArrayList<String> image, ArrayList<String> name) {
            context = doSomethingprofile;
            hobbies_name = name;
            hobbies_image = image;
        }

        @Override
        public int getCount() {
            return hobbies_name.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View grid;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                grid = new View(context);
                grid = inflater.inflate(R.layout.grid_layout_profile, null);
                grid_layout_profile_textview_name = (TextView) grid.findViewById(R.id.grid_layout_profile_textview_name);
                grid_layout_profile_imageview_hobbies = (ImageView) grid.findViewById(R.id.grid_layout_profile_imageview_hobbies);
                //hobbies_image.add(String.valueOf(R.drawable.pluis_icon));
                //hobbies_name.add("");
                //hobbies_image.add(position + 1, (int)String.valueOf(R.drawable.pluis_icon));
                //hobbies_name.add(position + 1, String.valueOf(R.drawable.pluis_icon));

                grid_layout_profile_textview_name.setText(hobbies_name.get(position));
                Log.d("QWERTY", "--------------->>>>>" + hobbies_name.get(position));
                Log.d("QWERTYY", "--------------->>>>>" + hobbies_image.get(position));
//                grid_layout_imageview_hobbies.setImageResource(hobbies_image.get(position));
                aQuery.id(grid_layout_profile_imageview_hobbies).image(hobbies_image.get(position), true, true, 0, 0, new BitmapAjaxCallback() {
                    @Override
                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                        if (status.getCode() == 200) {
                            BitmapDrawable bd = new BitmapDrawable(bm);
                            iv.setBackgroundDrawable(bd);
                        }
                    }
                });


                grid_layout_profile_imageview_hobbies.setScaleType(ImageView.ScaleType.CENTER_CROP);

            } else {
                grid = (View) convertView;
            }
            return grid;
        }
    }

    public class AsyncProfileData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (NetworkCheck.isNetworkAvailable(context) && NetworkCheck.isWifiAvailable(context)) {
                HashMap<String, Object> profiledata = new HashMap<>();
                profiledata.put(TAG_FIRSTNAME, firstname);
                profiledata.put(TAG_LASTNAME, lastname);
                profiledata.put(TAG_DATEOFBIRTH, dob);
                profiledata.put(TAG_PROFILEIMAGE1, profileImage1);
                profiledata.put(TAG_PROFILEIMAGE2, profileImage2);
                profiledata.put(TAG_PROFILEIMAGE3, profileImage3);
                profiledata.put(TAG_GENDER, gender);
                profiledata.put(TAG_ABOUT, about);
                profiledata.put(TAG_HOBBIES, hobbies);
                profiledata.put(TAG_LATITUDE, latitude);
                profiledata.put(TAG_LONGTITUDE, longitude);
                profiledata.put(TAG_NOTIFICATIONMESSAGE, notification);
                profiledata.put(TAG_SESSIONID, sessionId);


                json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_updateprofile), profiledata);
                Log.v("jasonupdate=======>", String.valueOf(profiledata));

                try {
                    json_object = new JSONObject(json_string);
                    json_content = json_object.getJSONObject("updateprofile");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            //TODO Auto-generated method stub
            super.onPostExecute(result);
            try {

                if (NetworkCheck.isNetworkAvailable(context) && NetworkCheck.isWifiAvailable(context)) {
                    try {
                        if (json_object.has("updateprofile")) {
                            if (json_content.getString("status").equalsIgnoreCase("success")) {
                                JSONArray sportsArray = json_content.getJSONArray("userDetails");
                                JSONObject firstSport = sportsArray.getJSONObject(0);
                                String SessionId = firstSport.getString("SessionId");
//                                sharedPreferences.setSessionid(context, SessionId);
//                                Log.v("SessionId =======>", SessionId);
                                Intent intent = new Intent(DoSomethingprofile.this, DoSomethingStatus.class);
                                startActivity(intent);
                                finish();
                            } else if (json_content.getString("status").equalsIgnoreCase("failed")) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                dialog.setMessage(context.getResources().getString(R.string.profile_error));
                                dialog.setPositiveButton(context.getResources().getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                        // TODO Auto-generated method stub

                                    }
                                });

                                dialog.show();

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    ////////////////////////////////HOBBIES////////////

    class Goodsin_Background extends AsyncTask<String, String, String> {
        JSONObject opj;
        JSONArray content, hobbieslist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(DoSomethingprofile.this);
//            pDialog.setMessage("Loading......");
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        protected String doInBackground(String... params) {
            try {
                String tv1 = sharedPrefrences.getSessionid(DoSomethingprofile.this);
//                url = "http://wiztestinghost.com/dosomething/dosomethinglist?";
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("sessionid", tv1);
                response = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_gethobbies), map);
                opj = new JSONObject(response);
                Log.v("response_goods", response);
//                pDialog.dismiss();
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
                                    hobbies_array_arts.add(new Arts_hobbies(hobbies_id, category_id, name, image, image_active, false));
                                    Log.d("EEEEEEEE", "DDDDDDDD" + hobbies_array_arts);
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
                                    hobbies_array_food.add(new Food_hobbies(hobbies_id, category_id, name, image, image_active, false));
                                    Log.d("EEEEEEEE", "FFFFFFFFF" + hobbies_array_food);
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
                                    hobbies_array_pets.add(new Pets_hobbies(hobbies_id, category_id, name, image, image_active, false));
                                    Log.d("EEEEEEEE", "AAAAAAAA" + hobbies_array_pets);
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
                                    hobbies_array_recreation.add(new Recreation_hobbies(hobbies_id, category_id, name, image, image_active, false));
                                    Log.d("EEEEEEEE", "DDDDDDDD" + hobbies_array_recreation);
//                                    hobbies_image_recreation.add(image);
                                }
                            }
                        }
                        Log.d("hobbies_array_arts", "........" + hobbies_array_arts);
                        Log.d("hobbies_array_food", "........" + hobbies_array_food);
                        Log.d("hobbies_array_pets", "........" + hobbies_array_pets);
                        Log.d("hobbies_array_rec", "........" + hobbies_array_recreation);


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

        protected void onPostExecute(String result) {
            //TODO Auto-generated method stub
            super.onPostExecute(result);

            if (!hobbies_array_arts.isEmpty()) {
                for (Arts_hobbies myClass : hobbies_array_arts) {
                    hobbies_list_arts.add(myClass.getImage_id_arts());
                    hobbies_list_arts_name.add(myClass.getImage_name_arts());
                    hobbies_list_arts_image.add(myClass.getImag_inActive_arts());

                    Log.d("ARTS", "HHHHAAAA1" + hobbies_list_arts + "--" + hobbies_list_arts_name + "--" + hobbies_list_arts_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                }
            } else {
//                Toast.makeText(getApplicationContext(), "EMPTYa", Toast.LENGTH_LONG).show();
            }
            if (!hobbies_array_food.isEmpty()) {
                for (Food_hobbies myClass : hobbies_array_food) {
                    hobbies_list_food.add(myClass.getImage_id_food());
                    hobbies_list_food_name.add(myClass.getImage_name_food());
                    hobbies_list_food_image.add(myClass.getImag_inActive_food());
                    Log.d("ARTS", "HHHHAAAA2" + hobbies_list_food + "--" + hobbies_list_food_name + "--" + hobbies_list_food_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                }
            } else {
//                Toast.makeText(getApplicationContext(), "EMPTYf", Toast.LENGTH_LONG).show();
            }
            if (!hobbies_array_pets.isEmpty()) {
                for (Pets_hobbies myClass : hobbies_array_pets) {
                    hobbies_list_pets.add(myClass.getImage_id_pets());
                    hobbies_list_pets_name.add(myClass.getImage_name_pets());
                    hobbies_list_pets_image.add(myClass.getImag_inActive_pets());
                    Log.d("ARTS", "HHHHAAAA3" + hobbies_array_pets + "--" + hobbies_list_pets_name + "--" + hobbies_list_pets_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                }
            } else {
//                Toast.makeText(getApplicationContext(), "EMPTYp", Toast.LENGTH_LONG).show();
            }
            if (!hobbies_array_recreation.isEmpty()) {
                for (Recreation_hobbies myClass : hobbies_array_recreation) {
                    hobbies_list_recreation.add(myClass.getImage_id_recreation());
                    hobbies_list_recreation_name.add(myClass.getImage_name_recreation());
                    hobbies_list_recreation_image.add(myClass.getImag_inActive_recreation());
                    Log.d("ARTS", "HHHHAAAA3" + hobbies_list_recreation + "--" + hobbies_list_recreation_name + "--" + hobbies_list_recreation_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                }
            } else {
//                Toast.makeText(getApplicationContext(), "EMPTYr", Toast.LENGTH_LONG).show();
            }
            if (hobbies_list.size() >= 1) {
                for (int i = 0; i < hobbies_list_arts.size(); i++) {
//            Log.d("ARTS", "LISTyyy"+hobbies_array_arts.get(i).getImage_id_arts());
//            hobbies_list_arts.add(hobbies_array_arts.get(i).getImage_id_arts());
                    Log.d("ARTS", "LIST" + hobbies_list_arts);
//                    Log.d("ARTS","AAAA"+hobbies_list.get(i));
                    if (hobbies_list.contains(hobbies_list_arts.get(i))) {
                        hobbies_name.add(hobbies_list_arts_name.get(i));
                        hobbies_image.add(hobbies_list_arts_image.get(i));
                        hobbies_id.add(hobbies_list_arts.get(i));
                        Log.d("KKKK", "JJJJJa" + hobbies_image + "--" + hobbies_name + "--" + hobbies_id);
                    } else {
                        Log.d("KKKK", "JJJJJa" + "NM");
                    }
                }
                for (int i = 0; i < hobbies_list_food.size(); i++) {
                    if (hobbies_list.contains(hobbies_list_food.get(i))) {
                        hobbies_name.add(hobbies_list_food_name.get(i));
                        hobbies_image.add(hobbies_list_food_image.get(i));
                        hobbies_id.add(hobbies_list_food.get(i));
                        Log.d("KKKK", "JJJJJf" + hobbies_image + "--" + hobbies_name + "--" + hobbies_id);
                    } else {
                        Log.d("KKKK", "JJJJJf" + "NM");
                    }
                }
                for (int i = 0; i < hobbies_list_pets.size(); i++) {
                    if (hobbies_list.contains(hobbies_list_pets.get(i))) {
                        hobbies_name.add(hobbies_list_pets_name.get(i));
                        hobbies_image.add(hobbies_list_pets_image.get(i));
                        hobbies_id.add(hobbies_list_pets.get(i));
                        Log.d("KKKK", "JJJJJp" + hobbies_image + "--" + hobbies_name + "--" + hobbies_id);
                    } else {
                        Log.d("KKKK", "JJJJJp" + "NM");
                    }
                }
                for (int i = 0; i < hobbies_list_recreation.size(); i++) {
                    if (hobbies_list.contains(hobbies_list_recreation.get(i))) {
                        hobbies_name.add(hobbies_list_recreation_name.get(i));
                        hobbies_image.add(hobbies_list_recreation_image.get(i));
                        hobbies_id.add(hobbies_list_recreation.get(i));
                        Log.d("KKKK", "JJJJJr" + hobbies_image + "--" + hobbies_name + "--" + hobbies_id);
                    } else {
                        Log.d("KKKK", "JJJJJr" + "NM");
                    }
                }

                try
                {
                    hobbies_append_id = new StringBuilder();
                    for (Integer string : hobbies_id) {
//                    for (int i=0;i<hobbies_id.size();i++){
                        hobbies_append_id.append(",");
                        hobbies_append_id.append(string);

//                        hobbies_append_id.replace(hobbies_id.size()-1,0,"(,)*$");
                    }
                    Log.d("OOOOOOOOOO", "JJJJJrf" + hobbies_append_id);
                    hobbies_append_id.deleteCharAt(0);

//                }
//                hobbies_append_id = hobbies_append_id.replaceAll(" ,$", "");
//                System.out.println(hobbies_append_id);
//                if (hobbies_append_id.endsWith(",")) {
//                    names = names.substring(0, names.length() - 1);
//                }
                    Log.d("OOOOOOOOOO", "JJJJJrl" + hobbies_append_id);

                }catch (Exception e)
                {
                    e.printStackTrace();
                }


//                for (int i=0;i<=hobbies_list.size();i++){
////            Log.d("ARTS", "LISTyyy"+hobbies_array_food.get(i).getImage_id_food());
////            hobbies_list_food.add(hobbies_array_food.get(i).getImage_id_food());
//                    Log.d("FOOD", "LIST" + hobbies_list_food);
//                    if (hobbies_list_food.contains(hobbies_list.get(i))){
//                        hobbies_name.add(hobbies_array_food.get(i).getImage_name_food());
//                        hobbies_image.add(hobbies_array_food.get(i).getImag_inActive_food());
//                    }
//                }
//                for (int i=0;i<=hobbies_list.size();i++){
////            Log.d("ARTS", "LISTyyy"+hobbies_array_pets.get(i).getImage_id_pets());
////            hobbies_list_pets.add(hobbies_array_pets.get(i).getImage_id_pets());
//                    Log.d("PETS", "LIST" + hobbies_list_pets);
//                    if (hobbies_list_pets.contains(hobbies_list.get(i))){
//                        hobbies_name.add(hobbies_array_pets.get(i).getImage_name_pets());
//                        hobbies_image.add(hobbies_array_pets.get(i).getImag_inActive_pets());
//                    }
//                }
//
//                for (int i=0;i<=hobbies_list.size();i++){
////            Log.d("ARTS", "LISTyyy"+hobbies_array_recreation.get(i).getImage_id_recreation());
////            hobbies_list_recreation.add(hobbies_array_recreation.get(i).getImage_id_recreation());
//                    Log.d("RECREATION", "LIST" + hobbies_list_recreation);
//                    if (hobbies_list_recreation.contains(hobbies_list.get(i))){
//                        hobbies_name.add(hobbies_array_recreation.get(i).getImage_name_recreation());
//                        hobbies_image.add(hobbies_array_recreation.get(i).getImag_inActive_recreation());
//                    }
//                }
//                hobbies_name.add("");
//                hobbies_image.add(String.valueOf(R.drawable.pluis_icon));
            } else {
                hobbies_name.add("SAMPLE");
                hobbies_image.add("http://indiawebcoders.com//mobileapps//dosomething//uploads//hobbies//bbq.png");
//                Toast.makeText(getApplicationContext(), "Bundle empty", Toast.LENGTH_LONG).show();
            }
            Log.d("KKKK", "JJJJJ" + hobbies_image + "--" + hobbies_name);
            hobbies_image.add(String.valueOf(R.drawable.pluis_icon));
            hobbies_name.add("");
            CustomGrid customGrid = new CustomGrid(DoSomethingprofile.this, hobbies_image, hobbies_name);
            profile_page_gridview_hobbies.setAdapter(customGrid);
            profile_page_gridview_hobbies.setExpanded(true);

        }
    }


    public void facebookAccountValidation(Boolean aBoolean) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(sharedPrefrences.getWalkThroughprofile(context).equals("false"))
        {
            layout_walkthrough_profile.setVisibility(View.VISIBLE);
            sharedPrefrences.setWalkThroughProfile(context,"true");

        }else
        {
            if(sharedPrefrences.getWalkThroughProfilesave(context).equals("false"))
            {
                layout_walkthrough_profilesave.setVisibility(View.VISIBLE);
                sharedPrefrences.setWalkThroughProfilesave(context,"true");
            }
        }





        layout_walkthrough_profilesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_profilesave.setVisibility(View.GONE);
                sharedPrefrences.setWalkThroughProfilesave(context,"true");
            }
        });

        walkthrough_profilesave_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_profilesave.setVisibility(View.GONE);
                sharedPrefrences.setWalkThroughProfilesave(context,"true");
                if (profile_page_edittext_firstname.getText().length() == 0) {


                    status_textview_availablenow.setText("Please Enter your First name");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();




                } else if (profile_page_edittext_lastname.getText().length() == 0) {


                    status_textview_availablenow.setText("Please Enter your Last name");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();





                } else if (profile_page_edittext_about_you.getText().length() == 0) {


                    status_textview_availablenow.setText("Say Something About you!!!");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();




                } else if (profile_page_textview_datepicker.getText().equals("DD / MM / YYYY")) {


                    status_textview_availablenow.setText("Please Enter your Birthday");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                } else if (sharedPrefrences.getGender(context).equals("")) {



                    status_textview_availablenow.setText("Please Choose your Gender");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();




                } else if (!sharedPrefrences.getLoginType(context).equals("Facebook")) {
                    if (profile_page_edittext_password.getText().length() == 0) {



                        status_textview_availablenow.setText("Please Enter password to register");
                        status_textview_accept_check.setText("Dismiss");
                        status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();




                    } else {

                        dialog_createAccount.show();

                    }
                } else {
                    dialog_createAccount.show();


                }

            }
        });


        walkthrough_profile_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_profile.setVisibility(View.GONE);
                sharedPrefrences.setWalkThroughProfile(context,"true");
                if (profile_page_edittext_firstname.getText().length() == 0) {


                    status_textview_availablenow.setText("Please Enter your First name");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();




                } else if (profile_page_edittext_lastname.getText().length() == 0) {


                    status_textview_availablenow.setText("Please Enter your Last name");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();





                } else if (profile_page_edittext_about_you.getText().length() == 0) {


                    status_textview_availablenow.setText("Say Something About you!!!");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();




                } else if (profile_page_textview_datepicker.getText().equals("DD / MM / YYYY")) {


                    status_textview_availablenow.setText("Please Enter your Birthday");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                } else if (sharedPrefrences.getGender(context).equals("")) {



                    status_textview_availablenow.setText("Please Choose your Gender");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();




                } else if (!sharedPrefrences.getLoginType(context).equals("Facebook")) {
                    if (profile_page_edittext_password.getText().length() == 0) {



                        status_textview_availablenow.setText("Please Enter password to register");
                        status_textview_accept_check.setText("Dismiss");
                        status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();




                    } else {

                        dialog_createAccount.show();

                    }
                } else {
                    dialog_createAccount.show();


                }
            }
        });
        layout_walkthrough_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_profile.setVisibility(View.GONE);
                sharedPrefrences.setWalkThroughProfile(context,"true");
            }
        });

        profile_page_edittext_firstname.setText(sharedPrefrences.getFirstName(context));
        profile_page_edittext_lastname.setText(sharedPrefrences.getLastname(context));
        if (sharedPrefrences.getGender(context).equals("Male")) {
            profile_page_textview_male.setTextColor(getResources().getColor(R.color.red));
        } else if (sharedPrefrences.getGender(context).equals("Female")) {
            profile_page_textview_female.setTextColor(getResources().getColor(R.color.red));
        }
        if (sharedPrefrences.getDateOfbirth(context).equals("")) {
            profile_page_textview_datepicker.setText("DD / MM / YYYY");

        }
// else if(sharedPrefrences.getDateOfbirth(context).equals(sharedPrefrences.getDateOfbirth(context)))
//        {
//            profile_page_textview_datepicker.setText(sharedPrefrences.getDateOfbirth(context));
//
//        }


        else {
            SimpleDateFormat input = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat output = new SimpleDateFormat("dd / MM / yyyy");
            try {
                Log.d("tripDate", sharedPrefrences.getDateOfbirth(context));
                oneWayTripDate = input.parse(sharedPrefrences.getDateOfbirth(context));                 // parse input
                tripDate = output.format(oneWayTripDate);// format output
                Log.d("tripDate", tripDate);
                dob = tripDate;
                tripDate = sharedPrefrences.setDateofBirth(context, tripDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            profile_page_textview_datepicker.setText(sharedPrefrences.getDateOfbirth(context));

        }
        profile_page_edittext_about_you.setText(sharedPrefrences.getAbout(context));
        String text2 = sharedPrefrences.getEMAIl(context);
        profile_page_edittext_email.setText(text2);
        String text = sharedPrefrences.getpassword(context);
        profile_page_edittext_password.setText(text);
//        hobbies_list.clear();
//        name.clear();
//        bundle = getIntent().getExtras();
////        bundle.getStringArrayList("array_bundle_hobbies_name");
//        bundle.getIntegerArrayList("array_bundle_hobbies_image");
////        bundle.getIntegerArrayList("array_bundle_hobbies_image");
//
//        hobbies_list.addAll(bundle.getIntegerArrayList("array_bundle_hobbies_image"));
//        name.addAll(bundle.getIntegerArrayList("array_bundle_hobbies_image"));
//        Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHH"+bundle);


//        if (name.get(0)== R.drawable.pluis_icon) {
//            Log.d("GGGGGGGGGGG","HHHHHHHHHHHHHelseif");
//            profile_page_imageview_hobbies.setVisibility(View.VISIBLE);
//            profile_page_gridview_hobbies.setVisibility(View.GONE);
//            profile_page_imageview_hobbies.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    name.clear();
//                    Intent i = new Intent(DoSomethingprofile.this, DoSomethingHobbies.class);
////                            bundle.putStringArrayList("array_bundle_hobbies_name", name);
//                    bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
////                            i.putStringArrayListExtra("array_bundle_hobbies_name", name);
//                    Log.d(";;;;;;;", ":::::::::::" + name);
//                    i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
//                    startActivity(i);
//                }
//            });
//        } else {
//            Log.d("GGGGGGGGGGG","HHHHHHHHHHHHHelseelse");
//            //custom grid view
//            profile_page_imageview_hobbies.setVisibility(View.GONE);
//            profile_page_gridview_hobbies.setVisibility(View.VISIBLE);
//
//            new Goodsin_Background().execute();
//
//        }
    }


    private class AsynDataClass extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//            pDialog = new ProgressDialog(DoSomeThingCreateAccount.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
            pd.show();
        }

        @Override
        protected String doInBackground(String... voids) {
            if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {


                HashMap<String, Object> params = new HashMap<String, Object>();
                params.put(TAG_TYPE, type);
                params.put(TAG_PROFILEID, profileId);
                params.put(TAG_FIRSTNAME, firstname);
                params.put(TAG_LASTNAME, lastname);
                params.put(TAG_GENDER, gender);
                params.put(TAG_DATEOFBIRTH, dob);
                params.put(TAG_EMAIL, email);
                params.put(TAG_PASSWORD, password);
                params.put(TAG_HOBBIES, hobbies);
                params.put(TAG_LATITUDE, latitude);
                params.put(TAG_LONGTITUDE, longitude);
                params.put(TAG_ABOUT, about);
                params.put(TAG_DEVICE, device);
                params.put(TAG_DEVICEID, deviceid);
                params.put(TAG_NOTIFICATIONMESSAGE, notification_message);
                params.put(TAG_NOTIFICATIONSOUND, notification_sound);
                params.put(TAG_NOTIFICATIONVIBRATION, notification_vibration);

                File file;
                try {
                    String image_url = sharedPrefrences.getProfilePicture(context);
                    image_url = image_url.replace(" ", "");
                    if (image_url == null || image_url.equals("")) {
                        Log.d("BITMAP===>", "NULL");
                        Log.d("null", sharedPrefrences.getProfilePicture(context));
                    } else {
                        file = new File(sharedPrefrences.getProfilePicture(context));
                        Log.d("PATH====>", sharedPrefrences.getProfilePicture(context));
                        params.put(TAG_PROFILEIMAGE1, file);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                File file2;
                try {
                    String image_url = sharedPrefrences.getProfilePicture1(context);
                    image_url = image_url.replace(" ", "");
                    if (image_url == null || image_url.equals("")) {
                        Log.d("BITMAP===>", "NULL");
                        Log.d("null", sharedPrefrences.getProfilePicture1(context));
                    } else {
                        file2 = new File(sharedPrefrences.getProfilePicture1(context));
                        Log.d("PATH====>", sharedPrefrences.getProfilePicture1(context));
                        params.put(TAG_PROFILEIMAGE2, file2);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                File file3;
                try {
                    String image_url = sharedPrefrences.getProfilePicture2(context);
                    image_url = image_url.replace(" ", "");
                    if (image_url == null || image_url.equals("")) {
                        Log.d("BITMAP===>", "NULL");
                        Log.d("null", sharedPrefrences.getProfilePicture2(context));
                    } else {
                        file3 = new File(sharedPrefrences.getProfilePicture2(context));
                        Log.d("PATH====>", sharedPrefrences.getProfilePicture2(context));
                        params.put(TAG_PROFILEIMAGE2, file3);
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//                params.put(TAG_PROFILEIMAGE1, profileImage1);
//                params.put(TAG_PROFILEIMAGE2, profileImage2);
//                params.put(TAG_PROFILEIMAGE3, profileImage3);


//                BitmapFactory.Options bmOptions1 = new BitmapFactory.Options();
//                Bitmap bitmap1 = BitmapFactory.decodeFile(sharedPrefrences.getProfilePicture1(context), bmOptions1);
//                json_string = jsonfunctions.postImageWebservice(url, params, bitmap1, TAG_PROFILEIMAGE2);
//                BitmapFactory.Options bmOptions2 = new BitmapFactory.Options();
//                Bitmap bitmap2 = BitmapFactory.decodeFile(sharedPrefrences.getProfilePicture2(context), bmOptions2);
//                json_string = jsonfunctions.postImageWebservice(url, params, bitmap2, TAG_PROFILEIMAGE3);
                aQuery.auth(handle).ajax(getString(R.string.dosomething_apilink_string_register), params, JSONObject.class, this, "photoCb");
//                json_string = jsonfunctions.postToURL(url, params);

//
                Log.v("jason url=======>", String.valueOf(params));


//                try {
//                    json_object = new JSONObject(json_string);
//                    json_content = json_object.getJSONObject("register");
//                    if (json_object.has("register")) {
//                        if (json_content.getString("status").equalsIgnoreCase("success")) {
//                            if (json_content.getString("Message").equalsIgnoreCase("Registred Successfully")) {
//                                JSONArray sportsArray = json_content.getJSONArray("userDetails");
//                                JSONObject firstSport = sportsArray.getJSONObject(0);
//                                String SessionId = firstSport.getString("SessionId");
//                                String user_id = firstSport.getString("user_id");
//                                sharedPrefrences.setSessionid(context, SessionId);
//                                sharedPrefrences.setUserId(context, user_id);
//                                Intent i = new Intent(DoSomethingprofile.this, DoSomethingStatus.class);
//                                startActivity(i);
//                                finish();
//                            }
//                        } else if (json_content.getString("status").equalsIgnoreCase("failed")) {
////                            pDialog.dismiss();
//                            pd.dismiss();
////                            pDialog = new ProgressDialog(DoSomeThingCreateAccount.this);
////                            pDialog.setMessage("Email id already exist");
////                            pDialog.show();
//                            pd.show();
////                        AlertDialog dialogBuilder =new AlertDialog.Builder(getApplicationContext()).create();
////                        dialogBuilder.setTitle("Info");
////                        dialogBuilder.setMessage("Failed to login");
////                        dialogBuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                dialog.dismiss();
////                            }
////                        });
////                        dialogBuilder.show();
//                        }
//                    } else if (json_content.getString("status").equalsIgnoreCase("error")) {
////                        pDialog.dismiss();
//                        pd.dismiss();
//
//                    }
//                } catch (JSONException e) {
//                    System.out.println("TEST...INTERNET_DISCONNECT...EXPECTION");
//                    e.printStackTrace();
//                    return "ERROR_IN_CODE";
//                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();
            System.out.println("TEST...POST EXECUTE...UNUSED..." + result);
            if (result != null && result.equals("ERROR_IN_CODE")) {
                NetworkCheck.alertdialog(context);
            } else {

            }
        }
    }

    @Override
    protected void onPause() {
        Log.d("onPause", "onPauseonPause");
        super.onPause();
        sharedPrefrences.setFirstname(context, profile_page_edittext_firstname.getText().toString());
        sharedPrefrences.setLastname(context, profile_page_edittext_lastname.getText().toString());
        if (male.equals("two")) {

            String text2 = profile_page_textview_male.getText().toString();
            sharedPrefrences.setGender(context, text2);
            Log.d("cliclkm", "pressed");

        } else if (female.equals("two")) {
            String text2 = profile_page_textview_female.getText().toString();
            sharedPrefrences.setGender(context, text2);
            Log.d("cliclkf", "pressed");
        }
        sharedPrefrences.setDateofBirth(context, profile_page_textview_datepicker.getText().toString());
        sharedPrefrences.setAbout(context, profile_page_edittext_about_you.getText().toString());
        sharedPrefrences.setEmail(context, profile_page_edittext_email.getText().toString());
        sharedPrefrences.setPassword(context, profile_page_edittext_password.getText().toString());
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sharedPrefrences.setLogin(context, "");
        sharedPrefrences.setEmail(context, "");
        sharedPrefrences.setDateofBirth(context, "");
        sharedPrefrences.setFirstname(context, "");
        sharedPrefrences.setDeviceToken(context, "");
        sharedPrefrences.setLastname(context, "");
        sharedPrefrences.setGender(context, "");
        sharedPrefrences.setPassword(context, "");
        sharedPrefrences.setUserId(context, "");
        sharedPrefrences.setSessionid(context, "");
        sharedPrefrences.setProfileId(context, "");
        sharedPrefrences.setNotifyMessage(context, "");
        sharedPrefrences.setNotifyVibration(context, "");
        sharedPrefrences.setNotifySound(context, "");
        sharedPrefrences.setProfilePicture(context, "");
        sharedPrefrences.setProfilePicture1(context, "");
        sharedPrefrences.setProfilePicture2(context, "");
        sharedPrefrences.setFBProfilePicture(context, "");
        sharedPrefrences.setAbout(context, "");
        sharedPrefrences.setProfileImageBitmap1(context, "");
        sharedPrefrences.setProfileImageBitmap2(context, "");
        sharedPrefrences.setProfileImageBitmap3(context, "");
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
}
