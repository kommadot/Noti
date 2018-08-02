package com.example.simhyobin.noti;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by insec on 2018-07-09.
 */

public class LoadingActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(isLogin()){
                    SharedPreferences pref = getSharedPreferences("userprofile", MODE_PRIVATE);
                    String user_name = pref.getString("name", "");
                    if(user_name.equals("")){
                        SharedPreferences login_pref = getSharedPreferences("pref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = login_pref.edit();
                        editor.putString("isLogin", "False");
                        editor.commit();
                    }else{
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }

                RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.layout_title);
                int height = relativeLayout.getHeight();

                int title_height = height/3 * 2;
                int login_height = height/3 * 1;
                int target_title_height = height - title_height;

                ResizeAnimation resizeAnimation = new ResizeAnimation(relativeLayout, (int)target_title_height, (int)height);
                resizeAnimation.setDuration(500);
                relativeLayout.startAnimation(resizeAnimation);

                RelativeLayout relativeLayout_login = (RelativeLayout)findViewById(R.id.layout_login);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, login_height);
                relativeLayout_login.setLayoutParams(params);
                relativeLayout_login.setVisibility(View.VISIBLE);
            }
        }, 1500);

        SignInButton signInButton = (SignInButton)findViewById(R.id.sign_in_google);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this ,  this )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });


    }
    private boolean isLogin(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String idx = pref.getString("isLogin", "");
        if(idx.equals("True")){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("testlog", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("testlog", "onOptionsItemSelected");
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("testlog", "onConnected");

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.d("testlog", "onConnectionSuspended");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("testlog", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("isLogin", "True");
            editor.commit();


            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
            mGoogleApiClient.connect();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        Log.d("LoginTest", String.valueOf(result.getStatus()));
        if(result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();

            SharedPreferences pref = getSharedPreferences("userprofile", MODE_PRIVATE);
            final SharedPreferences.Editor editor = pref.edit();

            String user_name = acct.getDisplayName();
            String user_email = acct.getEmail();
            String user_genID = acct.getId();
            String user_profile;
            if(acct.getPhotoUrl() == null){
                user_profile = "default";
            }else{
                user_profile = acct.getPhotoUrl().toString();
            }


            HttpService retrofitService = HttpService.retrofit.create(HttpService.class);
            Map<String, String> params3 = new HashMap<String, String>();
            params3.put("ungen_id", user_genID);
            params3.put("user_nickname", user_name);
            params3.put("user_email", user_email);
            params3.put("profile_url", user_profile);
            params3.put("fcm_token", FirebaseInstanceId.getInstance().getToken());

            Call<GeneratorResource> getID = retrofitService.id_generate(params3);
            getID.enqueue(new Callback<GeneratorResource>() {
                @Override
                public void onResponse(Call<GeneratorResource> call, Response<GeneratorResource> response) {
                    Log.d("NetworkTest", String.valueOf(response.body()));
                    GeneratorResource generatorResource = response.body();
                    String result = generatorResource.result;
                    if(result.equals("success")){
                        editor.putString("id", generatorResource.user_id);
                    }else{

                    }
                }

                @Override
                public void onFailure(Call<GeneratorResource> call, Throwable t) {
                    Log.d("httptest", "error");
                    t.printStackTrace();
                }
            });
            editor.putString("name", user_name);
            editor.putString("email", user_email);
            editor.putString("photo", user_profile);
            editor.commit();

        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("testlog", "onConnectionFailed");
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, RC_SIGN_IN);

            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {

        }
    }
}
