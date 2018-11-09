package com.example.simhyobin.noti;

import android.graphics.Bitmap;

/**
 * Created by insec on 2018-11-08.
 */

public class friendsData {

    private String id;
    private String nickname;
    private Bitmap profile;
    private int fav;
    private int cnt;


    friendsData(String _id, String _nickname, Bitmap _profile, int _fav, int _cnt){
        id = _id;
        nickname = _nickname;
        profile = _profile;
        fav = _fav;
        cnt = _cnt;
    }

    public String getID(){
        return id;
    }
    public String getNickname(){
        return nickname;
    }
    public Bitmap getProfile(){
        return profile;
    }
    public int getCnt(){
        return cnt;
    }
    public int getFav(){
        return fav;
    }

}
