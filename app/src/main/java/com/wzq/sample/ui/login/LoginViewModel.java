package com.wzq.sample.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.wzq.mvvmsmart.event.SingleLiveEvent;
import com.wzq.mvvmsmart.event.StateLiveData;
import com.wzq.sample.base.BaseViewModel;

public class LoginViewModel extends BaseViewModel {
    private LoginModel loginModel;
    public StateLiveData<Object> stateLiveData;
    //用户名的绑定,使用的是databinding的ObservableField
    public ObservableField<String> userName = new ObservableField<>("");
    //密码的绑定
    public ObservableField<String> password = new ObservableField<>("");
    //用户名清除按钮的显示隐藏绑定
    public ObservableInt clearBtnVisibility = new ObservableInt();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //密码开关观察者
        public SingleLiveEvent<Boolean> pSwitchEvent = new SingleLiveEvent<>();
    }

    /**
     * LoginActivity的factory调用此构造方法;
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginModel = new LoginModel();
        stateLiveData = new StateLiveData<>();
        stateLiveData.setValue(new Object());
        //回显数据,从本地取得数据绑定到View层,
        userName.set(loginModel.getUserName());
        password.set(loginModel.getPassword());
        uc.pSwitchEvent.setValue(false);
    }


    /**
     * 网络模拟一个登陆操作
     **/
    protected void login() {
        /*if (TextUtils.isEmpty(userName.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        //RaJava模拟登录
        addSubscribe(
                loginModel.login()
                .compose(RxUtils.observableToMain()) //线程调度
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        stateLiveData.postLoading();
                    }
                })
                .subscribe((Consumer<Object>) o -> {
                    stateLiveData.postSuccess();
                    //保存账号密码
                    loginModel.saveUserName(userName.get());
                    loginModel.savePassword(password.get());
                    ToastUtils.showLong("模拟Login成功,进入程序");
                    //关闭页面
                    sendBackPressEvent();
                }));
*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
