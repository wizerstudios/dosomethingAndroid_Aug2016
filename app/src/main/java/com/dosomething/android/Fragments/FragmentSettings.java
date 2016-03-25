package com.dosomething.android.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.dosomething.android.Database.DBAdapter;
import com.dosomething.android.DoSomethingPrivacyPolicy;
import com.dosomething.android.DoSomethingStatus;
import com.dosomething.android.DoSomethingTermsofUse;
import com.dosomething.android.SplashActivity;
import com.dosomething.android.MyApplication;
import com.dosomething.android.R;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.AUDIO_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentSettings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSettings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView settings_page_textview_title_notification;
    TextView settings_page_textview_notification_message;
    TextView settings_page_textview_notification_sound;
    TextView settings_page_textview_notification_vibration;
    TextView settings_page_textview_notification_match;
    TextView profile_page_textview_logout_button;
    TextView profile_page_textview_deleteaccount_button;
    TextView fragment_settings_textview_delete;
    TextView fragment_settings_textview_logout;
    Button settings_page_button_terms;
    Button settings_page_button_policy;
    private OnFragmentInteractionListener mListener;
    SharedPrefrences sharedPrefrences;
    RelativeLayout relativelayout_alertdialog_deleteaccount;
    RelativeLayout relativelayout_alertdialog_logout;
    TextView alert_textview_logout_yes;
    TextView alert_textview_logout_no;
    TextView alert_textview_deleteaccount_yes;
    TextView alert_textview_deleteaccount_no;
    private static String url = "http://wiztestinghost.com/dosomething/useraction?";
    private static final String TAG_SESSIONID = "sessionid";
    private static final String TAG_OP = "op";
    JSONObject json_object, json_content, details;
    JSONObject json_object_updateposition, json_content_updateposition, details_updateposition;
    Jsonfunctions jsonfunctions;
    private String json_string;
    String sessionid, operation, delete_op;
    private TransparentProgressDialog pd;
    CheckBox settings_checkbox_notifiacation_vibration;
    CheckBox settings_checkbox_notifiacation_sound;
    CheckBox settings_checkbox_notifiacation_messages;
    CheckBox settings_checkbox_notifiacation_match;
    DBAdapter dbAdapter;
    LinearLayout profilepage_layout_notifiacation_match, profilepage_layout_notifiacation_messages, profilepage_layout_notifiacation_sound, profilepage_layout_notifiacation_vibration;
    private String tonePath;
    private Tracker mTracker;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSettings newInstance(String param1, String param2) {
        FragmentSettings fragment = new FragmentSettings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentSettings() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_settings, container, false);
        try
        {
            MyApplication application = (MyApplication) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        sharedPrefrences = new SharedPrefrences();
        jsonfunctions = new Jsonfunctions(getActivity());
        dbAdapter = new DBAdapter(getActivity());
        settings_page_textview_title_notification = (TextView) view.findViewById(R.id.settings_page_textview_title_notification);
        settings_page_textview_notification_message = (TextView) view.findViewById(R.id.settings_page_textview_notification_message);
        settings_page_textview_notification_sound = (TextView) view.findViewById(R.id.settings_page_textview_notification_sound);
        settings_page_textview_notification_vibration = (TextView) view.findViewById(R.id.settings_page_textview_notification_vibration);
        settings_page_textview_notification_match = (TextView) view.findViewById(R.id.settings_page_textview_notification_match);
        profile_page_textview_logout_button = (TextView) view.findViewById(R.id.profile_page_textview_logout_button);
        profile_page_textview_deleteaccount_button = (TextView) view.findViewById(R.id.profile_page_textview_deleteaccount_button);
        fragment_settings_textview_delete = (TextView) view.findViewById(R.id.fragment_settings_textview_delete);
        fragment_settings_textview_logout = (TextView) view.findViewById(R.id.fragment_settings_textview_logout);
        settings_page_button_terms = (Button) view.findViewById(R.id.settings_page_button_terms);
        settings_page_button_policy = (Button) view.findViewById(R.id.settings_page_button_policy);
        relativelayout_alertdialog_deleteaccount = (RelativeLayout) view.findViewById(R.id.relativelayout_alertdialog_deleteaccount);
        relativelayout_alertdialog_logout = (RelativeLayout) view.findViewById(R.id.relativelayout_alertdialog_logout);
        pd = new TransparentProgressDialog(getActivity(), getResources().getDrawable(R.drawable.loading));
        settings_checkbox_notifiacation_vibration = (CheckBox) view.findViewById(R.id.settings_checkbox_notifiacation_vibration);
        settings_checkbox_notifiacation_sound = (CheckBox) view.findViewById(R.id.settings_checkbox_notifiacation_sound);
        settings_checkbox_notifiacation_messages = (CheckBox) view.findViewById(R.id.settings_checkbox_notifiacation_messages);
        settings_checkbox_notifiacation_match = (CheckBox) view.findViewById(R.id.settings_checkbox_notifiacation_match);
        profilepage_layout_notifiacation_match = (LinearLayout) view.findViewById(R.id.profilepage_layout_notifiacation_match);
        profilepage_layout_notifiacation_messages = (LinearLayout) view.findViewById(R.id.profilepage_layout_notifiacation_messages);
        profilepage_layout_notifiacation_sound = (LinearLayout) view.findViewById(R.id.profilepage_layout_notifiacation_sound);
        profilepage_layout_notifiacation_vibration = (LinearLayout) view.findViewById(R.id.profilepage_layout_notifiacation_vibration);
        alert_textview_logout_yes = (TextView) view.findViewById(R.id.alert_textview_logout_yes);
        alert_textview_logout_no = (TextView) view.findViewById(R.id.alert_textview_logout_no);
        alert_textview_deleteaccount_no = (TextView) view.findViewById(R.id.alert_textview_deleteaccount_no);
        alert_textview_deleteaccount_yes = (TextView) view.findViewById(R.id.alert_textview_deleteaccount_yes);


        text_font_typeface();


        AudioManager audioManager = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);

        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);


        if (getActivity() != null) {

            profilepage_layout_notifiacation_match.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoSomethingStatus) getActivity()).passwordMatch(true);

                    if (settings_checkbox_notifiacation_match.isChecked()) {
                        sharedPrefrences.setNotifyMatch(getActivity(), "Yes");
                    } else {
                        sharedPrefrences.setNotifyMatch(getActivity(), "No");

                    }


                }
            });

            settings_checkbox_notifiacation_match.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoSomethingStatus) getActivity()).passwordMatch(true);

                    if (settings_checkbox_notifiacation_match.isChecked()) {
                        sharedPrefrences.setNotifyMatch(getActivity(), "Yes");
                    } else {
                        sharedPrefrences.setNotifyMatch(getActivity(), "No");

                    }
                }
            });
            profilepage_layout_notifiacation_messages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoSomethingStatus) getActivity()).passwordMatch(true);

                    if (settings_checkbox_notifiacation_messages.isChecked()) {
                        sharedPrefrences.setNotifyMessage(getActivity(), "Yes");
                    } else {
                        sharedPrefrences.setNotifyMessage(getActivity(), "No");

                    }

                }
            });

            settings_checkbox_notifiacation_messages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoSomethingStatus) getActivity()).passwordMatch(true);

                    if (settings_checkbox_notifiacation_messages.isChecked()) {
                        sharedPrefrences.setNotifyMessage(getActivity(), "Yes");
                    } else {
                        sharedPrefrences.setNotifyMessage(getActivity(), "No");

                    }
                }
            });


            profilepage_layout_notifiacation_sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoSomethingStatus) getActivity()).passwordMatch(true);


                    if (settings_checkbox_notifiacation_sound.isChecked()) {

                        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "NOTIFICATION SOUND");
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                        startActivityForResult(intent, 3);
                        sharedPrefrences.setNotifySound(getActivity(), "Yes");

                    } else {
                        sharedPrefrences.setNotifySound(getActivity(), "No");

                    }
                }
            });


            settings_checkbox_notifiacation_sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoSomethingStatus) getActivity()).passwordMatch(true);

                    if (settings_checkbox_notifiacation_sound.isChecked()) {
                        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "NOTIFICATION SOUND");
                        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                        startActivityForResult(intent, 3);
                        sharedPrefrences.setNotifySound(getActivity(), "Yes");
                    } else {
                        sharedPrefrences.setNotifySound(getActivity(), "No");

                    }
                }
            });


            profilepage_layout_notifiacation_vibration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoSomethingStatus) getActivity()).passwordMatch(true);

                    if (settings_checkbox_notifiacation_vibration.isChecked()) {
                        sharedPrefrences.setNotifyVibration(getActivity(), "Yes");
                    } else {
                        sharedPrefrences.setNotifyVibration(getActivity(), "No");

                    }
                }
            });
            settings_checkbox_notifiacation_vibration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DoSomethingStatus) getActivity()).passwordMatch(true);

                    if (settings_checkbox_notifiacation_vibration.isChecked()) {
                        sharedPrefrences.setNotifyVibration(getActivity(), "Yes");
                    } else {
                        sharedPrefrences.setNotifyVibration(getActivity(), "No");

                    }
                }
            });

            try {

                if (getActivity() != null) {
                    if (sharedPrefrences.getNotifyMatch(getActivity()).equals("Yes")) {
                        settings_checkbox_notifiacation_match.setChecked(true);
                    } else

                    {
                        settings_checkbox_notifiacation_match.setChecked(false);
                    }

                    Log.d("sound", sharedPrefrences.getNotifyMessage(getActivity()));
                    if (sharedPrefrences.getNotifyMessage(getActivity()).equals("Yes")) {
                        settings_checkbox_notifiacation_messages.setChecked(true);
                    } else

                    {
                        settings_checkbox_notifiacation_messages.setChecked(false);
                    }

                    Log.d("sound", sharedPrefrences.getNotifySound(getActivity()));
                    if (sharedPrefrences.getNotifySound(getActivity()).equals("Yes")) {
                        settings_checkbox_notifiacation_sound.setChecked(true);
                    } else

                    {
                        settings_checkbox_notifiacation_sound.setChecked(false);
                    }
                }
                if (getActivity() != null) {
                    Log.d("sound", sharedPrefrences.getNotifyVibration(getActivity()));

                    if (sharedPrefrences.getNotifyVibration(getActivity()).equals("Yes")) {
                        settings_checkbox_notifiacation_vibration.setChecked(true);
                    } else

                    {
                        settings_checkbox_notifiacation_vibration.setChecked(false);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        settings_page_button_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoSomethingPrivacyPolicy.class);
                startActivity(intent);

            }
        });
        settings_page_button_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoSomethingTermsofUse.class);
                startActivity(intent);
            }
        });
        profile_page_textview_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn_alertdialog();
                profile_page_textview_logout_button.setClickable(false);
                settings_page_button_policy.setClickable(false);
                settings_page_button_terms.setClickable(false);
                profile_page_textview_deleteaccount_button.setClickable(false);


            }
        });
        profile_page_textview_deleteaccount_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn_deleteAccount();
                profile_page_textview_deleteaccount_button.setClickable(false);
                settings_page_button_policy.setClickable(false);
                settings_page_button_terms.setClickable(false);
                profile_page_textview_logout_button.setClickable(false);


            }
        });
        alert_textview_deleteaccount_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fadeOut_deleteAccount();
                sessionid = sharedPrefrences.getSessionid(getActivity());
                operation = "delete";
                new Logout().execute();
                relativelayout_alertdialog_deleteaccount.setVisibility(View.GONE);
                ((MyApplication) getActivity().getApplication()).getArts_hobbies().clear();
                ((MyApplication) getActivity().getApplication()).getFood_hobbies().clear();
                ((MyApplication) getActivity().getApplication()).getPets_hobbies().clear();
                ((MyApplication) getActivity().getApplication()).getRecreation_hobbies().clear();
                sharedPrefrences.setLogin(getActivity(), "");
                sharedPrefrences.setDateofBirth(getActivity(), "");
                sharedPrefrences.setFirstname(getActivity(), "");
                sharedPrefrences.setDeviceToken(getActivity(), "");
                sharedPrefrences.setLastname(getActivity(), "");
                sharedPrefrences.setGender(getActivity(), "");
                sharedPrefrences.setPassword(getActivity(), "");
                sharedPrefrences.setUserId(getActivity(), "");
                sharedPrefrences.setProfileId(getActivity(), "");
                sharedPrefrences.setNotifyMessage(getActivity(), "");
                sharedPrefrences.setNotifyVibration(getActivity(), "");
                sharedPrefrences.setNotifySound(getActivity(), "");
                sharedPrefrences.setProfilePicture(getActivity(), "");
                sharedPrefrences.setProfilePicture1(getActivity(), "");
                sharedPrefrences.setProfilePicture2(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap1(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap2(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap3(getActivity(), "");
                sharedPrefrences.setFBProfilePicture(getActivity(), "");
                sharedPrefrences.setAbout(getActivity(), "");
                sharedPrefrences.setHobbies(getActivity(), "");
                sharedPrefrences.setHobbiesId(getActivity(), null);
                sharedPrefrences.setBooleam(getActivity(), "false");
                sharedPrefrences.setAvailable(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap1(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap2(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap3(getActivity(), "");
                dbAdapter.open();
                dbAdapter.delete();
                dbAdapter.close();
                sharedPrefrences.setSessionid(getActivity(), "");
                profile_page_textview_deleteaccount_button.setClickable(true);
                profile_page_textview_logout_button.setClickable(true);

            }
        });
        alert_textview_deleteaccount_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut_deleteAccount();
                relativelayout_alertdialog_deleteaccount.setVisibility(View.GONE);
                profile_page_textview_deleteaccount_button.setClickable(true);
                profile_page_textview_logout_button.setClickable(true);
            }
        });
        alert_textview_logout_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fadeOut_onlythree();
                sessionid = sharedPrefrences.getSessionid(getActivity());
                operation = "logout";
                new Logout().execute();
                relativelayout_alertdialog_logout.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
                getActivity().finish();

                sharedPrefrences.setLogin(getActivity(), "");
                sharedPrefrences.setDateofBirth(getActivity(), "");
                sharedPrefrences.setFirstname(getActivity(), "");
                sharedPrefrences.setDeviceToken(getActivity(), "");
                sharedPrefrences.setLastname(getActivity(), "");
                sharedPrefrences.setGender(getActivity(), "");
                sharedPrefrences.setPassword(getActivity(), "");
                sharedPrefrences.setUserId(getActivity(), "");
                sharedPrefrences.setProfileId(getActivity(), "");
                sharedPrefrences.setNotifyMessage(getActivity(), "");
                sharedPrefrences.setNotifyVibration(getActivity(), "");
                sharedPrefrences.setNotifySound(getActivity(), "");
                sharedPrefrences.setProfilePicture(getActivity(), "");
                sharedPrefrences.setProfilePicture1(getActivity(), "");
                sharedPrefrences.setProfilePicture2(getActivity(), "");
                sharedPrefrences.setFBProfilePicture(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap1(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap2(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap3(getActivity(), "");
                sharedPrefrences.setAbout(getActivity(), "");
                sharedPrefrences.setHobbies(getActivity(), "");
                sharedPrefrences.setHobbiesId(getActivity(), null);
                sharedPrefrences.setBooleam(getActivity(), "false");
                sharedPrefrences.setAvailable(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap1(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap2(getActivity(), "");
                sharedPrefrences.setProfileImageBitmap3(getActivity(), "");
                dbAdapter.open();
                dbAdapter.delete();
                dbAdapter.close();

                profile_page_textview_deleteaccount_button.setClickable(true);
                profile_page_textview_logout_button.setClickable(true);
                sharedPrefrences.setSessionid(getActivity(), "");

            }
        });
        alert_textview_logout_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut_onlythree();
                relativelayout_alertdialog_logout.setVisibility(View.GONE);
                profile_page_textview_deleteaccount_button.setClickable(true);
                profile_page_textview_logout_button.setClickable(true);
            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 3) {
            Uri toneUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);

            if (toneUri != null) {
                RingtoneManager.setActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_NOTIFICATION, toneUri);
                tonePath = toneUri.toString();
                Log.i("tonepath", tonePath);
            }
        }
    }


    public void fadeIn_deleteAccount() {


        Animation fadeIn = new AlphaAnimation(0, 1);

        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this

        fadeIn.setStartOffset(0);

        fadeIn.setDuration(800);


        AnimationSet animation = new AnimationSet(false); //change to false


        animation.addAnimation(fadeIn);

        relativelayout_alertdialog_deleteaccount.setVisibility(View.VISIBLE);

        relativelayout_alertdialog_deleteaccount.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog_deleteaccount.clearAnimation();

            }


        });


    }

    public void fadeOut_deleteAccount() {


        Animation fadeOut = new AlphaAnimation(1, 0);

        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this

        fadeOut.setStartOffset(0);

        fadeOut.setDuration(500);


        AnimationSet animation = new AnimationSet(false); //change to false

        animation.addAnimation(fadeOut);

        relativelayout_alertdialog_deleteaccount.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog_deleteaccount.clearAnimation();

            }


        });


    }

    public void fadeIn_alertdialog() {


        Animation fadeIn = new AlphaAnimation(0, 1);

        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this

        fadeIn.setStartOffset(0);

        fadeIn.setDuration(800);


        AnimationSet animation = new AnimationSet(false); //change to false


        animation.addAnimation(fadeIn);

        relativelayout_alertdialog_logout.setVisibility(View.VISIBLE);

        relativelayout_alertdialog_logout.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog_logout.clearAnimation();
            }


        });


    }

    public void fadeOut_onlythree() {


        Animation fadeOut = new AlphaAnimation(1, 0);

        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this

        fadeOut.setStartOffset(0);

        fadeOut.setDuration(500);


        AnimationSet animation = new AnimationSet(false); //change to false

        animation.addAnimation(fadeOut);

        relativelayout_alertdialog_logout.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog_logout.clearAnimation();

            }


        });


    }

    private void text_font_typeface() {
        Typeface patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");
        Typeface patron_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Regular.ttf");
        Typeface patron_medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Medium.ttf");
        Typeface myfonts_thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Thin.ttf");

        settings_page_textview_title_notification.setTypeface(patron_regular);
        settings_page_textview_notification_message.setTypeface(patron_regular);
        settings_page_textview_notification_sound.setTypeface(patron_regular);
        settings_page_textview_notification_vibration.setTypeface(patron_regular);
        settings_page_textview_notification_match.setTypeface(patron_regular);
        fragment_settings_textview_logout.setTypeface(patron_bold);
        profile_page_textview_logout_button.setTypeface(patron_regular);
        profile_page_textview_deleteaccount_button.setTypeface(patron_regular);
        fragment_settings_textview_delete.setTypeface(patron_bold);
        settings_page_button_terms.setTypeface(patron_regular);
        settings_page_button_policy.setTypeface(patron_regular);
        alert_textview_logout_yes.setTypeface(patron_bold);
        alert_textview_logout_no.setTypeface(patron_bold);
        alert_textview_deleteaccount_yes.setTypeface(patron_bold);
        alert_textview_deleteaccount_no.setTypeface(patron_bold);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class Logout extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(DoSomethingHobbies.this);
//            pDialog.setMessage("Loading......");
//            pDialog.setCancelable(false);
//            pDialog.show();
//            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (getActivity() != null) {


                if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                    {
                        HashMap<String, Object> paramslogout = new HashMap<>();
                        paramslogout.put(TAG_SESSIONID, sessionid);
                        paramslogout.put(TAG_OP, operation);
                        json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_useraction), paramslogout);
                        Log.v("jason url=======>", String.valueOf(paramslogout));
                        try {
                            json_object = new JSONObject(json_string);
                            json_content = json_object.getJSONObject("useraction");
                            if (json_object.has("useraction")) {
                                if (json_content.getString("status").equalsIgnoreCase("success")) {
                                    if (json_content.getString("Message").equalsIgnoreCase("Logout success")) {
//                                    pd.dismiss();

                                    } else if (json_content.getString("Message").equalsIgnoreCase("Delete success")) {
//                                    pd.dismiss();
                                        Intent intent = new Intent(getActivity(), SplashActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }

                                } else if (json_content.getString("status").equalsIgnoreCase("failed")) {
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
                            } else if (json_content.getString("error").equalsIgnoreCase("InvalidSession")) {
//                        pDialog.dismiss();
                                pd.dismiss();

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
