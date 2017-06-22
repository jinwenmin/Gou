package com.example.king.gou.utils;

/**
 * Created by Administrator on 2017/6/22.
 */

public interface AutoScrollData<T> {

    /**
     * * 获取标题
     *
     * @param data
     * @return
     */
    public String getTextTitle(T data);

    /**
     * 获取内容
     *
     * @param data
     * @return
     */
    public String getTextInfo(T data);

}
