package com.dosomething.android;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.dosomething.android.Beanclasses.Arts_hobbies;
import com.dosomething.android.Beanclasses.Food_hobbies;
import com.dosomething.android.Beanclasses.Pets_hobbies;
import com.dosomething.android.Beanclasses.Recreation_hobbies;
import com.dosomething.android.CommonClasses.ExpandableHeightGridView;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.dosomething.android.Database.DBAdapter;
import com.dosomething.android.Fragments.FragmentProfile;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class DoSomethingHobbies extends AppCompatActivity {
    ExpandableHeightGridView
            hobbies_page_gridview_hobbies_arts,
            hobbies_page_gridview_hobbies_food,
            hobbies_page_gridview_hobbies_pets,
            hobbies_page_gridview_hobbies_recreation;

    Toolbar toolbar;
    Jsonfunctions jsonfunctions;

    Typeface patron_bold;
    int count = 1;
    AQuery aQuery;
    private ProgressDialog pDialog;

    ArrayList<Integer> hobbies_list_arts;
    ArrayList<Integer> hobbies_list_arts_id;
    ArrayList<String> hobbies_list_arts_name;
    ArrayList<String> hobbies_list_arts_image;
    ArrayList<Integer> hobbies_list_food;
    ArrayList<Integer> hobbies_list_food_id;
    ArrayList<String> hobbies_list_food_name;
    ArrayList<String> hobbies_list_food_image;
    ArrayList<Integer> hobbies_list_pets;
    ArrayList<Integer> hobbies_list_pets_id;
    ArrayList<String> hobbies_list_pets_name;
    ArrayList<String> hobbies_list_pets_image;
    ArrayList<Integer> hobbies_list_recreation;
    ArrayList<Integer> hobbies_list_recreation_id;
    ArrayList<String> hobbies_list_recreation_name;
    ArrayList<String> hobbies_list_recreation_image;

    private static String urlImageStatus = "http://wiztestinghost.com/dosomething/gethobbies?";
    String type, sessionid, response;
    int selectedposition;
    SharedPrefrences sharedPrefrences;
    Context context = this;
    ArrayList<Arts_hobbies> hobbies_array_arts;
    ArrayList<Boolean> hobbies_image_arts_state;
    ArrayList<Food_hobbies> hobbies_array_food;
    ArrayList<Boolean> hobbies_image_food_state;
    ArrayList<Pets_hobbies> hobbies_array_pets;
    ArrayList<Boolean> hobbies_image_pets_state;
    ArrayList<Recreation_hobbies> hobbies_array_recreation;
    ArrayList<Boolean> hobbies_image_recreation_state;
    private boolean[] thumbnailsselection;
    ArrayList<Integer> bundle_list;
    ArrayList<String> hobbies_name;
    ArrayList<String> hobbies_image;
    boolean set_arts = false;
    Bundle value, bundle;
    ArrayList<String> dummy_hobbies_name;
    ArrayList<Integer> dummy_hobbies_image;
    ArrayList<Integer> hobbies_list;
    TextView hobbies_toolbar_textview_save;
    private TransparentProgressDialog pd;
    private DBAdapter dbAdapter;

    Handler handler = new Handler();
    Timer timer = new Timer();
    private AnimationDrawable splashAnimation;
    private AnimationDrawable splashAnimation_walkthrough_hobbies;
    private ImageView kbv;
    private Dialog progress_bar;
    private ImageView progress_bar_imageview;
    private ImageView image_walkthrough_account_profile;
    RelativeLayout layout_walkthrough_profile;
    ImageView walkthrough_hobbies_ImageView;
TextView walkthrough_hobbies_TextView;
    private Timer blink_time;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobbies_page);
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
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.dosomething_navigation_icon);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        dbAdapter = new DBAdapter(context);
        hobbies_array_arts = new ArrayList<Arts_hobbies>();
        hobbies_image_arts_state = new ArrayList<>();
        hobbies_array_food = new ArrayList<Food_hobbies>();
        hobbies_image_food_state = new ArrayList<>();
        hobbies_array_pets = new ArrayList<Pets_hobbies>();
        hobbies_image_pets_state = new ArrayList<>();
        hobbies_array_recreation = new ArrayList<Recreation_hobbies>();
        hobbies_image_recreation_state = new ArrayList<>();
        bundle_list = new ArrayList<Integer>();
        hobbies_list = new ArrayList<Integer>();
//        kbv = (ImageView) findViewById(R.id.image);
//        kbv.setBackgroundResource(R.drawable.progress_drawable);
//        splashAnimation = (AnimationDrawable) kbv.getBackground();
        progress_bar = new Dialog(DoSomethingHobbies.this);
        progress_bar.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progress_bar.setContentView(R.layout.progress_bar);
        progress_bar_imageview = (ImageView) progress_bar.findViewById(R.id.progress_bar_imageview);
        progress_bar_imageview.setBackgroundResource(R.drawable.progress_drawable);
        splashAnimation = (AnimationDrawable) progress_bar_imageview.getBackground();
        progress_bar.setCancelable(false);
        layout_walkthrough_profile = (RelativeLayout) findViewById(R.id.layout_walkthrough_profile);
        walkthrough_hobbies_ImageView=(ImageView)findViewById(R.id.walkthrough_hobbies_ImageView);
        walkthrough_hobbies_TextView=(TextView)findViewById(R.id.walkthrough_hobbies_TextView);
        image_walkthrough_account_profile = (ImageView) findViewById(R.id.image_walkthrough_account_profile);
        hobbies_page_gridview_hobbies_arts = (ExpandableHeightGridView) findViewById(R.id.hobbies_page_gridview_hobbies_arts);
        hobbies_page_gridview_hobbies_food = (ExpandableHeightGridView) findViewById(R.id.hobbies_page_gridview_hobbies_food);
        hobbies_page_gridview_hobbies_pets = (ExpandableHeightGridView) findViewById(R.id.hobbies_page_gridview_hobbies_pets);

        walkthrough_hobbies_ImageView.setBackgroundResource(R.drawable.blink_icon);
        splashAnimation_walkthrough_hobbies = (AnimationDrawable) walkthrough_hobbies_ImageView.getBackground();
        pd = new TransparentProgressDialog(context, getResources().getDrawable(R.drawable.loading));

        hobbies_page_gridview_hobbies_recreation = (ExpandableHeightGridView) findViewById(R.id.hobbies_page_gridview_hobbies_recreation);
        aQuery = new AQuery(DoSomethingHobbies.this);
        hobbies_toolbar_textview_save = (TextView) findViewById(R.id.custom_toolbar_textview_save);
        hobbies_toolbar_textview_save.setText(R.string.custom_toolbar_textview_save);
        hobbies_name = new ArrayList<>();
        hobbies_image = new ArrayList<String>();
        text_font_typeface();
        value = new Bundle();
        value = getIntent().getExtras();
        Log.d("GGGGG", "OOOO" + value.getIntegerArrayList("array_bundle_hobbies_image"));
        hobbies_list.addAll(value.getIntegerArrayList("array_bundle_hobbies_image"));

        dummy_hobbies_name = new ArrayList<>();
        dummy_hobbies_name.add("");
        dummy_hobbies_image = new ArrayList<Integer>();
        dummy_hobbies_image.add(R.drawable.pluis_icon);
        Log.d("KKKKK", "LLLL" + hobbies_name);
