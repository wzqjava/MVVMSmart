package com.wzq.sample.base

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wzq.mvvmsmart.base.BaseViewModelMVVM
import com.wzq.mvvmsmart.event.StateLiveData

/**
 *
 * 作者：王志强
 *
 *
 *
 * 创建时间：2019/12/25
 *
 *
 *
 * 文件描述：
 *
 *
 */
open class BaseViewModel(application: Application) : BaseViewModelMVVM(application) {
    var stateLiveData: StateLiveData<Any> = StateLiveData()
    fun getStateLiveData(): MutableLiveData<Any> {
        return stateLiveData
    }

}