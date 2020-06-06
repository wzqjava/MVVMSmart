package com.wzq.mvvmsmart.http.net_utils;


import com.wzq.mvvmsmart.http.base.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created 王志强 2020.04.30
 **/
public class RetrofitUtil<T> {

    private Retrofit retrofit;

    private RetrofitUtil() {
        OkHttpClient okHttpClient = OkHttpUtil.getInstance().okHttpsCacheClient();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(MetaDataUtil.getBaseUrl())
                .build();
    }

    public  Retrofit getRetrofit(){
        return retrofit;
    }

    // todo 这个方法没有执行
    public void toSubscribe(Observable observable, DisposableObserver<BaseResponse<T>> disposableObserver) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }

    public static RetrofitUtil getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final RetrofitUtil instance = new RetrofitUtil();
    }

}
