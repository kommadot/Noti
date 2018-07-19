package com.example.simhyobin.noti;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by insec on 2018-07-15.
 */

public class activity_sendmsg  extends AppCompatActivity{

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
        collapsingToolbarLayout.setTitle(String.valueOf(list_name[0])+" 등 "+String.valueOf(user_cnt)+"명");
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



    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
