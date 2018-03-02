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

    private static final String USER_ID = "user_id";
    private static final String USER_FNAME = "user_fname";
    private static final String USER_LNAME = "user_lname";
    private static final String USER_PHONE = "user_phone";
    private static final String USER_AREA = "user_area";
    private static final String USER_CITY = "user_city";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_TIME = "user_create_time";
    private static final String USER_PIC = "user_pic_url";

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

    public void writeData(Activity activity){
        SharedPreferences sharedPreferences= activity.getSharedPreferences(MyConstants.DB_USER,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID,this.user_id);
        editor.putString(USER_FNAME,this.user_fname);
        editor.putString(USER_LNAME,this.user_lname);
        editor.putString(USER_PHONE,this.user_phone);
        editor.putString(USER_AREA,this.user_area);
        editor.putString(USER_EMAIL,this.user_email);
        editor.putString(USER_CITY,this.user_city);
        editor.putString(USER_TIME, this.user_create_time);
        editor.putString(USER_PIC,this.user_pic_url);
        editor.apply();
    }

    public void readData(Activity activity){
        SharedPreferences sharedPreferences= activity.getSharedPreferences(MyConstants.DB_USER,MODE_PRIVATE);
        this.user_id = sharedPreferences.getInt(USER_ID,0);
        this.user_fname = sharedPreferences.getString(USER_FNAME,null);
        this.user_lname = sharedPreferences.getString(USER_LNAME,null);
        this.user_phone = sharedPreferences.getString(USER_PHONE,null);
        this.user_area = sharedPreferences.getString(USER_AREA,null);
        this.user_email= sharedPreferences.getString(USER_EMAIL,null);
        this.user_city = sharedPreferences.getString(USER_CITY,null);
        this.user_pic_url = sharedPreferences.getString(USER_PIC,null);
        this.user_create_time = sharedPreferences.getString(USER_TIME,null);
    }
}
