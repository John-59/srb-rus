package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.flow.Flow

interface IDictionary {

    val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    val translationsForRepeat: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    val isWordsForRepeat: Flow<Boolean>

    val isNewWords: Flow<Boolean>

    val totalTranslationsCount: Flow<Int>

    val userTranslationCount: Flow<Int>

    val learningTranslationsCount: Flow<Int>

    val learnedTranslationsCount: Flow<Int>

    suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>?

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun add(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun edit(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun update(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun getRandom(
        randomTranslationsCount: Int,
        vararg statuses: LearningStatusName
    ): List<Translation<Word.Serbian, Word.Russian>>
}