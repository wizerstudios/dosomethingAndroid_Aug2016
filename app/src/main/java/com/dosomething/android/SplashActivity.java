package com.dosomething.android;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Splash Screen
// Madhan
public class SplashActivity extends AppCompatActivity {


    private static final int NUM_PAGES = 5;
    SectionsPagerAdapter mSectionsPagerAdapter;
    Jsonfunctions jsonfunctions;
    JSONObject json_object, json_content, details;

    String json_string;
    ViewPager mViewPager;
    Button activitymain_button_createaccount;

    Button activitymain_button_login;

    private List<ImageView> dotimages;
    Timer timer;
    Timer blink_time;

    Intent intent;

    SharedPrefrences sharedPrefrences;

    Context context = this;

    String android_device_id;

    ArrayList<String> dummy_hobbies_name;

    ArrayList<Integer> dummy_hobbies_image;

    Typeface patron_bold;
RelativeLayout layout_walkthrough_account_create;
    ImageView walkthrough_account_create_imageView;
    TextView walkthrough_account_create_TextView;
    private AnimationDrawable splashAnimation;
    private Tracker mTracker;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        try
        {
            MyApplication application = (MyApplication) getApplication();
            mTracker = application.getDefaultTracker();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

//        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        intent = new Intent(this, MyService.class);
        startService(intent);

        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.setStatusBarColor(getResources().getColor(R.color.red));

        }

//////////////////////NETWORk CHECK///////////////////////////////////////
        NetworkCheck.statusCheck(context);

        if (!isNetworkAvailable() && !isWifiAvailable()) {

            NetworkCheck.alertdialog(context);

        }

///////////////////////////////////////////////////////////////////////////

        assign_layoutComponent();
        ////////////////////Typeface method///////////////////////////////////////////////////
        text_font_typeface();

        if (!sharedPrefrences.getLogin(context).equals("")) {

            Intent intent = new Intent(this, DoSomethingStatus.class);
            startActivity(intent);
            finish();
        }



        // Create the adapter that will return a fragment for each of the three

        // primary sections of the activity.


//        GCMRegistrar.checkDevice(context);
//        GCMRegistrar.checkManifest(context);
//        String regId = GCMRegistrar.getRegistrationId(context);

        getdeviceregistrationid();

//        android_device_id = Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
//        Log.v("regId", "regId");
//        Log.v("Device ID => ", android_device_id);
//        Log.v("regId", regId);
//

        new VersionData().execute();
        ///////////////////////Hobbies Bundle Arraylist//////////////////////////////
        dummy_hobbies_name = new ArrayList<>();
        dummy_hobbies_image = new ArrayList<>();
        dummy_hobbies_name.add("");
        dummy_hobbies_image.add(R.drawable.pluis_icon);
        ///////////////////////////////////////////////////////////////////////////////////


/////////////////////Splash Screen//////////////////////////////////////////////////////

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        timer = new Timer();

        mViewPager.setAdapter(mSectionsPagerAdapter);

//        mViewPager.setScrollDurationFactor(5);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mViewPager.setScrollBarFadeDuration(5);
        }
        mViewPager.setPageMargin(NUM_PAGES);

        mViewPager.setClipChildren(false);

//        mViewPager.setActivity(this);

        mViewPager.onHoverChanged(true);

        mViewPager.setOffscreenPageLimit(5);

        mViewPager.setPageTransformer(false, new FadePageTransformer());

        add_dots();

        selectDot(mViewPager.getCurrentItem());
///////////////////////////////////////////////////////////////////////////////////////////////


        //////////////////////////Default Viewpager Animation//////////////////////////////////

//        final Handler handler = new Handler();

//

//        final Runnable Update = new Runnable() {

//            public void run() {

//                if (currentPage == NUM_PAGES) {

//                    currentPage = 0;

//                }

//                mViewPager.setCurrentItem(currentPage++, true);

//            }

//        };

//

//        swipeTimer = new Timer();

//        swipeTimer.schedule(new TimerTask() {

//

//            @Override

//            public void run() {handler.post(Update);}}, 3000, 2000);




        //////////////default pageIndicator//////////////////////////

//        final float density = getResources().getDisplayMetrics().density;

//        activitycircle_viewindicator_circle.setRadius(3 * density);

//        activitycircle_viewindicator_circle.setFillColor(getResources().getColor(android.R.color.holo_red_dark));

//        activitycircle_viewindicator_circle.setStrokeColor(getResources().getColor(android.R.color.holo_red_dark));

