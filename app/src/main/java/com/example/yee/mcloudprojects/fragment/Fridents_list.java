package com.example.yee.mcloudprojects.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.yee.mcloudprojects.CloudSeaModuleActivity;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.activity.BulidgroupActivity;
import com.example.yee.mcloudprojects.activity.Install;
import com.example.yee.mcloudprojects.activity.SearchActivity;
import com.example.yee.mcloudprojects.adapter.FragmentsAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Fridents_list extends Fragment{
    ArrayList<Fragment> fragments;
    FragmentManager mFragmentManager;
    Linked_f1 linke1;
    Linked_f2 linke2;
    //RadioGroup radioGroup;
    FragmentTransaction mFragmentTransaction;
    Fragment mTempFragment;
    ViewPager vp;
    FragmentsAdapter fadapter;
    View contentVIew;
     ImageView imageButton;
    String b;
    PopupWindow mPopupWindow;// 弹窗对象
    View mPopView;
    ImageView mChats,mChats2,mChats3,mChats4;// 弹窗上的选项
    ImageView user_tx;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
        contentVIew=inflater.inflate(R.layout.activity_my_linked_fridents,container,false);
        user_tx= (ImageView) contentVIew.findViewById(R.id.user_tx);
       // setContentView(R.layout.activity_my_linked_fridents);
        //radioGroup = (RadioGroup)contentVIew.findViewById(R.id.group);
        initviewpage();
        initpopouwindow();
      //  addlistener();
        getData();
        inituserdata();
        initListener();
        return contentVIew;
    }

    private void initListener() {
        user_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloudSeaModuleActivity cloudSeaModuleActivity = (CloudSeaModuleActivity) getActivity();
                cloudSeaModuleActivity.changeSliding();
            }
        });
    }


    private void initpopouwindow() {
        mPopView = LayoutInflater.from(getActivity()).inflate(
                R.layout.mypopupwindow, null);
        // 阴影遮挡图层
       // mCanversLayout= (RelativeLayout) contentVIew.findViewById(R.id.rl_canvers);
        mPopupWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mChats = (ImageView) mPopView.findViewById(R.id.pop_chat);
        mChats2 = (ImageView) mPopView.findViewById(R.id.pop_chat2);
        mChats3 = (ImageView) mPopView.findViewById(R.id.pop_chat3);
      //  mChats4= (ImageView) mPopView.findViewById(R.id.pop_chat4);
        //图标点击事件
        mChats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPopupWindow.dismiss();
                mPopupWindow.dismiss();
                Intent intent=new Intent(getActivity(),SearchActivity.class);
                  startActivity(intent);
            }
        });
        mChats2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                Intent intent=new Intent(getActivity(), BulidgroupActivity.class);
                startActivity(intent);
               // mPopupWindow.dismiss();
            }
        });
        mChats3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置
                Intent intent = new Intent(getActivity(), Install.class);
                startActivity(intent);
                mPopupWindow.dismiss();
            }
        });
//        mChats4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //扫一扫
//              //Intent intent = new Intent(getActivity(), CaptureActivity.class);
////                startActivityForResult(intent, 1);
//                mPopupWindow.dismiss();
//            }
//        });


        imageButton= (ImageView) contentVIew.findViewById(R.id.cloud_search);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {// 点击出现
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color
                        .parseColor("#F0F0F0")));
                //起始位置到，
                mPopupWindow.showAsDropDown(imageButton, 0, -15);
                mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
                mPopupWindow.setFocusable(true);
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.update();

            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {// 点击消失

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }
    //初始化viewpage
    private void initviewpage() {
        linke1 = new Linked_f1();
        linke2 = new Linked_f2();
        fragments=new ArrayList<Fragment>();
        fragments.add(linke1);
        vp= (ViewPager) contentVIew.findViewById(R.id.midle);
        fadapter=new  FragmentsAdapter(getFragmentManager(), fragments);

        vp.setAdapter(fadapter);
    }
    //group点击事件
//    private void addlistener() {
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i) {
//                    case R.id.group1:
//                        vp.setCurrentItem(0);
//                        break;
//                    case R.id.group2:
//                        vp.setCurrentItem(1);
//                        break;
//                }
//            }
//        });
//        //viewpage 设置监听事件控制按钮
//        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//            @Override
//            public void onPageSelected(int position) {
//                RadioButton button = null;
//               switch(position){
//                   case 0:
//                       button= (RadioButton) radioGroup.getChildAt(0);
//                       break;
//                   case 1:
//                       button= (RadioButton) radioGroup.getChildAt(1);
//                       break;
//               }
//                button.setChecked(true);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//    }


    @Override
    public void onResume() {
        Log.i("Fridents_list", "Fridents_list->onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("Fridents_list", "Fridents_list->onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("Fridents_list", "Fridents_list->onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("Fridents_list", "Fridents_list->onStop");
    }

    SQLiteDatabase mSQLiteDatabase;

    public void getData() {
        mSQLiteDatabase = getActivity().openOrCreateDatabase("moyun",getActivity().MODE_PRIVATE, null);
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
                .into(user_tx);

    }

}


