package com.dosomething.android.Fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.dosomething.android.Beanclasses.ActivityBean;
import com.dosomething.android.Beanclasses.ImageBan;
import com.dosomething.android.CommonClasses.GridViewItem;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.dosomething.android.Database.DBAdapter;
import com.dosomething.android.DoSomeThingLogin;
import com.dosomething.android.MyApplication;
import com.dosomething.android.R;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzip.runOnUiThread;


/**
 * A simple {@link Fragment} subclass.
 * <p/>
 * Activities that contain this fragment must implement the
 * <p/>
 * {@link FragmentStatus.OnFragmentInteractionListener} interface
 * <p/>
 * to handle interaction events.
 * <p/>
 * Use the {@link FragmentStatus#newInstance} factory method to
 * <p/>
 * create an instance of this fragment.
 */

public class FragmentStatus extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters

    private String mParam1;

    private String mParam2;

    private static final String TAG_SESSIONID = "sessionid";

    String type, sessionid, op, status_id = "", availableNow = "", _title, image_act, image_inact, firstname, lastname, email, password, profileId, dob, profileImage, gender, device, json_string, json_string_updateposition, deviceid, response;

    int id;

    boolean allowClick = true;

    String latitude;

    String longitude;

    Jsonfunctions jsonfunctions;

    JSONObject json_object, json_content, details;

    JSONObject json_object_updateposition, json_content_updateposition, details_updateposition;

    private ProgressDialog pDialog;

    ArrayList<ImageBan> img_list = new ArrayList<ImageBan>();
    ArrayList<ActivityBean> dosomething_beans = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    DBAdapter dbAdapter;

    LinearLayout dosmething_homescreenlayout_staus_gridview;
    LinearLayout status_layout_running;

    LinearLayout status_layout_beach;

    LinearLayout status_layout_cycling;

    LinearLayout status_layout_movies;

    LinearLayout status_layout_alcohol;

    LinearLayout status_layout_meals;

    LinearLayout status_layout_coffee;

    LinearLayout status_layout_shopping;

    LinearLayout status_layout_karoke;

    LinearLayout status_layout_gym;

    LinearLayout status_layout_tennis;

    LinearLayout status_layout_soccer;

    ImageView status_ImageView_running;

    ImageView status_ImageView_beach;

    ImageView status_ImageView_cycling;

    ImageView status_ImageView_movies;

    ImageView status_ImageView_alcohol;

    ImageView status_ImageView_meals;

    ImageView status_ImageView_coffee;

    ImageView status_ImageView_shopping;

    ImageView status_ImageView_karoke;

    ImageView status_ImageView_gym;

    ImageView status_ImageView_tennis;

    ImageView status_ImageView_soccer;
    ImageView status_dosomething_image1;
    ImageView status_dosomething_image2;
    ImageView status_dosomething_image3;
    LinearLayout status_dosomething_layout1;
    LinearLayout status_dosomething_layout2;
    LinearLayout status_dosomething_layout3;
    TextView status_TextView_running;

    TextView status_TextView_beach;

    TextView status_TextView_cycling;

    TextView status_TextView_movies;

    TextView status_TextView_alcohol;

    TextView status_TextView_meals;

    TextView status_TextView_coffee;

    TextView status_TextView_shopping;

    TextView status_TextView_karoke;

    TextView status_TextView_gym;

    TextView status_TextView_tennis;

    TextView status_TextView_soccer;
    TextView status_dosomething_text1;
    TextView status_dosomething_text2;
    TextView status_dosomething_text3;
    TextView status_information_activity_time;
    TextView status_information_activity_now;
    TextView status_activity_cancel;
    TextView status_information_activity_Anytime;

    TextView status_textview_letsDoSomething;
    GridView dosomething_activity_status_gridview;
    boolean click_action = false;

    boolean click_action1 = false;

    boolean click_action2 = false;

    boolean click_action3 = false;

    boolean click_action4 = false;

    boolean click_action5 = false;

    boolean click_action6 = false;

    boolean click_action7 = false;

    boolean click_action8 = false;

    boolean click_action9 = false;

    boolean click_action10 = false;

    boolean click_action11 = false;

    boolean click_action12 = false;

    boolean click_action13 = false;

    Typeface patron_bold;

    int count = 0;

    AQuery aQuery;

    Context context = getActivity();

    GridView activity_dosomething_status_grid;

    SharedPrefrences sharedPrefrences;

    RelativeLayout relativelayout_alertdialog, relativelayout_alertdialog_only_three, relativelayout_alertdialog_activity_results;

    TextView status_textview_unaccept_check;

    TextView status_textview_accept_check;

    TextView status_textview_availablenow;

    LinearLayout linearlayout_alertdialog_only_three;

    TextView status_textview_onlythree;

    TextView status_textview_accept_dismiss;

    String statusimage_id1;

    String statusimage_id2;

    String statusimage_id3;

    private static String url = "http://wiztestinghost.com/dosomething/ApiDetails";

    private TransparentProgressDialog transparentProgressDialog;
    private String available;
    private String updatedTime;
    private String LastActivity;
    private int dosomething_id;
    private String dosomething_name;
    private String dosomething_Image;
    private String dosomething_ActiveImage;
    private ImageAdapter imageAdapter;

    Handler handler = new Handler();
    Timer timer = new Timer();
    private AnimationDrawable splashAnimation;
    private AnimationDrawable splashAnimation_home,splashAnimation_activity1,splashAnimation_activity2,splashAnimation_activity3;
    private ImageView kbv;
    RelativeLayout relativelayout_progressImage;
    RelativeLayout layout_walkthrough_profile;
    RelativeLayout layout_walkthrough_activity;
    LinearLayout imagelayout_walkthrough_activity;
    ImageView image1_walkthrough_activity;
    ImageView image2_walkthrough_activity;
    ImageView image3_walkthrough_activity;
    private Activity_Dosomething_Adapter activity_dosomething_adapter;
    private RelativeLayout layout_walkthrough_home;
    private ImageView imageview_walkthrough_home;
    private TextView textview_walkthrough_home;
    private Timer blink_time,activity_blink1,activity_blink2,activity_blink3;
    private TextView walkthrough_activity_TextView;
    private Tracker mTracker;


    /**
     * Use this factory method to create a new instance of
     * <p/>
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentStatus.
     */

    // TODO: Rename and change types and number of parameters
    public static FragmentStatus newInstance(String param1, String param2) {

        FragmentStatus fragment = new FragmentStatus();

        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);

        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);

        return fragment;

    }


    public FragmentStatus() {

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

    public View onCreateView(LayoutInflater inflater, final ViewGroup container,

                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_status_gridview, container, false);
        try
        {
            MyApplication application = (MyApplication) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        aQuery = new AQuery(getActivity());

        dbAdapter = new DBAdapter(getActivity());
        dosmething_homescreenlayout_staus_gridview = (LinearLayout) view.findViewById(R.id.dosmething_homescreenlayout_staus_gridview);

//        status_layout_beach = (LinearLayout) view.findViewById(R.id.status_layout_beach);

//        status_layout_cycling = (LinearLayout) view.findViewById(R.id.status_layout_cycling);

//        status_layout_movies = (LinearLayout) view.findViewById(R.id.status_layout_movies);

//        status_layout_alcohol = (LinearLayout) view.findViewById(R.id.status_layout_alcohol);

//        status_layout_meals = (LinearLayout) view.findViewById(R.id.status_layout_meals);

//        status_layout_coffee = (LinearLayout) view.findViewById(R.id.status_layout_coffee);

//        status_layout_shopping = (LinearLayout) view.findViewById(R.id.status_layout_shopping);

//        status_layout_karoke = (LinearLayout) view.findViewById(R.id.status_layout_karoke);

//        status_layout_gym = (LinearLayout) view.findViewById(R.id.status_layout_gym);

//        status_layout_tennis = (LinearLayout) view.findViewById(R.id.status_layout_tennis);

//        status_layout_soccer = (LinearLayout) view.findViewById(R.id.status_layout_soccer);

//        status_ImageView_running = (ImageView) view.findViewById(R.id.status_ImageView_running);

//        status_ImageView_beach = (ImageView) view.findViewById(R.id.status_ImageView_beach);

//        status_ImageView_cycling = (ImageView) view.findViewById(R.id.status_ImageView_cycling);

//        status_ImageView_movies = (ImageView) view.findViewById(R.id.status_ImageView_movies);

//        status_ImageView_alcohol = (ImageView) view.findViewById(R.id.status_ImageView_alcohol);

//        status_ImageView_meals = (ImageView) view.findViewById(R.id.status_ImageView_meals);

//        status_ImageView_coffee = (ImageView) view.findViewById(R.id.status_ImageView_coffee);

//        status_ImageView_shopping = (ImageView) view.findViewById(R.id.status_ImageView_shopping);

//        status_ImageView_karoke = (ImageView) view.findViewById(R.id.status_ImageView_karoke);

//        status_ImageView_gym = (ImageView) view.findViewById(R.id.status_ImageView_gym);

//        status_ImageView_tennis = (ImageView) view.findViewById(R.id.status_ImageView_tennis);

//        status_ImageView_soccer = (ImageView) view.findViewById(R.id.status_ImageView_soccer);

//        status_TextView_running = (TextView) view.findViewById(R.id.status_TextView_running);

//        status_TextView_beach = (TextView) view.findViewById(R.id.status_TextView_beach);

//        status_TextView_cycling = (TextView) view.findViewById(R.id.status_TextView_cycling);

//        status_TextView_movies = (TextView) view.findViewById(R.id.status_TextView_movies);

//        status_TextView_alcohol = (TextView) view.findViewById(R.id.status_TextView_alcohol);

//        status_TextView_meals = (TextView) view.findViewById(R.id.status_TextView_meals);

//        status_TextView_coffee = (TextView) view.findViewById(R.id.status_TextView_coffee);

//        status_TextView_shopping = (TextView) view.findViewById(R.id.status_TextView_shopping);

//        status_TextView_karoke = (TextView) view.findViewById(R.id.status_TextView_karoke);

//        status_TextView_gym = (TextView) view.findViewById(R.id.status_TextView_gym);

//        status_TextView_tennis = (TextView) view.findViewById(R.id.status_TextView_tennis);

//        status_TextView_soccer = (TextView) view.findViewById(R.id.status_TextView_soccer);

        status_textview_letsDoSomething = (TextView) view.findViewById(R.id.status_textview_letsDoSomething);
        status_dosomething_text1 = (TextView) view.findViewById(R.id.status_dosomething_text1);
        status_dosomething_text2 = (TextView) view.findViewById(R.id.status_dosomething_text2);
        status_dosomething_text3 = (TextView) view.findViewById(R.id.status_dosomething_text3);
        status_information_activity_time = (TextView) view.findViewById(R.id.status_information_activity_time);
//        status_information_activity_now = (TextView) view.findViewById(R.id.status_information_activity_now);
        status_activity_cancel = (TextView) view.findViewById(R.id.status_activity_cancel);
//        status_information_activity_Anytime = (TextView) view.findViewById(R.id.status_information_activity_Anytime);
        status_dosomething_image1 = (ImageView) view.findViewById(R.id.status_dosomething_image1);
        status_dosomething_image2 = (ImageView) view.findViewById(R.id.status_dosomething_image2);
        status_dosomething_image3 = (ImageView) view.findViewById(R.id.status_dosomething_image3);
        status_dosomething_layout1 = (LinearLayout) view.findViewById(R.id.status_dosomething_layout1);
        status_dosomething_layout2 = (LinearLayout) view.findViewById(R.id.status_dosomething_layout2);
        status_dosomething_layout3 = (LinearLayout) view.findViewById(R.id.status_dosomething_layout3);
        sharedPrefrences = new SharedPrefrences(getActivity());

        jsonfunctions = new Jsonfunctions(getActivity());

        activity_dosomething_status_grid = (GridView) view.findViewById(R.id.activity_dosomething_status_grid);
        dosomething_activity_status_gridview = (GridView) view.findViewById(R.id.dosomething_activity_status_gridview);
        sessionid = sharedPrefrences.getSessionid(getActivity());

        relativelayout_alertdialog = (RelativeLayout) view.findViewById(R.id.relativelayout_alertdialog);

        relativelayout_alertdialog_only_three = (RelativeLayout) view.findViewById(R.id.relativelayout_alertdialog_only_three);
        relativelayout_alertdialog_activity_results = (RelativeLayout) view.findViewById(R.id.relativelayout_alertdialog_activity_results);

        status_textview_accept_check = (TextView) view.findViewById(R.id.status_textview_accept_check);

        status_textview_availablenow = (TextView) view.findViewById(R.id.status_textview_availablenow);

        status_textview_unaccept_check = (TextView) view.findViewById(R.id.status_textview_unaccept_check);


        linearlayout_alertdialog_only_three = (LinearLayout) view.findViewById(R.id.linearlayout_alertdialog_only_three);

        status_textview_onlythree = (TextView) view.findViewById(R.id.status_textview_onlythree);

        status_textview_accept_dismiss = (TextView) view.findViewById(R.id.status_textview_accept_dismiss);

        transparentProgressDialog = new TransparentProgressDialog(getActivity(), getResources().getDrawable(R.drawable.loading));

        relativelayout_progressImage = (RelativeLayout) view.findViewById(R.id.relativelayout_progressImage);
        kbv = (ImageView) view.findViewById(R.id.status_progress_image);
        kbv.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) kbv.getBackground();
        layout_walkthrough_home = (RelativeLayout) view.findViewById(R.id.layout_walkthrough_home);
        imageview_walkthrough_home=(ImageView)view.findViewById(R.id.imageview_walkthrough_home);
        textview_walkthrough_home=(TextView)view.findViewById(R.id.textview_walkthrough_home);
        layout_walkthrough_activity = (RelativeLayout) view.findViewById(R.id.layout_walkthrough_activity);
        image1_walkthrough_activity=(ImageView)view.findViewById(R.id.image1_walkthrough_activity);
        image2_walkthrough_activity=(ImageView)view.findViewById(R.id.image2_walkthrough_activity);
        image3_walkthrough_activity=(ImageView)view.findViewById(R.id.image3_walkthrough_activity);
        walkthrough_activity_TextView=(TextView)view.findViewById(R.id.walkthrough_activity_TextView);


        image1_walkthrough_activity.setBackgroundResource(R.drawable.blink_icon);
        image2_walkthrough_activity.setBackgroundResource(R.drawable.blink_icon);
        image3_walkthrough_activity.setBackgroundResource(R.drawable.blink_icon);
        splashAnimation_activity1 = (AnimationDrawable) image1_walkthrough_activity.getBackground();
        splashAnimation_activity2 = (AnimationDrawable) image2_walkthrough_activity.getBackground();
        splashAnimation_activity3 = (AnimationDrawable) image3_walkthrough_activity.getBackground();
        imageview_walkthrough_home.setBackgroundResource(R.drawable.blink_icon);
        splashAnimation_home = (AnimationDrawable) imageview_walkthrough_home.getBackground();

        text_font_typeface();
        dbAdapter.open();
        final int countStatus = dbAdapter.getStatusCount();

        dbAdapter.close();

        Log.d("countttttttttttttttt", String.valueOf(countStatus));

        if (getActivity() != null) {




            /*layout_walkthrough_activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout_walkthrough_activity.setVisibility(View.GONE);
                    if (activity_blink1 != null) {

                        activity_blink1.cancel();


                        activity_blink1 = null;

                    }
                    splashAnimation_activity1.stop();
                    if (activity_blink2 != null) {

                        activity_blink2.cancel();


                        activity_blink2 = null;

                    }
                    splashAnimation_activity2.stop();
                    if (activity_blink3 != null) {

                        activity_blink3.cancel();


                        activity_blink3 = null;

                    }
                    splashAnimation_activity3.stop();
                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");

                }
            });
*/
            if (countStatus == 12) {

                dbAdapter.open();

                img_list = dbAdapter.getStatusImages();

                imageAdapter = new ImageAdapter(getActivity(), img_list);

                activity_dosomething_status_grid.setAdapter(imageAdapter);
                imageAdapter.notifyDataSetChanged();
                dbAdapter.close();


            } else {
                if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {
                    new StatusBackground().execute();


                } else {
                    NetworkCheck.alertdialog(getActivity());

                }


            }
        }


        if (getActivity() != null) {
            if (sharedPrefrences.getOp(getActivity()).equals("update")) {
                if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {


                    new StatusUpdate().execute();
                    splashAnimation.start();


                } else {
                    NetworkCheck.alertdialog(getActivity());

                }


            }


            if (sharedPrefrences.getOp(getActivity()).equals("getlast")) {
                availableNow = "";
                status_id = "";
                activity_dosomething_status_grid.setEnabled(false);
                if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {
                    if (((MyApplication) getActivity().getApplication()).getActivityBeans().size() == 0) {
                        new StatusUpdate().execute();
                        splashAnimation.start();
                    } else {


                        dosmething_homescreenlayout_staus_gridview.setAlpha(0.2f);

                        if (sharedPrefrences.getWalkThroughActivity(getActivity()).equals("false")) {
                            layout_walkthrough_activity.setVisibility(View.VISIBLE);
                            activity_blink1 = new Timer();
                            activity_blink1.schedule(new Activity_Blink1(), 0, 340);
                            splashAnimation_activity1.start();
                            activity_blink2 = new Timer();
                            activity_blink2.schedule(new Activity_Blink2(), 0, 340);
                            splashAnimation_activity2.start();
                            activity_blink3 = new Timer();
                            activity_blink3.schedule(new Activity_Blink3(), 0, 340);
                            splashAnimation_activity3.start();
                            sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                        }

                        if (getActivity() != null) {
                            sharedPrefrences.setOp(getActivity(), "getlast");
                            if (!sharedPrefrences.getAvailable(getActivity()).equals("")) {
                                relativelayout_alertdialog_activity_results.setVisibility(View.VISIBLE);
                                allowClick = false;


                            } else {
                                dosmething_homescreenlayout_staus_gridview.setAlpha(1f);
                                relativelayout_alertdialog_activity_results.setVisibility(View.GONE);
                                status_textview_letsDoSomething.setClickable(true);
                                allowClick = true;

                            }


                            if (!sharedPrefrences.getAvailable(getActivity()).equals("Yes")) {
//
                            } else if (!sharedPrefrences.getAvailable(getActivity()).equals("No")) {
                            }

                        }
                        status_id = "";
                        statusimage_id1 = null;
                        statusimage_id2 = null;
                        statusimage_id3 = null;
                        if (((MyApplication) getActivity().getApplication()).getActivityBeans().size() == 1) {
                            dosomething_activity_status_gridview.setNumColumns(1);
                        }
                        if (((MyApplication) getActivity().getApplication()).getActivityBeans().size() == 2) {
                            dosomething_activity_status_gridview.setNumColumns(2);
                        }

                        if (((MyApplication) getActivity().getApplication()).getActivityBeans().size() == 3) {
                            dosomething_activity_status_gridview.setNumColumns(3);
                        }

                        activity_dosomething_adapter = new Activity_Dosomething_Adapter(getActivity(), ((MyApplication) getActivity().getApplication()).getActivityBeans());
                        dosomething_activity_status_gridview.setAdapter(activity_dosomething_adapter);

                    }

                } else {
                    NetworkCheck.alertdialog(getActivity());

                }

            }
        }

        layout_walkthrough_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_home.setVisibility(View.GONE);
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation_home.stop();
                sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");
            }
        });
        status_activity_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allowClick = true;
                availableNow = "";
                op = "cancel";
                sharedPrefrences.setOp(getActivity(), op);
                Log.d("statussssssssssssssssss", "lllllllllll" + status_id);
                if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {
                    new StatusUpdate().execute();

                } else {
                    NetworkCheck.alertdialog(getActivity());

                }
            }
        });
        status_textview_accept_dismiss.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                fadeOut_onlythree();

                allowClick = true;

                status_textview_letsDoSomething.setClickable(true);

                relativelayout_alertdialog_only_three.setVisibility(View.GONE);

                activity_dosomething_status_grid.setEnabled(false);

            }

        });


        status_textview_accept_check.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {
                    fadeOut_alertdialog();

                    allowClick = true;

                    status_id = statusimage_id1 + "," + statusimage_id2 + "," + statusimage_id3;
                    availableNow = "Yes";
                    op = "update";
                    sharedPrefrences.setOp(getActivity(), op);
                    Log.d("statussssssssssssssssss", "lllllllllll" + status_id);


                    new StatusUpdate().execute();


                    count = 0;
                    click_action = false;
                    click_action1 = false;
                    click_action2 = false;
                    click_action3 = false;
                    click_action4 = false;
                    click_action5 = false;
                    click_action6 = false;
                    click_action7 = false;
                    click_action8 = false;
                    click_action9 = false;
                    click_action10 = false;
                    click_action11 = false;
                    click_action12 = false;
                    click_action13 = false;
                    ImageAdapter imageAdapter = new ImageAdapter(getActivity(), img_list);
                    activity_dosomething_status_grid.setAdapter(imageAdapter);
                    imageAdapter.notifyDataSetChanged();
                    relativelayout_alertdialog.setVisibility(View.GONE);

                    activity_dosomething_status_grid.setEnabled(false);

                } else {
                    NetworkCheck.alertdialog(getActivity());

                }
            }

        });

        status_textview_unaccept_check.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {
                    fadeOut_alertdialog();

                    allowClick = true;

                    status_id = statusimage_id1 + "," + statusimage_id2 + "," + statusimage_id3;
                    availableNow = "No";
                    op = "update";
                    sharedPrefrences.setOp(getActivity(), op);
                    Log.d("statussssssssssssssssss", "lllllllllll" + status_id);


                    new StatusUpdate().execute();


                    count = 0;
                    click_action = false;
                    click_action1 = false;
                    click_action2 = false;
                    click_action3 = false;
                    click_action4 = false;
                    click_action5 = false;
                    click_action6 = false;
                    click_action7 = false;
                    click_action8 = false;
                    click_action9 = false;
                    click_action10 = false;
                    click_action11 = false;
                    click_action12 = false;
                    click_action13 = false;
                    ImageAdapter imageAdapter = new ImageAdapter(getActivity(), img_list);
                    activity_dosomething_status_grid.setAdapter(imageAdapter);
                    imageAdapter.notifyDataSetChanged();
                    relativelayout_alertdialog.setVisibility(View.GONE);

                    activity_dosomething_status_grid.setEnabled(false);
                } else {
                    NetworkCheck.alertdialog(getActivity());

                }
            }

        });


