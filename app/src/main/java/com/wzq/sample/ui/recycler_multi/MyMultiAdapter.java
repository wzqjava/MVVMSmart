package com.wzq.sample.ui.recycler_multi;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wzq.mvvmsmart.utils.GlideLoadUtils;
import com.wzq.sample.R;
import com.wzq.sample.bean.DemoBean;

import java.util.List;

/**
 * author : 王志强
 * date   : 2020/01/09 11:34
 */
public class MyMultiAdapter extends BaseMultiItemQuickAdapter<DemoBean.ItemsEntity, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MyMultiAdapter(List<DemoBean.ItemsEntity> data) {
        super(data);
        addItemType(0, R.layout.item_multiple1);
        addItemType(1, R.layout.item_multiple2);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DemoBean.ItemsEntity item) {
        switch (helper.getItemViewType()) {
            case 0:
                GlideLoadUtils.loadRoundCornerImg((ImageView) helper.getView(R.id.iv1), item.getImg(),
                        R.mipmap.ic_launcher, 3);
                helper.setText(R.id.tv_name, item.getName());
                helper.addOnClickListener(R.id.btn1);
                break;
            case 1:
                GlideLoadUtils.loadRoundCornerImg((ImageView) helper.getView(R.id.iv1), item.getImg(),
                        R.mipmap.ic_launcher, 3);
                GlideLoadUtils.loadRoundCornerImg((ImageView) helper.getView(R.id.iv2), item.getImg(),
                        R.mipmap.ic_launcher, 3);
                helper.setText(R.id.tv_name, item.getName());
                helper.addOnClickListener(R.id.btn2);
                break;
        }
    }


    @Override
    public void setNewData(@Nullable List<DemoBean.ItemsEntity> datas) {
        super.setNewData(datas);
    }
}
