package com.dosomething.android;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

public class DoSomethingPrivacyPolicy extends AppCompatActivity {
    TextView dosmething_privacypolicy_textview;
    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_something_privacy_policy);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        dosmething_privacypolicy_textview=(TextView)findViewById(R.id.dosmething_privacypolicy_textview);
        Typeface patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");
        dosmething_privacypolicy_textview.setTypeface(patron_bold);
    }
}
