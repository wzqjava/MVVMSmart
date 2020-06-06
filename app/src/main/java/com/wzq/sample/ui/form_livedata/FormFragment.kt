package com.wzq.sample.ui.form_livedata

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.mvvmsmart.utils.ToastUtils
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseFragment
import com.wzq.sample.bean.FormEntity
import com.wzq.sample.databinding.FragmentFormTempBinding
import java.util.*

/**
 * Created by 王志强 on 2019/11/30.
 * 表单提交/编辑界面
 */
class FormFragment : BaseFragment<FragmentFormTempBinding, FormViewModel>() {
    private lateinit var entity: FormEntity
    override fun initParam() {
        //获取列表传入的实体
        val mBundle = arguments
        if (mBundle != null) {
            entity = mBundle.getSerializable("entity") as FormEntity
        }
    }

    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_form_temp
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        binding.presenter = Presenter()
        binding.switchId.isChecked = entity.marry
        viewModel.entityLiveData.value = entity
        //通过binding拿到toolbar控件, 设置给Activity
        (activity as AppCompatActivity?)?.setSupportActionBar(binding.title.toolbar)
    }

    /**
     * 初始化Toolbar
     */
    override fun initToolbar() {
        //ID不为空是修改
        binding.title.tvTitle.text = "表单编辑"
        binding.title.ivRight.setOnClickListener { ToastUtils.showShort("点击了更多") }
        binding.title.ivBack.setOnClickListener{ NavHostFragment.findNavController(this@FormFragment).navigateUp() }
    }

    /**
     * 封装布局中的点击事件儿;
     */
    inner class Presenter {
        fun commitClick() {
            Toast.makeText(activity, "触发提交按钮", Toast.LENGTH_SHORT).show()
            val submitJson = Gson().toJson(viewModel.entityLiveData.value)
//            MaterialDialogUtils.Companion.showBasicDialog(context, "提交的json实体数据：\r\n$submitJson").show()
        }

        fun showDateDialog() {
            val calendar = Calendar.getInstance()
            val year = calendar[Calendar.YEAR]
            val month = calendar[Calendar.MONTH]
            val day = calendar[Calendar.DAY_OF_MONTH]
            val datePickerDialog = context?.let {
                DatePickerDialog(it, OnDateSetListener { view, year, month, dayOfMonth ->
                    val value = viewModel.entityLiveData.value
                    //设置数据到实体中，自动刷新界面
                    value?.bir = year.toString() + "年" + (month + 1) + "月" + dayOfMonth + "日"
                    viewModel.entityLiveData.setValue(value)
                }, year, month, day)
            }
            datePickerDialog?.setMessage("生日选择")
            datePickerDialog?.show()
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        binding.switchId.setOnCheckedChangeListener { buttonView, isChecked -> //是否已婚Switch点状态改变回调
            KLog.e("婚姻状态::$isChecked")
            val value = viewModel.entityLiveData.value
            value?.marry= isChecked
            viewModel.entityLiveData.setValue(value)
        }
    }
}