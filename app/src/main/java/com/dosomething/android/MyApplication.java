package com.dosomething.android;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.dosomething.android.Beanclasses.ActivityBean;
import com.dosomething.android.Beanclasses.Arts_hobbies;
import com.dosomething.android.Beanclasses.ChatBean;
import com.dosomething.android.Beanclasses.Dosomething_Bean;
import com.dosomething.android.Beanclasses.Filterbean;
import com.dosomething.android.Beanclasses.Food_hobbies;
import com.dosomething.android.Beanclasses.HobbiesBean;
import com.dosomething.android.Beanclasses.Pets_hobbies;
import com.dosomething.android.Beanclasses.Recreation_hobbies;
import com.dosomething.android.Fragments.DoSomethingNearMe;
import com.dosomething.android.Fragments.DoSomething_Friends_profile_fragment;
import com.dosomething.android.Fragments.FragmentProfile;
import com.dosomething.android.Fragments.UserProfileImage1_Fragment;
import com.dosomething.android.Fragments.UserProfileImage2_Fragment;
import com.dosomething.android.Fragments.UserProfileImage3_Fragment;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;

/**
 * Created by hi on 30-Oct-15.
 */
public class MyApplication extends Application {
    private Tracker mTracker;

    DoSomething_Friends_profile_fragment mDoSomething_Friends_profile_fragment;
    DoSomethingNearMe doSomethingNearMe;
    DoSomethingStatus doSomethingStatus;
    UserProfileImage1_Fragment userProfileImage1_fragment;
    UserProfileImage2_Fragment userProfileImage2_fragment;

    public DoSomethingNearMe getDoSomethingNearMe() {
        return doSomethingNearMe;
    }

    public void setDoSomethingNearMe(DoSomethingNearMe doSomethingNearMe) {
        this.doSomethingNearMe = doSomethingNearMe;
    }

    UserProfileImage3_Fragment userProfileImage3_fragment;
    Profile_image_one_fragment profile_image_viewpager_dots_one;
    Profile_image_two_fragment profile_image_two_fragment;
    Profile_image_three_fragment profile_image_three_fragment;

FragmentProfile fragmentProfile;

    public FragmentProfile getFragmentProfile() {
        return fragmentProfile;
    }

    public void setFragmentProfile(FragmentProfile fragmentProfile) {
        this.fragmentProfile = fragmentProfile;
    }

    public Profile_image_one_fragment getProfile_image_one_fragment() {
        return profile_image_viewpager_dots_one;
    }

    public void setProfile_image_one_fragment(Profile_image_one_fragment profile_image_viewpager_dots_one) {
        this.profile_image_viewpager_dots_one = profile_image_viewpager_dots_one;
    }


    public Profile_image_two_fragment getProfile_image_two_fragment() {
        return profile_image_two_fragment;
    }

    public void setProfile_image_two_fragment(Profile_image_two_fragment profile_image_two_fragment) {
        this.profile_image_two_fragment = profile_image_two_fragment;
    }


    public Profile_image_three_fragment getProfile_image_three_fragment() {
        return profile_image_three_fragment;
    }

    public void setProfile_image_three_fragment(Profile_image_three_fragment profile_image_three_fragment) {
        this.profile_image_three_fragment = profile_image_three_fragment;
    }

    public UserProfileImage3_Fragment getUserProfileImage3_fragment() {
        return userProfileImage3_fragment;
    }

    public void setUserProfileImage3_fragment(UserProfileImage3_Fragment userProfileImage3_fragment) {
        this.userProfileImage3_fragment = userProfileImage3_fragment;
    }

    public UserProfileImage2_Fragment getUserProfileImage2_fragment() {
        return userProfileImage2_fragment;
    }

    public void setUserProfileImage2_fragment(UserProfileImage2_Fragment userProfileImage2_fragment) {
        this.userProfileImage2_fragment = userProfileImage2_fragment;
    }

    public UserProfileImage1_Fragment getUserProfileImage1_fragment() {
        return userProfileImage1_fragment;
    }

    public void setUserProfileImage1_fragment(UserProfileImage1_Fragment userProfileImage1_fragment) {
        this.userProfileImage1_fragment = userProfileImage1_fragment;
    }

