package com.example.simhyobin.noti;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Created by insec on 2018-07-15.
 */

public class activity_sendmsg  extends AppCompatActivity{

    private int senddata_time;
    private int senddata_date;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmsg);
        /*
        String[] dat = {user_id, name, group_num};
         */
        Intent intent = getIntent();

        ArrayList<String[]> data = (ArrayList<String[]>)intent.getSerializableExtra("data");
        int user_cnt = data.size();
        int i=0;
        String[] list_name = new String[user_cnt];
        String[] list_id = new String[user_cnt];

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
        if(user_cnt == 1){
            collapsingToolbarLayout.setTitle(String.valueOf(list_name[0]));
        }else{
            collapsingToolbarLayout.setTitle(String.valueOf(list_name[0])+" 등 "+String.valueOf(user_cnt)+"명");
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
}
