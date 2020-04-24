package com.wzq.mvvmsmart.http.interceptor.logging

import android.text.TextUtils
import okhttp3.*
import okhttp3.internal.platform.Platform
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @author ihsan on 09/02/2017.
 */
class LoggingInterceptor private constructor(private val builder: Builder) : Interceptor {
    private val isDebug: Boolean

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (builder.headers.size() > 0) {
            val headers = request.headers()
            val names = headers.names()
            val iterator: Iterator<String> = names.iterator()
            val requestBuilder = request.newBuilder()
            requestBuilder.headers(builder.headers)
            while (iterator.hasNext()) {
                val name = iterator.next()
                requestBuilder.addHeader(name, headers[name])
            }
            request = requestBuilder.build()
        }
        if (!isDebug || builder.level == Level.NONE) {
            return chain.proceed(request)
        }
        val requestBody = request.body()
        var rContentType: MediaType? = null
        if (requestBody != null) {
            rContentType = request.body()!!.contentType()
        }
        var rSubtype: String? = null
        if (rContentType != null) {
            rSubtype = rContentType.subtype()
        }
        if (rSubtype != null && (rSubtype.contains("json")
                        || rSubtype.contains("xml")
                        || rSubtype.contains("plain")
                        || rSubtype.contains("html"))) {
            Printer.Companion.printJsonRequest(builder, request)
        } else {
            Printer.Companion.printFileRequest(builder, request)
        }
        val st = System.nanoTime()
        val response = chain.proceed(request)
        val segmentList = (request.tag() as Request).url().encodedPathSegments()
        val chainMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - st)
        val header = response.headers().toString()
        val code = response.code()
        val isSuccessful = response.isSuccessful
        val responseBody = response.body()
        val contentType = responseBody!!.contentType()
        var subtype: String? = null
        val body: ResponseBody
        if (contentType != null) {
            subtype = contentType.subtype()
        }
        body = if (subtype != null && (subtype.contains("json")
                        || subtype.contains("xml")
                        || subtype.contains("plain")
                        || subtype.contains("html"))) {
            val bodyString = responseBody.string()
            val bodyJson: String = Printer.Companion.getJsonString(bodyString)
            Printer.Companion.printJsonResponse(builder, chainMs, isSuccessful, code, header, bodyJson, segmentList)
            ResponseBody.create(contentType, bodyString)
        } else {
            Printer.Companion.printFileResponse(builder, chainMs, isSuccessful, code, header, segmentList)
            return response
        }
        return response.newBuilder().body(body).build()
    }

    class Builder {
        var isDebug = false
        var type = Platform.INFO
            private set
        private var requestTag: String? = null
        private var responseTag: String? = null
        var level = Level.BASIC
            private set
        private val builder: Headers.Builder
        var logger: Logger? = null
            private set

        val headers: Headers
            get() = builder.build()

        fun getTag(isRequest: Boolean): String {
            return if (isRequest) {
                (if (TextUtils.isEmpty(requestTag)) TAG else requestTag)!!
            } else {
                (if (TextUtils.isEmpty(responseTag)) TAG else responseTag)!!
            }
        }

        /**
         * @param name  Filed
         * @param value Value
         * @return Builder
         * Add a field with the specified value
         */
        fun addHeader(name: String?, value: String?): Builder {
            builder[name] = value
            return this
        }

        /**
         * @param level set log level
         * @return Builder
         * @see Level
         */
        fun setLevel(level: Level): Builder {
            this.level = level
            return this
        }

        /**
         * Set request and response each log tag
         *
         * @param tag general log tag
         * @return Builder
         */
        fun tag(tag: String): Builder {
            TAG = tag
            return this
        }

        /**
         * Set request log tag
         *
         * @param tag request log tag
         * @return Builder
         */
        fun request(tag: String?): Builder {
            requestTag = tag
            return this
        }

        /**
         * Set response log tag
         *
         * @param tag response log tag
         * @return Builder
         */
        fun response(tag: String?): Builder {
            responseTag = tag
            return this
        }

        /**
         * @param isDebug set can sending log output
         * @return Builder
         */
        fun loggable(isDebug: Boolean): Builder {
            this.isDebug = isDebug
            return this
        }

        /**
         * @param type set sending log output type
         * @return Builder
         * @see Platform
         */
        fun log(type: Int): Builder {
            this.type = type
            return this
        }

        /**
         * @param logger manuel logging interface
         * @return Builder
         * @see Logger
         */
        fun logger(logger: Logger?): Builder {
            this.logger = logger
            return this
        }

        fun build(): LoggingInterceptor {
            return LoggingInterceptor(this)
        }

        companion object {
            private var TAG = "LoggingI"
        }

        init {
            builder = Headers.Builder()
        }
    }

    init {
        isDebug = builder.isDebug
    }
}