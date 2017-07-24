package com.example.king.gou.bean;

/**
 * Created by Administrator on 2017/7/24.
 */

public class CardsData {
    int cardid;
    String holders_name;
    String bank_name;
    String account_number;
    String binding_time;
    int bankid;
    String bankname;
    int province_id;
    String provincename;
    boolean locked;
int city_id;
    String CityName;

    @Override
    public String toString() {
        return "CardsData{" +
                "cardid=" + cardid +
                ", holders_name='" + holders_name + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", account_number='" + account_number + '\'' +
                ", binding_time='" + binding_time + '\'' +
                ", bankid=" + bankid +
                ", bankname='" + bankname + '\'' +
                ", province_id=" + province_id +
                ", provincename='" + provincename + '\'' +
                ", locked=" + locked +
                ", city_id=" + city_id +
                ", CityName='" + CityName + '\'' +
                '}';
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public int getCardid() {
        return cardid;
    }

    public void setCardid(int cardid) {
        this.cardid = cardid;
    }

    public String getHolders_name() {
        return holders_name;
    }

    public void setHolders_name(String holders_name) {
        this.holders_name = holders_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getBinding_time() {
        return binding_time;
    }

    public void setBinding_time(String binding_time) {
        this.binding_time = binding_time;
    }

    public int getBankid() {
        return bankid;
    }

    public void setBankid(int bankid) {
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
