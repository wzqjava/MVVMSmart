package com.wzq.sample.ui.room_sample;

import android.app.Application;

import com.wzq.mvvmsmart.base.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


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
