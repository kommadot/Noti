package com.example.simhyobin.noti;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by insec on 2018-07-31.
 */

public class ProcessGetProfilePhoto extends AsyncTask<String, Void, Bitmap> {

    private Context mContext;
    private View rootView;

    public ProcessGetProfilePhoto(){

    }
    @Override
    protected Bitmap doInBackground(String ... params){

        try{
            URL url = new URL(params[0]);
            Bitmap img = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return img;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }


        return null;
    }
}
