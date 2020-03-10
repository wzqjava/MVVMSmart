package com.wzq.sample.ui.recycler_multi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.R;
import com.wzq.sample.bean.DemoBean;
import com.wzq.sample.databinding.FragmentMultiRvBinding;
import com.wzq.sample.base.BaseFragment;

import java.util.ArrayList;


/**
 * Create Date：2019/01/25
 * Description：RecycleView多布局实现
 */

public class MultiRecycleViewFragment extends BaseFragment<FragmentMultiRvBinding, MultiRecycleViewModel> {


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
        viewModel.getData();
        initRecyclerView();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.itemsEntityLiveData.observe(this, itemsEntities -> {
            mAdapter.setNewData(itemsEntities);
        });
    }

    private void initRecyclerView() {
        ArrayList<DemoBean.ItemsEntity> datas = new ArrayList<>();
        mAdapter = new MyMultiAdapter(datas);
        binding.setAdapter(mAdapter);
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DemoBean.ItemsEntity item = (DemoBean.ItemsEntity) adapter.getItem(position);
                ToastUtils.showShort(item.getName() + "---" + position);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DemoBean.ItemsEntity item = (DemoBean.ItemsEntity) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.btn1:
                        ToastUtils.showShort("btn1---" + position);
                        break;
                    case R.id.btn2:
                        ToastUtils.showShort("btn2---" + position);
                        break;
                }

            }
        });
    }



}
