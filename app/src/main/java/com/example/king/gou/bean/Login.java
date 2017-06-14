package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/6/14.
 */

public class Login {
    int uid;
    int freeze;
    int status;
    boolean unsignin;
    boolean state;
    String targetUrl;
String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "Login{" +
                "uid=" + uid +
                ", freeze=" + freeze +
                ", status=" + status +
                ", unsignin=" + unsignin +
                ", state=" + state +
                ", targetUrl='" + targetUrl + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public boolean isUnsignin() {
        return unsignin;
    }

    public void setUnsignin(boolean unsignin) {
        this.unsignin = unsignin;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
