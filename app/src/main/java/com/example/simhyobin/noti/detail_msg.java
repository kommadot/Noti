package com.example.simhyobin.noti;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
/**
 * Created by insec on 2018-07-15.
 */

public class detail_msg  extends AppCompatActivity{
    DBHelper dbhelper;
    String content;
    String username;
    String notidate;
    String recdate;
    String title;
    String hash;
    String userid;
    int position;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_msg);
        dbhelper = new DBHelper(this,"data", null, 1);
        intent = getIntent();
        content = intent.getStringExtra("content");
        username = intent.getStringExtra("username");
        notidate = intent.getStringExtra("notidate");
        recdate = intent.getStringExtra("recdate");
        title = intent.getStringExtra("title");
        hash = intent.getStringExtra("hash");
        userid = intent.getStringExtra("userid");
        position = intent.getIntExtra("position",-1);
        intent.putExtra("rm","N");
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); //툴바설정
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24);
        toolbar.setTitle("Message");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.del:
                // User chose the "Settings" item, show the app settings UI...
                dbhelper.rm_message(hash);
                intent.putExtra("rm", "Y");
                setResult(RESULT_OK,intent);
                super.onBackPressed();
                return true;

            case R.id.second:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
            default:
                return false;
        }
    }
}
