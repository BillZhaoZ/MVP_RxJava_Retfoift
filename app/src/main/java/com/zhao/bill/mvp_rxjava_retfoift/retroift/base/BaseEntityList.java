package com.zhao.bill.mvp_rxjava_retfoift.retroift.base;

import java.io.Serializable;
import java.util.List;

/**
 * 服务器通用返回数据格式  返回的data是list
 * Created by Bill on 2016/11/8.
 */
public class BaseEntityList<E> implements Serializable {

    private int code;
    private String message;
    private String result;
    private List<E> data;

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

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}