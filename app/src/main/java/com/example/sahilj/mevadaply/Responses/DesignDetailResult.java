package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sahil J on 3/5/2018.
 */

public class DesignDetailResult {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private List<DesignDetails> data;

    public boolean isStatus() {
        return status;
    }

    public List<DesignDetails> getData() {
        return data;
    }
}
