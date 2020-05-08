package com.wzq.sample.ui.viewpager_frgment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * FragmentPager适配器
 */
class BaseFragmentPagerAdapter //使用构造方法来将数据传进去
(fm: FragmentManager?, //ViewPager要填充的fragment列表
 private val list: List<Fragment>?, //tab中的title文字列表
 private val title: List<String>?) : FragmentPagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment { //获得position中的fragment来填充
        return list!![position]
    }

    override fun getCount(): Int { //返回FragmentPager的个数
        return list!!.size
    }

    //FragmentPager的标题,如果重写这个方法就显示不出tab的标题内容
    override fun getPageTitle(position: Int): CharSequence? {
        return title!![position]
    }

}