package com.wzq.sample.ui.testnet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.wzq.sample.R;
import com.wzq.sample.base.BaseFragment;
import com.wzq.sample.databinding.FragmentTestNetBinding;


/**
 * Create Date：2019/01/25
 * Description：RecycleView多布局实现
 */

public class TestNetFragment extends BaseFragment<FragmentTestNetBinding, TestNetViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_test_net;
    }

    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        binding.button.setOnClickListener(
                v -> viewModel.getData()
        );
        viewModel.resultJson.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvJson.setText("resule: " + s);
            }
        });

    }

}
