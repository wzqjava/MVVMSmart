package com.wzq.sample.ui.testnet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wzq.mvvmsmart.http.BaseResponse
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.mvvmsmart.utils.RxUtils
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.data.source.http.service.DemoApiService
import com.wzq.sample.utils.RetrofitClient
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * 王志强
 * Create Date：2019/01/25
 * Description：
 */
class TestNetViewModel(application: Application) : BaseViewModel(application) {
    //给RecyclerView添加ObservableList
    var resultJson: MutableLiveData<String?>

    //线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
    // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
    //  请求与ViewModel周期同步
    val data: Unit
        get() {
            val apiService: DemoApiService = RetrofitClient.instance.create(DemoApiService::class.java)
            apiService.jsonFile
                    .compose(RxUtils.observableToMain()) //线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
                    .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                    .doOnSubscribe(this@TestNetViewModel) //  请求与ViewModel周期同步
                    .subscribe()

            /* object : Observer<BaseResponse<>> {
                 override fun onSubscribe(d: Disposable) {
                     KLog.e("开始请求...")
                 }

                 override fun onNext(response: BaseResponse<Any?>) {
                     KLog.e("返回数据: $response")
                     resultJson.postValue(response.toString())
                 }

                 override fun onError(throwable: Throwable) {
                     KLog.e("进入onError" + throwable.message)
                 }

                 override fun onComplete() {
                     KLog.e("进入onComplete")
                 }
             }*/
        }

    init {
        resultJson = MutableLiveData()
    }
}