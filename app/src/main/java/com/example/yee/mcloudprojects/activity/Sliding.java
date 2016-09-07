package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Sliding extends AppCompatActivity {


    @Bind(R.id.mcloud_tx)
    CircleImageView mcloudTx;
    @Bind(R.id.mcloud_name)
    TextView mcloudName;
    @Bind(R.id.mcloud_chat)
    TextView mcloudChat;
    @Bind(R.id.mcloud_people_num_album)
    TextView mcloudPeopleNumAlbum;
    @Bind(R.id.mcloud_people_num_follower)
    TextView mcloudPeopleNumFollower;
    @Bind(R.id.mcloud_people_num_fans)
    TextView mcloudPeopleNumFans;
    @Bind(R.id.mcloud_people_num)
    LinearLayout mcloudPeopleNum;
    @Bind(R.id.mcloud_people_album)
    TextView mcloudPeopleAlbum;
    @Bind(R.id.mcloud_people_follower)
    TextView mcloudPeopleFollower;
    @Bind(R.id.mcloud_people_fans)
    TextView mcloudPeopleFans;
    @Bind(R.id.mcloud_people_type)
    LinearLayout mcloudPeopleType;
    @Bind(R.id.mcloud_people)
    LinearLayout mcloudPeople;
    @Bind(R.id.cloud_sliding)
    LinearLayout cloudSliding;
    @Bind(R.id.IV1)
    ImageView IV1;
    @Bind(R.id.R1)
    RelativeLayout R1;
    @Bind(R.id.IV2)
    ImageView IV2;
    @Bind(R.id.R2)
    RelativeLayout R2;
    @Bind(R.id.IV3)
    ImageView IV3;
    @Bind(R.id.R3)
    RelativeLayout R3;
    @Bind(R.id.IV4)
    ImageView IV4;
    @Bind(R.id.R4)
    RelativeLayout R4;
    @Bind(R.id.rl_sliding)
    LinearLayout rlSliding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sliding);
        ButterKnife.bind(this);

        addlisten();
        R1= (RelativeLayout) findViewById(R.id.R1);
        R2= (RelativeLayout) findViewById(R.id.R2);
        R3= (RelativeLayout) findViewById(R.id.R3);
        R4= (RelativeLayout) findViewById(R.id.R4);

    }

    private void addlisten() {
        R1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Sliding.this,SingleCenter.class);
                startActivity(intent);
            }
        });
    }



}
