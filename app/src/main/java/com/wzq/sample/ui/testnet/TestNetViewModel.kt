package com.wzq.sample.ui.testnet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.net.MRequest
import com.wzq.mvvmsmart.net.base.BaseResponse
import com.wzq.mvvmsmart.net.net_utils.GsonUtil
import com.wzq.mvvmsmart.net.observer.DefaultObserver
import com.wzq.mvvmsmart.net.net_utils.RxUtil
import com.wzq.sample.bean.NewsData
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * 王志强
 * Create Date：2019/01/25
 * Description：业务类
 */
class TestNetViewModel(application: Application) : BaseViewModel(application) {
    //给RecyclerView添加ObservableList
    var liveData = MutableLiveData<List<NewsData>>()

    /**
    线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
    网络错误的异常转换, 这里可以换成自己的ExceptionHandle
    请求与ViewModel周期同步
    获取个人信息
     */
    fun doPostServerNews() {
//        val param: Map<String, String> = HashMap()
        // 这里的参数是随便模拟的
        val param = mapOf("key" to 24,"name" to "zhangsan","age" to 25)
        val observable = MRequest.getInstance().doPostServerNews(GsonUtil.bean2String(param))
        observable.compose(RxUtil.observableToMain()) //线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
                .compose(RxUtil.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this@TestNetViewModel) //  请求与ViewModel周期同步
                .subscribe(object : DefaultObserver<ArrayList<NewsData>>() {
                    override fun onSubscribe(d: Disposable) {
                        stateLiveData.postLoading()
                    }

                    override fun onNext(baseResponse: BaseResponse<ArrayList<NewsData>>) {
                        super.onNext(baseResponse)
                        KLog.e("进入onNext")
                        // 请求成功
                        if (baseResponse.status == 1) {  // 接口返回code=1 代表成功
                            val news = baseResponse.data
                            if (news != null) {
                                if (news.isNotEmpty()) {
                                    liveData.postValue(news)
                                } else {
                                    //                                    showShortToast("没有更多数据了")
                                    KLog.e("请求到数据students.size" + news.size)
                                }
                            } else {
                                KLog.e("数据返回null")
                                stateLiveData.postError()
                            }
                        }
                    }

                    override fun onError(throwable: Throwable) {
                        KLog.e("进入onError" + throwable.message)
                        //关闭对话框
                        stateLiveData.postError()
                        /* if (throwable is ResponseThrowable) {
                         showShortToast(throwable.message)
                         }*/
                    }

                    override fun onComplete() {
                        KLog.e("进入onComplete")
                        //关闭对话框
                    }

                })

    }
}
