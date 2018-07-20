package com.example.simhyobin.noti;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by insec on 2018-07-15.
 */

public class detail_msg  extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_msg);

        Intent intent = getIntent();

        String content = intent.getStringExtra("content");
        String username = intent.getStringExtra("username");
        String notidate = intent.getStringExtra("notidate");
        String recdate = intent.getStringExtra("recdate");
        String title = intent.getStringExtra("title");
        EditText titleView = (EditText) findViewById(R.id.title);
        EditText contentView = (EditText) findViewById(R.id.content);
        EditText usernameView = (EditText) findViewById(R.id.username);
        EditText notidateView = (EditText) findViewById(R.id.notidate);
        EditText recdateView = (EditText) findViewById(R.id.recdate);

        titleView.setText(title);
        contentView.setText(content);
        usernameView.setText(username);
        notidateView.setText(notidate);
        recdateView.setText(recdate);
        Log.d("detail noti",notidate);
        Log.d("detail rec",recdate);

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
