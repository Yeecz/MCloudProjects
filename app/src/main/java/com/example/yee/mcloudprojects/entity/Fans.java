package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

/**
 * Created by xujidong on 2016/8/18.
 */
public class Fans implements Serializable {
    private int touxiang;
    private String fansName;
    private String dynamic;

    public Fans( int touxiang, String fansName, String dynamic ) {
        this.touxiang =touxiang;
        this.fansName = fansName;
        this.dynamic = dynamic;

    }

    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public String getFansName() {
        return fansName;
    }

    public void setFansName(String fansName) {
        this.fansName = fansName;
    }

    public int getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(int touxiang) {
        this.touxiang = touxiang;
    }

    @Override
    public String toString() {
        return "Fans{" +
                "dynamic='" + dynamic + '\'' +
                ", touxiang=" + touxiang +
                ", fansName='" + fansName + '\'' +
                '}';
    }
}
