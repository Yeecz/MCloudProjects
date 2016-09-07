package com.example.yee.mcloudprojects.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.utils.GetLoginUser;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;


public class ChatRongActivity extends AppCompatActivity implements RongIM.UserInfoProvider {


    GetLoginUser getLoginUser = new GetLoginUser(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat_rong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RongIM.setUserInfoProvider(this,true);
//        getSupportActionBar().setTitle("聊天");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;

            default:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public UserInfo getUserInfo(String s) {
        MUserData mUserData = getLoginUser.getLoginMUser();
        UserInfo userInfo = new UserInfo(String.valueOf(mUserData.getId()),mUserData.getName(), Uri.parse("http://oc1souo4f.bkt.clouddn.com/" + mUserData.getHeadportrait()));

        RongIM.getInstance().refreshUserInfoCache(userInfo);
        if (String.valueOf(mUserData.getId()).equals(s)){
            return userInfo;
        }

        Log.i("CloudSeaModuleActivity", "user:" + mUserData.toString());
        return null;
    }
}
