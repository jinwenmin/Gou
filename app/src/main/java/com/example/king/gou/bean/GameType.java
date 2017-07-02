package com.example.king.gou.bean;

/**
 * Created by king on 2017/7/1.
 */

public class GameType {
    int gid;
    String name;
    int tid;
    int group_id;
    int ptid;

    @Override
    public String toString() {
        return "GameType{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", tid=" + tid +
                ", group_id=" + group_id +
                ", ptid=" + ptid +
                '}';
    }

    public int getPtid() {
        return ptid;
    }

    public void setPtid(int ptid) {
        this.ptid = ptid;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
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
