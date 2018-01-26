package com.zhao.bill.mvp_rxjava_retfoift.retroift.base;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.zhao.bill.mvp_rxjava_retfoift.retroift.exception.ResultException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * BaseObserver for object
 * Created by Bill on 2016/11/8.
 */
public abstract class BaseObserver<T> implements Observer<BaseEntity<T>> {

    private static final String TAG = "BaseObserver";
    private Context mContext;

    protected BaseObserver(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseEntity<T> value) {

        if (value.getCode() == 0) {
            T t = value.getData();
            onHandleSuccess(t);
        } else {
            onHandleError(value.getMessage());
        }

        if (value.getResult().equals("success")) {
            Toast.makeText(mContext, value.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError(Throwable e) {

        if (e instanceof UnknownHostException) {
            Log.e(TAG, "onError: 网络连接失败，请检查网络设置");

        } else if (e instanceof SocketTimeoutException) {
            Log.e(TAG, "onError: 网络连接超时");

        } else if (e instanceof ConnectException) {
            Log.e(TAG, "onError: 网络连接超时");

        } else if (e instanceof HttpException) {
            Log.e(TAG, "onError: 网络连接超时");

            HttpException httpException = (HttpException) e;
            int code = httpException.code();

            if (code >= 400 && code <= 417) {
                Log.e(TAG, "onError: 访问地址异常，请稍后再试");

            } else if (code >= 500 && code <= 505) {
                Log.e(TAG, "onError: 服务器繁忙，请稍后再试");

            } else {
                Log.e(TAG, "onError: 网络连接异常");
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

            Log.e(TAG, "onError: 数据异常，请稍后再试");

        } else {
            Log.e(TAG, "onError: 未知错误，请稍后再试");
        }

    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }

    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
