package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/7/20.
 */

public class GamePrize {
    int grid;
    String gameName;
    String ruleType;
    String ruleName;
    String prize;
    double userRate;

    @Override
    public String toString() {
        return "GamePrize{" +
                "grid=" + grid +
                ", gameName='" + gameName + '\'' +
                ", ruleType='" + ruleType + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", prize='" + prize + '\'' +
                ", userRate='" + userRate + '\'' +
                '}';
    }

    public int getGrid() {
        return grid;
    }

    public void setGrid(int grid) {
        this.grid = grid;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public double getUserRate() {
        return userRate;
    }

    public void setUserRate(double userRate) {
        this.userRate = userRate;
    }

}
