package com.example.sahilj.mevadaply.Utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;

import com.example.sahilj.mevadaply.Responses.TransDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class MyUtilities {

    public static final String BASE_URL = "http://192.168.137.1:8080/";
    //public static final String BASE_URL = "http://192.168.43.107:8080/";
    public static final String MOBILE_BASE_URL = BASE_URL + "mobile/";
    public static final String IMAGE_BASE_URL = BASE_URL + "images/";
    public static final String USER_IMAGE_BASE_URL = IMAGE_BASE_URL + "users/";
    public static final String OFFER_BASE_URL = IMAGE_BASE_URL + "offers/";
    public static final String DESIGN_BASE_PATH = IMAGE_BASE_URL + "designs/";
    private static final String TAG = "MyUtilities";
    private static String phoneNumber;

    private static Integer sum=0;

    public static int getPointCount(List<TransDetails> transDetails){

        if(transDetails==null)
            return 0;
        sum=0;

        for (TransDetails trans :
                transDetails) {
            if(trans.getTrans_type().equals("Credited"))
                sum+=Integer.valueOf(trans.getTrans_amount());

            if(trans.getTrans_type().equals("Redeem"))
                sum-=Integer.valueOf(trans.getTrans_amount());

        }

        return sum;
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static String getPhoneNumber() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getPhoneNumber();
        }
        return "0";
    }

    public static void updateMobileNumber(String oldnumber, String phoneNumber, Context applicationContext) {
        Log.v(TAG,oldnumber + " " + phoneNumber);
        ProgressDialog progressDialog = new ProgressDialog(applicationContext);
        progressDialog.show();

    }

    public static String getPath(Uri uri,Context context)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =  cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public static void setSum() {
        sum= 0;
    }

    public static Integer getSum() {
        int point = MyConstants.USER_DETAILS.getPointEarned() - MyConstants.USER_DETAILS.getPointRedeem();
        return point;
    }
}
