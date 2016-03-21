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
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.dosomething.android.CommonClasses.SharedPrefrences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Profile_image_one_fragment extends android.support.v4.app.Fragment {
    ImageView
            profile_image_one_imageview_camera_outside,
            profile_image_one_page_imageview_profile_image, profile_image_one_imageview_camera_inside;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    Profile_image_viewpager_dots_one profile_image_viewpager_dots_one;
    SharedPrefrences sharedPrefrences;
    private Uri mImageCaptureUri;
    private ImageView mImageView;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private String filePathImage1;
    private AQuery aQuery;
    private Dialog dialog;
    private TextView dosomething_alert_pick_image_textview;
    private TextView dosomething_alert_pick_image_textview_gallery;
    private TextView dosomething_alert_pick_image_textview_camera;



    public static final int SELECT_FILE = 4;
    public static final int REQUEST_CAMERA = 3;
    public static final int PICK_IMAGE = 1;
    private Uri mImageCroppedUri;
    private static final int CROP_IMAGE_REQUEST = 200;
    File dest, sd, mediaFile;
    String resStr = "", userprofilepic, cropName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_image_one_fragment, container, false);
        profile_image_one_imageview_camera_outside = (ImageView) view.findViewById(R.id.profile_image_one_imageview_camera_outside);
        profile_image_one_page_imageview_profile_image = (ImageView) view.findViewById(R.id.profile_image_one_page_imageview_profile_image);
        profile_image_one_imageview_camera_inside = (ImageView) view.findViewById(R.id.profile_image_one_imageview_camera_inside);
        sharedPrefrences = new SharedPrefrences();
        aQuery = new AQuery(getActivity());
        dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dosomething_alert_pick_image);
        dosomething_alert_pick_image_textview = (TextView) dialog.findViewById(R.id.dosomething_alert_pick_image_textview);
        dosomething_alert_pick_image_textview_gallery = (TextView) dialog.findViewById(R.id.dosomething_alert_pick_image_textview_gallery);
        dosomething_alert_pick_image_textview_camera = (TextView) dialog.findViewById(R.id.dosomething_alert_pick_image_textview_camera);


        try {
            if (!sharedPrefrences.getProfileImageBitmap1(getActivity()).equals("")) {

                byte[] b = Base64.decode(sharedPrefrences.getProfileImageBitmap1(getActivity()), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                Bitmap conv_bm = getCroppedBitmap(bitmap);
                profile_image_one_page_imageview_profile_image.setImageBitmap(conv_bm);
                profile_image_one_imageview_camera_inside.setVisibility(View.VISIBLE);
                profile_image_one_imageview_camera_outside.setVisibility(View.GONE);
                profile_image_viewpager_dots_one.profile_Image_one();
            } else {

                if (!sharedPrefrences.getFBProfilePicture(getActivity()).equals("")) {
//                    LoadImageFromURL loadImage = new LoadImageFromURL();
//                    loadImage.execute();


                    aQuery.id(profile_image_one_page_imageview_profile_image).image(sharedPrefrences.getFBProfilePicture(getActivity()), true, true, 0, 0, new BitmapAjaxCallback() {
                        @Override
                        public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                            if (status.getCode() == 200) {
                                Bitmap resized = Bitmap.createScaledBitmap(bm, 200, 200, true);
                                Bitmap conv_bm = getRoundedRectanguleBitmap(resized, 40);
                                iv.setImageBitmap(conv_bm);
                                profile_image_one_imageview_camera_inside.setVisibility(View.VISIBLE);
                                profile_image_one_imageview_camera_outside.setVisibility(View.GONE);
                                profile_image_viewpager_dots_one.profile_Image_one();

                            }
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        profile_image_one_imageview_camera_outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                dosomething_alert_pick_image_textview_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       /* Intent intent = new Intent();

                        intent.setType("image*//*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);

                        dialog.dismiss();*/

                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_FROM_FILE);


                        /*Intent intent = new Intent(
                                Intent.ACTION_GET_CONTENT,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image*//*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Choose an image"),
                                PICK_FROM_FILE);*/
                        dialog.dismiss();
                    }
                });


                dosomething_alert_pick_image_textview_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                getResources().getString(R.string.app_name) + "profie_image" + ".jpg"));

                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                        try {
                            intent.putExtra("return-data", true);

                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }


                        dialog.dismiss();*/




                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                "crop.jpg"));
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                        dialog.dismiss();
                    }
                });

                dialog.show();



            }
        });

        profile_image_one_page_imageview_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                dosomething_alert_pick_image_textview_gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                      /*  Intent intent = new Intent();

                        intent.setType("image*//*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);

                        dialog.dismiss();*/


                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, PICK_FROM_FILE);

                        /*Intent intent = new Intent(
                                Intent.ACTION_GET_CONTENT,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image*//*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Choose an image"),
                                PICK_FROM_FILE);*/
                        dialog.dismiss();
                    }
                });


                dosomething_alert_pick_image_textview_camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                getResources().getString(R.string.app_name) + "profie_image" + ".jpg"));

                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);

                        try {
                            intent.putExtra("return-data", true);

                            startActivityForResult(intent, PICK_FROM_CAMERA);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }


                        dialog.dismiss();*/


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                            mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                    "crop.jpg"));
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                        dialog.dismiss();


                    }
                });

                dialog.show();









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
                if (data != null) {
                    mImageCaptureUri = data.getData();
                    Log.d("dosomething_creste_acc", "mImageCaptureUri" + mImageCaptureUri);

                    cropCapturedImage();
                }

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                try {


                    if (getActivity() != null) {

                        if (extras != null) {
                            Bitmap photo = extras.getParcelable("data");
                            Bitmap resized = Bitmap.createScaledBitmap(photo, 1080, 1080, true);
                            Bitmap conv_bm1 = getCroppedBitmap(photo);
                            profile_image_one_page_imageview_profile_image.setImageBitmap(conv_bm1);
                            profile_image_one_imageview_camera_inside.setVisibility(View.VISIBLE);
                            profile_image_one_imageview_camera_outside.setVisibility(View.GONE);
                            profile_image_viewpager_dots_one.profile_Image_one();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resized.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] b = baos.toByteArray();
                            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                            sharedPrefrences.setProfileImageBitmap1(getActivity(), encodedImage);

                            //create a file to write bitmap data
                            File f = new File(Environment.getExternalStorageDirectory(),
                                    getResources().getString(R.string.app_name) + "profile_image" + ".jpg");
                            f.createNewFile();
                            Log.d("filename", String.valueOf(f));

                            //write the bytes in file
                            FileOutputStream fos = new FileOutputStream(f);
                            fos.write(b);
                            fos.flush();
                            fos.close();
                            sharedPrefrences.setProfilePicture(getActivity(), String.valueOf(f));




                            sharedPrefrences.setFBProfilePicture(getActivity(), "");


                        }


                    }

                    File f1 = new File(mImageCaptureUri.getPath());

                    if (f1.exists()) f1.delete();

                    break;

                } catch (Exception e) {
                    e.printStackTrace();
                }


        }


