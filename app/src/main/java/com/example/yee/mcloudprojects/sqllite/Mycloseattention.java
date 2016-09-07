package com.example.yee.mcloudprojects.sqllite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zn on 2016/8/29.
 */
public class Mycloseattention extends SQLiteOpenHelper {
    private static String name = "myrecords";
    private static Integer version = 1;

    public Mycloseattention(Context context) {
        super(context, name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table deleteAttenion(id integer primary key autoincrement,record int(1))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
