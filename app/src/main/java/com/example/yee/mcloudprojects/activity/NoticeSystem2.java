package com.example.yee.mcloudprojects.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.yee.mcloudprojects.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoticeSystem2 extends AppCompatActivity {

    @Bind(R.id.backbutton)
    ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_system2);
        ButterKnife.bind(this);
        backbutton= (ImageButton) findViewById(R.id.backbutton);


        //返回单击事件
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
