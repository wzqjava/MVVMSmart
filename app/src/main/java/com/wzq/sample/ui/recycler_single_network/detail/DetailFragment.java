package com.wzq.sample.ui.recycler_single_network.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.wzq.mvvmsmart.base.BaseFragmentMVVM;
import com.wzq.sample.R;
import com.wzq.sample.databinding.FragmentDetailBinding;
import com.wzq.sample.bean.DemoBean;


/**
 * 详情界面
 */

public class DetailFragment extends BaseFragmentMVVM<FragmentDetailBinding, DetailViewModel> {

    private DemoBean.ItemsEntity entity;

    @Override
    public void initParam() {
        //获取列表传入的实体
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity");
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_detail;
    }

    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }

    @Override
    public void initData() {
        viewModel.setDemoEntity(entity);
    }
}
