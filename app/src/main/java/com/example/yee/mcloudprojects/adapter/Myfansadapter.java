package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.MyfansItems;

import java.util.List;

/**
 * Created by zn on 2016/8/25.
 */
public class Myfansadapter extends BaseAdapter{
    Context context;
    List<MyfansItems> list;
    boolean isFling;//是否快速滑动
    public void setFling(boolean fling) {
        isFling = fling;
    }
    public Myfansadapter(Context context, List list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    class ViewHolder{
        ImageView imageView;
        TextView tx1;
        TextView tx2;
        CheckBox checkBox;
}
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.myfans_items,null);
            viewHolder.imageView= (ImageView) view.findViewById(R.id.imageCircle);
            viewHolder.tx1= (TextView) view.findViewById(R.id.title);
            viewHolder.tx2= (TextView) view.findViewById(R.id.title2);
            viewHolder.checkBox= (CheckBox) view.findViewById(R.id.c_author);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }

     //   viewHolder.imageView.setImageResource(R.drawable.aaa);
        //viewHolder.tx1.setText("asd");
        viewHolder.tx2.setText("sad");
        //根据是否快速滑动状态决定是否加载网络数据
        if (isFling){
            //快速滑动:不需要加载数据
            viewHolder.imageView.setImageResource(R.drawable.img_loading_bg);
            //快速滑动，不需要加载图片，使用默认图片，同时把当前视图的标志位置为图片的url
            viewHolder.imageView.setTag(list.get(i).getDrawableIndex());
            viewHolder.tx1.setText("加载中");
            //快速滑动,统一显示文本内容为加载中，同时把正确的文字内容作为tab
            viewHolder.tx1.setTag(list.get(i).getContent());
        }else{
            //需要加载数据
            viewHolder.tx1.setText(list.get(i).getContent());
           // mViewHolder.mTextView.setTag(null);
           viewHolder.imageView.setImageResource(list.get(i).getDrawableIndex());
            //文本框便签为null表示正确加载数据
           // viewHolder.tx1.setText("asd");
           // viewHolder.tx2.setText("sad");
        }


        //每次调用getView方法都会自动调用，不管用户有没有单击
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    list.get(i).setChecked(true);
                } else {
                    list.get(i).setChecked(false);
                }
            }
        });
        //必须写在事件之后
        if (list.get(i).isChecked()) {
            //当前行被选中
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }


        return view;
    }


}