//        hobbies_image.addAll(value.getIntegerArrayList("array_bundle_hobbies_image"));

        sharedPrefrences = new SharedPrefrences();
        jsonfunctions = new Jsonfunctions(DoSomethingHobbies.this);
        sessionid = sharedPrefrences.getSessionid(DoSomethingHobbies.this);


        if (sharedPrefrences.getWalkThroughhobbies(context).equals("false")) {
            layout_walkthrough_profile.setVisibility(View.VISIBLE);
            blink_time = new Timer();
            blink_time.schedule(new Blink_progress(), 0, 340);
            splashAnimation_walkthrough_hobbies.start();
            sharedPrefrences.setWalkThroughHobbies(context,"true");
        }
        layout_walkthrough_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_profile.setVisibility(View.GONE);
                sharedPrefrences.setWalkThroughHobbies(context,"true");
            }
        });

        walkthrough_hobbies_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layout_walkthrough_profile.setVisibility(View.GONE);
                sharedPrefrences.setWalkThroughHobbies(context,"true");
                bundle_list.clear();


                for (int i = 0; i < ((MyApplication) getApplication()).getArts_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getArts_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getArts_hobbies().get(i).getImage_id_arts());
                    }
                }


                for (int i = 0; i < ((MyApplication) getApplication()).getFood_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getFood_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getFood_hobbies().get(i).getImage_id_food());
                    }
                }


                for (int i = 0; i < ((MyApplication) getApplication()).getPets_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getPets_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getPets_hobbies().get(i).getImage_id_pets());
                    }
                }


                for (int i = 0; i < ((MyApplication) getApplication()).getRecreation_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getRecreation_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getRecreation_hobbies().get(i).getImage_id_recreation());
                    }
                }
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation_walkthrough_hobbies.stop();

//                bundle_list.addAll(hobbies_list_arts);
//                bundle_list.addAll(hobbies_list_food);
//                bundle_list.addAll(hobbies_list_pets);
//                bundle_list.addAll(hobbies_list_recreation);
                Log.d("AAAA", "LLLL" + bundle_list);
                if (!sharedPrefrences.getHobbies(context).equalsIgnoreCase("Yes")) {
                    Intent i = new Intent(DoSomethingHobbies.this, DoSomethingprofile.class);
                    if (bundle_list.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle_list.clear();
                        bundle_list.add(R.drawable.pluis_icon);
                        Log.d("bundle_list", "H" + bundle_list);

                        bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    } else {
                        value = new Bundle();
//                value.putStringArrayList("array_bundle_hobbies_name", hobbies_array);
                        value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);

                        Log.d("DOSOMETHINGARRAyBUNDLE", "............" + value);
//                i.putStringArrayListExtra("array_bundle_hobbies_name", hobbies_array);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    }
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(DoSomethingHobbies.this, DoSomethingStatus.class);
                    FragmentProfile fragmentProfile = new FragmentProfile();

                    if (bundle_list.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle_list.clear();
                        bundle_list.add(R.drawable.pluis_icon);
                        Log.d("FFFFFFFFFFFFFFFFFFFFFFF", "H" + bundle_list);
                        Log.d("bundle_list", "string" + String.valueOf(bundle_list));
                        bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    } else {
                        value = new Bundle();
//                value.putStringArrayList("array_bundle_hobbies_name", hobbies_array);
                        value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        Log.d("ARRAYYYYBUNDLE", "............" + value);
                        Log.d("bundle_list", "withoutemptystring" + String.valueOf(bundle_list));
//                i.putStringArrayListExtra("array_bundle_hobbies_name", hobbies_array);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    }
                    startActivity(i);
                    finish();

                }
            }
        });
        hobbies_list_arts = new ArrayList<Integer>();
        hobbies_list_food = new ArrayList<Integer>();
        hobbies_list_pets = new ArrayList<Integer>();
        hobbies_list_recreation = new ArrayList<Integer>();

        hobbies_list_arts_id = new ArrayList<Integer>();
        hobbies_list_food_id = new ArrayList<Integer>();
        hobbies_list_pets_id = new ArrayList<Integer>();
        hobbies_list_recreation_id = new ArrayList<Integer>();

        hobbies_list_arts_name = new ArrayList<String>();
        hobbies_list_food_name = new ArrayList<String>();
        hobbies_list_pets_name = new ArrayList<String>();
        hobbies_list_recreation_name = new ArrayList<String>();

        hobbies_list_arts_image = new ArrayList<String>();
        hobbies_list_food_image = new ArrayList<String>();
        hobbies_list_pets_image = new ArrayList<String>();
        hobbies_list_recreation_image = new ArrayList<String>();

    /*    new Goodsin_Background().execute();*/

        if (((MyApplication) getApplication()).getArts_hobbies().size() > 1) {
            final CustomGrid_arts customGrid_arts = new CustomGrid_arts(DoSomethingHobbies.this, ((MyApplication) getApplication()).getArts_hobbies());
            hobbies_page_gridview_hobbies_arts.setChoiceMode(((MyApplication) getApplication()).getArts_hobbies().size());
            hobbies_page_gridview_hobbies_arts.setAdapter(customGrid_arts);
            hobbies_page_gridview_hobbies_arts.setExpanded(true);
            Log.d("JJJJJJJ", "" + customGrid_arts);


        } else {
            new HobbiesArts_ArrayList().execute();
        }


        if (((MyApplication) getApplication()).getFood_hobbies().size() > 0) {

            CustomGrid_food customGrid_food = new CustomGrid_food(DoSomethingHobbies.this, ((MyApplication) getApplication()).getFood_hobbies());
            hobbies_page_gridview_hobbies_food.setChoiceMode(((MyApplication) getApplication()).getFood_hobbies().size());
            hobbies_page_gridview_hobbies_food.setAdapter(customGrid_food);
            hobbies_page_gridview_hobbies_food.setExpanded(true);


        } else {


//            kbv.setVisibility(View.VISIBLE);
//        timer_chat = new Timer();
//        timer_chat.schedule(new AutoSlider(), 0, 1350);
//        splashAnimation.start();
            new HobbiesFood_Array().execute();
        }


        if (((MyApplication) getApplication()).getPets_hobbies().size() > 0) {
            CustomGrid_pets customGrid_pets = new CustomGrid_pets(DoSomethingHobbies.this, ((MyApplication) getApplication()).getPets_hobbies());
            hobbies_page_gridview_hobbies_pets.setChoiceMode(((MyApplication) getApplication()).getPets_hobbies().size());
            hobbies_page_gridview_hobbies_pets.setAdapter(customGrid_pets);
            hobbies_page_gridview_hobbies_pets.setExpanded(true);
        } else {
            new HoobiesPet_Array().execute();
        }


        if (((MyApplication) getApplication()).getRecreation_hobbies().size() > 0) {
            CustomGrid_recreation customGrid_recreation = new CustomGrid_recreation(DoSomethingHobbies.this, ((MyApplication) getApplication()).getRecreation_hobbies());
            hobbies_page_gridview_hobbies_recreation.setChoiceMode(((MyApplication) getApplication()).getRecreation_hobbies().size());
            hobbies_page_gridview_hobbies_recreation.setAdapter(customGrid_recreation);
            hobbies_page_gridview_hobbies_recreation.setExpanded(true);
        } else {
            new HobbiesRecreation().execute();
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bundle_list.clear();


                for (int i = 0; i < ((MyApplication) getApplication()).getArts_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getArts_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getArts_hobbies().get(i).getImage_id_arts());
                    }
                }


                for (int i = 0; i < ((MyApplication) getApplication()).getFood_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getFood_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getFood_hobbies().get(i).getImage_id_food());
                    }
                }


                for (int i = 0; i < ((MyApplication) getApplication()).getPets_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getPets_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getPets_hobbies().get(i).getImage_id_pets());
                    }
                }


                for (int i = 0; i < ((MyApplication) getApplication()).getRecreation_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getRecreation_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getRecreation_hobbies().get(i).getImage_id_recreation());
                    }
                }

                if (!sharedPrefrences.getHobbies(context).equalsIgnoreCase("Yes")) {
                    Intent i = new Intent(DoSomethingHobbies.this, DoSomethingprofile.class);
                    if (bundle_list.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle_list.clear();
                        bundle_list.add(R.drawable.pluis_icon);
                        Log.d("bundle_list", "H" + bundle_list);

                        bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    } else {
                        value = new Bundle();
//                value.putStringArrayList("array_bundle_hobbies_name", hobbies_array);
                        value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);

                        Log.d("DOSOMETHINGARRAyBUNDLE", "............" + value);
//                i.putStringArrayListExtra("array_bundle_hobbies_name", hobbies_array);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    }
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(DoSomethingHobbies.this, DoSomethingStatus.class);
                    FragmentProfile fragmentProfile = new FragmentProfile();

                    if (bundle_list.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle_list.clear();
                        bundle_list.add(R.drawable.pluis_icon);
                        Log.d("FFFFFFFFFFFFFFFFFFFFFFF", "H" + bundle_list);
                        Log.d("bundle_list", "string" + String.valueOf(bundle_list));
                        bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    } else {
                        value = new Bundle();
//                value.putStringArrayList("array_bundle_hobbies_name", hobbies_array);
                        value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        Log.d("ARRAYYYYBUNDLE", "............" + value);
                        Log.d("bundle_list", "withoutemptystring" + String.valueOf(bundle_list));
//                i.putStringArrayListExtra("array_bundle_hobbies_name", hobbies_array);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    }
                    startActivity(i);
                    finish();

                }














               /* Intent i = new Intent(DoSomethingHobbies.this, DoSomethingprofile.class);
                if (bundle_list.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle_list.clear();
                    bundle_list.add(R.drawable.pluis_icon);
                    Log.d("bundle_list", "H" + bundle_list);

                    bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                    i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                } else {
                    value = new Bundle();
//                value.putStringArrayList("array_bundle_hobbies_name", hobbies_array);
                    value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);

                    Log.d("DOSOMETHINGARRAyBUNDLE", "............" + value);
//                i.putStringArrayListExtra("array_bundle_hobbies_name", hobbies_array);
                    i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                }
                startActivity(i);
                finish();*/
            }
        });

        hobbies_toolbar_textview_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle_list.clear();


                for (int i = 0; i < ((MyApplication) getApplication()).getArts_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getArts_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getArts_hobbies().get(i).getImage_id_arts());
                    }
                }


                for (int i = 0; i < ((MyApplication) getApplication()).getFood_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getFood_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getFood_hobbies().get(i).getImage_id_food());
                    }
                }


                for (int i = 0; i < ((MyApplication) getApplication()).getPets_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getPets_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getPets_hobbies().get(i).getImage_id_pets());
                    }
                }


                for (int i = 0; i < ((MyApplication) getApplication()).getRecreation_hobbies().size(); i++) {
                    if (((MyApplication) getApplication()).getRecreation_hobbies().get(i).getstate()) {
                        bundle_list.add(((MyApplication) getApplication()).getRecreation_hobbies().get(i).getImage_id_recreation());
                    }
                }


