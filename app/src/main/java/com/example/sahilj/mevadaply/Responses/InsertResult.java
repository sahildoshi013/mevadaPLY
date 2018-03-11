package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sahil J on 3/12/2018.
 */

public class InsertResult {

    @SerializedName("status")
    private boolean success;

    public boolean isSuccess() {
        return success;
    }
}
