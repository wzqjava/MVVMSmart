package com.wzq.sample.ui.room_db;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.wzq.sample.base.BaseViewModel;

import java.util.List;


/**
 * Create Date：2020/01/01
 *  实现Room数据的基本操作
 *  王志强
 */
public class RoomSampleViewModel extends BaseViewModel {

    private WordRepository wordRepository;

    public RoomSampleViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
    }

    LiveData<List<Word>> getAllWordsLive() {

        return wordRepository.getAllWordsLive();
    }

    void insertWords(Word... words) {
        wordRepository.insertWords(words);
    }

    void updateWords(Word... words) {
        wordRepository.updateWords(words);
    }

    void deleteWords(Word... words) {
        wordRepository.deleteWords(words);
    }

    void deleteAllWords() {
        wordRepository.deleteAllWords();
    }

}
