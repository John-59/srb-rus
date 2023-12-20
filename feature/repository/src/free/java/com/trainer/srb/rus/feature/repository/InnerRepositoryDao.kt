package com.trainer.srb.rus.feature.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class InnerRepositoryDao {

    @Insert
    protected abstract suspend fun insert(word: SerbianLatinWord): Long

    @Insert
    protected abstract suspend fun insert(word: SerbianCyrillicWord)

    @Insert
    protected abstract suspend fun insert(word: RussianWord): Long

    @Insert
    protected abstract suspend fun insert(crossRefTable: SerbianRussianCrossRefTable)

    @Transaction
    open suspend fun insert(translationToRussian: TranslationToRussian) {
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

    @Query("SELECT * FROM srb_lat ORDER BY word")
    abstract suspend fun getAllByAlphabet(): List<SerbianToRussianWord>

    @Query("SELECT * FROM srb_lat WHERE word LIKE :value||'%'")
    abstract suspend fun searchInSrbLat(value: String): List<SerbianToRussianWord>
}