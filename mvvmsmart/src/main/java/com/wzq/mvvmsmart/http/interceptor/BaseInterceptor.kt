package com.wzq.mvvmsmart.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseInterceptor(private val headers: Map<String, String>?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request()
                .newBuilder()
        if (headers != null && headers.size > 0) {
            val keys = headers.keys
            for (headerKey in keys) {
                builder.addHeader(headerKey, headers[headerKey]).build()
            }
        }
        //请求信息
        return chain.proceed(builder.build())
    }

}