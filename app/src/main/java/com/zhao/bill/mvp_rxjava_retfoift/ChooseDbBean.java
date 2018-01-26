package com.zhao.bill.mvp_rxjava_retfoift;

import java.util.List;

/**
 * 选择病例库
 * Created by Bill on 2018/1/22.
 */

public class ChooseDbBean {

    /**
     * data : [{"database":"801B0A502F00022E","disease_word":"medbanks数据库数据库"},{"database":"801B0A502F00022F","disease_word":"医凌肿瘤数据库数据库"},{"database":"801B0A502F000236","disease_word":"开发修改问题数据库数据库"},{"database":"801B0A502F00023C","disease_word":"数据库加载过慢数据库"},{"database":"801B0A502F000240","disease_word":"数据加载过慢-验证问题数据库"}]
     * code : 0
     * message :
     * result : error
     */

    private int code;
    private String message;
    private String result;
    private List<DataBean> data;

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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * database : 801B0A502F00022E
         * disease_word : medbanks数据库数据库
         */

        private String database;
        private String disease_word;

        public String getDatabase() {
            return database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getDisease_word() {
            return disease_word;
        }

        public void setDisease_word(String disease_word) {
            this.disease_word = disease_word;
        }
    }
}
