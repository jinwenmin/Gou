package com.example.king.gou.utils;

import android.text.TextUtils;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by king on 2017/5/29.
 */

public class RxUtils {
    public RxUtils() throws NoSuchAlgorithmException {
    }

    public static <T> ObservableTransformer<T, T> rxHelper() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).unsubscribeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public synchronized static RxUtils getInstance() {
        RxUtils rxUtils = null;
        if (rxUtils == null) {
            try {
                rxUtils = new RxUtils();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return rxUtils;
    }

    public String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String SHA256(String value) {

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(value.getBytes());
            byte[] digest = sha.digest();
            String valueOf = String.valueOf(digest);
            return valueOf;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String HMACSHA256(String data, String key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data.getBytes())).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    public OkHttpClient Http() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url("http://vipfacaiflvbceshi.com/logout");
//可以省略，默认是GET请求
        requestBuilder.method("GET", null);

        return okHttpClient;
    }
}































