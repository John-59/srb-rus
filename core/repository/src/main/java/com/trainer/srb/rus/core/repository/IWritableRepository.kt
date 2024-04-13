package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.SerbianLatinWordId
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.flow.Flow

interface IWritableRepository {

    val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    /**
     * The list of words for exercise "Repeat again".
     */
    val repeatAgainTranslationIds: Flow<List<SerbianLatinWordId>>

    /**
     * The quantity of the words that the user selected for exercise "Repeat again".
     */
    val repeatAgainTranslationCount: Flow<Int>

    val predefinedStatuses: Flow<List<Pair<Long, LearningStatus>>>

    val totalTranslationsCount: Flow<Int>

    val learningTranslationsCount: Flow<Int>

    val learnedTranslationsCount: Flow<Int>

    suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>?

    suspend fun add(translation: Translation<Word.Serbian, Word.Russian>)

    /**
     * Add the translation to special part of the dictionary where store words for
     * the exercise "Repeat again".
     */
    suspend fun addToRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>)

    /**
     * Remove the translation from special part of the dictionary where store words for
     * the exercise "Repeat again".
     */
    suspend fun removeFromRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun update(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun addLinkToPredefinedTranslation(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun getRandom(
        randomTranslationsCount: Int,
        statuses: List<LearningStatusName>
    ): List<Translation<Word.Serbian, Word.Russian>>
}