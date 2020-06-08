package com.wzq.mvvmsmart.net.base;

import android.accounts.NetworkErrorException;
import com.wzq.mvvmsmart.R;
import com.wzq.mvvmsmart.net.net_utils.Utils;
import com.wzq.mvvmsmart.utils.KLog;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 * created 王志强 2020.04.30
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {

    private static final int RESPONSE_OK = 200;
    private final static int INVALID_TOKEN = 2000;//（invalid_token）- 不是正确的token  // 退出登录
    private final static int EXPIRED_TOKEN = 2007;//（expired_token）- token过期    // 刷新 token
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
    private final static int TOO_MANY_REQUESTS = 4029;//（too_many_requests）- 太多请求
    private final static int ACCESS_DENIED = 4030;//（access_denied）- 访问拒绝
    private final static int ACCESS_DENIED_AUTHORITY_EXPIRED = 4033;//（access_denied_authority_expired）- 认证过期访问拒绝
    private final static int NO_VERSION = 4037;//（无版本号）-无版本号
    private final static int ERROR = 5000;//（error）- 系统异常
    private final static int SERVICE_UNAVAILABLE = 5003;//（service_unavailable）- 服务不可用
    private final static int GATEWAY_TIMEOUT = 5004;//（gateway_timeout）- 网关超时

    // 其他
    private static final int RESPONSE_LOG_RETRY = 401;
    private final static int INVALID_SCOPE = 2001;//（invalid_scope）- 没有权限
    private final static int FAIL = 1000;//（fail）- 如果访问网关
    private final static int INVALID_CLIENT = 2003;//（invalid_client）- 不是正确的客户端
    private final static int INVALID_REQUEST = 2002;//（invalid_request）- 不是正确的请求
    private final static int UNSUPPORTED_GRANT_TYPE = 2008;//（unsupported_grant_type）- 不支持权限类型
    private final static int UNSUPPORTED_RESPONSE_TYPE = 2009;//（unsupported_response_type）- 不支持放回类型
    private final static int ACCESS_DENIED_UPDATING = 4034;//（access_denied_updating）- 访问拒绝更新
    private final static int ACCESS_DENIED_DISABLED = 4035;//（access_denied_disabled）- 访问被拒绝
    private final static int ACCESS_DENIED_NOT_OPEN = 4036;//（access_denied_not_open）- 访问拒绝未被打开
    private final static int ACCESS_DENIED_BLACK_LIMITED = 4031;//（access_denied_black_limited）- 黑名单访问拒绝
    private final static int ACCESS_DENIED_WHITE_LIMITED = 4032;//（access_denied_white_limited）- 白名单访问拒绝
    private final static int BAD_REQUEST = 4000;//（bad_request）- 错误请求
    private final static int NOT_FOUND = 4001;//（not_found）- 找不到
    private final static int METHOD_NOT_ALLOWED = 4005;//（method_not_allowed）- 不允许使用方法
    private final static int MEDIA_TYPE_NOT_ACCEPTABLE = 4006;//（media_type_not_acceptable）- 媒体类型不可接受

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        //baseResponse.getStatus() ==1  这个是开源api定义的成功状态, 根据自己公司配置即可
        if (baseResponse != null && (baseResponse.getCode() == RESPONSE_OK
                || baseResponse.getCode() == 0
                || baseResponse.getStatus() == 1)) {
            // do Nothing
        } else {
            if (baseResponse == null) {
                KLog.INSTANCE.e("baseResponse is null");
                return;
            }
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
                KLog.INSTANCE.e("responseCode--异常");
                Utils.toLogin(); // 无效token,跳转到登陆
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        KLog.INSTANCE.e("进入--BaseObserver--Error");
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                KLog.INSTANCE.e(Utils.getApp().getString(R.string.net_connect_exception));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    /*

    //请求成功且返回码为200的回调方法，这里抽象方法声明
    public abstract void onSuccess(BaseResponse<T> baseResponse);

    //请求成功但返回code码不是200的回调方法，这里抽象方法声明
    public abstract void onCodeError(BaseResponse baseResponse);

    *//***
     * 请求失败回调方法，这里抽象方法声明
     * @param isCauseNetReason 是否由于网络造成的失败 true是
     *//*
    public abstract void onFailure(Throwable throwable, boolean isCauseNetReason) throws Exception;

    */
}
