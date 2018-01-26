package com.zhao.bill.mvp_rxjava_retfoift.retroift.base;

import java.io.Serializable;

/**
 * 服务器通用返回数据格式  返回的data是object
 * Created by Bill on 2016/11/8.
 */
public class BaseEntity<E> implements Serializable {

    private int code;
    private String message;
    private String result;
    private E data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
