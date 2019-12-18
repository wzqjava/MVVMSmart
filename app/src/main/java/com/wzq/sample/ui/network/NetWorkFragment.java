package com.wzq.sample.ui.network;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wzq.mvvmsmart.base.BaseFragment;
import com.wzq.mvvmsmart.rv_adapter.BaseViewAdapter;
import com.wzq.mvvmsmart.rv_adapter.BindingViewHolder;
import com.wzq.mvvmsmart.rv_adapter.SingleTypeAdapter;
import com.wzq.mvvmsmart.utils.MaterialDialogUtils;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.R;
import com.wzq.sample.app.AppViewModelFactory;
import com.wzq.sample.databinding.FragmentNetworkBinding;
import com.wzq.sample.entity.Bean1;
import com.wzq.sample.entity.Bean2;

import java.util.ArrayList;


/**
 * 网络请求列表界面
 */

public class NetWorkFragment extends BaseFragment<FragmentNetworkBinding, NetWorkViewModel> {
    private static final ArrayList<Bean1> list1 = new ArrayList<>();
    @Override
    public void initParam() {
        super.initParam();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_network;
    }

    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }

    @Override
    public NetWorkViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(NetWorkViewModel.class);
    }

    @Override
    public void initData() {
        initMData();
        SingleTypeAdapter singleTypeAdapter = new SingleTypeAdapter(getActivity(), R.layout.item1);
        binding.setAdapter(singleTypeAdapter);
        singleTypeAdapter.setDecorator(new DemoAdapterDecorator());
        singleTypeAdapter.setPresenter(new DemoAdapterPresenter());
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));
        singleTypeAdapter.addAll(list1);
        binding.setAdapter(singleTypeAdapter);
        //请求网络数据
//        viewModel.requestNetWork();
    }

    @Override
    public void initViewObservable() {

        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
//                binding.refreshLayout.finishRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadmore.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                //结束刷新
//                binding.refreshLayout.finishLoadmore();
            }
        });
        //监听删除条目
        viewModel.deleteItemLiveData.observe(this, new Observer<NetWorkItemViewModel>() {
            @Override
            public void onChanged(@Nullable final NetWorkItemViewModel netWorkItemViewModel) {
                int index = viewModel.getItemPosition(netWorkItemViewModel);
                //删除选择对话框
                MaterialDialogUtils.showBasicDialog(getContext(), "提示", "是否删除【" + netWorkItemViewModel.entity.get().getName() + "】？ position：" + index)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ToastUtils.showShort("取消");
                            }
                        }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        viewModel.deleteItem(netWorkItemViewModel);
                    }
                }).show();
            }
        });
    }


    public class DemoAdapterPresenter implements BaseViewAdapter.Presenter {
        public void onItemClick(Bean1 model) {
            ToastUtils.showShort( model.name);
        }

        public void onItemClick(Bean2 model) {
            Toast.makeText(getActivity(),  model.name, Toast.LENGTH_SHORT).show();
        }
    }

    public class DemoAdapterDecorator implements BaseViewAdapter.Decorator {

        @Override
        public void decorator(BindingViewHolder holder, int position, int viewType) {
            // you may do something according to position or view type
        }
    }


    public void initMData() {
        Bean1 model0 = new Bean1("wzq0", 100);
        Bean1 model1 = new Bean1("wzq1", 100);
        Bean1 model2 = new Bean1("wzq2", 100);
        Bean1 model3 = new Bean1("wzq3", 100);
        Bean1 model4 = new Bean1("wzq4", 100);
        list1.add(model0);
        list1.add(model1);
        list1.add(model2);
        list1.add(model3);
        list1.add(model4);
    }
}
