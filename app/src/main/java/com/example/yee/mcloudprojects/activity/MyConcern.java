package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.yee.mcloudprojects.CloudSeaModuleActivity;
import com.example.yee.mcloudprojects.PullToRefreshLayout;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.MyConcernAdapter;
import com.example.yee.mcloudprojects.entity.MyConcerns;
import com.example.yee.mcloudprojects.pullableview.MyConcernListView;
import com.example.yee.mcloudprojects.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

public class MyConcern extends AppCompatActivity {
    private PullToRefreshLayout refreshLayout;
    private MyConcernListView listView;
    private ArrayList<MyConcerns> mList ;
    private MyConcernAdapter mAdapter;
    ImageButton backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_concern);
        initViews();
        initData();
        initAdpater();
        initListener();
        backbutton= (ImageButton) findViewById(R.id.backbutton);

        //返回单击点击事件
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MyConcern.this,CloudSeaModuleActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initListener() {
        listView.setOnItemClickListener(new  AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                finish();
            }
        });

        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                mList.clear();
                List<MyConcerns> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    MyConcerns myconcerns = new MyConcerns(R.drawable.cloudsea_container_history_id_image, "晓明", "你现在过得好么",false);
                    list.add(myconcerns);
                }
                mList.addAll(list);
                mAdapter.notifyDataSetChanged();

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);

            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

                List<MyConcerns> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    MyConcerns myconcerns = new MyConcerns(R.drawable.cloudsea_container_history_id_image, "晓明", "你现在过得好么",false);
                    list.add(myconcerns);
                }
                mList.addAll(list);
                mAdapter.notifyDataSetChanged();

                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });
    }

    private void initAdpater() {
        mAdapter = new MyConcernAdapter(this, mList);
        listView.setAdapter(mAdapter);
    }


    private void initData() {
       mList=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MyConcerns myconcerns = new MyConcerns(R.drawable.cloudsea_container_history_id_image, "晓明", "你现在过得好么",false);
            mList.add(myconcerns);
        }
       mAdapter=new MyConcernAdapter(this,mList);

    }


    private void initViews() {
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.follow_refreshlayout);
        listView = (MyConcernListView) findViewById(R.id.myconcernview);
    }
}

