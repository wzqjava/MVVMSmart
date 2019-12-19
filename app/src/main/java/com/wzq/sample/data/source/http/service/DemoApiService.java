package com.wzq.sample.data.source.http.service;


import com.wzq.sample.entity.DemoBean;
import com.wzq.mvvmsmart.http.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface DemoApiService {

    @GET("action/apiv2/banner?catalog=1")
    Observable<BaseResponse<DemoBean>> demoGet();

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    Observable<BaseResponse<DemoBean>> demoPost(@Field("catalog") String catalog);
}
