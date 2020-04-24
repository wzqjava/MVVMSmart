package com.wzq.sample.ui.recycler_multi

import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wzq.mvvmsmart.utils.GlideLoadUtils
import com.wzq.sample.R
import com.wzq.sample.bean.DemoBean.ItemsEntity

/**
 * author : 王志强
 * date   : 2020/01/09 11:34
 */
class MyMultiAdapter(data: List<ItemsEntity>?) : BaseMultiItemQuickAdapter<ItemsEntity, BaseViewHolder?>(data) {

    override fun convert(helper: BaseViewHolder, item: ItemsEntity) {
        when (helper.itemViewType) {
            0 -> {
                GlideLoadUtils.loadRoundCornerImg(helper.getView<View>(R.id.iv1) as ImageView, item.img,
                        R.mipmap.ic_launcher, 3)
                helper.setText(R.id.tv_name, item.name)
                helper.addOnClickListener(R.id.btn1)
            }
            1 -> {
                GlideLoadUtils.loadRoundCornerImg(helper.getView<View>(R.id.iv1) as ImageView, item.img,
                        R.mipmap.ic_launcher, 3)
                GlideLoadUtils.loadRoundCornerImg(helper.getView<View>(R.id.iv2) as ImageView, item.img,
                        R.mipmap.ic_launcher, 3)
                helper.setText(R.id.tv_name, item.name)
                helper.addOnClickListener(R.id.btn2)
            }
        }
    }


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     */
    init {
        addItemType(0, R.layout.item_multiple1)
        addItemType(1, R.layout.item_multiple2)
    }


}