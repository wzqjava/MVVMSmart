package com.wzq.sample.ui.rv_multi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.wzq.mvvmsmart.base.BaseFragment;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.R;
import com.wzq.sample.bean.DemoBean;
import com.wzq.sample.databinding.FragmentMultiRvBinding;
import com.wzq.sample.utils.TestUtils;

import java.util.ArrayList;


/**
 * Create Date：2019/01/25
 * Description：RecycleView多布局实现
 */

public class MultiRecycleViewFragment extends BaseFragment<FragmentMultiRvBinding, MultiRecycleViewModel> {


    private ArrayList<DemoBean.ItemsEntity> datas = new ArrayList<>();
    private MyMultiAdapter mAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_multi_rv;
    }

    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        getData();
        initRecyclerView();

    }

    private void initRecyclerView() {
        mAdapter = new MyMultiAdapter(datas);
        binding.setAdapter(mAdapter);
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rv.addOnItemTouchListener(new OnItemChildClickListener() {

            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DemoBean.ItemsEntity item = (DemoBean.ItemsEntity)adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.btn1:
                        ToastUtils.showShort("btn1" + item.getName());
                        break;
                    case R.id.btn2:
                        ToastUtils.showShort("btn2" + item.getName());

                        break;
                }

            }
        });
    }

    private void getData() {
        for (int i = 0; i < 50; i++) {
            DemoBean.ItemsEntity ItemBean = new DemoBean.ItemsEntity(i, "MVVMSmart", TestUtils.GetGirlImgUrl());
            if (i % 2 == 0) {
                ItemBean.itemType = 0;
            } else {
                ItemBean.itemType = 1;
            }
            datas.add(ItemBean);
        }
    }

}
