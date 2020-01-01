package com.wzq.mvvmsmart.event;

/**
 * <p>作者：王志强<p>
 * <p>创建时间：2019/12/21<p>
 * <p>文件描述：带状态的livedata<p>
 */

import androidx.lifecycle.MutableLiveData;

public class StateLiveData<T> extends MutableLiveData<T> {
    public enum StateEnum {
        Idle,
        Loading,
        Success,
        Error,
        NoData
    }

    public MutableLiveData<StateEnum> stateEnumMutableLiveData = new MutableLiveData<StateEnum>();

    public final void postValueAndSuccess(T t) {
        super.postValue(t);
        this.postSuccess();
    }

    public void postIdle() {
        stateEnumMutableLiveData.postValue(StateEnum.Idle);
    }

    public void postLoading() {
        stateEnumMutableLiveData.postValue(StateEnum.Loading);
    }

    public void postSuccess() {
        stateEnumMutableLiveData.postValue(StateEnum.Success);
    }

    public void postNoData() {
        stateEnumMutableLiveData.postValue(StateEnum.NoData);
    }

    public void postError() {
        stateEnumMutableLiveData.postValue(StateEnum.Error);
    }

    public void changeState(StateEnum stateEnum) {
        stateEnumMutableLiveData.postValue(stateEnum);
    }

}
