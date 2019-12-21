package com.wzq.sample.ui.network;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.wzq.mvvmsmart.base.BaseFragment;
import com.wzq.mvvmsmart.event.StateLiveData;
import com.wzq.mvvmsmart.rv_adapter.BaseViewAdapter;
import com.wzq.mvvmsmart.rv_adapter.BindingViewHolder;
import com.wzq.mvvmsmart.rv_adapter.SingleTypeAdapter;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.mvvmsmart.utils.MaterialDialogUtils;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.R;
import com.wzq.sample.app.AppViewModelFactory;
import com.wzq.sample.databinding.FragmentNetworkBinding;
import com.wzq.sample.entity.DemoBean;

/**
 * 王志强 2019/12/20
 * 截止2019年12月21日累计投入时间:45小时
 * 本项目接口地址:  https://www.oschina.net/action/apiv2/banner?catalog=1
 * 去除黄色警告
 * GithubBrowserSample  (NetworkBoundResource), Google AAC 架构中的加载网络or DB的策略
 */
public class NetWorkFragment extends BaseFragment<FragmentNetworkBinding, NetWorkViewModel> {

    private SingleTypeAdapter singleTypeAdapter;

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
        viewModel.requestNetWork();        //请求网络数据
        initRecyclerView();
    }

    private void initRecyclerView() {
        singleTypeAdapter = new SingleTypeAdapter(getActivity(), R.layout.item_single);
        binding.setAdapter(singleTypeAdapter);
        singleTypeAdapter.setDecorator(new DemoAdapterDecorator());
        singleTypeAdapter.setPresenter(new DemoAdapterPresenter());
        binding.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.setAdapter(singleTypeAdapter);
    }

    @Override
    public void initViewObservable() {
        viewModel.stateLiveData.observe(this, itemsEntities -> {
            KLog.e("mLiveData的listBeans.size():" + itemsEntities.size());
            singleTypeAdapter.set(itemsEntities);
        });

        /**
         * 每个界面默认页效果不同
         * 在这里可以动态替换 无网络页,数据错误页, 无数据默认页;
         */
        viewModel.stateLiveData.state
                .observe(this, new Observer<StateLiveData.State>() {
                    @Override
                    public void onChanged(StateLiveData.State state) {
                        if (state.equals(StateLiveData.State.Loading)) {
                            KLog.e("请求数据中--显示loading");
                            showLoading("请求数据中...");
                        }
                        if (state.equals(StateLiveData.State.Success)) {
                            KLog.e("数据获取成功--关闭loading");
                            dismissLoading();
                        }
                        if (state.equals(StateLiveData.State.Idle)) {
                            KLog.e("空闲状态--关闭loading");
                            dismissLoading();
                        }
                    }
                });

        //监听下拉刷新完成
        viewModel.uc.finishRefreshing.observe(NetWorkFragment.this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.refreshLayout.finishRefresh();    //结束刷新
            }
        });
        //监听上拉加载完成
        viewModel.uc.finishLoadMore.observe(NetWorkFragment.this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object o) {
                binding.refreshLayout.finishLoadMore();   //结束刷新
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
        public void onItemClick(DemoBean.ItemsEntity itemsEntity) {
            ToastUtils.showShort(itemsEntity.getName());
        }

        public void onItemLongClick(DemoBean.ItemsEntity itemsEntity) {
            ToastUtils.showShort(itemsEntity.getName());
        }
    }

    public class DemoAdapterDecorator implements BaseViewAdapter.Decorator {

        @Override
        public void decorator(BindingViewHolder holder, int position, int viewType) {
            // you may do something according to position or view type
        }
    }
}
