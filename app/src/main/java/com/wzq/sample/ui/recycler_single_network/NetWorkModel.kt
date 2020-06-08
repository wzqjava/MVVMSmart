package com.wzq.sample.ui.recycler_single_network

import com.wzq.mvvmsmart.base.BaseModelMVVM
import com.wzq.mvvmsmart.net.base.BaseResponse
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
    fun demoGet(pageNum: Int): Observable<BaseResponse<ArrayList<NewsData>>> {
        return MRequest.getInstance().demoGetNews(0, pageNum)
    }

    fun loadMore(): Completable? {
        return null
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
    }
}