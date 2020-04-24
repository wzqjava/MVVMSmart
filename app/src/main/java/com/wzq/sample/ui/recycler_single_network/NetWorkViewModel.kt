package com.wzq.sample.ui.recycler_single_network

import android.app.Application
import android.app.TaskStackBuilder
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.wzq.mvvmsmart.http.BaseResponse
import com.wzq.mvvmsmart.http.ResponseThrowable
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.mvvmsmart.utils.RxUtils
import com.wzq.mvvmsmart.utils.Tasks
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.bean.DemoBean
import com.wzq.sample.bean.DemoBean.ItemsEntity
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.observable.ObservableOnErrorNext
import org.reactivestreams.Subscriber
import java.util.*
import java.util.function.Consumer

class NetWorkViewModel(application: Application) : BaseViewModel(application) {
    private val model: NetWorkModel
    var pageNum = 1
    var liveData: MutableLiveData<MutableList<ItemsEntity?>>

    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    fun requestNetWork() {
        //可以调用addSubscribe()添加Disposable，请求与View周期同步
        val observable = model.demoGet(pageNum)
        observable.compose(RxUtils.observableToMain()) //线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this@NetWorkViewModel) //  请求与ViewModel周期同步
                .subscribe(object : Observer<BaseResponse<DemoBean>> {
                    override fun onSubscribe(d: Disposable) {
                        stateLiveData.postLoading()
                    }

                    override fun onNext(response: BaseResponse<DemoBean>) {
                        KLog.e("进入onNext")
                        //请求成功
                        if (response.code == 1) {  // code  =1 代表成功
                            val itemsEntities = response.result?.items
                            if (itemsEntities != null) {
                                if (itemsEntities.size > 0) {
                                    if (pageNum == 1) {
                                        liveData.value!!.clear()
                                    }
                                    liveData.postValue(itemsEntities)
                                } else {
//                                    showShortToast("没有更多数据了")
                                    KLog.e("请求到数据students.size" + itemsEntities.size)
                                }
                            } else {
                                KLog.e("数据返回null")
                                stateLiveData.postError()
                            }
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            KLog.e("请求失败response.getCode():" + response.code)
                            stateLiveData.postNoData() // 这里是接口问题,状态返回1代表

                        }
                    }

                    override fun onError(throwable: Throwable) {
                        KLog.e("进入onError" + throwable.message)
                        //关闭对话框
                        stateLiveData.postError()
                        if (throwable is ResponseThrowable) {
//                           showShortToast(throwable.message)
                        }
                    }

                    override fun onComplete() {
                        KLog.e("进入onComplete")
                        //关闭对话框
                    }
                })

        /*object : Observer<BaseResponse<DemoBean>> {
            override fun onSubscribe(d: Disposable) {
                stateLiveData.postLoading()
            }

            override fun onNext(response: BaseResponse<DemoBean>) {
                KLog.e("进入onNext")
                //请求成功
                if (response.code == 1) {
                    val itemsEntities = response.result?.items
                    if (itemsEntities != null) {
                        if (itemsEntities.size > 0) {
                            if (pageNum == 1) {
                                liveData.value!!.clear()
                            }
                            liveData.postValue(itemsEntities)
                        } else {
//                                    showShortToast("没有更多数据了")
                            KLog.e("请求到数据students.size" + itemsEntities.size)
                        }
                    } else {
                        KLog.e("数据返回null")
                        stateLiveData.postError()
                    }
                } else {
                    //code错误时也可以定义Observable回调到View层去处理
                    KLog.e("请求失败response.getCode():" + response.code)
//                            showLongToast("提醒开发者: 本次上拉服务器没有数据")
                }
            }

            override fun onError(throwable: Throwable) {
                KLog.e("进入onError" + throwable.message)
                //关闭对话框
                stateLiveData.postIdle()
                if (throwable is ResponseThrowable) {
//                           showShortToast(throwable.message)
                }
            }

            override fun onComplete() {
                KLog.e("进入onComplete")
                //关闭对话框
                stateLiveData.postIdle()
            }
        }*/
    }

    /**
     * 王志强 2019/12/20
     * 接口不能用的时候,模拟加载更多数据
     */
    private fun loadMoreTestData() {
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
    fun deleteItem(itemsEntity: ItemsEntity?) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        KLog.e("调用了删除")
        KLog.e("size" + liveData.value!!.size)
        liveData.value!!.remove(itemsEntity)
        KLog.e("size" + liveData.value!!.size)
    }

    init {
        liveData = MutableLiveData()
        liveData.value = ArrayList()
        model = NetWorkModel()
    }
}