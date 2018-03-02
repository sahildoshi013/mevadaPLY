package com.example.sahilj.mevadaply.APIs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sahil J on 3/21/2017.
 */

public class ApiClient {

    public static String getBaseUrl() {
        return BASE_URL;
    }

    //private static final String BASE_URL = "http://192.168.43.107:80/mevadaply/";
    //public static final String BASE_URL = "http://10.0.2.2:80/mevadaply/";
    //public static final String BASE_URL = "http://192.168.0.19:80/android/";
    private static final String BASE_URL = "http://www.swoopzi.com/mevada/";
    private static Retrofit retrofit = null;
    private static Gson gson=new GsonBuilder().setLenient().create();
    private static final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build();

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