    int anInt;
    int count=0;
    ArrayList<Filterbean> listFilterBeans = new ArrayList<>();
    HashMap<String, ChatBean> chatBeanList = new HashMap<>();
    ArrayList<String> chatBeanIdsList = new ArrayList<>();
    ArrayList<ActivityBean> activityBeans = new ArrayList<>();
    ArrayList<String> activityBeansIdsList = new ArrayList<>();
    ArrayList<Dosomething_Bean> dosomething_beans = new ArrayList<>();
    ArrayList<HobbiesBean> hobbiesBeans = new ArrayList<>();
    ArrayList<Arts_hobbies> arts_hobbies = new ArrayList<>();
    ArrayList<Food_hobbies> food_hobbies = new ArrayList<>();
    ArrayList<Pets_hobbies> pets_hobbies = new ArrayList<>();
    ArrayList<Recreation_hobbies> recreation_hobbies = new ArrayList<>();
    ArrayList<Dosomething_Bean> activity_bean=new ArrayList<>();

    public int getanInt() {
        return anInt;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public int getCount() {
        return count;
    }

    public void setanInt(int anInt) {
        this.anInt = anInt;
    }
    public ArrayList<Arts_hobbies> getArts_hobbies() {
        return arts_hobbies;
    }

    public void setArts_hobbies(ArrayList<Arts_hobbies> arts_hobbies) {
        this.arts_hobbies = arts_hobbies;
    }

    public ArrayList<Food_hobbies> getFood_hobbies() {
        return food_hobbies;
    }

    public void setFood_hobbies(ArrayList<Food_hobbies> food_hobbies) {
        this.food_hobbies = food_hobbies;
    }

    public ArrayList<Pets_hobbies> getPets_hobbies() {
        return pets_hobbies;
    }

    public void setPets_hobbies(ArrayList<Pets_hobbies> pets_hobbies) {
        this.pets_hobbies = pets_hobbies;
    }

    public ArrayList<Recreation_hobbies> getRecreation_hobbies() {
        return recreation_hobbies;
    }

    public void setRecreation_hobbies(ArrayList<Recreation_hobbies> recreation_hobbies) {
        this.recreation_hobbies = recreation_hobbies;
    }


    public ArrayList<HobbiesBean> getListHobbies() {
        return hobbiesBeans;
    }

    public void setListHobbies(ArrayList<HobbiesBean> hobbiesBeans) {
        this.hobbiesBeans = hobbiesBeans;
    }



    public ArrayList<Dosomething_Bean> getListActivityBean() {
        return activity_bean;
    }

    public void setListActivityBean(ArrayList<Dosomething_Bean> activity_bean) {
        this.activity_bean = activity_bean;
    }


    public ArrayList<Dosomething_Bean> getListDosomethingBean() {
        return dosomething_beans;
    }

    public void setListDosomethingBean(ArrayList<Dosomething_Bean> listDosomethingBean) {

        this.dosomething_beans = listDosomethingBean;
    }

    public HashMap<String, ChatBean> getListChatBean() {
        return chatBeanList;
    }

    public void setListChatBean(HashMap<String, ChatBean> chatBeanArrayList) {
        this.chatBeanList = chatBeanArrayList;
    }

    public ArrayList<String> getChatBeanIdsList() {
        return chatBeanIdsList;
    }

    public void setChatBeanIdsList(ArrayList<String> chatBeanIdsList) {
        this.chatBeanIdsList = chatBeanIdsList;
    }


    public ArrayList<ActivityBean> getActivityBeans() {
        return activityBeans;
    }

    public void setActivityBeans(ArrayList<ActivityBean> activityBeans) {
        this.activityBeans = activityBeans;
    }

    public ArrayList<String> getActivityBeansIdsList() {
        return activityBeansIdsList;
    }

    public void setActivityBeansIdsList(ArrayList<String> activityBeansIdsList) {
        this.activityBeansIdsList = activityBeansIdsList;
    }

    public ArrayList<Filterbean> getListFilterBeans() {
        return listFilterBeans;
    }

    public void setListFilterBeans(ArrayList<Filterbean> listFilterBeans) {
        this.listFilterBeans = listFilterBeans;
    }

    
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(getBaseContext());
//        Fabric.with(this, new Crashlytics());
        try
        {
            mTracker = this.getDefaultTracker();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public DoSomething_Friends_profile_fragment getmDoSomething_Friends_profile_fragment() {
        return mDoSomething_Friends_profile_fragment;
    }

    public void setmDoSomething_Friends_profile_fragment(DoSomething_Friends_profile_fragment mDoSomething_Friends_profile_fragment) {
        this.mDoSomething_Friends_profile_fragment = mDoSomething_Friends_profile_fragment;
    }

    public DoSomethingStatus getDoSomethingStatus() {
        return doSomethingStatus;
    }

    public void setDoSomethingStatus(DoSomethingStatus doSomethingStatus) {
        this.doSomethingStatus = doSomethingStatus;
    }


    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

}