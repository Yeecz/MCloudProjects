package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

/**
 * Created by zn on 2016/8/25.
 */
public class MyfansItems implements Serializable {
    private int drawableIndex;
    private String content;
    //标记当前行的复选框是否被选中，防止界面缓存导致的错乱
    private boolean isChecked;
    public MyfansItems(int drawableIndex, String content, boolean isChecked) {
        this.drawableIndex = drawableIndex;
        this.content = content;
        this.isChecked = isChecked;
    }

    public int getDrawableIndex() {
        return drawableIndex;
    }

    public void setDrawableIndex(int drawableIndex) {
        this.drawableIndex = drawableIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "Item{" +
                "drawableIndex=" + drawableIndex +
                ", content='" + content + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