//                bundle_list.addAll(hobbies_list_arts);
//                bundle_list.addAll(hobbies_list_food);
//                bundle_list.addAll(hobbies_list_pets);
//                bundle_list.addAll(hobbies_list_recreation);
                Log.d("AAAA", "LLLL" + bundle_list);
                if (!sharedPrefrences.getHobbies(context).equalsIgnoreCase("Yes")) {
                    Intent i = new Intent(DoSomethingHobbies.this, DoSomethingprofile.class);
                    if (bundle_list.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle_list.clear();
                        bundle_list.add(R.drawable.pluis_icon);
                        Log.d("bundle_list", "H" + bundle_list);

                        bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    } else {
                        value = new Bundle();
//                value.putStringArrayList("array_bundle_hobbies_name", hobbies_array);
                        value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);

                        Log.d("DOSOMETHINGARRAyBUNDLE", "............" + value);
//                i.putStringArrayListExtra("array_bundle_hobbies_name", hobbies_array);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    }
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(DoSomethingHobbies.this, DoSomethingStatus.class);
                    FragmentProfile fragmentProfile = new FragmentProfile();

                    if (bundle_list.isEmpty()) {
                        Bundle bundle = new Bundle();
                        bundle_list.clear();
                        bundle_list.add(R.drawable.pluis_icon);
                        Log.d("FFFFFFFFFFFFFFFFFFFFFFF", "H" + bundle_list);
                        Log.d("bundle_list", "string" + String.valueOf(bundle_list));
                        bundle.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    } else {
                        value = new Bundle();
//                value.putStringArrayList("array_bundle_hobbies_name", hobbies_array);
                        value.putIntegerArrayList("array_bundle_hobbies_image", bundle_list);
                        Log.d("ARRAYYYYBUNDLE", "............" + value);
                        Log.d("bundle_list", "withoutemptystring" + String.valueOf(bundle_list));
//                i.putStringArrayListExtra("array_bundle_hobbies_name", hobbies_array);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", bundle_list);
                    }
                    startActivity(i);
                    finish();

                }


            }
        });

