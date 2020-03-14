package com.wzq.sample.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;

import com.wzq.mvvmsmart.event.SingleLiveEvent;
import com.wzq.sample.base.ToolbarViewModel;


public class MainViewModel extends ToolbarViewModel {
    //使用Observable
    public SingleLiveEvent<Boolean> requestCameraPermissions = new SingleLiveEvent<>();
    //使用LiveData
    public SingleLiveEvent<String> loadUrlEvent = new SingleLiveEvent<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }


}
