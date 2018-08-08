package com.example.simhyobin.noti;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by insec on 2018-07-15.
 */

public class activity_sendmsg  extends AppCompatActivity{

    private int senddata_time;
    private int senddata_date;
    private int idx;
    private int user_cnt;
    private String[] list_name;
    private String[] list_id;
    private int sel_year, sel_month, sel_day, sel_hour, sel_minute;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmsg);
        /*
        String[] dat = {user_id, name, group_num};
         */
        Intent intent = getIntent();

        ArrayList<String[]> data = (ArrayList<String[]>)intent.getSerializableExtra("data");
        idx = (int)intent.getSerializableExtra("idx");
        user_cnt = data.size();
        int i=0;
        list_name = new String[user_cnt];
        list_id = new String[user_cnt];

        Iterator<String[]> iterator = data.iterator();

        while(iterator.hasNext()){
            String[] tempdata = iterator.next();
            list_name[i] = tempdata[1];
            list_id[i] = tempdata[0];
            i++;
        }


        /*
                    app:title="Name 외 1명"
            app:titleTextColor="@color/title_primary_text"
            app:subtitle="####년 ##월 ##일 ##시 ##분"
            app:subtitleTextColor="@color/title_secondary_text"
         */
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.test);

        if(idx == 1){
            if(user_cnt == 1){
                collapsingToolbarLayout.setTitle(String.valueOf(list_name[0]));
            }else{
                collapsingToolbarLayout.setTitle(String.valueOf(list_name[0])+" 등 "+String.valueOf(user_cnt)+"명");
            }
        }else{
            String grp_name = (String)intent.getSerializableExtra("grp_name");
            collapsingToolbarLayout.setTitle(grp_name);
        }



        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.title_primary_text, null));


        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.name_Toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        final EditText date_textInputLayout = (EditText)findViewById(R.id.textbox_date);
        date_textInputLayout.setInputType(0);
        date_textInputLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                int year = calendar.getWeekYear();
                int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(activity_sendmsg.this, 0, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedyear, int selectedmonth, int selectedday) {

                        GregorianCalendar gregorianCalendar = new GregorianCalendar(selectedyear, selectedmonth, selectedday-1);
                        int dayofweek = gregorianCalendar.get(Calendar.DAY_OF_WEEK);
                        String dayOfWeek = "NULL";
                        switch (dayofweek){
                            case 1:
                                dayOfWeek = "Mon";
                                break;
                            case 2:
                                dayOfWeek = "Tue";
                                break;
                            case 3:
                                dayOfWeek = "Wed";
                                break;
                            case 4:
                                dayOfWeek = "Thu";
                                break;
                            case 5:
                                dayOfWeek = "Fri";
                                break;
                            case 6:
                                dayOfWeek = "Sat";
                                break;
                            case 7:
                                dayOfWeek = "Sun";
                                break;
                        }

                        sel_year = selectedyear;
                        sel_month = selectedmonth;
                        sel_day = selectedday;
                        date_textInputLayout.setText(String.valueOf(selectedyear)+" / "+String.valueOf(selectedmonth+1) + " / " + String.valueOf(selectedday) + " ("+dayOfWeek+")");

                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });
        final EditText time_textInputLayout = (EditText)findViewById(R.id.textbox_time);
        time_textInputLayout.setInputType(0);
        time_textInputLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(activity_sendmsg.this, 3, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time_textInputLayout.setText(String.format("%02d", selectedHour)+" : "+ String.format("%02d", selectedMinute));
                        sel_hour = selectedHour;
                        sel_minute = selectedMinute;
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("시간 설정");
                mTimePicker.show();
            }
        });


        final ImageView expand_image = (ImageView)findViewById(R.id.expand_image);
        expand_image.setTag("closed");
        expand_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idx = (String)view.getTag();
                if (idx.equals("closed")) {
                    expand_image.setBackground(getResources().getDrawable(R.drawable.baseline_expand_less_black_24, null));
                    view.setTag("opened");

                    LinearLayout extra = (LinearLayout)findViewById(R.id.menu_extra);
                    extra.setVisibility(View.VISIBLE);



                }else{
                    expand_image.setBackground(getResources().getDrawable(R.drawable.baseline_expand_more_black_24, null));
                    view.setTag("closed");
                    LinearLayout extra = (LinearLayout)findViewById(R.id.menu_extra);
                    extra.setVisibility(View.GONE);
                }

            }
        });

        final EditText cnt_textboxlayout = (EditText)findViewById(R.id.textbox_alarmcnt);
        cnt_textboxlayout.setInputType(0);
        cnt_textboxlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity_sendmsg.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.number_picker_dialog);

                Button okBtn = (Button)dialog.findViewById(R.id.numberpicker_btn_ok);
                Button cancleBtn = (Button)dialog.findViewById(R.id.numberpicker_btn_cancel);
                final NumberPicker np = (NumberPicker)dialog.findViewById(R.id.numberPicker);
                np.setMinValue(1);
                np.setMaxValue(10);
                np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                setDividerColor(np, android.R.color.white);
                np.setWrapSelectorWheel(false);
                np.setValue(1);
                np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                    }
                });
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cnt_textboxlayout.setText(String.valueOf(np.getValue()));
                        dialog.dismiss();

                    }
                });
                cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        cnt_textboxlayout.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int val = Integer.parseInt(cnt_textboxlayout.getText().toString());
                for(int i=1; i<val; i++){

                }
            }
        });

        final EditText cycle_textboxlayout =  (EditText)findViewById(R.id.textbox_alarmcycle);
        cycle_textboxlayout.setInputType(0);
        cycle_textboxlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(activity_sendmsg.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.number_picker_dialog);

                TextView title = (TextView)dialog.findViewById(R.id.numberpicker_dialog_title);
                title.setText("알람 주기를 선택해주세요");

                Button okBtn = (Button)dialog.findViewById(R.id.numberpicker_btn_ok);
                Button cancleBtn = (Button)dialog.findViewById(R.id.numberpicker_btn_cancel);
                final NumberPicker np = (NumberPicker)dialog.findViewById(R.id.numberPicker);
                final String[] arrayString = new String[]{"1", "2", "3", "4", "5", "10", "15", "20", "25", "30", "40", "50", "60", "CUSTOM"};
                np.setMinValue(0);
                np.setMaxValue(arrayString.length-1);
                np.setFormatter(new NumberPicker.Formatter() {
                    @Override
                    public String format(int i) {
                        return arrayString[i];
                    }
                });

                np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                setDividerColor(np, android.R.color.white);
                np.setWrapSelectorWheel(false);
                np.setValue(1);
                np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                    }
                });
                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(np.getValue() == arrayString.length-1){
                            cycle_textboxlayout.setText("CUSTOM");
                        }else{
                            cycle_textboxlayout.setText(arrayString[np.getValue()]+" 분");
                        }

                        dialog.dismiss();
                    }
                });
                cancleBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_sendmsg);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getSharedPreferences("userprofile", MODE_PRIVATE);
                String user_id = pref.getString("id", "");

                EditText title = (EditText)findViewById(R.id.textbox_title);
                EditText date = (EditText)findViewById(R.id.textbox_date);
                EditText time = (EditText)findViewById(R.id.textbox_time);
                EditText content = (EditText)findViewById(R.id.textbox_content);
                EditText cycle = (EditText)findViewById(R.id.textbox_alarmcycle);
                EditText count = (EditText)findViewById(R.id.textbox_alarmcnt);

                long sendtime = System.currentTimeMillis()/1000L;
                Date notitime = getDate(sel_year, sel_month, sel_day, sel_hour, sel_minute);

                if(idx == 1){
                    HttpService retrofitService = HttpService.retrofit.create(HttpService.class);
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("content", content.getText().toString());
                    params.put("date", String.valueOf(sendtime));
                    params.put("title", title.getText().toString());
                    params.put("noti_time", String.valueOf(notitime.getTime()/1000L));
                    params.put("count", count.getText().toString());
                    params.put("cycle", cycle.getText().toString());
                    params.put("user_id", user_id);
                    for(int i=0; i<user_cnt; i++){
                        params.put("friend_id", list_id[i]);
                        Call<MessageResponseResource> send_message = retrofitService.send_message(params);
                        send_message.enqueue(new Callback<MessageResponseResource>() {
                            @Override
                            public void onResponse(Call<MessageResponseResource> call, Response<MessageResponseResource> response) {
                                MessageResponseResource messageResponseResource = response.body();
                                Log.d("message", messageResponseResource.result);
                            }

                            @Override
                            public void onFailure(Call<MessageResponseResource> call, Throwable t) {

                            }
                        });
                    }
                }else{

                }






            }
        });

    }

    private void setDividerColor(NumberPicker picker, int color){
        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for(java.lang.reflect.Field pf : pickerFields){
            if(pf.getName().equals("mSelectionDivier")){
                pf.setAccessible(true);
                try{
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                }catch (IllegalArgumentException e){
                    e.printStackTrace();
                }catch (Resources.NotFoundException e){
                    e.printStackTrace();
                }catch (IllegalAccessException e ){
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    public class SpinnerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

        public void onItemClick(AdapterView<?> parent, View view,
                                int pos, long id) {

        }
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    public Date getDate(int year, int month, int day, int hour, int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
