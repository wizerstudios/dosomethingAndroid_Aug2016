package com.dosomething.android.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.dosomething.android.Beanclasses.Dosomething_Bean;
import com.dosomething.android.Beanclasses.Filterbean;
import com.dosomething.android.Beanclasses.HobbiesBean;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.dosomething.android.DoSomeThingLogin;
import com.dosomething.android.DoSomethingStatus;
import com.dosomething.android.MyApplication;
import com.dosomething.android.R;
import com.google.android.gms.analytics.Tracker;
import com.paging.gridview.PagingGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzip.runOnUiThread;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoSomethingNearMe.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoSomethingNearMe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoSomethingNearMe extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_PROFILEUSERID = "profile_user_id";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private OnFragmentInteractionListener mListener;
    RecyclerView activity_dosomething_nearme;
    boolean click_action = false;
    boolean click_action1 = false;
    Typeface patron_bold;
    Typeface patron_regular;
    Typeface patron_medium;
    Typeface myfonts_thin;
    View activity_dosomething_nearme_view_top;
    View activity_dosomething_nearme_view_bottom;
    private static String url = "http://wiztestinghost.com/dosomething/nearestusers?";
    private static String urlrequestsend = "http://wiztestinghost.com/dosomething/sendrequest?";
    private static final String TAG_SESSIONID = "sessionid";
    private static final String TAG_REQUESTSEND_USERID = "request_send_user_id";
    private static final String TAG_CHATSTART = "chatstart";
    private static final String TAG_LATITUDE = "latitude";
    private static final String TAG_LONGITUDE = "longitude";
    private static final String TAG_FILTER_STATUS = "filter_status";
    private static final String TAG_FILTER_GENDER = "filter_gender";
    private static final String TAG_FILTER_AGERANGE = "filter_agerange";
    private static final String TAG_FILTER_DISTANCE = "filter_distance";
    private static final String TAG_USERLISTPAGE = "page";
    String sessionid, filter_status, filter_gender, filter_agerange, filter_distance, page, json_string, response, request_send_user_id, chatstart;
    String latitude;
    String longitude;
    Jsonfunctions jsonfunctions;
    JSONObject json_object, json_content, details;
    private ProgressDialog pDialog;
    SharedPrefrences sharedPreferences;
    ArrayList<Filterbean> filter_list;
    ArrayList<Filterbean> filter_list1;
    ArrayList<Dosomething_Bean> dosomething_list;
    AQuery aQuery;
    Set<String> hs = new HashSet<>();
    private TransparentProgressDialog pd;
    SwipeRefreshLayout nearme_profile_refreshlayout;
    ProgressBar pull_to_refresh_progress;
    PagingGridView dosomething_nearmelist_paginggridview;
    ProfileAdapter.ViewHolder holder;
    ProfileViewAdapter.DataObjectHolder objectHolder;
    RelativeLayout dosomething_nearme_matched_profile_popup;
    LinearLayout dosomething_nearme_matched_profile_popup_layout;
    ImageView dosomething_nearme_matched_profile_userImage, dosomething_nearme_matched_profile_near_userImage;
    private int matchedUser_user_id;
    private String matchedUser_first_name, matchedUser_last_name, matchedUser_about;
    private String matchedUser_image1;
    String dosmething_nearuser_matched_Api_staus, dosmething_nearuser_matched_grid_control = "";
    TextView dosomething_nearme_matched_profile_chat_dosomething_textview, dosomething_nearme_matched_profile_name_textview, activity_dosomething_nearme_textview_nouser;
    LinearLayout dosomething_nearme_gridview_layout;
    private ArrayList<Dosomething_Bean> dosomething_beans = new ArrayList<>();
    ArrayList<HobbiesBean> stringArrayList = new ArrayList<>();
    ArrayList<String> stringArrayList1 = new ArrayList<>();
    ArrayList<String> stringArrayLis2 = new ArrayList<>();
    ArrayList<String> stringArrayList3 = new ArrayList<>();
    private ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<>();
    private String profile_user_id;

    Handler handler = new Handler();
    Timer timer = new Timer();
    private AnimationDrawable splashAnimation, splashAnimation_progress;
    private ImageView kbv, nearme_retry_image;
    RelativeLayout relativelayout_nearme_progress, relativelayout_nearme_retry;
    DoSomething_Friends_profile_fragment doSomethingFriendsProfileFragment;
    FragmentTransaction fragmentTransaction;
    Dialog progress_bar;
    private ImageView progress_bar_imageview;
    private int recordCount;
    WifiInfo wifiInfo;
    TelephonyManager telephonyManager;
    private ProfileAdapter adapter;
    ProfileViewAdapter profileViewAdapter;
    private GridLayoutManager layoutManager;
    Boolean isGridClick = false;
    RelativeLayout layout_walkthrough_profile;
    RelativeLayout layout_walkthrough_match;
    ImageView imageview_walkthrough_match;
    ImageView imageview_walkthrough_profile;
    private AnimationDrawable splashAnimation_nearme, splashAnimation_match;
    private Timer blink_time, blink_match;
    private TextView text_walkthrough_profile;
    private TextView textview_walkthrough_match;
    private boolean loading = true;
    private int count = 1;
    private Tracker mTracker;
    private String formattedDate;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoSomethingNearMe.
     */
    // TODO: Rename and change types and number of parameters
    public static DoSomethingNearMe newInstance(String param1, String param2) {
        DoSomethingNearMe fragment = new DoSomethingNearMe();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DoSomethingNearMe() {
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
        View view = inflater.inflate(R.layout.fragment_do_something_near_me, container, false);
        try {
            MyApplication application = (MyApplication) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sharedPreferences = new SharedPrefrences();
        pd = new TransparentProgressDialog(getActivity(), getResources().getDrawable(R.drawable.loading));
        relativelayout_nearme_retry = (RelativeLayout) view.findViewById(R.id.relativelayout_nearme_retry);
        layout_walkthrough_profile = (RelativeLayout) view.findViewById(R.id.layout_walkthrough_profile);
        layout_walkthrough_match = (RelativeLayout) view.findViewById(R.id.layout_walkthrough_match);
        text_walkthrough_profile = (TextView) view.findViewById(R.id.text_walkthrough_profile);
        textview_walkthrough_match = (TextView) view.findViewById(R.id.textview_walkthrough_match);
        imageview_walkthrough_profile = (ImageView) view.findViewById(R.id.imageview_walkthrough_profile);
        imageview_walkthrough_match = (ImageView) view.findViewById(R.id.imageview_walkthrough_match);
        nearme_retry_image = (ImageView) view.findViewById(R.id.nearme_retry_image);
        imageview_walkthrough_profile.setBackgroundResource(R.drawable.blink_icon);
        splashAnimation_nearme = (AnimationDrawable) imageview_walkthrough_profile.getBackground();
        imageview_walkthrough_match.setBackgroundResource(R.drawable.blink_icon);
        splashAnimation_match = (AnimationDrawable) imageview_walkthrough_match.getBackground();
        relativelayout_nearme_progress = (RelativeLayout) view.findViewById(R.id.relativelayout_nearme_progress);

        kbv = (ImageView) view.findViewById(R.id.nearme_progress_image);
        kbv.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) kbv.getBackground();
        splashAnimation.start();
        jsonfunctions = new Jsonfunctions(getActivity());
        aQuery = new AQuery(getActivity());
        filter_list = new ArrayList<>();
        dosomething_list = new ArrayList<>();
        dosomething_nearme_gridview_layout = (LinearLayout) view.findViewById(R.id.dosomething_nearme_gridview_layout);
        pull_to_refresh_progress = (ProgressBar) view.findViewById(R.id.pull_to_refresh_progress);
        nearme_profile_refreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.nearme_profile_refreshlayout);
        activity_dosomething_nearme = (RecyclerView) view.findViewById(R.id.activity_dosomething_nearme);
        dosomething_nearme_matched_profile_popup = (RelativeLayout) view.findViewById(R.id.dosomething_nearme_matched_profile_popup);
        dosomething_nearme_matched_profile_popup_layout = (LinearLayout) view.findViewById(R.id.dosomething_nearme_matched_profile_popup_layout);
        dosomething_nearme_matched_profile_userImage = (ImageView) view.findViewById(R.id.dosomething_nearme_matched_profile_userImage);
        dosomething_nearme_matched_profile_near_userImage = (ImageView) view.findViewById(R.id.dosomething_nearme_matched_profile_near_userImage);
        dosomething_nearme_matched_profile_name_textview = (TextView) view.findViewById(R.id.dosomething_nearme_matched_profile_name_textview);
        activity_dosomething_nearme_textview_nouser = (TextView) view.findViewById(R.id.activity_dosomething_nearme_textview_nouser);
        dosomething_nearme_matched_profile_chat_dosomething_textview = (TextView) view.findViewById(R.id.dosomething_nearme_matched_profile_chat_dosomething_textview);
//        dosomething_nearmelist_paginggridview=(PagingGridView)view.findViewById(R.id.dosomething_nearmelist_paginggridview);
        activity_dosomething_nearme_view_top = (View) view.findViewById(R.id.activity_dosomething_nearme_view_top);
        activity_dosomething_nearme_view_bottom = (View) view.findViewById(R.id.activity_dosomething_nearme_view_bottom);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        formattedDate = df.format(c.getTime());
        layoutManager = new GridLayoutManager(getActivity(), 3);
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        activity_dosomething_nearme.hasFixedSize();
        activity_dosomething_nearme.setLayoutManager(layoutManager);
        setRetainInstance(true);
//        if(filter_list==null)
//        {


        try {
            if (getActivity() != null) {
                if (((MyApplication) getActivity().getApplication()).getCount() != 0) {
                    count = ((MyApplication) getActivity().getApplication()).getCount();

                }
               /* layout_walkthrough_match.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        layout_walkthrough_match.setVisibility(View.GONE);
                        sharedPreferences.setWalkThroughMatch(getActivity(), "true");
                    }
                });*/
                progress_bar = new Dialog(getActivity());
                progress_bar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progress_bar.setContentView(R.layout.progress_bar);
                progress_bar_imageview = (ImageView) progress_bar.findViewById(R.id.progress_bar_imageview);
                progress_bar_imageview.setBackgroundResource(R.drawable.progress_drawable);
                splashAnimation_progress = (AnimationDrawable) progress_bar_imageview.getBackground();
                progress_bar.setCancelable(false);


                if (((MyApplication) getActivity().getApplication()).getListFilterBeans().size() == 0) {
                    splashAnimation.start();

                    Log.d("dosomething", "nearme_list" + filter_list);
                    if (!sharedPreferences.getSessionid(getActivity()).equals("") || !sharedPreferences.getLatitude(getActivity()).equals("") || !sharedPreferences.getLongitude(getActivity()).equals("") && getActivity() != null) {

                        sessionid = sharedPreferences.getSessionid(getActivity());
                        latitude = sharedPreferences.getLatitude(getActivity());
                        longitude = sharedPreferences.getLongitude(getActivity());
                        filter_status = sharedPreferences.getFilterStatus(getActivity());
                        filter_gender = sharedPreferences.getFilterGender(getActivity());

                        Log.d("dosomething", "filter_distance" + sharedPreferences.getFilterDistance(getActivity()));

                        if (sharedPreferences.getFilterDistance(getActivity()).trim().equals("0.0-50.0")) {

                            filter_distance = "";
                            Log.d("dosomething", "filter_distance" + filter_distance);

                        } else {
                            filter_distance = sharedPreferences.getFilterDistance(getActivity());
                            Log.d("dosomething", "filter_distance" + filter_distance);

                        }
                        Log.d("dosomething", "filter_agerange" + sharedPreferences.getFilterAge(getActivity()));

                        if (sharedPreferences.getFilterAge(getActivity()).trim().equals("18.0-80.0")) {

                            filter_agerange = "";
                            Log.d("dosomething", "filter_agerange" + filter_agerange);
                        } else {
                            filter_agerange = sharedPreferences.getFilterAge(getActivity());
                            Log.d("dosomething", "filter_agerange" + filter_agerange);
                        }

                        page = "1";
                        count = 1;
                        new AsynDataClass().execute();

                    } else {
                        Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    filter_list = ((MyApplication) getActivity().getApplication()).getListFilterBeans();
                   /* adapter = new ProfileAdapter(getActivity(), ((MyApplication) getActivity().getApplication()).getListFilterBeans());
                    activity_dosomething_nearme.setAdapter(adapter);
                    adapter.notifyDataSetChanged();*/
                    if (profileViewAdapter == null) {
                        profileViewAdapter = new ProfileViewAdapter(getActivity(), ((MyApplication) getActivity().getApplication()).getListFilterBeans());
                        activity_dosomething_nearme.setAdapter(profileViewAdapter);
                    } else {

                        profileViewAdapter.setSelectedIndex(((MyApplication) getActivity().getApplication()).getListFilterBeans());


                    }


//                    activity_dosomething_nearme.setExpanded(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        text_font_typeface();
//        dosomething_nearmelist_paginggridview.setHasMoreItems(true);
//        dosomething_nearmelist_paginggridview.setPagingableListener(new PagingGridView.Pagingable() {
//            @Override
//            public void onLoadMoreItems() {
//                if (filter_list.size() == 30) {
//                    pull_to_refresh_progress.setVisibility(View.VISIBLE);
//                    if (!sharedPreferences.getSessionid(getActivity()).equals("") || !sharedPreferences.getLatitude(getActivity()).equals("") || !sharedPreferences.getLongitude(getActivity()).equals("") && getActivity() != null) {
//                        sessionid = sharedPreferences.getSessionid(getActivity());
//                        latitude = sharedPreferences.getLatitude(getActivity());
//                        longitude = sharedPreferences.getLongitude(getActivity());
//                        filter_status = sharedPreferences.getFilterStatus(getActivity());
//                        filter_agerange = sharedPreferences.getFilterAge(getActivity());
//                        filter_distance = sharedPreferences.getFilterDistance(getActivity());
//                        filter_gender = sharedPreferences.getFilterGender(getActivity());
//                        filter_list = new ArrayList<>();
//                        dosomething_list = new ArrayList<>();
//
//                        ProfileAdapter adapter = new ProfileAdapter(getActivity(), filter_list);
//                        activity_dosomething_nearme.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                        page = "1";
//                        new AsynDataClass().execute();
//
//
//                    } else {
//                        Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    dosomething_nearmelist_paginggridview.onFinishLoading(false, null);
//                }
//            }
//        });

//        }else
//        {
//
//            Log.d("dosomething","nearme_list"+filter_list);
//
//            ProfileAdapter adapter = new ProfileAdapter(getActivity(), filter_list);
//            activity_dosomething_nearme.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//            refreshList();
//        }


//        ProfileAdapter profileAdapter=new ProfileAdapter(getActivity(),filter_list);
//        activity_dosomething_nearme.setAdapter(profileAdapter);

        /*activity_dosomething_nearme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity_dosomething_nearme.smoothScrollToPosition(position);

            }
        });

        activity_dosomething_nearme.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("dosomething_nearme", "activity_dosomething_nearme_firstVisibleItem" + view);

                Log.d("dosomething_nearme", "activity_dosomething_nearme_firstVisibleItem" + firstVisibleItem);
                Log.d("dosomething_nearme", "activity_dosomething_nearme_visibleItemCount" + visibleItemCount);
                Log.d("dosomething_nearme", "activity_dosomething_nearme_totalItemCount" + totalItemCount);

                if (recordCount > 9) {
                    Log.d("dosomething_nearme", "visiblecount" + click_action);



                   *//* activity_dosomething_nearme_view_top.setVisibility(View.VISIBLE);
                    activity_dosomething_nearme_view_bottom.setVisibility(View.GONE);*//*
//                    pull_to_refresh_progress.setVisibility(View.VISIBLE);
//                    if (!sharedPreferences.getSessionid(getActivity()).equals("") || !sharedPreferences.getLatitude(getActivity()).equals("") || !sharedPreferences.getLongitude(getActivity()).equals("") && getActivity() != null) {
//                        sessionid = sharedPreferences.getSessionid(getActivity());
//                        latitude = sharedPreferences.getLatitude(getActivity());
//                        longitude = sharedPreferences.getLongitude(getActivity());
//                        filter_status = sharedPreferences.getFilterStatus(getActivity());
//                        filter_agerange = sharedPreferences.getFilterAge(getActivity());
//                        filter_distance = sharedPreferences.getFilterDistance(getActivity());
//                        filter_gender = sharedPreferences.getFilterGender(getActivity());
//                        filter_list = new ArrayList<>();
//                        dosomething_list = new ArrayList<>();
//
//                        ProfileAdapter adapter = new ProfileAdapter(getActivity(), filter_list);
//                        activity_dosomething_nearme.setAdapter(adapter);
//                        adapter.notifyDataSetChanged();
//                        page = "1";
//                        new AsynDataClass().execute();
//
//
//                    } else {
//                        Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
//                    }
                } else if (firstVisibleItem == filter_list.size()) {
                    Log.d("dosomething_nearme", "visiblecount30" + firstVisibleItem);
                   *//* activity_dosomething_nearme_view_top.setVisibility(View.GONE);
                    activity_dosomething_nearme_view_bottom.setVisibility(View.VISIBLE);*//*
//                    pull_to_refresh_progress.setVisibility(View.VISIBLE);
//                    if (!sharedPreferences.getSessionid(getActivity()).equals("") || !sharedPreferences.getLatitude(getActivity()).equals("") || !sharedPreferences.getLongitude(getActivity()).equals("") && getActivity() != null) {
//                        sessionid = sharedPreferences.getSessionid(getActivity());
//                        latitude = sharedPreferences.getLatitude(getActivity());
//                        longitude = sharedPreferences.getLongitude(getActivity());
//                        filter_status = sharedPreferences.getFilterStatus(getActivity());
//                        filter_agerange = sharedPreferences.getFilterAge(getActivity());
//                        filter_distance = sharedPreferences.getFilterDistance(getActivity());
//                        filter_gender = sharedPreferences.getFilterGender(getActivity());
//                        page = "2";
//                        new AsynDataClass().execute();
//
//
//                    } else {
//                        Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
//                    }
                } else if (firstVisibleItem == filter_list.size() + 21) {
                    Log.d("dosomething_nearme", "visiblecount30++" + click_action);

//                    pull_to_refresh_progress.setVisibility(View.VISIBLE);
//                    if (!sharedPreferences.getSessionid(getActivity()).equals("") || !sharedPreferences.getLatitude(getActivity()).equals("") || !sharedPreferences.getLongitude(getActivity()).equals("") && getActivity() != null) {
//                        sessionid = sharedPreferences.getSessionid(getActivity());
//                        latitude = sharedPreferences.getLatitude(getActivity());
//                        longitude = sharedPreferences.getLongitude(getActivity());
//                        filter_status = sharedPreferences.getFilterStatus(getActivity());
//                        filter_agerange = sharedPreferences.getFilterAge(getActivity());
//                        filter_distance = sharedPreferences.getFilterDistance(getActivity());
//                        filter_gender = sharedPreferences.getFilterGender(getActivity());
//
//
//                        new AsynDataClass().execute();
//
//
//                    } else {
//                        Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });*/
        layout_walkthrough_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_profile.setVisibility(View.GONE);
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation_nearme.stop();
                sharedPreferences.setWalkThroughNearme(getActivity(), "true");
            }
        });

        imageview_walkthrough_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_profile.setVisibility(View.GONE);
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation_nearme.stop();
                sharedPreferences.setWalkThroughNearme(getActivity(), "true");
            }
        });

        activity_dosomething_nearme.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dy > 0) //check for scroll down
                {

                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();


                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            pull_to_refresh_progress.setVisibility(View.VISIBLE);
                            Log.v("...", "Last Item Wow !");
                            count++;
                            ((MyApplication) getActivity().getApplication()).setCount(count);

                            loading = false;
                            if (!sharedPreferences.getSessionid(getActivity()).equals("") || !sharedPreferences.getLatitude(getActivity()).equals("") || !sharedPreferences.getLongitude(getActivity()).equals("") && getActivity() != null) {

                                sessionid = sharedPreferences.getSessionid(getActivity());
                                latitude = sharedPreferences.getLatitude(getActivity());
                                longitude = sharedPreferences.getLongitude(getActivity());
                                filter_status = sharedPreferences.getFilterStatus(getActivity());
                                filter_gender = sharedPreferences.getFilterGender(getActivity());

                                Log.d("dosomething", "filter_distance" + sharedPreferences.getFilterDistance(getActivity()));

                                if (sharedPreferences.getFilterDistance(getActivity()).trim().equals("0.0-50.0")) {

                                    filter_distance = "";
                                    Log.d("dosomething", "filter_distance" + filter_distance);

                                } else {
                                    filter_distance = sharedPreferences.getFilterDistance(getActivity());
                                    Log.d("dosomething", "filter_distance" + filter_distance);

                                }
                                Log.d("dosomething", "filter_agerange" + sharedPreferences.getFilterAge(getActivity()));

                                if (sharedPreferences.getFilterAge(getActivity()).trim().equals("18.0-80.0")) {

                                    filter_agerange = "";
                                    Log.d("dosomething", "filter_agerange" + filter_agerange);
                                } else {
                                    filter_agerange = sharedPreferences.getFilterAge(getActivity());
                                    Log.d("dosomething", "filter_agerange" + filter_agerange);
                                }

                                page = String.valueOf(count);
                                new AsynDataClass().execute();

                            } else {
                                Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }

                }
            }
        });


        nearme_profile_refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (getActivity() != null) {
                    ((MyApplication) getActivity().getApplication()).getListFilterBeans().clear();
                    nearme_profile_refreshlayout.setRefreshing(true);
                    sessionid = sharedPreferences.getSessionid(getActivity());
                    latitude = sharedPreferences.getLatitude(getActivity());
                    longitude = sharedPreferences.getLongitude(getActivity());
                    filter_status = sharedPreferences.getFilterStatus(getActivity());
                    filter_agerange = sharedPreferences.getFilterAge(getActivity());
                    filter_distance = sharedPreferences.getFilterDistance(getActivity());
                    filter_gender = sharedPreferences.getFilterGender(getActivity());
                    page = "1";
                    count = 1;
                    if (dosomething_list != null) {
                        dosomething_list.clear();
                    }
                    new AsynDataClass().execute();
                    refreshList();
                }

            }
        });
