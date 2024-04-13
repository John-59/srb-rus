package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.flow.Flow

interface IDictionary {

    val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    val translationsForRepeat: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    /**
     * The list of words for exercise "Repeat again".
     */
    val translationsForRepeatAgain: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    /**
     * Amount of words that the user selected for "Repeat again" exercise.
     */
    val translationsForRepeatAgainCount: Flow<Int>

    val isWordsForRepeat: Flow<Boolean>

    val isNewWords: Flow<Boolean>

    val isUnknownWords: Flow<Boolean>

    val totalTranslationsCount: Flow<Int>

    val userTranslationCount: Flow<Int>

    val learningTranslationsCount: Flow<Int>

    val learnedTranslationsCount: Flow<Int>

    suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>?

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun add(translation: Translation<Word.Serbian, Word.Russian>)

    /**
     * Add the translation to special part of the dictionary where store words for
     * the exercise "Repeat again".
     */
    suspend fun addToRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun edit(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun update(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>)

    /**
     * Remove the translation from special part of the dictionary where store words for
     * the exercise "Repeat again".
     */
    suspend fun removeFromRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun getRandom(
        randomTranslationsCount: Int,
        vararg statuses: LearningStatusName
    ): List<Translation<Word.Serbian, Word.Russian>>
}