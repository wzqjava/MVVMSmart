package com.wzq.sample.http2.base;

/**
 * created 王志强 2020.04.30
 * 根据服务器返回决定字段(不同公司字段有差异,任意修改即可)
 */
public class BaseResponse<T> {
    private int currentTime;
    private int totalRows;
    private String message;
    private int code;
    private T data;

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "currentTime=" + currentTime +
                ", totalRows=" + totalRows +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
