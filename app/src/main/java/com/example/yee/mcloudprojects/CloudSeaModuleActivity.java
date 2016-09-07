package com.example.yee.mcloudprojects;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.yee.mcloudprojects.activity.AboutSoftware;
import com.example.yee.mcloudprojects.activity.Install;
import com.example.yee.mcloudprojects.activity.Login;
import com.example.yee.mcloudprojects.activity.SingleCenter;
import com.example.yee.mcloudprojects.activity.SingleCenter_more5;
import com.example.yee.mcloudprojects.activity.TextActivity;
import com.example.yee.mcloudprojects.entity.MUserData;
import com.example.yee.mcloudprojects.fragment.CloudSeaFragment;
import com.example.yee.mcloudprojects.fragment.Fridents_list;
import com.example.yee.mcloudprojects.fragment.MessagesFragment;
import com.example.yee.mcloudprojects.fragment.ShowImagesFragment;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class CloudSeaModuleActivity extends AppCompatActivity implements View.OnClickListener,RongIM.UserInfoProvider {

    //    FloatingActionButton fab;           //发布功能键
    ImageView user_tx;                  //顶部导航头像侧滑
    DrawerLayout cloud_homepage;        //主体，滑动框架
    LinearLayout cloud_sliding;       //滑动框架
    RadioButton cloudsea;               //底部导航云海
    RadioButton cloudrun;               //底部导航云迹
    RadioButton cloudhome;              //底部导航云乡
    //    ViewPager vp_showbigpic;
    RelativeLayout rl_content;
    RadioGroup footergroup;
    List<Fragment> fragmentList = new ArrayList<Fragment>();//
    List<RadioButton> buttonList = new ArrayList<RadioButton>();

    int oldOperFlag;//前一次操作的是哪个按钮
    int currentOperFlag;//现在操作的是哪个按钮
    //    PhotoView mPhotoView;
//    ViewPager mViewPager;
//    Info mInfo;
    FrameLayout replaceshowimages;
    LinearLayout   L6;
    RelativeLayout R5;
    RelativeLayout R1;                    //侧滑栏所有
    RelativeLayout R2;                   //关于软件
    RelativeLayout R3;                  //设置
    RelativeLayout R4;                  //退出
//    TextView mcloud_people_follower; //关注
//    TextView mcloud_people_fans;     //粉丝
    ImageView mcloud_tx;        //头像
    TextView mcloud_chat;

    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    String g;


    private boolean isAppExit = false;

    TextView mcloudName;
    GetLoginUser getLoginUser = new GetLoginUser(this);


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                isAppExit=false;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloudsea_module);
        RongIM.setUserInfoProvider(this,true);

        initView();
        initData();
      //  getData();
       // inituserdata();
        initListener();

    }

    @Override
    protected void onStart() {
        getData();
        inituserdata();
        super.onStart();
    }

    private void inituserdata() {

//        ImageOptions imageOptions = new ImageOptions.Builder()
//                .setLoadingDrawableId(R.drawable.img_loading_bg)
//                .setCircular(true)
//                .build();
//        x.image().bind(mcloud_tx,"http://oc1souo4f.bkt.clouddn.com/"+c,imageOptions);
        Picasso.with(this)
                .load("http://oc1souo4f.bkt.clouddn.com/" + c)
                .placeholder(R.drawable.avatar_default)
                .into(mcloud_tx);
        mcloudName.setText(b);
        mcloud_chat.setText(g);
    }


    private void initData() {
        fragmentList.add(new CloudSeaFragment());
        fragmentList.add(new MessagesFragment());
        fragmentList.add(new Fridents_list());
        buttonList.add((RadioButton) findViewById(R.id.footer_cloudsea));
        buttonList.add((RadioButton) findViewById(R.id.footer_cloudrun));
        buttonList.add((RadioButton) findViewById(R.id.footer_cloudhome));
        getSupportFragmentManager().beginTransaction().add(R.id.cloudsea_content, fragmentList.get(0))
                .commit();
        cloudsea.setChecked(true);


    }

    private void initListener() {


        cloudsea.setOnClickListener(this);
        cloudrun.setOnClickListener(this);
        cloudhome.setOnClickListener(this);

        mcloud_tx.setOnClickListener(this);
        R1.setOnClickListener(this);
        R2.setOnClickListener(this);
        R3.setOnClickListener(this);
//        R4.setOnClickListener(this);
//        mcloud_people_follower.setOnClickListener(this);
//        mcloud_people_fans.setOnClickListener(this);
        R5.setOnClickListener(this);


    }

    private void initView() {
        replaceshowimages = (FrameLayout) findViewById(R.id.cloudsea_replace_showimagefragment);
        user_tx = (ImageView) findViewById(R.id.user_tx);
        cloud_homepage = (DrawerLayout) findViewById(R.id.cloud_homepage);
        cloud_sliding = (LinearLayout) findViewById(R.id.cloud_sliding);
        cloudsea = (RadioButton) findViewById(R.id.footer_cloudsea);
        cloudrun = (RadioButton) findViewById(R.id.footer_cloudrun);
        cloudhome = (RadioButton) findViewById(R.id.footer_cloudhome);
        rl_content = (RelativeLayout) findViewById(R.id.cloudsea_content);
        footergroup = (RadioGroup) findViewById(R.id.cloudsea_footergroup);

        mcloudName = (TextView) findViewById(R.id.mcloud_name);
        mcloud_chat= (TextView) findViewById(R.id.mcloud_chat);
//        mViewPager = (ViewPager) findViewById(R.id.cloudsea_showViewPager);
//        mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));


        R1 = (RelativeLayout) findViewById(R.id.R1);
        R2 = (RelativeLayout) findViewById(R.id.R2);
        R3 = (RelativeLayout) findViewById(R.id.R3);
        R4 = (RelativeLayout) findViewById(R.id.R4);
        R5= (RelativeLayout) findViewById(R.id.R5);
        L6= (LinearLayout) findViewById(R.id.L6);

        L6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CloudSeaModuleActivity.this,SingleCenter.class);
                startActivity(intent);
            }
        });

        R5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CloudSeaModuleActivity.this, TextActivity.class);
                startActivity(intent);
            }
        });
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog();

            }
        });

