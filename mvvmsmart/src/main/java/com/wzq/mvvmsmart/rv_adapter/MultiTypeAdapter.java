/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wzq.mvvmsmart.rv_adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.collection.ArrayMap;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * wzq  2019/12/7
 * Super simple multi-type adapter using data-binding.
 */
public class MultiTypeAdapter extends BaseViewAdapter<Object> {
    protected ArrayList<Integer> mCollectionViewType; // 一个条目对应一个type,存入定义好的类型0或1;
    private ArrayMap<Integer, Integer> mItemTypeToLayoutMap = new ArrayMap<>(); //存放条目类型

    public interface MultiViewTyper {
        int getViewType(Object item);
    }

    public MultiTypeAdapter(Context context) {
        this(context, null);
    }

    public MultiTypeAdapter(Context context, Map<Integer, Integer> viewTypeToLayoutMap) {
        super(context);
        mCollection = new ArrayList<>();
        mCollectionViewType = new ArrayList<>();
        if (viewTypeToLayoutMap != null && !viewTypeToLayoutMap.isEmpty()) {
            mItemTypeToLayoutMap.putAll(viewTypeToLayoutMap);
        }
    }
/**
 *  王志强 2019/12/10
 *  把条目类型和条目布局绑定
 */
    public void addViewTypeToLayoutMap(Integer viewType, Integer layoutRes) {
        mItemTypeToLayoutMap.put(viewType, layoutRes);
    }

    @Override
    public int getItemViewType(int position) {
        return mCollectionViewType.get(position);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder(
                // 根据条目类型inflate布局 ,addViewTypeToLayoutMap方法已经把viewtype和条目布局关联
                DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(viewType), parent, false));
    }




    public void set(List datas, int viewType) {
        mCollection.clear();
        mCollectionViewType.clear();

        if (datas == null) {
            add(null, viewType);
        } else {
            addAll(datas, viewType);
        }
    }

    public void set(List datas, MultiViewTyper viewTyper) {
        mCollection.clear();
        mCollectionViewType.clear();
        addAll(datas, viewTyper);
    }

    public void add(Object data, int viewType) {
        mCollection.add(data);
        mCollectionViewType.add(viewType);
        notifyItemInserted(0);
    }

    public void add(int position, Object data, int viewType) {
        mCollection.add(position, data);
        mCollectionViewType.add(position, viewType);
        notifyItemInserted(position);
    }

    public void addAll(List datas, int viewType) {
        mCollection.addAll(datas);
        for (int i = 0; i < datas.size(); ++i) {
            mCollectionViewType.add(viewType);
        }
        notifyDataSetChanged();
    }

    public void addAll(int position, List datas, int viewType) {
        mCollection.addAll(position, datas);
        for (int i = 0; i < datas.size(); i++) {
            mCollectionViewType.add(position + i, viewType);
        }
        notifyItemRangeChanged(position, datas.size() - position);
    }

    public void addAll(List datas, MultiViewTyper multiViewTyper) {
        mCollection.addAll(datas);
        for (int i = 0; i < datas.size(); ++i) {
            mCollectionViewType.add(multiViewTyper.getViewType(datas.get(i)));
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mCollectionViewType.remove(position);
        super.remove(position);
    }

    public void clear() {
        mCollectionViewType.clear();
        super.clear();
    }

    @LayoutRes
    protected int getLayoutRes(int viewType) {
        return mItemTypeToLayoutMap.get(viewType);
    }
}
