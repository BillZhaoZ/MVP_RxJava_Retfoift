package com.zhao.bill.mvp_rxjava_retfoift.mvp.model.interfaces;


import com.zhao.bill.mvp_rxjava_retfoift.ChooseDbBean;

import java.util.List;

/**
 * model层通往presenter的接口回调
 * Created by Bill on 2017/9/7.
 */
public interface IMtoPForChooseDb {

    void onSuccess(List<ChooseDbBean.DataBean> t);

    void onFailed(String errorMessage);
}
