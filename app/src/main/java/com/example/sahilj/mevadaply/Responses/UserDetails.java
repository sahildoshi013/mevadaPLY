package com.example.sahilj.mevadaply.Responses;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.sahilj.mevadaply.APIs.ApiClient;
import com.example.sahilj.mevadaply.Utils.MyConstants;
import com.example.sahilj.mevadaply.Utils.MyUtilities;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Sahil J on 2/27/2018.
 */

public class UserDetails implements Serializable {

    @SerializedName("userId")
    private Integer userId;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("emailId")
    private String emailId;

    @SerializedName("phoneNo")
    private String phoneNo;

    @SerializedName("addressLine1")
    private String addressLine1;

    @SerializedName("addressLine2")
    private String addressLine2;

    @SerializedName("city")
    private String city;

    @SerializedName("profilePic")
    private String profilePic;

    @SerializedName("password")
    private String password;

    @SerializedName("userVerified")
    private boolean userVerified;

    @SerializedName("userType")
    private int userType;

    @SerializedName("pointEarned")
    private int pointEarned;

    @SerializedName("pointRedeem")
    private int pointRedeem;

    @SerializedName("timeOfCreation")
    private Date timeOfCreation;

    @SerializedName("timeOfUpdation")
    private Date timeOfUpdation;

    public String get_area(){
        StringBuilder sb = new StringBuilder(addressLine1);
        sb.append(" ");
        sb.append(addressLine2);
        sb.append((city!=null) ? ", "+city : "");
        return sb.toString();
    }

    public String getUser_pic_url() {
        return MyUtilities.USER_IMAGE_BASE_URL + profilePic;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isUserVerified() {
        return userVerified;
    }

    public void setUserVerified(boolean userVerified) {
        this.userVerified = userVerified;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getPointEarned() {
        return pointEarned;
    }

    public void setPointEarned(int pointEarned) {
        this.pointEarned = pointEarned;
    }

    public int getPointRedeem() {
        return pointRedeem;
    }

    public void setPointRedeem(int pointRedeem) {
        this.pointRedeem = pointRedeem;
    }

    public Date getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(Date timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public Date getTimeOfUpdation() {
        return timeOfUpdation;
    }

    public void setTimeOfUpdation(Date timeOfUpdation) {
        this.timeOfUpdation = timeOfUpdation;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
