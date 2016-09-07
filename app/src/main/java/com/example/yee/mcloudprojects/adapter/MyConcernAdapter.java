package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.activity.MyConcern;
import com.example.yee.mcloudprojects.entity.MyConcerns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MyConcernAdapter extends BaseAdapter {
    List<MyConcerns> mList;
    Context mContext;
    LayoutInflater mInflater;


    public MyConcernAdapter(Context mContext, List<MyConcerns> mList) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);


    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder {
        RelativeLayout R1;
        TextView content, TV2;
        ImageView iv;
        CheckBox checkbox;

    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder mHolder;
        if (view == null) {
            mHolder = new ViewHolder();
            view = mInflater.inflate(R.layout.myconcern_item, null);
            mHolder.R1= (RelativeLayout) view.findViewById(R.id.R1);
            mHolder.content = (TextView) view.findViewById(R.id.content);
            mHolder.iv = (ImageView) view.findViewById(R.id.iv);
            mHolder.TV2 = (TextView) view.findViewById(R.id.TV2);
            mHolder.checkbox= (CheckBox) view.findViewById(R.id.checkbox);

            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();

        }
        MyConcerns myConcern = mList.get(i);
        mHolder.iv.setImageResource(myConcern.getPic());
        mHolder.content.setText(myConcern.getName());
        mHolder.TV2.setText(myConcern.getDongtai());
        //mHolder.checkbox.setChecked(myConcern.isChecked());
        //每次调用getView方法都会自动调用，不管用户有没有单击

        mHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mList.get(i).setChecked(true);
                } else {
                    mList.get(i).setChecked(false);
                }
            }
        });
        //必须写在事件之后
        if (mList.get(i).isChecked()) {
            //当前行被选中
            mHolder.checkbox.setChecked(true);
        } else {
            mHolder.checkbox.setChecked(false);
        }


        return view;
    }


}
