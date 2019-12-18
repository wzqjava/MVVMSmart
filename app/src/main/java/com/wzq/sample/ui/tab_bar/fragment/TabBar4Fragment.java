package com.wzq.sample.ui.tab_bar.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.wzq.mvvmsmart.base.BaseFragment;
import com.wzq.sample.R;

public class TabBar4Fragment extends BaseFragment {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_tab_bar_fore;
    }

    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }
}
