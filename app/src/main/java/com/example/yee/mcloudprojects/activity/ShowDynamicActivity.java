package com.example.yee.mcloudprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.PullToRefreshLayout;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.CommentAdapter;
import com.example.yee.mcloudprojects.adapter.DynamicNineAdapter;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.CommentBean;
import com.example.yee.mcloudprojects.entity.Dynamic;
import com.example.yee.mcloudprojects.fragment.ShowImagesFragment;
import com.example.yee.mcloudprojects.pullableview.LoadMoreListView;
import com.example.yee.mcloudprojects.pullableview.PullableListView;
import com.example.yee.mcloudprojects.utils.DateUtil;
import com.example.yee.mcloudprojects.utils.GetLoginUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ShowDynamicActivity extends AppCompatActivity {

    LoadMoreListView commentListView;
    PullToRefreshLayout pullToRefreshLayout;
    EditText commentContent;
    Button commentSubmit, replay, comment, like;
    RelativeLayout userAciton;
    LinearLayout commentView;
    FrameLayout footer;
    RelativeLayout top;
    ImageButton back;
    Button del;

    RelativeLayout viewpagerfragment;
    View popwin;

    boolean isliked;
    int level = 0;
    int responser;//被回复者

    Info mInfo;

//    List<String> images = new ArrayList<>();
    List<CommentBean> commentBeanList = new ArrayList<>();

    GetLoginUser getLoginUser = new GetLoginUser(this);
    MyApplication myApplication;
    PopupWindow delpopupWindos;
    String url;

    Dynamic dynamic;
    CommentBean reCommentBean;
    CommentAdapter commentAdapter ;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                Log.i("ShowDynamicActivity", "评论数目：" + commentBeanList.size());
                commentAdapter.notifyDataSetChanged();
                Log.i("ShowDynamicActivity", "刷新评论列表");
            }
            if (msg.what == 2){
                Log.i("ShowDynamicActivity", "1级评论成功");
                getCommentData(dynamic);

                Toast.makeText(ShowDynamicActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
            }

            if (msg.what == 3){
                getCommentData(dynamic);

                Log.i("ShowDynamicActivity", "2级评论成功");
                Toast.makeText(ShowDynamicActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dynamic);
        myApplication = (MyApplication) getApplication();
        url = myApplication.getUrl();
        initViews();
        initData();
        initAdapter();
        initListeners();
        initPopupWindow();

    }

    public void showDelPopupWindow(){
        View rootView = findViewById(R.id.show_dynamic_body);
        delpopupWindos.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        Button del_ok  = (Button) popwin.findViewById(R.id.del_dynamic_ok);
        Button del_cancel = (Button) popwin.findViewById(R.id.del_dynamic_cancel);
        del_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delpopupWindos.setFocusable(false);
                delpopupWindos.dismiss();
                goDelDynamic();
            }
        });

        del_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delpopupWindos.setFocusable(false);
                delpopupWindos.dismiss();
            }
        });
    }

    private void goDelDynamic() {
        RequestParams params = new RequestParams(myApplication.getUrl());
        params.addBodyParameter("flag","999");
        String dynamicid = String.valueOf(dynamic.getId());
        params.addBodyParameter("dynamic_id",dynamicid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("ok")){
                    Toast.makeText(ShowDynamicActivity.this, "删除完成", Toast.LENGTH_SHORT).show();
                    finish();
                }
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


    private void initPopupWindow() {
        popwin = LayoutInflater.from(this).inflate(R.layout.popwin_del_dynamic,null);
        delpopupWindos = new PopupWindow(popwin,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        delpopupWindos.setAnimationStyle(R.style.popupAnimation);
        delpopupWindos.setBackgroundDrawable(new BitmapDrawable());
        delpopupWindos.setFocusable(true);

    }

    private void initAdapter() {
        Log.i("ShowDynamicActivity", "头部初始化，请求评论列表");
        commentAdapter = new CommentAdapter(this,commentBeanList);

        commentListView.setAdapter(commentAdapter);
    }



    //设置listview头部
    private View initCommentHead(Dynamic dynamic) {
        View commentHead = LayoutInflater.from(ShowDynamicActivity.this).inflate(R.layout.show_dynamic_header,null);
        CircleImageView dynamic_tx = (CircleImageView) commentHead.findViewById(R.id.show_dynamic_header_tx);
        TextView dynamic_name = (TextView) commentHead.findViewById(R.id.show_dynamic_header_username);
        TextView dynamic_time = (TextView) commentHead.findViewById(R.id.show_dynamic_header_time);
        TextView dynamic_content = (TextView) commentHead.findViewById(R.id.show_dynamic_header_content);
        NineGridImageView dynamic_pic = (NineGridImageView) commentHead.findViewById(R.id.show_dynamic_header_pics);
        TextView dynamic_loc = (TextView) commentHead.findViewById(R.id.show_dynamic_header_location_text);
        RelativeLayout dynamic_locview = (RelativeLayout) commentHead.findViewById(R.id.show_dynamic_header__location);

        Picasso.with(this)
                .load("http://oc1souo4f.bkt.clouddn.com/"+dynamic.getHeadportrait())
                .placeholder(R.drawable.surprise_toolbar_picture)
                .into(dynamic_tx);
        //用户名
        dynamic_name.setText(dynamic.getName());


        long curTime =System.currentTimeMillis() / (long) 1000 ;
        long time = curTime - dynamic.getTime().getTime()/1000;
        if (time < 3600*24 && time >= 0){
            dynamic_time.setText(DateUtil.convertTimeToFormat(dynamic.getTime().getTime()/1000));
        }else if (time >= 3600 * 24 &&time < 3600 * 24 * 30 * 12){
            dynamic_time.setText(DateUtil.formatDateInYear(dynamic.getTime().getTime()/1000) + "  " + DateUtil.getTime(dynamic.getTime().getTime()/1000));
        }else if (time >= 3600 * 24 * 30 * 12){
            dynamic_time.setText(DateUtil.timeStampToStr(dynamic.getTime().getTime()/1000));
        }

//        dynamic_time.setText(dynamic.getTime().toString());
        dynamic_content.setText(dynamic.getText());

        Gson gson = new Gson();
        Type type1 = new TypeToken<List<String>>() {
        }.getType();
        String imagesurls = dynamic.getImages();
        List<String> images = new ArrayList<>();
        List<String> imagesurl = gson.fromJson(imagesurls, type1);
        if (imagesurl!=null) {

            for (String image : imagesurl) {
                String imageurl = "http://oc1souo4f.bkt.clouddn.com/" + image;
                Log.i("ItemViewHolder", "ItemViewHolder->onSuccess:" + imageurl);
                images.add(imageurl);
            }
            //如果有图片

            dynamic_pic.setVisibility(View.VISIBLE);
            DynamicNineAdapter myNineAdapter = new DynamicNineAdapter();
            dynamic_pic.setAdapter(myNineAdapter);
            dynamic_pic.setImagesData(images);

        }


        //如果有gps
        if (dynamic.getGps() != null) {
            dynamic_loc.setText(dynamic.getGps());
            dynamic_locview.setVisibility(View.VISIBLE);
        }
        return commentHead;
    }

    private void initListeners() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                canDelDynamic();

            }
        });

        pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //加载更多评论
                getCommentData(dynamic);
                pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }
        });

        commentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    level = 0;
                    commentContent.setHint("回复：");
                    commentShow();

                }else {
                    //2级评论回复
                    level = 1;
                    responser = commentBeanList.get(i-1).getAuthor_id();
                    if (level==1){
                        commentContent.setHint("回复：" + commentBeanList.get(i-1).getAuthor_name());
                    }
                    commentShow();
                }



            }
        });


        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1级评论回复
                level = 0;
                commentContent.setHint("回复：");
                commentShow();

            }
        });

        commentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提交评论
                commentHide();


                if (level == 0){
                    //1级评论回复
                    oneCommentReplay();
                }else {
                    reCommentReplay();
                }

                //清空输入框内容，隐藏键盘
                commentContent.setText("");
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(commentContent.getWindowToken(), 0);

            }
        });


        //点赞
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isliked){
                    isliked = true;
                    like2Server();
                }else {
                    isliked = false;
                    disLike2Server();
                }

            }
        });
    }

    private void canDelDynamic() {
        int userid = getLoginUser.getLoginMUser().getId();
        int dynamicCreator = dynamic.getUser_id();
        if (userid==dynamicCreator){
            showDelPopupWindow();
        }else {
            Toast.makeText(ShowDynamicActivity.this, "您无法删除", Toast.LENGTH_SHORT).show();
        }
    }

    private void disLike2Server() {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("flag","72");
        params.addBodyParameter("dynamic_id",String.valueOf(dynamic.getId()));
        params.addBodyParameter("user_id",String.valueOf(getLoginUser.getLoginMUser().getId()));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result!=null){
                    disLikedBtn();
                    Log.i("ShowDynamicActivity", "服务器相应点赞：" + result);
                }

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

    private void disLikedBtn() {
        Drawable drawable = getResources().getDrawable(R.drawable.history_btn_function_zan,null);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        like.setCompoundDrawables(drawable,null,null,null);
        like.setText((dynamic.getGood_c()-1) + "");
    }

    //点赞
    private void like2Server() {
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("flag","71");
        params.addBodyParameter("dynamic_id",String.valueOf(dynamic.getId()));
        params.addBodyParameter("user_id",String.valueOf(getLoginUser.getLoginMUser().getId()));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result!=null){
                    likedBtn();
                    Log.i("ShowDynamicActivity", "服务器相应点赞：" + result);
                }

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

    private void likedBtn() {
        Drawable drawable = getResources().getDrawable(R.drawable.dynamic_liked,null);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        like.setCompoundDrawables(drawable,null,null,null);
        like.setText((dynamic.getGood_c()+1) + "");

    }

    private void reCommentReplay() {
        Log.i("ShowDynamicActivity", "2级评论发送");
        String url = "http://10.50.7.49:8080/moyun/dispose";
        GetLoginUser getLoginUser = new GetLoginUser(this);
        long dynamic_id = dynamic.getId();
        int user_id = getLoginUser.getAccountId();
        String c_content = commentContent.getText().toString();
        int author = getLoginUser.getAccountId();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("flag","8");
        params.addBodyParameter("dynamic_id",String.valueOf(dynamic_id));
        params.addBodyParameter("user_id",String.valueOf(user_id));
        params.addBodyParameter("level",String.valueOf(level));
        params.addBodyParameter("text",c_content);
        params.addBodyParameter("author",String.valueOf(author));
        Log.i("ShowDynamicActivity", "responser:" + responser);
        params.addBodyParameter("responser",String.valueOf(responser));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("ok")){
                    handler.sendEmptyMessage(3);
                }
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

    private void oneCommentReplay() {
        Log.i("ShowDynamicActivity", "1级回复，开始");
        GetLoginUser getLoginUser = new GetLoginUser(this);
        long dynamic_id = dynamic.getId();
        int user_id = getLoginUser.getAccountId();
        String c_content = commentContent.getText().toString();
        int author = getLoginUser.getAccountId();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("flag","8");
        params.addBodyParameter("dynamic_id",String.valueOf(dynamic_id));
        params.addBodyParameter("user_id",String.valueOf(user_id));
        params.addBodyParameter("level",String.valueOf(level));
        params.addBodyParameter("text",c_content);
        params.addBodyParameter("author",String.valueOf(author));
        params.addBodyParameter("responser",String.valueOf(0));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result.equals("ok")){
                    handler.sendEmptyMessage(2);
                }
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



    private void initData() {
        Intent intent = getIntent();
//        List<String> images = intent.getStringArrayListExtra("DynamicPics");

        dynamic = (Dynamic) intent.getSerializableExtra("UserDynamic");
        getCommentData(dynamic);
        replay.setText(dynamic.getRelay_c() + "");
        comment.setText(dynamic.getComment_c() + "");
        if (dynamic.getZan()==1){
            isliked = true;
            Drawable drawable = getResources().getDrawable(R.drawable.dynamic_liked,null);
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
            like.setCompoundDrawables(drawable,null,null,null);
        }else {
            isliked = false;
        }
        like.setText(dynamic.getGood_c() + "");
        View commendHead = initCommentHead(dynamic);
        commentListView.addHeaderView(commendHead);
    }

    private void initViews() {
        Log.i("ShowDynamicActivity", "初始化动态详情界面");
        back = (ImageButton) findViewById(R.id.dynamic_top_back);
        del = (Button) findViewById(R.id.show_dynamic_del);
        top = (RelativeLayout) findViewById(R.id.dynamic_top);
        footer = (FrameLayout) findViewById(R.id.dynamic_footerview);
        userAciton = (RelativeLayout) findViewById(R.id.dynamic_footer);
        commentView = (LinearLayout) findViewById(R.id.dynamic_footer_commentView);
        commentListView = (LoadMoreListView) findViewById(R.id.dynamic_comment_listview);
        pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.dynamic_comment_pulltorefresh);
        commentContent = (EditText) findViewById(R.id.dynamic_footer_commentView_content);
        commentSubmit = (Button) findViewById(R.id.dynamic_footer_commentView_submit);
        replay = (Button) findViewById(R.id.dynamic_footer_reply);
        comment = (Button) findViewById(R.id.dynamic_footer_comment);
        like = (Button) findViewById(R.id.dynamic_footer_like);
        viewpagerfragment = (RelativeLayout) findViewById(R.id.show_dynamic_viewpagerfragment);


    }

    //显示评论回复区域
    private void commentShow() {

        userAciton.setVisibility(View.GONE);
        commentView.setVisibility(View.VISIBLE);

    }

    private void commentHide() {
        userAciton.setVisibility(View.VISIBLE);
        commentView.setVisibility(View.GONE);
    }


    //获取评论表数据
    private void getCommentData(Dynamic dyanmic) {
        Log.i("ShowDynamicActivity", "请求评论列表开始");
        String url = "http://10.50.7.49:8080/moyun/dispose";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("flag","9");
        params.addBodyParameter("dynamic_id",String.valueOf(dyanmic.getId()));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("ShowDynamicActivity", "评论列表返回:" + result);
                refreshCommentList(result);
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

    private void refreshCommentList(String result) {
        commentBeanList.clear();
        if (result.equals("no") || "".equals(result)){
//            Toast.makeText(ShowDynamicActivity.this, "当前没有评论", Toast.LENGTH_SHORT).show();
        }else {
            Log.i("ShowDynamicActivity", "获得评论列表");
            Gson gson = new Gson();

            Type type = new TypeToken<List<CommentBean>>(){}.getType();

            List<CommentBean> list = gson.fromJson(result,type);

            commentBeanList.addAll(list);

            handler.sendEmptyMessage(1);
            Log.i("ShowDynamicActivity", "发送消息给handler");
        }

    }


    public void hideBigImageView() {


        viewpagerfragment.setVisibility(View.GONE);

        top.setVisibility(View.VISIBLE);
        footer.setVisibility(View.VISIBLE);
        pullToRefreshLayout.setVisibility(View.VISIBLE);
    }

    public void showViewPager(List<String> imgsId, int index) {
        Log.i("CloudSeaModuleActivity", "显示showimagefragment");
        viewpagerfragment.setVisibility(View.VISIBLE);
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
        fragmentTransaction.replace(R.id.show_dynamic_viewpagerfragment, showImagesFragment).commit();

        top.setVisibility(View.GONE);
        footer.setVisibility(View.GONE);
        pullToRefreshLayout.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        if (viewpagerfragment.getVisibility() == View.VISIBLE) {
            hideBigImageView();
        }else if (commentView.getVisibility()==View.VISIBLE){
            commentView.setVisibility(View.GONE);
            userAciton.setVisibility(View.VISIBLE);
        }else if (delpopupWindos.isShowing()){
            delpopupWindos.dismiss();
        }else {
            super.onBackPressed();
        }
    }
}
