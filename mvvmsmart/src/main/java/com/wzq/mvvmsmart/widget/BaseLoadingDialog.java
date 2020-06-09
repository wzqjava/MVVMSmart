package com.wzq.mvvmsmart.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.wzq.mvvmsmart.R;

/**
 * 封装loading
 **/
public class BaseLoadingDialog {

    private Dialog cusDialog;
    private final Handler HANDLER = new Handler(Looper.getMainLooper());
    private final Activity activity;
    private AnimationDrawable animationDrawable;

    /**
     * 展示加载
     */
    public void show(String s) {
        show(s, false);
    }

    public BaseLoadingDialog(Activity activity) {
        this.activity = activity;
    }

    /**
     * 展示加载
     */
    public void show(String s, boolean isCancelDialog) {
        if (cusDialog != null && cusDialog.isShowing()) {
            try {
                if (animationDrawable != null) animationDrawable.stop();
                cusDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
            cusDialog = null;
        }
        cusDialog = createLoadingDialog(activity, s);
        if (activity != null && !activity.isFinishing()) {
            try {
                cusDialog.show();
                if (animationDrawable != null) animationDrawable.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isCancelDialog) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, "加载失败，请手动重试", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }, 15000);
        }
    }

    /**
     * 销毁加载
     */
    public void dismiss() {
        try {
            HANDLER.removeCallbacksAndMessages(null);
            if (cusDialog != null) {
                if (animationDrawable != null) animationDrawable.stop();
                cusDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到自定义的progressDialog
     */
    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    private Dialog createLoadingDialog(Context context, String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.common_loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        animationDrawable = (AnimationDrawable) spaceshipImage.getDrawable();
        /*// 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);*/
        tipTextView.setText(msg);// 设置加载信息
        if (TextUtils.equals(msg, "")) {
            tipTextView.setVisibility(View.GONE);
        }
        Dialog loadingDialog = new Dialog(context, R.style.common_loading_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setCanceledOnTouchOutside(true);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        return loadingDialog;

    }

    public boolean isShowing() {
        return cusDialog != null && cusDialog.isShowing();
    }

    public void updateText(String text) {
        if (cusDialog != null) {
            TextView tipText = cusDialog.findViewById(R.id.tipTextView);
            tipText.setText(text);
        }

    }
}
