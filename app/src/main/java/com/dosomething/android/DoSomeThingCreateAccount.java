package com.dosomething.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import org.json.JSONArray;
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

public class DoSomeThingCreateAccount extends Activity {
    TextView
            create_account_textview_signup_email,

    create_account_textview_terms,

    create_account_textview_privacy_policy,

    create_account_textview_signin,

    create_account_textview_create_account,

    create_account_textview_signup_facebookone,

    create_account_textview_signup_facebooktwo,

    create_account_textview_create_account_text;

    LinearLayout create_account_layout_create_account;

    EditText

            create_account_edittext_email,

    create_account_edittext_password;


    Button

            create_account_button_back;


    RelativeLayout

            create_account_textview_relativelayout_facebook;

    SharedPrefrences sharedPreferences;

    Context context = this;

    Activity activity = this;

    ArrayList<String> dummy_hobbies_name;

    ArrayList<Integer> dummy_hobbies_image;
    private TransparentProgressDialog pd;
    private static final String TAG_TYPE = "type";
    private static final String TAG_EMAIL = "email";

    String type, firstname, lastname, email, password, showpassword, profileId, dob, profileImage, gender, device, json_string, deviceid, response;
    String latitude;
    String longitude;
    Jsonfunctions jsonfunctions;
    JSONObject json_object, json_content;
    public static CallbackManager callbackmanager;
    Boolean click_fb = false;
    private CallbackManager callbackManager;
    Handler handler = new Handler();
    Timer timer = new Timer();
    private AnimationDrawable splashAnimation;
    private ImageView kbv;
    RelativeLayout layout_progress_image;
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
    private Dialog dialog;
    private Dialog progress_bar;
    private TextView status_textview_availablenow;
    private TextView status_textview_accept_check;
    RelativeLayout layout_walkthrough_account_create;
    ImageView walkthrough_account_create_imageView;
    TextView walkthrough_account_create_TextView;
    private Timer blink_time;
//

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
            protected void onCurrentProfileChanged(Profile profile, Profile profile1) {
                displayMessage(profile1);
            }

        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        setContentView(R.layout.activity_do_some_thing_create_account);
//        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

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
        sharedPreferences = new SharedPrefrences();
        jsonfunctions = new Jsonfunctions(this);
        pd = new TransparentProgressDialog(context, getResources().getDrawable(R.drawable.loading));
        /*kbv = (ImageView) findViewById(R.id.image);
        layout_progress_image=(RelativeLayout)findViewById(R.id.layout_progress_image);
        kbv.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) kbv.getBackground();*/
        layout_walkthrough_account_create = (RelativeLayout) findViewById(R.id.layout_walkthrough_account_create);
        walkthrough_account_create_imageView = (ImageView) findViewById(R.id.walkthrough_account_create_imageView);
        walkthrough_account_create_TextView = (TextView) findViewById(R.id.walkthrough_account_create_TextView);
        create_account_textview_signup_email = (TextView) findViewById(R.id.create_account_textview_signup_email);
        create_account_textview_terms = (TextView) findViewById(R.id.create_account_textview_terms);
        create_account_textview_privacy_policy = (TextView) findViewById(R.id.create_account_textview_privacy_policy);
        create_account_textview_signin = (TextView) findViewById(R.id.create_account_textview_signin);
        create_account_textview_create_account = (TextView) findViewById(R.id.create_account_textview_create_account);
        create_account_textview_signup_facebookone = (TextView) findViewById(R.id.create_account_textview_signup_facebookone);
        create_account_textview_signup_facebooktwo = (TextView) findViewById(R.id.create_account_textview_signup_facebooktwo);
        create_account_textview_create_account_text = (TextView) findViewById(R.id.create_account_textview_create_account_text);
        create_account_edittext_email = (EditText) findViewById(R.id.create_account_edittext_email);
        create_account_edittext_password = (EditText) findViewById(R.id.create_account_edittext_password);
        create_account_layout_create_account = (LinearLayout) findViewById(R.id.create_account_layout_create_account);
        create_account_button_back = (Button) findViewById(R.id.create_account_button_back);
        create_account_textview_relativelayout_facebook = (RelativeLayout) findViewById(R.id.create_account_textview_relativelayout_facebook);
        dialog = new Dialog(DoSomeThingCreateAccount.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        status_textview_availablenow = (TextView) dialog.findViewById(R.id.status_textview_availablenow);
        status_textview_accept_check = (TextView) dialog.findViewById(R.id.status_textview_accept_check);
        progress_bar = new Dialog(DoSomeThingCreateAccount.this);
        progress_bar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress_bar.setContentView(R.layout.progress_bar);
        ImageView progress_bar_imageview = (ImageView) progress_bar.findViewById(R.id.progress_bar_imageview);
        progress_bar_imageview.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) progress_bar_imageview.getBackground();
        progress_bar.setCancelable(false);
// Assign font typeface
        text_font_typeface();
        walkthrough_account_create_imageView.setBackgroundResource(R.drawable.blink_icon);
        splashAnimation = (AnimationDrawable) walkthrough_account_create_imageView.getBackground();

