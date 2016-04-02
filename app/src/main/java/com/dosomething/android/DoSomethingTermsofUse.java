package com.dosomething.android;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;

public class DoSomethingTermsofUse extends AppCompatActivity {
    TextView dosomething_termsofuse_textview;
    private Tracker mTracker;
    private WebView dosomething_termsofuse_doc;
    private Toolbar toolbar;
    private TextView custom_toolbar_textview_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_something_termsof_use);
        try
        {
            MyApplication application = (MyApplication) getApplication();
            mTracker = application.getDefaultTracker();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        dosomething_termsofuse_doc=(WebView)findViewById(R.id.dosomething_termsofuse_doc);
        dosomething_termsofuse_textview=(TextView) findViewById(R.id.dosomething_termsofuse_textview);
        Typeface patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");
        dosomething_termsofuse_textview.setTypeface(patron_bold);
        dosomething_termsofuse_doc = (WebView) findViewById(R.id.dosomething_termsofuse_doc);
        dosomething_termsofuse_doc.loadUrl("file:///android_asset/termsofservice.html");

//        dosomething_termsofuse_doc.loadUrl("file:///android_res/raw/myfile.html");

    }
}
