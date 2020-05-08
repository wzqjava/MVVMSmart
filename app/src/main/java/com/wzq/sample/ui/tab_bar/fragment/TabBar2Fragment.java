package com.wzq.sample.ui.tab_bar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wzq.sample.R;
import com.wzq.sample.base.BaseFragment;


public class TabBar2Fragment extends BaseFragment {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tab_bar_2;
    }

    @Override
    public int initVariableId() {

        return com.wzq.sample.BR.viewModel;
    }
    /**
     * ViewPager中的Fragment使用navigation,需要创建View的时候,从之前的父类中remove掉
     * 不remove掉的话,从其他页面返回含有ViewPager页面的话会报错"The specified child already has a parent"
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (lastView != null) {
            ViewGroup parent = (ViewGroup) lastView.getParent();
            if (parent != null) {
                parent.removeView(lastView);
            }
        }
        super.onCreateView(inflater, container, savedInstanceState);
        return lastView;

    }
}
