package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class TransDetails {

    @SerializedName("points_tranc")
    String trans_type;

    @SerializedName("points_amt")
    String trans_amount;

    @SerializedName("points_comments")
    String trans_comment;

    @SerializedName("points_tranc_time")
    String trans_time;

    public String getTrans_type() {
        if(trans_type.equals("1"))
            return "Credited";
        return "Redeem";
    }

    public String getTrans_amount() {
        return trans_amount;
    }

    public String getTrans_comment() {
        /*if(trans_comment.length()<19)
            return trans_comment;
        else
            return trans_comment.substring(0,16) + "...";*/
        return trans_comment;
    }

    public String getTrans_time() {
        Calendar mydate = Calendar.getInstance();
        mydate.setTimeInMillis(Long.parseLong(trans_time)*1000);
        return mydate.get(Calendar.DAY_OF_MONTH)+"/"+mydate.get(Calendar.MONTH)+"/"+mydate.get(Calendar.YEAR);
    }


}
