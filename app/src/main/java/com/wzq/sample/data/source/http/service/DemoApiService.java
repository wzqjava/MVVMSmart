package com.wzq.sample.data.source.http.service;


import com.wzq.mvvmsmart.http.BaseResponse;
import com.wzq.sample.bean.DemoBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface DemoApiService {

    @GET("action/apiv2/banner")
    Observable<BaseResponse<DemoBean>> demoGet(@Query("catalog") int pageNum);

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    Observable<BaseResponse<DemoBean>> demoPost(@Field("catalog") String catalog);

    @GET("getJsonFile")
    Observable<BaseResponse<Object>> getJsonFile();
}
