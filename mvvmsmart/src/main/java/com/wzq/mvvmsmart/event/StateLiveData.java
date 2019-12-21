package com.wzq.mvvmsmart.event;

/**
 * <p>作者：王志强<p>
 * <p>创建时间：2019/12/21<p>
 * <p>文件描述：带状态的livedata<p>
 */

import androidx.lifecycle.MutableLiveData;
public class StateLiveData<T> extends MutableLiveData<T> {
    public static enum State {
        Idle,
        Loading,
        Success,
        Error
    }

    public  MutableLiveData<State> state = new MutableLiveData<State>();

    public final void postValueAndSuccess(T t) {
        super.postValue(t);
        this.postSuccess();
    }

    public void clearState() {
        state.postValue(StateLiveData.State.Idle);
    }

    public void postLoading() {
        state.postValue(StateLiveData.State.Loading);
    }

    public void postSuccess() {
        state.postValue(StateLiveData.State.Success);
    }

    public void postError() {
        state.postValue(StateLiveData.State.Error);
    }

    public void changeState( StateLiveData.State s) {
        state.postValue(s);
    }


}
