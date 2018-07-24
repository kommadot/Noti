package com.example.simhyobin.noti;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by insec on 2018-07-24.
 */

public class activity_grp extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grp);
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

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsingtoolbar_grp);
        collapsingToolbarLayout.setTitle("새 그룹 만들기");
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

}
