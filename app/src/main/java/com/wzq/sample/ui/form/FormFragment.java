package com.wzq.sample.ui.form;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.mvvmsmart.utils.ToastUtils;
import com.wzq.sample.R;
import com.wzq.sample.base.BaseFragment;
import com.wzq.sample.bean.FormEntity;
import com.wzq.sample.databinding.FragmentFormTempBinding;
import com.wzq.sample.utils.MaterialDialogUtils;

import java.util.Calendar;


/**
 * Created by 王志强 on 2019/11/30.
 * 表单提交/编辑界面
 */

public class FormFragment extends BaseFragment<FragmentFormTempBinding, FormViewModel> {


    private FormEntity entity;

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
        binding.setPresenter(new Presenter());
        binding.switchId.setChecked(entity.getMarry());
        viewModel.entityLiveData.setValue(entity);
        //通过binding拿到toolbar控件, 设置给Activity
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.title.toolbar);
    }

    /**
     * 初始化Toolbar
     */
    @Override
    public void initToolbar() {

        /**
         *  此处AS对include的布局支持不友好,不影响编译和运行,开发者也可以换成下面方式
         *  TextView tvTitle = binding.getRoot().findViewById(R.id.tvTitle);
         */
        binding.title.tvTitle.setText("表单编辑");
        binding.title.ivRight.setOnClickListener(v -> ToastUtils.showShort("点击了更多"));
        binding.title.ivBack.setOnClickListener(
                v -> NavHostFragment.findNavController(FormFragment.this).navigateUp()
        );

    }

    /**
     * 封装布局中的点击事件儿;
     */
    public class Presenter {

        public void commitClick() {
            Toast.makeText(getActivity(), "触发提交按钮", Toast.LENGTH_SHORT).show();
            String submitJson = new Gson().toJson(viewModel.entityLiveData.getValue());
            MaterialDialogUtils.showBasicDialog(getContext(), "提交的json实体数据：\r\n" + submitJson).show();
        }


        public void showDateDialog() {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    FormEntity value = viewModel.entityLiveData.getValue();
                    //设置数据到实体中，自动刷新界面
                    value.setBir(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                    viewModel.entityLiveData.setValue(value);
                }
            }, year, month, day);
            datePickerDialog.setMessage("生日选择");
            datePickerDialog.show();
        }

    }


    @Override
    public void initViewObservable() {
        super.initViewObservable();
        binding.switchId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //是否已婚Switch点状态改变回调
                KLog.e("婚姻状态::" + isChecked);
                FormEntity value = viewModel.entityLiveData.getValue();
                value.setMarry(isChecked);
                viewModel.entityLiveData.setValue(value);

            }
        });
    }


}
