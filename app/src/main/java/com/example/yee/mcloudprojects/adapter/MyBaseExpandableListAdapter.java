package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.Mygroupchild;

import java.util.List;
import java.util.Map;

/**
 * Created by zn on 2016/8/18.
 */
public class MyBaseExpandableListAdapter extends BaseExpandableListAdapter implements View.OnClickListener {
    Map<Integer, List<Mygroupchild>> map;
    Context context;
    List<Integer> li;
    List<Mygroupchild> list1;
    public MyBaseExpandableListAdapter(Map<Integer, List<Mygroupchild>> map, Context context, List<Integer> li, List<Mygroupchild> list1) {
           this.map=map;
           this.context=context;
           this.li=li;
           this.list1=list1;
    }
    @Override
    public int getGroupCount() {
        return li.size();
    }

    @Override
    public int getChildrenCount(int i) {

        return map.get(li.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return li.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return (map.get(li.get(i)).get(i1));
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.fragment_f1_header,null);
        TextView text= (TextView) view.findViewById(R.id.text);
        if(li.get(i)==0){
           text.setText("我管理的群");
        }else if(li.get(i)==1){
            text.setText("我加入的群");
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.fragment_f1_body,null);
        ImageView im= (ImageView) view.findViewById(R.id.imageCircle);
        TextView t= (TextView) view.findViewById(R.id.title);
        im.setImageResource((map.get(li.get(i)).get(i1)).getImageview());
        t.setText((map.get(li.get(i)).get(i1)).getTitle());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void onClick(View view) {

    }


}
