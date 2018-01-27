package com.zhao.bill.mvp_rxjava_retfoift.mvp.model.interfaces;


/**
 * model层通往presenter的接口回调
 * Created by Bill on 2017/9/7.
 */
public interface IMtoPCommon {

    void onSuccess(Object o, int code, String message);

    void onFailed(String errorMessage);
}
