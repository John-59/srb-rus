package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import kotlinx.coroutines.flow.Flow

interface IPredefinedRepository {

    val usedTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    suspend fun markAsUnused(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>>
}