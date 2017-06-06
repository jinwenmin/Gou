package com.example.king.gou.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class GameImages {
    private String Type;
    private List<GameIm> gameIms;

    public List<GameIm> getGameIms() {
        return gameIms;
    }

    public void setGameIms(List<GameIm> gameIms) {
        this.gameIms = gameIms;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

}
