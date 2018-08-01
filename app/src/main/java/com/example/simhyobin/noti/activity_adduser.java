package com.example.simhyobin.noti;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by insec on 2018-08-01.
 */

public class activity_adduser extends AppCompatActivity {

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
                                FriendsResource friendsResource = response.body();
                                String result = friendsResource.result;
                                if(result.equals("success")){
                                    LinearLayout profile_friend = (LinearLayout)findViewById(R.id.profile_friend);
                                    profile_friend.setVisibility(View.VISIBLE);

                                    ImageView profile_friend_photo = (ImageView)findViewById(R.id.profile_friend_photo);
                                    TextView profile_friend_nickname = (TextView)findViewById(R.id.profile_friend_nickname);
                                    profile_friend_photo.setBackground(new ShapeDrawable(new OvalShape()));
                                    profile_friend_photo.setClipToOutline(true);

                                    String friend_photo = friendsResource.friend_img;
                                    String friend_nickname = friendsResource.friend_nickname;

                                    try{

                                        Bitmap img = new ProcessGetProfilePhoto(getApplicationContext(), profile_friend_photo).execute(friend_photo).get();
                                        profile_friend_photo.setImageBitmap(img);
                                        profile_friend_nickname.setText(friend_nickname);

                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }else{
                                    Log.d("addfriendtest", "FUCKYOU");

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
