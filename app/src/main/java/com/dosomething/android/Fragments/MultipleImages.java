package com.dosomething.android.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dosomething.android.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MultipleImages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Integer> anInt;
    Activity imageSlideView;

    private ImageView fragment_multiple_imageview;
    TextView fragment_multiple_view;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private Uri mImageCaptureUri;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultipleImages.
     */
    // TODO: Rename and change types and number of parameters
    public static MultipleImages newInstance(ArrayList<Integer> param1, Activity param2) {
        MultipleImages fragment = new MultipleImages();
        fragment.anInt = param1;
        fragment.imageSlideView = param2;
        return fragment;
    }

    public MultipleImages() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multiple_images, container, false);
        fragment_multiple_imageview = (ImageView) view.findViewById(R.id.fragment_multiple_imageview);
        fragment_multiple_view = (TextView) view.findViewById(R.id.fragment_multiple_view);

        fragment_multiple_view.setText("Fragemnt Postion" + anInt);
        fragment_multiple_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Image");
                builder.setMessage("Do you want to go with?");
                builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                        cameraIntent.putExtra("crop", "true");
////                        cameraIntent.putExtra("outputX", 900);
////                        cameraIntent.putExtra("outputY", 900);
////                        cameraIntent.putExtra("aspectX", 4);
////                        cameraIntent.putExtra("aspectY", 16);
////                        cameraIntent.putExtra("scale", true);
//                        startActivityForResult(cameraIntent, 5);

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                                "crop.jpg"));
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        getParentFragment().startActivityForResult(intent, PICK_FROM_CAMERA);
                        dialog.dismiss();




                    }
                });


                builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//
//                        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        getParentFragment().startActivityForResult(i, PICK_FROM_FILE);

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
                builder.show();
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
                            fragment_multiple_imageview.setImageBitmap(photo);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resized.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] b = baos.toByteArray();
                            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);


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


                        }


                    }

                    File f1 = new File(mImageCaptureUri.getPath());

                    if (f1.exists()) f1.delete();

                    break;

                } catch (Exception e) {
                    e.printStackTrace();
                }

        }}


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




}
