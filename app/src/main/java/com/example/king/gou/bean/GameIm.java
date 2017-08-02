package com.example.king.gou.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2017/6/6.
 */

public class GameIm {
    public int gid;
    public String name;

    @Override
    public String toString() {
        return "GameIm{" +
                "gid=" + gid +
                ", name='" + name + '\'' +
                ", Gameimg=" + Gameimg +
                ", imgText='" + imgText + '\'' +
                '}';
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int Gameimg;
    public String imgText;

    public String getImgText() {
        return imgText;
    }

    public void setImgText(String imgText) {
        this.imgText = imgText;
    }

    public int getGameimg() {
        return Gameimg;
    }

    public void setGameimg(int gameimg) {
        Gameimg = gameimg;
    }
}
