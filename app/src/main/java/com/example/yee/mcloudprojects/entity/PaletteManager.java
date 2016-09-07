package com.example.yee.mcloudprojects.entity;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.support.v7.graphics.Palette;

/**
 * Created by xujidong on 2016/8/23.
 */
public class PaletteManager {
    private LruCache<String, Palette> cache = new LruCache<>(150);

    public interface Callback {
        void onPaletteReady(Palette palette);
    }

    public void getPalette(final String key, Bitmap bitmap, final Callback callback) {
        Palette palette = cache.get(key);
        if (palette != null)
            callback.onPaletteReady(palette);
        else
            new Palette.Builder(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    cache.put(key, palette);
                    callback.onPaletteReady(palette);
                }
            });
    }
}
