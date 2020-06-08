package com.wzq.mvvmsmart.net.exception;

class ResponseCustomException extends Exception {
    private int code;
    String message;

    ResponseCustomException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
