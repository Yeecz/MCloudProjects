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

public class NoticeSystem extends AppCompatActivity {

    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.R1)
    RelativeLayout R1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_system);
        ButterKnife.bind(this);

        backbutton= (ImageButton) findViewById(R.id.backbutton);
        R1= (RelativeLayout) findViewById(R.id.R1);

        //返回事件
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //R1点击事件
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NoticeSystem.this,NoticeSystem2.class);
                startActivity(intent);
            }
        });
    }
}
