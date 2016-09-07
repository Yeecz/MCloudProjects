package com.example.yee.mcloudprojects.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.MUserData;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
//详细信息
public class Mycloud_more1_Activity extends AppCompatActivity {
    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.sex_text)
    TextView sexText;
    @Bind(R.id.age_text)
    TextView ageText;
    @Bind(R.id.brithday_text)
    TextView brithdayText;
    @Bind(R.id.work_text)
    TextView workText;
    @Bind(R.id.phone_text)
    TextView phoneText;
    @Bind(R.id.mail_text)
    TextView mailText;
    @Bind(R.id.words_text)
    TextView wordsText;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    MUserData mUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycloud_more1_);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
       mUserData = (MUserData) getIntent().getSerializableExtra("Mylinked");
        initdata();
    }

    private void initdata() {
        if(mUserData.getSex()==0){sexText.setText( "女");}else{
            sexText.setText( "男");
        }
        ageText.setText("保密");
        brithdayText.setText(mUserData.getBirthday()+"");
        workText.setText("无");
        phoneText.setText(mUserData.getPhone()+"");
        mailText.setText("无");
        wordsText.setText(mUserData.getSign());
    }

    @OnClick(R.id.backbutton)
    public void onClick() {
        finish();
    }
}
