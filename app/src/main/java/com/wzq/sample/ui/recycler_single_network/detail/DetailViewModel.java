package com.wzq.sample.ui.recycler_single_network.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.wzq.mvvmsmart.base.BaseViewModelMVVM;
import com.wzq.sample.bean.DemoBean;

public class DetailViewModel extends BaseViewModelMVVM {
    public ObservableField<DemoBean.ItemsEntity> entity = new ObservableField<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDemoEntity(DemoBean.ItemsEntity entity) {
        this.entity.set(entity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        entity = null;
    }
}
