package com.closedevice.fastapp.model.response.bk;

import com.closedevice.fastapp.model.response.BaseResult;

/**
 * Created by LSD on 2017/4/22.
 */

public class BKResult<T> implements BaseResult<T> {
    private int code;
    private String msg;
    private T results;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public boolean isOk() {
        return code == 200;
    }

    @Override
    public T getData() {
        return results;
    }
}
