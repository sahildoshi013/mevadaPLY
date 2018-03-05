package com.example.sahilj.mevadaply.Responses;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahil J on 3/5/2018.
 */

public class DesignDetail {

    @SerializedName("design_id")
    private int design_id;

    @SerializedName("design_name")
    private String design_name;

    @SerializedName("url")
    private String url;

    public int getDesign_id() {
        return design_id;
    }

    public String getDesign_name() {
        return design_name;
    }

    public String getUrl() {
        return ApiClient.getBaseUrl()+url;
    }
}
