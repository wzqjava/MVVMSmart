package com.wzq.sample.ui.testnet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseFragment
import com.wzq.sample.databinding.FragmentTestNetBinding
import com.wzq.sample.http2.utils.MmkvUtils

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


    override fun initViewObservable() {
        super.initViewObservable()
        binding.button.setOnClickListener { v: View? ->
            // 有效期为一个月,一个月后需要更新token,暂时写死,可以用一个月,(本次添加时间:2020.04.30)
            MmkvUtils.putStringValue("accessToken", "Bearer 6766c5f7-154e-40e0-bb05-7d66b91a11e8") // dev 1473203的账号
            viewModel.getPersonalSummary() // 请求网络数据;
//            viewModel.getPersonalSummary2() // 请求网络数据2;
            viewModel.userLiveData.observe(this, Observer { s -> binding.tvJson.text = "result: ${s.nickname + s.goldNum}" })
        }
    }

}