package com.wzq.sample.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.wzq.mvvmsmart.base.BaseModelMVVM;
import com.wzq.mvvmsmart.base.BaseViewModelMVVM;
import com.wzq.mvvmsmart.event.StateLiveData;

/**
 * <p>作者：王志强<p>
 * <p>创建时间：2019/12/25<p>
 * <p>文件描述：<p>
 */
public class BaseViewModel<M extends BaseModelMVVM> extends BaseViewModelMVVM<M> {
    public final String TAG = getClass().getSimpleName();
    public StateLiveData<Object> stateLiveData;

    public MutableLiveData<Object> getStateLiveData() {
        return stateLiveData;
    }
    public BaseViewModel(@NonNull Application application) {
        super(application);
        stateLiveData = new StateLiveData<>();
    }

    public BaseViewModel(@NonNull Application application, BaseModelMVVM model) {
        super(application, (M) model);
        stateLiveData = new StateLiveData<>();
    }




}