//        try {
//            ContentResolver sg = getActivity().getContentResolver();
//            if (requestCode == RESULT_LOAD_IMG && resultCode == Activity.RESULT_OK && null != data) {
//                Uri selectedImage = data.getData();
//                Log.d("selectedImage", String.valueOf(selectedImage));
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//                Cursor cursor = sg.query(selectedImage, filePathColumn, null, null, null);
//                assert cursor != null;
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                imgDecodableString = cursor.getString(columnIndex);
//                cursor.close();
//                Bitmap bm = BitmapFactory.decodeFile(imgDecodableString);
//                Log.d("getImageUri", getPath(getImageUri(getActivity(), bm), getActivity()));
//                sharedPrefrences.setProfilePicture(getActivity(), getPath(getImageUri(getActivity(), bm), getActivity()));
//                int w = bm.getWidth();
//                int h = bm.getHeight();
//                Bitmap resized = Bitmap.createScaledBitmap(bm, w, h, true);
////                Bitmap conv_bm = getRoundedRectanguleBitmap(resized, w);
//                Bitmap conv_bm = getCroppedBitmap(bm);
//                sharedPrefrences.setFBProfilePicture(getActivity(), "");
//                profile_image_one_page_imageview_profile_image.setImageBitmap(conv_bm);
//                profile_image_one_imageview_camera_inside.setVisibility(View.VISIBLE);
//                profile_image_one_imageview_camera_outside.setVisibility(View.GONE);
//                profile_image_viewpager_dots_one.profile_Image_one();
//
//
//            } else {
//
//                final Bitmap photo = (Bitmap) data.getExtras().get("data");
//                getPath(getImageUri(getActivity(), photo), getActivity());
//                Log.d("getImageUri", String.valueOf(photo));
//                sharedPrefrences.setProfilePicture(getActivity(), getPath(getImageUri(getActivity(), photo), getActivity()));
//                File file=new File(sharedPrefrences.getProfilePicture(getActivity()));
//                String absolutePath = file.getAbsolutePath();
//                Log.d("getImageUri", absolutePath);
//
//                int w = photo.getWidth();
//                int h = photo.getHeight();
//                Bitmap resized = Bitmap.createScaledBitmap(photo, w, h, true);
//                Bitmap conv_bm = getCroppedBitmap(resized);
//                profile_image_one_page_imageview_profile_image.setImageBitmap(conv_bm);
//                sharedPrefrences.setFBProfilePicture(getActivity(),"");
//                profile_image_one_imageview_camera_inside.setVisibility(View.VISIBLE);
//                profile_image_one_imageview_camera_outside.setVisibility(View.GONE);
//                profile_image_viewpager_dots_one.profile_Image_one();
//            }
//
//
//        } catch (Exception e) {
////            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG)
////                    .show();
//        }

    }

