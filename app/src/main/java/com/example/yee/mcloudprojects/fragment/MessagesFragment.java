package com.example.yee.mcloudprojects.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.CloudSeaModuleActivity;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.activity.MCloudSystemActivity;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.squareup.picasso.Picasso;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;


public class MessagesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View contentView;
    CircleImageView topuser_tx;
    ImageView top_msg;
    GetLoginUser getLoginUser;
    String b;
    ImageView iv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoginUser = new GetLoginUser(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.cloudrun_messages_fragment, container, false);
        iv=   (ImageView)contentView.findViewById(R.id.cloudrun_user_tx);
        initTopView();
        initMessageView();
        initListener();
        getData();
        inituserdata();
        return contentView;
    }

    private void initListener() {
        top_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MCloudSystemActivity.class);
                startActivity(intent);
            }
        });

        topuser_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloudSeaModuleActivity cloudSeaModuleActivity = (CloudSeaModuleActivity) getActivity();
                cloudSeaModuleActivity.changeSliding();
            }
        });
    }

    private void initTopView() {
        topuser_tx = (CircleImageView) contentView.findViewById(R.id.cloudrun_user_tx);
        top_msg = (ImageView) contentView.findViewById(R.id.cloudrun_top_msg);
        GetLoginUser getLoginUser = new GetLoginUser(getActivity());
        Picasso.with(getActivity())
                .load("http://oc1souo4f.bkt.clouddn.com/" + getLoginUser.getLoginMUser().getHeadportrait())
                .placeholder(R.drawable.avatar_default)
                .into(topuser_tx);
    }

    private void initMessageView() {
        ConversationListFragment fragment = (ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.cloudrun_messages);

        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//原本没false
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();

        fragment.setUri(uri);
//启动会话列表界面

    }

    @Override
    public void onResume() {
        MUserData mUserData = getLoginUser.getLoginMUser();
        UserInfo userInfo = new UserInfo(String.valueOf(mUserData.getId()),mUserData.getName(), Uri.parse("http://oc1souo4f.bkt.clouddn.com/" + mUserData.getHeadportrait()));

        RongIM.getInstance().refreshUserInfoCache(userInfo);
        super.onResume();
    }
    SQLiteDatabase mSQLiteDatabase;

    public void getData() {
        mSQLiteDatabase = getActivity().openOrCreateDatabase("moyun", getActivity().MODE_PRIVATE, null);
        String CREATE_TABLE2 = "select * from data ";
        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE2, null);
        while (cursor1.moveToNext()) {

            b = cursor1.getString(3);//昵称

            cursor1.close();
            mSQLiteDatabase.close();
        }

    }

    private void inituserdata() {
        Picasso.with(getActivity())
                .load("http://oc1souo4f.bkt.clouddn.com/" +b)
                .placeholder(R.drawable.avatar_default)
                .into(iv);

    }

}
