package com.example.simhyobin.noti;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

public class fragment_2 extends Fragment {
    // Store instance variables
    private View views;
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
        views=view;
        TextView message = (TextView) view.findViewById(R.id.message);
        Calendar calendar = Calendar.getInstance(Locale.KOREA);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int minute = calendar.get(Calendar.MINUTE);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String time;
        int h = calendar.get(Calendar.HOUR_OF_DAY);

        if (h > 12){
            h=h-12;
            time="오후 "+h+"시 "+minute+"분";
        }
        else {
            time="오전 "+h+"시 "+minute+"분";
        }
        Button b = (Button) views.findViewById(R.id.timeButton);
        b.setText(time);
        String date;
        int m =calendar.get(Calendar.MONTH);
        m=m+1;
        date=calendar.get(Calendar.YEAR)+"/"+m+"/"+calendar.get(Calendar.DAY_OF_MONTH);
        Button bu = (Button) views.findViewById(R.id.dateButton);
        bu.setText(date);
        view.findViewById(R.id.timeButton).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),timeSetListener,hour,minute,false);

                timePickerDialog.show();
            }
        });
        view.findViewById(R.id.dateButton).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });
        return view;
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date;
            date=year+"/"+monthOfYear+"/"+dayOfMonth;
            Button b = (Button) views.findViewById(R.id.dateButton);
            b.setText(date);

        }
    };
    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {

            String time;
            if (hour > 12){
                hour= hour-12;
                time="오후 "+hour+"시 "+minute+"분";
            }
            else {
                time="오전 "+hour+"시 "+minute+"분";
            }
            Button b = (Button) views.findViewById(R.id.timeButton);
            b.setText(time);

        }
    };


}