//        ProfileViewAdapter profileViewAdapter=new ProfileViewAdapter(this);
//        activity_dosomething_nearme.setAdapter(new ProfileAdapter(this));
//        activity_dosomething_nearme.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                if (scrollState == 10) {
//
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (visibleItemCount != 9) {
//
//                } else {
//                    pull_to_refresh_progress.setVisibility(View.GONE);
//
//                }
//
//            }
//
//
//        });
//        if (pull_to_refresh_progress.getMax() > 5000) {
//            pull_to_refresh_progress.setVisibility(View.GONE);
//            Toast.makeText(getActivity(), "No Records", Toast.LENGTH_SHORT).show();
//
//        }

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

    private void text_font_typeface() {
        patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");
        patron_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Regular.ttf");
        patron_medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Medium.ttf");
        myfonts_thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Thin.ttf");
        dosomething_nearme_matched_profile_name_textview.setTypeface(patron_bold);
        activity_dosomething_nearme_textview_nouser.setTypeface(patron_bold);
        dosomething_nearme_matched_profile_chat_dosomething_textview.setTypeface(patron_bold);
        text_walkthrough_profile.setTypeface(patron_regular);


    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = null;
        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);


            Canvas canvas = new Canvas(output);

            final int color = getResources().getColor(R.color.text_red);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                    bitmap.getHeight() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }


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
            canvas.drawCircle(100, 100, 94, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException | OutOfMemoryError ignored) {
            ignored.printStackTrace();
        }
        return result;
    }

    @Override
    public void onRefresh() {
        nearme_profile_refreshlayout.setRefreshing(true);
        sessionid = sharedPreferences.getSessionid(getActivity());
        latitude = sharedPreferences.getLatitude(getActivity());
        longitude = sharedPreferences.getLongitude(getActivity());
        filter_status = sharedPreferences.getFilterStatus(getActivity());
        filter_agerange = sharedPreferences.getFilterAge(getActivity());
        filter_distance = sharedPreferences.getFilterDistance(getActivity());
        filter_gender = sharedPreferences.getFilterGender(getActivity());

        new AsynDataClass().execute();
        refreshList();
    }

    public void refreshList() {
        //do processing to get new data and set your listview's adapter, maybe  reinitialise the loaders you may be using or so
        //when your data has finished loading, cset the refresh state of the view to false
        nearme_profile_refreshlayout.setRefreshing(false);

    }

    @Override
    public void onResume() {
        super.onResume();


        try {

            if (getActivity() != null) {

                if (sharedPreferences.getPushType(getActivity()).equals("sendrequest")) {
                    if(formattedDate.equals(sharedPreferences.getDeviceDate(getActivity()))) {
                    if (sharedPreferences.getWalkThroughMatch(getActivity()).equals("false")) {
                        layout_walkthrough_match.setVisibility(View.VISIBLE);
                        blink_match = new Timer();
                        blink_match.schedule(new Blink_match(), 0, 340);
                        splashAnimation_match.start();
                        sharedPreferences.setWalkThroughMatch(getActivity(), "true");
                    }}

                    isGridClick = false;
                    activity_dosomething_nearme.setEnabled(false);
                    dosomething_nearme_gridview_layout.setAlpha(0.1f);
                    dosomething_nearme_matched_profile_popup.setVisibility(View.VISIBLE);

                    dosomething_nearme_matched_profile_name_textview.setText("You and " + sharedPreferences.getFriendFirstName(getActivity()) + " " + "are a match \n\n Start Chatting to");


                    if (!sharedPreferences.getFriendProfilePicture(getActivity()).equals("")) {

                        aQuery.id(dosomething_nearme_matched_profile_near_userImage).image(sharedPreferences.getFriendProfilePicture(getActivity()), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
                                    Bitmap resized = Bitmap.createScaledBitmap(bm, 300, 300, true);
                                    Bitmap conv_bm = getCroppedBitmap(resized);
                                    iv.setImageBitmap(conv_bm);

                                }
                            }
                        });
                    } else {
                        dosomething_nearme_matched_profile_near_userImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));

                    }

                    if (getActivity() != null) {
                        if (!sharedPreferences.getProfilePicture(getActivity()).equals("")) {
                            aQuery.id(dosomething_nearme_matched_profile_userImage).image(sharedPreferences.getProfilePicture(getActivity()), true, true, 0, 0, new BitmapAjaxCallback() {
                                @Override
                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                    if (status.getCode() == 200) {
                                        Bitmap resized = Bitmap.createScaledBitmap(bm, 300, 300, true);
                                        Bitmap conv_bm = getCroppedBitmap(resized);
                                        iv.setImageBitmap(conv_bm);


                                    }
                                }
                            });

                        } else {
                            dosomething_nearme_matched_profile_userImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));

                        }


                    }
                    dosomething_nearme_matched_profile_popup.setVisibility(View.VISIBLE);
                    dosmething_nearuser_matched_grid_control = "Click_false";

                    dosomething_nearme_matched_profile_popup_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            sessionid = sharedPreferences.getSessionid(getActivity());
                            request_send_user_id = sharedPreferences.getFriendUserId(getActivity());

                            new CheckRequestStatus().execute();





                        }
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //    public void OnClickListener() {
//        activity_dosomething_nearme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (((MyApplication) getActivity().getApplication()).getDoSomethingStatus() != null) {
//                    DoSomething_Friends_profile_fragment doSomethingFriendsProfileFragment = new DoSomething_Friends_profile_fragment();
//                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
////                    fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
//                    fragmentTransaction.replace(R.id.detail_fragment, doSomethingFriendsProfileFragment);
//                    fragmentTransaction.commit();
//                    ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().settext("YES");
//                }
//            }
//        });
//    }

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

    private class ProfileAdapter extends BaseAdapter {
        ArrayList<Filterbean> filterr_list = new ArrayList<Filterbean>();
        ArrayList<Dosomething_Bean> dosomething_beans = new ArrayList<Dosomething_Bean>();
        ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<HobbiesBean>();
        LayoutInflater layoutInflater = null;
        Activity mactivity;
        Context mContext;
        private int selectedIndex;

        public ProfileAdapter(Activity activity, ArrayList<Filterbean> filterbeans) {
            super();
            text_font_typeface();
            this.filterr_list = filterbeans;
            this.hobbiesBeans = hobbiesBeans;
            this.mactivity = activity;
            selectedIndex = -1;
        }


        @Override
        public int getCount() {
            return filterr_list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            LinearLayout activity_something_doSomething_profile;
            ImageView activity_dosomething_imageview_nearme_chong;
            ImageView activity_dosomething_imageview_nearme_imageview_online;
            TextView activity_dosomething_textview_nearme_name;
            TextView activity_dosomething_nearme_textview_request;
            TextView activity_dosomething_imageview_nearme_textview_now;
            TextView activity_dosomething_imageview_nearme_textview_distance;
            LinearLayout activity_dosomething_nearme_ralativelayout;
            ImageView nearby_dosomething_image1;
            ImageView nearby_dosomething_image2;
            ImageView nearby_dosomething_image3;

        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        text_font_typeface();
            holder = null;
            convertView = inflater.inflate(R.layout.activity_nearme_profile, parent, false);

            if (convertView != null) {
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.activity_something_doSomething_profile = (LinearLayout) convertView.findViewById(R.id.activity_something_doSomething_profile);
                holder.activity_dosomething_imageview_nearme_chong = (ImageView) convertView.findViewById(R.id.activity_dosomething_imageview_nearme_chong);
                holder.activity_dosomething_imageview_nearme_imageview_online = (ImageView) convertView.findViewById(R.id.activity_dosomething_imageview_nearme_imageview_online);
                holder.activity_dosomething_textview_nearme_name = (TextView) convertView.findViewById(R.id.activity_dosomething_textview_nearme_name);
                holder.activity_dosomething_nearme_textview_request = (TextView) convertView.findViewById(R.id.activity_dosomething_nearme_textview_request);
                holder.activity_dosomething_imageview_nearme_textview_now = (TextView) convertView.findViewById(R.id.activity_dosomething_imageview_nearme_textview_now);
                holder.activity_dosomething_imageview_nearme_textview_distance = (TextView) convertView.findViewById(R.id.activity_dosomething_imageview_nearme_textview_distance);
                holder.activity_dosomething_nearme_ralativelayout = (LinearLayout) convertView.findViewById(R.id.activity_dosomething_nearme_ralativelayout);
                holder.nearby_dosomething_image1 = (ImageView) convertView.findViewById(R.id.nearby_dosomething_image1);
                holder.nearby_dosomething_image2 = (ImageView) convertView.findViewById(R.id.nearby_dosomething_image2);
                holder.nearby_dosomething_image3 = (ImageView) convertView.findViewById(R.id.nearby_dosomething_image3);
                holder.activity_dosomething_textview_nearme_name.setTypeface(patron_bold);
                holder.activity_dosomething_nearme_textview_request.setTypeface(patron_bold);
                holder.activity_dosomething_imageview_nearme_textview_now.setTypeface(patron_bold);
                holder.activity_dosomething_imageview_nearme_textview_distance.setTypeface(patron_bold);
//            OnClickListener();

            } else {

                holder = (ViewHolder) convertView.getTag();
            }


            try {
                layoutManager.scrollToPosition(position);
                Log.d("dosomething_neame", "count" + filterr_list.size());
                Log.d("dosomething_neame", "distance" + filterr_list.get(position).getdistance());
                holder.activity_dosomething_imageview_nearme_textview_distance.setText(filterr_list.get(position).getdistance());
//                AjaxCallback.setNetworkLimit(8);
//                BitmapAjaxCallback.setIconCacheLimit(50);
//                BitmapAjaxCallback.setCacheLimit(50);

                holder.activity_dosomething_imageview_nearme_chong.setOnClickListener(new View.OnClickListener() {
                                                                                          @Override
                                                                                          public void onClick(View v) {

                                                                                              if (getActivity() != null) {


                                                                                                  if (((MyApplication) getActivity().getApplication()).getDoSomethingStatus() != null) {
                                                                                                      Log.d("sharedPreferences", filterr_list.get(position).getuser_id());
                                                                                                      Log.d("sharedPreferences", filterr_list.get(position).getimage1());
                                                                                                      Log.d("sharedPreferences", filterr_list.get(position).getimage2());
                                                                                                      Log.d("sharedPreferences", filterr_list.get(position).getimage3());
                                                                                                      ((DoSomethingStatus) getActivity()).hideFilterIconVisible(true);
                                                                                                      sharedPreferences.setDosomething_filterImage_Visibility(getActivity(), "No");
                                                                                                      sharedPreferences.setFriendUserId(getActivity(), filterr_list.get(position).getuser_id());
                                                                                                      sharedPreferences.setFriendFirstname(getActivity(), filterr_list.get(position).getfirst_name());
                                                                                                      sharedPreferences.setFriendLastname(getActivity(), filterr_list.get(position).getlast_name());
                                                                                                      sharedPreferences.setFriendAge(getActivity(), filterr_list.get(position).getAge());
                                                                                                      sharedPreferences.setFriendProfilePicture(getActivity(), filterr_list.get(position).getimage1());
                                                                                                      sharedPreferences.setFriendProfilePicture1(getActivity(), filterr_list.get(position).getimage2());
                                                                                                      sharedPreferences.setFriendProfilePicture2(getActivity(), filterr_list.get(position).getimage3());
                                                                                                      sharedPreferences.setFriendAbout(getActivity(), filterr_list.get(position).getabout());
                                                                                                      sharedPreferences.setFriendGender(getActivity(), filterr_list.get(position).getgender());
                                                                                                      sharedPreferences.setSendRequest(getActivity(), filterr_list.get(position).getDoSomething());
                                                                                                      sessionid = sharedPreferences.getSessionid(getActivity());
                                                                                                      profile_user_id = sharedPreferences.getFriendUserId(getActivity());
                                                                                                      if (!click_action) {
                                                                                                          new GetUserDetails().execute();
                                                                                                          click_action = true;

                                                                                                      }
                                                                                                      if (getActivity() != null) {
                                                                                                          ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().settext("YES");

                                                                                                      }
                                                                                                  }
                                                                                              }
                                                                                          }
                                                                                      }

                );



               /* if (filterr_list.get(position).getimage1().equals("")) {
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));

                } else {*/
                Log.d("profilepic", "iyyo" + filterr_list.get(position).getimage1());
                aQuery.id(holder.activity_dosomething_imageview_nearme_chong).image(filterr_list.get(position).getimage1(), true, true, 0, 0, new BitmapAjaxCallback() {
                    @Override
                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                        if (status.getCode() == 200) {
                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
                            Bitmap conv_bm = getCroppedBitmap(resized);


                            iv.setImageBitmap(conv_bm);


                        } else {
                            iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.profile_noimg));
                        }
                    }
                });

              /*  }*/
                if (filterr_list.get(position).getfirst_name().equals("")) {
                    holder.activity_dosomething_textview_nearme_name.setText("User");
                } else {
                    holder.activity_dosomething_textview_nearme_name.setText(filterr_list.get(position).getfirst_name() + " " + filterr_list.get(position).getlast_name());

                }


                if (filterr_list.get(position).getAvilable_now().equals("Yes")) {
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.VISIBLE);
                } else {
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
                }
                holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);

                switch (filterr_list.get(position).getDoSomething()) {
                    case "No":
                        holder.activity_dosomething_nearme_ralativelayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (isGridClick) {
                                    if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                                        sharedPreferences.setFriendUserId(getActivity(), String.valueOf(filterr_list.get(position).getuser_id()));
                                        sessionid = sharedPreferences.getSessionid(getActivity());
                                        request_send_user_id = sharedPreferences.getFriendUserId(getActivity());
                                        chatstart = "";

                                        holder.activity_dosomething_nearme_ralativelayout.setBackgroundColor(getResources().getColor(android.R.color.white));
                                        holder.activity_dosomething_nearme_textview_request.setText("Request Send!");
                                        aQuery.id(holder.nearby_dosomething_image1).image(filterr_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {
                                            @Override
                                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                                if (status.getCode() == 200) {
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                    iv.setImageBitmap(bm);


                                                }


                                            }
                                        });
                                        aQuery.id(holder.nearby_dosomething_image2).image(filterr_list.get(position).getImag_inActive1(), true, true, 0, 0, new BitmapAjaxCallback() {
                                            @Override
                                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                                if (status.getCode() == 200) {
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                    iv.setImageBitmap(bm);


                                                }
                                            }
                                        });
                                        aQuery.id(holder.nearby_dosomething_image3).image(filterr_list.get(position).getImag_inActive2(), true, true, 0, 0, new BitmapAjaxCallback() {
                                            @Override
                                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                                if (status.getCode() == 200) {
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                    iv.setImageBitmap(bm);


                                                }
                                            }
                                        });
                                        holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.text_grey));


                                        new SendRequest().execute();
                                    }
                                }


                            }
                        });
                        holder.activity_dosomething_nearme_ralativelayout.setBackgroundColor(getResources().getColor(R.color.red));
                        holder.activity_dosomething_nearme_textview_request.setText("Send Request");
                        holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                        aQuery.id(holder.nearby_dosomething_image1).image(filterr_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);
                                }
                            }
                        });
                        aQuery.id(holder.nearby_dosomething_image2).image(filterr_list.get(position).getImag_Active1(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);
                                }
                            }
                        });
                        aQuery.id(holder.nearby_dosomething_image3).image(filterr_list.get(position).getImag_Active2(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);


                                }
                            }
                        });
                        break;

                    case "Yes":
                        sharedPreferences.setFriendUserId(getActivity(), String.valueOf(filterr_list.get(position).getuser_id()));
                        sessionid = sharedPreferences.getSessionid(getActivity());
                        request_send_user_id = sharedPreferences.getFriendUserId(getActivity());
                        holder.activity_dosomething_nearme_ralativelayout.setBackgroundColor(getResources().getColor(android.R.color.white));
                        holder.activity_dosomething_nearme_textview_request.setText("Request Sent!");
                        aQuery.id(holder.nearby_dosomething_image1).image(filterr_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);


                                }
                            }
                        });
                        aQuery.id(holder.nearby_dosomething_image2).image(filterr_list.get(position).getImag_inActive1(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);


                                }
                            }
                        });
                        aQuery.id(holder.nearby_dosomething_image3).image(filterr_list.get(position).getImag_inActive2(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);


                                }
                            }
                        });
                        holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.text_grey));

                        holder.activity_dosomething_nearme_ralativelayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isGridClick) {
                                    if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                                        sharedPreferences.setFriendUserId(getActivity(), String.valueOf(filterr_list.get(position).getuser_id()));
                                        sessionid = sharedPreferences.getSessionid(getActivity());
                                        request_send_user_id = sharedPreferences.getFriendUserId(getActivity());
                                        chatstart = "";

                                        holder.activity_dosomething_nearme_ralativelayout.setBackgroundColor(getResources().getColor(R.color.red));
                                        holder.activity_dosomething_nearme_textview_request.setText("Send Request");
                                        holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                                        aQuery.id(holder.nearby_dosomething_image1).image(filterr_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {
                                            @Override
                                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                                if (status.getCode() == 200) {
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                    iv.setImageBitmap(bm);


                                                }


                                            }
                                        });
                                        aQuery.id(holder.nearby_dosomething_image2).image(filterr_list.get(position).getImag_Active1(), true, true, 0, 0, new BitmapAjaxCallback() {
                                            @Override
                                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                                if (status.getCode() == 200) {
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                    iv.setImageBitmap(bm);


                                                }
                                            }
                                        });
                                        aQuery.id(holder.nearby_dosomething_image3).image(filterr_list.get(position).getImag_Active2(), true, true, 0, 0, new BitmapAjaxCallback() {
                                            @Override
                                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                                if (status.getCode() == 200) {
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                    iv.setImageBitmap(bm);


                                                }
                                            }
                                        });


                                        new SendRequest().execute();
                                    }
                                }

                            }
                        });


                        break;
                }
                if (dosmething_nearuser_matched_grid_control.equals("Click_false")) {
                    holder.activity_dosomething_imageview_nearme_chong.setClickable(false);
                    holder.activity_dosomething_imageview_nearme_chong.setEnabled(false);
                    holder.activity_dosomething_nearme_ralativelayout.setClickable(false);
                    holder.activity_dosomething_nearme_ralativelayout.setEnabled(false);
                    activity_dosomething_nearme.setEnabled(false);
                } else {
                    holder.activity_dosomething_imageview_nearme_chong.setClickable(true);
                    holder.activity_dosomething_imageview_nearme_chong.setEnabled(true);
                    holder.activity_dosomething_nearme_ralativelayout.setClickable(true);
                    holder.activity_dosomething_nearme_ralativelayout.setEnabled(true);
                    activity_dosomething_nearme.setEnabled(true);
                }
