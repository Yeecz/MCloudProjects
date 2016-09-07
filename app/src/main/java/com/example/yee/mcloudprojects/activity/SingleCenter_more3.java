package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleCenter_more3 extends AppCompatActivity {

       String h;
       String i;

    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.text_in)
    TextView textIn;
    @Bind(R.id.text_i2)
    TextView textI2;
    @Bind(R.id.cloudsea_top)
    LinearLayout cloudseaTop;
    @Bind(R.id.TV2)
    TextView TV2;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.TV1)
    TextView TV1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_center_more3);
        ButterKnife.bind(this);

        backbutton = (ImageButton) findViewById(R.id.backbutton);
        button = (Button) findViewById(R.id.button);
        TV2= (TextView) findViewById(R.id.TV2);


        //返回按键点击事件返回至SingleCenter
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
              finish();
            }
        });

        //更换按钮点击事件

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData1();
                Intent intent=new Intent(SingleCenter_more3.this,BangDingYanZheng.class);
                startActivity(intent);

            }
        });

    }

    //从数据库中获取
    @Override
    protected void onStart() {
        getData1();
      TV2.setText(i);
        super.onStart();
    }
    SQLiteDatabase mSQLiteDatabase1;

    public void getData1() {
        mSQLiteDatabase1 = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE="select * from account ";

        Cursor cursor1 = mSQLiteDatabase1.rawQuery(CREATE_TABLE, null);
        while (cursor1.moveToNext()) {
            h = cursor1.getString(2);//账号
            i = cursor1.getString(3);//绑定手机

            //账号
            // Toast.makeText(CloudSeaModuleActivity.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
        }
        cursor1.close();
        mSQLiteDatabase1.close();
    }

}
