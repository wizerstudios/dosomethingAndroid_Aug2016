package com.dosomething.android.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.auth.AccountHandle;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.dosomething.android.Beanclasses.Arts_hobbies;
import com.dosomething.android.Beanclasses.Food_hobbies;
import com.dosomething.android.Beanclasses.HobbiesBean;
import com.dosomething.android.Beanclasses.Pets_hobbies;
import com.dosomething.android.Beanclasses.Recreation_hobbies;
import com.dosomething.android.CommonClasses.ExpandableHeightGridView;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.dosomething.android.Database.DBAdapter;
import com.dosomething.android.DoSomeThingLogin;
import com.dosomething.android.DoSomethingHobbies;
import com.dosomething.android.DoSomethingStatus;
import com.dosomething.android.MyApplication;
import com.dosomething.android.R;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentProfile.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int NUM_PAGES=3;
    private static final String TAG_PROFILEUSERID = "profile_user_id";
    private static final int RESULT_LOAD_IMG = 1;
    private Date oneWayTripDate;
    String tripDate;
    private static final int PICK_FROM_CAMERA = 4;
    private static final int CROP_FROM_CAMERA = 7;
    private static final int PICK_FROM_FILE = 1;
    private Uri mImageCaptureUri;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPrefrences sharedPrefrences;
    Jsonfunctions jsonfunctions;
    TextView fragment_profile_page_textview_datepicker;
    TextView fragment_profile_page_textview_title_profile;
    TextView fragment_profile_page_textview_firstname;
    TextView fragment_profile_page_textview_lastname;
    TextView fragment_profile_page_textview_gender;
    TextView fragment_profile_page_textview_dateofbirth;
    TextView fragment_profile_page_textview_about_you;
    TextView fragment_profile_page_textview_hobbies;
    TextView fragment_profile_page_textview_title_account;
    TextView fragment_profile_page_textview_email;
    TextView fragment_profile_page_textview_password;
    TextView fragment_profile_page_textview_new_password;
    TextView fragment_profile_page_textview_confirm_password;
    EditText fragment_profile_page_edittext_firstname;
    EditText fragment_profile_page_edittext_lastname;
    EditText fragment_profile_page_edittext_gender;
    EditText fragment_profile_page_edittext_email;
    EditText fragment_profile_page_edittext_password;
    EditText fragment_profile_page_edittext_about_you;
    EditText fragment_profile_page_edittext_new_password;
    EditText fragment_profile_page_edittext_confirm_password;
    ExpandableHeightGridView fragment_profile_page_gridview_hobbies;
    private OnFragmentInteractionListener mListener;
    LinearLayout fragment_viewpager_dots;
    LinearLayout dosomething_account_login_via_email;
    private PagerAdapter mPagerAdapter;
    private ViewPager fragment_pager;

    ImageView dot;
    DBAdapter dbAdapter;
    ProgressBar fragment_profile_page_progressbar_hobbies;
    Bundle bundle;
    private static String urlImageStatus = "http://wiztestinghost.com/dosomething/gethobbies?";
    private static String urlupdate = "http://wiztestinghost.com/dosomething/updateprofile?";
    private static String url = "http://wiztestinghost.com/dosomething/register?";
    private static String urlgetUser = "http://wiztestinghost.com/dosomething/getuserdetails?";
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
    private static final String TAG_SESSIONID = "sessionid";
    private TransparentProgressDialog pd;
    StringBuilder hobbies_append_id;
    String firstname, lastname, sessionid, type, email, password, profileId, dob, profileImage1, profileImage2, profileImage3, gender, about, hobbies, device, sessionId, json_string, json_string_updateposition, notification_message, notification_sound, notification_vibration, deviceid, response;
    String latitude;
    String longitude;
    String notification;
    JSONObject json_object, json_content, details;
    JSONObject json_object_updateposition, json_content_updateposition, details_updateposition;
    ArrayList<Integer> hobbies_list = new ArrayList<>();
    ArrayList<Integer> hobbies_list_arts = new ArrayList<>();
    ArrayList<String> hobbies_list_arts_name = new ArrayList<>();
    ArrayList<String> hobbies_list_arts_image = new ArrayList<>();
    ArrayList<Integer> hobbies_list_food = new ArrayList<>();
    ArrayList<String> hobbies_list_food_name = new ArrayList<>();
    ArrayList<String> hobbies_list_food_image = new ArrayList<>();
    ArrayList<Integer> hobbies_list_pets = new ArrayList<>();
    ArrayList<String> hobbies_list_pets_name = new ArrayList<>();
    ArrayList<String> hobbies_list_pets_image = new ArrayList<>();
    ArrayList<Integer> hobbies_list_recreation = new ArrayList<>();
    ArrayList<String> hobbies_list_recreation_name = new ArrayList<>();
    ArrayList<String> hobbies_list_recreation_image = new ArrayList<>();
    ArrayList<String> hobbies_name = new ArrayList<>();
    ArrayList<String> hobbies_image = new ArrayList<>();
    ArrayList<Integer> hobbies_id = new ArrayList<>();
    ArrayList<Integer> name, empty = new ArrayList<>();
    ArrayList<Integer> image = new ArrayList<>();
    ArrayList<Arts_hobbies> hobbies_array_arts = new ArrayList<>();
    //    ArrayList<Integer> hobbies_image_arts;
    ArrayList<Food_hobbies> hobbies_array_food = new ArrayList<>();
    //    ArrayList<String> hobbies_image_food;
    ArrayList<Pets_hobbies> hobbies_array_pets = new ArrayList<>();
    //    ArrayList<String> hobbies_image_pets;
    ArrayList<Recreation_hobbies> hobbies_array_recreation = new ArrayList<>();
    ImageView
            profile_page_imageview_camera,
            profile_page_imageview_profile_image,
            fragment_profile_page_imageview_hobbies,
            grid_layout_profile_imageview_hobbies,
            profile_Cam, dosomething_login_via_imageview;
    private TextView grid_layout_profile_textview_name;
    private AQuery aQuery;
    ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<>();
    private String profile_user_id;
    private AccountHandle handle;
    private Date choosenDateFromUI;
    private TextView dosomething_login_via_textview;
    private Tracker mTracker;
    ImageView fragment_profile_image_autoincrease;
    List<android.support.v4.app.Fragment> fragments = new Vector<Fragment>();
    UserProfileImage1_Fragment userProfileImage1_fragment;
    private ArrayList<ImageView> dots;