//        pDialog.dismiss();
    }




    private void text_font_typeface() {
        Typeface patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");
        Typeface patron_regular = Typeface.createFromAsset(getAssets(), "fonts/Patron-Regular.ttf");
        Typeface patron_medium = Typeface.createFromAsset(getAssets(), "fonts/Patron-Medium.ttf");
        Typeface myfonts_thin = Typeface.createFromAsset(getAssets(), "fonts/Patron-Thin.ttf");
        walkthrough_hobbies_TextView.setTypeface(patron_regular);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_hobbies_page, menu);
        return true;
    }

    private class CustomGrid_arts extends BaseAdapter {

        Context context;
        ArrayList<Arts_hobbies> hobbies_name;

        public CustomGrid_arts(DoSomethingHobbies doSomethingHobbies, ArrayList<Arts_hobbies> name) {
            context = doSomethingHobbies;
            hobbies_name = name;
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

        class Holder {
            TextView grid_layout_textview_name;
            ImageView grid_layout_imageview_hobbies;
            CheckBox grid_layout_checkbox;
            LinearLayout grid_linear_layout;

        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            final Holder holder;
            View grid = null;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(context);
            holder = new Holder();
//            final Holder finalHolder = holder;
            grid = inflater.inflate(R.layout.grid_layout_hobbies, null);
//                convertView.setTag(holder);
            holder.grid_layout_textview_name = (TextView) grid.findViewById(R.id.grid_layout_textview_name);
            holder.grid_linear_layout = (LinearLayout) grid.findViewById(R.id.grid_linear_layout);
            holder.grid_layout_imageview_hobbies = (ImageView) grid.findViewById(R.id.grid_layout_imageview_hobbies);
//            holder.grid_layout_checkbox = (CheckBox) grid.findViewById(R.id.grid_layout_checkbox);
            if (convertView != null) {
                holder.grid_layout_textview_name.setText(hobbies_name.get(position).getImage_name_arts());
                if (hobbies_name.get(position).getstate()) {
                    aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_Active_arts(), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
                                BitmapDrawable bd = new BitmapDrawable(bm);
                                iv.setBackgroundDrawable(bd);
                            }
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.text_red));
                        }
                    });
                } else {
                    aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_inActive_arts(), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
                                BitmapDrawable bd = new BitmapDrawable(bm);
                                iv.setBackgroundDrawable(bd);
                            }
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.hint_color));
                        }
                    });
                }
                Log.d("GSDF", "" + hobbies_name);

//                Log.d("QWERTY", "--------------->>>>>" +hobbies_image.get(position).getImag_inActive_arts());


//            holder.grid_layout_checkbox.setId(position);
//            holder.grid_layout_imageview_hobbies.setId(position);

                holder.grid_linear_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedposition = position;
//                        Holder holder = new Holder();
                        Log.d("KKK", "LLL" + hobbies_name.get(selectedposition).getImag_inActive_arts());
//                        hobbies_name.get(selectedposition).setstate(true);
//                        if( holder.grid_layout_imageview_hobbies.getResources().getString(Integer.parseInt(hobbies_name.get(position).getImag_inActive_arts())).equals(hobbies_name.get(selectedposition).getImag_inActive_arts())){
                        if (!hobbies_name.get(selectedposition).getstate()) {
                            aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(selectedposition).getImag_Active_arts(), true, true, 0, 0, new BitmapAjaxCallback() {
                                @Override
                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                    if (status.getCode() == 200) {
                                        BitmapDrawable bd = new BitmapDrawable(bm);
                                        iv.setBackgroundDrawable(bd);
                                    }
                                    holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.text_red));
                                }
                            });
                            bundle_list.clear();
                            hobbies_list_arts.add(hobbies_name.get(selectedposition).getImage_id_arts());
                            Log.d("JJJJ", "PPP" + hobbies_list_arts);
                            hobbies_name.get(selectedposition).setstate(true);
                        } else {
                            aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(selectedposition).getImag_inActive_arts(), true, true, 0, 0, new BitmapAjaxCallback() {
                                @Override
                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                    if (status.getCode() == 200) {
                                        BitmapDrawable bd = new BitmapDrawable(bm);
                                        iv.setBackgroundDrawable(bd);
                                    }
                                }
                            });
                            Collections.shuffle(hobbies_list_arts);
                            if (hobbies_list_arts.contains(hobbies_name.get(selectedposition).getImage_id_arts())) {
                                hobbies_list_arts.indexOf(hobbies_name.get(selectedposition).getImage_id_arts());
                                hobbies_list_arts.remove(hobbies_list_arts.indexOf(hobbies_name.get(selectedposition).getImage_id_arts()));
//                                   notifyDataSetChanged();
                                bundle_list.clear();
                                Log.d("JJJJ", "YSNO" + hobbies_list_arts.indexOf(hobbies_name.get(selectedposition).getImage_id_arts()));
                            }
                            Log.d("JJJJ", "PPP" + hobbies_list_arts);
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.hint_color));
                            hobbies_name.get(selectedposition).setstate(false);
                            Log.d("KMMM", "NNNM" + hobbies_name.get(selectedposition).getstate());
                        }

                    }
                });

            } else {
                Log.d("OOOO", "KKKKKKK" + "GGGG");
//                holder = (Holder)grid.getTag();
            }

            return grid;
        }
    }

    private class CustomGrid_food extends BaseAdapter {

        Context context;
        ArrayList<Food_hobbies> hobbies_name;

        public CustomGrid_food(DoSomethingHobbies doSomethingHobbies, ArrayList<Food_hobbies> name) {
            context = doSomethingHobbies;
            hobbies_name = name;
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

        class Holder {
            TextView grid_layout_textview_name;
            ImageView grid_layout_imageview_hobbies;
            CheckBox grid_layout_checkbox;
            LinearLayout grid_linear_layout;

        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            final Holder holder;
            View grid = null;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(context);
            holder = new Holder();
            grid = inflater.inflate(R.layout.grid_layout_hobbies, null);
            holder.grid_layout_textview_name = (TextView) grid.findViewById(R.id.grid_layout_textview_name);
            holder.grid_linear_layout = (LinearLayout) grid.findViewById(R.id.grid_linear_layout);
            holder.grid_layout_imageview_hobbies = (ImageView) grid.findViewById(R.id.grid_layout_imageview_hobbies);
            if (convertView != null) {
                holder.grid_layout_textview_name.setText(hobbies_name.get(position).getImage_name_food());
                if (hobbies_name.get(position).getstate()) {
                    aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_Active_food(), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
                                BitmapDrawable bd = new BitmapDrawable(bm);
                                iv.setBackgroundDrawable(bd);
                            }
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.text_red));
                        }
                    });
                } else {
                    aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_inActive_food(), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
                                BitmapDrawable bd = new BitmapDrawable(bm);
                                iv.setBackgroundDrawable(bd);
                            }
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.hint_color));
                        }
                    });
                }
                Log.d("GSDF", "" + hobbies_name);

//                Log.d("QWERTY", "--------------->>>>>" +hobbies_image.get(position).getImag_inActive_arts());


                holder.grid_linear_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedposition = position;
//                        Holder holder = new Holder();
                        Log.d("KKK", "LLL" + hobbies_name.get(selectedposition).getImag_inActive_food());
