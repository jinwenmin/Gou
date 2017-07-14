package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/6/27.
 */

public class RestultInfo {
    boolean rc;
    String msg;
    boolean state;
    String message;

    @Override
    public String toString() {
        return "RestultInfo{" +
                "rc=" + rc +
                ", msg='" + msg + '\'' +
                ", state=" + state +
                ", message='" + message + '\'' +
                '}';
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

}
