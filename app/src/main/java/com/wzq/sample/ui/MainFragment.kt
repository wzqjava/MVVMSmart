package com.wzq.sample.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import com.wzq.mvvmsmart.http.DownLoadManager
import com.wzq.mvvmsmart.http.download.ProgressCallBack
import com.wzq.mvvmsmart.utils.KLog
import com.wzq.mvvmsmart.utils.ToastUtils
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseFragment
import com.wzq.sample.bean.FormEntity
import com.wzq.sample.databinding.FragmentHomeBinding
import com.wzq.sample.ui.tab_bar.activity.TabBarActivity
import okhttp3.ResponseBody

/**
 * 截止2019年12月21日累计投入时间:45小时
 * 本项目接口地址:  https://www.oschina.net/action/apiv2/banner?catalog=1
 */
class MainFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_home
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initData() {
        super.initData()
        binding.presenter = Presenter()
    }

    override fun initViewObservable() {
        super.initViewObservable()
        //注册监听相机权限的请求
        viewModel.requestCameraPermissions.observe(this, Observer { requestCameraPermissions() })

        //注册文件下载的监听
        viewModel.loadUrlEvent.observe(this, Observer { url -> downFile(url) })
    }

    /**
     * 封装布局中的点击事件儿;
     */
    inner class Presenter {
        //网络访问点击事件
       
        fun netWorkClick() {
            NavHostFragment
                    .findNavController(this@MainFragment)
                    .navigate(R.id.action_homeFragment_to_netWorkFragment)
        }

        //RecycleView多布局
       
        fun rvMultiClick() {
            NavHostFragment
                    .findNavController(this@MainFragment)
                    .navigate(R.id.action_homeFragment_to_multiRecycleViewFragment)
        }

        //进入TabBarActivity
       
        fun startTabBarClick() {
            startActivity(TabBarActivity::class.java)
        }

        //ViewPager绑定
       
        fun viewPagerBindingClick() {
            ToastUtils.showShort("点击跳转viewpager")
            NavHostFragment
                    .findNavController(this@MainFragment)
                    .navigate(R.id.action_homeFragment_to_viewPagerGroupFragment)
        }

        //表单修改点击事件
       
        fun formModifyClick() {
            //模拟一个修改的实体数据
            val entity = FormEntity("12345678","wzq","2020年08月08日",true)
            //传入实体数据
            val mBundle = Bundle()
            mBundle.putSerializable("entity", entity)
            NavHostFragment
                    .findNavController(this@MainFragment)
                    .navigate(R.id.action_homeFragment_to_formFragment, mBundle)
        }

        //权限申请
       
        fun permissionsClick() {
            viewModel.requestCameraPermissions.call()
        }

        //全局异常捕获
       
        fun exceptionClick() {
            //伪造一个异常
            "test".toInt()
        }

        //文件下载
        fun fileDownLoadClick() {
            viewModel.loadUrlEvent.value = "http://gdown.baidu.com/data/wisegame/a2cd8828b227b9f9/neihanduanzi_692.apk"
        }

        //文件下载
       
        fun roomSampleClick() {
            NavHostFragment
                    .findNavController(this@MainFragment)
                    .navigate(R.id.action_homeFragment_to_roomSampleFragment)
        }

        // 测试网络接口
       
        fun testNetUrl() {
            NavHostFragment
                    .findNavController(this@MainFragment)
                    .navigate(R.id.action_homeFragment_to_testNetFragment)
        }
    }

    /**
     * 请求相机权限
     */
    @SuppressLint("CheckResult")
    private fun requestCameraPermissions() {
        ToastUtils.showShort("请求相机权限")
        //请求打开相机权限
        val rxPermissions = RxPermissions(activity!!)
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { aBoolean ->
                    if (aBoolean) {
                        ToastUtils.showShort("相机权限已经打开，直接跳入相机")
                    } else {
                        ToastUtils.showShort("权限被拒绝")
                    }
                }
    }

    private fun downFile(url: String?) {
        val destFileDir = activity!!.application.cacheDir.path
        KLog.e("destFileDir--$destFileDir")
        val destFileName = System.currentTimeMillis().toString() + ".apk"
        val progressDialog = ProgressDialog(activity)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog.setTitle("正在下载...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        /**
         * ProgressCallBack构造方法中，LiveEventBus监听进度改变，调用ProgressCallBack的progress方法设置进度
         */
        DownLoadManager.instance?.load(url, object : ProgressCallBack<ResponseBody?>(this@MainFragment, destFileDir, destFileName) {
            override fun onStart() {
                super.onStart()
                KLog.e("下载--onStart")
            }

            override fun onSuccess(responseBody: Any?) {
                KLog.e("下载--onSuccess")

                ToastUtils.showShort("文件下载完成！")
            }

            override fun progress(progress: Long, total: Long) {
                KLog.e("下载--progress")
                progressDialog.max = total.toInt()
                progressDialog.progress = progress.toInt()
            }

            override fun onError(e: Throwable?) {
                e?.printStackTrace()
                ToastUtils.showShort("文件下载失败！")
                progressDialog.dismiss()
            }

            override fun onCompleted() {
                progressDialog.dismiss()
                KLog.e("下载--onCompleted")
            }
        })
    }


}