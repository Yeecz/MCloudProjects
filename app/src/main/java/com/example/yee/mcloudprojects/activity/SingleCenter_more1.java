package com.example.yee.mcloudprojects.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleCenter_more1 extends AppCompatActivity {


    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.text_in)
    TextView textIn;
    @Bind(R.id.text_i2)
    TextView textI2;
    @Bind(R.id.cloudsea_top)
    LinearLayout cloudseaTop;
    @Bind(R.id.user_text)
    TextView userText;
    @Bind(R.id.sex_text)
    TextView sexText;
    @Bind(R.id.brithday_text)
    TextView brithdayText;
    @Bind(R.id.phone_text)
    TextView phoneText;
    @Bind(R.id.words_text)
    TextView wordsText;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;
    String url;
    MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        getData();
        setContentView(R.layout.activity_single_center_more1);
        ButterKnife.bind(this);
        myApplication = (MyApplication) getApplication();
        url = myApplication.getUrl();
        userText= (TextView) findViewById(R.id.user_text);
        sexText= (TextView) findViewById(R.id.sex_text);
        brithdayText= (TextView) findViewById(R.id.brithday_text);
        phoneText= (TextView) findViewById(R.id.phone_text);
        wordsText= (TextView) findViewById(R.id.words_text);


        backbutton = (ImageButton) findViewById(R.id.backbutton);


        //个人信息跳转至singleCenter
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }

    @Override
    protected void onStart() {
        userText.setText(b);
        if("1".equals(d)){
            sexText.setText("男");
        }else{
            sexText.setText("女");
        }

        brithdayText.setText(e);
        phoneText.setText(f);
        wordsText.setText(g);

        super.onStart();
    }

    SQLiteDatabase mSQLiteDatabase;

    public void getData() {
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE="select * from data ";

        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE, null);
        while (cursor1.moveToNext()) {
            a = cursor1.getString(1);//用户id
            b = cursor1.getString(2);//昵称
            c = cursor1.getString(3);//头像
            d = cursor1.getString(4);//性别
            e = cursor1.getString(5);//生日
            f = cursor1.getString(6);//手机
            g = cursor1.getString(8);//签名
            // Toast.makeText(SingleCenter_more1.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
        }
        cursor1.close();
        mSQLiteDatabase.close();
    }


}