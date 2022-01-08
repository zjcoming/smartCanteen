package com.common.requestbase;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 响应体模板
 */
public class ResponseModel<T> {
    private String message; //请求错误时，返回的错误信息，对应SUCCESS与FAILED
    private T data; //要返回的数据
    private String result; //封装当前请求的处理结果是成功还是失败

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}