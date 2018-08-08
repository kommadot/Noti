package com.example.simhyobin.noti;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by insec on 2018-08-03.
 */

public class setting_userprofile extends AppCompatActivity {

    private String user_id;
    private String user_nickname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_userprofile);
        Intent intent = getIntent();

        user_id = intent.getStringExtra("userid");
        user_nickname = intent.getStringExtra("nickname");

        Toolbar toolbar = (Toolbar)findViewById(R.id.layout_title);
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

        ImageView imageView_profile = (ImageView)findViewById(R.id.setting_profile);
        imageView_profile.setBackground(new ShapeDrawable(new OvalShape()));
        imageView_profile.setClipToOutline(true);


        UserDB userDB = new UserDB(getApplicationContext(), "userdb", null, 1);
        Bitmap img = userDB.getUserProfile(user_id);
        imageView_profile.setImageBitmap(img);

        TextView textView_userid = (TextView)findViewById(R.id.textview_ID_content);
        textView_userid.setText(user_id);

        EditText edittext_usernickname = (EditText)findViewById(R.id.edittext_nickname_content);
        edittext_usernickname.setText(user_nickname);
    }
}
