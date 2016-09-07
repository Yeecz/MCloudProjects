package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MyConcerns implements Serializable {
    private  int pic;
    private  String name;
    private  String dongtai;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public  String getDongtai() {
        return dongtai;
    }



    public void setDongtai(String dongtai) {
        this.dongtai = dongtai;
    }

    public  int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyConcerns{" +
                "pic=" + pic +
                ", name='" + name + '\'' +
                ", dongtai='" + dongtai + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

    public MyConcerns(int pic, String name, String dongtai, boolean isCheck ){
        this.pic=pic;
        this.name=name;
        this.dongtai=dongtai;
        this.isChecked=isChecked;


    }

}
