package com.example.sahilj.mevadaply.Utils;

import android.app.AlertDialog;
import android.content.Context;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.example.sahilj.mevadaply.APIs.ApiInterface;
import com.example.sahilj.mevadaply.Responses.UserDetails;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class MyConstants {

    public static final String TYPE_REDEEM = "redeem";
    public static final String TYPE = "type";
    public static final String TYPE_PROFILE = "profile";
    public static final String TYPE_HISTORY = "history";
    public static final String NULL_URL = "http://www.swoopzi.com/mevada/null";
    public static final String TYPE_DESIGN = "design";
    public static final String DATA = "data";
    public static final String CURRENT_SELECTED_ID = "Current Selected Image ID";

    // supported file formats
    public static final String TIME = "time";
    public static final String POINTS = "points";
    public static final String DEFAULT_POINTS = "0";
    public static final String SHAREDPRENAME = "MevadaPLY";
    public static final String OLD_NUMBER = "Old_Number";
    public static final String NEW_NUMBER = "New Number";
    public static UserDetails USER_DETAILS = null;

    public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    public static AlertDialog alertBox(Context context){
        return new AlertDialog.Builder(context)
                .setTitle("Error !")
                .setMessage("No Internet").create();
    }
}
