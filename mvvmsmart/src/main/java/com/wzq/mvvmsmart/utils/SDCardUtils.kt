package com.wzq.mvvmsmart.utils

import android.annotation.TargetApi
import android.os.Build
import android.os.Environment
import android.os.StatFs
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * SD卡相关工具类
 */
class SDCardUtils private constructor() {
    class SDCardInfo {
        var isExist = false
        var totalBlocks: Long = 0
        var freeBlocks: Long = 0
        var availableBlocks: Long = 0
        var blockByteSize: Long = 0
        var totalBytes: Long = 0
        var freeBytes: Long = 0
        var availableBytes: Long = 0
        override fun toString(): String {
            return """
                isExist=$isExist
                totalBlocks=$totalBlocks
                freeBlocks=$freeBlocks
                availableBlocks=$availableBlocks
                blockByteSize=$blockByteSize
                totalBytes=$totalBytes
                freeBytes=$freeBytes
                availableBytes=$availableBytes
                """.trimIndent()
        }
    }

    companion object {
        /**
         * 判断SD卡是否可用
         *
         * @return true : 可用<br></br>false : 不可用
         */
        val isSDCardEnable: Boolean
            get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

        /**
         * 获取SD卡路径
         *
         * 先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/
         *
         * @return SD卡路径
         */
        val sDCardPath: String?
            get() {
                if (!isSDCardEnable) return null
                val cmd = "cat /proc/mounts"
                val run = Runtime.getRuntime()
                var bufferedReader: BufferedReader? = null
                try {
                    val p = run.exec(cmd)
                    bufferedReader = BufferedReader(InputStreamReader(BufferedInputStream(p.inputStream)))
                    var lineStr: String
                    while (bufferedReader.readLine().also { lineStr = it } != null) {
                        if (lineStr.contains("sdcard") && lineStr.contains(".android_secure")) {
                            val strArray = lineStr.split(" ").toTypedArray()
                            if (strArray.size >= 5) {
                                return strArray[1].replace("/.android_secure", "") + File.separator
                            }
                        }
                        if (p.waitFor() != 0 && p.exitValue() == 1) {
                            break
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    CloseUtils.closeIO(bufferedReader)
                }
                return Environment.getExternalStorageDirectory().path + File.separator
            }

        /**
         * 获取SD卡data路径
         *
         * @return SD卡data路径
         */
        val dataPath: String?
            get() = if (!isSDCardEnable) null else Environment.getExternalStorageDirectory().path + File.separator + "data" + File.separator

        /**
         * 获取SD卡剩余空间
         *
         * @return SD卡剩余空间
         */
      /*  @get:TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        val freeSpace: String?
            get() {
                if (!isSDCardEnable) return null
                val stat = StatFs(sDCardPath)
                val blockSize: Long
                val availableBlocks: Long
                availableBlocks = stat.availableBlocksLong
                blockSize = stat.blockSizeLong
                return ConvertUtils.Companion.byte2FitMemorySize(availableBlocks * blockSize)
            }*/

        /**
         * 获取SD卡信息
         *
         * @return SDCardInfo
         */
        @get:TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
        val sDCardInfo: String?
            get() {
                if (!isSDCardEnable) return null
                val sd = SDCardInfo()
                sd.isExist = true
                val sf = StatFs(Environment.getExternalStorageDirectory().path)
                sd.totalBlocks = sf.blockCountLong
                sd.blockByteSize = sf.blockSizeLong
                sd.availableBlocks = sf.availableBlocksLong
                sd.availableBytes = sf.availableBytes
                sd.freeBlocks = sf.freeBlocksLong
                sd.freeBytes = sf.freeBytes
                sd.totalBytes = sf.totalBytes
                return sd.toString()
            }
    }

    init {
        throw UnsupportedOperationException("u can't instantiate me...")
    }
}