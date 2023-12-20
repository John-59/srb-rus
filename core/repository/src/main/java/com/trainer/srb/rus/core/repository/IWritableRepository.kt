package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

interface IWritableRepository {
    suspend fun add(translation: Translation<Word.Serbian, Word.Russian>)

    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>>
}