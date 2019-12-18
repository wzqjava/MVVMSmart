package com.wzq.sample.app;


import com.wzq.sample.data.DemoRepository;
import com.wzq.sample.data.source.HttpDataSource;
import com.wzq.sample.data.source.LocalDataSource;
import com.wzq.sample.data.source.http.HttpDataSourceImpl;
import com.wzq.sample.data.source.http.service.DemoApiService;
import com.wzq.sample.data.source.local.LocalDataSourceImpl;
import com.wzq.sample.utils.RetrofitClient;

/**
 * 注入全局的数据仓库，可以考虑使用Dagger2。（根据项目实际情况搭建，千万不要为了架构而架构）
 */
public class Injection {    // TODO: wzq 2019/12/16  黄色警告
    public static DemoRepository provideDemoRepository() {
        //网络API服务
        DemoApiService apiService = RetrofitClient.getInstance().create(DemoApiService.class);  //  得到接口对象实例
        /**
         *  网络数据源
         *  get和post请求
         *  HttpDataSource为接口
         */
        HttpDataSource httpDataSource = HttpDataSourceImpl.getInstance(apiService);
//-------------------------------------------------------
        /**
         * 本地数据源
         *  保存和获取用户名和密码(4个功能)
         *  LocalDataSource为接口
         */
        LocalDataSource localDataSource = LocalDataSourceImpl.getInstance();

        //两条分支组成一个数据仓库
        return DemoRepository.getInstance(httpDataSource, localDataSource);
    }
}
