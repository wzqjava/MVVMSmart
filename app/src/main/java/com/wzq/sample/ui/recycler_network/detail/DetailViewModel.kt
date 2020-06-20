package com.wzq.sample.ui.recycler_network.detail

import android.app.Application
import androidx.databinding.ObservableField
import com.wzq.mvvmsmart.base.BaseViewModelMVVM
import com.wzq.sample.bean.DemoBean.ItemsEntity

class DetailViewModel(application: Application) : BaseViewModelMVVM(application) {
    var entity: ObservableField<ItemsEntity?>? = ObservableField()
    fun setDemoEntity(entity: ItemsEntity?) {
        this.entity!!.set(entity)
    }

    override fun onDestroy() {
        super.onDestroy()
        entity = null
    }
}