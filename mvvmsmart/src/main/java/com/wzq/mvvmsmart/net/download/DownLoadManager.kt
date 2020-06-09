package com.wzq.mvvmsmart.net.download

import com.wzq.mvvmsmart.net.interceptor.ProgressInterceptor
import com.wzq.mvvmsmart.net.net_utils.NetworkUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

/**
 * 文件下载管理，封装一行代码实现下载
 */
class DownLoadManager private constructor() {
    init {
        getRetrofit()
    }
    companion object {
        /**
         * 单例模式
         * @return DownLoadManager
         */
        var instance: DownLoadManager? = null
            get() {
                if (field == null) {
                    field = DownLoadManager()
                }
                return field
            }
            private set
        private var retrofit: Retrofit? = null

    }

    private interface ApiService {
        @Streaming
        @GET
        fun download(@Url url: String?): Observable<ResponseBody?>
    }

    //下载
    fun load(downUrl: String?, callBack: ProgressCallBack<*>) {
        retrofit!!.create(ApiService::class.java).download(downUrl).subscribeOn(Schedulers.io()) //请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext { responseBody -> callBack.saveFile(responseBody!!) }
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(DownLoadSubscriber(callBack))
    }

    private fun getRetrofit() {
        val okHttpClient = OkHttpClient.Builder() //  添加下载拦截器，里面用ProgressResponseBody拦截下载进度信息，在里面的source方法中发送进度改变事件儿
                .addInterceptor(ProgressInterceptor()).connectTimeout(20, TimeUnit.SECONDS).build()
        retrofit = Retrofit.Builder().client(okHttpClient).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(NetworkUtil.url).build()
    }

}