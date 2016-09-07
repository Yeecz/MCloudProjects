package com.example.yee.mcloudprojects.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.utils.GetLoginUser;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class SubChatRongActivity extends AppCompatActivity implements RongIM.UserInfoProvider {


    GetLoginUser getLoginUser = new GetLoginUser(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("你的好友");
        setContentView(R.layout.activity_sub_chat_rong);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        RongIM.setUserInfoProvider(this,true);

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
