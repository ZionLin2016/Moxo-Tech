package cn.lsd.app.model.response.gan;

import cn.lsd.app.model.response.BaseResult;


public class GanResult<T> implements BaseResult<T> {
    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public boolean isOk() {
        return !error;
    }

    @Override
    public T getData() {
        return results;
    }


}
