package com.wzq.sample.ui.recycler_multi;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.wzq.sample.bean.DemoBean;
import com.wzq.sample.base.BaseViewModel;
import com.wzq.sample.utils.TestUtils;

import java.util.ArrayList;

/**
 * 王志强
 * Create Date：2019/01/25
 * Description：
 */
public class MultiRecycleViewModel extends BaseViewModel {

    //给RecyclerView添加ObservableList
    public MutableLiveData<ArrayList<DemoBean.ItemsEntity>> itemsEntityLiveData;

    public MultiRecycleViewModel(@NonNull Application application) {
        super(application);
        itemsEntityLiveData = new MutableLiveData<>();
    }

    public void getData() {
        ArrayList<DemoBean.ItemsEntity> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            DemoBean.ItemsEntity ItemBean = new DemoBean.ItemsEntity(i, "MVVMSmart", TestUtils.GetGirlImgUrl());
            if (i % 2 == 0) {
                ItemBean.itemType = 0;
            } else {
                ItemBean.itemType = 1;
            }
            datas.add(ItemBean);
        }
        itemsEntityLiveData.setValue(datas);
    }
}
