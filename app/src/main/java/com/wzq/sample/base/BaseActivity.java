package com.wzq.sample.base;

import androidx.databinding.ViewDataBinding;

import com.wzq.mvvmsmart.base.BaseActivityMVVM;
import com.wzq.mvvmsmart.base.BaseViewModelMVVM;

/**
 * <p>作者：王志强<p>
 * <p>创建时间：2019/12/25<p>
 * <p>文件描述：<p>
 */
public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModelMVVM> extends BaseActivityMVVM<V, VM> {
    public final String TAG = getClass().getSimpleName();



}
