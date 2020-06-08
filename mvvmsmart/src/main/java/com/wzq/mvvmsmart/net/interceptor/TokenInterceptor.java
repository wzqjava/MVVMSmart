package com.wzq.mvvmsmart.net.interceptor;


import com.wzq.mvvmsmart.net.net_utils.MmkvUtils;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.mvvmsmart.net.token.TokenUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * created 王志强 2020.04.30
 * 拦截token,刷新token
 */
public class TokenInterceptor implements Interceptor {
//    private static final String TAG = TokenInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        Response proceed = chain.proceed(request.build());
        okhttp3.MediaType mediaType = proceed.body().contentType();
        //如果token过期 再去重新请求token 然后设置token的请求头 重新发起请求 用户无感
        String content = proceed.body().string();

        if (!"".equals(MmkvUtils.getStringValue("refreshToken"))) {
            //根据和服务端的约定判断token过期
            if (TokenUtils.isTokenExpired(content)) {
                KLog.INSTANCE.e("自动刷新Token,然后重新请求数据");
                //同步请求方式，获取最新的Token
                String newToken = TokenUtils.getNewToken();
                KLog.INSTANCE.e("newToken:" + newToken);
                if (newToken.equals("")) {//token刷新失败
                    return proceed.newBuilder()
                            .body(okhttp3.ResponseBody.create(mediaType, content))
                            .build();
                }
                Request newRequest = chain.request().newBuilder()
                        .removeHeader("Authorization")
                        .addHeader("Authorization", "Bearer " + MmkvUtils.getStringValue("accessToken"))
                        .build();
                //重新请求
                return chain.proceed(newRequest);
            }
        }
        return proceed.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }


}