//        img_list.add(new ImageBan(R.drawable.radio));

//        img_list.add(new ImageBan(R.drawable.radio));

//        img_list.add(new ImageBan(R.drawable.radio));

//        img_list.add(new ImageBan(R.drawable.radio));

//        img_list.add(new ImageBan(R.drawable.radio));

//        img_list.add(new ImageBan(R.drawable.radio));

//        img_list.add(new ImageBan(R.drawable.radio));


//

//            String imageUrl = img_list.get(i).getImag();

//            aQuery.id(R.id.status_ImageView_running).image(imageUrl, true, true, 200, 0);

//            /*aQuery.id(R.id.status_ImageView_running).image(img_list.get(i).getImag(), true, true, 0, 0, new BitmapAjaxCallback() {

////

//            @Override

//            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

//

//                iv.setImageBitmap(bm);

//

//                //do something to the bitmap

//

//            }

//

//        });*/

//        }


//     ;


//        status_layout_running.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action) {

//                    status_ImageView_running.setImageDrawable(getResources().getDrawable(R.drawable.running_active));

//                    status_TextView_running.setTextColor(getResources().getColor(R.color.red));

//                    click_action = true;

//                    count++;

//                } else {

//                    status_ImageView_running.setImageDrawable(getResources().getDrawable(R.drawable.running));

