package com.wzq.sample.ui.recycler_multi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.wzq.mvvmsmart.utils.ToastUtils
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseFragment
import com.wzq.sample.bean.DemoBean.ItemsEntity
import com.wzq.sample.databinding.FragmentMultiRvBinding
import java.util.*

/**
 * Create Date：2019/01/25
 * Description：RecycleView多布局实现
 */
class MultiRecycleViewFragment : BaseFragment<FragmentMultiRvBinding, MultiRecycleViewModel>() {
    private lateinit var mAdapter: MyMultiAdapter
    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_multi_rv
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()
        viewModel.getData()
        initRecyclerView()
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.itemsEntityLiveData.observe(this, Observer { itemsEntities: ArrayList<ItemsEntity>? -> mAdapter.setNewData(itemsEntities) })
    }

    private fun initRecyclerView() {
        val datas = ArrayList<ItemsEntity>()
        mAdapter = MyMultiAdapter(datas)
        binding.adapter = mAdapter
        binding.layoutManager = LinearLayoutManager(activity)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as ItemsEntity?
            ToastUtils.showShort(item?.name + "---" + position)
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as ItemsEntity?
            when (view.id) {
                R.id.btn1 -> ToastUtils.showShort("btn1---$position")
                R.id.btn2 -> ToastUtils.showShort("btn2---$position")
            }
        }
    }
}