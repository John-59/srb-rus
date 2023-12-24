package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import kotlinx.coroutines.flow.Flow

interface IWritableRepository {

    val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    suspend fun add(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>>
}