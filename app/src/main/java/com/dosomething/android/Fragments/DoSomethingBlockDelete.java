package com.dosomething.android.Fragments;


import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dosomething.android.R;
import com.google.android.gms.analytics.Tracker;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoSomethingBlockDelete.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DoSomethingBlockDelete#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoSomethingBlockDelete extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    TextView activity_dosomething_chatblock;
    TextView activity_dosomething_chatdelete;
    TextView activity_dosomething_chatcancel;
    Typeface patron_bold;
    Typeface patron_regular;
    Typeface patron_medium;
    Typeface myfonts_thin;
    private Tracker mTracker;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoSomethingBlockDelete.
     */
    // TODO: Rename and change types and number of parameters
    public static DoSomethingBlockDelete newInstance(String param1, String param2) {
        DoSomethingBlockDelete fragment = new DoSomethingBlockDelete();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DoSomethingBlockDelete() {
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
        View view = inflater.inflate(R.layout.fragment_do_something_block_delete, container, false);

        activity_dosomething_chatblock = (TextView) view.findViewById(R.id.activity_dosomething_chatblock);
        activity_dosomething_chatdelete = (TextView) view.findViewById(R.id.activity_dosomething_chatdelete);
        activity_dosomething_chatcancel = (TextView) view.findViewById(R.id.activity_dosomething_chatcancel);
        text_font_typeface();
        activity_dosomething_chatblock.setTypeface(patron_regular);
        activity_dosomething_chatcancel.setTypeface(patron_regular);
        activity_dosomething_chatdelete.setTypeface(patron_regular);
        activity_dosomething_chatblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSomethingChatBox doSomethingChatBox = new DoSomethingChatBox();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatBox);
                fragmentTransaction.commit();
            }
        });
        activity_dosomething_chatdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSomethingChatBox doSomethingChatBox = new DoSomethingChatBox();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatBox);
                fragmentTransaction.commit();
            }
        });
        activity_dosomething_chatcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoSomethingChatBox doSomethingChatBox = new DoSomethingChatBox();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                fragmentTransaction.replace(R.id.detail_fragment, doSomethingChatBox);
                fragmentTransaction.commit();
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

}
