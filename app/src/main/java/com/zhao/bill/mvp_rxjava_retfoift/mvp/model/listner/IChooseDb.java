package com.zhao.bill.mvp_rxjava_retfoift.mvp.model.listner;

import android.content.Context;

import com.zhao.bill.mvp_rxjava_retfoift.mvp.model.interfaces.IMtoPCommon;

/**
 * presenter层通往model层
 * Created by Bill on 2017/9/7.
 */
public interface IChooseDb {

    void loadData(Context context, IMtoPCommon iDataToPresenter);
}
