package com.wzq.sample.ui.form;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;

import com.google.gson.Gson;
import com.wzq.mvvmsmart.binding.command.BindingAction;
import com.wzq.mvvmsmart.binding.command.BindingCommand;
import com.wzq.mvvmsmart.binding.command.BindingConsumer;
import com.wzq.mvvmsmart.binding.viewadapter.spinner.IKeyAndValue;
import com.wzq.mvvmsmart.event.SingleLiveEvent;
import com.wzq.sample.bean.FormEntity;
import com.wzq.sample.bean.SpinnerItemData;
import com.wzq.sample.ui.base.ToolbarViewModel;

import java.util.ArrayList;
import java.util.List;


public class FormViewModel extends ToolbarViewModel {
    public FormEntity entity;

    public List<IKeyAndValue> sexItemDatas;
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

    //性别选择的监听
    public BindingCommand<IKeyAndValue> onSexSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            entity.setSex(iKeyAndValue.getValue());
        }
    });
    //生日选择的监听
    public BindingCommand onBirClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //回调到view层(Fragment)中显示日期对话框
            uc.showDateDialogObservable.set(!uc.showDateDialogObservable.get());
        }
    });
    //是否已婚Switch点状态改变回调
    public BindingCommand<Boolean> onMarryCheckedChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean isChecked) {
            entity.setMarry(isChecked);
        }
    });
    //提交按钮点击事件
    public BindingCommand onCmtClickCommand = new BindingCommand(   new BindingAction() {
        @Override
        public void call() {
            Toast.makeText(getApplication(), "触发提交按钮", Toast.LENGTH_SHORT).show();
            String submitJson = new Gson().toJson(entity);
            entityJsonLiveData.setValue(submitJson);
        }
    });

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
