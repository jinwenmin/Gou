package com.example.king.gou;

import android.app.Application;
import android.content.Context;

/**
 * Created by king on 2017/6/17.
 */

public class MyApp extends Application {
    private static MyApp myApp;
    private static Context context;
    private String sessionId = "";
    private boolean isFinger;
    private String UserNickName;
    private String UserName;


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserNickName() {
        return UserNickName;
    }

    public void setUserNickName(String userNickName) {
        UserNickName = userNickName;
    }

    public boolean isFinger() {
        return isFinger;
    }

    public void setFinger(boolean finger) {
        isFinger = finger;
    }

    public static MyApp getInstance() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    public Context getContext() {
        context = getApplicationContext();
        return context;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
