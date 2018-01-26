package com.zhao.bill.mvp_rxjava_retfoift.retroift.base;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * BaseObserver for list
 * Created by Bill on 2016/11/8.
 */
public abstract class BaseObserverList<T> implements Observer<BaseEntityList<T>> {

    private static final String TAG = "BaseObserverList";
    private Context mContext;

    protected BaseObserverList(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseEntityList<T> value) {

        if (value.getCode() == 0) {
            List<T> data = value.getData();

            onHandleSuccess(data);
        } else {
            onHandleError(value.getMessage());
        }

        if (value.getResult().equals("success")) {
            Toast.makeText(mContext, value.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "error:" + e.toString());
    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");

        onHandleComplete();
    }

    protected abstract void onHandleSuccess(List<T> t);

    protected abstract void onHandleError(String msg);

    protected abstract void onHandleComplete();
}