boolean popup=false;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance(String param1, String param2) {
        FragmentProfile fragment = new FragmentProfile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentProfile() {
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
        View view = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        try {
            MyApplication application = (MyApplication) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
        } catch (Exception e) {
            e.printStackTrace();
        }


        fragment_viewpager_dots = (LinearLayout) view.findViewById(R.id.fragment_viewpager_dots);
        dosomething_account_login_via_email = (LinearLayout) view.findViewById(R.id.dosomething_account_login_via_email);
        fragment_pager = (ViewPager) view.findViewById(R.id.fragment_pager);
        grid_layout_profile_imageview_hobbies = (ImageView) view.findViewById(R.id.grid_layout_profile_imageview_hobbies);
        fragment_profile_page_imageview_hobbies = (ImageView) view.findViewById(R.id.fragment_profile_page_imageview_hobbies);
        fragment_profile_image_autoincrease = (ImageView) view.findViewById(R.id.fragment_profile_image_autoincrease);
        dosomething_login_via_imageview = (ImageView) view.findViewById(R.id.dosomething_login_via_imageview);
        fragment_profile_page_edittext_firstname = (EditText) view.findViewById(R.id.fragment_profile_page_edittext_firstname);
        fragment_profile_page_edittext_lastname = (EditText) view.findViewById(R.id.fragment_profile_page_edittext_lastname);
        fragment_profile_page_edittext_gender = (EditText) view.findViewById(R.id.fragment_profile_page_edittext_gender);
        fragment_profile_page_edittext_email = (EditText) view.findViewById(R.id.fragment_profile_page_edittext_email);
        fragment_profile_page_edittext_password = (EditText) view.findViewById(R.id.fragment_profile_page_edittext_password);
        fragment_profile_page_edittext_new_password = (EditText) view.findViewById(R.id.fragment_profile_page_edittext_new_password);
        fragment_profile_page_edittext_confirm_password = (EditText) view.findViewById(R.id.fragment_profile_page_edittext_confirm_password);
        fragment_profile_page_edittext_about_you = (EditText) view.findViewById(R.id.fragment_profile_page_edittext_about_you);
        fragment_profile_page_textview_datepicker = (TextView) view.findViewById(R.id.fragment_profile_page_textview_datepicker);
        fragment_profile_page_textview_title_profile = (TextView) view.findViewById(R.id.fragment_profile_page_textview_title_profile);
        fragment_profile_page_textview_firstname = (TextView) view.findViewById(R.id.fragment_profile_page_textview_firstname);
        fragment_profile_page_textview_lastname = (TextView) view.findViewById(R.id.fragment_profile_page_textview_lastname);
        fragment_profile_page_textview_gender = (TextView) view.findViewById(R.id.fragment_profile_page_textview_gender);
        fragment_profile_page_textview_dateofbirth = (TextView) view.findViewById(R.id.fragment_profile_page_textview_dateofbirth);
        fragment_profile_page_textview_about_you = (TextView) view.findViewById(R.id.fragment_profile_page_textview_about_you);
        fragment_profile_page_textview_hobbies = (TextView) view.findViewById(R.id.fragment_profile_page_textview_hobbies);
        fragment_profile_page_textview_title_account = (TextView) view.findViewById(R.id.fragment_profile_page_textview_title_account);
        dosomething_login_via_textview = (TextView) view.findViewById(R.id.dosomething_login_via_textview);
        fragment_profile_page_textview_email = (TextView) view.findViewById(R.id.fragment_profile_page_textview_email);
        fragment_profile_page_textview_password = (TextView) view.findViewById(R.id.fragment_profile_page_textview_password);
        fragment_profile_page_textview_new_password = (TextView) view.findViewById(R.id.fragment_profile_page_textview_new_password);
        fragment_profile_page_textview_confirm_password = (TextView) view.findViewById(R.id.fragment_profile_page_textview_confirm_password);
        fragment_profile_page_gridview_hobbies = (ExpandableHeightGridView) view.findViewById(R.id.fragment_profile_page_gridview_hobbies);
        fragment_profile_page_progressbar_hobbies = (ProgressBar) view.findViewById(R.id.fragment_profile_page_progressbar_hobbies);
        jsonfunctions = new Jsonfunctions(getActivity());
        aQuery = new AQuery(getActivity());
        sharedPrefrences = new SharedPrefrences();
        dots = new ArrayList<>();
//        bundle = new Bundle();
        bundle = getArguments();

        ((DoSomethingStatus) getActivity()).passwordMatch(true);
//        bundle.getStringArrayList("array_bundle_hobbies_name");
        bundle.getIntegerArrayList("array_bundle_hobbies_image");
        Log.d("DOSOMETHING_hobbies_id", "bundle_hobbiesId" + bundle.getIntegerArrayList("array_bundle_hobbies_image"));

//        this.initialisePaging();
        text_font_typeface();


        pd = new TransparentProgressDialog(getActivity(), getResources().getDrawable(R.drawable.loading));

        String date = sharedPrefrences.getDateOfbirth(getActivity());
        SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
        SimpleDateFormat output = new SimpleDateFormat("dd / MM / yyyy",Locale.US);
        try {
            Log.d("tripDate", date);
            Date oneWayTripDate = input.parse(date);                 // parse input
            String tripDate = output.format(oneWayTripDate);// format output
            Log.d("tripDate", tripDate);
            sharedPrefrences.setDateofBirth(getActivity(), tripDate);
            fragment_profile_page_textview_datepicker.setText(sharedPrefrences.getDateOfbirth(getActivity()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (getActivity() != null) {
            if (sharedPrefrences.getRegistervia(getActivity()).equals("facebook")) {
                if (sharedPrefrences.getShowPassword(getActivity()).equals("no")) {
                    fragment_profile_page_textview_title_account.setVisibility(View.GONE);
                    dosomething_account_login_via_email.setVisibility(View.GONE);
                }

                dosomething_login_via_imageview.setImageDrawable(getResources().getDrawable(R.drawable.fb_login_icon));
                dosomething_login_via_textview.setText("You are connected via Facebook");

            }
            fragment_profile_page_edittext_firstname.setText(sharedPrefrences.getFirstName(getActivity()));
            fragment_profile_page_edittext_lastname.setText(sharedPrefrences.getLastname(getActivity()));
            fragment_profile_page_edittext_gender.setText(sharedPrefrences.getGender(getActivity()));
            fragment_profile_page_edittext_about_you.setText(sharedPrefrences.getAbout(getActivity()));
            fragment_profile_page_edittext_email.setText(sharedPrefrences.getEMAIl(getActivity()));
//        fragment_profile_page_edittext_password.setText(sharedPrefrences.getpassword(getActivity()));
            sessionid = sharedPrefrences.getSessionid(getActivity());
            profile_user_id = sharedPrefrences.getUserId(getActivity());
            Log.d("profile_user_id", sharedPrefrences.getUserId(getActivity()));


            new AsynDataClass().execute();
        }






        if (fragments.size() == 3) {
            fragment_profile_image_autoincrease.setClickable(false);
        }

        fragment_profile_image_autoincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {







/*userProfileImage1_fragment=new UserProfileImage1_Fragment();
                userProfileImage1_fragment.showImageSelectionAlert();*/


                if (fragments.size() == 0) {
                    fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), UserProfileImage1_Fragment.class.getName()));
                    mPagerAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
                    fragment_pager.setAdapter(mPagerAdapter);
                    fragment_pager.setOffscreenPageLimit(3);
                    dots.clear();
                    fragment_viewpager_dots.removeAllViews();
                    popup=true;
                    addDots();

                } else if (fragments.size() == 1)

                {

                    fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), UserProfileImage2_Fragment.class.getName()));
                    mPagerAdapter.notifyDataSetChanged();
                    dots.clear();
                    fragment_viewpager_dots.removeAllViews();
                    popup=true;
                    addDots();




                } else if (fragments.size() == 2) {

                    fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), UserProfileImage3_Fragment.class.getName()));
                    mPagerAdapter.notifyDataSetChanged();
                    dots.clear();
                    fragment_viewpager_dots.removeAllViews();
                    popup=true;
                    addDots();


                }

            }
        });




