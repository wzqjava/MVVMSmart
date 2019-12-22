package com.wzq.sample.ui.rv_multi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wzq.mvvmsmart.base.BaseFragment;
import com.wzq.mvvmsmart.rv_adapter.BaseViewAdapter;
import com.wzq.mvvmsmart.rv_adapter.BindingViewHolder;
import com.wzq.mvvmsmart.rv_adapter.MultiTypeAdapter;
import com.wzq.sample.R;
import com.wzq.sample.databinding.FragmentMultiRvBinding;
import com.wzq.sample.bean.Bean1;
import com.wzq.sample.bean.Bean2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Create Date：2019/01/25
 * Description：RecycleView多布局实现
 */

public class MultiRecycleViewFragment extends BaseFragment<FragmentMultiRvBinding, MultiRecycleViewModel> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_EMPLOYEE = 1;
    private static final int VIEW_TYPE_EMPLOYER = 2;
    private MultiTypeAdapter mMultiTypeAdapter;
    private static final ArrayList<Bean1> list1 = new ArrayList<>();
    private static final ArrayList<Bean2> list2 = new ArrayList<>();
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
        initData2();
        mMultiTypeAdapter = new MultiTypeAdapter(getActivity());
        mMultiTypeAdapter.setPresenter(new DemoAdapterPresenter());

        mMultiTypeAdapter.addViewTypeToLayoutMap(VIEW_TYPE_HEADER, R.layout.item_header);
        mMultiTypeAdapter.addViewTypeToLayoutMap(VIEW_TYPE_EMPLOYEE, R.layout.item2_1);
        mMultiTypeAdapter.addViewTypeToLayoutMap(VIEW_TYPE_EMPLOYER, R.layout.item2_2);
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));

        mMultiTypeAdapter.add(null, VIEW_TYPE_HEADER);
        mMultiTypeAdapter.addAll(list1, VIEW_TYPE_EMPLOYEE);
        mMultiTypeAdapter.addAll(list2, VIEW_TYPE_EMPLOYER);

        final List<Object> data = new ArrayList<Object>();
        data.addAll(list1);
        data.addAll(list2);
        Collections.shuffle(data);

        mMultiTypeAdapter.addAll(data, new MultiTypeAdapter.MultiViewTyper() {
            @Override
            public int getViewType(Object item) {
                if (item instanceof Bean2) {
                    return VIEW_TYPE_EMPLOYER;
                }

                if (item instanceof Bean1) {
                    return VIEW_TYPE_EMPLOYEE;
                }

                return 0;
            }
        });
        binding.setAdapter(mMultiTypeAdapter);
        mMultiTypeAdapter.setPresenter(new DemoAdapterPresenter());
    }

    private  void initData2() {
        Bean1 model1 = new Bean1("markzhai", 26);
        Bean1 model2 = new Bean1("dim", 25);
        Bean1 model3 = new Bean1("abner", 25);
        Bean1 model4 = new Bean1("cjj", 26);

        list1.add(model1);
        list1.add(model2);
        list1.add(model3);
        list1.add(model4);

        Bean2 model5 = new Bean2("boss1", 30,
                "https://avatars2.githubusercontent.com/u/1106500?v=3&s=150", "CEO");

        Bean2 model6 = new Bean2("boss2", 31,
                "https://avatars3.githubusercontent.com/u/11629640?v=3&s=150", "CTO");

        Bean2 model7 = new Bean2("boss3", 38,
                "https://avatars2.githubusercontent.com/u/1468623?v=3&s=150", "CAO");

        list2.add(model5);
        list2.add(model6);
        list2.add(model7);
    }

    public class DemoAdapterPresenter implements BaseViewAdapter.Presenter {
        public void onItemClick(Bean1 model) {
            Toast.makeText(getActivity(), "employee " + model.name, Toast.LENGTH_SHORT).show();

        }

        public void onItemClick(Bean2 model) {
            Toast.makeText(getActivity(), "employer " + model.name, Toast.LENGTH_SHORT).show();
        }
    }

    public class DemoAdapterDecorator implements BaseViewAdapter.Decorator {

        @Override
        public void decorator(BindingViewHolder holder, int position, int viewType) {
            // you may do something according to position or view type
        }
    }
}
