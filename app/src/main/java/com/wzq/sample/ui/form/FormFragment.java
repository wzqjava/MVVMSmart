package com.wzq.sample.ui.form;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Observable;
import androidx.lifecycle.Observer;

import com.wzq.mvvmsmart.base.BaseFragment;
import com.wzq.mvvmsmart.utils.MaterialDialogUtils;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.R;
import com.wzq.sample.bean.FormEntity;
import com.wzq.sample.databinding.FragmentFormTempBinding;

import java.util.Calendar;


/**
 * Created by 王志强 on 2019/11/30.
 * 表单提交/编辑界面
 */

public class FormFragment extends BaseFragment<FragmentFormTempBinding, FormViewModel> {

    private FormEntity entity = new FormEntity();

    @Override
    public void initParam() {
        //获取列表传入的实体
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity");
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return R.layout.fragment_form_temp;
    }

    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }

    @Override
    public void initData() {
        //通过binding拿到toolbar控件, 设置给Activity
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.title.toolbar);
        //View层传参到ViewModel层
        viewModel.setFormEntity(entity);
    }

    /**
     * 初始化Toolbar
     */
    @Override
    public void initToolbar() {
        //初始化标题栏
        viewModel.setRightTextVisible(View.VISIBLE);
        if (TextUtils.isEmpty(entity.getId())) {
            //ID为空是新增
            viewModel.setTitleText("表单提交");
        } else {
            //ID不为空是修改
            viewModel.setTitleText("表单编辑");
        }
        binding.title.tvRightText.setOnClickListener(viewModel ->{
            ToastUtils.showShort("点击了更多");
        });
    }

    @Override
    public void initViewObservable() {
        //监听日期选择
        viewModel.uc.showDateDialogObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        viewModel.setBir(year, month, dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.setMessage("生日选择");
                datePickerDialog.show();
            }
        });
        viewModel.entityJsonLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String submitJson) {
                MaterialDialogUtils.showBasicDialog(getContext(), "提交的json实体数据：\r\n" + submitJson).show();
            }
        });
    }
}
