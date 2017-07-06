package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/7/6.
 */

public class ZhuiHaoDetails {
    int id;
    double amount;
    double cancelAmount;
    int cancelPeriods;
    String drawPeriod;
    int gid;
    int mode;
    String number;
    int periods;
    String pickedNumbers;
    double purchaseAmount;
    String purchaseDate;
    int purchasePeriods;
    int rulesId;
    String startPeriod;
    String bids;
    int status;
    int stopByWin;
    int uid;

    @Override
    public String toString() {
        return "ZhuiHaoDetails{" +
                "id=" + id +
                ", amount=" + amount +
                ", cancelAmount=" + cancelAmount +
                ", cancelPeriods=" + cancelPeriods +
                ", drawPeriod='" + drawPeriod + '\'' +
                ", gid=" + gid +
                ", mode=" + mode +
                ", number='" + number + '\'' +
                ", periods=" + periods +
                ", pickedNumbers='" + pickedNumbers + '\'' +
                ", purchaseAmount=" + purchaseAmount +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", purchasePeriods=" + purchasePeriods +
                ", rulesId=" + rulesId +
                ", startPeriod='" + startPeriod + '\'' +
                ", bids='" + bids + '\'' +
                ", status=" + status +
                ", stopByWin=" + stopByWin +
                ", uid=" + uid +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getCancelAmount() {
        return cancelAmount;
    }

    public void setCancelAmount(double cancelAmount) {
        this.cancelAmount = cancelAmount;
    }

    public int getCancelPeriods() {
        return cancelPeriods;
    }

    public void setCancelPeriods(int cancelPeriods) {
        this.cancelPeriods = cancelPeriods;
    }

    public String getDrawPeriod() {
        return drawPeriod;
    }

    public void setDrawPeriod(String drawPeriod) {
        this.drawPeriod = drawPeriod;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public String getPickedNumbers() {
        return pickedNumbers;
    }

    public void setPickedNumbers(String pickedNumbers) {
        this.pickedNumbers = pickedNumbers;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getPurchasePeriods() {
        return purchasePeriods;
    }

    public void setPurchasePeriods(int purchasePeriods) {
        this.purchasePeriods = purchasePeriods;
    }

    public int getRulesId() {
        return rulesId;
    }

    public void setRulesId(int rulesId) {
        this.rulesId = rulesId;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public String getBids() {
        return bids;
    }

    public void setBids(String bids) {
        this.bids = bids;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStopByWin() {
        return stopByWin;
    }

    public void setStopByWin(int stopByWin) {
        this.stopByWin = stopByWin;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
