package com.wzq.mvvmsmart.net.token;


import com.wzq.mvvmsmart.net.base.BaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * created 王志强 2020.04.30
 */
public interface TokenService {
    /**
     * 刷新token接口
     * @param refreshToken
     * @return
     */
    @GET("chinese/student/refreshToken")
    Call<BaseResponse<TokenBean>> refreshToken(@Query(value = "refreshToken", encoded = true) String refreshToken);
}