//    mcloud_people_follower = (TextView) findViewById(R.id.mcloud_people_follower);
        mcloud_tx = (CircleImageView) findViewById(R.id.mcloud_tx);
//        mcloud_people_fans = (TextView) findViewById(R.id.mcloud_people_fans);

    }



    protected void dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CloudSeaModuleActivity.this);

        builder.setMessage("确认退出吗？");

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

                Intent intent=new Intent(CloudSeaModuleActivity.this, Login.class);
                startActivity(intent);

            }

        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }

        });

        builder.create().show();

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == android.view.KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {


        }
        return false;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.footer_cloudsea:
                //底部导航云海
                currentOperFlag = 0;//第一个按钮
                break;
            case R.id.footer_cloudrun:
                //底部导航云迹
                currentOperFlag = 1;
                break;
            case R.id.footer_cloudhome:
                currentOperFlag = 2;
                //底部导航云乡
                break;

            case R.id.R1:
                Intent intent = new Intent(CloudSeaModuleActivity.this, SingleCenter.class);
                startActivity(intent);
                break;
            case R.id.R2:
                Intent intent2 = new Intent(CloudSeaModuleActivity.this, AboutSoftware.class);
                startActivity(intent2);
                break;
            case R.id.R3:
                Intent intent3 = new Intent(CloudSeaModuleActivity.this, Install.class);
                startActivity(intent3);
                break;
            case R.id.R5:
                //底部导航云迹
                Intent intent1=new Intent(CloudSeaModuleActivity.this, TextActivity.class);
                startActivity(intent1);
                break;
//            case R.id.R4:
////                Intent intent4 = new Intent(CloudSeaModuleActivity.this, LogOff.class);
////                startActivity(intent4);
//                break;

//            case R.id.mcloud_people_follower:
//                Intent intent5 = new Intent(CloudSeaModuleActivity.this, MyConcern.class);
//                startActivity(intent5);
//                break;

            case R.id.mcloud_tx:
                Intent intent6 = new Intent(CloudSeaModuleActivity.this, SingleCenter_more5.class);
                startActivity(intent6);
                break;

