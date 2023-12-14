package com.trainer.srb.rus.core.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class PredefinedRepositoryDao {

    @Insert
    protected abstract fun insert(word: SerbianLatinWord): Long

    @Insert
    protected abstract fun insert(word: SerbianCyrillicWord)

    @Insert
    protected abstract fun insert(word: RussianWord): Long

    @Insert
    protected abstract fun insert(crossRefTable: SerbianRussianCrossRefTable)

    @Transaction
    open fun insert(translationToRussian: TranslationToRussian) {
        val srbLatinWord = SerbianLatinWord(word = translationToRussian.srbLatWord)
        val srbLatId = insert(srbLatinWord)
        if (translationToRussian.srbCyrWord.isNotBlank()) {
            val srbCyrillicWord =
                SerbianCyrillicWord(word = translationToRussian.srbCyrWord, latId = srbLatId)
            insert(srbCyrillicWord)
        }
        for (word in translationToRussian.rusWords) {
            val rusWord = RussianWord(word = word)
            val rusWordId = insert(rusWord)
            val crossRefTable = SerbianRussianCrossRefTable(srbLatWordId = srbLatId, rusWordId = rusWordId)
            insert(crossRefTable)
        }
    }

//    @Insert
//    fun insert(word: SerbianCyrillicWord): Long

//    @Transaction
//    @Query("SELECT * FROM srb_lat")
//    fun get(): List<SerbianWord>

//    @Query("SELECT * FROM rus")
//    fun getAllRus(): List<RussianWord>
}