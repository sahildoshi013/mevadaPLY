package com.example.sahilj.mevadaply.Responses;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.example.sahilj.mevadaply.Utils.MyUtilities;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sahil J on 3/5/2018.
 */

public class DesignDetails implements Serializable {

    @SerializedName("designId")
    private Integer designId;

    @SerializedName("tblMpFurnitureType")
    private DesignTrendsDetail tblMpFurnitureType;

    @SerializedName("designImage")
    private String designImage;

    @SerializedName("designVisiblity")
    private boolean designVisiblity;

    @SerializedName("designTime")
    private Date designTime;

    public Integer getDesignId() {
        return designId;
    }

    public DesignTrendsDetail getTblMpFurnitureType() {
        return tblMpFurnitureType;
    }

    public String getDesignImage() {
        return MyUtilities.DESIGN_BASE_PATH + designImage;
    }

    public boolean isDesignVisiblity() {
        return designVisiblity;
    }

    public Date getDesignTime() {
        return designTime;
    }
}
