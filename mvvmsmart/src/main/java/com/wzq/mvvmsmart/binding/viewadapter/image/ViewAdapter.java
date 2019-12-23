package com.wzq.mvvmsmart.binding.viewadapter.image;


import android.text.TextUtils;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.wzq.mvvmsmart.utils.GlideLoadUtils;


public final class ViewAdapter {

    @BindingAdapter(value = {"imgUrl", "placeholderRes", "round"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes, int round) {

        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            GlideLoadUtils.loadRoundCornerImg(imageView, url, placeholderRes, round);
        }
    }
}

