package com.wzq.sample.ui.recycler_network

import com.wzq.mvvmsmart.base.BaseModelMVVM
import com.wzq.mvvmsmart.net.base.BaseResponse
import com.wzq.sample.bean.NewsData
import com.wzq.sample.net.MRequest
import io.reactivex.Observable

/**
 * 作者：王志强
 * 创建时间：2020/4/13
 * 文件描述：
 */
class NetWorkModel : BaseModelMVVM() {
    /**
     * 数据来自内存
     */
    fun doGetServerNews1(pageNum: Int): Observable<BaseResponse<ArrayList<NewsData>>> {
        return MRequest.getInstance().doGetServerNews(pageNum)
    }

    /**
     * 数据来自DB
     */
    fun doGetServerNews2(pageNum: Int): Observable<BaseResponse<ArrayList<NewsData>>> {
        return MRequest.getInstance().doGetServerNews(pageNum)
    }

    /**
     * 数据来自网络
     */
    fun doGetServerNews(pageNum: Int): Observable<BaseResponse<ArrayList<NewsData>>> {
        return MRequest.getInstance().doGetServerNews(pageNum)
    }

}