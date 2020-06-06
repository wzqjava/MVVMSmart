package com.wzq.mvvmsmart.http.base;

import com.wzq.mvvmsmart.http.net_utils.RetrofitUtil;
import retrofit2.Retrofit;

/**
 * created 王志强 2020.04.30
 */
public class BaseRequest<T> {

    protected Retrofit retrofit;

    public BaseRequest() {
        RetrofitUtil retrofitUtil = RetrofitUtil.getInstance();
        this.retrofit = retrofitUtil.getRetrofit();
    }

}
