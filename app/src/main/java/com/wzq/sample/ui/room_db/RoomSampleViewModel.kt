package com.wzq.sample.ui.room_db

import android.app.Application
import androidx.lifecycle.LiveData
import com.wzq.sample.base.BaseViewModel

/**
 * Create Date：2020/01/01
 * 实现Room数据的基本操作
 * 王志强
 */
class RoomSampleViewModel(application: Application) : BaseViewModel(application) {
    private val wordRepository: WordRepository = WordRepository(application)
    val allWordsLive: LiveData<List<Word>>
        get() = wordRepository.listLiveData

    fun insertWords(vararg words: Word?) {
        wordRepository.insertWords(*words)
    }

    fun updateWords(vararg words: Word?) {
        wordRepository.updateWords(*words)
    }

    fun deleteWords(vararg words: Word?) {
        wordRepository.deleteWords(*words)
    }

    fun deleteAllWords() {
        wordRepository.deleteAllWords()
    }

}