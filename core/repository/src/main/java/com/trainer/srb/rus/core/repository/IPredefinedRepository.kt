package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import kotlinx.coroutines.flow.Flow

interface IPredefinedRepository {

    val srbToRusTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>>

    suspend fun addSrbToRusTranslation(srbToRusTranslation: Translation<Word.Serbian, Word.Russian>)

    suspend fun removeSrbToRusTranslation(srbToRusTranslation: Translation<Word.Serbian, Word.Russian>)

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>>
}