package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/6/27.
 */

public class RestultInfo {
    boolean rc;
    String msg;

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

    @Override
    public String toString() {
        return "RestultInfo{" +
                "rc=" + rc +
                ", msg='" + msg + '\'' +
                '}';
    }
}
