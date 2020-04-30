package com.wzq.sample.http2.net_utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.lang.reflect.InvocationTargetException;

/**
 * created 王志强 2020.04.30
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
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
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
     * @return 获取base url
     */
    public static String getBaseUrl() {
        String baseUrl = "";
        int serviceEnvironment = getEnvironment();
        switch (serviceEnvironment) {
            case 1://测试环境
                baseUrl = "http://10.0.3.200:8919";//dev环境
//                baseUrl = "http://10.0.18.15:8081";//赵迪本机dev环境
                break;

            case 3://管控qa环境
            case 4://非管控qa环境
                baseUrl = "https://qa-ireading-gw.tope365.com";//qa环境
                break;
            default:
//                baseUrl = "https://qa-ireading-gw.tope365.com";//qa环境
                baseUrl = "http://10.0.3.200:8919";//dev环境

                break;
        }
        return baseUrl;
    }


    /**
     * 服务环境 区分dev , qa , release;
     * @return
     */
    public static int getEnvironment() {
        try {
            ApplicationInfo appInfo = getApp().getPackageManager().
                    getApplicationInfo(getApp().getPackageName(),
                            PackageManager.GET_META_DATA);
            /**
             * AndroidManifest中封装的元数据,
             * DADA_ENVIRONMENT的值取自build.gradle文件中的productFlavors配置的ENVIRONMENT
             *
             *    <meta-data
             *             android:name="DADA_ENVIRONMENT"
             *             android:value="${ENVIRONMENT}" />
             */
            return appInfo.metaData.getInt("DADA_ENVIRONMENT");
        } catch (Exception e) {
            // 3 || 4 为qa环境, 参考 Config的init方法
//          return 3;
//---------------------------------------------------------
            // 1 为dev环境, 参考 Config的init方法
            return 1;
        }
    }

    /**
     * @return 渠道号
     */
    public static String getChannel() {
        try {
            ApplicationInfo appInfo = getApp().getPackageManager().
                    getApplicationInfo(getApp().getPackageName(),
                            PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("CHANNEL_ID");
        } catch (Exception e) {
            return null;
        }
    }




}
