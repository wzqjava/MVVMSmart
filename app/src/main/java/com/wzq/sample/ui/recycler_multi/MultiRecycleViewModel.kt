package com.wzq.sample.ui.recycler_multi

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.bean.DemoBean.ItemsEntity
import com.wzq.sample.utils.TestUtils
import java.util.*

/**
 * 王志强
 * Create Date：2019/01/25
 * Description：
 */
class MultiRecycleViewModel(application: Application) : BaseViewModel(application) {
    //给RecyclerView添加ObservableList
    var itemsEntityLiveData: MutableLiveData<ArrayList<ItemsEntity>> = MutableLiveData()
    fun getData() {
        val datas = ArrayList<ItemsEntity>()
        for (i in 0..49) {
            val itemBean = ItemsEntity(i, "MVVMSmart--多条目", TestUtils.getGirlImgUrl())
            if (i % 2 == 0) {
                itemBean.itemType = 0
            } else {
                itemBean.itemType = 1
            }
            datas.add(itemBean)
        }
        itemsEntityLiveData.value = datas

    }
}