package com.wzq.mvvmsmart.net.net_utils;

import com.wzq.mvvmsmart.net.exception.ExceptionInterceptorHandle;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 有关Rx的工具类
 *  线程调度器
 */
public class RxUtil {

    public static <T> ObservableTransformer<T, T> observableToMain() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> ObservableTransformer<T, T> observableBothToIo() {
        return upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
    }

    public static <T> ObservableTransformer<T, T> exceptionTransformer() {
        return observable ->
                observable.onErrorResumeNext(new HttpResponseFunction());
    }

    private static class HttpResponseFunction<T> implements Function<Throwable, Observable<T>> {

        @Override
        public Observable<T> apply(Throwable throwable) {
            return Observable.error(ExceptionInterceptorHandle.handleException(throwable));
        }
    }



}
