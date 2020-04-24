package com.wzq.mvvmsmart.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.wzq.mvvmsmart.utils.Tasks
import com.wzq.mvvmsmart.utils.Utils

/**
 * todo  AppManagerMVVM.appManager?
 */
open class BaseApplicationMVVM : Application() {
    override fun onCreate() {
        super.onCreate()
        setApplication(this)
    }

    companion object {
        private var sInstance: Application? = null

        /**
         * 当主工程没有继承BaseApplication时，可以使用setApplication方法初始化BaseApplication
         *
         * @param application
         */
        @Synchronized
        fun setApplication(application: Application) {
            sInstance = application
            //初始化工具类
            Utils.Companion.init(application)
            Tasks.init()
            //注册监听每个activity的生命周期,便于堆栈式管理
            application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    AppManagerMVVM.appManager?.addActivity(activity)
                }

                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {} // todo 是否必须加?,可为null
                override fun onActivityDestroyed(activity: Activity) {
                    AppManagerMVVM.appManager?.removeActivity(activity)

                }
            })
        }

        /**
         * 获得当前app运行的Application
         */
        val instance: Application?
            get() {
                if (sInstance == null) {
                    throw NullPointerException("please inherit BaseApplicationMVVM or call setApplication.")
                }
                return sInstance
            }
    }
}