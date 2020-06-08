package com.wzq.mvvmsmart.net.net_utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;

/**
 * created 王志强 2020.04.30
 * 一般header中需要的参数获取工具类
 */

public class BaseCommonUtils {

    /**
     * 是否强制竖屏
     * @param context
     * @return
     */
    public static boolean getScreenPortrait(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = Utils.getApp().getPackageManager().
                    getApplicationInfo(Utils.getApp().getPackageName(),
                            PackageManager.GET_META_DATA);
            int ENVIRONMENT = appInfo.metaData.getInt("SCREENPORTRAIT");
            return ENVIRONMENT == 1;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        try {
            if (context != null) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                @SuppressLint("MissingPermission") NetworkInfo mNetworkInfo = mConnectivityManager
                        .getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 跳转到网络设置页
     */
    public static void netSetting(Activity context) {
        try {
            Intent intent = null;
            // 判断手机系统的版本 即API大于10 就是3.0或以上版本
            intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            //1.在Activity上下文之外启动Activity需要给Intent设置FLAG_ACTIVITY_NEW_TASK标志，不然会报异常。
            //2.加了该标志，如果在同一个应用中进行Activity跳转，不会创建新的Task，只有在不同的应用中跳转才会创建新的Task
            //为了减少崩溃，再增加一个try拦截，2019年5月5日16:18:04 -- bobby改
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context,"请手动打开WIFI", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取app版本名称
     *
     * @return
     */
    public static String getVersionName() {
        try {
            Context context=Utils.getApp().getApplicationContext();
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取app版本号
     *
     * @return
     */
    public static int getVersionCode() {
        try {
            Context context=Utils.getApp().getApplicationContext();
            String pkName = context.getPackageName();
            int versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionName;
        } catch (Exception e) {
        }
        return -1;
    }
    /**
     * 获取SIM卡号
     * 用到的权限： <uses-permission
     * android:name="android.permission.READ_PHONE_STATE" />
     */
    public static String getSim(Context ctx) {

        String strResult = null;
        try {
            strResult = "";
            TelephonyManager telephonyManager = (TelephonyManager) ctx
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(Utils.getApp(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            if (telephonyManager != null) {
                strResult = telephonyManager.getSimSerialNumber();
                if(TextUtils.isEmpty(strResult)){
                    strResult ="";
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
            return "";
        }
        return strResult;
    }
    /**
     * 获取imei
     * @return
     */
    public static String getIMEI() {
        String androidID = Settings.Secure.getString(Utils.getApp().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        try {
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return id;
        }

    }

    /**
     * 获取mac
     * @return
     */
    public static String getMAC() {
        try {
            WifiManager wm = (WifiManager) Utils.getApp().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
            return m_szWLANMAC;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
