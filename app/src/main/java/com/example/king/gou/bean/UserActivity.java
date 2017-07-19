package com.example.king.gou.bean;

/**
 * Created by king on 2017/7/19.
 */

public class UserActivity {
    int aid;
    String name;
    int others;
String msg;

    @Override
    public String toString() {
        return "UserActivity{" +
                "aid=" + aid +
                ", name='" + name + '\'' +
                ", others=" + others +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOthers() {
        return others;
    }

    public void setOthers(int others) {
        this.others = others;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
