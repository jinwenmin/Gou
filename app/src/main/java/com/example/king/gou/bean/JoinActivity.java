package com.example.king.gou.bean;

/**
 * Created by king on 2017/7/23.
 */

public class JoinActivity {
    boolean rc;
    String msg;
    int id;
    String others;

    @Override
    public String toString() {
        return "JoinActivity{" +
                "rc=" + rc +
                ", msg='" + msg + '\'' +
                ", id=" + id +
                ", others='" + others + '\'' +
                '}';
    }

    public boolean isRc() {

        return rc;
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
