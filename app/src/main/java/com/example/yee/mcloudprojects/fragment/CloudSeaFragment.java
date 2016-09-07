package com.example.yee.mcloudprojects.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yee.mcloudprojects.CloudSeaModuleActivity;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.activity.ReleaseActivity;
import com.example.yee.mcloudprojects.adapter.DynamicRecyclerViewAdapter;
import com.example.yee.mcloudprojects.adapter.LoadStatus;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.customView.RecyclerViewDivider;
import com.example.yee.mcloudprojects.entity.Dynamic;
import com.example.yee.mcloudprojects.listener.ChangeFloatingBtnListener;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class CloudSeaFragment extends Fragment {

    DynamicRecyclerViewAdapter recyclerViewAdapter;   //recycler适配器
    View contentVIew;
    FloatingActionButton fab;           //发布功能键
    ImageView user_tx;                  //顶部导航头像侧滑
    TextView user_name,user_me;
    DrawerLayout cloud_homepage;        //主体，滑动框架
    LinearLayout cloud_sliding;       //滑动框架

    boolean isLoading;
//    boolean canLoadMore = true;
    int lastVisibleItemPosition = 0;
    SwipeRefreshLayout swipeRefreshLayout;    //下拉刷新
    RecyclerView dynamicRecyclerView;         //recycler
    List<Dynamic> dynamicList = new ArrayList<>();  //动态数据
    LinearLayoutManager layoutManager;
    MyApplication myApplication;
    long limitpage = 0;
    boolean isFirst = true;
    boolean isMine = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0){
                changeData();
            }
            if (msg.what == 1){
                changeData();
                Log.d("handler", "load more completed");
                Log.i("CloudSeaFragment", "加载完成");
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentVIew = inflater.inflate(R.layout.activity_clouldsea_fragment, container, false);
        myApplication = (MyApplication) getActivity().getApplication();
        initView();
        initData();
        initAdapter();
        initListener();
        isFirstInto();
        return contentVIew;
    }

    private void isFirstInto() {
        if (isFirst){
            isFirst=false;
            dynamicRefreshRecycler();
        }
    }

    private void initListener() {


        dynamicRecyclerView.setOnScrollListener(new ChangeFloatingBtnListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });

        dynamicRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition + 1 == recyclerViewAdapter.getItemCount()) {
                    Log.d("onScrolled", "loading executed");

//                    Log.i("CloudSeaFragment", "CloudSeaFragment->onScrolled:" + canLoadMore);
                    Log.i("CloudSeaFragment", "是否在加载" + isLoading);
                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    Log.i("CloudSeaFragment", "是否在刷新" + isRefreshing);
                    if (isRefreshing) {
                        recyclerViewAdapter.notifyItemRemoved(recyclerViewAdapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        Log.i("CloudSeaFragment", "开始加载");
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
                        recyclerViewAdapter.setLoadStatus(LoadStatus.LOADING_MORE);

                        //所有动态下一页
                        if (!isMine){
                            goNextPage();
                        }else {
                            //我的动态下一页
                            iDynamicLoadMore();
                        }




//                            }
//                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//                Log.i("CloudSeaFragment", "CloudSeaFragment->onScrolled:" + canLoadMore);
                Log.i("CloudSeaFragment", "CloudSeaFragment->onScrolled:" + lastVisibleItemPosition);
//                if (lastVisibleItemPosition + 1 == recyclerViewAdapter.getItemCount()) {
//
//                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                limitpage = 0;
                Log.i("CloudSeaFragment", "开始刷新");
                if (!isMine){
                    dynamicRefreshRecycler();
                }else {
                    iDynamicRefresh();
                }

            }
        });

        user_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeRefreshLayout.setRefreshing(true);
                if (!isMine){
                    user_me.setText("ALL");
                    isMine = true;
                    iDynamicRefresh();

                }else {
                    user_me.setText("ME");
                    isMine = false;
                    dynamicRefreshRecycler();
                }

            }
        });

        user_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloudSeaModuleActivity cloudSeaModuleActivity = (CloudSeaModuleActivity) getActivity();
                cloudSeaModuleActivity.changeSliding();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rel_fab = new Intent(getActivity(), ReleaseActivity.class);
                startActivityForResult(rel_fab,9);
            }
        });
    }



    private void initAdapter() {
        dynamicRecyclerView.setAdapter(recyclerViewAdapter);

    }

    private void initData() {
        recyclerViewAdapter = new DynamicRecyclerViewAdapter(getActivity(), dynamicList);
//        dynamicList.clear();
//        dynamicRefreshRecycler();


        setTopView();

    }

    private void setTopView() {
        GetLoginUser getLoginUser = new GetLoginUser(getActivity());

        Picasso.with(getActivity())
                .load("http://oc1souo4f.bkt.clouddn.com/" + getLoginUser.getLoginMUser().getHeadportrait())
                .placeholder(R.drawable.avatar_default).into(user_tx);

        user_name.setText(getLoginUser.getDataName());
    }

    private void initView() {
        user_me = (TextView) contentVIew.findViewById(R.id.cloud_mine);
        dynamicRecyclerView = (RecyclerView) contentVIew.findViewById(R.id.cloudsea_content_recycler);
        layoutManager = new LinearLayoutManager(getActivity());
        dynamicRecyclerView.setLayoutManager(layoutManager);
        dynamicRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), OrientationHelper.VERTICAL, 20, Color.parseColor("#F0F0F0")));
        swipeRefreshLayout = (SwipeRefreshLayout) contentVIew.findViewById(R.id.cloudsea_refreshcontent);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

