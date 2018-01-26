package com.zhao.bill.mvp_rxjava_retfoift.retroift.exception;

/**
 * 异常处理
 * Created by Bill on 2018/1/25.
 */
public class ResultException extends RuntimeException {

    private int erroCode;

    public ResultException(String message, int erroCode) {
        super(message);
        this.erroCode = erroCode;
    }

    public int getErroCode() {
        return erroCode;
    }
}