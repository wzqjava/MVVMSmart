package com.wzq.sample.ui.testnet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.wzq.mvvmsmart.http.BaseResponse;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.mvvmsmart.utils.RxUtils;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.base.BaseViewModel;
import com.wzq.sample.data.source.http.service.DemoApiService;
import com.wzq.sample.utils.RetrofitClient;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 王志强
 * Create Date：2019/01/25
 * Description：
 */
public class TestNetViewModel extends BaseViewModel {

    //给RecyclerView添加ObservableList
    public MutableLiveData<String> resultJson;

    public TestNetViewModel(@NonNull Application application) {
        super(application);
        resultJson = new MutableLiveData<>();
    }

    public void getData() {
        DemoApiService apiService = RetrofitClient.getInstance().create(DemoApiService.class);
        apiService.getJsonFile()
                .compose(RxUtils.observableToMain()) //线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(TestNetViewModel.this)    //  请求与ViewModel周期同步
                .subscribe(new Observer<BaseResponse<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        KLog.e("开始请求...");
                        ToastUtils.showShort("开始请求...");
                    }

                    @Override
                    public void onNext(BaseResponse<Object> response) {
                        KLog.e("返回数据: " + response.toString());
                        resultJson.postValue(response.toString());
                        ToastUtils.showShort(response.toString());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        ToastUtils.showShort("进入onError" + throwable.getMessage());
                        KLog.e("进入onError" + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        ToastUtils.showShort("进入onComplete");

                        KLog.e("进入onComplete");
                    }
                });
    }
}
