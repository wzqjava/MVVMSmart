package com.wzq.mvvmsmart.http.base;


import android.accounts.NetworkErrorException;
import com.wzq.mvvmsmart.http.net_utils.Utils;
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
    public void onNext(BaseResponse<T> baseResponse) {
        if (baseResponse != null && (baseResponse.getCode() == 0 || baseResponse.getCode() == 200)) {
            onSuccess(baseResponse);
        } else {
            if (baseResponse.getCode() == INVALID_TOKEN
                    || baseResponse.getCode() == UNAUTHORIZED
                    || baseResponse.getCode() == SIGNATURE_DENIED
                    || baseResponse.getCode() == BAD_CREDENTIALS
                    || baseResponse.getCode() == UNAUTHORIZED_CLIENT
                    || baseResponse.getCode() == INVALID_GRANT
                    || baseResponse.getCode() == REDIRECT_URI_MISMATCH
                    || baseResponse.getCode() == ACCOUNT_DISABLED
                    || baseResponse.getCode() == ACCOUNT_EXPIRED
                    || baseResponse.getCode() == CREDENTIALS_EXPIRED
                    || baseResponse.getCode() == ACCOUNT_LOCKED
                    || baseResponse.getCode() == USERNAME_NOT_FOUND
                    || baseResponse.getCode() == ACCESS_DENIED_AUTHORITY_EXPIRED) {
                Utils.toLogin(); // 无效token,跳转到登陆
            } else
                onCodeError(baseResponse);
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
    public abstract void onSuccess(BaseResponse<T> baseResponse);

    //请求成功但返回code码不是200的回调方法，这里抽象方法声明
    public abstract void onCodeError(BaseResponse baseResponse);

    /***
     * 请求失败回调方法，这里抽象方法声明
     * @param isCauseNetReason 是否由于网络造成的失败 true是
     */
    public abstract void onFailure(Throwable throwable, boolean isCauseNetReason) throws Exception;
}
