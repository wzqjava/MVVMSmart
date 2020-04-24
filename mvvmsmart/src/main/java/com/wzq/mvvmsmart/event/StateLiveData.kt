package com.wzq.mvvmsmart.event

import androidx.lifecycle.MutableLiveData

/**
 *
 * 作者：王志强
 *
 *
 *
 * 创建时间：2019/12/21
 *
 *
 *
 * 文件描述：带状态的livedata
 *
 *
 */
class StateLiveData<T> : MutableLiveData<T>() {
    enum class StateEnum {
        Idle, Loading, Success, Error, NoData, NoMoreData, NoNet
    }

    // 封装的枚举,用MutableLiveData可以被view观察;
    var stateEnumMutableLiveData = MutableLiveData<StateEnum>()
    fun postValueAndSuccess(t: T) {
        super.postValue(t)
        postSuccess()
    }

    fun postIdle() {
        stateEnumMutableLiveData.postValue(StateEnum.Idle)
    }

    fun postLoading() {
        stateEnumMutableLiveData.postValue(StateEnum.Loading)
    }

    fun postNoData() {
        stateEnumMutableLiveData.postValue(StateEnum.NoData)
    }

    fun postNoMoreData() {
        stateEnumMutableLiveData.postValue(StateEnum.NoMoreData)
    }

    fun postNoNet() {
        stateEnumMutableLiveData.postValue(StateEnum.NoNet)
    }

    fun postSuccess() {
        stateEnumMutableLiveData.postValue(StateEnum.Success)
    }

    fun postError() {
        stateEnumMutableLiveData.postValue(StateEnum.Error)
    }

    fun changeState(stateEnum: StateEnum) {
        stateEnumMutableLiveData.postValue(stateEnum)
    }
}