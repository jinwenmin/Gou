
package com.example.king.gou.utils;

import android.content.Context;
import android.widget.Toast;

public class SingleToast {

    private static Toast mToast;

    /**双重锁定，使用同一个Toast实例*/
    public static Toast getInstance(Context context){
        if (mToast == null){
            synchronized (SingleToast.class){
                if (mToast == null){
                    mToast = new Toast(context);
                }
            }
        }
        return mToast;
    }
}