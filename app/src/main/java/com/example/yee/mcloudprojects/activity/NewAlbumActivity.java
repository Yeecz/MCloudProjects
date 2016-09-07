package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewAlbumActivity extends AppCompatActivity {
    String mUrl;
    public static final String TAG = " NewAlbumActivity";
    @Bind(R.id.cancel)
    Button cancel;
    @Bind(R.id.conservation)
    Button conservation;
    @Bind(R.id.album_name_n)
    EditText albumNameN;
    @Bind(R.id.album_describe)
    EditText albumDescribe;
    private boolean isConservation = false;
    private RadioGroup group;
    private TextView textView;
    private String result;

    MyApplication myApplication;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_album);
        ButterKnife.bind(this);
     // newAlbum();
        getSupportActionBar().hide();
        myApplication = (MyApplication) getApplication();
        url = myApplication.getUrl();
//        group = (RadioGroup) findViewById(R.id.authority_group);
//        textView = (TextView) findViewById(R.id.authority_result);


    }

//    private void initListener() {
//        // 单选按钮组监听事件
//        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i) {
//                    case R.id.authority_all_people:
//                        Log.e(TAG, "RadioGroup-->>allpeople");
//                        textView.setText("所有人可见");
//                        break;
//                    case R.id.authority_friends:
//                        Log.e(TAG, "RadioGroup-->>friends");
//                        textView.setText("陌云好友可见");
//                        break;
//                    case R.id.authority_myself:
//                        Log.e(TAG, "RadioGroup-->>myself");
//                        textView.setText("仅自己可见");
//                        break;
//                    default:
//                        Log.e(TAG, "RadioGroup-->>allpeople");
//                        textView.setText("所有人可见");
//                        break;
//                }
//
//            }
//        });
//
//
//    }


    @OnClick({R.id.cancel, R.id.conservation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                jumpB(view);
                break;
            case R.id.conservation:
                String albumName = albumNameN.getText().toString();
                if ("".equals(albumName)) {
                    Toast.makeText(NewAlbumActivity.this, "请输入相册的名称", Toast.LENGTH_SHORT).show();
                } else {
                  newAlbum();
                    Toast.makeText(NewAlbumActivity.this, "创建相册成功", Toast.LENGTH_SHORT).show();
              }

                break;
        }
    }


    private void jumpB(View view) {
        Intent intent = new Intent(NewAlbumActivity.this, TextActivity.class);
        startActivity(intent);
    }
    //传到本地服务端
    public void newAlbum() {
        String user_id = "0";
        SQLiteDatabase mSQLiteDatabase = NewAlbumActivity.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE = "select * from data";
        Cursor cur = mSQLiteDatabase.rawQuery(CREATE_TABLE, null);
        while (cur.moveToNext()) {
            user_id = cur.getString(1);
        }

        RequestParams params = new RequestParams("http://10.50.7.49:8080/moyun/dispose");
        params.addBodyParameter("flag", "15");
        params.addBodyParameter("user_id", user_id);
        params.addBodyParameter("name", albumNameN.getText().toString());
        params.addBodyParameter("describe", albumDescribe.getText().toString());

        //设置请求类型，并开始访问服务器，获取服务器返回结果
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if ("ok".equals(result)) {
                    SQLiteDatabase mSQLiteDatabase = NewAlbumActivity.this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
                    String CREATE_TABLE = "update album set name=?,describe=?";
                    mSQLiteDatabase.execSQL(CREATE_TABLE, new Object[]{
                            albumNameN.getText().toString(),
                            albumDescribe.getText().toString()
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


