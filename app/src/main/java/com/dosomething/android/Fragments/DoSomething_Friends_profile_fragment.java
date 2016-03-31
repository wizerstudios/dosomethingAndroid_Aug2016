package com.dosomething.android.Fragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.dosomething.android.Beanclasses.Dosomething_Bean;
import com.dosomething.android.Beanclasses.HobbiesBean;
import com.dosomething.android.Beanclasses.ProfileBean;
import com.dosomething.android.CommonClasses.ExpandableHeightGridView;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.dosomething.android.DoSomeThingLogin;
import com.dosomething.android.MyApplication;
import com.dosomething.android.R;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import static com.google.android.gms.internal.zzip.runOnUiThread;


public class DoSomething_Friends_profile_fragment extends Fragment implements Friend_Profile_one_fragment.Friend_profile_image_viewpager_dots_one,

        Friend_Profile_two_fragment.Friend_profile_image_viewpager_dots_two, Friend_Profile_three_fragment.Friend_profile_image_viewpager_dots_three {


    private static final String TAG_REQUESTSEND_USERID = "request_send_user_id";
    ExpandableHeightGridView

            friends_profile_gridview_wanna_do,

    friends_profile_gridview_hobbies;


    TextView

            friend_profile_textview_lets_do_something,

    friend_profile_textview_about_me,

    friend_profile_textview_about_me_text,

    friend_profile_textview_things_wanna_do,

    friend_profile_textview_hobbies_interest,

    friend_profile_textview_profile_name;

    ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<>();
    ArrayList<Dosomething_Bean> dosomething_beans = new ArrayList<>();

    ArrayList<String> things_name;

    ArrayList<Integer> things_image;

    ArrayList<String> hobbies_name;

    ArrayList<Integer> hobbies_image;
    ArrayList<ProfileBean> profileBeans;
    LinearLayout dotsLayout;

    Typeface

            patron_bold,

    patron_regular,

    patron_medium,

    myfonts_thin;


    PagerAdapter mPagerAdapter;

    List<ImageView> dots;
    List<Fragment> fragments = new Vector<>();
    Context mContext;

    private final static int NUM_PAGES = 3;

    ViewPager pager;

    LinearLayout viewpagerdots, friend_profile_viewpager_layout;

    RelativeLayout friendprofile_relative_image_zoom;

    int width, height;

    WindowManager windowManager;

    ImageView friend_profile_zoom_imageview, profile_page_imageview_gender;

    TextView textview_friend_profilename;
    ImageView textview_friend_gender;

    private static String url = "http://wiztestinghost.com/dosomething/getuserdetails?";

    private static final String TAG_SESSIONID = "sessionid";

    private static final String TAG_PROFILEUSERID = "profile_user_id";

    String sessionid, profile_user_id, filter_status, filter_gender, filter_agerange, filter_distance, json_string, response;

    String latitude;

    String longitude;
    Jsonfunctions jsonfunctions;

    JSONObject json_object, json_content, details;

    private ProgressDialog pDialog;

    SharedPrefrences sharedPreferences;
    private TransparentProgressDialog pd;
    private AQuery aQuery;

    LinearLayout nearme_profile_mainlayout;
    private String request_send_user_id;
    private Bundle bundle;
    ArrayList<String> hobbiesname = new ArrayList<>();
    ArrayList<String> hobbiesimage = new ArrayList<>();
    private Dialog progress_bar;
    private ImageView progress_bar_imageview;
    private AnimationDrawable splashAnimation;
    private Timer timer;
    private Tracker mTracker;

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ((MyApplication) getActivity().getApplication()).setmDoSomething_Friends_profile_fragment(this);


    }


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_do_something__friends_profile_fragment, container, false);
        try
        {
            MyApplication application = (MyApplication) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        aQuery = new AQuery(getActivity());
        dots = new ArrayList<>();
        windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        pd = new TransparentProgressDialog(getActivity(), getResources().getDrawable(R.drawable.loading));
        jsonfunctions = new Jsonfunctions(getActivity());
        sharedPreferences = new SharedPrefrences();
        progress_bar = new Dialog(getActivity());
        progress_bar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress_bar.setContentView(R.layout.progress_bar);
        progress_bar_imageview = (ImageView) progress_bar.findViewById(R.id.progress_bar_imageview);
        progress_bar_imageview.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) progress_bar_imageview.getBackground();
        progress_bar.setCancelable(false);

        if (getActivity() != null) {
            sharedPreferences.setDosomething_filterImage_Visibility(getActivity(), "No");
        }


        /*if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {


            profile_user_id = sharedPreferences.getFriendUserId(getActivity());
            sessionid = sharedPreferences.getSessionid(getActivity());
            new AsynDataClass().execute();
        }*/


