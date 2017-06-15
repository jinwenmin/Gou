package com.example.king.gou.utils;

/**
 * Created by king on 2017/6/15.
 */

public class HttpEngine {
    public interface DataListener {
        public void onReceivedData(int apiId, Object object, int errorId);

        public void onRequestStart(int apiId);

        public void onRequestEnd(int apiId);

    }
}
