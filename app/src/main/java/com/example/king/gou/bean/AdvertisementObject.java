package com.example.king.gou.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/22.
 */

public class AdvertisementObject implements Serializable {
    public String title;
    public String info;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    @Override

    public String toString() {
        return "AdvertisementObject{" +
                "title='" + title + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}