//                    status_TextView_running.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action = false;

//                    count--;

//                }

//

//            }

//        });

//        status_layout_beach.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action1) {

//                    status_ImageView_beach.setImageDrawable(getResources().getDrawable(R.drawable.beach_active));

//                    status_TextView_beach.setTextColor(getResources().getColor(R.color.red));

//                    click_action1 = true;

//                    count++;

//                } else {

//                    status_ImageView_beach.setImageDrawable(getResources().getDrawable(R.drawable.beach));

//                    status_TextView_beach.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action1 = false;

//                    count--;

//                }

//            }

//

//

//        });

//        status_layout_cycling.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action2) {

//                    status_ImageView_cycling.setImageDrawable(getResources().getDrawable(R.drawable.cycling_active));

//                    status_TextView_cycling.setTextColor(getResources().getColor(R.color.red));

//                    click_action2 = true;

//                    count++;

//                } else {

//                    status_ImageView_cycling.setImageDrawable(getResources().getDrawable(R.drawable.cycling));

//                    status_TextView_cycling.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action2 = false;

//                    count--;

//                }

//            }

//        });

//        status_layout_movies.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action3) {

//                    status_ImageView_movies.setImageDrawable(getResources().getDrawable(R.drawable.movies_active));

//                    status_TextView_movies.setTextColor(getResources().getColor(R.color.red));

//                    click_action3 = true;

//                    count++;

//                } else {

//                    status_ImageView_movies.setImageDrawable(getResources().getDrawable(R.drawable.movies));

//                    status_TextView_movies.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action3 = false;

//                    count--;

//                }

//

//            }

//        });

//        status_layout_alcohol.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action4) {

//                    status_ImageView_alcohol.setImageDrawable(getResources().getDrawable(R.drawable.alchol_active));

