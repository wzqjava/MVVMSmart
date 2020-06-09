package com.wzq.mvvmsmart.utils

import android.app.Activity
import com.wzq.mvvmsmart.widget.BaseLoadingDialog

/**
 *<p>作者：王志强<p>
 * <p>创建时间：2020/6/8<p>
 * <p>文件描述：<p>
 */
open class LoadingUtil(private var activity: Activity?) {
    private var baseLoadingDialog: BaseLoadingDialog? = null

    /***
     * 显示英语端dialog
     * @param msg
     */
    fun showLoading(msg: String) {
        if (baseLoadingDialog == null) {
            baseLoadingDialog = BaseLoadingDialog(activity)
        }
        if (!(baseLoadingDialog!!.isShowing)) {
            baseLoadingDialog?.show(msg)
        } else {
            baseLoadingDialog?.updateText(msg)
        }
    }

    fun hideLoading() {
        if (baseLoadingDialog != null) {
            baseLoadingDialog?.dismiss()
        }
    }
}