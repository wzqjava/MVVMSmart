package com.wzq.sample.data.source.http.service

import com.wzq.mvvmsmart.http.BaseResponse
import com.wzq.sample.bean.DemoBean
import io.reactivex.Observable
import retrofit2.http.*

/**
 * created 王志强 2020.04.30
 */
interface DemoApiService {
    @GET("action/apiv2/banner")
    fun demoGet(@Query("catalog") pageNum: Int): Observable<BaseResponse<DemoBean>>

    @FormUrlEncoded
    @POST("action/apiv2/banner")
    fun demoPost(@Field("catalog") catalog: String?): Observable<BaseResponse<DemoBean>>

    @get:GET("getJsonFile")
    val jsonFile: Observable<BaseResponse<Any>>

}