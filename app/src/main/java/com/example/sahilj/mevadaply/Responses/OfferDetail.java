package com.example.sahilj.mevadaply.Responses;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.example.sahilj.mevadaply.Utils.MyUtilities;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Sahil J on 3/3/2018.
 */

public class OfferDetail {

    @SerializedName("offerId")
    private Integer offerId;

    @SerializedName("offerName")
    private String offerName;

    @SerializedName("offerDescription")
    private String offerDescription;

    @SerializedName("offerPoint")
    private int offerPoint;

    @SerializedName("offerImage")
    private String offerImage;

    @SerializedName("offerCreationTime")
    private Date offerCreationTime;

    @SerializedName("offerActive")
    private boolean offerActive;

    public Integer getOfferId() {
        return offerId;
    }

    public String getOfferName() {
        return offerName;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public int getOfferPoint() {
        return offerPoint;
    }

    public String getOfferImage() {
        return MyUtilities.OFFER_BASE_URL  + offerImage;
    }

    public Date getOfferCreationTime() {
        return offerCreationTime;
    }

    public boolean isOfferActive() {
        return offerActive;
    }
}
