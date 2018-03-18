package com.example.king.gou.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MyUtil {
    private static Toast mToast;

    public static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getString(resId), duration);
    }

    public static void showToast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
} 