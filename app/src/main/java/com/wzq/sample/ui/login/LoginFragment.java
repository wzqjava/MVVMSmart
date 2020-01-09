package com.wzq.sample.ui.login;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.wzq.mvvmsmart.base.BaseFragment;
import com.wzq.mvvmsmart.event.StateLiveData;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.sample.R;
import com.wzq.sample.app.AppViewModelFactory;
import com.wzq.sample.databinding.FragmentLoginBinding;

/**
 * 一个MVVM模式的登陆界面
 * 登录按钮的点击事件在viewmode中,
 */
public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginViewModel> {
    //ActivityLoginBinding类是databinding框架自动生成的,对应activity_login.xml
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_login;
    }

    // 给当前LoginActivity设置viewmodel,布局文件中的variable的name为viewModel;
    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }

    /**
     * initViewModel---此方法很重要,初学者可以慢慢体会,此处不必马上追究清楚,以先会用MVVMSmart为首要原则
     */
    /**
     * 重写父类的initViewModel,baseActivity中创建viewmodel会调用此处重写的创建方法
     * 使用自定义的ViewModelFactory来创建ViewModel
     * AppViewModelFactory,中注入仓库类(网络数据,本地数据)
     * 网络数据: 初始化RetrofitClient(),HttpDataSource(get和pos请求)
     * 本地数据: 保存和获取用户名和密码(4个功能),目前用sp,可以接入room
     */
    /**
     * 如果不重写调用默认的创建viewmode的方法(没有使用factory),
     * 如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
     * 如果不重写且LoginActivity的viewmode泛型传null,则会默认使用BaseViewModel
     */
    @Override
    public LoginViewModel initViewModel() {
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        //  使用了factory ,factory向LoginView里注入了数据仓库
        return ViewModelProviders.of(this, factory).get(LoginViewModel.class);
    }

    @Override
    public void initViewObservable() {
        binding.btnLogin.setOnClickListener(view ->{
            viewModel.login();
        });
        /**
         * 每个界面默认页效果不同
         * 在这里可以动态替换 无网络页,数据错误页, 无数据默认页;
         */
        viewModel.stateLiveData.stateEnumMutableLiveData
                .observe(this, new Observer<StateLiveData.StateEnum>() {
                    @Override
                    public void onChanged(StateLiveData.StateEnum stateEnum) {
                        if (stateEnum.equals(StateLiveData.StateEnum.Loading)) {
                            KLog.e("请求数据中--显示loading");
                            showLoading("请求数据中...");
                        }
                        if (stateEnum.equals(StateLiveData.StateEnum.Success)) {
                            KLog.e("数据获取成功--关闭loading");
                            dismissLoading();
                        }
                        if (stateEnum.equals(StateLiveData.StateEnum.Idle)) {
                            KLog.e("空闲状态--关闭loading");
                            dismissLoading();
                        }
                    }
                });
        //监听ViewModel中pSwitchObservable的变化, 当ViewModel中执行【uc.pSwitchObservable.set(!uc.pSwitchObservable.get());】时会回调该方法
        viewModel.uc.pSwitchEvent.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                //pSwitchObservable是boolean类型的LiveData观察者,所以可以直接使用它的值改变密码开关的图标
                if (viewModel.uc.pSwitchEvent.getValue()) {
                    //密码可见
                    //在xml中定义id后,使用binding可以直接拿到这个view的引用,不再需要findViewById去找控件了
                    binding.ivSwichPasswrod.setImageResource(R.drawable.show_psw);
                    binding.etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //密码不可见
                    binding.ivSwichPasswrod.setImageResource(R.drawable.show_psw_press);
                    binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }


}
