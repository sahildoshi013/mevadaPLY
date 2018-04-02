package com.example.sahilj.mevadaply.APIs;


import com.example.sahilj.mevadaply.Responses.DesignDetailResult;
import com.example.sahilj.mevadaply.Responses.DesignTrendsResult;
import com.example.sahilj.mevadaply.Responses.InsertResult;
import com.example.sahilj.mevadaply.Responses.RedeemOfferResult;
import com.example.sahilj.mevadaply.Responses.Result;
import com.example.sahilj.mevadaply.Responses.TransResult;
import com.example.sahilj.mevadaply.Responses.UpdateResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


/**
 * Created by Sahil J on 3/21/2017.
 */

public interface ApiInterface {


    @FormUrlEncoded
    @POST("userData.php")
    Call<Result> getUserData(@Field("user_id") String user);

    @FormUrlEncoded
    @POST("userTransaction.php")
    Call<TransResult> getTransactionData(@Field("user_id") String user);

    @Multipart
    @POST("updateUserData.php")
    Call<UpdateResult> updateUserData(@Part MultipartBody.Part file,
                                      @Part("fname") RequestBody fname,
                                      @Part("lname") RequestBody lname,
                                      @Part("email") RequestBody email,
                                      @Part("number") RequestBody number,
                                      @Part("area") RequestBody area,
                                      @Part("city") RequestBody city);

    @POST("redeemOffers.php")
    Call<RedeemOfferResult> getRedeemOffers();

    @POST("designTrends.php")
    Call<DesignTrendsResult> getDesignTrends();

    @FormUrlEncoded
    @POST("designDetails.php")
    Call<DesignDetailResult> getDesignDetails(@Field("design_id") int design_id);


    @FormUrlEncoded
    @POST("insertRequest.php")
    Call<InsertResult> redeemPoints(@Field("design_id") int design_id,
                                    @Field("Phone_No") String phone_no);

    @FormUrlEncoded
    @POST("updateMobileNumber.php")
    Call<InsertResult> updateMobileNumber(@Field("user_old_number") String user_old_number,
                                    @Field("user_new_number") String user_new_number);


}