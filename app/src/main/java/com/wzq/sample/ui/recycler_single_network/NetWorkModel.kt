package com.wzq.sample.ui.recycler_single_network

import com.wzq.mvvmsmart.base.BaseModelMVVM
import com.wzq.mvvmsmart.http.BaseResponse
import com.wzq.sample.bean.DemoBean
import com.wzq.sample.data.source.http.service.DemoApiService
import com.wzq.sample.utils.RetrofitClient
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * 作者：王志强
 * 创建时间：2020/4/13
 * 文件描述：
 */
class NetWorkModel : BaseModelMVVM() {
    fun demoGet(pageNum: Int): Observable<BaseResponse<DemoBean>> {
        val instance: RetrofitClient = RetrofitClient.instance
        val demoApiService = instance.create(DemoApiService::class.java)
        return demoApiService.demoGet(pageNum)
    }

    fun loadMore(): Completable? {
        return null
    }
}