//        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
//                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
//                        .getDisplayMetrics()));

//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        });

        cloud_homepage = (DrawerLayout) contentVIew.findViewById(R.id.cloud_homepage);
        cloud_sliding = (LinearLayout) contentVIew.findViewById(R.id.cloud_sliding);
        fab = (FloatingActionButton) contentVIew.findViewById(R.id.cloudsea_rf);
        user_tx = (ImageView) contentVIew.findViewById(R.id.cloudsea_user_tx);
        user_name = (TextView) contentVIew.findViewById(R.id.user_name);
    }


    private void iDynamicLoadMore(){
        Log.i("CloudSeaFragment", "me->开始获取加载的数据");
        String url = myApplication.getUrl();
        GetLoginUser getLoginUser = new GetLoginUser(getActivity());
        int user_id = getLoginUser.getAccountId();
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(3000);
        params.addBodyParameter("flag", "70");
        params.addBodyParameter("user_id", user_id + "");
        params.addBodyParameter("dynamic_id", String.valueOf(limitpage));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("CloudSeaFragment", "获取加载的数据:" + result);
                canLoadMore(result);
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

    //获取刷新自己的动态
    private void iDynamicRefresh() {

        String url = myApplication.getUrl();
        GetLoginUser getLoginUser = new GetLoginUser(getActivity());
        int user_id = getLoginUser.getAccountId();
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(8000);
        params.addBodyParameter("flag", "67");
        params.addBodyParameter("user_id", user_id + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("CloudSeaFragment", "获得刷新数据");
                swipeRefreshLayout.setRefreshing(false);
                canRefresh(result);
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

    //刷新获取数据，所有人的动态
    public void dynamicRefreshRecycler() {

        String url = myApplication.getUrl();
        GetLoginUser getLoginUser = new GetLoginUser(getActivity());
        int user_id = getLoginUser.getAccountId();
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(8000);
        params.addBodyParameter("flag", "73");
        params.addBodyParameter("user_id", user_id + "");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("CloudSeaFragment", "获得刷新数据");
                canRefresh(result);
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


    //根据数据判断能否刷新
    private void canRefresh(String result) {
        if (result.equals("no")) {
            Log.i("CloudSeaFragment", "刷新数据为空");
            swipeRefreshLayout.setRefreshing(false);
            return;
        } else {
//            canLoadMore = true;
            dynamicList.clear();
            Log.i("CloudSeaFragment", "清空已有数据，重新传入");
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Type type = new TypeToken<List<Dynamic>>() {
            }.getType();
            Log.i("CloudSeaFragment", "111111111");
            List<Dynamic> dynamics = gson.fromJson(result, type);
            Log.i("CloudSeaFragment", "222222222");
            limitpage = dynamics.get(dynamics.size() - 1).getId();
            Log.i("CloudSeaFragment", "CloudSeaFragment->onSuccess:" + dynamics.size());
            swipeRefreshLayout.setRefreshing(false);
//            if (dynamics.size() < 5) {
//                canLoadMore = false;
//            }


            dynamicList.addAll(dynamics);
            handler.sendEmptyMessage(0);
            Log.i("CloudSeaFragment", "333333333");
//            changeData();

        }
    }


    //根据数据判断能否加载
    private void canLoadMore(String result) {

        if (result.equals("no") || result.equals("")) {
            isLoading = false;
            Log.i("CloudSeaFragment", "加载数据为空");
            recyclerViewAdapter.setLoadStatus(LoadStatus.NO_LOAD_MORE);
            recyclerViewAdapter.notifyDataSetChanged();

            return;
        } else {
            Log.i("CloudSeaFragment", "获得加载数据");

//            Gson gson = new Gson();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Type type = new TypeToken<List<Dynamic>>() {
            }.getType();
            List<Dynamic> dynamics = gson.fromJson(result, type);
            limitpage = dynamics.get(dynamics.size() - 1).getId();
            Log.i("CloudSeaFragment", "onSuccess:" + limitpage);
            Log.i("CloudSeaFragment", "CloudSeaFragment->onSuccess:" + dynamics.size());
//            if (canLoadMore) {
//                if (dynamics.size() < 5) {
//                    canLoadMore = false;
//                }

                dynamicList.addAll(dynamics);
            Log.i("CloudSeaFragment", "加载数据发送，准备展示");
                handler.sendEmptyMessage(1);
//                changeData();
//            }

        }

    }


    //加载更多下一页
    public void goNextPage() {
        String url = myApplication.getUrl();
        GetLoginUser getLoginUser = new GetLoginUser(getActivity());
        int user_id = getLoginUser.getAccountId();
        RequestParams params = new RequestParams(url);
        params.setConnectTimeout(3000);
        params.addBodyParameter("flag", "74");
        params.addBodyParameter("user_id", user_id + "");
        params.addBodyParameter("dynamic_id", String.valueOf(limitpage));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("CloudSeaFragment", "获取加载的数据:" + result);
                canLoadMore(result);
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

    public void changeData() {
        recyclerViewAdapter.setLoadStatus(LoadStatus.NO_LOAD_MORE);
        recyclerViewAdapter.notifyDataSetChanged();

//        recyclerViewAdapter.notifyItemChanged(recyclerViewAdapter.getItemCount());
        isLoading = false;
    }

    private void hideViews() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) fab.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        fab.animate().translationY(fab.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==9&& resultCode==9){
            dynamicRefreshRecycler();
        }
    }


    @Override
    public void onResume() {
        setTopView();
        if (!isMine){
            dynamicRefreshRecycler();
        }else {
            iDynamicRefresh();
        }

        super.onResume();
    }
}
