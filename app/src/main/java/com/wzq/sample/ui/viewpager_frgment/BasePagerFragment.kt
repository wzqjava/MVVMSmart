package com.wzq.sample.ui.viewpager_frgment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseFragment
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.databinding.FragmentBasePagerTempBinding

/**
 * 抽取的二级BasePagerFragment
 */
abstract class BasePagerFragment : BaseFragment<FragmentBasePagerTempBinding, BaseViewModel>() {
    private var mFragments: List<Fragment>? = null
    private var titlePager: List<String>? = null
    protected abstract fun pagerFragment(): List<Fragment>?
    protected abstract fun pagerTitleString(): List<String>?
    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_base_pager_temp
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        mFragments = pagerFragment()
        titlePager = pagerTitleString()
        //设置Adapter
        val pagerAdapter = BaseFragmentPagerAdapter(childFragmentManager, mFragments, titlePager)
        binding.viewPager.adapter = pagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
        binding.viewPager.addOnPageChangeListener(TabLayoutOnPageChangeListener(binding.tabs))
    }
}