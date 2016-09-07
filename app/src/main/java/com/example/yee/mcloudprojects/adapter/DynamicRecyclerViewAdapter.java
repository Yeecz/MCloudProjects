package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.activity.ShowDynamicActivity;
import com.example.yee.mcloudprojects.application.MyApplication;
import com.example.yee.mcloudprojects.entity.Dynamic;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yee on 2016/8/19.
 */
public class DynamicRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LoadStatus mLoadStatus = LoadStatus.NO_LOAD_MORE;//加载的状态,非加载状态
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    public static final int TYPE_NULL = -1;
    private static Context context;
    private List<Dynamic> data = new ArrayList<>();
    MyApplication myApplication;
    GetLoginUser getLoginUser;
    boolean isliked;

//    DynamicListNineAdapter dynamicListNineAdapter;


    public DynamicRecyclerViewAdapter(Context context, List<Dynamic> data) {
        this.context = context;
        this.data = data;
        getLoginUser = new GetLoginUser(context);
        myApplication = (MyApplication) context.getApplicationContext();
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1==getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            View view = LayoutInflater.from(context).inflate(R.layout.activity_cloudsea_container,parent,false);

            return new ItemViewHolder(view, new ItemViewHolderClicks() {
                @Override
                public void onItemClick(Dynamic dynamic) {

                    //跳转到详情
                    Intent intent = new Intent(context, ShowDynamicActivity.class);
                    intent.putExtra("UserDynamic",dynamic);
                    context.startActivity(intent);
                }

                @Override
                public void onCommentClick(Dynamic dynamic) {
                    Intent intent = new Intent(context, ShowDynamicActivity.class);
                    intent.putExtra("UserDynamic",dynamic);
                    context.startActivity(intent);
                }

                @Override
                public void onLikeClick(Dynamic dynamic, Button like) {
                    if (!isliked){
                        isliked = true;
                        like2Server(dynamic,like);
                    }else {
                        isliked = false;
                        disLike2Server(dynamic,like);
                    }
                }

//                @Override
//                public void onLikeClick(Dynamic dynamic) {
////                    Intent intent = new Intent(context, ShowDynamicActivity.class);
////                    intent.putExtra("UserDynamic",dynamic);
////                    context.startActivity(intent);
//                }

                @Override
                public void onFollowStatusChange(Dynamic dynamic, int position) {

                }
            });
        }else if (viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(context).inflate(R.layout.cloudsea_recycler_item_foot,parent,false);
            return new FootViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            //holder.tv.setText(data.get(position));

            Dynamic dynamic = data.get(position);
            ((ItemViewHolder) holder).getMUser(dynamic);

        }else if (holder instanceof FootViewHolder){
            switch (mLoadStatus) {
                case NO_LOAD_MORE:
                    ((FootViewHolder) holder).loadingview.setVisibility(View.GONE);
                    ((FootViewHolder) holder).disloadview.setVisibility(View.VISIBLE);
                    break;
                case LOADING_MORE:
                    ((FootViewHolder) holder).loadingview.setVisibility(View.VISIBLE);
                    ((FootViewHolder) holder).disloadview.setVisibility(View.GONE);
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return (data.size() == 0) ? 0: data.size() + 1;
    }

    public void setLoadStatus(LoadStatus loadStatus) {

        this.mLoadStatus = loadStatus;
    }

    private void disLike2Server(final Dynamic dynamic, final Button like) {
        RequestParams params = new RequestParams(myApplication.getUrl());
        params.addBodyParameter("flag","72");
        params.addBodyParameter("dynamic_id",String.valueOf(dynamic.getId()));
        params.addBodyParameter("user_id",String.valueOf(getLoginUser.getLoginMUser().getId()));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result!=null){
                    disLikedBtn(dynamic,like);
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

    //点赞
    private void like2Server(final Dynamic dynamic, final Button like) {
        RequestParams params = new RequestParams(myApplication.getUrl());
        params.addBodyParameter("flag","71");
        params.addBodyParameter("dynamic_id",String.valueOf(dynamic.getId()));
        params.addBodyParameter("user_id",String.valueOf(getLoginUser.getLoginMUser().getId()));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result!=null){
                    likedBtn(dynamic,like);
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

    private void disLikedBtn(Dynamic dynamic,Button like) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.history_btn_function_zan,null);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        like.setCompoundDrawables(drawable,null,null,null);
        like.setText((dynamic.getGood_c()-1) + "");
    }

    private void likedBtn(Dynamic dynamic,Button like) {
        Drawable drawable = context.getResources().getDrawable(R.drawable.dynamic_liked,null);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        like.setCompoundDrawables(drawable,null,null,null);
        like.setText((dynamic.getGood_c()+1) + "");

    }



    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView user_tx;
        TextView user_name;
        TextView history_date;
        TextView history_content;
        Button btn_zan;
        Button btn_chat;
        Button btn_retweet;
        NineGridImageView ngiv;
        RelativeLayout item_loc;
        TextView item_loc_content;
        RelativeLayout item_body;
        ItemViewHolderClicks mListener;
        Dynamic dataMUser;
//        ArrayList<String> imagesList = new ArrayList<>();




        public ItemViewHolder(View itemView,ItemViewHolderClicks listener) {
            super(itemView);
            mListener = listener;
            item_body = (RelativeLayout) itemView.findViewById(R.id.cloudsea_item_body_content);
            user_tx = (CircleImageView) itemView.findViewById(R.id.history_id_tx);
            user_name = (TextView) itemView.findViewById(R.id.history_id_username);
            history_date = (TextView) itemView.findViewById(R.id.history_id_time);
            history_content = (TextView) itemView.findViewById(R.id.history_id_content);
            btn_zan = (Button) itemView.findViewById(R.id.history_id_function_zan);
            btn_chat = (Button) itemView.findViewById(R.id.history_id_function_chat);
            btn_retweet = (Button) itemView.findViewById(R.id.history_id_function_retweet);
            ngiv = (NineGridImageView) itemView.findViewById(R.id.history_id_pics);
            item_loc = (RelativeLayout) itemView.findViewById(R.id.history_id_location);
            item_loc_content = (TextView) itemView.findViewById(R.id.history_id_location_text);

            user_tx.setOnClickListener(this);
            btn_zan.setOnClickListener(this);
            btn_chat.setOnClickListener(this);
            btn_retweet.setOnClickListener(this);
            item_body.setOnClickListener(this);

//            DynamicListNineAdapter dynamicListNineAdapter = new DynamicListNineAdapter();
//            ngiv.setAdapter(dynamicListNineAdapter);


        }

        //用户动态数据赋值
        public void getMUser(Dynamic dynamic){
            //
            setData(dynamic);

        }


        //设置所有控件的值
        private void setData(Dynamic dynamic){

            //测试list<String>
            List<String> imageurls = new ArrayList<>();

            if (dynamic.getImages()==null||"".equals(dynamic.getImages())){
                Log.i("ItemViewHolder", "此item中图片为空");
            }else {
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>(){}.getType();
                String images = dynamic.getImages();
                List<String> imagesurl = gson.fromJson(images,type);
                for (String image:imagesurl) {
                    String imageurl = "http://oc1souo4f.bkt.clouddn.com/" + image;
                    Log.i("ItemViewHolder", "ItemViewHolder->onSuccess:" + imageurl);

                    imageurls.add(imageurl);
                }
                DynamicListNineAdapter dynamicListNineAdapter = new DynamicListNineAdapter();
                ngiv.setAdapter(dynamicListNineAdapter);
                Log.i("ItemViewHolder", "setData:" + imageurls.size());

            }


            ngiv.setImagesData(imageurls);

            Picasso.with(context)
                    .load("http://oc1souo4f.bkt.clouddn.com/"+dynamic.getHeadportrait())
                    .placeholder(R.drawable.avatar_default)
                    .into(user_tx);
            user_name.setText(dynamic.getName());


            long curTime =System.currentTimeMillis() / 1000 ;

            long time = curTime - (dynamic.getTime().getTime()/1000);

            if (time < 3600*24 && time >= 0){
                history_date.setText(DateUtil.convertTimeToFormat(dynamic.getTime().getTime()/1000));
            }else if (time >= 3600 * 24 &&time < 3600 * 24 * 30 * 12){
                history_date.setText(DateUtil.formatDateInYear(dynamic.getTime().getTime()/1000) + "  " + DateUtil.getTime(dynamic.getTime().getTime()/1000));
            }else {
                history_date.setText(DateUtil.timeStampToStr(dynamic.getTime().getTime()/1000));
            }


            history_content.setText(dynamic.getText());
            btn_zan.setText(dynamic.getGood_c() + "");
            btn_chat.setText(dynamic.getComment_c() + "");
            btn_retweet.setText(dynamic.getRelay_c() + "");

            if (dynamic.getZan()==1){
                isliked = true;
                Drawable drawable = context.getResources().getDrawable(R.drawable.dynamic_liked,null);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                btn_zan.setCompoundDrawables(drawable,null,null,null);
            }else {
                isliked = false;
                Drawable drawable = context.getResources().getDrawable(R.drawable.history_btn_function_zan,null);
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                btn_zan.setCompoundDrawables(drawable,null,null,null);
            }

            if (dynamic.getGps()!=null && "".equals(dynamic.getGps())){
                item_loc.setVisibility(View.VISIBLE);
            }else {
                item_loc.setVisibility(View.GONE);
            }
            item_loc_content.setText(dynamic.getGps());

            dataMUser = dynamic;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.history_id_tx:
                    //点击头像
                    break;
                case R.id.history_id_function_zan:
                    mListener.onLikeClick(dataMUser,btn_zan);
                    //点赞
                    break;
                case R.id.history_id_function_chat:
                    mListener.onCommentClick(dataMUser);
                    //评论
                    break;
                case R.id.history_id_function_retweet:
                    //转发
                    break;
                case R.id.cloudsea_item_body_content:
                    //点击item跳转到详情
                    mListener.onItemClick(dataMUser);
                    break;

                default:
                    Log.i("ItemViewHolder", "ItemViewHolder->onClick 点击了");
                    break;
            }
        }

    }

    interface ItemViewHolderClicks{
        //单击整个item跳转到用户界面，需要传递uid
        void onItemClick(Dynamic dynamic);

        //回复评论
        void onCommentClick(Dynamic dynamic);

        void onLikeClick(Dynamic dynamic,Button like);

        void onFollowStatusChange(Dynamic dynamic, int position);
        //关注按钮,需要更新按钮的状态
    }
    public class FootViewHolder extends RecyclerView.ViewHolder {

        LinearLayout loadingview;
        TextView disloadview;

        public FootViewHolder(View view) {
            super(view);
            loadingview = (LinearLayout) view.findViewById(R.id.recycler_footer_loading);
            disloadview = (TextView) view.findViewById(R.id.recycler_footer_disload);
        }
    }


}

