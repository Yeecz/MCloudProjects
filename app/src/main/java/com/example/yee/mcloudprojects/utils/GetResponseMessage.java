package com.example.yee.mcloudprojects.utils;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.yee.mcloudprojects.application.MyApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by caixuecheng on 2016/8/25.
 */
public class GetResponseMessage implements Runnable {
    Handler handler;
    String user_id;
    String res;
    String text1;
    String text2;


    public GetResponseMessage() {
    }

    public GetResponseMessage(Handler handler, String user_id, String res){
        this.handler = handler;
        this.user_id = user_id;
        this.res = res;
    }

    public GetResponseMessage(Handler handler,String user_id,String res,String text1,String text2){
        this.handler = handler;
        this.user_id = user_id;
        this.res = res;
        this.text1 = text1;
        this.text2 = text2;
    }
    @Override
    public void run() {
        Log.i("aaaa"," getres ==== run");
        request();
    }

    public void request(){

        RequestParams params = new RequestParams("http://10.50.7.49:8080/moyun/dispose");
        params.addBodyParameter("flag", "4");
        params.addBodyParameter("res", res);
        params.addBodyParameter("user_id",user_id);
        params.addBodyParameter("text1",text1);
        params.addBodyParameter("text2",text2);

        //设置请求类型，并开始访问服务器，获取服务器返回结果
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                sendHandler(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Log.e("aaaa", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i("aaaa", "onCancelled");
            }

            @Override
            public void onFinished() {
                Log.i("aaaa", "onFinished");
            }
        });
    }

    public void sendHandler(String result){
        Message msg = new Message();
        msg.what=2;
        Bundle bundle = new Bundle();
        bundle.putString("result",result);
        msg.setData(bundle);
        handler.sendMessageDelayed(msg,1000);
        Log.i("aaaa","获得的消息"+result);
    }
}
