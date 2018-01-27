package com.zhao.bill.mvp_rxjava_retfoift.retroift.client;

import com.zhao.bill.mvp_rxjava_retfoift.retroift.converfactory.StringConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * retrofit设置
 * Created by Bill on 16/11/8.
 */
public class Retrofit2Client {

    private static Retrofit.Builder retrofitBuilder = null;

    private Retrofit2Client() {

        retrofitBuilder = new Retrofit.Builder()
                //设置OKHttpClient
                .client(okHttp.getOkHttpClient())

                //添加Retrofit到RxJava的转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                //String转换器
                .addConverterFactory(StringConverterFactory.create())

                //gson转化器
                .addConverterFactory(GsonConverterFactory.create());
    }

    public static Retrofit.Builder getRetrofitBuilder() {
        if (retrofitBuilder == null) {
            new Retrofit2Client();
            return retrofitBuilder;
        }
        return retrofitBuilder;
    }

    /*private static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                // 此处可以添加Gson 自定义TypeAdapter
                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .create();
    }*/

}
