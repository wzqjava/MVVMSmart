package com.wzq.sample.ui.room_db;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.wzq.sample.R;
import com.wzq.sample.databinding.FragmentRoom1Binding;
import com.wzq.sample.base.BaseFragment;

import java.util.List;


/**
 * Create Date：2020/01/01
 *  实现Room数据的基本操作
 *  王志强
 */

public class RoomSampleFragment extends BaseFragment<FragmentRoom1Binding, RoomSampleViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_room1;
    }

    @Override
    public int initVariableId() {
        return com.wzq.sample.BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.getAllWordsLive().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(List<Word> words) {
                StringBuilder text = new StringBuilder();
                for (int i = 0; i < words.size(); i++) {
                    Word word = words.get(i);
                    text.append(word.getId()).append(":").append(word.getWord()).append("=").append(word.getChineseMeaning()).append("\n");
                }
                binding.textView.setText(text.toString());
            }
        });
        binding.buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word1 = new Word("Hello", "你好！");
                Word word2 = new Word("World", "世界！");
                viewModel.insertWords(word1, word2);
            }
        });

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.deleteAllWords();
            }
        });

        binding.buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("update", "更新!");
                word.setId(1);
                viewModel.updateWords(word);
            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Word word = new Word("update", "更新!");
                word.setId(1);
                viewModel.deleteWords(word);
            }
        });
    }
}
