package com.example.sahilj.mevadaply.Responses;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class UserDetails implements Serializable {

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("user_fname")
    private String user_fname;

    @SerializedName("user_lname")
    private String user_lname;

    @SerializedName("user_phone")
    private String user_phone;

    @SerializedName("user_area")
    private String user_area;

    @SerializedName("user_city")
    private String user_city;

    @SerializedName("user_email")
    private String user_email;

    @SerializedName("user_pic_url")
    private String user_pic_url;

    @SerializedName("user_create_time")
    private String user_create_time;



    public String getUser_fname() {
        return user_fname;
    }


    public String getUser_lname() {
        return user_lname;
    }


    public String getUser_phone() {
        return user_phone;
    }


    public String getUser_area() {
        return user_area;
    }

    public String get_area(){
        String area = "";
        area += user_area;
        if(!user_city.isEmpty())
            area+=", ";
        area += user_city;

        return area;
    }

    public String getUser_city() {
        return user_city;
    }

    public String getUser_email() {
        return user_email;
    }


    public String getUser_pic_url() {
        return ApiClient.getBaseUrl()+user_pic_url;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public void setUser_area(String user_area) {
        this.user_area = user_area;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public void setUser_pic_url(String user_pic_url) {
        this.user_pic_url = user_pic_url;
    }

    public void setUser_create_time(String user_create_time) {
        this.user_create_time = user_create_time;
    }
}
