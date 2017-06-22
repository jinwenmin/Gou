package com.example.king.gou.utils;

import android.content.Context;
import android.util.AttributeSet;

import com.example.king.gou.bean.AdvertisementObject;

/**
 * Created by Administrator on 2017/6/22.
 */

public class MainScrollUpAdvertisementView extends
        BaseAutoScrollUpTextView<AdvertisementObject> {

    public MainScrollUpAdvertisementView(Context context, AttributeSet attrs,
                                         int defStyle) {
        super(context, attrs, defStyle);
    }

    public MainScrollUpAdvertisementView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainScrollUpAdvertisementView(Context context) {
        super(context);
    }

    @Override
    public String getTextTitle(AdvertisementObject data) {
        return data.title;
    }

    @Override
    public String getTextInfo(AdvertisementObject data) {
        return data.info;
    }

    /**
     * 这里面的高度应该和你的xml里设置的高度一致
     */
    @Override
    protected int getAdertisementHeight() {
        return 40;
    }

}
