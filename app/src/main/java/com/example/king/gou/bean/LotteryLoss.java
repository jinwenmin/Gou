package com.example.king.gou.bean;

/**
 * Created by king on 2017/7/10.
 */

public class LotteryLoss {
    String uname;
    double betting_amount;
    double winning_amount;
    int type;
    double betting_amounts;
    double rebate_amount;
    double profit_loss;
    double recharge_amount;
    double withdraw_amount;
    double activity_amount;
    double activity_amount2;
    int counts;
int id;

    @Override
    public String toString() {
        return "LotteryLoss{" +
                "uname='" + uname + '\'' +
                ", betting_amount=" + betting_amount +
                ", winning_amount=" + winning_amount +
                ", type=" + type +
                ", betting_amounts=" + betting_amounts +
                ", rebate_amount=" + rebate_amount +
                ", profit_loss=" + profit_loss +
                ", recharge_amount=" + recharge_amount +
                ", withdraw_amount=" + withdraw_amount +
                ", activity_amount=" + activity_amount +
                ", activity_amount2=" + activity_amount2 +
                ", counts=" + counts +
                ", id=" + id +
                ", winning_amounts=" + winning_amounts +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(double recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public double getWithdraw_amount() {
        return withdraw_amount;
    }

    public void setWithdraw_amount(double withdraw_amount) {
        this.withdraw_amount = withdraw_amount;
    }

    public double getActivity_amount() {
        return activity_amount;
    }

    public void setActivity_amount(double activity_amount) {
        this.activity_amount = activity_amount;
    }

    public double getActivity_amount2() {
        return activity_amount2;
    }

    public void setActivity_amount2(double activity_amount2) {
        this.activity_amount2 = activity_amount2;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public double getProfit_loss() {
        return profit_loss;
    }

    public void setProfit_loss(double profit_loss) {
        this.profit_loss = profit_loss;
    }

    public double getRebate_amount() {
        return rebate_amount;
    }

    public void setRebate_amount(double rebate_amount) {
        this.rebate_amount = rebate_amount;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public double getBetting_amount() {
        return betting_amount;
    }

    public void setBetting_amount(double betting_amount) {
        this.betting_amount = betting_amount;
    }

    public double getWinning_amount() {
        return winning_amount;
    }

    public void setWinning_amount(double winning_amount) {
        this.winning_amount = winning_amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getBetting_amounts() {
        return betting_amounts;
    }

    public void setBetting_amounts(double betting_amounts) {
        this.betting_amounts = betting_amounts;
    }

    public double getWinning_amounts() {
        return winning_amounts;
    }

    public void setWinning_amounts(double winning_amounts) {
        this.winning_amounts = winning_amounts;
    }

    double winning_amounts;
}
