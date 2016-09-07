package com.example.yee.mcloudprojects.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.AddFridentLVAdapter;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.MUserData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddfridentActivity extends AppCompatActivity {
    ListView listView;
    List<MUserData> list;
    MUserData mn;
    String muri;
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;

    @Bind(R.id.backbutton)
    ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfrident);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        MyApplication myApplication = (MyApplication) getApplication();
        muri = myApplication.getUrl();
        list = new ArrayList<>();
        mn = (MUserData) getIntent().getSerializableExtra("UserData");
        list.add(mn);
        getData();
        listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(new AddFridentLVAdapter(AddfridentActivity.this, list, muri, a));
    }

    SQLiteDatabase mSQLiteDatabase;

    public void getData() {
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE2 = "select * from data ";
        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE2, null);
        while (cursor1.moveToNext()) {
            a = cursor1.getString(1);//用户id
            b = cursor1.getString(2);//昵称
            c = cursor1.getString(3);//头像
            d = cursor1.getString(4);//性别
            e = cursor1.getString(5);//生日
            f = cursor1.getString(6);//手机
            g = cursor1.getString(8);//签名
            // Toast.makeText(CloudSeaModuleActivity.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
        }
    }
    @OnClick(R.id.backbutton)
    public void onClick() {
        finish();
    }
}
