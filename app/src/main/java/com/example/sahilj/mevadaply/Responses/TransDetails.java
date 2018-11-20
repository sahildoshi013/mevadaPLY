package com.example.sahilj.mevadaply.Responses;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class TransDetails implements Serializable {

    @SerializedName("tranId")
    private Integer tranId;

    @SerializedName("tblMpUser")
    private UserDetails tblMpUser;

    @SerializedName("tranDescription")
    private String tranDescription;

    @SerializedName("tranPoint")
    private int tranPoint;

    @SerializedName("tranType")
    private int tranType;

    @SerializedName("tranTime")
    private Date tranTime;

    public String getTrans_type() {
        if(tranType == 1)
            return "Credited";
        return "Redeem";
    }

    public Integer getTrans_amount() {
        return tranPoint;
    }

    public String getTrans_comment() {
        /*if(trans_comment.length()<19)
            return trans_comment;
        else
            return trans_comment.substring(0,16) + "...";*/
        return tranDescription;
    }

    public String getTrans_time() {
        DateFormat sdf = SimpleDateFormat.getDateInstance();
        return sdf.format(tranTime);
    }

    public Integer getTranId() {
        return tranId;
    }

    public void setTranId(Integer tranId) {
        this.tranId = tranId;
    }

    public UserDetails getTblMpUser() {
        return tblMpUser;
    }

    public void setTblMpUser(UserDetails tblMpUser) {
        this.tblMpUser = tblMpUser;
    }

    public String getTranDescription() {
        return tranDescription;
    }

    public void setTranDescription(String tranDescription) {
        this.tranDescription = tranDescription;
    }

    public int getTranPoint() {
        return tranPoint;
    }

    public void setTranPoint(int tranPoint) {
        this.tranPoint = tranPoint;
    }

    public int getTranType() {
        return tranType;
    }

    public void setTranType(int tranType) {
        this.tranType = tranType;
    }

    public Date getTranTime() {
        return tranTime;
    }

    public void setTranTime(Date tranTime) {
        this.tranTime = tranTime;
    }
}
