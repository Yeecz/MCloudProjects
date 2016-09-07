package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zn on 2016/8/10.
 */
public class FragmentsAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    Context context;

    public FragmentsAdapter(FragmentManager fm) {
        super(fm);
    }
    public FragmentsAdapter(FragmentManager fragmentManager,
                          ArrayList<Fragment> fragments){
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
