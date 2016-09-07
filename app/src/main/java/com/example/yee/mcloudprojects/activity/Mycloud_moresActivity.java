package com.example.yee.mcloudprojects.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.CacheActivity;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.entity.Mylinked;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
//个人信息主界面
public class Mycloud_moresActivity extends AppCompatActivity {

    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.xiangxi)
    LinearLayout xiangxi;
    @Bind(R.id.beizhu)
    LinearLayout beizhu;
    @Bind(R.id.dongtaishezhi)
    LinearLayout dongtaishezhi;
    @Bind(R.id.yaoqing)
    LinearLayout yaoqing;
    @Bind(R.id.shanch)
    Button shanch;
    TextView tv_beizhu;
    String  name1;
    MUserData  mUserData;
    TextView  nametext1;
    String v1;
    String v2;
    String muri;
    Mylinked mu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycloud_mores);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        MyApplication myApplication= (MyApplication)getApplication();
        muri=myApplication.getUrl();
        initdata();
        if (!CacheActivity.activityList.contains(Mycloud_moresActivity.this)) {
            CacheActivity.addActivity(Mycloud_moresActivity.this);
        }

    }

    private void initdata() {
        mu = (Mylinked) getIntent().getSerializableExtra("Mylink");
        mUserData = (MUserData) getIntent().getSerializableExtra("Mylinked2");
        v1=getIntent().getStringExtra("Mylinked1");
        tv_beizhu= (TextView) findViewById(R.id.nowbeizhu);//当前备注的textview
        nametext1=(TextView) findViewById(R.id.nametext1);
        nametext1.setText(mUserData.getName());
        //备注名

        tv_beizhu.setText(v1);
    }

    @OnClick({R.id.backbutton, R.id.xiangxi, R.id.beizhu, R.id.dongtaishezhi, R.id.yaoqing, R.id.shanch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton:
                finish();
                break;
            case R.id.xiangxi:
                Intent intent=new Intent(this,Mycloud_more1_Activity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("Mylinked", mUserData);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.beizhu:
                Intent intent2=new Intent(this,Mycloud_more2_Activity.class);
                Bundle bundle2=new Bundle();
                bundle2.putString("usenamw", v1);
                bundle2.putString("who","1");
                bundle2.putSerializable("mylinked", mu);
                intent2.putExtras(bundle2);
                startActivity(intent2);

                break;
            case R.id.dongtaishezhi:
//                Intent intent3=new Intent(this,Mycloud_more3_Activity.class);
//                Bundle b=new Bundle();
//                b.putSerializable("Mylinked", mu);
//                intent3.putExtras(b);
//                startActivity(intent3);
                break;
            case R.id.yaoqing:
//               Intent intent4=new Intent(this,Mycloud_more4_Activity.class);
//               startActivity(intent4);
                break;
            case R.id.shanch:
                Dialog dialog = new AlertDialog.Builder(Mycloud_moresActivity.this)
                        .setIcon(R.drawable.warning).setTitle("是否删除好友？")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                delete();
                                TimerTask task = new TimerTask(){
                                    public void run(){
                                        //execute the task
                                        CacheActivity.finishSingleActivityByClass(Mycloud_moresActivity.class);
                                        CacheActivity.finishSingleActivityByClass(User_data_Activity.class);
                                    }
                                };
                                Timer timer = new Timer();
                                timer.schedule(task, 1000);
                            }

                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).create();
                dialog.show();

                break;
        }
    }
    private void delete() {
        //用户id,好友id,用户名
        RequestParams params = new RequestParams(muri);
        params.addQueryStringParameter("user_id",""+mu.getUser_id());
        params.addQueryStringParameter("friend_id",""+ mu.getFrident_id());
        params.addQueryStringParameter("flag","90");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if("ok".equals(result)){

                    SQLiteDatabase mSQLiteDatabase = Mycloud_moresActivity.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
                    String CREATE_TABLE = "delete from  friend where user_id=? and friend_id = ?";
                    mSQLiteDatabase.execSQL(CREATE_TABLE,new Object[]{
                           mu.getUser_id(),mu.getFrident_id()
                   });
                    mSQLiteDatabase.close();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
          @Override
            public void onFinished() {
            }
        });
    }

}
