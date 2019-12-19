package com.wzq.sample.ui.network;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.wzq.mvvmsmart.base.BaseViewModel;
import com.wzq.mvvmsmart.binding.command.BindingAction;
import com.wzq.mvvmsmart.binding.command.BindingCommand;
import com.wzq.mvvmsmart.event.SingleLiveEvent;
import com.wzq.mvvmsmart.http.BaseResponse;
import com.wzq.mvvmsmart.http.ResponseThrowable;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.mvvmsmart.utils.RxUtils;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.data.DemoRepository;
import com.wzq.sample.entity.DemoBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class NetWorkViewModel extends BaseViewModel<DemoRepository> {
    private final MutableLiveData<List<DemoBean.Student>> liveData;
    public SingleLiveEvent<NetWorkItemViewModel> deleteItemLiveData = new SingleLiveEvent<>();
    public NetWorkViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);
        liveData = new MutableLiveData<>();
        liveData.setValue(new ArrayList<DemoBean.Student>());
    }
    public MutableLiveData<List<DemoBean.Student>> getLiveData() {
        return liveData;
    }

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
    }


    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            requestNetWork();
        }
    });

    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            if (liveData.getValue().size() > 50) {
                ToastUtils.showLong("兄dei，崩是不可能的~");
                uc.finishLoadmore.call();
                return;
            }
            //模拟网络上拉加载更多
            model.loadMore()
                    .compose(RxUtils.schedulersTransformer()) //线程调度
                    .doOnSubscribe(NetWorkViewModel.this) //请求与ViewModel周期同步
                    .doOnSubscribe(new Consumer<Disposable>() {
                        @Override
                        public void accept(Disposable disposable) throws Exception {
                            ToastUtils.showShort("上拉加载");
                        }
                    })
                    .subscribe(new Consumer<DemoBean>() {
                        @Override
                        public void accept(DemoBean demoBean) throws Exception {
                            for (DemoBean.Student student : demoBean.getItems()) {
//                                NetWorkItemViewModel itemViewModel = new NetWorkItemViewModel(NetWorkViewModel.this, student);
                                //双向绑定动态添加Item
                                liveData.getValue().add(student);
                            }
                            //刷新完成收回
                            uc.finishLoadmore.call();
                        }
                    });
        }
    });

    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    public void requestNetWork() {
        //可以调用addSubscribe()添加Disposable，请求与View周期同步
        KLog.e("开始请求数据");
        model.demoGet()
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this)//请求与ViewModel周期同步
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog("正在请求...");
                    }
                })
                .subscribe(new Consumer<BaseResponse<DemoBean>>() {
                    @Override
                    public void accept(BaseResponse<DemoBean> response) throws Exception {
                        //清除列表
                        liveData.getValue().clear();
                        //请求成功
                        if (response.getCode() == 1) {
                            List<DemoBean.Student> students = response.getResult().getItems();
                            KLog.e("请求到数据students.size"+students.size());
                            liveData.getValue().addAll(students);
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            ToastUtils.showShort("数据错误");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        uc.finishRefreshing.call();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
                        dismissDialog();
                        //请求刷新完成收回
                        uc.finishRefreshing.call();
                    }
                });
    }

    /**
     * 删除条目
     *
     * @param netWorkItemViewModel
     */
    public void deleteItem(NetWorkItemViewModel netWorkItemViewModel) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        liveData.getValue().remove(netWorkItemViewModel);
    }

    /**
     * 获取条目下标
     *
     * @param netWorkItemViewModel
     * @return
     */
    public int getItemPosition(NetWorkItemViewModel netWorkItemViewModel) {
        return  liveData.getValue().indexOf(netWorkItemViewModel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
