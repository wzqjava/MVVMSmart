package com.wzq.mvvmsmart.utils.constant

/**
 * 网络相关常量,标识符
 * Created by hloong on 2020/06/30.
 */
interface NetConstants {
    companion object {

        /**
         * ------------------------Net 网络请求-----------------------------
         */
        const val AUTH = "Authorization"//token
        const val TOKEN = "accessToken"//
        const val DEVICE_NAME = "deviceName"//
        const val VERSION_CODE = "versionCode"//
        const val ICCID = "iccid"//
        const val TRACE_ID = "traceId"
        const val APP_VERSION = "appVersion"
        const val MAC = "mac"
        const val DEVICE_NUMBER = "deviceNumber"
        /**
         * ------------------------Net 格式-----------------------------
         */
        const val APPLICATION_JSON = "application/json; charset=utf-8"
        const val APPLICATION_FORM = "application/x-www-form-urlencoded; charset=utf-8"
        const val MULTIPART_FORM_DATA = "multipart/form-data;charset=utf-8"

    }
}
