package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleCenter extends AppCompatActivity {


    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;
    String h;
    String i;
    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.text_in)
    TextView textIn;
    @Bind(R.id.more)
    TextView more;
    @Bind(R.id.cloudsea_top)
    LinearLayout cloudseaTop;
    @Bind(R.id.touxiang)
    CircleImageView touxiang;
    @Bind(R.id.hename)
    TextView hename;
    @Bind(R.id.seximageView)
    ImageView seximageView;
    @Bind(R.id.background)
    LinearLayout background;
    @Bind(R.id.intor)
    TextView intor;
    @Bind(R.id.TV1)
    TextView TV1;
    @Bind(R.id.zhanghao)
    TextView zhanghao;
    @Bind(R.id.L2)
    RelativeLayout L2;
    @Bind(R.id.TV2)
    TextView TV2;
    @Bind(R.id.TV3)
    TextView TV3;
    @Bind(R.id.jiantou2)
    ImageView jiantou2;
    @Bind(R.id.L3)
    RelativeLayout L3;
    @Bind(R.id.TV4)
    TextView TV4;
    @Bind(R.id.jiantou3)
    ImageView jiantou3;
    @Bind(R.id.L4)
    RelativeLayout L4;
    @Bind(R.id.bt1)
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getData();
        getData1();

//        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_single_center);
        ButterKnife.bind(this);

        backbutton = (ImageButton) findViewById(R.id.backbutton);
        more = (TextView) findViewById(R.id.more);
        L2 = (RelativeLayout) findViewById(R.id.L2);
        L3 = (RelativeLayout) findViewById(R.id.L3);
        L4 = (RelativeLayout) findViewById(R.id.L4);

        bt1 = (Button) findViewById(R.id.bt1);
        touxiang = (CircleImageView) findViewById(R.id.touxiang);
        zhanghao = (TextView) findViewById(R.id.zhanghao);

        //返回侧滑栏单击事件
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //跳转更多的页面
        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleCenter.this, SingleCenter_more1.class);
                startActivity(intent);
            }
        });

        //跳转至账户信息
        L2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleCenter.this, SingleCenter_more3.class);
                startActivity(intent);
            }
        });

        //个性签名跳转点击事件
        L3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleCenter.this, SingleCenter_more2.class);
                startActivity(intent);

            }
        });
        //发表动态跳转页面
        L4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleCenter.this, ReleaseActivity.class);
                startActivity(intent);

            }
        });


        //编辑资料点击事件
        bt1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleCenter.this, SingleCenter_more4.class);
                startActivity(intent);
            }
        });

        //头像点击事件跳转至SingleCenter_more5
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SingleCenter.this, SingleCenter_more5.class);
                startActivity(intent);
            }
        });


    }

    //从数据库中获取
    @Override
    protected void onStart() {
        getData();
        getData1();
        Picasso.with(this)
                .load("http://oc1souo4f.bkt.clouddn.com/" + c)
                .placeholder(R.drawable.avatar_default)
                .into(touxiang);
        hename.setText(b);
        TV3.setText(g);
        zhanghao.setText(h);
        super.onStart();
    }
public void success(){
    Picasso.with(this)
            .load("http://oc1souo4f.bkt.clouddn.com/" + c)
            .placeholder(R.drawable.avatar_default)
            .into(touxiang);
    hename.setText(b);
    TV3.setText(g);
    zhanghao.setText(h);
}

    SQLiteDatabase mSQLiteDatabase1;

    public void getData1() {
        mSQLiteDatabase1 = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE = "select * from account ";

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


    SQLiteDatabase mSQLiteDatabase;

    public void getData() {
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE = "select * from data ";

        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE, null);
        while (cursor1.moveToNext()) {
            a = cursor1.getString(1);//用户id
            b = cursor1.getString(2);//昵称
            c = cursor1.getString(3);//头像
            d = cursor1.getString(4);//性别
            e = cursor1.getString(5);//生日
            f = cursor1.getString(6);//手机
            g = cursor1.getString(8);//签名
            //账号
            // Toast.makeText(CloudSeaModuleActivity.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
        }
        cursor1.close();
        mSQLiteDatabase.close();
    }


}
