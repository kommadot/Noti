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


public class MessageItem{
    private String username;
    private String content;
    private String title;
    private int noti_date,rec_date;
    public MessageItem (String username,String content,String title,int noti_date,int rec_date){
        this.username = username;
        this.content = content;
        this.noti_date = noti_date;
        this.rec_date = rec_date;
        this.title= title;
    }
    public String getTitle(){return title;}
    public String getUsername() {
        return username;
    }
    public String getContent(){
        return content;
    }
    public int getNoti_date(){
        return noti_date;
    }
    public int getRec_date(){
        return rec_date;
    }
    public static ArrayList<MessageItem> createContactsList(ArrayList<String[]> data) {
        ArrayList<MessageItem> contacts = new ArrayList<MessageItem>();
        Iterator iterator =  data.iterator();
        while(iterator.hasNext()){
            String[] temp = (String[])iterator.next();
            contacts.add(new MessageItem(temp[1],temp[2],temp[3],Integer.parseInt(temp[4]),Integer.parseInt(temp[5])));
            Log.d("mei",temp[4]);
            Log.d("mei",temp[5]);
        }
        return contacts;
    }
}