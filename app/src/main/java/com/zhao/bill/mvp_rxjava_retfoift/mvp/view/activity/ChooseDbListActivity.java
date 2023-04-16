package com.zhao.bill.mvp_rxjava_retfoift.mvp.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.PullToRefreshBase;
import com.handmark.pulltorefresh.recclerview.PullToRefreshRecyclerView;
import com.handmark.pulltorefresh.recclerview.WrapRecyclerView;
import com.zhao.bill.mvp_rxjava_retfoift.base.BasePresenter;
import com.zhao.bill.mvp_rxjava_retfoift.bean.ChooseDbBean;
import com.zhao.bill.mvp_rxjava_retfoift.bean.PatientDbData;
import com.zhao.bill.mvp_rxjava_retfoift.R;
import com.zhao.bill.mvp_rxjava_retfoift.base.BaseMvpActivity;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.contract.ChooseDbContract;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.model.interfaces.OnItemSelectedListener;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.presenter.ChooseDbPresenter;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.view.adapter.ChoseDBAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 业务界面
 * Created by Bill on 18/1/22.
 */
public class ChooseDbListActivity extends BaseMvpActivity implements ChooseDbContract.View,
        OnItemSelectedListener, PullToRefreshBase.OnRefreshListener2<WrapRecyclerView> {

    @BindView(R.id.recycleView)
    PullToRefreshRecyclerView mRecycleView;

    @BindView(R.id.left_btn)
    ImageView mLeftBtn;
    @BindView(R.id.left_tv)
    TextView mLeftTv;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.right_btn)
    ImageView mRightBtn;
    @BindView(R.id.right_tv)
    TextView mRightTv;
    @BindView(R.id.tv_choose)
    TextView mTvChoose;
    @BindView(R.id.tv_create_case)
    TextView mTvCreateCase;

    private String patientWxID;
    private ChoseDBAdapter adapter;
    private List<PatientDbData> patientDbDatas;
    private ChooseDbContract.Presenter presenter;
    private ChooseDbBean.DataBean mDataBean;
    private String patientDatabase;

    @Override
    protected void initView() {
        loadTitleBar(true, "关联病历", "");

        patientWxID = "100679";

        // mRecycleView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL));
        mRecycleView.getRefreshableView().setLayoutManager(new LinearLayoutManager(context));
        adapter = new ChoseDBAdapter(R.layout.item_choose_db_list, context, this);
        mRecycleView.getRefreshableView().setAdapter(adapter);

        mRecycleView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mRecycleView.setOnRefreshListener(this);
    }

    @Override
    protected void initData() {
        super.initData();

        // 请求网络数据
        presenter.loadDataFromServer();
    }

    @Override
    protected BasePresenter createPresenter() {
        return presenter = new ChooseDbPresenter(this, context);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<WrapRecyclerView> refreshView) {
        presenter.loadDataFromServer();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<WrapRecyclerView> refreshView) {

    }

    @Override
    protected int getContentViewID() {
        return R.layout.ac_choose_db;
    }

    @Override
    public void setDataToAdapter(List<ChooseDbBean.DataBean> t) {
        adapter.replaceData(t);
    }

    @Override
    public void refreshView() {
        mRecycleView.onRefreshComplete();
    }

    @Override
    public void setPresenter(ChooseDbContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @OnClick(R.id.tv_create_case)
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.tv_create_case: // 创建新病例

//                presenter.createCase(patientWxID, patientDatabase);

                break;
        }
    }

    @Override
    public void onItemSelected(Object object) {
        mDataBean = (ChooseDbBean.DataBean) object;
    }

}
