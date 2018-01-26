package com.zhao.bill.mvp_rxjava_retfoift;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.security.auth.x500.X500Principal;

/**
 * 方法工具类
 */
public class Tool {

    private static AlertDialog a;

    public static int getVersionCode(Context context) {// 获取版本号(内部识别号)
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getVersion(Context context) {// jkb版本号：
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 将数据流转化为字节
     *
     * @param is 数据流
     * @return byte[] 字节数组
     * @throws IOException
     */
    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = is.read(b, 0, 1024)) != -1) {
            baos.write(b, 0, len);
            baos.flush();
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }


    /*
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // 判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    /**
     * 将int值转化为ip字符串
     *
     * @param i
     * @return
     */
    private static String intToIp(int i) {

        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    /**
     * 获取当前的标准时间
     *
     * @return
     */
    public static String getNowDate() {
        Date dt = new Date();
        SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return matter1.format(dt);
    }

    /**
     * 获取sdk版本号 如17 18 19
     *
     * @return
     */
    public static int getSDKversion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public static void hidenSoftKeyboad(Context context, View view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftkeyboard(Context context, View view) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInputFromInputMethod(
                view.getWindowToken(), 0);
    }

    /**
     * 强制软键盘隐藏
     */
    public static void forceHidenSoftKeyboad(Activity activity) {
        View view2 = activity.getWindow().peekDecorView();
        InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (view2 != null) {
            inputmanger.hideSoftInputFromWindow(view2.getWindowToken(), 0);
        }
    }

    /**
     * 强行隐藏输入法
     */
    public static void forceHiddenSoftInputMethod(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获取签名
     *
     * @param context
     * @return
     */
    public static byte[] getSign(Context context) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> apps = pm.getInstalledPackages(PackageManager.GET_SIGNATURES);

        for (PackageInfo info : apps) {
            String packageName = info.packageName;
            // 按包名 取签名
            if (packageName.equals("com.test.test")) {
                return info.signatures[0].toByteArray();
            }
        }
        return null;
    }

    /**
     * 判断当前应用签名是否是debug签名
     *
     * @param ctx
     * @return
     */
    public static boolean isDebuggable(Context ctx) {
        boolean debuggable = false;
        X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");
        try {
            PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            Signature signatures[] = pinfo.signatures;
            for (Signature signature : signatures) {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream stream = new ByteArrayInputStream(signature.toByteArray());
                X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
                debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
                if (debuggable)
                    break;
            }

        } catch (NameNotFoundException | CertificateException e) {
        } catch (NullPointerException e) {
            debuggable = BuildConfig.DEBUG;
        }
        return debuggable;
    }

    /**
     * 清理掉除了主线程之外的全部线程
     */
    public static void clearAllThreadNOMain() {
        Map<Thread, StackTraceElement[]> thMap = Thread.getAllStackTraces();
        Set<Thread> set = thMap.keySet();
        for (Thread aSet : set) {
            aSet.interrupt();
        }
    }

    /**
     * 获取屏幕高度值
     *
     * @return
     */
    public static int getScreenHeigh(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        return screenHeigh;
    }

    /**
     * 获取屏幕宽度值
     *
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        return screenWidth;
    }

    /**
     * 获取屏幕密度
     * <p>
     * 屏幕密度比例值为：（0.75 / 1.0 / 1.5 /2 / 3）参考值为：mdpi 160
     *
     * @return
     */
    public static float getScreenDensity(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density;
        return density;
    }

    /**
     * 计算两点之间距离
     *
     * @param start
     * @param end
     * @return 千米
     */
    public static double getDistance(double[] start, double[] end) {
        double lat1 = (Math.PI / 180) * start[0];
        double lat2 = (Math.PI / 180) * end[0];

        double lon1 = (Math.PI / 180) * start[1];
        double lon2 = (Math.PI / 180) * end[1];

        // double Lat1r = (Math.PI/180)*(gp1.getLatitudeE6()/1E6);
        // double Lat2r = (Math.PI/180)*(gp2.getLatitudeE6()/1E6);
        // double Lon1r = (Math.PI/180)*(gp1.getLongitudeE6()/1E6);
        // double Lon2r = (Math.PI/180)*(gp2.getLongitudeE6()/1E6);
        // 地球半径
        double R = 6371;
        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))
                * R;
        DecimalFormat df = new DecimalFormat(".00");// .00就表示保留后两位数
        d = Double.parseDouble(df.format(d));
        return d;
    }


    /**
     * 计算时间毫秒
     */
    private static long initStartTime(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateTime = format.parse(date);
            long time = dateTime.getTime();
            return time;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel(Context context) {
        return android.os.Build.MODEL; // 手机型号
    }

    /**
     * android系统版本号
     */
    public static String getPhoneRELEASE(Context context) {
        return android.os.Build.VERSION.RELEASE; // android系统版本号
    }

    /**
     * 获取SDK号
     */
    public static String getPhoneSDK(Context context) {
        return android.os.Build.VERSION.SDK; // SDK号
    }

    /**
     * 获取设备厂商
     */
    public static String getPhoneBRAND(Context context) {
        return android.os.Build.BRAND; // SDK号
    }

    /**
     * 获取当前应用程序版本号
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionName = info.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取手机的唯一标识
     *
     * @param context 当前上下文
     * @return Android手机的位置标志 imei
     * @author zhangmeng
     */
    public static String getIMEI(Context context) {
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
    }

    /**
     * 把字节数组保存为一个文件
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            if (file != null && file.exists()) {
                file.delete();
            }
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 将时间字符串转化为时间戳
     *
     * @param time 时间字符串
     * @return
     */
    public static long getStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 判断手机中是否安装该程序
     *
     * @param context 当前上下文
     * @param pkgName 程序的包名
     * @return
     */
    public static boolean isPkgInstalled(Context context, String pkgName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 替换安装apk
     */
    public static void installApk(Context context, File f) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(f), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 检查当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (NetworkInfo aNetworkInfo : networkInfo) {
                    // 判断当前网络状态是否为连接状态
                    if (aNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取配置清单文件中的meta-data节点指定的key对应的值
     *
     * @param context 当前上下文
     * @param key     meta-data节点的key值
     * @return
     * @author zhangmeng
     */
    public static String getMetaData(Context context, String key) {
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            Object value = ai.metaData.get(key);
            if (value != null) {
                return value.toString();
            }
        } catch (Exception e) {
            //
        }
        return null;
    }

    /**
     * 启动到app详情界面
     *
     * @param context   当前上下文
     * @param appPkg    App的包名
     * @param marketPkg 应用商店包名 ,如果市场包名为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败,或者如果市场包名为""时手机会自动打开一个默认的市场
     *                  <p/>
     *                  主流应用商店对应的包名如下： com.qihoo.appstore 360手机助手 com.taobao.appcenter
     *                  淘宝手机助手 com.tencent.android.qqdownloader 应用宝
     *                  com.hiapk.marketpho 安卓市场 cn.goapk.market 安智市场
     */
    public static void launchAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测某Activity是否在当前Task的栈顶
     */
    public static boolean isTopActivy(Context context, String activityName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        String cmpNameTemp = null;

        if (null != runningTaskInfos) {
            cmpNameTemp = (runningTaskInfos.get(0).topActivity).getClassName();
        }

        if (null == cmpNameTemp) {
            return false;
        }
        return cmpNameTemp.contains(activityName);
    }

    /**
     * 打开手机主页，将程序退到后台
     */
    public static void startHomePage(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    /**
     * 修改当前屏幕的背景透明度
     *
     * @param activity
     * @param bgAlpha
     */
    public static void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 获取字符串的长度，如果有中文，则每个中文字符计为2位
     *
     * @param validateStr 指定的字符串
     * @return 字符串的长度
     */
    public static int getChineseLength(String validateStr) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";// 中文字符常用范围是  \u4e00-\u9fa5

        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < validateStr.length(); i++) {
            /* 获取一个字符 */
            String temp = validateStr.substring(i, i + 1);

            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 根据接口返回的性别的接口字段判断是什么类型的性别
     *
     * @param sex
     * @return
     */
    public static String getSexType(String sex) {
        if (TextUtils.equals("1", sex)) {//男
            return "男";
        } else if (TextUtils.equals("2", sex)) {//女
            return "女";
        } else {

            if (TextUtils.equals("999", sex)) {//其他
                return "性别其他";
            } else if (TextUtils.equals("998", sex)) {//未知
                return "性别未知";
            } else {//0代表无
                return "性别无";
            }
        }
    }

}
