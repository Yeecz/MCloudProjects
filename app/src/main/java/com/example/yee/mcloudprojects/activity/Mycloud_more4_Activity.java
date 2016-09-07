package com.example.yee.mcloudprojects.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.example.yee.mcloudprojects.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Mycloud_more4_Activity extends AppCompatActivity {

    @Bind(R.id.backbutton)
    ImageButton backbutton;

    //邀请加入我的云社
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycloud_more4_);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
    }

    @OnClick(R.id.backbutton)
    public void onClick() {
        finish();
    }
}
