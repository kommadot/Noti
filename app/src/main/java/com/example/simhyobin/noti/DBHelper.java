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
        StringBuffer sb2 = new StringBuffer();
        sb2.append("CREATE TABLE RECEIVE_MESSAGE(");
        sb2.append("USER_ID STRING NOT NULL,");
        sb2.append("USER_NAME STRING NOT NULL,");
        sb2.append("CONTENT STRING NOT NULL,");
        sb2.append("RECEIVE_DATE DATETIME,");
        sb2.append("NOTI_DATE DATETIME,");
        sb2.append("UNIQUE_HASH STRING PRIMARY KEY);");
        db.execSQL(sb2.toString());
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE USER_FRIENDS ( ");
        sb.append("USER_ID STRING PRIMARY KEY NOT NULL, ");
        sb.append("USER_NAME STRING NOT NULL, ");
        sb.append("USER_FAV INT,");
        sb.append("MSG_CNT INT ); ");

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
            result.add(temp);

            cursor.moveToNext();
        }
        cursor.close();

        return result;
    }
    public ArrayList<String[]> ReadReceiveMessage(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RECEIVE_MESSAGE", null);
        ArrayList<String[]> result = new ArrayList<String[]>();
        int i= 0;
        cursor.moveToFirst();

        while(!(cursor.isAfterLast())){
            String[] temp = new String[6];
            temp[0] = cursor.getString(0);
            temp[1] = cursor.getString(1);
            temp[2] = cursor.getString(2);
            temp[3] = cursor.getString(3);
            temp[4] = cursor.getString(4);
            temp[5] = cursor.getString(5);
            result.add(temp);

            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }
    public void insert() {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO RECEIVE_MESSAGE VALUES('a23bcd','김철수d','저는 행복합니다 님덜아아아아아앙아앙아아앙앙','2018-06-06 06:34:23','2018-07-13 08:12:33','hashman2');");
        db.close();
    }
}
