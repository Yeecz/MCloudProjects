package com.example.yee.mcloudprojects.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.example.yee.mcloudprojects.CloudSeaModuleActivity;
import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.activity.ShowDynamicActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yee on 2016/8/25.
 */
public class ShowImagesFragment extends Fragment {

    Toolbar toolbar;
    ViewPager viewPager;
    View view;
    List<String> imagesurls = new ArrayList<>();
    int index;
    String toolBarTitle;
    TextView pager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        container.removeView(view);
        view = inflater.inflate(R.layout.dynamic_viewpager_fragment,container,false);
        initView();
        initData();
        initAdapter();

        return view;
    }

    private void initData() {
        imagesurls.clear();
        index = 0;
        Bundle bundle = getArguments();

        if (bundle!=null){
            List<String> list = (List<String>) bundle.getSerializable("imagesUrls");
            index = bundle.getInt("index");
            imagesurls.addAll(list);
        }

        pager.setText((index+1) + "/" + imagesurls.size());
    }

    private void initAdapter() {


        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return imagesurls.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {

                return view == object;
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_item,null);

                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                PhotoView photoView = (PhotoView) view.findViewById(R.id.viewpager_item);

                PhotoView photoView = new PhotoView(getActivity());
                photoView.enable();
                photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                photoView.setLayoutParams(ll);
                photoView.setClickable(true);
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getActivity() instanceof CloudSeaModuleActivity){
                            CloudSeaModuleActivity cloudSeaModuleActivity = (CloudSeaModuleActivity) getActivity();
                            cloudSeaModuleActivity.hideBigImageView();
                        } else if (getActivity() instanceof ShowDynamicActivity) {
                            ShowDynamicActivity showDynamicActivity = (ShowDynamicActivity) getActivity();
                            showDynamicActivity.hideBigImageView();
                        }

                    }
                });
                Picasso.with(getActivity())
                        .load(imagesurls.get(position))
                        .placeholder(R.drawable.surprise_toolbar_picture)
                        .into(photoView);
                container.addView(photoView);
                return photoView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pager.setText((position+1) + "/" + imagesurls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(index);
    }

    private void initView() {
        viewPager = (ViewPager) view.findViewById(R.id.show_dynamic_viewpager);
        toolbar = (Toolbar) view.findViewById(R.id.dynamic_viewpager_toolbar);
        pager = (TextView) view.findViewById(R.id.show_dynamic_viewpager_position);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle("");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.BLACK);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (getActivity() instanceof CloudSeaModuleActivity){
                    CloudSeaModuleActivity cloudSeaModuleActivity = (CloudSeaModuleActivity) getActivity();
                    cloudSeaModuleActivity.hideBigImageView();
                } else if (getActivity() instanceof ShowDynamicActivity) {
                    ShowDynamicActivity showDynamicActivity = (ShowDynamicActivity) getActivity();
                    showDynamicActivity.hideBigImageView();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

