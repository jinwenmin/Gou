package com.example.king.gou.utils;

/**
 * 从服务器获得数据后的回调
 *
 * @author Administrator
 */
public interface DataListener {
    public void onReceivedData(int apiId, Object object, int errorId);

    public void onRequestStart(int apiId);

    public void onRequestEnd(int apiId);

}