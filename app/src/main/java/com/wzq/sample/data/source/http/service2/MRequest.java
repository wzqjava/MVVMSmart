package com.wzq.sample.data.source.http.service2;

import android.annotation.SuppressLint;
import android.content.Context;

import com.wzq.sample.bean.User;
import com.wzq.sample.http2.listener.OnServerResponseListener;
import com.wzq.sample.http2.model.BaseResponse;
import com.wzq.sample.http2.model.BaseVmRequst;
import com.wzq.sample.http2.service.DemoApiService;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author :王志强
 * date   : 2019/11/12 11:10
 */
public class MRequest extends BaseVmRequst {
    private DemoApiService service;

    public static MRequest getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        @SuppressLint("StaticFieldLeak")
        static MRequest INSTANCE = new MRequest();
    }

    private MRequest() {
        super();
        this.service = retrofit.create(DemoApiService.class);
    }
    // 获取个人信息
    public void getPersonalSummary(Context context, int what, String jsonParams, OnServerResponseListener listener) {
        this.mContext = context;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Observable<BaseResponse<User>> observable = service.getPersonalSummary(requestBody);
        doRequest(observable, what, listener);
    }

    // 获取个人信息2
    public void getPersonalSummary2(Context context, int what, String jsonParams, OnServerResponseListener listener) {
        this.mContext = context;
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Observable<BaseResponse<User>> observable = service.getPersonalSummary2(requestBody);
        doRequest(observable, what, listener);
    }

}
