package com.wzq.sample.ui.form;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;

import com.wzq.mvvmsmart.event.SingleLiveEvent;
import com.wzq.sample.base.BaseViewModel;
import com.wzq.sample.bean.FormEntity;
import com.wzq.sample.bean.SpinnerItemData;

import java.util.ArrayList;
import java.util.List;


public class FormViewModel extends BaseViewModel {
    public FormEntity entity;

    public List<SpinnerItemData> sexItemDatas;
    public SingleLiveEvent<String> entityJsonLiveData = new SingleLiveEvent<>();
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc;

    public class UIChangeObservable {
        //显示日期对话框
        public ObservableBoolean showDateDialogObservable;

        public UIChangeObservable() {
            showDateDialogObservable = new ObservableBoolean(false);
        }
    }

    public FormViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        uc = new UIChangeObservable();
        //sexItemDatas 一般可以从本地Sqlite数据库中取出数据字典对象集合，让该对象实现IKeyAndValue接口
        sexItemDatas = new ArrayList<>();
        sexItemDatas.add(new SpinnerItemData("男", "1"));
        sexItemDatas.add(new SpinnerItemData("女", "2"));
    }

    public void setFormEntity(FormEntity entity) {
        if (this.entity == null) {
            this.entity = entity;
        }
    }

    public void setBir(int year, int month, int dayOfMonth) {
        //设置数据到实体中，自动刷新界面
        entity.setBir(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
        //刷新实体,驱动界面更新
        entity.notifyChange();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
