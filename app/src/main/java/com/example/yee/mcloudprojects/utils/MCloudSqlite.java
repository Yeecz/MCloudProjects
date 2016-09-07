package com.example.yee.mcloudprojects.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by caixuecheng on 2016/8/19.
 */
public class MCloudSqlite extends SQLiteOpenHelper {
    public MCloudSqlite(Context context, String name, int version) {
        super(context, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
