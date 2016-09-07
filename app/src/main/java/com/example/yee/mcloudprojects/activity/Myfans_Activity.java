package com.example.yee.mcloudprojects.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.Myfansadapter;
import com.example.yee.mcloudprojects.entity.MyfansItems;

import java.util.ArrayList;
import java.util.List;

public class Myfans_Activity extends AppCompatActivity {
    ListView listView;
    List<MyfansItems> mList;
    Myfansadapter mMyAdapter;
    int firstVisibleItem, visibleItemCount;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfans_);
        getSupportActionBar().hide();
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light);
        listView= (ListView) findViewById(R.id.release_fans_loclv);
        initData();
        initAdapter();
        initListeners();
    }
    private void initData() {
        mList = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            MyfansItems item = new MyfansItems(R.drawable.ab2, "文字：" + i, false);
            mList.add(item);
        }
    }

    private void initAdapter() {
        mMyAdapter = new Myfansadapter(this, mList);
        listView.setAdapter(mMyAdapter);
    }
    private void initListeners() {
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_TOUCH_SCROLL:
                        mMyAdapter.setFling(false);
                        break;
                    case SCROLL_STATE_FLING:
                        mMyAdapter.setFling(true);
                        break;
                    case SCROLL_STATE_IDLE:
                        mMyAdapter.setFling(false);
                        //滑动停止，及时刷新界面,getChildCount返回的是当前页面可见子视图
                        int count = listView.getChildCount();
                        for (int i = 0; i < count; i++) {
                            //取出每一行的视图
                            View v = listView.getChildAt(i);
                            //取出当前视图中的控件
                            ImageView image = (ImageView) v.findViewById(R.id.imageCircle);
                            TextView text = (TextView) v.findViewById(R.id.title);
                            CheckBox check = (CheckBox) v.findViewById(R.id.c_author);
                            //刷新界面，主要是刷新快速滑动是没有更新的数据
                            Object textTag = text.getTag();
                            if (textTag != null) {
                                //快速滑动，需要设置正确数据
                                text.setText((String) text.getTag());
                                text.setTag(null);//文本标签为null，表示当前文字已经正常
                            }
                            //如果图片标签是“1”,说明正常加载图片，否则是快速滑动
                            try {
                                String imageTag = (String) image.getTag();
                            } catch (Exception e) {
                                //说明图片标签不是字符串，是整数类型
                                int drawableIndex = (int) image.getTag();
                                image.setImageResource(drawableIndex);
                            }
                        }
                        break;
                }
                //在该方法中进行网络请求最新数据
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {

                            }
                        }, 5000);
                        Toast.makeText(Myfans_Activity.this,"c",Toast.LENGTH_LONG).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                });

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Myfans_Activity.this.firstVisibleItem = firstVisibleItem;
                Myfans_Activity.this.visibleItemCount = visibleItemCount;
            }
        });
    }
}