package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/6/15.
 */

public class UserAmount {
//{rc=true, msg=, id=0.0, others=0.0}
    boolean rc;
    String msg;
    int id;
    String others;

    public boolean isRc() {
        return rc;
    }

    @Override
    public String toString() {
        return "UserAmount{" +
                "rc=" + rc +
                ", msg='" + msg + '\'' +
                ", id=" + id +
                ", others='" + others + '\'' +
                '}';
    }

    public void setRc(boolean rc) {
        this.rc = rc;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}
