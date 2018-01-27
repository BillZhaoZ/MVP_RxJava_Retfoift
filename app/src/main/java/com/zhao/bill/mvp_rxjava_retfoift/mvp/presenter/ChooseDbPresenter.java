package com.zhao.bill.mvp_rxjava_retfoift.mvp.presenter;

import android.content.Context;

import com.zhao.bill.mvp_rxjava_retfoift.bean.ChooseDbBean;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.contract.ChooseDbContract;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.model.impl.ChooseDbModel;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.model.interfaces.IMtoPCommon;

import java.util.List;

/**
 * p层   数据处理层，所有的数据逻辑，业务逻辑都在这里处理；责完成View于Model间的交互
 * <p>
 * ChooseDbPresenter 是 ChooseDbContract.Presenter的接口实现类
 * Created by Bill on 2017/9/5.
 */
public class ChooseDbPresenter implements ChooseDbContract.Presenter {

    private Context mContext;
    private ChooseDbModel model;
    private ChooseDbContract.View mView;

    public ChooseDbPresenter(ChooseDbContract.View mainView, Context context) {
        mView = mainView;
        mContext = context;
        this.model = new ChooseDbModel();

        // 建立View和presenter关系
        mView.setPresenter(this);
    }

    @Override
    public void loadDataFromServer() {

        // 请求网络   在model进行  回调返回数据
        model.loadData(mContext, new IMtoPCommon() {
            @Override
            public void onSuccess(List<ChooseDbBean.DataBean> t) {
                // 设置数据给view层
                mView.setDataToAdapter(t);
                mView.refreshView();
            }

            @Override
            public void onFailed(String errorMessage) {

            }

        });
    }

    /**
     * 新建病例
     *
     * @param id
     */
    @Override
    public void createCase(String id, String dataBase) {
        model.createNewCase(id, dataBase);
    }

    @Override
    public void start() {

    }
}
