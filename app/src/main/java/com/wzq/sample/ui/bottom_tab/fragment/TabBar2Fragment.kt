package com.wzq.sample.ui.bottom_tab.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseFragment
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.databinding.FragmentTabBar2Binding

class TabBar2Fragment : BaseFragment<FragmentTabBar2Binding, BaseViewModel>() {
    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_tab_bar_2
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    /**
     * ViewPager中的Fragment使用navigation,需要创建View的时候,从之前的父类中remove掉
     * 不remove掉的话,从其他页面返回含有ViewPager页面的话会报错"The specified child already has a parent"
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (lastView != null) {
            val parent = lastView?.parent as ViewGroup?
            parent?.removeView(lastView)
        }
        super.onCreateView(inflater, container, savedInstanceState)
        return lastView
    }
}