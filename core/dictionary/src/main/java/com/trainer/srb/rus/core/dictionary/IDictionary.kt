package com.trainer.srb.rus.core.dictionary

import kotlinx.coroutines.flow.Flow

interface IDictionary {

    val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun add(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>)
}