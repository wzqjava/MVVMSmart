package com.wzq.mvvmsmart.net.net_utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.wzq.mvvmsmart.utils.KLog;
import java.lang.reflect.InvocationTargetException;

/**
 * created 王志强 2020.04.30
 * 获取是否是正式环境(dev ,qa, release)
 * 获取渠道号
 * 根据环境获取baseUrl
 */
public class MetaDataUtil {

    private static final String TAG = MetaDataUtil.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication == null) {
            sApplication = getApplicationByReflect();
        }
        return sApplication;
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi") Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * 封装dev, qa,  release的BaseUrl
     *
     * @return 获取base url
     */
    public static String getBaseUrl() {
        String baseUrl = "";
        int serviceEnvironment = getEnvironment();
        switch (serviceEnvironment) {
            case 0://测试环境
                baseUrl = "http://api.expoon.com/"; //  dev环境
                break;

            case 1://qa环境
                //baseUrl = "https://www.oschina.net/"; //  qa环境
                baseUrl = "http://api.expoon.com/"; //  qa环境
                break;
            default:
                baseUrl = "http://api.expoon.com/"; //  线上环境
                break;
        }
        KLog.INSTANCE.e("baseUrl:" + baseUrl);
        return baseUrl;
    }

    /**
     * 服务环境 区分0:dev , 1:qa , 2:线上;
     * AndroidManifest中封装的元数据,
     * DADA_ENVIRONMENT的值取自build.gradle文件中的productFlavors配置的ENVIRONMENT
     * <meta-data
     * android:name="ENVIRONMENT"
     * android:value="${ENVIRONMENT}" />
     */
    private static int getEnvironment() {
        try {
            ApplicationInfo appInfo = getApp().getPackageManager().
                    getApplicationInfo(getApp().getPackageName(), PackageManager.GET_META_DATA);
            int environment = appInfo.metaData.getInt("ENVIRONMENT");
            if (environment == 0) {
                KLog.INSTANCE.e("environment:" + environment + "--dev环境");
            } else if (environment == 1) {
                KLog.INSTANCE.e("environment:" + environment + "--qa环境");
            } else if (environment == 2) {
                KLog.INSTANCE.e("environment:" + environment + "--生产环境");
            }
            return environment;
        } catch (Exception e) {
            //---------------------------------------------------------
            // 1 为qa环境, 参考 Config的init方法
            KLog.INSTANCE.e("environment:" + 2 + "--生产环境");
            return 2;
        }
    }

    /**
     * @return 渠道号
     */
    public static String getChannel() {
        try {
            ApplicationInfo appInfo = getApp().getPackageManager().
                    getApplicationInfo(getApp().getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("CHANNEL_ID");
        } catch (Exception e) {
            return null;
        }
    }
}
