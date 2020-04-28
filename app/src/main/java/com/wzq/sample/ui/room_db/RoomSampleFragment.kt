package com.wzq.sample.ui.room_db

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.wzq.mvvmsmart.utils.ToastUtils
import com.wzq.sample.BR
import com.wzq.sample.R
import com.wzq.sample.base.BaseFragment
import com.wzq.sample.databinding.FragmentRoom1Binding

/**
 * Create Date：2020/01/01
 * 实现Room数据的基本操作
 * 王志强
 */
class RoomSampleFragment : BaseFragment<FragmentRoom1Binding, RoomSampleViewModel>() {
    override fun initContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): Int {
        return R.layout.fragment_room1
    }

    override fun initVariableId(): Int {
        return BR.viewModel
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.allWordsLive.observe(this, Observer { words ->
            val text = StringBuilder()
            for (i in words.indices) {
                val word = words[i]
                text.append(word.id).append(":").append(word.word).append("=").append(word.chineseMeaning).append("\n")
            }
            binding.textView.text = text.toString()
        })
        binding.buttonInsert.setOnClickListener {
            val word1 = Word("Hello", "你好！")
            viewModel.insertWords(word1)
        }
        binding.buttonClear.setOnClickListener { viewModel.deleteAllWords() }
        binding.buttonUpdate.setOnClickListener {
            ToastUtils.showShort("更新 id=1 的数据...")
            val word = Word("update", "更新!")
            word.id = 1
            viewModel.updateWords(word)
        }
        binding.buttonDelete.setOnClickListener {
            ToastUtils.showShort("删除 id=1 的数据...")
            val word = Word("update", "更新!")
            word.id = 1
            viewModel.deleteWords(word)
        }
    }
}