package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.CacheActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SingleCenter_more2 extends AppCompatActivity {

    @Bind(R.id.backbutton)
    ImageButton backbutton;
    @Bind(R.id.text_in)
    TextView textIn;
    @Bind(R.id.sucess)
    TextView sucess;
    @Bind(R.id.cloudsea_top)
    LinearLayout cloudseaTop;
    @Bind(R.id.editText)
    EditText editText;

    MyApplication myApplication;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_center_more2);
        ButterKnife.bind(this);
        myApplication = (MyApplication) getApplication();
        url = myApplication.getUrl();

        backbutton = (ImageButton) findViewById(R.id.backbutton);
        sucess = (TextView) findViewById(R.id.sucess);


        //个性签名详情跳转至个人中心
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //发布成功点击事件
        sucess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                signs();
                TimerTask task1 = new TimerTask(){
                    public void run(){
                        //execute the task
                        finish();
                    }
                };
                Timer timer1 = new Timer();
                timer1.schedule(task1, 1000);

            }
        });


    }

    public  void signs(){
        editText = (EditText) findViewById(R.id.editText);
        Toast.makeText(SingleCenter_more2.this,"发布成功",Toast.LENGTH_SHORT).show();
        tijiao(editText.getText().toString().trim());
//        Intent intent =new Intent(SingleCenter_more2.this,SingleCenter.class);
//        startActivity(intent);
    }

    //传到本地服务端
    public void tijiao(String sign){
        String user_id="0";
        SQLiteDatabase mSQLiteDatabase = SingleCenter_more2.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE="select * from data";
        Cursor cur = mSQLiteDatabase.rawQuery(CREATE_TABLE,null);
        while(cur.moveToNext()){
            user_id = cur.getString(1);
        }
        RequestParams params = new RequestParams("http://10.50.7.49:8080/moyun/dispose");
        params.addBodyParameter("sign",editText.getText().toString());
        params.addBodyParameter("flag", "82");
        params.addBodyParameter("user_id",user_id);
        //设置请求类型，并开始访问服务器，获取服务器返回结果
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if("ok".equals(result)){
                    SQLiteDatabase mSQLiteDatabase = SingleCenter_more2.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
                    String CREATE_TABLE="update data set sign=?";
                    mSQLiteDatabase.execSQL(CREATE_TABLE,new Object[]{
                          editText.getText().toString()
                    });
                    mSQLiteDatabase.close();
                }

            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Log.e("zzzzzzz", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("zzzzzz", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.i("zzzzzz", "onFinished");
            }
        });
    }


}