//    private void doDownloadTaskAndGetSaveImage(String lastPathSegment) {
//
//
//
//        AQuery aq = new AQuery(profile_image_one_page_imageview_profile_image);
//        aq.hardwareAccelerated11();
//        aq.id(profile_image_one_page_imageview_profile_image);
//        aq.image(lastPathSegment, false, false, 0, 0,
//                new BitmapAjaxCallback() {
//
//                    @Override
//                    public void callback(String url, ImageView iv, Bitmap bm,
//                                         AjaxStatus status) {
//
//                        System.out.println("url = " + url);
//
//                        File file = new File(Environment
//                                .getExternalStorageDirectory(), "image.jpg");
//
//                        // delete exits image first.
//                        System.out.println("file.exists() = " + file.exists());
//                        if (file.exists())
//                            file.delete();
//
//                        // save this image into sdcard for temp.
//                        try {
//                            FileOutputStream fos = new FileOutputStream(file);
//                            boolean isSaved = bm.compress(Bitmap.CompressFormat.JPEG,
//                                    75, fos);
//                            if (isSaved) {
//                                filePathImage1 = file.getPath();
//                                System.out.println("get path = "
//                                        + filePathImage1);
//                            }
//                            fos.flush();
//                            fos.close();
//
//                            displayInImageViewAndRotateIfNeed(filePathImage1,iv);
//
//                            // hide the progressBar.
////                            imgeLoadingBar.setVisibility(View.GONE);
//
//                        } catch (FileNotFoundException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            // TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                    }
//
//                });
//
//
//    }
//
//    private void displayInImageViewAndRotateIfNeed(String filePathImage1, ImageView iv) {
//
//    }


    private File savebitmap(String filename) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;

        File file = new File(filename + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, filename + ".png");
            Log.e("file exist", "" + file + ",Bitmap= " + filename);
        }
        try {
            // make a new bitmap from your file
            Bitmap bitmap = BitmapFactory.decodeFile(file.getName());

            outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("file", "" + file);
        return file;

    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Uri getImageUrI(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        String baseDir = path;
        if (baseDir == null) {
            baseDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return Uri.parse(baseDir);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    private String getPath(Uri uri, Activity activity) {
        Cursor cursor = null;
        int column_index = 0;
        try {
            String[] projection = {MediaStore.Images.Media.DATA};
            Log.d("projection====", "" + Arrays.toString(projection));
            cursor = activity.managedQuery(uri, projection, null, null, null);
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert cursor != null;
        return cursor.getString(column_index);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
    }


    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, (float) pixels, (float) pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


    public static Bitmap getRoundedRectanguleBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            int color = 0xff424242;
            Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, result.getWidth(),
                    result.getHeight());
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

    public interface Profile_image_viewpager_dots_one {
        public void profile_Image_one();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        profile_image_viewpager_dots_one = (Profile_image_viewpager_dots_one) activity;

    }

    public class LoadImageFromURL extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub

            try {
                URL url = new URL(sharedPrefrences.getFBProfilePicture(getActivity()));
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                Bitmap resized = Bitmap.createScaledBitmap(bitMap, 200, 200, true);
                return getRoundedRectanguleBitmap(resized, 10);

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
            profile_image_one_page_imageview_profile_image.setImageBitmap(result);
            profile_image_one_imageview_camera_inside.setVisibility(View.VISIBLE);
            profile_image_one_imageview_camera_outside.setVisibility(View.GONE);
            profile_image_viewpager_dots_one.profile_Image_one();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class LoadImageFromURL1 extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub

            try {
                URL url = new URL(sharedPrefrences.getProfilePicture(getActivity()));
                InputStream is = url.openConnection().getInputStream();
                Bitmap bitMap = BitmapFactory.decodeStream(is);
                Bitmap resized = Bitmap.createScaledBitmap(bitMap, 200, 200, true);
                return getRoundedRectanguleBitmap(resized, 10);

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
            profile_image_one_page_imageview_profile_image.setImageBitmap(result);
            profile_image_one_page_imageview_profile_image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            profile_image_one_imageview_camera_inside.setVisibility(View.VISIBLE);
            profile_image_one_imageview_camera_outside.setVisibility(View.GONE);
            profile_image_viewpager_dots_one.profile_Image_one();
        }

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



    public void cropCapturedImage(Uri picUri) {
        try {
            mediaFile = getOutputMediaFile(getActivity());
            cropName = mediaFile.getAbsolutePath();
            mImageCroppedUri = Uri.fromFile(mediaFile);
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 1000);
            cropIntent.putExtra("outputY", 1000);
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCroppedUri);
            cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(cropIntent, CROP_IMAGE_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static File getOutputMediaFile(FragmentActivity inscriptionActivity) {
        File mediaStorageDir = new File(android.os.Environment.getExternalStorageDirectory(), inscriptionActivity.getString(R.string.app_name));
        Log.d("mediaStorageDir @@@@", "" + mediaStorageDir);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String fileName = "betpub_imgs";
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
        return mediaFile;
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


//    public class CropOption {
//        public CharSequence title;
//        public Drawable icon;
//        public Intent appIntent;
//    }
//
//    public class CropOptionAdapter extends ArrayAdapter<CropOption> {
//        private ArrayList<CropOption> mOptions;
//        private LayoutInflater mInflater;
//
//        public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
//            super(context, R.layout.crop_selector, options);
//
//            mOptions = options;
//
//            mInflater = LayoutInflater.from(context);
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup group) {
//            if (convertView == null)
//                convertView = mInflater.inflate(R.layout.crop_selector, null);
//
//            CropOption item = mOptions.get(position);
//
//            if (item != null) {
//                ((ImageView) convertView.findViewById(R.id.iv_icon)).setImageDrawable(item.icon);
//                ((TextView) convertView.findViewById(R.id.tv_name)).setText(item.title);
//
//                return convertView;
//            }
//
//            return null;
//        }
//    }
}