//                if (filterr_list.get(position).getDoSomething().equals("No")) {
//
//                } else {
//
//                }

//            switch (position) {
//                case 0:
//
//                    break;
//                case 1:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.zoe_tay));
//                    holder.activity_dosomething_textview_nearme_name.setText("Zoe Tay");
//                    holder.activity_dosomething_nearme_ralativelayout.setBackground(getResources().getDrawable(R.drawable.send_request));
//                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
//                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
//                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
//                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
//                    break;
//                case 2:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.felicin));
//                    holder.activity_dosomething_textview_nearme_name.setText("Felicia Chin");
//                    holder.activity_dosomething_nearme_ralativelayout.setBackground(getResources().getDrawable(R.drawable.send_request));
//                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
//                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
//                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
//                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
//
//                    break;
//                case 3:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.yuna));
//                    holder.activity_dosomething_textview_nearme_name.setText("Yuna");
//                    break;
//                case 4:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.taylor));
//                    holder.activity_dosomething_textview_nearme_name.setText("Taylor Schilling");
//                    break;
//                case 5:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.galglot));
//                    holder.activity_dosomething_textview_nearme_name.setText("Gal Gadot");
//                    holder.activity_dosomething_nearme_ralativelayout.setBackground(getResources().getDrawable(R.drawable.send_request));
//                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
//                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
//                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
//                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
//                    break;
//            }


            } catch (Exception e) {
                e.printStackTrace();
            }


            return convertView;
        }
    }

    private class ImageAdapter implements ListAdapter {

        public ImageAdapter(DoSomethingNearMe doSomethingNearMe) {

        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getCount() {
            return 40;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        class Holder {
            LinearLayout activity_something_doSomething_profile;
            ImageView activity_dosomething_imageview_nearme_chong;
            ImageView activity_dosomething_imageview_nearme_imageview_online;
            TextView activity_dosomething_textview_nearme_name;
            TextView activity_dosomething_nearme_textview_request;
            TextView activity_dosomething_imageview_nearme_textview_now;
            TextView activity_dosomething_imageview_nearme_textview_distance;
            LinearLayout activity_dosomething_nearme_ralativelayout;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final Holder holder = new Holder();
            convertView = inflater.inflate(R.layout.activity_nearme_profile, parent, false);
            holder.activity_something_doSomething_profile = (LinearLayout) convertView.findViewById(R.id.activity_something_doSomething_profile);
            holder.activity_dosomething_imageview_nearme_chong = (ImageView) convertView.findViewById(R.id.activity_dosomething_imageview_nearme_chong);
            holder.activity_dosomething_imageview_nearme_imageview_online = (ImageView) convertView.findViewById(R.id.activity_dosomething_imageview_nearme_imageview_online);
            holder.activity_dosomething_textview_nearme_name = (TextView) convertView.findViewById(R.id.activity_dosomething_textview_nearme_name);
            holder.activity_dosomething_nearme_textview_request = (TextView) convertView.findViewById(R.id.activity_dosomething_nearme_textview_request);
            holder.activity_dosomething_imageview_nearme_textview_now = (TextView) convertView.findViewById(R.id.activity_dosomething_imageview_nearme_textview_now);
            holder.activity_dosomething_imageview_nearme_textview_distance = (TextView) convertView.findViewById(R.id.activity_dosomething_imageview_nearme_textview_distance);
            holder.activity_dosomething_nearme_ralativelayout = (LinearLayout) convertView.findViewById(R.id.activity_dosomething_nearme_ralativelayout);
            holder.activity_dosomething_textview_nearme_name.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf"));
            holder.activity_dosomething_nearme_textview_request.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf"));
            holder.activity_dosomething_imageview_nearme_textview_now.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf"));
            holder.activity_dosomething_imageview_nearme_textview_distance.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf"));
            switch (position) {
                case 0:

                    break;
                case 1:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Zoe Tay");
                    holder.activity_dosomething_nearme_ralativelayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_request));
                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Felicia Chin");
                    holder.activity_dosomething_nearme_ralativelayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_request));
                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);

                    break;
                case 3:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Yuna");
                    break;
                case 4:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Taylor Schilling");
                    break;
                case 5:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Gal Gadot");
                    holder.activity_dosomething_nearme_ralativelayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_request));
                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
                    break;
            }
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 40;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

    }


    class ProfileViewAdapter extends RecyclerView.Adapter<ProfileViewAdapter.DataObjectHolder> {

        ArrayList<Filterbean> filterr_list = new ArrayList<Filterbean>();
        ArrayList<Dosomething_Bean> dosomething_beans = new ArrayList<Dosomething_Bean>();
        ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<HobbiesBean>();
        LayoutInflater layoutInflater = null;
        Activity mactivity;
        Context mContext;
        private int selectedIndex;


        public ProfileViewAdapter(Activity activity, ArrayList<Filterbean> filterbeans) {
            super();
            text_font_typeface();
            this.filterr_list = filterbeans;
            this.hobbiesBeans = hobbiesBeans;
            this.mactivity = activity;
            selectedIndex = -1;

        }


        public void setSelectedIndex(ArrayList<Filterbean> filterbeans) {
            this.filterr_list = filterbeans;
            notifyDataSetChanged();
        }


        public ProfileViewAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflater.inflate(R.layout.activity_nearme_profile, parent, false);


            return new DataObjectHolder(view);
        }

        @Override
        public void onBindViewHolder(final DataObjectHolder holder, final int position) {
            holder.activity_dosomething_textview_nearme_name.setTypeface(patron_bold);
            holder.activity_dosomething_nearme_textview_request.setTypeface(patron_bold);
            holder.activity_dosomething_imageview_nearme_textview_now.setTypeface(patron_bold);
            holder.activity_dosomething_imageview_nearme_textview_distance.setTypeface(patron_bold);
            /*switch (position) {
                case 0:

                    break;
                case 1:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Zoe Tay");
                    holder.activity_dosomething_nearme_ralativelayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_request));
                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Felicia Chin");
                    holder.activity_dosomething_nearme_ralativelayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_request));
                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);

                    break;
                case 3:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Yuna");
                    break;
                case 4:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Taylor Schilling");
                    break;
                case 5:
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                    holder.activity_dosomething_textview_nearme_name.setText("Gal Gadot");
                    holder.activity_dosomething_nearme_ralativelayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_request));
                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
                    break;
            }*/


            try {

                Log.d("dosomething_neame", "count" + filterr_list.size());
                Log.d("dosomething_neame", "distance" + filterr_list.get(position).getdistance());
                holder.activity_dosomething_imageview_nearme_textview_distance.setText(filterr_list.get(position).getdistance());
//                AjaxCallback.setNetworkLimit(8);
//                BitmapAjaxCallback.setIconCacheLimit(50);
//                BitmapAjaxCallback.setCacheLimit(50);

                holder.activity_dosomething_imageview_nearme_chong.setOnClickListener(new View.OnClickListener() {
                                                                                          @Override
                                                                                          public void onClick(View v) {

                                                                                              if (getActivity() != null) {


                                                                                                  if (((MyApplication) getActivity().getApplication()).getDoSomethingStatus() != null) {
                                                                                                      Log.d("sharedPreferences", filterr_list.get(position).getuser_id());
                                                                                                      Log.d("sharedPreferences", filterr_list.get(position).getimage1());
                                                                                                      Log.d("sharedPreferences", filterr_list.get(position).getimage2());
                                                                                                      Log.d("sharedPreferences", filterr_list.get(position).getimage3());
                                                                                                      ((DoSomethingStatus) getActivity()).hideFilterIconVisible(true);
                                                                                                      sharedPreferences.setDosomething_filterImage_Visibility(getActivity(), "No");
                                                                                                      sharedPreferences.setFriendUserId(getActivity(), filterr_list.get(position).getuser_id());
                                                                                                      sharedPreferences.setFriendFirstname(getActivity(), filterr_list.get(position).getfirst_name());
                                                                                                      sharedPreferences.setFriendLastname(getActivity(), filterr_list.get(position).getlast_name());
                                                                                                      sharedPreferences.setFriendAge(getActivity(), filterr_list.get(position).getAge());
                                                                                                      sharedPreferences.setFriendProfilePicture(getActivity(), filterr_list.get(position).getimage1());
                                                                                                      sharedPreferences.setFriendProfilePicture1(getActivity(), filterr_list.get(position).getimage2());
                                                                                                      sharedPreferences.setFriendProfilePicture2(getActivity(), filterr_list.get(position).getimage3());
                                                                                                      sharedPreferences.setFriendAbout(getActivity(), filterr_list.get(position).getabout());
                                                                                                      sharedPreferences.setFriendGender(getActivity(), filterr_list.get(position).getgender());
                                                                                                      sharedPreferences.setSendRequest(getActivity(), filterr_list.get(position).getDoSomething());
                                                                                                      sessionid = sharedPreferences.getSessionid(getActivity());
                                                                                                      profile_user_id = sharedPreferences.getFriendUserId(getActivity());
                                                                                                      if (!click_action) {
                                                                                                          new GetUserDetails().execute();
                                                                                                          click_action = true;

                                                                                                      }
                                                                                                      if (getActivity() != null) {
                                                                                                          ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().settext("YES");

                                                                                                      }
                                                                                                  }
                                                                                              }
                                                                                          }
                                                                                      }

                );



               /* if (filterr_list.get(position).getimage1().equals("")) {
                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));

                } else {*/
                Log.d("profilepic", "iyyo" + filterr_list.get(position).getimage1());
                aQuery.id(holder.activity_dosomething_imageview_nearme_chong).image(filterr_list.get(position).getimage1(), true, true, 0, 0, new BitmapAjaxCallback() {
                    @Override
                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                        if (status.getCode() == 200) {
                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
                            Bitmap conv_bm = getCroppedBitmap(resized);


                            iv.setImageBitmap(conv_bm);


                        } else {
                            iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.profile_noimg));
                        }
                    }
                });

              /*  }*/
                if (filterr_list.get(position).getfirst_name().equals("")) {
                    holder.activity_dosomething_textview_nearme_name.setText("User");
                } else {
                    holder.activity_dosomething_textview_nearme_name.setText(filterr_list.get(position).getfirst_name() + " " + filterr_list.get(position).getlast_name());

                }


                if (filterr_list.get(position).getAvilable_now().equals("Yes")) {
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.VISIBLE);
                } else {
                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
                }
                holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);

                switch (filterr_list.get(position).getDoSomething()) {
                    case "No":
                        holder.activity_dosomething_nearme_ralativelayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ((MyApplication) getActivity().getApplication()).setanInt(position);


                                if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                                    filter_list.get(position).setDoSomething("Yes");

                                    sharedPreferences.setFriendUserId(getActivity(), String.valueOf(filterr_list.get(position).getuser_id()));
                                    sessionid = sharedPreferences.getSessionid(getActivity());
                                    request_send_user_id = sharedPreferences.getFriendUserId(getActivity());
                                    chatstart = "";
                                    layout_walkthrough_profile.setVisibility(View.GONE);
                                    if (blink_time != null) {

                                        blink_time.cancel();


                                        blink_time = null;

                                    }
                                    splashAnimation_nearme.stop();
                                    sharedPreferences.setWalkThroughNearme(getActivity(), "true");
                                    holder.activity_dosomething_nearme_ralativelayout.setBackgroundColor(getResources().getColor(android.R.color.white));
                                    holder.activity_dosomething_nearme_textview_request.setText("Request Send!");
                                    aQuery.id(holder.nearby_dosomething_image1).image(filterr_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                holder.nearby_dosomething_image1.setVisibility(View.VISIBLE);
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                iv.setImageBitmap(bm);


                                            } else {
                                                holder.nearby_dosomething_image1.setVisibility(View.GONE);
                                            }


                                        }
                                    });
                                    aQuery.id(holder.nearby_dosomething_image2).image(filterr_list.get(position).getImag_inActive1(), true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                holder.nearby_dosomething_image2.setVisibility(View.VISIBLE);
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                iv.setImageBitmap(bm);


                                            } else {
                                                holder.nearby_dosomething_image2.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                    aQuery.id(holder.nearby_dosomething_image3).image(filterr_list.get(position).getImag_inActive2(), true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                holder.nearby_dosomething_image3.setVisibility(View.VISIBLE);
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                iv.setImageBitmap(bm);


                                            } else {
                                                holder.nearby_dosomething_image3.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.text_grey));


                                    new SendRequest().execute();
                                }

                            }
                        });
                        holder.activity_dosomething_nearme_ralativelayout.setBackgroundColor(getResources().getColor(R.color.red));
                        holder.activity_dosomething_nearme_textview_request.setText("Send Request");
                        holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                        aQuery.id(holder.nearby_dosomething_image1).image(filterr_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
                                    holder.nearby_dosomething_image1.setVisibility(View.VISIBLE);
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);
                                } else {
                                    holder.nearby_dosomething_image1.setVisibility(View.GONE);
                                }
                            }
                        });
                        aQuery.id(holder.nearby_dosomething_image2).image(filterr_list.get(position).getImag_Active1(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
                                    holder.nearby_dosomething_image2.setVisibility(View.VISIBLE);
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);
                                } else {
                                    holder.nearby_dosomething_image2.setVisibility(View.GONE);
                                }
                            }
                        });
                        aQuery.id(holder.nearby_dosomething_image3).image(filterr_list.get(position).getImag_Active2(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
                                    holder.nearby_dosomething_image3.setVisibility(View.VISIBLE);
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);


                                } else {
                                    holder.nearby_dosomething_image3.setVisibility(View.GONE);
                                }
                            }
                        });
                        break;

                    case "Yes":
                        sharedPreferences.setFriendUserId(getActivity(), String.valueOf(filterr_list.get(position).getuser_id()));
                        sessionid = sharedPreferences.getSessionid(getActivity());
                        request_send_user_id = sharedPreferences.getFriendUserId(getActivity());
                        holder.activity_dosomething_nearme_ralativelayout.setBackgroundColor(getResources().getColor(android.R.color.white));
                        holder.activity_dosomething_nearme_textview_request.setText("Request Sent!");
                        aQuery.id(holder.nearby_dosomething_image1).image(filterr_list.get(position).getImag_inActive(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
                                    holder.nearby_dosomething_image1.setVisibility(View.VISIBLE);
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);


                                } else {
                                    holder.nearby_dosomething_image1.setVisibility(View.GONE);
                                }
                            }
                        });
                        aQuery.id(holder.nearby_dosomething_image2).image(filterr_list.get(position).getImag_inActive1(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
                                    holder.nearby_dosomething_image2.setVisibility(View.VISIBLE);
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);


                                } else {
                                    holder.nearby_dosomething_image2.setVisibility(View.GONE);
                                }
                            }
                        });
                        aQuery.id(holder.nearby_dosomething_image3).image(filterr_list.get(position).getImag_inActive2(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
                                    holder.nearby_dosomething_image3.setVisibility(View.VISIBLE);
//                    Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                    iv.setImageBitmap(bm);


                                } else {
                                    holder.nearby_dosomething_image3.setVisibility(View.GONE);
                                }
                            }
                        });
                        holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.text_grey));


                        holder.activity_dosomething_nearme_ralativelayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((MyApplication) getActivity().getApplication()).setanInt(position);

                                if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {

                                    filter_list.get(position).setDoSomething("No");
                                    sharedPreferences.setFriendUserId(getActivity(), String.valueOf(filterr_list.get(position).getuser_id()));
                                    sessionid = sharedPreferences.getSessionid(getActivity());
                                    request_send_user_id = sharedPreferences.getFriendUserId(getActivity());
                                    chatstart = "";
                                    layout_walkthrough_profile.setVisibility(View.GONE);
                                    if (blink_time != null) {

                                        blink_time.cancel();


                                        blink_time = null;

                                    }
                                    splashAnimation_nearme.stop();
                                    sharedPreferences.setWalkThroughNearme(getActivity(), "true");
                                    holder.activity_dosomething_nearme_ralativelayout.setBackgroundColor(getResources().getColor(R.color.red));
                                    holder.activity_dosomething_nearme_textview_request.setText("Send Request");
                                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
                                    aQuery.id(holder.nearby_dosomething_image1).image(filterr_list.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                holder.nearby_dosomething_image1.setVisibility(View.VISIBLE);
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                iv.setImageBitmap(bm);


                                            } else {
                                                holder.nearby_dosomething_image1.setVisibility(View.GONE);
                                            }


                                        }
                                    });
                                    aQuery.id(holder.nearby_dosomething_image2).image(filterr_list.get(position).getImag_Active1(), true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                holder.nearby_dosomething_image2.setVisibility(View.VISIBLE);
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                iv.setImageBitmap(bm);


                                            } else {
                                                holder.nearby_dosomething_image2.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                    aQuery.id(holder.nearby_dosomething_image3).image(filterr_list.get(position).getImag_Active2(), true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                holder.nearby_dosomething_image3.setVisibility(View.VISIBLE);
//                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                                iv.setImageBitmap(bm);


                                            } else {
                                                holder.nearby_dosomething_image3.setVisibility(View.GONE);
                                            }
                                        }
                                    });


                                    new SendRequest().execute();
                                }
                            }
                        });


                        break;
                }
                if (dosmething_nearuser_matched_grid_control.equals("Click_false")) {
                    holder.activity_dosomething_imageview_nearme_chong.setClickable(false);
                    holder.activity_dosomething_imageview_nearme_chong.setEnabled(false);
                    holder.activity_dosomething_nearme_ralativelayout.setClickable(false);
                    holder.activity_dosomething_nearme_ralativelayout.setEnabled(false);
                    activity_dosomething_nearme.setEnabled(false);
                }
