package com.example.simhyobin.noti;

import android.database.Observable;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by insec on 2018-05-02.
 */

public interface HttpService {
    public static final String API_URL = "http://komad.kr:31337";


    @POST("/message")
    Call<ResponseBody> test_server();

    @POST("/user_generate")
    @FormUrlEncoded
    Call<GeneratorResource> id_generate(@FieldMap Map<String, String> params);

    @POST("/friend_add")
    @FormUrlEncoded
    Call<FriendsResource> add_friend(@FieldMap Map<String, String> params);

    @POST("/set_notification")
    @FormUrlEncoded
    Call<MessageResponseResource> send_message(@FieldMap Map<String, String> params);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
