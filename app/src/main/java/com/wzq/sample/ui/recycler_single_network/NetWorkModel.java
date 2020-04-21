package com.wzq.sample.ui.recycler_single_network;

import com.wzq.mvvmsmart.base.BaseModelMVVM;
import com.wzq.sample.data.source.http.service.DemoApiService;
import com.wzq.sample.utils.RetrofitClient;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * <p>作者：王志强<p>
 * <p>创建时间：2020/4/13<p>
 * <p>文件描述：<p>
 */
public class NetWorkModel extends BaseModelMVVM {


    Observable demoGet(int pageNum) {
        RetrofitClient instance = RetrofitClient.getInstance();
        DemoApiService demoApiService = instance.create(DemoApiService.class);
        return demoApiService.demoGet(pageNum);
    }


    public Completable loadMore() {
        return null;
    }
}
