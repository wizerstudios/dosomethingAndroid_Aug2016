package com.dosomething.android.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.dosomething.android.Beanclasses.ChatBean;
import com.dosomething.android.Beanclasses.ConversationBean;
import com.dosomething.android.Beanclasses.Dosomething_Bean;
import com.dosomething.android.Beanclasses.HobbiesBean;
import com.dosomething.android.CommonClasses.EmojiTextView;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.dosomething.android.DoSomeThingLogin;
import com.dosomething.android.DoSomethingStatus;
import com.dosomething.android.Message;
import com.dosomething.android.MyApplication;
import com.dosomething.android.R;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzip.runOnUiThread;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoSomethingChatBox.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoSomethingChatBox#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoSomethingChatBox extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RelativeLayout fragment_dosomething_chatbox_layout;
    LinearLayout dosomething_fragment_chatbox_deleteorblock, chatbox_block_screen;
    EditText dosomething_fragment_chatbox_messagebox;
    RecyclerView dosomething_fragment_chat_messages_list;
    TextView dosomething_fragment_chat_box_personname;
    TextView dosomething_fragment_chat_box_person_status;
    ArrayList<Message> messages;
    RecyclerAdapter adapter;
    Typeface patron_bold;
    Typeface patron_regular;
    Typeface patron_medium;
    Typeface myfonts_thin;
    TextView activity_dosomething_chatblock;
    TextView activity_dosomething_chatdelete;
    TextView activity_dosomething_chatcancel;
    ArrayList<ConversationBean> conversationBeans = new ArrayList<>();
    ImageView activity_dosomething_chatbox_messagesent_button;
    private String urlchatHistory = "http://wiztestinghost.com/dosomething/getconversation?";
    private String urlsendchat = "http://wiztestinghost.com/dosomething/sendmessage?";
    private static final String TAG_SESSIONID = "sessionid";
    private static final String TAG_FRIENDUSERID = "request_send_user_id";
    private static final String TAG_CONVERSATIONID = "conversationId";
    private static final String TAG_RECEIVERID = "message_receiver_id";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_TIME = "datetime";
    private static final String TAG_PROFILEUSERID = "profile_user_id";
    private String profile_user_id;
    private String sessionid, json_string;
    Jsonfunctions jsonfunctions;
    JSONObject json_object, json_content, details;
    private TransparentProgressDialog pd;
    SharedPrefrences sharedPrefrences;
    ArrayList<ChatBean> chatBeans = new ArrayList<>();
    TextView chat_noconversation_textview;
    AQuery aQuery;
    ImageView dosomething_fragment_chat_box_personImage;
    String message;
    private String sender_Message;
    private String sender_Time;
    private String receiver_Message;
    TransparentProgressDialog dosomething_chatbox_progress;
    TextView dosomething_fragment_chat_messages_textview;
    Boolean isLoader = false;
    private Timer timer;
    private Handler handler;
    RelativeLayout relative_image;
    RelativeLayout layout_walkthrough_profile;
    ImageView image_walkthrough_chat;
    private AnimationDrawable splashAnimation;
    private ImageView kbv;
    private String datetime;
    private String datetime_device;
    private LinearLayoutManager layoutManager;
    private AnimationDrawable splashAnimation_chat;
    private Timer blink_time;
    private Tracker mTracker;
    private String formattedDate;
    private LinearLayout activity_dosomething_profile_view_layout;
    private ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<>();
    private ArrayList<Dosomething_Bean> dosomething_beans = new ArrayList<>();
    private DoSomething_Friends_profile_fragment doSomethingFriendsProfileFragment;
    private FragmentTransaction fragmentTransaction;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoSomethingChatBox.
     */
    // TODO: Rename and change types and number of parameters
    public static DoSomethingChatBox newInstance(String param1, String param2) {
        DoSomethingChatBox fragment = new DoSomethingChatBox();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DoSomethingChatBox() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (((MyApplication) getActivity().getApplication()).getDoSomethingStatus() != null) {
            ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().setstate("No");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_do_something_chat_box, container, false);

        try {
            MyApplication application = (MyApplication) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sharedPrefrences = new SharedPrefrences();
        jsonfunctions = new Jsonfunctions(getActivity());
        aQuery = new AQuery(getActivity());
        handler = new Handler();
        timer = new Timer();
        dosomething_chatbox_progress = new TransparentProgressDialog(getActivity(), getResources().getDrawable(R.drawable.loading));

        kbv = (ImageView) view.findViewById(R.id.image);
        kbv.setBackgroundResource(R.drawable.progress_drawable);

        splashAnimation = (AnimationDrawable) kbv.getBackground();
        relative_image = (RelativeLayout) view.findViewById(R.id.relative_image);
        layout_walkthrough_profile = (RelativeLayout) view.findViewById(R.id.layout_walkthrough_profile);
        image_walkthrough_chat = (ImageView) view.findViewById(R.id.image_walkthrough_chat);
        dosomething_fragment_chatbox_deleteorblock = (LinearLayout) view.findViewById(R.id.dosomething_fragment_chatbox_deleteorblock);
        chatbox_block_screen = (LinearLayout) view.findViewById(R.id.chatbox_block_screen);
        dosomething_fragment_chatbox_messagebox = (EditText) view.findViewById(R.id.dosomething_fragment_chatbox_messagebox);
        dosomething_fragment_chat_box_personname = (TextView) view.findViewById(R.id.dosomething_fragment_chat_box_personname);
        dosomething_fragment_chat_box_personImage = (ImageView) view.findViewById(R.id.dosomething_fragment_chat_box_personImage);
        dosomething_fragment_chat_box_person_status = (TextView) view.findViewById(R.id.dosomething_fragment_chat_box_person_status);
        dosomething_fragment_chat_messages_list = (RecyclerView) view.findViewById(R.id.dosomething_fragment_chat_messages_list);
        activity_dosomething_chatblock = (TextView) view.findViewById(R.id.activity_dosomething_chatblock);
        activity_dosomething_chatdelete = (TextView) view.findViewById(R.id.activity_dosomething_chatdelete);
        activity_dosomething_chatcancel = (TextView) view.findViewById(R.id.activity_dosomething_chatcancel);
        dosomething_fragment_chat_messages_textview = (TextView) view.findViewById(R.id.dosomething_fragment_chat_messages_textview);
        activity_dosomething_chatbox_messagesent_button = (ImageView) view.findViewById(R.id.activity_dosomething_chatbox_messagesent_button);
        activity_dosomething_profile_view_layout = (LinearLayout) view.findViewById(R.id.activity_dosomething_profile_view_layout);
        image_walkthrough_chat.setBackgroundResource(R.drawable.blink_icon);
        splashAnimation_chat = (AnimationDrawable) image_walkthrough_chat.getBackground();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        formattedDate = df.format(c.getTime());
        text_font_typeface();


//        if (sharedPrefrences.getFriendFirstName(getActivity()).equals("Support")) {
//            dosomething_fragment_chatbox_deleteorblock.setEnabled(false);
//            dosomething_fragment_chatbox_deleteorblock.setClickable(false);
//        }

        if (formattedDate.equals(sharedPrefrences.getDeviceDate(getActivity()))) {

            if (sharedPrefrences.getWalkThroughchat(getActivity()).equals("false")) {
                layout_walkthrough_profile.setVisibility(View.VISIBLE);
                blink_time = new Timer();
                blink_time.schedule(new Blink_progress(), 0, 340);
                splashAnimation_chat.start();
                sharedPrefrences.setWalkThroughchat(getActivity(), "true");
            }
        }
        layout_walkthrough_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_profile.setVisibility(View.GONE);
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation_chat.stop();
                sharedPrefrences.setWalkThroughchat(getActivity(), "true");
            }
        });
        image_walkthrough_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_walkthrough_profile.setVisibility(View.GONE);
                if (blink_time != null) {

                    blink_time.cancel();


                    blink_time = null;

                }
                splashAnimation_chat.stop();
                sharedPrefrences.setWalkThroughchat(getActivity(), "true");

            }
        });
        dosomething_fragment_chat_box_personname.setText(sharedPrefrences.getFriendFirstName(getActivity()));
        if (dosomething_fragment_chat_box_personname.getText().toString().trim().equalsIgnoreCase("Support")) {
            dosomething_fragment_chatbox_deleteorblock.setClickable(false);
            dosomething_fragment_chatbox_deleteorblock.setEnabled(false);
            activity_dosomething_profile_view_layout.setClickable(false);
            activity_dosomething_profile_view_layout.setEnabled(false);
        }
        messages = new ArrayList<>();
