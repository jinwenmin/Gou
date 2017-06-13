package com.example.king.gou.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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


}































