package com.dosomething.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dosomething.android.CommonClasses.SharedPrefrences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Profile_image_three_fragment extends android.support.v4.app.Fragment {
    ImageView
            profile_image_three_imageview_camera_outside,
            profile_image_three_imageview_profile_image, profile_image_three_imageview_camera_inside;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Profile_image_viewpager_dots_three profile_image_viewpager_dots_three;
    SharedPrefrences sharedPrefrences;
    private Uri mImageCaptureUri;
    private ImageView mImageView;
    private Dialog dialog;
    private TextView dosomething_alert_pick_image_textview;
    private TextView dosomething_alert_pick_image_textview_gallery;
    private TextView dosomething_alert_pick_image_textview_camera;
    private static final int PICK_FROM_CAMERA = 4;
    private static final int CROP_FROM_CAMERA = 5;
    private static final int PICK_FROM_FILE = 6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_image_three_fragment, container, false);
        sharedPrefrences = new SharedPrefrences();
        profile_image_three_imageview_camera_outside = (ImageView) view.findViewById(R.id.profile_image_three_imageview_camera_outside);
        profile_image_three_imageview_profile_image = (ImageView) view.findViewById(R.id.profile_image_three_imageview_profile_image);
        profile_image_three_imageview_camera_inside = (ImageView) view.findViewById(R.id.profile_image_three_imageview_camera_inside);
//            profile_image_viewpager_dots_three = (Profile_image_viewpager_dots_three) getActivity();

        dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dosomething_alert_pick_image);
        dosomething_alert_pick_image_textview = (TextView) dialog.findViewById(R.id.dosomething_alert_pick_image_textview);
        dosomething_alert_pick_image_textview_gallery = (TextView) dialog.findViewById(R.id.dosomething_alert_pick_image_textview_gallery);
        dosomething_alert_pick_image_textview_camera = (TextView) dialog.findViewById(R.id.dosomething_alert_pick_image_textview_camera);


        try
        {
            if(!sharedPrefrences.getProfileImageBitmap3(getActivity()).equals(""))
            {

                byte[] b = Base64.decode(sharedPrefrences.getProfileImageBitmap3(getActivity()), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                Bitmap conv_bm = getCroppedBitmap(bitmap);
                profile_image_three_imageview_profile_image.setImageBitmap(conv_bm);
                profile_image_three_imageview_camera_inside.setVisibility(View.VISIBLE);
                profile_image_three_imageview_camera_outside.setVisibility(View.GONE);
                profile_image_viewpager_dots_three.profile_Image_three();

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        profile_image_three_imageview_camera_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                dosomething_alert_pick_image_textview_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       /* Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image*//*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                PICK_FROM_FILE);*/

                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_FROM_FILE);
                        dialog.dismiss();
                    }
                });


                dosomething_alert_pick_image_textview_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                "crop1.jpg"));
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                        dialog.dismiss();
                    }
                });

                dialog.show();











/*
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Image");
                builder.setMessage("Do you want to go with?");
                builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, 5);


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                getResources().getString(R.string.app_name) + "profie_image1" + ".jpg"));

                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                        try {
                            intent.putExtra("return-data", true);

                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }


                    }
                });
                builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                        Intent intent = new Intent();

                        intent.setType("image*//*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);

                    }
                });
                builder.show();*/
            }
        });

        profile_image_three_imageview_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                dosomething_alert_pick_image_textview_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       /* Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image*//*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                PICK_FROM_FILE);*/


                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_FROM_FILE);
                        dialog.dismiss();
                    }
                });


                dosomething_alert_pick_image_textview_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                "crop1.jpg"));
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                        dialog.dismiss();
                    }
                });

                dialog.show();











             /*   AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Image");
                builder.setMessage("Do you want to go with?");
                builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, 5);


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                getResources().getString(R.string.app_name) + "profie_image1" + ".jpg"));

                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                        try {
                            intent.putExtra("return-data", true);

                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                });
                builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
//
                        Intent intent = new Intent();

                        intent.setType("image*//*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);

                    }
                });
                builder.show();*/
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode != Activity.RESULT_OK) return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                cropCapturedImage();

                break;

            case PICK_FROM_FILE:
                if(data!=null)
                {
                    mImageCaptureUri = data.getData();
                    Log.d("dosomething_creste_acc","mImageCaptureUri"+mImageCaptureUri);

                    cropCapturedImage();
                }

                break;

            case CROP_FROM_CAMERA:
                try
                {
                    if(getActivity()!=null)
                    {
                        Bundle extras = data.getExtras();

                        if (extras != null) {
                            Bitmap photo = extras.getParcelable("data");
                            Bitmap resized = Bitmap.createScaledBitmap(photo, 1080, 1080, true);
                            Bitmap conv_bm = getCroppedBitmap(photo);
                            profile_image_three_imageview_profile_image.setImageBitmap(conv_bm);
                            profile_image_three_imageview_camera_inside.setVisibility(View.VISIBLE);
                            profile_image_three_imageview_camera_outside.setVisibility(View.GONE);
                            profile_image_viewpager_dots_three.profile_Image_three();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resized.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] b = baos.toByteArray();
                            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            sharedPrefrences.setProfileImageBitmap3(getActivity(), encodedImage);
                            File f =new File(Environment.getExternalStorageDirectory(),
                                    getResources().getString(R.string.app_name) + "profile_image2" + ".jpg");
                            f.createNewFile();
                            Log.d("filename", String.valueOf(f));

                            //write the bytes in file
                            FileOutputStream fos = new FileOutputStream(f);
                            fos.write(b);
                            fos.flush();
                            fos.close();
                            sharedPrefrences.setProfilePicture2(getActivity(), String.valueOf(f));



                        }

                        File f = new File(mImageCaptureUri.getPath());

                        if (f.exists()) f.delete();
                    }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }


                break;

        }


