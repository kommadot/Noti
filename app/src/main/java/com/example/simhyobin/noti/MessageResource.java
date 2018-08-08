package com.example.simhyobin.noti;

import com.google.gson.annotations.SerializedName;

/**
 * Created by simhyobin on 2018-08-01.
 */

public class MessageResource {

    @SerializedName("hash_key")
    public String hash_key;
    @SerializedName("title")
    public String title;
    @SerializedName("content")
    public String content;
    @SerializedName("date")
    public int date;
    @SerializedName("noti_time")
    public int noti_time;
    @SerializedName("author_id")
    public String author_id;
    @SerializedName("result")
    public String result;
}

/*'result':'success','hash_key':row[0],'title':row[1],'content':row[2],'date':row[3],'noti_time':row[4],'author_id':row[5]}*/