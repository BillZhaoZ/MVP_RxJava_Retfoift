package com.zhao.bill.mvp_rxjava_retfoift.retroift.client;

import com.medbanks.assistant.MedBanksApplication;
import com.medbanks.assistant.constants.Keys;
import com.medbanks.assistant.net.BuilderInstall;
import com.medbanks.assistant.utils.CommonUtils;
import com.medbanks.assistant.utils.Tool;
import com.medbanks.assistant.utils.encryption.MD5Utils;
import com.medbanks.assistant.utils.encryption.SHA1Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.medbanks.assistant.BuildConfig;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 定制OKHTTP：包括请求头  Log日志  网络缓存  公共参数
 * Created by Bill on 16/11/8.
 */
public class okHttp {

    private static MedBanksApplication app;
    private static OkHttpClient okHttpClient = null;

    private HttpLoggingInterceptor loggingInterceptor;// Log信息拦截器

    private static final int TIMEOUT_READ = 30;// 读流时间
    private static final int TIMEOUT_CONNECTION = 30;// 连接时间

    private final static String md5LinkString = "Me1!(@)03Jksiev";//生成签名字段时连接每个参数的字符串
    private final static String sha1LinkString = "Medbanks!@#$%&*()";//sha1对md5字符串加密时需要的拼接的后缀字符串

    /**
     * 返回OKHttpClient客户端
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {

        if (okHttpClient == null) {
            new okHttp();
            app = MedBanksApplication.getsInstance();

            return okHttpClient;
        }
        return okHttpClient;
    }

    /**
     * 定制OKHTTP
     * Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
     */
    private okHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder
                .addInterceptor(addQueryParameterInterceptor)  //公共参数

                .addInterceptor(getHttpLogInterceptor()) //设置  Log信息拦截器

                .addInterceptor(headerInterceptor)  // 设置请求头

                .cache(cache).addNetworkInterceptor(cacheInterceptor)   //设置Cache缓存

                .retryOnConnectionFailure(true)  //失败重连

                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)  //超时链接
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS);

        okHttpClient = builder.build();
    }

    /**
     * Log信息拦截器
     * Debug可以看到，网络请求，打印Log信息，发布的时候就不需要这些log
     */
    private HttpLoggingInterceptor getHttpLogInterceptor() {

        if (BuildConfig.DEBUG) {

            loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        return loggingInterceptor;
    }

    /**
     * 公共参数
     */
    private Interceptor addQueryParameterInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request originalRequest = chain.request();
            Request request;
            String method = originalRequest.method();
            Headers headers = originalRequest.headers();

            HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                    // Provide your custom parameter here

                    .addQueryParameter(Keys.LOGIN_TOKEN, CommonUtils.getsInstance().getLogin_Token())
                    .addQueryParameter(Keys.DB_NAME, app.getCaseDbName())
                    .addQueryParameter(Keys.APP_ID, "android")
                    .addQueryParameter(Keys.APP_VERSION, app.getVersion())
                    .addQueryParameter(Keys.DEVICE_TOKEN, app.getDeviceId())
                    .addQueryParameter(Keys.DEVICE_UUID, app.getDeviceId())
                    .addQueryParameter(Keys.SIGN_TIME, System.currentTimeMillis() + "")
                    .addQueryParameter(Keys.APP_SIGN, getAppSign(BuilderInstall.getCommonParameters()))

                    .build();

            // 新的请求
            request = originalRequest.newBuilder().url(modifiedUrl).build();
            return chain.proceed(request);
        }
    };

    /**
     * 缓存机制
     */
    private File cacheFile = new File(MedBanksApplication.getsInstance().getExternalCacheDir(), "MedbanksCache");
    private Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);

    private Interceptor cacheInterceptor = chain -> {
        Request request = chain.request();

        // 无网络
        if (!Tool.isNetworkAvailable(MedBanksApplication.getsInstance().getApplicationContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);

        // 有网络
        if (Tool.isNetworkAvailable(MedBanksApplication.getsInstance().getApplicationContext())) {
            int maxAge = 0;
            // 有网络时 设置缓存超时时间0个小时
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Medbanks")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            // 无网络时，设置超时为4周
            int maxStale = 60 * 60 * 24 * 28;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("nyn")
                    .build();
        }
        return response;
    };

    /**
     * 设置请求头
     * 添加通用的Header
     */
    private Interceptor headerInterceptor = chain -> {
        Request originalRequest = chain.request();

        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header("AppType", "Android")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .method(originalRequest.method(), originalRequest.body());

        Request request = requestBuilder.build();
        return chain.proceed(request);
    };

    /**
     * 获取接口参数对应的appsign字段
     *
     * @param parameters 上传网络的参数
     */
    public static String getAppSign(Map<String, String> parameters) {
        List<String> list = new ArrayList<>();

        //构建参数key的集合， 进行key的字母升序排列
        if (parameters != null && parameters.size() != 0) {
            for (String key : parameters.keySet()) {
                list.add(key);
            }
        }
        Collections.sort(list);//默认按字母升序排列

        //构建验证需要的字段
        StringBuilder stringKey = new StringBuilder();

        //将每个参数的key进行拼接
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                stringKey = stringKey.append(parameters.get(list.get(i)) + md5LinkString);
            } else {
                stringKey = stringKey.append(parameters.get(list.get(i)));
            }
        }

        //将每个key拼接后先进行MD5加密在进行sha1加密，得到验证字段
        return SHA1Util.sha1Encode(MD5Utils.getMd5Value(stringKey.toString()) + sha1LinkString);
    }
}
