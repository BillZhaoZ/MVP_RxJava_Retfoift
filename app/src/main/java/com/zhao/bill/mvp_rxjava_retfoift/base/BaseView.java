package com.zhao.bill.mvp_rxjava_retfoift.base;

/**
 * 建立View和presenter之间的关系
 *
 * @param <T>
 */
public interface BaseView<T> {

    void setPresenter(T presenter);
}
