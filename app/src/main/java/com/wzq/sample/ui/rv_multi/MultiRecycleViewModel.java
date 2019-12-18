package com.wzq.sample.ui.rv_multi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.wzq.mvvmsmart.base.BaseViewModel;
import com.wzq.mvvmsmart.base.MultiItemViewModel;


/**
 * Create Author：goldze
 * Create Date：2019/01/25
 * Description：
 */

public class MultiRecycleViewModel extends BaseViewModel {
    public MultiRecycleViewModel(@NonNull Application application) {
        super(application);
    }
    //给RecyclerView添加ObservableList
    public ObservableList<MultiItemViewModel> observableList = new ObservableArrayList<>();

}
