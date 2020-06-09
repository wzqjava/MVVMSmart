package com.wzq.mvvmsmart.net.download

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import okhttp3.ResponseBody
import java.io.*

abstract class ProgressCallBack<T>(lifecycleOwner: LifecycleOwner?, // 本地文件存放路径
                                   private val destFileDir: String, // 文件名
                                   private val destFileName: String) {

    abstract fun onSuccess(t: Any?)
    abstract fun progress(progress: Long, total: Long)
    open fun onStart() {}
    open fun onCompleted() {}
    abstract fun onError(e: Throwable?)
    init {
        subscribeLoadProgress(lifecycleOwner)
    }
    fun saveFile(body: ResponseBody) {
        var `is`: InputStream? = null
        val buf = ByteArray(2048)
        var len: Int
        var fos: FileOutputStream? = null
        try {
            `is` = body.byteStream()
            val dir = File(destFileDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, destFileName)
            fos = FileOutputStream(file)
            while (`is`.read(buf).also { len = it } != -1) {
                fos.write(buf, 0, len)
            }
            fos.flush()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                `is`?.close()
                fos?.close()
            } catch (e: IOException) {
                Log.e("saveFile", e.message)
            }
        }
    }

    /**
     * 订阅加载的进度条
     * @param lifecycleOwner
     */
    fun subscribeLoadProgress(lifecycleOwner: LifecycleOwner?) {
        LiveEventBus
                .get("key_name", DownLoadStateBean::class.java)
                .observe(lifecycleOwner!!, Observer { progressLoadBean -> progress(progressLoadBean.bytesLoaded, progressLoadBean.total) })
    }


}