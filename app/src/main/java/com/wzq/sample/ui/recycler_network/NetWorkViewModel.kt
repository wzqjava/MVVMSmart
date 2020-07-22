package com.wzq.sample.ui.recycler_network

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.wzq.mvvmsmart.net.base.BaseResponse
import com.wzq.mvvmsmart.net.observer.DefaultObserver
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.mvvmsmart.net.net_utils.RxUtil
import com.wzq.mvvmsmart.utils.ToastUtils
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.bean.NewsData
import io.reactivex.disposables.Disposable

class NetWorkViewModel(application: Application) : BaseViewModel(application) {
    private val model: NetWorkModel = NetWorkModel()
    var pageNum = 1

    //    var liveData: MutableLiveData<MutableList<ItemsEntity?>> = MutableLiveData()
    val liveData: MutableLiveData<ArrayList<NewsData>> by lazy {
        MutableLiveData<ArrayList<NewsData>>()
    }

    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    fun doGetServerNews() {
        //可以调用addSubscribe()添加Disposable，请求与View周期同步
        val observable = model.doGetServerNews(pageNum)
        observable.compose(RxUtil.observableToMain()) //线程调度,compose操作符是直接对当前Observable进行操作（可简单理解为不停地.方法名（）.方法名（）链式操作当前Observable）
                .compose(RxUtil.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(this@NetWorkViewModel) //  请求与ViewModel周期同步
                .doOnSubscribe {
                    d ->
                stateLiveData.postLoading()
            }
                .doFinally { stateLiveData.postIdle() }
                .subscribe(object : DefaultObserver<ArrayList<NewsData>>() {
                    override fun onSubscribe(d: Disposable) {
                        super.onSubscribe(d)
                    }

                    override fun onNext(baseResponse: BaseResponse<ArrayList<NewsData>>) {
                        super.onNext(baseResponse)
                        // 请求成功
                        if (baseResponse.status == 1) {  // 接口返回code=1 代表成功
                            val newsDataList = baseResponse.data
                            if (newsDataList != null) {
                                if (newsDataList.size > 0) {
                                    liveData.postValue(newsDataList)
                                } else {
                                    //    showShortToast("没有更多数据了")
                                    KLog.e("请求到数据students.size" + newsDataList.size)
                                }
                            } else {
                                KLog.e("数据返回null")
                                stateLiveData.postError()
                            }
                        } else {
                            //code错误时也可以定义Observable回调到View层去处理
                            ToastUtils.showShort("提醒开发者:本页无数据...")
                            KLog.e("请求失败response.getCode():" + baseResponse.code)
                            //                            liveData.postValue(null)
                        }
                    }

                    override fun onError(throwable: Throwable) {
                        super.onError(throwable)
                        KLog.e("进入onError" + throwable.message)
                        //关闭对话框
                        stateLiveData.postError()
                        /* if (throwable is ResponseThrowable) {
                         showShortToast(throwable.message)
                         }*/
                    }

                    override fun onComplete() {
                        super.onComplete()
                        //关闭对话框
                    }

                })

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
    fun deleteItem(newsData: NewsData?) {
        //点击确定，在 observableList 绑定中删除，界面立即刷新
        KLog.e("调用了删除")
        KLog.e("size" + liveData.value?.size)
        val newsDataList = liveData.value
        newsDataList?.remove(newsData)
        KLog.e("size" + liveData.value?.size)
    }

}