//                    status_TextView_alcohol.setTextColor(getResources().getColor(R.color.red));

//                    click_action4 = true;

//                    count++;

//                } else {

//                    status_ImageView_alcohol.setImageDrawable(getResources().getDrawable(R.drawable.alchol));

//                    status_TextView_alcohol.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action4 = false;

//                    count--;

//                }

//            }

//        });

//        status_layout_meals.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action5) {

//                    status_ImageView_meals.setImageDrawable(getResources().getDrawable(R.drawable.meals_active));

//                    status_TextView_meals.setTextColor(getResources().getColor(R.color.red));

//                    click_action5 = true;

//                    count++;

//                } else {

//                    status_ImageView_meals.setImageDrawable(getResources().getDrawable(R.drawable.meals));

//                    status_TextView_meals.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action5 = false;

//                    count--;

//                }

//            }

//        });

//        status_layout_coffee.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action6) {

//                    status_ImageView_coffee.setImageDrawable(getResources().getDrawable(R.drawable.coffee_active));

//                    status_TextView_coffee.setTextColor(getResources().getColor(R.color.red));

//                    click_action6 = true;

//                    count++;

//                } else {

//                    status_ImageView_coffee.setImageDrawable(getResources().getDrawable(R.drawable.coffee));

//                    status_TextView_coffee.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action6 = false;

//                    count--;

//                }

//

//            }

//        });

//        status_layout_shopping.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action7) {

//                    status_ImageView_shopping.setImageDrawable(getResources().getDrawable(R.drawable.shopping_active));

//                    status_TextView_shopping.setTextColor(getResources().getColor(R.color.red));

//                    click_action7 = true;

//                    count++;

//

//                } else {

//                    status_ImageView_shopping.setImageDrawable(getResources().getDrawable(R.drawable.shopping));

//                    status_TextView_shopping.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action7 = false;

//                    count--;

//

//                }

//            }

//        });

//        status_layout_karoke.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action8) {

//                    status_ImageView_karoke.setImageDrawable(getResources().getDrawable(R.drawable.karaoke_active));

//                    status_TextView_karoke.setTextColor(getResources().getColor(R.color.red));

//                    click_action8 = true;

//                    count++;

//                } else {

//                    status_ImageView_karoke.setImageDrawable(getResources().getDrawable(R.drawable.karaoke));

//                    status_TextView_karoke.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action8 = false;

//                    count--;

//                }

//

//            }

//        });

//        status_layout_gym.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action9) {

//                    status_ImageView_gym.setImageDrawable(getResources().getDrawable(R.drawable.gym_active));

//                    status_TextView_gym.setTextColor(getResources().getColor(R.color.red));

//                    click_action9 = true;

//                    count++;

//                } else {

//                    status_ImageView_gym.setImageDrawable(getResources().getDrawable(R.drawable.gym));

//                    status_TextView_gym.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action9 = false;

//                    count--;

//

//                }

//            }

//        });

//        status_layout_tennis.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action10) {

//                    status_ImageView_tennis.setImageDrawable(getResources().getDrawable(R.drawable.tennis_active));

//                    status_TextView_tennis.setTextColor(getResources().getColor(R.color.red));

//                    click_action10 = true;

//                    count++;

//                } else {

//                    status_ImageView_tennis.setImageDrawable(getResources().getDrawable(R.drawable.tennis));

//                    status_TextView_tennis.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action10 = false;

//                    count--;

//                }

//            }

//        });

//        status_layout_soccer.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (!click_action11) {

//                    status_ImageView_soccer.setImageDrawable(getResources().getDrawable(R.drawable.soccer_active));

//                    status_TextView_soccer.setTextColor(getResources().getColor(R.color.red));

//                    click_action11 = true;

//                    count++;

//                } else {

//                    status_ImageView_soccer.setImageDrawable(getResources().getDrawable(R.drawable.soccer));

//                    status_TextView_soccer.setTextColor(getResources().getColor(R.color.hint_color));

//                    click_action11 = false;

//                    count--;

//                }

//            }

