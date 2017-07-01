package com.example.king.gou.bean;

/**
 * Created by king on 2017/7/1.
 */

public class GameType {
    int gid;
    String name;
    int tid;

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    @Override
    public String toString() {
        return "GameType{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", tid=" + tid +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }
}
