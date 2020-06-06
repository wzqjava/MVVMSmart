package com.wzq.sample.net

import com.wzq.mvvmsmart.http.base.BaseResponse
import com.wzq.sample.bean.DemoBean
import com.wzq.sample.bean.NewsData
import io.reactivex.Observable
import retrofit2.http.*

/**
 * created 王志强 2020.04.30
 */
interface DemoApiService {
    @GET("action/apiv2/banner")
    fun demoGet(@Query("catalog") pageNum: Int): Observable<BaseResponse<DemoBean>>

    //  获取网络数据
    @GET("AppNews/getNewsList/type/1/p/{pageNum}")
    fun demoGetNews(@Path("pageNum") newsId: Int): Observable<BaseResponse<ArrayList<NewsData>>>

}