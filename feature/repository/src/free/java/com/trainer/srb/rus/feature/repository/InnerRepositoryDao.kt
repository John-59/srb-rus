package com.trainer.srb.rus.feature.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

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

    @Delete
    protected abstract suspend fun remove(word: SerbianLatinWord)

    @Delete
    protected abstract suspend fun remove(word: SerbianCyrillicWord)

    @Delete
    protected abstract suspend fun remove(words: List<RussianWord>)

    @Delete
    protected abstract suspend fun removeCrossRefs(crossRefTable: List<SerbianRussianCrossRefTable>)

    @Query("SELECT * FROM srb_lat WHERE id = :srbLatWordId")
    protected abstract suspend fun getWord(srbLatWordId: Long): SerbianToRussianWord?

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

    @Transaction
    open suspend fun remove(srbLatWordId: Long) {
        val word = getWord(srbLatWordId)
        if (word == null) {
            return
        }
        if (word.serbianCyr != null) {
            remove(word.serbianCyr)
        }
        val crossRefs = word.russians.map {
            SerbianRussianCrossRefTable(
                srbLatWordId = word.serbianLat.id,
                rusWordId = it.id
            )
        }
        removeCrossRefs(crossRefs)
        remove(word.russians)
        remove(word.serbianLat)
    }

    @Insert
    abstract suspend fun addLinkToUnused(unused: UnusedPredefined)

    @Query("SELECT * FROM unused_predefined")
    abstract fun getUnusedLinks(): Flow<List<UnusedPredefined>>

    @Query("SELECT * FROM srb_lat ORDER BY id DESC")
    abstract fun getAll(): Flow<List<SerbianToRussianWord>>

    @Query("SELECT * FROM srb_lat WHERE NOT unused ORDER BY RANDOM() LIMIT :randomTranslationsCount")
    abstract suspend fun getRandom(randomTranslationsCount: Int): List<SerbianToRussianWord>

    @Query("SELECT * FROM srb_lat WHERE word LIKE :value||'%'")
    abstract suspend fun searchInSrbLat(value: String): List<SerbianToRussianWord>
}