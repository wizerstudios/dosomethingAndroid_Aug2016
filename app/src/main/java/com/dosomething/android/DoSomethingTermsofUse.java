package com.dosomething.android;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

public class DoSomethingTermsofUse extends AppCompatActivity {
    TextView dosomething_termsofuse_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_something_termsof_use);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        dosomething_termsofuse_textview=(TextView) findViewById(R.id.dosomething_termsofuse_textview);
        Typeface patron_bold = Typeface.createFromAsset(getAssets(), "fonts/Patron-Bold.ttf");
        dosomething_termsofuse_textview.setTypeface(patron_bold);
    }
}
