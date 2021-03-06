package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.example.yee.mcloudprojects.CloudSeaModuleActivity;
import com.example.yee.mcloudprojects.R;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Yee on 2016/8/2.
 */
public class DynamicListNineAdapter extends NineGridImageViewAdapter<String> {

    ImageLoader imageLoader = ImageLoader.getInstance();
//    List<Info> infoList = new ArrayList<>();

    public DynamicListNineAdapter(){}

    @Override
    protected void onDisplayImage(Context context, ImageView imageView, String s) {
//        Picasso.with(context)
//                .load(s)
//                .placeholder(R.drawable.surprise_toolbar_picture)
//                .into(imageView);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.picture_default) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.surprise_toolbar_picture)//设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.drawable.error)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .build();//构建完成

        Log.i("DynamicListNineAdapter", "九宫格imageview:" + imageView.toString());
        imageLoader.displayImage(s,imageView,options);
//        Info mInfo = PhotoView.getImageViewInfo(imageView);
//        infoList.add(mInfo);
    }

    @Override
    protected void onItemImageClick(Context context, int index, List<String> list) {
        CloudSeaModuleActivity showpic = (CloudSeaModuleActivity) context;
//        showpic.showBigImageView(list.get(index),infoList.get(index));
        showpic.showViewPager(list,index);
        Log.i("DynamicListNineAdapter", "DynamicListNineAdapter->onItemImageClick:" + index);
    }

}
