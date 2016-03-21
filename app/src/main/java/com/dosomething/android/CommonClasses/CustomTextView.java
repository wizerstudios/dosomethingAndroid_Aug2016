package com.dosomething.android.CommonClasses;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextView extends TextView {
    Typeface patron_bold;
    private Context c;

    public CustomTextView(Context c) {
        super(c);
        this.c = c;
        patron_bold = Typeface.createFromAsset(c.getAssets(), "fonts/Patron-Bold.ttf");
        setTypeface(patron_bold);

    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.c = context;
        Typeface tfs = Typeface.createFromAsset(c.getAssets(),
                "fonts/Patron-Black.ttf");

        setTypeface(tfs);
        // TODO Auto-generated constructor stub
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context;
        Typeface tfs = Typeface.createFromAsset(c.getAssets(),
                "fonts/Patron-Black.ttf");

        setTypeface(tfs);

    }


}