//                        hobbies_name.get(selectedposition).setstate(true);
//                        if( holder.grid_layout_imageview_hobbies.getResources().getString(Integer.parseInt(hobbies_name.get(position).getImag_inActive_arts())).equals(hobbies_name.get(selectedposition).getImag_inActive_arts())){
                        if (!hobbies_name.get(selectedposition).getstate()) {
                            aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(selectedposition).getImag_Active_food(), true, true, 0, 0, new BitmapAjaxCallback() {
                                @Override
                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                    if (status.getCode() == 200) {
                                        BitmapDrawable bd = new BitmapDrawable(bm);
                                        iv.setBackgroundDrawable(bd);
                                    }
                                    holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.text_red));
                                }
                            });
                            bundle_list.clear();
                            hobbies_list_food.add(hobbies_name.get(selectedposition).getImage_id_food());
                            Log.d("JJJJ", "PPP" + hobbies_list_food);
                            hobbies_name.get(selectedposition).setstate(true);
                        } else {
                            aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(selectedposition).getImag_inActive_food(), true, true, 0, 0, new BitmapAjaxCallback() {
                                @Override
                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                    if (status.getCode() == 200) {
                                        BitmapDrawable bd = new BitmapDrawable(bm);
                                        iv.setBackgroundDrawable(bd);
                                    }
                                }
                            });
                            Collections.shuffle(hobbies_list_food);
                            if (hobbies_list_food.contains(hobbies_name.get(selectedposition).getImage_id_food())) {
                                hobbies_list_food.indexOf(hobbies_name.get(selectedposition).getImage_id_food());
                                hobbies_list_food.remove(hobbies_list_food.indexOf(hobbies_name.get(selectedposition).getImage_id_food()));
//                                   notifyDataSetChanged();
                                bundle_list.clear();
                                Log.d("JJJJ", "YSNO" + hobbies_list_food.indexOf(hobbies_name.get(selectedposition).getImage_id_food()));
                            }
                            Log.d("JJJJ", "PPP" + hobbies_list_food);
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.hint_color));
                            hobbies_name.get(selectedposition).setstate(false);
                            Log.d("KMMM", "NNNM" + hobbies_name.get(selectedposition).getstate());
                        }
                    }
                });


            } else {
                Log.d("OOOO", "KKKKKKK" + "GGGG");
            }


            return grid;
        }
    }


    private class CustomGrid_pets extends BaseAdapter {

        Context context;
        ArrayList<Pets_hobbies> hobbies_name;

        public CustomGrid_pets(DoSomethingHobbies doSomethingHobbies, ArrayList<Pets_hobbies> name) {
            context = doSomethingHobbies;
            hobbies_name = name;
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

        class Holder {
            TextView grid_layout_textview_name;
            ImageView grid_layout_imageview_hobbies;
            CheckBox grid_layout_checkbox;
            LinearLayout grid_linear_layout;

        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            final Holder holder;
            View grid = null;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(context);
            holder = new Holder();
            grid = inflater.inflate(R.layout.grid_layout_hobbies, null);
            holder.grid_layout_textview_name = (TextView) grid.findViewById(R.id.grid_layout_textview_name);
            holder.grid_linear_layout = (LinearLayout) grid.findViewById(R.id.grid_linear_layout);
            holder.grid_layout_imageview_hobbies = (ImageView) grid.findViewById(R.id.grid_layout_imageview_hobbies);
            if (convertView != null) {
                holder.grid_layout_textview_name.setText(hobbies_name.get(position).getImage_name_pets());
                if (hobbies_name.get(position).getstate()) {
                    aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_Active_pets(), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
                                BitmapDrawable bd = new BitmapDrawable(bm);
                                iv.setBackgroundDrawable(bd);
                            }
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.text_red));
                        }
                    });
                } else {
                    aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_inActive_pets(), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
                                BitmapDrawable bd = new BitmapDrawable(bm);
                                iv.setBackgroundDrawable(bd);
                            }
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.hint_color));
                        }
                    });
                }
                Log.d("GSDF", "" + hobbies_name);

//                Log.d("QWERTY", "--------------->>>>>" +hobbies_image.get(position).getImag_inActive_arts());


                holder.grid_linear_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedposition = position;
//                        Holder holder = new Holder();
                        Log.d("KKK", "LLL" + hobbies_name.get(selectedposition).getImag_inActive_pets());
//                        hobbies_name.get(selectedposition).setstate(true);
//                        if( holder.grid_layout_imageview_hobbies.getResources().getString(Integer.parseInt(hobbies_name.get(position).getImag_inActive_arts())).equals(hobbies_name.get(selectedposition).getImag_inActive_arts())){
                        if (!hobbies_name.get(selectedposition).getstate()) {
                            aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(selectedposition).getImag_Active_pets(), true, true, 0, 0, new BitmapAjaxCallback() {
                                @Override
                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                    if (status.getCode() == 200) {
                                        BitmapDrawable bd = new BitmapDrawable(bm);
                                        iv.setBackgroundDrawable(bd);
                                    }
                                    holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.text_red));
                                }
                            });
                            bundle_list.clear();
                            hobbies_list_pets.add(hobbies_name.get(selectedposition).getImage_id_pets());
                            Log.d("JJJJ", "PPP" + hobbies_list_pets);
                            hobbies_name.get(selectedposition).setstate(true);
                        } else {
                            aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(selectedposition).getImag_inActive_pets(), true, true, 0, 0, new BitmapAjaxCallback() {
                                @Override
                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                    if (status.getCode() == 200) {
                                        BitmapDrawable bd = new BitmapDrawable(bm);
                                        iv.setBackgroundDrawable(bd);
                                    }
                                }
                            });
                            Collections.shuffle(hobbies_list_pets);
                            if (hobbies_list_pets.contains(hobbies_name.get(selectedposition).getImage_id_pets())) {
                                hobbies_list_pets.indexOf(hobbies_name.get(selectedposition).getImage_id_pets());
                                hobbies_list_pets.remove(hobbies_list_pets.indexOf(hobbies_name.get(selectedposition).getImage_id_pets()));
//                                   notifyDataSetChanged();
                                bundle_list.clear();
                                Log.d("JJJJ", "YSNO" + hobbies_list_arts.indexOf(hobbies_name.get(selectedposition).getImage_id_pets()));
                            }
                            Log.d("JJJJ", "PPP" + hobbies_list_pets);
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.hint_color));
                            hobbies_name.get(selectedposition).setstate(false);
                            Log.d("KMMM", "NNNM" + hobbies_name.get(selectedposition).getstate());
                        }
                    }
                });

            } else {
                Log.d("OOOO", "KKKKKKK" + "GGGG");
            }


            return grid;
        }
    }


    private class CustomGrid_recreation extends BaseAdapter {

        Context context;
        ArrayList<Recreation_hobbies> hobbies_name;

        public CustomGrid_recreation(DoSomethingHobbies doSomethingHobbies, ArrayList<Recreation_hobbies> name) {
            context = doSomethingHobbies;
            hobbies_name = name;
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

        class Holder {
            TextView grid_layout_textview_name;
            ImageView grid_layout_imageview_hobbies;
            CheckBox grid_layout_checkbox;
            LinearLayout grid_linear_layout;

        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            final Holder holder;
            View grid = null;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = new View(context);
            holder = new Holder();
            grid = inflater.inflate(R.layout.grid_layout_hobbies, null);
            holder.grid_layout_textview_name = (TextView) grid.findViewById(R.id.grid_layout_textview_name);
            holder.grid_linear_layout = (LinearLayout) grid.findViewById(R.id.grid_linear_layout);
            holder.grid_layout_imageview_hobbies = (ImageView) grid.findViewById(R.id.grid_layout_imageview_hobbies);
            if (convertView != null) {
                holder.grid_layout_textview_name.setText(hobbies_name.get(position).getImage_name_recreation());
                if (hobbies_name.get(position).getstate()) {
                    aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_Active_recreation(), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
                                BitmapDrawable bd = new BitmapDrawable(bm);
                                iv.setBackgroundDrawable(bd);
                            }
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.text_red));
                        }
                    });
                } else {
                    aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_inActive_recreation(), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
                                BitmapDrawable bd = new BitmapDrawable(bm);
                                iv.setBackgroundDrawable(bd);
                            }
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.hint_color));
                        }
                    });
                }
                Log.d("GSDF", "" + hobbies_name);

