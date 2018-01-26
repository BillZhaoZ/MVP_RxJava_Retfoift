package com.zhao.bill.mvp_rxjava_retfoift.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Fragment基类
 *
 * @param <P>
 * @param <V>
 */
public abstract class BaseMvpFragment<P extends BaseContract.BasePresenter<V>,
        V extends BaseContract.BaseView> extends Fragment {

    protected P mPresenter;
    private boolean isFirstStart = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = getPresenter();

        if (mPresenter != null) {
            mPresenter.onViewAttached((V) this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewID() != 0) {
            return inflater.inflate(getContentViewID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onResume() {
        super.onResume();
        // MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);
        initData();
        initViews(view);
        initListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mPresenter != null) {
            mPresenter.onStart(isFirstStart);
            isFirstStart = false;
        }
    }

    @Override
    public void onStop() {
        if (mPresenter != null) {
            mPresenter.onStop();
        }

        super.onStop();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onViewDetached();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.onPresenterDestroyed();
        }
    }

    protected P getPresenter() {
        return null;
    }

    /**
     * bind layout resource file
     */
    protected abstract int getContentViewID();

    /**
     * view已经创建，在此处初始化数据开始
     */
    protected void initData() {

    }

    /**
     * init views and events here
     */
    protected abstract void initViews(View view);

    /**
     * 绑定监听
     */
    protected void initListener() {
    }
}
