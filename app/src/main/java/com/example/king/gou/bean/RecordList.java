package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/7/20.
 */

public class RecordList {
    int gid;
    String gameName;
    String period;
    String winningNumber;
    String drawDate;

    @Override
    public String toString() {
        return "RecordList{" +
                "gid=" + gid +
                ", gameName='" + gameName + '\'' +
                ", period='" + period + '\'' +
                ", winningNumber='" + winningNumber + '\'' +
                ", drawDate='" + drawDate + '\'' +
                '}';
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getWinningNumber() {
        return winningNumber;
    }

    public void setWinningNumber(String winningNumber) {
        this.winningNumber = winningNumber;
    }

    public String getDrawDate() {
        return drawDate;
    }

    public void setDrawDate(String drawDate) {
        this.drawDate = drawDate;
    }
}
