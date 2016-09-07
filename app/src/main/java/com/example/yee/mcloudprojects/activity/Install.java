package com.example.yee.mcloudprojects.activity;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.utils.ScreenLight;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Install extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    String h;
    String i;

    //关于夜间模式
    Switch tb1;
    ScreenLight screenLight;
    int s;
    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.text_in)
    TextView textIn;
    @Bind(R.id.text_i2)
    TextView textI2;
    @Bind(R.id.cloudsea_top)
    LinearLayout cloudseaTop;
    @Bind(R.id.IV1)
    ImageView IV1;
    @Bind(R.id.TV4)
    TextView TV4;
    @Bind(R.id.R1)
    RelativeLayout R1;
    @Bind(R.id.IV2)
    ImageView IV2;
    @Bind(R.id.TV2)
    TextView TV2;
    @Bind(R.id.TV3)
    TextView TV3;
    @Bind(R.id.R2)
    RelativeLayout R2;
    @Bind(R.id.IV3)
    ImageView IV3;
    @Bind(R.id.void_notification)
    Switch voidNotification;
    @Bind(R.id.R3)
    RelativeLayout R3;
    @Bind(R.id.IV6)
    ImageView IV6;
    @Bind(R.id.voice_notification)
    Switch voiceNotification;
    @Bind(R.id.R7)
    RelativeLayout R7;
    @Bind(R.id.IV7)
    ImageView IV7;
    @Bind(R.id.shock_notification)
    Switch shockNotification;
    @Bind(R.id.R6)
    RelativeLayout R6;
    @Bind(R.id.IV5)
    ImageView IV5;
    @Bind(R.id.R5)
    RelativeLayout R5;
    @Bind(R.id.scrollView)
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
        ButterKnife.bind(this);

        //关于夜间模式
        tb1 = (Switch) findViewById(R.id.tb1);
        tb1.setOnCheckedChangeListener(this);


        backbutton = (ImageButton) findViewById(R.id.backbutton);

        TV3 = (TextView) findViewById(R.id.TV3);
        TV4 = (TextView) findViewById(R.id.TV4);
        R2= (RelativeLayout) findViewById(R.id.R2);


        //加





        //点击修改绑定号码
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Install.this,BangDingYanZheng.class);
                startActivity(intent);
            }
        });
        //设置页面返回侧滑页面
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //关于夜间模式
    private void brightnessMaxoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = s;
        getWindow().setAttributes(lp);
    }

    public void brightnessMax(int paramInt) {
        Window localWindow = getWindow();
        WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
        int f = paramInt / 255;//255
        localLayoutParams.screenBrightness = f;
        localWindow.setAttributes(localLayoutParams);
        screenLight.saveScreenBrightness(f);
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
        //              screenLight.setScreenBrightness(0);
        //    screenLight.saveScreenBrightness(100);
        if (on) {
            screenLight = new ScreenLight();
            screenLight.getScreenMode();
            s = screenLight.getScreenBrightness();
            screenLight.setScreenMode(0);
            brightnessMax(1);


        } else {

            brightnessMaxoff();
        }


    }
//加
    public void onCheckedChanged( boolean on) {
        shockNotification= (Switch) findViewById(R.id.shock_notification);
        shockNotification.setOnCheckedChangeListener(this);

        if (on){
            Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{0,1000}, -1);
        }

    }

//    public void success(){
//        getData1();
//        TV3.setText(i);
//
//    }

    //从本地数据库获取手机号
    @Override
    protected void onStart() {
        getData1();
        TV3.setText(i);
        TV4.setText(h);
        super.onStart();
    }


    SQLiteDatabase mSQLiteDatabase1;

    public void getData1() {
        mSQLiteDatabase1 = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE = "select * from account ";

        Cursor cursor1 = mSQLiteDatabase1.rawQuery(CREATE_TABLE, null);
        while (cursor1.moveToNext()) {

            i = cursor1.getString(3);//绑定手机
            h = cursor1.getString(2);//账号
            //账号
            // Toast.makeText(CloudSeaModuleActivity.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
        }
        cursor1.close();
        mSQLiteDatabase1.close();
    }
}
