package com.example.yee.mcloudprojects.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.yee.mcloudprojects.entity.MUserData;
import com.google.gson.Gson;

/**
 * Created by Yee on 2016/8/22.
 */
public class GetLoginUser {

    Context context;


    public GetLoginUser(Context context){
        this.context = context;
    }



    public MUserData getLoginMUser(){
        SQLiteDatabase mSQLiteDatabase = context.openOrCreateDatabase("moyun",Context.MODE_PRIVATE,null);
        String CREATE_TABLE = "select * from data";
        Cursor cursor =  mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        MUserData mUserData = new MUserData();
        while (cursor.moveToNext()){
            mUserData.setId(cursor.getInt(1));
            mUserData.setName(cursor.getString(2));
            mUserData.setHeadportrait(cursor.getString(3));
        }

        return mUserData;
    }

    public String getLoginUser(){
        SQLiteDatabase mSQLiteDatabase = context.openOrCreateDatabase("moyun",Context.MODE_PRIVATE,null);
        String CREATE_TABLE = "select * from data";
        Cursor cursor =  mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        MUserData mUserData = new MUserData();
        while (cursor.moveToNext()){
            mUserData.setId(cursor.getInt(1));
            mUserData.setName(cursor.getString(2));
            mUserData.setHeadportrait(cursor.getString(3));
        }
        Gson gson = new Gson();
        String result = gson.toJson(mUserData);
        return result;
    }

    public int getAccountId(){
        SQLiteDatabase mSQLiteDatabase = context.openOrCreateDatabase("moyun",Context.MODE_PRIVATE,null);
        String CREATE_TABLE = "select user_id from account ";
        Cursor cursor =  mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        int id = 0;
        while (cursor.moveToNext()){
            id = cursor.getInt(0);
        }
        return id;
    }

    public String getAccountNumber(){
        SQLiteDatabase mSQLiteDatabase = context.openOrCreateDatabase("moyun",Context.MODE_PRIVATE,null);
        String CREATE_TABLE = "select number from account ";
        Cursor cursor =  mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        String number = null;
        while (cursor.moveToNext()){
            number = cursor.getString(0);
        }
        return number;
    }

    public String getDataName(){
        SQLiteDatabase mSQLiteDatabase = context.openOrCreateDatabase("moyun",Context.MODE_PRIVATE,null);
        String CREATE_TABLE = "select name from data ";
        Cursor cursor =  mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        String name = null;
        while (cursor.moveToNext()){
            name = cursor.getString(0);
        }
        return name;
    }

    public String getDataHeadPortrait(){
        SQLiteDatabase mSQLiteDatabase = context.openOrCreateDatabase("moyun",Context.MODE_PRIVATE,null);
        String CREATE_TABLE = "select headportrait from data ";
        Cursor cursor =  mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        String headportrait = null;
        while (cursor.moveToNext()){
            headportrait = cursor.getString(0);
        }
        return headportrait;
    }

}
