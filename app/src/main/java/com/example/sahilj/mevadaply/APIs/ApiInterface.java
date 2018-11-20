package com.example.sahilj.mevadaply.APIs;


import com.example.sahilj.mevadaply.Responses.DesignDetailResult;
import com.example.sahilj.mevadaply.Responses.DesignTrendsResult;
import com.example.sahilj.mevadaply.Responses.InsertResult;
import com.example.sahilj.mevadaply.Responses.RedeemOfferResult;
import com.example.sahilj.mevadaply.Responses.UserResult;
import com.example.sahilj.mevadaply.Responses.TransResult;
import com.example.sahilj.mevadaply.Responses.UpdateResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


/**
 * Created by Sahil J on 3/21/2017.
 */

public interface ApiInterface {


    @GET("users/{number}")
    Call<UserResult> getUserData(@Path("number") String number);

    @GET("users/{userId}/transactions")
    Call<TransResult> getTransactionData(@Path("userId") Integer userId);

    @Multipart
    @POST("users")
    Call<UserResult> updateUserData(@Part MultipartBody.Part file,
                                      @Part("fname") String fname,
                                      @Part("lname") String lname,
                                      @Part("email") String email,
                                      @Part("number") String number,
                                      @Part("address1") String address1,
                                      @Part("address2") String address2,
                                      @Part("city") String city);

    @GET("redeemOffers")
    Call<RedeemOfferResult> getRedeemOffers();

    @GET("ideasType")
    Call<DesignTrendsResult> getDesignTrends();

    @GET("ideas/{id}")
    Call<DesignDetailResult> getDesignDetails(@Path("id") int id);

    @FormUrlEncoded
    @POST("redeem")
    Call<InsertResult> redeemPoints(@Field("offerId") Integer offerId,
                                    @Field("userId") Integer userId);

    @FormUrlEncoded
    @POST("updateMobileNumber.php")
    Call<InsertResult> updateMobileNumber(@Field("user_old_number") String user_old_number,
                                    @Field("user_new_number") String user_new_number);


}