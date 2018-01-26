package com.zhao.bill.mvp_rxjava_retfoift.retroift.request;

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
    //   public static final String BASE_URL = "http://test.4sapp.medbanks.cn";// 测试库
    public static final String BASE_URL = "https://4s-api.medbanks.cn";// 正式库

   /* public static final boolean TEST = BuildConfig.DEBUG;
    public static final String BASE_URL = TEST ? "http://test.4sapp.medbanks.cn" : "https://4s-api.medbanks.cn";
*/

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

        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()) //在io线程中处理网络请求
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);  //在主线程中处理数据
    }
}