//        Log.d("profile_user_id", profile_user_id);
//        Log.d("profile_user_id", sessionid);
        nearme_profile_mainlayout = (LinearLayout) view.findViewById(R.id.nearme_profile_mainlayout);
        friends_profile_gridview_wanna_do = (ExpandableHeightGridView) view.findViewById(R.id.friends_profile_gridview_wanna_do);

        friends_profile_gridview_hobbies = (ExpandableHeightGridView) view.findViewById(R.id.friends_profile_gridview_hobbies);

        friend_profile_textview_lets_do_something = (TextView) view.findViewById(R.id.friend_profile_textview_lets_do_something);

        friend_profile_textview_about_me = (TextView) view.findViewById(R.id.friend_profile_textview_about_me);

        friend_profile_textview_about_me_text = (TextView) view.findViewById(R.id.friend_profile_textview_about_me_text);

        friend_profile_textview_things_wanna_do = (TextView) view.findViewById(R.id.friend_profile_textview_things_wanna_do);

        friend_profile_textview_hobbies_interest = (TextView) view.findViewById(R.id.friend_profile_textview_hobbies_interest);

        friend_profile_textview_profile_name = (TextView) view.findViewById(R.id.friend_profile_textview_profile_name);

        textview_friend_profilename = (TextView) view.findViewById(R.id.textview_friend_profilename);
        textview_friend_gender = (ImageView) view.findViewById(R.id.textview_friend_gender);

        pager = (ViewPager) view.findViewById(R.id.pager);

        dotsLayout = (LinearLayout) view.findViewById(R.id.viewpager_dots);

        friend_profile_viewpager_layout = (LinearLayout) view.findViewById(R.id.friend_profile_viewpager_layout);

        friendprofile_relative_image_zoom = (RelativeLayout) view.findViewById(R.id.friendprofile_relative_image_zoom);

        friend_profile_zoom_imageview = (ImageView) view.findViewById(R.id.friend_profile_zoom_imageview);
        profile_page_imageview_gender = (ImageView) view.findViewById(R.id.profile_page_imageview_gender);
        if(getActivity()!=null)
        {
            ((MyApplication) getActivity().getApplication()).setmDoSomething_Friends_profile_fragment(this);

            if (((MyApplication) getActivity().getApplication()).getDoSomethingStatus() != null) {

                ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().settext("No");


            }
        }


