package com.wzq.sample.ui.form_livedata

import android.app.Application
import com.wzq.mvvmsmart.event.SingleLiveEvent
import com.wzq.sample.base.BaseViewModel
import com.wzq.sample.bean.FormEntity

class FormViewModel(application: Application) : BaseViewModel(application) {
    var entity: FormEntity? = null
    var entityLiveData = SingleLiveEvent<FormEntity?>()
}