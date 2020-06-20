package com.wzq.sample.ui.recycler_network.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.wzq.mvvmsmart.base.BaseFragmentMVVM
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.bean.DemoBean.ItemsEntity
import com.wzq.sample.databinding.FragmentDetailBinding

/**
 * 详情界面
 */
class DetailFragment : BaseFragmentMVVM<FragmentDetailBinding, DetailViewModel>() {
    private var entity: ItemsEntity? = null
    override fun initParam() {
        //获取列表传入的实体
        val mBundle = arguments
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity")
        }
    }

    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_detail
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        viewModel.setDemoEntity(entity)
    }
}