//        bundle = getArguments();
////        bundle.getStringArrayList("array_bundle_hobbies_name");
//        bundle.getStringArray("array_bundle_hobbies_image");
//        bundle.getStringArray("array_bundle_hobbies_name");
//        hobbiesimage.add(Arrays.toString(bundle.getStringArray("array_bundle_hobbies_image")));
//        hobbiesname.add(Arrays.toString(bundle.getStringArray("array_bundle_hobbies_name")));
        if (getActivity() != null) {


            try {
                friend_profile_textview_profile_name.setText(sharedPreferences.getFriendFirstName(getActivity()) + " " + sharedPreferences.getFriendLastname(getActivity()) + "," + sharedPreferences.getFriendAge(getActivity()));
                textview_friend_profilename.setText(sharedPreferences.getFriendFirstName(getActivity()) + " " + sharedPreferences.getFriendLastname(getActivity()) + "," + sharedPreferences.getFriendAge(getActivity()));
                friend_profile_textview_about_me_text.setText(sharedPreferences.getFriendAbout(getActivity()));
                switch (sharedPreferences.getFriendGender(getActivity())) {
                    case "Male":
                        profile_page_imageview_gender.setImageDrawable(getResources().getDrawable(R.drawable.male));
                        textview_friend_gender.setImageDrawable(getResources().getDrawable(R.drawable.male));
                        break;
                    case "Female":
                        profile_page_imageview_gender.setImageDrawable(getResources().getDrawable(R.drawable.female));
                        textview_friend_gender.setImageDrawable(getResources().getDrawable(R.drawable.female));
                        break;
                }
            /*if (sharedPreferences.getFriendGender(getActivity()).equalsIgnoreCase("Male")) {
                profile_page_imageview_gender.setImageDrawable(getResources().getDrawable(R.drawable.male));
                textview_friend_gender.setImageDrawable(getResources().getDrawable(R.drawable.male));
            } else if (sharedPreferences.getFriendGender(getActivity()).equalsIgnoreCase("Female")) {
                profile_page_imageview_gender.setImageDrawable(getResources().getDrawable(R.drawable.female));
                textview_friend_gender.setImageDrawable(getResources().getDrawable(R.drawable.female));

            }*/

                switch (sharedPreferences.getSendRequest(getActivity())) {
                    case "Yes":
                        friend_profile_textview_lets_do_something.setTextColor(getResources().getColor(R.color.white));
                        friend_profile_textview_lets_do_something.setText(getResources().getString(R.string.friend_profile_textview_lets_do_requestsend));
                        friend_profile_textview_lets_do_something.setBackgroundColor(getResources().getColor(R.color.text_grey));
                        friend_profile_textview_lets_do_something.setOnClickListener(new View.OnClickListener() {

                            @Override

                            public void onClick(View v) {
                                ((MyApplication) getActivity().getApplication()).getListFilterBeans().clear();
                                profile_user_id = sharedPreferences.getFriendUserId(getActivity());
                                sessionid = sharedPreferences.getSessionid(getActivity());
                                new SendRequest().execute();

                            }

                        });

                        break;
                    case "No":
                        friend_profile_textview_lets_do_something.setOnClickListener(new View.OnClickListener() {

                            @Override

                            public void onClick(View v) {
                                ((MyApplication) getActivity().getApplication()).getListFilterBeans().clear();
                                profile_user_id = sharedPreferences.getFriendUserId(getActivity());
                                sessionid = sharedPreferences.getSessionid(getActivity());
                                new SendRequest().execute();

                            }

                        });
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if(getActivity()!=null)
            {
                if (((MyApplication) getActivity().getApplication()).getListHobbies().size() != 0) {
                    CustomGrid hobbiesGrid = new CustomGrid(getActivity(), ((MyApplication) getActivity().getApplication()).getListHobbies());
                    friends_profile_gridview_hobbies.setAdapter(hobbiesGrid);
                    friends_profile_gridview_hobbies.setExpanded(true);


                }
                if (((MyApplication) getActivity().getApplication()).getListDosomethingBean().size() != 0) {
                    GridDosomething gridDosomething = new GridDosomething(getActivity(), ((MyApplication) getActivity().getApplication()).getListDosomethingBean());
                    friends_profile_gridview_wanna_do.setAdapter(gridDosomething);
                    friends_profile_gridview_wanna_do.setExpanded(true);
                }
            }



           /* if (send_request.equals("Yes")) {
                friend_profile_textview_lets_do_something.setTextColor(getResources().getColor(R.color.white));
                friend_profile_textview_lets_do_something.setText(getResources().getString(R.string.friend_profile_textview_lets_do_requestsend));
                friend_profile_textview_lets_do_something.setBackgroundColor(getResources().getColor(R.color.text_grey));
            }


            if (send_request.equals("No")) {
                friend_profile_textview_lets_do_something.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View v) {
                        profile_user_id = sharedPreferences.getFriendUserId(getActivity());
                        sessionid = sharedPreferences.getSessionid(getActivity());
                        new SendRequest().execute();

                    }

                });
            }*/


        }

//        things_image = new ArrayList<>();
//
//        things_name = new ArrayList<>();
//
//        hobbies_image = new ArrayList<>();
//
//        hobbies_name = new ArrayList<>();
//
//
//        things_name.add((String) getResources().getText(R.string.status_TextView_running));
//
//        things_name.add((String) getResources().getText(R.string.status_TextView_beach));
//
//        things_name.add((String) getResources().getText(R.string.status_TextView_cycling));
//
////        things_name.add((String) getResources().getText(R.string.status_TextView_meals));
//
////        things_name.add((String) getResources().getText(R.string.status_TextView_coffee));
//
////        things_name.add((String) getResources().getText(R.string.status_TextView_shopping));
//
////        things_name.add((String) getResources().getText(R.string.status_TextView_karoke));
//
////        things_name.add((String) getResources().getText(R.string.status_TextView_gym));
//
////        things_name.add((String) getResources().getText(R.string.status_TextView_tennis));
//
////        things_name.add((String) getResources().getText(R.string.status_TextView_soccer));
//
//
//        things_image.add(R.drawable.running);
//
//        things_image.add(R.drawable.beach);
//
//        things_image.add(R.drawable.cycling);
//
////        things_image.add(R.drawable.meals);
//
////        things_image.add(R.drawable.coffee);
//
////        things_image.add( R.drawable.shopping);
//
////        things_image.add( R.drawable.karaoke);
//
////        things_image.add( R.drawable.gym);
//
////        things_image.add( R.drawable.tennis);
//
////        things_image.add( R.drawable.soccer);
//
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_guitar));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_paint));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_photography));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_piano));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_violin));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_bbq));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_cooking));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_fastfood));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_italianfood));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_japanesefood));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_koreanfood));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_cat));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_dog));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_boardgames));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_fishing));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_gaming));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_gardening));
//
//        hobbies_name.add((String) getResources().getText(R.string.hobbies_textview_finance));
//
//
//        hobbies_image.add(R.drawable.guitar);
//
//        hobbies_image.add(R.drawable.paint);
//
//        hobbies_image.add(R.drawable.photography);
//
//        hobbies_image.add(R.drawable.piano);
//
//        hobbies_image.add(R.drawable.violin);
//
//        hobbies_image.add(R.drawable.bbq);
//
//        hobbies_image.add(R.drawable.cooking);
//
//        hobbies_image.add(R.drawable.fastfood);
//
//        hobbies_image.add(R.drawable.italianfood);
//
//        hobbies_image.add(R.drawable.japanesefood);
//
//        hobbies_image.add(R.drawable.food);
//
//        hobbies_image.add(R.drawable.cat);
//
//        hobbies_image.add(R.drawable.dog);
//
//        hobbies_image.add(R.drawable.boardgames);
//
//        hobbies_image.add(R.drawable.fishing);
//
//        hobbies_image.add(R.drawable.game);
//
//        hobbies_image.add(R.drawable.gardening);
//
//        hobbies_image.add(R.drawable.finance);


        typeFace_textview();

        this.initialisePaging();
        dots.clear();
        dotsLayout.removeAllViews();
        addDots();

        selectDot(pager.getCurrentItem());

