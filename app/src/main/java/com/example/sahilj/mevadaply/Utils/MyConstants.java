package com.example.sahilj.mevadaply.Utils;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.example.sahilj.mevadaply.APIs.ApiInterface;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class MyConstants {

    public static final String DB_USER = "user";
    public static final String TYPE_REDEEM = "redeem";
    public static final String TYPE = "type";
    public static final String TYPE_PROFILE = "profile";
    public static final String TYPE_HISTORY = "history";
    public static final String NULL_URL = "http://www.swoopzi.com/mevada/null";
    public static final String TYPE_DESIGN = "design";
    public static final String DATA = "data";
    public static final String CURRENT_SELECTED_ID = "Current Selected Image ID";

    public static final int NUM_OF_COLUMNS = 3;

    // Gridview image padding
    public static final int GRID_PADDING = 8; // in dp

    // SD card image directory
    public static final String PHOTO_ALBUM = "androidhive";

    // supported file formats
    public static final List<String> FILE_EXTN = Arrays.asList("jpg", "jpeg", "png");

    public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
}
