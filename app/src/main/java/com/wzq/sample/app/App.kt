package com.wzq.sample.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cat.ereza.customactivityoncrash.config.CaocConfig
import com.jeremyliao.liveeventbus.LiveEventBus
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.mmkv.MMKV
import com.wzq.mvvmsmart.base.AppManagerMVVM
import com.wzq.mvvmsmart.net.net_utils.Utils
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.mvvmsmart.utils.Tasks
import com.wzq.sample.BuildConfig
import com.wzq.sample.R
import com.wzq.sample.ui.MainActivity

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Tasks.init()
        Utils.init(this)
        //是否开启打印日志
        KLog.init(BuildConfig.DEBUG)
        //初始化全局异常崩溃
        initCrash()
        MMKV.initialize(this)   // 替换sp
        LiveEventBus  // 事件儿总线通信
                .config().supportBroadcast(this) // 配置支持跨进程、跨APP通信，传入Context，需要在application onCreate中配置
                .lifecycleObserverAlwaysActive(true) //    整个生命周期（从onCreate到onDestroy）都可以实时收到消息
        setActivityLifecycle(this)
    }

    /**
     * app 崩溃重启的配置
     */
    private fun initCrash() {
        CaocConfig.Builder.create().backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
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
        /**
         * SmartRefreshLayout 上下拉刷新加载更多的全局配置
         */
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

    /**
     * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
     *
     * @param application
     */
    @Synchronized
    fun setActivityLifecycle(application: Application) {
        //注册监听每个activity的生命周期,便于堆栈式管理
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                AppManagerMVVM.get().addActivity(activity)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {}
            override fun onActivityDestroyed(activity: Activity) {
                AppManagerMVVM.get().removeActivity(activity)

            }
        })
    }
}