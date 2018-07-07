package com.example.simhyobin.noti;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import java.util.Calendar;


public final class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    static Calendar c = Calendar.getInstance();
    static int hour ;
    static int minute;
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState){
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minute,DateFormat.is24HourFormat(getActivity()));
    }
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }
    public static SelectTimeFragment newInstance(Bundle b) {
        SelectTimeFragment fragment = new SelectTimeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}
