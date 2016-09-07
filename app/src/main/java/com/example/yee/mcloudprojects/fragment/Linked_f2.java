package com.example.yee.mcloudprojects.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.adapter.MyBaseExpandableListAdapter;
import com.example.yee.mcloudprojects.entity.Mygroupchild;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;


public class Linked_f2 extends Fragment {
    ExpandableListView ev;
    MyBaseExpandableListAdapter myb;
    List<Integer> li;
    List<Mygroupchild> list2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_linked_f2, container, false);
        ButterKnife.bind(this, view);
        ev= (ExpandableListView) view.findViewById(R.id.expandableListView);

        initdata();
        addlistener();
        return view;
    }
    private void addlistener() {
        ev.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
              //  map.get(li.get(i)).get(i1)
                Toast.makeText(getActivity(), "你单击了："+childPosition,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private void initdata() {
        Map<Integer,List<Mygroupchild>> map=new HashMap<Integer, List<Mygroupchild>>();
        li=new ArrayList<>();
        li.add(0);
        li.add(1);
        //连个list用来和
        List<Mygroupchild> list1 = new ArrayList<>();
        list1.add(new Mygroupchild("loag",R.drawable.logo));
        list1.add(new Mygroupchild("loag",R.drawable.logo));
        list1.add(new Mygroupchild("loag",R.drawable.logo));
        list1.add(new Mygroupchild("loag",R.drawable.logo));
        map.put(0,list1);
        list2 = new ArrayList<>();
        list2.add(new Mygroupchild("loag",R.drawable.logo));
        list2.add(new Mygroupchild("loag",R.drawable.logo));
        list2.add(new Mygroupchild("loag",R.drawable.logo));

        map.put(1,list2);
        myb=new MyBaseExpandableListAdapter(map,getActivity(),li,list1);
        ev.setAdapter(myb);
        int groupCount = ev.getCount();

        for (int i=0; i<groupCount; i++) {
            ev.expandGroup(i);

        };

    }


}
