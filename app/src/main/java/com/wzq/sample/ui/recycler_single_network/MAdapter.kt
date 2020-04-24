package com.wzq.sample.ui.recycler_single_network

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wzq.mvvmsmart.utils.GlideLoadUtils
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.sample.R
import com.wzq.sample.bean.DemoBean.ItemsEntity

/**
 * author : 王志强
 * date   : 2020/01/09 11:34
 */
class MAdapter(layoutResId: Int, data: List<*>?) : BaseQuickAdapter<ItemsEntity, BaseViewHolder>(layoutResId, data as MutableList<ItemsEntity>?) {
    override fun convert(helper: BaseViewHolder, item: ItemsEntity) {
        helper.setText(R.id.iv2, item.name)
        KLog.e(item.img)
        GlideLoadUtils.loadRoundCornerImg(helper.getView(R.id.iv1), item.img, R.mipmap.ic_launcher, 10)
        helper.addOnClickListener(R.id.btn2)
    }

}