//        CustomGrid customGrid_wanna_do = new CustomGrid(getActivity(), things_image, things_name);
//
//        friends_profile_gridview_wanna_do.setAdapter(customGrid_wanna_do);
//
//        friends_profile_gridview_wanna_do.setExpanded(true);


//        CustomGrid customGrid_hobbies = new CustomGrid(getActivity(), hobbies_image, hobbies_name);
//
//        friends_profile_gridview_hobbies.setAdapter(customGrid_hobbies);


        friendprofile_relative_image_zoom.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                fadeOut_profile_pic();

            }

        });

        friendprofile_relative_image_zoom.setOnTouchListener(new View.OnTouchListener() {

            @Override

            public boolean onTouch(View v, MotionEvent event) {

                fadeOut_profile_pic();

                friendprofile_relative_image_zoom.setVisibility(View.GONE);

                return true;
            }
        });

        return view;

    }


    private void setDisplay() {

        DisplayMetrics displayMetrics = new DisplayMetrics();

        windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        width = displayMetrics.widthPixels;

        height = displayMetrics.heightPixels;

        width = (int) (width - (width * 0.05));

        height = (int) (height - (height * 0.05));

    }


    public void fadeIn_profile_pic(String Url) {

        Animation fadeIn = new AlphaAnimation(0, 1);

        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this

        fadeIn.setStartOffset(0);

        fadeIn.setDuration(500);


        AnimationSet animation = new AnimationSet(false); //change to false


        animation.addAnimation(fadeIn);

        friendprofile_relative_image_zoom.setVisibility(View.VISIBLE);

        friendprofile_relative_image_zoom.setAnimation(animation);



            aQuery.id(friend_profile_zoom_imageview).image(Url, true, true, 0, 0, new BitmapAjaxCallback() {
                @Override
                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                    if (status.getCode() == 200) {
                        Bitmap resized = Bitmap.createScaledBitmap(bm, bm.getWidth(), bm.getHeight(), true);
//                    Bitmap conv_bm = getOvalCroppedBitmap(resized);
                        iv.setImageBitmap(resized);


                    }
                }
            });


