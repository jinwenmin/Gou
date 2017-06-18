package com.example.king.gou.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.king.gou.MyApp;

import java.io.IOException;
import java.util.HashSet;
import java.util.prefs.Preferences;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/15.
 */
public class AddCookiesInterceptor implements Interceptor {
    public HashSet<String> cookies = new HashSet<String>();
    private static AddCookiesInterceptor addCookie;

    public static AddCookiesInterceptor getAddCookie() {
        if (addCookie == null) {
            addCookie = new AddCookiesInterceptor();
        }
        return addCookie;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (MyApp.getInstance().getSessionId()!=null) {
            builder.addHeader("Cookie", MyApp.getInstance().getSessionId());
        }
            Log.v("OkHttp==", "Adding Header: " +  MyApp.getInstance().getSessionId()); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        return chain.proceed(builder.build());
    }

    public void getHashSet(HashSet<String> has) {
        cookies = has;

    }

}