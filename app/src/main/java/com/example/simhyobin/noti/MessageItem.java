package com.example.simhyobin.noti;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class MessageItem {
    private String name;

    public MessageItem (String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public static ArrayList<MessageItem> createContactsList(int numContacts) {
        ArrayList<MessageItem> contacts = new ArrayList<MessageItem>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new MessageItem("Person "));
        }

        return contacts;
    }
}