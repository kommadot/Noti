package com.android.jakchang.cameraapp;

import android.database.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by insec on 2018-05-02.
 */

public interface HttpService {
    public static final String API_URL = "http://komad.kr:30001/";
    /*
    @GET("/getVersion.php")
    Call<ResponseBody>getComment(@Query("version") String version);
    @Multipart
    @POST("/menu2.php")
    Call<ResponseBody> postImage_menu2(@Part MultipartBody.Part file, @Part("name") RequestBody name);
    @Multipart
    @POST("/menu3.php")
    Call<ResponseBody> postImage_menu3(@Part MultipartBody.Part file, @Part("name") RequestBody name);
    @Multipart
    @POST("/menu4.php")
    Call<ResponseBody> postImage_menu4(@Part MultipartBody.Part file, @Part("name") RequestBody name);
    @GET("/menu4_check.php")
    Call<ResponseBody>check_menu4(@Query("check") String check);
    */
}
