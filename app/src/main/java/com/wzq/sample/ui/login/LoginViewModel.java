package com.wzq.sample.ui.login;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.wzq.mvvmsmart.base.BaseViewModel;
import com.wzq.mvvmsmart.binding.command.BindingAction;
import com.wzq.mvvmsmart.binding.command.BindingCommand;
import com.wzq.mvvmsmart.binding.command.BindingConsumer;
import com.wzq.mvvmsmart.event.SingleLiveEvent;
import com.wzq.mvvmsmart.event.StateLiveData;
import com.wzq.mvvmsmart.utils.RxUtils;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.data.DemoRepository;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginViewModel extends BaseViewModel<DemoRepository> {
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
     * 向父类传递数据仓库
     * @param application
     * @param repository    DemoRepository是BaseModel的子类;
     */
    public LoginViewModel(@NonNull Application application, DemoRepository repository) {
        super(application, repository);
        stateLiveData = new StateLiveData<>();
        stateLiveData.setValue(new Object());
        //回显数据,从本地取得数据绑定到View层,
        userName.set(model.getUserName());
        password.set(model.getPassword());
        uc.pSwitchEvent.setValue(false);
    }

    //清除用户名的点击事件, 逻辑从View层转换到ViewModel层
    public BindingCommand clearUserNameOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            userName.set("");
        }
    });
    //密码显示开关  (你可以尝试着狂按这个按钮,会发现它有防多次点击的功能)
    public BindingCommand passwordShowSwitchOnClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //让观察者的数据改变,逻辑从ViewModel层转到View层，在View层的监听则会被调用
            uc.pSwitchEvent.setValue(!uc.pSwitchEvent.getValue());
        }
    });

    //用户名输入框焦点改变的回调事件
    public BindingCommand<Boolean> onFocusChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean hasFocus) {
            if (hasFocus) {
                clearBtnVisibility.set(View.VISIBLE);
            } else {
                clearBtnVisibility.set(View.INVISIBLE);
            }
        }
    });

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
        //RaJava模拟登录
        addSubscribe(
                model.login()
                .compose(RxUtils.observableToMain()) //线程调度
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        stateLiveData.postLoading();
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        stateLiveData.postSuccess();
                        //保存账号密码
                        model.saveUserName(userName.get());
                        model.savePassword(password.get());
                        ToastUtils.showLong("模拟Login成功,进入程序");
                        //关闭页面
                        sendBackPressEvent();
                    }
                }));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
