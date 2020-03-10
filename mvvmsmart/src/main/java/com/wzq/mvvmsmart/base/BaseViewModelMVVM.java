package com.wzq.mvvmsmart.base;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.wzq.mvvmsmart.event.SingleLiveEvent;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
public class BaseViewModelMVVM<M extends BaseModelMVVM> extends AndroidViewModel implements IBaseViewModelMVVM, Consumer<Disposable> {
    protected M model;
    private UIChangeLiveData uiChangeLiveData;
    //管理RxJava，主要针对RxJava异步操作造成的内存泄漏
    private CompositeDisposable mCompositeDisposable;
    public BaseViewModelMVVM(@NonNull Application application) {
        this(application, null);
    }

    public BaseViewModelMVVM(@NonNull Application application, M model) {
        super(application);
        this.model = model;
        mCompositeDisposable = new CompositeDisposable();
    }

    protected void addSubscribe(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public UIChangeLiveData getUC() {
        if (uiChangeLiveData == null) {
            uiChangeLiveData = new UIChangeLiveData();
        }
        return uiChangeLiveData;
    }

    /**
     * 跳转页面
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Map<String, Object> params = new HashMap<>();
        params.put(ParameterField.CLASS, clz);
        if (bundle != null) {
            params.put(ParameterField.BUNDLE, bundle);
        }
        uiChangeLiveData.startActivityLiveData.postValue(params);
    }




    /**
     * 关闭界面
     * 事件儿触发在ToolbarViewModel中,最终把事件传递到了UI层(Activity,Fragment)
     */
    public void sendFinishEvent() {
        uiChangeLiveData.finishLiveData.call();
    }

    /**
     * 返回上一层
     * 事件儿触发在ToolbarViewModel中,最终把事件传递到了UI层(Activity,Fragment)
     */
    public void sendBackPressEvent() {
        uiChangeLiveData.onBackPressedLiveData.call();
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.onCleared();
        }
        //ViewModel销毁时会执行，同时取消所有异步任务
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void accept(Disposable disposable) throws Exception {
        addSubscribe(disposable);
    }

    public final class UIChangeLiveData extends SingleLiveEvent {
        private SingleLiveEvent<Map<String, Object>> startActivityLiveData;
        private SingleLiveEvent<Void> finishLiveData;
        private SingleLiveEvent<Void> onBackPressedLiveData;

        public SingleLiveEvent<Map<String, Object>> getStartActivityLiveData() {
            return startActivityLiveData = createLiveData(startActivityLiveData);
        }

        public SingleLiveEvent<Void> getFinishLiveData() {
            return finishLiveData = createLiveData(finishLiveData);
        }

        public SingleLiveEvent<Void> getOnBackPressedLiveData() {
            return onBackPressedLiveData = createLiveData(onBackPressedLiveData);
        }

        private <T> SingleLiveEvent<T> createLiveData(SingleLiveEvent<T> liveData) {
            if (liveData == null) {
                liveData = new SingleLiveEvent<>();
            }
            return liveData;
        }

        @Override
        public void observe(LifecycleOwner owner, Observer observer) {
            super.observe(owner, observer);
        }
    }

    public static final class ParameterField {
        public static String CLASS = "CLASS";
        public static String CANONICAL_NAME = "CANONICAL_NAME";
        public static String BUNDLE = "BUNDLE";
    }
}
