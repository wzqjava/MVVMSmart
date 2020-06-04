package com.wzq.sample.data.source.http.service2;

import android.annotation.SuppressLint;
import android.content.Context;
import com.wzq.sample.bean.NewsDataItem;
import com.wzq.sample.bean.User;
import com.wzq.mvvmsmart.http2.base.BaseRequest;
import com.wzq.mvvmsmart.http2.base.BaseResponse;
import com.wzq.mvvmsmart.http2.listener.OnServerResponseListener;
import io.reactivex.Observable;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author :王志强
 * date   : 2019/11/12 11:10
 */
public class MRequest extends BaseRequest {

    private DemoApiService2 service;

    public static MRequest getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {

        @SuppressLint("StaticFieldLeak")
        static MRequest INSTANCE = new MRequest();
    }

    private MRequest() {
        super();
        this.service = retrofit.create(DemoApiService2.class);
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

    // 获取个人信息2
    public void demoGet(Context context, int what, int num, OnServerResponseListener listener) {
        this.mContext = context;
        //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Observable<BaseResponse<List<NewsDataItem>>> observable = service.demoGet();
        doRequest(observable, what, listener);
    }

}
