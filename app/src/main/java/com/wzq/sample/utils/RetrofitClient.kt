package com.wzq.sample.utils

import android.text.TextUtils
import com.wzq.mvvmsmart.http.cookie.CookieJarImpl
import com.wzq.mvvmsmart.http.cookie.store.PersistentCookieStore
import com.wzq.mvvmsmart.http.interceptor.BaseInterceptor
import com.wzq.mvvmsmart.http.interceptor.CacheInterceptor
import com.wzq.mvvmsmart.http.interceptor.logging.Level
import com.wzq.mvvmsmart.http.interceptor.logging.LoggingInterceptor
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.mvvmsmart.utils.Utils
import com.wzq.sample.BuildConfig
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * RetrofitClient封装单例类, 实现网络请求
 */
class RetrofitClient private constructor(url: String = baseUrl, headers: Map<String, String>? = null) {
    private var cache: Cache? = null
    private var httpCacheDirectory: File? = null

    private object SingletonHolder {
        val INSTANCE = RetrofitClient()
    }

    /**
     * create you ApiService
     * Create an implementation of the API endpoints defined by the `service` interface.
     */
    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit.create(service)
    }

    companion object {
        //超时时间
        private const val DEFAULT_TIMEOUT = 20

        //缓存时间
        private const val CACHE_TIMEOUT = 10 * 1024 * 1024

        //服务端根路径
        var baseUrl = "https://www.oschina.net/"

        //    public static String baseUrl = "http://10.0.2.2:8082/"; // 本机启动服务
        //    public static String baseUrl = "http://152.136.107.93:8082/"; // 腾讯云服务器
        private val mContext = Utils.getContext()
        private lateinit var okHttpClient: OkHttpClient
        private lateinit var retrofit: Retrofit
        val instance: RetrofitClient
            get() = SingletonHolder.INSTANCE

        /**
         * execute your customer API
         * For example:
         * MyApiService service =
         * RetrofitClient.getInstance(MainActivity.this).create(MyApiService.class);
         *
         *
         * RetrofitClient.getInstance(MainActivity.this)
         * .execute(service.lgon("name", "password"), subscriber)
         * @param subscriber
         */
        fun <T> execute(observable: Observable<T>, subscriber: Observer<T>?): T? {
            observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber!!)
            return null
        }
    }

    //  私有构造
    init {
        var url: String? = url
        if (TextUtils.isEmpty(url)) {
            url = baseUrl
        }
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(mContext?.cacheDir, "wzq_cache")
        }
        try {
            if (cache == null) {
                cache = Cache(httpCacheDirectory, CACHE_TIMEOUT.toLong()) // okhttp的cache类;
            }
        } catch (e: Exception) {
            KLog.e("Could not create http cache", e)
        }
        val sslParams = HttpsUtils.getSslSocketFactory() // https证书认证,封装了认证方法,可根据自己公司进行调整;
        okHttpClient = OkHttpClient.Builder()
                .cookieJar(CookieJarImpl(PersistentCookieStore(mContext))) //                .cache(cache)
                .addInterceptor(BaseInterceptor(headers)) // 添加header的拦截器
                .addInterceptor(mContext?.let { CacheInterceptor(it) }) //无网络状态智能读取缓存
                .sslSocketFactory(sslParams!!.sSLSocketFactory, sslParams.trustManager) // https的证书校验
                .addInterceptor(LoggingInterceptor.Builder() //构建者模式
                        .loggable(BuildConfig.DEBUG) //是否开启日志打印
                        .setLevel(Level.BASIC) //打印的等级
                        .log(Platform.INFO) // 打印类型
                        .request("Request") // request的Tag
                        .response("Response") // Response的Tag
                        .addHeader("log-header", "I am the log request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                        .build()
                )
                .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.SECONDS) // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为15s
                .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
                .build()
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //  支持返回一个Observable泛型的接收对象:
                .baseUrl(url)
                .build()
    }
}