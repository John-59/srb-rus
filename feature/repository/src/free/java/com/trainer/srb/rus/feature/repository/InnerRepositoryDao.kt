package com.trainer.srb.rus.feature.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
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

    @Update
    protected abstract suspend fun update(word: SerbianLatinWord)

    @Update
    protected abstract suspend fun update(word: SerbianCyrillicWord)

    @Update
    protected abstract suspend fun update(words: List<RussianWord>)

    @Query("SELECT * FROM srb_lat WHERE id = :srbLatWordId")
    abstract suspend fun getWord(srbLatWordId: Long): SerbianToRussianWord?

    @Transaction
    open suspend fun insert(translationToRussian: TranslationToRussian) {
        val srbLatinWord = SerbianLatinWord(
            word = translationToRussian.srbLatWord,
            status = translationToRussian.status,
            statusDateTime = translationToRussian.statusDateTime
        )
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

    @Transaction
    open suspend fun update(srbToRussianWord: SerbianToRussianWord) {

        val oldWord = getWord(srbToRussianWord.serbianLat.id) ?: return

        update(srbToRussianWord.serbianLat)

        if (oldWord.serbianCyr != null && srbToRussianWord.serbianCyr == null) {
            remove(oldWord.serbianCyr)
        } else if (srbToRussianWord.serbianCyr != null) {
            if (oldWord.serbianCyr == null) {
                insert(srbToRussianWord.serbianCyr)
            } else {
                update(srbToRussianWord.serbianCyr)
            }
        }


        val removed = oldWord.russians.filter { oldRussian ->
            srbToRussianWord.russians.none { newRussian ->
                oldRussian.id == newRussian.id
            }
        }
        remove(removed)
        val removedCrossRefs = removed.map {
            SerbianRussianCrossRefTable(
                srbLatWordId = srbToRussianWord.serbianLat.id,
                rusWordId = it.id
            )
        }
        removeCrossRefs(removedCrossRefs)

        val added = srbToRussianWord.russians.filter { it.id == 0L }
        added.forEach {
            val id = insert(it)
            val crossRef = SerbianRussianCrossRefTable(
                srbLatWordId = srbToRussianWord.serbianLat.id,
                rusWordId = id
            )
            insert(crossRef)
        }

        val updated = srbToRussianWord.russians.filter { newRussian ->
            oldWord.russians.any { oldRussian ->
                oldRussian.id == newRussian.id
            }
        }
        update(updated)
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