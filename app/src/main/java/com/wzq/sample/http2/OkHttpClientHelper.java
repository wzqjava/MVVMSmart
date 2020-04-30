package com.wzq.sample.http2;

import com.wzq.sample.http2.Interceptor.HttpCommonInterceptor;
import com.wzq.sample.http2.Interceptor.TokenInterceptor;
import com.wzq.sample.http2.cookie.CookieJarImpl;
import com.wzq.sample.http2.cookie.MemoryCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * created 王志强 2020.04.30
 **/
public class OkHttpClientHelper {

    private OkHttpClient okHttpClient;
    private MemoryCookieStore mStore = new MemoryCookieStore();

    private static class OkHttpClientHelperHolder {
        private static OkHttpClientHelper instance = new OkHttpClientHelper();
    }

    public static OkHttpClientHelper getInstance() {
        return OkHttpClientHelperHolder.instance;
    }

    /**
     * 设置带缓存的OkHttpClient
     */
    public OkHttpClient okHttpsCacheClient() {
        if (okHttpClient == null) {
            okHttpClient = okHttpsBuilder().build();
        }
        return okHttpClient;
    }

    private OkHttpClient.Builder okHttpsBuilder() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.cookieJar(new CookieJarImpl(mStore));
        okHttpClientBuilder.connectTimeout(BaseConfig.OKHTTP_CONNECTTIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(BaseConfig.OKHTTP_READTIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(BaseConfig.OKHTTP_READTIMEOUT, TimeUnit.SECONDS);
        okHttpClientBuilder.addInterceptor(new HttpCommonInterceptor());
        okHttpClientBuilder.addInterceptor(new TokenInterceptor());
        return okHttpClientBuilder;
    }

    public MemoryCookieStore getMemoryCookieStore() {
        return mStore;
    }
}
