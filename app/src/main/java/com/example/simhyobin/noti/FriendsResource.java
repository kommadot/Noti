package com.example.simhyobin.noti;

import com.google.gson.annotations.SerializedName;

/**
 * Created by insec on 2018-08-01.
 */

public class FriendsResource {

    @SerializedName("result")
    public String result;
    @SerializedName("friend_id")
    public String friend_id;
    @SerializedName("friend_nickname")
    public String friend_nickname;
    @SerializedName("friend_img")
    public String friend_img;
}
