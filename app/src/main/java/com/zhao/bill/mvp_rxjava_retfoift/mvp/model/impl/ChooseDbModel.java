package com.zhao.bill.mvp_rxjava_retfoift.mvp.model.impl;

import android.content.Context;

import com.zhao.bill.mvp_rxjava_retfoift.bean.ChooseDbBean;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.model.interfaces.IMtoPCommon;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.model.listner.IChooseDb;
import com.zhao.bill.mvp_rxjava_retfoift.retroift.base.BaseObserverList;
import com.zhao.bill.mvp_rxjava_retfoift.retroift.request.RequestManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * model层  进行数据的处理
 * 处理完成后  通过接口回调的方式   把数据返回到presenter层
 * Created by Bill on 2017/9/7.
 */
public class ChooseDbModel implements IChooseDb {

    /**
     * 获取以上都不是其他数据库
     *
     * @param iDataToPresenter
     */
    @Override
    public void loadData(Context context, IMtoPCommon iDataToPresenter) {
        Map<String, String> map = new HashMap<>();

        RequestManager.getInstance().toSubscribe(RequestManager.createMainApi().getList(map),
                new BaseObserverList<ChooseDbBean.DataBean>(context) {
                    @Override
                    protected void onHandleSuccess(List<ChooseDbBean.DataBean> t, int code, String message) {
                        iDataToPresenter.onSuccess(t, code, message);
                    }

                    @Override
                    protected void onHandleError(String msg) {
                        iDataToPresenter.onFailed(msg);
                    }

                    @Override
                    protected void onHandleComplete() {

                    }
                });

    }

    @Override
    public void createNewCase(String id, String dataBase) {
        // 请求接口  创建新病例
    }

}
