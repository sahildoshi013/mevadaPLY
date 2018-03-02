package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahil J on 3/1/2018.
 */

public class UpdateResult {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