//        activitycircle_viewindicator_circle.setStrokeWidth(1 * density);

//        activitycircle_viewindicator_circle.setViewPager(mViewPager);

        //////////////////////////////////////Click Functions////////////////////////////////////////

        walkthrough_account_create_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_account_create.setVisibility(View.GONE);
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation.stop();
                sharedPrefrences.setWalkThrough_Splash(context, "true");
                Intent i = new Intent(SplashActivity.this, DoSomeThingCreateAccount.class);
                startActivity(i);
            }
        });


        layout_walkthrough_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout_walkthrough_account_create.setVisibility(View.GONE);
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation.stop();
                sharedPrefrences.setWalkThrough_Splash(context,"true");
            }
        });
        activitymain_button_login.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {


                if (!sharedPrefrences.getLogin(context).equals("")) {

                    Intent i = new Intent(SplashActivity.this, DoSomethingStatus.class);

                    startActivity(i);

                } else {

                    Intent i = new Intent(SplashActivity.this, DoSomeThingLogin.class);

                    startActivity(i);

                }


            }

        });


        activitymain_button_createaccount.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                Intent i = new Intent(SplashActivity.this, DoSomeThingCreateAccount.class);

                startActivity(i);



            }

        });

    }

    private void getdeviceregistrationid() {
        try {
            //push notification code
            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
            // Showing status
            if(status== ConnectionResult.SUCCESS){
                try {
                    Log.d("GCM REGISTER => ","success");
                    // setting push
                    GCMRegistrar.checkDevice(SplashActivity.this);
                    String regId = GCMRegistrar.getRegistrationId(SplashActivity.this);
                    Log.d("GCM REGISTER => ",regId);
                    if (regId.equals("")) {
                        GCMRegistrar.register(SplashActivity.this, "27024640493");
                    } else {
                        Log.v("Token ID 2 => ", regId);
                        sharedPrefrences.setDeviceToken(context, regId);
                    }
                } catch (Exception e ){
                    e.printStackTrace();
                    Log.v ("GCM registration", "Exception caught");
                }

            }
            else{
                Log.d("GCM REGISTER => ", "failed");
                //Toast.makeText(Splash.this, "Google Play Services are not available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //////////////////////////assign method/////////////////////////////////////////////////////////

    public void assign_layoutComponent() {
        sharedPrefrences = new SharedPrefrences();
        jsonfunctions = new Jsonfunctions(this);
        mViewPager = (ViewPager) findViewById(R.id.pager1);

        activitymain_button_login = (Button) findViewById(R.id.activitymain_button_login);

        activitymain_button_createaccount = (Button) findViewById(R.id.activitymain_button_createaccount);
        layout_walkthrough_account_create=(RelativeLayout)findViewById(R.id.layout_walkthrough_account_create);
        walkthrough_account_create_imageView=(ImageView)findViewById(R.id.walkthrough_account_create_imageView);
        walkthrough_account_create_TextView=(TextView)findViewById(R.id.walkthrough_account_create_TextView);
        walkthrough_account_create_imageView.setBackgroundResource(R.drawable.blink_icon);
        splashAnimation = (AnimationDrawable) walkthrough_account_create_imageView.getBackground();

    }


    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }


    private boolean isWifiAvailable() {

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return mWifi != null && mWifi.isConnected();

    }


    ///////////////////////////adding dot for viewpager/////////////////////////////////////////////////

    private void add_dots() {

        dotimages = new ArrayList<>();

        android.widget.LinearLayout indicatorlayout = (android.widget.LinearLayout) findViewById(R.id.linearlayoutsplit_dots);

        for (int i = 0; i < NUM_PAGES; i++) {

            ImageView dots = new ImageView(this);

            dots.setImageDrawable(getResources().getDrawable(R.drawable.dot));

            android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

            params.setMargins(0, 0, 0, 30);

            indicatorlayout.addView(dots, params);

            dotimages.add(dots);

        }


        ///////////////////////Viewpager page Change listener///////////////////////////////////////

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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


    ///////////////////////viewpager selection dot method//////////////////////////////////////////

    private void selectDot(int idx) {

        Resources res = getResources();

        for (int i = 0; i < NUM_PAGES; i++) {

            int drawableId = (i == idx) ? (R.drawable.dot_active) : (R.drawable.dot);

            Drawable drawable = res.getDrawable(drawableId);

            dotimages.get(i).setImageDrawable(drawable);

        }

    }

    ///////////To assign font typeface///////////////


    private void text_font_typeface() {

        patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");

        Typeface patron_regular = Typeface.createFromAsset(getAssets(), "fonts/Patron-Regular.ttf");

        Typeface patron_medium = Typeface.createFromAsset(getAssets(), "fonts/Patron-Medium.ttf");

        Typeface myfonts_thin = Typeface.createFromAsset(getAssets(), "fonts/Patron-Thin.ttf");

        activitymain_button_login.setTypeface(patron_bold);

        activitymain_button_createaccount.setTypeface(patron_bold);
        walkthrough_account_create_TextView.setTypeface(patron_regular);


    }


    public class FadePageTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {

            view.setTranslationX(view.getWidth() * -position);


            if (position <= -1.0F || position >= 1.0F) {

                view.setAlpha(0.0F);

            } else if (position == 0.0F) {

                view.setAlpha(1.0F);

            } else {

                view.setAlpha(1.0F - Math.abs(position));

                view.setBackgroundColor(getResources().getColor(R.color.text_grey));

            }

        }

    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

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


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * <p/>
     * one of the sections/tabs/pages.
     */


    ///////////////////////////pagerAdapter method///////////////////////////////////////////////

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        private int[] pageIDsArray;

        private int count;


        public SectionsPagerAdapter(FragmentManager fragmentManager) {

            super(fragmentManager);


        }


        @Override

        public Fragment getItem(int position) {


            // Open FragmentTab1.java

            switch (position) {


                // Open FragmentTab1.java

                case 0:

                    return new PlaceholderFragment();

                // Open FragmentTab2.java

                case 1:

                    return new Splash2();

                case 2:

                    return new Splash3();

                case 3:

                    return new Splash4();

                case 4:

                    return new Splash5();


            }


            return null;
        }


        @Override

        public int getCount() {

            // Show 5 total pages.

            return 5;

        }


        @Override

        public CharSequence getPageTitle(int position) {

            switch (position) {

                case 0:

                    return "SECTION 1";

                case 1:

                    return "SECTION 2";

                case 2:

                    return "SECTION 3";

                case 3:

                    return "SECTION 4";

                case 4:

                    return "SECTION 5";


            }

            return null;

        }

    }


    /**
     * A placeholder fragment containing a simple view.
     */

    /////////////////////////Splash page1 fragment/////////////////////////////////////////////////

    public static class PlaceholderFragment extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * <p/>
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";


        /**
         * Returns a new instance of this fragment for the given section
         * <p/>
         * number.
         */

        public static PlaceholderFragment newInstance(int sectionNumber) {

            PlaceholderFragment fragment = new PlaceholderFragment();

            Bundle args = new Bundle();

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);

            return fragment;

        }


        public PlaceholderFragment() {

        }


        @Override

        public View onCreateView(LayoutInflater inflater, ViewGroup container,

                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            KenBurnsView imageView = (KenBurnsView) rootView.findViewById(R.id.section_label);

            TextView font_string_dosomething = (TextView) rootView.findViewById(R.id.font_string_dosomething);

            Typeface patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");

            font_string_dosomething.setTypeface(patron_bold);

            return rootView;

        }

    }


    /////////////////////////splash screen 2 fragment/////////////////////////////////////////////

    public static class Splash2 extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * <p/>
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number2";


        /**
         * Returns a new instance of this fragment for the given section
         * <p/>
         * number.
         */

        public static Splash2 newInstance(int sectionNumber) {

            Splash2 fragment = new Splash2();

            Bundle args = new Bundle();

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);

            return fragment;

        }


        public Splash2() {

        }


        @Override

        public View onCreateView(LayoutInflater inflater, ViewGroup container,

                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.splash2, container, false);

            KenBurnsView imageView = (KenBurnsView) rootView.findViewById(R.id.section_label2);

            TextView font_splash2_main = (TextView) rootView.findViewById(R.id.font_splash2_main);

            TextView font_splash2_subtitle = (TextView) rootView.findViewById(R.id.font_splash2_subtitle);

            Typeface patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");

            font_splash2_main.setTypeface(patron_bold);

            font_splash2_subtitle.setTypeface(patron_bold);


            return rootView;

        }

    }


    /////////////////////////splash screen 3 fragment/////////////////////////////////////////////

    public static class Splash3 extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * <p/>
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number2";


        /**
         * Returns a new instance of this fragment for the given section
         * <p/>
         * number.
         */

        public static Splash3 newInstance(int sectionNumber) {

            Splash3 fragment = new Splash3();

            Bundle args = new Bundle();

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);

            return fragment;

        }


        public Splash3() {

        }


        @Override

        public View onCreateView(LayoutInflater inflater, ViewGroup container,

                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.splash3, container, false);

            KenBurnsView imageView = (KenBurnsView) rootView.findViewById(R.id.section_label3);

            TextView font_splash3_main = (TextView) rootView.findViewById(R.id.font_splash3_main);

            TextView font_splash3_subtitle = (TextView) rootView.findViewById(R.id.font_splash3_subtitle);

            Typeface patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");

            font_splash3_main.setTypeface(patron_bold);

            font_splash3_subtitle.setTypeface(patron_bold);

            return rootView;

        }

    }


    /////////////////////////splash screen 4 fragment/////////////////////////////////////////////

    public static class Splash4 extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * <p/>
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number2";


        /**
         * Returns a new instance of this fragment for the given section
         * <p/>
         * number.
         */

        public static Splash4 newInstance(int sectionNumber) {

            Splash4 fragment = new Splash4();

            Bundle args = new Bundle();

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);

            return fragment;

        }


        public Splash4() {

        }


        @Override

        public View onCreateView(LayoutInflater inflater, ViewGroup container,

                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.splash4, container, false);

            KenBurnsView imageView = (KenBurnsView) rootView.findViewById(R.id.section_label4);

            TextView font_splash4_main = (TextView) rootView.findViewById(R.id.font_splash4_main);

            TextView font_splash4_subtitle = (TextView) rootView.findViewById(R.id.font_splash4_subtitle);

            Typeface patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");

            font_splash4_main.setTypeface(patron_bold);

            font_splash4_subtitle.setTypeface(patron_bold);

            return rootView;

        }

    }


    /////////////////////////splash screen 5 fragment/////////////////////////////////////////////

    public static class Splash5 extends Fragment {

        /**
         * The fragment argument representing the section number for this
         * <p/>
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number2";


        /**
         * Returns a new instance of this fragment for the given section
         * <p/>
         * number.
         */

        public static Splash5 newInstance(int sectionNumber) {

            Splash5 fragment = new Splash5();

            Bundle args = new Bundle();

            args.putInt(ARG_SECTION_NUMBER, sectionNumber);

            fragment.setArguments(args);

            return fragment;

        }


        public Splash5() {

        }


        @Override

        public View onCreateView(LayoutInflater inflater, ViewGroup container,

                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.splash5, container, false);

            KenBurnsView imageView = (KenBurnsView) rootView.findViewById(R.id.section_label5);

            TextView font_splash5_main = (TextView) rootView.findViewById(R.id.font_splash5_main);

            TextView font_splash5_subtitle = (TextView) rootView.findViewById(R.id.font_splash5_subtitle);

            Typeface patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");

            font_splash5_main.setTypeface(patron_bold);

            font_splash5_subtitle.setTypeface(patron_bold);


            return rootView;

        }

    }


    //////////////////////////////Onbachpress for Splash screen/////////////////////////////////////

    @Override

    public void onBackPressed() {

        super.onBackPressed();

        Intent i = new Intent(Intent.ACTION_MAIN);

        i.addCategory(Intent.CATEGORY_HOME);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(i);


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







    ///////////////////////////Auto slider animation Splash////////////////////////////////////////////

    class AutoSlider extends TimerTask {

        @Override

        public void run() {

            runOnUiThread(new Runnable() {

                public void run() {

//                    mViewPager.setScrollDurationFactor(5);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mViewPager.setScrollBarFadeDuration(5);
                    }

                    if (mViewPager.getCurrentItem() + 1 == mSectionsPagerAdapter.getCount()) {

                        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                        mViewPager.setAdapter(mSectionsPagerAdapter);

                    } else {

                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);

                    }

//                    mViewPager.setScrollDurationFactor(5);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mViewPager.setScrollBarFadeDuration(5);
                    }

                    selectDot(mViewPager.getCurrentItem());


                }

            });

        }

    }


    /////////////////////////animation stop function//////////////////////////////////////////////

    void stopTimer() {

        if (timer != null) {

            timer.cancel();


            timer = null;

        }

    }


    @Override

    protected void onPause() {

        super.onPause();

        stopTimer();

    }


    @Override

    protected void onResume() {

        super.onResume();
        if(sharedPrefrences.getWalkThrough_Splash(context).equals("false"))
        {
            layout_walkthrough_account_create.setVisibility(View.GONE);
            blink_time = new Timer();
            blink_time.schedule(new Blink_progress(), 0, 340);
            splashAnimation.start();
            sharedPrefrences.setWalkThrough_Splash(context, "true");

        }
        if (!sharedPrefrences.getLogin(context).equals("")) {

            Intent intent = new Intent(this, DoSomethingStatus.class);
            startActivity(intent);
            finish();
        }
        timer = new Timer();

        timer.scheduleAtFixedRate(new AutoSlider(), 3000, 8000);

    }


    @Override

    protected void onStop() {

        super.onStop();

        stopTimer();

    }


    public class VersionData extends AsyncTask<Void, Void, Void> {

        protected void onPreExecute() {

            super.onPreExecute();

            if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {


            }

        }


        @Override

        protected Void doInBackground(Void... params) {

//        HashMap<String, String> paramsVersion = new HashMap<>();

//                }

//            } catch (JSONException e) {

//                e.printStackTrace();

//            }
            if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {


                try {

                    json_string = jsonfunctions.getJSONfromURL(getString(R.string.dosomething_apilink_string_apidetails));

                    json_object = new JSONObject(json_string);

                    json_content = json_object.getJSONObject("apidetails");

                    details = json_content.getJSONObject("path");

                } catch (JSONException e) {

                    e.printStackTrace();

                }
            }

            Log.d("DDDD", "GGGG1");

            return null;

        }


        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);

