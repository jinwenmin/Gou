package com.example.king.gou.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;

import com.example.king.gou.MyApp;

import java.io.IOException;
import java.util.HashSet;
import java.util.prefs.Preferences;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    private HashSet<String> cookies = new HashSet<>();
    static ReceivedCookiesInterceptor receivedCookiesInterceptor;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            for (String header : originalResponse.headers("Set-Cookie")) {
                System.out.println("这边是获取到的Cookie==" + header);
                cookies.add(header);
               MyApp.getInstance().setSessionId(header);

            }

        }
        return originalResponse;
    }

    public static ReceivedCookiesInterceptor getInstance() {
        if (receivedCookiesInterceptor == null) {
            receivedCookiesInterceptor = new ReceivedCookiesInterceptor();
        }
        return null;
    }

    public HashSet<String> getCookies() {
        return cookies;
    }
}