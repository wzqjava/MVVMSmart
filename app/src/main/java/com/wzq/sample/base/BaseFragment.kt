package com.wzq.sample.base

import androidx.databinding.ViewDataBinding
import com.wzq.mvvmsmart.base.BaseFragmentMVVM

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : BaseFragmentMVVM<V , VM>() {
    val baseFragmentTAG = javaClass.simpleName

}