package com.wzq.sample.ui.recycler_network

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wzq.mvvmsmart.utils.GlideLoadUtils
import com.wzq.sample.R
import com.wzq.sample.bean.NewsData

/**
 * author : 王志强
 * date   : 2020/01/09 11:34
 */
class MAdapter(layoutResId: Int, data: List<NewsData>?) : BaseQuickAdapter<NewsData, BaseViewHolder>(layoutResId, data as MutableList<NewsData>?) {
    override fun convert(helper: BaseViewHolder, item: NewsData) {
        helper.setText(R.id.iv2, item.news_title)
        GlideLoadUtils.loadRoundCornerImg(helper.getView(R.id.iv1), item.pic_url, R.mipmap.ic_launcher, 10)
        helper.addOnClickListener(R.id.btn2)
    }

}