        if (sharedPreferences.getWalkThroughCreate(context).equals("false")) {
            layout_walkthrough_account_create.setVisibility(View.VISIBLE);
            blink_time = new Timer();
            blink_time.schedule(new Blink_progress(), 0, 340);
            splashAnimation.start();
            sharedPreferences.setWalkThroughCreate(context, "true");
        }


        walkthrough_account_create_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_account_create.setVisibility(View.GONE);
                sharedPreferences.setWalkThroughCreate(context, "true");
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation.stop();
                if (create_account_edittext_email.getText().toString().equals("")) {


                    status_textview_availablenow.setText("Please enter your email address to create account");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                    /*final AlertDialog dialog = new AlertDialog.Builder(context).create();
                    dialog.setMessage("Please enter your email address to create account");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    dialog.show();*/

                } else if (!emailValidator(create_account_edittext_email.getText().toString())) {


                    status_textview_availablenow.setText("please enter valid email address to create account");
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


                } else if (create_account_edittext_password.getText().toString().equals("")) {


                    status_textview_availablenow.setText("please enter  password to create account");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                    /*final AlertDialog dialog = new AlertDialog.Builder(context).create();
                    dialog.setMessage("please enter  password to create account");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    dialog.show();*/


                } else {
                    if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                        splashAnimation.start();
                        type = "1";
                        profileId = "";
                        dob = "";
                        profileImage = "";
                        gender = "";
                        sharedPreferences.setFirstname(context, "");
                        sharedPreferences.setLastname(context, "");
                        sharedPreferences.setProfilePicture(context, "");
                        sharedPreferences.setFBProfilePicture(context, "");
                        sharedPreferences.setGender(context, "");
                        sharedPreferences.setAbout(context, "");

                        email = create_account_edittext_email.getText().toString();
                        sharedPreferences.setEmail(context, email);
                        String text1 = create_account_edittext_password.getText().toString();
                        password = text1;
                        sharedPreferences.setPassword(context, text1);
                        latitude = sharedPreferences.getLatitude(context);
                        longitude = sharedPreferences.getLongitude(context);
                        device = "Android";
                        deviceid = sharedPreferences.getDeviceToken(context);
                        hide_keyboard(activity);
                        sharedPreferences.setLoginType(context, "Normal");
                        new Dosomething_ChechUser_Api().execute();


                    } else {
                        NetworkCheck.alertdialog(context);

                    }
                }
            }
        });

        layout_walkthrough_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_account_create.setVisibility(View.GONE);
                sharedPreferences.setWalkThroughCreate(context, "true");
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation.stop();


            }
        });
        //to process bundle value in hobbies page
        dummy_hobbies_name = new ArrayList<>();
        dummy_hobbies_name.add("");
        dummy_hobbies_image = new ArrayList<>();
        dummy_hobbies_image.add(R.drawable.pluis_icon);
        create_account_textview_relativelayout_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {
                    onFblogin();
                    sharedPreferences.setAbout(context, "");
                } else {
                    NetworkCheck.alertdialog(context);

                }
            }
        });
        create_account_textview_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoSomeThingCreateAccount.this, DoSomethingTermsofUse.class);
                startActivity(i);
            }
        });
        create_account_textview_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoSomeThingCreateAccount.this, DoSomethingPrivacyPolicy.class);
                startActivity(i);
            }
        });

        create_account_layout_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (create_account_edittext_email.getText().toString().equals("")) {


                    status_textview_availablenow.setText("Please enter your email address to create account");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                    /*final AlertDialog dialog = new AlertDialog.Builder(context).create();
                    dialog.setMessage("Please enter your email address to create account");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    dialog.show();*/

                } else if (!emailValidator(create_account_edittext_email.getText().toString())) {


                    status_textview_availablenow.setText("please enter valid email address to create account");
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


                } else if (create_account_edittext_password.getText().toString().equals("")) {


                    status_textview_availablenow.setText("please enter  password to create account");
                    status_textview_accept_check.setText("Dismiss");
                    status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                    /*final AlertDialog dialog = new AlertDialog.Builder(context).create();
                    dialog.setMessage("please enter  password to create account");
                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                    dialog.show();*/


                } else {
                    if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                        splashAnimation.start();
                        type = "1";
                        profileId = "";
                        dob = "";
                        profileImage = "";
                        gender = "";
                        sharedPreferences.setFirstname(context, "");
                        sharedPreferences.setLastname(context, "");
                        sharedPreferences.setProfilePicture(context, "");
                        sharedPreferences.setFBProfilePicture(context, "");
                        sharedPreferences.setGender(context, "");
                        sharedPreferences.setAbout(context, "");

                        email = create_account_edittext_email.getText().toString();
                        sharedPreferences.setEmail(context, email);
                        String text1 = create_account_edittext_password.getText().toString();
                        password = text1;
                        sharedPreferences.setPassword(context, text1);
                        latitude = sharedPreferences.getLatitude(context);
                        longitude = sharedPreferences.getLongitude(context);
                        device = "Android";
                        deviceid = sharedPreferences.getDeviceToken(context);
                        hide_keyboard(activity);
                        sharedPreferences.setLoginType(context, "Normal");
                        new Dosomething_ChechUser_Api().execute();


                    } else {
                        NetworkCheck.alertdialog(context);

                    }
                }
            }
        });
        create_account_textview_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoSomeThingCreateAccount.this, DoSomeThingLogin.class);
                startActivity(i);
                finish();
            }
        });
        create_account_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoSomeThingCreateAccount.this, SplashActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
