package com.example.simhyobin.noti;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

        EditText search_user = (EditText)findViewById(R.id.edittext_searchuser);
        search_user.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId){
                    case EditorInfo.IME_ACTION_SEARCH:

                        SharedPreferences pref = getSharedPreferences("userprofile", MODE_PRIVATE);
                        String user_id = pref.getString("id", "");
                        String friend_id = ((EditText)((EditText) findViewById(R.id.edittext_searchuser))).getText().toString();

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
                                    Log.d("addfriendtest", friendsResource.friend_id);
                                    Log.d("addfriendtest", friendsResource.friend_nickname);
                                    Log.d("addfriendtest", friendsResource.friend_img);
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

                return true;
            }
        });

    }
}
