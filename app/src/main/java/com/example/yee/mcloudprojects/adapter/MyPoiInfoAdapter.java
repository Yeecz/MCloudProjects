package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.example.yee.mcloudprojects.R;

import java.util.List;

/**
 * Created by Yee on 2016/8/10.
 */
public class MyPoiInfoAdapter extends BaseAdapter {

    List<PoiInfo> mList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public MyPoiInfoAdapter(List<PoiInfo> list, Context context) {
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
        TextView tv1;
        TextView tv2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHold viewHold =null;
        if (view == null){
            view = mLayoutInflater.inflate(R.layout.poiinfo_item,null);
            viewHold = new ViewHold();
            TextView tv1 = (TextView) view.findViewById(R.id.poiinfo_tv1);
            TextView tv2 = (TextView) view.findViewById(R.id.poiinfo_tv2);

            viewHold.tv1 = tv1;
            viewHold.tv2 = tv2;

            view.setTag(viewHold);
        }else {
            viewHold = (ViewHold) view.getTag();
        }

        if (i==0){
            viewHold.tv1.setText("不显示位置");
            viewHold.tv2.setVisibility(View.GONE);
        }else {
            viewHold.tv1.setText(mList.get(i-1).name);
            viewHold.tv2.setText(mList.get(i-1).address);
        }


        return view;
    }
}