//                if (filterr_list.get(position).getDoSomething().equals("No")) {
//
//                } else {
//
//                }

//            switch (position) {
//                case 0:
//
//                    break;
//                case 1:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.zoe_tay));
//                    holder.activity_dosomething_textview_nearme_name.setText("Zoe Tay");
//                    holder.activity_dosomething_nearme_ralativelayout.setBackground(getResources().getDrawable(R.drawable.send_request));
//                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
//                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
//                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
//                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
//                    break;
//                case 2:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.felicin));
//                    holder.activity_dosomething_textview_nearme_name.setText("Felicia Chin");
//                    holder.activity_dosomething_nearme_ralativelayout.setBackground(getResources().getDrawable(R.drawable.send_request));
//                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
//                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
//                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
//                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
//
//                    break;
//                case 3:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.yuna));
//                    holder.activity_dosomething_textview_nearme_name.setText("Yuna");
//                    break;
//                case 4:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.taylor));
//                    holder.activity_dosomething_textview_nearme_name.setText("Taylor Schilling");
//                    break;
//                case 5:
//                    holder.activity_dosomething_imageview_nearme_chong.setImageDrawable(getResources().getDrawable(R.drawable.galglot));
//                    holder.activity_dosomething_textview_nearme_name.setText("Gal Gadot");
//                    holder.activity_dosomething_nearme_ralativelayout.setBackground(getResources().getDrawable(R.drawable.send_request));
//                    holder.activity_dosomething_nearme_textview_request.setText("Sent Request");
//                    holder.activity_dosomething_nearme_textview_request.setTextColor(getResources().getColor(R.color.white));
//                    holder.activity_dosomething_imageview_nearme_textview_now.setVisibility(View.GONE);
//                    holder.activity_dosomething_imageview_nearme_imageview_online.setVisibility(View.GONE);
//                    break;
//            }


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }

        @Override
        public int getItemCount() {

            return filterr_list.size();
        }

        public class DataObjectHolder extends RecyclerView.ViewHolder {
            LinearLayout activity_something_doSomething_profile;
            ImageView activity_dosomething_imageview_nearme_chong;
            ImageView activity_dosomething_imageview_nearme_imageview_online;
            TextView activity_dosomething_textview_nearme_name;
            TextView activity_dosomething_nearme_textview_request;
            TextView activity_dosomething_imageview_nearme_textview_now;
            TextView activity_dosomething_imageview_nearme_textview_distance;
            LinearLayout activity_dosomething_nearme_ralativelayout;
            ImageView nearby_dosomething_image1;
            ImageView nearby_dosomething_image2;
            ImageView nearby_dosomething_image3;

            public DataObjectHolder(View itemview) {
                super(itemview);
                activity_something_doSomething_profile = (LinearLayout) itemview.findViewById(R.id.activity_something_doSomething_profile);
                activity_dosomething_imageview_nearme_chong = (ImageView) itemview.findViewById(R.id.activity_dosomething_imageview_nearme_chong);
                activity_dosomething_imageview_nearme_imageview_online = (ImageView) itemview.findViewById(R.id.activity_dosomething_imageview_nearme_imageview_online);
                activity_dosomething_textview_nearme_name = (TextView) itemview.findViewById(R.id.activity_dosomething_textview_nearme_name);
                activity_dosomething_nearme_textview_request = (TextView) itemview.findViewById(R.id.activity_dosomething_nearme_textview_request);
                activity_dosomething_imageview_nearme_textview_now = (TextView) itemview.findViewById(R.id.activity_dosomething_imageview_nearme_textview_now);
                activity_dosomething_imageview_nearme_textview_distance = (TextView) itemview.findViewById(R.id.activity_dosomething_imageview_nearme_textview_distance);
                activity_dosomething_nearme_ralativelayout = (LinearLayout) itemview.findViewById(R.id.activity_dosomething_nearme_ralativelayout);
                nearby_dosomething_image1 = (ImageView) itemview.findViewById(R.id.nearby_dosomething_image1);
                nearby_dosomething_image2 = (ImageView) itemview.findViewById(R.id.nearby_dosomething_image2);
                nearby_dosomething_image3 = (ImageView) itemview.findViewById(R.id.nearby_dosomething_image3);
            }
        }
    }


    private class AsynDataClass extends AsyncTask<Void, Void, Boolean> {
        private String user_id;
        private String Image;
        private String Image1;
        private String Image2;
        private String status;
        int id;
        String name, name1, name2, ActiveImage, ActiveImage1, ActiveImage2;
        private int id1;
        private int id2;
        Exception error;
        String nearbyUser_Api_url;
        private String first_name;
        private String last_name;
        private String about;
        private String gender;
        private String age;
        private String date_of_birth;
        private String online_status;
        private String image2;
        private String image1;
        private String image3;
        private String available_now;
        private String send_request;
        private String distance;
        private String dosomethinglist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//            pDialog = new ProgressDialog(getActivity());
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//            pd.show();
            dosmething_nearuser_matched_grid_control = "Click_false";
            if (((MyApplication) getActivity().getApplication()).getListFilterBeans().size() == 0) {
                kbv.setVisibility(View.VISIBLE);
                relativelayout_nearme_progress.setVisibility(View.VISIBLE);
                timer = new Timer();
                timer.schedule(new AutoSlider(), 0, 1350);
                splashAnimation.start();
            }


            if (getActivity() != null) {
                nearbyUser_Api_url = getActivity().getResources().getString(R.string.dosomething_apilink_string_nearestusers);
            }


        }

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                HashMap<String, Object> paramsfb = new HashMap<>();
                paramsfb.put(TAG_SESSIONID, sessionid);
                paramsfb.put(TAG_LATITUDE, latitude);
                paramsfb.put(TAG_LONGITUDE, longitude);
                paramsfb.put(TAG_FILTER_STATUS, filter_status);
                paramsfb.put(TAG_FILTER_GENDER, filter_gender);
                paramsfb.put(TAG_FILTER_AGERANGE, filter_agerange);
                paramsfb.put(TAG_FILTER_DISTANCE, filter_distance);
                paramsfb.put(TAG_USERLISTPAGE, page);
                json_string = jsonfunctions.postToURL(nearbyUser_Api_url, paramsfb);
                Log.v("jason url=======>", String.valueOf(paramsfb));
                System.out.println("DOSOMETHING...RESPONCE..." + json_string);
                json_object = new JSONObject(json_string);
                if (json_object.has("nearestusers")) {
                    json_content = json_object.getJSONObject("nearestusers");
                    if (json_content.getString("status").equalsIgnoreCase("success")) {
//                            pDialog.dismiss();
                        status = "success";
//                                pd.dismiss();

                        if (json_content.has("recordCount")) {
                            recordCount = json_content.getInt("recordCount");

                        }
                        if (json_content.has("matchedUser")) {
                            if (!json_content.getString("matchedUser").equals("")) {
                                dosmething_nearuser_matched_Api_staus = "matchedUser";
                                JSONObject matchedUser = json_content.getJSONObject("matchedUser");

                                matchedUser_user_id = matchedUser.getInt("user_id");
                                matchedUser_first_name = matchedUser.getString("first_name");
                                matchedUser_last_name = matchedUser.getString("last_name");
                                matchedUser_about = matchedUser.getString("about");
                                matchedUser_image1 = matchedUser.getString("image1");
                            } else {
                                dosmething_nearuser_matched_Api_staus = "";
                            }


                        }
                        if (json_content.has("page")) {
                            page = json_content.getString("page");
                        }


                        JSONArray userlist = json_content.getJSONArray("UserList");


                        for (int i = 0; i < userlist.length(); i++) {
                            JSONObject userlistobject = userlist.getJSONObject(i);
                            if (userlistobject.has("user_id")) {
                                user_id = userlistobject.getString("user_id");
                            } else {
                                user_id = "";
                            }
                            if (userlistobject.has("first_name")) {
                                first_name = userlistobject.getString("first_name");

                            } else {
                                first_name = "";
                            }
                            if (userlistobject.has("last_name")) {
                                last_name = userlistobject.getString("last_name");

                            } else {
                                last_name = "";
                            }

                            if (userlistobject.has("about")) {
                                about = userlistobject.getString("about");

                            } else {
                                about = "";
                            }

                            if (userlistobject.has("gender")) {
                                gender = userlistobject.getString("gender");

                            } else {
                                gender = "";
                            }

                            if (userlistobject.has("age")) {
                                age = userlistobject.getString("age");

                            } else {
                                age = "";
                            }
                            if (userlistobject.has("date_of_birth")) {
                                date_of_birth = userlistobject.getString("date_of_birth");

                            } else {
                                date_of_birth = "";
                            }

                            if (userlistobject.has("online_status")) {
                                online_status = userlistobject.getString("online_status");

                            } else {
                                online_status = "";
                            }

                            if (userlistobject.has("image2")) {
                                image2 = userlistobject.getString("image2");

                            } else {
                                image2 = "";
                            }

                            if (userlistobject.has("image1_thumb")) {
                                image1 = userlistobject.getString("image1_thumb");
                            } else {
                                if (userlistobject.has("image1")) {
                                    image1 = userlistobject.getString("image1");

                                } else {
                                    image1 = "";
                                }

                            }


                            if (userlistobject.has("image3")) {
                                image3 = userlistobject.getString("image3");

                            } else {
                                image3 = "";
                            }

                            if (userlistobject.has("available_now")) {
                                available_now = userlistobject.getString("available_now");

                            } else {
                                available_now = "";
                            }

                            if (userlistobject.has("send_request")) {
                                send_request = userlistobject.getString("send_request");

                            } else {
                                send_request = "";
                            }
                            if (userlistobject.has("dosomething")) {
                                dosomethinglist = userlistobject.getString("dosomething");
                                if (!dosomethinglist.equals("")) {
                                    JSONArray dosomethingList = userlistobject.getJSONArray("dosomething");
                                    final int numberOfItemsInResp = dosomethingList.length();
                                    for (int j = 0; j < numberOfItemsInResp; j++) {
                                        if (numberOfItemsInResp == 1) {
                                            JSONObject details = dosomethingList.getJSONObject(0);
                                            id = details.getInt("Id");
                                            name = details.getString("name");
                                            Image = details.getString("NearbyImage");
                                            ActiveImage = details.getString("InactiveImage");
                                            id1 = 0;
                                            name1 = "";
                                            Image1 = "";
                                            ActiveImage1 = "";
                                            id2 = 0;
                                            name2 = "";
                                            Image2 = "";
                                            ActiveImage2 = "";
                                        }


                                        if (numberOfItemsInResp == 2) {
                                            JSONObject details = dosomethingList.getJSONObject(0);
                                            id = details.getInt("Id");
                                            name = details.getString("name");
                                            Image = details.getString("NearbyImage");
                                            ActiveImage = details.getString("InactiveImage");
                                            JSONObject details1 = dosomethingList.getJSONObject(1);
                                            id1 = details1.getInt("Id");
                                            name1 = details1.getString("name");
                                            Image1 = details1.getString("NearbyImage");
                                            ActiveImage1 = details1.getString("InactiveImage");
                                            id2 = 0;
                                            name2 = "";
                                            Image2 = "";
                                            ActiveImage2 = "";
                                        }


                                        if (numberOfItemsInResp == 3) {
                                            JSONObject details = dosomethingList.getJSONObject(0);
                                            id = details.getInt("Id");
                                            name = details.getString("name");
                                            Image = details.getString("NearbyImage");
                                            ActiveImage = details.getString("InactiveImage");
                                            JSONObject details1 = dosomethingList.getJSONObject(1);
                                            id1 = details1.getInt("Id");
                                            name1 = details1.getString("name");
                                            Image1 = details1.getString("NearbyImage");
                                            ActiveImage1 = details1.getString("InactiveImage");
                                            JSONObject details2 = dosomethingList.getJSONObject(2);
                                            id2 = details2.getInt("Id");
                                            name2 = details2.getString("name");
                                            Image2 = details2.getString("NearbyImage");
                                            ActiveImage2 = details2.getString("InactiveImage");
                                        }


                                    }


                                    dosomethingList = userlistobject.getJSONArray("dosomething");

                                    for (int j = 0; j < dosomethingList.length(); j++) {
                                        JSONObject details = dosomethingList.getJSONObject(j);
                                        int id = details.getInt("Id");
                                        String name = details.getString("name");
                                        String Image = details.getString("ActiveImage");
                                        String ActiveImage = details.getString("InactiveImage");
                                        dosomething_beans.add(new Dosomething_Bean(id, name, Image, ActiveImage));

                                    }


                                } else {
                                    id = 0;
                                    name = "";
                                    Image = "";
                                    ActiveImage = "";
                                    id1 = 0;
                                    name1 = "";
                                    Image1 = "";
                                    ActiveImage1 = "";
                                    id2 = 0;
                                    name2 = "";
                                    Image2 = "";
                                    ActiveImage2 = "";
                                }
                            }


                            if (userlistobject.has("distance")) {
                                distance = userlistobject.getString("distance");

                            } else {
                                distance = "";
                            }


                            if (userlistobject.has("hobbieslist")) {
                                JSONArray hobbiesArray = userlistobject.getJSONArray("hobbieslist");
                                for (int j = 0; j < hobbiesArray.length(); j++) {
                                    JSONObject details = hobbiesArray.getJSONObject(j);
                                    int id = details.getInt("hobbies_id");
                                    int category_id = details.getInt("category_id");
                                    String name = details.getString("name");
                                    String Image = details.getString("image");
                                    String ActiveImage = details.getString("image_active");
                                    hobbiesBeans.add(new HobbiesBean(id, category_id, name, Image, ActiveImage));
                                }

                            }

                            filter_list.add(new Filterbean(user_id, first_name, last_name, about, gender, age, date_of_birth, online_status, image2, image1, image3, available_now, send_request, distance, id, name, Image, ActiveImage, id1, name1, Image1, ActiveImage1, id2, name2, Image2, ActiveImage2, hobbiesBeans));

                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getDoSomething());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getonline_status());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getdistance());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getfirst_name());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getlast_name());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getgender());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getabout());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getdate_of_birth());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getimage1());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getuser_id());
                            Log.d("BEAN", "GHGHGH" + filter_list.get(i).getListHobbies());

                        }
                    } else if (json_content.getString("status").equalsIgnoreCase("failed")) {

                        if (json_content.getString("Message").equalsIgnoreCase("No Users Found")) {
                            status = "No Users Found";

                        } else if (json_content.getString("Message").equalsIgnoreCase("Please DoSomething to search for Users")) {
                            status = "Please DoSomething to search for Users";

                        }

                    } else if (json_content.getString("status").equalsIgnoreCase("failed")) {

                        if (json_content.getString("Message").equalsIgnoreCase("Please DoSomething to search for Users")) {
                            status = "Please DoSomething to search for Users";

                        }

                    } else if (json_content.getString("status").equalsIgnoreCase("error")) {
                        pd.dismiss();

                        status = "InvalidSession";
                    }

                } else if (json_object.has("error")) {
                    if (json_object.getString("error").equalsIgnoreCase("InvalidSession")) {
                        pd.dismiss();

                        status = "InvalidSession";

                    }
                }
                return true;

            } catch (Exception e) {
                e.printStackTrace();
                error = e;
                return false;

            }


        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                try {
                    switch (status) {
                        case "success":
                            try {
                                kbv.setVisibility(View.GONE);
                                relativelayout_nearme_progress.setVisibility(View.GONE);
                                if (timer != null) {

                                    timer.cancel();


                                    timer = null;

                                }
                                splashAnimation.stop();
                                activity_dosomething_nearme_textview_nouser.setVisibility(View.GONE);
                                if(formattedDate.equals(sharedPreferences.getDeviceDate(getActivity()))) {
                                    if (sharedPreferences.getWalkThroughNearme(getActivity()).equals("false")) {
                                        layout_walkthrough_profile.setVisibility(View.VISIBLE);
                                        blink_time = new Timer();
                                        blink_time.schedule(new Blink_progress(), 0, 340);
                                        splashAnimation_nearme.start();
                                        sharedPreferences.setWalkThroughNearme(getActivity(), "true");
                                    }
                                }

                                /*layout_walkthrough_profile.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        layout_walkthrough_profile.setVisibility(View.GONE);
                                        if (blink_time != null) {

                                            blink_time.cancel();


                                            blink_time = null;

                                        }
                                        splashAnimation_nearme.stop();
                                        sharedPreferences.setWalkThroughNearme(getActivity(), "true");
                                    }
                                });*/
                          /*  if (dosmething_nearuser_matched_Api_staus.equals("matchedUser")) {
                                activity_dosomething_nearme.setEnabled(false);
                                dosomething_nearme_gridview_layout.setAlpha(0.1f);
                                dosomething_nearme_matched_profile_popup.setVisibility(View.VISIBLE);

                                dosomething_nearme_matched_profile_name_textview.setText("You and " + matchedUser_first_name + " " + matchedUser_last_name + " " + "are a match \n\n Start Chatting to");
                                if (!matchedUser_image1.equals("")) {

                                    aQuery.id(dosomething_nearme_matched_profile_near_userImage).image(matchedUser_image1, true, true, 0, 0, new BitmapAjaxCallback() {
                                        @Override
                                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                            if (status.getCode() == 200) {
                                                Bitmap conv_bm = getCroppedBitmap(bm);
                                                iv.setImageBitmap(conv_bm);

                                            }
                                        }
                                    });
                                } else {
                                    dosomething_nearme_matched_profile_near_userImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));

                                }

                                if (getActivity() != null) {
                                    if (!sharedPreferences.getProfilePicture(getActivity()).equals("")) {
                                        aQuery.id(dosomething_nearme_matched_profile_userImage).image(sharedPreferences.getProfilePicture(getActivity()), true, true, 0, 0, new BitmapAjaxCallback() {
                                            @Override
                                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                                if (status.getCode() == 200) {
                                                    Bitmap conv_bm = getCroppedBitmap(bm);
                                                    iv.setImageBitmap(conv_bm);


                                                }
                                            }
                                        });

                                    } else {
                                        dosomething_nearme_matched_profile_userImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));

                                    }


                                }
                                dosomething_nearme_matched_profile_popup.setVisibility(View.VISIBLE);
                                dosmething_nearuser_matched_grid_control = "Click_false";

                                dosomething_nearme_matched_profile_chat_dosomething_textview.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dosomething_nearme_matched_profile_popup.setVisibility(View.GONE);
                                        dosmething_nearuser_matched_grid_control = "";
                                        dosomething_nearme_gridview_layout.setAlpha(1);
                                        if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                                            chatstart = "1";
                                            sessionid = sharedPreferences.getSessionid(getActivity());
                                            request_send_user_id = String.valueOf(matchedUser_user_id);
                                            new SendRequest().execute();
                                        }
                                        sharedPreferences.setFriendFirstname(getActivity(), matchedUser_first_name);
                                        sharedPreferences.setFriendProfilePicture(getActivity(), matchedUser_image1);
                                    }
                                });

                            } else {
                                dosmething_nearuser_matched_grid_control = "";
                            }*/

                                dosmething_nearuser_matched_grid_control = "";

                                if (getActivity() != null) {
                                    ((MyApplication) getActivity().getApplication()).setListFilterBeans(filter_list);
//                            ((MyApplication) getActivity().getApplication()).setListHobbies(hobbiesBeans);

                                    Log.d("Application Array", "near_mee" + ((MyApplication) getActivity().getApplication()).getListFilterBeans());
                                   /* ProfileAdapter adapter = new ProfileAdapter(getActivity(), ((MyApplication) getActivity().getApplication()).getListFilterBeans());
                                    activity_dosomething_nearme.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();*/
                                    activity_dosomething_nearme.hasFixedSize();
                                    activity_dosomething_nearme.setLayoutManager(layoutManager);

                                    if (profileViewAdapter == null) {
                                        profileViewAdapter = new ProfileViewAdapter(getActivity(), ((MyApplication) getActivity().getApplication()).getListFilterBeans());
                                        activity_dosomething_nearme.setAdapter(profileViewAdapter);
                                    } else {
//                                       profileViewAdapter.setHasStableIds(false);
                                        profileViewAdapter.setSelectedIndex(((MyApplication) getActivity().getApplication()).getListFilterBeans());

                                    }

//                                    if (((MyApplication) getActivity().getApplication()).getanInt() != 0) {
//                                        activity_dosomething_nearme.scrollToPosition(((MyApplication) getActivity().getApplication()).getanInt());
//
//                                    }

                                    loading = true;
//                                    activity_dosomething_nearme.setExpanded(true);
                                    Log.d("filter_list", String.valueOf(filter_list.size()));
                                    int index = 0;

                                    pull_to_refresh_progress.setVisibility(View.GONE);









                                    /*activity_dosomething_nearme.setSelection(index);
                                    activity_dosomething_nearme.setOnScrollListener(new AbsListView.OnScrollListener() {
                                        @Override
                                        public void onScrollStateChanged(AbsListView view, int scrollState) {
                                            Log.d("gridview", "scrollstate" + scrollState);
                                        }

                                        @Override
                                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                            Log.d("gridview", "firstVisibleItem" + firstVisibleItem);
                                            Log.d("gridview", "visibleItemCount" + visibleItemCount);
                                            Log.d("gridview", "totalItemCount" + totalItemCount);
                                            *//*if (totalItemCount == filter_list.size()) {
                                                Log.d("dosomething", "nearme_list" + filter_list);
                                                if (!sharedPreferences.getSessionid(getActivity()).equals("") || !sharedPreferences.getLatitude(getActivity()).equals("") || !sharedPreferences.getLongitude(getActivity()).equals("") && getActivity() != null) {


                                                    sessionid = sharedPreferences.getSessionid(getActivity());
                                                    latitude = sharedPreferences.getLatitude(getActivity());
                                                    longitude = sharedPreferences.getLongitude(getActivity());
                                                    filter_status = sharedPreferences.getFilterStatus(getActivity());
                                                    filter_gender = sharedPreferences.getFilterGender(getActivity());

                                                    Log.d("dosomething", "filter_distance" + sharedPreferences.getFilterDistance(getActivity()));

                                                    if (sharedPreferences.getFilterDistance(getActivity()).trim().equals("0.0-50.0")) {

                                                        filter_distance = "";
                                                        Log.d("dosomething", "filter_distance" + filter_distance);

                                                    } else {
                                                        filter_distance = sharedPreferences.getFilterDistance(getActivity());
                                                        Log.d("dosomething", "filter_distance" + filter_distance);

                                                    }
                                                    Log.d("dosomething", "filter_agerange" + sharedPreferences.getFilterAge(getActivity()));

                                                    if (sharedPreferences.getFilterAge(getActivity()).trim().equals("18.0-80.0")) {

                                                        filter_agerange = "";
                                                        Log.d("dosomething", "filter_agerange" + filter_agerange);
                                                    } else {
                                                        filter_agerange = sharedPreferences.getFilterAge(getActivity());
                                                        Log.d("dosomething", "filter_agerange" + filter_agerange);
                                                    }

                                                    page = "1";
                                                    new AsynDataClass().execute();


                                                }

                                            }*//*
                                        }


                                    });*/
                                }


                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "No Users Found":
                            if (getActivity() != null) {
                                kbv.setVisibility(View.GONE);
                                relativelayout_nearme_progress.setVisibility(View.GONE);
                                if (timer != null) {

                                    timer.cancel();


                                    timer = null;

                                }
                                splashAnimation.stop();
                                sharedPreferences.setFilterGender(getActivity(), "");
                                sharedPreferences.setFilterAvailable(getActivity(), "");
                                sharedPreferences.setFilterStatus(getActivity(), "");
                                sharedPreferences.setFilterDistance(getActivity(), "");
                                sharedPreferences.setFilterAge(getActivity(), "");

                                if (page == "1") {
                                    activity_dosomething_nearme_textview_nouser.setText("No Users Found");
                                    activity_dosomething_nearme_textview_nouser.setVisibility(View.VISIBLE);
                                    activity_dosomething_nearme_textview_nouser.setTextColor(getActivity().getResources().getColor(R.color.text_red));
                                }
                                dosmething_nearuser_matched_grid_control = "";
                            /*AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
                            dialogBuilder.setTitle("Info");
                            dialogBuilder.setMessage("No Users Found");
                            dialogBuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (getActivity() != null) {

//                                        sessionid = sharedPreferences.getSessionid(getActivity());
//                                        latitude = sharedPreferences.getLatitude(getActivity());
//                                        longitude = sharedPreferences.getLongitude(getActivity());
//                                        filter_status = sharedPreferences.getFilterStatus(getActivity());
//                                        filter_agerange = sharedPreferences.getFilterAge(getActivity());
//                                        filter_distance = sharedPreferences.getFilterDistance(getActivity());
//                                        filter_gender = sharedPreferences.getFilterGender(getActivity());
//                                        new AsynDataClass().execute();
                                    }
                                    dialog.dismiss();
                                }
                            });
                            dialogBuilder.setCancelable(false);
                            dialogBuilder.show();*/
//                                Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_LONG).show();
//                            pDialog.dismiss();
                                pd.dismiss();
                            }
                            pull_to_refresh_progress.setVisibility(View.GONE);

                            break;

                        case "Please DoSomething to search for Users":

                            if (getActivity() != null) {
                                kbv.setVisibility(View.GONE);
                                relativelayout_nearme_progress.setVisibility(View.GONE);
                                if (timer != null) {

                                    timer.cancel();


                                    timer = null;

                                }
                                splashAnimation.stop();
                                sharedPreferences.setFilterGender(getActivity(), "");
                                sharedPreferences.setFilterAvailable(getActivity(), "");
                                sharedPreferences.setFilterStatus(getActivity(), "");
                                sharedPreferences.setFilterDistance(getActivity(), "");
                                sharedPreferences.setFilterAge(getActivity(), "");


                                activity_dosomething_nearme_textview_nouser.setText("Please DoSomething to search for Users");
                                activity_dosomething_nearme_textview_nouser.setVisibility(View.VISIBLE);
                                activity_dosomething_nearme_textview_nouser.setTextColor(getActivity().getResources().getColor(R.color.text_red));
                            /*AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
                            dialogBuilder.setTitle("Info");
                            dialogBuilder.setMessage("No Users Found");
                            dialogBuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (getActivity() != null) {

//                                        sessionid = sharedPreferences.getSessionid(getActivity());
//                                        latitude = sharedPreferences.getLatitude(getActivity());
//                                        longitude = sharedPreferences.getLongitude(getActivity());
//                                        filter_status = sharedPreferences.getFilterStatus(getActivity());
//                                        filter_agerange = sharedPreferences.getFilterAge(getActivity());
//                                        filter_distance = sharedPreferences.getFilterDistance(getActivity());
//                                        filter_gender = sharedPreferences.getFilterGender(getActivity());
//                                        new AsynDataClass().execute();
                                    }
                                    dialog.dismiss();
                                }
                            });
                            dialogBuilder.setCancelable(false);
                            dialogBuilder.show();*/
//                                Toast.makeText(getActivity(), "FAILED", Toast.LENGTH_LONG).show();
//                            pDialog.dismiss();
                                pd.dismiss();
                            }
                            pull_to_refresh_progress.setVisibility(View.GONE);


                            break;

                        case "InvalidSession":


                            kbv.setVisibility(View.GONE);
                            relativelayout_nearme_progress.setVisibility(View.GONE);
                            if (timer != null) {

                                timer.cancel();


                                timer = null;

                            }
                            splashAnimation.stop();
                            sharedPreferences.setLogin(getActivity(), "");
                            sharedPreferences.setEmail(getActivity(), "");
                            sharedPreferences.setDateofBirth(getActivity(), "");
                            sharedPreferences.setFirstname(getActivity(), "");
                            sharedPreferences.setDeviceToken(getActivity(), "");
                            sharedPreferences.setLastname(getActivity(), "");
                            sharedPreferences.setGender(getActivity(), "");
                            sharedPreferences.setPassword(getActivity(), "");
                            sharedPreferences.setUserId(getActivity(), "");
                            sharedPreferences.setSessionid(getActivity(), "");
                            sharedPreferences.setProfileId(getActivity(), "");
                            sharedPreferences.setNotifyMessage(getActivity(), "");
                            sharedPreferences.setNotifyVibration(getActivity(), "");
                            sharedPreferences.setNotifySound(getActivity(), "");
                            sharedPreferences.setProfilePicture(getActivity(), "");
                            sharedPreferences.setProfilePicture1(getActivity(), "");
                            sharedPreferences.setProfilePicture2(getActivity(), "");
                            sharedPreferences.setFBProfilePicture(getActivity(), "");
                            sharedPreferences.setAbout(getActivity(), "");
                            Intent i = new Intent(getActivity(), DoSomeThingLogin.class);
                            startActivity(i);
                            getActivity().finish();
                            pull_to_refresh_progress.setVisibility(View.GONE);

                            break;
                    }
                } catch (Exception e) {
                    Log.e("Timeout Exception: ", e.toString());
                    e.printStackTrace();
                }
            } else {
                if (error != null) {
                    kbv.setVisibility(View.GONE);
                    relativelayout_nearme_progress.setVisibility(View.GONE);
                    if (timer != null) {

                        timer.cancel();


                        timer = null;

                    }
                    splashAnimation.stop();
                }

                relativelayout_nearme_retry.setVisibility(View.VISIBLE);

                nearme_retry_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        relativelayout_nearme_retry.setVisibility(View.GONE);
                        sessionid = sharedPreferences.getSessionid(getActivity());
                        latitude = sharedPreferences.getLatitude(getActivity());
                        longitude = sharedPreferences.getLongitude(getActivity());
                        filter_status = sharedPreferences.getFilterStatus(getActivity());
                        filter_gender = sharedPreferences.getFilterGender(getActivity());

                        Log.d("dosomething", "filter_distance" + sharedPreferences.getFilterDistance(getActivity()));

                        if (sharedPreferences.getFilterDistance(getActivity()).trim().equals("0.0-50.0")) {

                            filter_distance = "";
                            Log.d("dosomething", "filter_distance" + filter_distance);

                        } else {
                            filter_distance = sharedPreferences.getFilterDistance(getActivity());
                            Log.d("dosomething", "filter_distance" + filter_distance);

                        }
                        Log.d("dosomething", "filter_agerange" + sharedPreferences.getFilterAge(getActivity()));

                        if (sharedPreferences.getFilterAge(getActivity()).trim().equals("18.0-80.0")) {

                            filter_agerange = "";
                            Log.d("dosomething", "filter_agerange" + filter_agerange);
                        } else {
                            filter_agerange = sharedPreferences.getFilterAge(getActivity());
                            Log.d("dosomething", "filter_agerange" + filter_agerange);
                        }

                        page = "1";
                        new AsynDataClass().execute();
                    }
                });

            }


