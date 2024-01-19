package com.trainer.srb.rus.core.dictionary

import kotlinx.coroutines.flow.Flow

interface IDictionary {

    val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    val isNewWords: Flow<Boolean>

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

    suspend fun containsWordsForRepeat(): Boolean
}