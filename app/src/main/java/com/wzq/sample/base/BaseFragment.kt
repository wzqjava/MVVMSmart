package com.wzq.sample.base

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.wzq.mvvmsmart.base.BaseFragmentMVVM

abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : BaseFragmentMVVM<V , VM>() {
    val baseFragmentTAG = javaClass.simpleName

    protected open fun showShortToast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
    }
    protected open fun showLongToast(msg: String?) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

}