package com.dosomething.android.CommonClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by hi on 10/13/2015.
 */
public class SharedPrefrences {
    static String MyPREFERENCES = "MyPrefs";
    String Login = "loginKey";
    String EMAIL = "emailKey";
    String FriendLogin = "FriendemailKey";
    String Pwd = "pwdkey";
    String UpdatePwd = "Updatepwdkey";
    String UserId = "userid";
    String FriendUserId = "Frienduserid";
    String DeviceID = "device_id";
    String ProfileID = "profile_id";
    String FriendProfileID = "Friendprofile_id";
    String ConversationId = "conversationId";
    String FirstName = "firstname";
    String FriendFirstName = "Friendfirstname";
    String Lastname = "lastname";
    String FriendLastname = "Friendlastname";
    String Gender = "gender";
    String FriendGender = "Friendgender";
    String FriendAge = "Friendage";
    String About = "about";
    String FriendAbout = "Friendabout";
    String DateOfbirth = "dateofbirth";
    String FriendDateOfbirth = "Frienddateofbirth";
    String Latitude = "latitude";
    String Longitude = "longitude";
    String Sessionid = "sessionid";
    String Op = "op";
    String AvailableNow = "availablenow";
    String FilterStatus = "filterstatus";
    String FilterAvailable = "filteravailable";
    String FilterGender = "filtergender";
    String FilterAge = "filterage";
    String FilterDistance = "filterdistance";
    String LoginType = "logintype";

    String FriendProfilePicture = "friendProfilePicture";
    String FriendProfilePicture1 = "friendProfilePicture1";
    String FriendProfilePicture2 = "friendProfilePicture2";
    String FBProfilePicture = "fBprofilepicture";
    String ProfilePicture = "profilepicture";
    String ProfilePicture1 = "profilepicture1";
    String ProfilePicture2 = "profilepicture2";
    String UpdateProfilePicture = "updateprofilepicture";
    String UpdateProfilePicture1 = "updateprofilepicture1";
    String UpdateProfilePicture2 = "updateprofilepicture2";
    String Message = "message";
    String Vibration = "vibration";
    String Sound = "sound";
    String Setting_Sound = "setting_sound";
    String Match = "do_match";
    String Boolean = "boolean";
    String DosomethingApiVersion = "dosomethingapiversion";
    String Version = "version";
    String Hobbies = "hobbies";
    String HobbiesId = "hobbiesId";
    String HobbiesStringArray = "hobbiesStringArray";
    String ProfileImageBitmap1 = "profileimagebitmap1";
    String ProfileImageBitmap2 = "profileimagebitmap2";
    String ProfileImageBitmap3 = "profileimagebitmap3";
    String Dosomething_filterImage_Visibility = "Dosomething_filterImage_Visibility";
    String ShowPassword = "showpassword";
    String ClickDosomething = "click";
    String SendRequest = "sendrequest";
    String Registervia = "registervia";
    String PushType = "push_type";
    String WalkThroughSplash = "walkthroughsplash";
    String WalkThroughCreate = "walkthroughcreate";
    String WalkThroughprofile = "walkthroughprofile";
    String WalkThroughhobbies = "walkthroughhobbies";
    String WalkThroughprofilesave = "walkthroughprofilesave";
    String WalkThroughHomescreen = "walkthroughHomescreen";
    String WalkThroughActivity = "walkthroughActivity";
    String WalkThroughNearme = "walkthroughnear";
    String WalkThroughmatch = "walkthroughmatch";
    String WalkThroughchat = "walkthroughchat";

    public SharedPrefrences() {
        super();
    }

    public SharedPrefrences(FragmentActivity activity) {

    }