////////////////////////////////////////Hobbies/////////////////////////////////////////////////////

        try {


            hobbies_list = new ArrayList<>();
            name = new ArrayList<Integer>();
            hobbies_list.clear();
            name.clear();
            hobbies_list.addAll(bundle.getIntegerArrayList("array_bundle_hobbies_image"));

            name.addAll(bundle.getIntegerArrayList("array_bundle_hobbies_image"));
            Log.d("DOSOMETHING_hobbies_id", "name" + bundle.getIntegerArrayList("array_bundle_hobbies_image"));

            if (hobbies_list == null) {
                if (bundle == null) {
                    fragment_profile_page_imageview_hobbies.setVisibility(View.VISIBLE);
                    fragment_profile_page_gridview_hobbies.setVisibility(View.GONE);
                    Log.d("DOSOMETHING_hobbies_id", "hobbies_list_null");
                    //intent to hobbies page
                    fragment_profile_page_imageview_hobbies.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(), DoSomethingHobbies.class);
                            sharedPrefrences.setHobbies(getActivity(), "Yes");
                            sharedPrefrences.setBooleam(getActivity(), "");
                            ((DoSomethingStatus) getActivity()).passwordMatch(true);

                            bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
                            Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                            i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                            startActivity(i);
                        }
                    });
                } else {

                    fragment_profile_page_imageview_hobbies.setVisibility(View.GONE);
                    bundle = getArguments();
                    Log.d("DOSOMETHING_hobbies_id", "notnull");


                    Log.d("DOSOMETHING_hobbies_id", ":::::::::::" + name);

                    if (name.get(0) == R.drawable.pluis_icon) {
                        fragment_profile_page_imageview_hobbies.setVisibility(View.VISIBLE);
                        fragment_profile_page_gridview_hobbies.setVisibility(View.GONE);
                        Log.d("DOSOMETHING_hobbies_id", "bundle_notnull");
                        fragment_profile_page_imageview_hobbies.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getActivity(), DoSomethingHobbies.class);
                                sharedPrefrences.setHobbies(getActivity(), "Yes");
                                sharedPrefrences.setBooleam(getActivity(), "");
                                ((DoSomethingStatus) getActivity()).passwordMatch(true);
                                bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
                                Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                                i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                                startActivity(i);
                            }
                        });
                    } else {
                        fragment_profile_page_imageview_hobbies.setVisibility(View.GONE);
                        fragment_profile_page_gridview_hobbies.setVisibility(View.VISIBLE);
                        Log.d("DOSOMETHING_hobbies_id", "HHHHHHHHHHHHHelse");
                        new Hobbies_getApi().execute();

                    }
                    Log.d("BUNDLE", ">>>>>>>>>>>>>>>>" + name + "--" + image);

                }
            } else {
                if (bundle == null) {
                    Log.d("DOSOMETHING_hobbies_id", "bundle_null");
                    fragment_profile_page_imageview_hobbies.setVisibility(View.VISIBLE);
                    fragment_profile_page_gridview_hobbies.setVisibility(View.GONE);
                    //intent to hobbies page
                    Toast.makeText(getActivity(), "VISIBLE", Toast.LENGTH_SHORT).show();
                    fragment_profile_page_imageview_hobbies.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(), DoSomethingHobbies.class);
                            sharedPrefrences.setHobbies(getActivity(), "Yes");
                            sharedPrefrences.setBooleam(getActivity(), "");
                            ((DoSomethingStatus) getActivity()).passwordMatch(true);

                            bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
                            Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                            i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                            startActivity(i);

                        }
                    });
                } else {
                    Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHelseelse1");

                    fragment_profile_page_imageview_hobbies.setVisibility(View.GONE);
                    name.clear();
                    name.addAll(bundle.getIntegerArrayList("array_bundle_hobbies_image"));
                    Log.d("DOSOMETHING_hobbies_id", "bundle" + bundle.getIntegerArrayList("array_bundle_hobbies_image"));
                    if (name.get(0) == R.drawable.pluis_icon) {
                        Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHelseif");
                        fragment_profile_page_imageview_hobbies.setVisibility(View.VISIBLE);
                        fragment_profile_page_gridview_hobbies.setVisibility(View.GONE);
                        fragment_profile_page_imageview_hobbies.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                name.clear();
                                Intent i = new Intent(getActivity(), DoSomethingHobbies.class);
                                sharedPrefrences.setHobbies(getActivity(), "Yes");
                                sharedPrefrences.setBooleam(getActivity(), "");
                                ((DoSomethingStatus) getActivity()).passwordMatch(true);

//                            bundle.putStringArrayList("array_bundle_hobbies_name", name);
                                bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);
//                            i.putStringArrayListExtra("array_bundle_hobbies_name", name);
                                Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                                i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                                startActivity(i);
                            }
                        });
                    } else {
                        Log.d("GGGGGGGGGGG", "HHHHHHHHHHHHHelseelse");
                        //custom grid view
                        fragment_profile_page_imageview_hobbies.setVisibility(View.GONE);
                        fragment_profile_page_gridview_hobbies.setVisibility(View.VISIBLE);

                        new Hobbies_getApi().execute();

                    }
                    Log.d("BUNDLE", ">>>>>>>>>>>>>>>>" + name + "--" + image);


                }

            }
            fragment_profile_page_gridview_hobbies.setExpanded(true);
            fragment_profile_page_gridview_hobbies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("POSITION", "-------------->>>" + position + "--" + hobbies_list.size());
                    if (position == hobbies_list.size()) {
                        Intent i = new Intent(getActivity(), DoSomethingHobbies.class);
                        sharedPrefrences.setHobbies(getActivity(), "Yes");
                        sharedPrefrences.setBooleam(getActivity(), "");
                        ((DoSomethingStatus) getActivity()).passwordMatch(true);
                        bundle = new Bundle();
                        bundle.putIntegerArrayList("array_bundle_hobbies_image", hobbies_list);

                        Log.d(";;;;;;;", ":::::::::::" + hobbies_list);
                        i.putIntegerArrayListExtra("array_bundle_hobbies_image", hobbies_list);
                        startActivity(i);
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }



        ////////////////////////////////////////////////////////////////////////////////////////////
        fragment_profile_page_edittext_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                sharedPrefrences.setPassword(getActivity(), fragment_profile_page_edittext_password.getText().toString());
            }
        });
        fragment_profile_page_edittext_about_you.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ((DoSomethingStatus) getActivity()).passwordMatch(true);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((DoSomethingStatus) getActivity()).passwordMatch(true);

            }

            @Override
            public void afterTextChanged(Editable s) {
                sharedPrefrences.setAbout(getActivity(), fragment_profile_page_edittext_about_you.getText().toString());
                sharedPrefrences.setBooleam(getActivity(), "");

            }
        });
        fragment_profile_page_edittext_confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ((DoSomethingStatus) getActivity()).passwordMatch(true);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((DoSomethingStatus) getActivity()).passwordMatch(true);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!fragment_profile_page_edittext_new_password.getText().toString().equals("")) {
                    if (fragment_profile_page_edittext_new_password.getText().toString().equals(fragment_profile_page_edittext_confirm_password.getText().toString())) {
                        sharedPrefrences.setUpdatePassword(getActivity(), fragment_profile_page_edittext_confirm_password.getText().toString());
                        Toast.makeText(getActivity(), "Confirm password matched", Toast.LENGTH_SHORT).show();
                        sharedPrefrences.setBooleam(getActivity(), "");
                        ((DoSomethingStatus) getActivity()).passwordMatch(true);


                    } else {
                        ((DoSomethingStatus) getActivity()).passwordMatch(false);

//                Toast.makeText(getActivity(), "Confirm password no match", Toast.LENGTH_SHORT).show();
                    }


                } else if (!fragment_profile_page_edittext_new_password.getText().toString().equals("") && fragment_profile_page_edittext_confirm_password.getText().toString().equals("")) {

                    if (getActivity() != null) {
//                final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
//                dialog.setMessage("please enter valid password");
//                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                        // TODO Auto-generated method stub
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
                    }

                }
            }
        });

        return view;
    }


    public class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;


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

        List<android.support.v4.app.Fragment> fragments = new Vector<Fragment>();
        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), UserProfileImage1_Fragment.class.getName()));
        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), UserProfileImage2_Fragment.class.getName()));
        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), UserProfileImage3_Fragment.class.getName()));
        this.mPagerAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
        fragment_pager.setAdapter(this.mPagerAdapter);
        fragment_pager.setOffscreenPageLimit(3);

    }

    public void addDots() {

        try
        {


            Log.d("num_pages", "______" + NUM_PAGES);
            Log.d("num_dots", "______" + dots.size());
            for (int i = 0; i < fragments.size(); i++) {
                ImageView dot = new ImageView(getActivity());
                if(i==fragment_pager.getCurrentItem())
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
                params.setMargins(2, 5, 2, 5);
                fragment_viewpager_dots.addView(dot, params);
                dots.add(dot);
                Log.d("num_dots2", "______" + dots.size());

            }
            if(popup)
            {
                fragment_pager.setCurrentItem(fragment_pager.getCurrentItem() + 1, true);
            }



        }catch (Exception e)
        {
            e.printStackTrace();
        }



        fragment_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                popup=false;




            }

            @Override
            public void onPageSelected(int position) {

                selectDot(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(popup)
                {
                    switch (fragment_pager.getCurrentItem()+1)
                    {
                        case 2:
                            if(((MyApplication)getActivity().getApplication()).getUserProfileImage2_fragment()!=null)
                            {
                                ((MyApplication)getActivity().getApplication()).getUserProfileImage2_fragment().showImageSelectionAlert();
                            }

                            break;
                        case 3:
                            if(((MyApplication)getActivity().getApplication()).getUserProfileImage3_fragment()!=null)
                            {
                                ((MyApplication)getActivity().getApplication()).getUserProfileImage3_fragment().showImageSelectionAlert();
                            }

                            break;

                    }
                }

            }
        });
    }

    public void selectDot(int idx) {
        Resources res = getResources();
        for (int i = 0; i < fragments.size(); i++) {
            System.out.println("dots_pos..." + idx);
            int drawableId = (i == idx) ? (R.drawable.dot1) : (R.drawable.dot1_active);
            Drawable drawable = res.getDrawable(drawableId);
            drawable.setBounds(0, 0, 0, 0);
            dots.get(i).setImageDrawable(drawable);
        }




    }


    private class HobbiesGrid extends BaseAdapter {


        Context context;

        ArrayList<String> hobbies_name = new ArrayList<>();

        ArrayList<String> hobbies_image = new ArrayList<>();
        ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<>();

        public HobbiesGrid(Activity activity, ArrayList<HobbiesBean> hobbiesBeans) {

            this.context = activity;
            this.hobbiesBeans = hobbiesBeans;


        }


        public HobbiesGrid(Activity activity, ArrayList<HobbiesBean> hobbiesBeans, ArrayList<String> image, ArrayList<String> name) {

            this.context = activity;
            this.hobbiesBeans = hobbiesBeans;

            this.hobbies_name = name;

            this.hobbies_image = image;

        }


        @Override

        public int getCount() {

            return hobbies_image.size();

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

            ImageView grid_layout_profile_imageview_hobbies;

            TextView grid_layout_profile_textview_name;


        }

        @Override

        public View getView(int position, View convertView, ViewGroup parent) {

            View grid;
            final Holder holder = new Holder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                grid = new View(context);

                grid = inflater.inflate(R.layout.grid_layout_profile, parent, false);

                holder.grid_layout_profile_textview_name = (TextView) grid.findViewById(R.id.grid_layout_profile_textview_name);

                holder.grid_layout_profile_imageview_hobbies = (ImageView) grid.findViewById(R.id.grid_layout_profile_imageview_hobbies);

                holder.grid_layout_profile_textview_name.setText(hobbies_name.get(position));

                holder.grid_layout_profile_textview_name.setTextSize(8);
//                grid_layout_profile_textview_name.setText(hobbies_name.get(position));


                aQuery.id(holder.grid_layout_profile_imageview_hobbies).image(hobbies_image.get(position), true, true, 0, 0, new BitmapAjaxCallback() {

                    @Override

                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {

                        if (status.getCode() == 200) {

                            BitmapDrawable bd = new BitmapDrawable(bm);

                            iv.setBackgroundDrawable(bd);

                            holder.grid_layout_profile_textview_name.setTextColor(getActivity().getResources().getColor(R.color.text_grey));

                        }

                    }

                });


                holder.grid_layout_profile_imageview_hobbies.setScaleType(ImageView.ScaleType.CENTER_CROP);

            } else {

                grid = (View) convertView;

            }

            return grid;

        }

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
    ////////////////////////////////HOBBIES////////////


    private class AsynDataClass extends AsyncTask<Void, Void, Boolean> {

        private String first_name, last_name, gender, about;
        private String email;
        private String status;
        private String device;
        private int age;
        private int user_id;
        private String profile_id;
        private String image2;
        private String image1;
        private String image3;
        private String device_token;
        private String date_of_birth;
        private String image1_thumb;
        private String image2_thumb;
        private String image3_thumb;
        String getuserdetails_Api_url;
        Exception error;

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
//            pd.show();
            if (getActivity() != null) {
                getuserdetails_Api_url = getActivity().getResources().getString(R.string.dosomething_apilink_string_getuserdetails);
            }
        }


        @Override

        protected Boolean doInBackground(Void... params) {


            try {

                HashMap<String, Object> paramsfb = new HashMap<>();
                paramsfb.put(TAG_SESSIONID, sessionid);

                paramsfb.put(TAG_PROFILEUSERID, profile_user_id);

                json_string = jsonfunctions.postToURL(getuserdetails_Api_url, paramsfb);

                Log.v("jason url=======>", sessionid);


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

                        age = Integer.parseInt(userlistobject.getString("age"));

                        image2 = userlistobject.getString("image2");

                        image1 = userlistobject.getString("image1");

                        image3 = userlistobject.getString("image3");

                        device_token = userlistobject.getString("device_token");

                        date_of_birth = userlistobject.getString("date_of_birth");
                        password = userlistobject.getString("password");

                        if (userlistobject.has("image1_thumb")) {
                            image1_thumb = userlistobject.getString("image1_thumb");
                        }
                        if (userlistobject.has("image2_thumb")) {
                            image2_thumb = userlistobject.getString("image2_thumb");
                        }
                        if (userlistobject.has("image3_thumb")) {
                            image3_thumb = userlistobject.getString("image3_thumb");
                        }


                        JSONArray hobbiesArray = userlistobject.getJSONArray("hobbieslist");
                        for (int j = 0; j < hobbiesArray.length(); j++) {
                            JSONObject details = hobbiesArray.getJSONObject(j);
                            int id = details.getInt("hobbies_id");
                            int category_id = details.getInt("category_id");
                            String name = details.getString("name");
                            String Image = details.getString("image");
                            String ActiveImage = details.getString("image_active");


                        }


//                                profileBeans.add(new ProfileBean(user_id, profile_id, first_name, last_name, gender, about, email, status, device, age, image2, image1, image3, device_token, date_of_birth));
//                                sharedPrefrences.setFirstname(getActivity(), first_name);
//                                sharedPrefrences.setLastname(getActivity(), last_name);
//                                sharedPrefrences.setGender(getActivity(), gender);
//                                sharedPrefrences.setAbout(getActivity(), about);
//                                sharedPrefrences.setLogin(getActivity(), email);
//                                sharedPrefrences.setProfilePicture(getActivity(), image1);
//                                sharedPrefrences.setProfilePicture1(getActivity(), image2);
//                                sharedPrefrences.setProfilePicture2(getActivity(), image3);

                        Log.d("BEAN", "GHGHGH" + user_id);
                        Log.d("BEAN", "GHGHGH" + profile_id);

                        Log.d("BEAN", "GHGHGH" + first_name);

                        Log.d("BEAN", "GHGHGH" + last_name);

                        Log.d("BEAN", "GHGHGH" + gender);

                        Log.d("BEAN", "GHGHGH" + about);

                        Log.d("BEAN", "GHGHGH" + email);

                        Log.d("BEAN", "GHGHGH" + status);


                        Log.d("BEAN", "GHGHGH" + image1);
                        Log.d("BEAN", "GHGHGH" + date_of_birth);


                    } else if (json_content.getString("status").equalsIgnoreCase("failed")) {


                    } else if (json_content.getString("status").equalsIgnoreCase("error")) {


                    }

                }
                if (json_object.has("error")) {
                    if (json_object.getString("error").equalsIgnoreCase("InvalidSession")) {
                        pd.dismiss();

                        status = "InvalidSession";

                    }
                }
                return true;
            } catch (JSONException e) {

                e.printStackTrace();
                return false;
            }


        }


        @Override

        protected void onPostExecute(Boolean result) {

            super.onPostExecute(result);

            if (result) {
                try {
                    if (!status.equalsIgnoreCase("InvalidSession")) {


                        fragment_profile_page_edittext_firstname.requestFocus();
//                hobbies_image.add(String.valueOf(R.drawable.pluis_icon));
//                hobbies_name.add("");
//                Log.d("BEAN3", "GHGHGH" + hobbies_name.size());
//                Log.d("BEAN4", "GHGHGH" + hobbies_image.size());
//                HobbiesGrid hobbiesGrid1 = new HobbiesGrid(getActivity(), hobbiesBeans, hobbies_image, hobbies_name);
//                fragment_profile_page_gridview_hobbies.setAdapter(hobbiesGrid1);
//                fragment_profile_page_gridview_hobbies.setExpanded(true);
//                fragment_profile_page_gridview_hobbies.deferNotifyDataSetChanged();
                        if (getActivity() != null) {

                            sharedPrefrences.setProfilePicture(getActivity(), image1);
                            sharedPrefrences.setProfilePicture1(getActivity(), image2);
                            sharedPrefrences.setProfilePicture2(getActivity(), image3);




                        }
                        fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), UserProfileImage1_Fragment.class.getName()));
                        mPagerAdapter = new PagerAdapter(getChildFragmentManager(), fragments);
                        fragment_pager.setAdapter(mPagerAdapter);
                        fragment_pager.setOffscreenPageLimit(3);
                        dots.clear();
                        fragment_viewpager_dots.removeAllViews();
                        addDots();
                        selectDot(fragment_pager.getCurrentItem());




                        if (!sharedPrefrences.getProfilePicture1(getActivity()).equals("")) {
                            fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), UserProfileImage2_Fragment.class.getName()));
                            mPagerAdapter.notifyDataSetChanged();
                            NUM_PAGES=2;
                            dots.clear();
                            fragment_viewpager_dots.removeAllViews();

                            addDots();

                        }

                        if (!sharedPrefrences.getProfilePicture2(getActivity()).equals("")) {
                            fragments.add(android.support.v4.app.Fragment.instantiate(getActivity(), UserProfileImage3_Fragment.class.getName()));
                            mPagerAdapter.notifyDataSetChanged();
                            NUM_PAGES=3;
                            dots.clear();
                            fragment_viewpager_dots.removeAllViews();

                            addDots();

                        }





                        if (getActivity() != null) {
                            sharedPrefrences.setPassword(getActivity(), password);
                            String date = date_of_birth;
//                    String date ="29/07/13";
                            SimpleDateFormat input = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
                            SimpleDateFormat output = new SimpleDateFormat("dd / MM / yyyy",Locale.US);
                            try {
                                Log.d("tripDate", date);
                                oneWayTripDate = input.parse(date);                 // parse input
                                tripDate = output.format(oneWayTripDate);// format output
                                Log.d("tripDate", tripDate);
                                sharedPrefrences.setDateofBirth(getActivity(), tripDate);
                                fragment_profile_page_textview_datepicker.setText(sharedPrefrences.getDateOfbirth(getActivity()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }



                            Log.d("image1", sharedPrefrences.getProfilePicture(getActivity()));
                            Log.d("image2", sharedPrefrences.getProfilePicture1(getActivity()));
                            Log.d("image3", sharedPrefrences.getProfilePicture2(getActivity()));
                        }

                    } else {
                        Intent i = new Intent(getActivity(), DoSomeThingLogin.class);
                        startActivity(i);
                        getActivity().finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (error != null) {
                    new AsynDataClass().execute();
                }
            }


        }


    }



    private void text_font_typeface() {
        Typeface patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");
        Typeface patron_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Regular.ttf");
        Typeface patron_medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Medium.ttf");
        Typeface myfonts_thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Thin.ttf");

        fragment_profile_page_textview_datepicker.setTypeface(patron_medium);
        fragment_profile_page_textview_title_profile.setTypeface(patron_medium);
        fragment_profile_page_textview_firstname.setTypeface(patron_medium);
        fragment_profile_page_textview_lastname.setTypeface(patron_medium);
        fragment_profile_page_textview_gender.setTypeface(patron_medium);
        fragment_profile_page_textview_dateofbirth.setTypeface(patron_medium);
        fragment_profile_page_textview_about_you.setTypeface(patron_medium);
        fragment_profile_page_textview_hobbies.setTypeface(patron_medium);
        fragment_profile_page_textview_title_account.setTypeface(patron_medium);
        fragment_profile_page_textview_email.setTypeface(patron_medium);
        fragment_profile_page_textview_password.setTypeface(patron_medium);
        fragment_profile_page_edittext_firstname.setTypeface(patron_medium);
        fragment_profile_page_edittext_lastname.setTypeface(patron_medium);
        fragment_profile_page_edittext_gender.setTypeface(patron_medium);
        fragment_profile_page_edittext_email.setTypeface(patron_medium);
        fragment_profile_page_edittext_password.setTypeface(patron_medium);
        fragment_profile_page_edittext_new_password.setTypeface(patron_medium);
        fragment_profile_page_textview_confirm_password.setTypeface(patron_medium);
        fragment_profile_page_textview_new_password.setTypeface(patron_medium);
        fragment_profile_page_edittext_confirm_password.setTypeface(patron_medium);
        fragment_profile_page_edittext_about_you.setTypeface(patron_medium);

    }


    public void cropCapturedImage() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(mImageCaptureUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 1000);
            cropIntent.putExtra("outputY", 1000);
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra("return-data", true);
//            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
//            cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            getParentFragment().startActivityForResult(cropIntent, CROP_FROM_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                Log.d("requestCode", String.valueOf(requestCode));
                Log.d("fragment===", fragment.getClass().getSimpleName());
                Log.d("fragments", String.valueOf(fragments.get(0).getChildFragmentManager().getClass().getSimpleName()));

                if (fragment.getClass().getSimpleName().equals("UserProfileImage1_Fragment") && requestCode == 1) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                } else if (fragment.getClass().getSimpleName().equals("UserProfileImage1_Fragment") && requestCode == 4) {
                    fragment.onActivityResult(requestCode, resultCode, data);

                } else if (fragment.getClass().getSimpleName().equals("UserProfileImage1_Fragment") && requestCode == 7) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                } else if (fragment.getClass().getSimpleName().equals("UserProfileImage2_Fragment") && requestCode == 2) {
                    Log.d("fragments", String.valueOf(fragments.get(1).getChildFragmentManager().getClass().getSimpleName()));
                    fragment.onActivityResult(requestCode, resultCode, data);
                } else if (fragment.getClass().getSimpleName().equals("UserProfileImage2_Fragment") && requestCode == 5) {
                    Log.d("fragments", String.valueOf(fragments.get(1).getChildFragmentManager().getClass().getSimpleName()));

                    fragment.onActivityResult(requestCode, resultCode, data);
                } else if (fragment.getClass().getSimpleName().equals("UserProfileImage2_Fragment") && requestCode == 8) {

                    Log.d("fragments", String.valueOf(fragments.get(1).getChildFragmentManager().getClass().getSimpleName()));
                    fragment.onActivityResult(requestCode, resultCode, data);
                } else if (fragment.getClass().getSimpleName().equals("UserProfileImage3_Fragment") && requestCode == 3) {
                    Log.d("fragments", String.valueOf(fragments.get(2).getChildFragmentManager().getClass().getSimpleName()));

                    fragment.onActivityResult(requestCode, resultCode, data);
                } else if (fragment.getClass().getSimpleName().equals("UserProfileImage3_Fragment") && requestCode == 6) {
                    Log.d("fragments", String.valueOf(fragments.get(2).getChildFragmentManager().getClass().getSimpleName()));

                    fragment.onActivityResult(requestCode, resultCode, data);
                } else if (fragment.getClass().getSimpleName().equals("UserProfileImage3_Fragment") && requestCode == 9) {
                    Log.d("fragments", String.valueOf(fragments.get(2).getChildFragmentManager().getClass().getSimpleName()));

                    fragment.onActivityResult(requestCode, resultCode, data);
                }


            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    class Hobbies_getApi extends AsyncTask<String, String, String> {
        JSONObject opj;
        JSONArray content, hobbieslist;
        String getHobbies_Api_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (getActivity() != null) {
                getHobbies_Api_url = getActivity().getResources().getString(R.string.dosomething_apilink_string_gethobbies);
                sessionid = sharedPrefrences.getSessionid(getActivity());
            }
        }

        protected String doInBackground(String... params) {

            try {


                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("sessionid", sessionid);
                response = jsonfunctions.postToURL(getHobbies_Api_url, map);
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


                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;

        }

        @SuppressLint("LongLogTag")
        protected void onPostExecute(String result) {
            //TODO Auto-generated method stub
            super.onPostExecute(result);
            if (hobbies_image != null) {
                hobbies_image.clear();
            }
            if (hobbies_name != null) {
                hobbies_name.clear();
            }

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
                try {
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
                } catch (Exception e) {
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
                Toast.makeText(getActivity(), "Bundle empty", Toast.LENGTH_LONG).show();
            }

            fragment_profile_page_edittext_firstname.requestFocus();

            Log.d("KKKK", "JJJJJ" + hobbies_image + "--" + hobbies_name);
            hobbies_image.add(String.valueOf(R.drawable.pluis_icon));
            hobbies_name.add("");
            HobbiesGrid customGrid = new HobbiesGrid(getActivity(), hobbiesBeans, hobbies_image, hobbies_name);
            fragment_profile_page_gridview_hobbies.setAdapter(customGrid);
            fragment_profile_page_gridview_hobbies.setExpanded(true);
            fragment_profile_page_gridview_hobbies.deferNotifyDataSetChanged();
            if (getActivity() != null) {
                if (!hobbies_append_id.equals("")) {
                    sharedPrefrences.setHobbiesId(getActivity(), hobbies_append_id);
                    Log.d("dosomething_fragment_profile_", "hobbies_append_id" + sharedPrefrences.setHobbiesId(getActivity(), hobbies_append_id));
                } else {
                    new Hobbies_getApi().execute();
                }

            }


        }
    }


}


