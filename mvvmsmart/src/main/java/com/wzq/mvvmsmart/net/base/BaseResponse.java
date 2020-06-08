package com.wzq.mvvmsmart.net.base;

/**
 * created 王志强 2020.04.30
 * 根据服务器返回决定字段(不同公司字段有差异,任意修改即可)
 */
public class BaseResponse<T> {

    private int status; // 服务器返回的状态码
    private int code;   // 服务器返回的状态码
    private String message; // 服务器返回的状态信息
    private String info;  // 服务器返回的状态信息
    private T data; // 服务器返回的数据封装
    private int currentTime; // 用来判断token是否过期,TokenUtils中使用

    /*
     * 不同公司是否代表成功的code不同, 也许变量名也不同,这里用isOK来封装,根据自己公司情况进行判断
     */
    private boolean isOk;

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

    public String getMessage() {
        return message;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
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

    /**
     * 网络返回
     *
     * @return 自己公司服务器代表成功返回的唯一字段, 我们用isOk来封装
     */
    public boolean isOk() {
        //return code == 0;
        //return code == 200;
        return status == 1;

    }

}
