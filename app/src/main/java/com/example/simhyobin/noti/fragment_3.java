package com.example.simhyobin.noti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.ResponseBody;


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

        SharedPreferences profile_pref = this.getActivity().getSharedPreferences("userprofile", Context.MODE_PRIVATE);
        final String user_nickname = profile_pref.getString("name", "");
        final String user_id = profile_pref.getString("id", "");

        TextView nickname_textview = (TextView)view.findViewById(R.id.layout_profile_username);
        nickname_textview.setText(user_nickname);
        String myNumber = null;
        TelephonyManager mgr = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        try{
            myNumber = mgr.getLine1Number();
            TextView phone_textview = (TextView)view.findViewById(R.id.layout_profile_phonenumber);
            phone_textview.setText(myNumber);
        }catch (Exception e){
            e.printStackTrace();
        }

        UserDB userDB = new UserDB(getActivity(), "userdb", null, 1);

        Bitmap img = userDB.getUserProfile(user_id);
        profile_photo.setImageBitmap(img);



        final RelativeLayout setting_profile = (RelativeLayout)view.findViewById(R.id.layout_setting_profile);
        setting_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), setting_userprofile.class);
                intent.putExtra("userid", user_id);
                intent.putExtra("nickname", user_nickname);
                startActivity(intent);
            }
        });
        final RelativeLayout setting_notice = (RelativeLayout)view.findViewById(R.id.layout_setting_notice);
        setting_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), setting_notice.class);
                startActivity(intent);
            }
        });
        final RelativeLayout setting_help = (RelativeLayout)view.findViewById(R.id.layout_setting_help);
        setting_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), setting_help.class);
                startActivity(intent);
            }
        });



        return view;
    }

}