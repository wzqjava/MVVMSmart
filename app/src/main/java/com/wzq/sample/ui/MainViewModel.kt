package com.wzq.sample.ui

import android.app.Application
import com.wzq.mvvmsmart.event.SingleLiveEvent
import com.wzq.sample.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    //使用Observable
    var requestCameraPermissions = SingleLiveEvent<Boolean?>()

    //使用LiveData
    var loadUrlEvent = SingleLiveEvent<String?>()
}