//            case R.id.mcloud_people_fans:
////                Intent intent7 = new Intent(CloudSeaModuleActivity.this, MyFansActivity.class);
////                startActivity(intent7);
//                break;
        }
        switchFragment(fragmentList.get(oldOperFlag), fragmentList.get(currentOperFlag));
        buttonList.get(oldOperFlag).setSelected(false);//上一次点击的按钮，取消select

        buttonList.get(currentOperFlag).setSelected(true);//当前点击的按钮，设置select

        Log.i("MainActivity", "oldOperFlag:" + oldOperFlag + ",currentOperFlag:" + currentOperFlag);
        oldOperFlag = currentOperFlag;
    }

    //隐藏当前显示的fragment,新的fragment:1)没有添加过，添加；2）添加过，显示
    public void switchFragment(Fragment oldFragment, Fragment newFragment) {

        //判断是否是同一个fragment
        if (oldFragment != newFragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //隐藏oldfragment
            fragmentTransaction.hide(oldFragment);

            if (!newFragment.isAdded()) {
                //没有添加过，添加
                fragmentTransaction.add(R.id.cloudsea_content, newFragment).commit();

            } else {
                fragmentTransaction.show(newFragment).commit();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void changeSliding() {
        cloud_homepage.openDrawer(cloud_sliding);
    }


    public void hideBigImageView() {
        replaceshowimages.setVisibility(View.GONE);
        rl_content.setVisibility(View.VISIBLE);
        footergroup.setVisibility(View.VISIBLE);
    }

    public void showViewPager(List<String> imgsId, int index) {
        Log.i("CloudSeaModuleActivity", "显示showimagefragment");
        replaceshowimages.setVisibility(View.VISIBLE);
        //传值给showimagefragment 并显示它（viewpager）
        ArrayList<String> list = new ArrayList<>();
        list.addAll(imgsId);
//        Log.i("CloudSeaModuleActivity", "list:" + );
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ShowImagesFragment showImagesFragment = new ShowImagesFragment();
//        if (!showImagesFragment.isAdded()){
        Bundle bundle = new Bundle();
        bundle.putSerializable("imagesUrls", list);
        bundle.putInt("index", index);
        showImagesFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.cloudsea_replace_showimagefragment, showImagesFragment).commit();

        rl_content.setVisibility(View.GONE);
        footergroup.setVisibility(View.GONE);
    }




    SQLiteDatabase mSQLiteDatabase;

    public void getData() {
        mSQLiteDatabase = this.openOrCreateDatabase("moyun", MODE_PRIVATE, null);
        String CREATE_TABLE2 = "select * from data ";
        Cursor cursor1 = mSQLiteDatabase.rawQuery(CREATE_TABLE2, null);
        while (cursor1.moveToNext()) {
            a = cursor1.getString(1);//用户id
            b = cursor1.getString(2);//昵称
            c = cursor1.getString(3);//头像
            d = cursor1.getString(4);//性别
            e = cursor1.getString(5);//生日
            f = cursor1.getString(6);//手机
            g = cursor1.getString(8);//签名
            // Toast.makeText(CloudSeaModuleActivity.this, " 用户id" + a + " 昵称" + b + " 头像" + c + " 性别" + d + " 生日" + e + " 手机" + f + " 签名" + g, Toast.LENGTH_SHORT).show();
        }

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



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && replaceshowimages.getVisibility() == View.GONE){
            AppExit();
            return false;
        }else if (keyCode == KeyEvent.KEYCODE_BACK && replaceshowimages.getVisibility() == View.VISIBLE){
            //控制showimagefragment 按返回键 隐藏
            hideBigImageView();
            return false;
        }else {
            return super.onKeyUp(keyCode, event);
        }

    }

    private void AppExit() {
        if (!isAppExit){
            isAppExit = true;
            Toast.makeText(CloudSeaModuleActivity.this, R.string.app_exit, Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0,2000);
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
//            finish();
            System.exit(0);
        }
    }

}
