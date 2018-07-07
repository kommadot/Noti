package com.example.simhyobin.noti;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.Calendar;

public class fragment_2 extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private String time;
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
        view.findViewById(R.id.dateButton).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                DialogFragment newFragment = new selectDateFragment();
                newFragment.show(getActivity().getSupportFragmentManager(),"datePicker");
            }
        });
        view.findViewById(R.id.timeButton).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Bundle b = new Bundle();
                DialogFragment newFragment = new SelectTimeFragment(b);
                Bundle b;
                newFragment.show(getActivity().getSupportFragmentManager(),"timePicker");
                b=newFragment.getArguments();
                SelectTimeFragment fragment = (SelectTimeFragment) getFragmentManager().findFragmentById(R.id.fragment_select_time);
                int h;
                int m;
                h= b.getInt("hour");
                m= b.getInt("minute");
                time = h+"시 "+m+"분 ";
                Log.d("komad",time);
            }
        });
        Button b = (Button) view.findViewById(R.id.timeButton);
        b.setText(time);
        //message.setText(sec);
        return view;
    }

}
