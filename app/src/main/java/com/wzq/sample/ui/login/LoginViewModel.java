package com.wzq.sample.ui.login;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.wzq.mvvmsmart.event.SingleLiveEvent;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.base.BaseViewModel;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginViewModel extends BaseViewModel {
    private LoginModel model;
    //用户名的绑定,使用的是databinding的ObservableField
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();

    //密码开关观察者
    public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();


    /**
     * LoginActivity的factory调用此构造方法;
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        model = new LoginModel();
        //回显数据,从本地取得数据绑定到View层,
        userName.set(model.getUserName());
        password.set(model.getPassword());
        pSwitchEvent.setValue(false);
    }


    /**
     * 网络模拟一个登陆操作
     **/
    protected void login() {
        if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        stateLiveData.postLoading();
        Observable observable = model.login();
        // RaJava模拟登录
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
                stateLiveData.postLoading();
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //保存账号密码
                model.saveUserName(userName.get());
                model.savePassword(password.get());
                ToastUtils.showLong("模拟Login成功,进入程序");
                stateLiveData.postSuccess();
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
