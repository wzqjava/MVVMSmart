package com.wzq.mvvmsmart.utils.constant

import androidx.annotation.IntDef

/**
 * 时间相关常量
 */
object TimeConstants {
    /**
     * 毫秒与毫秒的倍数
     */
    const val MSEC = 1

    /**
     * 秒与毫秒的倍数
     */
    const val SEC = 1000

    /**
     * 分与毫秒的倍数
     */
    const val MIN = 60000

    /**
     * 时与毫秒的倍数
     */
    const val HOUR = 3600000

    /**
     * 天与毫秒的倍数
     */
    const val DAY = 86400000

    @IntDef(MSEC, SEC, MIN, HOUR, DAY)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Unit
}