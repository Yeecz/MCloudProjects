package com.example.yee.mcloudprojects.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.yee.mcloudprojects.R;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Function extends AppCompatActivity {

    @Bind(R.id.backbutton)
    ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        ButterKnife.bind(this);
        backbutton= (ImageButton) findViewById(R.id.backbutton);


        //返回事件
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
