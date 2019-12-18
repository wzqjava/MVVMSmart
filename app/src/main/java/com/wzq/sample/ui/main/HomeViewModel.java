package com.wzq.sample.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;

import com.wzq.mvvmsmart.base.BaseViewModel;
import com.wzq.mvvmsmart.event.SingleLiveEvent;


public class HomeViewModel extends BaseViewModel {
    //使用Observable
    public SingleLiveEvent<Boolean> requestCameraPermissions = new SingleLiveEvent<>();
    //使用LiveData
    public SingleLiveEvent<String> loadUrlEvent = new SingleLiveEvent<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }


}
