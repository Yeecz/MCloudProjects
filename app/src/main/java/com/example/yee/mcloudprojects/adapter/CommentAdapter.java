package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yee.mcloudprojects.CircleImageView;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.CommentBean;
import com.example.yee.mcloudprojects.entity.Dynamic;
import com.example.yee.mcloudprojects.utils.DateUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yee on 2016/8/24.
 */
public class CommentAdapter extends BaseAdapter {


    Context context;

    List<CommentBean> commentBeanList = new ArrayList<>();
    LayoutInflater inflater;

    public CommentAdapter(Context context,List<CommentBean> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (commentBeanList == null) ? 0 : commentBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return (commentBeanList == null) ? null : commentBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (commentBeanList == null) ? 0 : i;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CommentViewHolder commentViewHolder;
        int type = commentBeanList.get(i).getLevel();

        if (view == null) {

            view = inflater.inflate(R.layout.comment_item, null);
            commentViewHolder = new CommentViewHolder();
            commentViewHolder.c_author_tx = (CircleImageView) view.findViewById(R.id.c_author_tx);
            commentViewHolder.author_name = (TextView) view.findViewById(R.id.c_author);
            commentViewHolder.responview = (LinearLayout) view.findViewById(R.id.comment_item_responview);
            commentViewHolder.responser = (TextView) view.findViewById(R.id.c_responser);
            commentViewHolder.time = (TextView) view.findViewById(R.id.c_time);
            commentViewHolder.commentContent = (TextView) view.findViewById(R.id.c_content);


            view.setTag(commentViewHolder);
        } else {

            commentViewHolder = (CommentViewHolder) view.getTag();

        }

        CommentBean commentBean = commentBeanList.get(i);
        long curTime =System.currentTimeMillis() / (long) 1000 ;
        long time = curTime - commentBean.getTime().getTime()/1000;
        //评论者头像
        Picasso.with(context)
                .load("http://oc1souo4f.bkt.clouddn.com/" + commentBean.getAuthor_head())
                .placeholder(R.drawable.surprise_toolbar_picture)
                .into( commentViewHolder.c_author_tx);
        //评论者名称
        commentViewHolder.author_name.setText(commentBean.getAuthor_name());
        //评论内容
        commentViewHolder.commentContent.setText(commentBean.getText());
        //评论时间
//        commentViewHolder.time.setText(commentBean.getTime().toLocaleString());

        if (time < 3600*24 && time >= 0){
            commentViewHolder.time.setText(DateUtil.convertTimeToFormat(commentBean.getTime().getTime()/1000));
        }else if (time >= 3600 * 24 &&time < 3600 * 24 * 30 * 12){
            commentViewHolder.time.setText(DateUtil.formatDateInYear(commentBean.getTime().getTime()/1000) + "  " + DateUtil.getTime(commentBean.getTime().getTime()/1000));
        }else if (time >= 3600 * 24 * 30 * 12){
            commentViewHolder.time.setText(DateUtil.timeStampToStr(commentBean.getTime().getTime()/1000));
        }

        if (type==1){
            commentViewHolder.responview.setVisibility(View.VISIBLE);
            commentViewHolder.responser.setText(commentBean.getResponser_name());
        }else {
            commentViewHolder.responview.setVisibility(View.GONE);
        }



        return view;
    }


    class CommentViewHolder {
        CircleImageView c_author_tx;
        TextView author_name, time, commentContent;
        LinearLayout responview;
        TextView responser;

    }
}
