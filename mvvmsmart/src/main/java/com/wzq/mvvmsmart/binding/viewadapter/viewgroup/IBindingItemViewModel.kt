package com.wzq.mvvmsmart.binding.viewadapter.viewgroup

import androidx.databinding.ViewDataBinding

interface IBindingItemViewModel<V : ViewDataBinding?> {
    fun injecDataBinding(binding: V)
}