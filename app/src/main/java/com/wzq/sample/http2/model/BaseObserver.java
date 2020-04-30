package com.wzq.sample.http2.model;


import android.accounts.NetworkErrorException;

import com.wzq.sample.http2.utils.Utils;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * created 王志强 2020.04.30
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    // 退出登录
    public final static int INVALID_TOKEN = 2000;//（invalid_token）- 不是正确的token
    private final static int UNAUTHORIZED = 2010;//（unauthorized）- 未认证
    private final static int SIGNATURE_DENIED = 2011;//（signature_denied）-签名失败
    private final static int BAD_CREDENTIALS = 3000;//（bad_credentials）-错误凭证
    private final static int UNAUTHORIZED_CLIENT = 2006;//（unauthorized_client）- 无权限客户端
    private final static int INVALID_GRANT = 2004;//（invalid_grant）- 无效的授权
    private final static int REDIRECT_URI_MISMATCH = 2005;//（redirect_uri_mismatch）- 授权无效
    private final static int ACCOUNT_DISABLED = 3001;//（account_disabled）- 账号被禁用
    private final static int ACCOUNT_EXPIRED = 3002;//（account_expired）- 账号过期
    private final static int CREDENTIALS_EXPIRED = 3003;//（credentials_expired）- 认证过期
    private final static int ACCOUNT_LOCKED = 3004;//（account_locked）- 账号已锁
    private final static int USERNAME_NOT_FOUND = 3005;//（username_not_found）- 找不到用户名
    private final static int ACCESS_DENIED_AUTHORITY_EXPIRED = 4033;//（access_denied_authority_expired）- 认证过期访问拒绝

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> tBaseResponse) {
        if (tBaseResponse != null && (tBaseResponse.getCode() == 0 || tBaseResponse.getCode() == 200)) {
            onSuccess(tBaseResponse);
        } else {
            if (tBaseResponse.getCode() == INVALID_TOKEN
                    || tBaseResponse.getCode() == UNAUTHORIZED
                    || tBaseResponse.getCode() == SIGNATURE_DENIED
                    || tBaseResponse.getCode() == BAD_CREDENTIALS
                    || tBaseResponse.getCode() == UNAUTHORIZED_CLIENT
                    || tBaseResponse.getCode() == INVALID_GRANT
                    || tBaseResponse.getCode() == REDIRECT_URI_MISMATCH
                    || tBaseResponse.getCode() == ACCOUNT_DISABLED
                    || tBaseResponse.getCode() == ACCOUNT_EXPIRED
                    || tBaseResponse.getCode() == CREDENTIALS_EXPIRED
                    || tBaseResponse.getCode() == ACCOUNT_LOCKED
                    || tBaseResponse.getCode() == USERNAME_NOT_FOUND
                    || tBaseResponse.getCode() == ACCESS_DENIED_AUTHORITY_EXPIRED) {
                Utils.toLogin();
            } else
                onCodeError(tBaseResponse);
        }
    }

    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof ConnectException ||
                    e instanceof TimeoutException ||
                    e instanceof NetworkErrorException ||
                    e instanceof UnknownHostException) {
                onFailure(e, true);
            } else {
                onFailure(e, false);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    //请求成功且返回码为200的回调方法，这里抽象方法声明
    public abstract void onSuccess(BaseResponse<T> tBaseResponse);

    //请求成功但返回code码不是200的回调方法，这里抽象方法声明
    public abstract void onCodeError(BaseResponse tBaseResponse);

    /***
     * 请求失败回调方法，这里抽象方法声明
     * @param netWork 是否由于网络造成的失败 true是
     */
    public abstract void onFailure(Throwable e, boolean netWork) throws Exception;
}
