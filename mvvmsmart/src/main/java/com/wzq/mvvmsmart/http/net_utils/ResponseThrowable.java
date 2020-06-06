package com.wzq.mvvmsmart.http.net_utils;

class ResponseThrowable extends Exception {
    private int code;
    String message;

    ResponseThrowable(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