// Assign font typeface

    private void text_font_typeface() {
        Typeface patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");
        Typeface patron_regular = Typeface.createFromAsset(getAssets(), "fonts/Patron-Regular.ttf");
        Typeface patron_medium = Typeface.createFromAsset(getAssets(), "fonts/Patron-Medium.ttf");
        Typeface myfonts_thin = Typeface.createFromAsset(getAssets(), "fonts/Patron-Thin.ttf");

        create_account_textview_signup_email.setTypeface(patron_regular);
        create_account_textview_terms.setTypeface(patron_bold);
        create_account_textview_privacy_policy.setTypeface(patron_bold);
        create_account_textview_signin.setTypeface(patron_bold);
        create_account_textview_create_account.setTypeface(patron_bold);
        create_account_textview_create_account_text.setTypeface(patron_regular);
        create_account_textview_signup_facebookone.setTypeface(patron_regular);
        create_account_textview_signup_facebooktwo.setTypeface(patron_bold);

        create_account_edittext_email.setTypeface(patron_regular);
        create_account_edittext_password.setTypeface(patron_regular);
        create_account_button_back.setTypeface(patron_bold);
        status_textview_availablenow.setTypeface(patron_bold);
        status_textview_accept_check.setTypeface(patron_bold);
        walkthrough_account_create_TextView.setTypeface(patron_regular);
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



    class Blink_progress extends TimerTask{

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



    private void onFblogin() {

        if (AccessToken.getCurrentAccessToken() != null) {
            System.out.println("Current user AccessToken " + AccessToken.getCurrentAccessToken());
            LoginManager.getInstance().logOut();
        }


        callbackmanager = CallbackManager.Factory.create();
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
                                            System.out.println("url url" + url);
                                            if (object.has("email")) {
                                                String email_fb = object.getString("email");
                                                email = email_fb;
                                                sharedPreferences.setEmail(context, email_fb);
                                                System.out.println("email" + sharedPreferences.setEmail(context, email));
                                            }
                                            if (object.has("id")) {
                                                String id = object.getString("id");
                                                profileId = id;
                                                sharedPreferences.setProfileId(context, id);
                                                System.out.println("id" + id);
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
                                                gender = genderfb;
                                                sharedPreferences.setGender(context, genderfb);
                                                System.out.println("gender" + genderfb);
                                            }
                                            if (object.has("birthday")) {
                                                String birthday = object.getString("birthday");
                                                dob = birthday;
                                                sharedPreferences.setDateofBirth(context, birthday);
                                                System.out.println("birthday" + birthday);
                                            }

                                            type = "2";
                                            password = "";
                                            latitude = sharedPreferences.getLatitude(context);
                                            longitude = sharedPreferences.getLongitude(context);
                                            device = "Android";
                                            deviceid = sharedPreferences.getDeviceToken(context);
                                            hide_keyboard(activity);
                                            sharedPreferences.setLoginType(context, "Facebook");
                                            new Dosomething_ChechUser_Api().execute();
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
        //Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
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


    private class Dosomething_ChechUser_Api extends AsyncTask<Void, Void, Boolean> {
        private String SessionId;
        private String image1_thumb;
        private String image2_thumb;
        private String image3_thumb;
        private String first_name;
        private String last_name;
        private String date_of_birth;
        private String user_id;
        private String about;
        private String status;
        private String registervia;
        Exception error;
        private String notification_message;
        private String notification_sound;
        private String notification_vibration;
        private String notification_match;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd.show();


            progress_bar.show();
           /* kbv.setVisibility(View.VISIBLE);
            layout_progress_image.setVisibility(View.VISIBLE);*/
            timer = new Timer();

            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation.start();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            HashMap<String, Object> paramsCheck = new HashMap<>();
            paramsCheck.put(TAG_EMAIL, email);
            paramsCheck.put(TAG_TYPE, type);
            json_string = jsonfunctions.postToURL(getResources().getString(R.string.dosomething_apilink_string_checkuser), paramsCheck);
            Log.v("jason url=======>", String.valueOf(paramsCheck));
            try {
                json_object = new JSONObject(json_string);
                json_content = json_object.getJSONObject("checkuser");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                error = e;
                return false;
            }

        }

        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);


            if (result) {
                if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {
                    try {
                        if (json_object.has("checkuser")) {
                            if (json_content.getString("status").equalsIgnoreCase("success")) {
//                            pd.dismiss();

                           /* kbv.setVisibility(View.GONE);
                            layout_progress_image.setVisibility(View.GONE);*/
                                progress_bar.dismiss();
                                splashAnimation.stop();
                                Intent i = new Intent(DoSomeThingCreateAccount.this, DoSomethingprofile.class);
                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList("value", dummy_hobbies_name);
                                bundle.putIntegerArrayList("value", dummy_hobbies_image);
                                i.putStringArrayListExtra("array_bundle_hobbies_name", dummy_hobbies_name);
                                i.putIntegerArrayListExtra("array_bundle_hobbies_image", dummy_hobbies_image);
                                startActivity(i);
                                finish();
                            } else if (json_content.getString("status").equalsIgnoreCase("error")) {
                                if (json_content.getString("Message").equalsIgnoreCase("Email Already available")) {
                                    if (json_content.getString("RegisterType").equalsIgnoreCase("2")) {
                                        JSONArray sportsArray = json_content.getJSONArray("userDetails");
                                        JSONObject firstSport = sportsArray.getJSONObject(0);
                                        showpassword = firstSport.getString("showpassword");

                                        if (firstSport.has("SessionId")) {
                                            SessionId = firstSport.getString("SessionId");

                                        } else {
                                            SessionId = "";
                                        }
                                        if (firstSport.has("user_id")) {
                                            user_id = firstSport.getString("user_id");

                                        } else {
                                            user_id = "";
                                        }

                                        if (firstSport.has("image1_thumb")) {
                                            image1_thumb = firstSport.getString("image1_thumb");

                                        } else {
                                            image1_thumb = "";
                                        }
                                        if (firstSport.has("image2_thumb")) {
                                            image2_thumb = firstSport.getString("image2_thumb");

                                        } else {
                                            image2_thumb = "";
                                        }
                                        if (firstSport.has("image3_thumb")) {
                                            image3_thumb = firstSport.getString("image3_thumb");

                                        } else {
                                            image3_thumb = "";
                                        }
                                        if (firstSport.has("first_name"))

                                        {
                                            first_name = firstSport.getString("first_name");


                                        } else {
                                            first_name = "";
                                        }
                                        if (firstSport.has("last_name")) {
                                            last_name = firstSport.getString("last_name");
                                        } else {
                                            last_name = "";
                                        }

                                        if (firstSport.has("gender")) {
                                            gender = firstSport.getString("gender");
                                        } else {
                                            gender = "";
                                        }

                                        if (firstSport.has("about")) {
                                            about = firstSport.getString("about");
                                        } else {
                                            about = "";
                                        }
                                        if (firstSport.has("email")) {
                                            email = firstSport.getString("email");
                                        } else {
                                            email = "";
                                        }
                                        if (firstSport.has("status")) {
                                            status = firstSport.getString("status");
                                        } else {
                                            status = "";
                                        }
                                        if (firstSport.has("date_of_birth")) {
                                            date_of_birth = firstSport.getString("date_of_birth");
                                        } else {
                                            date_of_birth = "";
                                        }
                                        if (firstSport.has("registervia")) {
                                            registervia = firstSport.getString("registervia");

                                        } else {
                                            registervia = "";
                                        }


                                        if (firstSport.has("notification_message")) {
                                            notification_message = firstSport.getString("notification_message");

                                        } else {
                                            notification_message = "";
                                        }
                                        if (firstSport.has("notification_sound")) {
                                            notification_sound = firstSport.getString("notification_sound");

                                        } else {
                                            notification_sound = "";
                                        }
                                        if (firstSport.has("notification_vibration")) {
                                            notification_vibration = firstSport.getString("notification_vibration");

                                        } else {
                                            notification_vibration = "";
                                        }
                                        if (firstSport.has("isMatch")) {
                                            notification_match = firstSport.getString("isMatch");

                                        } else {
                                            notification_match = "";
                                        }
                                        sharedPreferences.setRegistervia(context, registervia);

                                        if (date_of_birth != null) {
                                            String date = date_of_birth;
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
                                        }


                                        progress_bar.dismiss();
                                        splashAnimation.stop();
                                        sharedPreferences.setSessionid(context, SessionId);
                                        sharedPreferences.setProfilePicture(context, image1_thumb);
                                        sharedPreferences.setProfilePicture1(context, image2_thumb);
                                        sharedPreferences.setProfilePicture2(context, image3_thumb);
                                        sharedPreferences.setNotifyMessage(context, notification_message);
                                        sharedPreferences.setNotifySound(context, notification_sound);
                                        sharedPreferences.setNotifyVibration(context, notification_vibration);
                                        sharedPreferences.setNotifyMatch(context, notification_match);
                                        sharedPreferences.setEmail(context, email);
                                        sharedPreferences.setUserId(context, user_id);
                                        sharedPreferences.setLogin(context, "Yes");
                                        sharedPreferences.setShowPassword(context, showpassword);
                                        Intent i = new Intent(DoSomeThingCreateAccount.this, DoSomethingStatus.class);
                                        startActivity(i);
                                        finish();
                                    } else if (json_content.getString("RegisterType").equalsIgnoreCase("1")) {

                                        progress_bar.dismiss();
                                   /* kbv.setVisibility(View.GONE);
                                    layout_progress_image.setVisibility(View.GONE);*/
                                        timer.cancel();
                                        splashAnimation.stop();


                                        status_textview_availablenow.setText("Email Already Exist");
                                        status_textview_accept_check.setText("Dismiss");
                                        status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.show();





                                    /*final AlertDialog dialog = new AlertDialog.Builder(context).create();
                                    dialog.setMessage("Email Already Exist");
                                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();
                                        }
                                    });
                                    dialog.show();*/
                                    }
//                                pd.dismiss();


                                } else if (json_content.getString("Message").equalsIgnoreCase("Please Enter Valid Email Address")) {

                             /*   kbv.setVisibility(View.GONE);
                                layout_progress_image.setVisibility(View.GONE);*/
                                    progress_bar.dismiss();
                                    if (timer != null) {

                                        timer.cancel();


                                        timer = null;

                                    }
                                    splashAnimation.stop();
//                                pd.dismiss();
                                    NetworkCheck.alertdialogEmail(context);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (error != null) {
                    progress_bar.dismiss();
                    if (timer != null) {

                        timer.cancel();


                        timer = null;

                    }
                    splashAnimation.stop();
                }
            }


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DoSomeThingCreateAccount.this, SplashActivity.class);
        startActivity(intent);
        finish();

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

