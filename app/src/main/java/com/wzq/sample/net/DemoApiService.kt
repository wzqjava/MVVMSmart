package com.wzq.sample.net

import com.wzq.mvvmsmart.net.base.BaseResponse
import com.wzq.sample.bean.DemoBean
import com.wzq.sample.bean.NewsData
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * created 王志强 2020.04.30
 */
interface DemoApiService {
    @GET("action/apiv2/banner")
    fun demoGet(@Query("catalog") pageNum: Int): Observable<BaseResponse<DemoBean>>

    //  获取网络数据
    @GET("AppNews/getNewsList/type/1/p/{pageNum}")
    fun doGetServerNews(@Path("pageNum") newsId: Int): Observable<BaseResponse<ArrayList<NewsData>>>

    //  获取网络数据
    @POST("AppNews/getNewsList/type/1/p/1")
    fun doPostServerNews(@Body requestBody: RequestBody): Observable<BaseResponse<ArrayList<NewsData>>>

}