//        friend_profile_zoom_imageview.setImageDrawable(Url);

        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                friendprofile_relative_image_zoom.clearAnimation();

            }


        });


    }


    public void fadeOut_profile_pic() {

        Animation fadeOut = new AlphaAnimation(1, 0);

        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this

        fadeOut.setStartOffset(0);

        fadeOut.setDuration(500);


        AnimationSet animation = new AnimationSet(false); //change to false

        animation.addAnimation(fadeOut);

        friendprofile_relative_image_zoom.setAnimation(animation);


        animation.setAnimationListener(new Animation.AnimationListener() {


            @Override

            public void onAnimationStart(Animation animation) {


            }


            @Override

            public void onAnimationRepeat(Animation animation) {

            }


            @Override

            public void onAnimationEnd(Animation animation) {


                friendprofile_relative_image_zoom.clearAnimation();

            }


        });


    }


    public static Bitmap getOvalCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                bitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        RectF oval = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawOval(oval, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, oval, paint);

        return output;
    }


    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
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
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }


    private void typeFace_textview() {

        patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");

        patron_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Regular.ttf");

        patron_medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Medium.ttf");

        myfonts_thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Thin.ttf");


        friend_profile_textview_lets_do_something.setTypeface(patron_bold);

        friend_profile_textview_about_me.setTypeface(patron_medium);

        friend_profile_textview_about_me_text.setTypeface(patron_regular);

        friend_profile_textview_things_wanna_do.setTypeface(patron_medium);

        friend_profile_textview_hobbies_interest.setTypeface(patron_medium);

        friend_profile_textview_profile_name.setTypeface(patron_medium);

        textview_friend_profilename.setTypeface(patron_medium);


    }


    @Override

    public void friend_profile_Image_one() {

        viewpagerdots.setVisibility(View.VISIBLE);

    }


    @Override

    public void friend_profile_Image_two() {

        viewpagerdots.setVisibility(View.VISIBLE);


    }

    @Override
    public void friend_profile_Image_three() {

        viewpagerdots.setVisibility(View.VISIBLE);


    }


    public class PagerAdapter extends FragmentPagerAdapter {


        private List<Fragment> fragments;


        public PagerAdapter(FragmentManager supportFragmentManager, List<android.support.v4.app.Fragment> fragments) {

            super(supportFragmentManager);

            this.fragments = fragments;

        }


        @Override

        public Fragment getItem(int position) {

            return this.fragments.get(position);

        }


        @Override

        public int getCount() {

            return this.fragments.size();

        }

    }


    private void initialisePaging() {




        fragments.add(Fragment.instantiate(getActivity(), Friend_Profile_one_fragment.class.getName()));
        this.mPagerAdapter = new PagerAdapter(getChildFragmentManager(), fragments);

        pager.setAdapter(this.mPagerAdapter);

        pager.setOffscreenPageLimit(3);
        dots.clear();
        dotsLayout.removeAllViews();

        addDots();
        if(getActivity()!=null)
        {
            if(!sharedPreferences.getFriendProfilePicture1(getActivity()).equals("")||sharedPreferences.getFriendProfilePicture1(getActivity()).equals("http://mobileapp.dosomethingapp.com//uploads//profile//noimage.png"))
            {
                fragments.add(Fragment.instantiate(getActivity(), Friend_Profile_two_fragment.class.getName()));
                this.mPagerAdapter.notifyDataSetChanged();
                dots.clear();
                dotsLayout.removeAllViews();
                addDots();
            }

            if(!sharedPreferences.getFriendProfilePicture2(getActivity()).equals("")||sharedPreferences.getFriendProfilePicture2(getActivity()).equals("http://mobileapp.dosomethingapp.com//uploads//profile//noimage.png"))
            {
                fragments.add(Fragment.instantiate(getActivity(), Friend_Profile_three_fragment.class.getName()));
                this.mPagerAdapter.notifyDataSetChanged();
                dots.clear();
                dotsLayout.removeAllViews();
                addDots();
            }

        }


    }


    public void addDots() {



        Log.d("num_pages", "______" + NUM_PAGES);

        for (int i = 0; i < fragments.size(); i++) {

            ImageView dot = new ImageView(getActivity());

            if(i==pager.getCurrentItem())
            {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.dot1));
            }else
            {
                dot.setImageDrawable(getResources().getDrawable(R.drawable.dot1_active));
            }


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(

                    LinearLayout.LayoutParams.WRAP_CONTENT,

                    LinearLayout.LayoutParams.WRAP_CONTENT

            );

            params.setMargins(4, 5, 4, 5);

            dotsLayout.addView(dot, params);

            dots.add(dot);

        }


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

        for (int i = 0; i < fragments.size(); i++) {

            System.out.println("dots_pos..." + idx);

            int drawableId = (i == idx) ? (R.drawable.dot1) : (R.drawable.dot1_active);

            Drawable drawable = res.getDrawable(drawableId);

            assert drawable != null;
            drawable.setBounds(0, 0, 0, 0);

            dots.get(i).setImageDrawable(drawable);

        }

    }

    private class GridDosomething extends BaseAdapter {
        ArrayList<Dosomething_Bean> dosomething_beans = new ArrayList<>();
        Activity context;

        public GridDosomething(Activity activity, ArrayList<Dosomething_Bean> dosomething_beans) {

            this.context = activity;
            this.dosomething_beans = dosomething_beans;


        }

        class Holder {

            LinearLayout activity_dosomething_status_layout;

            ImageView grid_layout_imageview_hobbies;

            TextView grid_layout_textview_name;


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
            View grid;
            final Holder holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                grid = new View(context);

                grid = inflater.inflate(R.layout.grid_layout_hobbies, null);

                holder.grid_layout_textview_name = (TextView) grid.findViewById(R.id.grid_layout_textview_name);

                holder.grid_layout_imageview_hobbies = (ImageView) grid.findViewById(R.id.grid_layout_imageview_hobbies);

                holder.grid_layout_textview_name.setText(dosomething_beans.get(position).getImage_name());

                holder.grid_layout_textview_name.setTextSize(8);

//                Log.d("QWERTY", "--------------->>>>>" + hobbies_name.get(position));
//
                Log.d("QWERTYY", "--------------->>>>>" + dosomething_beans.get(position).getImag_Active());

                aQuery.id(holder.grid_layout_imageview_hobbies).image(dosomething_beans.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                    @Override

                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                        if (status.getCode() == 200) {

                            BitmapDrawable bd = new BitmapDrawable(bm);

                            iv.setBackgroundDrawable(bd);

                            holder.grid_layout_textview_name.setTextColor(getResources().getColor(R.color.text_red));

                        }

                    }

                });


                holder.grid_layout_imageview_hobbies.setScaleType(ImageView.ScaleType.CENTER_CROP);

            } else {

                grid = (View) convertView;

            }

            return grid;

        }
    }

    private class CustomGrid extends BaseAdapter {


        Context context;

        ArrayList<String> hobbies_name;

        ArrayList<Integer> hobbies_image;
        ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<>();
        ArrayList<Dosomething_Bean> dosomething_beans = new ArrayList<>();

        public CustomGrid(Activity activity, ArrayList<HobbiesBean> hobbiesBeans) {

            this.context = activity;
            this.hobbiesBeans = hobbiesBeans;


        }


        public CustomGrid(Activity activity, ArrayList<Integer> image, ArrayList<String> name) {

            context = activity;

            hobbies_name = name;

            hobbies_image = image;

        }


        @Override

        public int getCount() {

            return hobbiesBeans.size();

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

            ImageView grid_layout_imageview_hobbies;

            TextView grid_layout_textview_name;


        }

        @Override

        public View getView(int position, View convertView, ViewGroup parent) {

            View grid;
            final Holder holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                grid = new View(context);

                grid = inflater.inflate(R.layout.grid_layout_hobbies, null);

                holder.grid_layout_textview_name = (TextView) grid.findViewById(R.id.grid_layout_textview_name);

                holder.grid_layout_imageview_hobbies = (ImageView) grid.findViewById(R.id.grid_layout_imageview_hobbies);

                holder.grid_layout_textview_name.setText(hobbiesBeans.get(position).getImage_name());

                holder.grid_layout_textview_name.setTextSize(8);

//                Log.d("QWERTY", "--------------->>>>>" + hobbies_name.get(position));
//
                Log.d("QWERTYY", "--------------->>>>>" + hobbiesBeans.get(position).getImag_Active());

                aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbiesBeans.get(position).getImag_Active(), true, true, 0, 0, new BitmapAjaxCallback() {

                    @Override

                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                        if (status.getCode() == 200) {
                            BitmapDrawable bd = new BitmapDrawable(bm);

                            iv.setBackgroundDrawable(bd);

                            holder.grid_layout_textview_name.setTextColor(getResources().getColor(R.color.text_grey));

                        }

                    }

                });


                holder.grid_layout_imageview_hobbies.setScaleType(ImageView.ScaleType.CENTER_CROP);

            } else {

                grid = (View) convertView;

            }

            return grid;

        }

    }


    private class HobbiesGrid extends BaseAdapter {


        Context context;

        ArrayList<String> hobbies_name_grid;

        ArrayList<String> hobbies_image_grid;


        public HobbiesGrid(Activity activity, ArrayList<String> image, ArrayList<String> name) {

            context = activity;

            hobbies_name_grid = name;

            hobbies_image_grid = image;

        }


        @Override

        public int getCount() {

            return hobbies_image_grid.size();

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

            ImageView grid_layout_imageview_hobbies;

            TextView grid_layout_textview_name;


        }

        @Override

        public View getView(int position, View convertView, ViewGroup parent) {

            View grid;
            final Holder holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                grid = new View(context);

                grid = inflater.inflate(R.layout.grid_layout_hobbies, null);

                holder.grid_layout_textview_name = (TextView) grid.findViewById(R.id.grid_layout_textview_name);

                holder.grid_layout_imageview_hobbies = (ImageView) grid.findViewById(R.id.grid_layout_imageview_hobbies);

                holder.grid_layout_textview_name.setText(hobbies_name_grid.get(position));

                holder.grid_layout_textview_name.setTextSize(8);

//                Log.d("QWERTY", "--------------->>>>>" + hobbies_name.get(position));
//
                Log.d("QWERTYY", "--------------->>>>>" + hobbies_name_grid.get(position));

                aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_image_grid.get(position), true, true, 0, 0, new BitmapAjaxCallback() {

                    @Override

                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                        if (status.getCode() == 200) {
                            BitmapDrawable bd = new BitmapDrawable(bm);

                            iv.setBackgroundDrawable(bd);

                            holder.grid_layout_textview_name.setTextColor(getResources().getColor(R.color.text_grey));

                        }

                    }

                });


                holder.grid_layout_imageview_hobbies.setScaleType(ImageView.ScaleType.CENTER_CROP);

            } else {

                grid = (View) convertView;

            }

            return grid;

        }

    }


    public void showImage(String URL) {

        Log.d("FFFF", "GGG" + URL);

        fadeIn_profile_pic(URL);

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private class AsynDataClass extends AsyncTask<Void, Void, Void> {

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

            if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {


                HashMap<String, Object> paramsfb = new HashMap<>();
                paramsfb.put(TAG_SESSIONID, sessionid);

                paramsfb.put(TAG_PROFILEUSERID, profile_user_id);

                json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_getuserdetails), paramsfb);

                Log.v("jason url=======>", sessionid);

                try {

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
                            for (int j = 0; j < hobbiesArray.length(); j++) {
                                JSONObject details = hobbiesArray.getJSONObject(j);
                                int id = details.getInt("hobbies_id");
                                int category_id = details.getInt("category_id");
                                String name = details.getString("name");
                                String Image = details.getString("image");
                                String ActiveImage = details.getString("image_active");
                                hobbiesBeans.add(new HobbiesBean(id, category_id, name, Image, ActiveImage));
                                Log.d("BEAN", "GHGHGH" + hobbiesBeans.get(j).getImag_Active());
                            }
                            JSONArray dosomethingList = userlistobject.getJSONArray("dosomething");

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

                    e.printStackTrace();

                }

            }


            return null;

        }


        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            if (!status.equalsIgnoreCase("")) {
                friend_profile_textview_profile_name.setText(first_name + " " + last_name + "," + age);
                textview_friend_profilename.setText(first_name + " " + last_name + "," + age);
                if (getActivity() != null) {


                    if (gender.equalsIgnoreCase("Male")) {
                        profile_page_imageview_gender.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.male));
                        textview_friend_gender.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.male));
                    } else if (gender.equalsIgnoreCase("Female")) {
                        profile_page_imageview_gender.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.female));
                        textview_friend_gender.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.female));

                    }

                    sharedPreferences.setFriendProfilePicture(getActivity(), image1);
                    sharedPreferences.setFriendProfilePicture1(getActivity(), image2);
                    sharedPreferences.setFriendProfilePicture2(getActivity(), image3);
                    if (send_request.equals("Yes")) {
                        friend_profile_textview_lets_do_something.setTextColor(getResources().getColor(R.color.white));
                        friend_profile_textview_lets_do_something.setText(getResources().getString(R.string.friend_profile_textview_lets_do_requestsend));
                        friend_profile_textview_lets_do_something.setBackgroundColor(getResources().getColor(R.color.text_grey));
                    }


                    if (send_request.equals("No")) {
                        friend_profile_textview_lets_do_something.setOnClickListener(new View.OnClickListener() {

                            @Override

                            public void onClick(View v) {
                                ((MyApplication) getActivity().getApplication()).getListFilterBeans().clear();
                                profile_user_id = sharedPreferences.getFriendUserId(getActivity());
                                sessionid = sharedPreferences.getSessionid(getActivity());
                                new SendRequest().execute();

                            }

                        });
                    }
                    friend_profile_textview_about_me_text.setText(about);
                    GridDosomething gridDosomething = new GridDosomething(getActivity(), dosomething_beans);
                    friends_profile_gridview_wanna_do.setAdapter(gridDosomething);
                    friends_profile_gridview_wanna_do.setExpanded(true);
                    CustomGrid customGrid = new CustomGrid(getActivity(), hobbiesBeans);
                    friends_profile_gridview_hobbies.setAdapter(customGrid);
                    friends_profile_gridview_hobbies.setExpanded(true);
                    nearme_profile_mainlayout.setVisibility(View.VISIBLE);
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
        }


    }


    private class SendRequest extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_bar.show();
            timer = new Timer();
            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation.start();
        }

        @Override
        protected Void doInBackground(Void... params) {
            if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {

                HashMap<String, Object> paramsfb = new HashMap<>();
                paramsfb.put(TAG_SESSIONID, sessionid);
                paramsfb.put(TAG_REQUESTSEND_USERID, profile_user_id);
                String urlrequestsend = "http://wiztestinghost.com/dosomething/sendrequest?";
                json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_sendrequest), paramsfb);
                Log.v("jason url=======>", String.valueOf(paramsfb));
                try {
                    json_object = new JSONObject(json_string);
                    json_content = json_object.getJSONObject("sendrequest");

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            {
                try {
                    if (json_object.has("sendrequest")) {
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
                            progress_bar.dismiss();
                            splashAnimation.stop();
                            if(json_content.getString("Message").equalsIgnoreCase("Request send successfully"))
                            {

                                friend_profile_textview_lets_do_something.setTextColor(getResources().getColor(R.color.white));

                                friend_profile_textview_lets_do_something.setText(getResources().getString(R.string.friend_profile_textview_lets_do_requestsend));

                                friend_profile_textview_lets_do_something.setBackgroundColor(getResources().getColor(R.color.text_grey));
                            }else if(json_content.getString("Message").equalsIgnoreCase("Request Canceled successfully"))
                            {
                                friend_profile_textview_lets_do_something.setTextColor(getResources().getColor(R.color.white));

                                friend_profile_textview_lets_do_something.setText(getResources().getString(R.string.friend_profile_textview_lets_do_something));

                                friend_profile_textview_lets_do_something.setBackgroundColor(getResources().getColor(R.color.red));
                            }



                        } else if (json_content.getString("status").equalsIgnoreCase("failed")) {


                            progress_bar.dismiss();
                            splashAnimation.stop();

                        } else if (json_content.getString("status").equalsIgnoreCase("error")) {

                            progress_bar.dismiss();
                            splashAnimation.stop();

                        }
                    }
                } catch (Exception e) {
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
