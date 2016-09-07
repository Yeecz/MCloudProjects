package com.example.yee.mcloudprojects.entity;

import java.io.Serializable;

/**
 * Created by Yee on 2016/8/28.
 */
public class MChatRoom implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MChatRoom{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
