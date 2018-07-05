package com.example.simhyobin.noti;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;

public class fragment_2 extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static fragment_2 newInstance(int page, String title) {
        fragment_2 fragmentFirst = new fragment_2();

        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String sec=getString(R.string.secondpage);
        View view = inflater.inflate(R.layout.fragment_fragment_2, container, false);
        TextView message = (TextView) view.findViewById(R.id.message);
        message.setText(sec);
        return view;
    }

}
