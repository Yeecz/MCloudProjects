package com.example.yee.mcloudprojects.entity;

/**
 * Created by zn on 2016/8/18.
 */
public class Mygroupchild {
    private  String title;
    private  int imageview;

    public Mygroupchild(String title, int imageview) {
        this.title = title;
        this.imageview = imageview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageview() {
        return imageview;
    }

    public void setImageview(int imageview) {
        this.imageview = imageview;
    }
}
