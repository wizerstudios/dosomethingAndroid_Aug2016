package com.dosomething.android;

import android.app.Application;
import android.support.multidex.MultiDex;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import com.dosomething.android.Beanclasses.ActivityBean;
import com.dosomething.android.Beanclasses.Arts_hobbies;
import com.dosomething.android.Beanclasses.ChatBean;
import com.dosomething.android.Beanclasses.Dosomething_Bean;
import com.dosomething.android.Beanclasses.Filterbean;
import com.dosomething.android.Beanclasses.Food_hobbies;
import com.dosomething.android.Beanclasses.HobbiesBean;
import com.dosomething.android.Beanclasses.Pets_hobbies;
import com.dosomething.android.Beanclasses.Recreation_hobbies;
import com.dosomething.android.Fragments.DoSomething_Friends_profile_fragment;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.HashMap;

import io.fabric.sdk.android.Fabric;

/**
 * Created by hi on 30-Oct-15.
 */
public class MyApplication extends Application {
    private Tracker mTracker;

    DoSomething_Friends_profile_fragment mDoSomething_Friends_profile_fragment;
    DoSomethingStatus doSomethingStatus;
int anInt;
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
        Fabric.with(this, new Crashlytics());
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