//        });



        imageview_walkthrough_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {
                    layout_walkthrough_home.setVisibility(View.GONE);
                    if (blink_time != null) {

                        blink_time.cancel();


                        blink_time = null;

                    }
                    splashAnimation_home.stop();
                    sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                    if (count <= 3 && count != 0) {

                        fadeIn_alertdialog();

                    } else {


                        fadeIn_onlythree();

                    }

                }
            }
        });

        status_textview_letsDoSomething.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {


                    if (count <= 3 && count != 0) {

                        fadeIn_alertdialog();

                    } else {


                        fadeIn_onlythree();

                    }

                }

            }

        });


        return view;


    }



    class Blink_progress extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        if (splashAnimation_home.isRunning()) {
                            splashAnimation_home.stop();
                        } else {
                            splashAnimation_home.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO Auto-generated catch block
                    }


                }

            });
        }
    }

    class Activity_Blink1 extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        if (splashAnimation_activity1.isRunning()) {
                            splashAnimation_activity1.stop();
                        } else {
                            splashAnimation_activity1.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO Auto-generated catch block
                    }


                }

            });
        }
    }
    class Activity_Blink2 extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        if (splashAnimation_activity2.isRunning()) {
                            splashAnimation_activity2.stop();
                        } else {
                            splashAnimation_activity2.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO Auto-generated catch block
                    }


                }

            });
        }
    }
    class Activity_Blink3 extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        if (splashAnimation_activity3.isRunning()) {
                            splashAnimation_activity3.stop();
                        } else {
                            splashAnimation_activity3.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO Auto-generated catch block
                    }


                }

            });
        }
    }

    private void text_font_typeface() {

        patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");

        Typeface patron_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Regular.ttf");

        Typeface patron_medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Medium.ttf");

        Typeface myfonts_thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Thin.ttf");
        status_information_activity_time.setTypeface(patron_bold);
//        status_information_activity_now.setTypeface(patron_medium);
        status_textview_availablenow.setTypeface(patron_bold);
        status_activity_cancel.setTypeface(patron_bold);
//        status_information_activity_Anytime.setTypeface(patron_medium);
        status_dosomething_text1.setTypeface(patron_bold);
        status_dosomething_text2.setTypeface(patron_bold);
        status_dosomething_text3.setTypeface(patron_bold);
        textview_walkthrough_home.setTypeface(patron_regular);
        walkthrough_activity_TextView.setTypeface(patron_regular);
//        status_TextView_running.setTypeface(patron_bold);

//        status_TextView_beach.setTypeface(patron_bold);

//        status_TextView_cycling.setTypeface(patron_bold);

//        status_TextView_movies.setTypeface(patron_bold);

//        status_TextView_alcohol.setTypeface(patron_bold);

//        status_TextView_meals.setTypeface(patron_bold);

//        status_TextView_coffee.setTypeface(patron_bold);

//        status_TextView_shopping.setTypeface(patron_bold);

//        status_TextView_karoke.setTypeface(patron_bold);

//        status_TextView_gym.setTypeface(patron_bold);

//        status_TextView_tennis.setTypeface(patron_bold);

//        status_TextView_soccer.setTypeface(patron_bold);

        status_textview_letsDoSomething.setTypeface(patron_bold);

        status_textview_availablenow.setTypeface(patron_bold);

        status_textview_accept_check.setTypeface(patron_bold);

        status_textview_unaccept_check.setTypeface(patron_bold);

        status_textview_onlythree.setTypeface(patron_bold);

        status_textview_accept_dismiss.setTypeface(patron_bold);

    }


    // TODO: Rename method, update argument and hook method into UI event

    public void onButtonPressed(Uri uri) {

        if (mListener != null) {

            mListener.onFragmentInteraction(uri);

        }

    }


    public void fadeIn_alertdialog() {

        allowClick = false;

        Animation fadeIn = new AlphaAnimation(0, 1);

        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this

        fadeIn.setStartOffset(0);

        fadeIn.setDuration(800);


        AnimationSet animation = new AnimationSet(false); //change to false


        animation.addAnimation(fadeIn);

        relativelayout_alertdialog.setVisibility(View.VISIBLE);

        relativelayout_alertdialog.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog.clearAnimation();

            }


        });


    }


    public void fadeOut_alertdialog() {

        allowClick = false;

        Animation fadeOut = new AlphaAnimation(1, 0);

        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this

        fadeOut.setStartOffset(0);

        fadeOut.setDuration(300);


        AnimationSet animation = new AnimationSet(false); //change to false

        animation.addAnimation(fadeOut);

        relativelayout_alertdialog.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {

            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog.clearAnimation();

            }


        });


    }


    public void fadeIn_onlythree() {

        allowClick = false;

        Animation fadeIn = new AlphaAnimation(0, 1);

        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this

        fadeIn.setStartOffset(0);

        fadeIn.setDuration(800);


        AnimationSet animation = new AnimationSet(false); //change to false


        animation.addAnimation(fadeIn);

        relativelayout_alertdialog_only_three.setVisibility(View.VISIBLE);

        relativelayout_alertdialog_only_three.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {

                status_textview_letsDoSomething.setClickable(false);


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

                status_textview_letsDoSomething.setClickable(false);


            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog_only_three.clearAnimation();

            }


        });


    }


    public void fadeOut_onlythree() {

        allowClick = false;

        Animation fadeOut = new AlphaAnimation(1, 0);

        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this

        fadeOut.setStartOffset(0);

        fadeOut.setDuration(300);


        AnimationSet animation = new AnimationSet(false); //change to false

        animation.addAnimation(fadeOut);

        relativelayout_alertdialog_only_three.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                relativelayout_alertdialog_only_three.clearAnimation();

            }


        });


    }


    @Override

    public void onDetach() {

        super.onDetach();

        mListener = null;

    }


    /**
     * This interface must be implemented by activities that contain this
     * <p/>
     * fragment to allow an interaction in this fragment to be communicated
     * <p/>
     * to the activity and potentially other fragments contained in that
     * <p/>
     * activity.
     * <p/>
     * <p/>
     * <p/>
     * See the Android Training lesson <a href=
     * <p/>
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * <p/>
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name

        public void onFragmentInteraction(Uri uri);

    }


    @Override

    public void onResume() {

        super.onResume();

        Log.d("TTTT", "YYYY" + sharedPrefrences.getVersion_dosomething(getActivity()));

//        if (sharedPrefrences.getVersion_dosomething(getActivity()).equals("1.0")) {


//            Toast.makeText(getActivity().getApplicationContext(),"VERSION MISMATCH",Toast.LENGTH_LONG).show();
//
//        } else {
//
//            Toast.makeText(getActivity().getApplicationContext(), "VERSION MISMATCH", Toast.LENGTH_LONG).show();
//
////            dbAdapter.open();
//
////            int countStatus = dbAdapter.getStatusCount();
//
////            dbAdapter.close();
//
////            Log.d("countttttttttttttttt", String.valueOf(countStatus));
//
////            if (countStatus == 12) {
//
////                dbAdapter.open();
//
////                img_list = dbAdapter.getStatusImages();
//
////                ImageAdapter imageAdapter = new ImageAdapter(getActivity(), img_list);
//
////                activity_dosomething_status_grid.setAdapter(imageAdapter);
//
////                dbAdapter.close();
//
////
//
////            } else {
//
////                new VersionData().execute();
//
////            }
//
//        }


//        status_textview_letsDoSomething.setOnClickListener(new View.OnClickListener() {

//            @Override

//            public void onClick(View v) {

//                if (count == 3) {

//                    AlertdialogFragment alertdialogFragment = new AlertdialogFragment();

//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

//                    fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);

//                    fragmentTransaction.add(R.id.status_container, alertdialogFragment);

//                    fragmentTransaction.commit();

//                    status_layout_running.setClickable(false);

//                    status_layout_beach.setClickable(false);

//                    status_layout_cycling.setClickable(false);

//                    status_layout_movies.setClickable(false);

//                    status_layout_alcohol.setClickable(false);

//                    status_layout_meals.setClickable(false);

//                    status_layout_coffee.setClickable(false);

//                    status_layout_shopping.setClickable(false);

//                    status_layout_karoke.setClickable(false);

//                    status_layout_gym.setClickable(false);

//                    status_layout_tennis.setClickable(false);

//                    status_layout_soccer.setClickable(false);

//                    status_textview_letsDoSomething.setClickable(false);

//                } else {

//                    AlertDialogStausThree alertDialogStausThree = new AlertDialogStausThree();

//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

//                    fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);

//                    fragmentTransaction.add(R.id.status_container, alertDialogStausThree);

//                    fragmentTransaction.commit();

//                    status_layout_running.setClickable(false);

//                    status_layout_beach.setClickable(false);

//                    status_layout_cycling.setClickable(false);

//                    status_layout_movies.setClickable(false);

//                    status_layout_alcohol.setClickable(false);

//                    status_layout_meals.setClickable(false);

//                    status_layout_coffee.setClickable(false);

//                    status_layout_shopping.setClickable(false);

//                    status_layout_karoke.setClickable(false);

//                    status_layout_gym.setClickable(false);

//                    status_layout_tennis.setClickable(false);

//                    status_layout_soccer.setClickable(false);

//                    status_textview_letsDoSomething.setClickable(false);

//                }

//            }

//        });

    }

    private class Activity_Dosomething_Adapter extends BaseAdapter {
        Activity activity;
        ArrayList<ActivityBean> dosomething_beans = new ArrayList<>();
        LayoutInflater layoutInflater = null;

        public Activity_Dosomething_Adapter(Activity mactivity, ArrayList<ActivityBean> dosomething_beans) {
            this.dosomething_beans = dosomething_beans;
            this.activity = mactivity;
            text_font_typeface();
        }

        @Override
        public int getCount() {
            return dosomething_beans.size();
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
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.fragment_status_layout, parent, false);
            Holder holder = new Holder();

//        text_font_typeface();


            holder.activity_dosomething_status_layout = (LinearLayout) convertView.findViewById(R.id.activity_dosomething_status_layout);

            holder.activity_dosomething_status_imageview = (GridViewItem) convertView.findViewById(R.id.activity_dosomething_status_imageview);

            holder.activity_dosomething_status_textview = (TextView) convertView.findViewById(R.id.activity_dosomething_status_textview);
            holder.activity_dosomething_status_textview.setText(dosomething_beans.get(position).getImage_name());
            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));
            holder.activity_dosomething_status_textview.setTypeface(patron_bold);
            holder.activity_dosomething_status_textview.setTextSize(14);


            status_information_activity_time.setText("Last Selected: \n" + dosomething_beans.get(position).getLastActivity());


            aQuery.id(holder.activity_dosomething_status_imageview).image(dosomething_beans.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                @Override

                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                    if (status.getCode() == 200) {

                        BitmapDrawable bd = new BitmapDrawable(bm);

                        iv.setBackgroundDrawable(bd);


                    }

                }

            });
            return convertView;
        }

        private class Holder {
            LinearLayout activity_dosomething_status_layout;

            GridViewItem activity_dosomething_status_imageview;

            TextView activity_dosomething_status_textview;
        }
    }

    private class ImageAdapter extends BaseAdapter {

        ArrayList<ImageBan> img_list = new ArrayList<ImageBan>();

        LayoutInflater layoutInflater = null;

        Activity activity;

        Context mContext;


        public ImageAdapter(Activity mactivity, ArrayList<ImageBan> img_list) {

            this.activity = mactivity;

            this.mContext = mactivity;

            this.img_list = img_list;

            text_font_typeface();

            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }


        @Override

        public int getCount() {

            return img_list.size();

        }


        @Override

        public Object getItem(int position) {

            return position;

        }


        @Override

        public long getItemId(int position) {

            return position;

        }


        class Holder {

            LinearLayout activity_dosomething_status_layout;

            GridViewItem activity_dosomething_status_imageview;

            TextView activity_dosomething_status_textview;


        }


        @SuppressLint("ViewHolder")

        @Override

        public View getView(final int position, View convertView, ViewGroup parent) {


            final Holder holder = new Holder();

//        text_font_typeface();

            convertView = layoutInflater.inflate(R.layout.fragment_status_layout, parent, false);

            holder.activity_dosomething_status_layout = (LinearLayout) convertView.findViewById(R.id.activity_dosomething_status_layout);

            holder.activity_dosomething_status_imageview = (GridViewItem) convertView.findViewById(R.id.activity_dosomething_status_imageview);

            holder.activity_dosomething_status_textview = (TextView) convertView.findViewById(R.id.activity_dosomething_status_textview);

            holder.activity_dosomething_status_textview.setTypeface(patron_bold);
            holder.activity_dosomething_status_layout.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                    if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {


                        if (allowClick) {




                            MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.click_one);

                            mp.start();

                            switch (position) {

                                case 0:

                                    Log.d("positionnnnn", String.valueOf(position));

                                    if (!click_action) {

                                        try {


                                            if (count >= 3) {

                                                mp.pause();

                                                fadeIn_onlythree();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {


                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));

                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }


                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {


                                        count--;

                                        Log.d("counnnntttttttttttttt", String.valueOf(count));


                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });

                                            click_action = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }


                                            Log.d("statusimage_id1", "" + statusimage_id1);

                                            Log.d("statusimage_id2", "" + statusimage_id2);

                                            Log.d("statusimage_id3", "" + statusimage_id3);

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 1:

                                    if (!click_action1) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {

                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));


                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }

                                                BitmapAjaxCallback.setCacheLimit(50);


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));


                                                        }

                                                    }

                                                });

                                                click_action1 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        count--;

                                        Log.d("counnnntttttttttttttt",String.valueOf(count));


                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });

                                            click_action1 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }


                                            Log.d("statusimage_id1", "" + statusimage_id1);

                                            Log.d("statusimage_id2", "" + statusimage_id2);

                                            Log.d("statusimage_id3", "" + statusimage_id3);


                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 2:

                                    if (!click_action2) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {


                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));

                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action2 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        count--;

                                        Log.d("counnnntttttttttttttt", String.valueOf(count));


                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });


                                            click_action2 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }


                                            Log.d("statusimage_id1", "" + statusimage_id1);

                                            Log.d("statusimage_id2", "" + statusimage_id2);

                                            Log.d("statusimage_id3", "" + statusimage_id3);

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 3:

                                    if (!click_action3) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {

                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));

                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });


                                                click_action3 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        count--;

                                        Log.d("counnnntttttttttttttt", String.valueOf(count));

                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });


                                            click_action3 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }


                                            Log.d("statusimage_id1", "" + statusimage_id1);

                                            Log.d("statusimage_id2", "" + statusimage_id2);

                                            Log.d("statusimage_id3", "" + statusimage_id3);

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 4:

                                    if (!click_action4) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {


                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));


                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }

                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action4 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        count--;

                                        Log.d("counnnntttttttttttttt", String.valueOf(count));


                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });


                                            click_action4 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }


                                            Log.d("statusimage_id1", "" + statusimage_id1);

                                            Log.d("statusimage_id2", "" + statusimage_id2);

                                            Log.d("statusimage_id3", "" + statusimage_id3);

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 5:

                                    if (!click_action5) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {


                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));

                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action5 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        count--;

                                        Log.d("counnnntttttttttttttt", String.valueOf(count));


                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });

                                            click_action5 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }


                                            Log.d("statusimage_id1", "" + statusimage_id1);

                                            Log.d("statusimage_id2", "" + statusimage_id2);

                                            Log.d("statusimage_id3", "" + statusimage_id3);

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 6:

                                    if (!click_action6) {

                                        try {


                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {

                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));


                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action6 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {

                                            count--;

                                            Log.d("counnnntttttttttttttt", String.valueOf(count));


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });

                                            click_action6 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }


                                            Log.d("statusimage_id1", "" + statusimage_id1);

                                            Log.d("statusimage_id2", "" + statusimage_id2);

                                            Log.d("statusimage_id3", "" + statusimage_id3);

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 7:

                                    if (!click_action7) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {

                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));

                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action7 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {

                                            count--;

                                            Log.d("counnnntttttttttttttt", String.valueOf(count));


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });

                                            click_action7 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 8:

                                    if (!click_action8) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {

                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));

                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }

                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action8 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        count--;

                                        Log.d("counnnntttttttttttttt", String.valueOf(count));


                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });

                                            click_action8 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 9:

                                    if (!click_action9) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {

                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));

                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action9 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        count--;

                                        Log.d("counnnntttttttttttttt", String.valueOf(count));


                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });

                                            click_action9 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 10:

                                    if (!click_action10) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else


                                            {

                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));

                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action10 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        count--;

                                        Log.d("counnnntttttttttttttt", String.valueOf(count));


                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });

                                            click_action10 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;

                                case 11:

                                    if (!click_action11) {

                                        try {

                                            if (count >= 3) {

                                                mp.pause();

                                                Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

                                                // Vibrate for 500 milliseconds

                                                vibrator.vibrate(500);

                                                fadeIn_onlythree();

                                                activity_dosomething_status_grid.setEnabled(false);


                                            } else {

                                                count++;

                                                Log.d("counnnntttttttttttttt", String.valueOf(count));

                                                if(count>=1)
                                                {
                                                    image1_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink1 != null) {

                                                        activity_blink1.cancel();


                                                        activity_blink1 = null;

                                                    }
                                                    splashAnimation_activity1.stop();
                                                }


                                                if(count>=2)
                                                {
                                                    image2_walkthrough_activity.setVisibility(View.GONE);
                                                    if (activity_blink2 != null) {

                                                        activity_blink2.cancel();


                                                        activity_blink2 = null;

                                                    }
                                                    splashAnimation_activity2.stop();
                                                }


                                                if(count>=3)
                                                {


                                                    layout_walkthrough_activity.setVisibility(View.GONE);
                                                    image3_walkthrough_activity.setVisibility(View.GONE);
                                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                                    if (activity_blink3 != null) {

                                                        activity_blink3.cancel();


                                                        activity_blink3 = null;

                                                    }
                                                    splashAnimation_activity3.stop();


                                                    if (sharedPrefrences.getWalkThroughHomescreen(getActivity()).equals("false")) {
                                                        layout_walkthrough_home.setVisibility(View.VISIBLE);
                                                        blink_time = new Timer();
                                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                                        splashAnimation_home.start();
                                                        sharedPrefrences.setWalkThroughHomescreen(getActivity(), "true");

                                                    }
                                                }


                                                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                    @Override

                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                        if (status.getCode() == 200) {

                                                            BitmapDrawable bd = new BitmapDrawable(bm);

                                                            iv.setBackgroundDrawable(bd);

                                                            holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.text_red));

                                                        }

                                                    }

                                                });

                                                click_action11 = true;

                                                if (statusimage_id1 == null) {

                                                    statusimage_id1 = String.valueOf(img_list.get(position).getImage_id());

                                                } else if (statusimage_id2 == null) {

                                                    statusimage_id2 = String.valueOf(img_list.get(position).getImage_id());

                                                } else {

                                                    statusimage_id3 = String.valueOf(img_list.get(position).getImage_id());

                                                }

                                                Log.d("statusimage_id1", "" + statusimage_id1);

                                                Log.d("statusimage_id2", "" + statusimage_id2);

                                                Log.d("statusimage_id3", "" + statusimage_id3);

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    } else {

                                        count--;

                                        Log.d("counnnntttttttttttttt", String.valueOf(count));


                                        holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

                                        holder.activity_dosomething_status_textview.setTextColor(getResources().getColor(R.color.hint_color));


                                        try {


                                            aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                                                @Override

                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                                                    if (status.getCode() == 200) {

                                                        BitmapDrawable bd = new BitmapDrawable(bm);

                                                        iv.setBackgroundDrawable(bd);

                                                    }

                                                }

                                            });


                                            click_action11 = false;

                                            if (statusimage_id1 != null) {

                                                statusimage_id1 = null;

                                            } else if (statusimage_id2 != null) {

                                                statusimage_id2 = null;

                                            } else {

                                                statusimage_id3 = null;

                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                    }


                                    break;


                            }


                        }

                    } else {

                        NetworkCheck.alertdialog(getActivity());

                    }

                }

            });


//            for (position=0;position<=imageBans.size();position++) {

//                //urlImageStatus = imageBans.get(position).getImag_inActive();

////            aQuery.id(R.id.status_ImageView_running).image(imageUrl, true, true, 200, 0);

//            aQuery.id(R.id.status_ImageView_running).image(imageBans.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

//

//            @Override

//            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

//

//                iv.setImageBitmap(bm);

//

//                //do something to the bitmap

//

//            }

//

//        });

            holder.activity_dosomething_status_textview.setText(img_list.get(position).getImage_name());

            try {

                aQuery.id(holder.activity_dosomething_status_imageview).image(img_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {

                    @Override

                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                        if (status.getCode() == 200) {

                            BitmapDrawable bd = new BitmapDrawable(bm);

                            iv.setBackgroundDrawable(bd);


                        }

                    }

                });


            } catch (Exception e) {

                e.printStackTrace();

            }


            return convertView;

        }

    }


    //   private class ImageAynctask extends AsyncTask<Void, Void, Void> {

//

//        @Override

//        protected Void doInBackground(Void... params) {

//            HashMap<String, String> paramsImages = new HashMap<>();

//            paramsImages.put(TAG_SESSIONID, SessionId);

//

//            json_string = jsonfunctions.postToURL(urlImageStatus, paramsImages);

//

//            Log.v("jasonImage=======>", String.valueOf(paramsImages));

//

//            try {

//                json_object = new JSONObject(json_string);

//                json_content = json_object.getJSONObject("dosomethinglist");

//

//            } catch (JSONException e) {

//                e.printStackTrace();

//            }

//            return null;

//

//        }

//        @Override

//        protected void onPostExecute(Void result) {

//            super.onPostExecute(result);

//            // Dismiss the progress dialog

//

//            try {

//                if (json_object.has("dosomethinglist")) {

//                    if (json_content.getString("status").equalsIgnoreCase("success")) {

//                        JSONArray dashboard_status_images = json_content.getJSONArray("list");

//                        Log.d("dashboard_status_images", String.valueOf(dashboard_status_images));

//

//                        for (int i = 0; i < dashboard_status_images.length(); i++) {

//                            JSONObject json_array_object = dashboard_status_images.getJSONObject(i);

//                            if (json_array_object.has("Id")) {

//                                id = json_array_object.getInt("Id");

//                            }

//                            if (json_array_object.has("name")) {

//                                _title = json_array_object.getString("name");

//                            }

//                            if(json_array_object.has("ActiveImage"))

//                            {

//                                image_act = json_array_object.getString("ActiveImage");

//

//                            }

//                            if (json_array_object.has("InactiveImage")) {

//                                image_inact = json_array_object.getString("InactiveImage");

//                            }

//                            Log.d("jsonarray=========>",_title+id+image_act+image_inact);

//

//                            img_list.add(new ImageBan(id, _title, image_act,image_inact));

//                        }

//

//                    } else if (json_content.getString("status").equalsIgnoreCase("error")) {

//                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();

//

//                    }

//                }

//

//            } catch (JSONException e) {

//                e.printStackTrace();

//            }

//        }

    class StatusUpdate extends AsyncTask<Void, Void, Boolean> {

        JSONObject opj;

        JSONArray content;
        private String Available;
        private String string_status = "";
        private String activitylist;

        Exception error;
        String statusUpdate_Api_link;
        String operation;

        String activity1_image;
        String activity2_image;
        String activity3_image;
        String activity1_name;
        String activity2_name;
        String activity3_name;

        protected void onPreExecute() {

            super.onPreExecute();

            if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {

                if (getActivity() != null) {
                    statusUpdate_Api_link = getActivity().getResources().getString(R.string.dosomething_apilink_string_activity);
                    operation = sharedPrefrences.getOp(getActivity());
                }
                activity_dosomething_status_grid.setEnabled(false);
                status_textview_letsDoSomething.setEnabled(false);
                status_textview_letsDoSomething.setClickable(false);
                sessionid = sharedPrefrences.getSessionid(getActivity());
                relativelayout_progressImage.setVisibility(View.VISIBLE);
                kbv.setVisibility(View.VISIBLE);
                timer = new Timer();
                timer.schedule(new AutoSlider(), 0, 1350);
                splashAnimation.start();
                allowClick = false;
                if (dosomething_beans != null) {
                    dosomething_beans.clear();
                }
            }

        }


        @Override

        protected Boolean doInBackground(Void... params) {


            try {

                HashMap<String, Object> map = new HashMap<>();

                map.put("sessionid", sessionid);
                if (!status_id.equals("")) {
                    map.put("dosomething_id", status_id);
                }
                if (!availableNow.equals("")) {
                    map.put("available_now", availableNow);
                }

                map.put("op", operation);


                json_string = jsonfunctions.postToURL(statusUpdate_Api_link, map);
                json_object = new JSONObject(json_string);

                if (json_object.has("activity")) {
                    string_status = "activity";


                    json_content = json_object.getJSONObject("activity");

                    if (json_content.has("Available")) {
                        Available = json_content.getString("Available");
                    } else {
                        Available = "";
                    }
                    if (json_content.has("Lastupdated")) {
                        updatedTime = json_content.getString("Lastupdated");
                    } else {
                        updatedTime = "";
                    }
                    if (json_content.has("LastActivity")) {
                        LastActivity = json_content.getString("LastActivity");

                    }
                    if (json_content.has("activityList")) {

                        activitylist = json_content.getString("activityList");
                        if (!activitylist.equals("")) {
                            JSONArray hobbiesArray = json_content.getJSONArray("activityList");
                            if (hobbiesArray.length() > 0) {
                                for (int i = 0; i < hobbiesArray.length(); i++) {
                                    JSONObject details = hobbiesArray.getJSONObject(i);
                                    dosomething_id = details.getInt("Id");
                                    dosomething_name = details.getString("name");
                                    dosomething_Image = details.getString("ActiveImage");
                                    dosomething_ActiveImage = details.getString("InactiveImage");
                                    dosomething_beans.add(new ActivityBean(dosomething_id, dosomething_name, dosomething_Image, dosomething_ActiveImage, LastActivity));
                                    allowClick = false;

                                }
                            }
                        } else {
                            Available = "";
                            if (getActivity() != null) {
                                sharedPrefrences.setOp(getActivity(), "cancel");
                            }


                        }

                    } else {
                        allowClick = true;
                        dosomething_beans.add(new ActivityBean(0, "", "", "", ""));

                    }


                    if (json_content.getString("status").equalsIgnoreCase("success")) {

                        if (json_content.getString("Message").equalsIgnoreCase("Activity Canelled successfully")) {
                            if (getActivity() != null) {
                                sharedPrefrences.setOp(getActivity(), "getlast");
                            }

                            allowClick = true;

                        }


                    }

                } else if (json_object.has("status")) {
                    if (json_object.getString("status").equalsIgnoreCase("sessionexpried")) {


                        string_status = "sessionexpried";
                    }
                } else if (json_object.has("error")) {
                    if (json_object.getString("error").equalsIgnoreCase("InvalidSession")) {
                        string_status = "InvalidSession";
                    }

                }
                if (getActivity() != null) {
                    sharedPrefrences.setAvailable(getActivity(), Available);
                }
                return true;

            } catch (Exception e) {
                Log.e("Timeout Exception: ", e.toString());
                e.printStackTrace();
                error = e;
                return false;
            }


        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);


            if (s) {
                try {

                    relativelayout_progressImage.setVisibility(View.GONE);
                    kbv.setVisibility(View.GONE);
                    if (timer != null) {

                        timer.cancel();


                        timer = null;

                    }
                    splashAnimation.stop();
                    if (string_status.equalsIgnoreCase("activity")) {
                        ((MyApplication) getActivity().getApplication()).setActivityBeans(dosomething_beans);

                        dosmething_homescreenlayout_staus_gridview.setAlpha(0.2f);
                        status_information_activity_time.setText("Last Selected: \n" + LastActivity);

                        if (getActivity() != null) {
                            sharedPrefrences.setOp(getActivity(), "getlast");
                            if (!sharedPrefrences.getAvailable(getActivity()).equals("")) {
                                relativelayout_alertdialog_activity_results.setVisibility(View.VISIBLE);
                                allowClick = false;
                                status_textview_letsDoSomething.setClickable(false);
                                status_textview_letsDoSomething.setEnabled(false);

                            } else {
                                if (sharedPrefrences.getWalkThroughActivity(getActivity()).equals("false")) {
                                    layout_walkthrough_activity.setVisibility(View.VISIBLE);
                                    activity_blink1 = new Timer();
                                    activity_blink1.schedule(new Activity_Blink1(), 0, 340);
                                    splashAnimation_activity1.start();
                                    activity_blink2 = new Timer();
                                    activity_blink2.schedule(new Activity_Blink2(), 0, 340);
                                    splashAnimation_activity2.start();
                                    activity_blink3 = new Timer();
                                    activity_blink3.schedule(new Activity_Blink3(), 0, 340);
                                    splashAnimation_activity3.start();
                                    sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                                }

                                dosmething_homescreenlayout_staus_gridview.setAlpha(1f);
                                relativelayout_alertdialog_activity_results.setVisibility(View.GONE);
                                allowClick = true;
                                status_textview_letsDoSomething.setClickable(true);
                                status_textview_letsDoSomething.setEnabled(true);

                            }






                            /*if (!activitylist.equals("")) {


                                activity1_image=dosomething_beans.get(0).getImag_Active();
                                activity2_image=dosomething_beans.get(1).getImag_Active();
                                activity3_image=dosomething_beans.get(2).getImag_Active();
                                activity1_name=dosomething_beans.get(0).getImage_name();
                                activity2_name=dosomething_beans.get(1).getImage_name();
                                activity3_name=dosomething_beans.get(2).getImage_name();


                                    aQuery.id(status_dosomething_image1).image(activity1_image, true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                iv.setImageBitmap(bm);
                                                activity_dosomething_status_grid.setEnabled(false);
                                                status_dosomething_text1.setText(activity1_name);

                                                status_dosomething_text1.setTextColor(getResources().getColor(R.color.red));
                                                status_dosomething_layout1.setVisibility(View.VISIBLE);
                                            }


                                        }
                                    });



                                    aQuery.id(status_dosomething_image2).image(activity2_image, true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                iv.setImageBitmap(bm);
                                                status_dosomething_layout2.setVisibility(View.VISIBLE);
                                                status_dosomething_text2.setText(activity2_name);

                                                status_dosomething_text2.setTextColor(getResources().getColor(R.color.red));
                                            }
                                        }
                                    });




                                    aQuery.id(status_dosomething_image3).image(activity3_image, true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                iv.setImageBitmap(bm);
                                                status_dosomething_layout3.setVisibility(View.VISIBLE);
                                                status_dosomething_text3.setText(activity3_name);

                                                status_dosomething_text3.setTextColor(getResources().getColor(R.color.red));
                                            }
                                        }
                                    });



                            }*/






               /*             if (!sharedPrefrences.getAvailable(getActivity()).equals("Yes")) {
//
                            } else if (!sharedPrefrences.getAvailable(getActivity()).equals("No")) {
                            }*/

                        }
                        status_id = "";
                        statusimage_id1 = null;
                        statusimage_id2 = null;
                        statusimage_id3 = null;


                        if (dosomething_beans.size() == 1) {
                            dosomething_activity_status_gridview.setNumColumns(1);
                        }
                        if (dosomething_beans.size() == 2) {
                            dosomething_activity_status_gridview.setNumColumns(2);
                        }

                        if (dosomething_beans.size() == 3) {
                            dosomething_activity_status_gridview.setNumColumns(3);
                        }
                        activity_dosomething_adapter = new Activity_Dosomething_Adapter(getActivity(), ((MyApplication) getActivity().getApplication()).getActivityBeans());
                        dosomething_activity_status_gridview.setAdapter(activity_dosomething_adapter);

                    } else if (string_status.equalsIgnoreCase(""))

                    {


                        relativelayout_progressImage.setVisibility(View.GONE);
                        kbv.setVisibility(View.GONE);
                        if (timer != null) {

                            timer.cancel();


                            timer = null;

                        }
                        splashAnimation.stop();
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.dosomething_alert_save_changes);
                        TextView status_textview_save = (TextView) dialog.findViewById(R.id.status_textview_save);
                        TextView alert_textview_save = (TextView) dialog.findViewById(R.id.alert_textview_save);
                        TextView alert_textview_save_cancel = (TextView) dialog.findViewById(R.id.alert_textview_save_cancel);
                        status_textview_save.setText("Something went wrong");
                        alert_textview_save.setVisibility(View.GONE);
                        alert_textview_save_cancel.setText("Retry");
                        dialog.show();
                        alert_textview_save_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (getActivity() != null) {
                                    if (sharedPrefrences.getOp(getActivity()).equals("getlast")) {
                                        availableNow = "";
                                        status_id = "";
                                        activity_dosomething_status_grid.setEnabled(false);
                                        if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {

                                            new StatusUpdate().execute();
                                            splashAnimation.start();


                                        } else {
                                            NetworkCheck.alertdialog(getActivity());

                                        }

                                    }
                                }

                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {

                        sharedPrefrences.setLogin(getActivity(), "");
                        sharedPrefrences.setDateofBirth(getActivity(), "");
                        sharedPrefrences.setFirstname(getActivity(), "");
                        sharedPrefrences.setDeviceToken(getActivity(), "");
                        sharedPrefrences.setLastname(getActivity(), "");
                        sharedPrefrences.setGender(getActivity(), "");
                        sharedPrefrences.setPassword(getActivity(), "");
                        sharedPrefrences.setUserId(getActivity(), "");
                        sharedPrefrences.setSessionid(getActivity(), "");
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
                        Intent i = new Intent(getActivity(), DoSomeThingLogin.class);
                        startActivity(i);
                        getActivity().finish();
                    }

                } catch (Exception e)

                {
                    Log.e("Timeout Exception: ", e.toString());
                    e.printStackTrace();
                }
            } else {
                if (error != null) {
                    relativelayout_progressImage.setVisibility(View.GONE);
                    kbv.setVisibility(View.GONE);
                    if (timer != null) {

                        timer.cancel();


                        timer = null;

                    }
                    splashAnimation.stop();
                }
            }


        }
    }


    class StatusBackground extends AsyncTask<Void, Void, Boolean> {

        JSONObject opj;

        JSONArray content;
        Exception error;
        String statusBackground_Api_url;

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

            if (NetworkCheck.isNetworkAvailable(getActivity()) || NetworkCheck.isWifiAvailable(getActivity())) {

                relativelayout_progressImage.setVisibility(View.VISIBLE);
                kbv.setVisibility(View.VISIBLE);
                timer = new Timer();
                timer.schedule(new AutoSlider(), 0, 1350);
                splashAnimation.start();
                if (getActivity() != null) {
                    sessionid = sharedPrefrences.getSessionid(getActivity());
                    statusBackground_Api_url = getActivity().getResources().getString(R.string.dosomething_apilink_string_dosomethinglist);
                }

            }

        }


        protected Boolean doInBackground(Void... params) {


            try {


                HashMap<String, Object> map = new HashMap<String, Object>();

                map.put("sessionid", sessionid);

                response = jsonfunctions.postToURL(statusBackground_Api_url, map);

                opj = new JSONObject(response);

                Log.v("response_goods", response);

                JSONObject dosumthng = opj.getJSONObject("dosomethinglist");

                if (dosumthng.getString("status").equalsIgnoreCase("success")) {

                    content = dosumthng.getJSONArray("list");


                    for (int i = 0; i < content.length(); i++) {

                        JSONObject details = content.getJSONObject(i);

                        int Id = details.getInt("Id");
                        Log.v("Id", String.valueOf(Id));

                        String name = details.getString("name");

                        Log.v("name", name);


                        String ActiveImage = details.getString("ActiveImage");


                        String InactiveImage = details.getString("InactiveImage");


                        img_list.add(new ImageBan(Id, name, ActiveImage, InactiveImage));


                        dbAdapter.open();


                        dbAdapter.addStatus(new ImageBan(Id, name, ActiveImage, InactiveImage));


                        dbAdapter.close();


                        Log.d("image_id", String.valueOf(Id));


                        Log.d("image_name", name);


                        Log.d("image_active", ActiveImage);


                        Log.d("image_inactive", InactiveImage);

                        Log.d("img_list.size()", String.valueOf(img_list.size()));

                    }


                } else if (dosumthng.getString("status").equalsIgnoreCase("error")) {

                    if (dosumthng.getString("Message").equalsIgnoreCase("Allowed only for 3 actions")) {

//                          pDialog.dismiss();


//                            transparentProgressDialog.dismiss();

                    }


                }
                return true;
            } catch (JSONException e) {

                e.printStackTrace();
                error = e;
                return false;

            }


        }


        protected void onPostExecute(Boolean result) {

            //TODO Auto-generated method stub

            super.onPostExecute(result);

//            Toast.makeText(getActivity(), "Version match", Toast.LENGTH_SHORT).show();


            if (result) {
                try {


                    if (getActivity() != null) {

//                    transparentProgressDialog.dismiss();

                        relativelayout_progressImage.setVisibility(View.GONE);
                        kbv.setVisibility(View.GONE);
                        if (timer != null) {

                            timer.cancel();


                            timer = null;

                        }
                        splashAnimation.stop();
                       /* if (sharedPrefrences.getWalkThroughActivity(getActivity()).equals("false")) {
                            layout_walkthrough_activity.setVisibility(View.VISIBLE);
                            activity_blink1 = new Timer();
                            activity_blink1.schedule(new Activity_Blink1(), 0, 340);
                            splashAnimation_activity1.start();
                            activity_blink2 = new Timer();
                            activity_blink2.schedule(new Activity_Blink2(), 0, 340);
                            splashAnimation_activity2.start();
                            activity_blink3 = new Timer();
                            activity_blink3.schedule(new Activity_Blink3(), 0, 340);
                            splashAnimation_activity3.start();
                            sharedPrefrences.setWalkThroughActivity(getActivity(), "true");
                        }*/
                        ImageAdapter imageAdapter = new ImageAdapter(getActivity(), img_list);

                        activity_dosomething_status_grid.setAdapter(imageAdapter);

//                    pDialog.dismiss();

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }
            } else {
                if (error != null) {
                    relativelayout_progressImage.setVisibility(View.GONE);
                    kbv.setVisibility(View.GONE);
                    if (timer != null) {

                        timer.cancel();


                        timer = null;

                    }
                    splashAnimation.stop();
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


