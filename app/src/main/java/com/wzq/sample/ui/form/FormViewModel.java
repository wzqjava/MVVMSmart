package com.wzq.sample.ui.form;

import android.app.Application;

import androidx.annotation.NonNull;

import com.wzq.mvvmsmart.event.SingleLiveEvent;
import com.wzq.sample.base.BaseViewModel;
import com.wzq.sample.bean.FormEntity;


public class FormViewModel extends BaseViewModel {
    public FormEntity entity;

    public SingleLiveEvent<FormEntity> entityLiveData = new SingleLiveEvent<>();


    public FormViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }





}
