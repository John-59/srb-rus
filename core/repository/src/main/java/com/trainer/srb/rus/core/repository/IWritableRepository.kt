package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.flow.Flow

interface IWritableRepository {

    val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    val predefinedStatuses: Flow<List<Pair<Long, LearningStatus>>>

    val totalTranslationsCount: Flow<Int>

//    val userTranslationCount: Flow<Int>

    val learningTranslationsCount: Flow<Int>

    val learnedTranslationsCount: Flow<Int>

    suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>?

    suspend fun add(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun update(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun addLinkToPredefinedTranslation(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun getRandom(
        randomTranslationsCount: Int,
        statuses: List<LearningStatusName>
    ): List<Translation<Word.Serbian, Word.Russian>>
}