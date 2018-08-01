package com.example.simhyobin.noti;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class fragment_3 extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static fragment_3 newInstance(int page, String title) {
        fragment_3 fragmentFirst = new fragment_3();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_fragment_3, container, false);


        ImageView profile_photo = (ImageView)view.findViewById(R.id.profile_photo);
        profile_photo.setBackground(new ShapeDrawable(new OvalShape()));
        profile_photo.setClipToOutline(true);

        //profile_photo.setImageResource(R.drawable.default_profilephoto);

        SharedPreferences profile_pref = this.getActivity().getSharedPreferences("userprofile", Context.MODE_PRIVATE);
        String str_photo_url = profile_pref.getString("photo", "");


        try{

            Bitmap img = new ProcessGetProfilePhoto(getActivity(), profile_photo).execute(str_photo_url).get();
            profile_photo.setImageBitmap(img);

        }catch(Exception e){
            e.printStackTrace();
        }




        return view;
    }
}