//                Log.d("QWERTY", "--------------->>>>>" +hobbies_image.get(position).getImag_inActive_arts());


                holder.grid_linear_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("KKK", "LLL" + hobbies_name.get(position).getImag_inActive_recreation());
//
                        if (!hobbies_name.get(position).getstate()) {
                            aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_Active_recreation(), true, true, 0, 0, new BitmapAjaxCallback() {
                                @Override
                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                    if (status.getCode() == 200) {
                                        BitmapDrawable bd = new BitmapDrawable(bm);
                                        iv.setBackgroundDrawable(bd);
                                    }
                                    holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.text_red));
                                }
                            });
                            bundle_list.clear();
                            hobbies_list_recreation.add(hobbies_name.get(position).getImage_id_recreation());
                            Log.d("JJJJ", "PPP" + hobbies_list_recreation);
                            hobbies_name.get(position).setstate(true);
                            if (hobbies_name.get(position).getstate()) {
                                hobbies_list_recreation.add(hobbies_name.get(position).getImage_id_recreation());
                            }
                        } else {
                            aQuery.id(holder.grid_layout_imageview_hobbies).image(hobbies_name.get(position).getImag_inActive_recreation(), true, true, 0, 0, new BitmapAjaxCallback() {
                                @Override
                                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                    if (status.getCode() == 200) {
                                        BitmapDrawable bd = new BitmapDrawable(bm);
                                        iv.setBackgroundDrawable(bd);
                                    }
                                }
                            });
                            Collections.shuffle(hobbies_list_recreation);
                            if (hobbies_list_recreation.contains(hobbies_name.get(position).getImage_id_recreation())) {
                                hobbies_list_recreation.indexOf(hobbies_name.get(position).getImage_id_recreation());
                                hobbies_list_recreation.remove(hobbies_list_recreation.indexOf(hobbies_name.get(position).getImage_id_recreation()));
//                                   notifyDataSetChanged();
                                bundle_list.clear();
                                Log.d("JJJJ", "YSNO" + hobbies_list_recreation.indexOf(hobbies_name.get(position).getImage_id_recreation()));
                            }
                            Log.d("JJJJ", "PPP" + hobbies_list_recreation);
                            holder.grid_layout_textview_name.setTextColor(context.getResources().getColor(R.color.hint_color));
                            hobbies_name.get(position).setstate(false);
                            if (hobbies_name.get(position).getstate()) {
                                hobbies_list_recreation.add(hobbies_name.get(position).getImage_id_recreation());
                            }
                            Log.d("KMMM", "NNNM" + hobbies_name.get(position).getstate());
                        }
                    }
                });
            } else {
                Log.d("OOOO", "KKKKKKK" + "GGGG");
            }


            return grid;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    class HoobiesPet_Array extends AsyncTask<String, String, String> {
        JSONObject opj;
        JSONArray content, hobbieslist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd.show();
            progress_bar.show();

//            kbv.setVisibility(View.VISIBLE);
            timer = new Timer();
            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation.start();

        }

        protected String doInBackground(String... params) {
            response = jsonfunctions.getJSONfromURL(getString(R.string.dosomething_apilink_string_gethobbies));
            JSONObject json = new JSONObject();
            try {
                String tv1 = sharedPrefrences.getSessionid(DoSomethingHobbies.this);
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
                        if (details.getInt("categories_id") == 3) {
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
                                    ((MyApplication) getApplication()).setPets_hobbies(hobbies_array_pets);

//                                    hobbies_image_pets.add(image);
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

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }


        protected void onPostExecute(String result) {
            //TODO Auto-generated method stub
            super.onPostExecute(result);
            try {


                CustomGrid_pets customGrid_pets = new CustomGrid_pets(DoSomethingHobbies.this, hobbies_array_pets);
                hobbies_page_gridview_hobbies_pets.setChoiceMode(hobbies_array_pets.size());
                hobbies_page_gridview_hobbies_pets.setAdapter(customGrid_pets);
                hobbies_page_gridview_hobbies_pets.setExpanded(true);


                if (!hobbies_array_pets.isEmpty()) {
                    for (Pets_hobbies myClass : hobbies_array_pets) {
                        hobbies_list_pets_id.add(myClass.getImage_id_pets());
                        hobbies_image_pets_state.add(myClass.getstate());
                        Log.d("ARTS", "HHHHAAAA3" + hobbies_array_pets + "--" + hobbies_list_pets_name + "--" + hobbies_list_pets_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "EMPTYp", Toast.LENGTH_LONG).show();
                }

                if (hobbies_list.size() >= 1) {

                    for (int i = 0; i < hobbies_list_pets_id.size(); i++) {
                        if (hobbies_list.contains(hobbies_list_pets_id.get(i))) {
                            hobbies_array_pets.get(i).setstate(true);
                            Log.d("KKKK", "JJJJJpp" + hobbies_array_pets.get(i).getstate());
                        } else {
                            Log.d("KKKK", "JJJJJp" + "NM");
                        }
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    class HobbiesFood_Array extends AsyncTask<String, String, String> {

        JSONObject opj;
        JSONArray content, hobbieslist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd.show();


        /*kbv.setVisibility(View.VISIBLE);
        timer_chat = new Timer();
        timer_chat.schedule(new AutoSlider(), 0, 1350);
        splashAnimation.start();*/

        }


        protected String doInBackground(String... params) {
            response = jsonfunctions.getJSONfromURL(getString(R.string.dosomething_apilink_string_gethobbies));
            JSONObject json = new JSONObject();
            try {
                String tv1 = sharedPrefrences.getSessionid(DoSomethingHobbies.this);
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
                        if (details.getInt("categories_id") == 2) {
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

                                    ((MyApplication) getApplication()).setFood_hobbies(hobbies_array_food);


                                    Log.d("EEEEEEEE", "FFFFFFFFF" + hobbies_array_food);

//                                    hobbies_image_food.add(image);
                                }
                            }
                        }


                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        protected void onPostExecute(String result) {
            //TODO Auto-generated method stub
            super.onPostExecute(result);
            try {


                CustomGrid_food customGrid_food = new CustomGrid_food(DoSomethingHobbies.this, hobbies_array_food);
                hobbies_page_gridview_hobbies_food.setChoiceMode(hobbies_array_food.size());
                hobbies_page_gridview_hobbies_food.setAdapter(customGrid_food);
                hobbies_page_gridview_hobbies_food.setExpanded(true);


                if (!hobbies_array_food.isEmpty()) {
                    for (Food_hobbies myClass : hobbies_array_food) {
                        hobbies_list_food_id.add(myClass.getImage_id_food());
                        hobbies_image_food_state.add(myClass.getstate());
                        Log.d("ARTS", "HHHHAAAA2" + hobbies_list_food + "--" + hobbies_list_food_name + "--" + hobbies_list_food_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "EMPTYf", Toast.LENGTH_LONG).show();
                }

                if (hobbies_list.size() >= 1) {

                    for (int i = 0; i < hobbies_list_food_id.size(); i++) {
                        if (hobbies_list.contains(hobbies_list_food_id.get(i))) {
                            hobbies_array_food.get(i).setstate(true);
                            Log.d("KKKK", "JJJJJff" + hobbies_array_food.get(i).getstate());
                        } else {
                            Log.d("KKKK", "JJJJJf" + "NM");
                        }
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    class HobbiesArts_ArrayList extends AsyncTask<String, String, String> {
        JSONObject opj;
        JSONArray content, hobbieslist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd.show();


       /* kbv.setVisibility(View.VISIBLE);
        timer_chat = new Timer();
        timer_chat.schedule(new AutoSlider(), 0, 1350);
        splashAnimation.start();*/

        }


        protected String doInBackground(String... params) {
            response = jsonfunctions.getJSONfromURL(getString(R.string.dosomething_apilink_string_gethobbies));
            JSONObject json = new JSONObject();
            try {
                String tv1 = sharedPrefrences.getSessionid(DoSomethingHobbies.this);
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
                                    hobbies_array_arts.add(new Arts_hobbies(hobbies_id, category_id, name, image, image_active, false));

                                    ((MyApplication) getApplication()).setArts_hobbies(hobbies_array_arts);

                                    Log.d("EEEEEEEE", "DDDDDDDD" + hobbies_array_arts);
//                                   hobbies_image_arts.add(image);
                                }
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }


        protected void onPostExecute(String result) {
            //TODO Auto-generated method stub
            super.onPostExecute(result);
            try {


                final CustomGrid_arts customGrid_arts = new CustomGrid_arts(DoSomethingHobbies.this, hobbies_array_arts);
                hobbies_page_gridview_hobbies_arts.setChoiceMode(hobbies_array_arts.size());
                hobbies_page_gridview_hobbies_arts.setAdapter(customGrid_arts);
                hobbies_page_gridview_hobbies_arts.setExpanded(true);
                Log.d("JJJJJJJ", "" + customGrid_arts);


                if (!hobbies_array_arts.isEmpty()) {
                    for (Arts_hobbies myClass : hobbies_array_arts) {
                        hobbies_list_arts_id.add(myClass.getImage_id_arts());
                        hobbies_image_arts_state.add(myClass.getstate());
                        Log.d("ARTS", "HHHHAAAA1" + hobbies_list_arts + "--" + hobbies_list_arts_name + "--" + hobbies_list_arts_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "EMPTYa", Toast.LENGTH_LONG).show();
                }

                if (hobbies_list.size() >= 1) {
                    for (int i = 0; i < hobbies_list_arts_id.size(); i++) {
//            Log.d("ARTS", "LISTyyy"+hobbies_array_arts.get(i).getImage_id_arts());
//            hobbies_list_arts.add(hobbies_array_arts.get(i).getImage_id_arts());
                        Log.d("ARTS", "LIST" + hobbies_list_arts_id);
//                    Log.d("ARTS","AAAA"+hobbies_list.get(i));
                        if (hobbies_list.contains(hobbies_list_arts_id.get(i))) {
                            hobbies_array_arts.get(i).setstate(true);
                            Log.d("KKKK", "JJJJJaa" + hobbies_array_arts.get(i).getstate());
                        } else {
                            Log.d("KKKK", "JJJJJa" + "NM");
                        }
                    }


//                ImageAdapter imageAdapter = new ImageAdapter(getActivity(), img_list);
//                activity_dosomething_status_grid.setAdapter(imageAdapter);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    class HobbiesRecreation extends AsyncTask<String, String, String> {

        JSONObject opj;
        JSONArray content, hobbieslist;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd.show();


      /*  kbv.setVisibility(View.VISIBLE);
        timer_chat = new Timer();
        timer_chat.schedule(new AutoSlider(), 0, 1350);
        splashAnimation.start();
*/
        }

        protected String doInBackground(String... params) {
            response = jsonfunctions.getJSONfromURL(getString(R.string.dosomething_apilink_string_gethobbies));
            JSONObject json = new JSONObject();
            try {
                String tv1 = sharedPrefrences.getSessionid(DoSomethingHobbies.this);
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
                        if (details.getInt("categories_id") == 4) {
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
                                    ((MyApplication) getApplication()).setRecreation_hobbies(hobbies_array_recreation);

                                }
                            }
                        }

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }


        protected void onPostExecute(String result) {
            //TODO Auto-generated method stub
            super.onPostExecute(result);
            try {


//            kbv.setVisibility(View.GONE);
                progress_bar.dismiss();
                if (timer != null) {

                    timer.cancel();


                    timer = null;

                }
                splashAnimation.stop();


                CustomGrid_recreation customGrid_recreation = new CustomGrid_recreation(DoSomethingHobbies.this, hobbies_array_recreation);
                hobbies_page_gridview_hobbies_recreation.setChoiceMode(hobbies_array_recreation.size());
                hobbies_page_gridview_hobbies_recreation.setAdapter(customGrid_recreation);
                hobbies_page_gridview_hobbies_recreation.setExpanded(true);


                if (!hobbies_array_recreation.isEmpty()) {
                    for (Recreation_hobbies myClass : hobbies_array_recreation) {
                        hobbies_list_recreation_id.add(myClass.getImage_id_recreation());
                        hobbies_image_recreation_state.add(myClass.getstate());
                        Log.d("ARTS", "HHHHAAAA3" + hobbies_list_recreation + "--" + hobbies_list_recreation_name + "--" + hobbies_list_recreation_image);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "EMPTYr", Toast.LENGTH_LONG).show();
                }
                if (hobbies_list.size() >= 1) {

                    for (int i = 0; i < hobbies_list_recreation_id.size(); i++) {
                        if (hobbies_list.contains(hobbies_list_recreation_id.get(i))) {
                            hobbies_array_recreation.get(i).setstate(true);
                            Log.d("KKKK", "JJJJJrr" + hobbies_array_recreation.get(i).getstate());
                        } else {
                            Log.d("KKKK", "JJJJJr" + "NM");
                        }
                    }

                }

            } catch (Exception e) {
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
//            pd.show();


            kbv.setVisibility(View.VISIBLE);
            timer = new Timer();
            timer.schedule(new AutoSlider(), 0, 1350);
            splashAnimation.start();

        }

        protected String doInBackground(String... params) {
            response = jsonfunctions.getJSONfromURL(getString(R.string.dosomething_apilink_string_gethobbies));
            JSONObject json = new JSONObject();
            try {
                String tv1 = sharedPrefrences.getSessionid(DoSomethingHobbies.this);
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
                                    hobbies_array_arts.add(new Arts_hobbies(hobbies_id, category_id, name, image, image_active, false));

                                    ((MyApplication) getApplication()).setArts_hobbies(hobbies_array_arts);

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

                                    ((MyApplication) getApplication()).setFood_hobbies(hobbies_array_food);


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
                                    ((MyApplication) getApplication()).setPets_hobbies(hobbies_array_pets);

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
                                    ((MyApplication) getApplication()).setRecreation_hobbies(hobbies_array_recreation);

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
            try {


                kbv.setVisibility(View.GONE);
                if (timer != null) {

                    timer.cancel();


                    timer = null;

                }
                splashAnimation.stop();


                final CustomGrid_arts customGrid_arts = new CustomGrid_arts(DoSomethingHobbies.this, hobbies_array_arts);
                hobbies_page_gridview_hobbies_arts.setChoiceMode(hobbies_array_arts.size());
                hobbies_page_gridview_hobbies_arts.setAdapter(customGrid_arts);
                hobbies_page_gridview_hobbies_arts.setExpanded(true);
                Log.d("JJJJJJJ", "" + customGrid_arts);

                CustomGrid_food customGrid_food = new CustomGrid_food(DoSomethingHobbies.this, hobbies_array_food);
                hobbies_page_gridview_hobbies_food.setChoiceMode(hobbies_array_food.size());
                hobbies_page_gridview_hobbies_food.setAdapter(customGrid_food);
                hobbies_page_gridview_hobbies_food.setExpanded(true);

                CustomGrid_pets customGrid_pets = new CustomGrid_pets(DoSomethingHobbies.this, hobbies_array_pets);
                hobbies_page_gridview_hobbies_pets.setChoiceMode(hobbies_array_pets.size());
                hobbies_page_gridview_hobbies_pets.setAdapter(customGrid_pets);
                hobbies_page_gridview_hobbies_pets.setExpanded(true);

                CustomGrid_recreation customGrid_recreation = new CustomGrid_recreation(DoSomethingHobbies.this, hobbies_array_recreation);
                hobbies_page_gridview_hobbies_recreation.setChoiceMode(hobbies_array_recreation.size());
                hobbies_page_gridview_hobbies_recreation.setAdapter(customGrid_recreation);
                hobbies_page_gridview_hobbies_recreation.setExpanded(true);


                if (!hobbies_array_arts.isEmpty()) {
                    for (Arts_hobbies myClass : hobbies_array_arts) {
                        hobbies_list_arts_id.add(myClass.getImage_id_arts());
                        hobbies_image_arts_state.add(myClass.getstate());
                        Log.d("ARTS", "HHHHAAAA1" + hobbies_list_arts + "--" + hobbies_list_arts_name + "--" + hobbies_list_arts_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "EMPTYa", Toast.LENGTH_LONG).show();
                }
                if (!hobbies_array_food.isEmpty()) {
                    for (Food_hobbies myClass : hobbies_array_food) {
                        hobbies_list_food_id.add(myClass.getImage_id_food());
                        hobbies_image_food_state.add(myClass.getstate());
                        Log.d("ARTS", "HHHHAAAA2" + hobbies_list_food + "--" + hobbies_list_food_name + "--" + hobbies_list_food_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "EMPTYf", Toast.LENGTH_LONG).show();
                }
                if (!hobbies_array_pets.isEmpty()) {
                    for (Pets_hobbies myClass : hobbies_array_pets) {
                        hobbies_list_pets_id.add(myClass.getImage_id_pets());
                        hobbies_image_pets_state.add(myClass.getstate());
                        Log.d("ARTS", "HHHHAAAA3" + hobbies_array_pets + "--" + hobbies_list_pets_name + "--" + hobbies_list_pets_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "EMPTYp", Toast.LENGTH_LONG).show();
                }
                if (!hobbies_array_recreation.isEmpty()) {
                    for (Recreation_hobbies myClass : hobbies_array_recreation) {
                        hobbies_list_recreation_id.add(myClass.getImage_id_recreation());
                        hobbies_image_recreation_state.add(myClass.getstate());
                        Log.d("ARTS", "HHHHAAAA3" + hobbies_list_recreation + "--" + hobbies_list_recreation_name + "--" + hobbies_list_recreation_image);
//                    Toast.makeText(getApplicationContext(),"VALUE",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "EMPTYr", Toast.LENGTH_LONG).show();
                }
                if (hobbies_list.size() >= 1) {
                    for (int i = 0; i < hobbies_list_arts_id.size(); i++) {
//            Log.d("ARTS", "LISTyyy"+hobbies_array_arts.get(i).getImage_id_arts());
//            hobbies_list_arts.add(hobbies_array_arts.get(i).getImage_id_arts());
                        Log.d("ARTS", "LIST" + hobbies_list_arts_id);
//                    Log.d("ARTS","AAAA"+hobbies_list.get(i));
                        if (hobbies_list.contains(hobbies_list_arts_id.get(i))) {
                            hobbies_array_arts.get(i).setstate(true);
                            Log.d("KKKK", "JJJJJaa" + hobbies_array_arts.get(i).getstate());
                        } else {
                            Log.d("KKKK", "JJJJJa" + "NM");
                        }
                    }
                    for (int i = 0; i < hobbies_list_food_id.size(); i++) {
                        if (hobbies_list.contains(hobbies_list_food_id.get(i))) {
                            hobbies_array_food.get(i).setstate(true);
                            Log.d("KKKK", "JJJJJff" + hobbies_array_food.get(i).getstate());
                        } else {
                            Log.d("KKKK", "JJJJJf" + "NM");
                        }
                    }
                    for (int i = 0; i < hobbies_list_pets_id.size(); i++) {
                        if (hobbies_list.contains(hobbies_list_pets_id.get(i))) {
                            hobbies_array_pets.get(i).setstate(true);
                            Log.d("KKKK", "JJJJJpp" + hobbies_array_pets.get(i).getstate());
                        } else {
                            Log.d("KKKK", "JJJJJp" + "NM");
                        }
                    }
                    for (int i = 0; i < hobbies_list_recreation_id.size(); i++) {
                        if (hobbies_list.contains(hobbies_list_recreation_id.get(i))) {
                            hobbies_array_recreation.get(i).setstate(true);
                            Log.d("KKKK", "JJJJJrr" + hobbies_array_recreation.get(i).getstate());
                        } else {
                            Log.d("KKKK", "JJJJJr" + "NM");
                        }
                    }

                    Log.d("ARAR", "JJJJ" + bundle_list);
//                ImageAdapter imageAdapter = new ImageAdapter(getActivity(), img_list);
//                activity_dosomething_status_grid.setAdapter(imageAdapter);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class Blink_progress extends TimerTask{

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        if (splashAnimation_walkthrough_hobbies.isRunning()) {
                            splashAnimation_walkthrough_hobbies.stop();
                        } else {
                            splashAnimation_walkthrough_hobbies.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO Auto-generated catch block
                    }


                }

            });
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
