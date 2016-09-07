package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.location.Poi;
import com.example.yee.mcloudprojects.R;

import java.util.List;

/**
 * Created by Yee on 2016/8/10.
 */
public class MyPoiAdapter extends BaseAdapter {

    List<Poi> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public MyPoiAdapter(List<Poi> list, Context context) {
        this.mList = list;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (mList==null)?0:mList.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return (mList==null)?null:mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    class ViewHold{
        TextView tv;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold =null;
        if (view == null){
            view = mLayoutInflater.inflate(R.layout.poi_item,null);
            viewHold = new ViewHold();
            TextView tv = (TextView) view.findViewById(R.id.poi_tv);

            viewHold.tv = tv;

            view.setTag(viewHold);
        }else {
            viewHold = (ViewHold) view.getTag();
        }
        if (i==0){
            viewHold.tv.setText("不显示位置");
        }else {
            viewHold.tv.setText(mList.get(i-1).getName());
        }


        return view;
    }
}
