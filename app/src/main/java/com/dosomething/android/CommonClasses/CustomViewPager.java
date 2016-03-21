package com.dosomething.android.CommonClasses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

import java.lang.reflect.Field;

/**
 * Created by User on 7/15/2015.
 */
public class CustomViewPager extends ViewPager {
    Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;

    }

    public CustomViewPager(Context context) {
        super(context);

        postInitViewPager();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
    }

    private ViewPagerScrollerDuration mScroller = null;

    /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private void postInitViewPager() {
        try {
            Field scroller = ViewPager.class.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = ViewPager.class
                    .getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);
            mScroller = new ViewPagerScrollerDuration(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);

        } catch (Exception e) {
        }
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        mScroller.setScrollDurationFactor(scrollFactor);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onHoverEvent(MotionEvent event) {

        return super.onHoverEvent(event);
    }

    public boolean onInterceptTouchEvent(MotionEvent e) {
        super.onInterceptTouchEvent(e);

        return false;
    }

}