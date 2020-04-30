package com.wzq.sample.http2.model;

import android.content.Context;

import com.reading.win.network.model.BaseViewModel;
import com.wzq.sample.http2.listener.OnServerResponseListener;
import com.wzq.sample.http2.observer.HttpObserver;
import com.wzq.sample.http2.utils.RetrofitUtil;

import io.reactivex.Observable;
import retrofit2.Retrofit;

/**
 * created 王志强 2020.04.30
 */
public class BaseVmRequst<T> {

    public Context mContext;
    private RetrofitUtil retrofitTool;
    public Retrofit retrofit;

    public BaseVmRequst() {
        this.retrofitTool = RetrofitUtil.getInstance();
        this.retrofit = retrofitTool.getRetrofit();
    }

    public void doRequest(Observable observable, int what, OnServerResponseListener listener) {
        retrofitTool.toSubscribe(observable, new HttpObserver(mContext, what, listener));
    }

    public void doRequest(BaseViewModel<T> model, Observable observable){
        retrofitTool.toSubscribe(model,observable);
    }

}
