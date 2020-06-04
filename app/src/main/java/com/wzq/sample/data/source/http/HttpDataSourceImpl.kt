package com.wzq.sample.data.source.http

import com.wzq.mvvmsmart.http.BaseResponse
import com.wzq.sample.bean.DemoBean
import com.wzq.sample.bean.DemoBean.ItemsEntity
import com.wzq.sample.data.source.http.service.DemoApiService
import io.reactivex.Observable
import java.util.*
import java.util.concurrent.TimeUnit

class HttpDataSourceImpl private constructor(private val apiService: DemoApiService) {
    fun login(): Observable<Any> {
        // TODO: wzq 2019/12/18  延迟时间
        return Observable.just(Any()).delay(1, TimeUnit.SECONDS) //延迟3秒
    }

    fun loadMore(): Observable<DemoBean> {
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
    }

    fun demoGet(pageNum: Int): Observable<BaseResponse<DemoBean>> {
        return apiService.demoGet(pageNum)
    }

    fun demoPost(catalog: String?): Observable<BaseResponse<DemoBean>> {
        return apiService.demoPost(catalog)
    }

    companion object {
        @Volatile
        private var INSTANCE: HttpDataSourceImpl? = null
        fun getInstance(apiService: DemoApiService): HttpDataSourceImpl? {
            if (INSTANCE == null) {
                synchronized(HttpDataSourceImpl::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = HttpDataSourceImpl(apiService)
                    }
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}