package com.wzq.sample.http2.net_utils;


import com.wzq.sample.http2.OkHttpClientHelper;
import com.wzq.sample.http2.base.BaseResponse;

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
        OkHttpClient okHttpClient = OkHttpClientHelper.getInstance().okHttpsCacheClient();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(MetaDataUtil.getBaseUrl())
                .build();
    }

    public  Retrofit getRetrofit(){
        return retrofit;
    }

    public void toSubscribe(Observable o, DisposableObserver<BaseResponse<T>> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public static RetrofitUtil getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final RetrofitUtil instance = new RetrofitUtil();
    }

}
