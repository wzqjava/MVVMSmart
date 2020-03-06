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
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;
import com.wzq.mvvmsmart.BR;
import java.util.List;

/**
 * wzq 2019/12/7
 * Base Data Binding RecyclerView Adapter.
 */
public abstract class BaseViewAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> {

    protected final LayoutInflater mLayoutInflater;

    List<T> mCollection;
    protected Presenter mPresenter;
    protected Decorator mDecorator;

    public interface Presenter {

    }

    public interface Decorator {
        void decorator(BindingViewHolder holder, int position, int viewType);
    }

    public BaseViewAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        final Object item = mCollection.get(position);
        holder.getBinding().setVariable(BR.item,item);
        holder.getBinding().setVariable(BR.presenter, getPresenter());
        holder.getBinding().executePendingBindings();
        if (mDecorator != null) {
            mDecorator.decorator(holder, position, getItemViewType(position));
        }
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    public void remove(int position) {
        mCollection.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        mCollection.clear();
        notifyDataSetChanged();
    }

    public void setDecorator(Decorator decorator) {
        mDecorator = decorator;
    }

    public void setPresenter(Presenter presenter) {
        mPresenter = presenter;
    }

    protected Presenter getPresenter() {
        return mPresenter;
    }
}
