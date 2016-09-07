package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.Friend;
import com.example.yee.mcloudprojects.entity.MCloudMsg;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.example.yee.mcloudprojects.utils.InsertSqliteUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Yee on 2016/8/26.
 */
public class MCloudMsgAdapter extends BaseAdapter {

    Context context;
    List<MCloudMsg> mCloudMsgs;
    LayoutInflater inflater;
    MyApplication myApplication;

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public MCloudMsgAdapter(Context context, List<MCloudMsg> mCloudMsgs) {
        this.context = context;
        this.mCloudMsgs = mCloudMsgs;
        inflater = LayoutInflater.from(context);
        myApplication = (MyApplication) context.getApplicationContext();
    }

    @Override
    public int getCount() {
        return (mCloudMsgs == null) ? 0 : mCloudMsgs.size();
    }

    @Override
    public Object getItem(int i) {
        return (mCloudMsgs == null) ? null : mCloudMsgs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (mCloudMsgs == null) ? 0 : i;
    }



    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        MCloudMsg mCloudMsg = mCloudMsgs.get(i);
        if (view == null) {
            view = inflater.inflate(R.layout.mcloudmsg_item, null);
            viewHolder = new ViewHolder();
            viewHolder.username = (TextView) view.findViewById(R.id.mcloudmsg_username);
            viewHolder.msg = (TextView) view.findViewById(R.id.mcloudmsg_msg);
            viewHolder.avator = (CircleImageView) view.findViewById(R.id.mcloudmsg_avator);
            viewHolder.msg_agree = (Button) view.findViewById(R.id.mcloudmsg_agree);
//            viewHolder.msg_refuse = (Button) view.findViewById(R.id.mcloudmsg_refuse);
            viewHolder.msg_agree.setTag(i);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        if (mCloudMsg.getRes() == 3){
            viewHolder.username.setText(mCloudMsg.getName());
            Picasso.with(context)
                    .load("http://oc1souo4f.bkt.clouddn.com/" + mCloudMsg.getHead())
                    .placeholder(R.drawable.avatar_default)
                    .into(viewHolder.avator);

        }else if (mCloudMsg.getRes() == 4){
            viewHolder.username.setText(mCloudMsg.getUser_id() + "");
            viewHolder.msg.setText("对方同意添加你为好友");
            Picasso.with(context)
                    .load("http://oc1souo4f.bkt.clouddn.com/" + mCloudMsg.getHead())
                    .placeholder(R.drawable.avatar_default)
                    .into(viewHolder.avator);
            changeAgreeBtn(viewHolder.msg_agree);
            int id = mCloudMsg.getUser_id();
            String text1 = mCloudMsg.getText1();
            complete2Server(id,text1);
        }

        viewHolder.msg_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgAgree(i);
                changeAgreeBtn(viewHolder.msg_agree);
            }
        });
//        viewHolder.msg_refuse.setOnClickListener(this);
//        viewHolder.msg_refuse.setOnClickListener();

        return view;
    }


    private void complete2Server(int id,String text1) {
        String url = myApplication.getUrl();
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(10000);
        params.addBodyParameter("flag","68");
        params.addBodyParameter("res","4");
        params.addBodyParameter("user_id",String.valueOf(id));
        params.addBodyParameter("friend_id",text1);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                agreeBtn(result);
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

    public void msgAgree(int position) {
        int id = mCloudMsgs.get(position).getUser_id();
        String text1 = mCloudMsgs.get(position).getText1();
        String url = myApplication.getUrl();

        Log.i("MCloudMsgAdapter", "msgAgree:" + id);
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(10000);
        params.addBodyParameter("flag", "4");
        params.addBodyParameter("res", "4");
        params.addBodyParameter("user_id", String.valueOf(id));
        params.addBodyParameter("text1", text1);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("MCloudMsgAdapter", "result:" + result);
                agreeBtn(result);
//                changeAgreeBtn();
                Log.i("MCloudMsgAdapter", "MCloudMsgAdapter->onSuccess");
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

    public void agreeBtn(String s) {
        Log.i("MCloudMsgAdapter", "agreeBtn");
        Friend friend = gson.fromJson(s, Friend.class);
        Log.i("MCloudMsgAdapter", "friend");
        InsertSqliteUtil insertSqliteUtil = new InsertSqliteUtil(context);
        Log.i("MCloudMsgAdapter", "insertSqliteUtil");
        insertSqliteUtil.insertFriend(friend);
        Log.i("MCloudMsgAdapter", "insertFriend");


    }

    public void changeAgreeBtn(Button view){
//        viewHolder.msg_refuse.setVisibility(View.GONE);
        view.setText("已同意");
        view.setClickable(false);
    }



    class ViewHolder {
        TextView username,msg;
        CircleImageView avator;
        Button msg_agree, msg_refuse;

    }
}