//                if(result != null && result.equals("ERROR_IN_CODE"))
//                {
//                    System.out.println("TEST...POST EXECUTE...UNUSED..."+result);
//                    NetworkCheck.alertdialog(getActivity());
//                }
//                else
//                {


        }
//            }


    }

    private class SendRequest extends AsyncTask<Void, Void, Boolean> {
        String status;
        String conversation_matched;
        private String Name;
        private String image1;
        private String UserId="0";
        Exception error;
        String sendRequestApi;
        private String ConversaionId="0";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd.show();
            dosmething_nearuser_matched_grid_control = "Click_false";

            kbv.setVisibility(View.VISIBLE);
            relativelayout_nearme_progress.setVisibility(View.VISIBLE);
            timer = new Timer();
            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation.start();

            sendRequestApi = getActivity().getResources().getString(R.string.dosomething_apilink_string_sendrequest);
           /* progress_bar.show();
            timer = new Timer();
            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation_progress.start();*/
        }

        @Override
        protected Boolean doInBackground(Void... params) {


            HashMap<String, Object> paramsfb = new HashMap<>();
            paramsfb.put(TAG_SESSIONID, sessionid);
            paramsfb.put(TAG_REQUESTSEND_USERID, request_send_user_id);
            if (!chatstart.equals("")) {
                paramsfb.put(TAG_CHATSTART, chatstart);
            }

            json_string = jsonfunctions.postToURL(sendRequestApi, paramsfb);
            Log.v("jason url=======>", String.valueOf(paramsfb));
            try {
                json_object = new JSONObject(json_string);
                json_content = json_object.getJSONObject("sendrequest");
                if (json_object.has("sendrequest")) {
                    if (json_content.getString("status").equalsIgnoreCase("success")) {

                        if (json_content.has("Conversaion")) {
                            if (!json_content.get("Conversaion").equals("0")) {
                                conversation_matched = "match";
                                JSONObject sendrequest = json_content.getJSONObject("Conversaion");
                                if (sendrequest.has("id")) {
                                    ConversaionId = sendrequest.getString("id");
                                    UserId = sendrequest.getString("UserId");
                                    Name = sendrequest.getString("Name");
                                    image1 = sendrequest.getString("image1");



                                }
                            } else {
                                conversation_matched = "0";
                            }

                        } else {
                            conversation_matched = "0";
                        }


                        pd.dismiss();
                        status = "success";

                    } else if (json_content.getString("status").equalsIgnoreCase("failed")) {
                        status = "failed";

//                            Toast.makeText(getActivity().getApplicationContext(), "FAILED", Toast.LENGTH_LONG);

                        pd.dismiss();
                    } else if (json_content.getString("error").equalsIgnoreCase("InvalidSession")) {
                        status = "InvalidSession";
                        pd.dismiss();

                    }
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
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);


            if (result) {
                try {

                    {
                        switch (status) {
                            case "success":

                                if (getActivity() != null) {
                                    kbv.setVisibility(View.GONE);
                                    relativelayout_nearme_progress.setVisibility(View.GONE);
                                    if (timer != null) {

                                        timer.cancel();


                                        timer = null;

                                    }
                                    splashAnimation.stop();
                                    if (conversation_matched.equals("match")) {
                                        isGridClick = false;
                                        activity_dosomething_nearme.setEnabled(false);
                                        dosomething_nearme_gridview_layout.setAlpha(0.1f);
                                        dosomething_nearme_matched_profile_popup.setVisibility(View.VISIBLE);
                                        if(formattedDate.equals(sharedPreferences.getDeviceDate(getActivity()))) {
                                            if (sharedPreferences.getWalkThroughMatch(getActivity()).equals("false")) {
                                                layout_walkthrough_match.setVisibility(View.VISIBLE);
                                                blink_match = new Timer();
                                                blink_match.schedule(new Blink_match(), 0, 340);
                                                splashAnimation_match.start();
                                                sharedPreferences.setWalkThroughMatch(getActivity(), "true");
                                            }
                                        }

                                            sharedPreferences.setConversationId(getActivity(), ConversaionId);
                                            sharedPreferences.setFriendUserId(getActivity(), UserId);
                                            sharedPreferences.setFriendFirstname(getActivity(),Name);


                                        dosomething_nearme_matched_profile_name_textview.setText("You and " + Name + " " + "are a match \n\n Start Chatting to");



                                        if (!image1.equals("")) {

                                            aQuery.id(dosomething_nearme_matched_profile_near_userImage).image(image1, true, true, 0, 0, new BitmapAjaxCallback() {
                                                @Override
                                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                                    if (status.getCode() == 200) {
                                                        Bitmap conv_bm = getCroppedBitmap(bm);
                                                        iv.setImageBitmap(conv_bm);

                                                    }
                                                }
                                            });
                                        } else {
                                            dosomething_nearme_matched_profile_near_userImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));

                                        }

                                        if (getActivity() != null) {
                                            if (!sharedPreferences.getProfilePicture(getActivity()).equals("")) {
                                                aQuery.id(dosomething_nearme_matched_profile_userImage).image(sharedPreferences.getProfilePicture(getActivity()), true, true, 0, 0, new BitmapAjaxCallback() {
                                                    @Override
                                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                                        if (status.getCode() == 200) {
                                                            Bitmap conv_bm = getCroppedBitmap(bm);
                                                            iv.setImageBitmap(conv_bm);


                                                        }
                                                    }
                                                });

                                            } else {
                                                dosomething_nearme_matched_profile_userImage.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));

                                            }


                                        }
                                        dosomething_nearme_matched_profile_popup.setVisibility(View.VISIBLE);
                                        dosmething_nearuser_matched_grid_control = "Click_false";

                                        dosomething_nearme_matched_profile_popup_layout.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                new CheckRequestStatus().execute();

                                            }
                                        });

                                    } else {
                                        dosmething_nearuser_matched_grid_control = " ";

                                    }


                                    profileViewAdapter.notifyDataSetChanged();
                                    /*latitude = sharedPreferences.getLatitude(getActivity());
                                    longitude = sharedPreferences.getLongitude(getActivity());
                                    filter_status = sharedPreferences.getFilterStatus(getActivity());
                                    filter_agerange = sharedPreferences.getFilterAge(getActivity());
                                    filter_distance = sharedPreferences.getFilterDistance(getActivity());
                                    filter_gender = sharedPreferences.getFilterGender(getActivity());
                                    if (filter_list != null) {
                                        filter_list.clear();
                                    }
                                    if (dosomething_list != null) {
                                        dosomething_list.clear();
                                    }


                                    new AsynDataClass().execute();*/
                                }

                                break;
                            case "failed":

                                progress_bar.dismiss();
                                if (timer != null) {

                                    timer.cancel();


                                    timer = null;

                                }
                                splashAnimation_progress.stop();
                                break;

                            case "InvalidSession":

                                progress_bar.dismiss();
                                if (timer != null) {

                                    timer.cancel();


                                    timer = null;

                                }
                                splashAnimation_progress.stop();
                                sharedPreferences.setLogin(getActivity(), "");
                                sharedPreferences.setEmail(getActivity(), "");
                                sharedPreferences.setDateofBirth(getActivity(), "");
                                sharedPreferences.setFirstname(getActivity(), "");
                                sharedPreferences.setDeviceToken(getActivity(), "");
                                sharedPreferences.setLastname(getActivity(), "");
                                sharedPreferences.setGender(getActivity(), "");
                                sharedPreferences.setPassword(getActivity(), "");
                                sharedPreferences.setUserId(getActivity(), "");
                                sharedPreferences.setSessionid(getActivity(), "");
                                sharedPreferences.setProfileId(getActivity(), "");
                                sharedPreferences.setNotifyMessage(getActivity(), "");
                                sharedPreferences.setNotifyVibration(getActivity(), "");
                                sharedPreferences.setNotifySound(getActivity(), "");
                                sharedPreferences.setProfilePicture(getActivity(), "");
                                sharedPreferences.setProfilePicture1(getActivity(), "");
                                sharedPreferences.setProfilePicture2(getActivity(), "");
                                sharedPreferences.setFBProfilePicture(getActivity(), "");
                                sharedPreferences.setAbout(getActivity(), "");
                                Intent i = new Intent(getActivity(), DoSomeThingLogin.class);
                                startActivity(i);
                                getActivity().finish();
                                break;

                            case "":
                                kbv.setVisibility(View.GONE);
                                relativelayout_nearme_progress.setVisibility(View.GONE);
                                if (timer != null) {

                                    timer.cancel();


                                    timer = null;

                                }
                                splashAnimation.stop();
                                break;
                        }

                    }
                } catch (Exception e) {
                    Log.e("Timeout Exception: ", e.toString());
                    e.printStackTrace();
                }

            } else {
                if (error != null) {
                    kbv.setVisibility(View.GONE);
                    relativelayout_nearme_progress.setVisibility(View.GONE);
                    if (timer != null) {

                        timer.cancel();


                        timer = null;

                    }
                    splashAnimation.stop();


                }
            }


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

            // Showing progress dialog

