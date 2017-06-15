package com.example.king.gou.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/15.
 */

public class LoginState {
    String uname;
    int refip;
    String SMS_SD;
    double amount;
    int way;
    String fmsg;
    List<msg> Msg;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public int getRefip() {
        return refip;
    }

    public void setRefip(int refip) {
        this.refip = refip;
    }

    public String getSMS_SD() {
        return SMS_SD;
    }

    public void setSMS_SD(String SMS_SD) {
        this.SMS_SD = SMS_SD;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getWay() {
        return way;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public String getFmsg() {
        return fmsg;
    }

    public void setFmsg(String fmsg) {
        this.fmsg = fmsg;
    }

    public List<msg> getMsg() {
        return Msg;
    }

    public void setMsg(List<msg> msg) {
        Msg = msg;
    }

 public    class msg {
        int cid;
        String ctime;
        boolean mine;
        int inform;
        int uid;
        String uname;
        int sid;
        String sname;
        String text;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public boolean isMine() {
            return mine;
        }

        public void setMine(boolean mine) {
            this.mine = mine;
        }

        public int getInform() {
            return inform;
        }

        public void setInform(int inform) {
            this.inform = inform;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    public String toString() {
        return "LoginState{" +
                "uname='" + uname + '\'' +
                ", refip=" + refip +
                ", SMS_SD='" + SMS_SD + '\'' +
                ", amount=" + amount +
                ", way=" + way +
                ", fmsg='" + fmsg + '\'' +
                ", Msg=" + Msg +
                '}';
    }
}
