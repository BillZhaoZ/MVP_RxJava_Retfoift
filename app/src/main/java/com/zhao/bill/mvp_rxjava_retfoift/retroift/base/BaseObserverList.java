package com.zhao.bill.mvp_rxjava_retfoift.retroift.base;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.medbanks.assistant.mvp.retroift.exception.ResultException;
import com.medbanks.assistant.utils.log.AppLog;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * BaseObserver for list
 * Created by Bill on 2016/11/8.
 */
public abstract class BaseObserverList<T> implements Observer<BaseEntityList<T>> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserverList(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {
        AppLog.e(TAG, "onSubscribe,接收在什么线程: " + Thread.currentThread().getName());
    }

    @Override
    public void onNext(BaseEntityList<T> value) {

        if (value.getCode() == 0) {

            List<T> data = value.getData();
            int code = value.getCode();
            String message = value.getMessage();

            onHandleSuccess(data, code, message);

        } else {
            onHandleError(value.getMessage());

            AppLog.e(TAG, "onHandleError:" + value.getMessage());
        }

        if (value.getResult().equals("success")) {
            Toast.makeText(mContext, value.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof UnknownHostException) {
            AppLog.e(TAG, "onError: 网络连接失败，请检查网络设置");

        } else if (e instanceof SocketTimeoutException) {
            AppLog.e(TAG, "onError: 网络连接超时");

        } else if (e instanceof ConnectException) {
            AppLog.e(TAG, "onError: 网络连接超时");

        } else if (e instanceof HttpException) {
            AppLog.e(TAG, "onError: 网络连接超时");

            HttpException httpException = (HttpException) e;
            int code = httpException.code();

            if (code >= 400 && code <= 417) {
                AppLog.e(TAG, "onError: 访问地址异常，请稍后再试");

            } else if (code >= 500 && code <= 505) {
                AppLog.e(TAG, "onError: 服务器繁忙，请稍后再试");

            } else {
                AppLog.e(TAG, "onError: 网络连接异常");
            }

        } else if (e instanceof ResultException) {

            ResultException resultException = (ResultException) e;
            int resultCode = resultException.getErroCode();

            if (resultCode == -1) {

            } else if (resultCode <= 0) {

            } else {
                Log.e(TAG, "onError: 数据异常,请稍后重试");
            }

        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                || e instanceof NullPointerException) {

            AppLog.e(TAG, "onError: 数据或解析异常，请稍后再试");

        } else {
            AppLog.e(TAG, "onError: 未知错误，请稍后再试");
        }
    }

    @Override
    public void onComplete() {
        AppLog.e(TAG, "onComplete");

        onHandleComplete();
    }

    protected abstract void onHandleSuccess(List<T> t, int code, String message);

    protected abstract void onHandleError(String msg);

    protected abstract void onHandleComplete();
}