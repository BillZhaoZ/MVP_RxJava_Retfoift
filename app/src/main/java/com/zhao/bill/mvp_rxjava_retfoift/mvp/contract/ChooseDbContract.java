package com.zhao.bill.mvp_rxjava_retfoift.mvp.contract;

import com.zhao.bill.mvp_rxjava_retfoift.bean.ChooseDbBean;
import com.zhao.bill.mvp_rxjava_retfoift.base.BasePresenter;
import com.zhao.bill.mvp_rxjava_retfoift.base.BaseView;

import java.util.List;

/**
 * 直接明了的看到View和Presenter之间的方法。
 * 此类需要被Activity（View层）继承； activity就是它的实现类
 * <p>
 * Created by Bill on 2017/9/5.
 */
public interface ChooseDbContract {

    /**
     * UI操作
     */
    interface View extends BaseView<Presenter> {

        void setDataToAdapter(List<ChooseDbBean.DataBean> t);

        void refreshView();
    }

    /**
     * 数据和逻辑操作
     */
    interface Presenter extends BasePresenter {
        void loadDataFromServer();
    }
}
