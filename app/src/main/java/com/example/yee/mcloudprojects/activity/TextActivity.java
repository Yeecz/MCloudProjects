package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.CloudSeaModuleActivity;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.MyRecyclerAdapter;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.Albums;
import com.example.yee.mcloudprojects.entity.OnRecyclerViewItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TextActivity extends AppCompatActivity {
    // private static final String MOCK_URL = "http://lorempixel.com/800/400/nightlife/";
    @Bind(R.id.back)
    Button back;
    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.new_album)
    Button newAlbum;
    @Bind(R.id.r_title)
    RelativeLayout rTitle;
    @Bind(R.id.list)
    RecyclerView list;
//    String user_id;
    SQLiteDatabase mSQLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        ButterKnife.bind(this);
        getData();

        getSupportActionBar().hide();
    }

    Handler handle = new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if(msg.what==1){
                setUI();
            }
        }
    };
    public void setUI(){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        final MyRecyclerAdapter adapter;
        recyclerView.setAdapter(adapter = new MyRecyclerAdapter(las, R.layout.text_album));
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //获取intent
        Intent intent = getIntent();

        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener<Albums>() {
            @Override
            public void onItemClick(View view, Albums mAlbums) {
                // adapter.remove(mAlbums);
             jumpS(view,mAlbums);
            }
        });
    }

    private void jumpS(View view, Albums mAlbums) {
        //显式跳转：跳转到jumpb
        Intent intent = new Intent(TextActivity.this, ShowAlbumActivity.class);
        int clickAlbumId=mAlbums.getId();
       // String  clickAlbumName=mAlbums.getName().toString();
        Bundle bundle=new Bundle();
       // bundle.putString("albumName",clickAlbumName);
        bundle.putInt("albumId",clickAlbumId);
//        Toast.makeText(TextActivity.this, "TextActivity输出Id:"+clickAlbumId, Toast.LENGTH_SHORT).show();
        intent.putExtras(bundle);
        startActivity(intent);
    }

//    private List<Albums> createMockList() {
//        List<Albums> mAlbums = new ArrayList<>();
//        for (int i = 0; i < mAlbums.size(); i++) {
//            mAlbums.add(new Albums());
//        }
//        return mAlbums;
//    }


    @OnClick({R.id.back, R.id.new_album, R.id.list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                jumpM(view);
                break;
            case R.id.new_album:
                jumpN(view);
                break;
            case R.id.list:
                break;
        }
    }

    private void jumpN(View view) {
        //显式跳转：跳转到jumpb
        Intent intent = new Intent(TextActivity.this, NewAlbumActivity.class);
        startActivity(intent);
    }

    private void jumpM(View view) {
        //显式跳转：跳转到jumpb
        Intent intent = new Intent(TextActivity.this, CloudSeaModuleActivity.class);
        startActivity(intent);
    }


    List<Albums> las = new ArrayList();
    public void getData() {
        las.clear();
        String user_id = "0";
        SQLiteDatabase mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE = "select * from data";
        Cursor cur = mSQLiteDatabase.rawQuery(CREATE_TABLE, null);
        while (cur.moveToNext()) {
            user_id = cur.getString(1);
        }


        MyApplication myApplication = new MyApplication();
        String url = myApplication.getUrl();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("flag", "16");
        params.addBodyParameter("user_id", user_id);


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
       //         Toast.makeText(TextActivity.this, "" + result, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                Type type = new TypeToken<List<Albums>>(){}.getType();
                las = gson.fromJson(result, type);
//
            //    Toast.makeText(TextActivity.this, ""+las, Toast.LENGTH_SHORT).show();
                handle.sendEmptyMessageDelayed(1,0);

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
