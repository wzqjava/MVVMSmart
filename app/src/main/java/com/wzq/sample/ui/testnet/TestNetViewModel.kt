package com.wzq.sample.ui.testnet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.bean.User
import com.wzq.sample.http2.listener.OnServerResponseListener
import com.wzq.sample.http2.base.BaseResponse
import com.wzq.sample.http2.service2.MRequest
import com.wzq.sample.http2.net_utils.GsonUtil
import java.util.*

/**
 * 王志强
 * Create Date：2019/01/25
 * Description：业务类
 */
class TestNetViewModel(application: Application) : BaseViewModel(application) {
    //给RecyclerView添加ObservableList
    var userLiveData = MutableLiveData<User>()

    /**
    线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
    网络错误的异常转换, 这里可以换成自己的ExceptionHandle
    请求与ViewModel周期同步
    获取个人信息
     */

    fun getPersonalSummary() {
        val param: Map<String, String> = HashMap()
        MRequest.getInstance().getPersonalSummary(null, 0, GsonUtil.bean2String(param), object : OnServerResponseListener<User> {
            override fun success(what: Int, isQualified: Boolean, baseResponse: BaseResponse<User>) {
                KLog.e("=====success=========");
                when (what) {
                    0 -> {
                        KLog.e(baseResponse.data)
                        val user: User = baseResponse.data as User
                        userLiveData.value = user
//                        KLog.e(user.toString())
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

    // 获取个人信息2
    fun getPersonalSummary2() {
        val param: Map<String, String> = HashMap()
        MRequest.getInstance().getPersonalSummary2(null, 0, GsonUtil.bean2String(param), object : OnServerResponseListener<User> {
            override fun success(what: Int, isQualified: Boolean, baseResponse: BaseResponse<User>) {
                KLog.e("=====success=========");
                when (what) {
                    0 -> {
                        KLog.e(baseResponse.data)
                        val user: User = baseResponse.data as User
                        userLiveData.value = user
//                        KLog.e(user.toString())
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