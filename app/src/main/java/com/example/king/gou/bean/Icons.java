package com.example.king.gou.bean;

/**
 * Created by king on 2017/8/9.
 */

public class Icons {
    int iconImg;
    String iconName;

    @Override
    public String toString() {
        return "Icons{" +
                "iconImg=" + iconImg +
                ", iconName='" + iconName + '\'' +
                '}';
    }

    public int getIconImg() {
        return iconImg;
    }

    public void setIconImg(int iconImg) {
        this.iconImg = iconImg;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }
}
