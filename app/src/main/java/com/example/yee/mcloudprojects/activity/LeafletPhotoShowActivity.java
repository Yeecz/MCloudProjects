package com.example.yee.mcloudprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.Photo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeafletPhotoShowActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    private static final String MOCK_URL = "http://lorempixel.com/800/400/nightlife/";
    @Bind(R.id.back_photo)
    ImageButton backPhoto;
    @Bind(R.id.album_name3)
    TextView albumName3;
    @Bind(R.id.photo_location)
    TextView photoLocation;
    @Bind(R.id.photo_viewpager)
    ViewPager photoViewpager;
    private ArrayList<View> list;
    private ArrayList<Photo> photoList;
    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;
    /**
     * 装ImageView数组
     */

    /**
     * 图片资源id
     */
    private int[] imgIdArray ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaflet_photo_show);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        //载入图片资源ID
        imgIdArray = new int[]{R.drawable.item01, R.drawable.item02, R.drawable.item03, R.drawable.item04,
                R.drawable.item05,R.drawable.item06, R.drawable.item07, R.drawable.item08};





        //将图片装载到数组中
        mImageViews = new ImageView[imgIdArray.length];
        for(int i=0; i<mImageViews.length; i++){
            ImageView imageView = new ImageView(this);
            mImageViews[i] = imageView;
            imageView.setBackgroundResource(imgIdArray[i]);
        }

        //设置Adapter
        photoViewpager.setAdapter(new MyAdapter());
        //设置监听，主要是设置点点的背景
        photoViewpager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        photoViewpager.setCurrentItem((mImageViews.length) * 100);

    }


    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
            return mImageViews[position % mImageViews.length];
        }



    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {

    }



    @OnClick({R.id.back_photo, R.id.album_name3, R.id.photo_location})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_photo:
                jumpN(view);

                break;
            case R.id.album_name3:
                break;
            case R.id.photo_location:
                break;
        }
    }
    private void jumpN(View view) {
        //显式跳转：跳转到jumpb
        Intent intent = new Intent( LeafletPhotoShowActivity.this,ShowAlbumActivity.class);
        startActivity(intent);

    }
}
