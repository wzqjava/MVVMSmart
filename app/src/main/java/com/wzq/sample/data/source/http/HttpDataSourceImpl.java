package com.wzq.sample.data.source.http;


import com.wzq.sample.data.source.HttpDataSource;
import com.wzq.sample.data.source.http.service.DemoApiService;
import com.wzq.sample.entity.DemoBean;
import com.wzq.mvvmsmart.http.BaseResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class HttpDataSourceImpl implements HttpDataSource {
    private DemoApiService apiService;
    private volatile static HttpDataSourceImpl INSTANCE = null;

    public static HttpDataSourceImpl getInstance(DemoApiService apiService) {
        if (INSTANCE == null) {
            synchronized (HttpDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDataSourceImpl(apiService);
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private HttpDataSourceImpl(DemoApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Observable<Object> login() {
        // TODO: wzq 2019/12/18  延迟时间
        return Observable.just(new Object()).delay(1, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<DemoBean> loadMore() {
        return Observable.create(new ObservableOnSubscribe<DemoBean>() {
            @Override
            public void subscribe(ObservableEmitter<DemoBean> observableEmitter) throws Exception {
                DemoBean entity = new DemoBean();
                List<DemoBean.Student> students = new ArrayList<>();
                //模拟一部分假数据
                for (int i = 0; i < 10; i++) {
                    DemoBean.Student student = new DemoBean.Student();
                    student.setId(-1);
                    student.setName("模拟条目");
                    students.add(student);
                }
                entity.setItems(students);
                observableEmitter.onNext(entity);
            }
        }).delay(2, TimeUnit.SECONDS); //延迟3秒
    }

    @Override
    public Observable<BaseResponse<DemoBean>> demoGet() {
        return apiService.demoGet();
    }

    @Override
    public Observable<BaseResponse<DemoBean>> demoPost(String catalog) {
        return apiService.demoPost(catalog);
    }
}
