package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

/**
 * Created by zn on 2016/8/9.
 */
public class Mylinked implements Serializable {
    private long id;                       //    id
    private int user_id;                 //    用户id
    private int frident_id;             //   好友id
    private String user_name;              //    名称昵称
    private String user_image;             //    头像
    private String beizhu;                 //    备注名
    private int ver;            //    版本
    private String sortLetters;          //开头首字母
    private String userwords;             //语句

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFrident_id() {
        return frident_id;
    }

    public void setFrident_id(int frident_id) {
        this.frident_id = frident_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getUserwords() {
        return userwords;
    }

    public void setUserwords(String userwords) {
        this.userwords = userwords;
    }

    public Mylinked(long id, int user_id, int frident_id, String user_name, String user_image,String beizhu, int ver, String sortLetters, String userwords) {
        this.id = id;
        this.user_id = user_id;
        this.frident_id = frident_id;
        this.user_name = user_name;
        this.beizhu = beizhu;
        this.user_image = user_image;
        this.ver = ver;
        this.sortLetters = sortLetters;
        this.userwords = userwords;
    }

    @Override
    public String toString() {
        return "Mylinked{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", frident_id=" + frident_id +
                ", user_name='" + user_name + '\'' +
                ", user_image='" + user_image + '\'' +
                ", beizhu='" + beizhu + '\'' +
                ", ver=" + ver +
                ", sortLetters='" + sortLetters + '\'' +
                ", userwords='" + userwords + '\'' +
                '}';
    }
}
