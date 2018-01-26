package com.zhao.bill.mvp_rxjava_retfoift;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.HashMap;
import java.util.List;

/**
 * 通用方法工具类
 * 取自之前的application
 * Created by Bill on 2017/9/26.
 */
public class CommonUtils {

    private HashMap<String, List> mHashMap = new HashMap<>(); // 存放筛选过的标签

    private static CommonUtils instance = null;

    /**
     * 获取实例
     *
     * @return
     */
    public static CommonUtils getsInstance() {

        if (instance == null) {
            synchronized (CommonUtils.class) {
                instance = new CommonUtils();
            }
        }
        return instance;
    }

    /****
     * *设置沉寂式系统状态栏
     ****/
    public static void initSystemBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);

        // 使用颜色资源
        tintManager.setStatusBarTintResource(R.color.color_title_bg);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucentStatus(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }

        win.setAttributes(winParams);
    }

    /****
     * *设置沉寂式系统状态栏
     ****/
    public static void initSystemBar(Activity activity, int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);

        // 使用颜色资源
        tintManager.setStatusBarTintResource(resId);
    }

}