//        messages.add(new Message("Hello", false));
//        messages.add(new Message("Hi!", true));
//        messages.add(new Message("Wassup??", false));
//        messages.add(new Message("nothing much,Then?", true));
//        messages.add(new Message("you say!", true));
//        messages.add(new Message("oh thats great. how are you showing them", false));


        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dosomething_fragment_chat_messages_list.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(getActivity(), messages, conversationBeans);
        dosomething_fragment_chat_messages_list.setAdapter(adapter);
        dosomething_fragment_chat_messages_list.scrollToPosition(messages.size() - 1);
        try {

            if (!sharedPrefrences.getSessionid(getActivity()).equals("")) {
                callAsynchronousTask();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

      /* if(dosomething_fragment_chatbox_messagebox.hasFocus())
       {
           layoutManager.scrollToPosition(messages.size() - 1);
       }*/

        activity_dosomething_profile_view_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {


                    if (((MyApplication) getActivity().getApplication()).getDoSomethingStatus() != null) {
                        ((DoSomethingStatus) getActivity()).hideFilterIconVisible(true);
                        sharedPrefrences.setDosomething_filterImage_Visibility(getActivity(), "No");
                        /*sharedPrefrences.setFriendUserId(getActivity(), filterr_list.get(position).getuser_id());
                        sharedPrefrences.setFriendFirstname(getActivity(), filterr_list.get(position).getfirst_name());
                        sharedPrefrences.setFriendLastname(getActivity(), filterr_list.get(position).getlast_name());
                        sharedPrefrences.setFriendAge(getActivity(), filterr_list.get(position).getAge());
                        sharedPrefrences.setFriendProfilePicture(getActivity(), filterr_list.get(position).getimage1());
                        sharedPrefrences.setFriendProfilePicture1(getActivity(), filterr_list.get(position).getimage2());
                        sharedPrefrences.setFriendProfilePicture2(getActivity(), filterr_list.get(position).getimage3());
                        sharedPrefrences.setFriendAbout(getActivity(), filterr_list.get(position).getabout());
                        sharedPrefrences.setFriendGender(getActivity(), filterr_list.get(position).getgender());
                        sharedPrefrences.setSendRequest(getActivity(), filterr_list.get(position).getDoSomething());
                        SharedPrefrences.clearDosomethingitems(getActivity());
                        SharedPrefrences.setDosomethingItem(getActivity(), filterr_list.get(position).getDosomething_beans());
                        SharedPrefrences.clearHobbiesitems(getActivity());
                        SharedPrefrences.setHobbiesItem(getActivity(),filterr_list.get(position).getListHobbies());*/
                        sessionid = sharedPrefrences.getSessionid(getActivity());
                        profile_user_id = sharedPrefrences.getFriendUserId(getActivity());

                        new GetUserDetails().execute();

                        if (getActivity() != null) {
                            ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().settext("YES");

                        }
                    }
                }
            }
        });


        dosomething_fragment_chat_messages_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(dosomething_fragment_chat_messages_list.getWindowToken(), 0);
                if (getActivity() != null)
                    ((DoSomethingStatus) getActivity()).showHideBottomLayout(true);

                return false;
            }
        });


        activity_dosomething_chatbox_messagesent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (dosomething_fragment_chat_messages_textview.getVisibility() == View.GONE) {
                    sendMessage();
                    dosomething_fragment_chat_messages_list.scrollToPosition(messages.size() - 1);
                } else {
                    dosomething_fragment_chat_messages_textview.setText("Match again to send message");
                }


            }
        });
        dosomething_fragment_chatbox_messagebox.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            if (dosomething_fragment_chat_messages_textview.getVisibility() == View.GONE) {
                                sendMessage();
                                dosomething_fragment_chat_messages_list.scrollToPosition(messages.size() - 1);
                            } else {
                                dosomething_fragment_chat_messages_textview.setText("Match again to send message");
                            }

                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
        dosomething_fragment_chatbox_deleteorblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(dosomething_fragment_chatbox_deleteorblock.getWindowToken(), 0);
                Log.d("dosomething_support", "onClick" + v);
                if (!dosomething_fragment_chat_box_personname.getText().toString().trim().equalsIgnoreCase("Support")) {
                    Log.d("dosomething_not_support", "onClick" + v);

                    fadeIn_menu();
                }

            }
        });

        activity_dosomething_chatblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut_block();
                chatbox_block_screen.setVisibility(View.GONE);

                if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                    new DosomethingBlockUser().execute();
                } else {
                    NetworkCheck.alertdialog(getActivity());
                }


            }
        });
        activity_dosomething_chatdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut_block();
                chatbox_block_screen.setVisibility(View.GONE);
                if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                    new DosomethingDeleteConversation().execute();
                } else {
                    NetworkCheck.alertdialog(getActivity());
                }
            }
        });
        activity_dosomething_chatcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut_block();
                chatbox_block_screen.setVisibility(View.GONE);
            }
        });

        dosomething_fragment_chatbox_messagebox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getActivity() != null)
                    ((DoSomethingStatus) getActivity()).showHideBottomLayout(false);
                return false;
            }
        });
        return view;
    }


    class Blink_progress extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                public void run() {

                    try {
                        if (splashAnimation_chat.isRunning()) {
                            splashAnimation_chat.stop();
                        } else {
                            splashAnimation_chat.start();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO Auto-generated catch block
                    }


                }

            });
        }
    }

    public void fadeIn_menu() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setStartOffset(0);
        fadeIn.setDuration(200);


        AnimationSet animation = new AnimationSet(false); //change to false

        animation.addAnimation(fadeIn);
        chatbox_block_screen.setVisibility(View.VISIBLE);
        chatbox_block_screen.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                dosomething_fragment_chatbox_deleteorblock.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                chatbox_block_screen.clearAnimation();
            }

        });

    }


    public void fadeOut_block() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(0);
        fadeOut.setDuration(500);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeOut);
        chatbox_block_screen.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dosomething_fragment_chatbox_deleteorblock.setVisibility(View.VISIBLE);
                chatbox_block_screen.clearAnimation();
            }

        });

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public void sendMessage() {
        String newMessage = dosomething_fragment_chatbox_messagebox.getText().toString().trim();
        if (newMessage.length() > 0) {
            dosomething_fragment_chatbox_messagebox.setText("");
            /*Calendar c = Calendar.getInstance();
            Date seconds = c.getTime();
            String date = String.valueOf(seconds);
            String[] part = date.split(" ");
            String parts1 = part[0];
            final String parts2 = part[1];
            final String parts3 = part[2];
            final String parts4 = part[3];
            Log.v("Data2", "" + parts4);
            Log.d("dosmething", "chat_time" + parts4);
            String[] chat_time = parts4.split(":");
            String chat_time1 = chat_time[0];
            final String chat_time2 = chat_time[1];
            sender_Time = chat_time1 + ":" + chat_time2;*/
            Date d = new Date();
            CharSequence s = DateFormat.format("yyyy/MM/dd HH:mm a", d.getTime());
            CharSequence deviceDate = DateFormat.format("dd/MM/yyyy h:mm a", d.getTime());
            datetime = String.valueOf(s);
            datetime_device = String.valueOf(deviceDate);
            message = newMessage;
            String[] part = datetime_device.split(" ");
            String parts1 = part[0];
            final String parts2 = part[1];
            final String parts3 = part[2];
            String date = "today" + " " + parts2 + " " + parts3;
            addNewMessage(new Message(newMessage, date, date, true));
            dosomething_fragment_chat_messages_list.setVisibility(View.VISIBLE);
            if (getActivity() != null) {
                if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                    new SendMessage().execute();
                } else {
                    NetworkCheck.alertdialog(getActivity());
                }
            }


            dosomething_fragment_chat_messages_list.scrollToPosition(messages.size() - 1);
        }
    }

    void addNewMessage(Message m) {
        messages.add(m);
        adapter.notifyDataSetChanged();
    }

    private void text_font_typeface() {
        patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");
        patron_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Regular.ttf");
        patron_medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Medium.ttf");
        myfonts_thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Thin.ttf");
        dosomething_fragment_chat_box_personname.setTypeface(patron_bold);
        dosomething_fragment_chat_box_person_status.setTypeface(patron_regular);
        activity_dosomething_chatblock.setTypeface(patron_regular);
        activity_dosomething_chatcancel.setTypeface(patron_regular);
        activity_dosomething_chatdelete.setTypeface(patron_regular);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void callAsynchronousTask() {
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {


                            Date d = new Date();
                            CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                            datetime = String.valueOf(s);

                            Log.d("date and time", datetime);
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {

                                new ChatConversation().execute();
                            }


                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 7000); //execute in every 50000 ms
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DataObjectHolder> {
        private Context mContext;
        private ArrayList<Message> mMessages;
        ArrayList<ConversationBean> conversationBeans = new ArrayList<>();
        String display_date = "";

        public RecyclerAdapter(Context context, ArrayList<Message> messages, ArrayList<ConversationBean> conversationBeans) {
            super();
            this.mContext = context;
            this.mMessages = messages;
            this.conversationBeans = conversationBeans;
            text_font_typeface();
        }


        public RecyclerAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflater.inflate(R.layout.message_chatlist, parent, false);
            return new DataObjectHolder(view);
        }

        @Override
        public void onBindViewHolder(final DataObjectHolder holder, int position) {

            try {
                Message message = mMessages.get(position);
                holder.dosomething_fragment_chat_message.setTypeface(patron_regular);
                holder.dosomething_fragment_chat_message_time.setTypeface(patron_regular);
                holder.dosomething_fragment_chat_message_date.setTypeface(patron_regular);
                holder.dosomething_fragment_chat_message.setEmojiText(message.getMessage());

                String date = message.getMessageDate();

                String[] apidate_format = date.split(" ");
                String apidate_format_part1 = apidate_format[0];
                String apidate_format_part2 = apidate_format[1];
                String apidate_format_part3 = apidate_format[2];
                Log.d("apidate", apidate_format_part1);

                Date d = new Date();
                CharSequence s = DateFormat.format("dd/MM/yyyy h:mm a", d.getTime());
                String currentdate = String.valueOf(s);
                String[] devicedate_formate = currentdate.split(" ");
                String devicedate_formate_part2 = devicedate_formate[0];
                Log.d("devicedate", "" + devicedate_formate_part2.length());
                if (apidate_format_part1.equals(devicedate_formate_part2)) {
                    holder.dosomething_fragment_chat_message_time.setText("today" + " " + apidate_format_part2 + " " + apidate_format_part3);
                    holder.dosomething_fragment_chat_message_date.setText("today" + " " + apidate_format_part2);
                } else {
                    holder.dosomething_fragment_chat_message_time.setText(message.getMessageTime());

                    holder.dosomething_fragment_chat_message_date.setText(display_date);
                }
                layoutManager.scrollToPosition(position);

                holder.dosomething_fragment_chat_message_date.setVisibility(View.GONE);

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.dosomething_fragment_chat_message.getLayoutParams();

                if (message.isStatusMessage()) {
                    holder.dosomething_fragment_chat_message.setBackgroundResource(R.drawable.round_edges_white);
                    lp.gravity = Gravity.START;
                    holder.dosomething_fragment_chat_message.setTextColor(getResources().getColor(R.color.black));
                    holder.dosomething_fragment_chat_message_time.setTextColor(getResources().getColor(R.color.black));
                } else {
                    //Check whether message is mine to show green background and align to right
                    if (message.isMine()) {
                        holder.dosomething_fragment_chat_message.setBackgroundResource(R.drawable.round_edges);

                        holder.dosomething_fragment_chat_message_time.setTextColor(getResources().getColor(R.color.text_red));
                        holder.dosomething_fragment_chat_message.setTextColor(getResources().getColor(R.color.white));
                        lp.gravity = Gravity.END;
                    }
                    //If not mine then it is from sender to show orange background and align to left
                    else {
                        holder.dosomething_fragment_chat_message.setBackgroundResource(R.drawable.round_edges_white);
                        holder.dosomething_fragment_chat_message.setTextColor(getResources().getColor(R.color.black));
                        holder.dosomething_fragment_chat_message_time.setTextColor(getResources().getColor(R.color.hint_color));
                        holder.dosomething_fragment_chat_message_layout.setGravity(Gravity.START | Gravity.LEFT);
                        holder.dosomething_fragment_chat_message_time.setGravity(Gravity.START | Gravity.LEFT);
                        lp.gravity = Gravity.START;
                    }
                    holder.dosomething_fragment_chat_message_layout.setLayoutParams(lp);
                }
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

            return mMessages.size();
        }

        public class DataObjectHolder extends RecyclerView.ViewHolder {
            LinearLayout dosomething_fragment_chat_message_layout;
            EmojiTextView dosomething_fragment_chat_message;
            TextView dosomething_fragment_chat_message_time;
            TextView dosomething_fragment_chat_message_date;

            public DataObjectHolder(View itemview) {
                super(itemview);
                dosomething_fragment_chat_message_layout = (LinearLayout) itemview.findViewById(R.id.dosomething_fragment_chat_message_layout);
                dosomething_fragment_chat_message = (EmojiTextView) itemview.findViewById(R.id.dosomething_fragment_chat_message);
                dosomething_fragment_chat_message_time = (TextView) itemview.findViewById(R.id.dosomething_fragment_chat_message_time);
                dosomething_fragment_chat_message_date = (TextView) itemview.findViewById(R.id.dosomething_fragment_chat_message_date);

            }
        }
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private class ChatConversation extends AsyncTask<Void, Void, Boolean> {

        private String Name;
        private String image1, Friend_Name;
        Exception error;
        String getConversation_Api_url;
        private String conversationId;
        private String online_status;
        private String UserId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isLoader) {
                kbv.setVisibility(View.VISIBLE);
                relative_image.setVisibility(View.VISIBLE);
                timer = new Timer();
                timer.schedule(new AutoSlider(), 0, 1350);
                splashAnimation.start();
            }
            if (getActivity() != null) {
                getConversation_Api_url = getActivity().getResources().getString(R.string.dosomething_apilink_string_getconversation);
                sessionid = sharedPrefrences.getSessionid(getActivity());
                conversationId = sharedPrefrences.getConversationId(getActivity());
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                HashMap<String, Object> paramsfb = new HashMap<>();
                paramsfb.put(TAG_SESSIONID, sessionid);
                paramsfb.put(TAG_CONVERSATIONID, conversationId);
                paramsfb.put(TAG_TIME, datetime);

                json_string = jsonfunctions.postToURL(getConversation_Api_url, paramsfb);
                Log.v("jason url=======>", String.valueOf(paramsfb));

                json_object = new JSONObject(json_string);
                json_content = json_object.getJSONObject("getconversation");
                return true;

            } catch (JSONException e) {
                e.printStackTrace();
                error = e;
                return false;
            }


        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);


            if (aVoid) {
                if (messages != null) {
                    messages.clear();
                }
                try {
                    if (json_object.has("getconversation")) {
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
                            {


                                if (json_content.has("receiver")) {
                                    JSONArray hobbiesArray = json_content.getJSONArray("receiver");

                                    for (int i = 0; i < hobbiesArray.length(); i++) {
                                        JSONObject details = hobbiesArray.getJSONObject(0);
                                        Friend_Name = details.getString("Name");
                                        image1 = details.getString("image1");
                                        UserId = details.getString("UserId");
                                        if (details.has("online_status")) {
                                            online_status = details.getString("online_status");
                                        } else {
                                            online_status = "";
                                        }
                                        if (getActivity() != null) {
                                            sharedPrefrences.setFriendUserId(getActivity(), UserId);
                                            sharedPrefrences.setFriendProfilePicture(getActivity(), image1);
                                            sharedPrefrences.setFriendFirstname(getActivity(), Friend_Name);
                                        }
                                    }

                                }


                                if (json_content.has("converation")) {
                                    dosomething_chatbox_progress.dismiss();
                                    JSONArray hobbiesArray = json_content.getJSONArray("converation");
                                    for (int i = 0; i < hobbiesArray.length(); i++) {
                                        JSONObject details = hobbiesArray.getJSONObject(i);
                                        int Mid = details.getInt("Mid");
                                        int UserId = details.getInt("userId");
                                        Name = details.getString("Name");
                                        String userEmail = details.getString("userEmail");
                                        String image2 = details.getString("image2");
                                        String image3 = details.getString("image3");
                                        String type = details.getString("type");


                                        if (details.has("type")) {
                                            if (details.getString("type").equals("SENDER")) {

                                                try {
                                                    sender_Message = details.getString("Message");
                                                    sender_Time = details.getString("senttime");
                                                /*Log.d("dosmething", "sender_Time..1" + sender_Time);
                                                Log.d("dosmething", "sender_Message..1" + sender_Message);
                                                SimpleDateFormat input = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
                                                SimpleDateFormat output = new SimpleDateFormat("dd/MMM/yyyy hh:mm a");

                                                    Log.d("tripDate", sender_Time);
                                                    Date oneWayTripDate = input.parse(sender_Time);                 // parse input
                                                    String tripDate = output.format(oneWayTripDate);// format output
                                                    Log.d("tripDate", tripDate);*/


                                                /*String string = sender_Time;
                                                String[] parts = string.split(" ");
                                                String part1 = parts[0];
                                                final String part2 = parts[1];
                                                String[] part = part2.split(":");
                                                String parts1 = part[0];
                                                final String parts2 = part[1];
                                                sender_Time = parts1 + ":" + parts2;*/
                                                    String inputPattern = "yyyy-MM-dd HH:mm:ss";
                                                    String outputPattern = "dd/MM/yyyy h:mm a";

                                                    SimpleDateFormat input = new SimpleDateFormat(inputPattern);
                                                    SimpleDateFormat output = new SimpleDateFormat(outputPattern);
                                                    try {
                                                        Date oneWayTripDate = input.parse(sender_Time);                 // parse input
                                                        String tripDate = output.format(oneWayTripDate);// format output
                                                        Log.d("tripDate", tripDate);
                                                        sender_Time = tripDate;
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Log.d("dosmething", "sender_Time..2" + sender_Time);
                                                    Log.d("dosmething", "sender_Message..2" + sender_Message);
                                                    messages.add(new Message(sender_Message, sender_Time, sender_Time, true));
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }

                                            } else if (details.getString("type").equals("RECEIVER")) {

                                                try {

                                                    receiver_Message = details.getString("Message");
                                                    String receiver_Time = details.getString("receivedtime");
                                                    Log.d("dosmething", "receiver_Time..1" + receiver_Time);
                                                    Log.d("dosmething", "receiver_Message..1" + receiver_Message);


                                                    String inputPattern = "yyyy-MM-dd HH:mm:ss";
                                                    String outputPattern = "dd/MM/yyyy h:mm a";

                                                    SimpleDateFormat input = new SimpleDateFormat(inputPattern);
                                                    SimpleDateFormat output = new SimpleDateFormat(outputPattern);
                                                    try {
                                                        Date oneWayTripDate = input.parse(receiver_Time);                 // parse input
                                                        String tripDate = output.format(oneWayTripDate);// format output
                                                        Log.d("tripDate", tripDate);
                                                        receiver_Time = tripDate;
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }


                                                    /*SimpleDateFormat input = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss");
                                                    SimpleDateFormat output = new SimpleDateFormat("dd/MMM/yyyy hh:mm a");

                                                    Log.d("tripDate", receiver_Message);
                                                    Date oneWayTripDate = input.parse(receiver_Message);                 // parse input
                                                    String tripDate = output.format(oneWayTripDate);// format output
                                                    Log.d("tripDate", tripDate);*/


                                                /*String receiver = receiver_Time;
                                                String[] receiver_parts = receiver.split(" ");
                                                String receiver_part1 = receiver_parts[0];
                                                final String receiver_part2 = receiver_parts[1];
                                                String[] receiver_part = receiver_part2.split(":");
                                                String receiver_parts1 = receiver_part[0];
                                                final String receiver_parts2 = receiver_part[1];
                                                receiver_Time = receiver_parts1 + ":" + receiver_parts2;*/
                                                    Log.d("dosmething", "receiver_Time..2" + receiver_Time);
                                                    Log.d("dosmething", "receiver_Message..2" + receiver_Message);
                                                    messages.add(new Message(receiver_Message, receiver_Time, receiver_Time, false));


                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        }


//                                    messages.add(new Message(Mid, UserId, Name, userEmail, image1, image2, image3, sender_Message, receiver_Message, type));

                                        conversationBeans.add(new ConversationBean(Mid, UserId, Name, userEmail, image1, image2, image3, sender_Message, receiver_Message, type));

                                    }

                                }
                            }


                            if (image1.equals("") || image1.equals("http://indiawebcoders.com/mobileapps/dosomething/uploads/profile/noimage.png")) {

                                dosomething_fragment_chat_box_personImage.setBackgroundDrawable(getResources().getDrawable(R.drawable.profile_noimg));

                            } else if (!(image1.equals("") || image1.equals("http://indiawebcoders.com/mobileapps/dosomething/uploads/profile/noimage.png"))) {

                                aQuery.id(dosomething_fragment_chat_box_personImage).image(image1, true, true, 0, 0, new BitmapAjaxCallback() {
                                    @Override
                                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                        if (status.getCode() == 200) {
                                            Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
                                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                            iv.setImageBitmap(conv_bm);


                                        }
                                    }
                                });
                            }


                            kbv.setVisibility(View.GONE);
                            relative_image.setVisibility(View.GONE);

                            if (timer != null) {

                                timer.cancel();


                                timer = null;

                            }
                            splashAnimation.stop();
                            dosomething_fragment_chat_messages_textview.setVisibility(View.GONE);
                            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            dosomething_fragment_chat_messages_list.setLayoutManager(layoutManager);


                            isLoader = true;
                            adapter = new RecyclerAdapter(getActivity(), messages, conversationBeans);
                            dosomething_fragment_chat_messages_list.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            dosomething_fragment_chat_messages_list.scrollToPosition(messages.size() - 1);
//                        dosomething_fragment_chat_messages_list.smoothScrollToPosition(messages.size());
                            dosomething_fragment_chat_messages_list.setVisibility(View.VISIBLE);
                            dosomething_chatbox_progress.dismiss();
                            Log.d("online_status", online_status);
                            if (online_status.equals("0")) {
                                dosomething_fragment_chat_box_person_status.setText("offline");

                            } else if (online_status.equals("1")) {
                                dosomething_fragment_chat_box_person_status.setText("online");

                            }


                        } else if (json_content.getString("status").equalsIgnoreCase("failed")) {


                            kbv.setVisibility(View.GONE);
                            relative_image.setVisibility(View.GONE);

                            if (timer != null) {

                                timer.cancel();


                                timer = null;

                            }
                            splashAnimation.stop();
                            dosomething_chatbox_progress.dismiss();
                            isLoader = true;
                            dosomething_fragment_chat_messages_list.setVisibility(View.GONE);
                            dosomething_fragment_chat_messages_textview.setText("No Conversaion Details Found");
                            dosomething_fragment_chat_messages_textview.setVisibility(View.VISIBLE);
//                        AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
//                        dialogBuilder.setTitle("Info");
//                        dialogBuilder.setMessage("No Conversaion Details Found");
//                        dialogBuilder.setButton(DialogInterface.BUTTON_POSITIVE, "Dismiss", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                dialog.dismiss();
//                            }
//                        });
//                        dialogBuilder.setCancelable(false);
//                        dialogBuilder.show();
                        }

                    } else if (json_object.has("error")) {
                        if (json_object.getString("error").equalsIgnoreCase("InvalidSession")) {

                            sharedPrefrences.setLogin(getActivity(), "");
                            sharedPrefrences.setEmail(getActivity(), "");
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
                            sharedPrefrences.setAbout(getActivity(), "");
                            Intent i = new Intent(getActivity(), DoSomeThingLogin.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (error != null) {
 /* Date d = new Date();
                    CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                    datetime = String.valueOf(s);
                    Log.d("date and time", datetime);
                    new ChatConversation().execute();*/
                }
            }


        }
    }

    private class SendMessage extends AsyncTask<Void, Void, Boolean> {
        Exception error;

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                if (getActivity() != null) {
                    HashMap<String, Object> paramsfb = new HashMap<>();
                    paramsfb.put(TAG_SESSIONID, sharedPrefrences.getSessionid(getActivity()));
                    paramsfb.put(TAG_CONVERSATIONID, sharedPrefrences.getConversationId(getActivity()));
                    paramsfb.put(TAG_RECEIVERID, sharedPrefrences.getFriendUserId(getActivity()));
                    paramsfb.put(TAG_MESSAGE, message);
                    json_string = jsonfunctions.postToURL(getString(R.string.dosomething_apilink_string_sendmessage), paramsfb);
                    Log.v("jason url=======>", String.valueOf(paramsfb));

                    json_object = new JSONObject(json_string);
                    json_content = json_object.getJSONObject("sendmessage");
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                error = e;
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            try {

                if (aVoid) {
                    if (json_object.has("sendmessage")) {
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
                            {

                                if (getActivity() != null) {
                                    if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                                        Date d = new Date();
                                        CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                                        datetime = String.valueOf(s);

                                        Log.d("date and time", datetime);
                                        new ChatConversation().execute();
                                        ((MyApplication) getActivity().getApplication()).getListChatBean().clear();

                                    }


                                }


                            }
                        } else if (json_content.getString("error").equalsIgnoreCase("InvalidSession")) {

                            sharedPrefrences.setLogin(getActivity(), "");
                            sharedPrefrences.setEmail(getActivity(), "");
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
                            sharedPrefrences.setAbout(getActivity(), "");


                            Intent i = new Intent(getActivity(), DoSomeThingLogin.class);
                            startActivity(i);
                            getActivity().finish();
                        }
                    }
                } else {

                    if (error != null) {
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.alert_dialog);
                        TextView status_textview_availablenow = (TextView) dialog.findViewById(R.id.status_textview_availablenow);
                        TextView status_textview_accept_check = (TextView) dialog.findViewById(R.id.status_textview_accept_check);
                        status_textview_availablenow.setText("Message sending failed.....\nPlease try again...");
                        status_textview_accept_check.setText("Dismiss");
                        status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class DosomethingBlockUser extends AsyncTask<Void, Void, Void> {
        String blockStatus;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HashMap<String, Object> paramsBlock = new HashMap<>();
                paramsBlock.put(TAG_SESSIONID, sharedPrefrences.getSessionid(getActivity()));
                paramsBlock.put(TAG_CONVERSATIONID, sharedPrefrences.getConversationId(getActivity()));
                json_string = jsonfunctions.postToURL(getActivity().getResources().getString(R.string.dosomething_apilink_string_blockuser), paramsBlock);
                try {
                    json_object = new JSONObject(json_string);
                    if (json_object.has("blockuser")) {
                        json_content = json_object.getJSONObject("blockuser");
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
                            blockStatus = "This User is Blocked Successfully";
                        }
                    } else if (json_content.getString("error").equalsIgnoreCase("InvalidSession")) {
                        blockStatus = "InvalidSession";
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (blockStatus) {
                case "This User is Blocked Successfully":
                    if (getActivity() != null) {

                        ((MyApplication) getActivity().getApplication()).getListChatBean().clear();
                        ((MyApplication) getActivity().getApplication()).getChatBeanIdsList().clear();
                        DoSomethingChatList doSomethingChatList = new DoSomethingChatList();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatList);
                        fragmentTransaction.commit();
                    }
                    break;
                default:
                    sharedPrefrences.setLogin(getActivity(), "");
                    sharedPrefrences.setEmail(getActivity(), "");
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
                    sharedPrefrences.setAbout(getActivity(), "");
                    Intent i = new Intent(getActivity(), DoSomeThingLogin.class);
                    startActivity(i);
                    getActivity().finish();
                    break;
            }
        }
    }


    public class DosomethingDeleteConversation extends AsyncTask<Void, Void, Void> {
        String blockStatus;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HashMap<String, Object> paramsBlock = new HashMap<>();
                paramsBlock.put(TAG_SESSIONID, sharedPrefrences.getSessionid(getActivity()));
                paramsBlock.put(TAG_FRIENDUSERID, sharedPrefrences.getFriendUserId(getActivity()));
                json_string = jsonfunctions.postToURL(getActivity().getResources().getString(R.string.dosomething_apilink_string_unmatch), paramsBlock);
                try {
                    json_object = new JSONObject(json_string);
                    if (json_object.has("cancelrequest")) {

                        blockStatus = json_object.getString("cancelrequest");
                        if (!json_object.getString("cancelrequest").equals("null")) {
                            json_content = json_object.getJSONObject("cancelrequest");
                            if (json_content.has("status")) {
                                if (json_content.getString("status").equalsIgnoreCase("success")) {
                                    blockStatus = "Conversaion has been Cleared";
                                } else if (json_content.getString("status").equalsIgnoreCase("error")) {
                                    blockStatus = "error";
                                }
                            }
                        }


                    } else if (json_content.getString("error").equalsIgnoreCase("InvalidSession")) {
                        blockStatus = "InvalidSession";
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (blockStatus) {
                case "Conversaion has been Cleared":
                   /* Date d = new Date();
                    CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                    datetime = String.valueOf(s);
                    Log.d("date and time", datetime);
                    new ChatConversation().execute();*/
                    if (getActivity() != null) {

                        ((MyApplication) getActivity().getApplication()).getListChatBean().clear();
                        ((MyApplication) getActivity().getApplication()).getChatBeanIdsList().clear();
//                        ((DoSomethingStatus) getActivity()).clickNearme(true);

                        ((MyApplication) getActivity().getApplication()).getListChatBean().clear();
                        ((DoSomethingStatus) getActivity()).slideToChat(true);
                      /*  DoSomethingChatList doSomethingChatList = new DoSomethingChatList();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatList);
                        fragmentTransaction.commit();*/

                    }


                    break;
                case "error":
                    if (getActivity() != null) {

                        ((MyApplication) getActivity().getApplication()).getListChatBean().clear();
                        ((DoSomethingStatus) getActivity()).clickNearme(true);

                    }

                    break;
                default:
                    sharedPrefrences.setLogin(getActivity(), "");
                    sharedPrefrences.setEmail(getActivity(), "");
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
                    sharedPrefrences.setAbout(getActivity(), "");


                    Intent i = new Intent(getActivity(), DoSomeThingLogin.class);
                    startActivity(i);
                    getActivity().finish();
                    break;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();

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
        String matched;

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


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
                            send_request = userlistobject.getString("send_request");

                            device_token = userlistobject.getString("device_token");
                            if (userlistobject.has("matched")) {
                                matched = userlistobject.getString("matched");

                            } else {
                                matched = "";
                            }
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


                        } else if (json_content.getString("status").equalsIgnoreCase("failed")) {

                            Toast.makeText(getActivity().getApplicationContext(), "FAILED", Toast.LENGTH_LONG);


                        } else if (json_content.getString("status").equalsIgnoreCase("error")) {

                            Toast.makeText(getActivity().getApplicationContext(), "ERROR", Toast.LENGTH_LONG);


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
                        DoSomethingNearMe.usermatched = matched;

                        sharedPrefrences.setFriendUserId(getActivity(), String.valueOf(user_id));
                        sharedPrefrences.setFriendFirstname(getActivity(), first_name);
                        sharedPrefrences.setFriendLastname(getActivity(), last_name);
                        sharedPrefrences.setFriendAge(getActivity(), age);
                        sharedPrefrences.setFriendProfilePicture(getActivity(), image1);
                        sharedPrefrences.setFriendProfilePicture1(getActivity(), image2);
                        sharedPrefrences.setFriendProfilePicture2(getActivity(), image3);
                        sharedPrefrences.setFriendAbout(getActivity(), about);
                        sharedPrefrences.setFriendGender(getActivity(), gender);
                        sharedPrefrences.setSendRequest(getActivity(), send_request);


                        ((MyApplication) getActivity().getApplication()).setListHobbies(hobbiesBeans);
                        ((MyApplication) getActivity().getApplication()).setListDosomethingBean(dosomething_beans);

                        doSomethingFriendsProfileFragment = new DoSomething_Friends_profile_fragment();


                        fragmentTransaction = getFragmentManager().beginTransaction();
                        if (fragmentTransaction != null) {
                            fragmentTransaction.replace(R.id.detail_fragment, doSomethingFriendsProfileFragment);
                            fragmentTransaction.commit();

                        }


                    }


                } else {
                    sharedPrefrences.setLogin(getActivity(), "");
                    sharedPrefrences.setEmail(getActivity(), "");
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
                    sharedPrefrences.setAbout(getActivity(), "");
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


}