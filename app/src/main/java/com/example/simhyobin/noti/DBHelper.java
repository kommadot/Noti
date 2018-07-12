package com.example.simhyobin.noti;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;


/**
 * Created by insec on 2018-07-10.
 */

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE USER_FRIENDS ( ");
        sb.append("USER_ID STRING PRIMARY KEY NOT NULL, ");
        sb.append("USER_NAME STRING NOT NULL, ");
        sb.append("USER_FAV INT,");
        sb.append("USER_GROUP INT,");
        sb.append("MSG_CNT INT ); ");

        db.execSQL(sb.toString());

        sb = new StringBuffer();
        sb.append("CREATE TABLE USER_GROUPINFO ( ");
        sb.append("GRP_NUM INT PRIMARY KEY AUTO_INCREMENT,");
        sb.append("GRP_NAME STRING NOT NULL ,");
        sb.append("GRP_CNT INT,");
        db.execSQL(sb.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }

    public ArrayList<String[]> ReadFriendsData(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER_FRIENDS", null);

        ArrayList<String[]> result = new ArrayList<String[]>();
        int i = 0;

        cursor.moveToFirst();

        while(!(cursor.isAfterLast())){
            String[] temp = new String[4];
            temp[0] = cursor.getString(0);
            temp[1] = cursor.getString(1);
            temp[2] = String.valueOf(cursor.getInt(2));
            temp[3] = String.valueOf(cursor.getInt(3));
            temp[4] = String.valueOf(cursor.getInt(4));
            result.add(temp);

            cursor.moveToNext();
        }
        cursor.close();

        return result;
    }
}
