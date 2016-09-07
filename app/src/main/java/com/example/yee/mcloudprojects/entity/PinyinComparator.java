package com.example.yee.mcloudprojects.entity;

import java.util.Comparator;

/**
 * Created by zn on 2016/8/9.
 */
public class PinyinComparator implements Comparator<Mylinked> {
    public int compare(Mylinked o1, Mylinked o2) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
