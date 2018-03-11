package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class TransDetails implements Serializable {

    @SerializedName("points_tranc")
    private String trans_type;

    @SerializedName("points_amt")
    private String trans_amount;

    @SerializedName("points_comments")
    private String trans_comment;

    @SerializedName("points_tranc_time")
    private String trans_time;

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
        mydate.setTimeInMillis(Long.parseLong(trans_time));
        return mydate.get(Calendar.DAY_OF_MONTH)+"/"+mydate.get(Calendar.MONTH)+"/"+mydate.get(Calendar.YEAR);
    }


}