//            pDialog = new ProgressDialog(getActivity());
//
//            pDialog.setMessage("Please wait...");
//
//            pDialog.setCancelable(false);
//
//            pDialog.show();
        }


        @Override

        protected Void doInBackground(Void... params) {

            if (getActivity() != null) {


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
                            JSONArray hobbiesArray = userlistobject.getJSONArray("hobbieslist");
                            hobbiesBeans.clear();
                            for (int j = 0; j < hobbiesArray.length(); j++) {
                                JSONObject details = hobbiesArray.getJSONObject(j);
                                int id = details.getInt("hobbies_id");
                                int category_id = details.getInt("category_id");
                                String name = details.getString("name");
                                String Image = details.getString("image");
                                String ActiveImage = details.getString("image_active");
                                hobbiesBeans.add(new HobbiesBean(id, category_id, name, Image, ActiveImage));


                            }
                            JSONArray dosomethingList = userlistobject.getJSONArray("dosomething");
                            dosomething_beans.clear();
                            for (int j = 0; j < dosomethingList.length(); j++) {
                                JSONObject details = dosomethingList.getJSONObject(j);
                                int id = details.getInt("Id");
                                String name = details.getString("name");
                                String Image = details.getString("ActiveImage");
                                String ActiveImage = details.getString("InactiveImage");
                                dosomething_beans.add(new Dosomething_Bean(id, name, Image, ActiveImage));

                            }

//                                profileBeans.add(new ProfileBean(user_id, profile_id, first_name, last_name, gender, about, email, status, device, age, image2, image1, image3, device_token, date_of_birth));

                            Log.d("BEAN", "GHGHGH" + user_id);
                            Log.d("BEAN", "GHGHGH" + profile_id);

                            Log.d("BEAN", "GHGHGH" + first_name);

                            Log.d("BEAN", "GHGHGH" + last_name);

                            Log.d("BEAN", "GHGHGH" + gender);

                            Log.d("BEAN", "GHGHGH" + about);

                            Log.d("BEAN", "GHGHGH" + email);

                            Log.d("BEAN", "GHGHGH" + status);


                            Log.d("BEAN", "GHGHGH" + image1);


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

                            Toast.makeText(getActivity().getApplicationContext(), "FAILED", Toast.LENGTH_LONG);

//                            pDialog.dismiss();

                        } else if (json_content.getString("status").equalsIgnoreCase("error")) {

                            Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_LONG);

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

            }


            return null;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);


        }

        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);

            try {


                if (!status.equalsIgnoreCase("")) {

                    if (getActivity() != null) {
                        ((MyApplication) getActivity().getApplication()).setListHobbies(hobbiesBeans);
                        ((MyApplication) getActivity().getApplication()).setListDosomethingBean(dosomething_beans);

                        doSomethingFriendsProfileFragment = new DoSomething_Friends_profile_fragment();


                        fragmentTransaction = getFragmentManager().beginTransaction();
                        if (fragmentTransaction != null) {
                            fragmentTransaction.replace(R.id.detail_fragment, doSomethingFriendsProfileFragment);
                            fragmentTransaction.commit();

                        }

                        click_action = false;
                    }


                } else {
                    sharedPreferences.setLogin(getActivity(), "");
                    sharedPreferences.setEmail(getActivity(), "");
                    sharedPreferences.setDateofBirth(getActivity(), "");
                    sharedPreferences.setFirstname(getActivity(), "");
                    sharedPreferences.setDeviceToken(getActivity(), "");
                    sharedPreferences.setLastname(getActivity(), "");
                    sharedPreferences.setGender(getActivity(), "");
                    sharedPreferences.setPassword(getActivity(), "");
                    sharedPreferences.setUserId(getActivity(), "");
                    sharedPreferences.setSessionid(getActivity(), "");
                    sharedPreferences.setProfileId(getActivity(), "");
                    sharedPreferences.setNotifyMessage(getActivity(), "");
                    sharedPreferences.setNotifyVibration(getActivity(), "");
                    sharedPreferences.setNotifySound(getActivity(), "");
                    sharedPreferences.setProfilePicture(getActivity(), "");
                    sharedPreferences.setProfilePicture1(getActivity(), "");
                    sharedPreferences.setProfilePicture2(getActivity(), "");
                    sharedPreferences.setFBProfilePicture(getActivity(), "");
                    sharedPreferences.setAbout(getActivity(), "");
                    Intent i = new Intent(getActivity(), DoSomeThingLogin.class);
                    startActivity(i);
                    getActivity().finish();
                    pd.dismiss();
                }


            } catch (Exception e) {
                Log.e("Timeout Exception: ", e.toString());
                e.printStackTrace();
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


    @Override
    public void onPause() {
        super.onPause();
        Log.d("dosomething", "pause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("dosomething", "destroy");

    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d("dosomething", "stop");
    }


    class Blink_progress extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        if (splashAnimation_nearme.isRunning()) {
                            splashAnimation_nearme.stop();
                        } else {
                            splashAnimation_nearme.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO Auto-generated catch block
                    }


                }

            });
        }
    }


    class Blink_match extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        if (splashAnimation_match.isRunning()) {
                            splashAnimation_match.stop();
                        } else {
                            splashAnimation_match.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO Auto-generated catch block
                    }


                }

            });
        }
    }

    private class CheckRequestStatus extends AsyncTask<Void, Void, Boolean> {

        private String checkRequestApi;
        private Exception error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (getActivity() != null) {
                checkRequestApi = getActivity().getResources().getString(R.string.dosomething_apilink_string_checkrequeststatus);
            }


        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HashMap<String, Object> paramsCheck = new HashMap<>();

            paramsCheck.put(TAG_REQUESTSEND_USERID, request_send_user_id);
            paramsCheck.put(TAG_SESSIONID, sessionid);
            json_string = jsonfunctions.postToURL(checkRequestApi, paramsCheck);
            Log.v("jason url=======>", String.valueOf(paramsCheck));
            try {
                json_object = new JSONObject(json_string);
                json_content = json_object.getJSONObject("checkrequeststatus");
                if (json_content.has("checkrequeststatus")) {
                    if (!json_content.get("Conversaion").equals("0")) {

                        String ConversaionId = json_content.getString("id");

                                sharedPreferences.setConversationId(getActivity(), ConversaionId);

                    }
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                error = e;
                return false;
            }

        }


        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                    try {
                        if (json_object.has("checkrequeststatus")) {
                            if (json_content.getString("status").equalsIgnoreCase("success")) {

                                isGridClick = true;
                                layout_walkthrough_match.setVisibility(View.GONE);
                                if (blink_match != null) {

                                    blink_match.cancel();


                                    blink_match = null;

                                }
                                splashAnimation_match.stop();
                                sharedPreferences.setWalkThroughMatch(getActivity(), "true");
                                dosomething_nearme_matched_profile_popup.setVisibility(View.GONE);
                                dosmething_nearuser_matched_grid_control = "";
                                dosomething_nearme_gridview_layout.setAlpha(1);
                                               /* chatstart = "1";
                                                sessionid = sharedPreferences.getSessionid(getActivity());
                                                request_send_user_id = String.valueOf(UserId);
                                                new SendRequest().execute();*/
                                if (getActivity() != null) {
                                    ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().setmatchState("match_chat");
                                    sessionid = sharedPreferences.getSessionid(getActivity());

                                    ((DoSomethingStatus) getActivity()).hideFilterIconVisible(true);
                                    sharedPreferences.setDosomething_filterImage_Visibility(getActivity(), "No");
                                    DoSomethingChatBox doSomethingChatBox = new DoSomethingChatBox();
                                    fragmentTransaction = getFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatBox);
                                    fragmentTransaction.commit();

                                    sharedPreferences.setPushType(getActivity(), "");


                                }
                            }else
                                 if(json_content.getString("status").equalsIgnoreCase("failed"))
                                 {
                                     ((DoSomethingStatus)getActivity()).slideToChat(true);

                                 }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } else {
                if (error != null) {

                }
            }
        }
    }
}
