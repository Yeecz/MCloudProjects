package com.example.yee.mcloudprojects.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.Mylinked;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//动态权限设置
public class Mycloud_more3_Activity extends AppCompatActivity {
    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.checkBox2)
    CheckBox checkBox2;
    SharedPreferences sp;
    Mylinked  mU;
    int frident_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycloud_more3_);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        sp=getSharedPreferences("user_Info", Context.MODE_APPEND);
        checkBox.setChecked(false);
        mU = (Mylinked) getIntent().getSerializableExtra("Mylinked");
        frident_id= mU.getFrident_id();
        initlistener();
        initdata();
    }

    private void initdata() {

        int i=0;
        if(sp.getBoolean("dongtaiqx",true)){
            checkBox.setChecked(true);
        };

        if(sp.getBoolean("dongtaiqx2",true)){
            checkBox2.setChecked(true);
            i=1;

        }else if(sp.getBoolean("dongtaiqx2",false)){
            checkBox2.setChecked(false);
            i=0;
        }


    }

    private void initlistener() {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = sp.edit();
                if(b){
                    Toast.makeText(Mycloud_more3_Activity.this,""+frident_id,Toast.LENGTH_LONG).show();
                    editor.putBoolean("dongtaiqx",true);
                    editor.commit();
                }else{
                    editor.putBoolean("dongtaiqx",false);
                    editor.commit();
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = sp.edit();
                if(b){
                    editor.putBoolean("dongtaiqx2",true);
                    editor.commit();
                }else{
                    editor.putBoolean("dongtaiqx2",false);
                    editor.commit();
                }
            }
        });
    }



    @OnClick(R.id.backbutton)
    public void onClick() {
        finish();
    }

}
