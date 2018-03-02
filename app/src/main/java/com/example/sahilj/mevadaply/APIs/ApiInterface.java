package com.example.sahilj.mevadaply.APIs;


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
    @POST("updateMobileNumber.php")
    Call<Result> updateMobileNumber(@Field("user_old_number") String user_old_number,@Field("user_new_number") String user_new_number);


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

    /*@FormUrlEncoded
    @POST("login.php")
    Call<Result> getUserData(@Field("user") String user,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("signup.php")
    Call<Result> signUp(@Field("fname") String fname,
                        @Field("lname") String lname,
                        @Field("email") String email,
                        @Field("password") String password,
                        @Field("dob_day") Integer dob_day,
                        @Field("dob_month") Integer dob_month,
                        @Field("dob_year") Integer dob_year,
                        @Field("gender") String gender);

    @Multipart
    @POST("steptwo.php")
    Call<Result> stepTwo(@Part MultipartBody.Part file,
                         @Part("nickname") RequestBody nickname,
                         @Part("sitename") RequestBody sitename);

    @FormUrlEncoded
    @POST("steptwo.php")
    Call<Result> stepTwowithoutImage(@Field("nickname") String nickname,
                                     @Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("stepthree.php")
    Call<Result> stepthree(@Field("backupemail") String backupemail,
                           @Field("phonenumber") String phonenumber,
                           @Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("post.php")
    Call<Result> post(
            @Field("caption") String caption,
            @Field("type") String type,
            @Field("sitename") String sitename,
            @Field("share") String share);

    @Multipart
    @POST("imageupload.php")
    Call<Result> imageupload(@Part MultipartBody.Part file,
                             @Part("imgnumber") RequestBody imgnumber,
                             @Part("postid") RequestBody postid,
                             @Part("extension") RequestBody extension);

    @Multipart
    @POST("imageComparisonupload.php")
    Call<Result> imageComparisonupload(@Part MultipartBody.Part file,
                                       @Part MultipartBody.Part file1,
                                       @Part("postid") RequestBody postid,
                                       @Part("extension") RequestBody extension,
                                       @Part("extension1") RequestBody extension1);

    @Multipart
    @POST("videoupload.php")
    Call<Result> videoupload(@Part MultipartBody.Part file,
                             @Part("postid") RequestBody postid,
                             @Part("extension") RequestBody extension);

    @FormUrlEncoded
    @POST("undoupload.php")
    Call<Result> stepthree(@Field("postid") String postid);

    @FormUrlEncoded
    @POST("userdetails.php")
    Call<UserDetails> userdetails(@Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("updateuserdetails.php")
    Call<Result> updateuserdetails(@Field("sitename") String sitename,
                                   @Field("fname") String fname,
                                   @Field("lname") String lname,
                                   @Field("email") String email,
                                   @Field("nickname") String nickname,
                                   @Field("backupmail") String backupmail,
                                   @Field("phone") String phone,
                                   @Field("dob_day") Integer dob_day,
                                   @Field("dob_month") Integer dob_month,
                                   @Field("dob_year") Integer dob_year,
                                   @Field("gender") String gender,
                                   @Field("active") Integer active);

    @FormUrlEncoded
    @POST("changepassword.php")
    Call<Result> changepassword(@Field("sitename") String sitename,
                                @Field("oldpassword") String oldpassword,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("userdescription.php")
    Call<UserDescription> userDescription(@Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("updateuserdescription.php")
    Call<Result> updateuserdescription(@Field("sitename") String sitename,
                                       @Field("homecity") String homecity,
                                       @Field("homecountry") String homecountry,
                                       @Field("currentcity") String currentcity,
                                       @Field("currentcountry") String currentcountry,
                                       @Field("school") String school,
                                       @Field("college") String college,
                                       @Field("desc") String desc);


    @FormUrlEncoded
    @POST("updateuseralert.php")
    Call<Result> updateuseralert(@Field("sitename") String sitename,
                                 @Field("mailalert") Integer mailalert,
                                 @Field("phonealert") Integer phonealert);

    @FormUrlEncoded
    @POST("userprivacy.php")
    Call<UserPrivacy> userPrivacyDetail(@Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("updateuserprivacy.php")
    Call<Result> updateuserPrivacyDetail(@Field("sitename") String sitename,
                                         @Field("mailprivacy") Integer mailprivacy,
                                         @Field("phoneprivacy") Integer phoneprivacy,
                                         @Field("postprivacy") Integer postprivacy);

    @FormUrlEncoded
    @POST("updateuserprivacyinfo.php")
    Call<Result>  updateuserPrivacyInfo(@Field("sitename") String sitename,
                                        @Field("dobprivacy") Integer dobprivacy,
                                        @Field("currentcityprivacy") Integer currentcityprivacy,
                                        @Field("homecityprivacy") Integer homecityprivacy,
                                        @Field("schoolprivacy") Integer schoolprivacy,
                                        @Field("collegeprivacy") Integer collegeprivacy);

    @FormUrlEncoded
    @POST("usernotificationprivacy.php")
    Call<UserNotificationPrivacy> userNotificationPrivacyDetail(@Field("sitename") String sitename);


    @FormUrlEncoded
    @POST("updateusernotiprivacy.php")
    Call<Result>  updateuserNotiPrivacy(@Field("sitename") String sitename,
                                        @Field("emailnotiprivacy") Integer emailnotiprivacy,
                                        @Field("phonenotiprivacy") Integer phonenotiprivacy);

    @FormUrlEncoded
    @POST("userchatprivacy.php")
    Call<UserChatPrivacy> userChatPrivacyDetail(@Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("userprofiledetails.php")
    Call<UserProfileDetail> userProfileDetail(@Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("updateuserchatprivacy.php")
    Call<Result>  updateuserChatPrivacy(@Field("sitename") String sitename,
                                        @Field("wcmy") Integer wcmy,
                                        @Field("chatoptions") Integer chatoptions);
    @FormUrlEncoded
    @POST("searchuserdata.php")
    Call<SearchResponse> getAllUser(@Field("data") String data, @Field("offset") Integer offset);

    @FormUrlEncoded
    @POST("displaypost.php")
    Call<PostResponse> getPost(@Field("offset") Integer offset, @Field("name") String name, @Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("requestImages.php")
    Call<ImageResponse> getImage(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("requestVideo.php")
    Call<VideoResponse> getVideo(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("postlike.php")
    Call<Result> setPostLike(@Field("id") Integer id, @Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("postunlike.php")
    Call<Result> setPostUnlike(@Field("id") Integer id, @Field("sitename") String sitename);

    @FormUrlEncoded
    @POST("postreport.php")
    Call<Result> reportPost(@Field("id") Integer id, @Field("sitename") String sitename, @Field("text") String text);

    @FormUrlEncoded
    @POST("updatePost.php")
    Call<Result> updatePost(@Field("id") Integer id, @Field("text") String text);

    @FormUrlEncoded
    @POST("deletePost.php")
    Call<Result> deletePost(@Field("id") Integer id);

    @FormUrlEncoded
    @POST("displaynoti.php")
    Call<NotiResponse> getNoti(@Field("sitename") String sitename, @Field("offset") Integer offset);

    @FormUrlEncoded
    @POST("verifyaccount.php")
    Call<Result> verifyacc(@Field("sitename") String sitename, @Field("pin") Integer pin);*/
}