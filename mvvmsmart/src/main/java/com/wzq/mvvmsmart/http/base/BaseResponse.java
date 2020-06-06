package com.wzq.mvvmsmart.http.base;

/**
 * created 王志强 2020.04.30
 * 根据服务器返回决定字段(不同公司字段有差异,任意修改即可)
 */
public class BaseResponse<T> {

    private int currentTime;
    private int totalRows;
    private String message;
    private int status;
    private String info;
    private int code;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

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
        return "BaseResponse{"
                + "currentTime="
                + currentTime
                + ", totalRows="
                + totalRows
                + ", message='"
                + message
                + '\''
                + ", code="
                + code
                + ", data="
                + data
                + '}';
    }
}
