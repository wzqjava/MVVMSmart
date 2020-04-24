package com.wzq.sample.ui.room_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Word(@field:ColumnInfo(name = "english_word") var word: String, @field:ColumnInfo(name = "chinese_meaning") var chineseMeaning: String) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}