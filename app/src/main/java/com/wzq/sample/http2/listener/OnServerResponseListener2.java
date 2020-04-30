package com.wzq.sample.http2.listener;

/**
 * created 王志强 2020.04.15
 */
public interface OnServerResponseListener2<T> extends OnServerResponseListener<T> {

    /**
     * 开始加载
     */
    void onStart();

    /**
     * 加载结束
     */
    void onComplete();
}
