package com.wzq.mvvmsmart.http.interceptor

import com.wzq.mvvmsmart.http.download.ProgressResponseBody
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ProgressInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
                .body(ProgressResponseBody(originalResponse.body()))
                .build()
    }
}