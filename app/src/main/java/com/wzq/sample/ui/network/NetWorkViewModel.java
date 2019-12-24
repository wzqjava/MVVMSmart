package com.wzq.sample.ui.network;

import android.app.Application;

import androidx.annotation.NonNull;

import com.wzq.mvvmsmart.base.BaseViewModel;
import com.wzq.mvvmsmart.binding.command.BindingAction;
import com.wzq.mvvmsmart.binding.command.BindingCommand;
import com.wzq.mvvmsmart.event.SingleLiveEvent;
import com.wzq.mvvmsmart.event.StateLiveData;
import com.wzq.mvvmsmart.http.BaseResponse;
import com.wzq.mvvmsmart.http.ResponseThrowable;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.mvvmsmart.utils.RxUtils;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.bean.DemoBean;
import com.wzq.sample.data.DemoRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NetWorkViewModel extends BaseViewModel<DemoRepository> {
    private int pageNum = 1;
    public StateLiveData<List<DemoBean.ItemsEntity>> stateLiveData;
//    public SingleLiveEvent<NetWorkItemViewModel> deleteItemLiveData = new SingleLiveEvent<>();

    public NetWorkViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);
        stateLiveData = new StateLiveData<>();
        stateLiveData.setValue(new ArrayList<DemoBean.ItemsEntity>());
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent<Object> finishRefreshing = new SingleLiveEvent<Object>();
        //上拉加载完成
        public SingleLiveEvent<Object> finishLoadMore = new SingleLiveEvent<Object>();
    }

    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    public void requestNetWork() {
        //可以调用addSubscribe()添加Disposable，请求与View周期同步

        model.demoGet(pageNum)
                .compose(RxUtils.observableToMain()) //线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
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
                                    if (pageNum == 1) {
                                        stateLiveData.getValue().clear();
                                    }
                                    stateLiveData.getValue().addAll(itemsEntities);
                                    stateLiveData.postValueAndSuccess(stateLiveData.getValue());
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
                            ToastUtils.showShort("请求错误,code:" + response.getCode());
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        KLog.e("进入onError" + throwable.getMessage());
                        //关闭对话框
                        stateLiveData.clearState();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }

                    @Override
                    public void onComplete() {
                        KLog.e("进入onComplete");
                        //关闭对话框
                        stateLiveData.clearState();
                        //请求刷新完成收回
                        uc.finishRefreshing.call();
                        uc.finishLoadMore.call();
                    }
                });
    }

    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            pageNum = 1;
            requestNetWork();
        }
    });

    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            pageNum++;
//            loadMoreTestData();   // 模拟加载更多数据
            requestNetWork();
        }
    });

    /**
     * 王志强 2019/12/20
     * 接口不能用的时候,模拟加载更多数据
     */
    private void loadMoreTestData() {
        if (stateLiveData.getValue().size() > 50) {
            ToastUtils.showLong("兄dei，崩是不可能的~");
            uc.finishLoadMore.call();
            return;
        }
        model.loadMore()
                .compose(RxUtils.observableToMain()) //线程调度
                .doOnSubscribe(NetWorkViewModel.this) //请求与ViewModel周期同步
                .subscribe(new Observer<DemoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        KLog.d("开始上拉加载更多");
                    }

                    @Override
                    public void onNext(DemoBean demoBean) {
                        stateLiveData.getValue().addAll(demoBean.getItems());
                        stateLiveData.setValue(stateLiveData.getValue());
                        ToastUtils.showShort("" + stateLiveData.getValue().size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        //刷新完成收回
                        uc.finishLoadMore.call();
                    }
                });
    }


    /**
     * 删除条目
     */
    public void deleteItem(DemoBean.ItemsEntity itemsEntity) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        KLog.e("调用了删除");
        KLog.e("size" + stateLiveData.getValue().size());
        stateLiveData.getValue().remove(itemsEntity);
//        stateLiveData.setValue(stateLiveData.getValue());
        KLog.e("size" + stateLiveData.getValue().size());

    }

    /**
     * 获取条目下标
     * @return
     */
    public int getItemPosition(DemoBean.ItemsEntity itemsEntity) {
        return stateLiveData.getValue().indexOf(itemsEntity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
