package com.wzq.sample.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.google.gson.reflect.TypeToken
import com.wzq.mvvmsmart.net.net_utils.GsonUtil
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseActivity
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.bean.TestGson
import com.wzq.sample.databinding.ActivityDemoBinding

class MainActivity : BaseActivity<ActivityDemoBinding, BaseViewModel>() {


    @SuppressLint("SourceLockedOrientationActivity")
    override fun initParam() {
        super.initParam()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun initData() {
        //测试Gson Utils的解析,替换boolean的null和string的null
        // 测试当boolean返回0动态替换false，1动态替换true，返回null替换false
        var json  = "{\n" +
                "\t\"status\": 0.0,\n" +
                "\t\"message\": null,\n" +
                "\t\"error\": null,\n" +
                "\t\"type\": \"1\",\n" +
                "\t\"success\": null,\n" +
                "\t\"timestamp\": \"0\"\n" +
                "}"
        val data = GsonUtil.gson2Bean(json,TestGson::class.java)
        KLog.d("status==="+data.status+
                "===error==="+data.error+
                "===message===" +data.message+
                "===success==="+data.success+
                "===timestamp==="+data.timestamp+
                "===type==="+data.type)
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_demo
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }
}