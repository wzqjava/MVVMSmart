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
package com.wzq.mvvmsmart.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Super simple single-type adapter using data-binding.
 * wzq  2019/12/7
 */
public class SingleTypeAdapter<T> extends BaseViewAdapter<T> {

    protected int mLayoutRes;

    public interface Presenter<T> extends BaseViewAdapter.Presenter {
        void onItemClick(T t);
    }

    public SingleTypeAdapter(Context context) {
        this(context, 0);
    }

    public SingleTypeAdapter(Context context, int layoutRes) {
        super(context);
        mCollection = new ArrayList<>();
        mLayoutRes = layoutRes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder(
                DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(), parent, false));
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    public void add(T data) {
        mCollection.add(data);
        notifyDataSetChanged();
    }

    public void add(int position, T data) {
        mCollection.add(position, data);
        notifyDataSetChanged();
    }

    public void set(List<T> datas) {
        mCollection.clear();
        addAll(datas);
    }

    public void addAll(List<T> datas) {
        mCollection.addAll(datas);
        notifyDataSetChanged();
    }

    @LayoutRes
    protected int getLayoutRes() {
        return mLayoutRes;
    }
}