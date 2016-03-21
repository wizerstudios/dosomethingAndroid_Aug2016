package com.dosomething.android.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.dosomething.android.CommonClasses.SharedPrefrences;
import com.dosomething.android.MyApplication;
import com.dosomething.android.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class Friend_Profile_two_fragment extends Fragment {
    ImageView friend_profile_imageview_two;
    ImageView friend_profile_imageview_two_imageview_camera_inside;
    ImageView friend_profile_imageview_two_imageview_camera_outside;
    Friend_profile_image_viewpager_dots_two friend_profile_image_viewpager_dots_two;
    SharedPrefrences sharedPrefrences;
    private AQuery aQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend__profile_two_fragment, container, false);
        sharedPrefrences = new SharedPrefrences();
        aQuery=new AQuery(getActivity());
        friend_profile_imageview_two = (ImageView) view.findViewById(R.id.friend_profile_imageview_two);
        friend_profile_imageview_two_imageview_camera_inside = (ImageView) view.findViewById(R.id.friend_profile_imageview_two_imageview_camera_inside);
        friend_profile_imageview_two_imageview_camera_outside = (ImageView) view.findViewById(R.id.friend_profile_imageview_two_imageview_camera_outside);
        if (!sharedPrefrences.getFriendProfilePicture1(getActivity()).equals("")) {
//            LoadImageFromURL loadImage = new LoadImageFromURL();
//            loadImage.execute();

            aQuery.id(friend_profile_imageview_two).image(sharedPrefrences.getFriendProfilePicture1(getActivity()), true, true, 0, 0, new BitmapAjaxCallback() {
                @Override
                public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                    if (status.getCode() == 200) {
                        Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
                        Bitmap conv_bm = getCroppedBitmap(bm);
                        iv.setImageBitmap(conv_bm);


                    }else
                    {
                        iv.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.profile_noimg));
                    }
                }
            });
        }
        OnClickListener();
        return view;
    }


    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
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
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }


    public void OnClickListener() {
        friend_profile_imageview_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getActivity() != null) {
                    if (((MyApplication) getActivity().getApplication()).getmDoSomething_Friends_profile_fragment() != null) {
                        if (!sharedPrefrences.getFriendProfilePicture1(getActivity()).equals("")) {
                            ((MyApplication) getActivity().getApplication()).getmDoSomething_Friends_profile_fragment().showImage(sharedPrefrences.getFriendProfilePicture1(getActivity()));

                        } else {
                            ((MyApplication) getActivity().getApplication()).getmDoSomething_Friends_profile_fragment().showImage("http://indiawebcoders.com/mobileapps/dosomething/uploads/profile/noimage.png");

                        }
                    }
                }

            }
        });
    }

    public interface Friend_profile_image_viewpager_dots_two {
        public void friend_profile_Image_two();
    }

    public static Bitmap getRoundedRectanguleBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(100, 100, 92, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

        } catch (NullPointerException | OutOfMemoryError ignored) {
        }
        return result;
    }

    public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub

            try {
                if(getActivity()!=null) {


                    URL url = new URL(sharedPrefrences.getFriendProfilePicture1(getActivity()));
                    InputStream is = url.openConnection().getInputStream();
                    Bitmap bitMap = BitmapFactory.decodeStream(is);
                    Bitmap resized = Bitmap.createScaledBitmap(bitMap, 200, 200, true);
                    Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 10);
                    return conv_bm;
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            friend_profile_imageview_two.setImageBitmap(result);
            friend_profile_imageview_two_imageview_camera_inside.setVisibility(View.GONE);
            friend_profile_imageview_two_imageview_camera_outside.setVisibility(View.GONE);
//            friend_profile_image_viewpager_dots_two.friend_profile_Image_two();
        }

    }
}