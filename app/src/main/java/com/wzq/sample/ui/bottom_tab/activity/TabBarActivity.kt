package com.wzq.sample.ui.bottom_tab.activity

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseActivity
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.databinding.ActivityTabBarBinding
import com.wzq.sample.ui.bottom_tab.fragment.TabBar1Fragment
import com.wzq.sample.ui.bottom_tab.fragment.TabBar2Fragment
import com.wzq.sample.ui.bottom_tab.fragment.TabBar3Fragment
import com.wzq.sample.ui.bottom_tab.fragment.TabBar4Fragment
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener
import java.util.*

/**
 * 底部tab按钮的例子
 * 所有例子仅做参考,理解如何使用才最重要。
 */
class TabBarActivity : BaseActivity<ActivityTabBarBinding, BaseViewModel>() {
    private var mFragments: MutableList<Fragment>? = null
    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_tab_bar
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        //初始化Fragment
        initFragment()
        //初始化底部Button
        initBottomTab()
    }

    private fun initFragment() {
        mFragments = ArrayList()
        (mFragments as ArrayList<Fragment>).add(TabBar1Fragment())
        (mFragments as ArrayList<Fragment>).add(TabBar2Fragment())
        (mFragments as ArrayList<Fragment>).add(TabBar3Fragment())
        (mFragments as ArrayList<Fragment>).add(TabBar4Fragment())
        //默认选中第一个
        commitAllowingStateLoss(0)
    }

    private fun initBottomTab() {
        val navigationController = binding.pagerBottomTab.material()
                .addItem(R.drawable.yingyong, "应用")
                .addItem(R.drawable.huanzhe, "工作")
                .addItem(R.drawable.xiaoxi_select, "消息")
                .addItem(R.drawable.wode_select, "我的")
                .setDefaultColor(ContextCompat.getColor(this, R.color.textColorVice))
                .build()
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(object : OnTabItemSelectedListener {
            override fun onSelected(index: Int, old: Int) {
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.frameLayout, mFragments.get(index));
//                transaction.commitAllowingStateLoss();
                commitAllowingStateLoss(index)
            }

            override fun onRepeat(index: Int) {}
        })
    }

    private fun commitAllowingStateLoss(position: Int) {
        hideAllFragment()
        val transaction = supportFragmentManager.beginTransaction()
        var currentFragment = supportFragmentManager.findFragmentByTag(position.toString() + "")
        if (currentFragment != null) {
            transaction.show(currentFragment)
        } else {
            currentFragment = mFragments!![position]
            transaction.add(R.id.frameLayout, currentFragment, position.toString() + "")
        }
        transaction.commitAllowingStateLoss()
    }

    //隐藏所有Fragment
    private fun hideAllFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        for (i in mFragments!!.indices) {
            val currentFragment = supportFragmentManager.findFragmentByTag(i.toString() + "")
            if (currentFragment != null) {
                transaction.hide(currentFragment)
            }
        }
        transaction.commitAllowingStateLoss()
    }
}