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
<<<<<<< Updated upstream
=======

        sb = new StringBuffer();
        sb.append("CREATE TABLE USER_GROUPINFO ( ");
        sb.append("GRP_NUM INTEGER PRIMARY KEY AUTOINCREMENT,");
        sb.append("GRP_NAME STRING NOT NULL ,");
        sb.append("GRP_MEMCNT INT,");
        sb.append("GRP_MSGCNT INT ); ");
        db.execSQL(sb.toString());


>>>>>>> Stashed changes
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    public ArrayList<String[]> ReadGroupData(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER_GROUPINFO", null);
        ArrayList<String[]> result = new ArrayList<String[]>();
        cursor.moveToFirst();

        while(!(cursor.isAfterLast())){
            String[] temp = new String[4];
            temp[0] = String.valueOf(cursor.getInt(0));
            temp[1] = cursor.getString(1);
            temp[2] = String.valueOf(cursor.getInt(2));
            temp[3] = String.valueOf(cursor.getInt(3));
            result.add(temp);

            cursor.moveToNext();
        }
        cursor.close();

        return result;
    }
    public ArrayList<String[]> ReadFriendsData(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER_FRIENDS", null);

        ArrayList<String[]> result = new ArrayList<String[]>();
        cursor.moveToFirst();

        while(!(cursor.isAfterLast())){
            String[] temp = new String[5];
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
    public void test_user(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"AAAAAA\", \"송인석\", 0, 0, 5);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"BBBBBB\", \"심효빈\", 0, 1, 15);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"CCCCCC\", \"최호수\", 1, 1, 2);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"DDDDDD\", \"양경석\", 1, 0, 6);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"EEEEEE\", \"양찬모\", 0, 2, 6);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"FFFFFF\", \"조우석\", 0, 2, 8);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"111111\", \"홍길동\", 0, 0, 8);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"222222\", \"아아아\", 0, 0, 8);");
        db.close();
    }
    public void test_group(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO USER_GROUPINFO VALUES(1, \"그룹1\", 2, 2);");
        db.execSQL("INSERT INTO USER_GROUPINFO VALUES(2, \"고인\", 2, 6);");
        db.close();
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
