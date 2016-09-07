package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.SearchChatRoomAdapter;
import com.example.yee.mcloudprojects.entity.ChatRoom;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchChatRoomActivity extends AppCompatActivity {

    @Bind(R.id.searchchat_back)
    ImageButton searchchatBack;
    @Bind(R.id.searchchat_conkey)
    EditText searchchatConkey;
    @Bind(R.id.searchchat_search)
    Button searchchatSearch;
    @Bind(R.id.searchchat_lv)
    ListView searchchatLv;

    Gson gson = new Gson();
    List<ChatRoom> chatRooms = new ArrayList<>();
    SearchChatRoomAdapter searchChatRoomAdapter;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){

                showChatRoom();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chat_room);
        ButterKnife.bind(this);
        initViews();
        initDatas();
        initAdapter();
        getAutoDatas();
    }

    private void showChatRoom(){
        searchChatRoomAdapter.notifyDataSetChanged();
    }

    private void initAdapter() {
        searchchatLv.setAdapter(searchChatRoomAdapter);
    }

    private void initDatas() {

        searchChatRoomAdapter = new SearchChatRoomAdapter(this,chatRooms);
    }

    private void getAutoDatas() {
        Intent intent =getIntent();
        String num = intent.getStringExtra("chatroomsearch");
        searchchatConkey.setText(num);
        Log.i("SearchChatRoomActivity", "getAutoDatas->num:" + num);
        if (num.length()>=4 && num.length()<=6){
            searchChatRooms(num);
        }else {
            Toast.makeText(SearchChatRoomActivity.this, "输入错误", Toast.LENGTH_SHORT).show();
        }

    }

    private void initViews() {

    }

    @OnClick({R.id.searchchat_back, R.id.searchchat_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchchat_back:
                finish();
                break;
            case R.id.searchchat_search:
                chatRooms.clear();
                String number = searchchatConkey.getText().toString();
                if (number.length()>=4 && number.length() <=6){
                    searchChatRooms(number);
                }else {
                    Toast.makeText(SearchChatRoomActivity.this, "输入错误", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void searchChatRooms(String number) {
        List<String> list = new ArrayList<>();
        list.add(number);
        String ids = gson.toJson(list);
        String url = "http://10.50.7.49:8080/moyun/MChatRooms";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("MCloudCode","30");
        params.addBodyParameter("MGroupIds",ids);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                isChatRoom(result);
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

    private void isChatRoom(String result) {
        if (result.equals("no")){
            Toast.makeText(SearchChatRoomActivity.this, "没有找到", Toast.LENGTH_SHORT).show();
        }else {
            ChatRoom chatRoom = gson.fromJson(result,ChatRoom.class);
            Log.i("SearchChatRoomActivity", "isChatRoom->:" + chatRoom.getName() + "," + chatRoom.getNumber());
            chatRooms.add(chatRoom);
            handler.sendEmptyMessage(1);
        }
    }
}
