package com.example.yee.mcloudprojects.utils;

import android.content.Context;
import android.util.Log;


import com.example.yee.mcloudprojects.entity.RongToken;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class GetRongToken{

    RongToken rongToken;

    private void initRongData() {


        //刷新用户信息
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {

                return null;
            }
        }, true);

    }


//
//    private void initListener() {
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                client2Server();
//                btn.setClickable(false);
//            }
//        });
//        onechat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (RongIM.getInstance() != null){
//                    RongIM.getInstance().startPrivateChat(GetTokenActivity.this,"941014","Yee");
//                }
//            }
//        });
//    }




}
