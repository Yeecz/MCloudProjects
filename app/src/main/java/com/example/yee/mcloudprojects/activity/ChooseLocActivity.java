package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.example.yee.mcloudprojects.BaiduLocation.GetLocData;
import com.example.yee.mcloudprojects.PullToRefreshLayout;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.MyPoiAdapter;
import com.example.yee.mcloudprojects.adapter.MyPoiInfoAdapter;
import com.example.yee.mcloudprojects.pullableview.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

public class ChooseLocActivity extends AppCompatActivity {

    private String city;
    private LatLng center;
    private GetLocData getLocData;
    private LoadMoreListView locListView;
    private List<Poi> poiList = new ArrayList();
    private List<PoiInfo> poiinfoList = new ArrayList();
    private EditText locEditText;
    private ImageButton backBtn;
    private Button searchBtn;
    private PullToRefreshLayout refreshLayout;
    private MyPoiAdapter myPoiAdapter;
    private MyPoiInfoAdapter myPoiInfoAdapter;
    private boolean isChangeAdapter = false;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                city = getLocData.getCity();
                center = getLocData.getCenter();
                List poilist = getLocData.getPoilist();
                Log.i("ChooseLocActivity", "handleMessage:" + poilist.isEmpty());
                poiList.addAll(poilist);
                myPoiAdapter.notifyDataSetChanged();
                Log.i("ChooseLocActivity", "handleMessage:" + city + "," + center);
            }
            if (msg.what==2){
                List poiinfolist = getLocData.getPoiinfolist();
                poiinfoList.addAll(poiinfolist);
                myPoiInfoAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_loc);
        initView();
        initData();
        initAdpater();
        initListener();
    }

    private void initView() {
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.release_loaction_refreshlayout);
        locListView = (LoadMoreListView) findViewById(R.id.release_loaction_loclv);
        backBtn = (ImageButton) findViewById(R.id.release_loaction_back);
        searchBtn = (Button) findViewById(R.id.release_loaction_search);
        locEditText = (EditText) findViewById(R.id.release_loaction_lockey);
    }

    private void initData() {
        getLocData = GetLocData.getInstance();
        getLocData.startLoc(this,handler);
        myPoiAdapter = new MyPoiAdapter(poiList,this);
        myPoiInfoAdapter = new MyPoiInfoAdapter(poiinfoList,this);
    }

    private void initAdpater() {
        locListView.setAdapter(myPoiAdapter);
    }

    private void initListener() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lockey = locEditText.getText().toString();
                if (lockey.isEmpty()){
                    Toast.makeText(ChooseLocActivity.this, "请输入", Toast.LENGTH_SHORT).show();
                }else {
                    if (isChangeAdapter==false){
                        locListView.setAdapter(myPoiInfoAdapter);
                        isChangeAdapter=true;
                        getLocData.searchBoundByProcess(lockey);
                    }else {
                        poiinfoList.clear();
                        getLocData.searchBoundByProcess(lockey);

                    }
                }
            }
        });

        locListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                String name = null;
                if (isChangeAdapter){
                    if (i==0){
                        name = "显示位置";
                    }else {
                        name = poiinfoList.get(i-1).name;
                    }

                }else {
                    if (i==0){
                        name = "显示位置";
                    }else {
                        name = poiList.get(i-1).getName();
                    }

                }

                intent.putExtra("RequestAddress",name);
                Log.i("ChooseLocActivity", "onItemClick:" + name);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        refreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {

                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                if (locEditText.getText().toString().isEmpty()){
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }else {
                    getLocData.goToNextPage(locEditText.getText().toString());
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    Log.i("ChooseLocActivity", "onLoadMore" + locEditText.getText().toString());
                }

            }
        });

    }


    @Override
    protected void onDestroy() {
        getLocData.stopLoc();
        super.onDestroy();
    }
}
