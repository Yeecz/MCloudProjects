package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


//实例化接口
public class Users implements Serializable {
    private String name;
    //好友类别分组--父
    private ArrayList<String> group;
    //好友分组 --子
    private ArrayList<ArrayList<String>> child;
    private String touxiang;
    private String words;

    //构造方法
    public Users(String name, String touxiang, String words) {
        this.name = name;
        this.touxiang = touxiang;
        this.words = words;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }


}