package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.CacheActivity;
import com.example.yee.mcloudprojects.entity.Mylinked;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//修改备注
public class Mycloud_more2_Activity extends AppCompatActivity implements View.OnClickListener{
    EditText editText;
    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.sucess)
    Button sucess;
    String s;
    String muri;
    Mylinked  mU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycloud_more2_);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        editText = (EditText)findViewById(R.id.editText);
        MyApplication myApplication= (MyApplication)getApplication();
        muri=myApplication.getUrl();
        mU = (Mylinked) getIntent().getSerializableExtra("mylinked");

        initdata();
        if (!CacheActivity.activityList.contains(Mycloud_more2_Activity.this)) {
            CacheActivity.addActivity(Mycloud_more2_Activity.this);
        }
    }
    private void updata() {
        //用户id,好友id,用户名
        RequestParams params = new RequestParams(muri);
        params.addQueryStringParameter("user_id",""+mU.getUser_id());
        params.addQueryStringParameter("friend_id",""+ mU.getFrident_id());
        params.addQueryStringParameter("flag","80");
        params.addQueryStringParameter("remarks",""+editText.getText().toString());
        x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if("ok".equals(result)){
                        SQLiteDatabase mSQLiteDatabase = Mycloud_more2_Activity.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
                        String CREATE_TABLE = "update friend set remarks = ?,ver=ver+1 where user_id=? and friend_id = ?";
                        mSQLiteDatabase.execSQL(CREATE_TABLE,new Object[]{
                                editText.getText().toString(), mU.getUser_id(),mU.getFrident_id()
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
    private void initdata() {
        s=getIntent().getStringExtra("who");
        switch (s){
            case "1":
                String na= getIntent().getStringExtra("usenamw");
                editText.setHint(na);
                break;
            case "2":

                String beizh= mU.getBeizhu();
                if(beizh == null ||beizh.equals("")){
                    editText.setHint("无");
                }else{
                    editText.setHint(beizh);
                }
                break;
        }
    }


    @OnClick({R.id.backbutton, R.id.sucess})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backbutton:
                //此页面调用finish,回到上一页
                finish();
                break;
            case R.id.sucess:
                //-----吧editText提交到服务器。然后通知f1刷新
                //数据是使用Intent返回
                switch (s){
                    case "1":
                        Toast.makeText(this,"修改完成",Toast.LENGTH_SHORT).show();
                        updata();

                        TimerTask task1 = new TimerTask(){
                            public void run(){
                                //execute the task
                                CacheActivity.finishActivity();
                            }
                        };
                        Timer timer1 = new Timer();
                        timer1.schedule(task1, 1000);
                        break;
                    case "2":
                        Toast.makeText(this,"修改完成",Toast.LENGTH_SHORT).show();
                        updata();
                        Intent intent=new Intent();
                        setResult(2,intent);
                        editText.clearFocus();
                        TimerTask task = new TimerTask(){
                            public void run(){
                                //execute the task
                                finish();
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, 1000);
                        break;
                }

        }
    }


}