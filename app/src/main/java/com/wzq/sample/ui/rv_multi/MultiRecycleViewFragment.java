package com.wzq.sample.ui.rv_multi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wzq.mvvmsmart.base.BaseFragment;
import com.wzq.mvvmsmart.rv_adapter.BaseViewAdapter;
import com.wzq.mvvmsmart.rv_adapter.BindingViewHolder;
import com.wzq.mvvmsmart.rv_adapter.MultiTypeAdapter;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.R;
import com.wzq.sample.bean.DemoBean;
import com.wzq.sample.databinding.FragmentMultiRvBinding;
import com.wzq.sample.utils.TestUtils;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * Create Date：2019/01/25
 * Description：RecycleView多布局实现
 */

public class MultiRecycleViewFragment extends BaseFragment<FragmentMultiRvBinding, MultiRecycleViewModel> {
    private static final int VIEW_TYPE_ONE = 1;
    private static final int VIEW_TYPE_TWO = 2;
    private MultiTypeAdapter mMultiTypeAdapter;
    private static final ArrayList<DemoBean.ItemsEntity> list = new ArrayList<>();

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
        mMultiTypeAdapter = new MultiTypeAdapter(getActivity());
        mMultiTypeAdapter.setPresenter(new DemoAdapterPresenter());
        mMultiTypeAdapter.addViewTypeToLayoutMap(VIEW_TYPE_ONE, R.layout.item_multiple1);
        mMultiTypeAdapter.addViewTypeToLayoutMap(VIEW_TYPE_TWO, R.layout.item_multiple2);
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMultiTypeAdapter.addAll(list, new MultiTypeAdapter.MultiViewTyper() {

            @Override
            public int getViewType(Object object) {
                DemoBean.ItemsEntity item = object instanceof DemoBean.ItemsEntity ? ((DemoBean.ItemsEntity) object) : null;
                if (item.getId() % 2 == 0) {
                    return VIEW_TYPE_TWO;
                } else {
                    return VIEW_TYPE_ONE;
                }
            }
        });

        binding.setAdapter(mMultiTypeAdapter);
        mMultiTypeAdapter.setPresenter(new DemoAdapterPresenter());
    }

    private void getData() {
        for (int i = 0; i < 50; i++) {
            list.add(new DemoBean.ItemsEntity(i, "MVVMSmart", TestUtils.GetGirlImgUrl()));
        }
    }

    public class DemoAdapterPresenter implements BaseViewAdapter.Presenter {
        public void onItemClick(DemoBean.ItemsEntity itemsEntity) {
            Toast.makeText(getActivity(), "itemsEntity " + itemsEntity.getName(), Toast.LENGTH_SHORT).show();

        }

        public void onItemLongClick(DemoBean.ItemsEntity itemsEntity) {
            Toast.makeText(getActivity(), "itemsEntity " + itemsEntity.getName(), Toast.LENGTH_SHORT).show();
        }

        public void onViewClick(DemoBean.ItemsEntity itemsEntity) {
            ToastUtils.showShort("点击了button按钮");
        }

    }

    public class DemoAdapterDecorator implements BaseViewAdapter.Decorator {
        @Override
        public void decorator(BindingViewHolder holder, int position, int viewType) {
            // you may do something according to position or view type
        }
    }
}
