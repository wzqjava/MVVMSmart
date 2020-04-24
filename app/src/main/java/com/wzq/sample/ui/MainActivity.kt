package com.wzq.sample.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseActivity
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.databinding.ActivityDemoBinding

class MainActivity : BaseActivity<ActivityDemoBinding, BaseViewModel>() {


    @SuppressLint("SourceLockedOrientationActivity")
    override fun initParam() {
        super.initParam()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }



    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_demo
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}