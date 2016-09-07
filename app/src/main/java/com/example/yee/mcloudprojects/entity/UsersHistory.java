package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Yee on 2016/7/18.
 */
public class UsersHistory implements Serializable {

    private int Usertx;
    private int History_id;
    private String Username;
    private String Historydate;
    private String HistoryContent;
    private int btn_zan;
    private int btn_chat;
    private int btn_retweet;
    private String[] pics;

    public  UsersHistory(){

    }

    public String[] getPics() {
        return pics;
    }

    public void setPics(String[] pics) {
        this.pics = pics;
    }

    public int getUsertx() {
        return Usertx;
    }

    public void setUsertx(int usertx) {
        Usertx = usertx;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getHistorydate() {
        return Historydate;
    }

    public void setHistorydate(String historydate) {
        Historydate = historydate;
    }

    public String getHistoryContent() {
        return HistoryContent;
    }

    public void setHistoryContent(String historyContent) {
        HistoryContent = historyContent;
    }

    public int getBtn_zan() {
        return btn_zan;
    }

    public void setBtn_zan(int btn_zan) {
        this.btn_zan = btn_zan;
    }

    public int getBtn_chat() {
        return btn_chat;
    }

    public void setBtn_chat(int btn_chat) {
        this.btn_chat = btn_chat;
    }

    public int getBtn_retweet() {
        return btn_retweet;
    }

    public void setBtn_retweet(int btn_retweet) {
        this.btn_retweet = btn_retweet;
    }

    public int getHistory_id() {
        return History_id;
    }

    public void setHistory_id(int history_id) {
        History_id = history_id;
    }
}
