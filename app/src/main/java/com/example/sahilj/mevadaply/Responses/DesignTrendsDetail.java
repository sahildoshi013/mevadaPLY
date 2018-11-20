package com.example.sahilj.mevadaply.Responses;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sahil J on 3/5/2018.
 */

public class DesignTrendsDetail implements Serializable {

    @SerializedName("typeId")
    private Integer typeId;

    @SerializedName("typeName")
    private String typeName;

    public Integer getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }
}
