package com.wzq.sample.ui.recycler_single_network

import com.wzq.mvvmsmart.base.BaseModelMVVM
import com.wzq.mvvmsmart.http.base.BaseResponse
import com.wzq.sample.bean.NewsData
import com.wzq.sample.net.MRequest
import io.reactivex.Completable
import io.reactivex.Observable

/**
 * 作者：王志强
 * 创建时间：2020/4/13
 * 文件描述：
 */
class NetWorkModel : BaseModelMVVM() {
    /*fun demoGet(pageNum: Int): Observable<BaseResponse<DemoBean>> {
        val instance: RetrofitClient = RetrofitClient.instance
        val demoApiService = instance.create(DemoApiService::class.java)
        return demoApiService.demoGet(pageNum)
    }*/
    /*MRequest.getInstance().demoGet( 0, 1, object : OnServerResponseListener<List<NewsDataItem>> {
        override fun success(what: Int, isQualified: Boolean, baseResponse: BaseResponse<List<NewsDataItem>>) {
            KLog.e("=====success=========");
            when (what) {
                0 -> {
                    val data = baseResponse.data
                    KLog.e(data)
                    userLiveData.value = data
                }
                else -> {
                }
            }
        }

        override fun error(what: Int, throwable: Throwable?) {
            KLog.e("=====error=========throwable:" + throwable?.message)
        }

        override fun reTry(what: Int) {
            KLog.e("=====reTry=========");
        }
    })*/
    fun demoGet(pageNum: Int): Observable<BaseResponse<ArrayList<NewsData>>> {
        return MRequest.getInstance().demoGetNews(0, pageNum)
    }

    fun loadMore(): Completable? {
        /* fun loadMore(): Observable<DemoBean> {
             return Observable.create<DemoBean> { observableEmitter ->
                 val entity = DemoBean()
                 val students: MutableList<ItemsEntity> = ArrayList()
                 //模拟一部分假数据
                 for (i in 0..9) {
                     val student = ItemsEntity()
                     student.id = -1
                     student.name = "模拟条目"
                     students.add(student)
                 }
                 entity.items = students
                 observableEmitter.onNext(entity)
             }.delay(2, TimeUnit.SECONDS) //延迟3秒
         }*/
        return null
    }
}