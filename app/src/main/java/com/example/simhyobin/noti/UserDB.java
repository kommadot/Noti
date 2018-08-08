package com.example.simhyobin.noti;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.sql.Blob;

/**
 * Created by insec on 2018-08-04.
 */

public class UserDB extends SQLiteOpenHelper {

    public UserDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE USER_FRIENDS ( ");
        sb.append("USER_ID STRING PRIMARY KEY NOT NULL, ");
        sb.append("USER_NAME STRING NOT NULL, ");
        sb.append("USER_EMAIL STRING, ");
        sb.append("FCM_TOKEN STRING, ");
        sb.append("USER_PROFILE BLOB );");

        db.execSQL(sb.toString());
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public void InsertUserData(String id, String name, String email, String token, byte[] profile){
        SQLiteDatabase db = getWritableDatabase();
        SQLiteStatement p = db.compileStatement("INSERT INTO USER_FRIENDS VALUES(?, ?, ?, ?, ?);");
        p.bindString(1, id);
        p.bindString(2, name);
        p.bindString(3, email);
        p.bindString(4, token);
        p.bindBlob(5, profile);
        p.execute();

        db.close();
    }
    public Bitmap getUserProfile(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT USER_PROFILE FROM USER_FRIENDS WHERE USER_ID=?", new String[]{id});

        try{
            cursor.moveToFirst();

            Bitmap bitmap = BitmapFactory.decodeByteArray(cursor.getBlob(0), 0, cursor.getBlob(0).length);

            return bitmap;
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }

    public void mod_usernickname(String nickname, String id){
        SQLiteDatabase db = getWritableDatabase();

        //update users set user_nickname="fuck" where user_id="b40f22"
        db.execSQL("UPDATE USER_FRIENDS SET USER_NAME = ? WHERE USER_ID=?", new String[]{nickname, id});
        db.close();
    }
}
