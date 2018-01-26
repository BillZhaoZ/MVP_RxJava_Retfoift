package com.zhao.bill.mvp_rxjava_retfoift.utils;

import android.util.Log;

/**
 * log 工具类
 */
public class AppLog {

    public static void v(String tag, String msg) {
        if (!Keys.DEBUG)
            return;
        Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (!Keys.DEBUG)
            return;
        Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!Keys.DEBUG)
            return;
        Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (!Keys.DEBUG)
            return;
        Log.w(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (!Keys.DEBUG)
            return;
        Log.i(tag, msg);
    }

    public static void logaaaaa(String str) {
        if (Keys.DEBUG) {
            Log.i("aaaaa", str);
        }
    }
}
