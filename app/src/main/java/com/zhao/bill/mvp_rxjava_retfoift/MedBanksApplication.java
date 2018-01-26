package com.zhao.bill.mvp_rxjava_retfoift;

import android.support.multidex.MultiDexApplication;

/**
 * Application是为了保存全局变量的，系统默认会创建这个类。如果我们想在这个类中去保存一些其它的变量，则写一个类继承它
 * 在清单文件中指定让系统创建我们的类
 * Created by David on 16/3/1.
 **/
public class MedBanksApplication extends MultiDexApplication {

    private static MedBanksApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * 获取application实例
     *
     * @return
     */
    public static MedBanksApplication getsInstance() {
        return sInstance;
    }

}
