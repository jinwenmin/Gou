package com.example.king.gou.bean;

/**
 * Created by king on 2017/8/26.
 */

public class HistoryGames {
    String name;
    int gid;
    int img;
    int count;

    @Override
    public String toString() {
        return "HistoryGames{" +
                "name='" + name + '\'' +
                ", gid=" + gid +
                ", img=" + img +
                ", count=" + count +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
