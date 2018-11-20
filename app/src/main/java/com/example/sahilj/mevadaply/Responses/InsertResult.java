package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahil J on 3/12/2018.
 */

public class InsertResult {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private UserDetails data;

    public boolean isStatus() {
        return status;
    }

    public UserDetails getData() {
        return data;
    }
}
