package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sahil J on 3/3/2018.
 */

public class RedeemOfferResult {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private List<OfferDetail> data;

    public boolean isStatus() {
        return status;
    }

    public List<OfferDetail> getData() {
        return data;
    }

}