//            new StatusBackground().execute();

//                Toast.makeText(getApplicationContext(), "LOADING", Toast.LENGTH_LONG).show();


            if (NetworkCheck.isNetworkAvailable(context) || NetworkCheck.isWifiAvailable(context)) {

                try {

                    if (json_content.has("path")) {

                        if (details.getString("live").equalsIgnoreCase("http://wiztestinghost.com/dosomething/")) {

//                            Toast.makeText(getApplicationContext(), "Version match", Toast.LENGTH_SHORT).show();

                            JSONObject api = json_content.getJSONObject("Api");

                            String dosomethinglist = api.getString("dosomethinglist");

                            Log.d("EEEEEE", "dosomethinglist" + dosomethinglist);

//                                JSONObject version = json_content.getJSONObject("Api");

                            JSONObject version = json_content.getJSONObject("version");

                            sharedPrefrences.setVersion_hobbies(context, version.getString("gethobbies"));

                            sharedPrefrences.setVersion_dosomething(context, version.getString("gethobbies"));

                            Log.d("VERSION", "DETAIL" + sharedPrefrences.getVersion_dosomething(context) + "--" + sharedPrefrences.getVersion_hobbies(context));

//                        new StatusBackground().execute();

                            if (version.getString("gethobbies").equalsIgnoreCase("1.0")) {


//                            new StatusBackground().execute();

//                            Toast.makeText(getActivity(), "Version match", Toast.LENGTH_SHORT).show();

                            } else {

                                Toast.makeText(getApplicationContext(), "Version mismatch", Toast.LENGTH_SHORT).show();

                            }

                            String gethobbies_version = version.getString("gethobbies");

                            String dosomethinglist_version = version.getString("dosomethinglist");

                            Log.d("EEEEEE", "dosomethinglist++++gethobbies_LIVE" + gethobbies_version + "--" + dosomethinglist_version);

                        } else if (details.getString("demo").equalsIgnoreCase("http://wiztestinghost.com/dosomething/")) {

                            Log.d("DDDD", "GGGG2");

                            JSONObject version = json_content.getJSONObject("version");

                            Log.d("DDDD", "GGGG3");

                            sharedPrefrences.setVersion_dosomething(context, version.getString("gethobbies"));

                            sharedPrefrences.setVersion_hobbies(context, version.getString("gethobbies"));

                            Log.d("VERSION_HHH", "DETAIL" + sharedPrefrences.getVersion_dosomething(context) + "--" + sharedPrefrences.getVersion_hobbies(context));

                            if (version.getString("gethobbies").equalsIgnoreCase("1.0")) {

//                            Toast.makeText(getActivity(), "Version match", Toast.LENGTH_SHORT).show();

//                            new StatusBackground().execute();


                            } else {

                                Toast.makeText(getApplicationContext(), "Version mismatch", Toast.LENGTH_SHORT).show();

                            }

                            String gethobbies_version = version.getString("gethobbies");

                            String dosomethinglist_version = version.getString("dosomethinglist");

                            Log.d("DDDD", "GGGG4");

                            Log.d("EEEEEE", "dosomethinglist++++gethobbies_DEMO" + gethobbies_version + "--" + dosomethinglist_version);

                        }

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }
        }

    }
}






