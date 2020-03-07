package com.wzq.sample.ui.recycler_single_network;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wzq.mvvmsmart.utils.GlideLoadUtils;
import com.wzq.mvvmsmart.utils.KLog;
import com.wzq.sample.R;
import com.wzq.sample.bean.DemoBean;

import java.util.List;

/**
 * author : 王志强
 * date   : 2020/01/09 11:34
 */
public class MAdapter extends BaseQuickAdapter<DemoBean.ItemsEntity, BaseViewHolder> {

    public MAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DemoBean.ItemsEntity item) {

        helper.setText(R.id.iv2, item.getName());
        KLog.e(item.getImg());
        GlideLoadUtils.loadRoundCornerImg(helper.getView(R.id.iv1),item.getImg(),R.mipmap.ic_launcher,10);
        helper.addOnClickListener(R.id.btn2);
    }

    @Override
    public void setNewData(@Nullable List<DemoBean.ItemsEntity> data) {
        super.setNewData(data);
    }

}
