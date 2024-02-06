package com.trainer.srb.rus.core.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.trainer.srb.rus.core.translation.LearningStatusName
import kotlinx.coroutines.flow.Flow

@Dao
abstract class PredefinedRepositoryDao {

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
    protected abstract suspend fun update(word: SerbianCyrillicWord)

    @Update
    protected abstract suspend fun update(words: List<RussianWord>)

    @Query("SELECT * FROM srb_lat WHERE id = :srbLatWordId")
    abstract suspend fun getWord(srbLatWordId: Long): SerbianToRussianWord?

    @Query("UPDATE srb_lat SET status = '${WordStatus.Unknown.name}'")
    abstract suspend fun resetStatuses()

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
        val word = getWord(srbLatWordId) ?: return
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

    @Update
    abstract suspend fun update(word: SerbianLatinWord)

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

    @Query("SELECT * FROM srb_lat ORDER BY id DESC")
    abstract fun getAll(): Flow<List<SerbianToRussianWord>>

    @Query("SELECT * FROM srb_lat WHERE LOWER(status) <> LOWER('${WordStatus.Unused.name}') ORDER BY id DESC")
    abstract fun getUsed(): Flow<List<SerbianToRussianWord>>

    @Query("SELECT * FROM srb_lat WHERE LOWER(status) = LOWER('${WordStatus.Unused.name}')")
    abstract fun getUnused(): Flow<List<SerbianToRussianWord>>

    @Query("SELECT * FROM srb_lat WHERE LOWER(status) IN (:statuses) ORDER BY RANDOM() LIMIT :randomTranslationsCount")
    protected abstract suspend fun getRandom(randomTranslationsCount: Int, vararg statuses: String): List<SerbianToRussianWord>

    suspend fun getRandom(randomTranslationsCount: Int, statuses: List<LearningStatusName>): List<SerbianToRussianWord> {
        val statusesString = statuses.map {
            it.name.lowercase()
        }.toTypedArray()
        return getRandom(randomTranslationsCount, *statusesString)
    }

    @RawQuery
    abstract suspend fun checkpoint(supportSQLiteQuery: SupportSQLiteQuery): Int

    @Query("SELECT * FROM srb_lat WHERE word LIKE :value||'%'")
    abstract suspend fun searchInSrbLat(value: String): List<SerbianToRussianWord>
}