package com.wzq.mvvmsmart.http2.observer;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wzq.mvvmsmart.http2.listener.OnServerResponseListener;
import com.wzq.mvvmsmart.http2.listener.OnServerResponseListener2;
import com.wzq.mvvmsmart.http2.base.BaseResponse;
import com.wzq.mvvmsmart.http2.net_utils.Utils;
import java.lang.reflect.Type;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * created 王志强 2020.04.30
 */
public class HttpDisposableObserver<T> extends DisposableObserver<BaseResponse<T>> {
    // 刷新 token
    private final static int EXPIRED_TOKEN = 2007;//（expired_token）- token过期

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
    // 其他
    public static final int RESPONSE_LOG_RETRY = 401;
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
    private final static int TOO_MANY_REQUESTS = 4029;//（too_many_requests）- 太多请求
    private final static int ACCESS_DENIED = 4030;//（access_denied）- 访问拒绝
    private final static int NO_VERSION = 4037;//（无版本号）-无版本号
    private final static int ERROR = 5000;//（error）- 系统异常
    private final static int SERVICE_UNAVAILABLE = 5003;//（service_unavailable）- 服务不可用
    private final static int GATEWAY_TIMEOUT = 5004;//（gateway_timeout）- 网关超时


    private static final int RESPONSE_OK = 200;
    private int what;
    private OnServerResponseListener<T> listener;
    private Context context;

    /**
     * 是否需要所有的错误响应回调
     */
    private boolean isAllErrorResponse;

    public HttpDisposableObserver(Context context, int what, OnServerResponseListener<T> listener) {
        this(context, what, true, listener);
    }


    public HttpDisposableObserver(Context context, int what, boolean isAllErrorResponse, OnServerResponseListener<T> listener) {
        this.context = context;
        this.what = what;
        this.isAllErrorResponse = isAllErrorResponse;
        this.listener = listener;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (listener instanceof OnServerResponseListener2) {
            ((OnServerResponseListener2) listener).onStart();
        }

    }

    @Override
    public void onNext(BaseResponse<T> t) {
        if (t == null) {
            if (listener != null) listener.error(what, new Throwable("后台直接返回空了"));
            return;
        }
        int code = t.getCode();
        switch (code) {
            case 0:
            case RESPONSE_OK:
                if (listener != null) listener.success(what, true, t);
                break;
            case RESPONSE_LOG_RETRY:
                break;
            case INVALID_TOKEN:
            case UNAUTHORIZED:
            case SIGNATURE_DENIED:
            case BAD_CREDENTIALS:
            case UNAUTHORIZED_CLIENT:
            case INVALID_GRANT:
            case REDIRECT_URI_MISMATCH:
            case ACCOUNT_DISABLED:
            case ACCOUNT_EXPIRED:
            case CREDENTIALS_EXPIRED:
            case ACCOUNT_LOCKED:
            case USERNAME_NOT_FOUND:
            case ACCESS_DENIED_AUTHORITY_EXPIRED:
                Utils.toLogin();
                break;
            default:
                if (isAllErrorResponse) {
                    if (listener != null) listener.success(what, false, t);
                } else {
                    if (listener != null) listener.error(what, new Throwable("Error:数据返回错误"));
                }
                break;
        }
    }

    @Override
    public void onError(Throwable throwable) {
        try {
            if (throwable instanceof HttpException) {//处理服务器返回的非成功异常
                ResponseBody responseBody = ((HttpException) throwable).response().errorBody();

                if (responseBody != null) {
                    Type type = new TypeToken<BaseResponse<Object>>() {
                    }.getType();

                    BaseResponse baseResponse = new Gson().fromJson(responseBody.string(), type);
                    onNext(baseResponse);

                } else {
                    if (listener != null)
                        listener.error(what, throwable);
                }

            } else {
                if (listener != null)
                    listener.error(what, throwable);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.error(what, throwable);
            }
        }
        if (!this.isDisposed()) {
            this.dispose();
        }
    }

    @Override
    public void onComplete() {
        if (listener instanceof OnServerResponseListener2) {
            ((OnServerResponseListener2) listener).onComplete();
        }
        if (!this.isDisposed()) {
            this.dispose();
        }
    }

}
