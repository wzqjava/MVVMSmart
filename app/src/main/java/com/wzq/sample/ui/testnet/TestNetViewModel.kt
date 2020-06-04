package com.wzq.sample.ui.testnet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.bean.NewsDataItem
import com.wzq.sample.data.source.http.service2.MRequest
import com.wzq.mvvmsmart.http2.listener.OnServerResponseListener
import com.wzq.mvvmsmart.http2.base.BaseResponse
import java.util.*

/**
 * 王志强
 * Create Date：2019/01/25
 * Description：业务类
 */
class TestNetViewModel(application: Application) : BaseViewModel(application) {
    //给RecyclerView添加ObservableList
    var userLiveData = MutableLiveData<List<NewsDataItem>>()

    /**
    线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
    网络错误的异常转换, 这里可以换成自己的ExceptionHandle
    请求与ViewModel周期同步
    获取个人信息
     */
    fun demoGet(pageNum: Int) {
        /*
        val instance: RetrofitClient = RetrofitClient.instance
          val demoApiService = instance.create(DemoApiService::class.java)
          return demoApiService.demoGet(pageNum)
      */
        val param: Map<String, String> = HashMap()
        MRequest.getInstance().demoGet(null, 0, 1, object :
                OnServerResponseListener<List<NewsDataItem>> {
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
                KLog.e("=====error=========throwable:" + throwable?.message);
            }

            override fun reTry(what: Int) {
                KLog.e("=====reTry=========");
            }
        })

    }
}
