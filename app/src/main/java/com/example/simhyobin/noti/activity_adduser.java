package com.example.simhyobin.noti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.vision.text.Line;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by insec on 2018-08-01.
 */

public class activity_adduser extends AppCompatActivity {

    private String result_friend_photo;
    private String result_friend_id;
    private String result_friend_nickname;
    private DBHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

        Toolbar toolbar = (Toolbar)findViewById(R.id.layout_title);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent().putExtra("idx", "N");
                setResult(RESULT_OK, intent);
                onBackPressed();
                finish();
            }
        });

        AppCompatButton appCompatButton = (AppCompatButton)findViewById(R.id.profile_friend_addbutton);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper = new DBHelper(getApplicationContext(), "data", null, 1);
                dbhelper.add_friends(result_friend_id, result_friend_nickname, result_friend_photo);
                Intent intent = getIntent().putExtra("idx", "Y");
                setResult(RESULT_OK, intent);
                onBackPressed();
                finish();
            }
        });

        final EditText search_user = (EditText)findViewById(R.id.edittext_searchuser);
        search_user.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId){
                    case EditorInfo.IME_ACTION_SEARCH:

                        SharedPreferences pref = getSharedPreferences("userprofile", MODE_PRIVATE);
                        String user_id = pref.getString("id", "");
                        String friend_id = ((EditText)findViewById(R.id.edittext_searchuser)).getText().toString();

                        HttpService retrofitService = HttpService.retrofit.create(HttpService.class);

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("user_id", user_id);
                        params.put("friend_id", friend_id);

                        Call<FriendsResource> add_Friend = retrofitService.add_friend(params);
                        add_Friend.enqueue(new Callback<FriendsResource>() {
                            @Override
                            public void onResponse(Call<FriendsResource> call, Response<FriendsResource> response) {

                                LinearLayout profile_notfound = (LinearLayout)findViewById(R.id.layout_profile_notfound);
                                LinearLayout profile_friend = (LinearLayout)findViewById(R.id.profile_friend);

                                profile_notfound.setVisibility(View.GONE);
                                profile_friend.setVisibility(View.GONE);

                                FriendsResource friendsResource = response.body();
                                String result = friendsResource.result;
                                if(result.equals("success")){

                                    profile_friend.setVisibility(View.VISIBLE);

                                    ImageView profile_friend_photo = (ImageView)findViewById(R.id.profile_friend_photo);
                                    TextView profile_friend_nickname = (TextView)findViewById(R.id.profile_friend_nickname);
                                    profile_friend_photo.setBackground(new ShapeDrawable(new OvalShape()));
                                    profile_friend_photo.setClipToOutline(true);

                                    result_friend_photo = friendsResource.friend_img;
                                    result_friend_nickname = friendsResource.friend_nickname;
                                    result_friend_id = friendsResource.friend_id;

                                    if(result_friend_photo.equals("default")){
                                        profile_friend_photo.setImageResource(R.drawable.default_profilephoto);
                                    }else{
                                        try{

                                            Bitmap img = new ProcessGetProfilePhoto(getApplicationContext(), profile_friend_photo).execute(result_friend_photo).get();
                                            profile_friend_photo.setImageBitmap(img);
                                            profile_friend_nickname.setText(result_friend_nickname);

                                        }catch(Exception e){
                                            e.printStackTrace();
                                        }
                                    }

                                }else{

                                    profile_notfound.setVisibility(View.VISIBLE);

                                }

                            }

                            @Override
                            public void onFailure(Call<FriendsResource> call, Throwable t) {

                            }
                        });



                        break;
                    default:
                        return false;
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_user.getWindowToken(), 0);
                return true;
            }
        });

    }
}
