package com.example.sahilj.mevadaply.Responses;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahil J on 3/5/2018.
 */

public class DesignDetails {

    @SerializedName("photo_id")
    private int photo_id;

    @SerializedName("photo_url")
    private String photo_url;

    public int getPhoto_id() {
        return photo_id;
    }

    public String getPhoto_url() {
        return ApiClient.getBaseUrl()+photo_url;
    }
}
