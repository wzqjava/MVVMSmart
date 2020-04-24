package com.wzq.sample.ui.tab_bar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseFragment
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.databinding.FragmentTabBar1Binding
import com.wzq.sample.databinding.FragmentTabBar3Binding

class TabBar3Fragment : BaseFragment<FragmentTabBar3Binding, BaseViewModel>() {
    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_tab_bar_3
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}