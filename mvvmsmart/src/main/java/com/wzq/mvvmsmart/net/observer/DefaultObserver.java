package com.wzq.mvvmsmart.net.observer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wzq.mvvmsmart.net.base.BaseObserver;
import com.wzq.mvvmsmart.net.base.BaseResponse;
import com.wzq.mvvmsmart.utils.KLog;
import io.reactivex.disposables.Disposable;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * created 王志强 2020.04.30
 * Observer回调的统一处理,封装的状态码一般服务器定好好,Android判断使用即可;
 * 训词根据自己的公司服务端情况修改;
 */

public abstract class DefaultObserver<T> extends BaseObserver<T> {

    @Override
    public void onSubscribe(Disposable d) {
        super.onSubscribe(d);
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        super.onNext(baseResponse);
        if (baseResponse == null) {
            return;
        }
        int code = baseResponse.getCode();
        switch (code) {
            case 0:
            default:
                break;
        }
    }

    @Override
    public void onError(Throwable throwable) {
        KLog.INSTANCE.e("进入--DefaultObserver--onError");
        super.onError(throwable);
        try {
            if (throwable instanceof HttpException) {   //  处理服务器返回的非成功异常
                ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
                if (responseBody != null) {
                    Type type = new TypeToken<BaseResponse<Object>>() {
                    }.getType();

                    BaseResponse baseResponse = new Gson().fromJson(responseBody.string(), type);
                    onNext(baseResponse);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onComplete() {
        super.onComplete();
    }

}
