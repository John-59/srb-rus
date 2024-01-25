package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.LearningStatusName
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import kotlinx.coroutines.flow.Flow

interface IPredefinedRepository {

    val usedTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>?

    suspend fun update(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun setStatusById(latinId: Long, status: LearningStatus)

    suspend fun resetStatuses()

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun getRandom(
        randomTranslationsCount: Int,
        statuses: List<LearningStatusName>
    ): List<Translation<Word.Serbian, Word.Russian>>
}