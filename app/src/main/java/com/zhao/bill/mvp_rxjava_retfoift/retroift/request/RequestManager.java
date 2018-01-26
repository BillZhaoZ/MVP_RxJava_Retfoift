package com.zhao.bill.mvp_rxjava_retfoift.retroift.request;

import com.zhao.bill.mvp_rxjava_retfoift.BuildConfig;
import com.zhao.bill.mvp_rxjava_retfoift.retroift.client.Retrofit2Client;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 网络请求
 * Created by Bill on 16/11/8.
 */
public class RequestManager {

    // 网络环境
    private static final boolean TEST = BuildConfig.DEBUG;
    private static final String BASE_URL = TEST ? "http://test.cn" : "https://4s.cn";

    private static RequestApi requestApi = null;

    public static RequestApi createMainApi() {
        if (requestApi == null) {
            requestApi = Retrofit2Client.getRetrofitBuilder()
                    .baseUrl(BASE_URL)
                    .build()
                    .create(RequestApi.class);
        }

        return requestApi;
    }

    /**
     * 获取实例
     */
    private static RequestManager requestManager = null;

    private RequestManager() {

    }

    public static RequestManager getInstance() {
        if (requestManager == null) {

            return new RequestManager();
        }
        return requestManager;
    }

    /**
     * 设置观察者
     *
     * @param observable
     * @param subscriber
     * @param <T>
     */
    public <T> void toSubscribe(Observable<T> observable, Observer<T> subscriber) {

        observable
                .subscribeOn(Schedulers.io()) // 指定Observable(被观察者)所在的线程，或者叫做事件产生的线程
                .unsubscribeOn(Schedulers.io()) //在io线程中处理网络请求
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中处理数据   指定 Observer(观察者)所运行在的线程，或者叫做事件消费的线程。
                .subscribe(subscriber);
    }
}