//        try {
//            ContentResolver sg = getActivity().getContentResolver();
//            if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK && null != data) {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = sg.query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgDecodableString = cursor.getString(columnIndex);
//                cursor.close();
//                Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
//                Log.d("getImageUri", getPath(getImageUri(getActivity(), bm), getActivity()));
//                sharedPrefrences.setProfilePicture2(getActivity(), getPath(getImageUri(getActivity(), bm), getActivity()));
//                Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
//                Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 10);
//                profile_image_three_imageview_profile_image.setImageBitmap(conv_bm);
//                profile_image_three_imageview_profile_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                profile_image_three_imageview_camera_inside.setVisibility(View.VISIBLE);
//                profile_image_three_imageview_camera_outside.setVisibility(View.GONE);
//                profile_image_viewpager_dots_three.profile_Image_three();
//
//
//            } else {
//                final Bitmap photo = (Bitmap) data.getExtras().get("data");
//                Log.d("getImageUri", getPath(getImageUri(getActivity(), photo), getActivity()));
//                sharedPrefrences.setProfilePicture2(getActivity(), getPath(getImageUri(getActivity(), photo), getActivity()));
//                Bitmap resized = Bitmap.createScaledBitmap(photo, 200, 200, true);
//                Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 10);
//                profile_image_three_imageview_profile_image.setImageBitmap(conv_bm);
//                profile_image_three_imageview_profile_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                profile_image_three_imageview_camera_inside.setVisibility(View.VISIBLE);
//                profile_image_three_imageview_camera_outside.setVisibility(View.GONE);
//                profile_image_viewpager_dots_three.profile_Image_three();
//            }
//
//
//        } catch (Exception e) {
//            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
//                    .show();
//        }

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




    public void cropCapturedImage() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(mImageCaptureUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 1000);
            cropIntent.putExtra("outputY", 1000);
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra("return-data", true);
//            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
//            cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(cropIntent, CROP_FROM_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);

        int size = list.size();

        if (size == 0) {
            Toast.makeText(getActivity(), "Can not find image crop app", Toast.LENGTH_SHORT).show();

            return;
        } else {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 1080);
            intent.putExtra("outputY", 1080);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = getActivity().getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon = getActivity().getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    if (!res.activityInfo.packageName.equals("com.google.android.apps.photos")) {
                        co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                        cropOptions.add(co);
                    }
                }

                CropOptionAdapter adapter = new CropOptionAdapter(getActivity().getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROP_FROM_CAMERA);
                    }
                });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getActivity().getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }


    private String getPath(Uri uri, Activity activity) {
        Cursor cursor = null;
        int column_index = 0;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Log.d("projection====", "" + projection);
            cursor = activity.managedQuery(uri, projection, null, null, null);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor.getString(column_index);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
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

    public interface Profile_image_viewpager_dots_three {
        public void profile_Image_three();
    }

    //
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        profile_image_viewpager_dots_three = (Profile_image_viewpager_dots_three) activity;

    }

    public class LoadImageFromURL1 extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub

            try {
                URL url = new URL(sharedPrefrences.getProfilePicture1(getActivity()));
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                Bitmap resized = Bitmap.createScaledBitmap(bitMap, 200, 200, false);
                Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 10);
                return conv_bm;

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
            profile_image_three_imageview_profile_image.setImageBitmap(result);
            profile_image_three_imageview_profile_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            profile_image_three_imageview_camera_inside.setVisibility(View.VISIBLE);
            profile_image_three_imageview_camera_outside.setVisibility(View.GONE);
            profile_image_viewpager_dots_three.profile_Image_three();
        }

    }

}
