package com.example.yee.mcloudprojects.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.ChatRoomsAdapter;
import com.example.yee.mcloudprojects.entity.ChatRoom;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MyChatRoomActivity extends AppCompatActivity implements RongIM.UserInfoProvider {

    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    List<ChatRoom> chatRoomList = new ArrayList<>();
    ChatRoomsAdapter chatRoomsAdapter;
    MUserData mUserData;
    Gson gson = new Gson();
    GetLoginUser getLoginUser = new GetLoginUser(this);

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                chatRoomsAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chat_room);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("我的云社");
        RongIM.setUserInfoProvider(this,true);
        initViews();
        initData();
        initAdapter();
        initListener();
    }


    private void getData() {
        chatRoomList.clear();
        swipeRefreshLayout.setRefreshing(true);

        int userid = getLoginUser.getAccountId();
        Log.i("MyChatRoomActivity", "getData + userid:" + userid);
        String url = "http://10.50.7.49:8080/moyun/MChatRooms";
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(8000);
        params.addBodyParameter("MCloudCode","50");
        params.addBodyParameter("user_id",String.valueOf(userid));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                getChatRommsList(result);
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

    private void getChatRommsList(String result) {
        if (result.equals("no")){
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(MyChatRoomActivity.this, "您没有创建云社", Toast.LENGTH_SHORT).show();
        }else {
            Type type = new TypeToken<List<ChatRoom>>(){}.getType();
            List<ChatRoom> list = gson.fromJson(result,type);
            chatRoomList.addAll(list);
            handler.sendEmptyMessage(1);
        }
    }

    private void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getData();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = chatRoomList.get(i).getNumber();
                String name = chatRoomList.get(i).getName();
                if (RongIM.getInstance()!=null){
                    UserInfo userInfo = new UserInfo(String.valueOf(mUserData.getId()),mUserData.getName(), Uri.parse("http://oc1souo4f.bkt.clouddn.com/" + mUserData.getHeadportrait()));
                    RongIM.getInstance().setCurrentUserInfo(userInfo);
                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                    RongIM.getInstance().startConversation(MyChatRoomActivity.this, Conversation.ConversationType.CHATROOM, id, name);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChatRoom chatRoom = chatRoomList.get(i);
//                delChatRooms(chatRoom);
                showDelChatRoomDialog(chatRoom);
                return false;
            }
        });
    }

    private void showDelChatRoomDialog(final ChatRoom chatRoom) {
        //先new出一个监听器，设置好监听
        DialogInterface.OnClickListener dialogOnclicListener=new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case Dialog.BUTTON_POSITIVE:
//                        Toast.makeText(MyChatRoomActivity.this, "确认" + which, Toast.LENGTH_SHORT).show();
                        delChatRooms(chatRoom);
                        break;
                    case Dialog.BUTTON_NEGATIVE:
//                        Toast.makeText(MyChatRoomActivity.this, "取消" + which, Toast.LENGTH_SHORT).show();
                        break;
//                    case Dialog.BUTTON_NEUTRAL:
//                        Toast.makeText(MyChatRoomActivity.this, "忽略" + which, Toast.LENGTH_SHORT).show();
//                        break;
                }
            }
        };
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("是否确认删除?"); //设置内容
        builder.setIcon(R.mipmap.mcloud_logo_dark);//设置图标，图片id即可
        builder.setPositiveButton("确认",dialogOnclicListener);
        builder.setNegativeButton("取消", dialogOnclicListener);
//        builder.setNeutralButton("忽略", dialogOnclicListener);
        builder.create().show();
    }

    private void delChatRooms(ChatRoom chatRoom) {
        String url = "http://10.50.7.49:8080/moyun/MChatRooms";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("MCloudCode","40");
        List<String> chatRoomIds = new ArrayList<>();
        String roomid = chatRoom.getNumber();
        chatRoomIds.add(roomid);
        String delChatRoomIds = gson.toJson(chatRoomIds);
        params.addBodyParameter("MGroupIds",delChatRoomIds);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                delChatRoomsOK(result);
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

    private void delChatRoomsOK(String result) {
        if (result.equals("200")){
            Toast.makeText(MyChatRoomActivity.this, "删除完成", Toast.LENGTH_SHORT).show();
            getData();

        }else {
            Toast.makeText(MyChatRoomActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void initAdapter() {
        listView.setAdapter(chatRoomsAdapter);
    }

    private void initData() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        chatRoomsAdapter = new ChatRoomsAdapter(this,chatRoomList);
        mUserData = getLoginUser.getLoginMUser();
        getData();
    }



    private void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_chatroom_sr);
        listView = (ListView) findViewById(R.id.my_chatroom_lv);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public UserInfo getUserInfo(String s) {

        UserInfo userInfo = new UserInfo(String.valueOf(mUserData.getId()),mUserData.getName(), Uri.parse("http://oc1souo4f.bkt.clouddn.com/" + mUserData.getHeadportrait()));

        RongIM.getInstance().refreshUserInfoCache(userInfo);
        if (String.valueOf(mUserData.getId()).equals(s)){
            return userInfo;
        }

        Log.i("CloudSeaModuleActivity", "user:" + mUserData.toString());
        return null;
    }
}
