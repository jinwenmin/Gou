package com.example.king.gou.bean;

import java.math.BigDecimal;

/**
 * Created by king on 2017/6/18.
 */

public class UserInfo {
    /*{amount=0.0, uname=testapp, rate=12.3, retainInfo=您未设置预保留验证信息, nname=测试APP, hasSafetyPassword=true, hasQuestions=false, login=2017-06-18 17:18:54, samount=0.0, email=1027956041@qq.com}*/
    String uname;
    String nname;
    String email;
    BigDecimal rate;
    BigDecimal amount;
    BigDecimal samount;
    String login;
    String retainInfo;
    boolean hasSafetyPassword;
    boolean hasQuestions;
    String question;
    boolean shares;
    int rtype;

    @Override
    public String toString() {
        return "UserInfo{" +
                "uname='" + uname + '\'' +
                ", nname='" + nname + '\'' +
                ", email='" + email + '\'' +
                ", rate=" + rate +
                ", amount=" + amount +
                ", samount=" + samount +
                ", login='" + login + '\'' +
                ", retainInfo='" + retainInfo + '\'' +
                ", hasSafetyPassword=" + hasSafetyPassword +
                ", hasQuestions=" + hasQuestions +
                ", question='" + question + '\'' +
                ", shares=" + shares +
                ", rtype=" + rtype +
                '}';
    }

    public boolean isShares() {
        return shares;
    }

    public void setShares(boolean shares) {
        this.shares = shares;
    }

    public int getRtype() {
        return rtype;
    }

    public void setRtype(int rtype) {
        this.rtype = rtype;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSamount() {
        return samount;
    }

    public void setSamount(BigDecimal samount) {
        this.samount = samount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRetainInfo() {
        return retainInfo;
    }

    public void setRetainInfo(String retainInfo) {
        this.retainInfo = retainInfo;
    }

    public boolean isHasSafetyPassword() {
        return hasSafetyPassword;
    }

    public void setHasSafetyPassword(boolean hasSafetyPassword) {
        this.hasSafetyPassword = hasSafetyPassword;
    }

    public boolean isHasQuestions() {
        return hasQuestions;
    }

    public void setHasQuestions(boolean hasQuestions) {
        this.hasQuestions = hasQuestions;
    }
}
