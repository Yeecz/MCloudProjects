package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

/**
 * Created by xujidong on 2016/8/18.
 */
public class Item implements Serializable {
    private String date;
    private int pic;
    private String content;
    private String page;

    public Item( String date,int pic, String content,String page ) {
        this.content = content;
        this.date = date;
        this.page = page;
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Item{" +
                "content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", pic=" + pic +
                ", page='" + page + '\'' +
                '}';
    }
}
