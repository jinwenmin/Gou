package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/7/12.
 */

public class BettingSync {
    int gid;
    String period;
    long drawTime;
    long condownTime;
    int count;
    String property;
    String winningNumber;
boolean rc;

    @Override
    public String toString() {
        return "BettingSync{" +
                "gid=" + gid +
                ", period='" + period + '\'' +
                ", drawTime=" + drawTime +
                ", condownTime=" + condownTime +
                ", count=" + count +
                ", property='" + property + '\'' +
                ", winningNumber='" + winningNumber + '\'' +
                ", rc=" + rc +
                '}';
    }

    public boolean isRc() {
        return rc;
    }

    public void setRc(boolean rc) {
        this.rc = rc;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getWinningNumber() {
        return winningNumber;
    }

    public void setWinningNumber(String winningNumber) {
        this.winningNumber = winningNumber;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public long getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(long drawTime) {
        this.drawTime = drawTime;
    }

    public long getCondownTime() {
        return condownTime;
    }

    public void setCondownTime(long condownTime) {
        this.condownTime = condownTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
