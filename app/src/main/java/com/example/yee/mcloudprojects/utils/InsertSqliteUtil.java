package com.example.yee.mcloudprojects.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yee.mcloudprojects.entity.Friend;
import com.example.yee.mcloudprojects.entity.MCloudMsg;

/**
 * Created by Yee on 2016/8/27.
 */
public class InsertSqliteUtil {

    Context context;
    SQLiteDatabase mSQLiteDatabase;

    public InsertSqliteUtil(Context context){
        Log.i("InsertSqliteUtil", "InsertSqliteUtil->1");
        this.context = context;
        mSQLiteDatabase = context.openOrCreateDatabase("moyun",context.MODE_PRIVATE, null);
        Log.i("InsertSqliteUtil", "InsertSqliteUtil->2");
    }


    public void insertFriend(Friend f){
        Log.i("InsertSqliteUtil", "1->insertFriend");
        String CREATE_TABLE3 = "insert into friend(user_id ,friend_id ,name ,headportrait ," +
                "remarks ,ver,sign)values(?,?,?,?,?,?,?)";
        mSQLiteDatabase.execSQL(CREATE_TABLE3,new Object[]{
                f.getUser_id(),f.getFriend_id(),f.getName(),
                f.getHeadportrait(),f.getRemarks(),f.getVer(),f.getSign()
        });
        Log.i("InsertSqliteUtil", "2->insertFriend");
        mSQLiteDatabase.close();
    }
}
