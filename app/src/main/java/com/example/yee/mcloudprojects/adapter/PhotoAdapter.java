package com.example.yee.mcloudprojects.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yee.mcloudprojects.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Yee on 2016/8/9.
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private ArrayList<String> photopaths = new ArrayList<>();
    private LayoutInflater inflater;
    private Context mContext;

    public PhotoAdapter(ArrayList<String> photopaths, Context context) {
        this.photopaths = photopaths;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.__picker_item_photo,parent,false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Uri uri = Uri.fromFile(new File(photopaths.get(position)));
        Glide.with(mContext)
                .load(uri)
                .centerCrop()
                .thumbnail(01f)
                .placeholder(R.drawable.__picker_ic_photo_black_48dp)
                .error(R.drawable.__picker_ic_broken_image_black_48dp)
                .into(holder.ivPhoto);
    }

    @Override
    public int getItemCount() {
        return photopaths.size();
    }


    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;
        private View vSelected;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            vSelected.setVisibility(View.GONE);
        }
    }
}
