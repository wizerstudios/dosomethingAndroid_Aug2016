package com.dosomething.android.CommonClasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import com.dosomething.android.R;


public class NetworkCheck {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return mWifi !=null && mWifi.isConnected() ;
    }
    public static void alertdialog(final Context context)
    {

      /*  final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.network_not_enabled))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,  final int id) {
                        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();*/


        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dosomething_alert_save_changes);
        TextView status_textview_save = (TextView) dialog.findViewById(R.id.status_textview_save);
        TextView alert_textview_save = (TextView) dialog.findViewById(R.id.alert_textview_save);
        TextView alert_textview_save_cancel = (TextView) dialog.findViewById(R.id.alert_textview_save_cancel);
        status_textview_save.setText(context.getResources().getString(R.string.network_not_enabled));
        alert_textview_save.setText("Yes");
        alert_textview_save_cancel.setText("No");
        alert_textview_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                dialog.dismiss();
            }
        });
        dialog.show();
        alert_textview_save_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
dialog.show();

    }

    public static void statusCheck(final Context context)
    {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps(context);

        }


    }
    private static void buildAlertMessageNoGps(final Context context) {


        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dosomething_alert_save_changes);
        TextView status_textview_save = (TextView) dialog.findViewById(R.id.status_textview_save);
        TextView alert_textview_save = (TextView) dialog.findViewById(R.id.alert_textview_save);
        TextView alert_textview_save_cancel = (TextView) dialog.findViewById(R.id.alert_textview_save_cancel);
        status_textview_save.setText("Your GPS seems to be disabled, do you want to enable it?");
        alert_textview_save.setText("Yes");
        alert_textview_save_cancel.setText("No");
        alert_textview_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                dialog.dismiss();
            }
        });
        dialog.show();
        alert_textview_save_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        dialog.show();







       /* final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,  final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();*/

    }
    public static void alertdialogGps(final Context context)
    {

    }
    public static void alertdialogEmail(final Context context) {


        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_dialog);
        TextView status_textview_availablenow = (TextView) dialog.findViewById(R.id.status_textview_availablenow);
        TextView status_textview_accept_check = (TextView) dialog.findViewById(R.id.status_textview_accept_check);
        status_textview_availablenow.setText("please enter valid email address to create account");
        status_textview_accept_check.setText("Dismiss");
        status_textview_accept_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();




       /* final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage("Please Enter Valid Email Address");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.show();*/
    }
    public static void alertdialogPwd(final Context context) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage("Please Enter Valid password");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
