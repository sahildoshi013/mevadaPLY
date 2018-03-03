package com.example.sahilj.mevadaply.Responses;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahil J on 3/3/2018.
 */

public class OfferDetail {

    @SerializedName("redeem_offer_id")
    private int redeem_offer_id;

    @SerializedName("redeem_offer_name")
    private String redeem_offer_name;

    @SerializedName("redeem_offer_points")
    private int redeem_offer_points;

    @SerializedName("redeem_offer_description")
    private String redeem_offer_description;

    @SerializedName("url")
    private String url;

    public int getRedeem_offer_id() {
        return redeem_offer_id;
    }

    public String getRedeem_offer_name() {
        return redeem_offer_name;
    }

    public int getRedeem_offer_points() {
        return redeem_offer_points;
    }

    public String getRedeem_offer_description() {
        return redeem_offer_description;
    }

    public String getUrl() {
        return ApiClient.getBaseUrl()+url;
    }
}
