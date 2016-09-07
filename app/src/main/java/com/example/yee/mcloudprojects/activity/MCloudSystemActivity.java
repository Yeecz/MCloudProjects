package com.example.yee.mcloudprojects.activity;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.MCloudMsgAdapter;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.MCloudMsg;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.example.yee.mcloudprojects.utils.GetResponseMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MCloudSystemActivity extends AppCompatActivity {

    Toolbar topbar;
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    MCloudMsgAdapter mCloudMsgAdapter;
    List<MCloudMsg> mCloudMsgList = new ArrayList<>();
    MyApplication myApplication;

    private boolean canRun=true;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2){
                Bundle bundle = msg.getData();
                String result = bundle.getString("result");
                Log.i("MCloudSystemActivity", "result: " + result);
                if (result.equals("[]")){
//                    getData();
                    if (canRun){
                        getSysMessage(handler);
                    }
                }else {
                    canRun = false;
                    showListViewMessage(result);
                    for (int i=0;i<mCloudMsgList.size();i++){
                        reMsg(i);
                    }

                }

                swipeRefreshLayout.setRefreshing(false);
                Log.i("MCloudSystemActivity", "X");
            }
        }
    };

    private void showMsg() {
//        getData();
        if (canRun){
            getSysMessage(handler);
        }
        mCloudMsgAdapter.notifyDataSetChanged();

    }


    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcloud_system_message);
        myApplication = (MyApplication) getApplication();
        initViews();
        initToolBar();
//        initData();
        initAdapter();
        initListener();
        initAutoRefresh();
//        getData();
        if (canRun){
            getSysMessage(handler);
        }


    }

    public void getSysMessage(Handler handler){
        //开始请求系统信息
        GetLoginUser getLoginUser = new GetLoginUser(this);
        GetResponseMessage getResponseMessage = new GetResponseMessage(handler,String.valueOf(getLoginUser.getLoginMUser().getId()),"2");
        Log.i("myapplication", "myapplication->message: " + String.valueOf(getLoginUser.getLoginMUser().getId()));
        Thread getMessage = new Thread(getResponseMessage);
        getMessage.start();
        Log.i("myapplication", "X");


    }

    private void initData() {
//        myApplication = (MyApplication) getApplication();
//        myApplication.setHandler(handler);
    }

    private void initAutoRefresh() {
        swipeRefreshLayout.setRefreshing(true);
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                canRun = true;
                showMsg();
            }
        });
    }

    private void initAdapter() {
        mCloudMsgAdapter = new MCloudMsgAdapter(this,mCloudMsgList);
        listView.setAdapter(mCloudMsgAdapter);
    }

    private void initToolBar() {

        topbar.setTitle("提醒");
//        topbar.setSubtitle("消息");
        setSupportActionBar(topbar);

//        topbar.setNavigationIcon(R.drawable.c2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        topbar = (Toolbar) findViewById(R.id.system_msg_toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mcloudsys_sr);
//                swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        listView = (ListView) findViewById(R.id.mcloudsys_listview);
    }

    private void getData(){
//        myApplication.getSysMessage(handler);
    }

    public void showListViewMessage(String msg){
        Log.i("MCloudSystemActivity", "showNotificationMessage:1");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Type type = new TypeToken<List<MCloudMsg>>(){}.getType();
        List<MCloudMsg> mCloudMessages = gson.fromJson(msg, type);
        mCloudMsgList.addAll(mCloudMessages);
        showMsg();
        Log.i("MCloudSystemActivity", "showNotificationMessage:2");
    }

    private void reMsg(final int i) {
        String url = myApplication.getUrl();
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(10000);
        params.addBodyParameter("flag","10");
        params.addBodyParameter("res",String.valueOf(mCloudMsgList.get(i).getRes()));
        params.addBodyParameter("user_id",String.valueOf(mCloudMsgList.get(i).getUser_id()));
        params.addBodyParameter("text1",mCloudMsgList.get(i).getText1());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("ok")){
                    Log.i("MCloudSystemActivity", "result:ok");
                }else {
                    Log.i("MCloudSystemActivity", "result:error");
//                    reMsg(mCloudMsgList.size());
                }
                if (mCloudMsgList.get(i).getRes()==3){
                    showRequest(mCloudMsgList.get(i));
                }else if (mCloudMsgList.get(i).getRes()==4){
                    showRespone(mCloudMsgList.get(i));
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

    public void showRequest(MCloudMsg mCloudMsg){
        Log.i("MCloudSystemActivity", "MCloudMsg: " + mCloudMsg.toString());
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MCloudSystemActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        //点击推送进入，没有数据，需要刷新


        Notification notification = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("好友申请")
                .setContentText(mCloudMsg.getName() + ",请求加你为好友。")
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(1,notification);
    }

    public void showRespone(MCloudMsg mCloudMsg){
        Log.i("MCloudSystemActivity", "MCloudMsg: " + mCloudMsg.toString());
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MCloudSystemActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);



        //点击推送进入，没有数据，需要刷新


        Notification notification = new Notification.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("好友申请")
                .setContentText(mCloudMsg.getName() + ",同意加你为好友。")
                .setContentIntent(pendingIntent)
                .build();
        notificationManager.notify(2,notification);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

