package com.wzq.mvvmsmart.utils.constant

import androidx.annotation.IntDef

/**
 * 存储相关常量
 */
object MemoryConstants {
    /**
     * Byte与Byte的倍数
     */
    const val BYTE = 1

    /**
     * KB与Byte的倍数
     */
    const val KB = 1024

    /**
     * MB与Byte的倍数
     */
    const val MB = 1048576

    /**
     * GB与Byte的倍数
     */
    const val GB = 1073741824

    @IntDef(BYTE, KB, MB, GB)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    annotation class Unit
}