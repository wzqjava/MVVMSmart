package com.wzq.sample.base

import androidx.databinding.ViewDataBinding
import com.wzq.mvvmsmart.base.BaseActivityMVVM
import com.wzq.mvvmsmart.base.BaseViewModelMVVM

/**
 *
 * 作者：王志强
 *
 *
 *
 * 创建时间：2019/12/25
 *
 *
 *
 * 文件描述：
 *
 *
 */
abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModelMVVM> : BaseActivityMVVM<V, VM>() {
    val tag = javaClass.simpleName


}