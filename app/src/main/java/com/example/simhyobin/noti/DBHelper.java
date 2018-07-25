package com.example.simhyobin.noti;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Iterator;


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
        sb2.append("TITLE STRING NOT NULL,");
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


        sb = new StringBuffer();
        sb.append("CREATE TABLE USER_GROUPINFO ( ");
        sb.append("GRP_NUM INTEGER PRIMARY KEY AUTOINCREMENT,");
        sb.append("GRP_NAME STRING NOT NULL ,");
        sb.append("GRP_MEMCNT INT,");
        sb.append("GRP_MSGCNT INT ); ");
        db.execSQL(sb.toString());

        sb = new StringBuffer();
        sb.append("CREATE TABLE USER_GROUPMEM (");
        sb.append("USER_ID STRING, ");
        sb.append("GRP_NUM INTEGER );");
        db.execSQL(sb.toString());

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
        int i = 0;

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
            String[] temp = new String[7];
            temp[0] = cursor.getString(0);
            temp[1] = cursor.getString(1);
            temp[2] = cursor.getString(2);
            temp[3] = cursor.getString(3);
            temp[4] = String.valueOf(cursor.getInt(4));
            temp[5] = String.valueOf(cursor.getInt(5));
            temp[6] = cursor.getString(6);
            result.add(temp);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }
    public void rm_user(String user_id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT USER_GROUP FROM USER_FRIENDS WHERE USER_ID=?",new String[]{user_id});
        cursor.moveToFirst();
        String num_usergroup = cursor.getString(0);

        if(!(num_usergroup.equals("0"))){
            cursor = db.rawQuery("SELECT GRP_MEMCNT FROM USER_GROUPINFO WHERE GRP_NUM=?", new String[]{num_usergroup});
            cursor.moveToFirst();
            int memcnt = Integer.parseInt(cursor.getString(0));
            if(memcnt-1 <= 0){
                db.execSQL("DELETE FROM USER_GROUPINFO WHERE GRP_NUM=?",new String[]{num_usergroup});
            }else{
                db.execSQL("UPDATE USER_GROUPINFO SET MEMCNT=? WHERE GRP_NUM=?", new String[]{String.valueOf(memcnt-1), num_usergroup});
            }
        }

        db.execSQL("DELETE FROM USER_FRIENDS WHERE USER_ID="+user_id);
        db.close();
    }
    public void rm_message(String hash){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM RECEIVE_MESSAGE WHERE UNIQUE_HASH='"+hash+"'");
        db.close();
    }
    public void fav_user(String user_id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT USER_FAV FROM USER_FRIENDS WHERE USER_ID=?", new String[]{user_id});
        cursor.moveToFirst();
        String user_fav = cursor.getString(0);

        if(user_fav.equals("0")){
            db.execSQL("UPDATE USER_FRIENDS SET USER_FAV=1 WHERE USER_ID=?",new String[]{user_id});
        }else{
            db.execSQL("UPDATE USER_FRIENDS SET USER_FAV=0 WHERE USER_ID=?",new String[]{user_id});
        }
        db.close();
    }
    public void group_user(ArrayList<String[]> data, String grp_name){

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT GRP_NUM FROM USER_GROUPINFO ORDER BY GRP_NUM DESC", null);
        int grp_num;

        try{
            cursor.moveToFirst();
            grp_num = Integer.parseInt(cursor.getString(0))+1;
        }catch (CursorIndexOutOfBoundsException e){
            grp_num = 1;
        }

        int mem_cnt = data.size();

        db.execSQL("INSERT INTO USER_GROUPINFO VALUES(?,  ? , ?, 0);", new String[]{String.valueOf(grp_num), grp_name, String.valueOf(mem_cnt)});

        Iterator<String[]> iterator = data.iterator();

        while(iterator.hasNext()){
            String[] tempdata = iterator.next();
            // 0 : name 1 : ID
            db.execSQL("INSERT INTO USER_GROUPMEM VALUES(?, ?);", new String[]{tempdata[1], String.valueOf(grp_num)});
        }
        db.close();
    }
    public ArrayList<String[]> getUserListfromGRPNUM(String grp_num){
        ArrayList<String[]> result = new ArrayList<String[]>();

        String[] temp = new String[2];

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT UGM.USER_ID, UF.USER_NAME FROM USER_FRIENDS AS UF, USER_GROUPINFO AS UGI, USER_GROUPMEM AS UGM WHERE UGM.USER_ID = UF.USER_ID AND UGM.GRP_NUM = UGI.GRP_NUM AND UGM.GRP_NUM = ?", new String[]{grp_num});
        cursor.moveToFirst();

        while(!(cursor.isAfterLast())){
            temp = new String[]{cursor.getString(0), cursor.getString(1)};
            result.add(temp);
            cursor.moveToNext();
        }

        return result;
    }
    public String getGRPNAME(String grp_num){
        SQLiteDatabase db = getReadableDatabase();
        String grp_name = new String();

        Cursor cursor = db.rawQuery("SELECT GRP_NAME FROM USER_GROUPINFO WHERE GRP_NUM=?", new String[]{grp_num});
        cursor.moveToFirst();
        grp_name = cursor.getString(0);

        return grp_name;
    }
    public void rm_group(String grp_num){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("DELETE FROM USER_GROUPINFO WHERE GRP_NUM=?", new String[]{grp_num});
        db.execSQL("DELETE FROM USER_GROUPMEM WHERE GRP_NUM=?", new String[]{grp_num});
        db.close();

    }
    public void modify_group(ArrayList<String[]> data, String grp_num, String grp_name, int idx){
        SQLiteDatabase db = getWritableDatabase();

        if(idx == 1){
         db.execSQL("UPDATE USER_GROUPINFO SET GRP_NAME=? WHERE GRP_NUM=?",new String[]{String.valueOf(grp_name), String.valueOf(grp_num)});
        }

        int mem_cnt = data.size();

        db.execSQL("DELETE FROM USER_GROUPMEM WHERE GRP_NUM=?", new String[]{grp_num});

        db.execSQL("UPDATE USER_GROUPINFO SET GRP_MEMCNT=? WHERE GRP_NUM=?;", new String[]{String.valueOf(mem_cnt), String.valueOf(grp_num)});

        Iterator<String[]> iterator = data.iterator();

        while(iterator.hasNext()){
            String[] tempdata = iterator.next();
            // 0 : name 1 : ID
            db.execSQL("INSERT INTO USER_GROUPMEM VALUES(?, ?);", new String[]{tempdata[1], String.valueOf(grp_num)});
        }
        db.close();
    }
    public void modify_group(ArrayList<String[]> data, String grp_name, String grp_num){

    }
    public void test_user(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"AAAAAA\", \"송인석\", 0,  5);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"BBBBBB\", \"심효빈\", 0,  15);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"CCCCCC\", \"최호수\", 1,  2);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"DDDDDD\", \"양경석\", 1,  6);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"EEEEEE\", \"양찬모\", 0,  6);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"FFFFFF\", \"조우석\", 0,  8);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"111111\", \"홍길동\", 0,  8);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"222222\", \"아아아\", 0,  8);");
        db.execSQL("INSERT INTO USER_FRIENDS VALUES(\"444444\", \"박세희\", 0,  13);");
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
        db.execSQL("INSERT INTO RECEIVE_MESSAGE VALUES('a23bcd','김철수d','blahblahblahblahblahblahblahblahblahblahblahblahblah','똥쌀시간','1532076593','1532076500','hashman2');");
        db.execSQL("INSERT INTO RECEIVE_MESSAGE VALUES('a23bcdfdd','송인석','blahblahblahblahblahblahblahblahblahblahblahblahblah','똥쌀시간','1372339830','1372339120','hashm22an2');");
        db.execSQL("INSERT INTO RECEIVE_MESSAGE VALUES('a23bcdfdd','심효빈','blahblahblahblahblahblahblahblahblahblahblahblahblah','똥쌀시간','1372339850','1372339990','hashm22an22');");
        db.close();
    }
}
