package com.wzq.sample.http2.service

import com.wzq.sample.bean.User
import com.wzq.sample.http2.model.BaseResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface DemoApiService {
    //  获取用户个人信息
    @POST("data/center/summary")
    fun getPersonalSummary(@Body requestBody: RequestBody?): Observable<BaseResponse<User>>

    //  获取用户个人信息2
    @POST("center/summary")
    fun getPersonalSummary2(@Body requestBody: RequestBody?): Observable<BaseResponse<User>>
}