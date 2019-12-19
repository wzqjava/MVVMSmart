package com.wzq.sample.ui.main;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.wzq.mvvmsmart.base.BaseActivity;
import com.wzq.mvvmsmart.base.BaseViewModel;
import com.wzq.sample.R;
import com.wzq.sample.databinding.ActivityDemoBinding;

public class DemoActivity extends BaseActivity<ActivityDemoBinding, BaseViewModel> {
    @Override
    public void initParam() {
        super.initParam();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_demo;
    }

    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }


}
