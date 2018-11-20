package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class TransResult {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private List<TransDetails> transDetailsList;

    public boolean isStatus() {
        return status;
    }

    public List<TransDetails> getTransDetailsList() {
        return transDetailsList;
    }
}
