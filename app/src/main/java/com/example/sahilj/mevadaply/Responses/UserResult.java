package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class UserResult {

    @SerializedName("status")
    boolean status;

    @SerializedName("message")
    UserDetails details;

    public boolean isStatus() {
        return status;
    }

    public UserDetails getDetails() {
        return details;
    }
}
