package com.wzq.sample.http2.Interceptor;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.sample.BuildConfig;
import com.wzq.sample.http2.OkHttpClientHelper;
import com.wzq.sample.http2.cookie.CookieUtils;
import com.wzq.sample.http2.net_utils.BaseCommonUtils;
import com.wzq.sample.http2.net_utils.MetaDataUtil;
import com.wzq.sample.http2.net_utils.MmkvUtils;
import com.wzq.sample.http2.net_utils.Utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 网络公用拦截器
 * created 王志强 2020.04.30
 * degbug模式自动打印请求的接口地址
 * degbug模式自动打印Server返回的json(Warning级别查看,Error级别一行显示)
 */
public class HttpCommonInterceptor implements Interceptor {
    private static final String TAG = HttpCommonInterceptor.class.getSimpleName();
    private static final String DEVICENAME = Build.FINGERPRINT.replace("_", "");
    private static final String VERSIONCODE = BaseCommonUtils.getVersionCode() + "";
    private static final String IMEI = BaseCommonUtils.getIMEI();
    private static final String MAC = BaseCommonUtils.getMAC();
    private static final String iccid = BaseCommonUtils.getSim(Utils.getApp());

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        Context context = MetaDataUtil.getApp().getApplicationContext();
        Request request = chain.request();
        request = addHeader(request);
        addCookie(context, request);
        response = chain.proceed(request);

        if (BuildConfig.DEBUG) {
            ResponseBody responseBody = response.body();
            KLog.INSTANCE.e(TAG, "网络请求--#" + response.request().url());
            BufferedSource source = responseBody.source();
            Buffer buffer = source.buffer();
            MediaType contentType = responseBody.contentType();
            Charset charset = contentType != null ? contentType.charset(Charset.forName("UTF-8")) : Charset.forName("UTF-8");
            Charset newCharset = Util.bomAwareCharset(source, charset);
            String string = buffer.clone().readString(newCharset);
            KLog.INSTANCE.json(TAG, string);  // Warn级别查看 带格式的json

        }
        return response;
    }

    /**
     * 添加cookie
     * @param context
     * @param request
     */
    private void addCookie(Context context, Request request) {
        List<Cookie> list = OkHttpClientHelper.getInstance().getMemoryCookieStore().get(request.url());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Cookie cookie = (Cookie) it.next();
            if (cookie.name().equals(CookieUtils.KJsessionidCookie)) {
                CookieUtils.getInstance(context).saveCookie(cookie.value());
            }
        }
    }

    /**
     * 添加header
     * @param request 请求的request
     * @return Request
     */
    private Request addHeader(Request request) {
        String uuidStr = UUID.randomUUID().toString();
        String finaluuIDStr = uuidStr.replaceAll("-", "");
        Request.Builder mBuilder = request.newBuilder();
        mBuilder.addHeader("traceId", finaluuIDStr);
        mBuilder.addHeader("deviceName", DEVICENAME);
        mBuilder.addHeader("appVersion", VERSIONCODE);
        mBuilder.addHeader("mac", MAC);
        mBuilder.addHeader("deviceNumber", IMEI);
        mBuilder.addHeader("iccid", iccid);
        if (!TextUtils.isEmpty(MmkvUtils.getStringValue("accessToken"))) {
            mBuilder.addHeader("Authorization",  MmkvUtils.getStringValue("accessToken"));//添加token
            KLog.INSTANCE.w("token",  MmkvUtils.getStringValue("accessToken"));
            mBuilder.addHeader("versionCode", VERSIONCODE);//登录的时候不传versioncode
        } else {
            KLog.INSTANCE.e("accessToken为空");
        }
        return mBuilder.build();
    }

}
