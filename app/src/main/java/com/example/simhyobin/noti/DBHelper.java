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
        sb2.append("RECEIVE_DATE int,");
        sb2.append("NOTI_DATE int,");
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
            temp[3] = String.valueOf(cursor.getInt(3));
            temp[4] = String.valueOf(cursor.getInt(4));
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
        db.execSQL("INSERT INTO RECEIVE_MESSAGE VALUES('a23bcd','김철수d','blahblahblahblahblahblahblahblahblahblahblahblahblah','1372339860','1372339860','hashman2');");
        db.execSQL("INSERT INTO RECEIVE_MESSAGE VALUES('a23bcdfdd','송인석','blahblahblahblahblahblahblahblahblahblahblahblahblah','1372339830','1372339120','hashm22an2');");
        db.execSQL("INSERT INTO RECEIVE_MESSAGE VALUES('a23bcdfdd','심효빈','blahblahblahblahblahblahblahblahblahblahblahblahblah','1372339850','1372339990','hashm22an22');");
        db.close();
    }
}
