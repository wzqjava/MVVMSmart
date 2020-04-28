package com.wzq.sample.ui.recycler_single_network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.wzq.mvvmsmart.http.BaseResponse;
import com.wzq.mvvmsmart.http.ResponseThrowable;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.mvvmsmart.utils.RxUtils;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.base.BaseViewModel;
import com.wzq.sample.bean.DemoBean;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NetWorkViewModel extends BaseViewModel {
    private NetWorkModel model;
    public int pageNum = 1;
    public MutableLiveData<List<DemoBean.ItemsEntity>> liveData;

    public NetWorkViewModel(@NonNull Application application) {
        super(application);
        liveData = new MutableLiveData<>();
        model = new NetWorkModel();
    }

    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    public void requestNetWork() {
        //可以调用addSubscribe()添加Disposable，请求与View周期同步
        Observable observable = model.demoGet(pageNum);
        observable.compose(RxUtils.observableToMain()) //线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(NetWorkViewModel.this)    //  请求与ViewModel周期同步
                .subscribe(new Observer<BaseResponse<DemoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        stateLiveData.postLoading();
                    }

                    @Override
                    public void onNext(BaseResponse<DemoBean> response) {
                        KLog.e("进入onNext");
                        //请求成功
                        if (response.getCode() == 1) {
                            List<DemoBean.ItemsEntity> itemsEntities = response.getResult().getItems();
                            if (itemsEntities != null) {
                                if (itemsEntities.size() > 0) {
                                    liveData.postValue(itemsEntities);
                                } else {
                                    ToastUtils.showShort("没有更多数据了");
                                    KLog.e("请求到数据students.size" + itemsEntities.size());
                                }
                            } else {
                                KLog.e("数据返回null");
                                stateLiveData.postError();
                            }
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            KLog.e("请求失败response.getCode():" + response.getCode());
                            ToastUtils.showLong("提醒开发者: 本次上拉服务器没有数据");
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        KLog.e("进入onError" + throwable.getMessage());
                        //关闭对话框
                        stateLiveData.postIdle();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        KLog.e("进入onComplete");
                        //关闭对话框
                        stateLiveData.postIdle();
                    }
                });


    }


    /**
     * 王志强 2019/12/20
     * 接口不能用的时候,模拟加载更多数据
     */
    private void loadMoreTestData() {
        /*if (liveData.getValue().size() > 50) {
            ToastUtils.showLong("兄dei，崩是不可能的~");
//            uc.finishLoadMore.call();
            return;
        }
        netWorkModel.loadMore()
                .compose(RxUtils.observableToMain()) //线程调度
                .doOnSubscribe(NetWorkViewModel.this) //请求与ViewModel周期同步
                .subscribe(new Observer<DemoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        KLog.d("开始上拉加载更多");
                    }

                    @Override
                    public void onNext(DemoBean demoBean) {
                        liveData.getValue().addAll(demoBean.getItems());
                        liveData.setValue(liveData.getValue());
                        ToastUtils.showShort("" + liveData.getValue().size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //刷新完成收回

                    }
                });*/
    }


    /**
     * 删除条目
     */
    public void deleteItem(DemoBean.ItemsEntity itemsEntity) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        KLog.e("调用了删除");
        KLog.e("size" + liveData.getValue().size());
        liveData.getValue().remove(itemsEntity);
        KLog.e("size" + liveData.getValue().size());

    }


}