    public String setWalkThrough_Splash(Context context, String walkThroughSplash) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughSplash, walkThroughSplash);

        editor.commit();
        return walkThroughSplash;
    }


    public String getWalkThrough_Splash(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughSplash, "false");

        return text;
    }


    public String setWalkThroughCreate(Context context, String walkThroughCreate) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughCreate, walkThroughCreate);

        editor.commit();
        return walkThroughCreate;
    }


    public String getWalkThroughCreate(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughCreate, "false");

        return text;
    }

    public String setWalkThroughProfile(Context context, String walkThroughprofile) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughprofile, walkThroughprofile);

        editor.commit();
        return walkThroughprofile;
    }


    public String getWalkThroughprofile(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughprofile, "false");

        return text;
    }


    public String setWalkThroughProfilesave(Context context, String walkThroughprofilesave) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughprofilesave, walkThroughprofilesave);

        editor.commit();
        return walkThroughprofilesave;
    }


    public String getWalkThroughProfilesave(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughprofilesave, "false");

        return text;
    }


    public String setWalkThroughHobbies(Context context, String walkThroughhobbies) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughhobbies, walkThroughhobbies);

        editor.commit();
        return walkThroughhobbies;
    }


    public String getWalkThroughhobbies(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughhobbies, "false");

        return text;
    }



    public String setWalkThroughHomescreen(Context context, String walkThroughHomescreen) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughHomescreen, walkThroughHomescreen);

        editor.commit();
        return walkThroughHomescreen;
    }


    public String getWalkThroughHomescreen(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughHomescreen, "false");

        return text;
    }


    public String setWalkThroughActivity(Context context, String walkThroughActivity) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughActivity, walkThroughActivity);

        editor.commit();
        return walkThroughActivity;
    }


    public String getWalkThroughActivity(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughActivity, "false");

        return text;
    }

    public String setWalkThroughNearme(Context context, String walkThroughNearme) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughNearme, walkThroughNearme);

        editor.commit();
        return walkThroughNearme;
    }


    public String getWalkThroughNearme(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughNearme, "false");

        return text;
    }


    public String setWalkThroughMatch(Context context, String walkThroughmatch) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughmatch, walkThroughmatch);

        editor.commit();
        return walkThroughmatch;
    }


    public String getWalkThroughMatch(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughmatch, "false");

        return text;
    }


    public String setWalkThroughchat(Context context, String walkThroughmatch) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(WalkThroughchat, walkThroughmatch);

        editor.commit();
        return walkThroughmatch;
    }


    public String getWalkThroughchat(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(WalkThroughchat, "false");

        return text;
    }


    public String setDosomething_filterImage_Visibility(Context context, String dosomething_filterImage_Visibility) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Dosomething_filterImage_Visibility, dosomething_filterImage_Visibility);

        editor.commit();
        return dosomething_filterImage_Visibility;
    }

    public String setSetting_Sound(Context context, String setting_Sound) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Setting_Sound, setting_Sound);

        editor.commit();
        return setting_Sound;
    }


    public String getSetting_Sound(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Setting_Sound, "");

        return text;
    }



    public String setPushType(Context context, String pushType) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(PushType, pushType);

        editor.commit();
        return pushType;
    }


    public String getPushType(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(PushType, "");

        return text;
    }


    public String setRegistervia(Context context, String registervia) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Registervia, registervia);

        editor.commit();
        return registervia;
    }


    public String getRegistervia(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Registervia, "");

        return text;
    }


    public String setSendRequest(Context context, String sendRequest) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(SendRequest, sendRequest);

        editor.commit();
        return sendRequest;
    }

    public String getSendRequest(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(SendRequest, "");

        return text;
    }

    public String setClickDosomething(Context context, String clickDosomething) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ClickDosomething, clickDosomething);

        editor.commit();
        return clickDosomething;
    }


    public String getClickDosomething(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ClickDosomething, "");

        return text;
    }

    public String setShowPassword(Context context, String showPassword) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ShowPassword, showPassword);

        editor.commit();
        return showPassword;
    }


    public String getShowPassword(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ShowPassword, "");

        return text;
    }


    public String setLoginType(Context context, String loginType) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(LoginType, loginType);

        editor.commit();
        return loginType;
    }

    public String getLoginType(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(LoginType, "");

        return text;
    }


    public String getDosomething_filterImage_Visibility(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Dosomething_filterImage_Visibility, "");

        return text;
    }


    public String setProfileImageBitmap1(Context context, String profileImageBitmap1) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ProfileImageBitmap1, profileImageBitmap1);

        editor.commit();
        return profileImageBitmap1;
    }

    public String setProfileImageBitmap2(Context context, String profileImageBitmap2) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ProfileImageBitmap2, profileImageBitmap2);

        editor.commit();
        return profileImageBitmap2;
    }

    public String setProfileImageBitmap3(Context context, String profileImageBitmap3) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ProfileImageBitmap3, profileImageBitmap3);

        editor.commit();
        return profileImageBitmap3;
    }

    public String getProfileImageBitmap1(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ProfileImageBitmap1, "");

        return text;
    }

    public String getProfileImageBitmap2(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ProfileImageBitmap2, "");

        return text;
    }

    public String getProfileImageBitmap3(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ProfileImageBitmap3, "");

        return text;
    }


    public String setHobbies(Context context, String hobbies) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Hobbies, hobbies);

        editor.commit();
        return hobbies;
    }


    public StringBuilder setHobbiesId(Context context, StringBuilder hobbiesId) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(HobbiesId, String.valueOf(hobbiesId));

        editor.commit();
        return hobbiesId;
    }


    public String getHobbiesId(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(HobbiesId, "");

        return text;
    }

    public String setBooleam(Context context, String aBoolean) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Boolean, aBoolean);

        editor.commit();
        return aBoolean;
    }

    public String getHobbies(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Hobbies, "");

        return text;
    }

    public String getBoolean(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Boolean, "false");

        return text;
    }

    public String setDoSoethingApiVersion(Context context, String dosomethingApiVersion) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(DosomethingApiVersion, dosomethingApiVersion);

        editor.commit();
        return dosomethingApiVersion;
    }

    public String getDosomethingApiVersion(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(DosomethingApiVersion, "");

        return text;
    }

    public String setLogin(Context context, String login) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Login, login);

        editor.commit();
        return login;
    }

    public String setEmail(Context context, String email) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(EMAIL, email);

        editor.commit();
        return email;
    }

    public String setFriendLogin(Context context, String friendlogin) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendLogin, friendlogin);

        editor.commit();
        return friendlogin;
    }

    public String getEMAIl(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(EMAIL, "");

        return text;
    }

    public String getLogin(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Login, "");

        return text;
    }

    public String getFriendLogin(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendLogin, "");

        return text;
    }

    public String setPassword(Context context, String pwd) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Pwd, pwd);

        editor.commit();
        return pwd;
    }

    public String setUpdatePassword(Context context, String friendpwd) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(UpdatePwd, friendpwd);

        editor.commit();
        return friendpwd;
    }

    public String getpassword(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Pwd, "");

        return text;
    }

    public String getUpdatepassword(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(UpdatePwd, "");

        return text;
    }

    public String setDeviceToken(Context context, String deviceID) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(DeviceID, deviceID);

        editor.commit();
        return deviceID;
    }

    public String setVersion_dosomething(Context context, String deviceID) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Version, deviceID);
        editor.commit();
        return deviceID;
    }

    public String setVersion_hobbies(Context context, String deviceID) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Version, deviceID);
        editor.commit();
        return deviceID;
    }

    public String getVersion_dosomething(Context context) {
        SharedPreferences settings;
        String text;
        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Version, "");
        return text;
    }

    public String getVersion_hobbies(Context context) {
        SharedPreferences settings;
        String text;
        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Version, "");
        return text;
    }

    public String getDeviceToken(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(DeviceID, "");

        return text;
    }

    public String setFirstname(Context context, String firstName) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FirstName, firstName);

        editor.commit();
        return firstName;
    }

    public String setFriendFirstname(Context context, String friendfirstName) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendFirstName, friendfirstName);

        editor.commit();
        return friendfirstName;
    }

    public String getFirstName(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FirstName, "");

        return text;
    }

    public String getFriendFirstName(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendFirstName, "");

        return text;
    }

    public String setFriendLastname(Context context, String lastname) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendLastname, lastname);

        editor.commit();
        return lastname;
    }

    public String setLastname(Context context, String lastname) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Lastname, lastname);

        editor.commit();
        return lastname;
    }

    public String getLastname(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Lastname, "");

        return text;
    }

    public String getFriendLastname(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendLastname, "");

        return text;
    }

    public String setGender(Context context, String gender) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Gender, gender);

        editor.commit();
        return gender;
    }

    public String setFriendGender(Context context, String gender) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendGender, gender);

        editor.commit();
        return gender;
    }

    public String setFriendAge(Context context, String friendAge) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendAge, friendAge);

        editor.commit();
        return friendAge;
    }

    public String getFriendAge(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendAge, "");

        return text;
    }

    public String getGender(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Gender, "");

        return text;
    }

    public String getFriendGender(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendGender, "");

        return text;
    }

    public String setDateofBirth(Context context, String dateOfbirth) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(DateOfbirth, dateOfbirth);

        editor.commit();
        return dateOfbirth;
    }

    public String setFriendDateofBirth(Context context, String dateOfbirth) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendDateOfbirth, dateOfbirth);

        editor.commit();
        return dateOfbirth;
    }

    public String getDateOfbirth(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(DateOfbirth, "");

        return text;
    }

    public String getFriendDateOfbirth(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendDateOfbirth, "");

        return text;
    }

    public String setLatitude(Context context, String latitude) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Latitude, latitude);

        editor.commit();
        return latitude;
    }

    public String getLatitude(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Latitude, "");

        return text;
    }

    public String setLongtitude(Context context, String longitude) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Longitude, longitude);

        editor.commit();
        return longitude;
    }

    public String getLongitude(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Longitude, "");

        return text;
    }

    public String setFilterStatus(Context context, String filterStatus) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FilterStatus, filterStatus);

        editor.commit();
        return filterStatus;
    }

    public String setFilterAvailable(Context context, String filterAvailable) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FilterAvailable, filterAvailable);

        editor.commit();
        return filterAvailable;
    }

    public String setFilterGender(Context context, String filterGender) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FilterGender, filterGender);

        editor.commit();
        return filterGender;
    }

    public String setFilterAge(Context context, String filterAge) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FilterAge, filterAge);

        editor.commit();
        return filterAge;
    }

    public String setFilterDistance(Context context, String filterDistance) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FilterDistance, filterDistance);

        editor.commit();
        return filterDistance;
    }

    public String setOp(Context context, String op) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Op, op);

        editor.commit();
        return op;
    }

    public String setAvailable(Context context, String availableNow) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(AvailableNow, availableNow);

        editor.commit();
        return availableNow;
    }

    public String setSessionid(Context context, String sessionid) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Sessionid, sessionid);

        editor.commit();
        return sessionid;
    }

    public String getSessionid(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Sessionid, "");

        return text;
    }

    public String getOp(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Op, "getlast");

        return text;
    }

    public String getAvailable(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(AvailableNow, "");

        return text;
    }

    public String getFilterStatus(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FilterStatus, "");

        return text;
    }

    public String getFilterAvailable(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FilterAvailable, "");

        return text;
    }

    public String getFilterGender(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FilterGender, "");

        return text;
    }

    public String getFilterAge(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FilterAge, "");

        return text;
    }

    public String getFilterDistance(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FilterDistance, "");

        return text;
    }

    public String setProfileId(Context context, String profileID) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ProfileID, profileID);

        editor.commit();
        return profileID;
    }

    public String setConversationId(Context context, String conversationId) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ConversationId, conversationId);

        editor.commit();
        return conversationId;
    }

    public String getConversationId(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ConversationId, "");

        return text;
    }

    public String getProfileID(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ProfileID, "");

        return text;
    }

    public String setUserId(Context context, String userId) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(UserId, userId);

        editor.commit();
        return userId;
    }

    public String setFriendUserId(Context context, String userId) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        Log.e("userId", userId);
        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendUserId, userId);

        editor.commit();
        return userId;
    }

    public String getFriendUserId(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendUserId, "");

        return text;
    }

    public String getUserId(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(UserId, "");

        return text;
    }

    public String setProfilePicture(Context context, String profilePicture) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ProfilePicture, profilePicture);

        editor.commit();
        return profilePicture;
    }

    public String setUpdateProfilePicture(Context context, String updateProfilePicture) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(UpdateProfilePicture, updateProfilePicture);

        editor.commit();
        return updateProfilePicture;
    }

    public String setFBProfilePicture(Context context, String fbprofilepicture) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FBProfilePicture, fbprofilepicture);

        editor.commit();
        return fbprofilepicture;
    }

    public String setAbout(Context context, String about) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(About, about);

        editor.commit();
        return about;
    }

    public String setFriendAbout(Context context, String about) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendAbout, about);

        editor.commit();
        return about;
    }

    public String setProfilePicture1(Context context, String profilePicture1) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ProfilePicture1, profilePicture1);

        editor.commit();
        return profilePicture1;
    }

    public String setUpdateProfilePicture1(Context context, String updateProfilePicture1) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(UpdateProfilePicture1, updateProfilePicture1);

        editor.commit();
        return updateProfilePicture1;
    }

    public String setProfilePicture2(Context context, String profilePicture2) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(ProfilePicture2, profilePicture2);

        editor.commit();
        return profilePicture2;
    }

    public String setUpdateProfilePicture2(Context context, String updateProfilePicture2) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(UpdateProfilePicture2, updateProfilePicture2);

        editor.commit();
        return updateProfilePicture2;
    }

    public String setFriendProfilePicture(Context context, String fbprofilepicture) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendProfilePicture, fbprofilepicture);

        editor.commit();
        return fbprofilepicture;
    }

    public String setFriendProfilePicture1(Context context, String fbprofilepicture1) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendProfilePicture1, fbprofilepicture1);

        editor.commit();
        return fbprofilepicture1;
    }

    public String setFriendProfilePicture2(Context context, String fbprofilepicture1) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(FriendProfilePicture2, fbprofilepicture1);

        editor.commit();
        return fbprofilepicture1;
    }

    public String setNotifyMessage(Context context, String message) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Message, message);

        editor.commit();
        return message;
    }


    public String setNotifyMatch(Context context, String match) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Match, match);

        editor.commit();
        return match;
    }

    public String setNotifySound(Context context, String sound) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Sound, sound);

        editor.commit();
        return sound;
    }

    public String setNotifyVibration(Context context, String vibration) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;

        //sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putString(Vibration, vibration);

        editor.commit();
        return vibration;
    }

    public String getNotifyMessage(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Message, "");

        return text;
    }

    public String getNotifyVibration(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Vibration, "");

        return text;
    }


    public String getNotifyMatch(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Match, "");

        return text;
    }

    public String getNotifySound(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(Sound, "");

        return text;
    }

    public String getFriendProfilePicture(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendProfilePicture, "");

        return text;
    }

    public String getFriendProfilePicture1(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendProfilePicture1, "");

        return text;
    }

    public String getFriendProfilePicture2(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendProfilePicture2, "");

        return text;
    }

    public String getProfilePicture(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ProfilePicture, "");

        return text;
    }

    public String getUpdateProfilePicture(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(UpdateProfilePicture, "");

        return text;
    }

    public String getUpdateProfilePicture1(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(UpdateProfilePicture1, "");

        return text;
    }

    public String getUpdateProfilePicture2(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(UpdateProfilePicture2, "");

        return text;
    }

    public String getAbout(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(About, "");

        return text;
    }

    public String getFriendAbout(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FriendAbout, "");

        return text;
    }

    public String getFBProfilePicture(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(FBProfilePicture, "");

        return text;
    }

    public String getProfilePicture1(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ProfilePicture1, "");

        return text;
    }

    public String getProfilePicture2(Context context) {
        SharedPreferences settings;
        String text;


        // settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        text = settings.getString(ProfilePicture2, "");

        return text;
    }


}
