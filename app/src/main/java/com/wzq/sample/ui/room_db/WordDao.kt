package com.wzq.sample.ui.room_db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao // Database access object
interface WordDao {
    @Insert
    fun insertWords(vararg words: Word?)

    @Update
    fun updateWords(vararg words: Word?)

    @Delete
    fun deleteWords(vararg words: Word?)

    @Query("DELETE FROM WORD")
    fun deleteAllWords()

    //List<Word> getAllWords();
    @get:Query("SELECT * FROM WORD ORDER BY ID DESC")
    val allWordsLive: LiveData<List<Word>>
}