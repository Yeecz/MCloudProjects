package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.yee.mcloudprojects.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutSoftware extends AppCompatActivity {

    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.R1)
    RelativeLayout R1;
    @Bind(R.id.R2)
    RelativeLayout R2;
    @Bind(R.id.R3)
    RelativeLayout R3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_software);
        ButterKnife.bind(this);

        backbutton = (ImageButton) findViewById(R.id.backbutton);
        R1= (RelativeLayout) findViewById(R.id.R1);
        R2= (RelativeLayout) findViewById(R.id.R2);
        R3= (RelativeLayout) findViewById(R.id.R3);

        //系统通知点击事件
        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AboutSoftware.this,NoticeSystem.class);
                startActivity(intent);
            }
        });

     //功能介绍单击事件
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AboutSoftware.this,Function.class);
                startActivity(intent);
            }
        });

        //关于软件返回点击事件
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        //关于欢迎页跳转
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AboutSoftware.this,WelcomePage.class);
                startActivity(intent);
            }
        });
    }

}
