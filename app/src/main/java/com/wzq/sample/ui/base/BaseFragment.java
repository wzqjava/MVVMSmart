package com.wzq.sample.ui.base;


import androidx.databinding.ViewDataBinding;

import com.wzq.mvvmsmart.base.BaseFragmentMVVM;


public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseFragmentMVVM<V, VM> {
    public final String TAG = getClass().getSimpleName();


}
