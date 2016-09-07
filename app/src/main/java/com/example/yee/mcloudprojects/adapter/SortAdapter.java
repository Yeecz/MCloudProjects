package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.Mylinked;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by zn on 2016/8/9.
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<Mylinked> list;
    private Context mContext;
    public SortAdapter(Context mContext, List<Mylinked> list) {
        this.mContext = mContext;
        this.list = list;
    }
    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     */

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    final static class ViewHolder {
        ImageView imageView;
        TextView tvLetter;
        TextView tvTitle;
        TextView tvTitle2;
    }
    //
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        final Mylinked mContent = list.get(i);
        if(view==null){
            viewHolder = new ViewHolder();
            //调用linked布局
            view = LayoutInflater.from(mContext).inflate(R.layout.item_linked, null);
            viewHolder.imageView= (ImageView) view.findViewById(R.id.imageCircle);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.tvTitle2 = (TextView) view.findViewById(R.id.title2);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(i);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(i == getPositionForSection(section)){
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());

        }else{
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        //填充数据
        if(list.get(i).getBeizhu()==null || list.get(i).getBeizhu().equals("")){
            viewHolder.tvTitle.setText(list.get(i).getUser_name());

        }else{
            viewHolder.tvTitle.setText(list.get(i).getBeizhu());
        }
        viewHolder.tvTitle2.setText(this.list.get(i).getUserwords());
       ImageOptions imageOptions = new ImageOptions.Builder()
                .setLoadingDrawableId(R.drawable.img_loading_bg)
               .setCircular(true)
              .build();
       //viewHolder.imageView.setImageResource(this.list.get(i).getUser_image());
       x.image().bind(viewHolder.imageView,"http://oc1souo4f.bkt.clouddn.com/"+this.list.get(i).getUser_image(),imageOptions);
        return view;
    }
    //数据库


    @Override
    public Object[] getSections() {
        return null;
    }
    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    @Override
    public int getSectionForPosition(int i) {
        return list.get(i).getSortLetters().charAt(0);
    }
    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */

}
