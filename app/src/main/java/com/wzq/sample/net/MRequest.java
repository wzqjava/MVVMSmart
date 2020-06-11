package com.wzq.sample.net;

import android.annotation.SuppressLint;
import com.wzq.mvvmsmart.net.base.BaseRequest;
import com.wzq.mvvmsmart.net.base.BaseResponse;
import com.wzq.sample.bean.DemoBean;
import com.wzq.sample.bean.NewsData;
import io.reactivex.Observable;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * author :王志强
 * date   : 2019/11/12 11:10
 */
public class MRequest extends BaseRequest {

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

    // 获取列表条目
    public Observable<BaseResponse<DemoBean>> demoGet(int what, int num) {
        Observable<BaseResponse<DemoBean>> observable = service.demoGet(num);
        return observable;
    }

    // 获取新闻列表
    public Observable<BaseResponse<ArrayList<NewsData>>> doGetServerNews(int pageNum) {
        Observable<BaseResponse<ArrayList<NewsData>>> observable = service.doGetServerNews(pageNum);
        return observable;
    }

    // post请求
    public Observable<BaseResponse<ArrayList<NewsData>>> doPostServerNews(String jsonParams) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        Observable<BaseResponse<ArrayList<NewsData>>> observable = service.doPostServerNews(requestBody);
        return observable;
    }

}
