package com.wzq.sample.ui.network.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

import com.wzq.mvvmsmart.base.BaseViewModel;
import com.wzq.sample.entity.DemoBean;

public class DetailViewModel extends BaseViewModel {
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
