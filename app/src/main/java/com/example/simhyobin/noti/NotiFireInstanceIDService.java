package com.example.simhyobin.noti;

/**
 * Created by simhyobin on 2018. 8. 2..
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class NotiFireInstanceIDService extends FirebaseInstanceIdService{
    private static final String TAG = "NotiFirebaseIDService";

    @Override
    public void onTokenRefresh(){
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG,"Refreshed token : "+token);
        sendRegistrationsToServer(token);

    }

    private void sendRegistrationsToServer(String token){

    }
}
