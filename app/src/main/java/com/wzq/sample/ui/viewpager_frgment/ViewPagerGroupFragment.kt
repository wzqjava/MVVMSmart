package com.wzq.sample.ui.viewpager_frgment

import androidx.fragment.app.Fragment
import com.wzq.sample.ui.bottom_tab.fragment.TabBar1Fragment
import com.wzq.sample.ui.bottom_tab.fragment.TabBar2Fragment
import com.wzq.sample.ui.bottom_tab.fragment.TabBar3Fragment
import com.wzq.sample.ui.bottom_tab.fragment.TabBar4Fragment
import java.util.*

/**
 * Description：ViewPager+Fragment的实现(绑定命令)
 * 使用这个Fragment,内部封装好了ViewPager+Fragment;
 */
class ViewPagerGroupFragment : BasePagerFragment() {
    override fun pagerFragment(): List<Fragment>? {
        val list: MutableList<Fragment> = ArrayList()
        list.add(TabBar1Fragment())
        list.add(TabBar2Fragment())
        list.add(TabBar3Fragment())
        list.add(TabBar4Fragment())
        return list
    }

    override fun pagerTitleString(): List<String>? {
        val list: MutableList<String> = ArrayList()
        list.add("page1")
        list.add("page2")
        list.add("page3")
        list.add("page4")
        return list
    }
}