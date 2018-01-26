package com.zhao.bill.mvp_rxjava_retfoift.utils;

import android.os.Environment;

import com.zhao.bill.mvp_rxjava_retfoift.BuildConfig;

import java.io.File;

/**
 * APP常量
 * Created by David on 16/3/7.
 */
public final class Keys {

    public static boolean DEBUG = BuildConfig.DEBUG;// 日志打印开关

    public final static String APK_FILE_PATH = Environment.getExternalStorageDirectory().toString() + File.separator
            + "MedBanks" + File.separator;// apk文件的保存基路径
}
