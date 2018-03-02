package com.example.sahilj.mevadaply.Utils;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.example.sahilj.mevadaply.APIs.ApiInterface;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class MyConstants {

    public static final String DB_USER = "user";
    public static ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
}
