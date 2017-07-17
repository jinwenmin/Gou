package com.example.king.gou.bean;

/**
 * Created by king on 2017/7/17.
 */

public class TeamUserInfo {
    int uid;
    int mine;//是否当前查询团队的最上级用户1：是0：否
    int utype;//用户类型 2,代理用户 3.普通用户
    String name;
    double amount;
    int rebate_id;
    double rate;
    String created;
    String login;
    int rtype;
    int status;//用户状态 /*0：离线，代表颜色#000000 1：在线，代表颜色#00FF00*/
    int zu;/*是否当前用户的直属下级0：不是1：是*/
    int totalElements;//总记录数

    @Override
    public String toString() {
        return "TeamUserInfo{" +
                "uid=" + uid +
                ", mine=" + mine +
                ", utype=" + utype +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", rebate_id=" + rebate_id +
                ", rate=" + rate +
                ", created='" + created + '\'' +
                ", login='" + login + '\'' +
                ", rtype=" + rtype +
                ", status=" + status +
                ", zu=" + zu +
                ", totalElements=" + totalElements +
                '}';
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getMine() {
        return mine;
    }

    public void setMine(int mine) {
        this.mine = mine;
    }

    public int getUtype() {
        return utype;
    }

    public void setUtype(int utype) {
        this.utype = utype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getRebate_id() {
        return rebate_id;
    }

    public void setRebate_id(int rebate_id) {
        this.rebate_id = rebate_id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getRtype() {
        return rtype;
    }

    public void setRtype(int rtype) {
        this.rtype = rtype;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getZu() {
        return zu;
    }

    public void setZu(int zu) {
        this.zu = zu;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
