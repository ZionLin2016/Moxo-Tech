package cn.lsd.app.model.response;


public interface BaseResult<T> {
    boolean isOk();

    T getData();
}
