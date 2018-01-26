package com.zhao.bill.mvp_rxjava_retfoift.mvp.view.adapter;

import android.content.Context;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhao.bill.mvp_rxjava_retfoift.ChooseDbBean;
import com.zhao.bill.mvp_rxjava_retfoift.R;
import com.zhao.bill.mvp_rxjava_retfoift.mvp.model.interfaces.OnItemSelectedListener;

import java.util.HashMap;

/**
 * 病例库列表
 * Created by Bill on 18/1/22.
 */
public class ChoseDBAdapter extends BaseQuickAdapter<ChooseDbBean.DataBean, BaseViewHolder> {

    private HashMap mSelectMap = new HashMap<String, String>();
    private OnItemSelectedListener listener;
    private Context activity;

    public ChoseDBAdapter(int layoutResId, Context activity, OnItemSelectedListener listener) {
        super(layoutResId);

        this.listener = listener;
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, ChooseDbBean.DataBean item) {
        holder.setText(R.id.tv_name, item.getDisease_word());

        CheckBox cb_select = holder.getView(R.id.cb_select);
        cb_select.setChecked(mSelectMap.containsKey(item.getDatabase()));

        cb_select.setOnClickListener(v -> {

            if (!mSelectMap.containsKey(item.getDatabase())) {
                mSelectMap.clear();
                mSelectMap.put(item.getDatabase(), item.getDisease_word());
            } else {
                mSelectMap.remove(item.getDatabase());
            }

            if (listener != null) {
                listener.onItemSelected(item);
            }

            notifyDataSetChanged();
        });
    }
}