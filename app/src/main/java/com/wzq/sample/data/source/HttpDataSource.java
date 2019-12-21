package com.wzq.sample.data.source;


import com.wzq.sample.entity.DemoBean;
import com.wzq.mvvmsmart.http.BaseResponse;

import io.reactivex.Observable;


public interface HttpDataSource {
    //模拟登录
    Observable<Object> login();

    //模拟上拉加载
    Observable<DemoBean> loadMore();

    Observable<BaseResponse<DemoBean>> demoGet(int pageNum);

    Observable<BaseResponse<DemoBean>> demoPost(String catalog);


}
