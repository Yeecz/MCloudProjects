package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

/**
 * Created by xujidong on 2016/8/26.
 */
public class Photo implements Serializable {
    private int id;
    private String pic;

    public Photo(int id, String pic) {
        this.id = id;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", pic='" + pic + '\'' +
                '}';
    }
}
