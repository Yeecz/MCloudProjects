package com.example.yee.mcloudprojects.adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yee.mcloudprojects.R;
import com.example.yee.mcloudprojects.entity.Album;
import com.example.yee.mcloudprojects.entity.OnRecyclerViewItemClickListener;
import com.example.yee.mcloudprojects.entity.PaletteManager;
import com.example.yee.mcloudprojects.entity.Photo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerPhotoAdapter extends RecyclerView.Adapter<MyRecyclerPhotoAdapter.ViewHolder> implements View.OnClickListener {

    private List<Album> mPhotos;
    private OnRecyclerViewItemClickListener<Album> itemClickListener;
    private int mPhotoLayout;
    private PaletteManager paletteManager = new PaletteManager();

    public MyRecyclerPhotoAdapter(List<Album> mPhotos, int mPhotoLayout) {
        this. mPhotos = mPhotos;
        this.mPhotoLayout = mPhotoLayout;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mPhotoLayout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(final ViewHolder holder, int position) {
        Album mPhoto = mPhotos.get(position);

        holder.itemView.setTag(mPhoto);

//        holder.albumName.setText(mPhoto.getId()));
//        holder.albumNumbers.setText(mAlbum.getNumbers());
//        holder.albumAuthority.setText(mAlbum.getAuthority());
      Picasso.with(holder.albumPhoto.getContext()).load("http://oc1souo4f.bkt.clouddn.com/"+mPhoto.getUrl()).into(holder.albumPhoto, new Callback() {
           @Override public void onSuccess() {
            holder.updatePalette(paletteManager);
           }

          @Override public void onError() {}
        });
    }

    @Override public int getItemCount() {
        return mPhotos.size();
    }

    @Override public void onClick(View view) {
        if (itemClickListener != null) {
            Album mPhotos = (Album) view.getTag();
            itemClickListener.onItemClick(view, mPhotos);
        }
    }

    public void add(Album mPhoto, int position) {
        mPhotos.add(position,mPhoto);
        notifyItemInserted(position);
    }

    public void remove(Album mPhoto) {
        int position = mPhotos.indexOf(mPhoto);
        mPhotos.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<Album> listener) {
        this.itemClickListener = listener;
    }

//    private static int setColorAlpha(int color, int alpha) {
//        return (alpha << 24) | (color & 0x00ffffff);
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView albumPhoto;



        public ViewHolder(View itemView) {
            super(itemView);
            albumPhoto = (ImageView)itemView.findViewById(R.id.album_photo);
//            albumName = (TextView) itemView.findViewById(R.id.album_name);
//            albumNumbers = (TextView)itemView.findViewById(R.id.album_numbers);
//            albumAuthority = (TextView) itemView.findViewById(R.id.album_authority);
        }

        public void updatePalette(PaletteManager paletteManager) {
            String key = ((Album)itemView.getTag()).getUrl();
            Bitmap bitmap = ((BitmapDrawable)albumPhoto.getDrawable()).getBitmap();
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