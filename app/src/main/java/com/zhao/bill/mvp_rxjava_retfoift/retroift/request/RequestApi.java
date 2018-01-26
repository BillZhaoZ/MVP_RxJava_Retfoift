package com.zhao.bill.mvp_rxjava_retfoift.retroift.request;

import com.zhao.bill.mvp_rxjava_retfoift.ChooseDbBean;
import com.zhao.bill.mvp_rxjava_retfoift.retroift.base.BaseEntityList;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 网络请求---具体的请求类
 * Created by Bill on 16/11/8.
 */
public interface RequestApi {

    // 以上都不是的 病例库列表
    @FormUrlEncoded
    @POST(URLs.DB_LIST)
    Observable<BaseEntityList<ChooseDbBean.DataBean>> getList(@FieldMap Map<String, String> map);

}