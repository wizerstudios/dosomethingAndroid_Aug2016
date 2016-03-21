package com.dosomething.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class DoSomethingForgetPassword extends AppCompatActivity {
    EditText dosomething_forgetpassword_activity_edittext_email;
    TextView dosomething_forgetpassword_activity_textview_resetpassword, dosomething_forgetpassword_activity_textview_forgetpassword;
    Button dosomething_forgetpassword_activity_button_back;
    TextView dosomething_forgetpassword_activity_textview_signin, dosomething_forgetpassword_activity_textview_createanaccount, dosomething_forgetpassword_activity_textview_enteremail;
    Jsonfunctions jsonfunctions;
    JSONObject json_object, json_content;
    SharedPrefrences sharedPreferences;
    private Context context;
    private static final String TAG_EMAIL = "email";
    private String json_string;

    Handler handler = new Handler();
    Timer timer = new Timer();
    private AnimationDrawable splashAnimation;
    private ImageView kbv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_something_forget_password);
        sharedPreferences = new SharedPrefrences();
        jsonfunctions = new Jsonfunctions(this);
        context = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        kbv = (ImageView) findViewById(R.id.image);
        kbv.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) kbv.getBackground();
        dosomething_forgetpassword_activity_edittext_email = (EditText) findViewById(R.id.dosomething_forgetpassword_activity_edittext_email);
        dosomething_forgetpassword_activity_textview_forgetpassword = (TextView) findViewById(R.id.dosomething_forgetpassword_activity_textview_forgetpassword);
        dosomething_forgetpassword_activity_textview_resetpassword = (TextView) findViewById(R.id.dosomething_forgetpassword_activity_textview_resetpassword);
        dosomething_forgetpassword_activity_textview_signin = (TextView) findViewById(R.id.dosomething_forgetpassword_activity_textview_signin);
        dosomething_forgetpassword_activity_textview_createanaccount = (TextView) findViewById(R.id.dosomething_forgetpassword_activity_textview_createanaccount);
        dosomething_forgetpassword_activity_textview_enteremail = (TextView) findViewById(R.id.dosomething_forgetpassword_activity_textview_enteremail);
        dosomething_forgetpassword_activity_button_back = (Button) findViewById(R.id.dosomething_forgetpassword_activity_button_back);
        text_font_typeface();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        dosomething_forgetpassword_activity_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                Intent i = new Intent(DoSomethingForgetPassword.this, DoSomeThingLogin.class);
                startActivity(i);
                finish();
            }
        });
        dosomething_forgetpassword_activity_textview_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                Intent i = new Intent(DoSomethingForgetPassword.this, DoSomeThingLogin.class);
                startActivity(i);
                finish();
            }
        });
        dosomething_forgetpassword_activity_textview_createanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                Intent i = new Intent(DoSomethingForgetPassword.this, DoSomeThingCreateAccount.class);
                startActivity(i);
                finish();
            }
        });
        if (context != null) {
            dosomething_forgetpassword_activity_edittext_email.setText(sharedPreferences.getEMAIl(context));
                if (!dosomething_forgetpassword_activity_edittext_email.getText().equals("")) {
                    sharedPreferences.setEmail(context, dosomething_forgetpassword_activity_edittext_email.getText().toString());
                }

        }

        dosomething_forgetpassword_activity_textview_resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if (dosomething_forgetpassword_activity_edittext_email.getText().toString().equals("")) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setMessage("Please enter your email");
                    dialog.setPositiveButton(context.getResources().getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            // TODO Auto-generated method stub

                        }
                    });

                    dialog.show();
                } else {
                    splashAnimation.start();
                    sharedPreferences.setEmail(context, dosomething_forgetpassword_activity_edittext_email.getText().toString());
                    new AsyncForgetPassword().execute();
                }
            }
        });
    }


    private void text_font_typeface() {
        Typeface patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");
        Typeface patron_regular = Typeface.createFromAsset(getAssets(), "fonts/Patron-Regular.ttf");
        Typeface patron_medium = Typeface.createFromAsset(getAssets(), "fonts/Patron-Medium.ttf");
        Typeface myfonts_thin = Typeface.createFromAsset(getAssets(), "fonts/Patron-Thin.ttf");

        dosomething_forgetpassword_activity_edittext_email.setTypeface(patron_regular);
        dosomething_forgetpassword_activity_textview_enteremail.setTypeface(patron_regular);
        dosomething_forgetpassword_activity_button_back.setTypeface(patron_regular);

        dosomething_forgetpassword_activity_textview_createanaccount.setTypeface(patron_regular);
        dosomething_forgetpassword_activity_textview_signin.setTypeface(patron_regular);

        dosomething_forgetpassword_activity_textview_forgetpassword.setTypeface(patron_regular);
        dosomething_forgetpassword_activity_textview_resetpassword.setTypeface(patron_bold);
    }

    private class AsyncForgetPassword extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            kbv.setVisibility(View.VISIBLE);
            timer = new Timer();
            timer.schedule(new AutoSlider(), 0, 1350);
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {
                HashMap<String, Object> forgetpassword_params = new HashMap<>();
                forgetpassword_params.put(TAG_EMAIL, sharedPreferences.getEMAIl(context));
                json_string = jsonfunctions.postToURL(getResources().getString(R.string.dosomething_apilink_string_forgetpassword), forgetpassword_params);

                try {
                    json_object = new JSONObject(json_string);
                    json_content = json_object.getJSONObject("forgetpassword");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (NetworkCheck.isWifiAvailable(context) || NetworkCheck.isNetworkAvailable(context)) {
                try {
                    if (json_object.has("forgetpassword")) {
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
                            if (json_content.getString("message").equalsIgnoreCase("password reseted successfully please check your mail")) {


                                kbv.setVisibility(View.GONE);
                                timer.cancel();
                                splashAnimation.stop();


                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                dialog.setMessage("password reseted successfully please check your mail");
                                dialog.setPositiveButton(context.getResources().getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                        // TODO Auto-generated method stub

                                    }
                                });

                                dialog.show();
                            }

                        } else if (json_content.getString("status").equalsIgnoreCase("failed")) {
                            if (json_content.getString("message").equalsIgnoreCase("enter valid email address")) {


                                kbv.setVisibility(View.GONE);
                                if (timer != null) {

                                    timer.cancel();


                                    timer = null;

                                }
                                splashAnimation.stop();



                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                dialog.setMessage("enter valid email address");
                                dialog.setPositiveButton(context.getResources().getString(R.string.dismiss), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                        // TODO Auto-generated method stub

                                    }
                                });

                                dialog.show();


                            }
                        }
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
