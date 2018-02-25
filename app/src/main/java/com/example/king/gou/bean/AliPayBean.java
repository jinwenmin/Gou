package com.example.king.gou.bean;

/**
 * Created by Administrator on 2018/2/11 0011.
 */

public class AliPayBean {
    boolean rc;
    String msg;
    int id;
Others others;

    public Others getOthers() {
        return others;
    }

    public void setOthers(Others others) {
        this.others = others;
    }

    @Override
    public String toString() {
        return "AliPayBean{" +
                "rc=" + rc +
                ", msg='" + msg + '\'' +
                ", id=" + id +
                ", others=" + others +
                '}';
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public class Others{
        String note;
        String bank_name;
        String holders_name;
        String account_number;
        String bank_address;

        @Override
        public String toString() {
            return "Others{" +
                    "note='" + note + '\'' +
                    ", bank_name='" + bank_name + '\'' +
                    ", holders_name='" + holders_name + '\'' +
                    ", account_number='" + account_number + '\'' +
                    ", bank_address='" + bank_address + '\'' +
                    '}';
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getHolders_name() {
            return holders_name;
        }

        public void setHolders_name(String holders_name) {
            this.holders_name = holders_name;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public String getBank_address() {
            return bank_address;
        }

        public void setBank_address(String bank_address) {
            this.bank_address = bank_address;
        }
    }
}
