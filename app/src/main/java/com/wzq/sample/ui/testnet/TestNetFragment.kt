package com.wzq.sample.ui.testnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseFragment
import com.wzq.sample.databinding.FragmentTestNetBinding

/**
 * Create Date：2019/01/25
 * Description：RecycleView多布局实现
 */
class TestNetFragment : BaseFragment<FragmentTestNetBinding, TestNetViewModel>() {
    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_test_net
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()

    }

    override fun initViewObservable() {
        super.initViewObservable()
        binding.button.setOnClickListener { v: View? ->
            KLog.e("发起请求")
            viewModel.demoGet(1) // 请求网络数据;
        }
        viewModel.userLiveData.observe(this, Observer {
            binding.tvJson.text = it.toString()
        })
    }

}