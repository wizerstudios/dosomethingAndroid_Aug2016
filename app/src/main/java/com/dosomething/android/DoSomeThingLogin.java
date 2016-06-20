package com.dosomething.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dosomething.android.Beanclasses.HobbiesBean;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoSomeThingLogin extends Activity {
    RelativeLayout login_textview_relativelayout_facebook;
    TextView
            login_textview_login_facebookone,
            login_textview_login_facebooktwo,
            login_textview_login_email,
            login_textview_forgot_password,
            login_textview_create_account;

    EditText
            login_edittext_email,
            login_edittext_password;

    Button
            login_button_login_signin,
            login_button_login_back;
    SharedPrefrences sharedPreferences;
    Context context = this;
    String text;
    String text1;
    private ProgressDialog pDialog;
    private static String urlsignin = "http://wiztestinghost.com/dosomething/signin?";
    private static String url = "http://wiztestinghost.com/dosomething/register?";
    private static String urlupdateposition = "http://wiztestinghost.com/dosomething/updatelocation?";
    private static String urlCheck = "http://wiztestinghost.com/dosomething/checkuser?";
    private static final String TAG_TYPE = "type";
    private static final String TAG_FIRSTNAME = "first_name";
    private static final String TAG_LASTNAME = "last_name";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_PROFILEID = "profileId";
    private static final String TAG_DATEOFBIRTH = "dob";
    private static final String TAG_PROFILEIMAGE = "profileImage1";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGTITUDE = "longitude";
    private static final String TAG_DEVICE = "device";
    private static final String TAG_DEVICEID = "deviceid";
    private TransparentProgressDialog pd;
    ArrayList<HobbiesBean> img_list = new ArrayList<>();

    String type, firstname, lastname, email, password, showpassword, profileId, dob, profileImage, gender, age, device, json_string, json_string_updateposition, deviceid, response;
    String latitude;
    String longitude;
    Jsonfunctions jsonfunctions;
    JSONObject json_object, json_content, details;
    JSONObject json_object_updateposition, json_content_updateposition, details_updateposition;
    private TextView info;
    private Button activityregister_button_facebooklogin;
    // Creating Facebook CallbackManager Value
    public static CallbackManager callbackmanager;
    Boolean click_fb = false;
    Activity activity = this;
    ArrayList<String> dummy_hobbies_name;
    ArrayList<Integer> dummy_hobbies_image;
    private String user_id;
    private String about;
    private String status;
    private String image2;
    private String image1;
    private String image3;
    Handler handler = new Handler();
    Timer timer = new Timer();
    private AnimationDrawable splashAnimation;
    private ImageView kbv;
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            com.facebook.Profile profile = com.facebook.Profile.getCurrentProfile();
            displayMessage(profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };
    private CallbackManager callbackManager;
    private Dialog dialog;
    private TextView status_textview_availablenow;
    private TextView status_textview_accept_check;
    private Dialog progress_bar;
    private ImageView progress_bar_imageview;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (AccessToken.getCurrentAccessToken() != null) {
            System.out.println("Current user AccessToken " + AccessToken.getCurrentAccessToken());
            LoginManager.getInstance().logOut();
        }
        callbackManager = CallbackManager.Factory.create();
        getFbKeyHash(getPackageName());
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(com.facebook.Profile profile, com.facebook.Profile profile1) {
            }

        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        setContentView(R.layout.activity_do_some_thing_login);
        try
        {
            MyApplication application = (MyApplication) getApplication();
            mTracker = application.getDefaultTracker();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
//        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//        com.facebook.Profile.getCurrentProfile();
        Window window = getWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
        sharedPreferences = new SharedPrefrences();
        jsonfunctions = new Jsonfunctions(this);
        login_textview_relativelayout_facebook = (RelativeLayout) findViewById(R.id.login_textview_relativelayout_facebook);
        login_textview_login_email = (TextView) findViewById(R.id.login_textview_login_email);
        login_textview_forgot_password = (TextView) findViewById(R.id.login_textview_forgot_password);
        login_textview_create_account = (TextView) findViewById(R.id.login_textview_create_account);
        login_textview_login_facebookone = (TextView) findViewById(R.id.login_textview_login_facebookone);
        login_textview_login_facebooktwo = (TextView) findViewById(R.id.login_textview_login_facebooktwo);

        login_edittext_email = (EditText) findViewById(R.id.login_edittext_email);
        login_edittext_password = (EditText) findViewById(R.id.login_edittext_password);

        progress_bar = new Dialog(DoSomeThingLogin.this);
        progress_bar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress_bar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progress_bar.setContentView(R.layout.progress_bar);
        progress_bar_imageview = (ImageView) progress_bar.findViewById(R.id.progress_bar_imageview);
        progress_bar_imageview.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) progress_bar_imageview.getBackground();
        progress_bar.setCancelable(false);

        /*pd = new TransparentProgressDialog(context, getResources().getDrawable(R.drawable.loading));
        kbv = (ImageView) findViewById(R.id.image);
        kbv.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) kbv.getBackground();*/
        login_button_login_signin = (Button) findViewById(R.id.login_button_login_signin);
        login_button_login_back = (Button) findViewById(R.id.login_button_login_back);
        dialog = new Dialog(DoSomeThingLogin.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.alert_dialog);
        status_textview_availablenow = (TextView) dialog.findViewById(R.id.status_textview_availablenow);
        status_textview_accept_check = (TextView) dialog.findViewById(R.id.status_textview_accept_check);
        text_font_typeface();
        dummy_hobbies_name = new ArrayList<>();
        dummy_hobbies_name.add("");
        dummy_hobbies_image = new ArrayList<>();
        dummy_hobbies_image.add(R.drawable.pluis_icon);
        login_textview_relativelayout_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                    onFblogin();
                } else {
                    NetworkCheck.alertdialog(context);

                }
            }
        });
        login_textview_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DoSomeThingLogin.this, DoSomeThingCreateAccount.class);
                startActivity(i);
                finish();
            }
        });
        login_textview_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoSomeThingLogin.this, DoSomethingForgetPassword.class);
                startActivity(i);
                finish();
            }
        });
        login_button_login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_edittext_email.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "Please enter your email address to create account?", Toast.LENGTH_SHORT).show();


                    status_textview_availablenow.setText("Please enter your email address to Login");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                    /*final AlertDialog dialog = new AlertDialog.Builder(context).create();
                    dialog.setMessage("Please enter your email address to Login");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    dialog.show();*/
                } else if (!emailValidator(login_edittext_email.getText().toString())) {
//                    Toast.makeText(getApplicationContext(), "please enter valid email address to create account?", Toast.LENGTH_SHORT).show();

                    status_textview_availablenow.setText("please enter valid email address");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                   /* final AlertDialog dialog = new AlertDialog.Builder(context).create();
                    dialog.setMessage("please enter valid email address to create account");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    dialog.show();*/
                } else if (login_edittext_password.getText().toString().equals("")) {
//                    Toast.makeText(getApplicationContext(), "please enter  password to create account?", Toast.LENGTH_SHORT).show();

                    status_textview_availablenow.setText("please enter  password");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                } else {
                    if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                        sharedPreferences.setWalkThroughHobbies(context, "true");
                        sharedPreferences.setWalkThroughNearme(context, "true");
                        sharedPreferences.setWalkThroughHomescreen(context, "true");
                        sharedPreferences.setWalkThroughActivity(context, "true");
                        sharedPreferences.setWalkThroughchat(context, "true");
                        sharedPreferences.setWalkThroughMatch(context, "true");
                        type = "1";
                        profileId = "";
                        dob = "";
                        profileImage = "";
                        gender = "";
                        String text = login_edittext_email.getText().toString();
                        email = text;
                        sharedPreferences.setEmail(context, text);
                        String text1 = login_edittext_password.getText().toString();
                        password = text1;
                        sharedPreferences.setPassword(context, text1);
                        latitude = sharedPreferences.getLatitude(context);
                        longitude = sharedPreferences.getLongitude(context);
                        Log.d("ltude............", latitude + "--" + longitude);
                        device = "Android";
                        deviceid = sharedPreferences.getDeviceToken(context);
                        hide_keyboard(activity);
                        sharedPreferences.setLoginType(context, "Normal");
                        sharedPreferences.setFBProfilePicture(context, "");
                        new AsynDataClass().execute();
                    } else {
                        NetworkCheck.alertdialog(context);
                    }
                }

            }

        });
        login_button_login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoSomeThingLogin.this, SplashActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void onFblogin() {
        if (AccessToken.getCurrentAccessToken() != null) {
            System.out.println("Current user AccessToken " + AccessToken.getCurrentAccessToken());
            LoginManager.getInstance().logOut();
        }


        callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_birthday", "email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("Success");

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        System.out.println("JSON Result" + object);
                                        try {
                                            JSONObject imageObject = object.getJSONObject("picture").getJSONObject("data");
                                            String url = imageObject.getString("url");
                                            if (object.has("email")) {
                                                String emailfb = object.getString("email");
                                                email = emailfb;
                                                sharedPreferences.setEmail(context, email);
                                                System.out.println("email" + email);
                                            }
                                            if (object.has("id")) {
                                                String id = object.getString("id");
                                                profileId = id;
                                                sharedPreferences.setProfileId(context, id);
                                                System.out.println("profileId========" + id);
                                            }
                                            if (object.has("first_name")) {
                                                String first_namefb = object.getString("first_name");
                                                firstname = first_namefb;
                                                sharedPreferences.setFirstname(context, first_namefb);
                                                System.out.println("first_name" + first_namefb);
                                            }
                                            if (object.has("last_name")) {
                                                String last_name = object.getString("last_name");
                                                lastname = last_name;
                                                sharedPreferences.setLastname(context, last_name);
                                                System.out.println("last_name" + last_name);
                                            }
                                            if (object.has("gender")) {
                                                String genderfb = object.getString("gender");
                                                sharedPreferences.setGender(context, genderfb);
                                                gender = genderfb;
                                                System.out.println("gender" + genderfb);
                                            }
                                            if (object.has("birthday")) {
                                                String birthday = object.getString("birthday");
                                                sharedPreferences.setDateofBirth(context, birthday);
                                                dob = birthday;
                                                System.out.println("birthday" + birthday);
                                            }
                                            System.out.println("url url" + url);

                                            type = "2";
                                            password = "";
                                            latitude = sharedPreferences.getLatitude(context);
                                            longitude = sharedPreferences.getLongitude(context);
                                            device = "Android";
                                            deviceid = sharedPreferences.getDeviceToken(context);
                                            hide_keyboard(activity);
                                            sharedPreferences.setLoginType(context, "Facebook");
                                            new AsynCheckClass().execute();
                                            splashAnimation.start();
                                            click_fb = true;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,first_name,last_name,gender,email,birthday,picture");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.d("CANCEL", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("ERROR", error.toString());
                    }
                });
    }

    private void displayMessage(final Profile newProfile) {


        if (newProfile != null) {

            String image = String.valueOf(newProfile.getProfilePictureUri(1080, 1080));
            sharedPreferences.setFBProfilePicture(context, image);
            profileImage = image;
            Log.d("Imageeeeeeeee", image);


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackmanager.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
/////////To assign font typeface////////////

    private void text_font_typeface() {
        Typeface patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");
        Typeface patron_regular = Typeface.createFromAsset(getAssets(), "fonts/Patron-Regular.ttf");
        Typeface patron_medium = Typeface.createFromAsset(getAssets(), "fonts/Patron-Medium.ttf");
        Typeface myfonts_thin = Typeface.createFromAsset(getAssets(), "fonts/Patron-Thin.ttf");

        login_textview_login_email.setTypeface(patron_regular);
        login_textview_forgot_password.setTypeface(patron_regular);
        login_textview_create_account.setTypeface(patron_regular);

        login_edittext_email.setTypeface(patron_regular);
        login_edittext_password.setTypeface(patron_regular);

        login_textview_login_facebookone.setTypeface(patron_regular);
        login_textview_login_facebooktwo.setTypeface(patron_bold);
        login_button_login_signin.setTypeface(patron_bold);
        login_button_login_back.setTypeface(patron_bold);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void getFbKeyHash(String packageName) {
        try {
            @SuppressLint("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("YourKeyHash :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("YourKeyHash: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException ignored) {

        }
    }


    private class AsynDataClass extends AsyncTask<Void, Void, Boolean> {
        private String image1_thumb;
        private String image2_thumb;
        private String image3_thumb;
        private String registervia;
        Exception error;
        private String notification_message;
        private String notification_sound;
        private String notification_vibration;
        private String notification_match;
        private String SessionId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progress_bar.show();timer = new Timer();
            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation.start();

        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            HashMap<String, Object> params = new HashMap<>();
            params.put(TAG_TYPE, type);
            params.put(TAG_FIRSTNAME, firstname);
            params.put(TAG_LASTNAME, lastname);
            params.put(TAG_EMAIL, email);
            params.put(TAG_PASSWORD, password);
            params.put(TAG_PROFILEID, profileId);
            params.put(TAG_DATEOFBIRTH, dob);
            params.put(TAG_PROFILEIMAGE, profileImage);
            params.put(TAG_GENDER, gender);
            params.put(TAG_LATITUDE, latitude);
            params.put(TAG_LONGTITUDE, longitude);
            params.put(TAG_DEVICE, device);
            params.put(TAG_DEVICEID, deviceid);

            json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_signin), params);
            Log.v("jasonlogin=======>", String.valueOf(params));

            try {
                json_object = new JSONObject(json_string);
                json_content = json_object.getJSONObject("signin");

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
            // Dismiss the progress dialog
            if (result) {
                if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                    try {
                        if (json_object.has("signin")) {
                            if (json_content.getString("status").equalsIgnoreCase("success")) {

                                JSONArray userlist = json_content.getJSONArray("userDetails");
                                for (int i = 0; i < userlist.length(); i++) {
                                    JSONObject userlistobject = userlist.getJSONObject(i);
                                    if(userlistobject.has("user_id"))
                                    {
                                        user_id = userlistobject.getString("user_id");
                                        sharedPreferences.setUserId(context, user_id);

                                    }else
                                    {
                                        user_id="";
                                    }
                                    if(userlistobject.has("profile_id"))
                                    {
                                        profileId = userlistobject.getString("profile_id");

                                    }else
                                    {
                                        profileId="";
                                    }
                                    if(userlistobject.has("first_name"))
                                    {
                                        firstname = userlistobject.getString("first_name");

                                    }else
                                    {
                                        firstname="";
                                    }
                                    if(userlistobject.has("last_name"))
                                    {
                                        lastname = userlistobject.getString("last_name");

                                    }else
                                    {
                                        lastname="";
                                    }
                                    if(userlistobject.has("gender"))
                                    {
                                        gender = userlistobject.getString("gender");

                                    }else
                                    {
                                        gender="";
                                    }
                                    if(userlistobject.has("about"))
                                    {
                                        about = userlistobject.getString("about");

                                    }else
                                    {
                                        about="";
                                    }
                                    if(userlistobject.has("email"))
                                    {
                                        email = userlistobject.getString("email");

                                    }else
                                    {
                                        email="";
                                    }
                                    if(userlistobject.has("status"))
                                    {
                                        status = userlistobject.getString("status");

                                    }else
                                    {
                                        status="";
                                    }
                                    if(userlistobject.has("device"))
                                    {
                                        device = userlistobject.getString("device");

                                    }else
                                    {
                                        device="";
                                    }
                                    if(userlistobject.has("age"))
                                    {
                                        age = userlistobject.getString("age");

                                    }else
                                    {
                                        age="";
                                    }
                                    if(userlistobject.has("image2"))
                                    {
                                        image2 = userlistobject.getString("image2");

                                    }else
                                    {
                                        image2="";
                                    }
                                    if(userlistobject.has("image1"))
                                    {
                                        image1 = userlistobject.getString("image1");

                                    }else
                                    {
                                        image1="";
                                    }
                                    if(userlistobject.has("image3"))
                                    {
                                        image3 = userlistobject.getString("image3");

                                    }else
                                    {
                                        image3="";
                                    }

                                    if (userlistobject.has("image1_thumb")) {
                                        image1_thumb = userlistobject.getString("image1_thumb");

                                    }
                                    if (userlistobject.has("image2_thumb")) {
                                        image2_thumb = userlistobject.getString("image2_thumb");

                                    }
                                    if (userlistobject.has("image3_thumb")) {
                                        image3_thumb = userlistobject.getString("image3_thumb");

                                    }else
                                    {
                                        image3_thumb="";
                                    }
                                    if(userlistobject.has("device_token"))
                                    {
                                        deviceid = userlistobject.getString("device_token");

                                    }else
                                    {
                                        deviceid="";
                                    }
                                    if(userlistobject.has("registervia"))
                                    {
                                        registervia = userlistobject.getString("registervia");

                                    }else
                                    {
                                        registervia="";
                                    }
                                    sharedPreferences.setRegistervia(context, registervia);

                                    if(userlistobject.has("date_of_birth"))
                                    {
                                        dob = userlistobject.getString("date_of_birth");

                                    }else
                                    {
                                        dob="";
                                    }
                                    if(userlistobject.has("password"))
                                    {
                                        password = userlistobject.getString("password");

                                    }else
                                    {
                                        password="";
                                    }
                                    if(userlistobject.has("showpassword"))
                                    {
                                        showpassword = userlistobject.getString("showpassword");

                                    }else
                                    {
                                        showpassword="";
                                    }
                                    if(userlistobject.has("notification_message"))
                                    {
                                        notification_message = userlistobject.getString("notification_message");

                                    }else
                                    {
                                        notification_message="";
                                    }
                                    if(userlistobject.has("notification_sound"))
                                    {
                                        notification_sound = userlistobject.getString("notification_sound");

                                    }else
                                    {
                                        notification_sound="";
                                    }
                                    if(userlistobject.has("notification_vibration"))
                                    {
                                        notification_vibration = userlistobject.getString("notification_vibration");

                                    }else
                                    {
                                        notification_vibration="";
                                    }
                                    if(userlistobject.has("isMatch"))
                                    {
                                        notification_match = userlistobject.getString("isMatch");

                                    }else
                                    {
                                        notification_match="";
                                    }
                                    if(userlistobject.has("SessionId"))
                                    {
                                        SessionId = userlistobject.getString("SessionId");
                                    }else
                                    {
                                        SessionId="";
                                    }

                                    sharedPreferences.setPassword(context, password);
                                    sharedPreferences.setSessionid(context, SessionId);
                                    sharedPreferences.setFirstname(context, firstname);
                                    sharedPreferences.setLastname(context, lastname);
                                    sharedPreferences.setGender(context, gender);
                                    sharedPreferences.setShowPassword(context, showpassword);
                                    String date = dob;
//                    String date ="29/07/13";
                                    SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
                                    SimpleDateFormat output = new SimpleDateFormat("dd / MM / yyyy");
                                    try {
                                        Log.d("tripDate", date);
                                        Date oneWayTripDate = input.parse(date);                 // parse input
                                        String tripDate = output.format(oneWayTripDate);// format output
                                        Log.d("tripDate", tripDate);
                                        sharedPreferences.setDateofBirth(context, tripDate);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                /*kbv.setVisibility(View.GONE);*/
                                    progress_bar.dismiss();
                                    timer.cancel();
                                    splashAnimation.stop();
                                    sharedPreferences.setEmail(context, email);
                                    sharedPreferences.setLogin(context, "Yes");
                                    sharedPreferences.setProfilePicture(context, image1_thumb);
                                    sharedPreferences.setProfilePicture1(context, image2_thumb);
                                    sharedPreferences.setProfilePicture2(context, image3_thumb);
                                    sharedPreferences.setAbout(context, about);
                                    sharedPreferences.setNotifyMessage(context, notification_message);
                                    sharedPreferences.setNotifySound(context, notification_sound);
                                    sharedPreferences.setNotifyVibration(context, notification_vibration);
                                    sharedPreferences.setNotifyMatch(context, notification_match);
                                    Log.v("SessionId =======>", SessionId);
                                    Intent intent = new Intent(DoSomeThingLogin.this, DoSomethingStatus.class);
                                    startActivity(intent);
                                    finish();
//                            }

                                }
                            } else if (json_content.getString("status").equalsIgnoreCase("error")) {
//                            pDialog.dismiss();


                           /* kbv.setVisibility(View.GONE);*/
                                progress_bar.dismiss();
                                timer.cancel();
                                splashAnimation.stop();
//                            pd.dismiss();

                            } else if (json_content.getString("status").equalsIgnoreCase("failed")) {
//                            pDialog.dismiss();
//                            pd.dismiss();
                           /* kbv.setVisibility(View.GONE);*/
                                progress_bar.dismiss();
                                timer.cancel();
                                splashAnimation.stop();

                                status_textview_availablenow.setText(context.getResources().getString(R.string.emailandpassword_error));
                                status_textview_accept_check.setText("Dismiss");
                                status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            /*AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                            dialog.setMessage(context.getResources().getString(R.string.emailandpassword_error));
                            dialog.setPositiveButton(context.getResources().getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    // TODO Auto-generated method stub

                                }
                            });

                            dialog.show();*/

                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (error != null) {
                    progress_bar.dismiss();
                    timer.cancel();
                    splashAnimation.stop();
                }
            }


        }
    }

    private class AsynCheckClass extends AsyncTask<Void, Void, Boolean> {
        private String SessionId;
        private String image1_thumb;
        private String image2_thumb;
        private String image3_thumb;
        private String first_name;
        private String last_name;
        private String date_of_birth;
        private String registervia;
        Exception error;
        private String notification_message;
        private String notification_sound;
        private String notification_vibration;
        private String notification_match;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//            pDialog = new ProgressDialog(DoSomeThingLogin.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//            pd.show();
          /*  kbv.setVisibility(View.VISIBLE);*/
            progress_bar.show();
            timer = new Timer();
            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation.start();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            HashMap<String, Object> paramsCheck = new HashMap<>();
            paramsCheck.put(TAG_EMAIL, email);
            paramsCheck.put(TAG_TYPE, type);
            json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_checkuser), paramsCheck);
            Log.v("jason url=======>", String.valueOf(paramsCheck));
            try {
                json_object = new JSONObject(json_string);
                json_content = json_object.getJSONObject("checkuser");
                return true;
            } catch (JSONException e) {
                error=e;
                e.printStackTrace();
                return false;
            }

        }

        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);


            if(result)
            {
                if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {
                    try {
                        if (json_object.has("checkuser")) {
                            if (json_content.getString("status").equalsIgnoreCase("success")) {
                                sharedPreferences.setWalkThroughProfile(context, "false");
                                sharedPreferences.setWalkThroughProfilesave(context, "false");
                                sharedPreferences.setWalkThroughHobbies(context, "false");
                                sharedPreferences.setWalkThroughNearme(context, "false");
                                sharedPreferences.setWalkThroughHomescreen(context, "false");
                                sharedPreferences.setWalkThroughActivity(context, "false");
                                sharedPreferences.setWalkThroughchat(context, "false");
                                sharedPreferences.setWalkThroughMatch(context, "false");
//                            pd.dismiss();
                           /* kbv.setVisibility(View.GONE);*/
                                progress_bar.dismiss();
                                timer.cancel();
                                splashAnimation.stop();
                                Intent i = new Intent(DoSomeThingLogin.this, DoSomethingprofile.class);
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("value", dummy_hobbies_name);
                                bundle.putIntegerArrayList("value", dummy_hobbies_image);
                                i.putStringArrayListExtra("array_bundle_hobbies_name", dummy_hobbies_name);
                                i.putIntegerArrayListExtra("array_bundle_hobbies_image", dummy_hobbies_image);
                                startActivity(i);
                                finish();
                            } else if (json_content.getString("status").equalsIgnoreCase("error")) {
//                            pd.dismiss();
                                sharedPreferences.setWalkThroughHobbies(context, "true");
                                sharedPreferences.setWalkThroughNearme(context, "true");
                                sharedPreferences.setWalkThroughHomescreen(context, "true");
                                sharedPreferences.setWalkThroughActivity(context, "true");
                                sharedPreferences.setWalkThroughchat(context, "true");
                                sharedPreferences.setWalkThroughMatch(context, "true");
                                JSONArray sportsArray = json_content.getJSONArray("userDetails");
                                JSONObject firstSport = sportsArray.getJSONObject(0);

                                if (firstSport.has("SessionId")) {
                                    SessionId = firstSport.getString("SessionId");

                                }
                                if (firstSport.has("user_id")) {
                                    user_id = firstSport.getString("user_id");

                                }
                                if (firstSport.has("image1")) {
                                    image1 = firstSport.getString("image1");

                                }
                                if (firstSport.has("image2")) {
                                    image2 = firstSport.getString("image2");

                                }
                                if (firstSport.has("image3")) {
                                    image3 = firstSport.getString("image3");

                                }
                                if (firstSport.has("image1_thumb")) {
                                    image1_thumb = firstSport.getString("image1_thumb");

                                }
                                if (firstSport.has("image2_thumb")) {
                                    image2_thumb = firstSport.getString("image2_thumb");

                                }
                                if (firstSport.has("image3_thumb")) {
                                    image3_thumb = firstSport.getString("image3_thumb");

                                }
                                if (firstSport.has("first_name"))

                                {
                                    first_name = firstSport.getString("first_name");
                                    sharedPreferences.setFirstname(context, first_name);


                                }
                                if (firstSport.has("last_name")) {
                                    last_name = firstSport.getString("last_name");
                                    sharedPreferences.setLastname(context, last_name);
                                }


                                if (firstSport.has("gender")) {
                                    gender = firstSport.getString("gender");
                                    sharedPreferences.setGender(context, gender);
                                }

                                if (firstSport.has("about")) {
                                    about = firstSport.getString("about");
                                    sharedPreferences.setAbout(context, about);
                                }
                                if (firstSport.has("email")) {
                                    email = firstSport.getString("email");
                                    sharedPreferences.setEmail(context, email);

                                }
                                if (firstSport.has("status")) {
                                    status = firstSport.getString("status");
                                }
                                if (firstSport.has("date_of_birth")) {
                                    date_of_birth = firstSport.getString("date_of_birth");
                                }
                                if (firstSport.has("password")) {
                                    password = firstSport.getString("password");
                                }else
                                {
                                    password="";
                                }

                                if (firstSport.has("registervia")) {
                                    registervia = firstSport.getString("registervia");
                                }else
                                {
                                    registervia="";
                                }
                                if (firstSport.has("showpassword")) {
                                    showpassword = firstSport.getString("showpassword");
                                }else
                                {
                                    showpassword="";
                                }
                                if (firstSport.has("notification_message")) {
                                    notification_message = firstSport.getString("notification_message");
                                }else
                                {
                                    notification_message="";
                                }


                                if (firstSport.has("notification_sound")) {
                                    notification_sound = firstSport.getString("notification_sound");
                                }else
                                {
                                    notification_sound="";
                                }

                                if (firstSport.has("notification_vibration")) {
                                    notification_vibration = firstSport.getString("notification_vibration");
                                }else
                                {
                                    notification_vibration="";
                                }
                                if(firstSport.has("isMatch"))
                                {
                                    notification_match = firstSport.getString("isMatch");

                                }else
                                {
                                    notification_match="";
                                }
                                sharedPreferences.setShowPassword(context, showpassword);
                                sharedPreferences.setPassword(context, password);
                                sharedPreferences.setRegistervia(context, registervia);
                                sharedPreferences.setLogin(context, "Yes");
                                String date = date_of_birth;
//                    String date ="29/07/13";
                                SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat output = new SimpleDateFormat("dd / MM / yyyy");
                                try {
                                    Log.d("tripDate", date);
                                    Date oneWayTripDate = input.parse(date);                 // parse input
                                    String tripDate = output.format(oneWayTripDate);// format output
                                    Log.d("tripDate", tripDate);
                                    sharedPreferences.setDateofBirth(context, tripDate);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                          /*  kbv.setVisibility(View.GONE);*/
                                progress_bar.dismiss();
                                if (timer != null) {

                                    timer.cancel();


                                    timer = null;

                                }
                                splashAnimation.stop();
                                sharedPreferences.setSessionid(context, SessionId);
                                sharedPreferences.setUserId(context, user_id);
                                sharedPreferences.setProfilePicture(context, image1_thumb);
                                sharedPreferences.setProfilePicture1(context, image2_thumb);
                                sharedPreferences.setProfilePicture2(context, image3_thumb);
                                sharedPreferences.setNotifyMessage(context, notification_message);
                                sharedPreferences.setNotifySound(context, notification_sound);
                                sharedPreferences.setNotifyVibration(context, notification_vibration);
                                sharedPreferences.setNotifyMatch(context, notification_match);
                                Intent i = new Intent(DoSomeThingLogin.this, DoSomethingStatus.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }else
            {
                if(error!=null)
                {
                    progress_bar.dismiss();
                    timer.cancel();
                    splashAnimation.stop();
                }
            }

        }
    }

    private class AsynDataRegisterClass extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(DoSomeThingLogin.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {


                HashMap<String, Object> params = new HashMap<>();
                params.put(TAG_TYPE, type);
                params.put(TAG_FIRSTNAME, firstname);
                params.put(TAG_LASTNAME, lastname);
                params.put(TAG_EMAIL, email);
                params.put(TAG_PASSWORD, password);
                params.put(TAG_PROFILEID, profileId);
                params.put(TAG_DATEOFBIRTH, dob);
                params.put(TAG_PROFILEIMAGE, profileImage);
                params.put(TAG_GENDER, gender);
                params.put(TAG_LATITUDE, latitude);
                params.put(TAG_LONGTITUDE, longitude);
                params.put(TAG_DEVICE, device);
                params.put(TAG_DEVICEID, deviceid);
                json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_register), params);
                Log.v("jason url=======>", String.valueOf(params));
                try {
                    json_object = new JSONObject(json_string);
                    json_content = json_object.getJSONObject("register");


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
//            if (pDialog.isShowing())
//                pDialog.dismiss();
            if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {
                try {
                    if (json_object.has("register")) {
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
                            if (json_content.getString("Message").equalsIgnoreCase("Registred Successfully")) {
                                JSONArray sportsArray = json_content.getJSONArray("userDetails");
                                JSONObject firstSport = sportsArray.getJSONObject(0);
                                String SessionId = firstSport.getString("SessionId");
                                sharedPreferences.setSessionid(context, SessionId);
                                Intent i = new Intent(DoSomeThingLogin.this, DoSomethingprofile.class);
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("value", dummy_hobbies_name);
                                bundle.putIntegerArrayList("value", dummy_hobbies_image);
                                i.putStringArrayListExtra("array_bundle_hobbies_name", dummy_hobbies_name);
                                i.putIntegerArrayListExtra("array_bundle_hobbies_image", dummy_hobbies_image);
                                startActivity(i);
                                finish();
                            } else {
                                JSONArray sportsArray = json_content.getJSONArray("userDetails");
                                JSONObject firstSport = sportsArray.getJSONObject(0);
                                String SessionId = firstSport.getString("SessionId");
                                sharedPreferences.setSessionid(context, SessionId);
                                Intent i = new Intent(DoSomeThingLogin.this, DoSomethingprofile.class);

                                startActivity(i);
                                finish();
                            }

                        } else if (json_content.getString("status").equalsIgnoreCase("failed")) {
                            pDialog.dismiss();
                            pDialog = new ProgressDialog(DoSomeThingLogin.this);
                            pDialog.setMessage("Email id already exist");
                            pDialog.show();
//                        AlertDialog dialogBuilder =new AlertDialog.Builder(getApplatlicationContext()).create();
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
                        pDialog.dismiss();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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


}
