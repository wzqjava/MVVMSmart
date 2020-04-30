package com.wzq.sample.app

import cat.ereza.customactivityoncrash.config.CaocConfig
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.mmkv.MMKV
import com.wzq.mvvmsmart.base.BaseApplicationMVVM
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.sample.BuildConfig
import com.wzq.sample.R
import com.wzq.sample.ui.MainActivity

class App : BaseApplicationMVVM() {
    override fun onCreate() {
        super.onCreate()
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG)
        //初始化全局异常崩溃
        initCrash()
        MMKV.initialize(this)
        LiveEventBus
                .config()
                .supportBroadcast(this) // 配置支持跨进程、跨APP通信，传入Context，需要在application onCreate中配置
                .lifecycleObserverAlwaysActive(true) //    整个生命周期（从onCreate到onDestroy）都可以实时收到消息
    }

    private fun initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(MainActivity::class.java) //重新启动后的activity
//                                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply()
    }

    companion object {
        init {
            ClassicsFooter.REFRESH_FOOTER_LOADING = "加载中..."
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }
}