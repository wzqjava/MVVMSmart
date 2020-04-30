package com.reading.win.network.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * created 王志强 2020.04.30
 */
open class BaseViewModel<T> : ViewModel() {
    //接口返回状态码
    var code : MutableLiveData<Int> = MutableLiveData()
    //接口返回数据
    var data : MutableLiveData<T> = MutableLiveData()
    //接口返回文本信息
    var message : MutableLiveData<String> = MutableLiveData()

    var mDis : CompositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable){
        mDis.add(disposable)
    }

    fun clearDisposable(){
        mDis.clear()
    }
}