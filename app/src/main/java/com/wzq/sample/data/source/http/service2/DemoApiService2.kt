package com.wzq.sample.data.source.http.service2

import com.wzq.sample.bean.NewsDataItem
import com.wzq.sample.bean.User
import com.wzq.mvvmsmart.http2.base.BaseResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DemoApiService2 {
    //  获取用户个人信息
    @POST("data/center/summary")
    fun getPersonalSummary(@Body requestBody: RequestBody?): Observable<BaseResponse<User>>

    //  获取用户个人信息2
    @POST("center/summary")
    fun getPersonalSummary2(@Body requestBody: RequestBody?): Observable<BaseResponse<User>>

    //  获取网络数据
    @GET("AppNews/getNewsList/type/1/p/1")
    fun demoGet(): Observable<BaseResponse<List<NewsDataItem>>>
}