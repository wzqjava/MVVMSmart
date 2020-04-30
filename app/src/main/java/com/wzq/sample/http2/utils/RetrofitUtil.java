package com.wzq.sample.http2.utils;


import com.reading.win.network.model.BaseViewModel;
import com.wzq.sample.http2.OkHttpClientHelper;
import com.wzq.sample.http2.model.BaseObserver;
import com.wzq.sample.http2.model.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    public void toSubscribe(BaseViewModel<T> model, Observable o) {
        o.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new BaseObserver<T>() {

              @Override
              public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                model.getMDis().add(d);
              }

              @Override
                public void onSuccess(BaseResponse<T> tBaseResponse) {
                   model.getCode().setValue(200);
                   model.getData().setValue(tBaseResponse.getData());
                }

                @Override
                public void onCodeError(BaseResponse tBaseResponse) {
                  model.getCode().setValue(-1);
                  model.getData().setValue(null);//加载占位图
                  model.getMessage().setValue(tBaseResponse.getMessage());
                }

                @Override
                public void onFailure(Throwable e, boolean netWork) throws Exception {
                  model.getCode().setValue(-1);
                  model.getData().setValue(null);//加载占位图
                  model.getMessage().setValue(e.getMessage());
                }
            });
    }

    public static RetrofitUtil getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final RetrofitUtil instance = new RetrofitUtil();
    }

}
