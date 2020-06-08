package com.wzq.mvvmsmart.net.token;

import android.os.ConditionVariable;

import com.google.gson.reflect.TypeToken;
import com.wzq.mvvmsmart.net.base.BaseResponse;
import com.wzq.mvvmsmart.net.net_utils.GsonUtil;
import com.wzq.mvvmsmart.net.net_utils.MetaDataUtil;
import com.wzq.mvvmsmart.net.net_utils.MmkvUtils;
import com.wzq.mvvmsmart.utils.KLog;

import java.io.IOException;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created 王志强 2020.04.30
 */
public class TokenUtils {
    public static boolean isTokenExpired(String response) {
        boolean isTokenExpired = false;
        try {
            Type type = new TypeToken<BaseResponse<Object>>() {
            }.getType();
            BaseResponse<Object> baseResponse = GsonUtil.getGson().fromJson(response, type);
            if (baseResponse != null) {
                int remainTime = MmkvUtils.getIntValue("expirationTime") - baseResponse.getCurrentTime();//过期时间现在单位是s
//                KLog.e("token:remainTime----"+remainTime);
                if (remainTime < 0) {
                    return false;
                }
//                if (baseResponse.getCode() == 2007 || remainTime < 60 ) {
                if (remainTime < 60 * 60 * 47) {//提前2天的时候自动刷新
                    return true;
                }
                return isTokenExpired;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return isTokenExpired;
    }

    public static String getNewToken() {
        final ConditionVariable variable = new ConditionVariable();
        // 通过获取token的接口，同步请求接口
        if ("".equals(MmkvUtils.getStringValue("refreshToken"))) {
            return "";
        }
        final String[] newToken = new String[1];
        Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                try {
                    KLog.INSTANCE.e("刷新token前  refreshToken：" + MmkvUtils.getStringValue("refreshToken"));
                    Request request = new Request.Builder()
                            .url(MetaDataUtil.getBaseUrl() + "/chinese/student/refreshToken?refreshToken=" + MmkvUtils.getStringValue("refreshToken"))
                            .build();

                    Response response = new OkHttpClient().newCall(request).execute();
                    String result = response.body().string();
                    KLog.INSTANCE.e("网络返回 刷新token请求结果：" + result);
                    Type type = new TypeToken<BaseResponse<TokenBean>>() {
                    }.getType();
                    BaseResponse<TokenBean> tokenBeanBaseResponse = GsonUtil.getGson().fromJson(result, type);
                    KLog.INSTANCE.e("TokenBean----------" + tokenBeanBaseResponse.getData());
                    if (tokenBeanBaseResponse != null && tokenBeanBaseResponse.getCode() == 0) {
                        newToken[0] = tokenBeanBaseResponse.getData().getAccessToken();
                        MmkvUtils.putStringValue("accessToken", tokenBeanBaseResponse.getData().getAccessToken());
                        MmkvUtils.putStringValue("refreshToken", tokenBeanBaseResponse.getData().getRefreshToken());
                        MmkvUtils.putIntValue("expirationTime", tokenBeanBaseResponse.getData().getExpirationTime());
                    } else {
                        newToken[0] = MmkvUtils.getStringValue("accessToken");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    emitter.onNext("");
                }
                emitter.onNext("");
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        variable.open();
                    }
                });

        variable.block();

        return newToken[0];
    }
}
