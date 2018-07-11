package com.example.simhyobin.noti;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;


public class MessageItem {
    private String username;
    private String content;
    private String noti_date,rec_date;
    public MessageItem (String username,String content,String noti_date,String rec_date){
        this.username = username;
        this.content = content;
        this.noti_date = noti_date;
        this.rec_date = rec_date;
    }

    public String getUsername() {
        return username;
    }
    public String getContent(){
        return content;
    }
    public String getNoti_date(){
        return noti_date;
    }
    public String getRec_date(){
        return rec_date;
    }
    public static ArrayList<MessageItem> createContactsList(ArrayList<String[]> data) {
        ArrayList<MessageItem> contacts = new ArrayList<MessageItem>();
        Iterator iterator =  data.iterator();
        while(iterator.hasNext()){
            String[] temp = (String[])iterator.next();
            contacts.add(new MessageItem(temp[1],temp[2],temp[3],temp[4]));

        }
        return contacts;
    }
}