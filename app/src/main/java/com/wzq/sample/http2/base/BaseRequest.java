package com.wzq.sample.http2.base;

import android.content.Context;

import com.wzq.sample.http2.listener.OnServerResponseListener;
import com.wzq.sample.http2.observer.HttpDisposableObserver;
import com.wzq.sample.http2.net_utils.RetrofitUtil;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * created 王志强 2020.04.30
 */
public class BaseRequest<T> {

    protected Context mContext;
    private RetrofitUtil retrofitTool;
    protected Retrofit retrofit;

    public BaseRequest() {
        this.retrofitTool = RetrofitUtil.getInstance();
        this.retrofit = retrofitTool.getRetrofit();
    }

    protected void doRequest(Observable observable, int what, OnServerResponseListener listener) {
        retrofitTool.toSubscribe(observable, new HttpDisposableObserver(mContext, what, listener));
    }



}
