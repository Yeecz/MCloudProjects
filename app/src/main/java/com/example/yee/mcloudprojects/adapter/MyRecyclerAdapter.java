package com.example.yee.mcloudprojects.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.Albums;
import com.example.yee.mcloudprojects.entity.OnRecyclerViewItemClickListener;
import com.example.yee.mcloudprojects.entity.PaletteManager;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> implements View.OnClickListener {

    private List<Albums> mAlbums;
    private OnRecyclerViewItemClickListener<Albums> itemClickListener;
    private int mAlbumsLayout;
    private PaletteManager paletteManager = new PaletteManager();


    public MyRecyclerAdapter(List<Albums> mAlbums, int mAlbumsLayout) {
        this.mAlbums = mAlbums;
        this.mAlbumsLayout = mAlbumsLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mAlbumsLayout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Albums mAlbum = mAlbums.get(position);

        holder.itemView.setTag(mAlbum);
        holder.albumName.setText(mAlbum.getName());

       // holder.albumNumbers.setText(mAlbum.getAlbumDescribe());
       // holder.albumAuthority.setText(mAlbum.getId());
//        Picasso.with(holder.albumCover.getContext()).load(mAlbum.getPic()).into(holder.albumCover, new Callback() {
//            @Override
//            public void onSuccess() {
//                holder.updatePalette(paletteManager);
//            }
//
//            @Override
//            public void onError() {
//            }
//        });
    }

    @Override
    public int getItemCount()  {
        return mAlbums.size();
    }

    @Override
    public void onClick(View view) {
        if (itemClickListener != null) {
            Albums mAlbums = (Albums) view.getTag();
            itemClickListener.onItemClick(view, mAlbums);
        }
    }

    public void add(Albums mAlbum, int position) {
        mAlbums.add(position, mAlbum);
        notifyItemInserted(position);
    }

    public void remove(Albums mAlbum) {
        int position = mAlbums.indexOf(mAlbum);
        mAlbums.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<Albums> listener) {
        this.itemClickListener = listener;
    }

//    private static int setColorAlpha(int color, int alpha) {
//        return (alpha << 24) | (color & 0x00ffffff);
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView albumCover;
        public TextView albumName;
        //albumNumbers, albumAuthority;


        public ViewHolder(View itemView) {
            super(itemView);
            albumCover = (ImageView) itemView.findViewById(R.id.album_cover);
            albumName = (TextView) itemView.findViewById(R.id.album_name);
            //   albumNumbers = (TextView)itemView.findViewById(R.id.album_numbers);
            //  albumAuthority = (TextView) itemView.findViewById(R.id.album_authority);
        }


        public void updatePalette(PaletteManager paletteManager) {
            String key = ((Albums) itemView.getTag()).getBackground();
            Bitmap bitmap = ((BitmapDrawable) albumCover.getDrawable()).getBitmap();
            paletteManager.getPalette(key, bitmap, new PaletteManager.Callback() {
                @Override
                public void onPaletteReady(Palette palette) {//                   int bgColor = palette.getDarkVibrantColor(0x7DD1FF);
//                    albumName.setBackgroundColor(setColorAlpha(bgColor, 192));
//                    albumName.setTextColor(palette.getLightMutedColor(0x7DD1FF));
//                    albumNumbers.setBackgroundColor(setColorAlpha(bgColor, 192));
//                    albumNumbers.setTextColor(palette.getLightMutedColor(0x7DD1FF));
//                    albumAuthority.setBackgroundColor(setColorAlpha(bgColor, 192));
//                    albumAuthority.setTextColor(palette.getLightMutedColor(0x7DD1FF));
                }
            });
        }
    }
}