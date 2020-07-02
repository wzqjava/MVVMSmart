package com.wzq.mvvmsmart.net.interceptor;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.wzq.mvvmsmart.BuildConfig;
import com.wzq.mvvmsmart.net.net_utils.OkHttpUtil;
import com.wzq.mvvmsmart.net.cookie.CookieUtils;
import com.wzq.mvvmsmart.net.net_utils.BaseCommonUtils;
import com.wzq.mvvmsmart.net.net_utils.MetaDataUtil;
import com.wzq.mvvmsmart.net.net_utils.MmkvUtils;
import com.wzq.mvvmsmart.net.net_utils.Utils;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.mvvmsmart.utils.constant.NetConstants;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
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
            //请求拦截 LOG
            RequestBody requestBody = request.body();
            Buffer bufferReq = new Buffer();
            String resultRequestBody = "";// GET 无参数时默认 resultRequestBody=""
            if (requestBody != null){
                requestBody.writeTo(bufferReq);
                MediaType reqContentType = requestBody.contentType();
                resultRequestBody = bufferReq.readString(reqContentType.charset(Charset.forName("UTF-8")));
            }

            //响应拦截 LOG
            ResponseBody responseBody = response.body();
            String resultResponseBody="";
            if (responseBody != null){
                BufferedSource source = responseBody.source();
                Buffer buffer = source.buffer();
                MediaType contentType = responseBody.contentType();
                Charset charset = contentType != null ? contentType.charset(Charset.forName("UTF-8")) : Charset.forName("UTF-8");
                Charset newCharset = Util.bomAwareCharset(source, charset);
                resultResponseBody = buffer.clone().readString(newCharset);
            }
            //String string2 = decodeUnicode(string);
            KLog.INSTANCE.e(TAG, String.format("发送请求\nmethod：%s\n原请求url：%s\nheaders: %s\n请求body：%s\n" +
                            "收到响应\n%s %s\n新的真实请求url：%s",
                    request.method(), request.url(), request.headers(), resultRequestBody,
                    response.code(), response.message(), response.request().url()));

            KLog.INSTANCE.json(TAG+"返回结果", resultResponseBody);  // Warn级别查看 带格式的json
        }
        return response;
    }

    /**
     * 添加cookie
     * @param context
     * @param request
     */
    private void addCookie(Context context, Request request) {
        List<Cookie> list = OkHttpUtil.getInstance().getMemoryCookieStore().get(request.url());
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
        mBuilder.addHeader(NetConstants.TRACE_ID, finaluuIDStr);
        mBuilder.addHeader(NetConstants.DEVICE_NAME, DEVICENAME);
        mBuilder.addHeader(NetConstants.APP_VERSION, VERSIONCODE);
        mBuilder.addHeader(NetConstants.MAC, MAC);
        mBuilder.addHeader(NetConstants.DEVICE_NUMBER, IMEI);
        mBuilder.addHeader(NetConstants.ICCID, iccid);
        if (!TextUtils.isEmpty(MmkvUtils.getStringValue(NetConstants.TOKEN))) {
            mBuilder.addHeader(NetConstants.AUTH,  MmkvUtils.getStringValue(NetConstants.TOKEN));//添加token
            KLog.INSTANCE.w("token",  MmkvUtils.getStringValue(NetConstants.TOKEN));
            mBuilder.addHeader(NetConstants.VERSION_CODE, VERSIONCODE);//登录的时候不传versioncode
        } else {
            KLog.INSTANCE.e("accessToken为空");
        }
        return mBuilder.build();
    }

    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }

}
