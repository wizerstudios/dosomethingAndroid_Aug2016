package com.dosomething.android.Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.daimajia.swipe.SwipeLayout;
import com.dosomething.android.Beanclasses.ChatBean;
import com.dosomething.android.CommonClasses.Jsonfunctions;
import com.dosomething.android.CommonClasses.NetworkCheck;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.CommonClasses.SwipeDetector;
import com.dosomething.android.CommonClasses.TransparentProgressDialog;
import com.dosomething.android.DoSomeThingLogin;
import com.dosomething.android.DoSomethingStatus;
import com.dosomething.android.MyApplication;
import com.dosomething.android.R;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.internal.zzip.runOnUiThread;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoSomethingChatList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoSomethingChatList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoSomethingChatList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_SESSIONID = "sessionid";
    private static final String TAG_CONVERSATIONID = "conversationId";
    private static final String TAG_FRIENDUSERID = "request_send_user_id";
    private static final String TAG_TIME = "datetime";
    private static String urlchatHistory = "http://wiztestinghost.com/dosomething/getchathistory?";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerAdapter chatAdapter;
    private OnFragmentInteractionListener mListener;
    RecyclerView dosomething_fragment_chatlist;
    Typeface patron_bold;
    Typeface patron_regular;
    Typeface patron_medium;
    Typeface myfonts_thin;
    private String sessionid, json_string;
    Jsonfunctions jsonfunctions;
    JSONObject json_object, json_content, details;
    private TransparentProgressDialog pd;
    SharedPrefrences sharedPrefrences;
    ArrayList<ChatBean> chatBeans = new ArrayList<>();
    ArrayList<String> listChatBeanIds = new ArrayList<>();
    HashMap<String, ChatBean> listChatBeans = new HashMap<>();
    TextView chat_noconversation_textview;
    AQuery aQuery;
    FrameLayout chatlist_layout;
    private RecyclerView.LayoutManager mLayoutManager;
    private GridLayoutManager lLayout;
    Boolean aBoolean = false;
    SwipeRefreshLayout dosomething_fragment_chatlist_refreshlayout;

    Handler handler = new Handler();
    Timer timer = new Timer();
    private AnimationDrawable splashAnimation;
    private ImageView kbv;
    private boolean isLoader = false;
    String datetime;
    private Tracker mTracker;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoSomethingChat.
     */
    // TODO: Rename and change types and number of parameters
    public static DoSomethingChatList newInstance(String param1, String param2) {
        DoSomethingChatList fragment = new DoSomethingChatList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DoSomethingChatList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (getActivity() != null) {
            ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().setstate("YES");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_do_something_chat, container, false);
        try {
            MyApplication application = (MyApplication) getActivity().getApplication();
            mTracker = application.getDefaultTracker();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sharedPrefrences = new SharedPrefrences();
        jsonfunctions = new Jsonfunctions(getActivity());
        aQuery = new AQuery(getActivity());
        SwipeDetector swipeDetector = new SwipeDetector();
        pd = new TransparentProgressDialog(getActivity(), getResources().getDrawable(R.drawable.loading));

        kbv = (ImageView) view.findViewById(R.id.image);
        kbv.setBackgroundResource(R.drawable.progress_drawable);

        splashAnimation = (AnimationDrawable) kbv.getBackground();
        sessionid = sharedPrefrences.getSessionid(getActivity());
        chatlist_layout = (FrameLayout) view.findViewById(R.id.chatlist_layout);
        dosomething_fragment_chatlist_refreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.dosomething_fragment_chatlist_refreshlayout);
        dosomething_fragment_chatlist = (RecyclerView) view.findViewById(R.id.dosomething_fragment_chatlist);
        chat_noconversation_textview = (TextView) view.findViewById(R.id.chat_noconversation_textview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        dosomething_fragment_chatlist.setLayoutManager(mLayoutManager);
        Log.d("chathistory", "converastion_size" + ((MyApplication) getActivity().getApplication()).getListChatBean().size());
        if (((MyApplication) getActivity().getApplication()).getListChatBean().size() == 0) {
            splashAnimation.start();
            if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                listChatBeans = new HashMap<>();
                listChatBeanIds = new ArrayList<>();
                Date d = new Date();
                CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                datetime = String.valueOf(s);

                Log.d("date and time", datetime);
                new ChatHistory().execute();
            }
        } else {
            dosomething_fragment_chatlist.setHasFixedSize(true);
            if (chatAdapter == null) {
                listChatBeans = new HashMap<>();
                listChatBeanIds = new ArrayList<>();
                listChatBeans = ((MyApplication) getActivity().getApplication()).getListChatBean();
                listChatBeanIds = ((MyApplication) getActivity().getApplication()).getChatBeanIdsList();
                chatAdapter = new RecyclerAdapter(getActivity());
                dosomething_fragment_chatlist.setAdapter(chatAdapter);
            } else {
                chatAdapter.notifyDataSetChanged();
            }


        }

        try {

            if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                isLoader = true;
                if (sharedPrefrences.getChatAutoUpdate(getActivity()).equals("false")) {
                    callAsynchronousTask();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        dosomething_fragment_chatlist_refreshlayout.setOnRefreshListener(this);
        /*dosomething_fragment_chatlist_refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dosomething_fragment_chatlist_refreshlayout.setRefreshing(true);
                if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                    new ChatHistory().execute();
                }


                refreshList();
            }
        });*/


        return view;
    }

    public void refreshList() {
        //do processing to get new data and set your listview's adapter, maybe  reinitialise the loaders you may be using or so
        //when your data has finished loading, cset the refresh state of the view to false
        dosomething_fragment_chatlist_refreshlayout.setRefreshing(false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void text_font_typeface() {
        patron_bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Bold.ttf");
        patron_regular = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Regular.ttf");
        patron_medium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Medium.ttf");
        myfonts_thin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Patron-Thin.ttf");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            // PerformBackgroundTask this class is the class that extends AsynchTask

                            if (getActivity() != null) {
                                if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                                    Date d = new Date();
                                    CharSequence s = DateFormat.format("yyyy-MM-dd HH:mm:ss", d.getTime());
                                    datetime = String.valueOf(s);

                                    Log.d("date and time", datetime);
                                    new ChatHistory().execute();
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 7000); //execute in every 15 sec
    }

    @Override
    public void onRefresh() {
        dosomething_fragment_chatlist_refreshlayout.setRefreshing(true);
        ((MyApplication) getActivity().getApplication()).getListChatBean().clear();
        ((MyApplication) getActivity().getApplication()).getChatBeanIdsList().clear();
        if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
            new ChatHistory().execute();
        }


        refreshList();
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

    public static Bitmap getRoundedRectanguleBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 100, 100);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(100, 100, 94, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException | OutOfMemoryError ignored) {
        }
        return result;
    }

    class ChatAdapter implements ListAdapter {
        ArrayList<ChatBean> chatBeans = new ArrayList<>();
        Activity activity;

        public ChatAdapter(Activity activity, ArrayList<ChatBean> chatBeans) {
            super();
            this.activity = activity;
            this.chatBeans = chatBeans;
            text_font_typeface();
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
            return chatBeans.size();
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
        public boolean hasStableIds() {
            return false;
        }

        class Holder {
            LinearLayout activity_dosomething_chatperson;
            LinearLayout activity_dosomething_chatperson_Tectview_blockanddelete;
            ImageView activity_dosomething_chatperson_appIcon;
            TextView activity_dosomething_chatperson_name;
            TextView activity_dosomething_chatperson_time;
            TextView activity_dosomething_chatperson_message;
            TextView activity_dosomething_meassge_count;
            TextView activity_dosomething_chatperson_Tectview_block;
            TextView activity_dosomething_chatperson_Tectview_delete;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Holder holder = null;
            convertView = inflater.inflate(R.layout.activity_chatperson_list, parent, false);
            if (convertView != null) {
                holder = new Holder();
                convertView.setTag(holder);
                holder.activity_dosomething_chatperson = (LinearLayout) convertView.findViewById(R.id.activity_dosomething_chatperson);
                holder.activity_dosomething_chatperson_Tectview_blockanddelete = (LinearLayout) convertView.findViewById(R.id.activity_dosomething_chatperson_Tectview_blockanddelete);
                holder.activity_dosomething_chatperson_appIcon = (ImageView) convertView.findViewById(R.id.activity_dosomething_chatperson_appIcon);
                holder.activity_dosomething_chatperson_name = (TextView) convertView.findViewById(R.id.activity_dosomething_chatperson_name);
                holder.activity_dosomething_chatperson_time = (TextView) convertView.findViewById(R.id.activity_dosomething_chatperson_time);
                holder.activity_dosomething_chatperson_message = (TextView) convertView.findViewById(R.id.activity_dosomething_chatperson_message);
                holder.activity_dosomething_meassge_count = (TextView) convertView.findViewById(R.id.activity_dosomething_meassge_count);
                holder.activity_dosomething_chatperson_Tectview_block = (TextView) convertView.findViewById(R.id.activity_dosomething_chatperson_Tectview_block);
                holder.activity_dosomething_chatperson_Tectview_delete = (TextView) convertView.findViewById(R.id.activity_dosomething_chatperson_Tectview_delete);
                holder.activity_dosomething_chatperson_name.setTypeface(patron_bold);
                holder.activity_dosomething_chatperson_Tectview_delete.setTypeface(patron_regular);
                holder.activity_dosomething_chatperson_Tectview_block.setTypeface(patron_regular);
                holder.activity_dosomething_chatperson_message.setTypeface(patron_regular);
                holder.activity_dosomething_chatperson_time.setTypeface(patron_regular);
                holder.activity_dosomething_meassge_count.setTypeface(patron_regular);

            } else {
                holder = (Holder) convertView.getTag();
            }


            holder.activity_dosomething_chatperson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("click", String.valueOf(position));
                    Log.d("click1", String.valueOf(chatBeans.get(position).getUser_id()));
                    sharedPrefrences.setConversationId(getActivity(), String.valueOf(chatBeans.get(position).getChat_id()));
                    sharedPrefrences.setFriendProfilePicture(getActivity(), chatBeans.get(position).getImage1());
                    sharedPrefrences.setFriendFirstname(getActivity(), chatBeans.get(position).getName());
                    sharedPrefrences.setFriendLastname(getActivity(), chatBeans.get(position).getName());
                    sharedPrefrences.setFriendUserId(getActivity(), String.valueOf(chatBeans.get(position).getUser_id()));
                    if (getActivity() != null) {
                        ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().setstate("YES");
                    }
                    DoSomethingChatBox doSomethingChatBox = new DoSomethingChatBox();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
                    fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatBox);

                    fragmentTransaction.commit();


                }
            });
            if (!chatBeans.get(position).getUnreadmessage().equals("0")) {
                holder.activity_dosomething_meassge_count.setText(chatBeans.get(position).getUnreadmessage());
                holder.activity_dosomething_meassge_count.setVisibility(View.VISIBLE);
            } else {
                holder.activity_dosomething_meassge_count.setVisibility(View.GONE);

            }


            if (!chatBeans.get(position).getImage1().equals("")) {
                final Holder finalHolder = holder;
                aQuery.id(holder.activity_dosomething_chatperson_appIcon).image(chatBeans.get(position).getImage1(), true, true, 0, 0, new BitmapAjaxCallback() {
                    @Override
                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                        if (status.getCode() == 200) {
                            Bitmap resized = Bitmap.createScaledBitmap(bm, 100, 100, true);
                            Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 10);
                            iv.setImageBitmap(conv_bm);
                            if (!chatBeans.get(position).getName().equals("")) {
                                finalHolder.activity_dosomething_chatperson_name.setText(chatBeans.get(position).getName());
                            } else {
                                finalHolder.activity_dosomething_chatperson_name.setText("User");
                            }
                            finalHolder.activity_dosomething_chatperson_message.setText(chatBeans.get(position).getLast_message());
                            if (!chatBeans.get(position).equals("" + ":" + "")) {
                                finalHolder.activity_dosomething_chatperson_time.setText(chatBeans.get(position).getLast_message_time());
                            } else {
                                finalHolder.activity_dosomething_chatperson_time.setText("time");
                            }


                        }
                    }
                });

            } else {
                holder.activity_dosomething_chatperson_appIcon.setImageDrawable(getResources().getDrawable(R.drawable.profile_icon));
                if (!chatBeans.get(position).getName().equals("")) {
                    holder.activity_dosomething_chatperson_name.setText(chatBeans.get(position).getName());
                } else {
                    holder.activity_dosomething_chatperson_name.setText("User");
                }
                holder.activity_dosomething_chatperson_message.setText(chatBeans.get(position).getLast_message());
                holder.activity_dosomething_chatperson_time.setText(chatBeans.get(position).getLast_message_time());

            }
            final Holder finalHolder1 = holder;
            holder.activity_dosomething_chatperson.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    finalHolder1.activity_dosomething_chatperson_Tectview_blockanddelete.setVisibility(View.VISIBLE);
                    return false;
                }
            });
            final Holder finalHolder2 = holder;
            holder.activity_dosomething_chatperson_Tectview_block.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder2.activity_dosomething_chatperson_Tectview_blockanddelete.setVisibility(View.GONE);

                }
            });
            final Holder finalHolder3 = holder;
            holder.activity_dosomething_chatperson_Tectview_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finalHolder3.activity_dosomething_chatperson_Tectview_blockanddelete.setVisibility(View.GONE);

                }
            });

            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.DataObjectHolder> {
        Activity activity;
        private int lastPosition = listChatBeans.size() - 1;

        public RecyclerAdapter(Activity activity) {
            super();
            this.activity = activity;
            text_font_typeface();
        }


        public RecyclerAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = mInflater.inflate(R.layout.activity_chatperson_list, parent, false);
            return new DataObjectHolder(view);
        }


        @Override
        public void onBindViewHolder(final DataObjectHolder holder, final int position) {

            try {
                final ChatBean chatBean = listChatBeans.get(listChatBeanIds.get(position));
                /*if (chatBean.getImage1().equals("") || chatBean.getImage1().equals("http://128.199.130.137//dosomething//uploads//profile//noimage.png")) {
                    holder.activity_dosomething_chatperson_appIcon.setImageDrawable(getResources().getDrawable(R.drawable.profile_noimg));
                } else */

                if (!(chatBean.getImage1().equals("")) && (!chatBean.getImage1().equalsIgnoreCase(chatBean.getOldImageUrl()))) {

                    aQuery.id(holder.activity_dosomething_chatperson_appIcon).image(chatBean.getImage1(), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
//                                Bitmap resized = Bitmap.createScaledBitmap(bm, 90, 90, true);
                                Bitmap conv_bm = getCroppedBitmap(bm);
                                iv.setImageBitmap(conv_bm);
                                chatBean.setProfileImage(conv_bm);
                                listChatBeans.put(chatBean.getChat_id() + "", chatBean);
                            } else {
                                holder.activity_dosomething_chatperson_appIcon.setImageResource(R.drawable.profile_noimg);

                            }
                        }
                    });

                } else {
                    if (chatBean.getProfileImage() != null) {
                        holder.activity_dosomething_chatperson_appIcon.setImageBitmap(chatBean.getProfileImage());
                    } else {

                        aQuery.id(holder.activity_dosomething_chatperson_appIcon).image(chatBean.getImage1(), true, true, 0, 0, new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                if (status.getCode() == 200) {
//                                Bitmap resized = Bitmap.createScaledBitmap(bm, 90, 90, true);
                                    Bitmap conv_bm = getCroppedBitmap(bm);
                                    iv.setImageBitmap(conv_bm);
                                    chatBean.setProfileImage(conv_bm);
                                    listChatBeans.put(chatBean.getChat_id() + "", chatBean);
                                } else {
                                    holder.activity_dosomething_chatperson_appIcon.setImageResource(R.drawable.profile_noimg);

                                }
                            }
                        });


                    }
                }
                holder.activity_dosomething_chatperson_name.setTypeface(patron_bold);
                holder.activity_dosomething_chatperson_Tectview_delete.setTypeface(patron_regular);
                holder.activity_dosomething_chatperson_Tectview_block.setTypeface(patron_regular);
                holder.activity_dosomething_chatperson_message.setTypeface(patron_regular);
                holder.activity_dosomething_chatperson_time.setTypeface(patron_regular);
                holder.activity_dosomething_meassge_count.setTypeface(patron_regular);

                holder.activity_dosomething_chatperson.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("dosomething_click", String.valueOf(position));
                        Log.d("click1", String.valueOf(chatBean.getUser_id()));
                        sharedPrefrences.setConversationId(getActivity(), String.valueOf(chatBean.getChat_id()));
                        sharedPrefrences.setFriendProfilePicture(getActivity(), chatBean.getImage1());
                        sharedPrefrences.setFriendFirstname(getActivity(), chatBean.getName());
                        sharedPrefrences.setFriendUserId(getActivity(), chatBean.getUser_id() + "");
                        if (getActivity() != null) {
                            ((MyApplication) getActivity().getApplication()).getDoSomethingStatus().setstate("YES");
                            ((DoSomethingStatus) getActivity()).showHideBottomLayout(true);
                        }

                        DoSomethingChatBox doSomethingChatBox = new DoSomethingChatBox();
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
                        fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatBox);

                        fragmentTransaction.commit();


                    }
                });

                try {
                    if (chatBean.getUnreadmessage() != null) {
                        if (chatBean.getUnreadmessage().equals("0")) {
                            holder.activity_dosomething_meassge_count.setVisibility(View.GONE);
                        } else {
                            holder.activity_dosomething_meassge_count.setVisibility(View.VISIBLE);
                            holder.activity_dosomething_meassge_count.setText(chatBean.getUnreadmessage());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (!chatBean.getName().equals("")) {
                    holder.activity_dosomething_chatperson_name.setText(chatBean.getName());
                } else {
                    holder.activity_dosomething_chatperson_name.setText("User");
                }
                holder.activity_dosomething_chatperson_message.setText(chatBean.getLast_message());
                Log.d("dosomethingchat", "Last_message_time" + chatBean.getLast_message_time());
                if (!chatBean.getLast_message_time().equals("" + ":" + "")) {
                    holder.activity_dosomething_chatperson_time.setText(chatBean.getLast_message_time());
                } else {
                    holder.activity_dosomething_chatperson_time.setText("");
                }

                if (chatBean.getName().trim().equalsIgnoreCase("Support")) {
                    holder.activity_dosomething_chatperson_appIcon.setBackgroundResource(R.drawable.dosomething_border);
                    holder.activity_dosomething_chatperson_appIcon.setPadding(3, 3, 3, 3);
                }

                if (!chatBean.getName().trim().equalsIgnoreCase("Support")) {
                    holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
                    holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper));

                    holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
                        @Override
                        public void onClose(SwipeLayout layout) {
                            //when the SurfaceView totally cover the BottomView.
                        }

                        @Override
                        public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                            //you are swiping.
                        }

                        @Override
                        public void onStartOpen(SwipeLayout layout) {

                        }

                        @Override
                        public void onOpen(SwipeLayout layout) {
                            //when the BottomView totally show.
                        }

                        @Override
                        public void onStartClose(SwipeLayout layout) {

                        }

                        @Override
                        public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
                            //when user's hand released.
                        }
                    });

                    holder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    holder.activity_dosomething_chatperson_Tectview_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            sharedPrefrences.setFriendUserId(getActivity(), chatBean.getUser_id() + "");
                            new DosomethingDeleteConversation().execute();
                            removeItem(position);


                        }
                    });
                } else {
                    holder.swipeLayout.setSwipeEnabled(false);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        public void removeItem(int position) {
            listChatBeans.remove(listChatBeanIds.get(position));
            listChatBeanIds.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listChatBeanIds.size());
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }

        @Override
        public int getItemCount() {

            return listChatBeanIds.size();
        }

        public class DataObjectHolder extends RecyclerView.ViewHolder {
            LinearLayout activity_dosomething_chatperson;
            LinearLayout activity_dosomething_chatperson_Tectview_blockanddelete;
            ImageView activity_dosomething_chatperson_appIcon;
            TextView activity_dosomething_chatperson_name;
            TextView activity_dosomething_chatperson_time;
            TextView activity_dosomething_chatperson_message;
            TextView activity_dosomething_meassge_count;
            TextView activity_dosomething_chatperson_Tectview_block;
            TextView activity_dosomething_chatperson_Tectview_delete;
            SwipeLayout swipeLayout;

            public DataObjectHolder(View itemview) {
                super(itemview);
                swipeLayout = (SwipeLayout) itemview.findViewById(R.id.swipe);
                activity_dosomething_chatperson = (LinearLayout) itemview.findViewById(R.id.activity_dosomething_chatperson);
                activity_dosomething_chatperson_Tectview_blockanddelete = (LinearLayout) itemview.findViewById(R.id.activity_dosomething_chatperson_Tectview_blockanddelete);
                activity_dosomething_chatperson_appIcon = (ImageView) itemview.findViewById(R.id.activity_dosomething_chatperson_appIcon);
                activity_dosomething_chatperson_name = (TextView) itemview.findViewById(R.id.activity_dosomething_chatperson_name);
                activity_dosomething_chatperson_time = (TextView) itemview.findViewById(R.id.activity_dosomething_chatperson_time);
                activity_dosomething_chatperson_message = (TextView) itemview.findViewById(R.id.activity_dosomething_chatperson_message);
                activity_dosomething_meassge_count = (TextView) itemview.findViewById(R.id.activity_dosomething_meassge_count);
                activity_dosomething_chatperson_Tectview_block = (TextView) itemview.findViewById(R.id.activity_dosomething_chatperson_Tectview_block);
                activity_dosomething_chatperson_Tectview_delete = (TextView) itemview.findViewById(R.id.activity_dosomething_chatperson_Tectview_delete);
            }
        }


        /*private void setAnimation(View viewToAnimate, int position)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {
                Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }*/
    }

    private int edit_position;
    private Paint p = new Paint();
    private View view;

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    chatAdapter.removeItem(position);
                } else {
                    removeView();
                    edit_position = position;

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_edit);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(dosomething_fragment_chatlist);
    }


    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
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


    private class ChatHistory extends AsyncTask<Void, Void, Boolean> {
        String status;
        private int UserId;
        Exception error;
        String chathistory_Api_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isLoader) {
                kbv.setVisibility(View.VISIBLE);
                timer = new Timer();
                timer.schedule(new AutoSlider(), 0, 1350);
                splashAnimation.start();
            }
            if (getActivity() != null) {
                sessionid = sharedPrefrences.getSessionid(getActivity());
                chathistory_Api_url = getActivity().getResources().getString(R.string.dosomething_apilink_string_getchathistory);

            }

        }

        @Override
        protected Boolean doInBackground(Void... params) {


            try {
                HashMap<String, Object> paramsfb = new HashMap<>();
                paramsfb.put(TAG_SESSIONID, sessionid);
                paramsfb.put(TAG_TIME, datetime);
                json_string = jsonfunctions.postToURL(chathistory_Api_url, paramsfb);
                Log.v("jason url=======>", String.valueOf(paramsfb));
                json_object = new JSONObject(json_string);
                if (json_object.has("getchathistory")) {
                    json_content = json_object.getJSONObject("getchathistory");


                    if (json_content.getString("status").equalsIgnoreCase("success")) {


                        if (json_content.has("converation")) {
                            JSONArray hobbiesArray = json_content.getJSONArray("converation");



                            for (int i = 0; i < hobbiesArray.length(); i++) {

                                JSONObject details = hobbiesArray.getJSONObject(i);

                                String send_request = details.getString("send_request");


                                if(send_request.equalsIgnoreCase("no"))
                                {
                                    if(i==0)
                                    {
                                        int id = details.getInt("id");
                                        ChatBean chatBeans = null;
                                        boolean isNewChat = false;
                                        if (listChatBeans.containsKey(id + "")) {
                                            chatBeans = listChatBeans.get(id + "");

                                        } else {
                                            chatBeans = new ChatBean();

                                            listChatBeanIds.add(id + "");
                                            isNewChat = true;
                                        }
                                        chatBeans.setChat_id(id);
                                        UserId = details.getInt("UserId");
                                        chatBeans.setUser_id(UserId);
                                        String Name = details.getString("Name");
                                        chatBeans.setName(Name);
                                        String userEmail = details.getString("userEmail");
                                        chatBeans.setUser_email(userEmail);


                                        String image1 = details.getString("image1_thumb");
                                        if (!isNewChat) {
                                            String imageOld = chatBeans.getImage1();
                                            chatBeans.setOldImageUrl(imageOld);
                                            chatBeans.setImage1(image1);
                                        } else {
                                            chatBeans.setImage1(image1);
                                        }

                                        String image2 = details.getString("image2");
                                        chatBeans.setImage2(image2);
                                        String image3 = details.getString("image3");
                                        chatBeans.setImage3(image3);
                                        String LastMessage = details.getString("LastMessage");
                                        chatBeans.setLast_message(LastMessage);
                                        String LastMessageSentTime = details.getString("LastMessageSentTime");

                                        String unreadmessage = details.getString("unreadmessage");
                                        chatBeans.setUnreadmessage(unreadmessage);
                                        String part2 = null;
                                        String hours = null;
                                        String min = null;
                                        String sec;
                                        if (!LastMessageSentTime.equals("")) {
                                            String string = LastMessageSentTime;
                                            String[] parts = string.split(" ");
                                            String part1 = parts[0];
                                            part2 = parts[1];
                                            Log.v("Data1", "" + part1);
                                            Log.v("Data2", "" + part2);
                                            String string1 = part2;
                                            String[] part = string1.split(":");
                                            hours = part[0];
                                            min = part[1];
                                            sec = part[2];
                                        } else {
                                            hours = "";
                                            min = "";
                                        }
                                        chatBeans.setLast_message_time(hours + ":" + min);
                                        listChatBeans.put(id + "", chatBeans);
                                        Log.d("chat_count", unreadmessage);
                                        status = "success";
                                    }



                                }else
                                {
                                    int id = details.getInt("id");
                                    ChatBean chatBeans = null;
                                    boolean isNewChat = false;
                                    if (listChatBeans.containsKey(id + "")) {
                                        chatBeans = listChatBeans.get(id + "");

                                    } else {
                                        chatBeans = new ChatBean();

                                        listChatBeanIds.add(id + "");
                                        isNewChat = true;
                                    }
                                    chatBeans.setChat_id(id);
                                    UserId = details.getInt("UserId");
                                    chatBeans.setUser_id(UserId);
                                    String Name = details.getString("Name");
                                    chatBeans.setName(Name);
                                    String userEmail = details.getString("userEmail");
                                    chatBeans.setUser_email(userEmail);


                                    String image1 = details.getString("image1_thumb");
                                    if (!isNewChat) {
                                        String imageOld = chatBeans.getImage1();
                                        chatBeans.setOldImageUrl(imageOld);
                                        chatBeans.setImage1(image1);
                                    } else {
                                        chatBeans.setImage1(image1);
                                    }

                                    String image2 = details.getString("image2");
                                    chatBeans.setImage2(image2);
                                    String image3 = details.getString("image3");
                                    chatBeans.setImage3(image3);
                                    String LastMessage = details.getString("LastMessage");
                                    chatBeans.setLast_message(LastMessage);
                                    String LastMessageSentTime = details.getString("LastMessageSentTime");

                                    String unreadmessage = details.getString("unreadmessage");
                                    chatBeans.setUnreadmessage(unreadmessage);
                                    String part2 = null;
                                    String hours = null;
                                    String min = null;
                                    String sec;
                                    if (!LastMessageSentTime.equals("")) {
                                        String string = LastMessageSentTime;
                                        String[] parts = string.split(" ");
                                        String part1 = parts[0];
                                        part2 = parts[1];
                                        Log.v("Data1", "" + part1);
                                        Log.v("Data2", "" + part2);
                                        String string1 = part2;
                                        String[] part = string1.split(":");
                                        hours = part[0];
                                        min = part[1];
                                        sec = part[2];
                                    } else {
                                        hours = "";
                                        min = "";
                                    }
                                    chatBeans.setLast_message_time(hours + ":" + min);
                                    listChatBeans.put(id + "", chatBeans);
                                    Log.d("chat_count", unreadmessage);
                                    status = "success";
                                }

                            }


                        } else {
                            status = "No Conversaion Details Found";


                            pd.dismiss();


                        }


                    } else if (json_content.getString("status").equalsIgnoreCase("failed")) {
                        pd.dismiss();
                        status = "No Conversaion Details Found";


                    }
                } else if (json_object.has("error")) {
                    if (json_object.getString("error").equalsIgnoreCase("InvalidSession")) {
                        status = "InvalidSession";


                    }
                }


                return true;

            } catch (JSONException e) {
                e.printStackTrace();
                error = e;
                return false;

            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            {

                if (result) {
                    try {

                        if (status != null) {
                            switch (status) {
                                case "success":
                                    if (getActivity() != null) {

                                        kbv.setVisibility(View.GONE);
                                        if (timer != null) {

                                            timer.cancel();


                                            timer = null;

                                        }
                                        splashAnimation.stop();
                                        ((MyApplication) getActivity().getApplication()).setListChatBean(listChatBeans);
                                        ((MyApplication) getActivity().getApplication()).setChatBeanIdsList(listChatBeanIds);
                                        dosomething_fragment_chatlist.setHasFixedSize(true);
                                        mLayoutManager = new LinearLayoutManager(getActivity());
                                        dosomething_fragment_chatlist.setLayoutManager(mLayoutManager);
                                        if (chatAdapter == null) {
                                            chatAdapter = new RecyclerAdapter(getActivity());
                                            dosomething_fragment_chatlist.setAdapter(chatAdapter);
//                                            initSwipe();
                                        } else {

                                            chatAdapter.notifyDataSetChanged();
//                                            initSwipe();
                                        }
                                        dosomething_fragment_chatlist.getItemAnimator().setSupportsChangeAnimations(false);


                                        isLoader = true;
                                        pd.dismiss();

                                    }
                                    break;
                                case "No Conversaion Details Found":
                                    kbv.setVisibility(View.GONE);
                                    if (timer != null) {

                                        timer.cancel();


                                        timer = null;

                                    }
                                    splashAnimation.stop();
                                    chat_noconversation_textview.setText("No Conversaion Details Found");

                                    break;

                                case "InvalidSession":
                                    kbv.setVisibility(View.GONE);
                                    if (timer != null) {

                                        timer.cancel();


                                        timer = null;

                                    }
                                    splashAnimation.stop();

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
                                    break;
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (error != null) {
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
    }


    public class DosomethingBlockUser extends AsyncTask<Void, Void, Void> {
        String blockStatus;
        String block_Api_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (getActivity() != null) {
                block_Api_url = getActivity().getResources().getString(R.string.dosomething_apilink_string_blockuser);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HashMap<String, Object> paramsBlock = new HashMap<>();
                paramsBlock.put(TAG_SESSIONID, sharedPrefrences.getSessionid(getActivity()));
                paramsBlock.put(TAG_CONVERSATIONID, sharedPrefrences.getConversationId(getActivity()));
                json_string = jsonfunctions.postToURL(block_Api_url, paramsBlock);
                try {
                    json_object = new JSONObject(json_string);
                    if (json_object.has("blockuser")) {
                        json_content = json_object.getJSONObject("blockuser");
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
                            blockStatus = "This User is Blocked Successfully";
                        }
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


                    if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                        new ChatHistory().execute();
                    }


                    break;
            }
        }
    }


    public class DosomethingDeleteConversation extends AsyncTask<Void, Void, Void> {
        String blockStatus="";
        String deleteConversation_Api_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (getActivity() != null) {
                deleteConversation_Api_url = getActivity().getResources().getString(R.string.dosomething_apilink_string_unmatch);
            }

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                HashMap<String, Object> paramsBlock = new HashMap<>();
                paramsBlock.put(TAG_SESSIONID, sharedPrefrences.getSessionid(getActivity()));
                paramsBlock.put(TAG_FRIENDUSERID, sharedPrefrences.getFriendUserId(getActivity()).trim());
                json_string = jsonfunctions.postToURL(deleteConversation_Api_url, paramsBlock);
                try {
                    json_object = new JSONObject(json_string);
                    if (json_object.has("cancelrequest")) {
                        json_content = json_object.getJSONObject("cancelrequest");
                        if (json_content.getString("status").equalsIgnoreCase("success")) {
                            blockStatus = "Conversaion has been Cleared";
                        } else if (json_content.getString("status").equalsIgnoreCase("error")) {
                            blockStatus = "Conversaion has been Cleared";
                        }
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
                    ((MyApplication) getActivity().getApplication()).getListChatBean().clear();
                    ((MyApplication) getActivity().getApplication()).getChatBeanIdsList().clear();

                    if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                        new ChatHistory().execute();

                    }
                    break;
                case "":
                    ((MyApplication) getActivity().getApplication()).getListChatBean().clear();
                    ((MyApplication) getActivity().getApplication()).getChatBeanIdsList().clear();
                    if (NetworkCheck.isWifiAvailable(getActivity()) || NetworkCheck.isNetworkAvailable(getActivity())) {
                        new ChatHistory().execute();

                    }
                    break;
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
