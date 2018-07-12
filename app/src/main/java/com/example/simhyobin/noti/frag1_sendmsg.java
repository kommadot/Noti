package com.example.simhyobin.noti;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by insec on 2018-07-11.
 */

public class frag1_sendmsg extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag1_sendmsg);
        String sec=getString(R.string.secondpage);
        TextView message = (TextView)findViewById(R.id.message);
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
        Button b = (Button)findViewById(R.id.timeButton);
        b.setText(time);
        String date;
        int m =calendar.get(Calendar.MONTH);
        m=m+1;
        date=calendar.get(Calendar.YEAR)+"/"+m+"/"+calendar.get(Calendar.DAY_OF_MONTH);
        Button bu = (Button)findViewById(R.id.dateButton);
        bu.setText(date);
        findViewById(R.id.timeButton).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(),timeSetListener,hour,minute,false);

                timePickerDialog.show();
            }
        });
        findViewById(R.id.dateButton).setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){

                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), dateSetListener, year, month, day);
                datePickerDialog.show();
            }
        });

    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date;
            date=year+"/"+monthOfYear+"/"+dayOfMonth;
            Button b = (Button)findViewById(R.id.dateButton);
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
            Button b = (Button)findViewById(R.id.timeButton);
            b.setText(time);

        }
    };
}
